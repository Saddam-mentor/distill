package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.mentor.action.DetailsOfIUAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class DetailsOfIUImpl {
	public void printReport(DetailsOfIUAction act) {

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
		String unitTypeFilte="";
 

		try {
			con = ConnectionToDataBase.getConnection(); 
		 
			Date FromDate=Utility.convertUtilDateToSQLDate(act.getFromDate());
			Date toDate=Utility.convertUtilDateToSQLDate(act.getToDate());
			
				reportQuery=" select x.iu_name,x.cbw_name,x.district,y.mapped_iu_name from                                                                            "+
						"	(select c.int_id,a.vch_firm_name as cbw_name, b.vch_firm_name as iu_name,                                                             "+
						"	d.description as district from custom_bonds.custom_bonds_master a,custom_bonds.mst_regis_importing_unit b,                  "+
						"	custom_bonds.mst_custom_bond_importing_unit_maping c, district d where a.int_id=c.int_bond_id and b.int_id=c.int_import_id            "+
						"	and a.int_distct_id=d.districtid and b.vch_apprv_flag='A')x                                                                                                    "+
						"	left outer join                                                                                                                       "+
						"	(select b.brand_iu_id,a.vch_firm_name as mapped_iu_name from custom_bonds.mst_regis_importing_unit a,custom_bonds.iu_mapping_with_iu b"+
						"	where a.int_id=b.mapped_iu_id)y on x.int_id=y.brand_iu_id order by x.iu_name";
	                          
		
			 
			System.out.println("reportQuery---------" + reportQuery);
			pst = con.prepareStatement(reportQuery);
			

			rs = pst.executeQuery();

			if (rs.next()) {
				
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				//parameters.put("type", type);
			 	JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
			 	
			 		jasperReport = (JasperReport) JRLoader.loadObject(relativePath
							+ File.separator+ "DetailsOfImportingUnit.jasper");
			 	
				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator + "DetailsOfImportingUnit" + "-" + n + ".pdf");
				act.setPdf_name("DetailsOfImportingUnit" + "-" + n+ ".pdf");
				act.setPrintFlag(true);
				
			} else { 
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found!!", "No Data Found!!"));
				act.setPrintFlag(false);
			}
		} catch (JRException e) {
			act.setPrintFlag(false);
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
		} catch (Exception e) {
			act.setPrintFlag(false);
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
			}
		}
	}
	
	public boolean writeExcel(DetailsOfIUAction act) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double total_due=0.0;
		double total_dispatch=0.0;
		double total_cl=0.0;
		double total_fl=0.0;
		double total_beer=0.0;
		double total_bond=0.0;

		String sql = "";

	 
		String loginFilter="";
		
			
                sql=" select x.iu_name,x.cbw_name,x.district,y.mapped_iu_name from                                                                            "+
						"	(select c.int_id,a.vch_firm_name as cbw_name, b.vch_firm_name as iu_name,                                                             "+
						"	d.description as district from custom_bonds.custom_bonds_master a,custom_bonds.mst_regis_importing_unit b,                  "+
						"	custom_bonds.mst_custom_bond_importing_unit_maping c, district d where a.int_id=c.int_bond_id and b.int_id=c.int_import_id            "+
						"	and a.int_distct_id=d.districtid and b.vch_apprv_flag='A')x                                                                                                    "+
						"	left outer join                                                                                                                       "+
						"	(select b.brand_iu_id,a.vch_firm_name as mapped_iu_name from custom_bonds.mst_regis_importing_unit a,custom_bonds.iu_mapping_with_iu b"+
						"	where a.int_id=b.mapped_iu_id)y on x.int_id=y.brand_iu_id order by x.iu_name";
	                          
					

	       
		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			worksheet.setColumnWidth(0, 2000);
			worksheet.setColumnWidth(1, 10000);
			worksheet.setColumnWidth(2, 10000);
			worksheet.setColumnWidth(3, 4000);
			worksheet.setColumnWidth(4, 10000);
			worksheet.setColumnWidth(5, 4000);
			worksheet.setColumnWidth(6, 4000);
			worksheet.setColumnWidth(7, 4000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("Details Of Importing Unit");

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
				cellhead2.setCellValue("Importing Unit Name");
				cellhead2.setCellStyle(cellStyle);
				
				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("CBW Name");
				cellhead3.setCellStyle(cellStyle);
	
				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("District");
				cellhead4.setCellStyle(cellStyle);
				
				XSSFCell cellhead5 = rowhead.createCell((int) 4);
				cellhead5.setCellValue("Authorised Importing Unit Name");
				cellhead5.setCellStyle(cellStyle);
				
				
			

			int i = 0;
			while (rs.next()) {
				
				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k - 1);

				
				

					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("iu_name"));
					XSSFCell cellC1 = row1.createCell((int) 2);
					cellC1.setCellValue(rs.getString("cbw_name"));
					XSSFCell cellD1 = row1.createCell((int) 3);
					cellD1.setCellValue(rs.getString("district"));
					XSSFCell cellE1 = row1.createCell((int) 4);
					cellE1.setCellValue(rs.getString("mapped_iu_name"));
				}
			
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "AdditionalConsidrationFee.xls");
			act.setExlname(n + "AdditionalConsidrationFee");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue("");
			cellA1.setCellStyle(cellStyle);
			/*if(act.getRadio().equalsIgnoreCase("D")){
				XSSFCell cellA2 = row1.createCell((int) 1);
				cellA2.setCellValue(" ");
				cellA2.setCellStyle(cellStyle);
			
				XSSFCell cellA3 = row1.createCell((int) 2);
				cellA3.setCellValue("");
				cellA3.setCellStyle(cellStyle);
	
				XSSFCell cellA4 = row1.createCell((int) 3);
				cellA4.setCellValue(total_due);
				cellA4.setCellStyle(cellStyle);
	 
				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue(total_dispatch);
				cellA5.setCellStyle(cellStyle);
			}else{
				XSSFCell cellA2 = row1.createCell((int) 1);
				cellA2.setCellValue("Total");
				cellA2.setCellStyle(cellStyle);
			
				XSSFCell cellA3 = row1.createCell((int) 2);
				cellA3.setCellValue(total_cl);
				cellA3.setCellStyle(cellStyle);
	
				XSSFCell cellA4 = row1.createCell((int) 3);
				cellA4.setCellValue(total_fl);
				cellA4.setCellStyle(cellStyle);
	 
				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue(total_beer);
				cellA5.setCellStyle(cellStyle);
				XSSFCell cellA6 = row1.createCell((int) 5);
				cellA6.setCellValue(total_bond);
				cellA6.setCellStyle(cellStyle);
			}*/
 
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
