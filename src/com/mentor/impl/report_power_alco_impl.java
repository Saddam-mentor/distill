//  <!-- Name : Prasoon Mishra  --------- Date : 22/1/2020 ----->
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Date;
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

import com.mentor.action.DispatcherReportAction;
import com.mentor.action.report_power_alco_action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;


public class report_power_alco_impl {

	
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		
		
	public void printReportWUP(report_power_alco_action act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery1 = null;
		String type=null;
		

		try {
			con = ConnectionToDataBase.getConnection();
			
				reportQuery1 = "select a.int_req_seq,b.vch_undertaking_name as distillerynm,c.company_name||c.depo_name as omd ,d.description as district,"+
       " a.hq2dt as approvaldt,a.export_order_no as approval_no, "+
       " sum(COALESCE(a.int_qty_bl_bef_bh,0)+COALESCE(a.int_qty_bl_aft_bh,0)) as n_heavy ,  "+
      " sum(COALESCE(a.int_qty_bl_bef_ch,0)+COALESCE(a.int_qty_bl_aft_ch, 0) )as c_heavy_qty "+
       " FROM omd.omd_oup_expord_req a,dis_mst_pd1_pd2_lic b, fl41.fl41_registration_approval c,district d "+
        "where a.int_distid=b.int_app_id_f and c.int_id=a.int_omdid and d.districtid::text=b.vch_unit_dist "+
        "and a.statetype='UP' and a.final_status='APPROVED' and a.hq2dt between    '"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"'  "
        		+ "and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' "+
        " group by a.int_req_seq ,b.vch_undertaking_name,c.company_name||c.depo_name ,d.description,"+
        " a.hq2dt ,a.export_order_no ";
						
						
					
			pst = con.prepareStatement(reportQuery1);
			
			//System.out.println("reportQuery jasper: ====================================-"+reportQuery1);
			
				
				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				//parameters.put("radio",type);
				parameters.put("fromdate",Utility.convertUtilDateToSQLDate(act.getFromdate()));
				parameters.put("todate",Utility.convertUtilDateToSQLDate(act.getTodate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "report_power_alco_within_UP.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "report_power_alco_within_UP" + "-" + n + ".pdf");
				act.setPdfName("report_power_alco_within_UP" + "-" + n + ".pdf");
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
	
	
	
	public void printReportOUP(report_power_alco_action act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		
		String reportQuery2 = null;
	
		String type=null;

		try {
			
			
			con = ConnectionToDataBase.getConnection();
            	reportQuery2=" select (select vch_undertaking_name from dis_mst_pd1_pd2_lic where int_app_id_f=o.int_distid) as distillery_name, "+
							"	(select vch_state_name from state_ind c where c.int_state_id=o.int_state) as vch_state_name,o.int_omd_id, "+
							"	o.int_req_seq,o.vch_omd_name,concat(o.export_order_no,' dt- ',o.approvaldt) as int_req_seq,o.n_heavy as b_heavy, "+
							"	o.c_heavy_qty,COALESCE(actual_dispatch,0) as actual_dispatch from (SELECT int_distid,int_state,c.int_omd_id,a.int_req_seq," +
							" c.vch_omd_name ,a.hq2dt as approvaldt,a.export_order_no,COALESCE(int_qty_bl_bef_bh,0)+COALESCE(int_qty_bl_aft_bh,0)  " +
							" as n_heavy,COALESCE(int_qty_bl_bef_ch,0)+COALESCE(int_qty_bl_aft_ch,0) as c_heavy_qty  FROM omd.omd_oup_expord_req a, " +
							" omd.omd_register c where c.int_omd_id=a.int_omdid and a.statetype='OUP' and a.final_status='APPROVED' "+
							" and a.hq2dt between '"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"')o left outer join (select sum(actual_dispatch) as actual_dispatch,order_id," +
							" distillery_id,unit_id,int_state from (select int_req_seq as order_id,o.int_state,o.actual_dispatch,o.fl41_indent_no,o.distillery_id," +
							" o.unit_id from (select int_state,sum(total_dip_bl) as actual_dispatch ,fl41_indent_no,distillery_id,unit_id,EXTRACT(MONTH from dt_created) as cr_month, "+
							"	EXTRACT(YEAR  from dt_created) as cr_year  from distillery.export_denatured_spirit p,omd.omd_register b where p.unit_id::int=b.int_omd_id and "+
							"	dt_created>='"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"' and  vch_saletype in ('OMDOUP','OUP') and EXTRACT(MONTH from dt_created)=EXTRACT(MONTH from now()::date) and EXTRACT(YEAR  from dt_created)=EXTRACT(YEAR  from now()::date)" +
							" group by unit_id,cr_month,cr_year,unit_id,int_state,fl41_indent_no,distillery_id)o ,(select a.int_req_seq,a.int_omdid,a.int_distid,b.export_order_no,b.int_indent_seq from omd.omd_oup_expord_req a," +
							" omd.omd_oup_expord_indent b where a.int_req_seq=b.export_order_no::int and a.int_omdid=b.int_omdid and a.int_distid=b.int_distid)oo  "+
							"	where o.fl41_indent_no::int=oo.int_indent_seq and o.distillery_id=oo.int_distid and o.unit_id::int=oo.int_omdid)xx group by order_id,distillery_id,unit_id,int_state)oo on o.int_req_seq=oo.order_id ;  " ;
            	         
            	pst = con.prepareStatement(reportQuery2);
		
		
		
			
			
			//System.out.println("reportQuery jasper:-2"+reportQuery2);
			
				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("radio",type);
				parameters.put("fromdate",Utility.convertUtilDateToSQLDate(act.getFromdate()));
				parameters.put("todate",Utility.convertUtilDateToSQLDate(act.getTodate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "report_power_alco_outside_UP.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "report_power_alco_outside_UP" + "-" + n + ".pdf");
				act.setPdfName("report_power_alco_outside_UP" + "-" + n + ".pdf");
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

	public void printReportSW(report_power_alco_action act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		
		String reportQuery3 = null;
	
		String type=null;

		try {
			
			
			con = ConnectionToDataBase.getConnection();
            	reportQuery3=" select m.vch_state_name,n.int_state,COALESCE(n.b_heavy,0) as b_heavy,COALESCE(n.c_heavy,0) as c_heavy,m.int_state_id, " +
            			" m.actual_dispatch from ( select s.vch_state_name,s.int_state_id, " +
            			" COALESCE(actual_dispatch,0) as actual_dispatch from state_ind s left outer join (select int_state,sum(actual_dispatch) as actual_dispatch from (select int_state,sum(total_dip_bl) as actual_dispatch , " +
            			" unit_id,EXTRACT(MONTH from dt_created) as cr_month, "+
" EXTRACT(YEAR  from dt_created) as cr_year  from distillery.export_denatured_spirit p,omd.omd_register b where p.unit_id::int=b.int_omd_id and  dt_created>'2020-01-01' and  vch_saletype in ('OMDOUP','OUP') and EXTRACT(MONTH from dt_created)=EXTRACT(MONTH from now()::date) and EXTRACT(YEAR  from dt_created)=EXTRACT(YEAR  from now()::date) group by unit_id, " +
" cr_month,cr_year,unit_id,int_state)act group by int_state)xx on s.int_state_id=xx.int_state order by s.vch_state_name)m left outer join (select int_state,sum(n_heavy) as b_heavy,sum(c_heavy_qty) as c_heavy from (select int_state,concat(int_req_seq,'-dt-',a.hq2dt) as int_req_seq,int_omdid, "+
" COALESCE(int_qty_bl_bef_bh,0)+COALESCE(int_qty_bl_aft_bh,0)  as n_heavy,COALESCE(int_qty_bl_bef_ch,0)+COALESCE(int_qty_bl_aft_ch,0) as c_heavy_qty from "+
" omd.omd_oup_expord_req a,omd.omd_register b where a.int_omdid=b.int_omd_id and a.hq2dt between'"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"'  and " +
            					"'"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' " +
            					"and statetype='OUP' and a.final_status='APPROVED')xx group by int_state)n on n.int_state=m.int_state_id ";
            	           
            	pst = con.prepareStatement(reportQuery3);
		
		
		
			
			
			//System.out.println("reportQuery jasper:-2"+reportQuery3);
			
				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("radio",type);
				parameters.put("fromdate",Utility.convertUtilDateToSQLDate(act.getFromdate()));
				parameters.put("todate",Utility.convertUtilDateToSQLDate(act.getTodate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "report_power_alco_statewise.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "report_power_alco_statewise" + "-" + n + ".pdf");
				act.setPdfName("report_power_alco_statewise" + "-" + n + ".pdf");
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

/*========================To generate Excel=============Aman Mishra============Created date 23/04/2020============= */
	
	/*===================printexcelWUP====================*/
	
	public boolean printexcelWUP(report_power_alco_action action) 
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
		
		    sql = " select a.int_req_seq,b.vch_undertaking_name as distillerynm,c.company_name||c.depo_name as omd ,d.description as district,"+
		    	       " a.hq2dt as approvaldt,a.export_order_no as approval_no, "+
		    	       " sum(COALESCE(a.int_qty_bl_bef_bh,0)+COALESCE(a.int_qty_bl_aft_bh,0)) as n_heavy ,  "+
		    	      " sum(COALESCE(a.int_qty_bl_bef_ch,0)+COALESCE(a.int_qty_bl_aft_ch, 0) )as c_heavy_qty "+
		    	       " FROM omd.omd_oup_expord_req a,dis_mst_pd1_pd2_lic b, fl41.fl41_registration_approval c,district d "+
		    	        " where a.int_distid=b.int_app_id_f and c.int_id=a.int_omdid and d.districtid::text=b.vch_unit_dist "+
		    	        " and a.statetype='UP' and a.final_status='APPROVED' "
		    	        + " and a.hq2dt between    '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' "
		    	        		+ "  and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' "+
		    	        " group by a.int_req_seq ,b.vch_undertaking_name,c.company_name||c.depo_name ,d.description,"+
		    	        " a.hq2dt ,a.export_order_no  ";
		try 
		{
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(sql);
		
			
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Report Power Alco");
			
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 3000);
			worksheet.setColumnWidth(2, 13000);
			worksheet.setColumnWidth(3, 15000);
			worksheet.setColumnWidth(4, 3000);
			worksheet.setColumnWidth(5, 5000);
			worksheet.setColumnWidth(6, 5000);
			worksheet.setColumnWidth(7, 3000);
			worksheet.setColumnWidth(8, 3000);
			
		    XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("Report On Power Alcohol Within State BETWEEN "+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ " " + 
					" To " + " "+ Utility.convertUtilDateToSQLDate(action.getTodate()));
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
			cellhead2.setCellValue("REQ ID.");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("DISTILLERY NAME.");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("OMD.");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("DISTRICT.");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("APPROVAL DATE.");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("APPROVAL NO.");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("B-Heavy (BL).");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("C-Heavy (BL).");
			cellhead9.setCellStyle(cellStyle);

		while (rs.next()) 
			
			{
				total_b_heavy = total_b_heavy + rs.getLong("n_heavy");
				total_c_heavy = total_c_heavy + rs.getLong("c_heavy_qty");
			   
				k++;
				
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("int_req_seq"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("distillerynm"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("omd"));
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("district"));
				
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("approvaldt"));
				
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("approval_no"));

				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getString("n_heavy"));

				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getString("c_heavy_qty"));

					
}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+"//ExciseUp//MIS//Excel//"+n+"_"+"REPORT_ON_POWER_ALCOHOL_WITHIN_STATE.xlsx");
			action.setExlname(n+"_"+"REPORT_ON_POWER_ALCOHOL_WITHIN_STATE");
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
			
			XSSFCell cellA6 = row1.createCell((int) 5);	
			cellA6.setCellValue(" ");
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 6);			
			cellA7.setCellValue("Total ");
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 7);			
			cellA8.setCellValue(total_b_heavy);
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 8);			
			cellA9.setCellValue(total_c_heavy);
			cellA9.setCellStyle(cellStyle);
			
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
  /*========================printexcelOUP=================================*/	
	
	public boolean printexcelOUP(report_power_alco_action action) {
		System.out.println("hiii");
		String relativePath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		Connection con = null;
		String type = "";
		String sql = "";
		double total_b_heavy = 0.0;
		double total_c_heavy = 0.0;
		double total_dispatch = 0.0;
		
		
			sql = " select (select vch_undertaking_name from dis_mst_pd1_pd2_lic where int_app_id_f=o.int_distid) as distillery_name, "+
					"	(select vch_state_name from state_ind c where c.int_state_id=o.int_state) as vch_state_name,o.int_omd_id, "+
					"	o.int_req_seq,o.vch_omd_name,concat(o.export_order_no,' dt- ',o.approvaldt) as int_req_seq,o.n_heavy as b_heavy, "+
					"	o.c_heavy_qty,COALESCE(actual_dispatch,0) as actual_dispatch from (SELECT int_distid,int_state,c.int_omd_id,a.int_req_seq," +
					" c.vch_omd_name ,a.hq2dt as approvaldt,a.export_order_no,COALESCE(int_qty_bl_bef_bh,0)+COALESCE(int_qty_bl_aft_bh,0)  " +
					" as n_heavy,COALESCE(int_qty_bl_bef_ch,0)+COALESCE(int_qty_bl_aft_ch,0) as c_heavy_qty  FROM omd.omd_oup_expord_req a, " +
					" omd.omd_register c where c.int_omd_id=a.int_omdid and a.statetype='OUP' and a.final_status='APPROVED' "+
					" and a.hq2dt between '"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"'  and '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"')o left outer join (select sum(actual_dispatch) as actual_dispatch,order_id," +
					" distillery_id,unit_id,int_state from (select int_req_seq as order_id,o.int_state,o.actual_dispatch,o.fl41_indent_no,o.distillery_id," +
					" o.unit_id from (select int_state,sum(total_dip_bl) as actual_dispatch ,fl41_indent_no,distillery_id,unit_id,EXTRACT(MONTH from dt_created) as cr_month, "+
					"	EXTRACT(YEAR  from dt_created) as cr_year  from distillery.export_denatured_spirit p,omd.omd_register b where p.unit_id::int=b.int_omd_id and "+
					"	dt_created>='"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"' and  vch_saletype in ('OMDOUP','OUP') and EXTRACT(MONTH from dt_created)=EXTRACT(MONTH from now()::date) and EXTRACT(YEAR  from dt_created)=EXTRACT(YEAR  from now()::date)" +
					" group by unit_id,cr_month,cr_year,unit_id,int_state,fl41_indent_no,distillery_id)o ,(select a.int_req_seq,a.int_omdid,a.int_distid,b.export_order_no,b.int_indent_seq from omd.omd_oup_expord_req a," +
					" omd.omd_oup_expord_indent b where a.int_req_seq=b.export_order_no::int and a.int_omdid=b.int_omdid and a.int_distid=b.int_distid)oo  "+
					"	where o.fl41_indent_no::int=oo.int_indent_seq and o.distillery_id=oo.int_distid and o.unit_id::int=oo.int_omdid)xx group by order_id,distillery_id,unit_id,int_state)oo on o.int_req_seq=oo.order_id ;  " ;
		
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(sql);
		
			
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Report Power Alco");
			
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 3000);
			worksheet.setColumnWidth(2, 16000);
			worksheet.setColumnWidth(3, 8000);
			worksheet.setColumnWidth(4, 6000);
			worksheet.setColumnWidth(5, 8000);
			worksheet.setColumnWidth(6, 8000);
			worksheet.setColumnWidth(7, 8000);
			
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("REPORT ON POWER ALCOHOL OUTSIDE STATE BETWEEN "+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ " " + 
					" To " + " "+ Utility.convertUtilDateToSQLDate(action.getTodate()));
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
			cellhead2.setCellValue("Order No.");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Distillery Name.");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Oil Depot Name.");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("State.");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Approval Qty in BL (B-Heavy).");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue(" Approval Qty in BL (C-Heavy).");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue(" Actual Dispatch in Current Month.");
			cellhead8.setCellStyle(cellStyle);

			while (rs.next()) 
			{
				total_b_heavy = total_b_heavy + rs.getLong("b_heavy");
				total_c_heavy = total_c_heavy + rs.getLong("c_heavy_qty");
				total_dispatch = total_dispatch + rs.getLong("actual_dispatch");
			
			    k++;
				
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("int_req_seq"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("distillery_name"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("vch_omd_name"));
				
				XSSFCell cellJ1 = row1.createCell((int) 4);
				cellJ1.setCellValue(rs.getString("vch_state_name"));
				
				XSSFCell cellK1 = row1.createCell((int) 5);
				cellK1.setCellValue(rs.getString("b_heavy"));
				
				XSSFCell cellE1 = row1.createCell((int) 6);
				cellE1.setCellValue(rs.getString("c_heavy_qty"));

				XSSFCell cellF1 = row1.createCell((int) 7);
				cellF1.setCellValue(rs.getString("actual_dispatch"));

			}
			
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+"//ExciseUp//MIS//Excel//"+n+"_"+"REPORT_ON_POWER_ALCOHOL_OUTSIDE_STATE.xlsx");
			action.setExlname(n+"_"+"REPORT_ON_POWER_ALCOHOL_OUTSIDE_STATE");
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
			cellA6.setCellValue(total_b_heavy);
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 6);			
			cellA7.setCellValue(total_c_heavy);
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 7);			
			cellA8.setCellValue(total_dispatch);
			cellA8.setCellStyle(cellStyle);
		
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
	
	/*======================printexcelSW========================*/
	
	public boolean printexcelSW(report_power_alco_action action) {
		System.out.println("hiii");
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
		double total_dispatch = 0.0;
		String sql = "";
		
		sql = " select m.vch_state_name,n.int_state,COALESCE(n.b_heavy,0) as b_heavy,COALESCE(n.c_heavy,0) as c_heavy,m.int_state_id, " +
    			" m.actual_dispatch from ( select s.vch_state_name,s.int_state_id, " +
    			" COALESCE(actual_dispatch,0) as actual_dispatch from state_ind s left outer join (select int_state,sum(actual_dispatch) as actual_dispatch from (select int_state,sum(total_dip_bl) as actual_dispatch , " +
    			" unit_id,EXTRACT(MONTH from dt_created) as cr_month, "+
" EXTRACT(YEAR  from dt_created) as cr_year  from distillery.export_denatured_spirit p,omd.omd_register b where p.unit_id::int=b.int_omd_id and  dt_created>'2020-01-01' and  vch_saletype in ('OMDOUP','OUP') and EXTRACT(MONTH from dt_created)=EXTRACT(MONTH from now()::date) and EXTRACT(YEAR  from dt_created)=EXTRACT(YEAR  from now()::date) group by unit_id, " +
" cr_month,cr_year,unit_id,int_state)act group by int_state)xx on s.int_state_id=xx.int_state order by s.vch_state_name)m left outer join (select int_state,sum(n_heavy) as b_heavy,sum(c_heavy_qty) as c_heavy from (select int_state,concat(int_req_seq,'-dt-',a.hq2dt) as int_req_seq,int_omdid, "+
" COALESCE(int_qty_bl_bef_bh,0)+COALESCE(int_qty_bl_aft_bh,0)  as n_heavy,COALESCE(int_qty_bl_bef_ch,0)+COALESCE(int_qty_bl_aft_ch,0) as c_heavy_qty from "+
" omd.omd_oup_expord_req a,omd.omd_register b where a.int_omdid=b.int_omd_id and a.hq2dt between'"+Utility.convertUtilDateToSQLDate(action.getFromdate())+"'  and " +
    					"'"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' " +
    					"and statetype='OUP' and a.final_status='APPROVED')xx group by int_state)n on n.int_state=m.int_state_id ";
		
		
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(sql);
		
			
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Report Power Alco");
			
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 7000);
			worksheet.setColumnWidth(2, 7000);
			worksheet.setColumnWidth(3, 7000);
			worksheet.setColumnWidth(4, 9000);
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("REPORT ON POWER ALCOHOL STATEWISE BETWEEN "+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ " " + 
					" To " + " "+ Utility.convertUtilDateToSQLDate(action.getTodate()));
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
			cellhead2.setCellValue("STATE NAME.");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("ALLOTMENT C-HEAVY (BL).");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue(" ALLOTMENT B-HEAVY (BL).");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("ACTUAL DISPATCH IN CURRENT MONTH.");
			cellhead5.setCellStyle(cellStyle);
			
				while (rs.next()) {
				
				total_b_heavy = total_b_heavy + rs.getLong("b_heavy");
				total_c_heavy = total_c_heavy + rs.getLong("c_heavy");
				total_dispatch = total_dispatch + rs.getLong("actual_dispatch");

				k++;
				
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("vch_state_name"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("c_heavy"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("b_heavy"));
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("actual_dispatch"));
				
			}
			
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+"//ExciseUp//MIS//Excel//"+n+"_"+"REPORT_ON_POWER_ALCOHOL_STATEWISE.xlsx");
			action.setExlname(n+"_"+"REPORT_ON_POWER_ALCOHOL_STATEWISE");
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			
			XSSFCell cellA1 = row1.createCell((int) 0);			
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);
			
			XSSFCell cellA2 = row1.createCell((int) 1);			
			cellA2.setCellValue("Total");
			cellA2.setCellStyle(cellStyle);
			
			XSSFCell cellA3 = row1.createCell((int) 2);			
			cellA3.setCellValue(total_c_heavy);
			cellA3.setCellStyle(cellStyle);
			
			XSSFCell cellA4 = row1.createCell((int) 3);			
			cellA4.setCellValue(total_b_heavy);
			cellA4.setCellStyle(cellStyle);
			
			XSSFCell cellA5 = row1.createCell((int) 4);			
			cellA5.setCellValue(total_dispatch);
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
	
	
	}