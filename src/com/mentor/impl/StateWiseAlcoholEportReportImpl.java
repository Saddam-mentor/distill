package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

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
import com.mentor.action.StateWiseAlcoholEportReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class StateWiseAlcoholEportReportImpl {
	public void printReport(StateWiseAlcoholEportReportAction act) {

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
			
				reportQuery=" select x.vch_state_name , y.alloted_bl ,y.alloted_al,y.actual_lift_al,y.actual_lift_bl from                              "+
                        " (select vch_state_name,int_state_id from public.state_ind where int_state_id not in (18,33,1)order by vch_state_name)x   "+
                        " left outer join                                                                                                          "+
                        " (SELECT state_id, year_id,month_id, coalesce(sum(alloted_bl),0)alloted_bl, coalesce(sum(alloted_al),0)alloted_al,                          "+
                        " coalesce(sum(actual_lift_al),0)actual_lift_al, coalesce(sum(actual_lift_bl),0)actual_lift_bl                             "+
                        " FROM distillery.statewise_export_of_alcohol group by state_id, year_id,month_id)y                                                          "+
                        " on x.int_state_id = y.state_id and y.year_id='"+act.getYear_id()+"' and  month_id="+ act.getMonth_id()+" order by  x.vch_state_name";
                         
	                          
		
			 
			System.out.println("reportQuery---------" + reportQuery);
			pst = con.prepareStatement(reportQuery);
			

			rs = pst.executeQuery();

			if (rs.next()) {
				
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("month", act.getMonthName());
				//parameters.put("type", type);
			 	JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
			 	
			 		jasperReport = (JasperReport) JRLoader.loadObject(relativePath
							+ File.separator+ "StateWiseAlcoholExportReport.jasper");
			 	
				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator + "StateWiseAlcoholExportReport" + "-" + n + ".pdf");
				act.setPdf_name("StateWiseAlcoholExportReport" + "-" + n+ ".pdf");
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
	
	public boolean writeExcel(StateWiseAlcoholEportReportAction act) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double total_alloted_al=0.0;
		double total_alloted_bl=0.0;
		double total_lifted_al=0.0;
		double total_lifted_bl=0.0;
	

		String sql = "";

	 
		String loginFilter="";
		
			
                sql=" select x.vch_state_name , y.alloted_bl ,y.alloted_al,y.actual_lift_al,y.actual_lift_bl from                              "+
                        " (select vch_state_name,int_state_id from public.state_ind where int_state_id not in (18,33,1)order by vch_state_name)x   "+
                        " left outer join                                                                                                          "+
                        " (SELECT state_id, year_id,month_id, coalesce(sum(alloted_bl),0)alloted_bl, coalesce(sum(alloted_al),0)alloted_al,                          "+
                        " coalesce(sum(actual_lift_al),0)actual_lift_al, coalesce(sum(actual_lift_bl),0)actual_lift_bl                             "+
                        " FROM distillery.statewise_export_of_alcohol group by state_id, year_id,month_id)y                                                          "+
                        " on x.int_state_id = y.state_id and y.year_id='"+act.getYear_id()+"' and  month_id="+ act.getMonth_id()+" order by  x.vch_state_name";
                         
					

	       
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
			worksheet.setColumnWidth(2, 5000);
			worksheet.setColumnWidth(3, 5000);
			worksheet.setColumnWidth(4, 5000);
			worksheet.setColumnWidth(5, 5000);
			worksheet.setColumnWidth(6, 5000);
			worksheet.setColumnWidth(7, 5000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("Report On State wise Export Of Alcohol - "+act.getMonthName());

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
				cellhead2.setCellValue("State");
				cellhead2.setCellStyle(cellStyle);
				
				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("Actual Allotment In Month (AL)");
				cellhead3.setCellStyle(cellStyle);
	
				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("Actual Allotment In Month  (BL)");
				cellhead4.setCellStyle(cellStyle);
				
				XSSFCell cellhead5 = rowhead.createCell((int) 4);
				cellhead5.setCellValue("Actual Lifting In Month (AL)");
				cellhead5.setCellStyle(cellStyle);
				
				XSSFCell cellhead6 = rowhead.createCell((int) 5);
				cellhead6.setCellValue("Actual Lifting In Month (BL)");
				cellhead6.setCellStyle(cellStyle);
				
			

			int i = 0;
			while (rs.next()) {
				
				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k - 1);

				
				

					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("vch_state_name"));
					XSSFCell cellC1 = row1.createCell((int) 2);
					cellC1.setCellValue(rs.getDouble("alloted_al"));
					XSSFCell cellD1 = row1.createCell((int) 3);
					cellD1.setCellValue(rs.getDouble("alloted_bl"));
					XSSFCell cellE1 = row1.createCell((int) 4);
					cellE1.setCellValue(rs.getDouble("actual_lift_al"));
					XSSFCell cellF1 = row1.createCell((int) 5);
					cellF1.setCellValue(rs.getDouble("actual_lift_bl"));
					
					total_alloted_al = total_alloted_al+rs.getDouble("alloted_al");
					total_alloted_bl = total_alloted_bl+rs.getDouble("alloted_bl");
					total_lifted_al = total_lifted_al+rs.getDouble("actual_lift_al");
					total_lifted_bl = total_lifted_bl+rs.getDouble("actual_lift_bl");
				}
			
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "StateWiseAlcoholExport.xls");
			act.setExlname(n + "StateWiseAlcoholExport");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue("");
			cellA1.setCellStyle(cellStyle);
			XSSFCell cellB1 = row1.createCell((int) 1);
			cellB1.setCellValue("Total");
			cellB1.setCellStyle(cellStyle);
			XSSFCell cellC1 = row1.createCell((int) 2);
			cellC1.setCellValue(total_alloted_al);
			cellC1.setCellStyle(cellStyle);
			XSSFCell cellD1 = row1.createCell((int) 3);
			cellD1.setCellValue(total_alloted_bl);
			cellD1.setCellStyle(cellStyle);
			XSSFCell cellE1 = row1.createCell((int) 4);
			cellE1.setCellValue(total_lifted_al);
			cellE1.setCellStyle(cellStyle);
			XSSFCell cellF1 = row1.createCell((int) 5);
			cellF1.setCellValue(total_lifted_bl);
			cellF1.setCellStyle(cellStyle);
 
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
	
	public ArrayList getMonthList(StateWiseAlcoholEportReportAction act)
	{

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue("");
		list.add(item);
		try {
			String query = " SELECT month_id, description FROM public.month_master ORDER BY month_id ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			
			//System.out.println("------------------get Month List-------------"+query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("month_id"));
				item.setLabel(rs.getString("description"));
				
				//act.setMontth(rs.getInt("month_id"));

				list.add(item);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;

	}
	
	
	public void getMonthName(StateWiseAlcoholEportReportAction act)
	{

		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String query = " SELECT description || ' (20"+act.getYear_id().replaceAll("_", "-")+")' as month FROM public.month_master where month_id="+act.getMonth_id()+" ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			
			//System.out.println("------------------get Month List-------------"+query);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				
           act.setMonthName(rs.getString("month"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
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
