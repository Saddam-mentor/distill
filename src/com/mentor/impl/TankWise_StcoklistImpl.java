package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.mentor.action.TankWise_StcoklistAction;
import com.mentor.datatable.TankWise_StcoklistDt;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class TankWise_StcoklistImpl {

	public String getDistillery(TankWise_StcoklistAction ac) {

		int id = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			String queryList = " SELECT int_app_id_f,vch_undertaking_name,vch_owner_address,vch_wrk_add FROM dis_mst_pd1_pd2_lic "
					+ " WHERE vch_wrk_phon="
					+ ResourceUtil.getUserNameAllReq().trim();

			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(queryList);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ac.setLoginUserNm(rs.getString("vch_undertaking_name"));
				ac.setLoginUserId(rs.getInt("int_app_id_f"));
				ac.setLoginUserAdrs(rs.getString("vch_wrk_add"));

			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return "";

	}

	// -------------------------------------

	public ArrayList getVatList(TankWise_StcoklistAction act) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selQuery = "";
		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue(0);
		list.add(item);

		try {
			if (act.getRadio().equalsIgnoreCase("SV")) {

				selQuery = "  SELECT b.int_id as storage_id,b.vch_tank_name as tank_nm, a.received_bl as recv_bl,a.recieved_al as recv_al  , "
						+ " a.consumed_bl as cosum_bl,a.consumed_al as cisum_al "
						+ " from distillery.distillery_spirit_store_detail b   "
						+ " left outer join  distillery.spirit_vat a on b.int_id=a.vat_id    "
						+ " and a.int_dist_id=b.int_distilleri_id    "
						+ " where  b.int_distilleri_id='"
						+ act.getLoginUserId() + "'   order by tank_nm  ";

				// System.out.println("---Blending SV---"+selQuery);

			} else if (act.getRadio().equalsIgnoreCase("DV")) {

				selQuery =

				" SELECT b.int_id as storage_id,b.vch_tank_name as tank_nm,a.received_bl as recv_bl ,a.recieved_al as recv_al, "
						+ " a.consumed_bl as cosum_bl,a.consumed_al as cisum_al                     "
						+ " from distillery.distillery_denatures_spirit_store_detail b                    "
						+ " left outer join  distillery.denatured_spirit_vat a on b.int_id=a.den_vat_id                "
						+ " and a.int_dist_id=b.int_distilleri_id                       "
						+ "  where   b.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "'   order by tank_nm               ";

				// System.out.println("---Blending DV---"+selQuery);

			}

			else if (act.getRadio().equalsIgnoreCase("BLENDFL")) {

				selQuery =

				" select  storage_id, storage_desc as tank_nm,recieve_bl  as recv_bl,recieve_al as recv_al, "
						+ " consumed_bl as cosum_bl,consumed_al  as cisum_al  "
						+ " from  distillery.spirit_for_bottling where int_distillery_id='"
						+ act.getLoginUserId() + "'  order by tank_nm  ";

				// System.out.println("---BLENDFL---"+selQuery);

			} else if (act.getRadio().equalsIgnoreCase("BLENDCL")) {
				selQuery =

				"Select storage_id,storage_desc as tank_nm,recieve_bl  as recv_bl,recieve_al as recv_al,consumed_bl as cosum_bl, "
						+ " consumed_al as cisum_al   "
						+ "from  distillery.spirit_for_bottling_cl where int_distillery_id='"
						+ act.getLoginUserId() + "'  order by tank_nm  ";

				// System.out.println("---BLEND=CL---"+selQuery);

			} else if (act.getRadio().equalsIgnoreCase("BOTFL")) {

				selQuery =

				"  select storage_id,  storage_desc as tank_nm,recieve_bl  as recv_bl,recieve_al,consumed_bl as cosum_bl, "
						+ " consumed_al as cisum_al             "
						+ " from  distillery.bottling_vat where int_distillery_id='"
						+ act.getLoginUserId() + "' order by tank_nm  ";

				// System.out.println("---Bottling FL---"+selQuery);

			} else if (act.getRadio().equalsIgnoreCase("BOTCL")) {
				selQuery =

				" Select  storage_id,storage_desc as tank_nm, recieve_bl as recv_bl,recieve_al as recv_al, "
						+ " consumed_bl as cosum_bl,consumed_al  as cisum_al   "
						+ " from  distillery.bottling_vat_cl where int_distillery_id='"
						+ act.getLoginUserId()
						+ "'  "
						+ " order by tank_nm     ";

				// System.out.println("---Bottling CL---"+selQuery);

			}

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(selQuery);

			System.out.println("=====query==BrandList====" + selQuery);
			rs = pstmt.executeQuery();
		//	System.out.println("=====query==BrandList====" + rs.next());
			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("storage_id"));
				item.setLabel(rs.getString("tank_nm"));
				// act.setBrandpack(rs.getString("brandpack"));

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

	// ---------------------------------
	// -------------------------------------------------------------------------------------------------------
	// ------------------Tank LIst

	/*
	 * public ArrayList tank_list(TankWise_StcoklistAction act) {
	 * 
	 * 
	 * ArrayList list = new ArrayList();
	 * 
	 * Connection con = null; PreparedStatement ps = null; ResultSet rs = null;
	 * String selQuery = "" ;
	 * 
	 * if(act.getRadio().equalsIgnoreCase("SV")){
	 * 
	 * selQuery =
	 * "  SELECT '26-06-2020' as date,b.vch_tank_name as tank_nm, a.received_bl as recv_bl,a.recieved_al as recv_al  , "
	 * + " a.consumed_bl as cosum_bl,a.consumed_al as cisum_al "+
	 * " from distillery.distillery_spirit_store_detail b   "+
	 * " left outer join  distillery.spirit_vat a on b.int_id=a.vat_id    "+
	 * " and a.int_dist_id=b.int_distilleri_id    "+
	 * " where  b.int_distilleri_id='"+act.getLoginUserId()+"'   ";
	 * //System.out.println("---Blending SV---"+selQuery);
	 * 
	 * } else if (act.getRadio().equalsIgnoreCase("DV")){ selQuery =
	 * " SELECT '26-06-2020' as date,b.vch_tank_name as tank_nm,a.received_bl as recv_bl ,a.recieved_al as recv_al, "
	 * +
	 * " a.consumed_bl as cosum_bl,a.consumed_al as cisum_al                     "
	 * +
	 * " from distillery.distillery_denatures_spirit_store_detail b                    "
	 * +
	 * " left outer join  distillery.denatured_spirit_vat a on b.int_id=a.den_vat_id                "
	 * + " and a.int_dist_id=b.int_distilleri_id                       "+
	 * "  where   b.int_distilleri_id='"
	 * +act.getLoginUserId()+"'                 ";
	 * //System.out.println("---Blending DV---"+selQuery);
	 * 
	 * }
	 * 
	 * else if(act.getRadio().equalsIgnoreCase("BLENDFL")){
	 * 
	 * selQuery =
	 * " select  '26-06-2020' as date, storage_desc as tank_nm,recieve_bl  as recv_bl,recieve_al as recv_al, "
	 * + " consumed_bl as cosum_bl,consumed_al  as cisum_al  "+
	 * " from  distillery.spirit_for_bottling where int_distillery_id='"
	 * +act.getLoginUserId()+"'  order by tank_nm  ";
	 * 
	 * 
	 * //System.out.println("---BLENDFL---"+selQuery);
	 * 
	 * } else if (act.getRadio().equalsIgnoreCase("BLENDCL")){ selQuery =
	 * "Select '26-06-2020' as date,storage_desc as tank_nm,recieve_bl  as recv_bl,recieve_al as recv_al,consumed_bl as cosum_bl, "
	 * + " consumed_al as cisum_al   "+
	 * "from  distillery.spirit_for_bottling_cl where int_distillery_id='"
	 * +act.getLoginUserId()+"'  order by tank_nm  ";
	 * 
	 * 
	 * " Select distinct storage_id, capacity,storage_desc, (recieve_bl-consumed_bl) as stock_bl,(recieve_al-consumed_al) as stock_al "
	 * +
	 * "  from  distillery.spirit_for_bottling_cl where int_distillery_id='"+act
	 * .getLoginUserId()+"' order by storage_desc ";
	 * 
	 * 
	 * //System.out.println("---BLEND=CL---"+selQuery);
	 * 
	 * } else if (act.getRadio().equalsIgnoreCase("BOTFL")){
	 * 
	 * selQuery =
	 * "  select '26-06-2020' as date,  storage_desc as tank_nm,recieve_bl  as recv_bl,recieve_al,consumed_bl as cosum_bl, "
	 * + " consumed_al as cisum_al             "+
	 * " from  distillery.bottling_vat where int_distillery_id='"
	 * +act.getLoginUserId()+"' order by tank_nm  ";
	 * 
	 * 
	 * 
	 * " select distinct storage_id, capacity,  storage_desc, (recieve_bl-consumed_bl) as stock_bl,(recieve_al-consumed_al) as stock_al "
	 * + "  from  distillery.bottling_vat where int_distillery_id='"+act.
	 * getLoginUserId()+"' order by storage_desc ";
	 * 
	 * 
	 * 
	 * //System.out.println("---Bottling FL---"+selQuery);
	 * 
	 * }else if (act.getRadio().equalsIgnoreCase("BOTCL")){ selQuery =
	 * " Select  '26-06-2020' as date,storage_desc as tank_nm, recieve_bl as recv_bl,recieve_al as recv_al, "
	 * + " consumed_bl as cosum_bl,consumed_al  as cisum_al   "+
	 * " from  distillery.bottling_vat_cl where int_distillery_id=30'"
	 * +act.getLoginUserId()+"'  "+ " order by tank_nm     ";
	 * 
	 * 
	 * 
	 * " Select distinct storage_id, capacity, storage_desc, (recieve_bl-consumed_bl) as stock_bl,(recieve_al-consumed_al) as stock_al "
	 * + "  from  distillery.bottling_vat_cl where int_distillery_id='"+act.
	 * getLoginUserId()+"' order by storage_desc ";
	 * 
	 * //System.out.println("---Bottling CL---"+selQuery);
	 * 
	 * }
	 * 
	 * 
	 * try { con = ConnectionToDataBase.getConnection(); ps =
	 * con.prepareStatement(selQuery);
	 * 
	 * rs = ps.executeQuery(); int i = 1;
	 * 
	 * while (rs.next()) {
	 * 
	 * TankWise_StcoklistDt dt=new TankWise_StcoklistDt();
	 * //System.out.println("---rs.next()---"); dt.setSrNO_bb(i);
	 * dt.setVat_name_bb(rs.getString("storage_desc"));
	 * dt.setStock_bl_bb(rs.getDouble("stock_bl"));
	 * dt.setStock_al_bb(rs.getDouble("stock_al"));
	 * dt.setStorage_id_bb(rs.getInt("storage_id"));
	 * dt.setCapacity_bb(rs.getDouble("capacity"));
	 * 
	 * 
	 * list.add(dt); i++; }
	 * 
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } finally { try { if (con !=
	 * null) con.close(); if (ps != null) ps.close(); if (rs != null)
	 * rs.close();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * return list; }
	 */
	//--------PRINT REPORT------------
	
	
	public void printReport(TankWise_StcoklistAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "Distillery" + File.separator + "jasper";
		// / \doc\ExciseUp\Distillery\jasper
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "Distillery" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String selQuery = null;
		String type = null;

		try {
			con = ConnectionToDataBase.getConnection();
		/*	Date m1 =Utility.convertUtilDateToSQLDate(act.getFormdate());
			Calendar cal =Calendar.getInstance();
			cal.setTime(act.getFormdate());
			cal.add(Calendar.DATE,-1);
			act.getFormdate() =cal.getTime();
			System.out.println(act.getFormdate());*/
			if (act.getRadio().equalsIgnoreCase("SV")) { 
     				type="Spirit Vat";
     				selQuery =
     						
     		"			 select distinct  zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,                                                                                  "+
     		"			 zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                                                                                                                                        "+
     		"			 case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl ,case when zz.flg=true then                                                                                "+
     		"			 zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al from                                                                                                                                    "+
     		"			 (select distinct x.dt_time,  x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0)                                                                     "+
     		"			 as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                  "+
     		"			 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as                                                                                           "+
     		"			 vat_wastage_bl, coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,                                                                                                                                  "+
     		"			 (coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                            "+
     		"			 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                        "+
     		"			 ( select b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                            "+
     		"			 b.qty_transfr_bl as cosum_bl,b.qty_transfr_al as cosum_al,                                                                                                                                         "+
     		"			 0 as recv_bl,0 as recv_al,                                                                                                                                                                         "+
     		"			 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                            "+
     		"			 from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_cl_blending b                                                                                                        "+
     		"			 where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                             "+
     		"			 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.from_vat_no='"+act.getVatNo()+"'   and                                                                                               "+
     		"			 b.tansfer_dt between    '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                 "+
     		"			 union                                                                                                                                                                                              "+
     		"			 select b.dt_time , false as flg,b.dt_created as date, 'ENA Gatepass Receiving With In State' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                           "+
     		"			 a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                      "+
     		"			 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                          "+
     		"			 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                            "+
     		"			 from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                               "+
     		"			 where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                              "+
     		"			 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                                "+
     		"			 b.dt_created between   '2020-07-15'  and '2020-07-25'                                                                                                                                              "+
     		"			 union                                                                                                                                                                                              "+
     		"			 select c.dt_time , false as flg,c.dt_created as date, 'ENASpritSale' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                "+
     		"			 a.int_distilleri_id,a.vch_tank_name ,(b.transfer_bl+b.wastage_bl) as cosum_bl,(b.transfer_al+b.wastage_al) as cosum_al,                                                                              "+
     		"			 0 as recv_bl,0 as recv_al,                                                                                                                                                                         "+
     		"			 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                            "+
     		"			 from distillery.distillery_spirit_store_detail a,distillery.export_spirit_in_state_detail b ,distillery.export_spirit_in_state c                                                                   "+
     		"			 where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id   and   b.int_id_fk=c.int_id                                                                                                     "+
     		"			 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vat_no='"+act.getVatNo()+"'  and                                                                                                    "+
     		"			 c.dt_created between  '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                  "+
     		"			 union                                                                                                                                                                                              "+
     		"			 select b.dt_time , false as flg,b.dt_created as date, 'Import of ENA' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                      "+
     		"			 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                       "+
     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.spirit_import b                                                                                                                                "+
     		"		 where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                           "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vatno='"+act.getVatNo()+"'  and                                                                                                             "+
     		"		 b.dt_created between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                          "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.created_date as date, 'ReceivedFromPlant' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                                "+
     		"		 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                               "+
     		"		 b.quantity_bl as recv_bl, b.quantity_al as recv_al,                                                                                                                                                        "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.received_from_plant_master b                                                                                                                   "+
     		"		 where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                                      "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'                                                                                                                  "+
     		"		 and  b.created_date between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                   "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.created_date as date, 'ReDistillationOfSpirit' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name                                             "+
     		"		 ,b.trnsfer_bl as cosum_bl,b.trnsfer_al as cosum_al,                                                                                                                                                        "+
     		"		 0 as recv_bl,0 as recv_al,                                                                                                                                                                                 "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.re_distillation_of_spirit_master b                                                                                                             "+
     		"		 where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                                            "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'                                                                                                                  "+
     		"		 and  b.created_date between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                   "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.date as date, 'REMOVAL OF SPIRIT FOR DENATURING' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                              "+
     		"		 (b.spirit_vat_quantitybl-b.from_qty_as_pr_dpt_bl) as cosum_bl,                                                                                                                                             "+
     		"		 (b.spirit_vat_quantityal-b.from_qty_as_pr_dpt_al)  as cosum_al,                                                                                                                                            "+
     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.removalofspiritfrdenaturing b                                                                                                                  "+
     		"		 where  a.int_id=b.spirit_vatno and  a.int_distilleri_id=b.int_distillery_code                                                                                                                              "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                     		                                                                            "+
     		"		 and      b.spirit_vatno='"+act.getVatNo()+"' and                                                                                                                                                           "+
     		"		 b.date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                               "+
     		"		  union                                                                                                                                                                                                     "+
     		"		 select b.dt_time , false as flg,b.dt_created as date, 'SpiritExport' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                                                              "+
     		"		 a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                        		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                    "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.export_spirit b                                                                                                                                "+
     		"		 where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                      "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                                        "+
     		"		 b.dt_created between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                          "+
     		"		                                                                                                                                                                                                            "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.dt_created as date, 'Spirit Purchased In ( State )' as dcription ,a.openingal,                                                                                                      "+
     		"		 a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                     "+
     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                                       "+
     		"		 where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                      "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                                        "+
     		"		 b.dt_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                         "+
     		"		                                        	                                                                                                                                                                "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,c.dt_created as date, 'Spirit Sale Other Than ENA  ' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                     "+
     		"		 a.int_distilleri_id,a.vch_tank_name ,(b.transfer_bl+b.wastage_bl) as cosum_bl,(b.transfer_al+b.wastage_al) as cosum_al,                                                                                      "+
     		"		 0 as recv_bl,0 as recv_al,                                                                                                                                                                                 "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.export_spirit_in_state_detail b ,distillery.export_spirit_in_state c                                                                           "+
     		"		 where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id   and   b.int_id_fk=c.int_id                                                                                                             "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vat_no='"+act.getVatNo()+"'  and                                                                                                            "+
     		"		 c.dt_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                         "+
     		"		                                                                                                                                                                                                            "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.date_created as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                             "+
     		"		 a.int_distilleri_id,a.vch_tank_name ,b.dob_qunty_transfer_bl as cosum_bl,b.dob_qunty_transfer_al as cosum_al,                                                                                              "+
     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b                                                                                                     "+
     		"		 where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                             "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"' and                                                                		   b.int_from_vat_id='"+act.getVatNo()+"'                                       "+
     		"		 and                                                                                                                                  		                                                                "+
     		"		 b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                       "+
     		"		 union 		                                                                                                                                                                                                "+
     		"		 select b.dt_time , false as flg,v.txn_date as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                 "+
     		"		 a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                          "+
     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
     		"		 v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                      "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b,                                                                                                    "+
     		"		 distillery.vat_wastage v  where  a.int_id=b.int_to_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                     "+
     		"		 and b.int_to_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"' and                                                                                "+
     		"		 b.int_to_vat_id='"+act.getVatNo()+"'                                                                                                                                                                       "+
     		"		 and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F'  and                                                                                                                                              "+
     		"		 v.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                 		                                                    "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                          "+
     		"		 qty_transfr_bl as cosum_bl,qty_transfr_al as cosum_al,                                                                                                                                                     "+
     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b                                                                                                                "+
     		"		 where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                                     "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.from_vat_no='"+act.getVatNo()+"'  and                                                                                                       "+
     		"		 b.tansfer_dt between  '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                          "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                          "+
     		"		 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                               "+
     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
     		"		 v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                      "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b ,distillery.vat_wastage v                                                                                      "+
     		"		 where  a.int_id=b.to_vat_no and  a.int_distilleri_id=b.distillery_id    and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int                                                                       "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.to_vat_no='"+act.getVatNo()+"' and   v.type='SPR-BLN_TRANSFER_WASTAGE' and v.vat_des='F'  and                                               "+
     		"		 b.tansfer_dt between '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'     ) X                                                                                                   "+
     		"		union                                                                                                                                                                                                       "+
     		"		 select distinct x.dt_time,  x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0)                                                                              "+
     		"		 as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                          "+
     		"		 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as                                                                                                   "+
     		"		 vat_wastage_bl, coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,                                                                                                                                          "+
     		"		 (coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                    "+
     		"		 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                "+
     		"		 ( select b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                    "+
     		"		 b.qty_transfr_bl as cosum_bl,b.qty_transfr_al as cosum_al,                                                                                                                                                 "+
     		"		 0 as recv_bl,0 as recv_al,                                                                                                                                                                                 "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_cl_blending b                                                                                                                "+
     		"		 where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                                     "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.from_vat_no='"+act.getVatNo()+"'   and                                                                                                       "+
     		"		 b.tansfer_dt between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                               "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.dt_created as date, 'ENA Gatepass Receiving With In State' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                                   "+
     		"		 a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                              "+
     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                                       "+
     		"		 where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                      "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                                        "+
     		"		 b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                               "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select c.dt_time , false as flg,c.dt_created as date, 'ENASpritSale' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                        "+
     		"		 a.int_distilleri_id,a.vch_tank_name ,(b.transfer_bl+b.wastage_bl) as cosum_bl,(b.transfer_al+b.wastage_al) as cosum_al,                                                                                      "+
     		"		 0 as recv_bl,0 as recv_al,                                                                                                                                                                                 "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.export_spirit_in_state_detail b ,distillery.export_spirit_in_state c                                                                           "+
     		"		 where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id   and   b.int_id_fk=c.int_id                                                                                                             "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vat_no='"+act.getVatNo()+"'  and                                                                                                            "+
     		"		 c.dt_created between  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.dt_created as date, 'Import of ENA' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                              "+
     		"		 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                               "+
     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.spirit_import b                                                                                                                                "+
     		"		 where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                           "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vatno='"+act.getVatNo()+"'  and                                                                                                             "+
     		"		 b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.created_date as date, 'ReceivedFromPlant' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                                "+
     		"		 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                               "+
     		"		 b.quantity_bl as recv_bl, b.quantity_al as recv_al,                                                                                                                                                        "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.received_from_plant_master b                                                                                                                   "+
     		"		 where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                                      "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'                                                                                                                  "+
     		"		 and  b.created_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                         "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.created_date as date, 'ReDistillationOfSpirit' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name                                             "+
     		"		 ,b.trnsfer_bl as cosum_bl,b.trnsfer_al as cosum_al,                                                                                                                                                        "+
     		"		 0 as recv_bl,0 as recv_al,                                                                                                                                                                                 "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.re_distillation_of_spirit_master b                                                                                                             "+
     		"		 where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                                            "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'                                                                                                                  "+
     		"		 and  b.created_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                         "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.date as date, 'REMOVAL OF SPIRIT FOR DENATURING' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                              "+
     		"		 (b.spirit_vat_quantitybl-b.from_qty_as_pr_dpt_bl) as cosum_bl,                                                                                                                                             "+
     		"		 (b.spirit_vat_quantityal-b.from_qty_as_pr_dpt_al)  as cosum_al,                                                                                                                                            "+
     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.removalofspiritfrdenaturing b                                                                                                                  "+
     		"		 where  a.int_id=b.spirit_vatno and  a.int_distilleri_id=b.int_distillery_code                                                                                                                              "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                     		                                                                            "+
     		"		 and      b.spirit_vatno='"+act.getVatNo()+"' and                                                                                                                                                           "+
     		"		 b.date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                     "+
     		"		  union                                                                                                                                                                                                     "+
     		"		 select b.dt_time , false as flg,b.dt_created as date, 'SpiritExport' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                                                              "+
     		"		 a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                        		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                    "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.export_spirit b                                                                                                                                "+
     		"		 where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                      "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                                        "+
     		"		 b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                "+
     		"		                                                                                                                                                                                                            "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.dt_created as date, 'Spirit Purchased In ( State )' as dcription ,a.openingal,                                                                                                      "+
     		"		 a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                     "+
     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                                       "+
     		"		 where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                      "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                                        "+
     		"		 b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                               "+
     		"		                                        	                                                                                                                                                                "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,c.dt_created as date, 'Spirit Sale Other Than ENA  ' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                     "+
     		"		 a.int_distilleri_id,a.vch_tank_name ,(b.transfer_bl+b.wastage_bl) as cosum_bl,(transfer_al+b.wastage_al) as cosum_al,                                                                                      "+
     		"		 0 as recv_bl,0 as recv_al,                                                                                                                                                                                 "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.export_spirit_in_state_detail b ,distillery.export_spirit_in_state c                                                                           "+
     		"		 where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id   and   b.int_id_fk=c.int_id                                                                                                             "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vat_no='"+act.getVatNo()+"'  and                                                                                                            "+
     		"		 c.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                               "+
     		"		                                                                                                                                                                                                            "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.date_created as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                             "+
     		"		 a.int_distilleri_id,a.vch_tank_name ,b.dob_qunty_transfer_bl as cosum_bl,b.dob_qunty_transfer_al as cosum_al,                                                                                              "+
     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b                                                                                                     "+
     		"		 where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                             "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"' and                                                                		   b.int_from_vat_id='"+act.getVatNo()+"'                                       "+
     		"		 and                                                                                                                                  		                                                                "+
     		"		 b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                             "+
     		"		 union 		                                                                                                                                                                                                "+
     		"		 select b.dt_time , false as flg,v.txn_date as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                 "+
     		"		 a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                          "+
     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
     		"		 v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                      "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b,                                                                                                    "+
     		"		 distillery.vat_wastage v  where  a.int_id=b.int_to_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                     "+
     		"		 and b.int_to_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"' and                                                                                "+
     		"		 b.int_to_vat_id='"+act.getVatNo()+"'                                                                                                                                                                       "+
     		"		 and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F'  and                                                                                                                                              "+
     		"		 v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                   		        "+                      
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                          "+
     		"		 qty_transfr_bl as cosum_bl,qty_transfr_al as cosum_al,                                                                                                                                                     "+
     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b                                                                                                                "+
     		"		 where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                                     "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.from_vat_no='"+act.getVatNo()+"'  and                                                                                                       "+
     		"		 b.tansfer_dt between  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                "+
     		"		 union                                                                                                                                                                                                      "+
     		"		 select b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                          "+
     		"		 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                               "+
     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
     		"		 v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                      "+
     		"		 from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b ,distillery.vat_wastage v                                                                                      "+
     		"		 where  a.int_id=b.to_vat_no and  a.int_distilleri_id=b.distillery_id    and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int                                                                       "+
     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.to_vat_no='"+act.getVatNo()+"' and   v.type='SPR-BLN_TRANSFER_WASTAGE' and v.vat_des='F'  and                                               "+
     		"		 b.tansfer_dt between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'       ) X )zz     order by zz.date, zz.dt_time,  zz.dcription 				";
     						
     						
     						
     						
     						
     						
     						
     						
     						
     						
     						
     						
     						
    /////////-----------------------------------------------------------------------------------------------                                                                                                                                                                                                        
    /* "		  select  distinct zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                               "+
     "		   case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl ,case when zz.flg=true then    zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al from                                                                                                                                                                    "+
     "		   (select x.flg, x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                            "+
     "		   coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl, coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,                  "+
     "		   (coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                      "+
     "		   (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                  "+
     "		   (select distinct   b.dt_time , false as flg,b.date_created as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT- FromVat' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                       "+
     "		   a.int_distilleri_id,a.vch_tank_name ,b.dob_qunty_transfer_bl as cosum_bl,b.dob_qunty_transfer_al as cosum_al,                                                                                "+
     "		   0 as recv_bl, 0 as recv_al,                                                                                                                                                    "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                        "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b                                                                                      "+
     "		    where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                     "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.int_from_vat_id='"+act.getVatNo()+"'                     "+
     "		   and   b.date_created between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                "+
     "         union  "+
     "		   select distinct   b.dt_time , false as flg, v.txn_date as date, 'TransferOfRemovalSpritVat-TO Vat' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                       "+
     "		   a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                "+
     "		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                    "+
     "		   v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                        "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b,                                                                                      "+
     "		   distillery.vat_wastage v  where  a.int_id=b.int_to_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                     "+
     "		   and b.int_to_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.int_to_vat_id='"+act.getVatNo()+"'                     "+
     "		   and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F'  and v.txn_date between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                "+
 
      "		   union                                                                                                                                                                                                 "+
     "		   select distinct   b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of Spirit to CL Blending Vat ' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                   "+
     "		   b.qty_transfr_bl as cosum_bl,b.qty_transfr_al as cosum_al,                                                                                                                                      "+
     "		   0 as recv_bl,0 as recv_al,                                                                                                                                                                   "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_cl_blending b                                                                                                        "+
     "		   where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                       "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.from_vat_no='"+act.getVatNo()+"'   and                                                                                          "+
     "		   b.tansfer_dt between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                          "+
     "		   union                                                                                                                                                                                        "+
 // 
 //   "		   select distinct   b.dt_time , false as flg,v.txn_date as date, 'Transfer of Spirit to CL Blending Vat-ToVAT' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                   "+
 //   "		   0 as cosum_bl,0 as cosum_al,                                                                                                                                      "+
 //   "		   0 as recv_bl,0 as recv_al,                                                                                                                                                                   "+
 //  "		   v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                                      "+
 //  "		   from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_cl_blending b, distillery.vat_wastage v                                                                                                         "+
 //  "		   where  a.int_id=b.to_vat_no and  a.int_distilleri_id=b.distillery_id   and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int                                                                                                                      "+
 //   "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.to_vat_no='"+act.getVatNo()+"'   and                                                                                          "+
 //  "		   v.txn_date between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                          "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.created_date as date, 'ReceivedFromPlantAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                       "+
     "		   0 as cosum_bl,0 as cosum_al,                                                                                                                                                                 "+
     "		   b.quantity_bl as recv_bl, b.quantity_al as recv_al,                                                                                                                                          "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.received_from_plant_master b                                                                                                     "+
     "		   where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                        "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'    and                                                                                             "+
     "		   b.created_date between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                          "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.created_date as date, 'ReDistillationOfSpiritAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name                                    "+
     "		   ,b.trnsfer_bl as cosum_bl,b.trnsfer_al as cosum_al,                                                                                                                                          "+
     "		   0 as recv_bl,0 as recv_al,                                                                                                                                                                   "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.re_distillation_of_spirit_master b                                                                                               "+
     "		   where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                              "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'   and                                                                                              "+
     "		   b.created_date between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                          "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.date as date, 'REMOVAL OF SPIRIT FOR DENATURING-FromVat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                     "+
     "		   (b.spirit_vat_quantitybl - b.from_qty_as_pr_dpt_bl) as cosum_bl,                                                                                                                               "+
     "		   (b.spirit_vat_quantityal-b.from_qty_as_pr_dpt_al)  as cosum_al,                                                                                                                                 "+
     "		   0 as recv_bl, 0 as recv_al,                                                                                                                                                                  "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.removalofspiritfrdenaturing b                                                                                                   "+
     "		    where  a.int_id=b.spirit_vatno and  a.int_distilleri_id=b.int_distillery_code                                                                                      "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                     "+
     "		   and       b.spirit_vatno='"+act.getVatNo()+"'  and                                                                              "+
     "		   b.date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                 "+
     "		   union                                                                                                                                                                                           "+
     "		   select distinct   b.dt_time , false as flg,b.date as date, 'REMOVAL OF SPIRIT FOR DENATURING-ToVat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                     "+
     "		   0 as cosum_bl,                                                                                                                               "+
     "		   0  as cosum_al,                                                                                                                                "+
     "		   0 as recv_bl, 0 as recv_al,                                                                                                                                                                  "+
     "		   v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                                   "+
     "		   distillery.vat_wastage v  where  a.int_id=b.den_vat_no and  a.int_distilleri_id=b.int_distillery_code                                                                                      "+
     "		   and b.den_vat_no= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                     "+
     "		   and     b.den_vat_no='"+act.getVatNo()+"'  and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F' and                                                                                    "+
     "		   b.date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                 "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.dt_created as date, 'SpiritExport' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                        "+
     "		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                    "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.export_spirit b                                                                                                                  "+
     "		   where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                        "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                          "+
     "		   b.dt_created between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                            "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.dt_created as date, 'BondAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                          "+
     "		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                    "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.spirit_import b                                                                                                                  "+
     "		   where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                             "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vatno='"+act.getVatNo()+"'  and                                                                                               "+
     "		   b.dt_created between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                             "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                 "+
     "		   qty_transfr_bl as cosum_bl,qty_transfr_al as cosum_al,                                                                                                                                       "+
     "		   0 as recv_bl, 0 as recv_al,                                                                                                                                                                  "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b                                                                                                  "+
     "		   where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                       "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.from_vat_no='"+act.getVatNo()+"'  and                                                                                         "+
     "		   b.tansfer_dt between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                             "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                 "+
     "		   0 as cosum_bl,0 as cosum_al,                                                                                                                                       "+
     "		   0 as recv_bl, 0 as recv_al,                                                                                                                                                                  "+
     "		   v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b ,distillery.vat_wastage v                                                                                                 "+
     "		   where  a.int_id=b.to_vat_no and  a.int_distilleri_id=b.distillery_id    and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int                                                                                                                    "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.to_vat_no='"+act.getVatNo()+"' and   v.type='SPR-BLN_TRANSFER_WASTAGE' and v.vat_des='F'  and                                                                                         "+
     "		   b.tansfer_dt between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                             "+
   
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.dt_created as date, 'ENA Gatepass Receiving With In State' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                          "+
     "		   a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                "+
     "		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                    "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                         "+
     "		   where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                        "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                          "+
     "		   b.dt_created between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                             "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.dt_created as date, 'Import of ENA' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name                                       "+
     "		   ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                "+
     "		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                    "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.spirit_import b                                                                                                                  "+
     "		   where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                             "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vatno='"+act.getVatNo()+"'  and                                                                                               "+
     "		   b.dt_created between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                             "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.dt_created as date, 'Spirit Purchased In ( State )' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                        "+
     "		   a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                            "+
     "		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                    "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                         "+
     "		   where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                        "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                          "+
     "		   b.dt_created between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                             "+
     "		   union                                                                                                                                                                                        "+
     
     "        select distinct   b.dt_time , false as flg,c.dt_created as date, 'ENA Spirit Sale ' as dcription ,a.openingal,a.openingbl,a.int_id,                              "+                                     
     "        a.int_distilleri_id,a.vch_tank_name ,(b.transfer_bl+b.wastage_bl) as cosum_bl,(transfer_al+b.wastage_al) as cosum_al,                       "+                                                                                                 
     "        0 as recv_bl,0 as recv_al,                                                                                                                  "+                              
     "        0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                    "+                                             
     "        from distillery.distillery_spirit_store_detail a,distillery.export_spirit_in_state_detail b ,distillery.export_spirit_in_state c            "+                                                                                      
     "        where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id   and   b.int_id_fk=c.int_id                                          "+                                                                    
     "        and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vat_no='"+act.getVatNo()+"'  and                                         "+                                             
     "       c.dt_created between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                            "+ 
     "		   union                                                                                                                                                                                        "+
      
     "       select distinct   b.dt_time , false as flg,c.dt_created as date, 'Spirit Sale Other Than ENA  ' as dcription ,a.openingal,a.openingbl,a.int_id,                              "+                                     
     "       a.int_distilleri_id,a.vch_tank_name ,(b.transfer_bl+b.wastage_bl) as cosum_bl,(transfer_al+b.wastage_al) as cosum_al,                       "+                                                                                                 
     "       0 as recv_bl,0 as recv_al,                                                                                                                  "+                              
     "       0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                    "+                                             
     "       from distillery.distillery_spirit_store_detail a,distillery.export_spirit_in_state_detail b ,distillery.export_spirit_in_state c            "+                                                                                      
     "       where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id   and   b.int_id_fk=c.int_id                                        "+                                                                    
     "       and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vat_no='"+act.getVatNo()+"'  and                                         "+                                             
     "       c.dt_created between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                            "+  

     "		   ) X                                                                                                                                                                                          "+
     "		   union                                                                                                                                                                                        "+
     "		   select   x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0)                                                                              "+
     "		   as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                            "+
     "		   coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as                                                                                     "+
     "		   vat_wastage_bl, coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,                                                                                                                            "+
     "		   (coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                      "+
     "		   (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                  "+
     "		   (select distinct   b.dt_time , false as flg,b.date_created as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                       "+
     "		   a.int_distilleri_id,a.vch_tank_name ,b.dob_qunty_transfer_bl as cosum_bl,b.dob_qunty_transfer_al as cosum_al,                                                                                "+
     "		   0 as recv_bl, 0 as recv_al,                                                                                                                                                    "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                        "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b                                                                                      "+
     "		   where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                     "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"' and                                                                "+
     "		   b.int_from_vat_id='"+act.getVatNo()+"'                                                                                                                                                       "+
     "		   and                                                                                                                                  "+
     "		   b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                  "+
     "         union "+
     "		   select distinct   b.dt_time , false as flg,v.txn_date as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                       "+
     "		   a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                "+
     "		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                    "+
     "		   v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                        "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b,                                                                                      "+
     "		   distillery.vat_wastage v  where  a.int_id=b.int_to_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                     "+
     "		   and b.int_to_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"' and                                                                "+
     "		   b.int_to_vat_id='"+act.getVatNo()+"'                                                                                                                                                       "+
     "		   and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F'  and                                                                                                                                "+
     "		   v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                  "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                   "+
     "		   b.qty_transfr_bl as cosum_bl,b.qty_transfr_al as cosum_al,                                                                                                                                      "+
     "		   0 as recv_bl,0 as recv_al,                                                                                                                                                                   "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_cl_blending b                                                                                                         "+
     "		   where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                     "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.from_vat_no='"+act.getVatNo()+"'   and                                                                                          "+
     " 		   b.tansfer_dt between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                          "+
 
 
 //  "		   union                                                                                                                                                                                        "+
 //  "		   select distinct   b.dt_time , false as flg,v.txn_date as date, 'Transfer of Spirit to CL Blending Vat-ToVat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                   "+
 //  "		   0 as cosum_bl,0 as cosum_al,                                                                                                                                      "+
 //  "		   0 as recv_bl,0 as recv_al,                                                                                                                                                                   "+
 // "		   v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                                      "+
 // "		   from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_cl_blending b, distillery.vat_wastage v                                                                                                         "+
 // "		   where  a.int_id=b.to_vat_no and  a.int_distilleri_id=b.distillery_id   and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int                                                                                                                      "+
 //  "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.to_vat_no='"+act.getVatNo()+"'   and                                                                                          "+
 // "		   v.txn_date between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                          "+
   
     // "		   select distinct b.date_created as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                   "+
    // "		   b.sprit_taken as cosum_bl,b.sprit_taken_al as cosum_al,                                                                                                                                      "+
    // "		   0 as recv_bl,0 as recv_al,                                                                                                                                                                   "+
    // "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
    // "		   from distillery.distillery_spirit_store_detail a,distillery.country_liquor_blending b                                                                                                        "+
   //  "		   where  a.int_id=b.int_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                        "+
   //  "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.int_vat_no='"+act.getVatNo()+"'                                                                                                "+
    // "		   and    b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                        "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.created_date as date, 'ReceivedFromPlantAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                       "+
     "		   0 as cosum_bl,0 as cosum_al,                                                                                                                                                                 "+
     "		   b.quantity_bl as recv_bl, b.quantity_al as recv_al,                                                                                                                                          "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.received_from_plant_master b                                                                                                     "+
     "		   where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                        "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'                                                                                                    "+
     "		   and  b.created_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                          "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.created_date as date, 'ReDistillationOfSpiritAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name                                    "+
     "		   ,b.trnsfer_bl as cosum_bl,b.trnsfer_al as cosum_al,                                                                                                                                          "+
     "		   0 as recv_bl,0 as recv_al,                                                                                                                                                                   "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.re_distillation_of_spirit_master b                                                                                               "+
     "		   where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                              "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'                                                                                                    "+
     "		   and  b.created_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                          "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.date as date, 'REMOVAL OF SPIRIT FOR DENATURING' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                     "+
     "		   (b.spirit_vat_quantitybl - b.from_qty_as_pr_dpt_bl) as cosum_bl,                                                                                                                               "+
     "		   (b.spirit_vat_quantityal-b.from_qty_as_pr_dpt_al)  as cosum_al,                                                                                                                                "+
     "		   0 as recv_bl, 0 as recv_al,                                                                                                                                                                  "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.removalofspiritfrdenaturing b                                                                                                   "+
     "		   where  a.int_id=b.spirit_vatno and  a.int_distilleri_id=b.int_distillery_code                                                                                      "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                     "+
     "		   and      b.spirit_vatno='"+act.getVatNo()+"' and                                                                                   "+
     "		   b.date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                      "+
     "		   union                                                                                                                                                                                        "+
   
     "		   select distinct   b.dt_time , false as flg,b.date as date, 'REMOVAL OF SPIRIT FOR DENATURING' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                     "+
     "		   0 as cosum_bl,                                                                                                                               "+
     "		   0  as cosum_al,                                                                                                                                "+
     "		   0 as recv_bl, 0 as recv_al,                                                                                                                                                                  "+
     "		   v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                                   "+
     "		   distillery.vat_wastage v  where  a.int_id=b.den_vat_no and  a.int_distilleri_id=b.int_distillery_code                                                                                      "+
     "		   and b.den_vat_no= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                     "+
     "		   and     b.den_vat_no='"+act.getVatNo()+"'  and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F' and                                                                                    "+
     "		   b.date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                      "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.dt_created as date, 'SpiritExport' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                        "+
     "		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                    "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.export_spirit b                                                                                                                  "+
     "		   where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                        "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                          "+
     "		   b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                 "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.dt_created as date, 'BondAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                          "+
     "		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                    "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.spirit_import b                                                                                                                  "+
     "		   where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                             "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vatno='"+act.getVatNo()+"'  and                                                                                               "+
     "		   b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                 "+
     "		   qty_transfr_bl as cosum_bl,qty_transfr_al as cosum_al,                                                                                                                                       "+
     "		   0 as recv_bl, 0 as recv_al,                                                                                                                                                                  "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b                                                                                                  "+
     "		   where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                       "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.from_vat_no='"+act.getVatNo()+"'  and                                                                                         "+
     "		   b.tansfer_dt between  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                 "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                 "+
     "		   0 as cosum_bl,0 as cosum_al,                                                                                                                                       "+
     "		   0 as recv_bl, 0 as recv_al,                                                                                                                                                                  "+
     "		   v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b ,distillery.vat_wastage v                                                                                                 "+
     "		   where  a.int_id=b.to_vat_no and  a.int_distilleri_id=b.distillery_id    and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int                                                                                                                    "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.to_vat_no='"+act.getVatNo()+"' and   v.type='SPR-BLN_TRANSFER_WASTAGE' and v.vat_des='F'  and                                                                                         "+
     "		   b.tansfer_dt between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                           "+

     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.dt_created as date, 'ENA Gatepass Receiving With In State' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                          "+
     "		   a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                "+
     "		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                    "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                         "+
     "		   where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                        "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                          "+
     "		   b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.dt_created as date, 'Import of ENA' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                     "+
     "		   0 as cosum_bl,0 as cosum_al,                                                                                                                                                                 "+
     "		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                    "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.spirit_import b                                                                                                                  "+
     "		   where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                             "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vatno='"+act.getVatNo()+"'  and                                                                                               "+
     "		   b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                 "+
     "		   union                                                                                                                                                                                        "+
     "		   select distinct   b.dt_time , false as flg,b.dt_created as date, 'Spirit Purchased In ( State )' as dcription ,a.openingal,                                                                                             "+
     "		   a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                       "+
     "		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                    "+
     "		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                      "+
     "		   from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                         "+
     "		   where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                        "+
     "		   and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                          "+
     "		   b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                "+
     "		   union                                                                                                                                                                                        "+
     "        select distinct   b.dt_time , false as flg,c.dt_created as date, 'ENA Spirit Sale ' as dcription ,a.openingal,a.openingbl,a.int_id,                              "+                                     
     "        a.int_distilleri_id,a.vch_tank_name ,(b.transfer_bl+b.wastage_bl) as cosum_bl,(transfer_al+b.wastage_al) as cosum_al,                       "+                                                                                                 
     "        0 as recv_bl,0 as recv_al,                                                                                                                  "+                              
     "        0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                    "+                                             
     "        from distillery.distillery_spirit_store_detail a,distillery.export_spirit_in_state_detail b ,distillery.export_spirit_in_state c            "+                                                                                      
     "        where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id   and   b.int_id_fk=c.int_id                                          "+                                                                    
     "        and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vat_no='"+act.getVatNo()+"'  and                                         "+                                             
     "       c.dt_created between  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                           "+ 
     "		   union                                                                                                                                                                                        "+ 
     "       select distinct   b.dt_time , false as flg,c.dt_created as date, 'Spirit Sale Other Than ENA  ' as dcription ,a.openingal,a.openingbl,a.int_id,                              "+                                     
     "       a.int_distilleri_id,a.vch_tank_name ,(b.transfer_bl+b.wastage_bl) as cosum_bl,(transfer_al+b.wastage_al) as cosum_al,                       "+                                                                                                 
     "       0 as recv_bl,0 as recv_al,                                                                                                                  "+                              
     "       0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                    "+                                             
     "       from distillery.distillery_spirit_store_detail a,distillery.export_spirit_in_state_detail b ,distillery.export_spirit_in_state c            "+                                                                                      
     "       where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id   and   b.int_id_fk=c.int_id                                          "+                                                                    
     "       and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vat_no='"+act.getVatNo()+"'  and                                         "+                                             
     "       c.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                             "+  
     "		   ) X  )zz     order by zz.date                                                                                                                                                    ";                    	
	                                                */                                                                                                                                                                                                                         
					                                                                                                                                                                                                                                                                                                                   
   //--------------------------------------------						                                                                                                                                                                                                                                                                   
						                                                                                                                                                                                                                                                                                                              
			System.out.println("---Blending SV---" + selQuery);

			} else if (act.getRadio().equalsIgnoreCase("DV")) {
			    
				type="Denatured Spirit  Vat";
  
				selQuery =
					
		"		select  distinct zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                             "+
		"		case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl ,                                                                                                          "+
		"		case when zz.flg=true then    zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al from                                                                                                      "+
		"		(select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,                                                         "+
		"		coalesce(x.cosum_al,0.0) as cosum_al, 	coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                  "+
		"		coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                           "+
		"		(coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                        "+
		"		(select   b.dt_time , false as flg,b.dt_crdt as date ,'REMOVAL OF DENATURED SPIRIT ' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,                                             "+
		"		a.vch_tank_name,int_issued_quantity_bl  as cosum_bl,                                                                                                                                               "+
		"		int_issued_quantity_al  as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                     "+
		"		0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                           "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.denatured_spirits_to_issuevat b                                                                                              "+
		"		where  a.int_id=b.int_denatured_vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                      "+
		"		and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.int_denatured_vat_id='"+act.getVatNo()+"'                                                                                             "+
		"		and b.dt_crdt between   '2020-07-15'    and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                              "+
		"		union                                                                                                                                                                                              "+
		"		select   b.dt_time , false as flg,c.recv_dt as date,'ENA Gatepass Receiving With In State ' as dcription,a.openingal,a.openingbl, a.int_id,a.int_distilleri_id,                                      "+
		"		a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                    "+
		"		0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                         "+
		"		0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                          "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b , distillery.export_spirit_in_state c                                                               "+
		"		where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.distillery_id and   b.distillery_id::text=c.consigneeid and c.permit_no=b.permit_no and                                                 "+
		"		c.permit_date=b.permit_date                                                                                                                                                                        "+
		"		and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.denatured_vat='"+act.getVatNo()+"'                                                                                                   "+
		"		and c.recv_dt  between   '2020-07-15'    and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                             "+
		"		union                                                                                                                                                                                              "+
		"		select   b.dt_time , false as flg,v.txn_date as date,'Import of ENA' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                      "+
		"		a.int_distilleri_id,a.vch_tank_name,0 as cosum_bl,                                                                                                                                                 "+
		"		0  as cosum_al, b.net_bl as recv_bl,                                                                                                                                                               "+
		"		b.net_al  as recv_al,                                                                                                                                                                              "+
		"		v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                            "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b,                                                                                                             "+
		"		distillery.vat_wastage v  where  a.int_id=b.denatured_spirit_id and  a.int_distilleri_id=b.distillery_id                                                                                           "+
		"		and b.denatured_spirit_id= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                          "+
		"		and     b.denatured_spirit_id='"+act.getVatNo()+"'                                                                                                                                                 "+
		"		and v.txn_date between   '2020-07-15'    and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                             "+
		"		union                                                                	                                                                                                                           "+
		"		select   b.dt_time , false as flg,c.dt_created as date,'Ethanol Sale To Oil Companies' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                     "+
		"		a.int_distilleri_id,a.vch_tank_name,(b.transfer_bl+b.wastage_bl) as cosum_bl,                                                                                                                      "+
		"		(transfer_al+b.wastage_al) as cosum_al,0 as recv_bl,                                                                                                                                               "+
		"		0 as recv_al,                                                                                                                                                                                      "+
		"		0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                           "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.export_denatured_spirit_detail b,distillery.export_denatured_spirit c                                                        "+
		"		where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id  and   b.int_id_fk=c.int_id                                                                                                      "+
		"		and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                                 "+
		"		and     b.vat_no='"+act.getVatNo()+"'  and c.dt_created between   '2020-07-15'    and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   	                                               "+
		"		union                                                                                                                                                                                              "+
		"		select   b.dt_time , false as flg,b.date as date,'REMOVAL OF SPIRIT FOR DENATURING' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                    "+
		"		a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                                "+
		"		0  as cosum_al,b.net_bl  as recv_bl,                                                                                                                                                               "+
		"		b.net_al  as recv_al,                                                                                                                                                                              "+
		"		0 as vat_wastage_bl,           0  as vat_wastage_al                                                                                                                                                "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                               "+
		"		distillery.vat_wastage v  where  a.int_id=b.den_vat_no and  a.int_distilleri_id=b.int_distillery_code                                                                                              "+
		"		and b.den_vat_no= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                             "+
		"		and     b.den_vat_no='"+act.getVatNo()+"'  and   v.type='SPR-DEN_TRANSFER_WASTAGE '  and v.vat_des ='D'   and  b.date between   '2020-07-15'                                                       "+
		"		 and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   	                                                                                                                               "+
		"		                                                                                                                                                                                                   "+
		"		union                                                                                                                                                                                              "+
		"		                                                                                                                                                                                                   "+
		"		select    b.dt_time , false as flg,b.dt_created as date,'Spirit Purchased In ( State )' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                  "+
		"		a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                                "+
		"		0 as cosum_al, b.net_bl  as recv_bl,                                                                                                                                                               "+
		"		b.net_al  as recv_al,                                                                                                                                                                              "+
		"		v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                            "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b,                                                                                                    "+
		"		distillery.vat_wastage v  where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.distillery_id                                                                                                 "+
		"		and b.denatured_vat= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                "+
		"		and     b.denatured_vat='"+act.getVatNo()+"'                                                                                                                                                       "+
		"		and b.dt_created between   '2020-07-15'     and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                          "+
		"		                                                                                                                                                                                                   "+
		"		union                                                                                                                                                                                              "+
		"		select   b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                        "+
		"		,a.vch_tank_name,0                                                                                                                                                                                 "+
		"		as cosum_bl,                                                                                                                                                                                       "+
		"		0 as cosum_al, b.bal_in_source_bl  as recv_bl, b.bal_in_source_al as recv_al,                                                                                                                      "+
		"		b.wastage_bl  as vat_wastage_bl,b.wastage_al  as vat_wastage_al                                                                                                                                    "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b,                                                                        "+
		"		distillery.vat_wastage v  where  a.int_id=b.int_to_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                             "+
		"		and b.int_to_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                            "+
		"		and     b.int_to_vat_id='"+act.getVatNo()+"' and   v.type='DEN-DEN_TRANSFER_WASTAGE'  and v.vat_des ='D' and b.date_created between   '2020-07-15'                                                 "+
		"		and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                      "+
		"		union		                                                                                                                                                                                       "+
		"		select    b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                        "+
		"		,a.vch_tank_name,dob_qunty_transfer_bl                                                                                                                                                             "+
		"		as cosum_bl,                                                                                                                                                                                       "+
		"		dob_qunty_transfer_al  as cosum_al,0  as recv_bl,0 as recv_al,                                                                                                                                     "+
		"		0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                          "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b                                                                         "+
		"		where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                     "+
		"		and  a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                                "+
		"		and     b.int_from_vat_id='"+act.getVatNo()+"'  and b.date_created between   '2020-07-15'    and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'     )x                                  "+
		"		                                                                                                                                                                                                   "+
		"		union                                                                                                                                                                                              "+
		"		select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,                                                          "+
		"		coalesce(x.cosum_al,0.0) as cosum_al, 	coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                  "+
		"		coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                           "+
		"		(coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                        "+
		"		(select    b.dt_time , false as flg,b.dt_crdt as date ,'REMOVAL OF DENATURED SPIRIT ' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,                                             "+
		"		a.vch_tank_name,int_issued_quantity_bl  as cosum_bl,                                                                                                                                               "+
		"		int_issued_quantity_al  as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                     "+
		"		0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                           "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.denatured_spirits_to_issuevat b                                                                                              "+
		"		where  a.int_id=b.int_denatured_vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                      "+
		"		and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.int_denatured_vat_id='"+act.getVatNo()+"'                                                                                             "+
		"		and b.dt_crdt between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                    "+
		"		union                                                                                                                                                                                              "+
		"		select  b.dt_time , false as flg,c.recv_dt as date,'ENA Gatepass Receiving With In State ' as dcription,a.openingal,a.openingbl, a.int_id,a.int_distilleri_id,                                      "+
		"		a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                    "+
		"		0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                         "+
		"		0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                          "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b , distillery.export_spirit_in_state c                                                               "+
		"		where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.distillery_id and   b.distillery_id::text=c.consigneeid and c.permit_no=b.permit_no and                                                 "+
		"		c.permit_date=b.permit_date                                                                                                                                                                        "+
		"		and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.denatured_vat='"+act.getVatNo()+"'                                                                                                   "+
		"		and c.recv_dt  between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                   "+
		"		union                                                                                                                                                                                              "+
		"		select   b.dt_time , false as flg,v.txn_date as date,'Import of ENA' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                      "+
		"		a.int_distilleri_id,a.vch_tank_name,0 as cosum_bl,                                                                                                                                                 "+
		"		0  as cosum_al, b.net_bl as recv_bl,                                                                                                                                                               "+
		"		b.net_al  as recv_al,                                                                                                                                                                              "+
		"		v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                            "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b,                                                                                                             "+
		"		distillery.vat_wastage v  where  a.int_id=b.denatured_spirit_id and  a.int_distilleri_id=b.distillery_id                                                                                           "+
		"		and b.denatured_spirit_id= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                          "+
		"		and     b.denatured_spirit_id='"+act.getVatNo()+"'                                                                                                                                                 "+
		"		and v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                   "+
		"		union                                                                	                                                                                                                           "+
		"		select  b.dt_time , false as flg,c.dt_created as date,'Ethanol Sale To Oil Companies' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                     "+
		"		a.int_distilleri_id,a.vch_tank_name,(b.transfer_bl+b.wastage_bl) as cosum_bl,                                                                                                                      "+
		"		(transfer_al+b.wastage_al) as cosum_al,0 as recv_bl,                                                                                                                                               "+
		"		0 as recv_al,                                                                                                                                                                                      "+
		"		0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                           "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.export_denatured_spirit_detail b,distillery.export_denatured_spirit c                                                        "+
		"		where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id  and   b.int_id_fk=c.int_id                                                                                                      "+
		"		and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                                 "+
		"		and     b.vat_no='"+act.getVatNo()+"'  and c.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                        "+
		"		 and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   	                                                                                                                                   "+
		"		union                                                                                                                                                                                              "+
		"		select    b.dt_time , false as flg,b.date as date,'REMOVAL OF SPIRIT FOR DENATURING' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                    "+
		"		a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                                "+
		"		0  as cosum_al,b.net_bl  as recv_bl,                                                                                                                                                               "+
		"		b.net_al  as recv_al,                                                                                                                                                                              "+
		"		0 as vat_wastage_bl,           0  as vat_wastage_al                                                                                                                                                "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                               "+
		"		distillery.vat_wastage v  where  a.int_id=b.den_vat_no and  a.int_distilleri_id=b.int_distillery_code                                                                                              "+
		"		and b.den_vat_no= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                             "+
		"		and     b.den_vat_no='"+act.getVatNo()+"'  and   v.type='SPR-DEN_TRANSFER_WASTAGE '  and v.vat_des ='D'   and  b.date between                                                                      "+
		"		 '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   	                                                                       "+
		"		                                                                                                                                                                                                   "+
		"		union                                                                                                                                                                                              "+
		"		                                                                                                                                                                                                   "+
		"		select   b.dt_time , false as flg,b.dt_created as date,'Spirit Purchased In ( State )' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                  "+
		"		a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                                "+
		"		0 as cosum_al, b.net_bl  as recv_bl,                                                                                                                                                               "+
		"		b.net_al  as recv_al,                                                                                                                                                                              "+
		"		v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                            "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b,                                                                                                    "+
		"		distillery.vat_wastage v  where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.distillery_id                                                                                                 "+
		"		and b.denatured_vat= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                "+
		"		and     b.denatured_vat='"+act.getVatNo()+"'                                                                                                                                                       "+
		"		and b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'    and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                "+
		"		                                                                                                                                                                                                   "+
		"		union                                                                                                                                                                                              "+
		"		select   b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                        "+
		"		,a.vch_tank_name,0                                                                                                                                                                                 "+
		"		as cosum_bl,                                                                                                                                                                                       "+
		"		0 as cosum_al, b.bal_in_source_bl  as recv_bl, b.bal_in_source_al as recv_al,                                                                                                                      "+
		"		b.wastage_bl  as vat_wastage_bl,b.wastage_al  as vat_wastage_al                                                                                                                                    "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b,                                                                        "+
		"		distillery.vat_wastage v  where  a.int_id=b.int_to_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                             "+
		"		and b.int_to_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                            "+
		"		and     b.int_to_vat_id='"+act.getVatNo()+"' and   v.type='DEN-DEN_TRANSFER_WASTAGE'  and v.vat_des ='D' and b.date_created between                                                                "+
		"		 '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                           "+
		"		union		                                                                                                                                                                                       "+
		"		select    b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                        "+
		"		,a.vch_tank_name,dob_qunty_transfer_bl                                                                                                                                                             "+
		"		as cosum_bl,                                                                                                                                                                                       "+
		"		dob_qunty_transfer_al  as cosum_al,0  as recv_bl,0 as recv_al,                                                                                                                                     "+
		"		0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                          "+
		"		from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b                                                                         "+
		"		where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                     "+
		"		and  a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                                "+
		"		and     b.int_from_vat_id='"+act.getVatNo()+"'  and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                             "+
		"	    and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'     )x )zz     order by zz.date, zz.dt_time,  zz.dcription                                                                                        ";
					
					




						
						
//-----------------------------------------------------------------------------------------------------------
			/*"			select  distinct zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,     "+
			"			 case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl ,case when zz.flg=true then    zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al from                                                                                                                                                                "+
			"			(select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,                                                   "+
			"			coalesce(x.cosum_al,0.0) as cosum_al, 	coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                         "+
			"			coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                  "+
			"			(coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                               "+
			"			 (select distinct   b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                              "+
			"			 ,a.vch_tank_name,0                                                                                                                                                  "+
			"			 as cosum_bl,                                                                                                                                                                             "+
			"			 0 as cosum_al, b.bal_in_source_bl  as recv_bl, b.bal_in_source_al as recv_al,                                                                                       "+
			"			 b.wastage_bl  as vat_wastage_bl,b.wastage_al  as vat_wastage_al                                                                                                                   "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b,                                                              "+
			"			 distillery.vat_wastage v  where  a.int_id=b.int_to_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                 "+
			"			 and b.int_to_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                "+
			"			 and     b.int_to_vat_id='"+act.getVatNo()+"' and   v.type='DEN-DEN_TRANSFER_WASTAGE'  and v.vat_des ='D' and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                     "+
			"			 union		                                                                                                                                                                              "+
			"			 select distinct   b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                              "+
			"			 ,a.vch_tank_name,dob_qunty_transfer_bl                                                                                                                                                   "+
			"			 as cosum_bl,                                                                                                                                                                             "+
			"			 dob_qunty_transfer_al  as cosum_al,0  as recv_bl,0 as recv_al,                                                                                       "+
			"			 0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                  "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b                                                           "+
			"			 where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                 "+
			"			 and  a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                "+
			"			 and     b.int_from_vat_id='"+act.getVatNo()+"'  and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                     "+

			"			 union                                                                                                                                                                                    "+
			"			 select distinct   b.dt_time , false as flg,b.dt_crdt as date ,'REMOVAL OF DENATURED SPIRIT ' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,                                                 "+
			"			 a.vch_tank_name,int_issued_quantity_bl  as cosum_bl,                                                                                                                                     "+
			"			 int_issued_quantity_al  as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                           "+
			"			 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                 "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.denatured_spirits_to_issuevat b                                                                                    "+
			"			 where  a.int_id=b.int_denatured_vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                            "+
			"			 and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.int_denatured_vat_id='"+act.getVatNo()+"'                                                                                   "+
			"			 and b.dt_crdt between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                      "+
			"			 union                                                                                                                                                                                    "+
			"			 select distinct   b.dt_time , false as flg,c.recv_dt as date,'ENA Gatepass Receiving With In State ' as dcription,a.openingal,a.openingbl, a.int_id,a.int_distilleri_id,                                      "+
			"			 a.vch_tank_name,0  as cosum_bl,                                                                                                                                                          "+
			"			 0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                               "+
			"			 0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b , distillery.export_spirit_in_state c                                                                                          "+
			"			 where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.distillery_id and   b.distillery_id::text=c.consigneeid and c.permit_no=b.permit_no and  c.permit_date=b.permit_date                                                                                                                        "+
			"			 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.denatured_vat='"+act.getVatNo()+"'                                                                                                "+
			"			 and c.recv_dt  between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                  "+
			"			 union  	                                                                                                                                                                              "+
			"			 select distinct   b.dt_time , false as flg,b.dt_created as date,'Spirit Purchased In ( State )' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                       "+
			"			 a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                      "+
			"			 0 as cosum_al, b.net_bl  as recv_bl,                                                                                                                                                     "+
			"			 b.net_al  as recv_al,                                                                                                                                                                    "+
			"			 v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                  "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b,                                                                                          "+
			"			 distillery.vat_wastage v  where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.distillery_id                                                                                       "+
			"			 and b.denatured_vat= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                      "+
			"			 and     b.denatured_vat='"+act.getVatNo()+"'                                                                                                                                             "+
			"			 and b.dt_created between   '2020-07-15'   and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   			                                                                      "+
			"			                                                                                                                                                                                          "+

			"			 union                                                                                                                                                                                    "+
			"			 select distinct   b.dt_time , false as flg,v.txn_date as date,'BondAction' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,a.vch_tank_name,0 as cosum_bl,                                        "+
			"			 0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                               "+
			"			 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                  "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b,                                                                                                   "+
			"			 distillery.vat_wastage v  where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                               "+
			"			 and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.vatno='"+act.getVatNo()+"'                                                                                                  "+
			"			 and v.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                     "+
			"			 union                                                                                                                                                                                    "+
			"			 select distinct   b.dt_time , false as flg,b.date as date,'REMOVAL OF SPIRIT FOR DENATURING' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                   "+
			"			 a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                   "+
			"			 0  as cosum_al,b.net_bl  as recv_bl,                                                                                        "+
			"			 b.net_al  as recv_al,                                                                                                                           "+
			//"			 b.trns_wst_bl " +
			"            0 as vat_wastage_bl," +
			//"            b.trns_wst_al " +
			"           0  as vat_wastage_al                                                                                                                  "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                     "+
			"			 distillery.vat_wastage v  where  a.int_id=b.den_vat_no and  a.int_distilleri_id=b.int_distillery_code                                                                                  "+
			"			 and b.den_vat_no= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                 "+
			"			 and     b.den_vat_no='"+act.getVatNo()+"'  and   v.type='SPR-DEN_TRANSFER_WASTAGE '  and v.vat_des ='D'   and  b.date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  		                              "+
			"  			 union                                                                                                                                                                                    "+
			"			 select distinct   b.dt_time , false as flg,v.txn_date as date,'Import of ENA' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                         "+
			"			 a.int_distilleri_id,a.vch_tank_name,0 as cosum_bl,                                                                                                                                       "+
			"			 0  as cosum_al, b.net_bl as recv_bl,                                                                                                                                                     "+
			"			 b.net_al  as recv_al,                                                                                                                                                                    "+
			"			 v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                  "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b,                                                                                                   "+
			"			 distillery.vat_wastage v  where  a.int_id=b.denatured_spirit_id and  a.int_distilleri_id=b.distillery_id                                                                                 "+
			"			 and b.denatured_spirit_id= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                "+
			"			 and     b.denatured_spirit_id='"+act.getVatNo()+"'                                                                                                                                       "+
			"			 and v.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                   "+
			"            union                                                                "+
			"	         select distinct   b.dt_time , false as flg,c.dt_created as date,'Ethanol Sale To Oil Companies' as dcription,a.openingal,a.openingbl ,a.int_id,                                                 "+
			"	         a.int_distilleri_id,a.vch_tank_name,(b.transfer_bl+b.wastage_bl) as cosum_bl,                                                                                     "+
			"	        (transfer_al+b.wastage_al) as cosum_al,0 as recv_bl,                                                                                                              "+
			"            0 as recv_al,                                                                                                                                                     "+
			"            0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                          "+
			"             from distillery.distillery_denatures_spirit_store_detail a,distillery.export_denatured_spirit_detail b,distillery.export_denatured_spirit c                       "+
			"	         where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id  and   b.int_id_fk=c.int_id                                                               "+
			"            and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                "+
			"	         and     b.vat_no='"+act.getVatNo()+"'  and c.dt_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  		)x              "+
            "                                                                                                                                                                                                     "+
			"			 union                                                                                                                                                                                    "+
			"			 select distinct 	x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,                                                   "+
			"			 coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                    "+
			"			 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                "+
			"			 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                 "+
			"			 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                              "+
			"			 (select distinct   b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                              "+
			"			 ,a.vch_tank_name,0                                                                                                                                                   "+
			"			 as cosum_bl,                                                                                                                                                                             "+
			"			 0  as cosum_al, b.bal_in_source_bl  as recv_bl, b.bal_in_source_al as recv_al,                                                                                       "+
			"			 b.wastage_bl  as vat_wastage_bl,b.wastage_al  as vat_wastage_al                                                                                                                  "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b,                                                              "+
			"			 distillery.vat_wastage v  where  a.int_id=b.int_to_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                 "+
			"			 and b.int_to_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                "+
			"			 and     b.int_to_vat_id='"+act.getVatNo()+"'  and   v.type='DEN-DEN_TRANSFER_WASTAGE'  and v.vat_des ='D'                                                                                                                                         "+
			"			 and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                         "+
			"			 union		                                                                                                                                                                              "+
			"			 select distinct   b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                              "+
			"			 ,a.vch_tank_name,dob_qunty_transfer_bl                                                                                                                                                   "+
			"			 as cosum_bl,                                                                                                                                                                             "+
			"			 dob_qunty_transfer_al  as cosum_al,0  as recv_bl,0 as recv_al,                                                                                       "+
			"			 0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                  "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b                                                           "+
			"			 where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                 "+
			"			 and  a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                "+
			"			 and     b.int_from_vat_id='"+act.getVatNo()+"'  and b.date_created between  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                     "+
			"			 union                                                                                                                                                                                    "+
			"			 select distinct   b.dt_time , false as flg,b.dt_crdt as date ,'REMOVAL OF DENATURED SPIRIT ' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,                                                 "+
			"			 a.vch_tank_name,int_issued_quantity_bl  as cosum_bl,                                                                                                                                     "+
			"			 int_issued_quantity_al  as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                           "+
			"			 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                 "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.denatured_spirits_to_issuevat b                                                                                    "+
			"			 where  a.int_id=b.int_denatured_vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                            "+
			"			 and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.int_denatured_vat_id='"+act.getVatNo()+"'                                                                                   "+
			"			 and b.dt_crdt between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                          "+
			"			 union                                                                                                                                                                                    "+
			"			 select distinct   b.dt_time , false as flg,c.recv_dt as date,'ENA Gatepass Receiving With In State ' as dcription,a.openingal,a.openingbl, a.int_id,a.int_distilleri_id,                                      "+
			"			 a.vch_tank_name,0  as cosum_bl,                                                                                                                                                          "+
			"			 0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                               "+
			"			 0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b , distillery.export_spirit_in_state c                                                                                          "+
			"			 where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.distillery_id and   b.distillery_id::text=c.consigneeid and c.permit_no=b.permit_no and  c.permit_date=b.permit_date                                                                                                                        "+
			"			 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.denatured_vat='"+act.getVatNo()+"'                                                                                                "+
			"			 and c.recv_dt  between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                  "+
			"			 union  	                                                                                                                                                                              "+
			"			 select distinct   b.dt_time , false as flg,b.dt_created as date,'Spirit Purchased In ( State )' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                       "+
			"			 a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                      "+
			"			 0 as cosum_al, b.net_bl  as recv_bl,                                                                                                                                                     "+
			"			 b.net_al  as recv_al,                                                                                                                                                                    "+
			"			 v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                  "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b,                                                                                          "+
			"			 distillery.vat_wastage v  where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.distillery_id                                                                                       "+
			"			 and b.denatured_vat= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                      "+
			"			 and     b.denatured_vat='"+act.getVatNo()+"'                                                                                                                                             "+
			"			 and b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                         "+

			"			 union                                                                                                                                                                                    "+
			"			 select distinct   b.dt_time , false as flg,v.txn_date as date,'BondAction' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,a.vch_tank_name,0 as cosum_bl,                                        "+
			"			 0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                               "+
			"			 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                  "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b,                                                                                                   "+
			"			 distillery.vat_wastage v  where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                               "+
			"			 and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.vatno='"+act.getVatNo()+"'                                                                                                  "+
			"			 and v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                         "+
			"			 union                                                                                                                                                                                    "+
			"			 select distinct   b.dt_time , false as flg,b.date as date,'REMOVAL OF SPIRIT FOR DENATURING' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                   "+
			"			 a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                   "+
			"			 0 as cosum_al, b.net_bl as recv_bl,                                                                                        "+
			"			 b.net_al  as recv_al,                                                                                                                           "+
			//"			 b.trns_wst_bl  " +
			"            0  as vat_wastage_bl," +
			//"             b.trns_wst_al  " +
			"            0  as vat_wastage_al                                                                                                                  "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                     "+
			"			 distillery.vat_wastage v  where  a.int_id=b.den_vat_no and  a.int_distilleri_id=b.int_distillery_code                                                                                  "+
			"			 and b.den_vat_no= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                 "+
			"			 and     b.den_vat_no='"+act.getVatNo()+"' and   v.type='SPR-DEN_TRANSFER_WASTAGE '  and v.vat_des ='D'                                                                                                                                               "+
			"			 and b.date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                         "+
			"                                                                                                                                                                                                     "+
			"			 union                                                                                                                                                                                    "+
			"			 select distinct   b.dt_time , false as flg,v.txn_date as date,'Import of ENA' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                         "+
			"			 a.int_distilleri_id,a.vch_tank_name,0 as cosum_bl,                                                                                                                                       "+
			"			 0  as cosum_al, b.net_bl as recv_bl,                                                                                                                                                     "+
			"			 b.net_al  as recv_al,                                                                                                                                                                    "+
			"			 v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                  "+
			"			 from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b,                                                                                                   "+
			"			 distillery.vat_wastage v  where  a.int_id=b.denatured_spirit_id and  a.int_distilleri_id=b.distillery_id                                                                                 "+
			"			 and b.denatured_spirit_id= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                "+
			"			 and     b.denatured_spirit_id='"+act.getVatNo()+"'                                                                                                                                       "+
			"			 and v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   					                  "+
			"            union "+
			"	         select distinct   b.dt_time , false as flg,c.dt_created as date,'Ethanol Sale To Oil Companies' as dcription,a.openingal,a.openingbl ,a.int_id,                                                 "+
			"	         a.int_distilleri_id,a.vch_tank_name,(b.transfer_bl+b.wastage_bl) as cosum_bl,                                                                                     "+
			"	        (transfer_al+b.wastage_al) as cosum_al,0 as recv_bl,                                                                                                              "+
			"            0 as recv_al,                                                                                                                                                     "+
			"            0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                          "+
			"             from distillery.distillery_denatures_spirit_store_detail a,distillery.export_denatured_spirit_detail b,distillery.export_denatured_spirit c                       "+
			"	         where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id  and   b.int_id_fk=c.int_id                                                                 "+
			"            and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                "+
			"	         and     b.vat_no='"+act.getVatNo()+"'  and c.dt_created between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   	             "+
   
			"			 )X )zz     order by zz.date                                                                                                                                                                   ";    
						 
*/
	
//--------------------------------------------------
				
				System.out.println("---Blending DV---" + selQuery);

			}

			else if (act.getRadio().equalsIgnoreCase("BLENDFL")) {
				
				type="Blending VatFL";

				selQuery =
				"		select  distinct zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                                                                       "+
				"		 case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl ,case when zz.flg=true then    zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al " +
				"        from (select distinct 	x.dt_time,x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,                                                                                                        "+
				"		 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                   	                                                                                                                      "+
				"		 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                            "+
				"		 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                             "+
				"		   (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                          "+
				"		 (select  b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                                                                       "+
				"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,dob_qunty_transfer_bl                                                                                                                "+
				"		 as cosum_bl,                                                                                                                                                                                                                         "+
				"		 dob_qunty_transfer_al  as cosum_al, 0 as recv_bl,0 as recv_al,                                                                                                                                                                       "+
				"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
				"		 from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                                                                                "+
				"		 where  a.storage_id=b.int_from_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                                                                   "+
				"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
				"		 and     b.int_from_vat_id='"+act.getVatNo()+"'                                                                                                                                                                                       "+
				"		 and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                             "+
				"		 union                                                                                                                                                                                                                                "+
				"		 select  b.dt_time , false as flg, b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                                                                        "+
				"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                    "+
				"		 as cosum_bl,   0 as cosum_al,b.net_bl as recv_bl,b.net_al as recv_al,                                                                                                                                                                "+
				"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
				"		 from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                                                                                "+
				"		 where  a.storage_id=b.int_to_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                                                                     "+
				"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
				"		 and     b.int_to_vat_id='"+act.getVatNo()+"'        and                                                                                                                                                                              "+
				"		 b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                 "+
				"		 union                                                                                                                                                                                                                                "+
				"		 select  b.dt_time , false as flg,b.txn_date as date ,'Transfer To Bottling Vat ' as dcription,a.openingal,a.openingbl ,                                                                                                                                       "+
				"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,(b.recieve_bl+b.wastagebl)                                                                                                           "+
				"		 as cosum_bl, (b.recieve_al+b.wastageal) as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                                       "+
				"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
				"		 from distillery.spirit_for_bottling a,distillery.master_bottoling_of_vat b                                                                                                                                                           "+
				"		 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                "+
				"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
				"		 and     b.vat_no='"+act.getVatNo()+"'                                                                                                                                                                                                "+
				"		 and b.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                 "+
				"		 union                                                                                                                                                                                                                                "+
				"		 select   b.dt_time , false as flg,b.tansfer_dt as date ,'Transfer of spirit to FL Blending Vat ' as dcription,a.openingal,a.openingbl ,                                                                                                                      "+
				"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                    "+
				"		 as cosum_bl,  0 as cosum_al,qty_recv_bl as recv_bl,qty_recv_al as recv_al,                                                                                                                                                           "+
				"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
				"		 from distillery.spirit_for_bottling a,distillery.transferspirit_to_fl_blending b                                                                                                                                                     "+
				"		 where  a.storage_id=b.to_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                             "+
				"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
				"		 and     b.to_vat_no='"+act.getVatNo()+"'       and b.tansfer_dt between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                            "+
				"		 union                                                                                                                                                                                                                                "+
				"		 select   b.dt_time ,b.data_flg as flg,b.date_created as date ,'Prepration Of FL Blend  ' as dcription,a.openingal,a.openingbl ,                                                                                                                      "+
				"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                    "+
				"		 as cosum_bl,  0 as cosum_al,produced_bl as recv_bl,((produced_bl*strength)/100) as recv_al,                                                                                                                                                           "+
				"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
				"		 from distillery.spirit_for_bottling a,distillery.foreign_liquor_blending b                                                                                                                                                     "+
				"		 where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                             "+
				"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
				"		 and     b.int_vat_no='"+act.getVatNo()+"'       and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  )x	                                                                          "+
				"		 union                                                                                                                                                                                                                                "+

				"		 select distinct x.dt_time,	x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                          "+
				"		 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                            "+
				"		 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                             "+
				"		 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                          "+
				"		 (select   b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                                                                       "+
				"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,dob_qunty_transfer_bl                                                                                                                "+
				"		 as cosum_bl,                                                                                                                                                                                                                         "+
				"		 dob_qunty_transfer_al  as cosum_al, 0 as recv_bl,0 as recv_al,                                                                                                                                                                       "+
				"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
				"		 from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                                                                                "+
				"		 where  a.storage_id=b.int_from_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                                                                   "+
				"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
				"		 and     b.int_from_vat_id='"+act.getVatNo()+"'       and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                             "+
				"		 union                                                                                                                                                                                                                                "+
				"		 select  b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                                                                        "+
				"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                    "+
				"		 as cosum_bl,   0 as cosum_al,b.net_bl as recv_bl,b.net_al as recv_al,                                                                                                                                                                "+
				"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
				"		 from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                                                                                "+
				"		 where  a.storage_id=b.int_to_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                                                                     "+
				"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
				"		 and     b.int_to_vat_id='"+act.getVatNo()+"'        and                                                                                                                                                                              "+
				"		 b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                      "+
				"		 union                                                                                                                                                                                                                                "+
				"		 select   b.dt_time , false as flg,b.txn_date as date ,'Transfer To Bottling Vat ' as dcription,a.openingal,a.openingbl ,                                                                                                                                       "+
				"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,(b.recieve_bl+b.wastagebl)                                                                                                           "+
				"		 as cosum_bl, (b.recieve_al+b.wastageal) as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                                       "+
				"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
				"		 from distillery.spirit_for_bottling a,distillery.master_bottoling_of_vat b                                                                                                                                                           "+
				"		 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                "+
				"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
				"		 and     b.vat_no='"+act.getVatNo()+"'       and b.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' 	                                      "+
				"		 union                                                                                                                                                                                                                                "+
				"		 select   b.dt_time , false as flg,b.tansfer_dt as date ,'Transfer of spirit to FL Blending Vat ' as dcription,a.openingal,a.openingbl ,                                                                                                                      "+
				"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                    "+
				"		 as cosum_bl, 0 as cosum_al,qty_recv_bl as recv_bl,qty_recv_al as recv_al,                                                                                                                                                            "+
				"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
				"		 from distillery.spirit_for_bottling a,distillery.transferspirit_to_fl_blending b                                                                                                                                                     "+
				"		 where  a.storage_id=b.to_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                             "+
				"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
				"		 and     b.to_vat_no='"+act.getVatNo()+"'       and                                                                                                                                                                                   "+
				"		 b.tansfer_dt between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                     "+
				"		 union                                                                                                                                                                                                                                "+

				"		 select b.dt_time ,b.data_flg as flg,b.date_created as date ,'Prepration Of FL Blend  ' as dcription,a.openingal,a.openingbl ,                                                                                                                      "+
				"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                    "+
				"		 as cosum_bl,  0 as cosum_al,produced_bl as recv_bl,((produced_bl*strength)/100) as recv_al,                                                                                                                                                           "+
				"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
				"		 from distillery.spirit_for_bottling a,distillery.foreign_liquor_blending b                                                                                                                                                     "+
				"		 where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                             "+
				"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
				"		 and     b.int_vat_no='"+act.getVatNo()+"'       and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'     )x	)zz     " +
						"   group by zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al, "+
   " zz.recv_bl, zz.recv_al ,zz.vat_wastage_bl,zz.vat_wastage_al ,zz.bal_bl,zz.bal_al "+
						"order by zz.date , zz.dt_time, zz.dcription                                                          ";

				                                                                                                                                                                                                                                              
                                                                                                                                                                                                                                                              
//--------------------------------------	                                                                                                                                                                                                                  
				System.out.println("---BLENDFL---" + selQuery);                                                                                                                                                                                               
                                                                                                                                                                                                                                                              
			} else if (act.getRadio().equalsIgnoreCase("BLENDCL")) {                                                                                                                                                                                          
				                                                                                                                                                                                                                                              
				type="Blending VatCL";                                                                                                                                                                                                                        
			                                                                                                                                                                                                                                                  
				selQuery =                                                                                                                                                                                                                                        
					                                                                                                                                                                                                                                          
			"		select  distinct zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                                                                              "+
			"		   case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl, " +
			"           case when zz.flg=true then    zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al from                                                                                                                                                                                                              "+
			"		   (select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm  ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                             "+
			"		   coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                              "+
			"		   coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                               "+
			"		   (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                                                           "+
			"		   ( select   b.dt_time , false as flg,b.txn_date as date, 'Transfer To CL Bottling Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                       "+
			"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
			"		   ,(b.recieve_bl+b.wastagebl) as cosum_bl, (b.recieve_al+b.wastageal) as cosum_al, " +
		  //"          (((b.recieve_bl*recieve_strength)/100)+b.wastagebl) as cosum_al,                                                                                                                                                        "+
			"		   0 as recv_bl,0 as recv_al,                                                                                                                                                                                                             "+
			"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
			"		   from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                                                                                       "+
			"		   where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                  "+
			"		   and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
			"		   and     b.vat_no='"+act.getVatNo()+"'  	 and b.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                         "+
			//"		   union                                                                                                                                                                                                                                  "+
			//"		   select distinct   b.dt_time , false as flg,b.txn_date as date, 'BottlingVatForCLAction' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                             "+
			//"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
			//"		   ,(b.recieve_bl+b.wastagebl) as cosum_bl,(b.recieve_al+b.wastageal) as cosum_al,                                                                                                                                                        "+
			//"		   0 as recv_bl,0 as recv_al,                                                                                                                                                                                                             "+
			//"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
			//"		   from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                                                                                       "+
			//"		   where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                  "+
			//"		   and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
			//"		   and     b.vat_no='"+act.getVatNo()+"'  	 and b.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                          "+
			"		   union                                                                                                                                                                                                                                  "+
			"		   select  b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                    "+
			"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
			"		   ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                                          "+
			"		   b.qty_recv_bl as recv_bl, b.qty_recv_al  as recv_al,                                                                                                                                                                                  "+
			"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
			"		   from distillery.spirit_for_bottling_cl a,distillery.transferspirit_to_cl_blending b ,distillery.vat_wastage v                                                                                                                                                         "+
			"		   where  a.storage_id=b.to_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                              "+
			"		   and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distillery_id='"+act.getLoginUserId()+"' and b.tansfer_dt=v.txn_date                                                                                                                                                                                      "+
			"		   and     b.to_vat_no='"+act.getVatNo()+"' and  v.type='CL_BLN_WASTAGE' and   v.vat_des='F'  	 and b.tansfer_dt between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                  "+

			"		   union                                                                                                                                                                                                                                  "+
			"		   select  b.dt_time,b.data_flg as flg,b.date_created as date, 'Prepration Of CL Blend ' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                     "+
			"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
			"		   ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                  "+
			"		   b.produced_bl as recv_bl,b.produced_al    as recv_al,                                                                                                                                                                                                           "+
			"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
			"		   from distillery.spirit_for_bottling_cl a,distillery.country_liquor_blending b                                                                                                                                                          "+
			"		   where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                              "+
			"		   and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
			"		   and     b.int_vat_no='"+act.getVatNo()+"'  	 and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  )x                                                                             "+
			"		   union                                                                                                                                                                                                                                  "+
			"		   select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm  ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                              "+
			"		   coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                              "+
			"		   coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                               "+
			"		   (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                                                           "+
			"		   ( select   b.dt_time , false as flg,b.txn_date as date, 'Transfer To CL Bottling Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                       "+
			"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
			"		   ,(b.recieve_bl+b.wastagebl) as cosum_bl,(b.recieve_al+b.wastageal) as cosum_al," +
		//	"          (((b.recieve_bl*recieve_strength)/100)+b.wastagebl) as cosum_al,                                                                                                                                                        "+
			"		   0 as recv_bl,0 as recv_al,                                                                                                                                                                                                             "+
			"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
			"		   from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                                                                                       "+
			"		   where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                  "+
			"		   and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
			"		   and     b.vat_no='"+act.getVatNo()+"'  	 and b.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                              "+
		//	"		   union                                                                                                                                                                                                                                  "+
		//	"		   select distinct b.dt_time , false as flg,b.txn_date as date, 'BottlingVatForCLAction' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                             "+
		//	"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
		//	"		   ,(b.recieve_bl+b.wastagebl) as cosum_bl,(b.recieve_al+b.wastageal) as cosum_al,                                                                                                                                                        "+
		//	"		   0 as recv_bl,0 as recv_al,                                                                                                                                                                                                             "+
		//	"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
		//	"		   from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                                                                                       "+
		//	"		   where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                  "+
		//	"		   and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
		//	"		   and     b.vat_no='"+act.getVatNo()+"'  	 and b.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                              "+
			"		   union                                                                                                                                                                                                                                  "+
			"		   select   b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                    "+
			"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
			"		   ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                                          "+
			"		   b.qty_recv_bl as recv_bl, b.qty_recv_al  as recv_al,                                                                                                                                                                                  "+
			"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
			"		   from distillery.spirit_for_bottling_cl a,distillery.transferspirit_to_cl_blending b ,distillery.vat_wastage v                                                                                                                                                         "+
			"		   where  a.storage_id=b.to_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                              "+
			"		   and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distillery_id='"+act.getLoginUserId()+"'   and b.tansfer_dt=v.txn_date                                                                                                                                                                                    "+
			"		   and     b.to_vat_no='"+act.getVatNo()+"' and  v.type='CL_BLN_WASTAGE' and   v.vat_des='F' 	 and b.tansfer_dt between  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                 "+
			"		   union                                                                                                                                                                                                                                  "+
			"		   select b.dt_time,b.data_flg as flg, b.date_created as date, 'Prepration Of CL Blend ' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                     "+
			"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
			"		   ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                  "+
			"		   b.produced_bl as recv_bl,b.produced_al  as recv_al,                                                                                                                                                                                                           "+
			"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
			"		   from distillery.spirit_for_bottling_cl a,distillery.country_liquor_blending b                                                                                                                                                          "+
			"		   where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                              "+
			"		   and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
			"		   and     b.int_vat_no='"+act.getVatNo()+"'  	 and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  )x    )zz     order by zz.date , zz.dt_time, zz.dcription     ";
	

//------------------------------				

				System.out.println("---BLEND=CL---" + selQuery);
                                                                                                                                                                                                                                                                                            
			} else if (act.getRadio().equalsIgnoreCase("BOTFL")) {                                                                                                                                                                                                                          
				                                                                                                                                                                                                                                                                            
				type="Bottling VatFL";                                                                                                                                                                                                                                                      
				                                                                                                                                                                                                                                                                            
                                                                                                                                                                                                                                                                                            
				selQuery =                                                                                                                                                                                                                                                                  
						                                                                                                                                                                                                                                                                   
					"	 select  distinct zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                                                                                                      "+
					"	 case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl ,case when zz.flg=true then    zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al from                                                                                                                                                                                                                                          "+
					"	 (select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,                                                                                                                                                                    "+
					"	 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                                                         "+
					"	 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                                          "+
					"	 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                           "+
					"	 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                                                                                       "+
					"	 (select distinct   b.dt_time , false as flg,b.txn_date as date ,'Transfer To Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                              "+
					"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
					"	 0 as cosum_bl,  0 as cosum_al, b.recieve_bl as recv_bl,                                                                                                                                                                                                          "+
					"	 b.recieve_al as recv_al,                                                                                                                                                                                                                                         "+
					"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
					"	 from distillery.bottling_vat a, distillery.master_bottoling_of_vat b                                                                                                                                                                                               "+
					"	 where  a.storage_id=b.storage_id and  a.int_distillery_id=b.distillery_id                                                                                                                                                                                              "+
					"	 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.storage_id='"+act.getVatNo()+"'   and                                                                                                                                                                     "+
					"	 b.txn_date between   '2020-07-15'   and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                                                  "+
					"	 union                                                                                                                                                                                                                                                              "+
					"	 select distinct   b.dt_time , false as flg,c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Permises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                "+
					"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
					"	 (b.double_quantity_bl-b.double_wastg_bottling) as cosum_bl,  (b.double_al-b.double_wastage_al) as cosum_al,                                                                                                                                                        "+
					"	 0 as recv_bl,0 as recv_al,                                                                                                                                                                                                                                         "+
					"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
					"	 from distillery.bottling_vat a, distillery.bottling_dtl_20_21 b ,distillery.bottling_master_20_21 c                                                                                                                                                             "+
					"	 where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id                                                                                                                                                                                   "+
					"	 and b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id and a.int_distillery_id='"+act.getLoginUserId()+"' and                                                                                                                                               "+
					"	 b.blending_vat_id='"+act.getVatNo()+"'   and   c.date_currunt_date between   '2020-07-15'  and 		'"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' )X                                                                                                "+
					"	 union                                                                                                                                                                                                                                                              "+
					"	 select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,                                                                                                                                                                     "+
					"	 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                                                         "+
					"	 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                                          "+
					"	 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                           "+
					"	 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                                                                                       "+
					"	 (select distinct   b.dt_time , false as flg,b.txn_date as date ,'Transfer To Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                              "+
					"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
					"	 0 as cosum_bl,  0 as cosum_al, b.recieve_bl as recv_bl,                                                                                                                                                                                              "+
					"	 b.recieve_al as recv_al,                                                                                                                                                                                                                             "+
					"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
					"	 from distillery.bottling_vat a, distillery.master_bottoling_of_vat b                                                                                                                                                                                               "+
					"	 where  a.storage_id=b.storage_id and  a.int_distillery_id=b.distillery_id                                                                                                                                                                                              "+
					"	 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.storage_id='"+act.getVatNo()+"'   and   b.txn_date between                                                                                                                                                "+
					"	 '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and  '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                                             "+
					"	 union                                                                                                                                                                                                                                                              "+
					"	 select distinct   b.dt_time , false as flg,c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Permises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                "+
					"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
					"	 (b.double_quantity_bl-b.double_wastg_bottling) as cosum_bl,  (b.double_al-b.double_wastage_al) as cosum_al,                                                                                                                                                        "+
					"	 0 as recv_bl,0 as recv_al,                                                                                                                                                                                                                                         "+
					"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
					"	 from distillery.bottling_vat a, distillery.bottling_dtl_20_21 b ,distillery.bottling_master_20_21 c                                                                                                                                                             "+
					"	 where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id                                                                                                                                                                                   "+
					"	 and b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id and a.int_distillery_id='"+act.getLoginUserId()+"' and                                                                                                                                               "+
					"	 b.blending_vat_id='"+act.getVatNo()+"'   and                                                                                                                                                                                                                       "+
					"	 c.date_currunt_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and   '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   )X  )zz     order by zz.date  zz.dt_time, zz.dcription                                                                                 ";                         
                                                                                                                                                                                                                                                                                            
						                                                                                                                                                                                                                                                                    
				                                                                                                                                                                                                                                                                            
                                                                                                                                                                                                                                                                                            
				System.out.println("---Bottling FL---" + selQuery);                                                                                                                                                                                                                         
                                                                                                                                                                                                                                                                                            
			} else if (act.getRadio().equalsIgnoreCase("BOTCL")) {                                                                                                                                                                                                                         
			                                                                                                                                                                                                                                                                                
				                                                                                                                                                                                                                                                                            
				type="Bottling VatCL";                                                                                                                                                                                                                                                      
				                                                                                                                                                                                                                                                                            
				selQuery =                                                                                                                                                                                                                                                                  
					                                                                                                                                                                                                                                                                        
					"	select  distinct zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,                                                                                                                           "+
					"	 zz.vat_wastage_al,                                                                                                                                                                                                                                                 "+
					"	case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl ,case when zz.flg=true then    zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al from                                                                                                                                                                                                                                          "+
					"	 (select distinct x.dt_time,	x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,                                                                                                                                                                                                      "+
					"	 x.vch_tank_name as tank_nm ,                                                                                                                                                                                                                                       "+
					"	 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                                                         "+
					"	 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                                          "+
					"	 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                           "+
					"	 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                                                        "+
					"	 (select distinct   b.dt_time , false as flg,b.txn_date as date ,'Transfer To CL Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                     "+
					"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
					"	 0 as cosum_bl,  0 as cosum_al,b.recieve_bl as recv_bl, b.recieve_al as recv_al,                                                                                                                                                                                                            "+
			//		"	(((b.recieve_bl*b.recieve_strength)/100)+b.wastageal) as recv_al,                                                                                                                                                                                                                                           "+
					"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
					"	 from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                                                                                                                                         "+
					"	 where  a.storage_id=b.storage_id and  a.int_distillery_id=b.distillery_id                                                                                                                                                                                              "+
					"	 and a.int_distillery_id='"+act.getLoginUserId()+"'  and     b.storage_id='"+act.getVatNo()+"'      and                                                                                                                                                                 "+
					"	 b.txn_date between   '2020-07-15' and  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                                                   "+
					//"	 union                                                                                                                                                                                                                                                              "+
					//"	 select distinct   b.dt_time , false as flg,b.txn_date as date ,'BottlingVatForCLAction' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                          "+
					//"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
					//"	 0 as cosum_bl,  0 as cosum_al, b.recieve_bl as recv_bl,                                                                                                                                                                                                            "+
					//"	 b.recieve_al as recv_al,                                                                                                                                                                                                                                           "+
					//"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
					//"	 from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                                                                                                                                         "+
					//"	 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                                              "+
					//"	 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.vat_no='"+act.getVatNo()+"'    and                                                                                                                                                                    "+
					//"	 b.txn_date between   '2020-07-15'   and  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'	                                                                                                                                                                "+
					"	 union                                                                                                                                                                                                                                                              "+
					"	 select distinct   b.dt_time , false as flg,c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Premises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                     "+
					"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
					"	 (double_quantity_bl+double_wastg_bottling) as cosum_bl, (double_al+double_wastage_al) as cosum_al, 0 as recv_bl,                                                                                                                                                   "+
					"	 0 as recv_al,                                                                                                                                                                                                                                                      "+
					"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
					"	 from distillery.bottling_vat_cl a, distillery.bottling_dtl_cl_20_21 b ,distillery.bottling_master_cl_19_20 c                                                                                                                                                       "+
					"	 where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id  and   b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id                                                                                                                  "+
					"	 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.blending_vat_id='"+act.getVatNo()+"'                                                                                                                                                                  "+
					"	 and c.date_currunt_date between     '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' ) X	                                                                                                                                            "+
					"	 union                                                                                                                                                                                                                                                              "+
					"	 select distinct	x.dt_time,false as flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,                                                                                                                                                                                                      "+
					"	 x.vch_tank_name as tank_nm ,                                                                                                                                                                                                                                       "+
					"	 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                                                         "+
					"	 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                                          "+
					"	 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                           "+
					"	 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                                                        "+
					"	 (select distinct   b.dt_time , false as flg,b.txn_date as date ,'Transfer To CL Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                     "+
					"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
					"	 0 as cosum_bl,  0 as cosum_al,b.recieve_bl as recv_bl, b.recieve_al as recv_al,                                                                                                                                                                                                              "+
			//		"	 (((b.recieve_bl*b.recieve_strength)/100)+b.wastageal) as recv_al,                                                                                                                                                                                                                                           "+
					"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
					"	 from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                                                                                                                                         "+
					"	 where  a.storage_id=b.storage_id and  a.int_distillery_id=b.distillery_id                                                                                                                                                                                              "+
					"	 and a.int_distillery_id='"+act.getLoginUserId()+"'  and     b.storage_id='"+act.getVatNo()+"'      and   b.txn_date between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'    and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                "+
					//"	 union                                                                                                                                                                                                                                                              "+
					///"	 select distinct   b.dt_time , false as flg,b.txn_date as date ,'BottlingVatForCLAction' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                          "+
					//"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
					//"	 0 as cosum_bl,  0 as cosum_al, b.recieve_bl as recv_bl,                                                                                                                                                                                                            "+
					//"	 b.recieve_al as recv_al,                                                                                                                                                                                                                                           "+
					//"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
					//"	 from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                                                                                                                                         "+
					//"	 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                                              "+
					//"	 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.vat_no='"+act.getVatNo()+"'                                                                                                                                                                           "+
					//"	 and b.txn_date between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                  "+
					"	 union                                                                                                                                                                                                                                                              "+
					"	 select distinct   b.dt_time , false as flg,c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Premises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                     "+
					"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
					"	 (double_quantity_bl+double_wastg_bottling) as cosum_bl, (double_al+double_wastage_al) as cosum_al, 0 as recv_bl,                                                                                                                                                   "+
					"	 0 as recv_al,                                                                                                                                                                                                                                                      "+
					"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
					"	 from distillery.bottling_vat_cl a, distillery.bottling_dtl_cl_20_21 b ,distillery.bottling_master_cl_19_20 c                                                                                                                                                       "+
					"	 where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id and   b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id                                                                                                                   "+
					"	 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.blending_vat_id='"+act.getVatNo()+"'    and                                                                                                                                                           "+
					"	 c.date_currunt_date between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'         ) X		 )zz     order by zz.date  zz.dt_time, zz.dcription    "      ;                                                         
							
				System.out.println("---Bottling CL---" + selQuery);

			}
			// /System.out.println("======check=======" + selQuery);
			pst = con.prepareStatement(selQuery);

			rs = pst.executeQuery();
			// //System.out.println("======check=======" + rs.next());
			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("image", relativePath + File.separator);
				parameters.put("type", type);
				System.out.println("============" + relativePath);
				parameters.put("todate", act.getTodate());
				parameters.put("formdate", act.getFormdate());
				parameters.put("op_al",act.getOpening_al());
				parameters.put("op_bl",act.getOpening_bl());
				parameters.put("opDate",Utility.convertUtilDateToSQLDate(act.getFormdate()));
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader
						.loadObject(relativePath + File.separator
								+ "Tank_WiseReportDistillery.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "Tank_WiseReportDistillery" + "-" + n
								+ ".pdf");
				act.setPdfName("Tank_WiseReportDistillery" + "-" + n + ".pdf");
				act.setPrintFlag(true);
			} else {
				// act.setPrintFlag(false);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found!!", "No Data Found!!"));
				act.setPrintFlag(false);

			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
	
	
	//----------------------------------------------

	// ---------------------------------------------------------------------------------------

	/*public void printReport(TankWise_StcoklistAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "Distillery" + File.separator + "jasper";
		// / \doc\ExciseUp\Distillery\jasper
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "Distillery" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String selQuery = null;
		String type = null;

		try {
			con = ConnectionToDataBase.getConnection();
			if (act.getRadio().equalsIgnoreCase("SV")) {
				type="Spirit Vat";
				selQuery =
						
					
		"				select zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                                                                                                                                             "+
		"				  zz.bal_bl, zz.bal_al from                                                                                                                                                                                                                                                                                 "+
		"				  (select  x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                         "+
		"				  coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl, coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,                                                                                                                               "+
		"				  (coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                                                                                                                   "+
		"				  (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                                                                                               "+
		"				  (select distinct v.txn_date as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                                                                                                                    "+
		"				  a.int_distilleri_id,a.vch_tank_name ,b.dob_qunty_transfer_bl as cosum_bl,b.dob_qunty_transfer_al as cosum_al,                                                                                                                                                                                             "+
		"				  b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                                                                                 "+
		"				  v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                                                                                                                     "+
		"				  from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b,                                                                                                                                                                                                   "+
		"				  distillery.vat_wastage v  where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                                                                                                  "+
		"				  and b.int_from_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.int_from_vat_id='"+act.getVatNo()+"'                                                                                                                                  "+
		"				  and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F'  and v.txn_date between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                             "+
		"				  union                                                                                                                                                                                                                                                                                                     "+
		///"				  select distinct b.date_created as date, 'Prepration Of FL Blend ' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                                                                                                                                                "+
		//"				  a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                                                                                             "+
		//"				  b.produced_bl as recv_bl, b.produced_al as recv_al,                                                                                                                                                                                                                                                       "+
		///"				  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                   "+
		//"				  from distillery.distillery_spirit_store_detail a,distillery.foreign_liquor_blending b                                                                                                                                                                                                                     "+
		//"				  where  a.int_id=b.int_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                     "+
		//"				  and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.int_vat_no='"+act.getVatNo()+"'  and    b.date_created between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                "+
		//"				  union                                                                                                                                                                                                                                                                                                     "+
		"				  select distinct b.date_created as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                                                                                                                                "+
		"				  b.sprit_taken as cosum_bl,b.sprit_taken_al as cosum_al,                                                                                                                                                                                                                                                   "+
		"				  0 as recv_bl,0 as recv_al,                                                                                                                                                                                                                                                                                "+
		"				  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                   "+
		"				  from distillery.distillery_spirit_store_detail a,distillery.country_liquor_blending b                                                                                                                                                                                                                     "+
		"				  where  a.int_id=b.int_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                     "+
		"				  and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.int_vat_no='"+act.getVatNo()+"'   and    b.date_created between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                "+
		"				  union                                                                                                                                                                                                                                                                                                     "+
		"				  select distinct b.created_date as date, 'ReceivedFromPlantAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                                                                                                                                    "+
		"				  0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                                                                                                              "+
		"				  b.quantity_bl as recv_bl, b.quantity_al as recv_al,                                                                                                                                                                                                                                                       "+
		"				  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                   "+
		"				  from distillery.distillery_spirit_store_detail a,distillery.received_from_plant_master b                                                                                                                                                                                                                  "+
		"				  where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                                                                                                                                     "+
		"				  and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'    and  b.created_date between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                     "+
		"				  union                                                                                                                                                                                                                                                                                                     "+
		"				  select distinct b.created_date as date, 'ReDistillationOfSpiritAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name                                                                                                                                                 "+
		"				  ,b.trnsfer_bl as cosum_bl,b.trnsfer_al as cosum_al,                                                                                                                                                                                                                                                       "+
		"				  0 as recv_bl,0 as recv_al,                                                                                                                                                                                                                                                                                "+
		"				  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                   "+
		"				  from distillery.distillery_spirit_store_detail a,distillery.re_distillation_of_spirit_master b                                                                                                                                                                                                            "+
		"				  where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                                                                                                                                           "+
		"				  and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'   and  b.created_date between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                      "+
		"				  union                                                                                                                                                                                                                                                                                                     "+
		"				  select distinct b.date as date, 'REMOVAL OF SPIRIT FOR DENATURING' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                                                                                                                                  "+
		"				  (b.spirit_vat_quantitybl - b.to_qty_as_pr_dpt_bl) as cosum_bl,                                                                                                                                                                                                                                            "+
		"				  (b.spirit_vat_quantityal-b.to_qty_as_pr_dpt_al)  as cosum_al,                                                                                                                                                                                                                                             "+
		"				  0 as recv_bl, 0 as recv_al,                                                                                                                                                                                                                                                                               "+
		"				  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                   "+
		"				  from distillery.distillery_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                                                                                                                                                "+
		"				  distillery.vat_wastage v  where  a.int_id=b.spirit_vatno and  a.int_distilleri_id=b.int_distillery_code                                                                                                                                                                                                   "+
		"				  and b.spirit_vatno= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                                                                  "+
		"				  and     b.spirit_vatno='"+act.getVatNo()+"'  and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F' and     b.date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                "+
		"				  union                                                                                                                                                                                                                                                                                                     "+
		"				  select distinct b.dt_created as date, 'SpiritExport' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                     "+
		"				  b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                                                                                 "+
		"				  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                   "+
		"				  from distillery.distillery_spirit_store_detail a,distillery.export_spirit b                                                                                                                                                                                                                               "+
		"				  where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                     "+
		"				  and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and     b.dt_created between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'" +                                                                                                                                           
		"              union                                                                                                                                                                                                                                                                                                        "+
		"              select distinct b.dt_created as date, 'BondAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                          "+
		"             b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                                                                                     "+
		"             0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                       "+
		"             from distillery.distillery_spirit_store_detail a,distillery.spirit_import b                                                                                                                                                                                                                                   "+
		"             where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                              "+
		"             and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vatno='"+act.getVatNo()+"'  and     b.dt_created between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                                                                           "+
		"                                                                                                                                                                                                                                                                                                                           "+
		"                                                                                                                                                                                                                                                                                                                           "+
        "                                                                                                                                                                                                                                                                                                                           "+
		"                                                                                                                                                                                                                                                                                                                           "+
		"               union                                                                                                                                                                                                                                                                                                       "+
		"               select distinct b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                                                                                                                                "+
		"               qty_transfr_bl as cosum_bl,qty_transfr_al as cosum_al,                                                                                                                                                                                                                                                      "+
		"             0 as recv_bl, 0 as recv_al,                                                                                                                                                                                                                                                                                   "+
		"             0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                       "+
		"             from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b                                                                                                                                                                                                                   "+
		"             where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                        "+
		"             and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.from_vat_no='"+act.getVatNo()+"'  and     b.tansfer_dt between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                                                                      "+
		"                                                                                                                                                                                                                                                                                                                           "+
		"                union                                                                                                                                                                                                                                                                                                      "+
		"                                                                                                                                                                                                                                                                                                                           "+
		"               select distinct b.dt_created as date, 'ENA Gatepass Receiving With In State' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                            "+
		"             b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                                                                                     "+
		"             0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                       "+
		"             from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                                                                                                                                          "+
		"             where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                         "+
		"             and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and     b.dt_created between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                                                                       "+
		"                                                                                                                                                                                                                                                                                                                           "+
		"             union                                                                                                                                                                                                                                                                                                         "+
		"               select distinct b.dt_created as date, 'Import of ENA' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                        "+
		"             b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                                                                                     "+
		"             0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                       "+
		"             from distillery.distillery_spirit_store_detail a,distillery.spirit_import b                                                                                                                                                                                                                                   "+
		"             where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                              "+
		"             and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vatno='"+act.getVatNo()+"'  and     b.dt_created between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                                                                            "+
		"                                                                                                                                                                                                                                                                                                                           "+
		"                                                                                                                                                                                                                                                                                                                           "+
		"             union                                                                                                                                                                                                                                                                                                         "+
		"                                                                                                                                                                                                                                                                                                                           "+
		"               select distinct b.dt_created as date, 'Spirit Purchased In ( State )' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                      "+
		"             b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                                                                                     "+
		"             0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                       "+
		"             from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                                                                                                                                          "+
		"             where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                         "+
		"             and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and     b.dt_created between   '2020-07-15'and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                                                                       "+
				") X                                                                                                                                           "+
		"				  union                                                                                                                                                                                                                                                                                                     "+       
		"				  select   x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                         "+
		"				  coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl, coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,                                                                                                                               "+
		"				  (coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                                                                                                                   "+
		"				  (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                                                                                               "+
		"				  (select distinct v.txn_date as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                                                                                                                    "+
		"				  a.int_distilleri_id,a.vch_tank_name ,b.dob_qunty_transfer_bl as cosum_bl,b.dob_qunty_transfer_al as cosum_al,                                                                                                                                                                                             "+
		"				  b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                                                                                 "+
		"				  v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                                                                                                                     "+
		"				  from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b,                                                                                                                                                                                                   "+
		"				  distillery.vat_wastage v  where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                                                                                                  "+
		"				  and b.int_from_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.int_from_vat_id='"+act.getVatNo()+"'                                                                                                                                  "+
		"				  and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F'  and v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                   "+
		"				  union                                                                                                                                                                                                                                                                                                     "+
	//	"				  select distinct b.date_created as date, 'Prepration Of FL Blend ' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                                                                                                                                                "+
		//"				  a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                                                                                             "+
		//"				  b.produced_bl as recv_bl, b.produced_al as recv_al,                                                                                                                                                                                                                                                       "+
		//"				  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                   "+
		//"				  from distillery.distillery_spirit_store_detail a,distillery.foreign_liquor_blending b                                                                                                                                                                                                                     "+
		//"				  where  a.int_id=b.int_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                     "+
		//"				  and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.int_vat_no='"+act.getVatNo()+"'  and    b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                      "+
		//"				  union                                                                                                                                                                                                                                                                                                     "+
		"				  select distinct b.date_created as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                                                                                                                                "+
		"				  b.sprit_taken as cosum_bl,b.sprit_taken_al as cosum_al,                                                                                                                                                                                                                                                   "+
		"				  0 as recv_bl,0 as recv_al,                                                                                                                                                                                                                                                                                "+
		"				  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                   "+
		"				  from distillery.distillery_spirit_store_detail a,distillery.country_liquor_blending b                                                                                                                                                                                                                     "+
		"				  where  a.int_id=b.int_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                     "+
		"				  and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.int_vat_no='"+act.getVatNo()+"'   and    b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                      "+
		"				  union                                                                                                                                                                                                                                                                                                     "+
		"				  select distinct b.created_date as date, 'ReceivedFromPlantAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                                                                                                                                    "+
		"				  0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                                                                                                              "+
		"				  b.quantity_bl as recv_bl, b.quantity_al as recv_al,                                                                                                                                                                                                                                                       "+
		"				  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                   "+
		"				  from distillery.distillery_spirit_store_detail a,distillery.received_from_plant_master b                                                                                                                                                                                                                  "+
		"				  where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                                                                                                                                     "+
		"				  and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'    and  b.created_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                           "+
		"				  union                                                                                                                                                                                                                                                                                                     "+
		"				  select distinct b.created_date as date, 'ReDistillationOfSpiritAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name                                                                                                                                                 "+
		"				  ,b.trnsfer_bl as cosum_bl,b.trnsfer_al as cosum_al,                                                                                                                                                                                                                                                       "+
		"				  0 as recv_bl,0 as recv_al,                                                                                                                                                                                                                                                                                "+
		"				  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                   "+
		"				  from distillery.distillery_spirit_store_detail a,distillery.re_distillation_of_spirit_master b                                                                                                                                                                                                            "+
		"				  where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                                                                                                                                           "+
		"				  and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'   and  b.created_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                            "+
		"				  union                                                                                                                                                                                                                                                                                                     "+
		"				  select distinct b.date as date, 'REMOVAL OF SPIRIT FOR DENATURING' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                                                                                                                                  "+
		"				  (b.spirit_vat_quantitybl - b.to_qty_as_pr_dpt_bl) as cosum_bl,                                                                                                                                                                                                                                            "+
		"				  (b.spirit_vat_quantityal-b.to_qty_as_pr_dpt_al)  as cosum_al,                                                                                                                                                                                                                                             "+
		"				  0 as recv_bl, 0 as recv_al,                                                                                                                                                                                                                                                                               "+
		"				  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                   "+
		"				  from distillery.distillery_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                                                                                                                                                "+
		"				  distillery.vat_wastage v  where  a.int_id=b.spirit_vatno and  a.int_distilleri_id=b.int_distillery_code                                                                                                                                                                                                   "+
		"				  and b.spirit_vatno= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                                                                  "+
		"				  and     b.spirit_vatno='"+act.getVatNo()+"'  and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F' and     b.date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                      "+
		"				  union                                                                                                                                                                                                                                                                                                     "+
		"				  select distinct b.dt_created as date, 'SpiritExport' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                     "+
		"				  b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                                                                                 "+
		"				  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                   "+
		"				  from distillery.distillery_spirit_store_detail a,distillery.export_spirit b                                                                                                                                                                                                                               "+
		"				  where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                     "+
		"				  and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and     b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                      "+   
		   "              union                                                                                                                                                                                                                                                                                                        "+
		   "              select distinct b.dt_created as date, 'BondAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                          "+
		    "           b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                                                                                      "+
		    "           0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                        "+
		    "           from distillery.distillery_spirit_store_detail a,distillery.spirit_import b                                                                                                                                                                                                                                    "+
		    "           where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                               "+
		    "           and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vatno='"+act.getVatNo()+"'  and     b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                                                                                             "+
		    "                                                                                                                                                                                                                                                                                                                          "+
		    "                                                                                                                                                                                                                                                                                                                          "+
            "                                                                                                                                                                                                                                                                                                                          "+
		    "                                                                                                                                                                                                                                                                                                                          "+
		    "             union                                                                                                                                                                                                                                                                                                        "+
		    "             select distinct b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                                                                                                                                 "+
		    "             qty_transfr_bl as cosum_bl,qty_transfr_al as cosum_al,                                                                                                                                                                                                                                                       "+
		    "           0 as recv_bl, 0 as recv_al,                                                                                                                                                                                                                                                                                    "+
		    "           0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                        "+
		    "           from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b                                                                                                                                                                                                                    "+
		    "           where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                         "+
		    "           and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.from_vat_no='"+act.getVatNo()+"'  and     b.tansfer_dt between  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                                                                                       "+
		    "                                                                                                                                                                                                                                                                                                                          "+
		    "              union                                                                                                                                                                                                                                                                                                       "+
		    "                                                                                                                                                                                                                                                                                                                          "+
		    "             select distinct b.dt_created as date, 'ENA Gatepass Receiving With In State' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                             "+
		    "           b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                                                                                      "+
		    "           0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                        "+
		    "           from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                                                                                                                                           "+
		    "           where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                          "+
		    "           and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and     b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                                                                                        "+
		    "                                                                                                                                                                                                                                                                                                                          "+
		    "           union                                                                                                                                                                                                                                                                                                          "+
		    "             select distinct b.dt_created as date, 'Import of ENA' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                         "+
		    "           b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                                                                                      "+
		    "           0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                        "+
		    "           from distillery.distillery_spirit_store_detail a,distillery.spirit_import b                                                                                                                                                                                                                                    "+
		    "           where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                               "+
		    "           and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vatno='"+act.getVatNo()+"'  and     b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                                                                                             "+
		    "                                                                                                                                                                                                                                                                                                                          "+
		    "                                                                                                                                                                                                                                                                                                                          "+
		    "           union                                                                                                                                                                                                                                                                                                          "+
		    "                                                                                                                                                                                                                                                                                                                          "+
		    "             select distinct b.dt_created as date, 'Spirit Purchased In ( State )' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                       "+
		    "           b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                                                                                      "+
		    "           0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                                                                        "+
		    "           from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                                                                                                                                           "+
		    "           where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                                                                          "+
		    "           and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and     b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                                                                                        "+
				"        ) X  )zz     order by zz.date                          ";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
					                                                                                                                                                                                                                                                                                                                   
					                                                                                                                                                                                                                                                                                                                   
//--------------------------------------------						                                                                                                                                                                                                                                                                   
						                                                                                                                                                                                                                                                                                                               
						                                                                                                                                                                                                                                                                                                               
			                                                                                                                                                                                                                                                                                                                         
				"  select                                                                                                                                                                                            "
						+ "  x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                               "
						+ " coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl, coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                         "
						+ "  (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                           "
						+ " (select distinct v.txn_date as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                             "
						+ " a.int_distilleri_id,a.vch_tank_name ,b.dob_qunty_transfer_bl as cosum_bl,b.dob_qunty_transfer_al as cosum_al,                                                                                      "
						+ " b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                          "
						+ "  v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                             "
						+ "  from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b,                                                                                           "
						+ "  distillery.vat_wastage v  where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                          "
						+ "  and b.int_from_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "' and     b.int_from_vat_id='"+act.getVatNo()+"'                                                                     "
						+ "  and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F'  and v.txn_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                                                                                "
						+ "  union                                                                                                                                                                                             "
						+ "  select distinct b.date_created as date, 'Prepration Of FL Blend ' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                                        "
						+ "  a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                     "
						+ "  b.produced_bl as recv_bl, b.produced_al as recv_al,                                                                                                                                               "
						+ "  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                           "
						+ "  from distillery.distillery_spirit_store_detail a,distillery.foreign_liquor_blending b                                                                                                             "
						+ "   where  a.int_id=b.int_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                            "
						+ " and a.int_distilleri_id='"
						+ act.getLoginUserId() 
						+ "'   and     b.int_vat_no='"+act.getVatNo()+"'  and    b.date_created between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                                                                               "
						+ " 			                                                                                                                                                                                         "
						+ "  union                                                                                                                                                                                             "
						+ "  select distinct b.date_created as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                        "
						+ "  b.sprit_taken as cosum_bl,b.sprit_taken_al as cosum_al,                                                                                                                                           "
						+ "  0 as recv_bl,0 as recv_al,                                                                                                                                                                        "
						+ "  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                           "
						+ "  from distillery.distillery_spirit_store_detail a,distillery.country_liquor_blending b                                                                                                             "
						+ " where  a.int_id=b.int_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                              "
						+ "  and a.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "'  and     b.int_vat_no='"+act.getVatNo()+"'   and    b.date_created between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                                                                               "
						+ " 			                                                                                                                                                                                         "
						+ " union                                                                                                                                                                                              "
						+ " select distinct b.created_date as date, 'ReceivedFromPlantAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                             "
						+ " 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                       "
						+ " b.quantity_bl as recv_bl, b.quantity_al as recv_al,                                                                                                                                                "
						+ "  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                           "
						+ "  from distillery.distillery_spirit_store_detail a,distillery.received_from_plant_master b                                                                                                          "
						+ "  where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                             "
						+ " and a.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "'  and     b.vat_id='"+act.getVatNo()+"'    and  b.created_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                                                                                "
						+ " 			                                                                                                                                                                                         "
						+ "  union                                                                                                                                                                                             "
						+ "  select distinct b.created_date as date, 'ReDistillationOfSpiritAction' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name                                         "
						+ "  ,b.trnsfer_bl as cosum_bl,b.trnsfer_al as cosum_al,                                                                                                                                               "
						+ " 0 as recv_bl,0 as recv_al,                                                                                                                                                                         "
						+ "  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                           "
						+ " from distillery.distillery_spirit_store_detail a,distillery.re_distillation_of_spirit_master b                                                                                                     "
						+ "  where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                                   "
						+ "  and a.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "'  and     b.vat_id='"+act.getVatNo()+"'   and  b.created_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                                                                                 "
						+ " 			                                                                                                                                                                                         "
						+ "  union                                                                                                                                                                                             "
						+ "  select distinct b.date as date, 'REMOVAL OF SPIRIT FOR DENATURING' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                          "
						+ "  (b.spirit_vat_quantitybl - b.to_qty_as_pr_dpt_bl) as cosum_bl,                                                                                                                                    "
						+ "  (b.spirit_vat_quantityal-b.to_qty_as_pr_dpt_al)  as cosum_al,                                                                                                                                     "
						+ "  0 as recv_bl, 0 as recv_al,                                                                                                                                                                       "
						+ "   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                          "
						+ " from distillery.distillery_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                                         "
						+ "  distillery.vat_wastage v  where  a.int_id=b.spirit_vatno and  a.int_distilleri_id=b.int_distillery_code                                                                                           "
						+ "   and b.spirit_vatno= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "'                                                                         "
						+ "   and     b.spirit_vatno='"+act.getVatNo()+"'  and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F' and     b.date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                                                                                  "
						+ "   union                                                                                                                                                                                            "
						+ "   select distinct b.dt_created as date, 'SpiritExport' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                            "
						+ "  b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                         "
						+ "  0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                           "
						+ "   from distillery.distillery_spirit_store_detail a,distillery.export_spirit b                                                                                                                      "
						+ "  where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                             "
						+ " and a.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "'   and     b.spirit_vat='"+act.getVatNo()+"'  and     b.dt_created between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "') X   order by date                                                      ";

			System.out.println("---Blending SV---" + selQuery);

			} else if (act.getRadio().equalsIgnoreCase("DV")) {
			    
				type="Denatured Spirit  Vat";
  
				selQuery =
						
						

                                                                                                                                                                                                                                                              
" select zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                                                                                             "+
"   zz.bal_bl, zz.bal_al from                                                                                                                                                                                                                                 "+
" (select distinct 	x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,                                                                                                                       "+
" coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                              	                                                                                                      "+
" 	coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                                 "+
" 	coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                  "+
" 	(coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                                               "+
" 	(select distinct v.txn_date as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                                                                                               "+
" 	,a.vch_tank_name,dob_qunty_transfer_bl                                                                                                                                                                                                                    "+
" 	as cosum_bl,                                                                                                                                                                                                                                              "+
" 	dob_qunty_transfer_al  as cosum_al, b.bal_in_source_bl  as recv_bl, b.bal_in_source_al as recv_al,                                                                                                                                                        "+
" 	v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                                                                                   "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b,                                                                                                                               "+
" 	distillery.vat_wastage v  where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                                                  "+
" 	and b.int_from_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                 "+
" 	and     b.int_from_vat_id='"+act.getVatNo()+"'  and v.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                      "+
" 	union		                                                                                                                                                                                                                                              "+
" 	select distinct b.dt_created as date,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,                                                                                                               "+
" 	a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                                                                           "+
" 	0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                "+
" 	0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                   "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b                                                                                                                                                                     "+
" 	where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                          "+
" 	and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vatno='"+act.getVatNo()+"'    and b.dt_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                        "+
" 	union                                                                                                                                                                                                                                                     "+
" 	select distinct b.dt_crdt as date ,'REMOVAL OF DENATURED SPIRIT ' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,                                                                                                                  "+
" 	a.vch_tank_name,int_issued_quantity_bl  as cosum_bl,                                                                                                                                                                                                      "+
" 	int_issued_quantity_al  as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                                                                            "+
" 	0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                  "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.denatured_spirits_to_issuevat b                                                                                                                                                     "+
" 	where  a.int_id=b.int_denatured_vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                                                                             "+
" 	and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.int_denatured_vat_id='"+act.getVatNo()+"'   and b.dt_crdt between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                              "+
" 	union                                                                                                                                                                                                                                                     "+
" 	select distinct b.dt_created as date,'ENA Gatepass Receiving With In State ' as dcription,a.openingal,a.openingbl, a.int_id,a.int_distilleri_id,                                                                                                       "+
" 	a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                                                                           "+
" 	0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                "+
" 	0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                 "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                                                                            "+
" 	where  a.int_id=b.int_id and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                         "+
" 	and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.int_id='"+act.getVatNo()+"'  and b.dt_created  between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                        "+
" 	union                                                                                                                                                                                                                                                     "+
" 	select distinct v.txn_date as date,'BondAction' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,a.vch_tank_name,0 as cosum_bl,                                                                                                         "+
" 	0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                "+
" 	0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                   "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b,                                                                                                                                                                    "+
" 	distillery.vat_wastage v  where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                "+
" 	and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.vatno='"+act.getVatNo()+"'   and v.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                            "+
" 	union                                                                                                                                                                                                                                                     "+
" 	select distinct v.txn_date as date,'REMOVAL OF SPIRIT FOR DENATURING' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                                                                                    "+
" 	a.int_distilleri_id,a.vch_tank_name,quantitytakenfrdenbl  as cosum_bl,                                                                                                                                                                                    "+
" 	quantitytakenfrdenal  as cosum_al, (b.spirit_vat_quantitybl-b.from_qty_as_pr_dpt_bl)  as recv_bl,                                                                                                                                                         "+
" 	(b.spirit_vat_quantityal-b.from_qty_as_pr_dpt_al)  as recv_al,                                                                                                                                                                                            "+
" 	v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                                                                                   "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                                                                                      "+
" 	distillery.vat_wastage v  where  a.int_id=b.spirit_vatno and  a.int_distilleri_id=b.int_distillery_code                                                                                                                                                   "+
" 	and b.spirit_vatno= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                  "+
" 	and     b.spirit_vatno='"+act.getVatNo()+"'  and v.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' 			                                                                            "+
" union "+
" 	select distinct v.txn_date as date,'Spirit Purchased In ( State )' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                                                                                    "+
" 	a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                                    "+
" 	0 as cosum_al, b.net_bl  as recv_bl,                                                                                                                                                         "+
" 	b.net_al  as recv_al,                                                                                                                                                                                            "+
" 	v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                                                                                   "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b,                                                                                                                                                      "+
" 	distillery.vat_wastage v  where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.int_distillery_code                                                                                                                                                   "+
" 	and b.denatured_vat= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                  "+
" 	and     b.denatured_vat='"+act.getVatNo()+"'  and v.txn_date between   '2020-07-15'   and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' "+  
" union "+
" 	select distinct v.txn_date as date,'Spirit Purchased In ( State )' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                                                                                    "+
" 	a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                                    "+
" 	0 as cosum_al, b.net_bl  as recv_bl,                                                                                                                                                         "+
" 	b.net_al  as recv_al,                                                                                                                                                                                            "+
" 	v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                                                                                   "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b,                                                                                                                                                      "+
" 	distillery.vat_wastage v  where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.int_distillery_code                                                                                                                                                   "+
" 	and b.denatured_vat= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                  "+
" 	and     b.denatured_vat='"+act.getVatNo()+"'  and v.txn_date between   '2020-07-15'   and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  "+

"				 union                                                                                                                                                                                                                                              "+
		"				 select distinct b.date_created as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                                 "+
		"				 a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                                           "+
		"				 ,b.produced_bl as cosum_bl,b.produced_al as cosum_al,                                                                                                                                                                                              "+
		"				 0 as recv_bl,0   as recv_al,                                                                                                                                                                                                                       "+
		"				v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                                                                                                        "+
		"				 from distillery.spirit_for_bottling_cl a,distillery.transferspirit_to_cl_blending b  ,distillery.vat_wastage v                                                                                                                                                                    "+
		"				 where  a.storage_id=b.to_vat_no and  a.int_distillery_id=b.distillery_id and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int and                                                                                                                                                                         "+
		"				 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                                 "+
		"				 and     b.to_vat_no='"+act.getVatNo()+"' 	 and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  )x                                                                                           "+



"                                                                                                                                                                                                                                                             "+
" union                                                                                                                                                                                                                                                       "+
"                                                                                                                                                                                                                                                             "+
"                                                                                                                                                                                                                                                             "+
" select distinct 	x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,                                                                                                                       "+
" coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                              	                                                                                                      "+
" 	coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                                 "+
" 	coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                  "+
" 	(coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                                               "+
" 	(select distinct v.txn_date as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                                                                                               "+
" 	,a.vch_tank_name,dob_qunty_transfer_bl                                                                                                                                                                                                                    "+
" 	as cosum_bl,                                                                                                                                                                                                                                              "+
" 	dob_qunty_transfer_al  as cosum_al, b.bal_in_source_bl  as recv_bl, b.bal_in_source_al as recv_al,                                                                                                                                                        "+
" 	v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                                                                                   "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b,                                                                                                                               "+
" 	distillery.vat_wastage v  where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                                                  "+
" 	and b.int_from_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                 "+
" 	and     b.int_from_vat_id='"+act.getVatNo()+"'  and v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                           "+
" 	union		                                                                                                                                                                                                                                              "+
" 	select distinct b.dt_created as date,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,                                                                                                               "+
" 	a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                                                                           "+
" 	0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                "+
" 	0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                   "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b                                                                                                                                                                     "+
" 	where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                          "+
" 	and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vatno='"+act.getVatNo()+"'    and b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'             "+
" 	union                                                                                                                                                                                                                                                     "+
" 	select distinct b.dt_crdt as date ,'REMOVAL OF DENATURED SPIRIT ' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,                                                                                                                  "+
" 	a.vch_tank_name,int_issued_quantity_bl  as cosum_bl,                                                                                                                                                                                                      "+
" 	int_issued_quantity_al  as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                                                                            "+
" 	0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                  "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.denatured_spirits_to_issuevat b                                                                                                                                                     "+
" 	where  a.int_id=b.int_denatured_vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                                                                             "+
" 	and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.int_denatured_vat_id='"+act.getVatNo()+"'   and b.dt_crdt between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   "+
" 	union                                                                                                                                                                                                                                                     "+
" 	select distinct b.dt_created as date,'ENA Gatepass Receiving With In State ' as dcription,a.openingal,a.openingbl, a.int_id,a.int_distilleri_id,                                                                                                       "+
" 	a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                                                                           "+
" 	0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                "+
" 	0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                 "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                                                                            "+
" 	where  a.int_id=b.int_id and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                                         "+
" 	and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.int_id='"+act.getVatNo()+"'  and b.dt_created  between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'             "+
" 	union                                                                                                                                                                                                                                                     "+
" 	select distinct v.txn_date as date,'BondAction' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,a.vch_tank_name,0 as cosum_bl,                                                                                                         "+
" 	0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                                                                                "+
" 	0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                                   "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b,                                                                                                                                                                    "+
" 	distillery.vat_wastage v  where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                                                "+
" 	and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.vatno='"+act.getVatNo()+"'   and v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                 "+
" 	union                                                                                                                                                                                                                                                     "+
" 	select distinct v.txn_date as date,'REMOVAL OF SPIRIT FOR DENATURING' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                                                                                    "+
" 	a.int_distilleri_id,a.vch_tank_name,quantitytakenfrdenbl  as cosum_bl,                                                                                                                                                                                    "+
" 	quantitytakenfrdenal  as cosum_al, (b.spirit_vat_quantitybl-b.from_qty_as_pr_dpt_bl)  as recv_bl,                                                                                                                                                         "+
" 	(b.spirit_vat_quantityal-b.from_qty_as_pr_dpt_al)  as recv_al,                                                                                                                                                                                            "+
" 	v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                                                                                   "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                                                                                      "+
" 	distillery.vat_wastage v  where  a.int_id=b.spirit_vatno and  a.int_distilleri_id=b.int_distillery_code                                                                                                                                                   "+
" 	and b.spirit_vatno= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                  "+
" 	and     b.spirit_vatno='"+act.getVatNo()+"'  and v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' " +
"   union "+
" 	select distinct v.txn_date as date,'Spirit Purchased In ( State )' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                                                                                    "+
" 	a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                                    "+
" 	0 as cosum_al, b.net_bl  as recv_bl,                                                                                                                                                         "+
" 	b.net_al  as recv_al,                                                                                                                                                                                            "+
" 	v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                                                                                   "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b,                                                                                                                                                      "+
" 	distillery.vat_wastage v  where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.int_distillery_code                                                                                                                                                   "+
" 	and b.denatured_vat= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                  "+
" 	and     b.denatured_vat='"+act.getVatNo()+"'  and v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'    "+
"   union "+
" 	select distinct v.txn_date as date,'Import of ENA' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                                                                                    "+
" 	a.int_distilleri_id,a.vch_tank_name,0 as cosum_bl,                                                                                                                                                                                    "+
" 	0  as cosum_al, b.net_bl as recv_bl,                                                                                                                                                         "+
" 	b.net_al  as recv_al,                                                                                                                                                                                            "+
" 	v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                                                                                   "+
" 	from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b,                                                                                                                                                      "+
" 	distillery.vat_wastage v  where  a.int_id=b.denatured_spirit_id and  a.int_distilleri_id=b.distillery_id                                                                                                                                                   "+
" 	and b.denatured_spirit_id= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                  "+
" 	and     b.denatured_spirit_id='"+act.getVatNo()+"'  and v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   "+

        "				 union                                                                                                                                                                                                                                              "+
		"				 select distinct b.date_created as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                                 "+
		"				 a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                                           "+
		"				 ,b.produced_bl as cosum_bl,b.produced_al as cosum_al,                                                                                                                                                                                              "+
		"				 0 as recv_bl,0   as recv_al,                                                                                                                                                                                                                       "+
		"				v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                                                                                                        "+
		"				 from distillery.spirit_for_bottling_cl a,distillery.transferspirit_to_cl_blending b  ,distillery.vat_wastage v                                                                                                                                                                    "+
		"				 where  a.storage_id=b.to_vat_no and  a.int_distillery_id=b.distillery_id and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int and                                                                                                                                                                         "+
		"				 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                                 "+
		"				 and     b.to_vat_no='"+act.getVatNo()+"' 	 and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                            "+


"   )X )zz     order by zz.date                                  ";                                         
		
	
//--------------------------------------------------
						
						"	 select distinct 	x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                              "
						+ "		coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                                                                              "
						+ "	 				coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                                   "
						+ "	 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                                                   "
						+ "	 (select distinct v.txn_date as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                                                               "
						+ "	  ,a.vch_tank_name,dob_qunty_transfer_bl                                                                                                                                                                                  "
						+ "	 as cosum_bl,                                                                                                                                                                                                             "
						+ "	  dob_qunty_transfer_al  as cosum_al, b.bal_in_source_bl  as recv_bl, b.bal_in_source_al as recv_al,                                                                                                                      "
						+ "	 v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                                                  "
						+ "	  from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b,                                                                                             "
						+ "	  distillery.vat_wastage v  where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                "
						+ "	  and b.int_from_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "'                                                                                               "
						+ "	 and     b.int_from_vat_id='"+act.getVatNo()+"'  and v.txn_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                                                                         "
						+ "	 union					                                                                                                                                                                                                  "
						+ "	 select distinct b.dt_created as date,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,                                                                               "
						+ "	  a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                                         "
						+ "	 0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                                               "
						+ "	 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                  "
						+ "	 from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b                                                                                                                                    "
						+ "	 where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                                         "
						+ "	 and a.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "'  and     b.vatno='"+act.getVatNo()+"'    and b.dt_created between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                  "
						+ "	 union 				                                                                                                                                                                                                      "
						+ "	 select distinct b.dt_crdt as date ,'REMOVAL OF DENATURED SPIRIT ' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,                                                                                  "
						+ "	  a.vch_tank_name,int_issued_quantity_bl  as cosum_bl,                                                                                                                                                                    "
						+ "	 int_issued_quantity_al  as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                                           "
						+ "	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                 "
						+ "	 from distillery.distillery_denatures_spirit_store_detail a,distillery.denatured_spirits_to_issuevat b                                                                                                                    "
						+ "	 where  a.int_id=b.int_denatured_vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                                            "
						+ "	 and a.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "' and     b.int_denatured_vat_id='"+act.getVatNo()+"'   and b.dt_crdt between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                      "
						+ "	    	union  		                                                                                                                                                                                                      "
						+ "	 select distinct b.dt_created as date,'ENA Gatepass Receiving With In State ' as dcription,a.openingal,a.openingbl, a.int_id,a.int_distilleri_id,                                                                       "
						+ "	  a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                                         "
						+ "	 0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                                               "
						+ "	 0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                "
						+ "	 from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                                           "
						+ "	   where  a.int_id=b.int_id and  a.int_distilleri_id=b.distillery_id                                                                                                                                                      "
						+ "	  and a.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "'  and     b.int_id='"+act.getVatNo()+"'  and b.dt_created  between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                 "
						+ "	  union                                                                                                                                                                                                                   "
						+ "	 select distinct v.txn_date as date,'BondAction' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,a.vch_tank_name,0 as cosum_bl,                                                                         "
						+ "	 0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                                               "
						+ "	 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                  "
						+ "	 from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b,                                                                                                                                   "
						+ "	  distillery.vat_wastage v  where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                              "
						+ "	 and a.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "' and     b.vatno='"+act.getVatNo()+"'   and v.txn_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                      "
						+ "	 	 union                                                                                                                                                                                                                "
						+ "	 select distinct v.txn_date as date,'REMOVAL OF SPIRIT FOR DENATURING' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                                                    "
						+ "	 a.int_distilleri_id,a.vch_tank_name,quantitytakenfrdenbl  as cosum_bl,                                                                                                                                                   "
						+ "	 	 quantitytakenfrdenal  as cosum_al, (b.spirit_vat_quantitybl-b.from_qty_as_pr_dpt_bl)  as recv_bl,                                                                                                                    "
						+ "	 	 (b.spirit_vat_quantityal-b.from_qty_as_pr_dpt_al)  as recv_al,                                                                                                                                                       "
						+ "	 v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                                                  "
						+ "	 from distillery.distillery_denatures_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                                                     "
						+ "	 distillery.vat_wastage v  where  a.int_id=b.spirit_vatno and  a.int_distilleri_id=b.int_distillery_code                                                                                                                  "
						+ "	 and b.spirit_vatno= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"
						+ act.getLoginUserId()
						+ "'                                                                                                 "
						+ "	 and     b.spirit_vatno='"+act.getVatNo()+"'  and v.txn_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "' )X			order by date                                                                ";

				
				System.out.println("---Blending DV---" + selQuery);

			}

			else if (act.getRadio().equalsIgnoreCase("BLENDFL")) {
				
				type="Blending VatFL";

				selQuery =

				"	    select zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                                                                        "+
				"	    zz.bal_bl, zz.bal_al from (select distinct 	x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                   "+
				"		coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                              "+
				"		coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                               "+
				"		(coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                            "+
				"		(select distinct b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                                                                         "+
				"		a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,dob_qunty_transfer_bl                                                                                                                  "+
				"		as cosum_bl,                                                                                                                                                                                                                           "+
				"		dob_qunty_transfer_al  as cosum_al, 0 as recv_bl,0 as recv_al,                                                                                                                                                                         "+
				"		0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                                                                                  "+
				"		where  a.storage_id=b.int_from_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                                                                     "+
				"		and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
				"		and     b.int_from_vat_id='"+act.getVatNo()+"'      and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                           "+
				"		union                                                                                                                                                                                                                                  "+
				"		select distinct b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                                                                          "+
				"		a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                      "+
				"		as cosum_bl,   0 as cosum_al,b.net_bl as recv_bl,b.net_al as recv_al,                                                                                                                                                                  "+
				"		0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                                                                                  "+
				"		where  a.storage_id=b.int_to_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                                                                       "+
				"		and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
				"		and     b.int_to_vat_id='"+act.getVatNo()+"'       and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                            "+
				"		union                                                                                                                                                                                                                                  "+
				"		select distinct b.txn_date as date ,'bottoling_of_vat ' as dcription,a.openingal,a.openingbl ,                                                                                                                                         "+
				"		a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,(b.recieve_bl-b.wastagebl)                                                                                                             "+
				"		as cosum_bl, (b.recieve_al-b.wastageal) as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                                         "+
				"		0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		from distillery.spirit_for_bottling a,distillery.master_bottoling_of_vat b                                                                                                                                                             "+
				"		where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                  "+
				"		and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
				"		and     b.vat_no='"+act.getVatNo()+"'      and b.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  	    	                                                                       "+
			    "		union                                                                                                                                                                                                                                  "+
					"		select distinct b.tansfer_dt as date ,'Transfer of spirit to FL Blending Vat ' as dcription,a.openingal,a.openingbl ,                                                                                                                                         "+
					"		a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                              "+
					"		as cosum_bl,  0 as cosum_al,qty_recv_bl as recv_bl,qty_recv_al as recv_al,                                                                                                                                                         "+
					"		0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
					"		from distillery.spirit_for_bottling a,distillery.transferspirit_to_fl_blending b                                                                                                                                                             "+
					"		where  a.storage_id=b.to_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                  "+
					"		and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
					"		and     b.to_vat_no='"+act.getVatNo()+"'      and b.tansfer_dt between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  )x		    	                                                                       "+
	      
                "                                                                                                                                                                                                                                              "+
				"	     union                                                                                                                                                                                                                                 "+
                "                                                                                                                                                                                                                                              "+
				"	    select distinct 	x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                           "+
				"		coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                              "+
				"		coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                               "+
				"		(coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                            "+
				"		(select distinct b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                                                                         "+
				"		a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,dob_qunty_transfer_bl                                                                                                                  "+
				"		as cosum_bl,                                                                                                                                                                                                                           "+
				"		dob_qunty_transfer_al  as cosum_al, 0 as recv_bl,0 as recv_al,                                                                                                                                                                         "+
				"		0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                                                                                  "+
				"		where  a.storage_id=b.int_from_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                                                                     "+
				"		and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
				"		and     b.int_from_vat_id='"+act.getVatNo()+"'      and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                "+
				"		union                                                                                                                                                                                                                                  "+
				"		select distinct b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                                                                          "+
				"		a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                      "+
				"		as cosum_bl,   0 as cosum_al,b.net_bl as recv_bl,b.net_al as recv_al,                                                                                                                                                                  "+
				"		0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                                                                                  "+
				"		where  a.storage_id=b.int_to_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                                                                       "+
				"		and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
				"		and     b.int_to_vat_id='"+act.getVatNo()+"'       and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                 "+
				"		union                                                                                                                                                                                                                                  "+
				"		select distinct b.txn_date as date ,'bottoling_of_vat ' as dcription,a.openingal,a.openingbl ,                                                                                                                                         "+
				"		a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,(b.recieve_bl-b.wastagebl)                                                                                                             "+
				"		as cosum_bl, (b.recieve_al-b.wastageal) as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                                         "+
				"		0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		from distillery.spirit_for_bottling a,distillery.master_bottoling_of_vat b                                                                                                                                                             "+
				"		where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                  "+
				"		and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
				"		and     b.vat_no='"+act.getVatNo()+"'      and b.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'" +
				"		union                                                                                                                                                                                                                                  "+
				"		select distinct b.tansfer_dt as date ,'Transfer of spirit to FL Blending Vat ' as dcription,a.openingal,a.openingbl ,                                                                                                                                         "+
				"		a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                             "+
				"		as cosum_bl, 0 as cosum_al,qty_recv_bl as recv_bl,qty_recv_al as recv_al,                                                                                                                                                         "+
				"		0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		from distillery.spirit_for_bottling a,distillery.transferspirit_to_fl_blending b                                                                                                                                                             "+
				"		where  a.storage_id=b.to_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                  "+
				"		and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
				"		and     b.to_vat_no='"+act.getVatNo()+"'      and b.tansfer_dt between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'" +
				"       )x	)zz     order by zz.date       	       ";                  		
						
						
					
	//--------------------------------------------------------------------------------------------------					

				"       select distinct 	x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                        "
						+ "     				coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                          "
						+ "       		coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                "
						+ "       (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                 "
						+ "       (select distinct b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                       "
						+ "        a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,dob_qunty_transfer_bl                                                              "
						+ "       as cosum_bl,                                                                                                                                                                        "
						+ "        dob_qunty_transfer_al  as cosum_al, 0 as recv_bl,0 as recv_al,                                                                                                                     "
						+ "       0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                             "
						+ "        from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                              "
						+ "         where  a.storage_id=b.int_from_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                "
						+ "        and a.int_distillery_id='"
						+ act.getLoginUserId()
						+ "'                                                                                                                                 "
						+ "   and     b.int_from_vat_id='"+act.getVatNo()+"'      and b.date_created between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                                "
						+ "        union                                                                                                                                                                              "
						+ "         select distinct b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                      "
						+ "        a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                  "
						+ "       as cosum_bl,   0 as cosum_al,b.net_bl as recv_bl,b.net_al as recv_al,                                                                                                               "
						+ "       0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                             "
						+ "        from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                              "
						+ "         where  a.storage_id=b.int_to_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                  "
						+ "        and a.int_distillery_id='"
						+ act.getLoginUserId()
						+ "'                                                                                                                                 "
						+ "  and     b.int_to_vat_id='"+act.getVatNo()+"'       and b.date_created between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                                "
						+ "        union                                                                                                                                                                              "
						+ "         select distinct b.txn_date as date ,'bottoling_of_vat ' as dcription,a.openingal,a.openingbl ,                                                                                     "
						+ "        a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,(b.recieve_bl-b.wastagebl)                                                         "
						+ "       as cosum_bl, (b.recieve_al-b.wastageal) as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                      "
						+ "       0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                             "
						+ "        from distillery.spirit_for_bottling a,distillery.master_bottoling_of_vat b                                                                                                         "
						+ "         where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                             "
						+ "        and a.int_distillery_id='"
						+ act.getLoginUserId()
						+ "'                                                                                                                                 "
						+ "   and     b.vat_no='"+act.getVatNo()+"'      and b.txn_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "' )x		order by date      	                        ";

				
				System.out.println("---BLENDFL---" + selQuery);

			} else if (act.getRadio().equalsIgnoreCase("BLENDCL")) {
				  
				type="Blending VatCL";
			 
				selQuery =
						
		"			    select zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,   "+
		"			    zz.bal_bl, zz.bal_al from                                                                                                                                                                                                                           "+
		"			    (select distinct x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm  ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                          "+
		"			    coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                           "+
		"			     coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                           "+
		"			     (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                                                                       "+
		"				 ( select distinct b.txn_date as date, 'Transfer To CL Bottling Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                                   "+
		"				 a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                                           "+
		"				 ,(b.recieve_bl-b.wastagebl) as cosum_bl,(b.recieve_al-b.wastageal) as cosum_al,                                                                                                                                                                    "+
		"				 0 as recv_bl,0 as recv_al,                                                                                                                                                                                                                         "+
		"				 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                            "+
		"				 from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                                                                                                   "+
		"				 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                              "+
		"				 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                                 "+
		"				 and     b.vat_no='"+act.getVatNo()+"' 	 and b.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                       "+
		"				 union                                                                                                                                                                                                                                              "+
		"				 select distinct b.txn_date as date, 'BottlingVatForCLAction' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                                         "+
		"				 a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                                           "+
		"				 ,(b.recieve_bl-b.wastagebl) as cosum_bl,(b.recieve_al-b.wastageal) as cosum_al,                                                                                                                                                                    "+
		"				 0 as recv_bl,0 as recv_al,                                                                                                                                                                                                                         "+
		"				 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                            "+
		"				 from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                                                                                                   "+
		"				 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                              "+
		"				 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                                 "+
		"				 and     b.vat_no='"+act.getVatNo()+"' 	 and b.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                       "+
		"				 union                                                                                                                                                                                                                                              "+
		"				 select distinct b.date_created as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                                "+
		"				 a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                                           "+
		"				 ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                                                      "+
		"				 b.produced_bl as recv_bl, b.produced_al  as recv_al,                                                                                                                                                                                               "+
		"				 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                            "+
		"				 from distillery.spirit_for_bottling_cl a,distillery.country_liquor_blending b                                                                                                                                                                      "+
		"				 where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                          "+
		"				 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                                 "+
		"				 and     b.int_vat_no='"+act.getVatNo()+"' 	 and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                               "+
		"				 union                                                                                                                                                                                                                                              "+
		"				 select distinct b.date_created as date, 'Prepration Of CL Blend ' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                                 "+
		"				 a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                                           "+
		"				 ,b.produced_bl as cosum_bl,b.produced_al as cosum_al,                                                                                                                                                                                              "+
		"				 0 as recv_bl,0   as recv_al,                                                                                                                                                                                                                       "+
		"				 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                            "+
		"				 from distillery.spirit_for_bottling_cl a,distillery.country_liquor_blending b                                                                                                                                                                      "+
		"				 where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                          "+
		"				 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                                 "+
		"				 and     b.int_vat_no='"+act.getVatNo()+"' 	 and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  )x                                                                                           "+
		
		
        "                                                                                                                                                                                                                                                                   "+
        "                                                                                                                                                                                                                                                                   "+
		"			     union                                                                                                                                                                                                                                              "+
		"			                                                                                                                                                                                                                                                        "+
		"			    select distinct x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm  ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                           "+
		"			coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                               "+
		"			coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                "+
		"			     (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                                                                       "+
		"				 ( select distinct b.txn_date as date, 'Transfer To CL Bottling Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                                   "+
		"				 a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                                           "+
		"				 ,(b.recieve_bl-b.wastagebl) as cosum_bl,(b.recieve_al-b.wastageal) as cosum_al,                                                                                                                                                                    "+
		"				 0 as recv_bl,0 as recv_al,                                                                                                                                                                                                                         "+
		"				 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                            "+
		"				 from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                                                                                                   "+
		"				 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                              "+
		"				 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                                 "+
		"				 and     b.vat_no='"+act.getVatNo()+"' 	 and b.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+ Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                           "+
		"				 union                                                                                                                                                                                                                                              "+
		"				 select distinct b.txn_date as date, 'BottlingVatForCLAction' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                                         "+
		"				 a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                                           "+
		"				 ,(b.recieve_bl-b.wastagebl) as cosum_bl,(b.recieve_al-b.wastageal) as cosum_al,                                                                                                                                                                    "+
		"				 0 as recv_bl,0 as recv_al,                                                                                                                                                                                                                         "+
		"				 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                            "+
		"				 from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                                                                                                   "+
		"				 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                              "+
		"				 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                                 "+
		"				 and     b.vat_no='"+act.getVatNo()+"' 	 and b.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+ Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                       "+
		"				 union                                                                                                                                                                                                                                              "+
		"				 select distinct b.date_created as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                                "+
		"				 a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                                           "+
		"				 ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                                                      "+
		"				 b.produced_bl as recv_bl, b.produced_al  as recv_al,                                                                                                                                                                                               "+
		"				 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                            "+
		"				 from distillery.spirit_for_bottling_cl a,distillery.country_liquor_blending b                                                                                                                                                                      "+
		"				 where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                          "+
		"				 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                                 "+
		"				 and     b.int_vat_no='"+act.getVatNo()+"' 	 and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+ Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                   "+
		"				 union                                                                                                                                                                                                                                              "+
		"				 select distinct b.date_created as date, 'Prepration Of CL Blend ' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                                 "+
		"				 a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                                           "+
		"				 ,b.produced_bl as cosum_bl,b.produced_al as cosum_al,                                                                                                                                                                                              "+
		"				 0 as recv_bl,0   as recv_al,                                                                                                                                                                                                                       "+
		"				 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                            "+
		"				 from distillery.spirit_for_bottling_cl a,distillery.country_liquor_blending b                                                                                                                                                                      "+
		"				 where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                          "+
		"				 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                                 "+
		"				 and     b.int_vat_no='"+act.getVatNo()+"' 	 and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+ Utility.convertUtilDateToSQLDate(act.getTodate())+"' )x    )zz     order by zz.date                   	";	
						
						
						
//------------------------------------------------------------------------
				"		 select distinct x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm                                                                                                 "
						+ "		 ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                        "
						+ "			coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                           "
						+ "			coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,   "
						+ "       (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                                                "
						+ "		 ( select distinct b.txn_date as date, 'Transfer To CL Bottling Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                       "
						+ "		  a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                              "
						+ "		  ,(b.recieve_bl-b.wastagebl) as cosum_bl,(b.recieve_al-b.wastageal) as cosum_al,                                                                                       "
						+ "		0 as recv_bl,0 as recv_al,                                                                                                                                              "
						+ "		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                "
						+ "		 from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                       "
						+ "		 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                  "
						+ "		 and a.int_distillery_id='"
						+ act.getLoginUserId()
						+ "'                                                                                                                     "
						+ "	 and     b.vat_no='"+act.getVatNo()+"' 	 and b.txn_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                        "
						+ "		 union                                                                                                                                                                  "
						+ "		    select distinct b.txn_date as date, 'BottlingVatForCLAction' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                          "
						+ "		  a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                              "
						+ "		  ,(b.recieve_bl-b.wastagebl) as cosum_bl,(b.recieve_al-b.wastageal) as cosum_al,                                                                                       "
						+ "		0 as recv_bl,0 as recv_al,                                                                                                                                              "
						+ "		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                "
						+ "		 from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                       "
						+ "		 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                  "
						+ "		 and a.int_distillery_id='"
						+ act.getLoginUserId()
						+ "'                                                                                                                     "
						+ "	and     b.vat_no='"+act.getVatNo()+"' 	 and b.txn_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                        "
						+ "		 union                                                                                                                                                                  "
						+ "		    select distinct b.date_created as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                 "
						+ "		  a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                              "
						+ "		  ,0 as cosum_bl,0 as cosum_al,                                                                                                                                         "
						+ "		b.produced_bl as recv_bl, b.produced_al  as recv_al,                                                                                                                    "
						+ "		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                "
						+ "		 from distillery.spirit_for_bottling_cl a,distillery.country_liquor_blending b                                                                                          "
						+ "		 where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                              "
						+ "		 and a.int_distillery_id='"
						+ act.getLoginUserId()
						+ "'                                                                                                                     "
						+ "	and     b.int_vat_no='"+act.getVatNo()+"' 	 and b.date_created between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                    "
						+ "		 union                                                                                                                                                                   "
						+ "		     select distinct b.date_created as date, 'Prepration Of CL Blend ' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                 "
						+ "		  a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                              "
						+ "		  ,b.produced_bl as cosum_bl,b.produced_al as cosum_al,                                                                                                                 "
			 			+ "		 0 as recv_bl,0   as recv_al,                                                                                                                                           "
						+ "		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                "
						+ "		 from distillery.spirit_for_bottling_cl a,distillery.country_liquor_blending b                                                                                          "
						+ "		 where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                              "
						+ "		 and a.int_distillery_id='"
						+ act.getLoginUserId()
						+ "'                                                                                                                     "
						+ "	and     b.int_vat_no='"+act.getVatNo()+"' 	 and b.date_created between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'  )x    order by date                  ";

				

				System.out.println("---BLEND=CL---" + selQuery);

			} else if (act.getRadio().equalsIgnoreCase("BOTFL")) {
				 
				type="Bottling VatFL";
				  

				selQuery =
						
				"	   select zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                              "+
					"	   zz.bal_bl, zz.bal_al from                                                                                                                                                                    "+
					"	   (select distinct x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,                                                                                              "+
					"	    coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                  "+
					"	    coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                   "+
					"	    coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                    "+
					"	    (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                "+
					"	    (select distinct b.txn_date as date ,'Transfer To Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                       "+
					"	    ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                  "+
					"	    0 as cosum_bl,  0 as cosum_al, (b.recieve_bl) as recv_bl,                                                                                                                       "+
					"	     (b.recieve_al) as recv_al,                                                                                                                                                     "+
					"	    0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                    "+
					"	    from distillery.bottling_vat a, distillery.master_bottoling_of_vat b                                                                                                                        "+
					"	    where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                       "+
					"	    and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.vat_no='"+act.getVatNo()+"'  and   b.txn_date between   '2020-07-15'                                                           "+
					"     and     		 '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                      "+
					"	    union                                                                                                                                                                                       "+
					"	    select distinct c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Permises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                         "+
					"	    ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                  "+
					"	    (b.double_quantity_bl-b.double_wastg_bottling) as cosum_bl,  (b.double_al-b.double_wastage_al) as cosum_al,                                                                                 "+
					"	    0 as recv_bl,0 as recv_al,                                                                                                                                                                  "+
					"	    0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                    "+
					"	    from distillery.bottling_vat a, distillery.bottling_dtl_19_20 b ,distillery.bottling_master_cl_19_20 c                                                                                      "+
					"	    where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id                                                                                                            "+
					"	    and b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id and a.int_distillery_id='"+act.getLoginUserId()+"' and                                                                        "+
					"	    b.blending_vat_id='"+act.getVatNo()+"'  and   c.date_currunt_date between   '2020-07-15'  and 		'"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' )X                            "+
					"	                                                                                                                                                                                                "+
                    "                                                                                                                                                                                                   "+
					"	    union                                                                                                                                                                                       "+
                    "                                                                                                                                                                                                   "+
					"	       select distinct x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,                                                                                           "+
					"	    coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                  "+
					"	    coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                   "+
					"	    coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                    "+
					"	    (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                "+
					"	    (select distinct b.txn_date as date ,'Transfer To Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                       "+
					"	    ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                  "+
					"	     0 as cosum_bl,  0 as cosum_al, (b.recieve_bl-b.wastagebl) as recv_bl,                                                                                                                      "+
					"	    (b.recieve_al-b.wastageal) as recv_al,                                                                                                                                                      "+
					"	    0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                    "+
					"	    from distillery.bottling_vat a, distillery.master_bottoling_of_vat b                                                                                                                        "+
					"	    where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                       "+
					"	    and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.vat_no='"+act.getVatNo()+"'  and   b.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'              "+
					"       and     		 '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                    "+
					"	    union                                                                                                                                                                                       "+
					"	    select distinct c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Permises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                         "+
					"	    ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                  "+
					"	    (b.double_quantity_bl-b.double_wastg_bottling) as cosum_bl,  (b.double_al-b.double_wastage_al) as cosum_al,                                                                                 "+
					"	    0 as recv_bl,0 as recv_al,                                                                                                                                                                  "+
					"	    0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                    "+
					"	    from distillery.bottling_vat a, distillery.bottling_dtl_19_20 b ,distillery.bottling_master_cl_19_20 c                                                                                      "+
					"	    where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id                                                                                                            "+
					"	    and b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id and a.int_distillery_id='"+act.getLoginUserId()+"' and                                                                        "+
					"	    b.blending_vat_id='"+act.getVatNo()+"'  and   c.date_currunt_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and                                                  "+
					"	    '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  )X  )zz     order by zz.date                                                                                                       ";                                                                                                                                                                                                    
						                                                                                             
						
						
						
				
						
//--------------------------------

				"		 select distinct x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,                                                                           "
						+ "		 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                   "
						+ "		 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                      "
						+ "		coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,  "
						+ "        (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                          "
						+ "		 (select distinct b.txn_date as date ,'Transfer To Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                            "
						+ "		 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                       "
						+ "		 0 as cosum_bl,  0 as cosum_al, (b.recieve_bl-b.wastagebl) as recv_bl,                                                                            "
						+ "		 (b.recieve_al-b.wastageal) as recv_al,                                                                                                           "
						+ "		 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                         "
						+ "		  from distillery.bottling_vat a, distillery.master_bottoling_of_vat b                                                                            "
						+ "		  where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                           "
						+ "		 and a.int_distillery_id='"
						+ act.getLoginUserId()
						+ "' and     b.vat_no='"+act.getVatNo()+"'  and   b.txn_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and     "
						+ "		  '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                                                                                         "
						+ "		 union                                                                                                                                            "
						+ "		 select distinct c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Permises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                     "
						+ "		 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                       "
						+ "		 (b.double_quantity_bl-b.double_wastg_bottling) as cosum_bl,  (b.double_al-b.double_wastage_al) as cosum_al,                                      "
						+ "		 0 as recv_bl,0 as recv_al,                                                                                                                       "
						+ "		 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                         "
						+ "		  from distillery.bottling_vat a, distillery.bottling_dtl_19_20 b ,distillery.bottling_master_cl_19_20 c                                                                                 "
						+ "		  where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id                                                                "
						+ "		 and b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id and a.int_distillery_id='"
						+ act.getLoginUserId()
						+ "' and     b.blending_vat_id='"+act.getVatNo()+"'  and   c.date_currunt_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'  and "
						+ "		 '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "' )X   order by date                                                                                          ";

				

				System.out.println("---Bottling FL---" + selQuery);

			} else if (act.getRadio().equalsIgnoreCase("BOTCL")) {
			 
				
				type="Bottling VatCL";
				
				selQuery =
					
			"		select zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,                                              "+
			"		zz.vat_wastage_al,                                                                                                                                                                         "+
			"		  zz.bal_bl, zz.bal_al from                                                                                                                                                                "+
			"		  (select 	x.date, x.dcription ,x.int_id,x.int_distilleri_id,                                                                                                                             "+
			"		 x.vch_tank_name as tank_nm ,                                                                                                                                                              "+
			"		 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                "+
			"		 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                 "+
			"		 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                  "+
			"		 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                               "+
			"		 (select distinct b.txn_date as date ,'Transfer To CL Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                            "+
			"		 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                "+
			"		 0 as cosum_bl,  0 as cosum_al,b.recieve_bl as recv_bl,                                                                                                                                    "+
			"		 b.recieve_al as recv_al,                                                                                                                                                                  "+
			"		 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                  "+
			"		 from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                                                                "+
			"		 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                     "+
			"		 and a.int_distillery_id='"+act.getLoginUserId()+"'  and     b.vat_no='"+act.getVatNo()+"'     and   b.txn_date between   '2020-07-15'                                                     "+
			"		 and  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                            "+
			"		 union                                                                                                                                                                                     "+
			"		 select distinct c.date_currunt_date as date ,'Transfer To CL Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                    "+
			"		 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                "+
			"		 (b.double_quantity_bl-b.double_wastg_bottling) as cosum_bl,  (b.double_al-b.double_wastage_al) as cosum_al, 0 as recv_bl,                                                                 "+
			"		 0 as recv_al,                                                                                                                                                                             "+
			"		 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                  "+
			"		 from distillery.bottling_vat_cl a, distillery.bottling_dtl_cl_19_20 b,distillery.bottling_master_cl_19_20 c                                                                               "+
			"		 where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id      and                                                                                                 "+
			"		 b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id and a.int_distillery_id='"+act.getLoginUserId()+"' and   c.date_currunt_date between   '2020-07-15'                             "+
			"		 and  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                            "+
			"		 union                                                                                                                                                                                     "+
			"		 select distinct b.txn_date as date ,'BottlingVatForCLAction' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                 "+
			"		 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                "+
			"		 0 as cosum_bl,  0 as cosum_al, b.recieve_bl as recv_bl,                                                                                                                                   "+
			"		 b.recieve_al as recv_al,                                                                                                                                                                  "+
			"		 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                  "+
			"		 from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                                                                "+
			"		 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                     "+
			"		 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.vat_no='"+act.getVatNo()+"'   and b.txn_date between   '2020-07-15'                                                          "+
			"		 and  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'	                                                                                                                   "+
			"		 union                                                                                                                                                                                     "+
			"		 select distinct c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Premises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                 "+
			"		 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                "+
			"		 (double_quantity_bl+double_wastg_bottling) as cosum_bl, (double_al+double_wastage_al) as cosum_al, 0 as recv_bl,                                                                                                                                   "+
			"		 0 as recv_al,                                                                                                                                                                  "+
			"		 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                  "+
			"		 from distillery.bottling_vat_cl a, distillery.bottling_dtl_cl_20_21 b ,distillery.bottling_master_cl_19_20 c                                                                                                                "+
			"		 where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id  and   b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id                                                                                                                    "+
			"		 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.blending_vat_id='"+act.getVatNo()+"'   and c.date_currunt_date between     '2020-07-15'            "+
			"		 and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' ) X	  " + 
		
			"                                                                                                                                                                                                  "+
			"		union                                                                                                                                                                                      "+
            "                                                                                                                                                                                                  "+
			"		 select 	x.date, x.dcription ,x.int_id,x.int_distilleri_id,                                                                                                                             "+
			"		 x.vch_tank_name as tank_nm ,                                                                                                                                                              "+
			"		 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                "+
			"		 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                 "+
			"		 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                  "+
			"		 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                               "+
			"		 (select distinct b.txn_date as date ,'Transfer To CL Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                            "+
			"		 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                "+
			"		 0 as cosum_bl,  0 as cosum_al,b.recieve_bl as recv_bl,                                                                                                                                    "+
			"		 b.recieve_al as recv_al,                                                                                                                                                                  "+
			"		 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                  "+
			"		 from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                                                                "+
			"		 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                     "+
			"		 and a.int_distillery_id='"+act.getLoginUserId()+"'  and     b.vat_no='"+act.getVatNo()+"'     and   b.txn_date between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'       "+
			"		 and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                               "+
			"		 union                                                                                                                                                                                     "+
			"		 select distinct c.date_currunt_date as date ,'Transfer To CL Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                    "+
			"		 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                "+
			"		 (b.double_quantity_bl-b.double_wastg_bottling) as cosum_bl,  (b.double_al-b.double_wastage_al) as cosum_al, 0 as recv_bl,                                                                 "+
			"		 0 as recv_al,                                                                                                                                                                             "+
			"		 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                  "+
			"		 from distillery.bottling_vat_cl a, distillery.bottling_dtl_cl_19_20 b,distillery.bottling_master_cl_19_20 c                                                                               "+
			"		 where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id      and                                                                                                 "+
			"		 b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id and a.int_distillery_id='"+act.getLoginUserId()+"' and   c.date_currunt_date between                                            "+
			"		 '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                 "+
			"		 and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                               "+
			"		 union                                                                                                                                                                                     "+
			"		 select distinct b.txn_date as date ,'BottlingVatForCLAction' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                 "+
			"		 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                "+
			"		 0 as cosum_bl,  0 as cosum_al, b.recieve_bl as recv_bl,                                                                                                                                   "+
			"		 b.recieve_al as recv_al,                                                                                                                                                                  "+
			"		 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                  "+
			"		 from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                                                                "+
			"		 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                     "+
			"		 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.vat_no='"+act.getVatNo()+"'   and b.txn_date between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'            "+
			"		 and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'" +
			 " union                                                                                                                                                                                     "+
				"		 select distinct c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Premises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                 "+
				"		 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                "+
				"		 (double_quantity_bl+double_wastg_bottling) as cosum_bl, (double_al+double_wastage_al) as cosum_al, 0 as recv_bl,                                                                                                                                   "+
				"		 0 as recv_al,                                                                                                                                                                  "+
				"		 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                  "+
				"		 from distillery.bottling_vat_cl a, distillery.bottling_dtl_cl_20_21 b ,distillery.bottling_master_cl_19_20 c                                                                                                               "+
				"		 where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id and   b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id                                                                                                                     "+
				"		 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.blending_vat_id='"+act.getVatNo()+"'   and c.date_currunt_date between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'            "+
				"		 and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'" +
			"        ) X		 )zz     order by zz.date    		                                                                                   ";
						
						
					
//------------------------------------------------						

				" select 	x.date, x.dcription ,x.int_id,x.int_distilleri_id,                                                                                     "
						+ " x.vch_tank_name as tank_nm ,                                                                                                                   "
						+ " coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                        "
						+ " coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                                               "
						+ " 	coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,  "
						+ "  (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                     "
						+ " (select distinct b.txn_date as date ,'Transfer To CL Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                           "
						+ " ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                               "
						+ " 0 as cosum_bl,  0 as cosum_al,b.recieve_bl as recv_bl,                                                                                                   "
						+ " b.recieve_al as recv_al,                                                                                                                              "
						+ " 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                 "
						+ "  from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                              "
						+ "  where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                   "
						+ "   and a.int_distillery_id='"
						+ act.getLoginUserId()
						+ "'  and     b.vat_no='"+act.getVatNo()+"'    and   b.txn_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'         "
						+ "   and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                                                                                            "
					+ "     union                                                                                                                                                "
					+ "   select distinct c.date_currunt_date as date ,'Transfer To CL Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                          "
						+ " ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                               "
						+ "  (b.double_quantity_bl-b.double_wastg_bottling) as cosum_bl,  (b.double_al-b.double_wastage_al) as cosum_al, 0 as recv_bl,                               "
						+ " 0 as recv_al,                                                                                                                                            "
			        	+ " 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                 "
			            + "  from distillery.bottling_vat_cl a, distillery.bottling_dtl_cl_19_20 b,distillery.bottling_master_cl_19_20 c                                                                                   "
					    + "  where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id      and                                                                  "
						+ "  b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id and a.int_distillery_id='"
						+ act.getLoginUserId()
						+ "' and   c.date_currunt_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'            "
						+ "   and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'                                                                                            "
						+ "     union                                                                                                                                                "
						+ "   select distinct b.txn_date as date ,'BottlingVatForCLAction' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                              "
						+ " ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                               "
						+ "  0 as cosum_bl,  0 as cosum_al, b.recieve_bl as recv_bl,                    "
						+ " b.recieve_al as recv_al,                                                                                                                                 "
						+ " 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                 "
						+ "  from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                              "
						+ "  where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                   "
						+ "   and a.int_distillery_id='"
						+ act.getLoginUserId()
						+ "' and     b.vat_no='"+act.getVatNo()+"'  and b.txn_date between   '"
						+ Utility.convertUtilDateToSQLDate(act.getFormdate())
						+ "'                  "
						+ "   and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "') X		 order by date                                                                                       ";

				
				System.out.println("---Bottling CL---" + selQuery);

			}
			// /System.out.println("======check=======" + selQuery);
			pst = con.prepareStatement(selQuery);

			rs = pst.executeQuery();
			// //System.out.println("======check=======" + rs.next());
			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("image", relativePath + File.separator);
				parameters.put("type", type);
				System.out.println("============" + relativePath);
				parameters.put("todate", act.getTodate());
				parameters.put("formdate", act.getFormdate());
				parameters.put("op_al",act.getOpening_al());
				parameters.put("op_bl",act.getOpening_bl());
				parameters.put("opDate",Utility.convertUtilDateToSQLDate(act.getFormdate()));
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader
						.loadObject(relativePath + File.separator
								+ "Distillery_TankWise_wastage.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "Distillery_TankWise_wastage" + "-" + n
								+ ".pdf");
				act.setPdfName("Distillery_TankWise_wastage" + "-" + n + ".pdf");
				act.setPrintFlag(true);
			} else {
				// act.setPrintFlag(false);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found!!", "No Data Found!!"));
				act.setPrintFlag(false);

			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}*/

	// ------------------------------------------------------------------------------------------

		public void getopening(TankWise_StcoklistAction act) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String selQuery = null;

			try {
				con = ConnectionToDataBase.getConnection();
				if (act.getRadio().equalsIgnoreCase("SV")) {
				/*	opn_al
					opn_bl
					 den_vat_id,int_dist_id,vat_id
	*/
					selQuery = "  select 	sum(opn_bl) as openingbl,sum(opn_al) as openingal  "
							+ " from distillery.spirit_vat where int_dist_id = '"
							+ act.getLoginUserId() + "'  and vat_id='"+act.getVatNo()+"' ";

					System.out.println("--getopening-Blending SV---" + selQuery);

				} else if (act.getRadio().equalsIgnoreCase("DV")) {

					selQuery = "	select sum(opn_bl) as openingbl,sum(opn_al) as openingal from distillery.denatured_spirit_vat  where int_dist_id='"
							+ act.getLoginUserId() + "' and den_vat_id='"+act.getVatNo()+"' ";

					System.out.println("-getopening--Blending DV---" + selQuery);

				}

				else if (act.getRadio().equalsIgnoreCase("BLENDFL")) {

					selQuery =

					" select sum(opn_bl) as openingbl,sum(opn_al) as openingal from distillery.spirit_for_bottling  where int_distillery_id='"
							+ act.getLoginUserId() + "'  and storage_id='"+act.getVatNo()+"' ";

					System.out.println("--getopening-BLENDFL---" + selQuery);

				} else if (act.getRadio().equalsIgnoreCase("BLENDCL")) {
					selQuery =

					" select sum(opn_bl) as openingbl,sum(opn_al) as openingal from distillery.spirit_for_bottling_cl  where int_distillery_id='"
							+ act.getLoginUserId() + "' and storage_id='"+act.getVatNo()+"'  ";

					System.out.println("-getopening--BLEND=CL---" + selQuery);

				} else if (act.getRadio().equalsIgnoreCase("BOTFL")) {

					selQuery =

					"select sum(opn_bl) as openingbl,sum(opn_al) as openingal from distillery.bottling_vat  where int_distillery_id='"
							+ act.getLoginUserId() + "'  and storage_id='"+act.getVatNo()+"' ";

					System.out.println("--getopening-Bottling FL---" + selQuery);

				} else if (act.getRadio().equalsIgnoreCase("BOTCL")) {
					selQuery =

					"  select sum(opn_bl) as openingbl,sum(opn_al) as openingal from distillery.bottling_vat_cl  where int_distillery_id='"
							+ act.getLoginUserId() + "' and storage_id='"+act.getVatNo()+"' ";

					System.out.println("getopening---Bottling CL---" + selQuery);

				}
				ps = con.prepareStatement(selQuery);
				rs = ps.executeQuery();

				if (rs.next()) {

					act.setOpening_al(rs.getDouble("openingal"));
					act.setOpening_bl(rs.getDouble("openingbl"));
					/*
					 * ac.setBalance(rs.getInt("opening"));
					 * ac.setNewb(rs.getInt("opening"));
					 */

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}


	// ========= show DataTable =============

			public ArrayList getShowData(TankWise_StcoklistAction act) {
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				ArrayList list = new ArrayList();
				String selQuery = null;
				String type = null;
				double bal_al =0.0;
				double bal_bl =0.0;
				double bal_al_2 =0.0;
				double bal_bl_2 =0.0;
				double op_al =act.getOpening_al();
				double op_bl =act.getOpening_bl();
				

				int j = 1;
				int i=  0;
				try {
					if (act.getRadio().equalsIgnoreCase("SV")) {
	     				type="Spirit Vat";
	     				selQuery =
	     						
	     		"			 select distinct  zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,                                                                                  "+
	     		"			 zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                                                                                                                                        "+
	     		"			 case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl ,case when zz.flg=true then                                                                                "+
	     		"			 zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al from                                                                                                                                    "+
	     		"			 (select distinct x.dt_time,  x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0)                                                                     "+
	     		"			 as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                  "+
	     		"			 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as                                                                                           "+
	     		"			 vat_wastage_bl, coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,                                                                                                                                  "+
	     		"			 (coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                            "+
	     		"			 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                        "+
	     		"			 ( select b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of Spirit to CL Blending Vat-FromVat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                            "+
	     		"			 b.qty_transfr_bl as cosum_bl,b.qty_transfr_al as cosum_al,                                                                                                                                         "+
	     		"			 0 as recv_bl,0 as recv_al,                                                                                                                                                                         "+
	     		"			 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                            "+
	     		"			 from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_cl_blending b                                                                                                        "+
	     		"			 where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                             "+
	     		"			 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.from_vat_no='"+act.getVatNo()+"'   and                                                                                               "+
	     		"			 b.tansfer_dt between    '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                 "+
	     		"			 union                                                                                                                                                                                              "+
	     		"			 select c.dt_time , false as flg,c.recv_dt as date, 'ENA Gatepass Receiving With In State' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                           "+
	     		"			 a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                      "+
	     		"			 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                          "+
	     		"			 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                            "+
	     		"			 from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b ,distillery.export_spirit_in_state c                                                                                                                "+
	     		"			 where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id   and    b.permit_no=c.permit_no and  b.permit_date=c.permit_date                                                                                                                            "+
	     		"			 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                                "+
	     		"			 c.recv_dt between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                              "+
	     		"			 union                                                                                                                                                                                              "+
	     		"			 select b.dt_time , false as flg,c.dt_created as date, 'ENA Spirit Sale ' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                "+
	     		"			 a.int_distilleri_id,a.vch_tank_name ,(b.transfer_bl+b.wastage_bl) as cosum_bl,(b.transfer_al+b.wastage_al) as cosum_al,                                                                              "+
	     		"			 0 as recv_bl,0 as recv_al,                                                                                                                                                                         "+
	     		"			 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                            "+
	     		"			 from distillery.distillery_spirit_store_detail a,distillery.export_spirit_in_state_detail b ,distillery.export_spirit_in_state c                                                                   "+
	     		"			 where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id   and   b.int_id_fk=c.int_id                                                                                                     "+
	     		"			 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vat_no='"+act.getVatNo()+"'  and                                                                                                    "+
	     		"			 c.dt_created between  '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                  "+
	     		"			 union                                                                                                                                                                                              "+
	     		"			 select b.dt_time , false as flg,b.dt_created as date, 'Import of ENA' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                      "+
	     		"			 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                       "+
	     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.spirit_import b                                                                                                                                "+
	     		"		 where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                           "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vatno='"+act.getVatNo()+"'  and                                                                                                             "+
	     		"		 b.dt_created between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                          "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.created_date as date, 'ReceivedFromPlant' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                                "+
	     		"		 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                               "+
	     		"		 b.quantity_bl as recv_bl, b.quantity_al as recv_al,                                                                                                                                                        "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.received_from_plant_master b                                                                                                                   "+
	     		"		 where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                                      "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'                                                                                                                  "+
	     		"		 and  b.created_date between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                   "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.created_date as date, 'ReDistillationOfSpirit' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name                                             "+
	     		"		 ,b.trnsfer_bl as cosum_bl,b.trnsfer_al as cosum_al,                                                                                                                                                        "+
	     		"		 0 as recv_bl,0 as recv_al,                                                                                                                                                                                 "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.re_distillation_of_spirit_master b                                                                                                             "+
	     		"		 where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                                            "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'                                                                                                                  "+
	     		"		 and  b.created_date between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                   "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.date as date, 'REMOVAL OF SPIRIT FOR DENATURING' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                              "+
	     		"		 (b.spirit_vat_quantitybl-b.from_qty_as_pr_dpt_bl) as cosum_bl,                                                                                                                                             "+
	     		"		 (b.spirit_vat_quantityal-b.from_qty_as_pr_dpt_al)  as cosum_al,                                                                                                                                            "+
	     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.removalofspiritfrdenaturing b                                                                                                                  "+
	     		"		 where  a.int_id=b.spirit_vatno and  a.int_distilleri_id=b.int_distillery_code                                                                                                                              "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                     		                                                                            "+
	     		"		 and      b.spirit_vatno='"+act.getVatNo()+"' and                                                                                                                                                           "+
	     		"		 b.date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                               "+
	     		"		  union                                                                                                                                                                                                     "+
	     		"		 select b.dt_time , false as flg,b.dt_created as date, 'SpiritExport' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                                                              "+
	     		"		 a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                        		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                    "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.export_spirit b                                                                                                                                "+
	     		"		 where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                      "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                                        "+
	     		"		 b.dt_created between   '2020-07-15' and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                          "+
	     		"		                                                                                                                                                                                                            "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.dt_created as date, 'Spirit Purchased In ( State )' as dcription ,a.openingal,                                                                                                      "+
	     		"		 a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                     "+
	     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                                       "+
	     		"		 where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                      "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                                        "+
	     		"		 b.dt_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                         "+
	     		"		                                        	                                                                                                                                                                "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,c.dt_created as date, 'Spirit Sale Other Than ENA  ' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                     "+
	     		"		 a.int_distilleri_id,a.vch_tank_name ,(b.transfer_bl+b.wastage_bl) as cosum_bl,(b.transfer_al+b.wastage_al) as cosum_al,                                                                                      "+
	     		"		 0 as recv_bl,0 as recv_al,                                                                                                                                                                                 "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.export_spirit_in_state_detail b ,distillery.export_spirit_in_state c                                                                           "+
	     		"		 where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id   and   b.int_id_fk=c.int_id                                                                                                             "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vat_no='"+act.getVatNo()+"'  and                                                                                                            "+
	     		"		 c.dt_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                         "+
	     		"		                                                                                                                                                                                                            "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.date_created as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                             "+
	     		"		 a.int_distilleri_id,a.vch_tank_name ,b.dob_qunty_transfer_bl as cosum_bl,b.dob_qunty_transfer_al as cosum_al,                                                                                              "+
	     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b                                                                                                     "+
	     		"		 where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                             "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"' and                                                                		   b.int_from_vat_id='"+act.getVatNo()+"'                                       "+
	     		"		 and                                                                                                                                  		                                                                "+
	     		"		 b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                       "+
	     		"		 union 		                                                                                                                                                                                                "+
	     		"		 select b.dt_time , false as flg,v.txn_date as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                 "+
	     		"		 a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                          "+
	     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
	     		"		 v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                      "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b,                                                                                                    "+
	     		"		 distillery.vat_wastage v  where  a.int_id=b.int_to_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                     "+
	     		"		 and b.int_to_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"' and                                                                                "+
	     		"		 b.int_to_vat_id='"+act.getVatNo()+"'                                                                                                                                                                       "+
	     		"		 and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F'  and                                                                                                                                              "+
	     		"		 v.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                 		                                                    "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                          "+
	     		"		 qty_transfr_bl as cosum_bl,qty_transfr_al as cosum_al,                                                                                                                                                     "+
	     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b                                                                                                                "+
	     		"		 where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                                     "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.from_vat_no='"+act.getVatNo()+"'  and                                                                                                       "+
	     		"		 b.tansfer_dt between  '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                          "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                          "+
	     		"		 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                               "+
	     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
	     		"		 v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                      "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b ,distillery.vat_wastage v                                                                                      "+
	     		"		 where  a.int_id=b.to_vat_no and  a.int_distilleri_id=b.distillery_id    and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int                                                                       "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.to_vat_no='"+act.getVatNo()+"' and   v.type='SPR-BLN_TRANSFER_WASTAGE' and v.vat_des='F'  and                                               "+
	     		"		 b.tansfer_dt between '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'     ) X                                                                                                   "+
	     		"		union                                                                                                                                                                                                       "+
	     		"		 select distinct x.dt_time,  x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0)                                                                              "+
	     		"		 as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                          "+
	     		"		 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as                                                                                                   "+
	     		"		 vat_wastage_bl, coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,                                                                                                                                          "+
	     		"		 (coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                    "+
	     		"		 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                "+
	     		"		 ( select b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of Spirit to CL Blending Vat-FromVat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                    "+
	     		"		 b.qty_transfr_bl as cosum_bl,b.qty_transfr_al as cosum_al,                                                                                                                                                 "+
	     		"		 0 as recv_bl,0 as recv_al,                                                                                                                                                                                 "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_cl_blending b                                                                                                                "+
	     		"		 where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                                     "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.from_vat_no='"+act.getVatNo()+"'   and                                                                                                       "+
	     		"		 b.tansfer_dt between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                               "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"			 select c.dt_time , false as flg,c.recv_dt as date, 'ENA Gatepass Receiving With In State' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                           "+
	     		"			 a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                      "+
	     		"			 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                          "+
	     		"			 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                            "+
	     		"			 from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b ,distillery.export_spirit_in_state c                                                                                                                "+
	     		"			 where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id   and    b.permit_no=c.permit_no and  b.permit_date=c.permit_date                                                                                                                            "+
	     		"			 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                                "+
	     		"			 c.recv_dt between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                                              "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,c.dt_created as date, 'ENA Spirit Sale ' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                        "+
	     		"		 a.int_distilleri_id,a.vch_tank_name ,(b.transfer_bl+b.wastage_bl) as cosum_bl,(b.transfer_al+b.wastage_al) as cosum_al,                                                                                      "+
	     		"		 0 as recv_bl,0 as recv_al,                                                                                                                                                                                 "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.export_spirit_in_state_detail b ,distillery.export_spirit_in_state c                                                                           "+
	     		"		 where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id   and   b.int_id_fk=c.int_id                                                                                                             "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vat_no='"+act.getVatNo()+"'  and                                                                                                            "+
	     		"		 c.dt_created between  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.dt_created as date, 'Import of ENA' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                              "+
	     		"		 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                               "+
	     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.spirit_import b                                                                                                                                "+
	     		"		 where  a.int_id=b.vatno and  a.int_distilleri_id=b.distillery_id                                                                                                                                           "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vatno='"+act.getVatNo()+"'  and                                                                                                             "+
	     		"		 b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.created_date as date, 'ReceivedFromPlant' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                                "+
	     		"		 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                               "+
	     		"		 b.quantity_bl as recv_bl, b.quantity_al as recv_al,                                                                                                                                                        "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.received_from_plant_master b                                                                                                                   "+
	     		"		 where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                                      "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'                                                                                                                  "+
	     		"		 and  b.created_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                         "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.created_date as date, 'ReDistillationOfSpirit' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name                                             "+
	     		"		 ,b.trnsfer_bl as cosum_bl,b.trnsfer_al as cosum_al,                                                                                                                                                        "+
	     		"		 0 as recv_bl,0 as recv_al,                                                                                                                                                                                 "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.re_distillation_of_spirit_master b                                                                                                             "+
	     		"		 where  a.int_id=b.vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                                            "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.vat_id='"+act.getVatNo()+"'                                                                                                                  "+
	     		"		 and  b.created_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                         "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.date as date, 'REMOVAL OF SPIRIT FOR DENATURING' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                              "+
	     		"		 (b.spirit_vat_quantitybl-b.from_qty_as_pr_dpt_bl) as cosum_bl,                                                                                                                                             "+
	     		"		 (b.spirit_vat_quantityal-b.from_qty_as_pr_dpt_al)  as cosum_al,                                                                                                                                            "+
	     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.removalofspiritfrdenaturing b                                                                                                                  "+
	     		"		 where  a.int_id=b.spirit_vatno and  a.int_distilleri_id=b.int_distillery_code                                                                                                                              "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                     		                                                                            "+
	     		"		 and      b.spirit_vatno='"+act.getVatNo()+"' and                                                                                                                                                           "+
	     		"		 b.date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                     "+
	     		"		  union                                                                                                                                                                                                     "+
	     		"		 select b.dt_time , false as flg,b.dt_created as date, 'SpiritExport' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,                                                                              "+
	     		"		 a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                        		   b.net_bl as recv_bl, b.net_al as recv_al,                                                                                    "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.export_spirit b                                                                                                                                "+
	     		"		 where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                      "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                                        "+
	     		"		 b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                "+
	     		"		                                                                                                                                                                                                            "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.dt_created as date, 'Spirit Purchased In ( State )' as dcription ,a.openingal,                                                                                                      "+
	     		"		 a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                     "+
	     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.import_spirit_in_state b                                                                                                                       "+
	     		"		 where  a.int_id=b.spirit_vat and  a.int_distilleri_id=b.distillery_id                                                                                                                                      "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.spirit_vat='"+act.getVatNo()+"'  and                                                                                                        "+
	     		"		 b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                               "+
	     		"		                                        	                                                                                                                                                                "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,c.dt_created as date, 'Spirit Sale Other Than ENA  ' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                     "+
	     		"		 a.int_distilleri_id,a.vch_tank_name ,(b.transfer_bl+b.wastage_bl) as cosum_bl,(b.transfer_al+b.wastage_al) as cosum_al,                                                                                      "+
	     		"		 0 as recv_bl,0 as recv_al,                                                                                                                                                                                 "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.export_spirit_in_state_detail b ,distillery.export_spirit_in_state c                                                                           "+
	     		"		 where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id   and   b.int_id_fk=c.int_id                                                                                                             "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.vat_no='"+act.getVatNo()+"'  and                                                                                                            "+
	     		"		 c.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                               "+
	     		"		                                                                                                                                                                                                            "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.date_created as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                             "+
	     		"		 a.int_distilleri_id,a.vch_tank_name ,b.dob_qunty_transfer_bl as cosum_bl,b.dob_qunty_transfer_al as cosum_al,                                                                                              "+
	     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b                                                                                                     "+
	     		"		 where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                             "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"' and                                                                		   b.int_from_vat_id='"+act.getVatNo()+"'                                       "+
	     		"		 and                                                                                                                                  		                                                                "+
	     		"		 b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                             "+
	     		"		 union 		                                                                                                                                                                                                "+
	     		"		 select b.dt_time , false as flg,v.txn_date as date, 'TRANSFER OF SPIRIT BETWEEN STORAGE VAT' as dcription ,a.openingal,a.openingbl,a.int_id,                                                                                 "+
	     		"		 a.int_distilleri_id,a.vch_tank_name ,0 as cosum_bl,0 as cosum_al,                                                                                                                                          "+
	     		"		 b.net_bl as recv_bl, b.net_al as recv_al,                                                                                                                                                                  "+
	     		"		 v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                      "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.transfer_of_spirit_from_one_vat_to_other b,                                                                                                    "+
	     		"		 distillery.vat_wastage v  where  a.int_id=b.int_to_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                     "+
	     		"		 and b.int_to_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"' and                                                                                "+
	     		"		 b.int_to_vat_id='"+act.getVatNo()+"'                                                                                                                                                                       "+
	     		"		 and   v.type='SPIRIT_TRANSFER_WASTAGE' and v.vat_des='F'  and                                                                                                                                              "+
	     		"		 v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                   		        "+                      
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                          "+
	     		"		 qty_transfr_bl as cosum_bl,qty_transfr_al as cosum_al,                                                                                                                                                     "+
	     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
	     		"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                    "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b                                                                                                                "+
	     		"		 where  a.int_id=b.from_vat_no and  a.int_distilleri_id=b.distillery_id                                                                                                                                     "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.from_vat_no='"+act.getVatNo()+"'  and                                                                                                       "+
	     		"		 b.tansfer_dt between  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                "+
	     		"		 union                                                                                                                                                                                                      "+
	     		"		 select b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of spirit to FL Blending Vat' as dcription ,a.openingal,a.openingbl,a.int_id,a.int_distilleri_id,a.vch_tank_name ,                                          "+
	     		"		 0 as cosum_bl,0 as cosum_al,                                                                                                                                                                               "+
	     		"		 0 as recv_bl, 0 as recv_al,                                                                                                                                                                                "+
	     		"		 v.vat_wastage_bl as vat_wastage_bl,v.vat_wastage_al as vat_wastage_al                                                                                                                                      "+
	     		"		 from distillery.distillery_spirit_store_detail a,distillery.transferspirit_to_fl_blending b ,distillery.vat_wastage v                                                                                      "+
	     		"		 where  a.int_id=b.to_vat_no and  a.int_distilleri_id=b.distillery_id    and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int                                                                       "+
	     		"		 and a.int_distilleri_id='"+act.getLoginUserId()+"'   and     b.to_vat_no='"+act.getVatNo()+"' and   v.type='SPR-BLN_TRANSFER_WASTAGE' and v.vat_des='F'  and                                               "+
	     		"		 b.tansfer_dt between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'       ) X )zz     order by zz.date, zz.dt_time,  zz.dcription 				";
	     						
	     						
	     						
	     						
	     						
	     						
	     						
	     						
	     						
	     						
	     						
	     						
	     						
	                                                                                                                                                                                              
						                                                                                                                                                                                                                                                                                                                   
	   //--------------------------------------------						                                                                                                                                                                                                                                                                   
							                                                                                                                                                                                                                                                                                                              
				System.out.println("---Blending SV---" + selQuery);

				} else if (act.getRadio().equalsIgnoreCase("DV")) {
				    
					type="Denatured Spirit  Vat";
	  
					selQuery =
						
			"		select  distinct zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                             "+
			"		case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl ,                                                                                                          "+
			"		case when zz.flg=true then    zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al from                                                                                                      "+
			"		(select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,                                                         "+
			"		coalesce(x.cosum_al,0.0) as cosum_al, 	coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                  "+
			"		coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                           "+
			"		(coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                        "+
			"		(select   b.dt_time , false as flg,b.dt_crdt as date ,'REMOVAL OF DENATURED SPIRIT ' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,                                             "+
			"		a.vch_tank_name,int_issued_quantity_bl  as cosum_bl,                                                                                                                                               "+
			"		int_issued_quantity_al  as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                     "+
			"		0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                           "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.denatured_spirits_to_issuevat b                                                                                              "+
			"		where  a.int_id=b.int_denatured_vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                      "+
			"		and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.int_denatured_vat_id='"+act.getVatNo()+"'                                                                                             "+
			"		and b.dt_crdt between   '2020-07-15'    and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                              "+
			"		union                                                                                                                                                                                              "+
			"		select   b.dt_time , false as flg,c.recv_dt as date,'ENA Gatepass Receiving With In State ' as dcription,a.openingal,a.openingbl, a.int_id,a.int_distilleri_id,                                      "+
			"		a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                    "+
			"		0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                         "+
			"		0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                          "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b , distillery.export_spirit_in_state c                                                               "+
			"		where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.distillery_id and   b.distillery_id::text=c.consigneeid and c.permit_no=b.permit_no and                                                 "+
			"		c.permit_date=b.permit_date                                                                                                                                                                        "+
			"		and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.denatured_vat='"+act.getVatNo()+"'                                                                                                   "+
			"		and c.recv_dt  between   '2020-07-15'    and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                             "+
			"		union                                                                                                                                                                                              "+
			"		select   b.dt_time , false as flg,v.txn_date as date,'Import of ENA' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                      "+
			"		a.int_distilleri_id,a.vch_tank_name,0 as cosum_bl,                                                                                                                                                 "+
			"		0  as cosum_al, b.net_bl as recv_bl,                                                                                                                                                               "+
			"		b.net_al  as recv_al,                                                                                                                                                                              "+
			"		v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                            "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b,                                                                                                             "+
			"		distillery.vat_wastage v  where  a.int_id=b.denatured_spirit_id and  a.int_distilleri_id=b.distillery_id                                                                                           "+
			"		and b.denatured_spirit_id= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                          "+
			"		and     b.denatured_spirit_id='"+act.getVatNo()+"'                                                                                                                                                 "+
			"		and v.txn_date between   '2020-07-15'    and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                             "+
			"		union                                                                	                                                                                                                           "+
			"		select   b.dt_time , false as flg,c.dt_created as date,'Ethanol Sale To Oil Companies' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                     "+
			"		a.int_distilleri_id,a.vch_tank_name,(b.transfer_bl+b.wastage_bl) as cosum_bl,                                                                                                                      "+
			"		(transfer_al+b.wastage_al) as cosum_al,0 as recv_bl,                                                                                                                                               "+
			"		0 as recv_al,                                                                                                                                                                                      "+
			"		0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                           "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.export_denatured_spirit_detail b,distillery.export_denatured_spirit c                                                        "+
			"		where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id  and   b.int_id_fk=c.int_id                                                                                                      "+
			"		and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                                 "+
			"		and     b.vat_no='"+act.getVatNo()+"'  and c.dt_created between   '2020-07-15'    and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   	                                               "+
			"		union                                                                                                                                                                                              "+
			"		select   b.dt_time , false as flg,b.date as date,'REMOVAL OF SPIRIT FOR DENATURING' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                    "+
			"		a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                                "+
			"		0  as cosum_al,b.net_bl  as recv_bl,                                                                                                                                                               "+
			"		b.net_al  as recv_al,                                                                                                                                                                              "+
			"		0 as vat_wastage_bl,           0  as vat_wastage_al                                                                                                                                                "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                               "+
			"		distillery.vat_wastage v  where  a.int_id=b.den_vat_no and  a.int_distilleri_id=b.int_distillery_code                                                                                              "+
			"		and b.den_vat_no= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                             "+
			"		and     b.den_vat_no='"+act.getVatNo()+"'  and   v.type='SPR-DEN_TRANSFER_WASTAGE '  and v.vat_des ='D'   and  b.date between   '2020-07-15'                                                       "+
			"		 and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   	                                                                                                                               "+
			"		                                                                                                                                                                                                   "+
			"		union                                                                                                                                                                                              "+
			"		                                                                                                                                                                                                   "+
			"		select    b.dt_time , false as flg,b.dt_created as date,'Spirit Purchased In ( State )' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                  "+
			"		a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                                "+
			"		0 as cosum_al, b.net_bl  as recv_bl,                                                                                                                                                               "+
			"		b.net_al  as recv_al,                                                                                                                                                                              "+
			"		v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                            "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b,                                                                                                    "+
			"		distillery.vat_wastage v  where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.distillery_id                                                                                                 "+
			"		and b.denatured_vat= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                "+
			"		and     b.denatured_vat='"+act.getVatNo()+"'                                                                                                                                                       "+
			"		and b.dt_created between   '2020-07-15'     and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                          "+
			"		                                                                                                                                                                                                   "+
			"		union                                                                                                                                                                                              "+
			"		select   b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                        "+
			"		,a.vch_tank_name,0                                                                                                                                                                                 "+
			"		as cosum_bl,                                                                                                                                                                                       "+
			"		0 as cosum_al, b.bal_in_source_bl  as recv_bl, b.bal_in_source_al as recv_al,                                                                                                                      "+
			"		b.wastage_bl  as vat_wastage_bl,b.wastage_al  as vat_wastage_al                                                                                                                                    "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b,                                                                        "+
			"		distillery.vat_wastage v  where  a.int_id=b.int_to_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                             "+
			"		and b.int_to_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                            "+
			"		and     b.int_to_vat_id='"+act.getVatNo()+"' and   v.type='DEN-DEN_TRANSFER_WASTAGE'  and v.vat_des ='D' and b.date_created between   '2020-07-15'                                                 "+
			"		and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                      "+
			"		union		                                                                                                                                                                                       "+
			"		select    b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                        "+
			"		,a.vch_tank_name,dob_qunty_transfer_bl                                                                                                                                                             "+
			"		as cosum_bl,                                                                                                                                                                                       "+
			"		dob_qunty_transfer_al  as cosum_al,0  as recv_bl,0 as recv_al,                                                                                                                                     "+
			"		0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                          "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b                                                                         "+
			"		where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                     "+
			"		and  a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                                "+
			"		and     b.int_from_vat_id='"+act.getVatNo()+"'  and b.date_created between   '2020-07-15'    and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'     )x                                  "+
			"		                                                                                                                                                                                                   "+
			"		union                                                                                                                                                                                              "+
			"		select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,                                                          "+
			"		coalesce(x.cosum_al,0.0) as cosum_al, 	coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                  "+
			"		coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                           "+
			"		(coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                        "+
			"		(select    b.dt_time , false as flg,b.dt_crdt as date ,'REMOVAL OF DENATURED SPIRIT ' as dcription,a.openingal,a.openingbl ,a.int_id,a.int_distilleri_id,                                             "+
			"		a.vch_tank_name,int_issued_quantity_bl  as cosum_bl,                                                                                                                                               "+
			"		int_issued_quantity_al  as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                     "+
			"		0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                           "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.denatured_spirits_to_issuevat b                                                                                              "+
			"		where  a.int_id=b.int_denatured_vat_id and  a.int_distilleri_id=b.int_dist_id                                                                                                                      "+
			"		and a.int_distilleri_id='"+act.getLoginUserId()+"' and     b.int_denatured_vat_id='"+act.getVatNo()+"'                                                                                             "+
			"		and b.dt_crdt between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                    "+
			"		union                                                                                                                                                                                              "+
			"		select  b.dt_time , false as flg,c.recv_dt as date,'ENA Gatepass Receiving With In State ' as dcription,a.openingal,a.openingbl, a.int_id,a.int_distilleri_id,                                      "+
			"		a.vch_tank_name,0  as cosum_bl,                                                                                                                                                                    "+
			"		0  as cosum_al, b.net_bl  as recv_bl, b.net_al as recv_al,                                                                                                                                         "+
			"		0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                          "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b , distillery.export_spirit_in_state c                                                               "+
			"		where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.distillery_id and   b.distillery_id::text=c.consigneeid and c.permit_no=b.permit_no and                                                 "+
			"		c.permit_date=b.permit_date                                                                                                                                                                        "+
			"		and a.int_distilleri_id='"+act.getLoginUserId()+"'  and     b.denatured_vat='"+act.getVatNo()+"'                                                                                                   "+
			"		and c.recv_dt  between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                   "+
			"		union                                                                                                                                                                                              "+
			"		select   b.dt_time , false as flg,v.txn_date as date,'Import of ENA' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                      "+
			"		a.int_distilleri_id,a.vch_tank_name,0 as cosum_bl,                                                                                                                                                 "+
			"		0  as cosum_al, b.net_bl as recv_bl,                                                                                                                                                               "+
			"		b.net_al  as recv_al,                                                                                                                                                                              "+
			"		v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                            "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.spirit_import b,                                                                                                             "+
			"		distillery.vat_wastage v  where  a.int_id=b.denatured_spirit_id and  a.int_distilleri_id=b.distillery_id                                                                                           "+
			"		and b.denatured_spirit_id= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                          "+
			"		and     b.denatured_spirit_id='"+act.getVatNo()+"'                                                                                                                                                 "+
			"		and v.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                   "+
			"		union                                                                	                                                                                                                           "+
			"		select  b.dt_time , false as flg,c.dt_created as date,'Ethanol Sale To Oil Companies' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                     "+
			"		a.int_distilleri_id,a.vch_tank_name,(b.transfer_bl+b.wastage_bl) as cosum_bl,                                                                                                                      "+
			"		(transfer_al+b.wastage_al) as cosum_al,0 as recv_bl,                                                                                                                                               "+
			"		0 as recv_al,                                                                                                                                                                                      "+
			"		0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                           "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.export_denatured_spirit_detail b,distillery.export_denatured_spirit c                                                        "+
			"		where  a.int_id=b.vat_no and  a.int_distilleri_id=c.distillery_id  and   b.int_id_fk=c.int_id                                                                                                      "+
			"		and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                                 "+
			"		and     b.vat_no='"+act.getVatNo()+"'  and c.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                        "+
			"		 and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   	                                                                                                                                   "+
			"		union                                                                                                                                                                                              "+
			"		select    b.dt_time , false as flg,b.date as date,'REMOVAL OF SPIRIT FOR DENATURING' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                    "+
			"		a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                                "+
			"		0  as cosum_al,b.net_bl  as recv_bl,                                                                                                                                                               "+
			"		b.net_al  as recv_al,                                                                                                                                                                              "+
			"		0 as vat_wastage_bl,           0  as vat_wastage_al                                                                                                                                                "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.removalofspiritfrdenaturing b,                                                                                               "+
			"		distillery.vat_wastage v  where  a.int_id=b.den_vat_no and  a.int_distilleri_id=b.int_distillery_code                                                                                              "+
			"		and b.den_vat_no= v.vat_no and b.int_distillery_code=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                             "+
			"		and     b.den_vat_no='"+act.getVatNo()+"'  and   v.type='SPR-DEN_TRANSFER_WASTAGE '  and v.vat_des ='D'   and  b.date between                                                                      "+
			"		 '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   	                                                                       "+
			"		                                                                                                                                                                                                   "+
			"		union                                                                                                                                                                                              "+
			"		                                                                                                                                                                                                   "+
			"		select   b.dt_time , false as flg,b.dt_created as date,'Spirit Purchased In ( State )' as dcription,a.openingal,a.openingbl ,a.int_id,                                                                  "+
			"		a.int_distilleri_id,a.vch_tank_name,0  as cosum_bl,                                                                                                                                                "+
			"		0 as cosum_al, b.net_bl  as recv_bl,                                                                                                                                                               "+
			"		b.net_al  as recv_al,                                                                                                                                                                              "+
			"		v.vat_wastage_bl  as vat_wastage_bl,v.vat_wastage_al  as vat_wastage_al                                                                                                                            "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.import_spirit_in_state b,                                                                                                    "+
			"		distillery.vat_wastage v  where  a.int_id=b.denatured_vat and  a.int_distilleri_id=b.distillery_id                                                                                                 "+
			"		and b.denatured_vat= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                "+
			"		and     b.denatured_vat='"+act.getVatNo()+"'                                                                                                                                                       "+
			"		and b.dt_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'    and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                "+
			"		                                                                                                                                                                                                   "+
			"		union                                                                                                                                                                                              "+
			"		select   b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                        "+
			"		,a.vch_tank_name,0                                                                                                                                                                                 "+
			"		as cosum_bl,                                                                                                                                                                                       "+
			"		0 as cosum_al, b.bal_in_source_bl  as recv_bl, b.bal_in_source_al as recv_al,                                                                                                                      "+
			"		b.wastage_bl  as vat_wastage_bl,b.wastage_al  as vat_wastage_al                                                                                                                                    "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b,                                                                        "+
			"		distillery.vat_wastage v  where  a.int_id=b.int_to_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                             "+
			"		and b.int_to_vat_id= v.vat_no and b.int_distillery_id=v.unit_id::int and a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                            "+
			"		and     b.int_to_vat_id='"+act.getVatNo()+"' and   v.type='DEN-DEN_TRANSFER_WASTAGE'  and v.vat_des ='D' and b.date_created between                                                                "+
			"		 '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                           "+
			"		union		                                                                                                                                                                                       "+
			"		select    b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF DENATURED SPIRIT BETWEEN STORAGE VATS ' as dcription,a.openingal,a.openingbl , a.int_id,a.int_distilleri_id                                        "+
			"		,a.vch_tank_name,dob_qunty_transfer_bl                                                                                                                                                             "+
			"		as cosum_bl,                                                                                                                                                                                       "+
			"		dob_qunty_transfer_al  as cosum_al,0  as recv_bl,0 as recv_al,                                                                                                                                     "+
			"		0  as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                          "+
			"		from distillery.distillery_denatures_spirit_store_detail a,distillery.transfer_of_denatured_spirit_from_one_vat_to_other b                                                                         "+
			"		where  a.int_id=b.int_from_vat_id and  a.int_distilleri_id=b.int_distillery_id                                                                                                                     "+
			"		and  a.int_distilleri_id='"+act.getLoginUserId()+"'                                                                                                                                                "+
			"		and     b.int_from_vat_id='"+act.getVatNo()+"'  and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                             "+
			"	    and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'     )x )zz     order by zz.date, zz.dt_time,  zz.dcription                                                                                        ";
						
						



                                                                                                                                                  
							 

		
	//--------------------------------------------------
					
					System.out.println("---Blending DV---" + selQuery);

				}

				else if (act.getRadio().equalsIgnoreCase("BLENDFL")) {
					
					type="Blending VatFL";

					selQuery =
					"		select  distinct zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                                                                       "+
					"		 case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl ,case when zz.flg=true then    zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al " +
					"        from (select distinct 	x.dt_time,x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,                                                                                                        "+
					"		 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                   	                                                                                                                      "+
					"		 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                            "+
					"		 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                             "+
					"		 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                          "+
					"		 (select  b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                                                                       "+
					"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,dob_qunty_transfer_bl                                                                                                                "+
					"		 as cosum_bl,                                                                                                                                                                                                                         "+
					"		 dob_qunty_transfer_al  as cosum_al, 0 as recv_bl,0 as recv_al,                                                                                                                                                                       "+
					"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
					"		 from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                                                                                "+
					"		 where  a.storage_id=b.int_from_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                                                                   "+
					"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
					"		 and     b.int_from_vat_id='"+act.getVatNo()+"'                                                                                                                                                                                       "+
					"		 and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                             "+
					"		 union                                                                                                                                                                                                                                "+
					"		 select  b.dt_time , false as flg, b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                                                                        "+
					"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                    "+
					"		 as cosum_bl,   0 as cosum_al,b.net_bl as recv_bl,b.net_al as recv_al,                                                                                                                                                                "+
					"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
					"		 from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                                                                                "+
					"		 where  a.storage_id=b.int_to_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                                                                     "+
					"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
					"		 and     b.int_to_vat_id='"+act.getVatNo()+"'        and                                                                                                                                                                              "+
					"		 b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                 "+
					"		 union                                                                                                                                                                                                                                "+
					"		 select  b.dt_time , false as flg,b.txn_date as date ,'Transfer To Bottling Vat ' as dcription,a.openingal,a.openingbl ,                                                                                                                                       "+
					"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,(b.recieve_bl+b.wastagebl)                                                                                                           "+
					"		 as cosum_bl, (b.recieve_al+b.wastageal) as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                                       "+
					"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
					"		 from distillery.spirit_for_bottling a,distillery.master_bottoling_of_vat b                                                                                                                                                           "+
					"		 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                "+
					"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
					"		 and     b.vat_no='"+act.getVatNo()+"'                                                                                                                                                                                                "+
					"		 and b.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                 "+
					"		 union                                                                                                                                                                                                                                "+
					"		 select   b.dt_time , false as flg,b.tansfer_dt as date ,'Transfer of spirit to FL Blending Vat ' as dcription,a.openingal,a.openingbl ,                                                                                                                      "+
					"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                    "+
					"		 as cosum_bl,  0 as cosum_al,qty_recv_bl as recv_bl,qty_recv_al as recv_al,                                                                                                                                                           "+
					"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
					"		 from distillery.spirit_for_bottling a,distillery.transferspirit_to_fl_blending b                                                                                                                                                     "+
					"		 where  a.storage_id=b.to_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                             "+
					"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
					"		 and     b.to_vat_no='"+act.getVatNo()+"'       and b.tansfer_dt between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                            "+
					"		 union                                                                                                                                                                                                                                "+
					"		 select   b.dt_time ,b.data_flg as flg,b.date_created as date ,'Prepration Of FL Blend  ' as dcription,a.openingal,a.openingbl ,                                                                                                                      "+
					"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                    "+
					"		 as cosum_bl,  0 as cosum_al,produced_bl as recv_bl,((produced_bl*strength)/100) as recv_al,                                                                                                                                                           "+
					"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
					"		 from distillery.spirit_for_bottling a,distillery.foreign_liquor_blending b                                                                                                                                                     "+
					"		 where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                             "+
					"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
					"		 and     b.int_vat_no='"+act.getVatNo()+"'       and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  )x	                                                                          "+
					"		 union                                                                                                                                                                                                                                "+

					"		 select distinct x.dt_time,	x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                          "+
					"		 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                            "+
					"		 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                             "+
					"		 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                          "+
					"		 (select   b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                                                                       "+
					"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,dob_qunty_transfer_bl                                                                                                                "+
					"		 as cosum_bl,                                                                                                                                                                                                                         "+
					"		 dob_qunty_transfer_al  as cosum_al, 0 as recv_bl,0 as recv_al,                                                                                                                                                                       "+
					"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
					"		 from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                                                                                "+
					"		 where  a.storage_id=b.int_from_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                                                                   "+
					"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
					"		 and     b.int_from_vat_id='"+act.getVatNo()+"'       and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                             "+
					"		 union                                                                                                                                                                                                                                "+
					"		 select  b.dt_time , false as flg,b.date_created as date ,'TRANSFER OF BLENDING VAT BETWEEN BLENDING VATS ' as dcription,a.openingal,a.openingbl ,                                                                                                        "+
					"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                    "+
					"		 as cosum_bl,   0 as cosum_al,b.net_bl as recv_bl,b.net_al as recv_al,                                                                                                                                                                "+
					"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
					"		 from distillery.spirit_for_bottling a,distillery.transfer_of_blending_vat_from_one_vat_to_other_vat b                                                                                                                                "+
					"		 where  a.storage_id=b.int_to_vat_id and  a.int_distillery_id=b.int_distillery_id                                                                                                                                                     "+
					"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
					"		 and     b.int_to_vat_id='"+act.getVatNo()+"'        and                                                                                                                                                                              "+
					"		 b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                      "+
					"		 union                                                                                                                                                                                                                                "+
					"		 select   b.dt_time , false as flg,b.txn_date as date ,'Transfer To Bottling Vat ' as dcription,a.openingal,a.openingbl ,                                                                                                                                       "+
					"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,(b.recieve_bl+b.wastagebl)                                                                                                           "+
					"		 as cosum_bl,(b.recieve_al+b.wastageal) as cosum_al,0 as recv_bl,0 as recv_al,                                                                                                                                                       "+
					"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
					"		 from distillery.spirit_for_bottling a,distillery.master_bottoling_of_vat b                                                                                                                                                           "+
					"		 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                "+
					"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
					"		 and     b.vat_no='"+act.getVatNo()+"'       and b.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' 	                                      "+
					"		 union                                                                                                                                                                                                                                "+
					"		 select   b.dt_time , false as flg,b.tansfer_dt as date ,'Transfer of spirit to FL Blending Vat ' as dcription,a.openingal,a.openingbl ,                                                                                                                      "+
					"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                    "+
					"		 as cosum_bl, 0 as cosum_al,qty_recv_bl as recv_bl,qty_recv_al as recv_al,                                                                                                                                                            "+
					"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
					"		 from distillery.spirit_for_bottling a,distillery.transferspirit_to_fl_blending b                                                                                                                                                     "+
					"		 where  a.storage_id=b.to_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                             "+
					"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
					"		 and     b.to_vat_no='"+act.getVatNo()+"'       and                                                                                                                                                                                   "+
					"		 b.tansfer_dt between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                     "+
					"		 union                                                                                                                                                                                                                                "+

					"		 select b.dt_time ,b.data_flg as flg,b.date_created as date ,'Prepration Of FL Blend  ' as dcription,a.openingal,a.openingbl ,                                                                                                                      "+
					"		 a.storage_id as int_id,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,0                                                                                                                                    "+
					"		 as cosum_bl,  0 as cosum_al,produced_bl as recv_bl,((produced_bl*strength)/100) as recv_al,                                                                                                                                                           "+
					"		 0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                              "+
					"		 from distillery.spirit_for_bottling a,distillery.foreign_liquor_blending b                                                                                                                                                     "+
					"		 where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                             "+
					"		 and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                   "+
					"		 and     b.int_vat_no='"+act.getVatNo()+"'       and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'     )x	)zz     order by zz.date , zz.dt_time, zz.dcription                                                          ";

					                                                                                                                                                                                                                                              
	                                                                                                                                                                                                                                                              
	//--------------------------------------	                                                                                                                                                                                                                  
					System.out.println("---BLENDFL---" + selQuery);                                                                                                                                                                                               
	                                                                                                                                                                                                                                                              
				} else if (act.getRadio().equalsIgnoreCase("BLENDCL")) {                                                                                                                                                                                          
					                                                                                                                                                                                                                                              
					type="Blending VatCL";                                                                                                                                                                                                                        
				                                                                                                                                                                                                                                                  
					selQuery =                                                                                                                                                                                                                                        
						                                                                                                                                                                                                                                          
				"		select  distinct zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                                                                              "+
				"		   case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl, " +
				"           case when zz.flg=true then    zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al from                                                                                                                                                                                                              "+
				"		   (select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm  ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                             "+
				"		   coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                              "+
				"		   coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                               "+
				"		   (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                                                           "+
				"		   ( select   b.dt_time , false as flg,b.txn_date as date, 'Transfer To CL Bottling Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                       "+
				"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
				"		   ,(b.recieve_bl+b.wastagebl) as cosum_bl, (b.recieve_al+b.wastageal) as cosum_al, " +
			  //"          (((b.recieve_bl*recieve_strength)/100)+b.wastagebl) as cosum_al,                                                                                                                                                        "+
				"		   0 as recv_bl,0 as recv_al,                                                                                                                                                                                                             "+
				"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		   from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                                                                                       "+
				"		   where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                  "+
				"		   and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
				"		   and     b.vat_no='"+act.getVatNo()+"'  	 and b.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                         "+
				//"		   union                                                                                                                                                                                                                                  "+
				//"		   select distinct   b.dt_time , false as flg,b.txn_date as date, 'BottlingVatForCLAction' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                             "+
				//"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
				//"		   ,(b.recieve_bl+b.wastagebl) as cosum_bl,(b.recieve_al+b.wastageal) as cosum_al,                                                                                                                                                        "+
				//"		   0 as recv_bl,0 as recv_al,                                                                                                                                                                                                             "+
				//"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				//"		   from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                                                                                       "+
				//"		   where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                  "+
				//"		   and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
				//"		   and     b.vat_no='"+act.getVatNo()+"'  	 and b.txn_date between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                          "+
				"		   union                                                                                                                                                                                                                                  "+
				"		   select  b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                    "+
				"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
				"		   ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                                          "+
				"		   b.qty_recv_bl as recv_bl, b.qty_recv_al  as recv_al,                                                                                                                                                                                  "+
				"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		   from distillery.spirit_for_bottling_cl a,distillery.transferspirit_to_cl_blending b ,distillery.vat_wastage v                                                                                                                                                         "+
				"		   where  a.storage_id=b.to_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                              "+
				"		   and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distillery_id='"+act.getLoginUserId()+"' and b.tansfer_dt=v.txn_date                                                                                                                                                                                      "+
				"		   and     b.to_vat_no='"+act.getVatNo()+"' and  v.type='CL_BLN_WASTAGE' and   v.vat_des='F'  	 and b.tansfer_dt between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                  "+

				"		   union                                                                                                                                                                                                                                  "+
				"		   select  b.dt_time,b.data_flg as flg,b.date_created as date, 'Prepration Of CL Blend ' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                     "+
				"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
				"		   ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                  "+
				"		   b.produced_bl as recv_bl,b.produced_al    as recv_al,                                                                                                                                                                                                           "+
				"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		   from distillery.spirit_for_bottling_cl a,distillery.country_liquor_blending b                                                                                                                                                          "+
				"		   where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                              "+
				"		   and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
				"		   and     b.int_vat_no='"+act.getVatNo()+"'  	 and b.date_created between   '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  )x                                                                             "+
				"		   union                                                                                                                                                                                                                                  "+
				"		   select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm  ,coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                              "+
				"		   coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                              "+
				"		   coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                               "+
				"		   (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                                                           "+
				"		   ( select   b.dt_time , false as flg,b.txn_date as date, 'Transfer To CL Bottling Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                       "+
				"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
				"		   ,(b.recieve_bl+b.wastagebl) as cosum_bl,(b.recieve_al+b.wastageal) as cosum_al," +
			//	"          (((b.recieve_bl*recieve_strength)/100)+b.wastagebl) as cosum_al,                                                                                                                                                        "+
				"		   0 as recv_bl,0 as recv_al,                                                                                                                                                                                                             "+
				"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		   from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                                                                                       "+
				"		   where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                  "+
				"		   and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
				"		   and     b.vat_no='"+act.getVatNo()+"'  	 and b.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                              "+
			//	"		   union                                                                                                                                                                                                                                  "+
			//	"		   select distinct b.dt_time , false as flg,b.txn_date as date, 'BottlingVatForCLAction' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                             "+
			//	"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
			//	"		   ,(b.recieve_bl+b.wastagebl) as cosum_bl,(b.recieve_al+b.wastageal) as cosum_al,                                                                                                                                                        "+
			//	"		   0 as recv_bl,0 as recv_al,                                                                                                                                                                                                             "+
			//	"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
			//	"		   from distillery.spirit_for_bottling_cl a,distillery.master_bottoling_of_vat_cl b                                                                                                                                                       "+
			//	"		   where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                  "+
			//	"		   and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
			//	"		   and     b.vat_no='"+act.getVatNo()+"'  	 and b.txn_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                              "+
				"		   union                                                                                                                                                                                                                                  "+
				"		   select   b.dt_time , false as flg,b.tansfer_dt as date, 'Transfer of Spirit to CL Blending Vat' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                    "+
				"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
				"		   ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                                          "+
				"		   b.qty_recv_bl as recv_bl, b.qty_recv_al  as recv_al,                                                                                                                                                                                  "+
				"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		   from distillery.spirit_for_bottling_cl a,distillery.transferspirit_to_cl_blending b ,distillery.vat_wastage v                                                                                                                                                         "+
				"		   where  a.storage_id=b.to_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                              "+
				"		   and b.to_vat_no= v.vat_no and b.distillery_id=v.unit_id::int and a.int_distillery_id='"+act.getLoginUserId()+"'   and b.tansfer_dt=v.txn_date                                                                                                                                                                                    "+
				"		   and     b.to_vat_no='"+act.getVatNo()+"' and  v.type='CL_BLN_WASTAGE' and   v.vat_des='F' 	 and b.tansfer_dt between  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                 "+
				"		   union                                                                                                                                                                                                                                  "+
				"		   select b.dt_time,b.data_flg as flg, b.date_created as date, 'Prepration Of CL Blend ' as dcription ,a.openingal,a.openingbl,a.storage_id as int_id,                                                                                                     "+
				"		   a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name                                                                                                                                                               "+
				"		   ,0 as cosum_bl,0 as cosum_al,                                                                                                                                                                                  "+
				"		   b.produced_bl as recv_bl,b.produced_al  as recv_al,                                                                                                                                                                                                           "+
				"		   0 as vat_wastage_bl,0 as vat_wastage_al                                                                                                                                                                                                "+
				"		   from distillery.spirit_for_bottling_cl a,distillery.country_liquor_blending b                                                                                                                                                          "+
				"		   where  a.storage_id=b.int_vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                              "+
				"		   and a.int_distillery_id='"+act.getLoginUserId()+"'                                                                                                                                                                                     "+
				"		   and     b.int_vat_no='"+act.getVatNo()+"'  	 and b.date_created between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  )x    )zz     order by zz.date , zz.dt_time, zz.dcription     ";
		

	//------------------------------				

					System.out.println("---BLEND=CL---" + selQuery);
	                                                                                                                                                                                                                                                                                            
				} else if (act.getRadio().equalsIgnoreCase("BOTFL")) {                                                                                                                                                                                                                          
					                                                                                                                                                                                                                                                                            
					type="Bottling VatFL";                                                                                                                                                                                                                                                      
					                                                                                                                                                                                                                                                                            
	                                                                                                                                                                                                                                                                                            
					selQuery =                                                                                                                                                                                                                                                                  
							                                                                                                                                                                                                                                                                   
						"	 select  distinct zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,zz.vat_wastage_al,                                                                                                      "+
						"	 case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl ,case when zz.flg=true then    zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al from                                                                                                                                                                                                                                          "+
						"	 (select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,                                                                                                                                                                    "+
						"	 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                                                         "+
						"	 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                                          "+
						"	 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                           "+
						"	 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                                                                                       "+
						"	 (select distinct   b.dt_time , false as flg,b.txn_date as date ,'Transfer To Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                              "+
						"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
						"	 0 as cosum_bl,  0 as cosum_al, b.recieve_bl as recv_bl,                                                                                                                                                                                                          "+
						"	 b.recieve_al as recv_al,                                                                                                                                                                                                                                         "+
						"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
						"	 from distillery.bottling_vat a, distillery.master_bottoling_of_vat b                                                                                                                                                                                               "+
						"	 where  a.storage_id=b.storage_id and  a.int_distillery_id=b.distillery_id                                                                                                                                                                                              "+
						"	 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.storage_id='"+act.getVatNo()+"'   and                                                                                                                                                                     "+
						"	 b.txn_date between   '2020-07-15'   and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                                                  "+
						"	 union                                                                                                                                                                                                                                                              "+
						"	 select distinct   c.dt_time , false as flg,c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Permises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                "+
						"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
						"	 (b.double_quantity_bl-b.double_wastg_bottling) as cosum_bl,  (b.double_al-b.double_wastage_al) as cosum_al,                                                                                                                                                        "+
						"	 0 as recv_bl,0 as recv_al,                                                                                                                                                                                                                                         "+
						"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
						"	 from distillery.bottling_vat a, distillery.bottling_dtl_20_21 b ,distillery.bottling_master_20_21 c                                                                                                                                                             "+
						"	 where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id                                                                                                                                                                                   "+
						"	 and b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id and a.int_distillery_id='"+act.getLoginUserId()+"' and                                                                                                                                               "+
						"	 b.blending_vat_id='"+act.getVatNo()+"'   and   c.date_currunt_date between   '2020-07-15'  and 		'"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' )X                                                                                                "+
						"	 union                                                                                                                                                                                                                                                              "+
						"	 select distinct   x.dt_time,    x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,x.vch_tank_name as tank_nm ,                                                                                                                                                                     "+
						"	 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                                                         "+
						"	 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                                          "+
						"	 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                           "+
						"	 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al  from                                                                                                                                                                       "+
						"	 (select distinct   b.dt_time , false as flg,b.txn_date as date ,'Transfer To Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                              "+
						"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
						"	 0 as cosum_bl,  0 as cosum_al, b.recieve_bl as recv_bl,                                                                                                                                                                                              "+
						"	 b.recieve_al as recv_al,                                                                                                                                                                                                                             "+
						"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
						"	 from distillery.bottling_vat a, distillery.master_bottoling_of_vat b                                                                                                                                                                                               "+
						"	 where  a.storage_id=b.storage_id and  a.int_distillery_id=b.distillery_id                                                                                                                                                                                              "+
						"	 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.storage_id='"+act.getVatNo()+"'   and   b.txn_date between                                                                                                                                                "+
						"	 '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and  '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                                             "+
						"	 union                                                                                                                                                                                                                                                              "+
						"	 select distinct   c.dt_time , false as flg,c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Permises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                "+
						"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
						"	 (b.double_quantity_bl-b.double_wastg_bottling) as cosum_bl,  (b.double_al-b.double_wastage_al) as cosum_al,                                                                                                                                                        "+
						"	 0 as recv_bl,0 as recv_al,                                                                                                                                                                                                                                         "+
						"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
						"	 from distillery.bottling_vat a, distillery.bottling_dtl_20_21 b ,distillery.bottling_master_20_21 c                                                                                                                                                             "+
						"	 where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id                                                                                                                                                                                   "+
						"	 and b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id and a.int_distillery_id='"+act.getLoginUserId()+"' and                                                                                                                                               "+
						"	 b.blending_vat_id='"+act.getVatNo()+"'   and                                                                                                                                                                                                                       "+
						"	 c.date_currunt_date between   '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'  and   '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   )X  )zz     order by zz.date , zz.dt_time, zz.dcription                                                                                 ";                         
	                                                                                                                                                                                                                                                                                            
							                                                                                                                                                                                                                                                                    
					                                                                                                                                                                                                                                                                            
	                                                                                                                                                                                                                                                                                            
					System.out.println("---Bottling FL---" + selQuery);                                                                                                                                                                                                                         
	                                                                                                                                                                                                                                                                                            
				} else if (act.getRadio().equalsIgnoreCase("BOTCL")) {                                                                                                                                                                                                                         
				                                                                                                                                                                                                                                                                                
					                                                                                                                                                                                                                                                                            
					type="Bottling VatCL";                                                                                                                                                                                                                                                      
					                                                                                                                                                                                                                                                                            
					selQuery =                                                                                                                                                                                                                                                                  
						                                                                                                                                                                                                                                                                        
						"	select  distinct zz.dt_time,zz.flg,zz.date, zz.dcription ,zz.int_id,zz.int_distilleri_id,zz.tank_nm ,zz.cosum_bl,zz.cosum_al,   zz.recv_bl, zz.recv_al,zz.vat_wastage_bl,                                                                                                                           "+
						"	 zz.vat_wastage_al,                                                                                                                                                                                                                                                 "+
						"	case when zz.flg=true then   zz.recv_bl  when zz.flg=false then zz.bal_bl end as bal_bl ,case when zz.flg=true then    zz.recv_al  when zz.flg=false then zz.bal_al end as bal_al from                                                                                                                                                                                                                                          "+
						"	 (select distinct x.dt_time,	x.flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,                                                                                                                                                                                                      "+
						"	 x.vch_tank_name as tank_nm ,                                                                                                                                                                                                                                       "+
						"	 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                                                         "+
						"	 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                                          "+
						"	 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                           "+
						"	 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                                                        "+
						"	 (select distinct   b.dt_time , false as flg,b.txn_date as date ,'Transfer To CL Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                     "+
						"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
						"	 0 as cosum_bl,  0 as cosum_al,b.recieve_bl as recv_bl, b.recieve_al as recv_al,                                                                                                                                                                                                            "+
				//		"	(((b.recieve_bl*b.recieve_strength)/100)+b.wastageal) as recv_al,                                                                                                                                                                                                                                           "+
						"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
						"	 from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                                                                                                                                         "+
						"	 where  a.storage_id=b.storage_id and  a.int_distillery_id=b.distillery_id                                                                                                                                                                                              "+
						"	 and a.int_distillery_id='"+act.getLoginUserId()+"'  and     b.storage_id='"+act.getVatNo()+"'      and                                                                                                                                                                 "+
						"	 b.txn_date between   '2020-07-15' and  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'                                                                                                                                                                   "+
						//"	 union                                                                                                                                                                                                                                                              "+
						//"	 select distinct   b.dt_time , false as flg,b.txn_date as date ,'BottlingVatForCLAction' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                          "+
						//"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
						//"	 0 as cosum_bl,  0 as cosum_al, b.recieve_bl as recv_bl,                                                                                                                                                                                                            "+
						//"	 b.recieve_al as recv_al,                                                                                                                                                                                                                                           "+
						//"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
						//"	 from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                                                                                                                                         "+
						//"	 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                                              "+
						//"	 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.vat_no='"+act.getVatNo()+"'    and                                                                                                                                                                    "+
						//"	 b.txn_date between   '2020-07-15'   and  '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'	                                                                                                                                                                "+
						"	 union                                                                                                                                                                                                                                                              "+
						"	 select distinct   c.dt_time , false as flg,c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Premises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                     "+
						"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
						"	 (b.double_quantity_bl+b.double_wastg_bottling) as cosum_bl, (b.double_al+b.double_wastage_al) as cosum_al, 0 as recv_bl,                                                                                                                                                   "+
						"	 0 as recv_al,                                                                                                                                                                                                                                                      "+
						"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
						"	 from distillery.bottling_vat_cl a, distillery.bottling_dtl_cl_20_21 b ,distillery.bottling_master_cl_20_21 c                                                                                                                                                       "+
						"	 where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id  and   b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id                                                                                                                  "+
						"	 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.blending_vat_id='"+act.getVatNo()+"'                                                                                                                                                                  "+
						"	 and c.date_currunt_date between     '2020-07-15'  and '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' ) X	                                                                                                                                            "+
						"	 union                                                                                                                                                                                                                                                              "+
						"	 select distinct	x.dt_time,false as flg,x.date, x.dcription ,x.int_id,x.int_distilleri_id,                                                                                                                                                                                                      "+
						"	 x.vch_tank_name as tank_nm ,                                                                                                                                                                                                                                       "+
						"	 coalesce(x.cosum_bl,0.0) as cosum_bl,coalesce(x.cosum_al,0.0) as cosum_al,                                                                                                                                                                                         "+
						"	 coalesce(x.recv_bl,0.0) as recv_bl,coalesce(x.recv_al,0.0) as  recv_al,coalesce(x.vat_wastage_bl,0.0) as  vat_wastage_bl,                                                                                                                                          "+
						"	 coalesce(x.vat_wastage_al,0.0) as vat_wastage_al,(coalesce(recv_bl,0.0)-coalesce(cosum_bl,0.0)-coalesce(vat_wastage_bl,0.0))  as bal_bl,                                                                                                                           "+
						"	 (coalesce(recv_al,0.0)-coalesce(cosum_al,0.0)-coalesce(vat_wastage_al,0.0))  as bal_al from                                                                                                                                                                        "+
						"	 (select distinct   b.dt_time , false as flg,b.txn_date as date ,'Transfer To CL Bottling Vat' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                     "+
						"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
						"	 0 as cosum_bl,  0 as cosum_al,b.recieve_bl as recv_bl, b.recieve_al as recv_al,                                                                                                                                                                                                              "+
				//		"	 (((b.recieve_bl*b.recieve_strength)/100)+b.wastageal) as recv_al,                                                                                                                                                                                                                                           "+
						"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
						"	 from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                                                                                                                                         "+
						"	 where  a.storage_id=b.storage_id and  a.int_distillery_id=b.distillery_id                                                                                                                                                                                              "+
						"	 and a.int_distillery_id='"+act.getLoginUserId()+"'  and     b.storage_id='"+act.getVatNo()+"'      and   b.txn_date between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'    and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                "+
						//"	 union                                                                                                                                                                                                                                                              "+
						///"	 select distinct   b.dt_time , false as flg,b.txn_date as date ,'BottlingVatForCLAction' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                          "+
						//"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
						//"	 0 as cosum_bl,  0 as cosum_al, b.recieve_bl as recv_bl,                                                                                                                                                                                                            "+
						//"	 b.recieve_al as recv_al,                                                                                                                                                                                                                                           "+
						//"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
						//"	 from distillery.bottling_vat_cl a, distillery.master_bottoling_of_vat_cl b                                                                                                                                                                                         "+
						//"	 where  a.storage_id=b.vat_no and  a.int_distillery_id=b.distillery_id                                                                                                                                                                                              "+
						//"	 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.vat_no='"+act.getVatNo()+"'                                                                                                                                                                           "+
						//"	 and b.txn_date between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'                                                                                                                  "+
						"	 union                                                                                                                                                                                                                                                              "+
						"	 select distinct   c.dt_time , false as flg,c.date_currunt_date as date ,'Bottling Operations Carried on in the Licensed Bottling Premises At Distillery' as dcription,a.openingbl, a.openingal,a.storage_id as int_id                                                                                                                                     "+
						"	 ,a.int_distillery_id as int_distilleri_id,a.storage_desc as vch_tank_name,                                                                                                                                                                                         "+
						"	 (b.double_quantity_bl+b.double_wastg_bottling) as cosum_bl, (b.double_al+b.double_wastage_al) as cosum_al, 0 as recv_bl,                                                                                                                                                   "+
						"	 0 as recv_al,                                                                                                                                                                                                                                                      "+
						"	 0 as vat_wastage_bl,0  as vat_wastage_al                                                                                                                                                                                                                           "+
						"	 from distillery.bottling_vat_cl a, distillery.bottling_dtl_cl_20_21 b ,distillery.bottling_master_cl_19_20 c                                                                                                                                                       "+
						"	 where  a.storage_id=b.blending_vat_id and  a.int_distillery_id=b.int_dissleri_id and   b.int_id=c.int_id and b.int_dissleri_id=c.int_dissleri_id                                                                                                                   "+
						"	 and a.int_distillery_id='"+act.getLoginUserId()+"' and     b.blending_vat_id='"+act.getVatNo()+"'    and                                                                                                                                                           "+
						"	 c.date_currunt_date between    '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"'   and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'         ) X		 )zz     order by zz.date , zz.dt_time, zz.dcription    "      ;                                                         
								
					System.out.println("---Bottling CL---" + selQuery);

				}
					 //System.out.println("------- show data table   -------"+query);

					conn = ConnectionToDataBase.getConnection();
					pstmt = conn.prepareStatement(selQuery);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						
						
						
						
						
						/*TankWise_StcoklistDt dt = new TankWise_StcoklistDt();
				      
						dt.setSrNo(j++);
						dt.setDate(rs.getDate("date"));
						dt.setDiscription(rs.getString("dcription"));
						dt.setWast_al(rs.getDouble("vat_wastage_bl"));
						dt.setWast_bl(rs.getDouble("vat_wastage_al"));
						dt.setRecv_al(rs.getDouble("recv_al"));
						dt.setRecv_bl(rs.getDouble("recv_bl"));
						dt.setCosum_al(rs.getDouble("cosum_al"));
						dt.setCosum_bl(rs.getDouble("cosum_bl"));
						dt.setTank_nm(rs.getString("tank_nm"));
						dt.setBal_al(rs.getDouble("bal_al"));
						dt.setBal_bl(rs.getDouble("bal_bl"));
						
						bal_al = op_al+rs.getDouble("bal_al");
						bal_bl = op_bl+rs.getDouble("bal_bl");
						
						System.out.println("------- bal_al   -------"+bal_al);
						System.out.println("------- bal_bl   -------"+bal_bl);
						if(rs.getBoolean("flg") == false){
							dt.setTot_al(bal_al+op_al);
							dt.setTot_bl(bal_bl+op_bl);
						}
						
						
						else {
							dt.setTot_al(rs.getDouble("bal_al"));
							dt.setTot_bl(rs.getDouble("bal_bl"));
						}

						
						if(i==0 && rs.getBoolean("flg") == false){
							dt.setTot_al(bal_al);
							dt.setTot_bl(bal_bl);
						}
						else if(i>0 && rs.getBoolean("flg") == false){
							//dt.setTot_al(bal_al);
							//dt.setTot_bl(bal_bl);
							dt.setTot_al(bal_al-dt.getTot_al());
							dt.setTot_bl(bal_bl-dt.getTot_bl());
						}
						else if(i>0 && rs.getBoolean("flg") == true){
							dt.setTot_al(rs.getDouble("bal_al"));
							dt.setTot_bl(rs.getDouble("bal_bl"));
						}*/
						
						TankWise_StcoklistDt dt = new TankWise_StcoklistDt();
					      
						dt.setSrNo(j++);
						dt.setDate(rs.getDate("date"));
						dt.setDiscription(rs.getString("dcription"));
						dt.setWast_al(rs.getDouble("vat_wastage_bl"));
						dt.setWast_bl(rs.getDouble("vat_wastage_al"));
						dt.setRecv_al(rs.getDouble("recv_al"));
						dt.setRecv_bl(rs.getDouble("recv_bl"));
						dt.setCosum_al(rs.getDouble("cosum_al"));
						dt.setCosum_bl(rs.getDouble("cosum_bl"));
						dt.setTank_nm(rs.getString("tank_nm"));
						dt.setBal_al(rs.getDouble("bal_al"));
						dt.setBal_bl(rs.getDouble("bal_bl"));
						////=-------------------------------------------
						bal_al = bal_al+rs.getDouble("bal_al");
						bal_bl = bal_bl+rs.getDouble("bal_bl");
						
					
						if( i==0 && rs.getBoolean("flg") == false){
							dt.setTot_al(bal_al+op_al);
							dt.setTot_bl(bal_bl+op_bl);
							act.setTotal_al(dt.getTot_al());
							act.setTotal_bl(dt.getTot_bl());
							System.out.println("===tesdt1111=====false="+act.getTotal_al());
							System.out.println("===tesdt1111=====false="+act.getTotal_bl());
							System.out.println("===tesdt111--i="+i);
					
						}else if( i>0 && rs.getBoolean("flg") == false){
							dt.setTot_al(act.getTotal_al()+rs.getDouble("bal_al"));
							dt.setTot_bl(act.getTotal_bl()+rs.getDouble("bal_bl"));
							act.setTotal_al(act.getTotal_al()+rs.getDouble("bal_al"));
							act.setTotal_bl(act.getTotal_bl()+rs.getDouble("bal_bl"));
							System.out.println("===tesdt1111====I=false="+dt.getTot_al());
							System.out.println("===tesdt1111===I==false="+dt.getTot_bl());
							System.out.println("===tesdt1111=act i====false="+act.getTotal_al());
							System.out.println("===tesdt1111==act i====false="+act.getTotal_bl());
							System.out.println("===tesdt111--i="+i);
					
						}
						else if(rs.getBoolean("flg") == true){
							dt.setTot_al(rs.getDouble("bal_al"));
							dt.setTot_bl(rs.getDouble("bal_bl"));
							act.setTotal_al(dt.getTot_al());
							act.setTotal_bl(dt.getTot_bl());
					
						}
						/*else if(rs.getBoolean("flg") == false && i>0 ){
							//dt.setTot_al(bal_al);
							//dt.setTot_bl(bal_bl);
							dt.setTot_al(dt.getBal_al()+act.getTotal_al());
							dt.setTot_bl(dt.getBal_bl()+act.getTotal_bl());
						}
						else if( rs.getBoolean("flg") == true && i>0 ){
							dt.setTot_al(rs.getDouble("bal_al"));
							dt.setTot_bl(rs.getDouble("bal_bl"));
							act.setTotal_al(dt.getTot_al());
							act.setTotal_bl(dt.getTot_bl());
						}*/
						////=-------------------------------------------
				/*		
					bal_al = bal_al+rs.getDouble("bal_al");
						bal_bl = bal_bl+rs.getDouble("bal_bl");
						
					
						if(i==0 && rs.getBoolean("flg") == false){
							bal_al = op_al+rs.getDouble("bal_al");
							bal_bl = op_bl+rs.getDouble("bal_bl");
							dt.setTot_al(bal_al);
							dt.setTot_bl(bal_bl);
							act.setTotal_al(dt.getTot_al());
							act.setTotal_bl(dt.getTot_bl());
					
						}
						else if(rs.getBoolean("flg") == false && i>0 ){
							bal_al_2 = act.getTotal_al()+rs.getDouble("bal_al");
							bal_bl_2 = act.getTotal_bl()+rs.getDouble("bal_bl");
							dt.setTot_al(bal);
							dt.setTot_bl(dt.getBal_bl()+act.getTotal_bl());
						}
						else if( rs.getBoolean("flg") == true && i>0 ){
							dt.setTot_al(rs.getDouble("bal_al"));
							dt.setTot_bl(rs.getDouble("bal_bl"));
							act.setTotal_al(dt.getTot_al());
							act.setTotal_bl(dt.getTot_bl());
						}*/


                     i++;
						list.add(dt);
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
	
}
