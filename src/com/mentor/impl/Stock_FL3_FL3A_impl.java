package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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

import com.mentor.action.Stock_FL3_FL3A_action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;


public class Stock_FL3_FL3A_impl {
	
	
	public String getDetails(Stock_FL3_FL3A_action ac) {
		int id = 0;
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		String s = "";
		try {
		con = ConnectionToDataBase.getConnection();
		String queryList = "SELECT int_app_id_f,vch_undertaking_name,vch_wrk_add  FROM  dis_mst_pd1_pd2_lic WHERE vch_wrk_phon="
		+ ResourceUtil.getUserNameAllReq().trim();

		pstmt = con.prepareStatement(queryList);

		rs = pstmt.executeQuery();
		
		//System.out.println("query------"+queryList);

		while (rs.next()) {
		ac.setName(rs.getString("vch_undertaking_name"));
		ac.setDist_id(rs.getInt("int_app_id_f"));
		ac.setAddress(rs.getString("vch_wrk_add"));

		}

		} catch (SQLException se) {
		se.printStackTrace();
		} finally {
		try {
		if (pstmt != null)
		pstmt.close();
		if (ps2 != null)
		ps2.close();
		if (rs != null)
		rs.close();
		if (rs2 != null)
		rs2.close();
		if (con != null)
		con.close();

		} catch (SQLException se) {
		se.printStackTrace();
		}
		}
		return "";

		}

	
	 

	// -----------list
		public ArrayList fromliclistImpl(Stock_FL3_FL3A_action act) {
			ArrayList list = new ArrayList();
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			SelectItem item = new SelectItem();
			item.setLabel("--select--");
			item.setValue("");
			list.add(item);
			try {//@rvind
				String query = "SELECT distinct licence_no   from  "
						+ " distillery.mst_bottling_plan_20_21 where  int_distillery_id='"
						+ act.getDist_id() + "'  and vch_license_type= '"
						+ act.getVch_from() + "'";

				//System.out.println("licquery=="+query);
				conn = ConnectionToDataBase.getConnection();
				ps = conn.prepareStatement(query);
				// ps.setDate(1,
				// Utility.convertUtilDateToSQLDate(act.getDt_date()));
				rs = ps.executeQuery();

				while (rs.next()) {

					item = new SelectItem();

					item.setValue(rs.getString("licence_no"));
					item.setLabel(rs.getString("licence_no"));

					list.add(item);

				}

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
				e.printStackTrace();
			} finally {
				try {

					if (conn != null)
						conn.close();
					if (ps != null)
						ps.close();
					if (rs != null)
						rs.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		}
	
	
		
		
		
		
		
		
		
		
		public void print(Stock_FL3_FL3A_action action) {
			String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
			String relativePath = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "jasper";
			String relativePathpdf = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "pdf";
			JasperReport jasperReport = null;
			PreparedStatement pst = null;
			Connection con = null;
			ResultSet rs = null;
			String reportQuery = null;
			
			
			
				reportQuery = " SELECT liquor_type,quantity as qnt_ml_detail,a.code_generate_through,b.domain,a.package_name, a.package_id, a.duty, a.adduty, c.int_brand_id, b.brand_name,coalesce(c.tnt,'X') as tt , c.tnt ,"
						+ " c.int_pckg_id, c.int_stock-c.int_dispatched as avlbottle, c.size as box_size,"
						+ " ROUND(((c.int_stock-c.int_dispatched)/c.size)) as avlbox,a.code_generate_through "
						+ " FROM distillery.packaging_details_20_21 a  , distillery.brand_registration_20_21 b, "
						+ " distillery.boxing_stock_20_21 c     "
						+ " WHERE a.brand_id_fk=b.brand_id AND a.brand_id_fk=c.int_brand_id "
						+ " AND a.package_id=c.int_pckg_id AND b.brand_id=c.int_brand_id "
						+ " AND c.int_dissleri_id='"+ action.getDist_id()+"' " +
						"AND c.vch_lic_no='"+action.getLic_id()+"'  AND c.vch_lic_type='"+ action.getVch_from()+ "'" +
						" AND c.int_stock-c.int_dispatched >0   "
						+ " order by b.brand_name,a.package_name,c.tnt";

				
				
			
			try {
				con = ConnectionToDataBase.getConnection();	
				pst = con.prepareStatement(reportQuery);
			
				//System.out.println("====11111===="+reportQuery);
				rs = pst.executeQuery();
				if (rs.next()) {
					rs = pst.executeQuery();
					Map parameters = new HashMap();
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("SUBREPORT_DIR", relativePath + File.separator);
					parameters.put("image", relativePath + File.separator);
					parameters.put("distnm", action.getName());
					parameters.put("lic_type", action.getVch_from());
					parameters.put("lic_no", action.getLic_id());
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
	//System.out.println("------------------------");
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath
							+ File.separator + "Fl3_Stock.jasper");

					JasperPrint print = JasperFillManager.fillReport(jasperReport,
							parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;

					JasperExportManager.exportReportToPdfFile(print,
							relativePathpdf + File.separator
									+ "Fl3_Stock" + n + ".pdf");
					action.setPdfname("Fl3_Stock" + n + ".pdf");
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
		
		
		public void printCL(Stock_FL3_FL3A_action action) {
			String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
			String relativePath = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "jasper";
			String relativePathpdf = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "pdf";
			JasperReport jasperReport = null;
			PreparedStatement pst = null;
			Connection con = null;
			ResultSet rs = null;
			String reportQuery = null;
			
			
			
				reportQuery = " SELECT a.package_name, a.package_id,c.vch_lic_no,a.code_generate_through, c.int_brand_id, b.brand_name,"
						+ " c.int_pckg_id, c.int_stock-c.int_dispatched as avlbottle, d.box_size, "
						+ " ROUND(((c.int_stock-c.int_dispatched)/d.box_size)) as avlbox "
						+ " FROM distillery.packaging_details_20_21 a, distillery.brand_registration_20_21 b, "
						+ " distillery.boxing_stock_20_21 c, distillery.box_size_details d  "
						+ " WHERE a.brand_id_fk=b.brand_id AND a.brand_id_fk=c.int_brand_id "
						+ " AND a.package_id=c.int_pckg_id AND b.brand_id=c.int_brand_id  "
						+ " AND c.int_dissleri_id='"
						+ action.getDist_id()
						+ "' "
						+ " AND c.vch_lic_type='CL' AND ROUND(((c.int_stock-c.int_dispatched)/d.box_size)) >0 "
						+ " AND a.box_id=d.box_id AND a.quantity=d.qnt_ml_detail";

				
				
			
			try {
				con = ConnectionToDataBase.getConnection();	
				pst = con.prepareStatement(reportQuery);
			
				//System.out.println("====11111===="+reportQuery);
				rs = pst.executeQuery();
				if (rs.next()) {
					rs = pst.executeQuery();
					Map parameters = new HashMap();
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("SUBREPORT_DIR", relativePath + File.separator);
					parameters.put("image", relativePath + File.separator);
					parameters.put("distnm", action.getName());
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
	 System.out.println("------------file------------"+relativePath + File.separator);
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath
							+ File.separator + "Fl3_Stock_CL.jasper");

					JasperPrint print = JasperFillManager.fillReport(jasperReport,
							parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;

					JasperExportManager.exportReportToPdfFile(print,
							relativePathpdf + File.separator
									+ "Fl3_Stock_CL" + n + ".pdf");
					action.setPdfname("Fl3_Stock_CL" + n + ".pdf");
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
		
//==============================generate excel CL
		
		public boolean generateexcelCL(Stock_FL3_FL3A_action action) {

			Connection con = null;
			con = ConnectionToDataBase.getConnection();
			DecimalFormat diciformatter = new DecimalFormat("#.##");
			String reportQuery = null;
			double total_avlbx = 0 ;
			double total_avlbtl = 0 ;
			SimpleDateFormat showDate = new SimpleDateFormat("dd-MM-yyyy");
			
			

		   //System.out.println("----------  Excel   -----" + reportQuery);


			String relativePath = Constants.JBOSS_SERVER_PATH
					+ Constants.JBOSS_LINX_PATH;
			FileOutputStream fileOut = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean flag = false;
			long k = 0;
	          
			try {
                
				
				reportQuery = " SELECT a.package_name, a.package_id,c.vch_lic_no,a.code_generate_through, c.int_brand_id, b.brand_name,"
						+ " c.int_pckg_id, c.int_stock-c.int_dispatched as avlbottle, d.box_size, "
						+ " ROUND(((c.int_stock-c.int_dispatched)/d.box_size)) as avlbox "
						+ " FROM distillery.packaging_details_20_21 a, distillery.brand_registration_20_21 b, "
						+ " distillery.boxing_stock_20_21 c, distillery.box_size_details d  "
						+ " WHERE a.brand_id_fk=b.brand_id AND a.brand_id_fk=c.int_brand_id "
						+ " AND a.package_id=c.int_pckg_id AND b.brand_id=c.int_brand_id  "
						+ " AND c.int_dissleri_id='"
						+ action.getDist_id()
						+ "' "
						+ " AND c.vch_lic_type='CL' AND ROUND(((c.int_stock-c.int_dispatched)/d.box_size)) >0 "
						+ " AND a.box_id=d.box_id AND a.quantity=d.qnt_ml_detail";
				
				
				pstmt = con.prepareStatement(reportQuery);

				rs = pstmt.executeQuery();
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet worksheet = workbook.createSheet("FL3_stock_CL");
				worksheet.setColumnWidth(0, 2000);
				worksheet.setColumnWidth(1, 4000);
				worksheet.setColumnWidth(2, 7000);
				worksheet.setColumnWidth(3, 3000);	
				worksheet.setColumnWidth(4, 3000);	
				worksheet.setColumnWidth(5, 3000);	
				worksheet.setColumnWidth(6, 3000);	
			    
				XSSFRow rowhead0 = worksheet.createRow((int) 0);
				XSSFCell cellhead0 = rowhead0.createCell((int) 1);
				cellhead0.setCellValue("FL3_stock_CL");			
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
				cellhead1.setCellValue("Sr.No.");
				cellhead1.setCellStyle(cellStyle);

				XSSFCell cellhead2 = rowhead.createCell((int) 1);
				cellhead2.setCellValue("Brand Name");
				cellhead2.setCellStyle(cellStyle);

				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("Package Name");
				cellhead3.setCellStyle(cellStyle);

				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("Size (ml)");
				cellhead4.setCellStyle(cellStyle);

				XSSFCell cellhead5 = rowhead.createCell((int) 4);
				cellhead5.setCellValue("ETIN No");
				cellhead5.setCellStyle(cellStyle);
				
				XSSFCell cellhead6 = rowhead.createCell((int) 5);
				cellhead6.setCellValue("Available Box");
				cellhead6.setCellStyle(cellStyle);
				
				XSSFCell cellhead7 = rowhead.createCell((int) 6);
				cellhead7.setCellValue("Available Bottles");
				cellhead7.setCellStyle(cellStyle);

				int i = 0;
				while (rs.next()) {
					
                    total_avlbx = total_avlbx + rs.getDouble("avlbox");
					
                    total_avlbtl = total_avlbtl + rs.getDouble("avlbottle");
                    
	                k++;
	                
	                XSSFRow row1 = worksheet.createRow((int) k);
					XSSFCell cellA1 = row1.createCell((int) 0);
					cellA1.setCellValue(k - 1);

					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("brand_name"));

					XSSFCell cellC1 = row1.createCell((int) 2);
					cellC1.setCellValue(rs.getString("package_name") );
					
                    XSSFCell cellD1 = row1.createCell((int) 3);
					cellD1.setCellValue(rs.getString("box_size"));
					
				     XSSFCell cellE1 = row1.createCell((int) 4);
				    cellE1.setCellValue(rs.getString("code_generate_through"));
						
				    XSSFCell cellF1 = row1.createCell((int) 5);
					cellF1.setCellValue(rs.getString("avlbox"));
							
					XSSFCell cellG1 = row1.createCell((int) 6);
					cellG1.setCellValue(rs.getString("avlbottle"));

				}
				Random rand = new Random();
				int n = rand.nextInt(550) + 1; 
				fileOut = new FileOutputStream(relativePath
						+ "//ExciseUp//MIS//Excel//" 
						+ n + "_FL3_stock_CL.xlsx");

				action.setExlname( n + "_FL3_stock_CL.xlsx" );
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
				cellA4.setCellValue(" ");
				cellA4.setCellStyle(cellStyle);
				
				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue("Total");
				cellA5.setCellStyle(cellStyle);
				
				XSSFCell cellA6 = row1.createCell((int) 5);
				cellA6.setCellValue(total_avlbx);
				cellA6.setCellStyle(cellStyle);
				
				XSSFCell cellA7 = row1.createCell((int) 6);
				cellA7.setCellValue(total_avlbtl);
				cellA7.setCellStyle(cellStyle);

				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				flag = true;
				action.setExcelFlag(true);
				con.close();
				
				


			} catch (Exception e) {

			e.printStackTrace();

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								e.getMessage(), e.getMessage()));
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found!!", "No Data Found!!"));
				
				action.setExcelFlag(false);
				
				

			} finally {

				try {
					con.close();
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, e
									.getMessage(), e.getMessage()));
					

					e.printStackTrace();
				}
			}
	        return flag;

		}	
		
//===========================generate Excel
		
		public boolean generateexcel(Stock_FL3_FL3A_action action) {

			Connection con = null;
			con = ConnectionToDataBase.getConnection();
			DecimalFormat diciformatter = new DecimalFormat("#.##");
			String reportQuery = null;
			double total_avlbx = 0 ;
			double total_avlbtl = 0 ;
			SimpleDateFormat showDate = new SimpleDateFormat("dd-MM-yyyy");
			
			

		   //System.out.println("----------  Excel   -----" + reportQuery);


			String relativePath = Constants.JBOSS_SERVER_PATH
					+ Constants.JBOSS_LINX_PATH;
			FileOutputStream fileOut = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean flag = false;
			long k = 0;
	          
			try {
				
				
				reportQuery = " SELECT liquor_type,quantity as qnt_ml_detail,a.code_generate_through,b.domain,a.package_name, a.package_id, a.duty, a.adduty, c.int_brand_id, b.brand_name,coalesce(c.tnt,'X') as tt , c.tnt ,"
						+ " c.int_pckg_id, c.int_stock-c.int_dispatched as avlbottle, c.size as box_size,"
						+ " ROUND(((c.int_stock-c.int_dispatched)/c.size)) as avlbox,a.code_generate_through "
						+ " FROM distillery.packaging_details_20_21 a  , distillery.brand_registration_20_21 b, "
						+ " distillery.boxing_stock_20_21 c     "
						+ " WHERE a.brand_id_fk=b.brand_id AND a.brand_id_fk=c.int_brand_id "
						+ " AND a.package_id=c.int_pckg_id AND b.brand_id=c.int_brand_id "
						+ " AND c.int_dissleri_id='"+ action.getDist_id()+"' " +
						 " AND c.vch_lic_no='"+action.getLic_id()+"'  AND c.vch_lic_type='"+ action.getVch_from()+ "'" +
						" AND c.int_stock-c.int_dispatched >0   "
						+ " order by b.brand_name,a.package_name,c.tnt";

				pstmt = con.prepareStatement(reportQuery);

				rs = pstmt.executeQuery();
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet worksheet = workbook.createSheet("Stock_fl3_fl3A");
				worksheet.setColumnWidth(0, 2000);
				worksheet.setColumnWidth(1, 4000);
				worksheet.setColumnWidth(2, 7000);
				worksheet.setColumnWidth(3, 3000);	
				worksheet.setColumnWidth(4, 3000);	
				worksheet.setColumnWidth(5, 3000);	
				worksheet.setColumnWidth(6, 3000);	
				
				
				XSSFRow rowhead0 = worksheet.createRow((int) 0);
				XSSFCell cellhead0 = rowhead0.createCell((int) 1);
				cellhead0.setCellValue("Stock_fl3_fl3A");			
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
				cellhead1.setCellValue("Sr.No.");
				cellhead1.setCellStyle(cellStyle);

				XSSFCell cellhead2 = rowhead.createCell((int) 1);
				cellhead2.setCellValue("Brand Name");
				cellhead2.setCellStyle(cellStyle);

				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("Package Name");
				cellhead3.setCellStyle(cellStyle);

				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("Size (ml)");
				cellhead4.setCellStyle(cellStyle);

				XSSFCell cellhead5 = rowhead.createCell((int) 4);
				cellhead5.setCellValue("ETIN No");
				cellhead5.setCellStyle(cellStyle);
				
				XSSFCell cellhead6 = rowhead.createCell((int) 5);
				cellhead6.setCellValue("Available Box");
				cellhead6.setCellStyle(cellStyle);
				
				XSSFCell cellhead7 = rowhead.createCell((int) 6);
				cellhead7.setCellValue("Available Bottles");
				cellhead7.setCellStyle(cellStyle);
				
				int i = 0;
				while (rs.next()) {
					
					total_avlbx = total_avlbx + rs.getDouble("avlbox");
					
					total_avlbtl = total_avlbtl + rs.getDouble("avlbottle");
					
				    k++;
					XSSFRow row1 = worksheet.createRow((int) k);
					XSSFCell cellA1 = row1.createCell((int) 0);
					cellA1.setCellValue(k - 1);

					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("brand_name"));

					XSSFCell cellC1 = row1.createCell((int) 2);
					cellC1.setCellValue(rs.getString("package_name") );
					
                    XSSFCell cellD1 = row1.createCell((int) 3);
					cellD1.setCellValue(rs.getString("box_size"));
					
				     XSSFCell cellE1 = row1.createCell((int) 4);
				    cellE1.setCellValue(rs.getString("code_generate_through"));
						
				    XSSFCell cellF1 = row1.createCell((int) 5);
					cellF1.setCellValue(rs.getString("avlbox"));
							
					XSSFCell cellG1 = row1.createCell((int) 6);
					cellG1.setCellValue(rs.getString("avlbottle"));
			   
				
				}
				Random rand = new Random();
				int n = rand.nextInt(550) + 1; 
				fileOut = new FileOutputStream(relativePath
						+ "//ExciseUp//MIS//Excel//" 
						+ n + "_FL3_stock.xlsx");

				action.setExlname( n + "_FL3_stock.xlsx" );
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
				cellA4.setCellValue(" ");
				cellA4.setCellStyle(cellStyle);
				
				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue("Total");
				cellA5.setCellStyle(cellStyle);
				
				XSSFCell cellA6 = row1.createCell((int) 5);
				cellA6.setCellValue(total_avlbx);
				cellA6.setCellStyle(cellStyle);
				
				XSSFCell cellA7 = row1.createCell((int) 6);
				cellA7.setCellValue(total_avlbtl);
				cellA7.setCellStyle(cellStyle);
				
				

				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				flag = true;
				action.setExcelFlag(true);
				con.close();
				
				


			} catch (Exception e) {

			e.printStackTrace();

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								e.getMessage(), e.getMessage()));
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found!!", "No Data Found!!"));
				
				action.setExcelFlag(false);
				
				

			} finally {

				try {
					con.close();
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, e
									.getMessage(), e.getMessage()));
					

					e.printStackTrace();
				}
			}
	        return flag;

		}

}
