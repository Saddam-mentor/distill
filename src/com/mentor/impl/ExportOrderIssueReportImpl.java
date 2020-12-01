package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.mentor.action.ExportOrderIssueReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class ExportOrderIssueReportImpl {
	
	/*public String getDetails(ExportOrderIssueReportAction ac) {
		int id = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String s = "";
		try {
			con = ConnectionToDataBase.getConnection();
			String queryList = " SELECT int_app_id, vch_applicant_name, vch_firm_name ,"
					+ " vch_mobile_no,vch_license_type, vch_licence_no "
					+ " FROM licence.fl2_2b_2d WHERE loginid= ? ";

			pstmt = con.prepareStatement(queryList);
			pstmt.setString(1, ResourceUtil.getUserNameAllReq().trim());

			rs = pstmt.executeQuery();

			while (rs.next()) {

				ac.setName(rs.getString("vch_firm_name"));
				ac.setDisId(rs.getInt("int_app_id"));

			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();

				if (rs != null)
					rs.close();

				if (con != null)
					con.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return "";

	}*/

	

	
		
		
		public void printReport(ExportOrderIssueReportAction act) {


			String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

			String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "reports";
			String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "reports" ;
			JasperReport jasperReport = null;
			PreparedStatement pst = null;
			Connection con = null;
			ResultSet rs = null;
			String reportQuery = null;
			
			try {
				con = ConnectionToDataBase.getConnection();
				
				
				
				
   reportQuery = " select a.esign_date , a.app_id ,c.vch_undertaking_name , sum(coalesce(b.reqstd_bottles,0)) as reqstd_bottles ," +
   		         " sum(coalesce(b.dispatched_bottles,0)) as dispatched_bottles ,"+
				 " sum(b.reqstd_bottles)-sum(coalesce(b.dispatched_bottles,0)) as avl_bottles                                                                                                 "+
				 " from distillery.eoi_app_for_export_order a, distillery.eoi_app_for_export_order_brand b, public.dis_mst_pd1_pd2_lic c                                                      "+
				 " where a.app_id = b.app_id_fk and a.esign_date is not null and a.int_dist_id = c.int_app_id_f " +
				 " and a.esign_date  between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'" +
                 " and  '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' "+
				 " group by a.esign_date , " +
				 " a.app_id,c.vch_undertaking_name    order by   c.vch_undertaking_name, a.esign_date  ";
						
					pst = con.prepareStatement(reportQuery);
					
				//System.out.println("reportQuery----------"+reportQuery+"=======act.getRadio()--------"+act.getRadio());
					
					rs = pst.executeQuery();
					
					SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
					String fromdate= formatter.format(act.getFromDate());
					String todate= formatter.format(act.getToDate());
;
				if (rs.next()) {

					rs = pst.executeQuery();
					Map parameters = new HashMap();
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("SUBREPORT_DIR", relativePath + File.separator);
					parameters.put("image", relativePath + File.separator);
					parameters.put("from_date",fromdate);
					parameters.put("to_date",todate);
				
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
					

					
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "ExportOrdrIssueDetail.jasper");
					

					JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "ExportOrdrIssueDetail" + "-" + n + ".pdf");
					act.setPdfName("ExportOrdrIssueDetail" + "-" + n + ".pdf");
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
		
		
		

	}
