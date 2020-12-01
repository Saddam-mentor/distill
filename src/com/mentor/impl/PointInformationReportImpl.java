package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.mentor.action.PointInformationReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class PointInformationReportImpl {
	
	// ===================print report======================

	public void printReport(PointInformationReportAction act){

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
			                                                                                                                  
			reportQuery = 	" SELECT a.district, d.jc_user_name as zone_name, CONCAT('Excise-CH-',c.description)as chrg_name,                         "+
							" b.description as district_name, SUM(a.actualrevenuereceipt)as tot_revenue, SUM(a.licencefeefromshop) as lic_fees,       "+
							" SUM(a.consumptioncl) as cl_consumption,                                                                                 "+
							" round(CAST(float8(SUM(a.consumptionflfromretailshop + a.consumptionflfrommodelshop + a.consumptionflfromhbr +           "+
							" ((a.consumptionflfromarmywhisky/2)+(a.consumptionflfromarmyrum/4))))  as numeric), 2) as fl_consumption,                "+
							" SUM(a.consumptionbeerfromretail + a.consumptionbeerfrommodel + a.consumptionbeerfromhbr +                               "+
							"  a.consumptionbeerfromarmy)as beer_consumption,                                                                         "+
							" SUM(a.otherreceiptsdutyfromdistbrew + a.otherreceiptsdutyfrombonds + a.otherreceiptsdutyfromotherheads) as othr_reciept "+
							" FROM retail.sixpointinfoentry a, public.district b, public.charge c,                                                    "+
							" public.joint_commissioners_zone_master d                                                                                "+
							" WHERE a.district=b.districtid AND b.chargeid=c.chargeid AND b.zoneid=c.zoneid AND c.zoneid=d.pk_id                      "+
							" AND a.yearid::text=(SELECT DISTINCT sesn_id FROM public.mst_season where active='a')                                    "+
							" GROUP BY d.jc_user_name, chrg_name, b.description, a.district                                                           "+
							" ORDER BY zone_name, chrg_name, district_name ";                                                                              
			

			

			pst = con.prepareStatement(reportQuery);
			System.out.println("reportQuery--------------" + reportQuery);

			rs = pst.executeQuery();

			if(rs.next()) {

				
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "Point_6InformationReport.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print, relativePathpdf+ File.separator + "Point_6InformationReport_" + n + ".pdf");
				act.setPdfName("Point_6InformationReport_" + n + ".pdf");
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
	
	
	//----------------------generate excel for date wise report------------------------------
	
		public boolean writeExcel(PointInformationReportAction act){


			Connection con = null;
			con = ConnectionToDataBase.getConnection();
			
			double tot_revenue = 0;
			double tot_lic_fees = 0;
			double tot_cl_cons = 0;
			double tot_fl_cons = 0;
			double tot_beer_cons = 0;
			double tot_othr_recipt = 0;
			String sql = "";
			String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
			FileOutputStream fileOut = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean flag = false;
			long k = 0;
			String date = null;
			

			sql = 	" SELECT a.district, d.jc_user_name as zone_name, CONCAT('Excise-CH-',c.description)as chrg_name,                         "+
					" b.description as district_name, SUM(a.actualrevenuereceipt)as tot_revenue, SUM(a.licencefeefromshop) as lic_fees,       "+
					" SUM(a.consumptioncl) as cl_consumption,                                                                                 "+
					" round(CAST(float8(SUM(a.consumptionflfromretailshop + a.consumptionflfrommodelshop + a.consumptionflfromhbr +           "+
					" ((a.consumptionflfromarmywhisky/2)+(a.consumptionflfromarmyrum/4))))  as numeric), 2) as fl_consumption,                "+
					" SUM(a.consumptionbeerfromretail + a.consumptionbeerfrommodel + a.consumptionbeerfromhbr +                               "+
					"  a.consumptionbeerfromarmy)as beer_consumption,                                                                         "+
					" SUM(a.otherreceiptsdutyfromdistbrew + a.otherreceiptsdutyfrombonds + a.otherreceiptsdutyfromotherheads) as othr_reciept "+
					" FROM retail.sixpointinfoentry a, public.district b, public.charge c,                                                    "+
					" public.joint_commissioners_zone_master d                                                                                "+
					" WHERE a.district=b.districtid AND b.chargeid=c.chargeid AND b.zoneid=c.zoneid AND c.zoneid=d.pk_id                      "+
					" AND a.yearid::text=(SELECT DISTINCT sesn_id FROM public.mst_season where active='a')                                    "+
					" GROUP BY d.jc_user_name, chrg_name, b.description, a.district                                                           "+
					" ORDER BY zone_name, chrg_name, district_name ";

			
			try {
				pstmt = con.prepareStatement(sql);
				
				rs = pstmt.executeQuery();

				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet worksheet = workbook.createSheet("Point Information Report");
				worksheet.setColumnWidth(0, 3000);
				worksheet.setColumnWidth(1, 8000);
				worksheet.setColumnWidth(2, 8000);
				worksheet.setColumnWidth(3, 8000);
				worksheet.setColumnWidth(4, 9000);
				worksheet.setColumnWidth(5, 9000);
				worksheet.setColumnWidth(6, 9000);
				worksheet.setColumnWidth(7, 9000);
				worksheet.setColumnWidth(8, 9000);
				worksheet.setColumnWidth(9, 9000);

				XSSFRow rowhead0 = worksheet.createRow((int) 0);
				XSSFCell cellhead0 = rowhead0.createCell((int) 0);
				cellhead0.setCellValue(" 6 Point Information Report ");
				
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
				cellhead1.setCellValue("S.No.");
				cellhead1.setCellStyle(cellStyle);
				
				XSSFCell cellhead2 = rowhead.createCell((int) 1);
				cellhead2.setCellValue("Zone Name");
				cellhead2.setCellStyle(cellStyle);
										
				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("Charge Name");
				cellhead3.setCellStyle(cellStyle);
				
				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("District");
				cellhead4.setCellStyle(cellStyle);
				
				XSSFCell cellhead5 = rowhead.createCell((int) 4);
				cellhead5.setCellValue("Total Revenue (In LAC Rs.)");
				cellhead5.setCellStyle(cellStyle);
				
				XSSFCell cellhead6 = rowhead.createCell((int) 5);
				cellhead6.setCellValue("License Fees (In LAC Rs.)");
				cellhead6.setCellStyle(cellStyle);
				
				XSSFCell cellhead7 = rowhead.createCell((int) 6);
				cellhead7.setCellValue("CL Consumption (In LAC Rs.)");
				cellhead7.setCellStyle(cellStyle);
				
				XSSFCell cellhead8 = rowhead.createCell((int) 7);
				cellhead8.setCellValue("FL Consumption (In LAC Bottle)");
				cellhead8.setCellStyle(cellStyle);
				
				XSSFCell cellhead9 = rowhead.createCell((int) 8);
				cellhead9.setCellValue("BEER Consumption (In LAC Can)");
				cellhead9.setCellStyle(cellStyle);
				
				XSSFCell cellhead10 = rowhead.createCell((int) 9);
				cellhead10.setCellValue("Other Reciept (In LAC Rs.)");
				cellhead10.setCellStyle(cellStyle);
				
				int i = 0;
				while (rs.next()) {
					/*Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt"));
					DateFormat formatter;
					formatter = new SimpleDateFormat("dd/MM/yyyy");
					date = formatter.format(dat);*/
					

					tot_revenue = tot_revenue + rs.getDouble("tot_revenue");
					tot_lic_fees = tot_lic_fees + rs.getDouble("lic_fees");
					tot_cl_cons = tot_cl_cons + rs.getDouble("cl_consumption");
					tot_fl_cons = tot_fl_cons + rs.getDouble("fl_consumption");					
					tot_beer_cons = tot_beer_cons + (rs.getDouble("beer_consumption"));
					tot_othr_recipt = tot_othr_recipt + (rs.getDouble("othr_reciept"));

					
					k++;
					XSSFRow row1 = worksheet.createRow((int) k);
					XSSFCell cellA1 = row1.createCell((int) 0);
					cellA1.setCellValue(k - 1);
					
					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("zone_name"));
					
					XSSFCell cellC1 = row1.createCell((int) 2);
					cellC1.setCellValue(rs.getString("chrg_name"));
					
					XSSFCell cellD1 = row1.createCell((int) 3);
					cellD1.setCellValue(rs.getString("district_name"));
					
					XSSFCell cellE1 = row1.createCell((int) 4);
					cellE1.setCellValue(rs.getDouble("tot_revenue"));
					
					XSSFCell cellF1 = row1.createCell((int) 5);
					cellF1.setCellValue(rs.getDouble("lic_fees"));
					
					XSSFCell cellG1 = row1.createCell((int) 6);
					cellG1.setCellValue(rs.getDouble("cl_consumption"));
					
					XSSFCell cellH1 = row1.createCell((int) 7);
					cellH1.setCellValue(rs.getDouble("fl_consumption"));
					
					XSSFCell cellI1 = row1.createCell((int) 8);
					cellI1.setCellValue(rs.getDouble("beer_consumption"));
					
					XSSFCell cellJ1 = row1.createCell((int) 9);
					cellJ1.setCellValue(rs.getDouble("othr_reciept"));
									
				}
				Random rand = new Random();
				int n = rand.nextInt(550) + 1;
				fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "-Point_6InformationReport.xlsx");
				act.setExcelName(n + "-Point_6InformationReport");

				XSSFRow row1 = worksheet.createRow((int) k + 1);

				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(" ");
				cellA1.setCellStyle(cellStyle);

				XSSFCell cellA2 = row1.createCell((int) 1);
				cellA2.setCellValue(" ");
				cellA2.setCellStyle(cellStyle);

				XSSFCell cellA3 = row1.createCell((int) 2);
				cellA3.setCellValue(" ");
				cellA3.setCellStyle(cellStyle);

				XSSFCell cellA4 = row1.createCell((int) 3);
				cellA4.setCellValue(" TOTAL: ");
				cellA4.setCellStyle(cellStyle);

				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue(tot_revenue);
				cellA5.setCellStyle(cellStyle);

				XSSFCell cellA6 = row1.createCell((int) 5);
				cellA6.setCellValue(tot_lic_fees);
				cellA6.setCellStyle(cellStyle);
				
				XSSFCell cellA7 = row1.createCell((int) 6);
				cellA7.setCellValue(tot_cl_cons);
				cellA7.setCellStyle(cellStyle);
				
				XSSFCell cellA8 = row1.createCell((int) 7);
				cellA8.setCellValue(tot_fl_cons);
				cellA8.setCellStyle(cellStyle);
				
				XSSFCell cellA9 = row1.createCell((int) 8);
				cellA9.setCellValue(tot_beer_cons);
				cellA9.setCellStyle(cellStyle);
				
				XSSFCell cellA10 = row1.createCell((int) 9);
				cellA10.setCellValue(tot_othr_recipt);
				cellA10.setCellStyle(cellStyle);
		
				
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();

				flag = true;
				act.setExcelFlag(true);
				con.close();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			return flag;
		
		
		}

}
