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
import com.mentor.action.ReportOn_tankConversion_DistilleryAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;


public class ReportOn_tankConversion_DistilleryImpl {
	
	
	
	
	// ===================== Print Pdf =================

	     public void printReport(ReportOn_tankConversion_DistilleryAction action) {

			String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

			String relativePath = mypath + File.separator + "ExciseUp"
					+ File.separator + "Distillery" + File.separator + "jasper";
			
			String relativePathpdf = mypath + File.separator + "ExciseUp"
					+ File.separator + "Distillery" + File.separator + "pdf";
			JasperReport jasperReport = null;
			PreparedStatement pst = null;
			Connection con = null;
			ResultSet rs = null;
			String reportQuery = null;
			

			try {
				con = ConnectionToDataBase.getConnection();
				
			
				reportQuery = "select distinct a.seq_id, a.date, a.distill_id,coalesce(a.order_no,'Pending For Approval')order_no, " +
					    "(SELECT vch_undertaking_name  from  dis_mst_pd1_pd2_lic where int_app_id_f=distill_id) as distillery_name, " +
					"(case when tank_from='SVC' then 'Spirit VAT(C-Heavy)' when tank_from='SVB' then 'Spirit VAT(B-Heavy)' " +
					" when tank_from='DVC' then 'Denatured Spirit VAT(C-Heavy)' when tank_from='DVB' then 'Denatured Spirit VAT(B-Heavy)' end )as tank_from, " +
					"(case when tank_to='SVC' then 'Spirit VAT(C-Heavy)' when tank_to='SVB' then 'Spirit VAT(B-Heavy)' " +
					  " when tank_to='DVC' then 'Denatured Spirit VAT(C-Heavy)' when tank_to='DVB' then 'Denatured Spirit VAT(B-Heavy)' end )as tank_to," +
					" a.status,a.usr3_dt,b.tank_name,b.capacity  " +
					  " from distillery.tank_transfer a,   " +
					  " distillery.tank_transfer_detail b where  b.int_app_id=a.seq_id   and " +
					  "a.date between '"+Utility.convertUtilDateToSQLDate(action.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"'  " ;
			
				 pst = con.prepareStatement(reportQuery);

				rs = pst.executeQuery();

				
				
				if (rs.next()) {
					
					

					rs = pst.executeQuery();
					Map parameters = new HashMap();
				    parameters.put("REPORT_CONNECTION", con);
				    parameters.put("fromDate",Utility.convertUtilDateToSQLDate(action.getFormdate()));
					parameters.put("toDate",Utility.convertUtilDateToSQLDate(action.getTodate()));
					parameters.put("image", relativePath + File.separator+ "");
					parameters.put("background_image", relativePath + File.separator+ "");
					parameters.put("SUBREPORT_DIR", relativePath + File.separator);
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath
							+ File.separator + "distillery_vat_Indent_Duty_Details.jasper");

				 
					JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager.exportReportToPdfFile(print,
							relativePathpdf + File.separator
									+ "distillery_vat_Indent_Duty_Details" + "-" + n + ".pdf" );
					action.setPrintName("distillery_vat_Indent_Duty_Details" + "-" + n + ".pdf");
					//act.setPrintFlag(true);
					action.setPrintFlag(true);
				
				} else {
					//act.setPrintFlag(false);
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"No Data Found!!", "No Data Found!!"));
					action.setPrintFlag(false);
					
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
