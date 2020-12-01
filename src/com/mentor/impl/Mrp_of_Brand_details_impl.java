package com.mentor.impl;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
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
import com.mentor.action.Mrp_of_Brand_details_action;
import com.mentor.datatable.Mrp_of_Brand_details_dt;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;


//=====================Prasoon 08-04-2020==================================

public class Mrp_of_Brand_details_impl {
	
	
//=====================display datatable method==========================
	
	public ArrayList display_datatable(Mrp_of_Brand_details_action act)

	   {
		  
			ArrayList list=new ArrayList();
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs = null;
			int i =1;
			String getQr=null;
			
		
			try
			     {	
				con = ConnectionToDataBase.getConnection();
				
				if(act.getRadio().equalsIgnoreCase("D")){

				      getQr = "  select p.package_name, p.mrp, br.brand_name,br.license_category," +
				     		  " br.unit ,case when br.renewal_flg='true' then 'Yes' else 'No' end as renewal_flg from distillery.brand_registration_20_21 br,"+
			                  "  distillery.packaging_details_20_21 p where br.brand_id=p.brand_id_fk and  "+
				     		  " coalesce(br.distillery_id,0)>0 "+
			                " order by br.unit,p.mrp,br.brand_name;" ;
				}
				else if(act.getRadio().equalsIgnoreCase("B")){

				      getQr = "  select p.package_name, p.mrp, br.brand_name,br.license_category," +
				     		" br.unit ,case when br.renewal_flg='true' then 'Yes' else 'No' end as renewal_flg from distillery.brand_registration_20_21 br,"+
			                "  distillery.packaging_details_20_21 p where br.brand_id=p.brand_id_fk and  "+
				     		" coalesce(br.brewery_id,0)>0 "+
			                " order by br.unit,p.mrp,br.brand_name;" ;
				}
				else if(act.getRadio().equalsIgnoreCase("BWFL")){

				      getQr = "  select p.package_name, p.mrp, br.brand_name,br.license_category," +
				     		" br.unit ,case when br.renewal_flg='true' then 'Yes' else 'No' end as renewal_flg from distillery.brand_registration_20_21 br,"+
			                "  distillery.packaging_details_20_21 p where br.brand_id=p.brand_id_fk and  "+
				     		" coalesce(br.int_bwfl_id,0)>0 "+
			                " order by br.unit,p.mrp,br.brand_name; " ;
				}else if(act.getRadio().equalsIgnoreCase("IU")){

				      getQr = "  select p.package_name, p.mrp, br.brand_name,br.license_category," +
				     		" br.unit ,case when br.renewal_flg='true' then 'Yes' else 'No' end as renewal_flg from distillery.brand_registration_20_21 br,"+
			                "  distillery.packaging_details_20_21 p where br.brand_id=p.brand_id_fk and  "+
				     		" coalesce(br.int_fl2d_id,0)>0 "+
			                " order by br.unit,p.mrp,br.brand_name; " ;
				}else if(act.getRadio().equalsIgnoreCase("CSD")){

				      getQr = "  select p.package_name, p.mrp, br.brand_name,br.license_category," +
				     		" br.unit ,case when br.renewal_flg='true' then 'Yes' else 'No' end as renewal_flg from distillery.brand_registration_20_21 br,"+
			                "  distillery.packaging_details_20_21 p where br.brand_id=p.brand_id_fk and  "+
				     		" coalesce(br.int_fl2a_id,0)>0 "+
			                " order by br.unit,p.mrp,br.brand_name; " ;
				}
			
	     	pstmt= con.prepareStatement(getQr);
		    rs= pstmt.executeQuery();	
		  //  //System.out.println("---act.getRadio===== "+act.getRadio());
				 //System.out.println("---last data===== "+getQr);
				while(rs.next())
				{
					Mrp_of_Brand_details_dt dt = new Mrp_of_Brand_details_dt();
					dt.setSrNO(i);
					dt.setUnitName(rs.getString("unit"));
					dt.setBrandName(rs.getString("brand_name"));
					dt.setSize(rs.getString("package_name"));
					dt.setMrp(rs.getDouble("mrp"));	
					dt.setRenew(rs.getString("renewal_flg"));
					dt.setType(rs.getString("license_category"));
					list.add(dt);
					i++;
					
				}
				//act.setFlag(false);
			}
			  
			catch(Exception e){
				e.printStackTrace();
			}
			
			finally
			{
				try
				{
					if(pstmt!=null) pstmt.close();
					if(con!=null) con.close();
					if(rs!=null) rs.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			return list;
		  
	}

//========================Excel================Print====================================================
	
	public boolean excelMrpBrandDetails(Mrp_of_Brand_details_action action) {
		
		//System.out.println("--------excelMrpBrandDetails_-- execute ");

			Connection con = null;
			con = ConnectionToDataBase.getConnection();
			DecimalFormat diciformatter = new DecimalFormat("#.##");
		    String reportQuery = null;
		    String LicType = action.getRadio() ;
		  
		    if(action.getRadio().equalsIgnoreCase("D")){

		    	reportQuery = "  select p.package_name,  round(edp::int,2)edp, round(duty,2)duty,  round(adduty,2)adduty, round(export,2)export, round(permit,2)permit,round( mrp::int,2)mrp, br.brand_name,br.license_category," +
			     		  " br.unit ,case when br.renewal_flg='true' then 'Yes' else 'No' end as renewal_flg from distillery.brand_registration_20_21 br,"+
		                  "  distillery.packaging_details_20_21 p where br.brand_id=p.brand_id_fk and  "+
			     		  " coalesce(br.distillery_id,0)>0 "+
		                " order by br.unit,p.mrp,br.brand_name;" ;
			}
			else if(action.getRadio().equalsIgnoreCase("B")){

				reportQuery = "  select p.package_name,  round(edp::int,2)edp, round(duty,2)duty,  round(adduty,2)adduty, round(export,2)export, round(permit,2)permit,round( mrp::int,2)mrp, br.brand_name,br.license_category," +
			     		" br.unit ,case when br.renewal_flg='true' then 'Yes' else 'No' end as renewal_flg from distillery.brand_registration_20_21 br,"+
		                "  distillery.packaging_details_20_21 p where br.brand_id=p.brand_id_fk and  "+
			     		" coalesce(br.brewery_id,0)>0 "+
		                " order by br.unit,p.mrp,br.brand_name;" ;
			}
			else if(action.getRadio().equalsIgnoreCase("BWFL")){

				reportQuery = "  select p.package_name,  round(edp::int,2)edp, round(duty,2)duty,  round(adduty,2)adduty, round(export,2)export, round(permit,2)permit,round( mrp::int,2)mrp, br.brand_name,br.license_category," +
			     		" br.unit ,case when br.renewal_flg='true' then 'Yes' else 'No' end as renewal_flg from distillery.brand_registration_20_21 br,"+
		                "  distillery.packaging_details_20_21 p where br.brand_id=p.brand_id_fk and  "+
			     		" coalesce(br.int_bwfl_id,0)>0 "+
		                " order by br.unit,p.mrp,br.brand_name; " ;
			}else if(action.getRadio().equalsIgnoreCase("IU")){

				reportQuery = "  select p.package_name,  br.brand_name,br.license_category, round(edp::int,2)edp, round(duty,2)duty,  round(adduty,2)adduty, round(export,2)export, round(permit,2)permit,round( mrp::int,2)mrp," +
			     		" br.unit ,case when br.renewal_flg='true' then 'Yes' else 'No' end as renewal_flg from distillery.brand_registration_20_21 br,"+
		                "  distillery.packaging_details_20_21 p where br.brand_id=p.brand_id_fk and  "+
			     		" coalesce(br.int_fl2d_id,0)>0 "+
		                " order by br.unit,p.mrp,br.brand_name; " ;
			}
			else if(action.getRadio().equalsIgnoreCase("CSD")){

				reportQuery = "  select p.package_name,  br.brand_name,br.license_category, round(edp::int,2)edp, round(duty,2)duty,  round(adduty,2)adduty, round(export,2)export, round(permit,2)permit,round( mrp::int,2)mrp," +
			     		" br.unit ,case when br.renewal_flg='true' then 'Yes' else 'No' end as renewal_flg from distillery.brand_registration_20_21 br,"+
		                "  distillery.packaging_details_20_21 p where br.brand_id=p.brand_id_fk and  "+
			     		" coalesce(br.int_fl2a_id,0)>0 "+
		                " order by br.unit,p.mrp,br.brand_name; " ;
			}
			
			
			//System.out.println("----------  Excel   -----" + reportQuery);

			
			String relativePath = Constants.JBOSS_SERVER_PATH
					+ Constants.JBOSS_LINX_PATH;
			FileOutputStream fileOut = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean flag = false;
			long k = 0;
			

			
			
			try {
			
				pstmt = con.prepareStatement(reportQuery);

				rs = pstmt.executeQuery();
				
				

				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet worksheet = workbook.createSheet("Mrp of Brand Details");
				// CellStyle unlockedCellStyle = workbook.createCellStyle();
				// unlockedCellStyle.setLocked(false);
				// worksheet.protectSheet("UP-EX-MIS");
				worksheet.setColumnWidth(0, 2000);
				worksheet.setColumnWidth(1, 20000);
				worksheet.setColumnWidth(2, 10000);
				worksheet.setColumnWidth(3, 3000);				
				worksheet.setColumnWidth(4, 3000);
				worksheet.setColumnWidth(5, 3000);
				worksheet.setColumnWidth(6, 3000);
				worksheet.setColumnWidth(7, 3000);
				worksheet.setColumnWidth(8, 3000);
				worksheet.setColumnWidth(9, 3000);
				
				XSSFRow rowhead0 = worksheet.createRow((int) 0);
				XSSFCell cellhead0 = rowhead0.createCell((int) 1);
				cellhead0.setCellValue("MRP OF BRAND DETAILS  "+ LicType);			
				rowhead0.setHeight((short) 700);
		
		
				// cellhead0.setCellStyle(unlockedCellStyle);
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
				cellhead2.setCellValue("Unit Name");
				cellhead2.setCellStyle(cellStyle);

				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("Brand Name");
				cellhead3.setCellStyle(cellStyle);

				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("Size");
				cellhead4.setCellStyle(cellStyle);

				XSSFCell cellhead5 = rowhead.createCell((int) 4);
				cellhead5.setCellValue("EDP");
				cellhead5.setCellStyle(cellStyle);

				XSSFCell cellhead6 = rowhead.createCell((int) 5);
				cellhead6.setCellValue("Duty");
				cellhead6.setCellStyle(cellStyle);

				XSSFCell cellhead7 = rowhead.createCell((int) 6);
				cellhead7.setCellValue("Addt. Duty");
				cellhead7.setCellStyle(cellStyle);

				XSSFCell cellhead8 = rowhead.createCell((int) 7);
				cellhead8.setCellValue("Export");
				cellhead8.setCellStyle(cellStyle);

				XSSFCell cellhead9 = rowhead.createCell((int) 8);
				cellhead9.setCellValue("Permit");
				cellhead9.setCellStyle(cellStyle);

				XSSFCell cellhead10 = rowhead.createCell((int) 9);
				cellhead10.setCellValue("MRP");
				cellhead10.setCellStyle(cellStyle);

				
				int i = 0;
				while (rs.next()) {

				
					k++;
					XSSFRow row1 = worksheet.createRow((int) k);
					XSSFCell cellA1 = row1.createCell((int) 0);
					cellA1.setCellValue(k - 1);

					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("unit"));

					XSSFCell cellC1 = row1.createCell((int) 2);
					cellC1.setCellValue(rs.getString("brand_name") );
					// cellC1.setCellStyle(unlockcellStyle);

					XSSFCell cellD1 = row1.createCell((int) 3);
					cellD1.setCellValue(rs.getString("package_name"));

					XSSFCell cellE1 = row1.createCell((int) 4);
					cellE1.setCellValue(rs.getString("edp"));

					XSSFCell cellF1 = row1.createCell((int) 5);
					cellF1.setCellValue(rs.getString("duty"));

					XSSFCell cellG1 = row1.createCell((int) 6);
					cellG1.setCellValue(rs.getString("adduty"));
					
					XSSFCell cellG2 = row1.createCell((int) 7);
					cellG2.setCellValue(rs.getString("export"));
					
					XSSFCell cellG3 = row1.createCell((int) 8);
					cellG3.setCellValue(rs.getString("permit"));
					
					XSSFCell cellG4 = row1.createCell((int) 9);
					cellG4.setCellValue(rs.getString("mrp"));

					
				}
				Random rand = new Random();
				int n = rand.nextInt(550) + 1; 
				fileOut = new FileOutputStream(relativePath
						+ "//ExciseUp//MIS//Excel//" 
						+ n + "_MRP_Of_Brand_Details.xls");

				action.setExlname( n + "_MRP_Of_Brand_Details.xls" );
				XSSFRow row1 = worksheet.createRow((int) k + 1);
				// XSSFCell cellA1 = row1.createCell((int) 0);
				// cellA1.setCellValue("End");
				// cellA1.setCellStyle(cellStyle);

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
				cellA7.setCellValue(" ");
				cellA7.setCellStyle(cellStyle);
				
				XSSFCell cellA8 = row1.createCell((int) 7);
				cellA8.setCellValue(" ");
				cellA8.setCellStyle(cellStyle);
				
				XSSFCell cellA9 = row1.createCell((int) 8);
				cellA9.setCellValue(" ");
				cellA9.setCellStyle(cellStyle);
				
				XSSFCell cellA10 = row1.createCell((int) 9);
				cellA10.setCellValue(" ");
				cellA10.setCellStyle(cellStyle);
				
				
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				flag = true;
				action.setExcelFlag(true);
				con.close();
				

			} catch (Exception e) {

				// //System.out.println("xls2" + e.getMessage());
				e.printStackTrace();

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								e.getMessage(), e.getMessage()));

			} finally {

				try {
					con.close();
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, e
									.getMessage(), e.getMessage()));
					//System.out.println("  final block ");

					e.printStackTrace();
				}
			}
		
		
		return flag;
		
	}

}
