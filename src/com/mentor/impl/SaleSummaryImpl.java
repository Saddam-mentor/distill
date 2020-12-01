package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	 
import com.mentor.action.SaleSummaryAction;
import com.mentor.action.SaleSummaryAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

	public class SaleSummaryImpl {
	 public void printReport(SaleSummaryAction act) {

			String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

			String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
			String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
			JasperReport jasperReport = null;
			PreparedStatement pst = null;
			Connection con = null;
			ResultSet rs = null;
			String reportQuery = null; 
			String type = ""; 
			String dist=null;
	 

			try {
				con = ConnectionToDataBase.getConnection(); 
			 
				Date FromDate=Utility.convertUtilDateToSQLDate(act.getFromDate());
				Date toDate=Utility.convertUtilDateToSQLDate(act.getToDate());
				if(act.getRadio().trim().equalsIgnoreCase("CL")){
					type="for CL";
				}else if(act.getRadio().trim().equalsIgnoreCase("FL")){
					type="for FL";
				}else if(act.getRadio().trim().equalsIgnoreCase("B")){
					type="for Beer";
				}else{
					type="";
				}
				/// reportQuery = "select * from fl2d.getstock('"+FromDate+"','" + toDate+ "','"+act.getRadio()+"', "+filter+")";
				if(act.getRadio().trim().equalsIgnoreCase("CL")){
		
				
				
				
				reportQuery="SELECT DISTINCT  c.brand_name,d.quantity,sum(b.dispatchd_box) as dispatchd_box,sum(b.dispatchd_bottl) as dispatchd_bottl, "+
						"sum((((b.size::int*b.dispatchd_box)*e.qnt_ml_detail)/1000)) as bl, (SUM(b.duty)+SUM(b.addduty)+sum(d.cesh*b.dispatchd_bottl)) as totalduty     "+  //(b.duty+b.addduty ) as totalduty
						"FROM distillery.gatepass_to_manufacturewholesale_cl_20_21 a,  distillery.cl2_stock_trxn_20_21 b , "+
						"distillery.brand_registration_20_21 c ,distillery.packaging_details_20_21 d,distillery.box_size_details e  "+
						"where a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt_date and b.int_brand_id=c.brand_id "+
						"and c.brand_id=d.brand_id_fk and d.box_id=e.box_id and b.int_pckg_id=d.package_id "+
						" and a.dt_date between '"+FromDate+"' and '"+toDate+"' group by c.brand_name,d.quantity " + //,b.duty,b.addduty 
						//and a.dt_date between '2018-03-30' and '2018-11-12'  group by c.brand_name,d.quantity,b.duty,b.addduty "+
						"order by c.brand_name asc ";
			
			 }else if(act.getRadio().trim().equalsIgnoreCase("FL") || act.getRadio().trim().equalsIgnoreCase("B")){
				reportQuery=" select x.brand_name,x.quantity,x.dispatch_box as dispatchd_box,x.dispatch_bottle as dispatchd_bottl,x.bl,x.totalduty from (                                                   "+
							" (SELECT DISTINCT c.brand_name,d.quantity,sum(b.dispatch_box) as dispatch_box,sum(b.dispatch_bottle) as dispatch_bottle,                   "+
							" sum((((b.size::int*b.dispatch_box)*e.qnt_ml_detail)/1000)) as bl, ((d.duty+d.adduty+d.cesh)*sum(b.dispatch_bottle)) as totalduty                 "+
							" FROM distillery.gatepass_to_districtwholesale_20_21 a,  distillery.fl2_stock_trxn_20_21 b ,                                                           "+
							" distillery.brand_registration_20_21 c ,distillery.packaging_details_20_21 d,distillery.box_size_details e                                             "+
							" where a.vch_gatepass_no=b.vch_gatepass_no and b.int_brand_id=c.brand_id                                                                   "+
							" and c.brand_id=d.brand_id_fk and d.box_id=e.box_id and b.int_pckg_id=d.package_id                                                         "+
							" and a.dt_date  between '"+FromDate+"' and '"+toDate+"'  and   case when  '"+act.getRadio()+"'='FL' then a.vch_to='FL2' else a.vch_to='FL2B' end " +//a.vch_to='FL2'
							" group by c.brand_name,d.quantity,d.duty, d.adduty,d.cesh )              "+
							" union all                                                                                                                                 "+
							" (SELECT DISTINCT c.brand_name,d.quantity,sum(b.dispatch_box) as dispatch_box,sum(b.dispatch_bottle) as dispatch_bottle,                   "+
							" sum((((b.size::int*b.dispatch_box)*e.qnt_ml_detail)/1000)) as bl,(b.cal_duty+(d.cesh*sum(b.dispatch_bottle))) as totalduty                                                  "+
							" FROM fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,  fl2d.fl2d_stock_trxn_20_21 b ,                                                                 "+
							" distillery.brand_registration_20_21 c ,distillery.packaging_details_20_21 d,distillery.box_size_details e                                             "+
							" where a.vch_gatepass_no=b.vch_gatepass_no and b.int_brand_id=c.brand_id                                                                   "+
							" and c.brand_id=d.brand_id_fk and d.box_id=e.box_id and b.int_pckg_id=d.package_id                                                         "+
							" and a.dt_date   between '"+FromDate+"' and '"+toDate+"'   and case when  '"+act.getRadio()+"'='FL' then a.vch_to='FL2' else a.vch_to='FL2B' end " +// a.vch_to='FL2'
							" group by c.brand_name,d.quantity,b.cal_duty,d.cesh )                       "+
							" union all                                                                                                                                 "+
							" (SELECT DISTINCT  c.brand_name,d.quantity,sum(b.dispatch_box) as dispatch_box,sum(b.dispatch_bottle) as dispatch_bottle,                  "+
							" sum((((b.size::int*b.dispatch_box)*e.qnt_ml_detail)/1000)) as bl,((d.duty+d.adduty+d.cesh)*sum(b.dispatch_bottle)) as totalduty                  "+
							" FROM bwfl_license.gatepass_to_districtwholesale_bwfl_20_21 a,  bwfl_license.fl2_stock_trxn_bwfl_20_21 b ,                                             "+
							" distillery.brand_registration_20_21 c ,distillery.packaging_details_20_21 d,distillery.box_size_details e                                             "+
							" where a.vch_gatepass_no=b.vch_gatepass_no and b.int_brand_id=c.brand_id                                                                   "+
							" and c.brand_id=d.brand_id_fk and d.box_id=e.box_id and b.int_pckg_id=d.package_id                                                         "+
							" and a.dt_date   between '"+FromDate+"' and '"+toDate+"'  and                                                                                   "+
							"  case when  '"+act.getRadio()+"'='B' then a.vch_from='BWFL2B' else a.vch_from!='BWFL2B' end                                                                "+
							" group by c.brand_name,d.quantity,d.duty,d.adduty,d.cesh )                                                                                        "+
							" )x order by x.brand_name,x.quantity,x.dispatch_box,x.dispatch_bottle,x.bl,x.totalduty ";
			                                                                                                                           
				}
				 
				//System.out.println("reportQuery---------" + reportQuery);
				pst = con.prepareStatement(reportQuery);
				

				rs = pst.executeQuery();

				if (rs.next()) {
					
					rs = pst.executeQuery();
					Map parameters = new HashMap();
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("SUBREPORT_DIR", relativePath + File.separator);
					parameters.put("image", relativePath + File.separator);
					parameters.put("fromDate", Utility.convertUtilDateToSQLDate(act.getFromDate()));
					parameters.put("toDate", Utility.convertUtilDateToSQLDate(act.getToDate()));
					parameters.put("type", type);
				//	parameters.put("vch_applicant_name", act.getApplicant_name());
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

					/*ac.setLicence_no(rs.getString("vch_licence_no"));
					ac.setApplicant_name(rs.getString("vch_applicant_name"));
					*/
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator+ "saleSummary_cl_fl_beer.jasper");

					JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator + "saleSummary_cl_fl_beer" + "-" + n + ".pdf");
					act.setPdfname("saleSummary_cl_fl_beer" + "-" + n+ ".pdf");
					act.setPrintFlag(true);
					
				} else {
					type="";
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No Data Found!!", "No Data Found!!"));
					act.setPrintFlag(false);
				}
			} catch (JRException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
				}
			}

		}
	 
	 
	 public boolean writeExcel(SaleSummaryAction act){

			Connection con = null;
			int boxes = 0;
			int bottles = 0;
			double bl = 0.0;
			double duty = 0.0;
			String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
			FileOutputStream fileOut = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean flag = false;
			long k = 0;
			String type = "";
			String reportQuery = null; 

			try {			
				
				con = ConnectionToDataBase.getConnection(); 
				 
				Date FromDate=Utility.convertUtilDateToSQLDate(act.getFromDate());
				Date toDate=Utility.convertUtilDateToSQLDate(act.getToDate());
				
				if(act.getRadio().trim().equalsIgnoreCase("CL")){
					type="for CL";
				}else if(act.getRadio().trim().equalsIgnoreCase("FL")){
					type="for FL";
				}else if(act.getRadio().trim().equalsIgnoreCase("B")){
					type="for Beer";
				}else{
					type="";
				}
				
				
				if(act.getRadio().trim().equalsIgnoreCase("CL")){
					
					
					
					
					reportQuery="SELECT DISTINCT  c.brand_name,d.quantity,sum(b.dispatchd_box) as dispatchd_box,sum(b.dispatchd_bottl) as dispatchd_bottl, "+
							"sum((((b.size::int*b.dispatchd_box)*e.qnt_ml_detail)/1000)) as bl, (SUM(b.duty)+SUM(b.addduty)+sum(d.cesh*b.dispatchd_bottl)) as totalduty     "+  //(b.duty+b.addduty ) as totalduty
							"FROM distillery.gatepass_to_manufacturewholesale_cl_20_21 a,  distillery.cl2_stock_trxn_20_21 b , "+
							"distillery.brand_registration_20_21 c ,distillery.packaging_details_20_21 d,distillery.box_size_details e  "+
							"where a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt_date and b.int_brand_id=c.brand_id "+
							"and c.brand_id=d.brand_id_fk and d.box_id=e.box_id and b.int_pckg_id=d.package_id "+
							" and a.dt_date between '"+FromDate+"' and '"+toDate+"' group by c.brand_name,d.quantity " + //,b.duty,b.addduty 
							//and a.dt_date between '2018-03-30' and '2018-11-12'  group by c.brand_name,d.quantity,b.duty,b.addduty "+
							"order by c.brand_name asc ";
				
				 }else if(act.getRadio().trim().equalsIgnoreCase("FL") || act.getRadio().trim().equalsIgnoreCase("B")){
					reportQuery=" select x.brand_name,x.quantity,x.dispatch_box as dispatchd_box,x.dispatch_bottle as dispatchd_bottl,x.bl,x.totalduty from (                                                   "+
								" (SELECT DISTINCT c.brand_name,d.quantity,sum(b.dispatch_box) as dispatch_box,sum(b.dispatch_bottle) as dispatch_bottle,                   "+
								" sum((((b.size::int*b.dispatch_box)*e.qnt_ml_detail)/1000)) as bl, ((d.duty+d.adduty+d.cesh)*sum(b.dispatch_bottle)) as totalduty                 "+
								" FROM distillery.gatepass_to_districtwholesale_20_21 a,  distillery.fl2_stock_trxn_20_21 b ,                                                           "+
								" distillery.brand_registration_20_21 c ,distillery.packaging_details_20_21 d,distillery.box_size_details e                                             "+
								" where a.vch_gatepass_no=b.vch_gatepass_no and b.int_brand_id=c.brand_id                                                                   "+
								" and c.brand_id=d.brand_id_fk and d.box_id=e.box_id and b.int_pckg_id=d.package_id                                                         "+
								" and a.dt_date  between '"+FromDate+"' and '"+toDate+"'  and   case when  '"+act.getRadio()+"'='FL' then a.vch_to='FL2' else a.vch_to='FL2B' end " +//a.vch_to='FL2'
								" group by c.brand_name,d.quantity,d.duty, d.adduty,d.cesh )              "+
								" union all                                                                                                                                 "+
								" (SELECT DISTINCT c.brand_name,d.quantity,sum(b.dispatch_box) as dispatch_box,sum(b.dispatch_bottle) as dispatch_bottle,                   "+
								" sum((((b.size::int*b.dispatch_box)*e.qnt_ml_detail)/1000)) as bl,(b.cal_duty+(d.cesh*sum(b.dispatch_bottle))) as totalduty                                                  "+
								" FROM fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,  fl2d.fl2d_stock_trxn_20_21 b ,                                                                 "+
								" distillery.brand_registration_20_21 c ,distillery.packaging_details_20_21 d,distillery.box_size_details e                                             "+
								" where a.vch_gatepass_no=b.vch_gatepass_no and b.int_brand_id=c.brand_id                                                                   "+
								" and c.brand_id=d.brand_id_fk and d.box_id=e.box_id and b.int_pckg_id=d.package_id                                                         "+
								" and a.dt_date   between '"+FromDate+"' and '"+toDate+"'   and case when  '"+act.getRadio()+"'='FL' then a.vch_to='FL2' else a.vch_to='FL2B' end " +// a.vch_to='FL2'
								" group by c.brand_name,d.quantity,b.cal_duty,d.cesh )                       "+
								" union all                                                                                                                                 "+
								" (SELECT DISTINCT  c.brand_name,d.quantity,sum(b.dispatch_box) as dispatch_box,sum(b.dispatch_bottle) as dispatch_bottle,                  "+
								" sum((((b.size::int*b.dispatch_box)*e.qnt_ml_detail)/1000)) as bl,((d.duty+d.adduty+d.cesh)*sum(b.dispatch_bottle)) as totalduty                  "+
								" FROM bwfl_license.gatepass_to_districtwholesale_bwfl_20_21 a,  bwfl_license.fl2_stock_trxn_bwfl_20_21 b ,                                             "+
								" distillery.brand_registration_20_21 c ,distillery.packaging_details_20_21 d,distillery.box_size_details e                                             "+
								" where a.vch_gatepass_no=b.vch_gatepass_no and b.int_brand_id=c.brand_id                                                                   "+
								" and c.brand_id=d.brand_id_fk and d.box_id=e.box_id and b.int_pckg_id=d.package_id                                                         "+
								" and a.dt_date   between '"+FromDate+"' and '"+toDate+"'  and                                                                                   "+
								"  case when  '"+act.getRadio()+"'='B' then a.vch_from='BWFL2B' else a.vch_from!='BWFL2B' end                                                                "+
								" group by c.brand_name,d.quantity,d.duty,d.adduty,d.cesh )                                                                                        "+
								" )x order by x.brand_name,x.quantity,x.dispatch_box,x.dispatch_bottle,x.bl,x.totalduty ";
				                                                                                                                           
					}
					 
			 
				pstmt = con.prepareStatement(reportQuery);

				rs = pstmt.executeQuery();

				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet worksheet = workbook.createSheet("Sale Summary Report");

				worksheet.setColumnWidth(0, 3000);
				worksheet.setColumnWidth(1, 20000);
				worksheet.setColumnWidth(2, 7000);
				worksheet.setColumnWidth(3, 7000);
				worksheet.setColumnWidth(4, 7000);
				worksheet.setColumnWidth(5, 7000);
				worksheet.setColumnWidth(6, 7000);
				

				XSSFRow rowhead0 = worksheet.createRow((int) 0);
				XSSFCell cellhead0 = rowhead0.createCell((int) 0);
				cellhead0.setCellValue(" Sale Summary of "+ type + " "+ " From " + Utility.convertUtilDateToSQLDate(act.getFromDate())+ " " + " To " + " "+ Utility.convertUtilDateToSQLDate(act.getToDate()));
				
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
				cellhead2.setCellValue("Brand");
				cellhead2.setCellStyle(cellStyle);

				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("Size");
				cellhead3.setCellStyle(cellStyle);

				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("Boxes");
				cellhead4.setCellStyle(cellStyle);

				XSSFCell cellhead5 = rowhead.createCell((int) 4);
				cellhead5.setCellValue("Bottles");
				cellhead5.setCellStyle(cellStyle);

				XSSFCell cellhead6 = rowhead.createCell((int) 5);
				cellhead6.setCellValue("BL");
				cellhead6.setCellStyle(cellStyle);

				XSSFCell cellhead7 = rowhead.createCell((int) 6);
				cellhead7.setCellValue("Duty");
				cellhead7.setCellStyle(cellStyle);
				

				while (rs.next()) {

					boxes = boxes + rs.getInt("dispatchd_box");
					bottles = bottles + rs.getInt("dispatchd_bottl");
					bl = bl + rs.getDouble("bl");
					duty = duty + rs.getDouble("totalduty");
										
					k++;

					XSSFRow row1 = worksheet.createRow((int) k);

					XSSFCell cellA1 = row1.createCell((int) 0);
					cellA1.setCellValue(k-1);

					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("brand_name"));

					XSSFCell cellK1 = row1.createCell((int) 2);
					cellK1.setCellValue(rs.getInt("quantity"));

					XSSFCell cellC1 = row1.createCell((int) 3);
					cellC1.setCellValue(rs.getInt("dispatchd_box"));

					XSSFCell cellD1 = row1.createCell((int) 4);
					cellD1.setCellValue(rs.getInt("dispatchd_bottl"));

					XSSFCell cellE1 = row1.createCell((int) 5);
					cellE1.setCellValue(rs.getDouble("bl"));

					XSSFCell cellF1 = row1.createCell((int) 6);
					cellF1.setCellValue(rs.getDouble("totalduty"));
		

				}
				Random rand = new Random();
				int n = rand.nextInt(550) + 1;
				fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "_SaleSummary.xls");
				act.setExlname(n + "_SaleSummary");

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
				cellA4.setCellValue(boxes);
				cellA4.setCellStyle(cellStyle);

				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue(bottles);
				cellA5.setCellStyle(cellStyle);

				XSSFCell cellA6 = row1.createCell((int) 5);
				cellA6.setCellValue(bl);
				cellA6.setCellStyle(cellStyle);

				XSSFCell cellA7 = row1.createCell((int) 6);
				cellA7.setCellValue(duty);
				cellA7.setCellStyle(cellStyle);

				
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
		
				flag = true;
				act.setExcelFlag(true);
				con.close();
			} catch (Exception e) {			
				e.printStackTrace();
			} finally {
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

	}
