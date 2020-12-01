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

import com.mentor.action.DispatchReportBWFL_FL2DAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class DispatchReportBWFL_FL2DImpl {

	// ------------------------------get fl2d list---------------------

	public ArrayList getFL2DList(DispatchReportBWFL_FL2DAction act) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue(0);
		list.add(item);

		try {
			String query = 	" SELECT DISTINCT a.int_app_id, " +
							" concat(concat(a.vch_firm_name,' - '),b.description) as vch_firm_name " +
							" FROM licence.fl2_2b_2d a LEFT OUTER JOIN public.district b " +
							" ON COALESCE(a.core_district_id,0)=b.districtid " +
							" WHERE a.vch_license_type='FL2D' AND a.vch_approval='V' " +
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

	// ------------------------------get fl2d list---------------------

	public ArrayList getBWFLList(DispatchReportBWFL_FL2DAction act, String val)
	{


		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue(0);
		list.add(item);

		try {
			
			if(val.equalsIgnoreCase("BWFL2A"))
			{				
				
				query = " SELECT DISTINCT a.int_id, " +
						" concat(concat(a.vch_firm_name,' - '),b.description) as vch_firm_name " +
						" FROM  bwfl.registration_of_bwfl_lic_holder a LEFT OUTER JOIN public.district b " +
						" ON a.vch_firm_district_id=b.districtid::text  " +
						" WHERE a.vch_license_type='1' AND a.vch_approval='V' ORDER BY vch_firm_name ";
			}
			else if(val.equalsIgnoreCase("BWFL2B"))
			{
							
				query = " SELECT DISTINCT a.int_id, " +
						" concat(concat(a.vch_firm_name,' - '),b.description) as vch_firm_name " +
						" FROM  bwfl.registration_of_bwfl_lic_holder a LEFT OUTER JOIN public.district b " +
						" ON a.vch_firm_district_id=b.districtid::text  " +
						" WHERE a.vch_license_type='2' AND a.vch_approval='V' ORDER BY vch_firm_name ";
			}
			else if(val.equalsIgnoreCase("BWFL2C"))
			{				
				
			    query = " SELECT DISTINCT a.int_id, " +
						" concat(concat(a.vch_firm_name,' - '),b.description) as vch_firm_name " +
						" FROM  bwfl.registration_of_bwfl_lic_holder a LEFT OUTER JOIN public.district b " +
						" ON a.vch_firm_district_id=b.districtid::text  " +
						" WHERE a.vch_license_type='3' AND a.vch_approval='V' ORDER BY vch_firm_name ";
			}
			else if(val.equalsIgnoreCase("BWFL2D"))
			{				
				
				query = " SELECT DISTINCT a.int_id, " +
						" concat(concat(a.vch_firm_name,' - '),b.description) as vch_firm_name " +
						" FROM  bwfl.registration_of_bwfl_lic_holder a LEFT OUTER JOIN public.district b " +
						" ON a.vch_firm_district_id=b.districtid::text  " +
						" WHERE a.vch_license_type='4' AND a.vch_approval='V' ORDER BY vch_firm_name ";
			}
			else{
				query = "";
			}
			
			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("int_id"));
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
	
	
	// =======================print report=================================

	public void printReport(DispatchReportBWFL_FL2DAction act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String reportQuery1 = null;
		String reportQueryBWFL = null;
		String reportQueryFL2D = null;

		try {
			con = ConnectionToDataBase.getConnection();

			/*reportQuery = 	" SELECT x.dt, SUM(x.bwfl_dispatchd_bottl) as bwfl_dispatchd_bottl,  " +
							" SUM(x.bwfl_sale) as bwfl_sale, SUM(x.bwfl_duty) as bwfl_duty, " +
							" SUM(x.fl2d_dispatchd_bottl) as fl2d_dispatchd_bottl, SUM(x.fl2d_sale) as fl2d_sale, " +
							" SUM(x.fl2d_duty) as fl2d_duty " +
							" FROM " +
							" (SELECT a.dt as dt, SUM(a.dispatch_bottle) as bwfl_dispatchd_bottl, " +
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as bwfl_sale, " +
							" SUM(a.duty) as bwfl_duty, SUM(j.quantity) as quantity, " +
							" 0 as fl2d_dispatchd_bottl, 0 as fl2d_sale, 0 as fl2d_duty " +
							" FROM bwfl_license.fl2_stock_trxn_bwfl a, distillery.packaging_details j  " +
							" WHERE a.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' " +
							" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND  a.int_pckg_id=j.package_id  " +
							" GROUP BY a.dt " +
							" UNION " +
							" SELECT a.dt as dt, 0 as bwfl_dispatchd_bottl, 0 as bwfl_sale, 0 as bwfl_duty, " +
							" SUM(j.quantity) as quantity, SUM(a.dispatch_bottle) as fl2d_dispatchd_bottl, " +
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as fl2d_sale, " +
							" SUM(a.duty) as fl2d_duty " +
							" FROM fl2d.fl2d_stock_trxn a, distillery.packaging_details j  " +
							" WHERE a.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND a.int_pckg_id=j.package_id " +
							" GROUP BY a.dt)x " +
							" GROUP BY x.dt ORDER BY x.dt ";*/
			
			
			reportQuery = 	" SELECT x.dt, SUM(x.bwfl_dispatchd_bottl) as bwfl_dispatchd_bottl,  " +
							" SUM(x.bwfl_sale) as bwfl_sale, SUM(x.bwfl_duty) as bwfl_duty, " +
							" SUM(x.fl2d_dispatchd_bottl) as fl2d_dispatchd_bottl, SUM(x.fl2d_sale) as fl2d_sale, " +
							" SUM(x.fl2d_duty) as fl2d_duty " +
							" FROM " +
							" (SELECT a.dt as dt, SUM(a.dispatch_bottle) as bwfl_dispatchd_bottl, " +
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as bwfl_sale, " +
							" SUM(a.duty) as bwfl_duty, SUM(j.quantity) as quantity, " +
							" 0 as fl2d_dispatchd_bottl, 0 as fl2d_sale, 0 as fl2d_duty " +
							" FROM bwfl_license.fl2_stock_trxn_bwfl a, distillery.packaging_details j, " +
							" bwfl_license.gatepass_to_districtwholesale_bwfl z  " +
							" WHERE z.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' " +
							" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND  a.int_pckg_id=j.package_id  " +
							" AND z.int_bwfl_id=a.int_bwfl_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt "+
							" GROUP BY a.dt " +
							" UNION " +
							" SELECT a.dt as dt, 0 as bwfl_dispatchd_bottl, 0 as bwfl_sale, 0 as bwfl_duty, " +
							" SUM(j.quantity) as quantity, SUM(a.dispatch_bottle) as fl2d_dispatchd_bottl, " +
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as fl2d_sale, " +
							" SUM(a.cal_duty) as fl2d_duty " +
							" FROM fl2d.fl2d_stock_trxn a, distillery.packaging_details j, licence.fl2_2b_2d m, " +
							" fl2d.gatepass_to_districtwholesale_fl2d z  " +
							" WHERE z.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND a.int_pckg_id=j.package_id AND a.int_fl2d_id=m.int_app_id AND m.vch_approval='V' " +
							" AND z.int_fl2d_id=a.int_fl2d_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt "+
							" GROUP BY a.dt)x " +
							" GROUP BY x.dt ORDER BY x.dt ";

			
			reportQuery1 = 	" SELECT x.dt, x.bwfl_name, x.vch_license_type, x.licence_nmbr, " +
							" SUM(x.sale) as sale, SUM(x.duty) as duty " +
							" FROM  " +
							" (SELECT a.dt as dt, SUM(a.dispatch_bottle) as dispatchd_bottl, " +
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale, " +
							" SUM(a.duty) as duty, SUM(j.quantity) as quantity, m.vch_firm_name ||'-'||n.description as bwfl_name, " +
							" CASE WHEN m.vch_license_type='1' THEN 'BWFL2A' " +
							" WHEN m.vch_license_type='2' THEN 'BWFL2B' " +
							" WHEN m.vch_license_type='3' THEN 'BWFL2C'  " +
							" WHEN m.vch_license_type='4' THEN 'BWFL2D' end as vch_license_type, " +
							" m.vch_license_number as licence_nmbr " +
							" FROM bwfl_license.fl2_stock_trxn_bwfl a, distillery.packaging_details j, " +
							" bwfl.registration_of_bwfl_lic_holder m, public.district n, " +
							" bwfl_license.gatepass_to_districtwholesale_bwfl z   " +
							" WHERE z.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND a.int_pckg_id=j.package_id AND a.int_bwfl_id=m.int_id AND m.vch_approval='V' "+
							" AND COALESCE(m.vch_firm_district_id,'0')=n.districtid::text  " +
							" AND z.int_bwfl_id=a.int_bwfl_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt "+
							" GROUP BY a.dt, m.vch_firm_name, m.vch_license_type, m.vch_license_number,n.description " +
							" UNION " +
							" SELECT a.dt as dt, SUM(a.dispatch_bottle) as dispatchd_bottl,  " +
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale, " +
							" SUM(a.cal_duty) as duty, SUM(j.quantity) as quantity, m.vch_firm_name ||'-'||n.description as bwfl_name, " +
							" m.vch_license_type, m.vch_licence_no as licence_nmbr " +
							" FROM fl2d.fl2d_stock_trxn a, distillery.packaging_details j, licence.fl2_2b_2d m, " +
							" public.district n, fl2d.gatepass_to_districtwholesale_fl2d z  " +
							" WHERE z.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND  a.int_pckg_id=j.package_id AND a.int_fl2d_id=m.int_app_id AND m.vch_approval='V' "+
							" AND COALESCE(m.core_district_id,0)=n.districtid " +
							" AND z.int_fl2d_id=a.int_fl2d_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt "+
							" GROUP BY a.dt, m.vch_firm_name, m.vch_license_type, m.vch_licence_no, n.description)x  " +
							" GROUP BY x.dt, x.vch_license_type, x.licence_nmbr, x.bwfl_name  " +
							" ORDER BY x.vch_license_type, x.licence_nmbr,x.bwfl_name, x.dt ";

			
		reportQueryBWFL = 	" SELECT x.dt, x.bwfl_name, x.vch_license_type, x.licence_nmbr, " +
							" SUM(x.sale) as sale, SUM(x.duty) as duty " +
							" FROM " +
							" (SELECT a.dt as dt, SUM(a.dispatch_bottle) as dispatchd_bottl, " +
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale, " +
							" SUM(a.duty) as duty, SUM(j.quantity) as quantity, m.vch_firm_name ||'-'||n.description as bwfl_name, " +
							" CASE WHEN m.vch_license_type='1' THEN 'BWFL2A' " +
							" WHEN m.vch_license_type='2' THEN 'BWFL2B' " +
							" WHEN m.vch_license_type='3' THEN 'BWFL2C'  " +
							" WHEN m.vch_license_type='4' THEN 'BWFL2D' end as vch_license_type, " +
							" m.vch_license_number as licence_nmbr  " +
							" FROM bwfl_license.fl2_stock_trxn_bwfl a, distillery.packaging_details j, " +
							" bwfl.registration_of_bwfl_lic_holder m, " +
							" public.district n, bwfl_license.gatepass_to_districtwholesale_bwfl z  " +
							" WHERE z.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND a.int_pckg_id=j.package_id AND a.int_bwfl_id=m.int_id AND m.vch_approval='V' "+
							" AND COALESCE(m.vch_firm_district_id,'0')= n.districtid::text " +
							" AND z.int_bwfl_id=a.int_bwfl_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt "+
							" AND a.int_bwfl_id='"+act.getBwflId()+"'  " +							
							" GROUP BY a.dt, m.vch_firm_name, m.vch_license_type, m.vch_license_number, n.description)x  " +
							" GROUP BY x.dt, x.vch_license_type, x.licence_nmbr, x.bwfl_name  " +
							" ORDER BY x.vch_license_type, x.licence_nmbr,x.bwfl_name, x.dt ";

		
		reportQueryFL2D = 	" SELECT x.dt, x.bwfl_name, x.vch_license_type, x.licence_nmbr, " +
							" SUM(x.sale) as sale, SUM(x.duty) as duty " +
							" FROM  " +
							" (SELECT a.dt as dt, SUM(a.dispatch_bottle) as dispatchd_bottl, " +
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale, " +
							" SUM(a.cal_duty) as duty, SUM(j.quantity) as quantity, m.vch_firm_name ||'-'||n.description as bwfl_name, " +
							" m.vch_license_type, m.vch_licence_no as licence_nmbr " +
							" FROM fl2d.fl2d_stock_trxn a, distillery.packaging_details j, licence.fl2_2b_2d m, " +
							" public.district n, fl2d.gatepass_to_districtwholesale_fl2d z   " +
							" WHERE z.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND a.int_pckg_id=j.package_id AND a.int_fl2d_id=m.int_app_id AND m.vch_approval='V' "+
							" AND COALESCE(m.core_district_id,0)=n.districtid " +
							" AND z.int_fl2d_id=a.int_fl2d_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt "+
							" AND a.int_fl2d_id='"+act.getFl2DId()+"' " +
							" GROUP BY a.dt, m.vch_firm_name, m.vch_license_type, m.vch_licence_no,n.description)x  " +
							" GROUP BY x.dt, x.vch_license_type, x.licence_nmbr, x.bwfl_name  " +
							" ORDER BY x.vch_license_type, x.licence_nmbr,x.bwfl_name, x.dt ";

		
		
			if (act.getRadio().equalsIgnoreCase("CD")) {
				pst = con.prepareStatement(reportQuery);
				 //System.out.println("reportQuery-----consolidated---------"+reportQuery);
			} else if (act.getRadio().equalsIgnoreCase("BFW")) {
				pst = con.prepareStatement(reportQuery1);
				 //System.out.println("reportQuery1----dist wise---------"+reportQuery1);
			} else if (act.getRadio().equalsIgnoreCase("SBW")) {
				pst = con.prepareStatement(reportQueryBWFL);
				 //System.out.println("reportQueryBWFL----dist wise---------"+reportQueryBWFL);
			} else if (act.getRadio().equalsIgnoreCase("SFL")) {
				pst = con.prepareStatement(reportQueryFL2D);
				 //System.out.println("reportQueryFL2D----dist wise---------"+reportQueryFL2D);
			}

			
			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				if (act.getRadio().equalsIgnoreCase("CD")) {
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "DispatchStockReport_BWFL_FL2D.jasper");
				} else if (act.getRadio().equalsIgnoreCase("BFW") || act.getRadio().equalsIgnoreCase("SBW") || act.getRadio().equalsIgnoreCase("SFL")) {
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "DispatchStockReportNameWise.jasper");
				} 

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "DispatchStockReport_BWFL_FL2D" + "-" + n + ".pdf");
				act.setPdfname("DispatchStockReport_BWFL_FL2D" + "-" + n + ".pdf");
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
	
	//----------------------generate excel for date wise report------------------------------
	
	public boolean write(DispatchReportBWFL_FL2DAction act)
	{

		Connection con = null;
		con = ConnectionToDataBase.getConnection();
		
		double bwfl_sale = 0;
		double bwfl_duty = 0;
		double fl2d_sale = 0;
		double fl2d_duty = 0;
		double total_sale = 0;
		double total_duty = 0;
		String sql = "";
		

		sql = 	" SELECT x.dt, SUM(x.bwfl_dispatchd_bottl) as bwfl_dispatchd_bottl,  " +
				" SUM(x.bwfl_sale) as bwfl_sale, SUM(x.bwfl_duty) as bwfl_duty, " +
				" SUM(x.fl2d_dispatchd_bottl) as fl2d_dispatchd_bottl, SUM(x.fl2d_sale) as fl2d_sale, " +
				" SUM(x.fl2d_duty) as fl2d_duty " +
				" FROM " +
				" (SELECT a.dt as dt, SUM(a.dispatch_bottle) as bwfl_dispatchd_bottl, " +
				" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as bwfl_sale, " +
				" SUM(a.duty) as bwfl_duty, SUM(j.quantity) as quantity, " +
				" 0 as fl2d_dispatchd_bottl, 0 as fl2d_sale, 0 as fl2d_duty " +
				" FROM bwfl_license.fl2_stock_trxn_bwfl a, distillery.packaging_details j, " +
				" bwfl_license.gatepass_to_districtwholesale_bwfl z  " +
				" WHERE z.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' " +
				" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
				" AND  a.int_pckg_id=j.package_id  " +
				" AND z.int_bwfl_id=a.int_bwfl_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt "+
				" GROUP BY a.dt " +
				" UNION " +
				" SELECT a.dt as dt, 0 as bwfl_dispatchd_bottl, 0 as bwfl_sale, 0 as bwfl_duty, " +
				" SUM(j.quantity) as quantity, SUM(a.dispatch_bottle) as fl2d_dispatchd_bottl, " +
				" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as fl2d_sale, " +
				" SUM(a.cal_duty) as fl2d_duty " +
				" FROM fl2d.fl2d_stock_trxn a, distillery.packaging_details j, licence.fl2_2b_2d m, " +
				" fl2d.gatepass_to_districtwholesale_fl2d z  " +
				" WHERE z.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' " +
				" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
				" AND a.int_pckg_id=j.package_id AND a.int_fl2d_id=m.int_app_id AND m.vch_approval='V' " +
				" AND z.int_fl2d_id=a.int_fl2d_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt "+
				" GROUP BY a.dt)x " +
				" GROUP BY x.dt ORDER BY x.dt ";

		
		String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		try {
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 4000);
			worksheet.setColumnWidth(2, 8000);
			worksheet.setColumnWidth(3, 8000);
			worksheet.setColumnWidth(4, 8000);
			worksheet.setColumnWidth(5, 8000);
			worksheet.setColumnWidth(6, 10000);
			worksheet.setColumnWidth(7, 9000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" Date Wise Dispatches Of BWFL / FL2D From "+ " " + Utility.convertUtilDateToSQLDate(act.getFromDate())+ " " 
			+ " To " + " "+ Utility.convertUtilDateToSQLDate(act.getToDate()));
			
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
			cellhead2.setCellValue("Date");
			cellhead2.setCellStyle(cellStyle);
						
			
			XSSFCell cellhead7 = rowhead.createCell((int) 2);
			cellhead7.setCellValue("BWFL Sale(BL)");
			cellhead7.setCellStyle(cellStyle);
			
			XSSFCell cellhead8 = rowhead.createCell((int) 3);
			cellhead8.setCellValue("BWFL Duty");
			cellhead8.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 4);
			cellhead9.setCellValue("FL2D Sale(BL)");
			cellhead9.setCellStyle(cellStyle);
			
			XSSFCell cellhead10 = rowhead.createCell((int) 5);
			cellhead10.setCellValue("FL2D Duty");
			cellhead10.setCellStyle(cellStyle);
			
			int i = 0;
			while (rs.next()) {
				Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);
				

				bwfl_sale = bwfl_sale + rs.getDouble("bwfl_sale");
				bwfl_duty = bwfl_duty + rs.getDouble("bwfl_duty");

				fl2d_sale = fl2d_sale + rs.getDouble("fl2d_sale");
				fl2d_duty = fl2d_duty + rs.getDouble("fl2d_duty");
				
				total_sale = total_sale + (rs.getDouble("bwfl_sale")+rs.getDouble("fl2d_sale"));
				total_duty = total_duty + (rs.getDouble("bwfl_duty")+rs.getDouble("fl2d_duty"));

				
				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k - 1);
				
				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(date);
				
				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getDouble("bwfl_sale"));
				
				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getDouble("bwfl_duty"));
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getDouble("fl2d_sale"));
				
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("fl2d_duty"));
								
			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "_DispatchStockReport_BWFL_FL2D.xls");
			act.setExlname(n + "_DispatchStockReport_BWFL_FL2D");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);

			XSSFCell cellA2 = row1.createCell((int) 1);
			cellA2.setCellValue("Total: ");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 2);
			cellA3.setCellValue(bwfl_sale);
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 3);
			cellA4.setCellValue(bwfl_duty);
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(fl2d_sale);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(fl2d_duty);
			cellA6.setCellStyle(cellStyle);
			
			
			XSSFRow row2 = worksheet.createRow((int) k + 2);
						

			XSSFCell cellA11 = row2.createCell((int) 0);
			cellA11.setCellValue(" ");
			cellA11.setCellStyle(cellStyle);

			XSSFCell cellA12 = row2.createCell((int) 1);
			cellA12.setCellValue("Final Total Sale:");
			cellA12.setCellStyle(cellStyle);

			XSSFCell cellA113 = row2.createCell((int) 2);
			cellA113.setCellValue(total_sale);
			cellA113.setCellStyle(cellStyle);

			XSSFCell cellA14 = row2.createCell((int) 3);
			cellA14.setCellValue(" ");
			cellA14.setCellStyle(cellStyle);

			XSSFCell cellA15 = row2.createCell((int) 4);
			cellA15.setCellValue("Final Total Duty:");
			cellA15.setCellStyle(cellStyle);

			XSSFCell cellA16 = row2.createCell((int) 5);
			cellA16.setCellValue(total_duty);
			cellA16.setCellStyle(cellStyle);
			
			
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

			flag = true;
			act.setExcelFlag(true);
			con.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return flag;
	
	}
	
	//----------------------generate excel for bwfl and fl2d wise report------------------------------
	
	public boolean writeNameWise(DispatchReportBWFL_FL2DAction act)
	{


		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double bwfl_sale = 0;
		double bwfl_duty = 0;
		
		String reportQueryBWFL = null;
		String reportQueryFL2D = null;
		String sql = "";

					sql = 	" SELECT x.dt, x.bwfl_name, x.vch_license_type, x.licence_nmbr, " +
							" SUM(x.sale) as sale, SUM(x.duty) as duty " +
							" FROM  " +
							" (SELECT a.dt as dt, SUM(a.dispatch_bottle) as dispatchd_bottl, " +
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale, " +
							" SUM(a.duty) as duty, SUM(j.quantity) as quantity, m.vch_firm_name ||'-'||n.description as bwfl_name, " +
							" CASE WHEN m.vch_license_type='1' THEN 'BWFL2A' " +
							" WHEN m.vch_license_type='2' THEN 'BWFL2B' " +
							" WHEN m.vch_license_type='3' THEN 'BWFL2C'  " +
							" WHEN m.vch_license_type='4' THEN 'BWFL2D' end as vch_license_type, " +
							" m.vch_license_number as licence_nmbr " +
							" FROM bwfl_license.fl2_stock_trxn_bwfl a, distillery.packaging_details j, " +
							" bwfl.registration_of_bwfl_lic_holder m, public.district n, " +
							" bwfl_license.gatepass_to_districtwholesale_bwfl z   " +
							" WHERE z.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND a.int_pckg_id=j.package_id AND a.int_bwfl_id=m.int_id AND m.vch_approval='V' "+
							" AND COALESCE(m.vch_firm_district_id,'0')=n.districtid::text  " +
							" AND z.int_bwfl_id=a.int_bwfl_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt "+
							" GROUP BY a.dt, m.vch_firm_name, m.vch_license_type, m.vch_license_number,n.description " +
							" UNION " +
							" SELECT a.dt as dt, SUM(a.dispatch_bottle) as dispatchd_bottl,  " +
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale, " +
							" SUM(a.cal_duty) as duty, SUM(j.quantity) as quantity, m.vch_firm_name ||'-'||n.description as bwfl_name, " +
							" m.vch_license_type, m.vch_licence_no as licence_nmbr " +
							" FROM fl2d.fl2d_stock_trxn a, distillery.packaging_details j, licence.fl2_2b_2d m, " +
							" public.district n, fl2d.gatepass_to_districtwholesale_fl2d z  " +
							" WHERE z.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND  a.int_pckg_id=j.package_id AND a.int_fl2d_id=m.int_app_id AND m.vch_approval='V' "+
							" AND COALESCE(m.core_district_id,0)=n.districtid " +
							" AND z.int_fl2d_id=a.int_fl2d_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt "+
							" GROUP BY a.dt, m.vch_firm_name, m.vch_license_type, m.vch_licence_no, n.description)x  " +
							" GROUP BY x.dt, x.vch_license_type, x.licence_nmbr, x.bwfl_name  " +
							" ORDER BY x.vch_license_type, x.licence_nmbr,x.bwfl_name, x.dt ";
		
					
					reportQueryBWFL = 	" SELECT x.dt, x.bwfl_name, x.vch_license_type, x.licence_nmbr, " +
							" SUM(x.sale) as sale, SUM(x.duty) as duty " +
							" FROM " +
							" (SELECT a.dt as dt, SUM(a.dispatch_bottle) as dispatchd_bottl, " +
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale, " +
							" SUM(a.duty) as duty, SUM(j.quantity) as quantity, m.vch_firm_name ||'-'||n.description as bwfl_name, " +
							" CASE WHEN m.vch_license_type='1' THEN 'BWFL2A' " +
							" WHEN m.vch_license_type='2' THEN 'BWFL2B' " +
							" WHEN m.vch_license_type='3' THEN 'BWFL2C'  " +
							" WHEN m.vch_license_type='4' THEN 'BWFL2D' end as vch_license_type, " +
							" m.vch_license_number as licence_nmbr  " +
							" FROM bwfl_license.fl2_stock_trxn_bwfl a, distillery.packaging_details j, " +
							" bwfl.registration_of_bwfl_lic_holder m, " +
							" public.district n, bwfl_license.gatepass_to_districtwholesale_bwfl z  " +
							" WHERE z.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND a.int_pckg_id=j.package_id AND a.int_bwfl_id=m.int_id AND m.vch_approval='V' "+
							" AND COALESCE(m.vch_firm_district_id,'0')= n.districtid::text " +
							" AND z.int_bwfl_id=a.int_bwfl_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt "+
							" AND a.int_bwfl_id='"+act.getBwflId()+"'  " +							
							" GROUP BY a.dt, m.vch_firm_name, m.vch_license_type, m.vch_license_number, n.description)x  " +
							" GROUP BY x.dt, x.vch_license_type, x.licence_nmbr, x.bwfl_name  " +
							" ORDER BY x.vch_license_type, x.licence_nmbr,x.bwfl_name, x.dt ";

		
		reportQueryFL2D = 	" SELECT x.dt, x.bwfl_name, x.vch_license_type, x.licence_nmbr, " +
							" SUM(x.sale) as sale, SUM(x.duty) as duty " +
							" FROM  " +
							" (SELECT a.dt as dt, SUM(a.dispatch_bottle) as dispatchd_bottl, " +
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale, " +
							" SUM(a.cal_duty) as duty, SUM(j.quantity) as quantity, m.vch_firm_name ||'-'||n.description as bwfl_name, " +
							" m.vch_license_type, m.vch_licence_no as licence_nmbr " +
							" FROM fl2d.fl2d_stock_trxn a, distillery.packaging_details j, licence.fl2_2b_2d m, " +
							" public.district n, fl2d.gatepass_to_districtwholesale_fl2d z   " +
							" WHERE z.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND a.int_pckg_id=j.package_id AND a.int_fl2d_id=m.int_app_id AND m.vch_approval='V' "+
							" AND COALESCE(m.core_district_id,0)=n.districtid " +
							" AND z.int_fl2d_id=a.int_fl2d_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt "+
							" AND a.int_fl2d_id='"+act.getFl2DId()+"' " +
							" GROUP BY a.dt, m.vch_firm_name, m.vch_license_type, m.vch_licence_no,n.description)x  " +
							" GROUP BY x.dt, x.vch_license_type, x.licence_nmbr, x.bwfl_name  " +
							" ORDER BY x.vch_license_type, x.licence_nmbr,x.bwfl_name, x.dt ";

		
		String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;

		try {			
			
			if (act.getRadio().equalsIgnoreCase("BFW")) {
				pstmt = con.prepareStatement(sql);
				
			} else if (act.getRadio().equalsIgnoreCase("SBW")) {
				pstmt = con.prepareStatement(reportQueryBWFL);
				
			} else if (act.getRadio().equalsIgnoreCase("SFL")) {
				pstmt = con.prepareStatement(reportQueryFL2D);
				
			}

			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");

			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 4000);

			worksheet.setColumnWidth(2, 4000);

			worksheet.setColumnWidth(3, 20000);
			worksheet.setColumnWidth(4, 7000);
			worksheet.setColumnWidth(5, 7000);
			worksheet.setColumnWidth(6, 7000);
			worksheet.setColumnWidth(7, 10000);
			worksheet.setColumnWidth(8, 9000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" Dispatches Of BWFL / FL2D From "+ " " + Utility.convertUtilDateToSQLDate(act.getFromDate())+ " " + 
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
			cellhead2.setCellValue("Date");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("License Type");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Licensee Name");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("License Number");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Sale(BL)");
			cellhead6.setCellStyle(cellStyle);

			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Duty");
			cellhead7.setCellStyle(cellStyle);
			

			while (rs.next()) {

				Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);
				

				bwfl_sale = bwfl_sale + rs.getDouble("sale");
				bwfl_duty = bwfl_duty + rs.getDouble("duty");
				
				
				k++;

				XSSFRow row1 = worksheet.createRow((int) k);

				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(date);

				XSSFCell cellK1 = row1.createCell((int) 2);
				cellK1.setCellValue(rs.getString("vch_license_type"));

				XSSFCell cellC1 = row1.createCell((int) 3);
				cellC1.setCellValue(rs.getString("bwfl_name"));

				XSSFCell cellD1 = row1.createCell((int) 4);
				cellD1.setCellValue(rs.getString("licence_nmbr"));

				XSSFCell cellE1 = row1.createCell((int) 5);
				cellE1.setCellValue(rs.getDouble("sale"));

				XSSFCell cellF1 = row1.createCell((int) 6);
				cellF1.setCellValue(rs.getDouble("duty"));
	

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "_DispatchStockReportNameWise.xls");
			act.setExlname(n + "_DispatchStockReportNameWise");

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
			cellA5.setCellValue(" Total ");
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(bwfl_sale);
			cellA6.setCellStyle(cellStyle);

			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(bwfl_duty);
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
