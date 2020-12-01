package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


import com.mentor.action.DateWiseDispatchAction;
import com.mentor.action.Monthwisereportonproductionandconsumptionofalcohol_action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class Monthwisereportonproductionandconsumptionofalcohol_impl {
	
	public ArrayList yearListImpl(Monthwisereportonproductionandconsumptionofalcohol_action act) {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue("");
		list.add(item);
		conn = ConnectionToDataBase.getConnection();

		try {
		
		
	String query = " SELECT year, value FROM public.reporting_year;";

				ps = conn.prepareStatement(query);
			
			   rs = ps.executeQuery();
          
			while (rs.next()) {

				item = new SelectItem();

				item.setValue(rs.getString("value"));
				item.setLabel(rs.getString("year"));
				
				//act.setYearr(rs.getString("value"));
				
				list.add(item);
				
				//System.out.println("== get year List== "+query);

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

//===================get Month
	    
	    public ArrayList getMonthList(Monthwisereportonproductionandconsumptionofalcohol_action act)
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
	    
//==  generate PDf
	    
	        public boolean printReportImpl(Monthwisereportonproductionandconsumptionofalcohol_action act) {
			String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
			String relativePath = mypath + File.separator + "ExciseUp"
					+ File.separator + "MIS" + File.separator + "jasper";
			String relativePathpdf = mypath + File.separator + "ExciseUp"
					+ File.separator + "MIS" + File.separator + "pdf";
			JasperReport jasperReport = null;
			JasperPrint jasperPrint = null;
			PreparedStatement pst = null;
			Connection con = null;
			ResultSet rs = null;
			String reportQuery = null;
			boolean printFlag = false;
			
			reportQuery = "  select distinct '' as tank_nm , x.vch_undertaking_name ,x.vch_original_capacity, sum(coalesce(y.produ_bl,0))as produ_bl , sum(coalesce(y.produ_al,0)) as produ_al ," +
					     "  sum(coalesce(y.export_bl,0)) as export_bl ,sum(coalesce(y.export_al,0)) as export_al from                                                                          " +
					     " (select distinct a.int_app_id_f ,a.vch_undertaking_name , a.vch_original_capacity                                                                                   " +                 
					     " from public.dis_mst_pd1_pd2_lic a where a.vch_unit_state ='1' and a.vch_verify_flag ='V' and vch_finalize ='F'  ) x left outer join                                 " +                 
					     " (select distinct b.distillery_id,sum(coalesce(b.produ_bl,0)) as produ_bl,  sum(coalesce(b.produ_al,0)) as produ_al ,                                                " +
					     " coalesce(sum(b.saleoutstatedrink_bl+b.saleoutstateother_bl+b.saleoutcountrydrink_bl+b.saleoutcountryother_bl),0) as export_bl,                                      " +
					     " coalesce(sum(b.saleoutstatedrink_al+b.saleoutstateother_al+b.saleoutcountrydrink_al+b.saleoutcountryother_al),0)as export_al                                        " +                 
					     " from distillery.productionandconsumptionofalcohol b where b.year_id='"+act.getYearr()+"' and b.month_id='"+act.getMontth()+"' group by b.distillery_id )y " +
					     " on x.int_app_id_f=y.distillery_id group by x.vch_undertaking_name , x.vch_original_capacity   order by x.vch_undertaking_name ";                                                                                                                                   
					                                                                                                                                                                           
				try {
					
				con = ConnectionToDataBase.getConnection();

				
			   //System.out.println("-------------report Query---------"+reportQuery);
				

				pst = con.prepareStatement(reportQuery);

				rs = pst.executeQuery();
				if (rs.next()) {

					rs = pst.executeQuery();

					Map parameters = new HashMap();
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("SUBREPORT_DIR", relativePath + File.separator);
					parameters.put("image", relativePath + File.separator);
					parameters.put("month",act.getMonthName());
					/*parameters.put("fromDate",
							Utility.convertUtilDateToSQLDate(act.getFromdate()));
					parameters.put("toDate",
							Utility.convertUtilDateToSQLDate(act.getTodate()));*/

					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

					jasperReport = (JasperReport) JRLoader.loadObject(relativePath
							+ File.separator
							+ "monthwiseReportonproductionandConsumption.jasper");

					JasperPrint print = JasperFillManager.fillReport(jasperReport,
							parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;

					JasperExportManager.exportReportToPdfFile(print,
							relativePathpdf + File.separator
									+ "monthwiseReportonproductionandConsumption" + n + ".pdf");

					//DateWiseDatatable dt = new DateWiseDatatable();
				
				
				
					act.setPdfname("monthwiseReportonproductionandConsumption" + n + ".pdf");
					act.setPrintFlag(true);
					printFlag = true;
				} else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("No Data Found", "No Data Found"));
					act.setPrintFlag(false);
					printFlag = false;
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

			return printFlag;
		}
	    
//====================Generate Excel
	   
	     public boolean write(Monthwisereportonproductionandconsumptionofalcohol_action act)
		 {

			Connection con = null;
			
			double production_al = 0;
			double production_bl = 0;
			double export_al = 0;
			double export_bl = 0;
			
			String sql = "";

			sql =    "  select distinct '' as tank_nm , x.vch_undertaking_name ,x.vch_original_capacity, sum(coalesce(y.produ_bl,0))as produ_bl , sum(coalesce(y.produ_al,0)) as produ_al ," +
				     "  sum(coalesce(y.export_bl,0)) as export_bl ,sum(coalesce(y.export_al,0)) as export_al from                                                                          " +
				     " (select distinct a.int_app_id_f ,a.vch_undertaking_name , a.vch_original_capacity                                                                                   " +                 
				     " from public.dis_mst_pd1_pd2_lic a where a.vch_unit_state ='1' and a.vch_verify_flag ='V' and vch_finalize ='F'  ) x left outer join                                 " +                 
				     " (select distinct b.distillery_id,sum(coalesce(b.produ_bl,0)) as produ_bl,  sum(coalesce(b.produ_al,0)) as produ_al ,                                                " +
				     " coalesce(sum(b.saleoutstatedrink_bl+b.saleoutstateother_bl+b.saleoutcountrydrink_bl+b.saleoutcountryother_bl),0) as export_bl,                                      " +
				     " coalesce(sum(b.saleoutstatedrink_al+b.saleoutstateother_al+b.saleoutcountrydrink_al+b.saleoutcountryother_al),0)as export_al                                        " +                 
				     " from distillery.productionandconsumptionofalcohol b where b.year_id='"+act.getYearr()+"' and b.month_id='"+act.getMontth()+"' group by b.distillery_id )y " +
				     " on x.int_app_id_f=y.distillery_id group by x.vch_undertaking_name , x.vch_original_capacity   order by x.vch_undertaking_name "; 
			
			

			String relativePath = Constants.JBOSS_SERVER_PATH
					+ Constants.JBOSS_LINX_PATH;
			FileOutputStream fileOut = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean flag = false;
			long k = 0;
			
			try {
				
				con = ConnectionToDataBase.getConnection();
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();

				XSSFWorkbook workbook = new XSSFWorkbook();
				
				XSSFSheet worksheet = workbook.createSheet("Monthwise Production And Export(Outside U.p + Outside Country) Of Alcohol");
				worksheet.setColumnWidth(0, 3000);
				worksheet.setColumnWidth(1, 8000);
				worksheet.setColumnWidth(2, 4000);
				worksheet.setColumnWidth(3, 9999);
				worksheet.setColumnWidth(4, 9999);
				worksheet.setColumnWidth(5, 4000);
				worksheet.setColumnWidth(6, 10000);
			    
				XSSFRow rowhead0 = worksheet.createRow((int) 0);
				XSSFCell cellhead0 = rowhead0.createCell((int) 0);
				cellhead0.setCellValue("Monthwise Production And Export(Outside U.p + Outside Country) Of Alcohol in"+"-"+act.getMonthName());
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
				cellhead2.setCellValue("Distillery Name");
				cellhead2.setCellStyle(cellStyle);

				XSSFCell cellhead7 = rowhead.createCell((int) 2);
				cellhead7.setCellValue("Capacity");
				cellhead7.setCellStyle(cellStyle);

				XSSFCell cellhead8 = rowhead.createCell((int) 3);
				cellhead8.setCellValue("Production AL");
				cellhead8.setCellStyle(cellStyle);

				XSSFCell cellhead9 = rowhead.createCell((int) 4);
				cellhead9.setCellValue("Production BL");
				cellhead9.setCellStyle(cellStyle);

				XSSFCell cellhead10 = rowhead.createCell((int) 5);
				cellhead10.setCellValue("Export AL");
				cellhead10.setCellStyle(cellStyle);

				XSSFCell cellhead11 = rowhead.createCell((int) 6);
				cellhead11.setCellValue("Export BL");
				cellhead11.setCellStyle(cellStyle);

				int i = 0;
				while (rs.next()) 
				{
					
                    production_al = production_al + rs.getDouble("produ_al");
                    production_bl = production_bl + rs.getDouble("produ_bl");
					export_al = export_al + rs.getDouble("export_al");
					export_bl = export_bl + rs.getDouble("export_bl");
					
					k++;
					XSSFRow row1 = worksheet.createRow((int) k);

					XSSFCell cellA1 = row1.createCell((int) 0);
					cellA1.setCellValue(k - 1);

					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("vch_undertaking_name"));

					XSSFCell cellC1 = row1.createCell((int) 2);
					cellC1.setCellValue(rs.getString("vch_original_capacity"));

					XSSFCell cellD1 = row1.createCell((int) 3);
					cellD1.setCellValue(rs.getDouble("produ_al"));

					XSSFCell cellE1 = row1.createCell((int) 4);
					cellE1.setCellValue(rs.getDouble("produ_bl"));

					XSSFCell cellF1 = row1.createCell((int) 5);
					cellF1.setCellValue(rs.getDouble("export_al"));

					XSSFCell cellG1 = row1.createCell((int) 6);
					cellG1.setCellValue(rs.getDouble("export_bl"));
                    
				}
				
				Random rand = new Random();
				int n = rand.nextInt(550) + 1;
				fileOut = new FileOutputStream(relativePath+ "/ExciseUp/MIS/Excel/" + n+"_monthwiseproductionandexport.xlsx");
				act.setExlname(n +"_monthwiseproductionandexport");

				XSSFRow row1 = worksheet.createRow((int) k + 1);

				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(" ");
				cellA1.setCellStyle(cellStyle);

				XSSFCell cellA2 = row1.createCell((int) 1);
				cellA2.setCellValue(" ");
				cellA2.setCellStyle(cellStyle);

				XSSFCell cellA3 = row1.createCell((int) 2);
				cellA3.setCellValue(" Total ");
				cellA3.setCellStyle(cellStyle);

				XSSFCell cellA4 = row1.createCell((int) 3);
				cellA4.setCellValue(production_al);
				cellA4.setCellStyle(cellStyle);

				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue(production_bl);
				cellA5.setCellStyle(cellStyle);

				XSSFCell cellA6 = row1.createCell((int) 5);
				cellA6.setCellValue(export_al);
				cellA6.setCellStyle(cellStyle);

				XSSFCell cellA7 = row1.createCell((int) 6);
				cellA7.setCellValue(export_bl);
				cellA7.setCellStyle(cellStyle);

				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();

				flag = true;
				act.setExcelFlag(true);
				con.close();
			} catch (Exception e) {
				// System.out.println("xls2" + e.getMessage());
				e.printStackTrace();
			} 
			 finally {
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
			return flag;
		} 
	     
//========================
	     
	     public void getMonthName(Monthwisereportonproductionandconsumptionofalcohol_action act)
	 	{

	 		
	 		Connection conn = null;
	 		PreparedStatement pstmt = null;
	 		ResultSet rs = null;

	 		try {
	 			String query = " SELECT description || ' (20"+act.getYearr().replaceAll("_", "-")+")' as month FROM public.month_master where month_id="+act.getMontth()+" ";

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
