package com.mentor.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
import java.util.StringTokenizer;

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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.mentor.action.ValidateBarcodeAction;
import com.mentor.datatable.NewCodeGenerationDatatable;
import com.mentor.datatable.ValidateBarcodeDT;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class ValidateBarcodeImpl {
	

	//----------------------- Dist_Brewery_BWFL_FL2DList ----------------------
	
		public ArrayList getDist_Brewery_BWFL_FL2DList(ValidateBarcodeAction action) {
			ArrayList list = new ArrayList();
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			SelectItem item = new SelectItem();
			item.setLabel("--SELECT--");
			item.setValue("");
			list.add(item);
			String SQl =null;
			
			
			if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("CL") || action.getFl_Dist_Bwfl2A().equalsIgnoreCase("Dist_FL"))
			{

				SQl=	"SELECT int_app_id_f as app_id ,vch_undertaking_name as app_name  from dis_mst_pd1_pd2_lic where vch_verify_flag='V'  order by app_name ";
				       
			}
			
			
			
			
			else if( action.getRadio_CL_FL_Beer().equalsIgnoreCase("Beer") && action.getBeer_Brewrey_Bwfl2B().equalsIgnoreCase("Brewery"))
			{
				SQl=		
						" select vch_app_id_f as app_id, brewery_nm as app_name  FROM public.bre_mst_b1_lic where vch_verify_flag='V' order by app_name ";
			}
			else if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("FL") && action.getFl_Dist_Bwfl2A().equalsIgnoreCase("BWFL2A"))
			{
				SQl=" select int_id as app_id , concat(vch_firm_name,'-(' ,vch_firm_district_name,')')  as app_name, vch_firm_district_name as district from bwfl.registration_of_bwfl_lic_holder where vch_license_type='1' order by app_name ";
			}
			else if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("Beer") && action.getBeer_Brewrey_Bwfl2B().equalsIgnoreCase("BWFL2B"))
			{
				SQl=" select int_id as app_id , concat(vch_firm_name,'-(' ,vch_firm_district_name,')')  as app_name, vch_firm_district_name as district from bwfl.registration_of_bwfl_lic_holder where vch_license_type='2' order by app_name ";
			}
			
			
			
			else{
				
			}
			
			try {
				con = ConnectionToDataBase.getConnection();
				ps = con.prepareStatement(SQl);
				rs = ps.executeQuery();
				while (rs.next()) {
					item = new SelectItem();
					
					item.setLabel(rs.getString("app_name"));
					item.setValue(rs.getString("app_id"));
					list.add(item);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {

					if (con != null)
						con.close();
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
	
	
	
	
	
	
	
	
	
	
	
	
	//----------------------------------------------------------
	

	public void saveExcelData(ValidateBarcodeAction action) {
	
		String gatepass = "";
		int status = 0, flag = 1, excelcase = 0;
		String Dist_Or_Brewry=null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int id = getMaxId() + 1;
		FileInputStream fileInputStream = null;
		XSSFWorkbook workbook = null;
		try {
			String sql = "INSERT INTO licence.barcode_replace(id, dist_or_brewry_id, barcode, type, status, cr_date)VALUES (?, ?, ?, ?, ?, ?);";

			conn = ConnectionToDataBase.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);
			fileInputStream = new FileInputStream(action.getExcelFilepath());

			workbook = new XSSFWorkbook(fileInputStream);

			XSSFSheet worksheet = workbook.getSheet("Sheet1"); 
			String barcode = "";
			
			//brwid
			String id_from_barcode=null;
			
			String etin_No=null;
			String case_Code=null;
			String barcode_status = "";
			int i = 0, j = 0;
			do {

				/*String casecode = "";
				XSSFRow row1 = worksheet.getRow(j);
				 XSSFRow row2 = worksheet.getRow(j); //

		 	
				XSSFCell cellA1 = row1.getCell((short) 0);
				 XSSFCell cellA2 = row2.getCell((short) 1); //

				String cellval = getCellValue(cellA1);
				 String cellval2=getCellValue(cellA2); //

				*/	
				
				String casecode = "";
				XSSFRow row1 = worksheet.getRow(j);
			//	 XSSFRow row2 = worksheet.getRow(j); //

		 	
				XSSFCell cellA1 = row1.getCell((short) 0);
			//	 XSSFCell cellA2 = row2.getCell((short) 1); //

				String cellval = getCellValue(cellA1);
			//	 String cellval2=getCellValue(cellA2); //

					System.out.println("cell val ------------"+cellval);
					
				
				
				
				
				 
				if ((cellval == null)|| (cellval.length() == 0 )|| (cellval.equals("0.0")) ) {
			 
					break;
				} else {
					
						/*cellA1 = row1.getCell((short) 0);
						Dist_Or_Brewry = getCellValue(cellA1);
						
						cellA2=row2.getCell((short) 1);
						barcode=getCellValue(cellA2);
					
						id_from_barcode=barcode.substring(6,9);*/
					
					
					
					cellA1 = row1.getCell((short) 0);
					//	Dist_Or_Brewry = getCellValue(cellA1);
						
					//	cellA2=row2.getCell((short) 1);
					//	barcode=getCellValue(cellA2);
						
						barcode=getCellValue(cellA1);
					
						id_from_barcode=barcode.substring(6,9);
					
					
					
						
						case_Code=barcode.substring(29);
						etin_No=barcode.substring(2, 15);
						
					
					
					
						/*if(this.etin(casecode.trim().substring(2, 15), action)==true){
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" Casecode-"+casecode.trim()+" does not belongs to the brands for the selected gatepass!" ," Casecode-"+casecode.trim()+" does not belongs to the brands for the selected gatepass!"));	
					}else{*/
					
						//if(barcode.length()==39 && Dist_Or_Brewry.equalsIgnoreCase(id_from_barcode))
					
						
				//		System.out.println("--------- action.getDist_Bwfl_brwryId() ---------"+action.getDist_Bwfl_brwryId() +"bar code id "+id_from_barcode);
						
						if(barcode.length()==39 && Integer.parseInt(action.getDist_Bwfl_brwryId())==Integer.parseInt(id_from_barcode))
						{
						
						pstmt.setInt(1, id);
						pstmt.setInt(2, Integer.parseInt(action.getDist_Bwfl_brwryId()));
						pstmt.setString(3, barcode);
						pstmt.setString(4, action.getRadio_CL_FL_Beer());
						pstmt.setString(5, get_Status(action,barcode).trim());
						
						pstmt.setDate(6, Utility.convertUtilDateToSQLDate(new Date()));
						
					
		  
					//	status = pstmt.executeUpdate();
						 pstmt.addBatch();
						
						excelcase++;
						action.setExcelCases(excelcase);
						i = 1;
					//	id++;
						
					} else {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" BarCode-"+barcode.trim()+" Does Not Belongs To Selected  List!" ,"  BarCode-"+barcode.trim()+" Does Not Belongs To  Selected  List !"));
							
							pstmt.setInt(1, id);
							pstmt.setInt(2, Integer.parseInt(action.getDist_Bwfl_brwryId()));
							pstmt.setString(3, barcode);
							pstmt.setString(4, action.getRadio_CL_FL_Beer());
							pstmt.setString(5, "Rejected (Code Invalid Not For This Distillery)");
							pstmt.setDate(6, Utility.convertUtilDateToSQLDate(new Date()));
							
							pstmt.addBatch();
							
							excelcase++;
							
					}
						
						
						
						
						
					//} 
				}
				

				j++;
			} while (i == 1);
			
	
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
						
		}

		
		if (flag == 1) {
			try{
				int st[]=pstmt.executeBatch();
				
				System.out.println("---st  ------"+st.length+" --excel---"+excelcase);
				
				
				if (st.length == excelcase && excelcase>0 ) { //	if (status > 0) {
			////	this.delete(action);
				
				conn.commit();
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,	"Upload successfully!!",	"Upload successfully!!"));
				action.setShowdata_Flag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(	null,	new FacesMessage(FacesMessage.SEVERITY_ERROR,	"Not Uploaded !!",	"Not Uploaded !!"));
			}
		
			}catch(Exception exx){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(exx.getMessage(), exx.getMessage())); 
				exx.printStackTrace();
				}
			finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			} else {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,	"Kindly enter the same gatepass number!!",	"Kindly enter the same gatepass number!!"));
				
						
		}

	}

	private String getCellValue(XSSFCell cell) {
		try {
		//	System.out	.println("get cell value type----------------------------------"+ cell.getCellType());
				
						
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:

				return cell.getStringCellValue().toString();

			case XSSFCell.CELL_TYPE_BOOLEAN:
				return Boolean.toString(cell.getBooleanCellValue());

			case XSSFCell.CELL_TYPE_NUMERIC:
				String val = Double.toString(cell.getNumericCellValue());
				val = val.substring(0, val.lastIndexOf("."));

				return val;

			case XSSFCell.CELL_TYPE_BLANK:

				return null;

			}

			return null;
		} catch (Exception e) {
			return null;
		}

	}

	public int getMaxId() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT max(id) as id FROM licence.barcode_replace ";
		int maxid = 0;
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				maxid = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return maxid;

	}
	
	
	//---------------------------------   --------------------------------------------------
	
	
	public String get_Status(ValidateBarcodeAction action,String caseode)
	{
		String status=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
		ResultSet rs=null;
		ResultSet rs2=null;
		String sql_unmp=null;
		String sql_publ=null;
		int save=0;
		int save_unmp=0;
		String gatepass_11=null;
		String gatepass_36=null;
		
		
		//System.out.println("-0------ case code check-------"+caseode);
		
		if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("CL"))
		{
	sql_publ=  " select distillery_id  FROM public.bottling_cl where gtin_no='"+caseode.trim().substring(2, 15)+"'    " +
			"    and case_no='"+Long.parseLong(caseode.trim().substring(29, caseode.trim().length()))+"'  " +
					" union " +
			" select distillery_id  FROM public.bottling_cl_08_07_18 where gtin_no='"+caseode.trim().substring(2, 15)+"'    " +
			"    and case_no='"+Long.parseLong(caseode.trim().substring(29, caseode.trim().length()))+"'  " ;		
		}
		
		else if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("FL") &&  action.getFl_Dist_Bwfl2A().equalsIgnoreCase("Dist_FL") )
		{
		
	 sql_publ= " select distillery_id  FROM public.bottling_fl3 where gtin_no=? and case_no=?  "+
			 "  union  "+
			 "   SELECT distillery_id  FROM public.bottling_fl3a where gtin_no=? and case_no=?  " +
			 " union all " +
			 " select distillery_id  FROM public.bottling_fl3_08_07_18 where gtin_no=? and case_no=?  "+
			 "  union  "+
			 "   SELECT distillery_id  FROM public.bottling_fl3a_08_07_18 where gtin_no=? and case_no=?  " ;
		}
		
		else if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("FL") &&  action.getFl_Dist_Bwfl2A().equalsIgnoreCase("BWFL2A") )
		{
		
	 sql_publ= " select gtin_no ,serial_no_start::text  FROM public.bottling_bwfl where gtin_no='"+caseode.substring(2, 15)+"'    " +
				"    and case_no='"+Long.parseLong(caseode.substring(29, caseode.length()))+"' " +
				" union " +
				" select fl11gatepass, fl36gatepass FROM bottling_unmapped.bwfl where etin='"+caseode.substring(2, 15)+"'    " +
				" and casecode='"+(caseode.substring(29, caseode.length()))+"'  ";		
		}
		
		
		else if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("Beer") &&  action.getBeer_Brewrey_Bwfl2B().equalsIgnoreCase("Brewery") )
		{
			 sql_publ= " select distillery_id  FROM public.bottling_fl3 where gtin_no=? and case_no=?  "+
					 "  union  "+
					 "   SELECT distillery_id  FROM public.bottling_fl3a where gtin_no=? and case_no=?  " +
					 " union all  " +
					 " select distillery_id  FROM public.bottling_fl3_08_07_18 where gtin_no=? and case_no=?  "+
					 "  union  "+
					 "   SELECT distillery_id  FROM public.bottling_fl3a_08_07_18 where gtin_no=? and case_no=?  " ;
			 
				}
		
		else if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("Beer") &&  action.getBeer_Brewrey_Bwfl2B().equalsIgnoreCase("BWFL2B") )
		{
			sql_publ= " select gtin_no ,serial_no_start::text  FROM public.bottling_bwfl where gtin_no='"+caseode.substring(2, 15)+"'    " +
					"    and case_no='"+Long.parseLong(caseode.substring(29, caseode.length()))+"' " +
					"union " +
					" select fl11gatepass, fl36gatepass FROM bottling_unmapped.bwfl where etin='"+caseode.substring(2, 15)+"'    " +
					" and casecode='"+(caseode.substring(29, caseode.length()))+"'  ";		
			
		}
		
		//System.out.println("------ sql_publ  -----"+sql_publ);
		
		try{
			conn=ConnectionToDataBase.getConnection3();
			pstmt=conn.prepareStatement(sql_publ);
			
			 if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("Beer") &&  action.getBeer_Brewrey_Bwfl2B().equalsIgnoreCase("Brewery") 
					  || action.getRadio_CL_FL_Beer().equalsIgnoreCase("FL") &&  action.getFl_Dist_Bwfl2A().equalsIgnoreCase("Dist_FL") )
			{
			
			pstmt.setString(1, caseode.trim().substring(2, 15));
			pstmt.setLong(2, Long.parseLong(caseode.trim().substring(29, caseode.trim().length())));
			pstmt.setString(3, caseode.trim().substring(2, 15));
			pstmt.setLong(4, Long.parseLong(caseode.trim().substring(29, caseode.trim().length())));
			
			pstmt.setString(5, caseode.trim().substring(2, 15));
			pstmt.setLong(6, Long.parseLong(caseode.trim().substring(29, caseode.trim().length())));
			pstmt.setString(7, caseode.trim().substring(2, 15));
			pstmt.setLong(8, Long.parseLong(caseode.trim().substring(29, caseode.trim().length())));
			
			
			
			
			}else{
				
			}
		
			rs=pstmt.executeQuery();
			if(rs.next())
			{
				save=1;
				
				//System.out.println("------   1111111111111111111111----------"+save);
				
			}
			
			
			if(save>0)
			{
				
				if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("CL"))
				{
			sql_unmp= " select  fl36gatepass FROM bottling_unmapped.disliry_unmap_cl where etin='"+caseode.trim().substring(2, 15)+"'    " +
						" and casecode='"+(caseode.trim().substring(29, caseode.trim().length()))+"' " +
								" union " +
								" select  fl36gatepass FROM bottling_unmapped.disliry_unmap_cl_08_07_18 where etin='"+caseode.trim().substring(2, 15)+"'    " +
								" and casecode='"+(caseode.trim().substring(29, caseode.trim().length()))+"' " ;
					
				}
				
				else if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("FL") &&  action.getFl_Dist_Bwfl2A().equalsIgnoreCase("Dist_FL") )
				{
				
				sql_unmp=	" select fl11gatepass, fl36gatepass FROM bottling_unmapped.disliry_unmap_fl3 where etin=? and casecode=?  "+
							" union "+
							" select fl11gatepass, fl36gatepass FROM bottling_unmapped.disliry_unmap_fl3a where etin=? and casecode=? " +
							" union all " +
							" select fl11gatepass, fl36gatepass FROM bottling_unmapped.disliry_unmap_fl3_08_07_18 where etin=? and casecode=?  "+
							" union "+
							" select fl11gatepass, fl36gatepass FROM bottling_unmapped.disliry_unmap_fl3a_08_07_18 where etin=? and casecode=? " ;
								
				}
				
				
				else if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("FL") &&  action.getFl_Dist_Bwfl2A().equalsIgnoreCase("BWFL2A") )
				{
					sql_unmp= " select fl11gatepass, fl36gatepass FROM bottling_unmapped.bwfl where etin='"+caseode.substring(2, 15)+"'    " +
							" and casecode='"+(caseode.substring(29, caseode.length()))+"'  ";
				
					
				}
				

				else if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("Beer") &&  action.getBeer_Brewrey_Bwfl2B().equalsIgnoreCase("Brewery") )
				{
				sql_unmp=" select fl11gatepass, fl36gatepass FROM bottling_unmapped.brewary_unmap_fl3 where etin=? and casecode=?  "+
						" union "+
						" select fl11gatepass, fl36gatepass FROM bottling_unmapped.brewary_unmap_fl3a where etin=? and casecode=? " +
						" union all " +
						" select fl11gatepass, fl36gatepass FROM bottling_unmapped.brewary_unmap_fl3_08_07_18 where etin=? and casecode=?  "+
						" union "+
						" select fl11gatepass, fl36gatepass FROM bottling_unmapped.brewary_unmap_fl3a_08_07_18 where etin=? and casecode=? " ;
						 
				}
				
				else if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("Beer") &&  action.getBeer_Brewrey_Bwfl2B().equalsIgnoreCase("BWFL2B") )
				{
				
					sql_unmp= " select fl11gatepass, fl36gatepass FROM bottling_unmapped.bwfl where etin='"+caseode.substring(2, 15)+"'    " +
							" and casecode='"+(caseode.substring(29, caseode.length()))+"'  ";
				
				}
				
				
				
				
				pstmt2=conn.prepareStatement(sql_unmp);
				//System.out.println("------   2222222222222 ----------"+sql_unmp);	
				if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("Beer") &&  action.getBeer_Brewrey_Bwfl2B().equalsIgnoreCase("Brewery") 
						  || action.getRadio_CL_FL_Beer().equalsIgnoreCase("FL") &&  action.getFl_Dist_Bwfl2A().equalsIgnoreCase("Dist_FL") )
				{//	System.out.println("------   3333333333 ----------");
				pstmt2.setString(1, caseode.trim().substring(2, 15));
				pstmt2.setString(2, (caseode.trim().substring(29, caseode.trim().length())));
				pstmt2.setString(3, caseode.trim().substring(2, 15));
				pstmt2.setString(4, (caseode.trim().substring(29, caseode.trim().length())));
				
				pstmt2.setString(5, caseode.trim().substring(2, 15));
				pstmt2.setString(6, (caseode.trim().substring(29, caseode.trim().length())));
				pstmt2.setString(7, caseode.trim().substring(2, 15));
				pstmt2.setString(8, (caseode.trim().substring(29, caseode.trim().length())));
				
				
				
				}else
				{
					//System.out.println("------   4444444 ----------");
				}
				
				rs2=pstmt2.executeQuery();
				
				if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("CL"))
				{	//System.out.println("------   555555555 ----------");
					if(rs2.next())
					{//System.out.println("------   5666666666666 ----------");
						
						gatepass_36=rs2.getString("fl36gatepass");
						save_unmp=1;
					}
				}else
				{//System.out.println("------   77777777777777 ----------");
					if(rs2.next())
					{//System.out.println("------   8888888888888 ----------");
						gatepass_11=rs2.getString("fl11gatepass");
						gatepass_36=rs2.getString("fl36gatepass");
						save_unmp=1;
					}
				}
				
				
				
				/*if(rs2.next())
				{
					gatepass_11=rs2.getString("fl11gatepass");
					gatepass_36=rs2.getString("fl36gatepass");
					save_unmp=1;
				}*/
				
			}
			
			
			if(action.getRadio_CL_FL_Beer().equalsIgnoreCase("CL"))
			{
				//System.out.println("------   999999999999 ----------");
				 if(save>0 && save_unmp>0 &&  gatepass_36!=null  && !gatepass_36.equalsIgnoreCase("NA") )
					{
						status="Rejected -GatePass-36 Is Generated ("+gatepass_36+")";
					}
					 else  if(save>0 && save_unmp==0  )
					{
						status="Accepted";
					}
					else if(save>0 && save_unmp>0)
					{
						status="Accepted";	
					}else if(save==0)
					{
						status="Rejected- Code Not Found ";	
					}
					
				
			}else{ 
			
			//--------- Not CL ---------------
			System.out.println("------   10000000000 ----------"+save+"-------"+gatepass_11+"---------"+gatepass_36+"----"+save_unmp);
			 if(save>0 &&  gatepass_11!=null && gatepass_11.length()>0 && gatepass_36!=null && gatepass_36.length()>0 )
			{//System.out.println("------   aaaaaaa ----------");
				status="Rejected (GatePass-11 And GatePass-36 Is Generated)";
			}
			 else if(save>0 && gatepass_11!=null && gatepass_11.length()>0)
			 {//System.out.println("------   sssssssssss ----------");
				 status="Rejected (GatePass-11 Is Generated)"; 
			 }
		
			 else  if(save>0 && save_unmp==0  )
			{//System.out.println("------   dddddddddd ----------");
				status="Accepted";
			}
			 else  if(save>0 && save_unmp>0  )
				{//System.out.println("------   dddddddddd ----------");
					status="Accepted";
				}
				
			else if(save==0 )
			{//System.out.println("------   fffffffffffffffffff ----------");
				status="Rejected ( Code Not Found )";	
			}
			
			 
			 
			}// Not CL For else
			 
			 
			
			
			
		}
		
		catch(Exception e1)
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e1.getMessage(), e1.getMessage())); 
			e1.printStackTrace();
		}finally{
			
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(rs2!=null)rs2.close();
				if(pstmt2!=null)pstmt2.close();
				if(conn!=null)conn.close();
				
				
			}catch(Exception e)
			{FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage())); 
				e.printStackTrace();
			}
			
		}	
		return status;
		}
			
			
			
	//-------------------------- delete -------------------------
	
	public void delete(ValidateBarcodeAction action ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		 
		try {
			
			String query ="";

			
			conn = ConnectionToDataBase.getConnection();
			
		//    query = "	DELETE FROM licence.barcode_replace WHERE dist_or_brewry_id='"+Integer.parseInt(action.getDist_Bwfl_brwryId())+"'   " ;

			
			 query = "	DELETE FROM licence.barcode_replace " ;

			
		 
						pstmt = conn.prepareStatement(query);
					//	pstmt.setInt(1, Integer.parseInt(action.getDist_Bwfl_brwryId()));
						pstmt.executeUpdate();
		}catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage())); 
		}finally {
			try {
				if (conn != null)conn.close();
				if (pstmt != null)pstmt.close();
				if (rs != null)rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
			
	
	//-------------------------------------------- show data ----------------------
	
	public ArrayList getdatatabale(ValidateBarcodeAction action) {
		
		ArrayList list = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;


	
		
		String query =  "	SELECT id, type, cr_date, dist_or_brewry_id, "+
						"	array_to_string(array_agg(distinct barcode),'|') AS barcode "+
						"	FROM licence.barcode_replace WHERE dist_or_brewry_id = '"+Integer.parseInt(action.getDist_Bwfl_brwryId())+"'    "+
						"	GROUP BY id, type, cr_date, dist_or_brewry_id  order by id ";

				/*"	SELECT id, type,  cr_date,dist_or_brewry_id,  " +
				"	(select string_agg(b.barcode,'|') from licence.barcode_replace b where b.id=id and b.dist_or_brewry_id ='"+Integer.parseInt(action.getDist_Bwfl_brwryId())+"'  ) as barcode, " +
				"	type,  cr_date " +
				"	FROM licence.barcode_replace  " +
				"   where dist_or_brewry_id ='"+Integer.parseInt(action.getDist_Bwfl_brwryId())+"' " +
						" group by id ,type , cr_date,dist_or_brewry_id order by id ";*/
			
	//	System.out.println("---- show data -----"+query);
		
		
		try {
			list = new ArrayList();
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();
			int i = 1;
			while (rs.next()) {
				ValidateBarcodeDT dt=new ValidateBarcodeDT();
			//	dt.setSeq(i);
				dt.setUploadedExcelId(rs.getInt("id"));
				dt.setSeq(rs.getInt("id"));
				dt.setBarcode(rs.getString("barcode"));
				dt.setD_and_b_id(rs.getString("dist_or_brewry_id"));
			//	dt.setStatus(rs.getString("status"));
				dt.setType(rs.getString("type"));
				dt.setCr_date(rs.getDate("cr_date"));
				
				
				list.add(dt);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	//---------------------------------------------- -------------
	
	public boolean printReport(ValidateBarcodeAction ac ,int id) {

		System.out.println("printReport()");
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "pdf";
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		boolean printFlag = false;

		try {
			con = ConnectionToDataBase.getConnection();

			reportQuery = "SELECT id, dist_or_brewry_id, barcode, type, status FROM licence.barcode_replace" +
					"  where id= '"+id +"' ";
			
			pst = con.prepareStatement(reportQuery);
			rs = pst.executeQuery();

			if (rs.next()) {
				
				rs = pst.executeQuery();

				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "validate_barcode_report_New.jasper");
				
				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				
				ValidateBarcodeDT dt = new ValidateBarcodeDT();
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator + "validate_barcode_report" + n
								+ ".pdf");
			
			//	ac.setPrintFlag(true);
				dt.setPrintFlag(true);
				printFlag = true;
				ac.setPdfName("validate_barcode_report" + n + ".pdf");
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Report Generated !! ", "Report Generated !! "));

			} else {
				
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found"));
						
				ValidateBarcodeDT dt1 = new ValidateBarcodeDT();
				printFlag = false;
				ac.setPrintFlag(false);
				dt1.setPrintFlag(false);
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
	
	
	public ArrayList getDataTableData(ValidateBarcodeAction action)
	{
		ArrayList list=new ArrayList();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String date=null;
		
		String sql=
				
	" SELECT distinct a.id,  a.dist_or_brewry_id, a.type, a.status, a.cr_date ,a.finalize_flag  "+
	" ,(select count(b.id) from licence.barcode_replace b  where dist_or_brewry_id =a.dist_or_brewry_id and b.id=a.id and type=a.type and  status='Accepted' group by b.id,   b.type, b.status, b.cr_date) as count"+
  "	FROM licence.barcode_replace a where  status='Accepted' group by a.id,   a.type, a.status, a.cr_date,a.dist_or_brewry_id,a.finalize_flag ";


		
		try{
			conn=ConnectionToDataBase.getConnection();
		    pstmt=conn.prepareStatement(sql);
		    
		    rs=pstmt.executeQuery();
		    
		    while(rs.next())
		    {
		    	NewCodeGenerationDatatable dt=new NewCodeGenerationDatatable();
		    	
		    	if(rs.getDate("cr_date")!=null)
		    	{
		    	Date dat = Utility.convertSqlDateToUtilDate(rs
						.getDate("cr_date"));

				DateFormat formatter;

				formatter = new SimpleDateFormat("yyMMdd");
				date = formatter.format(dat);
		    	}
		    	dt.setDatelink(date);
		    	dt.setFinalizeFlag(rs.getString("finalize_flag"));
		    	dt.setDistillery_id(rs.getInt("dist_or_brewry_id"));
		    	dt.setBarCodeCount(rs.getInt("count"));
		    	dt.setDate(Utility.convertSqlDateToUtilDate(rs.getDate("cr_date")));
		    	dt.setStatus(rs.getString("status"));
		    	dt.setType(rs.getString("type"));
		    	dt.setUploadedId(rs.getInt("id"));
		    	list.add(dt);
		    }
		    
		    
			
		}catch(Exception e)
		{
			e.printStackTrace();
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
		
		
		return list;
	}
	
	
	
	
	
	
	
	
	public boolean finalizeData(NewCodeGenerationDatatable dt)
	{
		boolean flag=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt1=null;
		ResultSet rs=null;
		String oldBarcode=null;
		String etin="";
		String licenseType="";
		int count=0;
		int status=0;
		int packSize=0;
		String select=
				
				
				" SELECT id, dist_or_brewry_id, barcode, type, status, cr_date, update_barcode, finalize_flag,  "+
				" new_barcode, serial_no_start, serial_no_end "+
				" FROM licence.barcode_replace  where id=? and dist_or_brewry_id=? and type=? and status='Accepted' ";
		
		
		String update=
				
				
				"UPDATE licence.barcode_replace  "+
				" SET  finalize_flag='F',  "+
				" new_barcode=?, serial_no_start=?, serial_no_end=? ,bottling_date=?,pack_size=?,licence_type=?,gtin=?"+
				" WHERE  id=? and  dist_or_brewry_id=? and  barcode=? and  type=? and status=? and  cr_date=?  ";
		
		try{
			conn=ConnectionToDataBase.getConnection();
			conn.setAutoCommit(false);
			
		pstmt=conn.prepareStatement(select);
		pstmt.setInt(1, dt.getUploadedId());
		pstmt.setInt(2, dt.getDistillery_id());
		pstmt.setString(3,dt.getType());
		rs=pstmt.executeQuery();
		
		long serialStart=0;
		while(rs.next())
		{
			
		    oldBarcode=rs.getString("barcode");
		    etin=oldBarcode.substring(2, 15);
		    licenseType=etin.substring(2, 4);
		    packSize=getPackSize(etin);
		    String dat=oldBarcode.substring(17, 23);
		    SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
		    String license="";
		    
		    if(licenseType.equals("08"))
			{
		    	license="CL";
			} if(licenseType.equals("01"))
			{
		    	license="FL3";
			}if(licenseType.equals("02"))
			{
		    	license="FL3A";
			}if(licenseType.equals("03"))
			{
		    	license="FL2D";
			}if(licenseType.equals("04"))
			{
		    	license="BWFL2A";
			}if(licenseType.equals("05"))
			{
		    	license="BWFL2B";
			}if(licenseType.equals("06"))
			{
		    	license="BWFL2C";
			}if(licenseType.equals("07"))
			{
		    	license="BWFL2D";
			}
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		 Date bottledat=   sdf.parse(dat);
		    
		   
		    
			pstmt1=conn.prepareStatement(update);
			
			pstmt1.setLong(1,getcaseNoFl3a());
			
			
			if(licenseType.equals("08"))
			{
				//cl
				
				 
				  serialStart=getSerialclBottle(packSize);
				 
				pstmt1.setLong(2, serialStart);
				pstmt1.setLong(3, serialStart-1+packSize);
				
				
				
			}if(licenseType.equals("01"))
			{
				//fl3
				
				 
					  serialStart=getSerialFl3Bottle(packSize);
					 
				System.out.println("serialStart---"+serialStart);
				System.out.println("serialStartpackSize---"+serialStart+packSize);
				pstmt1.setLong(2, serialStart);
				pstmt1.setLong(3, serialStart-1+packSize);
			}if(licenseType.equals("02"))
			{
				//fl3a
			 
					  serialStart=getSerialFl3aBottle(packSize);
					 
				pstmt1.setLong(2, serialStart);
				pstmt1.setLong(3, serialStart-1+packSize);
			}
			
			pstmt1.setDate(4, Utility.convertUtilDateToSQLDate(bottledat));
			pstmt1.setInt(5, packSize);
			pstmt1.setString(6, license);
			pstmt1.setString(7, etin);
			pstmt1.setInt(8, dt.getUploadedId());
			pstmt1.setInt(9, dt.getDistillery_id());
			pstmt1.setString(10, oldBarcode);
			pstmt1.setString(11,dt.getType());
			pstmt1.setString(12, "Accepted");
			pstmt1.setDate(13, Utility.convertUtilDateToSQLDate(dt.getDate()));
			status+=pstmt1.executeUpdate();
			count++;
			
			
			
			
			
		}
		
		System.out.println("dt.getBarCodeCount()"+dt.getBarCodeCount() +"  count "+count+" status " +status);
		if(dt.getBarCodeCount()==count && dt.getBarCodeCount()==status)
		{
			try{
				
			
			
				flag=true;
			conn.commit();
			
				
			}
		catch(Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
			e.printStackTrace();
		}
		}else{
			
			try{
				flag=false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ACCEPTED CODE COUNT AND NEW CODE COUNT ARE NOT MATCH", "ACCEPTED CODE COUNT AND NEW CODE COUNT ARE NOT MATCH"));
				conn.rollback();
			}
		catch(Exception e)
		{
			flag=false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
			e.printStackTrace();
		}
			
		}
		
		
		
		
		
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			
			
			
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(pstmt1!=null)pstmt.close();
				if(conn!=null)conn.close();
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
		}
		return flag;
		
	}
	
	
	public synchronized long getcaseNoFl3a() {
		String sql = " select     nextval('public.fl3_3a_old_stock_case_code')";

		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		long serialNo = 0;
		long currseq = 0;

		try {
			conn = ConnectionToDataBase.getConnection3();

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				serialNo = rs.getLong(1);
				if (serialNo == 0) {
					serialNo = serialNo;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();

				if (pstmt != null)
					pstmt.close();

				if (conn != null)
					conn.close();

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage(), e.getMessage()));
				e.printStackTrace();
			}
		}

		return serialNo;
	}
	
	//Bottle code  
	
	
	public synchronized long getSerialclBottle(long noOfSequenc) {
		String sql = " select     nextval('public.cl_manual_bottle_code')";
		//String sqll = "ALTER SEQUENCE public.fl3_manual_bottle_code RESTART WITH ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		long serialNo = 0;
		long currseq = 0;

		try {
			conn = ConnectionToDataBase.getConnection3();

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				serialNo = rs.getLong(1);
				if (serialNo == 0) {
					serialNo = serialNo + 1;
				}
				

				pstmt1 = conn.prepareStatement("ALTER SEQUENCE public.cl_manual_bottle_code RESTART WITH "
						+ (noOfSequenc+serialNo+1));

			
				pstmt1.executeUpdate();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();

				if (pstmt != null)
					pstmt.close();

				if (conn != null)
					conn.close();

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage(), e.getMessage()));
				e.printStackTrace();
			}
		}

		return serialNo;
	}
	
	public synchronized long getSerialFl3Bottle(long noOfSequenc) {
		String sql = " select     nextval('public.fl3_manual_bottle_code')";
		//String sqll = "ALTER SEQUENCE public.fl3_manual_bottle_code RESTART WITH ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		long serialNo = 0;
		long currseq = 0;

		try {
			conn = ConnectionToDataBase.getConnection3();

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				serialNo = rs.getLong(1);
				if (serialNo == 0) {
					serialNo = serialNo + 1;
				}
				

				pstmt1 = conn.prepareStatement("ALTER SEQUENCE public.fl3_manual_bottle_code RESTART WITH "
						+ (noOfSequenc + serialNo+1));

			
				pstmt1.executeUpdate();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();

				if (pstmt != null)
					pstmt.close();

				if (conn != null)
					conn.close();

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage(), e.getMessage()));
				e.printStackTrace();
			}
		}

		return serialNo;
	}

	public synchronized long getSerialFl3aBottle(long noOfSequenc) {
		String sql = " select     nextval('public.fl3a_manual_bottle_code')";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		long serialNo = 0;
		long currseq = 0;

		try {
			conn = ConnectionToDataBase.getConnection3();

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				serialNo = rs.getLong(1);
				if (serialNo == 0) {
					serialNo = serialNo + 1;
				}
				  
				pstmt1 = conn
						.prepareStatement("ALTER SEQUENCE public.fl3a_manual_bottle_code RESTART WITH "
								+ (noOfSequenc + serialNo+1));

				
				pstmt1.executeUpdate();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();

				if (pstmt != null)
					pstmt.close();

				if (conn != null)
					conn.close();

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage(), e.getMessage()));
				e.printStackTrace();
			}
		}

		return serialNo;
	}

	
	public int getPackSize(String etin)
	{
		
		int size=0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select box_size from distillery.packaging_details a,distillery.box_size_details b "+
                   " where a.box_id=b.box_id and a.code_generate_through=?";
		
		try{
			conn=ConnectionToDataBase.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, etin);
			rs=pstmt.executeQuery();
			if(rs.next())
			{
				size=rs.getInt("box_size");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			try{
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
			}catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}
		
		return size;
	}
	
	
	
	public boolean write(NewCodeGenerationDatatable dt) {
		
		
		Connection conn=null;

		String fl3 = 
			"	select y.cr_date,y.pack_size  ,to_char(y.gs, 'fm000000000000')as bottlecode ,y.dist_or_brewry_id, y.dispatch_date, y.gtin, "+
			"	y.licence_type,y.case_no from(select x.cr_date, x.pack_size , GENERATE_SERIES(x.serial_no_start ,x.serial_no_end ) as gs , "+
			"	x.serial_no_start ,x.serial_no_end, x.licence_type, "+
			"	x.case_no,x.dispatch_date,x.dist_or_brewry_id,x.gtin "+
			"	from (SELECT cr_date,pack_size,dist_or_brewry_id  , bottling_date as dispatch_date ,serial_no_start, serial_no_end, "+
			"	licence_type, new_barcode as case_no ,gtin FROM licence.barcode_replace a "+
			"	where id=?  and cr_date=?   and dist_or_brewry_id=? and  status='Accepted' and type=?)x)y";


		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long start = 0;
		long end = 0;
		boolean flag = false;
		long k = 0;
		String noOfUnit = "";
		String date = null;
		String createdDate=null;

		try {
                  conn=ConnectionToDataBase.getConnection();
			
			
			

				pstmt = conn.prepareStatement(fl3);

				
				pstmt.setInt(1, dt.getUploadedId());
				pstmt.setDate(2, Utility.convertUtilDateToSQLDate(dt.getDate()));
				pstmt.setInt(3, dt.getDistillery_id());
				pstmt.setString(4, dt.getType());
				rs = pstmt.executeQuery();
				//System.out.println("excecute query fl3");

			

			

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			

			CellStyle unlockedCellStyle = workbook.createCellStyle();
			unlockedCellStyle.setLocked(false);

			
			worksheet.protectSheet("agristat");
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 8000);
			worksheet.setColumnWidth(2, 8000);
			worksheet.setColumnWidth(3, 8000);
			worksheet.setColumnWidth(4, 6000);
			
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("New Barcode Report");

			rowhead0.setHeight((short) 700);
			cellhead0.setCellStyle(unlockedCellStyle);
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

			// -----------------------------

			XSSFCellStyle unlockcellStyle = workbook.createCellStyle();
			unlockcellStyle.setLocked(false);

			// ----------------------------
			XSSFRow rowhead = worksheet.createRow((int) 1);

			XSSFCell cellhead1 = rowhead.createCell((int) 0);
			cellhead1.setCellValue("S.No.");

			cellhead1.setCellStyle(cellStyle);

			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("Gtin");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("FinalizeDate");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Case Etn");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("BottleBarcode");
			cellhead5.setCellStyle(cellStyle);
			

			int i = 0;
			while (rs.next()) {

				
				
				Date crdt=Utility.convertSqlDateToUtilDate(rs
						.getDate("cr_date"));
				
				Date dat = Utility.convertSqlDateToUtilDate(rs
						.getDate("dispatch_date"));

				DateFormat formatter;

				formatter = new SimpleDateFormat("yyMMdd");
				date = formatter.format(dat);

				formatter = new SimpleDateFormat("yyMMdd");
				createdDate= formatter.format(crdt);

				
			
				k++;
				XSSFRow row1 = worksheet.createRow((int) k);

				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("gtin"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(date);
				// cellC1.setCellStyle(unlockcellStyle);

				XSSFCell cellD1 = row1.createCell((int) 3);
			
				noOfUnit=String.valueOf(rs.getInt("pack_size"));
				
				if(noOfUnit.length()==2){
					cellD1.setCellValue("01" + rs.getString("gtin") + "13"
							+ date + "37" + noOfUnit + "21"
							+ rs.getString("case_no"));
					}
					else if(noOfUnit.length()==1)
					{
						cellD1.setCellValue("01" + rs.getString("gtin") + "13"
								+ date + "370" + noOfUnit + "21"
								+ rs.getString("case_no"));
					}else{
						cellD1.setCellValue("01" + rs.getString("gtin") + "13"
								+ date + "37" + noOfUnit.substring(0,2) + "21"
								+ rs.getString("case_no"));
					}
				 

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue("01" + rs.getString("gtin") + "13"
						+ date + "21" + rs.getString("bottlecode"));

			}
			fileOut = new FileOutputStream(relativePath + "//ExciseUp//Excel//"
					+"NewBarcode"+ dt.getUploadedId() + "" + createdDate + ".xls");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

		
			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue("End");

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;

		} catch (Exception e) {

			
			e.printStackTrace();
		}finally{
			
			
			try{
				
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return flag;
	}

	
	
	
	
	
	
	

	// ===========================code for csv============================
	
	
	

		public void saveCSV(ValidateBarcodeAction action) throws IOException {
			
			Connection con = null;
			
			int  excelcase = 0;
			
			
			PreparedStatement pstmt = null;
			int id = getMaxId() + 1;
			
			String query = "INSERT INTO licence.barcode_replace(id, dist_or_brewry_id, barcode, type, status, cr_date)VALUES (?, ?, ?, ?, ?, ?)";

			
			int  flag = 0;
			BufferedReader bReader = new BufferedReader(new FileReader(action.getCsvFilepath()));
			
			try {
				con = ConnectionToDataBase.getConnection();
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				

				String line = "";
				StringTokenizer st = null;
				int lineNumber = 0;
				int tokenNumber = 0;
				while ((line = bReader.readLine()) != null) {
					lineNumber++;
					
					st = new StringTokenizer(line, " ");
					while (st.hasMoreTokens()) {
						
						String barcode = st.nextToken().trim();
						
						String	id_from_barcode=barcode.substring(6,9);
						
						System.out.println("----barcode.length()---"+barcode.length()+"--  action.getDist_Bwfl_brwryId()---"+action.getDist_Bwfl_brwryId()+"---id_from_barcode--"+id_from_barcode);
						

						if (barcode != null) {
							
							System.out.println("-0---00000000000000");	
								if (barcode !=null) {								
									System.out.println("-111111111111111111111");	
									
									if(barcode.length()==39 && Integer.parseInt(action.getDist_Bwfl_brwryId())==Integer.parseInt(id_from_barcode))
									{
										System.out.println("-22222222222222222222");	
									pstmt.setInt(1, id);
									pstmt.setInt(2, Integer.parseInt(action.getDist_Bwfl_brwryId()));
									pstmt.setString(3, barcode);
									pstmt.setString(4, action.getRadio_CL_FL_Beer());
									//if(get_Status(action,barcode).trim()!=null && get_Status(action,barcode).trim().length()>0)
									pstmt.setString(5, get_Status(action,barcode).trim());
									pstmt.setDate(6, Utility.convertUtilDateToSQLDate(new Date()));
								
					        		 pstmt.addBatch();
					        		 excelcase++;		 
					        		      
					        		   }else{System.out.println("-33333333333333333");	
					        			   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
													" BarCode-"+barcode.trim()+" Does Not Belongs To Selected  List ! "+excelcase+"" ,"  BarCode-"+barcode.trim()+" Does Not Belongs To  Selected  List ! "+excelcase+"  "));	
											
												pstmt.setInt(1, id);
												pstmt.setInt(2, Integer.parseInt(action.getDist_Bwfl_brwryId()));
												pstmt.setString(3, barcode);
												pstmt.setString(4, action.getRadio_CL_FL_Beer());
												pstmt.setString(5, "Rejected (Code Invalid Not For This Distillery)");
												pstmt.setDate(6, Utility.convertUtilDateToSQLDate(new Date()));
												
												pstmt.addBatch();
												
												
												action.setExcelCases(excelcase);
												
												excelcase++;	
					        		   }
									
								} else {
									flag = 1;
								}
							

						}

						tokenNumber = 0;
					}
				}
						
				if (flag == 0) {
					
					int csv_batch[]=pstmt.executeBatch();
					
					System.out.println("---  csv_batch  ------"+csv_batch.length+" --csv lnth---"+excelcase);
					
					
					if (csv_batch.length == excelcase && excelcase>0 ) {
						con.commit();
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,	"File Uploaded Successfully ","File Uploaded Successfully "));
									
						action.setShowdata_Flag(true);
					} else {
						con.rollback();
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"File Not Uploaded!!", "File Not Uploaded!!"));
										
					}
				} else {
					con.rollback();
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Kindly Enter Same Gatepass Number !! ","Kindly Enter Same Gatepass Number !! "));
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();

					if (con != null)
						con.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			

		}
	
	
	
	
	
	
}
