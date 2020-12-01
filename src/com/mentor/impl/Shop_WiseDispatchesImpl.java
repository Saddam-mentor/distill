package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mentor.action.Shop_WiseDispatchesAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class Shop_WiseDispatchesImpl {
	
	
	
	
	
	public ArrayList getDistrict() {
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SelectItem item = new SelectItem();
		String SQl="";
		if(ResourceUtil.getUserNameAllReq().trim().substring(0,10).equalsIgnoreCase("Excise-DEO")){
			  SQl =
					"select  distinct description ,districtid  from public.district  "
					+ "where    deo='"
					+ ResourceUtil.getUserNameAllReq().trim()
					+ "'"; 
		}else  {
 	  SQl =
		"select  distinct description ,districtid  from public.district  "
		+ " order by description "; 
		}
		
		
		 
		try {
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(SQl);
			rs = ps.executeQuery();
			while (rs.next()) {
				item = new SelectItem();
				item.setLabel(rs.getString("description"));
				item.setValue(rs.getString("districtid"));
				
				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	
	//===============================distict name
	
	
	public String getUserDetails(Shop_WiseDispatchesAction act) {


		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;

		
		try {
			con = ConnectionToDataBase.getConnection();
            
			 String selQr = " SELECT description, districtid FROM public.district " +
			 		        " WHERE deo='"+ ResourceUtil.getUserNameAllReq().trim() + "' ";

			 pstmt = con.prepareStatement(selQr);

			 
			rs = pstmt.executeQuery();

			if (rs.next()) {

				act.setDistrict_name(rs.getString("description"));
				
				act.setDistricId(rs.getInt("districtid"));	
			}
           
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (ps2 != null)
					ps2.close();
				if (rs != null)
					rs.close();
				if (rs2 != null)
					rs2.close();
				if (con != null)
					con.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return "";

	}

//===========================shop list
	
	
	
	public ArrayList getShop(Shop_WiseDispatchesAction act) {
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SelectItem item = new SelectItem();
		item.setLabel("--Select --");
		item.setValue("0");
		list.add(item);
		String SQl = null;

		String filter = "";

		if (act.getDistricId() == 9999) {
			filter = " is not null ";
		} else {
			filter = " = " + String.valueOf(act.getDistricId());

		}

		try {

			if (act.radio.equalsIgnoreCase("Foreign Liquor") ) {

				SQl = " select serial_no::text as serial_no ,vch_name_of_shop from retail.retail_shop"
						+ " where district_id "
						+ filter
						+ " and vch_shop_type ='Foreign Liquor'   " 
						+ "  order by vch_name_of_shop ";
				
			}

	
			else if (act.radio.equalsIgnoreCase("Model Shop")) {

				SQl = " select serial_no::text as serial_no ,vch_name_of_shop from retail.retail_shop"
						+ " where district_id "
						+ filter
						+ " and vch_shop_type='Model Shop'  "
						+ "  order by vch_name_of_shop";
			

			}else if ( act.radio.equals("Beer")) {

				SQl = " select serial_no::text as serial_no ,vch_name_of_shop from retail.retail_shop"
						+ " where district_id "
						+ filter
						+ " and vch_shop_type='Beer'  "
						+ "  order by vch_name_of_shop";

			}

			//System.out.println("===========radio========="+SQl);

			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(SQl);

			rs = ps.executeQuery();

			rs = ps.executeQuery();
			while (rs.next()) {
				item = new SelectItem();
				item.setLabel(rs.getString("vch_name_of_shop") + " - "
						+ rs.getString("serial_no"));
				item.setValue(rs.getString("serial_no"));
				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

  //======================================shop
	

	public void PrintReport(Shop_WiseDispatchesAction action) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "jasper";

		String relativePath1 = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "pdf";

		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;

		try {

			
			if ( action.getShopId()!=0) {

				
			       reportQuery ="select vch_name_of_shop,(select  description   from public.district  where districtid=district_id) as disttrict_name," +
" license_category,shop_id,sum(dispatch_bottle) as bottale,sum(duty) as duty,sum(bl) as bl from " +
" (select z.license_category,z.shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty,sum(bl) as bl from " + 
" ( select CASE when license_category in ('WINE','IMPORTED FL','IMPORTED WINE','IMFL') then 'FL' else 'BEER' end as license_category, " +
" vch_gatepass_no,shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(tduty) as duty,sum(bl) as bl from " +
" (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size, " +
" dispatch_bottle,(dispatch_bottle*p.duty+p.adduty*dispatch_bottle) as tduty,license_category, " +
" (dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a, " +
" fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b, " +
" distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p " +
" where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and " +
" b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and " +
" dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' )x GROUP BY vch_gatepass_no,shop_id,license_category)z group by z.license_category,z.shop_id " +
" union " +
 " select z.license_category,z.shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty,sum(bl) as bl from " +
" ( select CASE when license_category in ('WINE','IMPORTED FL','IMPORTED WINE','IMFL') then 'FL' else 'BEER' end as " +
" license_category,vch_gatepass_no,shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(tduty) as duty " +
 " ,sum(bl) as bl from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,dispatch_bottle, " +
"  (dispatch_bottle*p.duty+p.adduty*dispatch_bottle) as tduty,license_category, " +
"(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b, " +
" distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and " +
"b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' )x GROUP BY vch_gatepass_no, " +
"shop_id,license_category)z group by z.license_category,z.shop_id)m, " +
" retail.retail_shop s where district_id='"+action.getDistricId()+"' and vch_shop_type='"+action.getRadio()+"' and m.shop_id=sequence_no group by license_category, " +
"shop_id,vch_name_of_shop,disttrict_name";
			    		/*   
			    		   "select vch_name_of_shop,shop_id, sum(bl) as bl,sum(t_duty) as duty,sum(bottale) as bottale,disttrict_name from ( "+
						"select s.vch_name_of_shop,z.shop_id,z.bl ,zz.t_duty,z.bottale ,(select  description from public.district  where districtid=district_id) as disttrict_name from "+
							" (select shop_id,sum(bl) as bl,sum(dispatch_bottle) as bottale from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,dispatch_bottle, "+
							"(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a,fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b, "+
							" distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and  "+
							" b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date" +
							" between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' "+
							" and a.shop_id='"+action.getShopId()+"')x group by shop_id)z,(select shop_id,sum(a.total_duty+a.total_adduty) as t_duty from  "+
							" fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a where vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' and a.shop_id='"+action.getShopId()+"' group by "+
							" shop_id)zz,retail.retail_shop s where vch_shop_type='"+action.getRadio()+"' and  z.shop_id=zz.shop_id and z.shop_id=s.sequence_no and s.sequence_no=zz.shop_id   "+
							 
							" union "+
							 
							" select s.vch_name_of_shop,z.shop_id,z.bl ,zz.t_duty,z.bottale ,(select  description from public.district  where districtid=district_id) as disttrict_name from (select shop_id,sum(bl) as bl,sum(dispatch_bottle) as bottale from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,"+
							" dispatch_bottle,(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b, "+
							" distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and "+
							" b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and "+
							" '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' and a.shop_id='"+action.getShopId()+"')x group by shop_id)z,(select shop_id,sum(a.tot_cal_duty+a.total_add_duty)  as t_duty from "+
							" fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19  a where vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' and a.shop_id='"+action.getShopId()+"' group by "+
							" shop_id)zz,retail.retail_shop s where vch_shop_type='"+action.getRadio()+"' and  z.shop_id=zz.shop_id and z.shop_id=s.sequence_no and s.sequence_no=zz.shop_id)p group by   "+
							" disttrict_name,vch_name_of_shop,shop_id order by vch_name_of_shop ";*/
			       
			       
			System.out.println("======================printr"+reportQuery);	
			
			}
				


				
			
			else if(action.getDistricId()!=0) {
				 reportQuery ="select vch_name_of_shop,(select  description   from public.district  where districtid=district_id) as disttrict_name," +
						 " license_category,shop_id,sum(dispatch_bottle) as bottale,sum(duty) as duty,sum(bl) as bl from " +
						 " (select z.license_category,z.shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty,sum(bl) as bl from " + 
						 " ( select CASE when license_category in ('WINE','IMPORTED FL','IMPORTED WINE','IMFL') then 'FL' else 'BEER' end as license_category, " +
						 " vch_gatepass_no,shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(tduty) as duty,sum(bl) as bl from " +
						 " (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size, " +
						 " dispatch_bottle,(dispatch_bottle*p.duty+p.adduty*dispatch_bottle) as tduty,license_category, " +
						 " (dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a, " +
						 " fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b, " +
						 " distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p " +
						 " where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and " +
						 " b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and " +
						 " dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' )x GROUP BY vch_gatepass_no,shop_id,license_category)z group by z.license_category,z.shop_id " +
						 " union " +
						  " select z.license_category,z.shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty,sum(bl) as bl from " +
						 " ( select CASE when license_category in ('WINE','IMPORTED FL','IMPORTED WINE','IMFL') then 'FL' else 'BEER' end as " +
						 " license_category,vch_gatepass_no,shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(tduty) as duty " +
						  " ,sum(bl) as bl from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,dispatch_bottle, " +
						 "  (dispatch_bottle*p.duty+p.adduty*dispatch_bottle) as tduty,license_category, " +
						 "(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b, " +
						 " distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and " +
						 "b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' )x GROUP BY vch_gatepass_no, " +
						 "shop_id,license_category)z group by z.license_category,z.shop_id)m, " +
						 " retail.retail_shop s where district_id='"+action.getDistricId()+"' and vch_shop_type='"+action.getRadio()+"' and m.shop_id=sequence_no group by license_category, " +
						 "shop_id,vch_name_of_shop,disttrict_name";
				
				/*
				reportQuery =
				
					"	select vch_name_of_shop,shop_id, sum(bl) as bl,sum(t_duty) as duty,sum(bottale) as bottale ,disttrict_name from ( " +
					"			select s.vch_name_of_shop,z.shop_id,z.bl ,zz.t_duty,z.bottale ,(select  description   "+
                       "     from public.district  where districtid=district_id) as disttrict_name from " +
						"			(select shop_id,sum(bl) as bl,sum(dispatch_bottle) as bottale from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,dispatch_bottle, " +
						"			(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a,fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b, " +
						"		distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and   " +
						"		b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"')x group by shop_id)z,(select shop_id,sum(a.total_duty+a.total_adduty) as t_duty from " +
						"		 fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a where vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' group by " +
						"		 shop_id)zz,retail.retail_shop s where district_id='"+action.getDistricId()+"' and vch_shop_type='"+action.getRadio()+"' and  z.shop_id=zz.shop_id and z.shop_id=s.sequence_no and s.sequence_no=zz.shop_id    " +
								  
						"		 union  " +
								 
						"		 select s.vch_name_of_shop,z.shop_id,z.bl ,zz.t_duty,z.bottale,(select  description   from public.district  where districtid=district_id) as disttrict_name  from (select shop_id,sum(bl) as bl,sum(dispatch_bottle) as bottale from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,  " +

						"		 dispatch_bottle,(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b,  " +
						"		 distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and   " +
						"		 b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and   " +
						"		'"+Utility.convertUtilDateToSQLDate(action.getTodate())+"')x group by shop_id)z,(select shop_id,sum(a.tot_cal_duty+a.total_add_duty)  as t_duty from    " +
						"		 fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19  a where vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' group by   " +
						"		 shop_id)zz,retail.retail_shop s where district_id='"+action.getDistricId()+"' and vch_shop_type='"+action.getRadio()+"' and  z.shop_id=zz.shop_id and z.shop_id=s.sequence_no and s.sequence_no=zz.shop_id)p group by    " +
						"		  disttrict_name,vch_name_of_shop,shop_id order by vch_name_of_shop  ";
				*/
				System.out.println("dis======================"+reportQuery);
				
				
			}
			
			
			else {
				 reportQuery ="select vch_name_of_shop,(select  description   from public.district  where districtid=district_id) as disttrict_name," +
						 " license_category,shop_id,sum(dispatch_bottle) as bottale,sum(duty) as duty,sum(bl) as bl from " +
						 " (select z.license_category,z.shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty,sum(bl) as bl from " + 
						 " ( select CASE when license_category in ('WINE','IMPORTED FL','IMPORTED WINE','IMFL') then 'FL' else 'BEER' end as license_category, " +
						 " vch_gatepass_no,shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(tduty) as duty,sum(bl) as bl from " +
						 " (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size, " +
						 " dispatch_bottle,(dispatch_bottle*p.duty+p.adduty*dispatch_bottle) as tduty,license_category, " +
						 " (dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a, " +
						 " fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b, " +
						 " distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p " +
						 " where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and " +
						 " b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and " +
						 " dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' )x GROUP BY vch_gatepass_no,shop_id,license_category)z group by z.license_category,z.shop_id " +
						 " union " +
						  " select z.license_category,z.shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty,sum(bl) as bl from " +
						 " ( select CASE when license_category in ('WINE','IMPORTED FL','IMPORTED WINE','IMFL') then 'FL' else 'BEER' end as " +
						 " license_category,vch_gatepass_no,shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(tduty) as duty " +
						  " ,sum(bl) as bl from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,dispatch_bottle, " +
						 "  (dispatch_bottle*p.duty+p.adduty*dispatch_bottle) as tduty,license_category, " +
						 "(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b, " +
						 " distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and " +
						 "b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' )x GROUP BY vch_gatepass_no, " +
						 "shop_id,license_category)z group by z.license_category,z.shop_id)m, " +
						 " retail.retail_shop s where district_id='"+action.getDistricId()+"' and vch_shop_type='"+action.getRadio()+"' and m.shop_id=sequence_no group by license_category, " +
						 "shop_id,vch_name_of_shop,disttrict_name";

				/*reportQuery =
				
					"	select vch_name_of_shop,shop_id, sum(bl) as bl,sum(t_duty) as duty,sum(bottale) as bottale ,disttrict_name from ( " +
					"			select s.vch_name_of_shop,z.shop_id,z.bl ,zz.t_duty,z.bottale ,(select  description   "+
                       "     from public.district  where districtid=district_id) as disttrict_name from " +
						"			(select shop_id,sum(bl) as bl,sum(dispatch_bottle) as bottale from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,dispatch_bottle, " +
						"			(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a,fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b, " +
						"		distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and   " +
						"		b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"')x group by shop_id)z,(select shop_id,sum(a.total_duty+a.total_adduty) as t_duty from " +
						"		 fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a where vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' group by " +
						"		 shop_id)zz,retail.retail_shop s where  vch_shop_type='"+action.getRadio()+"' and  z.shop_id=zz.shop_id and z.shop_id=s.sequence_no and s.sequence_no=zz.shop_id    " +
								  
						"		 union  " +
								 
						"		 select s.vch_name_of_shop,z.shop_id,z.bl ,zz.t_duty,z.bottale,(select  description   from public.district  where districtid=district_id) as disttrict_name  from (select shop_id,sum(bl) as bl,sum(dispatch_bottle) as bottale from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,  " +

						"		 dispatch_bottle,(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b,  " +
						"		 distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and   " +
						"		 b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and   " +
						"		'"+Utility.convertUtilDateToSQLDate(action.getTodate())+"')x group by shop_id)z,(select shop_id,sum(a.tot_cal_duty+a.total_add_duty)  as t_duty from    " +
						"		 fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19  a where vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' group by   " +
						"		 shop_id)zz,retail.retail_shop s where vch_shop_type='"+action.getRadio()+"' and  z.shop_id=zz.shop_id and z.shop_id=s.sequence_no and s.sequence_no=zz.shop_id)p group by    " +
						"		  disttrict_name,vch_name_of_shop,shop_id order by vch_name_of_shop  ";
				
				*/
				
				
				
				System.out.println("else============="+reportQuery);
				
			}
               

			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(reportQuery);

			rs = ps.executeQuery();

			if (rs.next()) {

				ps = con.prepareStatement(reportQuery);
				rs = ps.executeQuery();

				while (rs.next()) {

					rs = ps.executeQuery();
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				

						jasperReport = (JasperReport) JRLoader
								.loadObject(relativePath
										+ File.separator
										+ "shopwise_Dispatches_oldStock_2018_19.jasper");

				

					Map<String, Object> parameters = new HashMap<String, Object>();

					parameters.put("REPORT_CONNECTION", con);
					parameters.put("SUBREPORT_DIR", relativePath
							+ File.separator);

					parameters.put("image", mypath + File.separator
							+ "ExciseUp" + File.separator + "image"
							+ File.separator);

					parameters.put("type", action.getRadio());
					

		
					parameters.put("fromDate", action.getFromdate());
					parameters.put("toDate", action.getTodate());
					parameters.put("district", action.getDistricList());
					

					jasperPrint = JasperFillManager.fillReport(jasperReport,
							parameters, jrRs);

				}

				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager
						.exportReportToPdfFile(jasperPrint, relativePath1
								+ File.separator
								+ "shopwise_Dispatches_oldStock_2018_19" + n + ".pdf");

				action.setPdfname("shopwise_Dispatches_oldStock_2018_19" + n + ".pdf");

				action.setPrintFlag(true);

			} else {

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"No Records Found !", "No Records Found !"));
			}

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), e.getMessage()));
			e.printStackTrace();
		} finally {

			try {
				con.close();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e
								.getMessage(), e.getMessage()));
				e.printStackTrace();
			}
		}

	}
	
	
	
	//----------------------generate excel ------------------------------
	
		public boolean writeNameWise(Shop_WiseDispatchesAction action)
		{


			Connection con = null;
			con = ConnectionToDataBase.getConnection();
			String reportQuery = null;
			double Bottles = 0;
			double BL = 0;
			double Duty = 0;


			if ( action.getShopId()!=0) {

				 reportQuery ="select vch_name_of_shop,(select  description   from public.district  where districtid=district_id) as disttrict_name," +
						 " license_category,shop_id,sum(dispatch_bottle) as bottale,sum(duty) as duty,sum(bl) as bl from " +
						 " (select z.license_category,z.shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty,sum(bl) as bl from " + 
						 " ( select CASE when license_category in ('WINE','IMPORTED FL','IMPORTED WINE','IMFL') then 'FL' else 'BEER' end as license_category, " +
						 " vch_gatepass_no,shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(tduty) as duty,sum(bl) as bl from " +
						 " (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size, " +
						 " dispatch_bottle,(dispatch_bottle*p.duty+p.adduty*dispatch_bottle) as tduty,license_category, " +
						 " (dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a, " +
						 " fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b, " +
						 " distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p " +
						 " where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and " +
						 " b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and " +
						 " dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' )x GROUP BY vch_gatepass_no,shop_id,license_category)z group by z.license_category,z.shop_id " +
						 " union " +
						  " select z.license_category,z.shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty,sum(bl) as bl from " +
						 " ( select CASE when license_category in ('WINE','IMPORTED FL','IMPORTED WINE','IMFL') then 'FL' else 'BEER' end as " +
						 " license_category,vch_gatepass_no,shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(tduty) as duty " +
						  " ,sum(bl) as bl from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,dispatch_bottle, " +
						 "  (dispatch_bottle*p.duty+p.adduty*dispatch_bottle) as tduty,license_category, " +
						 "(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b, " +
						 " distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and " +
						 "b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' )x GROUP BY vch_gatepass_no, " +
						 "shop_id,license_category)z group by z.license_category,z.shop_id)m, " +
						 " retail.retail_shop s where district_id='"+action.getDistricId()+"' and vch_shop_type='"+action.getRadio()+"' and m.shop_id=sequence_no group by license_category, " +
						 "shop_id,vch_name_of_shop,disttrict_name";
				
			     /*  reportQuery ="select vch_name_of_shop,shop_id, sum(bl) as bl,sum(t_duty) as duty,sum(bottale) as bottale,disttrict_name from ( "+
							"select s.vch_name_of_shop,z.shop_id,z.bl ,zz.t_duty,z.bottale ,(select  description from public.district  where districtid=district_id) as disttrict_name from "+
							" (select shop_id,sum(bl) as bl,sum(dispatch_bottle) as bottale from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,dispatch_bottle, "+
							"(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a,fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b, "+
							" distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and  "+
							" b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date" +
							" between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' "+
							" and a.shop_id='"+action.getShopId()+"')x group by shop_id)z,(select shop_id,sum(a.total_duty+a.total_adduty) as t_duty from  "+
							" fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a where vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' and a.shop_id='"+action.getShopId()+"' group by "+
							" shop_id)zz,retail.retail_shop s where vch_shop_type='"+action.getRadio()+"' and  z.shop_id=zz.shop_id and z.shop_id=s.sequence_no and s.sequence_no=zz.shop_id   "+
							 
							" union "+
							 
							" select s.vch_name_of_shop,z.shop_id,z.bl ,zz.t_duty,z.bottale ,(select  description from public.district  where districtid=district_id) as disttrict_name from (select shop_id,sum(bl) as bl,sum(dispatch_bottle) as bottale from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,"+
							" dispatch_bottle,(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b, "+
							" distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and "+
							" b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and "+
							" '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' and a.shop_id='"+action.getShopId()+"')x group by shop_id)z,(select shop_id,sum(a.tot_cal_duty+a.total_add_duty)  as t_duty from "+
							" fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19  a where vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' and a.shop_id='"+action.getShopId()+"' group by "+
							" shop_id)zz,retail.retail_shop s where vch_shop_type='"+action.getRadio()+"' and  z.shop_id=zz.shop_id and z.shop_id=s.sequence_no and s.sequence_no=zz.shop_id)p group by   "+
							" disttrict_name,vch_name_of_shop,shop_id order by vch_name_of_shop ";*/
				
			
			}
				


				
			
			else if(action.getDistricId()!=0) {
				 reportQuery ="select vch_name_of_shop,(select  description   from public.district  where districtid=district_id) as disttrict_name," +
						 " license_category,shop_id,sum(dispatch_bottle) as bottale,sum(duty) as duty,sum(bl) as bl from " +
						 " (select z.license_category,z.shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty,sum(bl) as bl from " + 
						 " ( select CASE when license_category in ('WINE','IMPORTED FL','IMPORTED WINE','IMFL') then 'FL' else 'BEER' end as license_category, " +
						 " vch_gatepass_no,shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(tduty) as duty,sum(bl) as bl from " +
						 " (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size, " +
						 " dispatch_bottle,(dispatch_bottle*p.duty+p.adduty*dispatch_bottle) as tduty,license_category, " +
						 " (dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a, " +
						 " fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b, " +
						 " distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p " +
						 " where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and " +
						 " b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and " +
						 " dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' )x GROUP BY vch_gatepass_no,shop_id,license_category)z group by z.license_category,z.shop_id " +
						 " union " +
						  " select z.license_category,z.shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty,sum(bl) as bl from " +
						 " ( select CASE when license_category in ('WINE','IMPORTED FL','IMPORTED WINE','IMFL') then 'FL' else 'BEER' end as " +
						 " license_category,vch_gatepass_no,shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(tduty) as duty " +
						  " ,sum(bl) as bl from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,dispatch_bottle, " +
						 "  (dispatch_bottle*p.duty+p.adduty*dispatch_bottle) as tduty,license_category, " +
						 "(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b, " +
						 " distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and " +
						 "b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' )x GROUP BY vch_gatepass_no, " +
						 "shop_id,license_category)z group by z.license_category,z.shop_id)m, " +
						 " retail.retail_shop s where district_id='"+action.getDistricId()+"' and vch_shop_type='"+action.getRadio()+"' and m.shop_id=sequence_no group by license_category, " +
						 "shop_id,vch_name_of_shop,disttrict_name";
				
				/*reportQuery =
				
					"	select vch_name_of_shop,shop_id, sum(bl) as bl,sum(t_duty) as duty,sum(bottale) as bottale ,disttrict_name from ( " +
					"			select s.vch_name_of_shop,z.shop_id,z.bl ,zz.t_duty,z.bottale ,(select  description   "+
                       "     from public.district  where districtid=district_id) as disttrict_name from " +
						"			(select shop_id,sum(bl) as bl,sum(dispatch_bottle) as bottale from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,dispatch_bottle, " +
						"			(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a,fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b, " +
						"		distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and   " +
						"		b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"')x group by shop_id)z,(select shop_id,sum(a.total_duty+a.total_adduty) as t_duty from " +
						"		 fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a where vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' group by " +
						"		 shop_id)zz,retail.retail_shop s where district_id='"+action.getDistricId()+"' and vch_shop_type='"+action.getRadio()+"' and  z.shop_id=zz.shop_id and z.shop_id=s.sequence_no and s.sequence_no=zz.shop_id    " +
								  
						"		 union  " +
								 
						"		 select s.vch_name_of_shop,z.shop_id,z.bl ,zz.t_duty,z.bottale,(select  description   from public.district  where districtid=district_id) as disttrict_name  from (select shop_id,sum(bl) as bl,sum(dispatch_bottle) as bottale from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,  " +

						"		 dispatch_bottle,(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b,  " +
						"		 distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and   " +
						"		 b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and   " +
						"		'"+Utility.convertUtilDateToSQLDate(action.getTodate())+"')x group by shop_id)z,(select shop_id,sum(a.tot_cal_duty+a.total_add_duty)  as t_duty from    " +
						"		 fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19  a where vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' group by   " +
						"		 shop_id)zz,retail.retail_shop s where district_id='"+action.getDistricId()+"' and vch_shop_type='"+action.getRadio()+"' and  z.shop_id=zz.shop_id and z.shop_id=s.sequence_no and s.sequence_no=zz.shop_id)p group by    " +
						"		  disttrict_name,vch_name_of_shop,shop_id order by vch_name_of_shop  ";
				*/
				
				
				
			}
			
			
			else {
				

				/*reportQuery =
				
					"	select vch_name_of_shop,shop_id, sum(bl) as bl,sum(t_duty) as duty,sum(bottale) as bottale ,disttrict_name from ( " +
					"			select s.vch_name_of_shop,z.shop_id,z.bl ,zz.t_duty,z.bottale ,(select  description   "+
                       "     from public.district  where districtid=district_id) as disttrict_name from " +
						"			(select shop_id,sum(bl) as bl,sum(dispatch_bottle) as bottale from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,dispatch_bottle, " +
						"			(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a,fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b, " +
						"		distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and   " +
						"		b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"')x group by shop_id)z,(select shop_id,sum(a.total_duty+a.total_adduty) as t_duty from " +
						"		 fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a where vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' group by " +
						"		 shop_id)zz,retail.retail_shop s where  vch_shop_type='"+action.getRadio()+"' and  z.shop_id=zz.shop_id and z.shop_id=s.sequence_no and s.sequence_no=zz.shop_id    " +
								  
						"		 union  " +
								 
						"		 select s.vch_name_of_shop,z.shop_id,z.bl ,zz.t_duty,z.bottale,(select  description   from public.district  where districtid=district_id) as disttrict_name  from (select shop_id,sum(bl) as bl,sum(dispatch_bottle) as bottale from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,  " +

						"		 dispatch_bottle,(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b,  " +
						"		 distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and   " +
						"		 b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and   " +
						"		'"+Utility.convertUtilDateToSQLDate(action.getTodate())+"')x group by shop_id)z,(select shop_id,sum(a.tot_cal_duty+a.total_add_duty)  as t_duty from    " +
						"		 fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19  a where vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' group by   " +
						"		 shop_id)zz,retail.retail_shop s where vch_shop_type='"+action.getRadio()+"' and  z.shop_id=zz.shop_id and z.shop_id=s.sequence_no and s.sequence_no=zz.shop_id)p group by    " +
						"		  disttrict_name,vch_name_of_shop,shop_id order by vch_name_of_shop  ";
				*/
				
				 reportQuery ="select vch_name_of_shop,(select  description   from public.district  where districtid=district_id) as disttrict_name," +
						 " license_category,shop_id,sum(dispatch_bottle) as bottale,sum(duty) as duty,sum(bl) as bl from " +
						 " (select z.license_category,z.shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty,sum(bl) as bl from " + 
						 " ( select CASE when license_category in ('WINE','IMPORTED FL','IMPORTED WINE','IMFL') then 'FL' else 'BEER' end as license_category, " +
						 " vch_gatepass_no,shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(tduty) as duty,sum(bl) as bl from " +
						 " (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size, " +
						 " dispatch_bottle,(dispatch_bottle*p.duty+p.adduty*dispatch_bottle) as tduty,license_category, " +
						 " (dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a, " +
						 " fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b, " +
						 " distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p " +
						 " where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and " +
						 " b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and " +
						 " dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' )x GROUP BY vch_gatepass_no,shop_id,license_category)z group by z.license_category,z.shop_id " +
						 " union " +
						  " select z.license_category,z.shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty,sum(bl) as bl from " +
						 " ( select CASE when license_category in ('WINE','IMPORTED FL','IMPORTED WINE','IMFL') then 'FL' else 'BEER' end as " +
						 " license_category,vch_gatepass_no,shop_id,sum(dispatch_bottle) as dispatch_bottle,sum(tduty) as duty " +
						  " ,sum(bl) as bl from (select a.vch_gatepass_no,a.shop_id,int_brand_id,int_pckg_id,size,dispatch_bottle, " +
						 "  (dispatch_bottle*p.duty+p.adduty*dispatch_bottle) as tduty,license_category, " +
						 "(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b, " +
						 " distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id and br.brand_id=p.brand_id_fk and " +
						 "b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no and vch_to='RT' and dt_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' )x GROUP BY vch_gatepass_no, " +
						 "shop_id,license_category)z group by z.license_category,z.shop_id)m, " +
						 " retail.retail_shop s where district_id='"+action.getDistricId()+"' and vch_shop_type='"+action.getRadio()+"' and m.shop_id=sequence_no group by license_category, " +
						 "shop_id,vch_name_of_shop,disttrict_name";
				
				
				
				
				
			}
			
			String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
			FileOutputStream fileOut = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean flag = false;
			long k = 0;
			String date = null;

			try {			
				
			 
			    pstmt = con.prepareStatement(reportQuery);
				

				rs = pstmt.executeQuery();
				
				
				if (rs.next()) {
					
					  pstmt = con.prepareStatement(reportQuery);
						

						rs = pstmt.executeQuery();

				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet worksheet = workbook.createSheet("Shop Wise Dispatches Old Stock 2018-19");

				worksheet.setColumnWidth(0, 3000);
				worksheet.setColumnWidth(1, 4000);

				worksheet.setColumnWidth(2, 10000);

				worksheet.setColumnWidth(3, 15000);
				worksheet.setColumnWidth(4, 7000);
				worksheet.setColumnWidth(5, 7000);
				worksheet.setColumnWidth(6, 7000);
				worksheet.setColumnWidth(7, 10000);
				worksheet.setColumnWidth(8, 9000);

				XSSFRow rowhead0 = worksheet.createRow((int) 0);
				XSSFCell cellhead0 = rowhead0.createCell((int) 0);
				cellhead0.setCellValue("Shop Wise Dispatches : "+ " " + Utility.convertUtilDateToSQLDate(action.getFromdate())+ " " + 
				" To " + " "+ Utility.convertUtilDateToSQLDate(action.getTodate())+" Shop Type : " + "" + action.getRadio() + " ");
				
				
				rowhead0.setHeight((short) 700);
				XSSFCellStyle cellStyl = workbook.createCellStyle();
				cellStyl = workbook.createCellStyle();
				XSSFFont hSSFFont = workbook.createFont();
				hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
				hSSFFont.setFontHeightInPoints((short) 12);
				hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				hSSFFont.setColor(HSSFColor.GREEN.index);
				cellStyl.setFont(hSSFFont);
				cellhead0.setCellStyle(cellStyl);
				XSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				XSSFCellStyle unlockcellStyle = workbook.createCellStyle();
				unlockcellStyle.setLocked(false);
				k = k + 1;

				XSSFRow rowhead = worksheet.createRow((int) 1);

				XSSFCell cellhead1 = rowhead.createCell((int) 0);
				cellhead1.setCellValue("S.No.");
				cellhead1.setCellStyle(cellStyle);
               

				
				XSSFCell cellhead2 = rowhead.createCell((int) 1);
				cellhead2.setCellValue("District Name");
				cellhead2.setCellStyle(cellStyle);
				
				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("Shop ID");
				cellhead3.setCellStyle(cellStyle);

				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("Shop Name");
				cellhead4.setCellStyle(cellStyle);

				XSSFCell cellhead5 = rowhead.createCell((int) 4);
				cellhead5.setCellValue("Duty");
				cellhead5.setCellStyle(cellStyle);

				XSSFCell cellhead6 = rowhead.createCell((int) 5);
				cellhead6.setCellValue("BL");
				cellhead6.setCellStyle(cellStyle);

				XSSFCell cellhead7 = rowhead.createCell((int) 6);
				cellhead7.setCellValue("Bottles");
				cellhead7.setCellStyle(cellStyle);

				XSSFCell cellhead8 = rowhead.createCell((int) 7);
				cellhead8.setCellValue("liquor Type");
				cellhead8.setCellStyle(cellStyle);
				

				while (rs.next()) {

					//Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt"));
					DateFormat formatter;
					formatter = new SimpleDateFormat("dd/MM/yyyy");
					//date = formatter.format(dat);
					

					Duty = Duty + rs.getDouble("duty");
					BL = BL + rs.getDouble("bl");
					Bottles = Bottles + rs.getDouble("bottale");
					
					k++;

					XSSFRow row1 = worksheet.createRow((int) k);

					XSSFCell cellA1 = row1.createCell((int) 0);
					cellA1.setCellValue(k-1);
                   
					

					
					
					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("disttrict_name"));

					XSSFCell cellK1 = row1.createCell((int) 2);
					cellK1.setCellValue(rs.getString("shop_id"));

					XSSFCell cellC1 = row1.createCell((int) 3);
					cellC1.setCellValue(rs.getString("vch_name_of_shop"));

					XSSFCell cellD1 = row1.createCell((int) 4);
					cellD1.setCellValue(rs.getDouble("duty"));

					XSSFCell cellE1 = row1.createCell((int) 5);
					cellE1.setCellValue(rs.getDouble("bl"));

					XSSFCell cellF1 = row1.createCell((int) 6);
					cellF1.setCellValue(rs.getDouble("bottale"));
					
					XSSFCell cellG1 = row1.createCell((int) 7);
					cellG1.setCellValue(rs.getString("license_category"));
		

				}
				Random rand = new Random();
				int n = rand.nextInt(550) + 1;
				fileOut = new FileOutputStream(relativePath+ "//ExciseUp//WholeSale//Excel//" + n+ "_Shop_Wise_Dispatches.xls");
				action.setExlname(n + "_Shop_Wise_Dispatches");

				XSSFRow row1 = worksheet.createRow((int) k + 1);

				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(" ");
				cellA1.setCellStyle(cellStyle);
				
				XSSFCell cellA2 = row1.createCell((int) 1);
				cellA2.setCellValue(" ");
				cellA2.setCellStyle(cellStyle);

				XSSFCell cellA3 = row1.createCell((int) 2);
				cellA3.setCellValue(" ");
				cellA3.setCellStyle(cellStyle);

				XSSFCell cellA4 = row1.createCell((int) 3);
				cellA4.setCellValue("Total");
				cellA4.setCellStyle(cellStyle);

				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue(Duty);
				cellA5.setCellStyle(cellStyle);

				XSSFCell cellA6 = row1.createCell((int) 5);
				cellA6.setCellValue(BL);
				cellA6.setCellStyle(cellStyle);

				XSSFCell cellA7 = row1.createCell((int) 6);
				cellA7.setCellValue(Bottles);
				cellA7.setCellStyle(cellStyle);

				XSSFCell cellA8 = row1.createCell((int) 7);
				cellA8.setCellValue("");
				cellA8.setCellStyle(cellStyle);

				
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
		
				flag = true;
				action.setExcelFlag(true);
				con.close();
				
				
			} else {

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"No Records Found !", "No Records Found !"));
			}
				
			} catch (Exception e) {			
				e.printStackTrace();
			} finally {
				try {
					if (con != null)
						con.close();
					if (pstmt != null)
						pstmt.close();
					if (rs != null)
						rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return flag;

		
		}
	
}
