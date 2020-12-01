package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.mentor.action.HelpDeskComplainAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class HelpDeskComplainImpl {
	
	// ===================print report======================

	public void printReport(HelpDeskComplainAction act){

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String filter1 = "";
		String filter2 = "";

		try {
			con = ConnectionToDataBase.getConnection();
				
			
			if(act.getRadioType().equalsIgnoreCase("P") || act.getRadioType().equalsIgnoreCase("S")){
				
				filter1 = " AND a.status_flag='"+act.getRadioType()+"' ";				
				
			}else if(act.getRadioType().equalsIgnoreCase("A")){
				
				filter1 = "";				
			}
			
			if(act.getPrblmType().equalsIgnoreCase("DR") || act.getPrblmType().equalsIgnoreCase("PR") || act.getPrblmType().equalsIgnoreCase("ER")){
				
				filter2 = " AND a.complaint_type='"+act.getPrblmType()+"' ";
				
			}else if(act.getPrblmType().equalsIgnoreCase("9999")){
				
				filter2 = "";				
			}
			
			
			                                                                                                                            
		reportQuery = 	" SELECT a.problem_id, a.user_type, a.login_id, a.contact_no, a.created_date, a.solution_date,           "+
						" a.problem_description, a.solution_description, a.generated_ticket_no, a.status_flag, a.complaint_type, "+
						" CASE WHEN a.user_type='D' THEN 'Distillery'                                                            "+
						" WHEN a.user_type='B' THEN 'Brewery'                                                                    "+
						" WHEN a.user_type='SM' THEN 'Sugarmill'                                                                 "+
						" WHEN a.user_type='BWFL' THEN 'BWFL'                                                                    "+
						" WHEN a.user_type='FL2D' THEN 'FL2D'                                                                    "+
						" WHEN a.user_type='Wholesale' THEN 'Wholesale'                                                          "+
						" WHEN a.user_type='FL41' THEN 'FL41/39/40'                                                              "+
						" WHEN a.user_type='ED' THEN 'Excise Department' end as type                                             "+
						" FROM licence.help_desk a WHERE generated_ticket_no IS NOT NULL AND                                     "+
						" a.created_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                       "+
						" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                            "+
						" "+filter1+"  "+filter2+"                                                                               "+  
						" ORDER BY a.created_date DESC ";
			

			

			pst = con.prepareStatement(reportQuery);
			//System.out.println("reportQuery-----FL1---------" + reportQuery);

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

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "HelpDeskComplainReport.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print, relativePathpdf+ File.separator + "HelpDeskComplainReport_"+n+".pdf");
				act.setPdfName("HelpDeskComplainReport_"+n+".pdf");
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
