package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.mentor.action.BrandLabelApprovalaAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;

public class BrandLabelApprovalImpl {

	
	public void printReport(BrandLabelApprovalaAction act) {

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


          if(act.getRadio_type().equalsIgnoreCase("BL")){
        	  if(act.getRadioS().equalsIgnoreCase("S")){
        		  type="BrandLabel Summary";
       reportQuery =
    		   
    		  " SELECT distinct a.app_id,a.user5_date,a.vch_approved,b.description,c.brand_id,c.brand_name,c.distillery_id,c.app_id           "+
    		  " ,d.package_name,d.package_id,e.description as brandlable,f.vch_undertaking_name                                               "+
    		  " FROM brandlabel.brand_label_applications a, public.district b                                                                 "+
    		  " ,distillery.brand_registration_20_21 c,distillery.packaging_details_20_21 d                                                   "+
    		  " ,brandlabel.brand_label_uploading e,public.dis_mst_pd1_pd2_lic f                                                              "+
    		  " WHERE a.unit_district_id=b.districtid  AND a.label_flag=true and a.total_fees>=0 and a.challan_flg=true  and a.unit_type='D'  "+
    		  " AND a.user5_date IS NOT NULL and a.digital_sign_pdf_name is not null and a.digital_sign_date is not null and                  "+
    		  " a.digital_sign_time is not null  and a.app_id=c.app_id and c.app_id=d.app_id and  d.app_id=e.app_id                           "+
    		  " and c.brand_id=d.brand_id_fk and d.brand_id_fk =e.brand_id and d.package_id=e.package_id                                      "+
    		  "  and a.unit_id=f.int_app_id_f                                                                                                 "+
    		  "  group by a.unit_district_id,b.description ,a.user5_date,a.app_id,a.vch_approved,c.brand_id,c.brand_name,c.distillery_id,     "+
    		  " c.app_id,d.package_name,e.description,f.vch_undertaking_name " +
    		  " ,d.package_id   " + 
    		  " ORDER BY b.description,a.app_id  ";
    		   
    		   
    		   
    		   
    		   
    		   
    		   
    		/*   
    		   " SELECT distinct a.app_id,a.user5_date,a.vch_approved,b.description,c.brand_id,c.brand_name,c.distillery_id,c.app_id                                                        "+
      	        " ,d.package_name,f.vch_undertaking_name,  e.description  as brandlable                                                                                                                                    "+
      			//" count(a.app_id) filter(where  a.app_id=e.app_id and c.brand_id=e.brand_id ) as brandlable                                                                                       "+
      			" FROM brandlabel.brand_label_applications a, public.district b                                                                                                              "+
      			" ,distillery.brand_registration_20_21 c,distillery.packaging_details_20_21 d                                                                                                "+
      			" ,brandlabel.brand_label_uploading e,public.dis_mst_pd1_pd2_lic f                                                                                                           "+
      			" WHERE a.unit_district_id=b.districtid  AND a.label_flag=true and a.total_fees>=0 and a.challan_flg=true  and a.unit_type='D'                                               "+
      			" AND a.user5_date IS NOT NULL and a.digital_sign_pdf_name is not null and a.digital_sign_date is not null and                                                               "+
      			" a.digital_sign_time is not null  and a.app_id=c.app_id and c.app_id=d.app_id and c.brand_id=d.brand_id_fk  and                                                             "+
      			" a.app_id=e.app_id and a.unit_id=f.int_app_id_f                                                                                                                             "+
      			" group by a.unit_district_id,b.description, a.app_id,a.user5_date,a.vch_approved,c.brand_id,c.brand_name,c.distillery_id,c.app_id  ,d.package_name ,f.vch_undertaking_name , e.description "+                                                 
      			" ORDER BY b.description,   c.app_id ";
*/
        	  }
        	  else if(act.getRadioS().equalsIgnoreCase("D")){
        		  type="BrandLabel Detail";
       reportQuery =
    		 " select a.unit_district_id,a.user5_date,b.description,a.app_id,a.unit_name,                                                                "+
    		 " (select count(app_id) from  distillery.brand_registration_20_21   where app_id=a.app_id ) as barnd,                           "+
    		 "  (select count(brand_id_fk) from distillery.packaging_details_20_21 where app_id=a.app_id) as package,                        "+
    		 "  (select count(description) from brandlabel.brand_label_uploading where app_id=a.app_id and package_id=c.package_id) as brand_lable                       "+
    		 " from                                                                                                                          "+
    		 " brandlabel.brand_label_applications a, public.district b ,distillery.packaging_details_20_21 c                                                                        "+
    		 " WHERE a.unit_district_id=b.districtid  AND a.label_flag=true and a.total_fees>=0 and a.challan_flg=true  and a.unit_type='D'  "+
    		 " AND a.user5_date IS NOT NULL and a.digital_sign_pdf_name is not null and a.digital_sign_date is not null and   a.app_id=c.app_id and                   "+
    		 " a.digital_sign_time is not null order by b.description,a.app_id                                                               ";
    		   
    		   
    		   
    		/*   " select   a.unit_district_id,a.user5_date,count(a.app_id),count(a.app_id) filter(where a.app_id=c.app_id ) as brand_name,  "+
        			" count(a.app_id) filter(where  c.brand_id=d.brand_id_fk ) as package,                                                      "+
        			" count(a.app_id) filter(where  c.app_id=e.app_id and d.brand_id_fk =e.brand_id) as label,                                  "+
        			" count(a.app_id) filter(where  a.vch_approved='APPROVED' ),                                                                "+
        			" b.description                                                                                                             "+
        			" FROM brandlabel.brand_label_applications a, public.district b                                                             "+
        			" ,distillery.brand_registration_20_21 c,distillery.packaging_details_20_21 d                                               "+
        			" ,brandlabel.brand_label_uploading e                                                                                       "+
        			" WHERE a.unit_district_id=b.districtid  AND a.label_flag=true and a.total_fees>=0 and a.challan_flg=true                   "+
        			" AND a.user5_date IS NOT NULL and a.digital_sign_pdf_name is not null and a.digital_sign_date is not null and              "+
        			" a.digital_sign_time is not null and a.app_id=c.app_id and c.brand_id=d.brand_id_fk and  c.app_id=e.app_id                 "+
        			" and d.brand_id_fk =e.brand_id  and d.package_id=e.package_id                                                              "+
        			" group by   a.unit_district_id,a.user5_date,b.description order by b.description,a.user5_date                              ";
   						*/                                                                                                                        
        	 System.out.println("======upbonds detail====="+act.getRadioS()+""+reportQuery);  
        	  
        	  }
        	  
          }

        
          
			
		
					

			//System.out.println("======check== print jasper====="+reportQuery);
			pst = con.prepareStatement(reportQuery);

			rs = pst.executeQuery();



			if (rs.next()) {



				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("type", type);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				if(act.getRadio_type().equalsIgnoreCase("BL")){
		        	  if(act.getRadioS().equalsIgnoreCase("S")){
		        			jasperReport = (JasperReport) JRLoader.loadObject(relativePath
									+ File.separator+ "BrandLabelApprovalSummary.jasper");
		        	  }
		        	  else if(act.getRadioS().equalsIgnoreCase("D")){
		        			jasperReport = (JasperReport) JRLoader.loadObject(relativePath
									+ File.separator+ "BrandlabelApprovalDetail.jasper"); 
		        	  }
		        	  
		          }
				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
						+ "BrandLabelApproval" + "-" + n + ".pdf" );
				act.setPdfName("BrandLabelApproval" + "-" + n + ".pdf");
				//act.setPrintFlag(true);
				act.setPrintFlag(true);

			} else {
				//act.setPrintFlag(false);
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

	
	//---------------ankur
	
//-------------------------------------EXCEl Summary--------------------
	
	
	public boolean printexcelSummary(BrandLabelApprovalaAction action) 
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
		String sql = "";
		
		   
		try 
		{
			con = ConnectionToDataBase.getConnection();
			if(action.getRadio_type().equalsIgnoreCase("BL")){
		       	  if(action.getRadioS().equalsIgnoreCase("S")){
		       		  type="BrandLabel Summary";
		       		sql =  	  " SELECT distinct a.app_id,a.user5_date,a.vch_approved,b.description,c.brand_id,c.brand_name,c.distillery_id,c.app_id           "+
		          		  " ,d.package_name,d.package_id,e.description as brandlable,f.vch_undertaking_name                                               "+
		        		  " FROM brandlabel.brand_label_applications a, public.district b                                                                 "+
		        		  " ,distillery.brand_registration_20_21 c,distillery.packaging_details_20_21 d                                                   "+
		        		  " ,brandlabel.brand_label_uploading e,public.dis_mst_pd1_pd2_lic f                                                              "+
		        		  " WHERE a.unit_district_id=b.districtid  AND a.label_flag=true and a.total_fees>=0 and a.challan_flg=true  and a.unit_type='D'  "+
		        		  " AND a.user5_date IS NOT NULL and a.digital_sign_pdf_name is not null and a.digital_sign_date is not null and                  "+
		        		  " a.digital_sign_time is not null  and a.app_id=c.app_id and c.app_id=d.app_id and  d.app_id=e.app_id                           "+
		        		  " and c.brand_id=d.brand_id_fk and d.brand_id_fk =e.brand_id and d.package_id=e.package_id                                      "+
		        		  "  and a.unit_id=f.int_app_id_f                                                                                                 "+
		        		  "  group by a.unit_district_id,b.description ,a.user5_date,a.app_id,a.vch_approved,c.brand_id,c.brand_name,c.distillery_id,     "+
		        		  " c.app_id,d.package_name,e.description,f.vch_undertaking_name " +
		        		  " ,d.package_id   " + 
		        		  " ORDER BY b.description,a.app_id  ";
		       				
		       				
		       			
		       				
		       			
		       		System.out.println("======UPBOND Summary====="+action.getRadioS()+""+sql);
		       	  
		       	  }
		       
		       	  
		         }

		         
			pstmt = con.prepareStatement(sql);
		
			
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("wholesale Summary");
			
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 10000);
			worksheet.setColumnWidth(2, 8000);
			worksheet.setColumnWidth(3, 8000);
			worksheet.setColumnWidth(4, 8000);
			worksheet.setColumnWidth(5, 5000);
			worksheet.setColumnWidth(6, 5000);
			
			
		    XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("BrandLable Approval "+ type);
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
			cellhead2.setCellValue("District Name.");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Distillery Name");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Brand Name");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Package");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Label.");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Approval Date.");
			cellhead7.setCellStyle(cellStyle);
		

		while (rs.next()) 
			
			{
		/*		total_b_heavy = total_b_heavy + rs.getLong("n_heavy");
				total_c_heavy = total_c_heavy + rs.getLong("c_heavy_qty");*/
			   
				k++;
				
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("vch_undertaking_name"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("brand_name"));
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("package_name"));
				
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("brandlable"));
				
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("user5_date"));
				
			

					
}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+"//ExciseUp//MIS//Excel//"+n+"_"+"BrandLabelApproval.xlsx");
			action.setExlname(n+"_"+"BrandLabelApproval");
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

/*===================printexceUP BondDetail====================*/
	
	public boolean printexcelDetail(BrandLabelApprovalaAction action) 
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
	long total_no_2A = 00;
	long total_no_2B = 00;
	long total_no_2C = 00;
	//long total_no_2D = 00;
		String sql = "";
		
		   
		try 
		{
			con = ConnectionToDataBase.getConnection();
	         if(action.getRadio_type().equalsIgnoreCase("BL")){
	         if(action.getRadioS().equalsIgnoreCase("D")){
	        		  type="BrandLabel Detail";
	        		  sql = " select a.unit_district_id,a.user5_date,b.description,a.app_id, a.unit_name,                                                               "+
	        		    		 " (select count(app_id) from  distillery.brand_registration_20_21   where app_id=a.app_id ) as brand_name,                           "+
	        		    		 "  (select count(brand_id_fk) from distillery.packaging_details_20_21 where app_id=a.app_id) as package,                        "+
	        		    		 "  (select count(description) from brandlabel.brand_label_uploading where app_id=a.app_id) as brand_lable                       "+
	        		    		 " from                                                                                                                          "+
	        		    		 " brandlabel.brand_label_applications a, public.district b                                                                      "+
	        		    		 " WHERE a.unit_district_id=b.districtid  AND a.label_flag=true and a.total_fees>=0 and a.challan_flg=true  and a.unit_type='D'  "+
	        		    		 " AND a.user5_date IS NOT NULL and a.digital_sign_pdf_name is not null and a.digital_sign_date is not null and                  "+
	        		    		 " a.digital_sign_time is not null order by b.description,a.app_id                                                               ";
	        		    		   
	        		    		 
	   						                                                                                                                        
	        	 System.out.println("======upbonds detail====="+action.getRadioS()+""+sql);  
	        	  
	        	  }
	        	  
	          }


		       
			pstmt = con.prepareStatement(sql);
		
			
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("upbonds detail");
			
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 10000);
			worksheet.setColumnWidth(2, 3000);
			worksheet.setColumnWidth(3, 3000);
			worksheet.setColumnWidth(4, 3000);
			worksheet.setColumnWidth(5, 3000);
			
			
		    XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("BrandLableApproval "+ type);
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
			cellhead2.setCellValue("District Name.");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Unit Name.");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Brand Name");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Package");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Label.");
			cellhead6.setCellStyle(cellStyle);
			
			
		

		while (rs.next()) 
			
			{
				total_no_2A = total_no_2A + rs.getLong("brand_name");
				total_no_2B = total_no_2B + rs.getLong("package");
				total_no_2C = total_no_2C + rs.getLong("brand_lable");
	
			   
				k++;
				
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("unit_name"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("brand_name"));
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("package"));
				
				XSSFCell cellF1 = row1.createCell((int) 4);
				cellF1.setCellValue(rs.getString("brand_lable"));
			
			

					
}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+"//ExciseUp//MIS//Excel//"+n+"_"+"BrandLableApproval.xlsx");
			action.setExlname(n+"_"+"BrandLableApproval");
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			
			XSSFCell cellA1 = row1.createCell((int) 0);			
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);
			
			XSSFCell cellA2 = row1.createCell((int) 1);			
			cellA2.setCellValue("");
			cellA2.setCellStyle(cellStyle);
			
			XSSFCell cellA3 = row1.createCell((int) 2);			
			cellA3.setCellValue("Total");
			cellA3.setCellStyle(cellStyle);
			
			XSSFCell cellA4 = row1.createCell((int) 3);			
			cellA4.setCellValue(total_no_2A);
			cellA4.setCellStyle(cellStyle);
			
			XSSFCell cellA5 = row1.createCell((int) 4);			
			cellA5.setCellValue(total_no_2B);
			cellA5.setCellStyle(cellStyle);
			
			XSSFCell cellA6 = row1.createCell((int) 5);			
			cellA6.setCellValue(total_no_2C);
			cellA6.setCellStyle(cellStyle);
			
		
		
			

			
	
			
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
