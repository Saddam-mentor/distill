package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


import com.mentor.action.Gatepass_Search_Action;
import com.mentor.datatable.Gatepass_Search_DataTable;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Utility;

public class Gatepass_Search_Impl
{
	
	public ArrayList getDataFromimpl(
			Gatepass_Search_Action action) 
	{
		ArrayList list = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query ="";
		
		
		if(action.getVch().equalsIgnoreCase("D"))
		{
		 query =" SELECT  a.int_dist_id as id, "+
				" a.dt_date,  a.vch_from,  a.vch_to,  a.vch_from_lic_no, "+
				"  a.vch_to_lic_no,   a.curr_date,  "+
				"  a.int_max_id,  a.vch_gatepass_no , "+
				
				" a.vch_route_detail, a.vch_vehicle_no, a.vehicle_driver_name, "+
				" a.vehicle_agency_name_adrs, a.licensee_name, a.licensee_adrs, "+
				
				"  b.int_brand_id, b.int_pckg_id, "+
				"  b.dispatch_box,b.dispatch_bottle, "+
				"  (select brand_name from distillery.brand_registration_19_20 where brand_id=b.int_brand_id)  "+
				"  as brand_name, "+
				"  (select package_name from distillery.packaging_details_19_20 where package_id= b.int_pckg_id) "+
				"  as package_name "+
                
                 
				"  FROM  "+
				" distillery.gatepass_to_districtwholesale_19_20 a, "+
				"  distillery.fl2_stock_trxn_19_20 b "+
                
				"  where  a.vch_gatepass_no='"+action.getGatepassNo().trim()+"' " +
						" and dt_date='"+Utility.convertUtilDateToSQLDate(action.getGatepass_date())+"' "+
               "  and  a.vch_finalize='F' and b.int_dissleri_id=a.int_dist_id "+
               "  and a.vch_gatepass_no=b.vch_gatepass_no "
               ;
		}         
		if(action.getVch().equalsIgnoreCase("B"))
		{
			query=" SELECT a.brewery_id as id, a.vch_gatepass_no, a.dt_date, a.vch_from," +
					" a.vch_to, a.vch_from_lic_no, "+
				  " a.vch_to_lic_no, a.curr_date, a.int_max_id," +
				  " a.licencee_id, a.vch_route_detail,a.vch_vehicle_no ," +
				  " a.vehicle_driver_name, a.vehicle_agency_name_adrs, a.licensee_name, a.licensee_adrs,"+
					"   b.dispatch_box,b.dispatch_bottle, b.int_brand_id, b.int_pckg_id,"+
					"    (select brand_name from distillery.brand_registration_19_20 where brand_id=b.int_brand_id)  "+
					"              as brand_name,"+
					"             (select package_name from distillery.packaging_details_19_20 where package_id= b.int_pckg_id) "+
					"             as package_name"+
					" 			FROM bwfl.gatepass_to_districtwholesale_19_20  a,"+
					"          bwfl.fl2_stock_trxn_19_20 b "+
			  
					"        WHERE  a.vch_gatepass_no='"+action.getGatepassNo().trim()+"' and" +
							"  a.dt_date='"+Utility.convertUtilDateToSQLDate(action.getGatepass_date())+"' "+
                		"     and    a.vch_finalize='F' and b.brewery_id=a.brewery_id and "+
					"       a.vch_gatepass_no=b.vch_gatepass_no";
                
              
		}
		
		if(action.getVch().equalsIgnoreCase("BWFL"))
		{		
        
			query=" 	SELECT   a.int_bwfl_id as id,a.dt_date,  a.vch_to,  a.vch_from,a.vch_to_lic_no," +
					"   a.curr_date, a.vch_gatepass_no,  a.seq , " +
					"   a.vch_finalize  , a.vch_route_detail,a.vch_vehicle_no ," +
					"   a.vehicle_driver_name, a.vehicle_agency_name_adrs, a.licensee_name," +
					"   a.licensee_adrs,  b.dispatch_box,b.dispatch_bottle, " +
					"    (select brand_name from distillery.brand_registration_19_20 where brand_id=b.int_brand_id) " +
					"   as brand_name,       " +    
					"   	(select package_name from distillery.packaging_details_19_20 where package_id= b.int_pckg_id) " +
					"   as package_name " +
					"    FROM bwfl_license.gatepass_to_districtwholesale_bwfl_19_20 a," +
					"   bwfl_license.fl2_stock_trxn_bwfl_19_20 b" +

					"   WHERE  " +
					"   a.vch_finalize='F' and a.vch_gatepass_no='"+action.getGatepassNo().trim()+"' and" +
							"  a.dt_date='"+Utility.convertUtilDateToSQLDate(action.getGatepass_date())+"' " +
					"   and  a.vch_gatepass_no=b.vch_gatepass_no and " +
					"   a.int_bwfl_id=b.int_bwfl_id " ;
		}
		
		
		if(action.getVch().equalsIgnoreCase("FL2D"))
		{		
        	
			query=" 	select     a.vch_finalize,a.int_fl2d_id as id  , a.dt_date,  a.vch_from, a.vch_to, " +
					"  a.vch_from_lic_no,  a.vch_to_lic_no,  a.curr_date,  " + 
					"  a.vch_gatepass_no,  a.vch_route_detail,  a.vch_vehicle_no, a.vehicle_driver_name,  " +
					"   a.vehicle_agency_name_adrs,  a.licensee_name,  a.licensee_adrs,  " +                       
					"    b.int_brand_id,  b.int_pckg_id, b.dispatch_box,b.dispatch_bottle" +
					"     ,  " +
					"  (select brand_name from distillery.brand_registration_19_20 where brand_id=b.int_brand_id) " +
					"  as brand_name,           " +
					"  (select package_name from distillery.packaging_details_19_20 where package_id= b.int_pckg_id) " +
					"  as package_name " +
					"      from            fl2d.gatepass_to_districtwholesale_fl2d_19_20 a," +
					"               fl2d.fl2d_stock_trxn_19_20 b " +
					"   where a.int_fl2d_id =b.int_fl2d_id and  a.vch_gatepass_no= b.vch_gatepass_no " +
					"   and  a.vch_gatepass_no='"+action.getGatepassNo().trim()+"'  and" +
					"  a.dt_date='"+Utility.convertUtilDateToSQLDate(action.getGatepass_date())+"'  " +
					" and   a.vch_finalize='F' ";
          
          
		}
                           
	     
		if(action.getVch().equalsIgnoreCase("WS"))
		{		
        	
			query=" select a.int_fl2_fl2b_id as id, a.vch_gatepass_no, a.dt_date, a.vch_to,a.vch_from,a.vch_to_lic_no, "+
					 " a.vch_route_detail, a.vch_vehicle_no, a.vehicle_driver_name,a. vehicle_agency_name_adrs,  "+
					 " a.licensee_name, a.licensee_adrs, "+
					 " b.dispatch_box,b.dispatch_bottle "+
					 " ,    "+
					 " (select brand_name from distillery.brand_registration_19_20 where brand_id=b.int_brand_id)  "+
					 " as brand_name,            "+
					 " (select package_name from distillery.packaging_details_19_20 where package_id= b.int_pckg_id)  "+
					 " as package_name  "+
        
					 "  from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a, "+
					 "    fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b "+
         
					 "   where a.int_fl2_fl2b_id=b.int_fl2_fl2b_id and a.vch_gatepass_no=b.vch_gatepass_no "+
					 "   and  a.vch_finalize='F' "+ 
					 "   and a.vch_gatepass_no='"+action.getGatepassNo().trim()+"'  and  " +
					 " a.dt_date='"+Utility.convertUtilDateToSQLDate(action.getGatepass_date())+"' ";
         
		
		
		}
		
		
		
		
		System.out.println("==query=="+query);
		try {
			list = new ArrayList();
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			
			

			rs = ps.executeQuery();
			int i = 1;
			
			int j=0;
			
			while (rs.next()) 
			{
				Gatepass_Search_DataTable dt = new Gatepass_Search_DataTable();
				
				dt.setSno(i);
				j=1;
				action.setInt_dist_id(rs.getInt("id"));
				action.setDt_date(rs.getDate("dt_date"));
				action.setVch_from(rs.getString("vch_from"));
				action.setVch_to(rs.getString("vch_to"));
				
				
				if(!action.getVch().equalsIgnoreCase("BWFL") && !action.getVch().equalsIgnoreCase("WS"))
				{
				action.setVch_from_lic_no(rs.getString("vch_from_lic_no"));
				}else
				{
					action.setVch_from_lic_no("NA");
				}
				
				action.setVch_to_lic_no(rs.getString("vch_to_lic_no"));
				action.setVch_gatepass_no(rs.getString("vch_gatepass_no"));
		
				action.setLicenceenm(rs.getString("licensee_name"));
				action.setLicenceeadd(rs.getString("licensee_adrs"));
				
				action.setRouteDtl(rs.getString("vch_route_detail"));
				action.setVehicleNo(rs.getString("vch_vehicle_no"));
				
				action.setVehicleDrvrName(rs.getString("vehicle_driver_name"));//
				action.setVehicleAgencyNmAdrs(rs.getString("vehicle_agency_name_adrs"));//
				
				dt.setBrandName(rs.getString("brand_name"));
				dt.setPackage_name(rs.getString("package_name"));
				dt.setDispatch_bottle(rs.getInt("dispatch_bottle"));
				dt.setDispatch_box(rs.getInt("dispatch_box"));
				
				list.add(dt);
				i++;
			}
			System.out.println("	if(list.size()==0)===== "+list.size());
			if(list.size()==0)
			{
				action.setFlag(false);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found On This Gatepass And Selected Date", 
											"No Data Found On This Gatepass And Selected Date"));
			}else
			{
				action.setFlag(true);
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
		return list;
	}

	
	
	public ArrayList getCaseCodeDis(Gatepass_Search_Action action) 
	{
		String casecode=null;
		ArrayList list = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query="";
		
		
	if (action.getLicenceType().equalsIgnoreCase("FL3")) 
		{
			query = " SELECT etin, casecode,date_plan "+
    				" FROM bottling_unmapped.disliry_unmap_fl3 "+
    				"  where fl36gatepass='"+action.getVch_gatepass_no()+"' ";
		}
		else if (action.getLicenceType().equalsIgnoreCase("FL3A")) 
		{
			query = " SELECT etin, casecode,date_plan "+
    				" FROM bottling_unmapped.disliry_unmap_fl3a "+
    				"  where fl36gatepass='"+action.getVch_gatepass_no()+"' ";
    				
		}else if (action.getLicenceType().equalsIgnoreCase("CL")  ) 
		{
			query =  " SELECT date_plan, etin, casecode  "+
					" FROM bottling_unmapped.disliry_unmap_cl "+
				    " where fl36gatepass='"+action.getVch_gatepass_no()+"' ";
		}
		
		try {
			list = new ArrayList();
			con = ConnectionToDataBase.getConnection19_20();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			int i = 1;
			while (rs.next()) 
			{
				Gatepass_Search_DataTable dt = new Gatepass_Search_DataTable();
				dt.setSnoK(i);
				dt.setCaseCode(rs.getString("casecode"));
				dt.setEtinNo(rs.getString("etin"));
				dt.setPlnDt(rs.getString("date_plan"));
				list.add(dt);
				i++;	
			}
			System.out.println("	if(list.size()==0)===== "+list.size());
			if(list.size()==0)
			{
				action.setGetCaseCodeFlag(false);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found On This Gatepass",
								"No Data Found On This Gatepass"));
			}else
			{
				action.setGetCaseCodeFlag(true);
			
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
		return list;
	}
	
	public ArrayList getCaseCodeBre(Gatepass_Search_Action action) 
	{
		String casecode=null;
		ArrayList list = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query="";
		
		
	if (action.getLicenceType().equalsIgnoreCase("FL3")) 
		{
			query = " SELECT date_plan, etin, casecode " +
					" FROM bottling_unmapped.brewary_unmap_fl3 "+
   
    				"  where fl36gatepass='"+action.getVch_gatepass_no()+"' ";
		}
		else if (action.getLicenceType().equalsIgnoreCase("FL3A")) 
		{
			query = " SELECT date_plan, etin, casecode " +
					" FROM bottling_unmapped.brewary_unmap_fl3a "+
   
    				"  where fl36gatepass='"+action.getVch_gatepass_no()+"' ";
    				
		}
	
	
	
		
		try {
			list = new ArrayList();
			con = ConnectionToDataBase.getConnection19_20();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			int i = 1;
			while (rs.next()) 
			{
				Gatepass_Search_DataTable dt = new Gatepass_Search_DataTable();
				dt.setSnoK(i);
				dt.setCaseCode(rs.getString("casecode"));
				dt.setEtinNo(rs.getString("etin"));
				dt.setPlnDt(rs.getString("date_plan"));
				list.add(dt);
				i++;	
			}
			System.out.println("	if(list.size()==0)===== "+list.size());
			if(list.size()==0)
			{
				action.setGetCaseCodeFlag(false);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found On This Gatepass",
								"No Data Found On This Gatepass"));
			}else
			{
				action.setGetCaseCodeFlag(true);
			
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
		return list;
	}
	public ArrayList getCaseCodeBWFL_FL2D(Gatepass_Search_Action action) 
	{
		String casecode=null;
		ArrayList list = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query="";
		
		
	if (action.getVch().equalsIgnoreCase("BWFL")) 
		{
			query = " SELECT date_plan, etin, casecode " +
					" FROM bottling_unmapped.bwfl "+
   
    				"  where fl36gatepass='"+action.getVch_gatepass_no()+"' ";
		}
		else if (action.getVch().equalsIgnoreCase("FL2D")) 
		{
			query = " SELECT date_plan, etin, casecode " +
					" FROM bottling_unmapped.fl2d "+
    				"  where fl36gatepass='"+action.getVch_gatepass_no()+"' ";
    				
		}
		
		
		try {
			list = new ArrayList();
			con = ConnectionToDataBase.getConnection19_20();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			int i = 1;
			while (rs.next()) 
			{
				Gatepass_Search_DataTable dt = new Gatepass_Search_DataTable();
				dt.setSnoK(i);
				dt.setCaseCode(rs.getString("casecode"));
				dt.setEtinNo(rs.getString("etin"));
				dt.setPlnDt(rs.getString("date_plan"));
				list.add(dt);
				i++;	
			}
			System.out.println("	if(list.size()==0)===== "+list.size());
			if(list.size()==0)
			{
				action.setGetCaseCodeFlag(false);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found On This Gatepass",
								"No Data Found On This Gatepass"));
			}else
			{
				action.setGetCaseCodeFlag(true);
			
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
		return list;
	}
	public ArrayList getCaseCodeWS(Gatepass_Search_Action action) 
	{
		String casecode=null;
		ArrayList list = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query="";
		
		
	if (action.getLicenceType().equalsIgnoreCase("CL2")) 
		{
			query = " SELECT date_plan, etin, casecode " +
					" FROM bottling_unmapped.disliry_unmap_cl "+
   
    				"  where ws_gatepass='"+action.getVch_gatepass_no()+"' ";
		}
		/*else if (action.getLicenceType().equalsIgnoreCase("FL2")) 
		{
			query = " SELECT date_plan, etin, casecode " +
					" FROM bottling_unmapped.fl2_unmap "+
    				"  where fl36gatepass='"+action.getVch_gatepass_no()+"' ";
    				
		}
		else if (action.getLicenceType().equalsIgnoreCase("BEER")) 
		{
			query = " SELECT date_plan, etin, casecode " +
					" FROM bottling_unmapped.fl2_2b_cl2_unmap "+
    				"  where fl36gatepass='"+action.getVch_gatepass_no()+"' ";
    				
		}*/
		
		try {
			list = new ArrayList();
			con = ConnectionToDataBase.getConnection19_20();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			int i = 1;
			while (rs.next()) 
			{
				Gatepass_Search_DataTable dt = new Gatepass_Search_DataTable();
				dt.setSnoK(i);
				dt.setCaseCode(rs.getString("casecode"));
				dt.setEtinNo(rs.getString("etin"));
				dt.setPlnDt(rs.getString("date_plan"));
				list.add(dt);
				i++;	
			}
			System.out.println("	if(list.size()==0)===== "+list.size());
			if(list.size()==0)
			{
				action.setGetCaseCodeFlag(false);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found On This Gatepass",
								"No Data Found On This Gatepass"));
			}else
			{
				action.setGetCaseCodeFlag(true);
			
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
		return list;
	}
	


}
