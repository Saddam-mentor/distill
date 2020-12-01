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
import com.mentor.action.Dispatchfrom_CBWto_wholesale_Action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;



public class Dispatchfrom_CBWto_wholesale_impl {
	
	


//======================================generate@ excel=========================================//




       public void write_excel(Dispatchfrom_CBWto_wholesale_Action act) {
    	  
    	   PreparedStatement pstmt = null;
           ResultSet rs = null;
           Connection con = null;
    	   try {

	


	  String sql  =  /*"  select (select vch_firm_name from custom_bonds.custom_bonds_master     "+                           
		         " where int_id=int_import_id) as CBW_farm,x.*,brand_name,(select vch_firm_name   "+                  
				" from custom_bonds.mst_regis_importing_unit where int_id=custom_id) as   "+                          
				" import_unit,(select vch_bond_address from custom_bonds.custom_bonds_master                        "+
				" where int_id=int_import_id) as CBW from ( select int_fl2d_id,int_brand_id,int_pack_id,int_quantity,"+
				" sum(int_planned_bottles) as bottle,sum(int_boxes) as box,custom_id,int_import_id,      "+            
				"  (SELECT description from district where districtid=b.district_id)as district from fl2d.mst_stock_receive_20_21 a,bwfl_license.import_permit_20_21 b            "+                
				" where b.permit_nmbr=a.permit_no  and gatepass_date is not null and int_fl2d_id='"+act.getFl2DId()+"' group by    "+                       
				" int_fl2d_id,int_brand_id,int_pack_id,int_quantity,custom_id,int_import_id,b.district_id)x,     "+                 
				" distillery.brand_registration_20_21 br where x.int_brand_id=br.brand_id  "+
				" order by brand_name  "; */      
	
	
	
	  
				/*   "select (select vch_firm_name from custom_bonds.custom_bonds_master              "+                                                
			      "  where int_id=int_import_id) as CBW_farm,((x.bottle*x.int_quantity)/1000) as bl,(select replace(concat(vch_applicant_name,vch_licence_no,   "+                  
			      " (select description from district where districtid=core_district_id)),'LICENCE-','-  ') as FL2d        "+                         
			      " from licence.fl2_2b_2d_20_21 where vch_license_type='FL2D' and int_app_id=x.int_fl2d_id) as fl2d,    "+                           
			      " (SELECT description from district where districtid=x.district_id)as district,x.*,brand_name,    "+                                
			      " (select vch_firm_name from custom_bonds.mst_regis_importing_unit where int_id=custom_id) as import_unit,(select vch_bond_address  "+
			      " from custom_bonds.custom_bonds_master where int_id=int_import_id) as CBW from         "+                                          
			      "  ( select int_fl2d_id,int_brand_id,int_pack_id,int_quantity,b.district_id,sum(int_planned_bottles)     "+                          
			      " as bottle,b.duty,  sum(int_boxes) as box,custom_id,int_import_id  from fl2d.mst_stock_receive_20_21 a,   "+                               
			      " bwfl_license.import_permit_20_21 b where b.permit_nmbr=a.permit_no  and gatepass_date is not null   "+
			      " and gatepass_date between '"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'" +
		      	  " and int_fl2d_id='"+act.getFl2DId()+"' group by  "+
			      "  int_fl2d_id,int_brand_id,int_pack_id,int_quantity,custom_id,int_import_id,b.district_id,b.duty)x,distillery.brand_registration_20_21 br   "+
			    "  where x.int_brand_id=br.brand_id order by fl2d,brand_name ";*/
	  
	  
	  
	  
	           " select (select vch_firm_name from custom_bonds.custom_bonds_master        "+                                             
			    "  where int_id=int_import_id) as CBW_farm,((x.bottle*x.int_quantity)/1000) as bl,(select replace(concat(vch_applicant_name,vch_licence_no,  "+                  
			    "(select description from district where districtid=core_district_id)),'LICENCE-','-  ') as FL2d      "+                    
		  	    "from licence.fl2_2b_2d_20_21 where vch_license_type='FL2D' and int_app_id=x.int_fl2d_id) as fl2d,   "+                           
			   "(SELECT description from district where districtid=x.district_id)as district,x.*,brand_name,      "+                             
			     "(select vch_firm_name from custom_bonds.mst_regis_importing_unit where int_id=custom_id) as import_unit,(select vch_bond_address "+ 
			    "from custom_bonds.custom_bonds_master where int_id=int_import_id) as CBW from         "+                                 
			    "( select int_fl2d_id,int_brand_id,int_pack_id,int_quantity,b.district_id,sum(int_planned_bottles)     "+                         
			   "as bottle,pckg.duty,  sum(int_boxes) as box,custom_id,int_import_id  from fl2d.mst_stock_receive_20_21 a,      "+                           
			  "bwfl_license.import_permit_20_21 b, distillery.packaging_details_20_21 pckg where b.permit_nmbr=a.permit_no  and a.int_pack_id = pckg.package_id and gatepass_date is not null  "+ 
			   "and gatepass_date between  '"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'" +
			    " and int_fl2d_id='"+act.getFl2DId()+"' group by  "+
			    "int_fl2d_id,int_brand_id,int_pack_id,int_quantity,custom_id,int_import_id,b.district_id,pckg.duty)x,distillery.brand_registration_20_21 br "+ 
			     " where x.int_brand_id=br.brand_id  order by fl2d,brand_name" ;
				  

	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
			System.out.println("query=="+sql);
			
	                String relativePath = Constants.JBOSS_SERVER_PATH
	                  + Constants.JBOSS_LINX_PATH;
	                  FileOutputStream fileOut = null;
                    
                       boolean flag = false;
	                long k = 0;
	               String date = "";
	               
         	   
		          con = ConnectionToDataBase.getConnection();
		    pstmt = con.prepareStatement(sql);
		// System.out.println("==SQL=11111=" + sql);
		rs = pstmt.executeQuery();

		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet worksheet = workbook.createSheet("Dispatch From CBW To Wholesale");
		worksheet.setColumnWidth(0, 3000);
		worksheet.setColumnWidth(1, 4000);
		worksheet.setColumnWidth(2, 8000);
		worksheet.setColumnWidth(3, 8000);
		worksheet.setColumnWidth(4, 10000);
		worksheet.setColumnWidth(5, 12000);
		worksheet.setColumnWidth(6, 4000);
		worksheet.setColumnWidth(7, 4000);
		worksheet.setColumnWidth(8, 4000);

		XSSFRow rowhead0 = worksheet.createRow((int) 0);
		XSSFCell cellhead0 = rowhead0.createCell((int) 0);
		//+ Utility.convertUtilDateToSQLDate(act.getTodate()));
		rowhead0.setHeight((short) 700);cellhead0.setCellValue("Reports On Dispatch From CBW To Wholesale");
		//	+ " " + Utility.convertUtilDateToSQLDate(act.getFromdate())
			//	+ " " + " To " + " "
				
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
		cellhead2.setCellValue("District");
		cellhead2.setCellStyle(cellStyle);
		
		
		
		XSSFCell cellhead5 = rowhead.createCell((int) 2);
		cellhead5.setCellValue("FL2D");
		cellhead5.setCellStyle(cellStyle);
		
		XSSFCell cellhead7 = rowhead.createCell((int) 3);
		cellhead7.setCellValue("CBW_farm");
		cellhead7.setCellStyle(cellStyle);
		
		XSSFCell cellhead9 = rowhead.createCell((int) 4);
		cellhead9.setCellValue("CBW Address");
		cellhead9.setCellStyle(cellStyle);
		
		XSSFCell cellhead10 = rowhead.createCell((int) 5);
		cellhead10.setCellValue("Brand name");
		cellhead10.setCellStyle(cellStyle);
		
		XSSFCell cellhead11 = rowhead.createCell((int) 6);
		cellhead11.setCellValue("Import Unit");
		cellhead11.setCellStyle(cellStyle);
		
		XSSFCell cellhead12 = rowhead.createCell((int) 7);
		cellhead12.setCellValue("Bottle");
		cellhead12.setCellStyle(cellStyle);
		
		XSSFCell cellhead13 = rowhead.createCell((int) 8);
		cellhead13.setCellValue("BL");
		cellhead13.setCellStyle(cellStyle);
		
		
		XSSFCell cellhead14 = rowhead.createCell((int) 9);
		cellhead14.setCellValue("Duty");
		cellhead14.setCellStyle(cellStyle);
		

		XSSFCell cellhead15 = rowhead.createCell((int) 10);
		cellhead15.setCellValue("Quantity");
		cellhead15.setCellStyle(cellStyle);
		

		XSSFCell cellhead16 = rowhead.createCell((int) 11);
		cellhead16.setCellValue("Box");
		cellhead16.setCellStyle(cellStyle);
		
		
		

		
		int i = 0;
		
		while (rs.next()) {
			
			

			k++;
			XSSFRow row1 = worksheet.createRow((int) k);
			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue(k-1);
			
			XSSFCell cellB1 = row1.createCell((int) 1);
			cellB1.setCellValue(rs.getString("district"));
			
			XSSFCell cellC1 = row1.createCell((int) 2);
			cellC1.setCellValue(rs.getString("FL2d"));
			
			XSSFCell cellD1 = row1.createCell((int) 3);
			cellD1.setCellValue(rs.getString("CBW_farm"));
			
			XSSFCell cellE1 = row1.createCell((int) 4);
			cellE1.setCellValue(rs.getString("cbw"));
			
			
			XSSFCell cellF1= row1.createCell((int) 5);
			cellF1.setCellValue(rs.getString("brand_name"));
			
			

			XSSFCell cellG1=row1.createCell((int) 6);
			cellG1.setCellValue(rs.getString("import_unit"));
			
			
			
			XSSFCell cellH1=row1.createCell((int) 7);
			cellH1.setCellValue(rs.getString("bottle"));
			
			XSSFCell cellI1=row1.createCell((int) 8);
			cellI1.setCellValue(rs.getString("bl"));
			
			XSSFCell cellJ1=row1.createCell((int) 9);
			cellJ1.setCellValue(rs.getString("duty"));
	
			XSSFCell cellK1=row1.createCell((int) 10);
			cellK1.setCellValue(rs.getString("int_quantity"));
			
			

			XSSFCell cellL1=row1.createCell((int) 11);
			cellL1.setCellValue(rs.getString("box"));
			
			
	
			
			

		       }
		         Random rand = new Random();
		        int n = rand.nextInt(550) + 1;
		 fileOut = new FileOutputStream(relativePath + "/ExciseUp/WholeSale/excel/" + n + "DispatchfromCBWTOWholesaleReport.xls");
						
				 act.setExlname(n + "DispatchfromCBWTOWholesaleReport");

		
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
	        
         }
          




         ///=============================FL2DList=====================================


public ArrayList getFL2DList(Dispatchfrom_CBWto_wholesale_Action act) {

	ArrayList list = new ArrayList();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	SelectItem item = new SelectItem();
	item.setLabel("--select--");
	item.setValue(0);
	list.add(item);

	try {
		String query = " SELECT DISTINCT a.int_app_id, " +
				" concat(concat(a.vch_firm_name,' - '),b.description) as vch_firm_name " +
				" FROM licence.fl2_2b_2d_20_21 a LEFT OUTER JOIN public.district b " +
				" ON COALESCE(a.core_district_id,0)=b.districtid " +
				" WHERE a.vch_license_type='FL2D' and  b.districtid = "+act.getDistrict_id()+"" +
				" ORDER BY vch_firm_name "; 

		conn = ConnectionToDataBase.getConnection();
		pstmt = conn.prepareStatement(query);
		// pstmt.setInt(1,id);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			item = new SelectItem();

			item.setValue(rs.getString("int_app_id"));
			item.setLabel(rs.getString("vch_firm_name"));

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










//================================================district===================================================



public ArrayList distList(Dispatchfrom_CBWto_wholesale_Action act) {

	ArrayList list = new ArrayList();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	SelectItem item = new SelectItem();
	item.setLabel("--select--");
	item.setValue("");
	list.add(item);

	try {
		String query =   " select distinct m.district_id,m.district from (select (select vch_firm_name from custom_bonds.custom_bonds_master where int_id=int_import_id) as CBW_farm,(select replace(concat(vch_applicant_name,vch_licence_no,"+
				       "(select description from district where districtid=core_district_id)),'LICENCE-','-  ') as"+
				        "FL2d  from licence.fl2_2b_2d_20_21 where vch_license_type='FL2D' and int_app_id=x.int_fl2d_id) as fl2d,x.*,brand_name,"+
				        "(SELECT description from district where districtid=x.district_id)as district,(select vch_firm_name from custom_bonds.mst_regis_importing_unit "+
						"where int_id=custom_id) as import_unit,(select vch_bond_address from custom_bonds.custom_bonds_master where int_id=int_import_id)"+
				        "as CBW from ( select int_fl2d_id,int_brand_id,int_pack_id,int_quantity,b.district_id,sum(int_planned_bottles)"+
					"as bottle,sum(int_boxes) as box,custom_id,int_import_id  from fl2d.mst_stock_receive_20_21 a,"+
					"bwfl_license.import_permit_20_21 b where b.permit_nmbr=a.permit_no  and gatepass_date is not null group by int_fl2d_id,int_brand_id,int_pack_id,int_quantity,custom_id,"+
						"int_import_id,b.district_id)x,distillery.brand_registration_20_21 br where x.int_brand_id=br.brand_id order by fl2d,brand_name) m";
		
;

                  System.out.println("dist list=="+query);
		conn = ConnectionToDataBase.getConnection();
		pstmt = conn.prepareStatement(query);
		// pstmt.setInt(1,id);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			item = new SelectItem();

			item.setValue(rs.getString("district_id"));
			item.setLabel(rs.getString("district"));

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





//===================================================================data===================================



public void Data_excel(Dispatchfrom_CBWto_wholesale_Action act) {
	  
	   PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection con = null;
	   try {




String sql  =  /*"  select (select vch_firm_name from custom_bonds.custom_bonds_master     "+                           
	         " where int_id=int_import_id) as CBW_farm,x.*,brand_name,(select vch_firm_name   "+                  
			" from custom_bonds.mst_regis_importing_unit where int_id=custom_id) as   "+                          
			" import_unit,(select vch_bond_address from custom_bonds.custom_bonds_master                        "+
			" where int_id=int_import_id) as CBW from ( select int_fl2d_id,int_brand_id,int_pack_id,int_quantity,"+
			" sum(int_planned_bottles) as bottle,sum(int_boxes) as box,custom_id,int_import_id,      "+            
			"  (SELECT description from district where districtid=b.district_id)as district from fl2d.mst_stock_receive_20_21 a,bwfl_license.import_permit_20_21 b            "+                
			" where b.permit_nmbr=a.permit_no  and gatepass_date is not null and int_fl2d_id='"+act.getFl2DId()+"' group by    "+                       
			" int_fl2d_id,int_brand_id,int_pack_id,int_quantity,custom_id,int_import_id,b.district_id)x,     "+                 
			" distillery.brand_registration_20_21 br where x.int_brand_id=br.brand_id  "+
			" order by brand_name  "; */      




			/*   "select (select vch_firm_name from custom_bonds.custom_bonds_master              "+                                                
		      "  where int_id=int_import_id) as CBW_farm,((x.bottle*x.int_quantity)/1000) as bl,(select replace(concat(vch_applicant_name,vch_licence_no,   "+                  
		      " (select description from district where districtid=core_district_id)),'LICENCE-','-  ') as FL2d        "+                         
		      " from licence.fl2_2b_2d_20_21 where vch_license_type='FL2D' and int_app_id=x.int_fl2d_id) as fl2d,    "+                           
		      " (SELECT description from district where districtid=x.district_id)as district,x.*,brand_name,    "+                                
		      " (select vch_firm_name from custom_bonds.mst_regis_importing_unit where int_id=custom_id) as import_unit,(select vch_bond_address  "+
		      " from custom_bonds.custom_bonds_master where int_id=int_import_id) as CBW from         "+                                          
		      "  ( select int_fl2d_id,int_brand_id,int_pack_id,int_quantity,b.district_id,sum(int_planned_bottles)     "+                          
		      " as bottle,b.duty,  sum(int_boxes) as box,custom_id,int_import_id  from fl2d.mst_stock_receive_20_21 a,   "+                               
		      " bwfl_license.import_permit_20_21 b where b.permit_nmbr=a.permit_no  and gatepass_date is not null   "+
		      " and gatepass_date between '"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'" +
	      	  " and int_fl2d_id='"+act.getFl2DId()+"' group by  "+
		      "  int_fl2d_id,int_brand_id,int_pack_id,int_quantity,custom_id,int_import_id,b.district_id,b.duty)x,distillery.brand_registration_20_21 br   "+
		    "  where x.int_brand_id=br.brand_id order by fl2d,brand_name ";*/




        " select (select vch_firm_name from custom_bonds.custom_bonds_master        "+                                             
		    "  where int_id=int_import_id) as CBW_farm,((x.bottle*x.int_quantity)/1000) as bl,(select replace(concat(vch_applicant_name,vch_licence_no,  "+                  
		    "(select description from district where districtid=core_district_id)),'LICENCE-','-  ') as FL2d      "+                    
	  	    "from licence.fl2_2b_2d_20_21 where vch_license_type='FL2D' and int_app_id=x.int_fl2d_id) as fl2d,   "+                           
		   "(SELECT description from district where districtid=x.district_id)as district,x.*,brand_name,      "+                             
		     "(select vch_firm_name from custom_bonds.mst_regis_importing_unit where int_id=custom_id) as import_unit,(select vch_bond_address "+ 
		    "from custom_bonds.custom_bonds_master where int_id=int_import_id) as CBW from         "+                                 
		    "( select int_fl2d_id,int_brand_id,int_pack_id,int_quantity,b.district_id,sum(int_planned_bottles)     "+                         
		   "as bottle,pckg.duty,  sum(int_boxes) as box,custom_id,int_import_id  from fl2d.mst_stock_receive_20_21 a,      "+                           
		  "bwfl_license.import_permit_20_21 b, distillery.packaging_details_20_21 pckg where b.permit_nmbr=a.permit_no  and a.int_pack_id = pckg.package_id and gatepass_date is not null  "+ 
		   "and gatepass_date between  '"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'" +
		    " group by  "+
		    "int_fl2d_id,int_brand_id,int_pack_id,int_quantity,custom_id,int_import_id,b.district_id,pckg.duty)x,distillery.brand_registration_20_21 br "+ 
		     " where x.int_brand_id=br.brand_id  order by fl2d,brand_name" ;
			  













		System.out.println("else=="+sql);
		
             String relativePath = Constants.JBOSS_SERVER_PATH
               + Constants.JBOSS_LINX_PATH;
               FileOutputStream fileOut = null;
             
                boolean flag = false;
             long k = 0;
            String date = "";
            
  	   
	          con = ConnectionToDataBase.getConnection();
	    pstmt = con.prepareStatement(sql);
	// System.out.println("==SQL=11111=" + sql);
	rs = pstmt.executeQuery();

	XSSFWorkbook workbook = new XSSFWorkbook();
	
	XSSFSheet worksheet = workbook.createSheet("Dispatch From CBW To Wholesale");
	worksheet.setColumnWidth(0, 3000);
	worksheet.setColumnWidth(1, 4000);
	worksheet.setColumnWidth(2, 8000);
	worksheet.setColumnWidth(3, 8000);
	worksheet.setColumnWidth(4, 10000);
	worksheet.setColumnWidth(5, 12000);
	worksheet.setColumnWidth(6, 4000);
	worksheet.setColumnWidth(7, 4000);
	worksheet.setColumnWidth(8, 4000);

	XSSFRow rowhead0 = worksheet.createRow((int) 0);
	XSSFCell cellhead0 = rowhead0.createCell((int) 0);
	//+ Utility.convertUtilDateToSQLDate(act.getTodate()));
	rowhead0.setHeight((short) 700);cellhead0.setCellValue("Reports On Dispatch From CBW To Wholesale");
	//	+ " " + Utility.convertUtilDateToSQLDate(act.getFromdate())
		//	+ " " + " To " + " "
			
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
	cellhead2.setCellValue("District");
	cellhead2.setCellStyle(cellStyle);
	
	
	
	XSSFCell cellhead5 = rowhead.createCell((int) 2);
	cellhead5.setCellValue("FL2D");
	cellhead5.setCellStyle(cellStyle);
	
	XSSFCell cellhead7 = rowhead.createCell((int) 3);
	cellhead7.setCellValue("CBW_farm");
	cellhead7.setCellStyle(cellStyle);
	
	XSSFCell cellhead9 = rowhead.createCell((int) 4);
	cellhead9.setCellValue("CBW Address");
	cellhead9.setCellStyle(cellStyle);
	
	XSSFCell cellhead10 = rowhead.createCell((int) 5);
	cellhead10.setCellValue("Brand name");
	cellhead10.setCellStyle(cellStyle);
	
	XSSFCell cellhead11 = rowhead.createCell((int) 6);
	cellhead11.setCellValue("Import Unit");
	cellhead11.setCellStyle(cellStyle);
	
	XSSFCell cellhead12 = rowhead.createCell((int) 7);
	cellhead12.setCellValue("Bottle");
	cellhead12.setCellStyle(cellStyle);
	
	XSSFCell cellhead13 = rowhead.createCell((int) 8);
	cellhead13.setCellValue("BL");
	cellhead13.setCellStyle(cellStyle);
	
	
	XSSFCell cellhead14 = rowhead.createCell((int) 9);
	cellhead14.setCellValue("Duty");
	cellhead14.setCellStyle(cellStyle);
	

	XSSFCell cellhead15 = rowhead.createCell((int) 10);
	cellhead15.setCellValue("Quantity");
	cellhead15.setCellStyle(cellStyle);
	

	XSSFCell cellhead16 = rowhead.createCell((int) 11);
	cellhead16.setCellValue("Box");
	cellhead16.setCellStyle(cellStyle);
	
	
	

	
	int i = 0;
	
	while (rs.next()) {
		
		

		k++;
		XSSFRow row1 = worksheet.createRow((int) k);
		XSSFCell cellA1 = row1.createCell((int) 0);
		cellA1.setCellValue(k-1);
		
		XSSFCell cellB1 = row1.createCell((int) 1);
		cellB1.setCellValue(rs.getString("district"));
		
		XSSFCell cellC1 = row1.createCell((int) 2);
		cellC1.setCellValue(rs.getString("FL2d"));
		
		XSSFCell cellD1 = row1.createCell((int) 3);
		cellD1.setCellValue(rs.getString("CBW_farm"));
		
		XSSFCell cellE1 = row1.createCell((int) 4);
		cellE1.setCellValue(rs.getString("cbw"));
		
		
		XSSFCell cellF1= row1.createCell((int) 5);
		cellF1.setCellValue(rs.getString("brand_name"));
		
		

		XSSFCell cellG1=row1.createCell((int) 6);
		cellG1.setCellValue(rs.getString("import_unit"));
		
		
		
		XSSFCell cellH1=row1.createCell((int) 7);
		cellH1.setCellValue(rs.getString("bottle"));
		
		XSSFCell cellI1=row1.createCell((int) 8);
		cellI1.setCellValue(rs.getString("bl"));
		
		XSSFCell cellJ1=row1.createCell((int) 9);
		cellJ1.setCellValue(rs.getString("duty"));

		XSSFCell cellK1=row1.createCell((int) 10);
		cellK1.setCellValue(rs.getString("int_quantity"));
		
		

		XSSFCell cellL1=row1.createCell((int) 11);
		cellL1.setCellValue(rs.getString("box"));
		
		

		
		

	       }
	         Random rand = new Random();
	        int n = rand.nextInt(550) + 1;
	 fileOut = new FileOutputStream(relativePath + "/ExciseUp/WholeSale/excel/" + n + "DispatchfromCBWTOWholesaleReport.xls");
					
			 act.setExlname(n + "DispatchfromCBWTOWholesaleReport");

	
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
     
}
}

   


//=================================================//








