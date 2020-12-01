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

import com.mentor.action.UnusedBarcodeAndQRCodeAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class UnusedBarcodeAndQRCodeImpl {
	
	
	public boolean admin()
	{
	boolean b=false;
	
	String queryList="";
	try{
		
		if (ResourceUtil.getUserNameAllReq().trim().equals("admin"))
		{
		 b=true;
		}

			
		
		
	}
	catch(Exception se)
	{
		se.printStackTrace();
	}
	
	return b;
	
}
	public void printDCL(UnusedBarcodeAndQRCodeAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
	
		
		
			reportQuery=" select seq,case_no,dispatch_date,gtin_no " +
					"  from public.unused_barcode_qrcode('"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' ," +
					" '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"') ";
			
					
		try {
			con = ConnectionToDataBase.getConnection3();	
			pst = con.prepareStatement(reportQuery);
		
			System.out.println("====11111===="+reportQuery);
			rs = pst.executeQuery();
			System.out.println(rs);
			if (rs.next()) 
			{
				System.out.println("hiiii");
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("todate", Utility.convertUtilDateToSQLDate(action.getTodate()));
				parameters.put("fromdate", Utility.convertUtilDateToSQLDate(action.getFromdate()));

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
System.out.println("-----------------------------------------------------------------------------");
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "ReportUnsuedBarCode.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "ReportUnsuedBarCode" + n + ".pdf");
				action.setPdfname("ReportUnsuedBarCode" + n + ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
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
	
	public void printDFL(UnusedBarcodeAndQRCodeAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;

		
	
			reportQuery=" select seq,case_no,dispatch_date,gtin_no " +
					"  from unused_barcode_qrcode_fl('"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' ," +
					" '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"') ";
		
					
		try {
			con = ConnectionToDataBase.getConnection3();	
			pst = con.prepareStatement(reportQuery);
		
			System.out.println("====11111===="+reportQuery);
			rs = pst.executeQuery();
			System.out.println(rs);
			if (rs.next()) 
			{
				System.out.println("hiiii");
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("todate", Utility.convertUtilDateToSQLDate(action.getTodate()));
				parameters.put("fromdate", Utility.convertUtilDateToSQLDate(action.getFromdate()));

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
System.out.println("-----------------------------------------------------------------------------");
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "ReportUnsuedBarCodeFL.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "ReportUnsuedBarCodeFL" + n + ".pdf");
				action.setPdfname("ReportUnsuedBarCodeFL" + n + ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
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

	/*reportQuery = " select x.seq,x.caseno,x.dispatch,x.gstin   "+
	" from (SELECT a.seq as seq, a.case_no as caseno,  a.dispatch_date as dispatch,  a.gtin_no as gstin,"+
 " (select (case_no || gtin_no)  as cgt from public.bottling_cl  where (case_no || gtin_no)  not in "+
 " (select (casecode ||etin)  FROM bottling_unmapped.disliry_unmap_cl )    ) "+
 " FROM public.bottling_cl a, bottling_unmapped.disliry_unmap_cl b   "+
 " where  a.dispatch_date between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' ) x " +
 " group by x.seq,x.caseno,x.dispatch,x.gstin ";*/

	public void printBWFL(UnusedBarcodeAndQRCodeAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
	
		
		
			reportQuery=" select plan_id, date_plan, etin, casecode from " +
					" public.unused_barcode_qrcode_for_BWFL('"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' ," +
					" '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"') ";
			
				
					
		try {
			con = ConnectionToDataBase.getConnection3();	
			pst = con.prepareStatement(reportQuery);
		
			
			rs = pst.executeQuery();
			System.out.println(rs);
			if (rs.next()) 
			{
				System.out.println("hiiii");
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("todate", action.getTodate());
				parameters.put("fromdate", action.getFromdate());

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
System.out.println("-----------------------------------------------------------------------------");
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "unused_QR_Bar_Code_for_BWFL.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "unused_QR_Bar_Code_for_BWFL" + n + ".pdf");
				action.setPdfname("unused_QR_Bar_Code_for_BWFL" + n + ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
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

	public void printBre(UnusedBarcodeAndQRCodeAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
	
		
		
			reportQuery=" select seq,case_no,dispatch_date,gtin_no from " +
					" public.unused_barcode_qrcode_brewery('"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' ," +
					" '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"') ";
			
				
					
		try {
			con = ConnectionToDataBase.getConnection3();	
			pst = con.prepareStatement(reportQuery);
		
			
			rs = pst.executeQuery();
			System.out.println(rs);
			if (rs.next()) 
			{
				System.out.println("hiiii");
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				
				parameters.put("todate", action.getTodate());
				parameters.put("fromdate", action.getFromdate());
				
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
System.out.println("-----------------------------------------------------------------------------");
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "unused_QR_Bar_Code_for_breweryL.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "unused_QR_Bar_Code_for_breweryL" + n + ".pdf");
				action.setPdfname("unused_QR_Bar_Code_for_breweryL" + n + ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
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
