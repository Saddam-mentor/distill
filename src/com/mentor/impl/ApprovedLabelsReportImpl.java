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

import com.mentor.action.ApprovedLabelsReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class ApprovedLabelsReportImpl {

	public void printReport(ApprovedLabelsReportAction act)
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
			
		"	select a.unit_name, sum(b.fees)as total_fee, count(b.vch_approved) as total_no_label, b.size ,  "+
		"	c.brand_name, c.brand_strength,a.domain_name ,  "+
		"	CASE WHEN a.unit_type='D' THEN 'Distillery'  "+
		"	WHEN a.unit_type='B' THEN 'Brewery'  "+
		"	WHEN a.unit_type='IU' THEN 'Importing Unit' "+  
		"	WHEN a.unit_type='DCSD' THEN 'Distillery For CSD' "+ 
		"	WHEN a.unit_type='BCSD' THEN 'Brewery For CSD'   "+
		"	WHEN a.unit_type='WCSD' THEN 'Winery For CSD'  "+
		"	WHEN a.unit_type='BUCSD' THEN 'Bottling Unit For CSD' "+ 
		"	else a.unit_type end as unit_type_name   "+
		"	FROM brandlabel.brand_label_applications a  "+
		"	left outer join   "+
		"	brandlabel.brand_label_application_details b on a.app_id=b.app_id and b.vch_approved='A'  "+
		"	left outer join  "+
		"	brandlabel.brand_registration c  on b.brand_id=c.brand_id_pk  "+
		"	where a.vch_approved='APPROVED'  and a.unit_type='D'  "+
		"	and a.lic_cat='"+act.getRadio()+"' "+
		"	group by  a.unit_name, unit_type_name ,a.domain_name, "+ 
		"	 b.size , c.brand_name, c.brand_strength "+
		"	order by unit_type_name , unit_name  "; 
			

				pst = con.prepareStatement(reportQuery);
			 
				
				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("radioType",act.getRadio());
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "BrandWiseLabelApprovalReport.jasper");
				
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "BrandWiseLabelApprovalReport" + "-" + n + ".pdf");
				act.setPdfName("BrandWiseLabelApprovalReport" + "-" + n + ".pdf");
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage() ,e.getMessage()));
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
	
	
	//-------------------------------------- Beer --------------------
	
	
	
	public void printReportBeer(ApprovedLabelsReportAction act)
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
				
				"	select a.unit_name, sum(b.fees)as total_fee, count(b.vch_approved) as total_no_label, b.size ,  "+
				"	c.brand_name, c.brand_strength,a.domain_name ,  "+
				"	CASE WHEN a.unit_type='D' THEN 'Distillery'  "+
				"	WHEN a.unit_type='B' THEN 'Brewery'  "+
				"	WHEN a.unit_type='IU' THEN 'Importing Unit' "+  
				"	WHEN a.unit_type='DCSD' THEN 'Distillery For CSD' "+ 
				"	WHEN a.unit_type='BCSD' THEN 'Brewery For CSD'   "+
				"	WHEN a.unit_type='WCSD' THEN 'Winery For CSD'  "+
				"	WHEN a.unit_type='BUCSD' THEN 'Bottling Unit For CSD' "+ 
				"	else a.unit_type end as unit_type_name   "+
				"	FROM brandlabel.brand_label_applications a  "+
				"	left outer join   "+
				"	brandlabel.brand_label_application_details b on a.app_id=b.app_id and b.vch_approved='A'  "+
				"	left outer join  "+
				"	brandlabel.brand_registration c  on b.brand_id=c.brand_id_pk  "+
				"	where a.vch_approved='APPROVED'  and a.unit_type='B'  "+
				"	and a.lic_cat='"+act.getRadio()+"' "+
				"	group by  a.unit_name, unit_type_name ,a.domain_name, "+ 
				"	 b.size , c.brand_name, c.brand_strength "+
				"	order by unit_type_name , unit_name  "; 
				
			
			
				pst = con.prepareStatement(reportQuery);
				System.out.println("reportQuery----  Beer  ------"+reportQuery);
				
				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("radioType",act.getRadio());
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "BrandWiseLabelApprovalReportBeer.jasper");
				
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "BrandWiseLabelApprovalReport" + "-" + n + ".pdf");
				act.setPdfName("BrandWiseLabelApprovalReport" + "-" + n + ".pdf");
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
