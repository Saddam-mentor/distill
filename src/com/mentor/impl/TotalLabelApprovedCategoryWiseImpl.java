package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mentor.action.TotalLabelApprovedCategoryWiseAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;

public class TotalLabelApprovedCategoryWiseImpl {

	
	
	
	
	

	public void printReport(TotalLabelApprovedCategoryWiseAction action)
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		JasperPrint  jasperPrint=null;
		
		
		String type=null;
		
		
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
				
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
				
		
		
		String sql=" select substring(a.category,2)category,sum(b.fees)as total_fee, count(b.vch_approved) as total_no_label from  " +
				" (select  case  " +
				" when domain_name='Within UP' and lic_cat='CL' and unit_type not in ('DCSD','BCSD','WCSD') then '1Country Liquor' " +
				" when domain_name='Within UP' and lic_cat in ('FL','IMFL') and unit_type not in ('DCSD','BCSD','WCSD') then '2Foreign Liquor'   " +
				" when domain_name='Within UP' and lic_cat in ('Beer','LAB') and unit_type not in ('DCSD','BCSD','WCSD') then '3Beer'   " +
				" when domain_name='Within UP' and lic_cat='Wine' and unit_type not in ('DCSD','BCSD','WCSD') then '4Wine'  " +
				" when domain_name!='Within UP' and unit_type not in ('DCSD','BCSD','WCSD') then '5Export' " +
				" when unit_type in ('DCSD','BCSD','WCSD') then '6CSD' end as category,app_id  " +
				" from brandlabel.brand_label_applications where vch_approved='APPROVED') a left outer join  	 " +
				" brandlabel.brand_label_application_details b on a.app_id=b.app_id and b.vch_approved='A' group by a.category  " +
				" order by a.category  ";


System.out.println("----------  qury  ----------11"+sql);

		try{
			conn=ConnectionToDataBase.getConnection();
			
			 pstmt=  conn.prepareStatement(sql)	 ;
		      
		//      pstmt.setDate(1,Utility.convertUtilDateToSQLDate(action.getFromDate()));
		//      pstmt.setDate(2,Utility.convertUtilDateToSQLDate(action.getToDate()));
		      rs=pstmt.executeQuery();
		      if(rs.next())
				{
		    	  
		    	  rs=pstmt.executeQuery();
					
					
					
					
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
			          HashMap map=new HashMap();
			          
			          
			          map.put("image", relativePath + File.separator);
		//	          map.put("bgimage", relativePath + File.separator);
	//		          map.put("fromDate", Utility.convertUtilDateToSQLDate(action.getFromDate()));
	//		          map.put("toDate", Utility.convertUtilDateToSQLDate(action.getToDate()));
			          map.put("type", type);
			        
			        Random rand = new Random();
			      	  int  n = rand.nextInt(250) + 1;
				          action.setPdfName("TotalLabelApprovalCategoryWise"+n+".pdf");
				 jasperPrint=JasperFillManager.fillReport(relativePath+File.separator+"TotalLabelApprovalCategoryWise.jasper", map, jrRs);
				 
				 JasperExportManager.exportReportToPdfFile(jasperPrint, relativePathpdf+File.separator+action.getPdfName());
				 generateExcel( n,relativePathpdf+File.separator+"TotalLabelApprovalCategoryWise"+n+".xlsx");
				 action.setExcelName("TotalLabelApprovalCategoryWise"+n+".xlsx");
						action.setPrintFlag(true);
						
						
				}else{
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"No Data Found!!", "No Data Found!!"));
					action.setPrintFlag(false);
				}
				
		
}
			catch(Exception e)
			{
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage() ,e.getMessage()));
			}finally{
				
				
				try{
					if(conn!=null)conn.close();
					if(pstmt!=null)pstmt.close();
					if(rs!=null)rs.close();
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	
	public boolean generateExcel(int n,String path)
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		boolean flag=false;
		String sql=" select substring(a.category,2)category,sum(b.fees)as total_fee, count(b.vch_approved) as total_no_label from  " +
				" (select  case  " +
				" when domain_name='Within UP' and lic_cat='CL' and unit_type not in ('DCSD','BCSD','WCSD') then '1Country Liquor' " +
				" when domain_name='Within UP' and lic_cat in ('FL','IMFL') and unit_type not in ('DCSD','BCSD','WCSD') then '2Foreign Liquor'   " +
				" when domain_name='Within UP' and lic_cat in ('Beer','LAB') and unit_type not in ('DCSD','BCSD','WCSD') then '3Beer'   " +
				" when domain_name='Within UP' and lic_cat='Wine' and unit_type not in ('DCSD','BCSD','WCSD') then '4Wine'  " +
				" when domain_name!='Within UP' and unit_type not in ('DCSD','BCSD','WCSD') then '5Export' " +
				" when unit_type in ('DCSD','BCSD','WCSD') then '6CSD' end as category,app_id  " +
				" from brandlabel.brand_label_applications where vch_approved='APPROVED') a left outer join  	 " +
				" brandlabel.brand_label_application_details b on a.app_id=b.app_id and b.vch_approved='A' group by a.category  " +
				" order by a.category ";
System.out.println("sql="+sql);
		try{
			conn=ConnectionToDataBase.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("Excel Sheet");
			sheet.setColumnWidth(0, 9000);
			sheet.setColumnWidth(1, 9000);
			sheet.setColumnWidth(2, 9000);
			
			XSSFRow rowhead = sheet.createRow((short) 0);
			
			rowhead.createCell((short) 0).setCellValue("Category"); 
			rowhead.createCell((short) 1).setCellValue("Total No Of Label");
			rowhead.createCell((short) 2).setCellValue("Fee Paid");
			int index = 1;
			while(rs.next())
			{
				System.out.println("-----");
				XSSFRow row = sheet.createRow((short) index);
				row.createCell((short) 0).setCellValue(rs.getString("category")); 
	            row.createCell((short) 1).setCellValue(rs.getString("total_no_label")+"");
				row.createCell((short) 2).setCellValue(rs.getString("total_fee")+"");
				
				
				index++;
			}
			 FileOutputStream fileOut = new FileOutputStream(path);
				
				wb.write(fileOut);
				fileOut.close();
				System.out.println("Data is saved in excel file....");
				rs.close();
			
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			try{
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return flag;
		
	}
	
	
	
	
	
	
	
	
	
	
	



}
