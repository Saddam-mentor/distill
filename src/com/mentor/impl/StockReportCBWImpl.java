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

import com.mentor.action.StockReportCBWAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class StockReportCBWImpl {
	

	
	
	public void getDetails(StockReportCBWAction ac) {
		//String imfl = "";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query =  " select a.int_id,a.int_bond_id,a.int_import_id,a.vch_user_nm,b.int_id, b.vch_applicant_name,b.vch_firm_name  "+
    			" ,concat(c.vch_firm_name,': ',c.vch_bond_address) as vch_bond_address ,c.int_id from  "+
				" custom_bonds.mst_custom_bond_importing_unit_maping a,custom_bonds.mst_regis_importing_unit  "+
         		" b,custom_bonds.custom_bonds_master c where a.int_bond_id=c.int_id and a.int_import_id=b.int_id "+
    			" and a.vch_user_nm='"+ResourceUtil.getUserNameAllReq()+"'";
		
		 // System.out.println("=====query==Login===="+query);
		
		  try {
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				//ac.setLicence_type(rs.getString("vch_license_type"));
				ac.setInt_id(rs.getInt("int_id"));
				ac.setInt_import_id(rs.getInt("int_import_id"));
				ac.setInt_bond_id(rs.getInt("int_bond_id"));
				ac.setApplicant_name(rs.getString("vch_firm_name"));
				//ac.setDistrict(rs.getString("description"));

				//ac.setDistrictId(rs.getInt("vch_firm_district_id"));

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
	
	
	
	public ArrayList getBrandList(StockReportCBWAction act) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue("");
		list.add(item);

		try {
			String query = 
		    " Select distinct b.brand_id || '/' || c.package_id as id,  brand_name || '(' || package_name || ')'                 "+
			" as brandpack   from distillery.brand_registration_20_21 b,distillery.packaging_details_20_21 c                     "+
			" where c.brand_id_fk=b.brand_id  and  b.brand_id in                                                                 "+
			" (select distinct brand_id from custom_bonds.bill_receive_from_oup where login_id="+act.getInt_import_id()+" and bond_id="+act.getInt_bond_id()+""+
			" union                                                                                                              "+
			" select distinct brand_id from custom_bonds.bill_dispatch_from_oup where login_id='"+act.getInt_id()+"'                               "+
			" union                                                                                                              "+
			" select distinct brand_id from custom_bonds.stock_transfer_cbw_up where bond_id="+act.getInt_bond_id()+" and import_id="+act.getInt_import_id()+" or app_id="+act.getInt_id()+"    "+
			" union                                                                                                              "+
			" select distinct a.int_brand_id from fl2d.mst_stock_receive_20_21 a,bwfl_license.import_permit_20_21 b where        "+
			" a.cbw_id="+act.getInt_bond_id()+" and b.int_import_id="+act.getInt_import_id()+" and a.cbw_id=b.custom_id and a.gatepass_date is not  null                      "+
			")                                                                                                                  "+
			" and c.package_id in                                                                                                "+
			" (select distinct pack_id from custom_bonds.bill_receive_from_oup where login_id="+act.getInt_import_id()+" and bond_id="+act.getInt_bond_id()+"                    "+
			" union                                                                                                              "+
			" select distinct pack_id from custom_bonds.bill_dispatch_from_oup where login_id='"+act.getInt_id()+"'                                "+
			" union                                                                                                              "+
			" select distinct package_id from custom_bonds.stock_transfer_cbw_up where bond_id="+act.getInt_bond_id()+" and import_id="+act.getInt_import_id()+" or app_id="+act.getInt_id()+"  "+
			" union                                                                                                              "+
			" select distinct a.int_pack_id from fl2d.mst_stock_receive_20_21 a,bwfl_license.import_permit_20_21 b where         "+
			" a.cbw_id="+act.getInt_bond_id()+" and b.int_import_id="+act.getInt_import_id()+" and a.cbw_id=b.custom_id and a.gatepass_date is not  null                      "+
			") "+
			" order by brandpack";
			System.out.println("=====query==BrandList===="+query);
			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			 
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("id"));
				item.setLabel(rs.getString("brandpack"));
				//act.setBrandpack(rs.getString("brandpack"));

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


	// =======================print report=================================

	public void printReport(StockReportCBWAction act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		  String arr[] = act.getPackId().split("/");
          String brand_id  = arr[0];
          String package_id  = arr[1];

		
		try {
			con = ConnectionToDataBase.getConnection();
			
			reportQuery =
					" select a.cr_date,a.des,sum(total_receive_bottls) as total_receive_bottls,sum(total_dispatch_bottls) as total_dispatch_bottls,                                         "+
					" br.brand_name || ' (' || pk.package_name || ')'as brand from   "+
					" (select brand_id, pack_id,'Received From Outside UP' as des,cr_date,coalesce(sum(no_of_bottls),0) as total_receive_bottls,0 as total_dispatch_bottls                                 "+
					" from custom_bonds.bill_receive_from_oup where login_id="+act.getInt_import_id()+" and bond_id="+act.getInt_bond_id()+" and cr_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' " +
					" and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' and brand_id='"+brand_id+"' and pack_id="+package_id+" group by pack_id,brand_id,cr_date           "+
					" union                                                                                                                                                                "+
					" select brand_id,pack_id,'Dispatched Outside UP' as des,cr_date,0 as total_receive_bottls ,coalesce(sum(no_of_bottls),0) as total_dispatch_bottls from                            "+
					" custom_bonds.bill_dispatch_from_oup where login_id='"+act.getInt_id()+"' and cr_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' " +
					" and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' and brand_id='"+brand_id+"' and pack_id="+package_id+" group by brand_id,pack_id,cr_date                           "+
					" union                                                                                                                                                                "+
					" select brand_id,package_id,'Received From Inside UP' as des, receive_date,coalesce(sum(totat_receive_bottle),0) as totat_receive_bottle, 0 as total_dispatch_bottls from                    "+
					" custom_bonds.stock_transfer_cbw_up where bond_id="+act.getInt_bond_id()+" and import_id="+act.getInt_import_id()+" and receive_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'" +
					" and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' and brand_id='"+brand_id+"' and package_id="+package_id+" group by brand_id,package_id,receive_date  "+
					" union                                                                                                                                                                "+
					" select brand_id,package_id,'Dispatched In UP' as des ,bill_date,0 as total_receive_bottls ,coalesce(sum(total_no_of_bottle),0) as total_dispatch_bottls  from                        "+
					" custom_bonds.stock_transfer_cbw_up where app_id="+act.getInt_id()+" and bill_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' " +
					" and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' and brand_id='"+brand_id+"' and package_id="+package_id+" group by brand_id,package_id,bill_date                         "+
					" union                                                                                                                                                                "+
					" select a.int_brand_id,a.int_pack_id,a.gatepass_no as des,a.gatepass_date::date,0 as total_receive_bottls,coalesce(sum(a.dispatch_36),0) as total_dispatch_bottls     "+
					" from fl2d.mst_stock_receive_20_21 a, bwfl_license.import_permit_20_21 b where a.cbw_id="+act.getInt_bond_id()+" and b.int_import_id="+act.getInt_import_id()+"   "+
					" and a.cbw_id=b.custom_id and a.gatepass_date is not  null and a.int_brand_id="+brand_id+" and a.int_pack_id="+package_id+" " +
					" and a.permit_no=b.permit_nmbr and a.int_fl2d_id=b.bwfl_id group by a.int_brand_id,a.int_pack_id,a.gatepass_date,a.gatepass_no)a,                                      "+
					" distillery.brand_registration_20_21 br,distillery.packaging_details_20_21 pk where br.brand_id=a.brand_id and br.brand_id=pk.brand_id_fk and "+
                    " pk.package_id=a.pack_id group by a.cr_date ,br.brand_name,pk.package_name,a.des  order by a.cr_date";
					
					
					
					
					
				
			
			System.out.println("queryy==="+reportQuery);
				pst = con.prepareStatement(reportQuery);
				
				rs = pst.executeQuery();
				 

			if (rs.next()) {
				

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("brandName",act.getBrandpack());
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				parameters.put("balance",act.getNewb());
				parameters.put("exDate",Utility.convertUtilDateToSQLDate(act.getLastDate()));
				parameters.put("user",ResourceUtil.getUserNameAllReq().trim());

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "Stock_register_for_cbw.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "Stock_register_for_cbw" + "-" + n + ".pdf");
				act.setPdfName("Stock_register_for_cbw" + "-" + n + ".pdf");
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
	
	
	public void printReportDetailed(StockReportCBWAction act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		 

		
		try {
			con = ConnectionToDataBase.getConnection();
			
			reportQuery =
					" select a.int_id,a.brand_id,a.pack_id,a.box_size,coalesce(sum(a.no_of_box::int),0) as no_of_box ,                     "+
                    " coalesce(sum(a.no_of_bottel::int-a.dispatch_bottle),0) as no_of_bottel,                                              "+
                    " br.brand_name,pk.package_name from custom_bonds.custom_bond_stock_2020 a, distillery.brand_registration_20_21 br,    "+
                    " distillery.packaging_details_20_21 pk where a.int_id="+act.getInt_id()+" and br.brand_id=a.brand_id and br.brand_id=pk.brand_id_fk and "+
                    " pk.package_id=a.pack_id group by a.int_id,a.brand_id,a.pack_id,a.box_size,br.brand_name,pk.package_name order by br.brand_name";
					
					

			
			    System.out.println("queryy==="+reportQuery);
				pst = con.prepareStatement(reportQuery);
				
				rs = pst.executeQuery();
				 

			if (rs.next()) {
				

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				parameters.put("user",ResourceUtil.getUserNameAllReq().trim());

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "Stock_register_for_cbw_detail.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "Stock_register_for_cbw_detail" + "-" + n + ".pdf");
				act.setPdfName("Stock_register_for_cbw_detail" + "-" + n + ".pdf");
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
	
	public void getopening(StockReportCBWAction ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		 String arr[] = ac.getPackId().split("/");
         String brand_id  = arr[0];
         String package_id  = arr[1];
         
		String query =  
				
				 " select coalesce(sum(bottel_no),0) as opening from custom_bonds.customband_opening_stock_entry "+
                 " where int_id='"+ac.getInt_id()+"' and brand_id="+brand_id+" and pack_id ="+package_id+" ";
		
		try {
			//System.out.println("==================Get=====Opening===>"+query);
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				ac.setBalance(rs.getInt("opening"));
				ac.setNewb(rs.getInt("opening"));
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
	
	


}
