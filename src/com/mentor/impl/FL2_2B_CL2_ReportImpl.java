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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import com.mentor.action.FL2_2B_CL2_ReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class FL2_2B_CL2_ReportImpl {

	// =======================print report=================================

	public void printReport(FL2_2B_CL2_ReportAction act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String year = act.getYear();
		String filter = "";
		String filter1 = "";
		try {
			con = ConnectionToDataBase.getConnection();
			
			if(ResourceUtil.getUserNameAllReq().length()>9){
				if(ResourceUtil.getUserNameAllReq().substring(0,10).equalsIgnoreCase("Excise-DEO")){
					
					
					filter = " and f.core_district_id=(select   districtid from public.district where deo = '"+ResourceUtil.getUserNameAllReq()+"') ";
					filter1 = " and licencee_district	=(select   districtid from public.district where deo = '"+ResourceUtil.getUserNameAllReq()+"') ";  
					//System.out.println("inside deo "+filter);
					}else{
						filter = "";
						filter1="";
					}
				
			}
			
			
			
			
			
			if (act.getRadio().equalsIgnoreCase("CL2"))
			{

			 //==================== without strength===================
				
			reportQuery = 	"  SELECT  DISTINCT  a.rcvdt ,a.dt_date as dt, a.vch_gatepass_no,  " +
							"  a.vch_distillary_name || '(DISTILLERY)' as recv_from, " +
							"  a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
							"  SUM( b.dispatchd_box) as boxes, c.description as district, " +							
							"  SUM(round(CAST(float8(((b.dispatchd_box*b.size::int)*d.quantity)/1000) as numeric), 2)) as bl  " +
							"  FROM distillery.gatepass_to_manufacturewholesale_cl_"+year+" a, distillery.cl2_stock_trxn_"+year+" b, " +
							" public.district c, distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f " +
							" WHERE a.int_dist_id=b.int_dissleri_id AND a.vch_gatepass_no=b.vch_gatepass_no  " +
							" AND a.dt_date=b.dt_date AND f.core_district_id= c.districtid " +
							" "+ filter+" AND b.int_pckg_id=d.package_id AND a.vch_to='CL2' " +
							" AND  a.vch_to_lic_no=f.vch_licence_no AND a.vch_to=f.vch_license_type    "+
							" AND a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
							" GROUP BY a.vch_gatepass_no, a.vch_distillary_name, a.licensee_name, " +
							" a.dt_date, a.vch_to_lic_no, c.description, a.rcvdt   " +
							" ORDER BY district, wholeseller_nm, dt "; 
			
			
			//==================== with strength===================	
							/*	
			reportQuery = 	" SELECT  DISTINCT a.dt_date as dt, a.vch_gatepass_no, g.strength,  " +
							" a.vch_distillary_name || '(DISTILLERY)' as recv_from, " +
							" a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
							" SUM( b.dispatchd_box) as boxes, c.description as district, " +
							" SUM(round(CAST(float8(((b.dispatchd_box*b.size::int)*d.quantity)/1000) as numeric), 2)) as bl " +
							" FROM distillery.gatepass_to_manufacturewholesale_cl_"+year+" a, distillery.cl2_stock_trxn_"+year+" b, " +
							" public.district c, distillery.packaging_details_"+year+" d, " +
							" licence.fl2_2b_2d_"+year+" f, distillery.brand_registration g " +
							" WHERE a.int_dist_id=b.int_dissleri_id AND a.vch_gatepass_no=b.vch_gatepass_no  " +
							" AND a.dt_date=b.dt_date AND f.core_district_id=c.districtid " +
							" AND b.int_pckg_id=d.package_id AND a.vch_to='CL2' " +
							" AND  a.vch_to_lic_no=f.vch_licence_no AND a.vch_to=f.vch_license_type "+
							" AND b.int_brand_id=g.brand_id AND a.int_dist_id=g.distillery_id "+
							" AND a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
							" GROUP BY g.strength, a.vch_gatepass_no, a.vch_distillary_name, a.licensee_name, " +
							" a.dt_date, a.vch_to_lic_no, c.description  " +
							" ORDER BY g.strength, district, dt, wholeseller_nm  ";*/

			}
			
			else if (act.getRadio().equalsIgnoreCase("FL2B"))
			{
			
			// ==================== without strength===================

			reportQuery = 	" SELECT x.dt, x.vch_gatepass_no, x.recv_from, x.wholeseller_nm, x.lic_no, x.bl, x.boxes, x.district, x.rcvdt   " +
							" FROM " +
							" (SELECT DISTINCT   a.rcvdt ,a.dt_date as dt,  " +
							" d.vch_undertaking_name || '(DISTILLERY)' as recv_from, " +
							" a.vch_gatepass_no, a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
							" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl, " +
							" SUM( b.dispatch_box) as boxes, c.description as district  " +
							" FROM distillery.gatepass_to_districtwholesale_"+year+" a, distillery.fl2_stock_trxn_"+year+" b,  " +
							" public.district c, public.dis_mst_pd1_pd2_lic d, distillery.packaging_details_"+year+" e, licence.fl2_2b_2d_"+year+" f  " +
							" WHERE a.int_dist_id=b.int_dissleri_id AND a.vch_gatepass_no=b.vch_gatepass_no " +
							" AND a.dt_date=b.dt AND f.core_district_id=c.districtid  " +
							" "+ filter+" AND a.int_dist_id=d.int_app_id_f AND b.int_pckg_id=e.package_id " +
							" AND a.vch_to='FL2B'  " +
							" AND  a.vch_to_lic_no=f.vch_licence_no AND a.vch_to=f.vch_license_type " +
							" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
							" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no, a.rcvdt, " +
							" d.vch_undertaking_name, a.licensee_name, a.licensee_adrs, c.description " +
							" UNION " +
							" SELECT DISTINCT  a.rcvdt , a.dt_date as dt,  " +
							" d.vch_firm_name || '(BWFL)' as recv_from, a.vch_gatepass_no, " +
							" a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
							" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl, " +
							" SUM( b.dispatch_box) as boxes, c.description as district " +
							" FROM bwfl_license.gatepass_to_districtwholesale_bwfl_"+year+" a, bwfl_license.fl2_stock_trxn_bwfl_"+year+" b, " +
							" public.district c, bwfl.registration_of_bwfl_lic_holder_"+year+" d, " +
							" distillery.packaging_details_"+year+" e, licence.fl2_2b_2d_"+year+" f " +
							" WHERE a.int_bwfl_id=b.int_bwfl_id AND a.vch_gatepass_no=b.vch_gatepass_no  " +
							" AND a.dt_date=b.dt AND f.core_district_id=c.districtid "+ filter+" AND a.int_bwfl_id=d.int_id " +
							" AND b.int_pckg_id=e.package_id AND f.vch_license_type='FL2B' AND a.vch_from='BWFL2B' " +
							" AND  a.vch_to_lic_no=f.vch_licence_no     " +
							" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
							" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no,  a.rcvdt,  " +
							" d.vch_firm_name, a.licensee_name, a.licensee_adrs, c.description " +
							" UNION " +
							" SELECT DISTINCT  a.rcvdt ,a.dt_date as dt,   " +
							" d.vch_firm_name || '(FL2D)' as recv_from, a.vch_gatepass_no,  " +
							" a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " + 
							" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl, " +  
							" SUM( b.dispatch_box) as boxes, " +
							" CASE WHEN COALESCE(a.licencee_district,0)='0' THEN a.licensee_adrs else " +
							" (SELECT c.description from public.district c where a.licencee_district=c.districtid ) end as district " +
							" FROM fl2d.gatepass_to_districtwholesale_fl2d_"+year+" a, fl2d.fl2d_stock_trxn_"+year+" b,   " +
							" licence.fl2_2b_2d_"+year+" d, distillery.packaging_details_"+year+" e  " +
							" WHERE a.int_fl2d_id=b.int_fl2d_id AND a.vch_gatepass_no=b.vch_gatepass_no  " +
							" AND a.dt_date=b.dt AND d.vch_license_type='FL2D'     " +
							" "+ filter1+" AND a.int_fl2d_id=d.int_app_id AND b.int_pckg_id=e.package_id AND a.vch_to='FL2B'  " +
							" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
							" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no, a.rcvdt ,  " +
							" d.vch_firm_name, a.licensee_name, a.licensee_adrs, a.licencee_district  " +
							" UNION " +
							" SELECT DISTINCT  a.rcvdt , a.dt_date as dt, " +
							" d.brewery_nm || '(BREWERY)' as recv_from, a.vch_gatepass_no, " +
							" a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
							" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl, " +
							" SUM( b.dispatch_box) as boxes, c.description as district " +
							" FROM bwfl.gatepass_to_districtwholesale_"+year+" a, bwfl.fl2_stock_trxn_"+year+" b,  " +
							" public.district c, public.bre_mst_b1_lic d, distillery.packaging_details_"+year+" e, licence.fl2_2b_2d_"+year+" f " +
							" WHERE a.brewery_id=b.brewery_id AND a.vch_gatepass_no=b.vch_gatepass_no  " +
							" AND a.dt_date=b.dt AND f.core_district_id=c.districtid "+ filter+" " +
							" AND a.brewery_id=d.vch_app_id_f AND b.int_pckg_id=e.package_id AND a.vch_to='FL2B'    " +
							" AND  a.vch_to_lic_no=f.vch_licence_no AND a.vch_to=f.vch_license_type  " +
							" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
							" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no,  a.rcvdt ,	 " +
							" d.brewery_nm, a.licensee_name, a.licensee_adrs, c.description)x " +
							" ORDER BY x.district, x.wholeseller_nm, x.dt ";
			
			}
			
			else if (act.getRadio().equalsIgnoreCase("FL2"))
			{
				
			// ==================== without strength===================
				
			reportQuery = 	" SELECT x.dt, x.vch_gatepass_no, x.recv_from, x.wholeseller_nm, x.lic_no, x.bl, x.boxes, x.district, x.rcvdt   " +
							" FROM " +
							" (SELECT DISTINCT  a.rcvdt,  a.dt_date as dt,  " +
							" d.vch_undertaking_name || '(DISTILLERY)' as recv_from, " +
							" a.vch_gatepass_no, a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
							" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl, " +
							" SUM( b.dispatch_box) as boxes, c.description as district " +
							" FROM distillery.gatepass_to_districtwholesale_"+year+" a, distillery.fl2_stock_trxn_"+year+" b,  " +
							" public.district c, public.dis_mst_pd1_pd2_lic d, distillery.packaging_details_"+year+" e, licence.fl2_2b_2d_"+year+" f " +
							" WHERE a.int_dist_id=b.int_dissleri_id AND a.vch_gatepass_no=b.vch_gatepass_no " +
							" AND a.dt_date=b.dt AND f.core_district_id=c.districtid  " +
							" "+ filter+" AND a.int_dist_id=d.int_app_id_f AND b.int_pckg_id=e.package_id " +
							" AND a.vch_to='FL2'  " +
							" AND  a.vch_to_lic_no=f.vch_licence_no AND a.vch_to=f.vch_license_type " +
							" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
							" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no, a.rcvdt,  " +
							" d.vch_undertaking_name, a.licensee_name, a.licensee_adrs, c.description " +
							" UNION " +
							" SELECT DISTINCT  a.rcvdt, a.dt_date as dt,  " +
							" d.vch_firm_name || '(BWFL)' as recv_from, a.vch_gatepass_no, " +
							" a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
							" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl, " +
							" SUM( b.dispatch_box) as boxes, c.description as district " +
							" FROM bwfl_license.gatepass_to_districtwholesale_bwfl_"+year+" a, bwfl_license.fl2_stock_trxn_bwfl_"+year+" b, " +
							" public.district c, bwfl.registration_of_bwfl_lic_holder_"+year+" d, " +
							" distillery.packaging_details_"+year+" e, licence.fl2_2b_2d_"+year+" f " +
							" WHERE a.int_bwfl_id=b.int_bwfl_id AND a.vch_gatepass_no=b.vch_gatepass_no  " +
							" AND a.dt_date=b.dt AND f.core_district_id=c.districtid "+ filter+"  AND a.int_bwfl_id=d.int_id " +
							" AND b.int_pckg_id=e.package_id AND f.vch_license_type='FL2'    " +
							" AND  a.vch_to_lic_no=f.vch_licence_no  " +
							" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
							" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no, a.rcvdt,  " +
							" d.vch_firm_name, a.licensee_name, a.licensee_adrs, c.description " +
							" UNION " +
							" SELECT DISTINCT  a.rcvdt, a.dt_date as dt,   " +
							" d.vch_firm_name || '(FL2D)' as recv_from, a.vch_gatepass_no, " + 
							" a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no,  " +
							" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl,  " +
							" SUM( b.dispatch_box) as boxes, " +
							" CASE WHEN COALESCE(a.licencee_district,0)='0' THEN a.licensee_adrs else " +
							" (SELECT c.description from public.district c where a.licencee_district=c.districtid ) end as district " +
							" FROM fl2d.gatepass_to_districtwholesale_fl2d_"+year+" a, fl2d.fl2d_stock_trxn_"+year+" b,    " +
							" licence.fl2_2b_2d_"+year+" d, distillery.packaging_details_"+year+" e " +
							" WHERE a.int_fl2d_id=b.int_fl2d_id AND a.vch_gatepass_no=b.vch_gatepass_no " + 
							" AND a.dt_date=b.dt AND d.vch_license_type='FL2D'    " +
							" "+ filter1+" AND a.int_fl2d_id=d.int_app_id AND b.int_pckg_id=e.package_id AND a.vch_to='FL2'    " +
							" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
							" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no, a.rcvdt,    " +
							" d.vch_firm_name, a.licensee_name, a.licensee_adrs, a.licencee_district)x " +							
							" ORDER BY x.district, x.wholeseller_nm, x.dt ";
			
			}
			else{
				reportQuery= "";
			}

			
			//System.out.println(" ---reportquery---"+reportQuery);
				pst = con.prepareStatement(reportQuery);
				 
				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("radioType",act.getRadio());
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "FL2_FL2B_CL2_Report.jasper");
				//jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "FL2_FL2B_CL2_Strength_Report.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "FL2_FL2B_CL2_Report" + "_" + year + "_" + n + ".pdf");
				act.setPdfName("FL2_FL2B_CL2_Report" + "_" + year + "_" +n + ".pdf");
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
	
	//----------------------generate excel for bwfl and fl2d wise report------------------------------
	
	public boolean writeExcel(FL2_2B_CL2_ReportAction act)
	{

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double boxesTotal = 0;
		double blTotal = 0;
		
		String excelQuery = null;		
		String type="";
		String year = act.getYear();
		String filter = "";
		String filter1 = "";
		
		if(ResourceUtil.getUserNameAllReq().length()>9){
			if(ResourceUtil.getUserNameAllReq().substring(0,10).equalsIgnoreCase("Excise-DEO")){
				
				
				filter = " and f.core_district_id=(select   districtid from public.district where deo = '"+ResourceUtil.getUserNameAllReq()+"') ";
				filter1 = " and licencee_district	=(select   districtid from public.district where deo = '"+ResourceUtil.getUserNameAllReq()+"') ";  
				//System.out.println("inside deo "+filter);
				}else{
					filter = "";
					filter1="";
				}
			
		}
		
		
		
		if (act.getRadio().equalsIgnoreCase("CL2"))
		{
			type="CL2";
			
		excelQuery = 	" SELECT  DISTINCT  a.rcvdt,a.dt_date as dt, a.vch_gatepass_no,  " +
						" a.vch_distillary_name || '(DISTILLERY)' as recv_from, " +
						" a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
						" SUM( b.dispatchd_box) as boxes, c.description as district, " +
						" SUM(round(CAST(float8(((b.dispatchd_box*b.size::int)*d.quantity)/1000) as numeric), 2)) as bl " +
						" FROM distillery.gatepass_to_manufacturewholesale_cl_"+year+" a, distillery.cl2_stock_trxn_"+year+" b, " +
						" public.district c, distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f " +
						" WHERE a.int_dist_id=b.int_dissleri_id AND a.vch_gatepass_no=b.vch_gatepass_no  " +
						" "+ filter+" AND a.dt_date=b.dt_date AND f.core_district_id=c.districtid " +
						" AND b.int_pckg_id=d.package_id AND a.vch_to='CL2'   " +
						" AND  a.vch_to_lic_no=f.vch_licence_no AND a.vch_to=f.vch_license_type "+
						" AND a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
						" GROUP BY a.vch_gatepass_no, a.vch_distillary_name, a.licensee_name, a.rcvdt, " +
						" a.dt_date, a.vch_to_lic_no, c.description  " +
						" ORDER BY district, wholeseller_nm, dt "; 

		}	
		
		
		else if (act.getRadio().equalsIgnoreCase("FL2B"))
		{
			type="FL2B";
		
		excelQuery = 	" SELECT x.dt, x.vch_gatepass_no, x.recv_from, x.wholeseller_nm, x.lic_no, x.bl, x.boxes, x.district, x.rcvdt   " +
						" FROM " +
						" (SELECT DISTINCT   a.rcvdt, a.dt_date as dt,  " +
						" d.vch_undertaking_name || '(DISTILLERY)' as recv_from, " +
						" a.vch_gatepass_no, a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
						" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl, " +
						" SUM( b.dispatch_box) as boxes, c.description as district " +
						" FROM distillery.gatepass_to_districtwholesale_"+year+" a, distillery.fl2_stock_trxn_"+year+" b,  " +
						" public.district c, public.dis_mst_pd1_pd2_lic d, distillery.packaging_details_"+year+" e, licence.fl2_2b_2d_"+year+" f " +
						" WHERE a.int_dist_id=b.int_dissleri_id AND a.vch_gatepass_no=b.vch_gatepass_no " +
						" AND a.dt_date=b.dt AND f.core_district_id=c.districtid  " +
						" "+ filter+" AND a.int_dist_id=d.int_app_id_f AND b.int_pckg_id=e.package_id " +
						" AND a.vch_to='FL2B'   " +
						" AND  a.vch_to_lic_no=f.vch_licence_no AND a.vch_to=f.vch_license_type " +
						" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
						" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no, a.rcvdt,  " +
						" d.vch_undertaking_name, a.licensee_name, a.licensee_adrs, c.description " +
						" UNION " +
						" SELECT DISTINCT  a.rcvdt , a.dt_date as dt,  " +
						" d.vch_firm_name || '(BWFL)' as recv_from, a.vch_gatepass_no, " +
						" a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
						" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl, " +
						" SUM( b.dispatch_box) as boxes, c.description as district " +
						" FROM bwfl_license.gatepass_to_districtwholesale_bwfl_"+year+" a, bwfl_license.fl2_stock_trxn_bwfl_"+year+" b, " +
						" public.district c, bwfl.registration_of_bwfl_lic_holder_"+year+" d, " +
						" distillery.packaging_details_"+year+" e, licence.fl2_2b_2d_"+year+" f " +
						" WHERE a.int_bwfl_id=b.int_bwfl_id AND a.vch_gatepass_no=b.vch_gatepass_no  " +
						" AND a.dt_date=b.dt AND f.core_district_id=c.districtid "+ filter+" AND a.int_bwfl_id=d.int_id " +
						" AND b.int_pckg_id=e.package_id AND f.vch_license_type='FL2B' AND a.vch_from='BWFL2B' " +
						" AND  a.vch_to_lic_no=f.vch_licence_no    " +
						" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
						" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no, a.rcvdt, " +
						" d.vch_firm_name, a.licensee_name, a.licensee_adrs, c.description " +
						" UNION " +
						" SELECT DISTINCT  a.rcvdt, a.dt_date as dt,   " +
						" d.vch_firm_name || '(FL2D)' as recv_from, a.vch_gatepass_no,  " +
						" a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " + 
						" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl, " +  
						" SUM( b.dispatch_box) as boxes, " +
						" CASE WHEN COALESCE(a.licencee_district,0)='0' THEN a.licensee_adrs else " +
						" (SELECT c.description from public.district c where a.licencee_district=c.districtid) end as district " +
						" FROM fl2d.gatepass_to_districtwholesale_fl2d_"+year+" a, fl2d.fl2d_stock_trxn_"+year+" b,   " +
						" licence.fl2_2b_2d_"+year+" d, distillery.packaging_details_"+year+" e  " +
						" WHERE a.int_fl2d_id=b.int_fl2d_id AND a.vch_gatepass_no=b.vch_gatepass_no  " +
						" AND a.dt_date=b.dt AND d.vch_license_type='FL2D'    " +
						" "+ filter1+" AND a.int_fl2d_id=d.int_app_id AND b.int_pckg_id=e.package_id AND a.vch_to='FL2B'  " +
						" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
						" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no, a.rcvdt,   " +
						" d.vch_firm_name, a.licensee_name, a.licensee_adrs, a.licencee_district  " +
						" UNION " +
						" SELECT DISTINCT  a.rcvdt ,a.dt_date as dt, " +
						" d.brewery_nm || '(BREWERY)' as recv_from, a.vch_gatepass_no, " +
						" a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
						" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl, " +
						" SUM( b.dispatch_box) as boxes, c.description as district " +
						" FROM bwfl.gatepass_to_districtwholesale_"+year+" a, bwfl.fl2_stock_trxn_"+year+" b,  " +
						" public.district c, public.bre_mst_b1_lic d, distillery.packaging_details_"+year+" e, licence.fl2_2b_2d_"+year+" f " +
						" WHERE a.brewery_id=b.brewery_id AND a.vch_gatepass_no=b.vch_gatepass_no  " +
						" AND a.dt_date=b.dt AND f.core_district_id=c.districtid  " +
						" "+ filter+" AND a.brewery_id=d.vch_app_id_f AND b.int_pckg_id=e.package_id AND a.vch_to='FL2B'   " +
						" AND  a.vch_to_lic_no=f.vch_licence_no AND a.vch_to=f.vch_license_type " +
						" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
						" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no, a.rcvdt ,	 " +
						" d.brewery_nm, a.licensee_name, a.licensee_adrs, c.description)x " +
						" ORDER BY x.district, x.wholeseller_nm, x.dt ";
		}
		
		else if (act.getRadio().equalsIgnoreCase("FL2"))
		{
			type="FL2";
			
		excelQuery = 	" SELECT x.dt, x.vch_gatepass_no, x.recv_from, x.wholeseller_nm, x.lic_no, x.bl, x.boxes, x.district, x.rcvdt   " +
						" FROM " +
						" (SELECT DISTINCT  a.rcvdt, a.dt_date as dt,  " +
						" d.vch_undertaking_name || '(DISTILLERY)' as recv_from, " +
						" a.vch_gatepass_no, a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
						" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl, " +
						" SUM( b.dispatch_box) as boxes, c.description as district " +
						" FROM distillery.gatepass_to_districtwholesale_"+year+" a, distillery.fl2_stock_trxn_"+year+" b,  " +
						" public.district c, public.dis_mst_pd1_pd2_lic d, distillery.packaging_details_"+year+" e, licence.fl2_2b_2d_"+year+" f " +
						" WHERE a.int_dist_id=b.int_dissleri_id AND a.vch_gatepass_no=b.vch_gatepass_no " +
						" AND a.dt_date=b.dt AND f.core_district_id=c.districtid  " +
						" "+ filter+" AND a.int_dist_id=d.int_app_id_f AND b.int_pckg_id=e.package_id " +
						" AND a.vch_to='FL2'  " +
						" AND  a.vch_to_lic_no=f.vch_licence_no AND a.vch_to=f.vch_license_type " +
						" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
						" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no, a.rcvdt,  " +
						" d.vch_undertaking_name, a.licensee_name, a.licensee_adrs, c.description " +
						" UNION " +
						" SELECT DISTINCT  a.rcvdt,  a.dt_date as dt,  " +
						" d.vch_firm_name || '(BWFL)' as recv_from, a.vch_gatepass_no, " +
						" a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no, " +
						" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl, " +
						" SUM( b.dispatch_box) as boxes, c.description as district " +
						" FROM bwfl_license.gatepass_to_districtwholesale_bwfl_"+year+" a, bwfl_license.fl2_stock_trxn_bwfl_"+year+" b, " +
						" public.district c, bwfl.registration_of_bwfl_lic_holder_"+year+" d, " +
						" distillery.packaging_details_"+year+" e, licence.fl2_2b_2d_"+year+" f " +
						" WHERE a.int_bwfl_id=b.int_bwfl_id AND a.vch_gatepass_no=b.vch_gatepass_no  " +
						" AND a.dt_date=b.dt AND f.core_district_id=c.districtid "+ filter+" AND a.int_bwfl_id=d.int_id " +
						" AND b.int_pckg_id=e.package_id AND f.vch_license_type='FL2'    " +
						" AND  a.vch_to_lic_no=f.vch_licence_no  " +
						" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
						" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no, a.rcvdt,  " +
						" d.vch_firm_name, a.licensee_name, a.licensee_adrs, c.description " +
						" UNION " +
						" SELECT DISTINCT  a.rcvdt, a.dt_date as dt,   " +
						" d.vch_firm_name || '(FL2D)' as recv_from, a.vch_gatepass_no, " + 
						" a.licensee_name as wholeseller_nm, a.vch_to_lic_no as lic_no,  " +
						" SUM(round(CAST(float8(((b.dispatch_box*b.size::int)*e.quantity)/1000) as numeric), 2)) as bl,  " +
						" SUM( b.dispatch_box) as boxes, " +
						" CASE WHEN COALESCE(a.licencee_district,0)='0' THEN a.licensee_adrs else " +
						" (SELECT c.description from public.district c where a.licencee_district=c.districtid) end as district " +
						" FROM fl2d.gatepass_to_districtwholesale_fl2d_"+year+" a, fl2d.fl2d_stock_trxn_"+year+" b,    " +
						" licence.fl2_2b_2d_"+year+" d, distillery.packaging_details_"+year+" e " +
						" WHERE a.int_fl2d_id=b.int_fl2d_id AND a.vch_gatepass_no=b.vch_gatepass_no " + 
						" "+ filter1+" AND a.dt_date=b.dt AND d.vch_license_type='FL2D'    " +
						" AND a.int_fl2d_id=d.int_app_id AND b.int_pckg_id=e.package_id AND a.vch_to='FL2'    " +
						" AND  a.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+
						" GROUP BY a.vch_gatepass_no, a.dt_date, a.vch_to_lic_no, a.rcvdt ,   " +
						" d.vch_firm_name, a.licensee_name, a.licensee_adrs, a.licencee_district)x " +							
						" ORDER BY x.district, x.wholeseller_nm, x.dt ";
	

		}
		else{
			excelQuery="";
		}

		//System.out.println(" ---excelQuery---"+excelQuery);
		
		String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		
		

		try {			
			
			
			pstmt = con.prepareStatement(excelQuery);
			//System.out.println("---excelQuery--"+excelQuery);
			
			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");

			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 5000);

			worksheet.setColumnWidth(2, 20000);

			worksheet.setColumnWidth(3, 7000);
			worksheet.setColumnWidth(4, 7000);
			worksheet.setColumnWidth(5, 7000);
			worksheet.setColumnWidth(6, 7000);
			worksheet.setColumnWidth(7, 9000);
			worksheet.setColumnWidth(8, 20000);
			worksheet.setColumnWidth(9, 9000);
			worksheet.setColumnWidth(10, 9000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);			
			
			cellhead0.setCellValue(" Report Of " + type + " " + " From " + " " + Utility.convertUtilDateToSQLDate(act.getFromDate())+ " " + 
			" To " + " "+ Utility.convertUtilDateToSQLDate(act.getToDate()));
			
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
			cellhead2.setCellValue("District");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("WholeSeller");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Licensee Number");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("No. Of Boxes");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Bulk Litre");
			cellhead6.setCellStyle(cellStyle);
			
			/*XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Strength");
			cellhead7.setCellStyle(cellStyle);*/

			XSSFCell cellhead8 = rowhead.createCell((int) 6);
			cellhead8.setCellValue("Dispatch Date");
			cellhead8.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 7);
			cellhead9.setCellValue("Recieve From");
			cellhead9.setCellStyle(cellStyle);
			
			XSSFCell cellhead10 = rowhead.createCell((int) 8);
			cellhead10.setCellValue("Recieve Date");
			cellhead10.setCellStyle(cellStyle);
			

			while (rs.next()) {

				Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);
				

				boxesTotal = boxesTotal + rs.getDouble("boxes");
				blTotal = blTotal + rs.getDouble("bl");
				
				
				k++;

				XSSFRow row1 = worksheet.createRow((int) k);

				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("district"));

				XSSFCell cellK1 = row1.createCell((int) 2);
				cellK1.setCellValue(rs.getString("wholeseller_nm"));

				XSSFCell cellC1 = row1.createCell((int) 3);
				cellC1.setCellValue(rs.getString("lic_no"));

				XSSFCell cellD1 = row1.createCell((int) 4);
				cellD1.setCellValue(rs.getInt("boxes"));

				XSSFCell cellE1 = row1.createCell((int) 5);
				cellE1.setCellValue(rs.getDouble("bl"));
				
				/*XSSFCell cellH1 = row1.createCell((int) 6);
				cellH1.setCellValue(rs.getDouble("strength"));*/

				XSSFCell cellF1 = row1.createCell((int) 6);
				cellF1.setCellValue(date);
				
				XSSFCell cellG1 = row1.createCell((int) 7);
				cellG1.setCellValue(rs.getString("recv_from"));
				
				XSSFCell cellH1 = row1.createCell((int) 8);
				cellH1.setCellValue(rs.getString("rcvdt"));
	

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n + "_" + year + "_" +"-FL2_FL2B_CL2_Report.xlsx");
			act.setExlname(n + "_" + year + "_" + "-FL2_FL2B_CL2_Report");

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
			cellA4.setCellValue(" Total ");
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(boxesTotal);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(blTotal);
			cellA6.setCellStyle(cellStyle);
			

			
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
	//=========================================
	public ArrayList yearListImpl(FL2_2B_CL2_ReportAction act) {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue("");
		list.add(item);
		conn = ConnectionToDataBase.getConnection();

		try {
		
		
	String query = " SELECT year, value FROM public.reporting_year;";

				ps = conn.prepareStatement(query);
			// ps.setDate(1,
			// Utility.convertUtilDateToSQLDate(act.getDt_date()));
			rs = ps.executeQuery();
          //System.out.println("before rs===  "+rs);
			while (rs.next()) {

				item = new SelectItem();

				item.setValue(rs.getString("value"));
				item.setLabel(rs.getString("year"));
				
				list.add(item);
				////System.out.println("== year== "+query);

			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
			e.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
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
	
	//==============================================
		public String getDetails(FL2_2B_CL2_ReportAction act) {

			Connection con = null;
			PreparedStatement pstmt = null, ps2 = null;
			ResultSet rs = null, rs2 = null;

			try {
				con = ConnectionToDataBase.getConnection();

				String queryList = " SELECT start_dt, end_dt FROM public.reporting_year where " +
						           " value='"+ act.getYear()+ "' ";
			//System.out.println("========DateValidation===========" + queryList);
				pstmt = con.prepareStatement(queryList);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					act.setStart_dt(rs.getDate("start_dt"));
					act.setEnd_dt(rs.getDate("end_dt"));
					
					//System.out.println("========StartDate==========" +act.getStart_dt());
					 //System.out.println("========EndDate==========" +act.getEnd_dt());
				}

			} catch (SQLException se) {
				se.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (ps2 != null)
						ps2.close();
					if (rs != null)
						rs.close();
					if (rs2 != null)
						rs2.close();
					if (con != null)
						con.close();

				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			return "";

		}
		
	
}
