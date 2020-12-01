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

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.mentor.action.Report_On_ENA_For_CL_Action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class Report_On_ENA_For_CL_Impl {

	public void print(Report_On_ENA_For_CL_Action action) {
		String mypath=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH;
		

		String relativePath=mypath+File.separator+"ExciseUp"+File.separator+"WholeSale"+File.separator+"jasper";
		String relativePathpdf=mypath+File.separator+"ExciseUp"+File.separator+"WholeSale"+File.separator+"pdf";
		
		
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		try {
			con =ConnectionToDataBase.getConnection();
			reportQuery =  "	select x.seller,sum(x.adjusted)adjusted,x.login_dis_id, sum(x.user3_qty)approved,x.Purcheses_Name,sum(x.ENA_Requested)ENA_Requested,x.date\r\n" + 
					"from (select a.user3_qty,c.vch_undertaking_name as seller , a.login_dis_id," +
		"	b.total_molasses as adjusted,(select  c.vch_undertaking_name   from  dis_mst_pd1_pd2_lic c where a.login_dis_id =c.int_app_id_f) as Purcheses_Name, " +
		"	a.ena as ENA_Requested  ,a.date from distillery.online_ena_purchase a " +
		"	,distillery.app_request_reserve_adjustment_ena b , " +
		"	PUBLIC.dis_mst_pd1_pd2_lic c   " +
		"	where trim(a.purpose) ='FOR CL'  and  a.from_dis_id=c.int_app_id_f and b.int_dis_id=a.from_dis_id and a.approve_flag='A' and a.date "
		+ " between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"'   and '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+"'"
				+ " order by a.date   )x group by x.seller,x.login_dis_id,x.Purcheses_Name,x.date order by x.date,x.seller,x.Purcheses_Name";

			pst = con.prepareStatement(reportQuery);
  	     	rs=pst.executeQuery();
  	     	System.out.println(reportQuery);
	  		if(rs.next())
			{
				rs=pst.executeQuery();
			Map parameters = new HashMap();
			parameters.put("REPORT_CONNECTION", con);
			parameters.put("SUBREPORT_DIR", relativePath+File.separator);
			parameters.put("image", relativePath+File.separator);
			parameters.put("fromdate",Utility.convertUtilDateToSQLDate(action.getFromdate()));
			parameters.put("todate",Utility.convertUtilDateToSQLDate(action.getTodate()));
			
			 
			JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

			jasperReport = (JasperReport) JRLoader.loadObject(relativePath+File.separator+"ReportOnEnaForCl.jasper");
           
			JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);	
			  Random rand = new Random();
        	  int  n = rand.nextInt(250) + 1;

			JasperExportManager.exportReportToPdfFile(print,relativePathpdf+File.separator+"ReportOnEnaForCl"+n+".pdf");
			action.setPdfname("ReportOnEnaForCl"+n+".pdf");
			action.setPrintFlag(true);
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found")); 	
				action.setPrintFlag(false);
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally
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
