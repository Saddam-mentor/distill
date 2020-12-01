package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.mentor.action.ChallanDepositDetailSuccessRptAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class ChallanDepositDetailSuccessRptImpl {
	
	// =======================print report FL2 =================================

	public void printReport(ChallanDepositDetailSuccessRptAction act){



		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String type = null;


		try {
			con = ConnectionToDataBase.getConnection();

			
			if(act.getRadioType().equalsIgnoreCase("SM")){
				
				type = "Sugarmill";
				
				reportQuery = 	" SELECT a.int_distillery_id as unit_id, CASE WHEN a.vch_challan_type='S' THEN 'SUGARMILL' end as unit_type,"+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,     "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code         "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                     "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                        "+
								" AND a.vch_challan_type='S' AND a.date_challan_date                                                        "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                         "+
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                               "+
								" GROUP BY a.int_distillery_id, a.vch_challan_type, a.vch_depositor_name,                                   "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                                "+
								" ORDER BY a.vch_depositor_name, a.vch_challan_type,  a.date_challan_date ";
			}
			else if(act.getRadioType().equalsIgnoreCase("DL")){
				
				type = "Distillery";
				
				reportQuery = 	" SELECT a.int_distillery_id as unit_id, CASE WHEN a.vch_challan_type='D' THEN 'DISTILLERY' end as unit_type, "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,       "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code           "+
								" FROM public.challan_deposit a, public.challn_deposit_detail b                                               "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                          "+
								" AND a.vch_challan_type='D' AND a.date_challan_date                                                          "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                           "+
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                 "+
								" GROUP BY a.int_distillery_id, a.vch_challan_type, a.vch_depositor_name,                                     "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                                  "+
								" ORDER BY a.vch_depositor_name, a.vch_challan_type,  a.date_challan_date ";
			}
			else if(act.getRadioType().equalsIgnoreCase("BR")){
				
				type = "Brewery";
				
				reportQuery = 	" SELECT a.int_brewery_id as unit_id, CASE WHEN a.vch_challan_type='B' THEN 'BREWERY' end as unit_type, "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no, "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code     "+
								" FROM bwfl.challan_deposit_brewery a, bwfl.challn_deposit_brewery_detail b                             "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                    "+
								" AND a.vch_challan_type='B' AND a.date_challan_date                                                    "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                     "+      
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                           "+
								" GROUP BY a.int_brewery_id, a.vch_challan_type, a.vch_depositor_name,                                  "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                            "+
								" ORDER BY a.vch_depositor_name, a.vch_challan_type,  a.date_challan_date ";
			}
			else if(act.getRadioType().equalsIgnoreCase("BWFL")){
				
				type = "BWFL";
							
				reportQuery = 	" SELECT a.unit_id as unit_id, CASE WHEN a.unit_type='BWFL2A' THEN 'BWFL2A'                            "+
								" WHEN a.unit_type='BWFL2B' THEN 'BWFL2B' WHEN a.unit_type='BWFL2C' THEN 'BWFL2C'                      "+
								" WHEN a.unit_type='BWFL2D' THEN 'BWFL2D' end as unit_type,                                            "+
								" b.vch_firm_name as unit_name, a.chalan_date as challan_dt, a.chalan_no as challan_no,                "+
								" SUM(a.amount) as amount, left(a.head_code,15) as head_code, a.g6_code as g6_code                     "+
								" FROM bwfl_license.chalan_deposit_bwfl_fl2d a, bwfl.registration_of_bwfl_lic_holder_20_21 b           "+
								" WHERE a.unit_id=b.int_id AND a.unit_type in ('BWFL2A','BWFL2B','BWFL2C','BWFL2D')                    "+
								" AND a.chalan_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                  "+                        
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                          "+
								" GROUP BY a.unit_id, a.unit_type, a.chalan_date, a.chalan_no, a.head_code, a.g6_code, b.vch_firm_name "+
								" ORDER BY  b.vch_firm_name, a.unit_type, a.chalan_date ";
			}
			else if(act.getRadioType().equalsIgnoreCase("FL2D")){
				
				type = "FL2D";
				
				reportQuery = 	" SELECT a.unit_id as unit_id, CASE WHEN a.unit_type='FL2D' THEN 'FL2D' end as unit_type,                "+
								" b.vch_firm_name as unit_name, a.chalan_date as challan_dt, a.chalan_no as challan_no,                  "+
								" SUM(a.amount) as amount, left(a.head_code,15) as head_code, a.g6_code as g6_code                       "+
								" FROM bwfl_license.chalan_deposit_bwfl_fl2d a, licence.fl2_2b_2d_20_21 b                                "+
								" WHERE a.unit_id=b.int_app_id AND a.unit_type='FL2D'                                                    "+
								" AND a.chalan_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                    "+                       
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                            "+
								" GROUP BY a.unit_id, a.unit_type, a.chalan_date, a.chalan_no, a.head_code, a.g6_code, b.vch_firm_name   "+
								" ORDER BY  b.vch_firm_name, a.unit_type, a.chalan_date ";
			}
			else if(act.getRadioType().equalsIgnoreCase("NR")){
				
				type = "Non-Registered";
				
				reportQuery = 	" SELECT a.int_distillery_id as unit_id, 'NON-REGISTERED' as unit_type,                                   "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,   "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code       "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                   "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                      "+
								" AND a.vch_challan_type NOT IN ('BWFL', 'FL2D', 'D', 'S') AND a.date_challan_date                        "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                       "+    
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                             "+
								" GROUP BY a.int_distillery_id, a.vch_challan_type, a.vch_depositor_name,                                 "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                              "+
								" ORDER BY  a.vch_depositor_name, a.vch_challan_type, a.date_challan_date ";
			}
			else if(act.getRadioType().equalsIgnoreCase("ALL")){
				
				type = "ALL";
				
				reportQuery = 	" SELECT x.unit_id, x.unit_type, x.unit_name, x.challan_dt, x.challan_no, x.amount, x.head_code, x.g6_code     "+
								" FROM                                                                                                         "+
								" (SELECT a.int_distillery_id as unit_id, CASE WHEN a.vch_challan_type='S' THEN 'SUGARMILL' end as unit_type,  "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,        "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code            "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                        "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                           "+
								" AND a.vch_challan_type='S' AND a.date_challan_date                                                           "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                            "+
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
								" GROUP BY a.int_distillery_id, a.vch_challan_type, a.vch_depositor_name,                                      "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                                   "+
								" UNION                                                                                                        "+
								" SELECT a.int_distillery_id as unit_id, CASE WHEN a.vch_challan_type='D' THEN 'DISTILLERY' end as unit_type,  "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,        "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code            "+
								" FROM public.challan_deposit a, public.challn_deposit_detail b                                                "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                           "+
								" AND a.vch_challan_type='D' AND a.date_challan_date                                                           "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                            "+
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
								" GROUP BY a.int_distillery_id, a.vch_challan_type, a.vch_depositor_name,                                      "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                                   "+
								" UNION                                                                                                        "+
								" SELECT a.int_brewery_id as unit_id, CASE WHEN a.vch_challan_type='B' THEN 'BREWERY' end as unit_type,        "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,        "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code            "+
								" FROM bwfl.challan_deposit_brewery a, bwfl.challn_deposit_brewery_detail b                                    "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                           "+
								" AND a.vch_challan_type='B' AND a.date_challan_date                                                           "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                            "+
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
								" GROUP BY a.int_brewery_id, a.vch_challan_type, a.vch_depositor_name,                                         "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                                   "+
								" UNION                                                                                                        "+
								" SELECT a.unit_id as unit_id, CASE WHEN a.unit_type='BWFL2A' THEN 'BWFL2A'                                    "+
								" WHEN a.unit_type='BWFL2B' THEN 'BWFL2B' WHEN a.unit_type='BWFL2C' THEN 'BWFL2C'                              "+
								" WHEN a.unit_type='BWFL2D' THEN 'BWFL2D' end as unit_type,                                                    "+
								" b.vch_firm_name as unit_name, a.chalan_date as challan_dt, a.chalan_no as challan_no,                        "+
								" SUM(a.amount) as amount, left(a.head_code,15) as head_code, a.g6_code as g6_code                             "+
								" FROM bwfl_license.chalan_deposit_bwfl_fl2d a, bwfl.registration_of_bwfl_lic_holder_20_21 b                   "+
								" WHERE a.unit_id=b.int_id AND a.unit_type in ('BWFL2A','BWFL2B','BWFL2C','BWFL2D')                            "+
								" AND a.chalan_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                          "+                
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
								" GROUP BY a.unit_id, a.unit_type, a.chalan_date, a.chalan_no, a.head_code, a.g6_code, b.vch_firm_name         "+
								" UNION                                                                                                        "+
								" SELECT a.unit_id as unit_id, CASE WHEN a.unit_type='FL2D' THEN 'FL2D' end as unit_type,                      "+
								" b.vch_firm_name as unit_name, a.chalan_date as challan_dt, a.chalan_no as challan_no,                        "+
								" SUM(a.amount) as amount, left(a.head_code,15) as head_code, a.g6_code as g6_code                             "+
								" FROM bwfl_license.chalan_deposit_bwfl_fl2d a, licence.fl2_2b_2d_20_21 b                                      "+
								" WHERE a.unit_id=b.int_app_id AND a.unit_type='FL2D'                                                          "+
								" AND a.chalan_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                          "+                
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
								" GROUP BY a.unit_id, a.unit_type, a.chalan_date, a.chalan_no, a.head_code, a.g6_code, b.vch_firm_name         "+
								" UNION                                                                                                        "+
								" SELECT a.int_distillery_id as unit_id, 'NON-REGISTERED' as unit_type,                                        "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,        "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code            "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                        "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                           "+
								" AND a.vch_challan_type NOT IN ('BWFL', 'FL2D', 'D', 'S')                                                     "+
								" AND a.date_challan_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                    "+                      
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
								" GROUP BY a.int_distillery_id, a.vch_challan_type, a.vch_depositor_name,                                      "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code)x                                 "+
								" ORDER BY  x.unit_type, x.unit_name, x.challan_dt ";
			}


			pst = con.prepareStatement(reportQuery);
			System.out.println("reportQuery-----FL2---------" + reportQuery);

			rs = pst.executeQuery();

			if(rs.next()) {

				
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
				parameters.put("type",type);
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
                if (act.getRadioType().equalsIgnoreCase("ALL")){
				   
                	jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "ChallanDepositDetailSuccessRpt.jasper");

    				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
    				Random rand = new Random();
    				int n = rand.nextInt(250) + 1;
    				JasperExportManager.exportReportToPdfFile(print, relativePathpdf+ File.separator + "ChallanDepositDetailSuccessRpt"+ "-" + n + ".pdf");
    				act.setPdfName("ChallanDepositDetailSuccessRpt" + "-" + n + ".pdf");
    				act.setPrintFlag(true);
				
                }
                
                else{
                	
    				
    				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "ChallanDepositDetailSuccessRptUnitWise.jasper");

    				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
    				Random rand = new Random();
    				int n = rand.nextInt(250) + 1;
    				JasperExportManager.exportReportToPdfFile(print, relativePathpdf+ File.separator + "ChallanDepositDetailSuccessRptUnitWise"+ "-" + n + ".pdf");
    				act.setPdfName("ChallanDepositDetailSuccessRptUnitWise" + "-" + n + ".pdf");
    				act.setPrintFlag(true);
                }
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

	public void printExcel(ChallanDepositDetailSuccessRptAction act){


		Connection con = null;
	
	    double total = 0.0;	
		String type="";
		String reportQuery="";
			
			if(act.getRadioType().equalsIgnoreCase("SM")){
				
				type = "Sugarmill";
				
				reportQuery = 	" SELECT a.int_distillery_id as unit_id, CASE WHEN a.vch_challan_type='S' THEN 'SUGARMILL' end as unit_type,"+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,     "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code         "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                     "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                        "+
								" AND a.vch_challan_type='S' AND a.date_challan_date                                                        "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                         "+
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                               "+
								" GROUP BY a.int_distillery_id, a.vch_challan_type, a.vch_depositor_name,                                   "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                                "+
								" ORDER BY a.vch_depositor_name, a.vch_challan_type,  a.date_challan_date ";
			}
			else if(act.getRadioType().equalsIgnoreCase("DL")){
				
				type = "Distillery";
				
				reportQuery = 	" SELECT a.int_distillery_id as unit_id, CASE WHEN a.vch_challan_type='D' THEN 'DISTILLERY' end as unit_type, "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,       "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code           "+
								" FROM public.challan_deposit a, public.challn_deposit_detail b                                               "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                          "+
								" AND a.vch_challan_type='D' AND a.date_challan_date                                                          "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                           "+
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                 "+
								" GROUP BY a.int_distillery_id, a.vch_challan_type, a.vch_depositor_name,                                     "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                                  "+
								" ORDER BY a.vch_depositor_name, a.vch_challan_type,  a.date_challan_date ";
			}
			else if(act.getRadioType().equalsIgnoreCase("BR")){
				
				type = "Brewery";
				
				reportQuery = 	" SELECT a.int_brewery_id as unit_id, CASE WHEN a.vch_challan_type='B' THEN 'BREWERY' end as unit_type, "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no, "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code     "+
								" FROM bwfl.challan_deposit_brewery a, bwfl.challn_deposit_brewery_detail b                             "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                    "+
								" AND a.vch_challan_type='B' AND a.date_challan_date                                                    "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                     "+      
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                           "+
								" GROUP BY a.int_brewery_id, a.vch_challan_type, a.vch_depositor_name,                                  "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                            "+
								" ORDER BY a.vch_depositor_name, a.vch_challan_type,  a.date_challan_date ";
			}
			else if(act.getRadioType().equalsIgnoreCase("BWFL")){
				
				type = "BWFL";
							
				reportQuery = 	" SELECT a.unit_id as unit_id, CASE WHEN a.unit_type='BWFL2A' THEN 'BWFL2A'                            "+
								" WHEN a.unit_type='BWFL2B' THEN 'BWFL2B' WHEN a.unit_type='BWFL2C' THEN 'BWFL2C'                      "+
								" WHEN a.unit_type='BWFL2D' THEN 'BWFL2D' end as unit_type,                                            "+
								" b.vch_firm_name as unit_name, a.chalan_date as challan_dt, a.chalan_no as challan_no,                "+
								" SUM(a.amount) as amount, left(a.head_code,15) as head_code, a.g6_code as g6_code                     "+
								" FROM bwfl_license.chalan_deposit_bwfl_fl2d a, bwfl.registration_of_bwfl_lic_holder_19_20 b           "+
								" WHERE a.unit_id=b.int_id AND a.unit_type in ('BWFL2A','BWFL2B','BWFL2C','BWFL2D')                    "+
								" AND a.chalan_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                  "+                        
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                          "+
								" GROUP BY a.unit_id, a.unit_type, a.chalan_date, a.chalan_no, a.head_code, a.g6_code, b.vch_firm_name "+
								" ORDER BY  b.vch_firm_name, a.unit_type, a.chalan_date ";
			}
			else if(act.getRadioType().equalsIgnoreCase("FL2D")){
				
				type = "FL2D";
				
				reportQuery = 	" SELECT a.unit_id as unit_id, CASE WHEN a.unit_type='FL2D' THEN 'FL2D' end as unit_type,                "+
								" b.vch_firm_name as unit_name, a.chalan_date as challan_dt, a.chalan_no as challan_no,                  "+
								" SUM(a.amount) as amount, left(a.head_code,15) as head_code, a.g6_code as g6_code                       "+
								" FROM bwfl_license.chalan_deposit_bwfl_fl2d a, licence.fl2_2b_2d_19_20 b                                "+
								" WHERE a.unit_id=b.int_app_id AND a.unit_type='FL2D'                                                    "+
								" AND a.chalan_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                    "+                       
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                            "+
								" GROUP BY a.unit_id, a.unit_type, a.chalan_date, a.chalan_no, a.head_code, a.g6_code, b.vch_firm_name   "+
								" ORDER BY  b.vch_firm_name, a.unit_type, a.chalan_date ";
			}
			else if(act.getRadioType().equalsIgnoreCase("NR")){
				
				type = "Non-Registered";
				
				reportQuery = 	" SELECT a.int_distillery_id as unit_id, 'NON-REGISTERED' as unit_type,                                   "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,   "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code       "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                   "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                      "+
								" AND a.vch_challan_type NOT IN ('BWFL', 'FL2D', 'D', 'S') AND a.date_challan_date                        "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                       "+    
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                             "+
								" GROUP BY a.int_distillery_id, a.vch_challan_type, a.vch_depositor_name,                                 "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                              "+
								" ORDER BY  a.vch_depositor_name, a.vch_challan_type, a.date_challan_date ";
			}
			else if(act.getRadioType().equalsIgnoreCase("ALL")){
				
				type = "ALL";
				
				reportQuery = 	" SELECT x.unit_id, x.unit_type, x.unit_name, x.challan_dt, x.challan_no, x.amount, x.head_code, x.g6_code     "+
								" FROM                                                                                                         "+
								" (SELECT a.int_distillery_id as unit_id, CASE WHEN a.vch_challan_type='S' THEN 'SUGARMILL' end as unit_type,  "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,        "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code            "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                        "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                           "+
								" AND a.vch_challan_type='S' AND a.date_challan_date                                                           "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                            "+
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
								" GROUP BY a.int_distillery_id, a.vch_challan_type, a.vch_depositor_name,                                      "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                                   "+
								" UNION                                                                                                        "+
								" SELECT a.int_distillery_id as unit_id, CASE WHEN a.vch_challan_type='D' THEN 'DISTILLERY' end as unit_type,  "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,        "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code            "+
								" FROM public.challan_deposit a, public.challn_deposit_detail b                                                "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                           "+
								" AND a.vch_challan_type='D' AND a.date_challan_date                                                           "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                            "+
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
								" GROUP BY a.int_distillery_id, a.vch_challan_type, a.vch_depositor_name,                                      "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                                   "+
								" UNION                                                                                                        "+
								" SELECT a.int_brewery_id as unit_id, CASE WHEN a.vch_challan_type='B' THEN 'BREWERY' end as unit_type,        "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,        "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code            "+
								" FROM bwfl.challan_deposit_brewery a, bwfl.challn_deposit_brewery_detail b                                    "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                           "+
								" AND a.vch_challan_type='B' AND a.date_challan_date                                                           "+
								" BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                            "+
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
								" GROUP BY a.int_brewery_id, a.vch_challan_type, a.vch_depositor_name,                                         "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code                                   "+
								" UNION                                                                                                        "+
								" SELECT a.unit_id as unit_id, CASE WHEN a.unit_type='BWFL2A' THEN 'BWFL2A'                                    "+
								" WHEN a.unit_type='BWFL2B' THEN 'BWFL2B' WHEN a.unit_type='BWFL2C' THEN 'BWFL2C'                              "+
								" WHEN a.unit_type='BWFL2D' THEN 'BWFL2D' end as unit_type,                                                    "+
								" b.vch_firm_name as unit_name, a.chalan_date as challan_dt, a.chalan_no as challan_no,                        "+
								" SUM(a.amount) as amount, left(a.head_code,15) as head_code, a.g6_code as g6_code                             "+
								" FROM bwfl_license.chalan_deposit_bwfl_fl2d a, bwfl.registration_of_bwfl_lic_holder_19_20 b                   "+
								" WHERE a.unit_id=b.int_id AND a.unit_type in ('BWFL2A','BWFL2B','BWFL2C','BWFL2D')                            "+
								" AND a.chalan_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                          "+                
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
								" GROUP BY a.unit_id, a.unit_type, a.chalan_date, a.chalan_no, a.head_code, a.g6_code, b.vch_firm_name         "+
								" UNION                                                                                                        "+
								" SELECT a.unit_id as unit_id, CASE WHEN a.unit_type='FL2D' THEN 'FL2D' end as unit_type,                      "+
								" b.vch_firm_name as unit_name, a.chalan_date as challan_dt, a.chalan_no as challan_no,                        "+
								" SUM(a.amount) as amount, left(a.head_code,15) as head_code, a.g6_code as g6_code                             "+
								" FROM bwfl_license.chalan_deposit_bwfl_fl2d a, licence.fl2_2b_2d_19_20 b                                      "+
								" WHERE a.unit_id=b.int_app_id AND a.unit_type='FL2D'                                                          "+
								" AND a.chalan_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                          "+                
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
								" GROUP BY a.unit_id, a.unit_type, a.chalan_date, a.chalan_no, a.head_code, a.g6_code, b.vch_firm_name         "+
								" UNION                                                                                                        "+
								" SELECT a.int_distillery_id as unit_id, 'NON-REGISTERED' as unit_type,                                        "+
								" a.vch_depositor_name as unit_name, a.date_challan_date as challan_dt, a.vch_challan_no as challan_no,        "+
								" SUM(a.double_amt) as amount, left(b.vch_head_code,15) as head_code, b.vch_g6_head_code as g6_code            "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                        "+
								" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                           "+
								" AND a.vch_challan_type NOT IN ('BWFL', 'FL2D', 'D', 'S')                                                     "+
								" AND a.date_challan_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                    "+                      
								" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                                  "+
								" GROUP BY a.int_distillery_id, a.vch_challan_type, a.vch_depositor_name,                                      "+
								" a.date_challan_date, a.vch_challan_no, b.vch_head_code, b.vch_g6_head_code)x                                 "+
								" ORDER BY  x.unit_type, x.unit_name, x.challan_dt ";
			}
				
		System.out.println("excel query===  " + reportQuery);

		
		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long start = 0;
		long end = 0;
		boolean flag = false;
		long k = 0;
		

		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(reportQuery);

			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Challan Deposite Detailed Success Report");

			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 5000);
			worksheet.setColumnWidth(2, 5000);
			worksheet.setColumnWidth(3, 5000);
			worksheet.setColumnWidth(4, 5000);
			worksheet.setColumnWidth(5, 5000);
			
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			
			cellhead0.setCellValue("Challan Deposite Detailed Success Report  For " +type + " From "
			+Utility.convertUtilDateToSQLDate(act.getFromDate())+ " To " +Utility.convertUtilDateToSQLDate(act.getToDate()) );
			
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
			cellhead1.setCellValue("Sr. No");
			cellhead1.setCellStyle(cellStyle);
			
			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("Unit Type");
			cellhead2.setCellStyle(cellStyle);
			
			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Uniit Name");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Challan Date");
			cellhead4.setCellStyle(cellStyle);
			
			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Challan no.");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Head Code");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("G6 Code");
			cellhead7.setCellStyle(cellStyle);
			
			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("Amount");
			cellhead8.setCellStyle(cellStyle);
			
			int i = 0;
			
			while (rs.next()) 
			{
				total = total + rs.getDouble("amount");
			
				i++;
				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				XSSFCell cellA1 = row1.createCell((int) 0); //
				cellA1.setCellValue(i); //
				
				
				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("unit_type")); 
				
				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("unit_name"));
				
				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getDate("challan_dt").toString());
				
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("challan_no"));
				
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("head_code"));
				
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("g6_code"));
				
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("amount"));
		
			//System.out.println("------------ "+Math.round(cases_of_last_year_sale));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n+ "_Challan_Deposite_Detailed_report.xls");

			act.setExlname(n+"_Challan_Deposite_Detailed_report.xls");
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			

			XSSFCell cellA2 = row1.createCell((int) 0);
			cellA2.setCellValue(" "); 
			cellA2.setCellStyle(cellStyle);
			
			XSSFCell cellA3 = row1.createCell((int) 1); 
			cellA3.setCellValue(" "); 
			cellA3.setCellStyle(cellStyle); 
			
			XSSFCell cellA4 = row1.createCell((int) 2); 
			cellA4.setCellValue(""); 
			cellA4.setCellStyle(cellStyle); 
			

			XSSFCell cellA5 = row1.createCell((int) 3); 
			cellA5.setCellValue(""); 
			cellA5.setCellStyle(cellStyle); 
			
			XSSFCell cellA6 = row1.createCell((int) 4);
			cellA6.setCellValue("");
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 5);
			cellA7.setCellValue("");
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 6);
			cellA8.setCellValue("Total:"); 
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 7);
			cellA9.setCellValue(total); 
			cellA9.setCellStyle(cellStyle);
		    
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			act.setExcelFlag(true);
			
			} catch (Exception e) {
		
		      e.printStackTrace();
		
			} finally {
			
			try
			{
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



