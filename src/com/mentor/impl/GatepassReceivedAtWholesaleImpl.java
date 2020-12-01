package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

import com.mentor.action.GatepassReceivedAtWholesaleAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class GatepassReceivedAtWholesaleImpl {
	
	
	public void getDetails(GatepassReceivedAtWholesaleAction ac) {
		String imfl = "";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select  a.vch_license_type,a.int_app_id, a.vch_licence_no,a.vch_applicant_name,b.description,a.core_district_id  "
				+ " from licence.fl2_2b_2d a,public.district b "
				+ "where   b.districtid=a.core_district_id "
				+ "and  a.loginid='"
				+ ResourceUtil.getUserNameAllReq().trim()
				+ "'";
		
		// System.out.println("query="+query);
		try {
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				ac.setLicence_type(rs.getString("vch_license_type"));
				ac.setInt_id(rs.getInt("int_app_id"));
				ac.setLicence_no(rs.getString("vch_licence_no"));
				ac.setApplicant_name(rs.getString("vch_applicant_name"));
				ac.setDistrict(rs.getString("description"));

				ac.setDistrictId(rs.getInt("core_district_id"));

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
	
	
	
	public ArrayList getInspectorListImpl(GatepassReceivedAtWholesaleAction act) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue(0);
		list.add(item);

		try {
			String query = " SELECT inspector_id, inspector_nm FROM licence.inspector_assign where type='W' order by inspector_nm;";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			 

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("inspector_id"));
				item.setLabel(rs.getString("inspector_nm"));
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

	public void printReport(GatepassReceivedAtWholesaleAction act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		Date from_dt=Utility.convertUtilDateToSQLDate(act.getFromDate());
		Date to_dt=Utility.convertUtilDateToSQLDate(act.getToDate());
		try {
			con = ConnectionToDataBase.getConnection();
			
			if(act.getRadio().equalsIgnoreCase("D")){
			reportQuery = " SELECT a.vch_gatepass_no,a.dt_date,a.vch_from,b.vch_firm_name || ' (' || b.vch_mobile_no || ')' as wholesale,a.rcvdt         "+
                          " FROM distillery.gatepass_to_districtwholesale_20_21 a,licence.fl2_2b_2d_20_21 b                                              "+
                          " where a.rcvdbyid::int=b.int_app_id and b.inspector_id="+act.getInspector_id()+" " +
                          " and  a.dt_date between '"+from_dt+"' and '"+to_dt+"'  ";                                                              
			}
			else if(act.getRadio().equalsIgnoreCase("CL")){
           reportQuery =  " SELECT a.vch_gatepass_no,a.dt_date,a.vch_from,b.vch_firm_name || ' (' || b.vch_mobile_no || ')' as wholesale,a.rcvdt         "+
                          " FROM distillery.gatepass_to_manufacturewholesale_cl_20_21 a,licence.fl2_2b_2d_20_21 b                                        "+
                          " where a.rcvdbyid::int=b.int_app_id and b.inspector_id="+act.getInspector_id()+"                                                                      "+
                          " and  a.dt_date between '"+from_dt+"' and '"+to_dt+"'";
			}
		   else if(act.getRadio().equalsIgnoreCase("BEER")){
           reportQuery =    " SELECT a.vch_gatepass_no,a.dt_date,a.vch_from,b.vch_firm_name || ' (' || b.vch_mobile_no || ')' as wholesale,a.rcvdt         "+
                          " FROM bwfl.gatepass_to_districtwholesale_20_21 a,licence.fl2_2b_2d_20_21 b                                                    "+
                          " where a.rcvdbyid::int=b.int_app_id and b.inspector_id="+act.getInspector_id()+"                                                                      "+
                          " and  a.dt_date between '"+from_dt+"' and '"+to_dt+"' ";
                                    
			}
		  else if(act.getRadio().equalsIgnoreCase("BWFL")){
          reportQuery =  " SELECT a.vch_gatepass_no,a.dt_date,a.vch_from,b.vch_firm_name || ' (' || b.vch_mobile_no || ')' as wholesale,a.rcvdt         "+
                          " FROM bwfl_license.gatepass_to_districtwholesale_bwfl_20_21 a,licence.fl2_2b_2d_20_21 b                                       "+
                          " where a.rcvdbyid::int=b.int_app_id and b.inspector_id="+act.getInspector_id()+"                                                                      "+
                          " and  a.dt_date between '"+from_dt+"' and '"+to_dt+"'  " ;
          
			} else if(act.getRadio().equalsIgnoreCase("FL2D")){
                                                                                                                                               
          reportQuery =   " SELECT a.vch_gatepass_no,a.dt_date,'FL2D' as vch_from,b.vch_firm_name || ' (' || b.vch_mobile_no || ')' as wholesale,a.rcvdt "+ 
                          " FROM fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,licence.fl2_2b_2d_20_21 b                                               "+
                          " where a.rcvdbyid::int=b.int_app_id and b.inspector_id="+act.getInspector_id()+" " +
                          " and  a.dt_date between '"+from_dt+"' and '"+to_dt+"' order by dt_date";
			}

			 //System.out.println("reportQuery==="+reportQuery);
				pst = con.prepareStatement(reportQuery);
				
				rs = pst.executeQuery();
				 

			if (rs.next()) {
				

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate", act.getFromDate());
				parameters.put("toDate", act.getToDate());
				parameters.put("inspectorName", this.getInspectorName(act.getInspector_id()));
				
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "GatepassRecivedAtwholesale.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "GatepassRecivedAtwholesale" + "-" + n + ".pdf");
				act.setPdfName("GatepassRecivedAtwholesale" + "-" + n + ".pdf");
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
	
	public String getInspectorName(int id) {

		String name="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String query = " SELECT inspector_nm FROM licence.inspector_assign where inspector_id="+id+"";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			 

			rs = pstmt.executeQuery();

			if (rs.next()) {
				name=rs.getString("inspector_nm");

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
		return name;

	}
	
	
	
	

}
