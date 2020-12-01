package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.mentor.action.ImportPermitStatusAction;
import com.mentor.action.LifitingComparisionFLAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class ImportPermitStatusImpl {
	
	// =======================print report=================================

	public void printReportCD(ImportPermitStatusAction act){


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
			
			
	        reportQuery = 		" SELECT ds.districtid, ds.description,                                                        "+                   
					" SUM(COALESCE(x.all_cases,0))all_cases, SUM(COALESCE(x.all_bottles,0))all_bottles, SUM(COALESCE(x.all_bl,0))all_bl,  "+
					"	SUM(COALESCE(x.ap_cases,0))ap_cases, SUM(COALESCE(x.ap_bottles,0))ap_bottles, SUM(COALESCE(x.ap_bl,0))ap_bl,       "+
					"	SUM(COALESCE(x.rj_cases,0))rj_cases, SUM(COALESCE(x.rj_bottles,0))rj_bottles, SUM(COALESCE(x.rj_bl,0))rj_bl , "+
					"	sum(all_app) as all_app,sum(all_approved) as all_approved,sum(all_reject) as all_reject       "+
					"	FROM district ds,                                                                                                  "+
					"	 (SELECT a.district_id, SUM(b.no_of_cases) as all_cases, SUM(b.pland_no_of_bottles) as all_bottles,               "+  
				"	SUM(round(CAST(float8(((b.pland_no_of_bottles)*pk.quantity)/1000) as numeric), 2)) as all_bl,                 "+
			"		 0 as ap_cases, 0 as ap_bottles, 0 as ap_bl, 0 as rj_cases, 0 as rj_bottles, 0 as rj_bl,0 as all_app, 0 as all_approved, 0 as all_reject    "+                       
		"			FROM bwfl_license.import_permit a, bwfl_license.import_permit_dtl b, distillery.packaging_details_19_20 pk        "+
		"			WHERE a.id=b.fk_id AND a.app_id=b.app_id AND a.login_type=b.login_type AND a.district_id=b.district_id             "+
		"			AND b.pckg_id=pk.package_id AND a.bwfl_type='"+act.getUnitType()+"'                                             "+
		"			GROUP BY a.district_id                                                                                          "+
		"				UNION                                                                                                           "+ 
		"			 SELECT a.district_id, 0 as all_cases, 0 as all_bottles, 0 as all_bl,                                              "+
		"			SUM(b.no_of_cases) as ap_cases, SUM(b.pland_no_of_bottles) as ap_bottles,                                         "+
		"			SUM(round(CAST(float8(((b.pland_no_of_bottles)*pk.quantity)/1000) as numeric), 2)) as ap_bl,                     "+
		"			0 as rj_cases, 0 as rj_bottles, 0 as rj_bl,0 as all_app, 0 as all_approved, 0 as all_reject                        "+                                    
		"			FROM bwfl_license.import_permit a, bwfl_license.import_permit_dtl b, distillery.packaging_details_19_20 pk      "+
		"			WHERE a.id=b.fk_id AND a.app_id=b.app_id AND a.login_type=b.login_type AND a.district_id=b.district_id            "+
		"			AND b.pckg_id=pk.package_id AND a.bwfl_type='"+act.getUnitType()+"'                                             "+
		"			AND a.deo_date IS NOT NULL AND a.vch_approved='APPROVED'                                                         "+
		"			GROUP BY a.district_id                                                                                             "+
		"			 UNION                                                                                                              "+ 
		"			 SELECT a.district_id, 0 as all_cases, 0 as all_bottles, 0 as all_bl,                                     "+
		"			0 as ap_cases, 0 as ap_bottles, 0 as ap_bl,                                                                       "+
		"			 SUM(b.no_of_cases) as rj_cases, SUM(b.pland_no_of_bottles) as rj_bottles,                                  "+
		"			SUM(round(CAST(float8(((b.pland_no_of_bottles)*pk.quantity)/1000) as numeric), 2)) as rj_bl,0 as all_app, 0 as all_approved, 0 as all_reject     "+               
		"		 FROM bwfl_license.import_permit a, bwfl_license.import_permit_dtl b, distillery.packaging_details_19_20 pk     "+
		"			 WHERE a.id=b.fk_id AND a.app_id=b.app_id AND a.login_type=b.login_type AND a.district_id=b.district_id           "+
		"			AND b.pckg_id=pk.package_id AND a.bwfl_type='"+act.getUnitType()+"'                                             "+ 
		"			AND a.deo_date IS NOT NULL AND a.vch_approved='REJECTED'                                                         "+
		"			GROUP BY a.district_id                                                                                         "+
		"			UNION                                                                                                               "+
		"			select district_id,0 as all_cases, 0 as all_bottles, 0 as all_bl, 0 as ap_cases, 0 as ap_bottles, 0 as ap_bl, 0 as rj_cases, 0 as rj_bottles,  "+
		"   0 as rj_bl,sum(all_app) as all_app,sum(all_approved) as all_approved,sum(all_reject) as all_reject from                                                  "+
		"  (select count(*) as all_app, 0 as all_approved, 0 as all_reject,district_id from  bwfl_license.import_permit where bwfl_type='"+act.getUnitType()+"' group by district_id          "+
		"  union                                                                                                                                                           "+
		"  select  0  as all_app, count(*) as all_approved,  0 as all_reject,district_id from  bwfl_license.import_permit where bwfl_type='"+act.getUnitType()+"' and vch_approved='APPROVED' group by district_id    "+
		"  union                                                                                                                              "+
		"  select 0  as all_app,0 as all_approved, count(*) as all_reject ,district_id from  bwfl_license.import_permit where bwfl_type='"+act.getUnitType()+"'       "+
		"  and vch_approved='REJECTED' group by district_id)x group by district_id                                                                "+

		"			)x                                                                                                                   "+                                       
				
		"				 WHERE ds.districtid!=0 AND ds.districtid=x.district_id                                                         "+ 
			"			 GROUP BY ds.districtid, ds.description                                                            "+
			"			 ORDER BY ds.description     ";                                                     



			pst = con.prepareStatement(reportQuery);
			System.out.println("reportQuery----------" + reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);


				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "ImportPermitStatusConsolidated.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "ImportPermitStatusConsolidated" + "-" + n + ".pdf");
				act.setPdfName("ImportPermitStatusConsolidated" + "-" + n + ".pdf");
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
	
	
	public boolean excelCD(ImportPermitStatusAction action) throws ParseException {

		Connection con = null;
		
	//System.out.println("action.getDateSelected()===="+action.getDateSelected());
	/*	double current_bl_per = 0;
		double current_bl = 0;
		
		double commulative_bl = 0;
		double commulative_bl_per = 0;
		

		double yearly_mgq = 0;
		double monthly_mgq = 0;*/
		
	    int Applied_Cases=0;
	    int Applied_Bottle=0;
	    double Applied_Bulk_letre=0;
	    int Total_Applied=0;
	    
	    int Approved_Cases=0;
	    int Approved_Bottle=0;
	    double Approved_Bulk_letre=0;
	    int Total_Approved=0;
	    
	    int Rejected_Cases=0;
	    int Rejected_Bottle=0;
	    double Rejected_Bulk_letre=0;
	    int Total_Rejected=0;
	   
	    DecimalFormat diciformatter = new DecimalFormat("#.##");
		
	
		// Date date11=new SimpleDateFormat("dd/MM/yyyy").parse(action.getDateSelected());
		 
		 //System.out.println("action.getDateSelected()==date11=="+date11);
		
		 /*if(!action.getDistid().equalsIgnoreCase("99") && !action.getDistid().equalsIgnoreCase("9999"))
			{
				filterDistrict=" and x.district_id="+ Integer.parseInt(action.getDistid())+ "";
				
				
				
				if(action.getSectorId().equalsIgnoreCase("0"))
				{
					filterSector ="";
					
					if(action.getShopId().equalsIgnoreCase("0"))
					{
						filterShop="";
					}
					else
					{
						filterShop=" and x.serial_no='"+action.getShopId()+"' ";
					}
				}
					
				else
				{
					filterSector = " and x.sector='"+action.getSectorId()+"' ";
					
					
					if(action.getShopId().equalsIgnoreCase("0"))
					{
						filterShop="";
					}
					else
					{
						filterShop=" and x.serial_no='"+action.getShopId()+"' ";
					}
					
					
				}
				
				
				
			}*/
		/*else
		{
			System.out.println("-----123--------");
			filterDistrict="";
			filterSector = "";
			filterShop="";
		}*/

		
		

	   String reportQuery = 	" SELECT ds.districtid, ds.description,                                                        "+                   
				" SUM(COALESCE(x.all_cases,0))all_cases, SUM(COALESCE(x.all_bottles,0))all_bottles, SUM(COALESCE(x.all_bl,0))all_bl,  "+
			"	SUM(COALESCE(x.ap_cases,0))ap_cases, SUM(COALESCE(x.ap_bottles,0))ap_bottles, SUM(COALESCE(x.ap_bl,0))ap_bl,       "+
			"	SUM(COALESCE(x.rj_cases,0))rj_cases, SUM(COALESCE(x.rj_bottles,0))rj_bottles, SUM(COALESCE(x.rj_bl,0))rj_bl , "+
			"	sum(all_app) as all_app,sum(all_approved) as all_approved,sum(all_reject) as all_reject       "+
			"	FROM district ds,                                                                                                  "+
			"	 (SELECT a.district_id, SUM(b.no_of_cases) as all_cases, SUM(b.pland_no_of_bottles) as all_bottles,               "+  
		"	SUM(round(CAST(float8(((b.pland_no_of_bottles)*pk.quantity)/1000) as numeric), 2)) as all_bl,                 "+
	"		 0 as ap_cases, 0 as ap_bottles, 0 as ap_bl, 0 as rj_cases, 0 as rj_bottles, 0 as rj_bl,0 as all_app, 0 as all_approved, 0 as all_reject    "+                       
"			FROM bwfl_license.import_permit a, bwfl_license.import_permit_dtl b, distillery.packaging_details_19_20 pk        "+
"			WHERE a.id=b.fk_id AND a.app_id=b.app_id AND a.login_type=b.login_type AND a.district_id=b.district_id             "+
"			AND b.pckg_id=pk.package_id AND a.bwfl_type='"+action.getUnitType()+"'                                             "+
"			GROUP BY a.district_id                                                                                          "+
"				UNION                                                                                                           "+ 
"			 SELECT a.district_id, 0 as all_cases, 0 as all_bottles, 0 as all_bl,                                              "+
"			SUM(b.no_of_cases) as ap_cases, SUM(b.pland_no_of_bottles) as ap_bottles,                                         "+
"			SUM(round(CAST(float8(((b.pland_no_of_bottles)*pk.quantity)/1000) as numeric), 2)) as ap_bl,                     "+
"			0 as rj_cases, 0 as rj_bottles, 0 as rj_bl,0 as all_app, 0 as all_approved, 0 as all_reject                        "+                                    
"			FROM bwfl_license.import_permit a, bwfl_license.import_permit_dtl b, distillery.packaging_details_19_20 pk      "+
"			WHERE a.id=b.fk_id AND a.app_id=b.app_id AND a.login_type=b.login_type AND a.district_id=b.district_id            "+
"			AND b.pckg_id=pk.package_id AND a.bwfl_type='"+action.getUnitType()+"'                                             "+
"			AND a.deo_date IS NOT NULL AND a.vch_approved='APPROVED'                                                         "+
"			GROUP BY a.district_id                                                                                             "+
"			 UNION                                                                                                              "+ 
"			 SELECT a.district_id, 0 as all_cases, 0 as all_bottles, 0 as all_bl,                                     "+
"			0 as ap_cases, 0 as ap_bottles, 0 as ap_bl,                                                                       "+
"			 SUM(b.no_of_cases) as rj_cases, SUM(b.pland_no_of_bottles) as rj_bottles,                                  "+
"			SUM(round(CAST(float8(((b.pland_no_of_bottles)*pk.quantity)/1000) as numeric), 2)) as rj_bl,0 as all_app, 0 as all_approved, 0 as all_reject     "+               
"		 FROM bwfl_license.import_permit a, bwfl_license.import_permit_dtl b, distillery.packaging_details_19_20 pk     "+
"			 WHERE a.id=b.fk_id AND a.app_id=b.app_id AND a.login_type=b.login_type AND a.district_id=b.district_id           "+
"			AND b.pckg_id=pk.package_id AND a.bwfl_type='"+action.getUnitType()+"'                                             "+ 
"			AND a.deo_date IS NOT NULL AND a.vch_approved='REJECTED'                                                         "+
"			GROUP BY a.district_id                                                                                         "+
"			UNION                                                                                                               "+
"			select district_id,0 as all_cases, 0 as all_bottles, 0 as all_bl, 0 as ap_cases, 0 as ap_bottles, 0 as ap_bl, 0 as rj_cases, 0 as rj_bottles,  "+
"   0 as rj_bl,sum(all_app) as all_app,sum(all_approved) as all_approved,sum(all_reject) as all_reject from                                                  "+
"  (select count(*) as all_app, 0 as all_approved, 0 as all_reject,district_id from  bwfl_license.import_permit where bwfl_type='"+action.getUnitType()+"' group by district_id          "+
"  union                                                                                                                                                           "+
"  select  0  as all_app, count(*) as all_approved,  0 as all_reject,district_id from  bwfl_license.import_permit where bwfl_type='"+action.getUnitType()+"' and vch_approved='APPROVED' group by district_id    "+
"  union                                                                                                                              "+
"  select 0  as all_app,0 as all_approved, count(*) as all_reject ,district_id from  bwfl_license.import_permit where bwfl_type='"+action.getUnitType()+"'       "+
"  and vch_approved='REJECTED' group by district_id)x group by district_id                                                                "+

"			)x                                                                                                                   "+                                       
		
"				 WHERE ds.districtid!=0 AND ds.districtid=x.district_id                                                         "+ 
	"			 GROUP BY ds.districtid, ds.description                                                            "+
	"			 ORDER BY ds.description     ";                                                     


		System.out.println("excel query===" + reportQuery);

		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long start = 0;
		long end = 0;
		boolean flag = false;
		long k = 0;
		String noOfUnit = "";
		String date = null;

		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(reportQuery);

			rs = pstmt.executeQuery();
      /*  if(rs.next())
            {*/
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook
					.createSheet("Import Permit Status CD Report");
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n+ "_Import_Permit_Status_CD.xls");

			action.setExlname(n+"_Import_Permit_Status_CD.xls");

			worksheet.setColumnWidth(0, 5000);
			worksheet.setColumnWidth(1, 5000);
			worksheet.setColumnWidth(2, 5000);
			worksheet.setColumnWidth(3, 5000);
			worksheet.setColumnWidth(4, 5000);
			worksheet.setColumnWidth(5, 5000);
			worksheet.setColumnWidth(6, 5000);
			worksheet.setColumnWidth(7, 5000);
			worksheet.setColumnWidth(8, 5000);
			worksheet.setColumnWidth(9, 5000);
			worksheet.setColumnWidth(10, 5000);
			worksheet.setColumnWidth(11, 5000);
			worksheet.setColumnWidth(12, 5000);
			worksheet.setColumnWidth(13, 5000);


			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("Import Permit Status CD");
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
			cellhead1.setCellValue("District ID");
			cellhead1.setCellStyle(cellStyle);
			
			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("District");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Applied Cases");
			cellhead3.setCellStyle(cellStyle);
			
			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Applied Bottles");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Applied Bulk Litre");
			cellhead5.setCellStyle(cellStyle);
			
		    XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Total Applied");
			cellhead6.setCellStyle(cellStyle);
			

			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Approved Cases");
			cellhead7.setCellStyle(cellStyle);
			

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("Approved Bottles");
			cellhead8.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("Approved Bulk Litre");
			cellhead9.setCellStyle(cellStyle);
			
			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("Total Approved");
			cellhead10.setCellStyle(cellStyle);
			
			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("Rejected Cases");
			cellhead11.setCellStyle(cellStyle);
			
			XSSFCell cellhead12 = rowhead.createCell((int) 11);
			cellhead12.setCellValue("Rejected Bottles");
			cellhead12.setCellStyle(cellStyle);
			
			XSSFCell cellhead13 = rowhead.createCell((int) 12);
			cellhead13.setCellValue("Rejected Bulk Litre");
			cellhead13.setCellStyle(cellStyle);
			
			XSSFCell cellhead14 = rowhead.createCell((int) 13);
			cellhead14.setCellValue("Total Rejected");
			cellhead14.setCellStyle(cellStyle);
			
			
	
			int i = 0;
			
			while (rs.next()) 
			{
				i=i+1;
				
				Applied_Cases = Applied_Cases + rs.getInt("all_cases");
				Applied_Bottle = Applied_Bottle + rs.getInt("all_bottles");
				Applied_Bulk_letre = Applied_Bulk_letre + rs.getDouble("all_bl");
				Total_Applied=Total_Applied+rs.getInt("all_app");
				
				Approved_Cases = Approved_Cases + rs.getInt("ap_cases");
				Approved_Bottle = Approved_Bottle + rs.getInt("ap_bottles");
				Approved_Bulk_letre = Approved_Bulk_letre + rs.getDouble("ap_bl");
				Total_Approved=Total_Approved+rs.getInt("all_approved");
				
				Rejected_Cases = Rejected_Cases + rs.getInt("rj_cases");
				Rejected_Bottle = Rejected_Bottle + rs.getInt("rj_bottles");
				Rejected_Bulk_letre = Rejected_Bulk_letre + rs.getDouble("rj_bl");
			    Total_Rejected=Total_Rejected+rs.getInt("all_reject");
				

				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				
				XSSFCell cellA1 = row1.createCell((int) 0); //
				cellA1.setCellValue(rs.getInt("districtid")); //
			/*	district_id*/
				
				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description")); 
				
				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getInt("all_cases"));
				
				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getInt("all_bottles"));
				
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getDouble("all_bl"));
				
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("all_app"));
				
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getInt("ap_cases"));
				
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getInt("ap_bottles"));
				
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getDouble("ap_bl"));
				
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getDouble("all_approved"));
				
				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getInt("rj_cases"));
				
				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(rs.getInt("rj_bottles"));
				
				XSSFCell cellM1 = row1.createCell((int) 12);
				cellM1.setCellValue(rs.getDouble("rj_bl"));
				
				XSSFCell cellN1 = row1.createCell((int) 13);
				cellN1.setCellValue(rs.getDouble("all_reject"));
				
				
			}
			
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			
			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue(""); 
			cellA1.setCellStyle(cellStyle);
			
			XSSFCell cellA2 = row1.createCell((int) 1); 
			cellA2.setCellValue("Total: "); 
			cellA2.setCellStyle(cellStyle); 
			
			XSSFCell cellA3 = row1.createCell((int) 2); 
			cellA3.setCellValue(Applied_Cases); 
			cellA3.setCellStyle(cellStyle); 
			

			XSSFCell cellA4 = row1.createCell((int) 3); 
			cellA4.setCellValue(Applied_Bottle); 
			cellA4.setCellStyle(cellStyle); 
			
			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(Applied_Bulk_letre);
			cellA5.setCellStyle(cellStyle);
			
			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(Total_Applied);
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(Approved_Cases); 
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(Approved_Bottle);
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(Approved_Bulk_letre);
			cellA9.setCellStyle(cellStyle);
			
			
			XSSFCell cellA10 = row1.createCell((int) 9);
			cellA10.setCellValue(Total_Approved);
			cellA10.setCellStyle(cellStyle);
			
			XSSFCell cellA11 = row1.createCell((int) 10);
			cellA11.setCellValue(Rejected_Cases);
			cellA11.setCellStyle(cellStyle);
			
			
			XSSFCell cellA12 = row1.createCell((int) 11);
			cellA12.setCellValue(Rejected_Bottle);
			cellA12.setCellStyle(cellStyle);
		
			XSSFCell cellA13 = row1.createCell((int) 12);
			cellA13.setCellValue(Rejected_Bulk_letre);
			cellA13.setCellStyle(cellStyle);
			
			XSSFCell cellA14 = row1.createCell((int) 13);
			cellA14.setCellValue(Total_Rejected);
			cellA14.setCellStyle(cellStyle);
		
		
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			action.setExcelFlag(true);
			//con.close();

		
		/*}
		else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
			"No Data Found!!", "No Data Found!!"));
			action.setExcelFlag(false);
		}*/
			if(i == 0)
			{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No Data Found!!", "No Data Found!!"));
				action.setExcelFlag(false);
			}
		}catch (Exception e) {


			e.printStackTrace();


		} finally {
			
			try
			{
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

		return flag;

	}
	
	public void printReportDTL(ImportPermitStatusAction act){


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
			
			
	        reportQuery = 	" SELECT a.district_id, ds.description, a.app_id, a.cr_date,                                              "+
							" CASE WHEN a.vch_approved IS NULL THEN 'PENDING' else a.vch_approved end as status,                      "+
							" CASE WHEN a.bwfl_type=1 THEN 'BWFL2A'                                                                   "+
							" WHEN a.bwfl_type=2 THEN 'BWFL2B'                                                                        "+
							" WHEN a.bwfl_type=3 THEN 'BWFL2C'                                                                        "+
							" WHEN a.bwfl_type=4 THEN 'BWFL2D'                                                                        "+
							" WHEN a.bwfl_type=99 THEN 'FL2D' end as type,                                                            "+
							" CASE WHEN a.login_type='FL2D' THEN                                                                      "+
							" (SELECT d.vch_firm_name FROM licence.fl2_2b_2d_19_20 d WHERE a.bwfl_id=d.int_app_id)                    "+          
							" WHEN a.login_type='BWFL' THEN                                                                           "+          
							" (SELECT d.vch_firm_name FROM bwfl.registration_of_bwfl_lic_holder_19_20 d WHERE a.bwfl_id=d.int_id)     "+          
							" end as unit_name,                                                                                       "+
							" SUM(b.no_of_cases) as cases, SUM(b.pland_no_of_bottles) as bottles,                                     "+
							" SUM(round(CAST(float8(((b.pland_no_of_bottles)*pk.quantity)/1000) as numeric), 2)) as bl                "+
							" FROM bwfl_license.import_permit a, bwfl_license.import_permit_dtl b,                                    "+
							" distillery.packaging_details_19_20 pk, district ds                                                      "+
							" WHERE a.id=b.fk_id AND a.app_id=b.app_id AND a.login_type=b.login_type AND a.district_id=b.district_id  "+
							" AND b.pckg_id=pk.package_id AND ds.districtid=a.district_id AND a.bwfl_type='"+act.getUnitType()+"'     "+
							" GROUP BY a.district_id, ds.description, a.app_id, a.cr_date, a.vch_approved, a.bwfl_type,               "+
							" a.login_type, a.bwfl_id                                                                                 "+
							" ORDER BY ds.description, a.app_id, a.cr_date, unit_name ";


			pst = con.prepareStatement(reportQuery);
			System.out.println("reportQuery----------" + reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);


				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "ImportPermitStatusDetail.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "ImportPermitStatusDetail" + "-" + n + ".pdf");
				act.setPdfName("ImportPermitStatusDetail" + "-" + n + ".pdf");
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
	public boolean excelDTL(ImportPermitStatusAction action) throws ParseException {

		

			Connection con = null;
			
		//System.out.println("action.getDateSelected()===="+action.getDateSelected());
	
			
		    int cases=0;
		    int bottles=0;
		    double bulk_litre=0;

			
		   
		    DecimalFormat diciformatter = new DecimalFormat("#.##");
			
		
			

		   String reportQuery = 		" SELECT a.district_id, ds.description, a.app_id, a.cr_date,                                              "+
					" CASE WHEN a.vch_approved IS NULL THEN 'PENDING' else a.vch_approved end as status,                      "+
					" CASE WHEN a.bwfl_type=1 THEN 'BWFL2A'                                                                   "+
					" WHEN a.bwfl_type=2 THEN 'BWFL2B'                                                                        "+
					" WHEN a.bwfl_type=3 THEN 'BWFL2C'                                                                        "+
					" WHEN a.bwfl_type=4 THEN 'BWFL2D'                                                                        "+
					" WHEN a.bwfl_type=99 THEN 'FL2D' end as type,                                                            "+
					" CASE WHEN a.login_type='FL2D' THEN                                                                      "+
					" (SELECT d.vch_firm_name FROM licence.fl2_2b_2d_19_20 d WHERE a.bwfl_id=d.int_app_id)                    "+          
					" WHEN a.login_type='BWFL' THEN                                                                           "+          
					" (SELECT d.vch_firm_name FROM bwfl.registration_of_bwfl_lic_holder_19_20 d WHERE a.bwfl_id=d.int_id)     "+          
					" end as unit_name,                                                                                       "+
					" SUM(b.no_of_cases) as cases, SUM(b.pland_no_of_bottles) as bottles,                                     "+
					" SUM(round(CAST(float8(((b.pland_no_of_bottles)*pk.quantity)/1000) as numeric), 2)) as bl                "+
					" FROM bwfl_license.import_permit a, bwfl_license.import_permit_dtl b,                                    "+
					" distillery.packaging_details_19_20 pk, district ds                                                      "+
					" WHERE a.id=b.fk_id AND a.app_id=b.app_id AND a.login_type=b.login_type AND a.district_id=b.district_id  "+
					" AND b.pckg_id=pk.package_id AND ds.districtid=a.district_id AND a.bwfl_type='"+action.getUnitType()+"'     "+
					" GROUP BY a.district_id, ds.description, a.app_id, a.cr_date, a.vch_approved, a.bwfl_type,               "+
					" a.login_type, a.bwfl_id                                                                                 "+
					" ORDER BY ds.description, a.app_id, a.cr_date, unit_name ";


			System.out.println("excel query===" + reportQuery);

			String relativePath = Constants.JBOSS_SERVER_PATH
					+ Constants.JBOSS_LINX_PATH;
			FileOutputStream fileOut = null;

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			long start = 0;
			long end = 0;
			boolean flag = false;
			long k = 0;
			String noOfUnit = "";
			String date = null;

			try {
				con = ConnectionToDataBase.getConnection();
				pstmt = con.prepareStatement(reportQuery);

				rs = pstmt.executeQuery();
				
          /*  if(rs.next())
                {*/rs = pstmt.executeQuery();
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet worksheet = workbook
						.createSheet("Import Permit Status DTL Report");
				Random rand = new Random();
				int n = rand.nextInt(550) + 1;
				fileOut = new FileOutputStream(relativePath
						+ "//ExciseUp//MIS//Excel//" + n+ "_Import_Permit_Status_DTL.xls");

				action.setExlname(n+"_Import_Permit_Status_DTL.xls");
				action.setExcelFlag(true);
				

				worksheet.setColumnWidth(0, 5000);
				worksheet.setColumnWidth(1, 5000);
				worksheet.setColumnWidth(2, 5000);
				worksheet.setColumnWidth(3, 5000);
				worksheet.setColumnWidth(4, 5000);
				worksheet.setColumnWidth(5, 5000);
				worksheet.setColumnWidth(6, 5000);
				worksheet.setColumnWidth(7, 5000);
			

				XSSFRow rowhead0 = worksheet.createRow((int) 0);
				XSSFCell cellhead0 = rowhead0.createCell((int) 0);
				cellhead0.setCellValue("Import Permit Status DTL");
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
				cellhead1.setCellValue("App ID");
				cellhead1.setCellStyle(cellStyle);
				
				XSSFCell cellhead2 = rowhead.createCell((int) 1);
				cellhead2.setCellValue("App Date");
				cellhead2.setCellStyle(cellStyle);

				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("Unit Name");
				cellhead3.setCellStyle(cellStyle);
				
				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("License Type");
				cellhead4.setCellStyle(cellStyle);

				XSSFCell cellhead5 = rowhead.createCell((int) 4);
				cellhead5.setCellValue("Cases");
				cellhead5.setCellStyle(cellStyle);
				
				XSSFCell cellhead6 = rowhead.createCell((int) 5);
				cellhead6.setCellValue("Bottles");
				cellhead6.setCellStyle(cellStyle);
				

				XSSFCell cellhead7 = rowhead.createCell((int) 6);
				cellhead7.setCellValue("Bulk Litre");
				cellhead7.setCellStyle(cellStyle);
				

				XSSFCell cellhead8 = rowhead.createCell((int) 7);
				cellhead8.setCellValue("Status");
				cellhead8.setCellStyle(cellStyle);
				
				
		
				int i = 0;
				
				while (rs.next()) 
				{ i=i+1;
					cases = cases + rs.getInt("cases");
					bottles = bottles + rs.getInt("bottles");
					bulk_litre = bulk_litre + rs.getDouble("bl");
					

					k++; //
					XSSFRow row1 = worksheet.createRow((int) k); //
					
					XSSFCell cellA1 = row1.createCell((int) 0); //
					cellA1.setCellValue(rs.getInt("app_id")); //
				/*	district_id*/
					
					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("cr_date")); 
					
					XSSFCell cellC1 = row1.createCell((int) 2);
					cellC1.setCellValue(rs.getString("unit_name"));
					
					XSSFCell cellD1 = row1.createCell((int) 3);
					cellD1.setCellValue(rs.getString("type"));
					
					
					XSSFCell cellE1 = row1.createCell((int) 4);
					cellE1.setCellValue(rs.getInt("cases"));
					
					XSSFCell cellF1 = row1.createCell((int) 5);
					cellF1.setCellValue(rs.getInt("bottles"));
					
					XSSFCell cellG1 = row1.createCell((int) 6);
					cellG1.setCellValue(rs.getDouble("bl"));
					
					XSSFCell cellH1 = row1.createCell((int) 7);
					cellH1.setCellValue(rs.getString("status"));
					
					
				}
				
			
				XSSFRow row1 = worksheet.createRow((int) k + 1);
				
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(""); 
				cellA1.setCellStyle(cellStyle);
				
				XSSFCell cellA2 = row1.createCell((int) 1); 
				cellA2.setCellValue(" "); 
				cellA2.setCellStyle(cellStyle); 
				
				XSSFCell cellA3 = row1.createCell((int) 2); 
				cellA3.setCellValue(""); 
				cellA3.setCellStyle(cellStyle); 
				

				XSSFCell cellA4 = row1.createCell((int) 3); 
				cellA4.setCellValue("Total:"); 
				cellA4.setCellStyle(cellStyle); 
				
				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue(cases);
				cellA5.setCellStyle(cellStyle);
				
				XSSFCell cellA6 = row1.createCell((int) 5);
				cellA6.setCellValue(bottles);
				cellA6.setCellStyle(cellStyle);
				
				XSSFCell cellA7 = row1.createCell((int) 6);
				cellA7.setCellValue(bulk_litre); 
				cellA7.setCellStyle(cellStyle);
				
				XSSFCell cellA8 = row1.createCell((int) 7);
				cellA8.setCellValue("");
				cellA8.setCellStyle(cellStyle);
				
				
			
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				flag = true;
				action.setExcelFlag(true);
				//con.close();

			
			
			
            if(i == 0)
			{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No Data Found!!", "No Data Found!!"));
				action.setExcelFlag(false);
			}
			}catch (Exception e) {


				e.printStackTrace();


			} finally {
				
				try
				{
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

			return flag;

		}

}

