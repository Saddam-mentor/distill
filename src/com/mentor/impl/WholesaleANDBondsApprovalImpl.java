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

import com.mentor.action.WholesaleANDBondsApprovalAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;

public class WholesaleANDBondsApprovalImpl {

	
	public void printReport(WholesaleANDBondsApprovalAction act) {

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


          if(act.getRadio_type().equalsIgnoreCase("UP")){
        	  if(act.getRadioS().equalsIgnoreCase("S")){
        		  type="UPBonds Summary";
        	  reportQuery = " SELECT  b.app_no,a.registrationid, c.description, b.license_type,                                                   "+
        					" replace(SUBSTRING(b.permit_esign , 51 , 77),'_esign.pdf','') AS lice_no, a.reg_nm,                          "+
        					" b.user4_dt                                                                                                          "+
        					" FROM bwfl_license.upbond_registration_new a, bwfl_license.upbond_applications_new b,                                "+        
        					" public.district c, public.joint_commissioners_zone_master f                                                         "+
        					" WHERE a.registrationid=b.reg_id_fk AND b.district=c.districtid::text   and b.old_vch_application_no is null         "+                                     
        					" AND b.payment_flg=true AND f.pk_id=c.zoneid and vch_approved='APPROVED' and b.user4_dt is not null ORDER BY a.registrationid DESC, b.app_no DESC ";
        	  }
        	  else if(act.getRadioS().equalsIgnoreCase("D")){
        		  type="UPBonds Detail";
        	reportQuery =" select a.district as district_id, count(a.vch_application_no), "+
   	            		 " count(a.vch_application_no) filter (where a.old_vch_application_no  is not  null and a.license_type='2A' ) as no_2A ,                                                             "+
   	            		 " count(a.vch_application_no) filter (where a.old_vch_application_no  is not  null and a.license_type='2B') as no_2B ,                                                              "+
   	            		 " count(a.vch_application_no) filter (where a.old_vch_application_no   is not null and a.license_type='2C') as no_2C ,                                                              "+
   	            		 " count(a.vch_application_no) filter (where a.old_vch_application_no   is not null and a.license_type='2D') as no_2D ,                                                              "+
   	            		 " b.description,count(a.vch_application_no) filter(where a.old_vch_application_no  is not  null and a.vch_approved='APPROVED')                                                      "+
   						 "  from bwfl_license.upbond_applications_new  a ,                                                                                                                                   "+
   	            		 " public.district b where a.old_vch_application_no  is not null  and security_fee is true and license_fee is true and   " +
   	            		 " payment_flg is true  and  a.district = b.districtid::text  "+
   	            		 " group by b.description, district_id order by b.description   ";
   						 
        	 //System.out.println("======upbonds detail====="+act.getRadioS()+""+reportQuery);  
        	  
        	  }
        	  
          }

          else if(act.getRadio_type().equalsIgnoreCase("W")){
        	  if(act.getRadioS().equalsIgnoreCase("S")){
        		  type="WholeSale Summary";                                                                                                                  
        	 reportQuery=" select   b.applicationid as app_no ,b.reg_id_fk as registrationid,c.description,b.license_type,                         "+
        				" replace(SUBSTRING(b.wholesale_permit_esign_name, 3, 23),'_esign.pdf','') AS lice_no,     "+
        				" a.applicant_nm as reg_nm,b.user4_dt                                                                                      "+
        				" FROM bwfl_license.registration_for_district_wholesale_new a, bwfl_license.district_warehouse_applications_new b,         "+
        				" public.district c, public.joint_commissioners_zone_master f                                                              "+                             
        				" WHERE a.registration_id=b.reg_id_fk AND b.application_district_id=c.districtid                                           "+
        				" AND b.phase3=true AND f.pk_id=c.zoneid and old_vch_application_no is not null                                            "+
        				" and vch_approved='APPROVED'   and b.user4_dt is not null   ORDER BY b.reg_id_fk DESC, b.applicationid DESC                                           ";
        	  }                                                                                                                                   
        	  else if(act.getRadioS().equalsIgnoreCase("D")){
        		  type="WholeSale Summary";
        	  reportQuery = " select a.application_district_id as district_id, count(a.vch_application_no), "+
        					" count(a.vch_application_no) filter (where a.old_vch_application_no  is not  null and a.license_type='FL2' ) as no_FL2 ,       "+
        					" count(a.vch_application_no) filter (where a.old_vch_application_no  is not  null and a.license_type='CL2') as no_CL2 ,        "+
        					" count(a.vch_application_no) filter (where a.old_vch_application_no   is not null and a.license_type='FL2B') as no_FL2B ,      "+
        					" count(a.vch_application_no) filter (where a.old_vch_application_no   is not null and a.license_type='FL2D') as no_FL2D ,      "+
        					" b.description,count(a.vch_application_no) filter(where a.old_vch_application_no  is not  null and a.vch_approved='APPROVED')  "+
        					" as approved                                                                                                                   "+
        					" from bwfl_license.district_warehouse_applications_new  a ,                                                                    "+
        					" public.district b                                                                                                             "+
        					" where a.old_vch_application_no  is not null  and a.application_district_id = b.districtid                                     "+
        					"	            		 group by b.description, district_id order by b.description                                             ";
        	 
        	  
        	  //System.out.println("======wholesale detail====="+act.getRadioS()+""+reportQuery);  
        	  
        	  }
        	  
          }
          
			
		
					

			System.out.println("======check== print jasper====="+reportQuery);
			pst = con.prepareStatement(reportQuery);

			rs = pst.executeQuery();



			if (rs.next()) {



				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("type", type);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				if(act.getRadio_type().equalsIgnoreCase("UP")){
		        	  if(act.getRadioS().equalsIgnoreCase("S")){
		        			jasperReport = (JasperReport) JRLoader.loadObject(relativePath
									+ File.separator+ "Wholseal_bond_ApprovalSummary.jasper");
		        	  }
		        	  else if(act.getRadioS().equalsIgnoreCase("D")){
		        			jasperReport = (JasperReport) JRLoader.loadObject(relativePath
									+ File.separator+ "Wholseal_bond_Approal_BondDetail.jasper"); 
		        	  }
		        	  
		          }

		          else if(act.getRadio_type().equalsIgnoreCase("W")){
		        	  if(act.getRadioS().equalsIgnoreCase("S")){
		        			jasperReport = (JasperReport) JRLoader.loadObject(relativePath
									+ File.separator+ "Wholseal_bond_ApprovalSummary.jasper");
		        	  }
		        	  else if(act.getRadioS().equalsIgnoreCase("D")){
		        			jasperReport = (JasperReport) JRLoader.loadObject(relativePath
									+ File.separator+ "Wholseal_bond_Approval_WholsaleDetail.jasper");
		        	  }
		        	  
		          }

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
						+ "Wholseal_bond_Approval" + "-" + n + ".pdf" );
				act.setPdfName("Wholseal_bond_Approval" + "-" + n + ".pdf");
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
	
	
	public boolean printexcelSummary(WholesaleANDBondsApprovalAction action) 
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
		
		   
		try 
		{
			con = ConnectionToDataBase.getConnection();
			if(action.getRadio_type().equalsIgnoreCase("UP")){
		       	  if(action.getRadioS().equalsIgnoreCase("S")){
		       		  type="Summary";
		       		sql = " SELECT  b.app_no,a.registrationid, c.description, b.license_type,                                                   "+
		       					" replace(SUBSTRING(b.permit_esign , 51 , 77),'_esign.pdf','') AS lice_no, a.reg_nm,                          "+
		       					" b.user4_dt                                                                                                          "+
		       					" FROM bwfl_license.upbond_registration_new a, bwfl_license.upbond_applications_new b,                                "+        
		       					" public.district c, public.joint_commissioners_zone_master f                                                         "+
		       					" WHERE a.registrationid=b.reg_id_fk AND b.district=c.districtid::text   and b.old_vch_application_no is null         "+                                     
		       					" AND b.payment_flg=true AND f.pk_id=c.zoneid and vch_approved='APPROVED' and b.user4_dt is not null ORDER BY a.registrationid DESC, b.app_no DESC ";
		       	  
		       	  
		       		System.out.println("======UPBOND Summary====="+action.getRadioS()+""+sql);
		       	  
		       	  }
		       
		       	  
		         }

		         else if(action.getRadio_type().equalsIgnoreCase("W")){
		       	  if(action.getRadioS().equalsIgnoreCase("S")){
		       		  type="Summary";                                                                                                                  
		       		sql=" select   b.applicationid as app_no ,b.reg_id_fk as registrationid,c.description,b.license_type,                         "+
		       				" replace(SUBSTRING(b.wholesale_permit_esign_name, 3, 23),'_esign.pdf','') AS lice_no,     "+
		       				" a.applicant_nm as reg_nm,b.user4_dt                                                                                      "+
		       				" FROM bwfl_license.registration_for_district_wholesale_new a, bwfl_license.district_warehouse_applications_new b,         "+
		       				" public.district c, public.joint_commissioners_zone_master f                                                              "+                             
		       				" WHERE a.registration_id=b.reg_id_fk AND b.application_district_id=c.districtid                                           "+
		       				" AND b.phase3=true AND f.pk_id=c.zoneid and old_vch_application_no is not null                                            "+
		       				" and vch_approved='APPROVED'   and b.user4_dt is not null   ORDER BY b.reg_id_fk DESC, b.applicationid DESC                                           ";
		       	 
		       	  
		       	 System.out.println("======wholesale Summary====="+action.getRadioS()+""+sql);
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
			
			
		    XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("Wholesale AND BondApproval "+ type);
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
			cellhead3.setCellValue("Licence Type.");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Licence No.");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Licence Name.");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("APPROVAL DATE.");
			cellhead6.setCellStyle(cellStyle);
			
		

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
				cellC1.setCellValue(rs.getString("license_type"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("lice_no"));
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("reg_nm"));
				
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("user4_dt"));
				
			

					
}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+"//ExciseUp//MIS//Excel//"+n+"_"+"WholesaleANDBondApproval.xlsx");
			action.setExlname(n+"_"+"WholesaleANDBondApproval");
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
	
	public boolean printexcelDetail(WholesaleANDBondsApprovalAction action) 
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
	long total_no_2D = 00;
		String sql = "";
		
		   
		try 
		{
			con = ConnectionToDataBase.getConnection();
			if(action.getRadio_type().equalsIgnoreCase("UP")){
			 if(action.getRadioS().equalsIgnoreCase("D")){
		        		  type="Detail";
		        	sql =" select a.district as district_id, count(a.vch_application_no), "+
		   	            		 " count(a.vch_application_no) filter (where a.old_vch_application_no  is not  null and a.license_type='2A' ) as no_2A ,                                                             "+
		   	            		 " count(a.vch_application_no) filter (where a.old_vch_application_no  is not  null and a.license_type='2B') as no_2B ,                                                              "+
		   	            		 " count(a.vch_application_no) filter (where a.old_vch_application_no   is not null and a.license_type='2C') as no_2C ,                                                              "+
		   	            		 " count(a.vch_application_no) filter (where a.old_vch_application_no   is not null and a.license_type='2D') as no_2D ,                                                              "+
		   	            		 " b.description,count(a.vch_application_no) filter(where a.old_vch_application_no  is not  null and a.vch_approved='APPROVED')                                                      "+
		   						 "  from bwfl_license.upbond_applications_new  a ,                                                                                                                                   "+
		   	            		 " public.district b where a.old_vch_application_no  is not null  and security_fee is true and license_fee is true and   " +
		   	            		 " payment_flg is true  and  a.district = b.districtid::text  "+
		   	            		 " group by b.description, district_id order by b.description   ";
		   						 
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
			cellhead0.setCellValue("Wholesale AND BondApproval "+ type);
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
			cellhead3.setCellValue("BWFL 2A.");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("BWFL 2B.");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("BWFL 2C.");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("BWFL 2D.");
			cellhead6.setCellStyle(cellStyle);
			
		

		while (rs.next()) 
			
			{
				total_no_2A = total_no_2A + rs.getLong("no_2A");
				total_no_2B = total_no_2B + rs.getLong("no_2B");
				total_no_2C = total_no_2C + rs.getLong("no_2C");
				total_no_2D = total_no_2D + rs.getLong("no_2D");
			   
				k++;
				
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("no_2A"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("no_2B"));
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("no_2C"));
				
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("no_2D"));
				
			

					
}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+"//ExciseUp//MIS//Excel//"+n+"_"+"WholesaleANDBondApproval.xlsx");
			action.setExlname(n+"_"+"WholesaleANDBondApproval");
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			
			XSSFCell cellA1 = row1.createCell((int) 0);			
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);
			
			XSSFCell cellA2 = row1.createCell((int) 1);			
			cellA2.setCellValue("Total");
			cellA2.setCellStyle(cellStyle);
			
			XSSFCell cellA3 = row1.createCell((int) 2);			
			cellA3.setCellValue(total_no_2A);
			cellA3.setCellStyle(cellStyle);
			
			XSSFCell cellA4 = row1.createCell((int) 3);			
			cellA4.setCellValue(total_no_2B);
			cellA4.setCellStyle(cellStyle);
			
			XSSFCell cellA5 = row1.createCell((int) 4);			
			cellA5.setCellValue(total_no_2C);
			cellA5.setCellStyle(cellStyle);
			
			XSSFCell cellA6 = row1.createCell((int) 5);	
			cellA6.setCellValue(total_no_2D);
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

	//---------------------Wholsealm Detail
	public boolean printexcelWholeseal(WholesaleANDBondsApprovalAction action) 
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
		long total_no_FL2 = 00;
		long total_no_CL2 = 00;
		long total_no_FL2B = 00;
		long total_no_FL2D = 00;
		String sql = "";
		
		   
		try 
		{
			con = ConnectionToDataBase.getConnection();
			if(action.getRadio_type().equalsIgnoreCase("W")){
			 if(action.getRadioS().equalsIgnoreCase("D")){
				 type="detail";
	        	  sql = " select a.application_district_id as district_id, count(a.vch_application_no), "+
	        					" count(a.vch_application_no) filter (where a.old_vch_application_no  is not  null and a.license_type='FL2' ) as no_FL2 ,       "+
	        					" count(a.vch_application_no) filter (where a.old_vch_application_no  is not  null and a.license_type='CL2') as no_CL2 ,        "+
	        					" count(a.vch_application_no) filter (where a.old_vch_application_no   is not null and a.license_type='FL2B') as no_FL2B ,      "+
	        					" count(a.vch_application_no) filter (where a.old_vch_application_no   is not null and a.license_type='FL2D') as no_FL2D ,      "+
	        					" b.description,count(a.vch_application_no) filter(where a.old_vch_application_no  is not  null and a.vch_approved='APPROVED')  "+
	        					" as approved                                                                                                                   "+
	        					" from bwfl_license.district_warehouse_applications_new  a ,                                                                    "+
	        					" public.district b                                                                                                             "+
	        					" where a.old_vch_application_no  is not null  and a.application_district_id = b.districtid                                     "+
	        					"	            		 group by b.description, district_id order by b.description                                             ";
	        	 
	        	  
	        	  System.out.println("======wholesale detail====="+action.getRadioS()+""+sql);  
	        	  
	        	  }
	        	  
	          }

		       
			pstmt = con.prepareStatement(sql);
		
			
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("wholesale detail");
			
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 10000);
			worksheet.setColumnWidth(2, 3000);
			worksheet.setColumnWidth(3, 3000);
			worksheet.setColumnWidth(4, 3000);
			worksheet.setColumnWidth(5, 3000);
			
			
		    XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("Wholesale AND BondApproval "+ type);
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
			cellhead3.setCellValue("FL2");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("CL2.");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("FL2B.");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("FL2D.");
			cellhead6.setCellStyle(cellStyle);
			
		

		while (rs.next()) 
			
			{
				total_no_FL2= total_no_FL2 + rs.getLong("no_FL2");
				total_no_CL2 = total_no_CL2 + rs.getLong("no_CL2");
				total_no_FL2B = total_no_FL2B + rs.getLong("no_FL2B");
				total_no_FL2D = total_no_FL2D+ rs.getLong("no_FL2D");
			   
				k++;
				
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("no_FL2"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("no_CL2"));
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("no_FL2B"));
				
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("no_FL2D"));
				
			

					
}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+"//ExciseUp//MIS//Excel//"+n+"_"+"WholesaleANDBondApproval.xlsx");
			action.setExlname(n+"_"+"WholesaleANDBondApproval");
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			
			XSSFCell cellA1 = row1.createCell((int) 0);			
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);
			
			XSSFCell cellA2 = row1.createCell((int) 1);			
			cellA2.setCellValue("Total");
			cellA2.setCellStyle(cellStyle);
			
			XSSFCell cellA3 = row1.createCell((int) 2);			
			cellA3.setCellValue(total_no_FL2);
			cellA3.setCellStyle(cellStyle);
			
			XSSFCell cellA4 = row1.createCell((int) 3);			
			cellA4.setCellValue(total_no_CL2);
			cellA4.setCellStyle(cellStyle);
			
			XSSFCell cellA5 = row1.createCell((int) 4);			
			cellA5.setCellValue(total_no_FL2B);
			cellA5.setCellStyle(cellStyle);
			
			XSSFCell cellA6 = row1.createCell((int) 5);	
			cellA6.setCellValue(total_no_FL2D);
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
