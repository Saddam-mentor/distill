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
import com.mentor.action.WholesaleStockRegisterAction; 
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class WholesaleStockRegisterImpl {
	
	
	//====================get login details=====================
	
	
	public void getDetails(WholesaleStockRegisterAction act){

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = 	" SELECT DISTINCT b.description, b.districtid  FROM public.district b " +
						" WHERE  b.deo='"+ ResourceUtil.getUserNameAllReq().trim()+ "'";
		
		
		try {
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) 
			{
				act.setDistrictName(rs.getString("description"));
				act.setDistrictId(rs.getString("districtid"));

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
	
	
	
	
	public void getDetails1(WholesaleStockRegisterAction act,String roleID){

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = 	" SELECT DISTINCT b.description, b.districtid  FROM public.district b " +
						" WHERE  b.deo='"+ ResourceUtil.getUserNameAllReq().trim()+ "' order by b.description";
		
		
		String query1= "select a.districtid,a.description from public.district a,public.charge b  "+
				      "where a.chargeid=b.chargeid and b.description='Varanasi' order by a.description";
		
		
		String query2="select a.districtid,a.description from public.district a order by a.description";
		
		
		try {
			con = ConnectionToDataBase.getConnection();
			if(roleID.equals("207"))
			{
				 ps = con.prepareStatement(query1);	
			}else if(roleID.equals("199"))
			{
            ps = con.prepareStatement(query);
			}else if(roleID.equals("NA"))
			{
	            ps = con.prepareStatement(query2);
				}
			rs = ps.executeQuery();
			if (rs.next()) 
			{
				act.setDistrictName(rs.getString("description"));
				act.setDistrictId(rs.getString("districtid"));

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
	
	
	
	
	
	
	
	public boolean getWs(WholesaleStockRegisterAction act){

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String year = act.getYear();
		String query = 	"  SELECT DISTINCT CONCAT(a.vch_firm_name,'-',a.vch_licence_no)as firm_name, b.description, b.districtid, a.int_app_id "
					+ " FROM licence.fl2_2b_2d_"+year+" a, public.district b "
					+ " WHERE  a.core_district_id=b.districtid and a.loginid='"
					+ ResourceUtil.getUserNameAllReq().trim() + "'";
		
		
		try {
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) 
			{
				act.setDistrictName(rs.getString("description"));
				act.setDistrictId(rs.getString("districtid"));
				act.setWholesaleId(rs.getString("int_app_id"));
				act.setWholesaleName(rs.getString("firm_name"));
				return true;

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
		return false;
	
	}
	
	
	
	// ---------------------get district--------------------------

		public ArrayList getDistList() {

			ArrayList list = new ArrayList();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			SelectItem item = new SelectItem();
			item.setLabel("--Select--");
			item.setValue("9999");
			list.add(item);
			try {
				
				String query = " SELECT districtid, description FROM public.district order by description ";

				conn = ConnectionToDataBase.getConnection();
				pstmt = conn.prepareStatement(query);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					item = new SelectItem();

					item.setValue(rs.getString("districtid"));
					item.setLabel(rs.getString("description"));

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
		
		
		
		
		
		public ArrayList getDistList1(String roleID) {

			ArrayList list = new ArrayList();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query="";
			String query1="";
			String query2="";
			SelectItem item = new SelectItem();
			item.setLabel("--Select--");
			item.setValue("9999");
			list.add(item);
			try {
				if(roleID.equals("207")||roleID.equals("199"))
				{
				 query = 	" SELECT DISTINCT b.description, b.districtid  FROM public.district b " +
						" WHERE  b.deo='"+ ResourceUtil.getUserNameAllReq().trim()+ "' order by b.description";
		

		       query1= "select a.districtid,a.description from public.district a,public.charge b  "+
				      "where a.chargeid=b.chargeid and b.description='"+ ResourceUtil.getUserNameAllReq().trim().substring(10,ResourceUtil.getUserNameAllReq().trim().length())+ "' order by a.description";
				}else{
		
	           query2="select a.districtid,a.description from public.district a order by a.description";
				}

				conn = ConnectionToDataBase.getConnection();
				if(roleID.equals("207"))
				{
					 pstmt = conn.prepareStatement(query1);	
				}else if(roleID.equals("199"))
				{
	            pstmt = conn.prepareStatement(query);
				}else if(roleID.equals("NA")||(!roleID.equals("207")&&!roleID.equals("199")))
				{
		            pstmt = conn.prepareStatement(query2);
					}

				rs = pstmt.executeQuery();

				while (rs.next()) {
					item = new SelectItem();

					item.setValue(rs.getString("districtid"));
					item.setLabel(rs.getString("description"));

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
	
		
		
		
		
		
		
		
		
		
		
	
	//===================get wholeseller list======================
	
	public ArrayList getWholesaleList(WholesaleStockRegisterAction act){


		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		SelectItem item = new SelectItem();
		item.setLabel("--ALL--");
		item.setValue("8888");
		list.add(item);
		
		
		String query="";
		String year = act.getYear();
		try {
			/*if(ResourceUtil.getUserNameAllReq()!=null&&)
			{
				if (ResourceUtil.getUserNameAllReq().trim().substring(0, 10).equalsIgnoreCase("Excise-DEO")) 
				{
	
						 query= " SELECT CONCAT(vch_firm_name,'-',vch_licence_no)as firm_name, int_app_id," +
								" vch_firm_name FROM licence.fl2_2b_2d_"+year+" " +
								" WHERE vch_license_type='"+act.getRadio()+"'  AND  " +
								" core_district_id='"+act.getDistrictId()+"'  ORDER BY vch_firm_name ";
					
				} 
			}
				else {*/

					/*	query = " SELECT CONCAT(vch_firm_name,'-',vch_licence_no)as firm_name, int_app_id," +
								" vch_firm_name FROM licence.fl2_2b_2d_"+year+" " +
								" where vch_license_type='"+act.getRadio()+"'   " +
								" ORDER BY vch_firm_name ";	
					*/
					
					 query= " SELECT CONCAT(vch_firm_name,'-',vch_licence_no)as firm_name, int_app_id," +
								" vch_firm_name FROM licence.fl2_2b_2d_"+year+" " +
								" WHERE vch_license_type='"+act.getRadio()+"'  AND  " +
								" core_district_id='"+act.getDistrictId()+"'  ORDER BY vch_firm_name ";
				//}
				
			
			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			
			System.out.println("wholeasle query---------------------"+query);

			rs = pstmt.executeQuery();

			while (rs.next()) 
			{
				item = new SelectItem();
				item.setValue(rs.getString("int_app_id")); 
				item.setLabel(rs.getString("firm_name"));

				list.add(item);
			}

		} 
		catch (Exception e) 
		{
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

	// =======================print report FL2 =================================

		public void printReportFL2(WholesaleStockRegisterAction act){


			String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

			String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
			String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
			JasperReport jasperReport = null;
			PreparedStatement pst = null;
			Connection con = null;
			ResultSet rs = null;
			String reportQuery = null;

			String filter = "";
			String filterR = "",filterR1="";
			String filterD = "";
			String filterDistrict = "";
			String year = act.getYear();

			
			if(ResourceUtil.getUserNameAllReq().trim().substring(0, 10).equalsIgnoreCase("Excise-DEO"))
			{
				
				if (act.getWholesaleId().equalsIgnoreCase("8888")) {
					filter = " AND ms.core_district_id='"+act.getDistrictId()+"'  ";
					filterDistrict = " AND e.licence_district='"+act.getDistrictId()+"' ";
					filterR="";
					filterD="";
				} 
				else if (!act.getWholesaleId().equalsIgnoreCase("8888")) {
					filter = " AND ms.core_district_id='"+act.getDistrictId()+"' AND ms.int_app_id='"+act.getWholesaleId()+"' ";
					filterR = " AND a.fl2_2bid='"+act.getWholesaleId()+"' ";filterR1 = " AND a.unit_id='"+act.getWholesaleId()+"' ";
					filterD = " AND b.int_fl2_fl2b_id='"+act.getWholesaleId()+"' ";
					filterDistrict = " AND e.licence_district='"+act.getDistrictId()+"' ";
				}
				
			}
			else{
				if (act.getWholesaleId().equalsIgnoreCase("8888") && act.getDistrictId().equalsIgnoreCase("9999")) {
					filter =  "";
					filterR = "";
					filterD = "";
					filterDistrict = "";
				} 
				else if (!act.getWholesaleId().equalsIgnoreCase("8888") && act.getDistrictId().equalsIgnoreCase("9999")) {
					filter =  " AND ms.int_app_id='"+act.getWholesaleId()+"' ";
					filterR = " AND a.fl2_2bid='"+act.getWholesaleId()+"' ";filterR1 = " AND a.unit_id='"+act.getWholesaleId()+"' ";
					filterD = " AND b.int_fl2_fl2b_id='"+act.getWholesaleId()+"' ";
					filterDistrict = "";
				}
				else if (act.getWholesaleId().equalsIgnoreCase("8888") && !act.getDistrictId().equalsIgnoreCase("9999")) {
					filter = " AND ms.core_district_id='"+act.getDistrictId()+"' ";
					filterR = "";
					filterD = "";
					filterDistrict = " AND e.licence_district='"+act.getDistrictId()+"' ";
				}
				else if (!act.getWholesaleId().equalsIgnoreCase("8888") && !act.getDistrictId().equalsIgnoreCase("9999")) {
					filter = " AND ms.core_district_id='"+act.getDistrictId()+"' AND ms.int_app_id='"+act.getWholesaleId()+"' ";
					filterR = " AND a.fl2_2bid='"+act.getWholesaleId()+"' ";filterR1 = " AND a.unit_id='"+act.getWholesaleId()+"' ";
					filterD = " AND b.int_fl2_fl2b_id='"+act.getWholesaleId()+"' ";
					filterDistrict = " AND e.licence_district='"+act.getDistrictId()+"' ";
				}
			}
			
			

			try {
				con = ConnectionToDataBase.getConnection();

				if(year.equalsIgnoreCase("19_20"))
				{
				                                                                                                                            
			reportQuery = 	" select distinct aa.brand_id, aa.pckg_id, aa.fl2_2bid, aa.dt, aa.gatepass,  " +
							" case when aa.shop_nm='' then 'HBR/BRC' else aa.shop_nm end as shop_nm,                       "+
							" aa.brand_name, aa.package_name, aa.opening, aa.reciving, aa.dispatch,                                         "+
							" CONCAT(ms.vch_firm_name,' - ',ms.vch_licence_no)as firm_name, dd.description as district                       "+
							" from                                                                                                           "+
							" (select z.brand_id, z.pckg_id, z.fl2_2bid, z.dt, z.gatepass, z.shop_nm, br.brand_name, pk.package_name,        "+
							" z.opening, z.reciving, z.dispatch                                                                             "+
							" from                                                                                                           "+
							" (select y.brand_id, y.pckg_id, y.fl2_2bid, y.dt, y.gatepass, y.shop_nm,                                        "+
							" y.opening, sum(y.reciving)reciving, sum(y.dispatch)dispatch                                                  "+
							" from                                                                                                           "+
							" (select x.brand_id, x.pckg_id, x.fl2_2bid,  " +
							" (to_date('"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"','YYYY-MM-DD')  - INTERVAL '1' DAY) as dt, " +
							" 'OPENING' as gatepass, '------' as shop_nm,         "+
							" sum(x.recieving_opn)-sum(x.dispatch_opn) as opening, 0 as reciving, 0 as dispatch                             "+
							" from                                                                                                           "+
							" (select a.brand_id as brand_id,  a.pckg_id as pckg_id, a.fl2_2bid as fl2_2bid,                                 "+
							" SUM(COALESCE(a.total_recv_bottels,0)) as recieving_opn, 0 as dispatch_opn                                      "+
							" from fl2d.fl2_2b_receiving_stock_trxn_"+year+" a                                                                  "+
							" where a.created_date < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                             "+
							" AND a.fl2_2btype='"+act.getRadio()+"' "+filterR+"                  "+
							" group by a.brand_id, a.pckg_id, a.fl2_2bid                                                                     "+
							" union                                                                                                          "+
							" select b.int_brand_id as brand_id, b.int_pckg_id as pckg_id, b.int_fl2_fl2b_id::text  as fl2_2bid,             "+
							" 0 as recieving_opn, SUM(COALESCE(b.dispatch_bottle,0)) as dispatch_opn                                         "+
							" FROM fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b                                                                      "+
							" where b.dt < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' "+filterD+"                                                          "+
							" group by b.int_brand_id, b.int_pckg_id, b.int_fl2_fl2b_id)x                                                    "+
							" group by x.brand_id, x.pckg_id, x.fl2_2bid                                                                     "+
							"                                                                                                                "+
							" union all                                                                                                      "+
							"                                                                                                                "+
							" select a.brand_id as brand_id,  a.pckg_id as pckg_id, a.fl2_2bid as fl2_2bid,                                  "+
							" a.created_date as dt, a.gatepass_no as gatepass, '------' as shop_nm, 0 as opening,                                "+
							" SUM(COALESCE(a.total_recv_bottels,0)) as reciving, 0 as dispatch                                              "+
							" from fl2d.fl2_2b_receiving_stock_trxn_"+year+" a                                                                  "+
							" where a.created_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                       "+
							" and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
							" AND a.fl2_2btype='"+act.getRadio()+"' "+filterR+"                                                   "+
							" group by a.brand_id, a.pckg_id, a.fl2_2bid, a.created_date, a.gatepass_no                                      "+
							" union                                                                                                          "+
							" select b.int_brand_id as brand_id, b.int_pckg_id as pckg_id, b.int_fl2_fl2b_id::text  as fl2_2bid,             "+
							" b.dt  as dt, b.vch_gatepass_no as gatepass, e.shop_nm, 0 as opening, 0 as reciving,                           "+
							" SUM(COALESCE(b.dispatch_bottle,0)) as dispatch                                                                 "+
							" FROM fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b, fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+year+" e                 "+
							" where b.vch_gatepass_no=e.vch_gatepass_no AND b.dt=e.dt_date AND b.int_fl2_fl2b_id=e.int_fl2_fl2b_id           "+
							" and b.dt between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                   "+
							" and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
							" AND e.vch_from='"+act.getRadio()+"' "+filterD+"                                               "+
							" group by b.int_brand_id, b.int_pckg_id, b.int_fl2_fl2b_id, b.dt, b.vch_gatepass_no, e.shop_nm)y                "+
							" group by y.brand_id, y.pckg_id, y.fl2_2bid, y.dt, y.gatepass, y.shop_nm, y.opening)z,                          "+
							" distillery.brand_registration_"+year+" br, distillery.packaging_details_"+year+" pk                                  "+
							" where br.brand_id=z.brand_id and br.brand_id=pk.brand_id_fk and z.pckg_id=pk.package_id)aa,                    "+
							" licence.fl2_2b_2d_"+year+" ms, public.district dd                                                                 "+
							" where aa.fl2_2bid=ms.int_app_id::text and dd.districtid=ms.core_district_id                                    "+
							" AND ms.vch_license_type='"+act.getRadio()+"' "+filter+"                                                             "+
							" group by district, firm_name, aa.shop_nm, aa.brand_id, aa.pckg_id, aa.fl2_2bid, aa.dt, aa.gatepass,            "+
							" aa.brand_name, aa.package_name, aa.opening, aa.reciving, aa.dispatch                                          "+
							" order by district, firm_name, aa.brand_name, aa.package_name, aa.dt, shop_nm ";
				}else {

                    
					reportQuery = 	" select distinct aa.brand_id, aa.pckg_id, aa.fl2_2bid, aa.dt, aa.gatepass,  " +
									" case when aa.shop_nm='' then 'HBR/BRC' else aa.shop_nm end as shop_nm,                       "+
									" aa.brand_name, aa.package_name, aa.opening, aa.reciving, aa.dispatch,                                         "+
									" CONCAT(ms.vch_firm_name,' - ',ms.vch_licence_no)as firm_name, dd.description as district                       "+
									" from                                                                                                           "+
									" (select z.brand_id, z.pckg_id, z.fl2_2bid, z.dt, z.gatepass, z.shop_nm, br.brand_name, pk.package_name,        "+
									" z.opening, z.reciving, z.dispatch                                                                             "+
									" from                                                                                                           "+
									" (select y.brand_id, y.pckg_id, y.fl2_2bid, y.dt, y.gatepass, y.shop_nm,                                        "+
									" y.opening, sum(y.reciving)reciving, sum(y.dispatch)dispatch                                                  "+
									" from                                                                                                           "+
									" (select x.brand_id, x.pckg_id, x.fl2_2bid,  " +
									" (to_date('"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"','YYYY-MM-DD')  - INTERVAL '1' DAY) as dt, " +
									" 'OPENING / ROLLOVER STOCK' as gatepass, '------' as shop_nm,         "+
									" 0 as opening, sum(x.recieving_opn)-sum(x.dispatch_opn) as reciving, 0 as dispatch                             "+
									" from                                                                                                           "+
									" ("
									+ "select a.brand_id as brand_id,  a.pckg_id as pckg_id, a.fl2_2bid as fl2_2bid,                                 "+
									" SUM(COALESCE(a.total_recv_bottels,0)) as recieving_opn, 0 as dispatch_opn                                      "+
									" from fl2d.fl2_2b_receiving_stock_trxn_"+year+" a                                                                  "+
									" where a.created_date < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                             "+
									" AND a.fl2_2btype='"+act.getRadio()+"' "+filterR+"                  "+
									" group by a.brand_id, a.pckg_id, a.fl2_2bid                                                                     "+
									" union all "+
									" select a.brand_id as brand_id,  a.package_id as pckg_id, a.unit_id::text as fl2_2bid, "
									+ " SUM(COALESCE(a.rollover_bottles,0)) as recieving_opn,  0 as dispatch_opn from fl2d.rollover_fl_stock a "
									+ "where a.finalize_dt is not null and a.unit_id in (select int_app_id  from licence.fl2_2b_2d_20_21 where vch_license_type='"+act.getRadio()+"') "+filterR1+" group by a.brand_id, a.package_id, a.unit_id  "+
									" union                                                                                                          "+
									" select b.int_brand_id as brand_id, b.int_pckg_id as pckg_id, b.int_fl2_fl2b_id::text  as fl2_2bid,             "+
									" 0 as recieving_opn, SUM(COALESCE(b.dispatch_bottle,0)) as dispatch_opn                                         "+
									" FROM fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b                                                                      "+
									" where b.dt < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' "+filterD+"                                                          "+
									" group by b.int_brand_id, b.int_pckg_id, b.int_fl2_fl2b_id "
									 
									 
									+ ")x                                                    "+
									" group by x.brand_id, x.pckg_id, x.fl2_2bid                                                                     "+
									"                                                                                                                "+
									" union all                                                                                                      "+
									"                                                                                                                "+
									" select a.brand_id as brand_id,  a.pckg_id as pckg_id, a.fl2_2bid as fl2_2bid,                                  "+
									" a.created_date as dt, a.gatepass_no as gatepass, '------' as shop_nm, 0 as opening,                                "+
									" SUM(COALESCE(a.total_recv_bottels,0)) as reciving, 0 as dispatch                                              "+
									" from fl2d.fl2_2b_receiving_stock_trxn_"+year+" a                                                                  "+
									" where a.created_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                       "+
									" and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
									" AND a.fl2_2btype='"+act.getRadio()+"' "+filterR+"                                                   "+
									" group by a.brand_id, a.pckg_id, a.fl2_2bid, a.created_date, a.gatepass_no             "+
									 
									" union                                                                                                          "+
									" select b.int_brand_id as brand_id, b.int_pckg_id as pckg_id, b.int_fl2_fl2b_id::text  as fl2_2bid,             "+
									" b.dt  as dt, b.vch_gatepass_no as gatepass, e.shop_nm, 0 as opening, 0 as reciving,                           "+
									" SUM(COALESCE(b.dispatch_bottle,0)) as dispatch                                                                 "+
									" FROM fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b, fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+year+" e                 "+
									" where b.vch_gatepass_no=e.vch_gatepass_no AND b.dt=e.dt_date AND b.int_fl2_fl2b_id=e.int_fl2_fl2b_id           "+
									" and b.dt between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                   "+
									" and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
									" AND e.vch_from='"+act.getRadio()+"' "+filterD+"                                               "+
									" group by b.int_brand_id, b.int_pckg_id, b.int_fl2_fl2b_id, b.dt, b.vch_gatepass_no, e.shop_nm"
									 
									+ ")y                "+
									" group by y.brand_id, y.pckg_id, y.fl2_2bid, y.dt, y.gatepass, y.shop_nm, y.opening)z,                          "+
									" distillery.brand_registration_"+year+" br, distillery.packaging_details_"+year+" pk                                  "+
									" where br.brand_id=z.brand_id and br.brand_id=pk.brand_id_fk and z.pckg_id=pk.package_id)aa,                    "+
									" licence.fl2_2b_2d_"+year+" ms, public.district dd                                                                 "+
									" where aa.fl2_2bid=ms.int_app_id::text and dd.districtid=ms.core_district_id                                    "+
									" AND ms.vch_license_type='"+act.getRadio()+"' "+filter+"                                                             "+
									" group by district, firm_name, aa.shop_nm, aa.brand_id, aa.pckg_id, aa.fl2_2bid, aa.dt, aa.gatepass,            "+
									" aa.brand_name, aa.package_name, aa.opening, aa.reciving, aa.dispatch                                          "+
									" order by district, firm_name, aa.brand_name, aa.package_name, aa.dt, aa.reciving desc,aa.dispatch,shop_nm ";
						
				}

				pst = con.prepareStatement(reportQuery);
				 System.out.println(reportQuery);
				rs = pst.executeQuery();

				if(rs.next()) {

					
					rs = pst.executeQuery();
					Map parameters = new HashMap();
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("SUBREPORT_DIR", relativePath + File.separator);
					parameters.put("image", relativePath + File.separator);
					parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
					parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
					
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

					jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "WholesellerStockRegister.jasper");

					JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager.exportReportToPdfFile(print, relativePathpdf+ File.separator + "WholesellerStockRegister"+ "-" + year + "_" + n + ".pdf");
					act.setPdfName("WholesellerStockRegister" + "-" + year + "_" + n + ".pdf");
					act.setPrintFlag(true);
				} else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
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
		
		public String getUserRole(String userId) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String userRole = "";
			String query = "";

			try {
				if (ResourceUtil.getUserNameAllReq() != null) {
					System.out.println("queryy come in");
					query =
					

					"SELECT jbp_rid from jbp_role_membership a,jbp_users b "
							+ " where a.jbp_uid=b.jbp_uid and b.jbp_uname='"
							+ userId + "' ";
					;

				
				conn = ConnectionToDataBase.getConnection2();
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					userRole = rs.getString("jbp_rid");
                  System.out.println("Role id"+userRole);
				}
				} else {
					userRole="NA";
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage(), e.getMessage()));
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
			return userRole;
		}
	/*======================Aman=============================	*/
		
		public ArrayList yearListImpl(WholesaleStockRegisterAction act) {
			ArrayList list = new ArrayList();
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			SelectItem item = new SelectItem();
			item.setLabel("--select--");
			item.setValue("");
			list.add(item);
			conn = ConnectionToDataBase.getConnection();

			try {
			
			
		String query = " SELECT year, value FROM public.reporting_year;";

					ps = conn.prepareStatement(query);
				// ps.setDate(1,
				// Utility.convertUtilDateToSQLDate(act.getDt_date()));
				rs = ps.executeQuery();
	           // System.out.println("before rs===  "+rs);
				while (rs.next()) {

					item = new SelectItem();

					item.setValue(rs.getString("value"));
					item.setLabel(rs.getString("year"));
					
					list.add(item);
					//System.out.println("== year== "+query);

				}

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
				e.printStackTrace();
			} finally {
				try {

					if (conn != null)
						conn.close();
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
		
		//==============================================
			public String getDetailsyr(WholesaleStockRegisterAction act) {

				Connection con = null;
				PreparedStatement pstmt = null, ps2 = null;
				ResultSet rs = null, rs2 = null;

				try {
					con = ConnectionToDataBase.getConnection();

					String queryList = " SELECT start_dt, end_dt FROM public.reporting_year where " +
							           " value='"+ act.getYear()+ "' ";
 
					pstmt = con.prepareStatement(queryList);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						act.setStart_dt(rs.getDate("start_dt"));
						act.setEnd_dt(rs.getDate("end_dt"));
						 
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
	
	

}
