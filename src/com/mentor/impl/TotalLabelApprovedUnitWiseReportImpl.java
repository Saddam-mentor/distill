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

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.mentor.action.Fl41OilCompanyReportAction;
import com.mentor.action.TotalLabelApprovedUnitWiseReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class TotalLabelApprovedUnitWiseReportImpl {
	
	
	
	
	

	public void printReport(TotalLabelApprovedUnitWiseReportAction action)
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		JasperPrint  jasperPrint=null;
		
		
		String type=null;
		
		
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
				
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
				
		
		
		String sql=
				
			"	select a.unit_name, sum(b.fees)as total_fee, count(b.vch_approved) as total_no_label, "+
			"	CASE WHEN a.unit_type='D' THEN 'Distillery' "+
			"	WHEN a.unit_type='B' THEN 'Brewery' "+
			"	WHEN a.unit_type='IU' THEN 'Importing Unit'  "+
			"	WHEN a.unit_type='DCSD' THEN 'Distillery For CSD' "+
			"	WHEN a.unit_type='BCSD' THEN 'Brewery For CSD'  "+
			"	WHEN a.unit_type='WCSD' THEN 'Winery For CSD' "+
			"	WHEN a.unit_type='BUCSD' THEN 'Bottling Unit For CSD' "+
			"	else a.unit_type end as unit_type_name  "+
			"	FROM brandlabel.brand_label_applications a  "+
			"	left outer join  "+
			"	brandlabel.brand_label_application_details b on  "+
			"	a.app_id=b.app_id and b.vch_approved='A' "+
			"	where a.vch_approved='APPROVED'  "+
			"	group by  a.unit_name, unit_type_name   "+
			"	order by unit_type_name , unit_name ";


System.out.println("----------  qury  ----------"+sql);

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
				          action.setPdfName("TotalLabelApprovalUnitWiseReport"+n+".pdf");
				 jasperPrint=JasperFillManager.fillReport(relativePath+File.separator+"TotalLabelApprovalUnitWiseReport.jasper", map, jrRs);
				 
				 JasperExportManager.exportReportToPdfFile(jasperPrint, relativePathpdf+File.separator+action.getPdfName());
				 generateExcel( n,relativePathpdf+File.separator+"TotalLabelApprovalUnitWiseReport"+n+".xlsx");
				 action.setExcelName("TotalLabelApprovalUnitWiseReport"+n+".xlsx");
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
		String sql=
				
				"	select a.unit_name, sum(b.fees)as total_fee, count(b.vch_approved) as total_no_label, "+
				"	CASE WHEN a.unit_type='D' THEN 'Distillery' "+
				"	WHEN a.unit_type='B' THEN 'Brewery' "+
				"	WHEN a.unit_type='IU' THEN 'Importing Unit'  "+
				"	WHEN a.unit_type='DCSD' THEN 'Distillery For CSD' "+
				"	WHEN a.unit_type='BCSD' THEN 'Brewery For CSD'  "+
				"	WHEN a.unit_type='WCSD' THEN 'Winery For CSD' "+
				"	WHEN a.unit_type='BUCSD' THEN 'Bottling Unit For CSD' "+
				"	else a.unit_type end as unit_type_name  "+
				"	FROM brandlabel.brand_label_applications a  "+
				"	left outer join  "+
				"	brandlabel.brand_label_application_details b on  "+
				"	a.app_id=b.app_id and b.vch_approved='A' "+
				"	where a.vch_approved='APPROVED'  "+
				"	group by  a.unit_name, unit_type_name   "+
				"	order by unit_type_name , unit_name ";

		try{
			conn=ConnectionToDataBase.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("Excel Sheet");
			sheet.setColumnWidth(1, 7500);
			
			XSSFRow rowhead = sheet.createRow((short) 0);
			
			rowhead.createCell((short) 0).setCellValue("Unit Type");
			rowhead.createCell((short) 1).setCellValue("Unit Name");
			rowhead.createCell((short) 2).setCellValue("Total No Of Label");
			rowhead.createCell((short) 3).setCellValue("Fee Paid");
			int index = 1;
			while(rs.next())
			{
				System.out.println("come come come");
				XSSFRow row = sheet.createRow((short) index);
				row.createCell((short) 0).setCellValue(rs.getString("unit_type_name"));
				row.createCell((short) 1).setCellValue(rs.getString("unit_name"));
	            row.createCell((short) 2).setCellValue(rs.getString("total_no_label")+"");
				row.createCell((short) 3).setCellValue(rs.getString("total_fee")+"");
				
				
				index++;
			}
			 FileOutputStream fileOut = new FileOutputStream(path);
				
				wb.write(fileOut);
				fileOut.close();
				System.out.println("Data is saved in excel file.");
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
