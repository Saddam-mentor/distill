package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

import com.mentor.action.ReportOnProductionAndConsumptionOfAlcohol_action;
import com.mentor.action.report_power_alco_action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class ReportOnProductionAndConsumptionOfAlcohol_impl {
	
	public ArrayList getMonthList(ReportOnProductionAndConsumptionOfAlcohol_action act)
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
	
	//===================generate excel
	public boolean generateexcel(ReportOnProductionAndConsumptionOfAlcohol_action action) 
	{
		String relativePath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		Connection con = null;
		String type = "";
		double total_b_heavy = 0.0;
		double total_c_heavy = 0.0;
		String sql = "";
		
		    sql = 

" select 1 as sr ,'Production 'as des, sum(COALESCE(produ_al,0))as sum_pro_al, sum(COALESCE(produ_bl,0))as sum_pro_bl  FROM distillery.productionandconsumptionofalcohol where  month_id = '"+action.getMontth()+"'   and  year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          "+         
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 2 as sr, 'Import Out Of State ' as des,sum(COALESCE(importoutstate_al,0)) as sum_pro_al, sum(COALESCE(importoutstate_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'      and  year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 3 as sr,'Import Out Of India' as des,sum(COALESCE(importoutindia_al,0)) as sum_pro_al, sum(COALESCE(importoutindia_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'      and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 4 as sr, '   TOTAL (1+2+3) ' as des,sum(COALESCE(importoutindia_al,0)+COALESCE(importoutstate_al,0)+COALESCE(produ_al,0)) as sum_pro_al, sum(COALESCE(importoutindia_bl,0)+COALESCE(importoutstate_bl,0)+COALESCE(produ_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'   and  year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 5 as sr ,'Consumption (a)portable use only' as des,  sum(COALESCE( saleinupdrink_al,0)) as sum_pro_al, sum(COALESCE(saleinupdrink_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'    and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 6 as sr, 'Consumption (b)industrial use only' as des,sum(COALESCE(saleinupother_al,0)) as sum_pro_al, sum(COALESCE(saleinupother_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'  and  year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 7 as sr,'   TOTAL(5+6)  ' as des, sum(COALESCE(saleinupdrink_al,0)+COALESCE(saleinupother_al,0)) as sum_pro_al, sum(COALESCE(saleinupdrink_bl,0)+COALESCE(saleinupother_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'    and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 8 as sr,'  EXPORT ' as des,  '0' as sum_pro_al , '0' as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'   and  year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 9 as sr ,'Export Out of State (portable use only)'as des, sum(COALESCE(saleoutstatedrink_al,0))as sum_pro_al, sum(COALESCE(saleoutstatedrink_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'  and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 10 as sr, 'Export Out of State (industrial use only)' as des,sum(COALESCE(saleoutstateother_al,0))as sum_pro_al, sum(COALESCE(saleoutstateother_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'   and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 "+
" select 11 as sr, 'Export Out of Country' as des,sum(COALESCE(saleoutcountryother_al,0))as sum_pro_al, sum(COALESCE(saleoutcountryother_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'   and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 12 as sr,'  TOTAL EXPORT (9+10+11) ' as des,sum(COALESCE(saleoutcountryother_al,0)+COALESCE(saleoutstatedrink_al,0)+COALESCE(saleoutstateother_al,0)) as sum_pro_al, sum(COALESCE(saleoutcountryother_bl,0)+COALESCE(saleoutstatedrink_bl,0)+COALESCE(saleoutstateother_bl,0)) as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'   and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                  "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 13 as sr,'  TOTAL CONSUMPTION (12+7)' as des,sum(COALESCE(saleinupdrink_al,0)+COALESCE(saleinupother_al,0)+COALESCE(saleoutcountryother_al,0)+COALESCE(saleoutstateother_al,0)+COALESCE(saleoutstatedrink_al,0)) as sum_pro_al , sum(COALESCE(saleinupdrink_bl,0)+COALESCE(saleinupother_bl,0)+COALESCE(saleoutcountryother_bl,0)+COALESCE(saleoutstateother_bl,0)+COALESCE(saleoutstatedrink_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id='"+action.getMontth()+"'   and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                          "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 14 as sr ,'  STOCK REMAINING (4-13) ' as des, sum(COALESCE(importoutindia_al,0)+COALESCE(importoutstate_al,0)+COALESCE(produ_al,0)) - sum(COALESCE(saleinupdrink_al,0)+COALESCE(saleinupother_al,0)+COALESCE(saleoutcountryother_al,0)+COALESCE(saleoutstateother_al,0)+COALESCE(saleoutstatedrink_al,0)) as sum_pro_al, sum(COALESCE(importoutindia_bl,0)+COALESCE(importoutstate_bl,0)+COALESCE(produ_bl,0)) - sum(COALESCE(saleinupdrink_bl,0)+COALESCE(saleinupother_bl,0)+COALESCE(saleoutcountryother_bl,0)+COALESCE(saleoutstateother_bl,0)+COALESCE(saleoutstatedrink_bl,0)) as sum_pro_bl  FROM distillery.productionandconsumptionofalcohol where  month_id='"+action.getMontth()+"'  and year_id = '"+action.getYear_value()+"'                                                                         "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 15 as sr, '  Wastage ' as des,sum(COALESCE(wastage_al,0)) as sum_saleoutcountryother_al,sum(COALESCE(wastage_bl,0)) as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"' and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 16 as sr, ' ACTUAL REMAINING (14-15)' as des,(sum(COALESCE(importoutindia_al,0)+COALESCE(importoutstate_al,0)+COALESCE(produ_al,0)) - sum(COALESCE(saleinupdrink_al,0)+COALESCE(saleinupother_al,0)+COALESCE(saleoutcountryother_al,0)+COALESCE(saleoutstateother_al,0)+COALESCE(saleoutstatedrink_al,0)))- sum(COALESCE(wastage_al,0)) as sum_pro_al , ((sum(COALESCE(importoutindia_bl,0)+COALESCE(importoutstate_bl,0)+COALESCE(produ_bl,0)) - sum(COALESCE(saleinupdrink_bl,0)+COALESCE(saleinupother_bl,0)+COALESCE(saleoutcountryother_bl,0)+COALESCE(saleoutstateother_bl,0)+COALESCE(saleoutstatedrink_bl,0)))- (sum(COALESCE(wastage_bl,0))))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"' and year_id = '"+action.getYear_value()+"'        "+
" order by sr                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         " ;
						                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		                                                                 
		    
		    //System.out.println("------------------getexcel------------"+sql);
			try 
			{
				con = ConnectionToDataBase.getConnection();
				pstmt = con.prepareStatement(sql);
			
				
				rs = pstmt.executeQuery();
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet worksheet = workbook.createSheet("Report_On_Production_And_Consumption_Of_Alcohol");
				
				worksheet.setColumnWidth(0, 1500);
				worksheet.setColumnWidth(1, 10000);
				worksheet.setColumnWidth(2, 5000);
				worksheet.setColumnWidth(2, 5000);
				
			    XSSFRow rowhead0 = worksheet.createRow((int) 0);
				XSSFCell cellhead0 = rowhead0.createCell((int) 0);
				cellhead0.setCellValue("Report On Production And Consumption Of Alcohol    -   "+action.getMonth_name()+" "+action.getYear_name());
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
				cellhead1.setCellValue("Sr NO.");

				cellhead1.setCellStyle(cellStyle);

				XSSFCell cellhead2 = rowhead.createCell((int) 1);
				cellhead2.setCellValue("Description");
				cellhead2.setCellStyle(cellStyle);

				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("AL");
				cellhead3.setCellStyle(cellStyle);
				
				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("BL");
				cellhead4.setCellStyle(cellStyle);

				

			while (rs.next()) 
				
				{
					/*total_b_heavy = total_b_heavy + rs.getLong("n_heavy");
					total_c_heavy = total_c_heavy + rs.getLong("c_heavy_qty");*/
				   
					k++;
					
					XSSFRow row1 = worksheet.createRow((int) k);
					XSSFCell cellA1 = row1.createCell((int) 0);
					cellA1.setCellValue(k-1);

					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("des"));

					XSSFCell cellC1 = row1.createCell((int) 2);
					cellC1.setCellValue(rs.getString("sum_pro_al"));

					XSSFCell cellD1 = row1.createCell((int) 3);
					cellD1.setCellValue(rs.getString("sum_pro_bl"));
					
		
						
	}
				Random rand = new Random();
				int n = rand.nextInt(550) + 1;
				fileOut = new FileOutputStream(relativePath+"//ExciseUp//MIS//Excel//"+n+"_"+"Report_On_Production_And_Consumption_Of_Alcohol.xlsx");
				action.setExlname(n+"_"+"Report_On_Production_And_Consumption_Of_Alcohol");
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
				cellA5.setCellValue(" ");
				cellA5.setCellStyle(cellStyle);
				
				
				
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				flag = true;
				action.setExcelFlag(true);
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
			try
			{	
			
			if(con!=null)con.close();
			if(pstmt!=null)pstmt.close();
			if(rs!=null)rs.close();
			
			
			}
			catch(Exception e)
			{
			e.printStackTrace();
			}
			}
			return flag;
		
		}
	
	
	public void month_name(ReportOnProductionAndConsumptionOfAlcohol_action act)
	{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String query = " SELECT m.description,year FROM public.month_master m,public.reporting_year where month_id = '"+act.getMontth()+"' and  value = '"+act.getYear_value()+"' ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			
			//System.out.println("---------------name month----------"+query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
			act.setMonth_name(rs.getString("description"));
			act.setYear_name(rs.getString("year"));
			
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
	
	//===============================year list
	
    public ArrayList yearListImpl(ReportOnProductionAndConsumptionOfAlcohol_action act) {
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
			
			////System.out.println("== get year List== "+query);

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
	
	public void print_pdf(ReportOnProductionAndConsumptionOfAlcohol_action action)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String sql = null;
		String type=null;
		

		try {
			con = ConnectionToDataBase.getConnection();
			
			   sql = 	

" select 1 as sr ,'Production 'as des, sum(COALESCE(produ_al,0))as sum_pro_al, sum(COALESCE(produ_bl,0))as sum_pro_bl  FROM distillery.productionandconsumptionofalcohol where  month_id = '"+action.getMontth()+"'   and  year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          "+         
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 2 as sr, 'Import Out Of State ' as des,sum(COALESCE(importoutstate_al,0)) as sum_pro_al, sum(COALESCE(importoutstate_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'      and  year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 3 as sr,'Import Out Of India' as des,sum(COALESCE(importoutindia_al,0)) as sum_pro_al, sum(COALESCE(importoutindia_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'      and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 4 as sr, '   TOTAL (1+2+3) ' as des,sum(COALESCE(importoutindia_al,0)+COALESCE(importoutstate_al,0)+COALESCE(produ_al,0)) as sum_pro_al, sum(COALESCE(importoutindia_bl,0)+COALESCE(importoutstate_bl,0)+COALESCE(produ_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'   and  year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 5 as sr ,'Consumption (a)portable use only' as des,  sum(COALESCE( saleinupdrink_al,0)) as sum_pro_al, sum(COALESCE(saleinupdrink_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'    and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 6 as sr, 'Consumption (b)industrial use only' as des,sum(COALESCE(saleinupother_al,0)) as sum_pro_al, sum(COALESCE(saleinupother_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'  and  year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 7 as sr,'   TOTAL(5+6)  ' as des, sum(COALESCE(saleinupdrink_al,0)+COALESCE(saleinupother_al,0)) as sum_pro_al, sum(COALESCE(saleinupdrink_bl,0)+COALESCE(saleinupother_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'    and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 8 as sr,'  EXPORT ' as des,  '0' as sum_pro_al , '0' as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'   and  year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 9 as sr ,'Export Out of State (portable use only)'as des, sum(COALESCE(saleoutstatedrink_al,0))as sum_pro_al, sum(COALESCE(saleoutstatedrink_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'  and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 10 as sr, 'Export Out of State (industrial use only)' as des,sum(COALESCE(saleoutstateother_al,0))as sum_pro_al, sum(COALESCE(saleoutstateother_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'   and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 "+
" select 11 as sr, 'Export Out of Country' as des,sum(COALESCE(saleoutcountryother_al,0))as sum_pro_al, sum(COALESCE(saleoutcountryother_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'   and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 12 as sr,'  TOTAL EXPORT (9+10+11) ' as des,sum(COALESCE(saleoutcountryother_al,0)+COALESCE(saleoutstatedrink_al,0)+COALESCE(saleoutstateother_al,0)) as sum_pro_al, sum(COALESCE(saleoutcountryother_bl,0)+COALESCE(saleoutstatedrink_bl,0)+COALESCE(saleoutstateother_bl,0)) as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"'   and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                  "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 13 as sr,'  TOTAL CONSUMPTION (12+7)' as des,sum(COALESCE(saleinupdrink_al,0)+COALESCE(saleinupother_al,0)+COALESCE(saleoutcountryother_al,0)+COALESCE(saleoutstateother_al,0)+COALESCE(saleoutstatedrink_al,0)) as sum_pro_al , sum(COALESCE(saleinupdrink_bl,0)+COALESCE(saleinupother_bl,0)+COALESCE(saleoutcountryother_bl,0)+COALESCE(saleoutstateother_bl,0)+COALESCE(saleoutstatedrink_bl,0))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id='"+action.getMontth()+"'   and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                          "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 14 as sr ,'  STOCK REMAINING (4-13) ' as des, sum(COALESCE(importoutindia_al,0)+COALESCE(importoutstate_al,0)+COALESCE(produ_al,0)) - sum(COALESCE(saleinupdrink_al,0)+COALESCE(saleinupother_al,0)+COALESCE(saleoutcountryother_al,0)+COALESCE(saleoutstateother_al,0)+COALESCE(saleoutstatedrink_al,0)) as sum_pro_al, sum(COALESCE(importoutindia_bl,0)+COALESCE(importoutstate_bl,0)+COALESCE(produ_bl,0)) - sum(COALESCE(saleinupdrink_bl,0)+COALESCE(saleinupother_bl,0)+COALESCE(saleoutcountryother_bl,0)+COALESCE(saleoutstateother_bl,0)+COALESCE(saleoutstatedrink_bl,0)) as sum_pro_bl  FROM distillery.productionandconsumptionofalcohol where  month_id='"+action.getMontth()+"'  and year_id = '"+action.getYear_value()+"'                                                                         "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 15 as sr, '  Wastage ' as des,sum(COALESCE(wastage_al,0)) as sum_saleoutcountryother_al,sum(COALESCE(wastage_bl,0)) as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"' and year_id = '"+action.getYear_value()+"'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           "+
" union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "+
" select 16 as sr, ' ACTUAL REMAINING (14-15)' as des,(sum(COALESCE(importoutindia_al,0)+COALESCE(importoutstate_al,0)+COALESCE(produ_al,0)) - sum(COALESCE(saleinupdrink_al,0)+COALESCE(saleinupother_al,0)+COALESCE(saleoutcountryother_al,0)+COALESCE(saleoutstateother_al,0)+COALESCE(saleoutstatedrink_al,0)))- sum(COALESCE(wastage_al,0)) as sum_pro_al , ((sum(COALESCE(importoutindia_bl,0)+COALESCE(importoutstate_bl,0)+COALESCE(produ_bl,0)) - sum(COALESCE(saleinupdrink_bl,0)+COALESCE(saleinupother_bl,0)+COALESCE(saleoutcountryother_bl,0)+COALESCE(saleoutstateother_bl,0)+COALESCE(saleoutstatedrink_bl,0)))- (sum(COALESCE(wastage_bl,0))))as sum_pro_bl FROM distillery.productionandconsumptionofalcohol where month_id= '"+action.getMontth()+"' and year_id = '"+action.getYear_value()+"'        "+
" order by sr                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         " ;
						                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
					
			pst = con.prepareStatement(sql);
			
			//System.out.println("reportQuery jasper: ================-"+sql);
			
				
				rs = pst.executeQuery();
          System.out.println("---monthName---"+action.getMonth_name()+" j"+ action.getYear_name() );
			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);		
				parameters.put("monthName",action.getMonth_name());
				parameters.put("yearName",action.getYear_name());
				//parameters.put("todate",Utility.convertUtilDateToSQLDate(act.getTodate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "reportOnProductionAndConsumptionOfAlcohol.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "reportOnProductionAndConsumptionOfAlcohol" + "-" + n + ".pdf");
				action.setPdfName("reportOnProductionAndConsumptionOfAlcohol" + "-" + n + ".pdf");
				action.setPrintFlag(true);
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
	
}
