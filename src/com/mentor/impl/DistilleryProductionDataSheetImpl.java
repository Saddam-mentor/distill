package com.mentor.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.mentor.action.DistilleryProductionDataSheetAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.ResourceUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DistilleryProductionDataSheetImpl {

	public static ArrayList getDistillery() {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue("0");
		list.add(item);
		try {
			String query = " select int_app_id_f,vch_undertaking_name  FROM  public.dis_mst_pd1_pd2_lic where vch_verify_flag='V' order by trim(vch_undertaking_name)";
			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				item = new SelectItem();
				item.setValue(rs.getString(1));
				item.setLabel(rs.getString(2));
				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public void getBL(DistilleryProductionDataSheetAction ac, String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		double a = 0, b = 0;
		try {
			con = ConnectionToDataBase.getConnection();
			query = "select COALESCE(round(sum(a.spirit_produced_by_grains)::numeric,2),0)spirit_produced_by_grains, "
					+ "COALESCE(round(sum(a.spirit_produced_by_molasses)::numeric,2),0)spirit_produced_by_molasses from( "
					+ "select case when vch_production='G' then sum(db_produced_bl) end as spirit_produced_by_grains, "
					+ "case when vch_production='M' then sum(db_produced_bl) end as spirit_produced_by_molasses "
					+ "from distillery.alchohal_production where int_dist_id='"	+ id + 
					"' and dt_date>='2019-04-01' " +
					" group by vch_production,int_season_id)a ";
			System.out.println("1=" + query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				ac.setSpirit_produced_by_grains(rs
						.getBigDecimal("spirit_produced_by_grains"));
				ac.setSpirit_produced_by_molasses(rs
						.getBigDecimal("spirit_produced_by_molasses"));
			 }  

			query = "select COALESCE(round(sum(recv_bl)::numeric,2),0) as spirit_purchased_up from distillery.import_spirit_in_state where distillery_id='"
					+ id + "'  and dt_created>='2019-04-01'";
			System.out.println("2=" + query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setSpirit_purchased_up(rs
						.getBigDecimal("spirit_purchased_up"));
			}  

			query = "select COALESCE(round(sum(quantity)::numeric,2),0) as spirit_purchased_export from distillery.spirit_import where distillery_id='"
					+ id + "' and dt_created>='2019-04-01'";
			System.out.println("3=" + query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setSpirit_purchased_export(rs
						.getBigDecimal("spirit_purchased_export"));
			}  
			query = " select COALESCE(round(sum(a.spirit_sold_export)::numeric,2),0)spirit_sold_export, "
					+ " COALESCE(round(sum(a.spirit_sold_up)::numeric,2),0)spirit_sold_up from( "
					+ " select case when vch_saletype='SUP' then vch_saletype else 'EXPORT' end as vch_saletype, "
					+ " case when vch_saletype='SUP' then sum(recv_bl) end as spirit_sold_up, "
					+ " case when vch_saletype!='SUP' then sum(recv_bl) end as spirit_sold_export "
					+ " from distillery.export_spirit_in_state where distillery_id='"
					+ id + "' and dt_created>='2019-04-01' group by vch_saletype)a	";
			System.out.println("4=" + query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setSpirit_sold_up(rs.getBigDecimal("spirit_sold_up"));
				ac.setSpirit_sold_export(rs.getBigDecimal("spirit_sold_export"));
			 } 
			//query = "select COALESCE(round(sum(consumed_bl)::numeric,2),0)spirit_used_cl_production from  distillery.spirit_for_bottling_cl where int_distillery_id='"+ id + "'";
			query = "select COALESCE(round(sum(recieve_bl)::numeric,2),0)spirit_used_cl_production from distillery.master_bottoling_of_vat_cl where distillery_id='"+ id + "' and created_date>='2019-04-01'";
			System.out.println("5=" + query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setSpirit_used_cl_production(rs.getBigDecimal("spirit_used_cl_production"));
			} 
			query = "select COALESCE(round(sum(recieve_bl)::numeric,2),0)spirit_used_fl_production from distillery.master_bottoling_of_vat where distillery_id='"+ id + "' and created_date>='2019-04-01'";
			//query = "select COALESCE(round(sum(consumed_bl)::numeric,2),0)spirit_used_fl_production from distillery.spirit_for_bottling where int_distillery_id='"+ id + "'";
			System.out.println("6=" + query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setSpirit_used_fl_production(rs.getBigDecimal("spirit_used_fl_production"));
			} 
			query = "select COALESCE(round(sum(y.cl_bl),2),0)cl_bl,COALESCE(round(sum(y.fl_bl),2),0)fl_bl from ( "
					+ "select case when x.liquor_type='3' then sum((((x.bottle)*f.qnt_ml_detail)/1000))*d.strength/36 end as cl_bl, "
					+ "case when x.liquor_type!='3' then sum((((x.bottle)*f.qnt_ml_detail)/1000)) end as fl_bl, x.liquor_type,d.strength from "
					+ "(select sum(bottling_No_of_Bottle) as bottle,liquor_type,brand_id,packaging_id from distillery.daily_bottling_stock_19_20 "
					+ "where distillery_id='"+ id+ "' and created_dt>='2019-04-01' group by liquor_type,brand_id,packaging_id)x, "
					+ "distillery.box_size_details f,distillery.brand_registration_19_20 d,distillery.packaging_details_19_20 e "
					+ "where x.brand_id=d.brand_id and e.box_id=f.box_id and x.packaging_id=e.package_id group by x.liquor_type,d.strength)y";
			System.out.println("7=" + query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setProduced_bl_for_cl(rs.getBigDecimal("cl_bl"));
				ac.setProduced_bl_for_fl(rs.getBigDecimal("fl_bl"));
			} 
		} catch (Exception se) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(se.getMessage(), se.getMessage()));
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(se.getMessage(), se.getMessage()));
			}
		}
	}

	public void getMolassesAndSpiritConsumed(DistilleryProductionDataSheetAction act,String id) throws UnsupportedEncodingException {
		String xml = "";
		org.apache.http.HttpResponse respons = null;
		org.apache.http.client.HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("https://www.upexcisewholesale.in/BarcodeQrcode/rest/datasheet/molassesspirit?distillery_id="+id+"");
		//HttpPost post = new HttpPost("http://192.168.1.143:2018/BarcodeQrcode/rest/datasheet/molassesspirit?distillery_id="+id+"");
		  System.out.println("1="+post);
		List nameValuePairs = new ArrayList(2);
	    nameValuePairs.add(new org.apache.http.message.BasicNameValuePair("distillery_id", id)); 
		try {
			//System.setProperty("https.protocols", "TLSv1");
			//System.setProperty("https.protocols", "TLSv1.2");
			respons = client.execute(post);
			
			xml = EntityUtils.toString((respons.getEntity()));
			JSONObject job = new JSONObject(xml);
			Object s = job.get("ConsumedMolassesAndSpirit");
			//System.out.println("sssssssssssssss" + s);
			JSONArray jary = new JSONArray(s.toString());
			JSONObject oo = jary.getJSONObject(0);
			System.out.println("o=" + oo.getString("molasses_consumed_reserved"));
			act.setMolasses_consumed_reserved(new BigDecimal(oo.getString("molasses_consumed_reserved")));	
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
}
