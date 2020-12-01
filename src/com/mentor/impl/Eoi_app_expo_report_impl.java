package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.faces.model.SelectItem;


import com.mentor.action.Eoi_app_expo_report_action;
import com.mentor.datatable.Eoi_app_expo_report_dt;
import com.mentor.resource.ConnectionToDataBase;


public class Eoi_app_expo_report_impl {
	
	public ArrayList getDistilleryList(Eoi_app_expo_report_action act) 
	
	{

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue(0);
		list.add(item);

		try {
			String query = " select dmppl.vch_undertaking_name ,dmppl.int_app_id_f from distillery.reg_of_distilleryasexpunit rod ," +
					       " public.dis_mst_pd1_pd2_lic dmppl where dmppl.int_app_id_f = rod.distillery_id and rod .approve_flag ='A' ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			// pstmt.setInt(1,id);
System.out.println("-------distillery list ==="+query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("int_app_id_f"));
				item.setLabel(rs.getString("vch_undertaking_name"));

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
	
//==============================
	
	public ArrayList beforeApproval(Eoi_app_expo_report_action action ) {
		
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	    int sr = 1 ; ;
	    try 
	    {

			String query = " select distinct hfdeau.leo_date,hfdeau .brc_no , hfdeau .brc_date ,hfdeau .riceipt_date , hfdeau .leo_no " +
				          ",gtm.vch_gatepass_no , gtm.curr_date ,gtm.vch_to_lic_no from distillery.historical_for_distillery_export_as_unit hfdeau" +
				          " ,distillery.gatepass_to_manufacturewholesale_20_21 gtm where gtm.vch_gatepass_no =hfdeau .getpass_no and  " +
				          " gtm.int_dist_id ='"+action.getDistilleryId()+"'";
					
			 conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();
			
			SimpleDateFormat formatter= new SimpleDateFormat("dd MMMM yyyy");
		
			
			System.out.println("====query========"+query);

			while (rs.next()) {
				
			
				
				
				Eoi_app_expo_report_dt  dt = new Eoi_app_expo_report_dt();
				
				dt.setSr_no(sr);
				dt.setExp_no(rs.getDate("leo_date"));
				dt.setFlb11_no(rs.getString("vch_gatepass_no"));
				dt.setFlb11_dt(rs.getDate("curr_date"));
				dt.setShipp_no(rs.getString("leo_no"));
				dt.setBond_dt(rs.getDate("riceipt_date"));
				dt.setBrc_no(rs.getString("brc_no"));
				dt.setBrc_dt(rs.getDate("brc_date"));
				sr++;
										
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
	
//=====================================
	
public ArrayList afterApproval(Eoi_app_expo_report_action action ) {
		
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	    int sr = 1 ; ;
	    try 
	    {

			String query = " select a.seq_pk ,a.created_date ,a.importing_unit_nm ,cm.vch_country_name from " +
					       " distillery.eoi_import_order_master a , public.country_mst cm " +
					       " where a.country_id = cm.int_country_id and a.int_dist_id ='"+action.getDistilleryId()+"'";
					
			 conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();
			
			SimpleDateFormat formatter= new SimpleDateFormat("dd MMMM yyyy");
		
			
			System.out.println("====query==1======"+query);

			while (rs.next()) {
				
				
				
				Eoi_app_expo_report_dt  dt = new Eoi_app_expo_report_dt();
				
				dt.setSr_n(sr);
				dt.setImp_no(rs.getString("seq_pk"));
				dt.setImp_dt(rs.getDate("created_date"));
				dt.setCountry(rs.getString("vch_country_name"));
				dt.setImport_unit(rs.getString("importing_unit_nm"));
				
				sr++;
										
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

//===================================

public ArrayList viewImpl(Eoi_app_expo_report_action action , String imp_no  ) {
	
	ArrayList list = new ArrayList();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
    int sr = 1 ; ;
    try 
    {

		String query = " select eafeo.app_id , eafeo.created_date  from distillery.eoi_app_for_export_order eafeo ," +
				       " distillery.eoi_import_order_master a where eafeo.app_id = a.seq_pk and eafeo.int_dist_id ='"+action.getDistilleryId()+"' and a.seq_pk ='"+imp_no+"'   ";
				
		 conn = ConnectionToDataBase.getConnection();
		pstmt = conn.prepareStatement(query);

		rs = pstmt.executeQuery();
		
		SimpleDateFormat formatter= new SimpleDateFormat("dd MMMM yyyy");
	
		
		System.out.println("====query===2====="+query);

		while (rs.next()) {
			
			
			
			
			Eoi_app_expo_report_dt  dt = new Eoi_app_expo_report_dt();
			
			dt.setSr(sr);
			dt.setEx_no(rs.getString("app_id"));
			dt.setEx_dt(rs.getDate("created_date"));
			sr++;
									
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

//=====================================

public ArrayList viewImpl_new(Eoi_app_expo_report_action action , String Expo_no ) {
	
	ArrayList list = new ArrayList();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
    int sr = 1 ; ;
    try 
    {

		String query = " select gtm.vch_gatepass_no ,gtm.rcvdt ,gtm.shipping_bill_no ,a.brc_number,a.brc_date,gtm.vch_to_lic_no,gtm.curr_date  from distillery.gatepass_to_manufacturewholesale_20_21 gtm , " +
				       " distillery.eoi_app_for_export_order a where a.app_id::varchar=gtm.vch_to_lic_no and " +
				       " gtm.int_dist_id ='"+action.getDistilleryId()+"' and gtm.vch_to_lic_no='"+Expo_no+"'                                                 ";
				
		conn = ConnectionToDataBase.getConnection();
		pstmt = conn.prepareStatement(query);

		rs = pstmt.executeQuery();
		
		System.out.println("====query=====3==="+query);

		while (rs.next()) {
			
			
			
			
			Eoi_app_expo_report_dt  dt = new Eoi_app_expo_report_dt();
			
			dt.setSro(sr);
			dt.setExport_no(rs.getString("vch_to_lic_no"));
			dt.setFlb_no(rs.getString("vch_gatepass_no"));
			dt.setFlb1_dt(rs.getDate("curr_date"));
			dt.setShipping_no(rs.getString("shipping_bill_no"));
			dt.setBrc_no(rs.getString("brc_number"));
			dt.setBrcc_dt(rs.getDate("brc_date"));
			dt.setBond_recdt(rs.getDate("rcvdt"));
			sr++;
									
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
