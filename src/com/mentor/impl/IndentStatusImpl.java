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

import com.mentor.action.IndentStatusAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


public class IndentStatusImpl {

	public void printReport(IndentStatusAction action) {
		String mypath=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH;
		String relativePath=mypath+File.separator+"ExciseUp"+File.separator+"reports";
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst=null;
		Connection con=null;
		ResultSet rs=null;
		String reportQuery=null;
		String reportQuery1=null;
		try {
			con =ConnectionToDataBase.getConnection();
			
	 
			
			reportQuery="SELECT name, cr_date, number_cases, Description as District , cases_date, total_cases, type, user_id " +
					"FROM distillery.indent_status a,public.district b " +
					"where a.district_id::int=b.DistrictID and type in('CL','FL') and " +
					"cr_date=( select max(cr_date) from distillery.indent_status where user_id=a.user_id) order by cr_date";
			
			
			reportQuery1= " SELECT name, cr_date, number_cases,  Description as District , cases_date, total_cases, type, user_id " +
					"FROM distillery.indent_status a,public.district b where a.district_id::int=b.DistrictID and type in('Beer')" +
					" and cr_date=( select max(cr_date) from distillery.indent_status where user_id=a.user_id) order by cr_date";
			
			
			if(action.getRadioCheck().equalsIgnoreCase("D"))
			{
				pst=con.prepareStatement(reportQuery);
				System.out.println("report query------------"+reportQuery);
			}
			else if(action.getRadioCheck().equalsIgnoreCase("B"))
			{
				pst=con.prepareStatement(reportQuery1);
				System.out.println("report query------111-------"+reportQuery1);
			}
			
			rs=pst.executeQuery();
			if(rs.next())
			{
				rs=pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath+File.separator);
				parameters.put("image", relativePath+File.separator);
				 
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+File.separator+"IndentStatus.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);	
				  Random rand = new Random();
	        	  int  n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,relativePath+File.separator+"IndentStatus"+n+".pdf");
				action.setPdfname("IndentStatus"+n+".pdf");
				action.setPrintFlag(true);
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data Found!!", "No Data Found!!"));
				action.setPrintFlag(false);
			}
			 
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}


	}

	
}
