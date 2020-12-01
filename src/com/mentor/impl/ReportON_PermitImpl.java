package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mentor.action.ReportON_PermitAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class ReportON_PermitImpl {

	public void printReport(ReportON_PermitAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "jasper";

		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String type = null;

		try {
			con = ConnectionToDataBase.getConnection();
			if (act.getRadioType().equalsIgnoreCase("C")) {

				type = "Consolidated";

				reportQuery = "select distinct a.no_of_cases,b.import_fee,b.permit_nmbr,c.description,a.cr_date "+
                              " from  bwfl_license.import_permit_dtl_20_21 a,bwfl_license.import_permit_20_21 b,public.district c "+
                              " where a.fk_id=b.id  and a.district_id=c.districtid and a.cr_date  "+
                              " between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' "+
                              " order by  c.description";

			} else if (act.getRadioType().equalsIgnoreCase("D")) {

				type = "cc";

				reportQuery =" select distinct a.no_of_cases,b.import_fee,b.permit_nmbr,c.description,a.cr_date,e.vch_state_name,a.login_type,b.print_permit_dt"+
						     " from  bwfl_license.import_permit_dtl_20_21 a,bwfl_license.import_permit_20_21 b,public.district c, "+
						     " public.other_unit_registration_20_21 d,public.state_ind e "+
						     " where a.fk_id=b.id  and a.district_id=c.districtid and b.unit_id=d.unit_id and d.vch_indus_type='OUPB' and a.login_type='BWFL'  and "+
						     " d.vch_reg_office_state::int=e.int_state_id and a.cr_date  " +
						     " between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' "+
						     " order by  c.description ";
			}

			System.out.println("======check=======" + reportQuery);
			pst = con.prepareStatement(reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("type", type);
				parameters.put("todate", act.getTodate());
				parameters.put("formdate", act.getFormdate());
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				if (act.getRadioType().equalsIgnoreCase("C")) {

					jasperReport = (JasperReport) JRLoader
							.loadObject(relativePath
									+ File.separator
									+ "REPORT_ON_PERMIT_Consolideted.jasper");

					JasperPrint print = JasperFillManager.fillReport(
							jasperReport, parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager
							.exportReportToPdfFile(
									print,
									relativePathpdf
											+ File.separator
											+ "REPORT_ON_PERMIT_Consolideted"
											+ "-" + n + ".pdf");
					act.setPdfName("REPORT_ON_PERMIT_Consolideted"
							+ "-" + n + ".pdf");
					act.setPrintFlag(true);

				}

				else {

					jasperReport = (JasperReport) JRLoader
							.loadObject(relativePath
									+ File.separator
									+ "REPORT_ON_PERMIT_Detailed.jasper");

					JasperPrint print = JasperFillManager.fillReport(
							jasperReport, parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager
							.exportReportToPdfFile(
									print,
									relativePathpdf
											+ File.separator
											+ "REPORT_ON_PERMIT_Detailed"
											+ "-" + n + ".pdf");
					act.setPdfName("REPORT_ON_PERMIT_Detailed"
							+ "-" + n + ".pdf");
					act.setPrintFlag(true);
				}
				/*
				 * jasperReport = (JasperReport)
				 * JRLoader.loadObject(relativePath + File.separator +
				 * "Report_On_Dispatches.jasper");
				 * 
				 * 
				 * JasperPrint print =
				 * JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				 * Random rand = new Random(); int n = rand.nextInt(250) + 1;
				 * JasperExportManager.exportReportToPdfFile(print,
				 * relativePathpdf + File.separator + "Report_On_Dispatches" +
				 * "-" + n + ".pdf" ); act.setPdfName("Report_On_Dispatches" +
				 * "-" + n + ".pdf"); //act.setPrintFlag(true);
				 * act.setPrintFlag(true);
				 */

			} else {
				// act.setPrintFlag(false);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
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

	// -------------------------------------------------------------

	public void printExcel_Detailed(ReportON_PermitAction act) {

		Connection con = null;
		double case_total = 0.0;
		double fees_total = 0.0;
	

		String type = "";
		String reportQuery = "";


		if (act.getRadioType().equalsIgnoreCase("D")) {

			type = "Detailed";

			reportQuery =" select distinct a.no_of_cases,b.import_fee,b.permit_nmbr,c.description,a.cr_date,e.vch_state_name, "+
					     " a.login_type,b.print_permit_dt "+
					     " from  bwfl_license.import_permit_dtl_20_21 a,bwfl_license.import_permit_20_21 b,public.district c, "+
					     " public.other_unit_registration_20_21 d,public.state_ind e "+
					     " where a.fk_id=b.id  and a.district_id=c.districtid and b.unit_id=d.unit_id and d.vch_indus_type='OUPB' "+
					     " and a.login_type='BWFL'  and "+
					     " d.vch_reg_office_state::int=e.int_state_id and a.cr_date  " +
					     "between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' "+
					     " order by  c.description ";
		} else {

		}

		System.out.println("excel query===  " + reportQuery);

		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long start = 0;
		long end = 0;
		boolean flag = false;
		long k = 0;

		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(reportQuery);

			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook
					.createSheet("Report On Permit For Detailed");

			worksheet.setColumnWidth(0, 4000);
			worksheet.setColumnWidth(1, 12000);
			worksheet.setColumnWidth(2, 3000);
			worksheet.setColumnWidth(3, 3000);
			worksheet.setColumnWidth(4, 3000);
			worksheet.setColumnWidth(5, 3000);
			worksheet.setColumnWidth(6, 3000);
			worksheet.setColumnWidth(7, 3000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);

			cellhead0.setCellValue("Report On Permit For " + type
					+ " From (Date" + Utility.convertUtilDateToSQLDate(act.getFormdate())
					+ " To "
					+ Utility.convertUtilDateToSQLDate(act.getTodate())
					+ ")");

			rowhead0.setHeight((short) 700);

			XSSFCellStyle cellStyl = workbook.createCellStyle();
			cellStyl = workbook.createCellStyle();
			XSSFFont hSSFFont = workbook.createFont();
			hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
			hSSFFont.setFontHeightInPoints((short) 12);
			hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			hSSFFont.setColor(HSSFColor.GREEN.index);
			cellStyl.setFont(hSSFFont);

			cellhead0.setCellStyle(cellStyl);
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			XSSFCellStyle unlockcellStyle = workbook.createCellStyle();
			unlockcellStyle.setLocked(false);

			

			XSSFRow rowhead = worksheet.createRow((int) 1);

			XSSFCell cellhead1 = rowhead.createCell((int) 0);
			cellhead1.setCellValue("Sr. No");
			cellhead1.setCellStyle(cellStyle);

			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("Permit No");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Permit Date");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Bond Name");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Parent Unit-State");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("District");
			cellhead6.setCellStyle(cellStyle);

			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Cases");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("Permit Fees");
			cellhead8.setCellStyle(cellStyle);


			k = k + 1;
			int i = 0;

			while (rs.next()) {
				// total = total + rs.getDouble("amount");
				case_total = case_total + rs.getDouble("no_of_cases");
				System.out.println(case_total);
				fees_total = fees_total + rs.getInt("import_fee");
				

				i++;
				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				XSSFCell cellA1 = row1.createCell((int) 0); //
				cellA1.setCellValue(k - 1); //

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("permit_nmbr"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getInt("print_permit_dt"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getDouble("login_type"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("vch_state_name"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("description"));

				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("no_of_cases"));

				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("import_fee"));

			

				// System.out.println("------------ "+Math.round(cases_of_last_year_sale));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "REPORT_ON_PERMIT_Detailed.xls");

			act.setExlname(n
					+ "REPORT_ON_PERMIT_Detailed.xls");
			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA2 = row1.createCell((int) 0);
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 1);
			cellA3.setCellValue("");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 2);
			cellA4.setCellValue("");
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 3);
			cellA5.setCellValue("");
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 4);
			cellA6.setCellValue("");
			cellA6.setCellStyle(cellStyle);

			XSSFCell cellA7 = row1.createCell((int) 5);
			cellA7.setCellValue("TOTAL:- ");
			cellA7.setCellStyle(cellStyle);

			XSSFCell cellA8 = row1.createCell((int) 6);
			cellA8.setCellValue(case_total);
			cellA8.setCellStyle(cellStyle);

			XSSFCell cellA9 = row1.createCell((int) 7);
			cellA9.setCellValue(fees_total);
			cellA9.setCellStyle(cellStyle);

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			act.setExcelFlag(true);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				if (con != null)
					con.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// ----------------------------------------------------

	public void printExcel_Consolidated(ReportON_PermitAction act) {

		Connection con = null;

		double case_total = 0.0;
		double fees_total = 0.0;
	
		String type = "";
		String reportQuery = "";



	if (act.getRadioType().equalsIgnoreCase("C")) {

			type = "Consolidated";

			reportQuery = "select distinct a.no_of_cases,b.import_fee,b.permit_nmbr,c.description,a.cr_date "+
                    " from  bwfl_license.import_permit_dtl_20_21 a,bwfl_license.import_permit_20_21 b,public.district c "+
                    " where a.fk_id=b.id  and a.district_id=c.districtid and a.cr_date  "+
                    " between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' "+
                    " order by  c.description";
		}

		else {

		}
		System.out.println("excel query===  " +reportQuery);

		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long start = 0;
		long end = 0;
		boolean flag = false;
		long k = 0;

		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(reportQuery);

			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook
					.createSheet("Report O Permit For Consolidated ");

			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 3000);
			worksheet.setColumnWidth(2, 12000);
			worksheet.setColumnWidth(3, 6000);
			worksheet.setColumnWidth(4, 6000);
		

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);

			cellhead0.setCellValue("Report On Permit For " + type
					+ " From (Date" + Utility.convertUtilDateToSQLDate(act.getFormdate())
					+ " To "
					+ Utility.convertUtilDateToSQLDate(act.getTodate())
					+ ")");

			rowhead0.setHeight((short) 700);

			XSSFCellStyle cellStyl = workbook.createCellStyle();
			cellStyl = workbook.createCellStyle();
			XSSFFont hSSFFont = workbook.createFont();
			hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
			hSSFFont.setFontHeightInPoints((short) 12);
			hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			hSSFFont.setColor(HSSFColor.GREEN.index);
			cellStyl.setFont(hSSFFont);

			cellhead0.setCellStyle(cellStyl);
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			XSSFCellStyle unlockcellStyle = workbook.createCellStyle();
			unlockcellStyle.setLocked(false);

			 k = k + 1;
			

			XSSFRow rowhead = worksheet.createRow((int) 1);

			XSSFCell cellhead1 = rowhead.createCell((int) 0);
			cellhead1.setCellValue("Sr. No");
			cellhead1.setCellStyle(cellStyle);

			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("District");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("No Of Permit");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Cases");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Permit Fees");
			cellhead5.setCellStyle(cellStyle);

	


			//k = k + 2;
			int i = 0;

			while (rs.next()) {
				case_total = case_total + rs.getDouble("no_of_cases");
				System.out.println(case_total);
				fees_total = fees_total + rs.getInt("import_fee");

				i++;
				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				XSSFCell cellA1 = row1.createCell((int) 0); //
				cellA1.setCellValue(k - 1); //

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("permit_nmbr"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getDouble("no_of_cases"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("import_fee"));

			

				// System.out.println("------------ "+Math.round(cases_of_last_year_sale));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "REPORT_ON_PERMIT_Consolideted.xls");

			act.setExlname(n
					+ "REPORT_ON_PERMIT_Consolideted.xls");
			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA2 = row1.createCell((int) 0);
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 1);
			cellA3.setCellValue("");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 2);
			cellA4.setCellValue("TOTAL:-");
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 3);
			cellA5.setCellValue(case_total);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 4);
			cellA6.setCellValue(fees_total);
			cellA6.setCellStyle(cellStyle);


			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			act.setExcelFlag(true);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				if (con != null)
					con.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
