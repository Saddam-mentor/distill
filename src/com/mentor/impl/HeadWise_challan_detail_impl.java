package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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
import com.mentor.action.HeadWise_challan_detail_action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

@SuppressWarnings("unchecked")
public class HeadWise_challan_detail_impl {
	
	/* ---------------------- get Challan List ------------------------------ */

	public ArrayList getChallanHeadList(HeadWise_challan_detail_action action) {

		ArrayList list = new ArrayList();

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SelectItem item = new SelectItem();

		item.setLabel("-Select-");
		item.setValue("Select");
		list.add(item);
		try {

			String q = "SELECT distinct head,hdesc,int_head_key FROM licence.distillery_challan_heads order by head";
			c = ConnectionToDataBase.getConnection();
			ps = c.prepareStatement(q);

			rs = ps.executeQuery();

			while (rs.next()) {

				item = new SelectItem();

				item.setLabel(rs.getString("head") + " -- "
						+ rs.getString("hdesc"));
				item.setValue(rs.getString("head") + "|"
						+ rs.getString("int_head_key"));

				list.add(item);
			}
			//System.out.println("----getChallanHeadList----"+ q);

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {

			try {
				c.close();
			} catch (Exception e2) {

			}
		}

		return list;

	}
	
	/* ---------------------- get G6 List ------------------------------ */

	public ArrayList g_six_list(HeadWise_challan_detail_action action) {

		ArrayList list = new ArrayList();

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SelectItem item = new SelectItem();

		item.setLabel("-Select-");
		item.setValue("Select");
		list.add(item);
		try {

			String q = "select distinct g6.description,g6.g6code,ch.head_type  from licence.distillery_challan_heads ch , licence.distillery_g6head g6 where ch.head = g6.challan_head "
					+ " and ch.head_type=challan_head_type and g6.challan_head = ? order by g6.g6code,g6.description";
																														
																														
			c = ConnectionToDataBase.getConnection();
			ps = c.prepareStatement(q);

			String[] arr = { "", "" };

			if (action.getChallanHeadId() != null) {

				arr = action.getChallanHeadId().split("\\|");

			}

			String challanHeadId = arr[0];
			// String challanHeadType = arr[3];
			ps.setString(1, challanHeadId);
			// ps.setString(2, challanHeadType);
			
			//System.out.println("--- g 6 headlist===- "+ q);

			rs = ps.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setLabel(rs.getString("g6code") + "|"
						+ rs.getString("description"));
				item.setValue(rs.getString("g6code") + "|"
						+ rs.getString("head_type") + "|0");

				list.add(item);
				
				//System.out.println("ckmnlkmfvlkfd");
			}
			
			//System.out.println("--- g 6 headlist===- "+ q);

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {

			try {
				c.close();
			} catch (Exception e2) {

			}
		}

		return list;

	}
	
	
	

	
	
  /* ========================================print report ========================================================*/
	
		public void printReport(HeadWise_challan_detail_action action)
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
				
				reportQuery = " SELECT  vch_depositor_name, coalesce(vch_mill_type,'Other') as vch_mill_type, mst.vch_challan_id," +
						" dat_created_date, cast(vch_total_amount as numeric), vch_remarks FROM licence.mst_challan_master mst," +
						" licence.challan_head_details hd where dat_created_date " +
						" between '"+Utility.convertUtilDateToSQLDate(action.getFrom_date())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTo_date())+"' "+
					    " and mst.vch_challan_id = hd.vch_challan_id and g6_head = '"+action.getG6HeadId().substring(0, 2)+"' and " +
						" vch_rajaswa_head = '"+action.getChallanHeadId().substring(0, 15)+"' order by vch_depositor_name ";

				
					pst = con.prepareStatement(reportQuery);
					
					rs = pst.executeQuery();
					//System.out.println("---action.g6 head--"+action.getChallanHeadId().substring(0, 2));
					//System.out.println("---action.getChallanHeadId()--"+action.getChallanHeadId().substring(0, 15));
					
					//System.out.println("-----printreport----"+reportQuery);
					 

				if (rs.next()) {
					

					rs = pst.executeQuery();
					Map parameters = new HashMap();
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("SUBREPORT_DIR", relativePath + File.separator);
					parameters.put("image", relativePath + File.separator);
					parameters.put("fromDate",Utility.convertUtilDateToSQLDate(action.getFrom_date()));
					parameters.put("toDate",Utility.convertUtilDateToSQLDate(action.getTo_date()));
					parameters.put("challanHeadId",(action.getChallanHeadId().substring(0, 15)));
					parameters.put("g6HeadId",(action.getG6HeadId().substring(0, 2)));
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);		
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "HeadWise_challan_details.jasper");
					JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "HeadWise_challan_details" + "-" + n + ".pdf");
					action.setPdfName("HeadWise_challan_details" + "-" + n + ".pdf");
					action.setPrintFlag(true);
					//System.out.println("==== pdf created =======");
				} else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
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
		
		
//==========================Excel =====================================================	
		
		public boolean excelheadwise_challan(HeadWise_challan_detail_action action) {
			
			////System.out.println("--------excelMrpBrandDetails_-- execute ");

				Connection con = null;
				con = ConnectionToDataBase.getConnection();
				DecimalFormat diciformatter = new DecimalFormat("#.##");
			    String reportQuery = null;
			   //System.out.println("----------  Excel-----  method impl start======");
			   double amount_sum = 0;

			reportQuery =  " SELECT  vch_depositor_name, coalesce(vch_mill_type,'Other') as vch_mill_type,  mst.vch_challan_id," +
					" dat_created_date, cast(vch_total_amount as numeric), vch_remarks FROM licence.mst_challan_master mst," +
					" licence.challan_head_details hd where dat_created_date " +
					" between '"+Utility.convertUtilDateToSQLDate(action.getFrom_date())+"' and '"+Utility.convertUtilDateToSQLDate(action.getTo_date())+"' "+
				    " and mst.vch_challan_id = hd.vch_challan_id and g6_head = '"+action.getG6HeadId().substring(0, 2)+"' and " +
					" vch_rajaswa_head = '"+action.getChallanHeadId().substring(0, 15)+"' order by vch_depositor_name  ";
				
				
				//System.out.println("----------  Excel   -----" + reportQuery);

				
				String relativePath = Constants.JBOSS_SERVER_PATH
						+ Constants.JBOSS_LINX_PATH;
				FileOutputStream fileOut = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				boolean flag = false;
				long k = 0;
	
				
				try {
				
					pstmt = con.prepareStatement(reportQuery);
					//System.out.println("-------HeadWise Challan Details----" );

					rs = pstmt.executeQuery();				
					XSSFWorkbook workbook = new XSSFWorkbook();
					XSSFSheet worksheet = workbook.createSheet("HeadWise Challan Details");
					// CellStyle unlockedCellStyle = workbook.createCellStyle();
					// unlockedCellStyle.setLocked(false);
					// worksheet.protectSheet("UP-EX-MIS");
					worksheet.setColumnWidth(0, 2000);
					worksheet.setColumnWidth(1, 9000);
					worksheet.setColumnWidth(2, 5000);
					worksheet.setColumnWidth(3, 5000);
					worksheet.setColumnWidth(4, 5000);
					worksheet.setColumnWidth(5, 5000);
					worksheet.setColumnWidth(6, 10000);
					
					XSSFRow rowhead0 = worksheet.createRow((int) 0);		
					XSSFCell cellhead0 = rowhead0.createCell((int) 0);
					cellhead0.setCellValue("HeadWise Challan Details"+" From Date:   "+Utility.convertUtilDateToSQLDate(action.getFrom_date())+"   TO Date   "+Utility.convertUtilDateToSQLDate(action.getTo_date()));
					XSSFRow rowhead1 = worksheet.createRow((int)1);
					XSSFCell cellheading = rowhead1.createCell((int) 1);
					cellheading.setCellValue("Challan Head "+action.getChallanHeadId().substring(0, 15)+"  G6 Head    "+action.getG6HeadId().substring(0, 2));
															
					rowhead0.setHeight((short) 700);
					rowhead1.setHeight((short) 300);

					// cellhead0.setCellStyle(unlockedCellStyle);
					XSSFCellStyle cellStyl = workbook.createCellStyle();
					cellStyl = workbook.createCellStyle();
					XSSFFont hSSFFont = workbook.createFont();
					hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
					hSSFFont.setFontHeightInPoints((short) 12);
					hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					hSSFFont.setColor(HSSFColor.GREEN.index);
					cellStyl.setFont(hSSFFont);
					cellhead0.setCellStyle(cellStyl);
				//	cellheading.setCellStyle(cellStyl);
					
					XSSFCellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
					cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					XSSFCellStyle unlockcellStyle = workbook.createCellStyle();
					unlockcellStyle.setLocked(false);

					k = k + 2;
					XSSFRow rowhead = worksheet.createRow((int) 2);

					XSSFCell cellhead1 = rowhead.createCell((int) 0);
					cellhead1.setCellValue("S.No.");

					cellhead1.setCellStyle(cellStyle);

					XSSFCell cellhead2 = rowhead.createCell((int) 1);
					cellhead2.setCellValue("Depositor Name");
					cellhead2.setCellStyle(cellStyle);

					XSSFCell cellhead3 = rowhead.createCell((int) 2);
					cellhead3.setCellValue("Type");
					cellhead3.setCellStyle(cellStyle);

					XSSFCell cellhead4 = rowhead.createCell((int) 3);
					cellhead4.setCellValue("Challan No.");
					cellhead4.setCellStyle(cellStyle);

					XSSFCell cellhead5 = rowhead.createCell((int) 4);
					cellhead5.setCellValue("Depositor Date");
					cellhead5.setCellStyle(cellStyle);

					XSSFCell cellhead6 = rowhead.createCell((int) 5);
					cellhead6.setCellValue("Amount");
					cellhead6.setCellStyle(cellStyle);

					XSSFCell cellhead7 = rowhead.createCell((int) 6);
					cellhead7.setCellValue("Remark");
					cellhead7.setCellStyle(cellStyle);

					
					int i = 0;
					while (rs.next()) {
						amount_sum = amount_sum + rs.getDouble("vch_total_amount");
					
						k++;
						XSSFRow row1 = worksheet.createRow((int) k);
						XSSFCell cellA1 = row1.createCell((int) 0);
						cellA1.setCellValue(k - 1);

						XSSFCell cellB1 = row1.createCell((int) 1);
						cellB1.setCellValue(rs.getString("vch_depositor_name"));

						XSSFCell cellC1 = row1.createCell((int) 2);
						cellC1.setCellValue(rs.getString("vch_mill_type") );
						// cellC1.setCellStyle(unlockcellStyle);

						XSSFCell cellD1 = row1.createCell((int) 3);
						cellD1.setCellValue(rs.getString("vch_challan_id"));

						XSSFCell cellE1 = row1.createCell((int) 4);
						cellE1.setCellValue(rs.getString("dat_created_date"));

						XSSFCell cellF1 = row1.createCell((int) 5);
						cellF1.setCellValue(rs.getString("vch_total_amount"));

						XSSFCell cellG1 = row1.createCell((int) 6);
						cellG1.setCellValue(rs.getString("vch_remarks"));

						
					}
					Random rand = new Random();
					int n = rand.nextInt(550) + 1; 
					fileOut = new FileOutputStream(relativePath
							+ "//ExciseUp//MIS//Excel//" 
							+ n + "_HeadWise_challan_details.xls");

					action.setExlname( n + "_HeadWise_challan_details.xls" );
					XSSFRow row1 = worksheet.createRow((int) k + 1);
					// XSSFCell cellA1 = row1.createCell((int) 0);
					// cellA1.setCellValue("End");
					// cellA1.setCellStyle(cellStyle);

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
					cellA4.setCellValue(" ");
					cellA4.setCellStyle(cellStyle);

					XSSFCell cellA5 = row1.createCell((int) 4);
					cellA5.setCellValue("TOTAL:-");
					cellA5.setCellStyle(cellStyle);

					XSSFCell cellA6 = row1.createCell((int) 5);
					cellA6.setCellValue(amount_sum);
					cellA6.setCellStyle(cellStyle);

					XSSFCell cellA7 = row1.createCell((int) 6);
					cellA7.setCellValue(" ");
					cellA7.setCellStyle(cellStyle);
					workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					flag = true;
					action.setExcelFlag(true);
					con.close();
					

				} catch (Exception e) {

					//System.out.println("xls2" + e.getMessage());
					e.printStackTrace();

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									e.getMessage(), e.getMessage()));

				} finally {

					try {
						con.close();
					} catch (Exception e) {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, e
										.getMessage(), e.getMessage()));
						//System.out.println("  excel block ");

						e.printStackTrace();
					}
				}
			
			
			return flag;
			
		}

}
