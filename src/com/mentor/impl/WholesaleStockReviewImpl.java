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

import com.mentor.action.WholesaleStockAction;
import com.mentor.action.WholesaleStockReviewAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class WholesaleStockReviewImpl {

	// =======================print report for
	// CL2=================================

	public void printReportCl2(WholesaleStockReviewAction act) {

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
		String filter = "";

		try {
			con = ConnectionToDataBase.getConnection();

			reportQuery = " SELECT distinct  x.loging_id, x.licence_no, x.licensee_name, x.description, "
					+ " SUM(x.cl25_opn) as cl25_opn , SUM(x.cl36_opn) as cl36_opn, "
					+ " SUM(x.cl45_opn) as cl45_opn, SUM(x.cl25_rcv) as cl25_rcv , SUM(x.cl25_dis) as cl25_dis, "
					+ " SUM(x.cl36_rcv) as cl36_rcv, SUM(x.cl36_dis) as cl36_dis, SUM(x.cl45_rcv) as cl45_rcv, "
					+ " SUM(x.cl45_dis) as cl45_dis "
					+ " FROM "
					+ " (SELECT y.loging_id, y.licence_no, y.licensee_name, y.description, "
					+ " (COALESCE(y.cl25,0)+COALESCE(y.cl25_diff,0)) as cl25_opn, "
					+ " (COALESCE(y.cl36,0)+COALESCE(y.cl36_diff,0)) as cl36_opn, "
					+ " (COALESCE(y.cl45,0)+COALESCE(y.cl45_diff,0)) as cl45_opn, "
					+ " 0 as cl25_rcv, 0 as cl25_dis, 0 as cl36_rcv, 0 as cl36_dis, 0 as cl45_rcv, 0 as cl45_dis "
					+ " FROM "
					+ " (SELECT a.licence_no, a.licensee_name, a.no_of_box_25prc as cl25, "
					+ " a.no_of_box_36prc as cl36, a.no_of_box_42prc as cl45, a.loging_id, "
					+ " z.cl25_diff, z.cl36_diff, z.cl45_diff, "
					+ " (SELECT c.description FROM public.district c WHERE a.district_id=c.districtid) "
					+ " FROM fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (SELECT b.int_id, b.licence_no, "
					+ " (SUM(COALESCE(b.cl25r,0)-COALESCE(b.cl25d,0))) as cl25_diff, "
					+ " (SUM(COALESCE(b.cl36r,0)-COALESCE(b.cl36d,0))) as cl36_diff, "
					+ " (SUM(COALESCE(b.cl45r,0)-COALESCE(b.cl45d,0))) as cl45_diff  "
					+ " FROM fl2d.cl2_trxn b "
					+ " WHERE b.dt_date BETWEEN '2018-08-16' AND "
					+ " (to_date('"
					+ Utility.convertUtilDateToSQLDate(act.getFromDate())
					+ "','YYYY-MM-DD')  - INTERVAL '1' DAY) "
					+ " GROUP BY b.int_id, b.licence_no)z ON a.loging_id=z.int_id AND a.licence_no=z.licence_no "
					+ " WHERE a.licence_type='CL2')y "
					+ " UNION "
					+ " SELECT distinct d.int_id, e.licence_no, e.licensee_name, "
					+ " (SELECT c.description FROM public.district c WHERE e.district_id=c.districtid), "
					+ " 0 as cl25_opn, 0 as cl36_opn, 0 as cl45_opn, "
					+ " COALESCE(d.cl25r,0) as cl25_rcv, COALESCE(d.cl25d,0) as cl25_dis, "
					+ " COALESCE(d.cl36r,0) as cl36_rcv, COALESCE(d.cl36d,0) as cl36_dis, "
					+ " COALESCE(d.cl45r,0) as cl45_rcv, COALESCE(d.cl45d,0) as cl45_dis "
					+ " FROM fl2d.cl2_trxn d, fl2d.opening_stock_fl2_fl2b_cl e  "
					+ " WHERE d.licence_type='CL2' AND e.loging_id=d.int_id AND d.licence_no=e.licence_no "
					+ " AND d.dt_date='"
					+ Utility.convertUtilDateToSQLDate(act.getFromDate())
					+ "')x  "
					+ " GROUP BY x.description, x.loging_id, x.licence_no, x.licensee_name  "
					+ " ORDER BY x.description, x.licence_no";

			pst = con.prepareStatement(reportQuery);
			// System.out.println("reportQuery-----CL2---------" + reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",
						Utility.convertUtilDateToSQLDate(act.getFromDate()));

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "CL2_StockReviewReport.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "CL2_StockReviewReport" + "-" + n + ".pdf");
				act.setPdfName("CL2_StockReviewReport" + "-" + n + ".pdf");
				act.setPrintFlag(true);
			} else {
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

	// =======================print report for
	// FL2=================================

	public void printReportFl2(WholesaleStockReviewAction act) {

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
		String filter = "";

		try {
			con = ConnectionToDataBase.getConnection();

			reportQuery = "SELECT c.licence_type, c.licence_no, c.description, c.licensee_name,c.fl750_opening, c.fl375_opening,"
					+ " c.fl180_opening, c.other_opening, c.fl750d, c.fl750r,c.fl375d, c.fl375r, c.fl180d, c.fl180r,c.other_d, "
					+ "c.other_r from"
					+ " (SELECT y.licence_type, y.licence_no, y.description, y.licensee_name, "
					+ "(COALESCE(y.fl750,0)+y.fl2_750_ml) as fl750_opening, (COALESCE(y.fl375,0)+y.fl2_375_ml) as fl375_opening,  "
					+ "(COALESCE(y.fl180,0)+y.fl2_180_ml) as fl180_opening, (COALESCE(y.other,0)+y.fl2_other_ml) as other_opening, "
					+ " COALESCE(z.fl750d,0) as fl750d , COALESCE(z.fl750r,0) as fl750r, COALESCE(z.fl375d,0) as fl375d, "
					+ " COALESCE(z.fl375r,0) as fl375r, COALESCE(z.fl180d,0) as fl180d, "
					+ " COALESCE(z.fl180r,0) as fl180r, COALESCE(z.other_d,0) as other_d, COALESCE(z.other_r,0) as other_r "
					+ " from "
					+ " (SELECT a.licence_type, a.licence_no, a.licensee_name, a.fl2_750_ml, a.fl2_375_ml, "
					+ " a.fl2_180_ml, a.fl2_other_ml, a.loging_id, x.fl750, x.fl375, x.fl180, x.other, "
					+ " (SELECT c.description FROM public.district c where c.districtid=a.district_id) "
					+ " FROM fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN "
					+ " (SELECT (COALESCE(sum(b.fl750r),0)-COALESCE(sum(b.fl750d),0)) as fl750, (COALESCE(sum(b.fl375r),0)-COALESCE(sum(b.fl375d),0)) as fl375 , "
					+ " (COALESCE(sum(b.fl180r),0)-COALESCE(sum(b.fl180d),0)) as fl180, (COALESCE(sum(b.other_r),0)-COALESCE(sum(b.other_d),0)) as other, "
					+ " b.int_id, b.licence_no FROM fl2d.fl2_trxn b "
					+ " WHERE b.opening_date BETWEEN '2018-08-16' AND  "
					+ " (to_date('"
					+ Utility.convertUtilDateToSQLDate(act.getFromDate())
					+ "','YYYY-MM-DD')  - INTERVAL '1' DAY) group by b.int_id, b.licence_no) x "
					+ " on a.loging_id = CAST (x.int_id AS INTEGER) and a.licence_no=x.licence_no where a.licence_type='FL2' ) y "
					+ " LEFT JOIN "
					+ " (SELECT licence_no, fl750d, fl750r, fl375d, fl375r, fl180d, fl180r, other_d, other_r, int_id "
					+ " FROM fl2d.fl2_trxn where opening_date='"
					+ Utility.convertUtilDateToSQLDate(act.getFromDate())
					+ "' and licence_type='FL2') z "
					+ " on y.licence_no=z.licence_no) c group by c.licence_type, c.licence_no, c.description, "
					+ "c.licensee_name,c.fl750_opening, c.fl375_opening, c.fl180_opening, c.other_opening, "
					+ "c.fl750d, c.fl750r,c.fl375d, c.fl375r, c.fl180d, c.fl180r,c.other_d, c.other_r order by c.description ";

			pst = con.prepareStatement(reportQuery);
			System.out.println("reportQuery-----FL2---------" + reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",
						Utility.convertUtilDateToSQLDate(act.getFromDate()));

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "FL2_WholesaleStockReport2.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "FL2_WholesaleStockReport2" + "-" + n
								+ ".pdf");
				act.setPdfName("FL2_WholesaleStockReport2" + "-" + n + ".pdf");
				act.setPrintFlag(true);
			} else {
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

	// =======================print report for
	// FL2B=================================

	public void printReportFl2B(WholesaleStockReviewAction act) {

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
		String filter = "";

		try {
			con = ConnectionToDataBase.getConnection();

			reportQuery = " SELECT c.licence_type, c.licence_no, c.description, c.licensee_name, c.fl2b650_opening,"
					+ " c.fl2b330_opening, c.fl2b500_opening, c.other_opening,c.fl2b650_d, c.fl2b650_r, c.fl2b500_d, "
					+ " c.fl2b500_r, c.fl2b330_d, c.fl2b330_r, c.fl2bother_d, c.fl2bother_r from"
					+ " (SELECT y.licence_type, y.licence_no, y.description, y.licensee_name, "
					+ " (COALESCE(y.fl2b650,0)+y.fl2b_650_ml) as fl2b650_opening, "
					+ " (COALESCE(y.fl2b330,0)+y.fl2b_330_ml) as fl2b330_opening, "
					+ " (COALESCE(y.fl2b500,0)+y.fl2b_500_ml) as fl2b500_opening, "
					+ " (COALESCE(y.other,0)+y.fl2b_other_ml) as other_opening, "
					+ " COALESCE(fl2b650_d, 0) as fl2b650_d, COALESCE(fl2b650_r, 0) as fl2b650_r, COALESCE(fl2b500_d, 0) as fl2b500_d, "
					+ " COALESCE(fl2b500_r,0) as fl2b500_r, COALESCE(fl2b330_d, 0) as fl2b330_d, COALESCE(fl2b330_r,0) as fl2b330_r,"
					+ " COALESCE(fl2bother_d,0) as fl2bother_d, COALESCE(fl2bother_r,0) as fl2bother_r from (SELECT a.licence_type, a.licence_no, a.licensee_name,"
					+ " a.fl2b_650_ml, a.fl2b_330_ml, a.fl2b_500_ml, a.fl2b_other_ml, a.loging_id, x.fl2b650,"
					+ " x.fl2b330, x.fl2b500, x.other, (SELECT c.description FROM public.district c where "
					+ "c.districtid=a.district_id) FROM fl2d.opening_stock_fl2_fl2b_cl a  LEFT JOIN "
					+ "(SELECT (COALESCE(sum(b.fl2b650_r),0)-COALESCE(sum(b.fl2b650_d),0)) as fl2b650, (COALESCE(sum(b.fl2b330_r),0)-COALESCE(sum(b.fl2b330_d),0))"
					+ " as fl2b330 , (COALESCE(sum(b.fl2b500_r),0)-COALESCE(sum(b.fl2b500_d),0)) as fl2b500, "
					+ "(COALESCE(sum(b.fl2bother_r),0)-COALESCE(sum(b.fl2bother_d),0)) as other, b.int_id, b.licence_no FROM fl2d.fl2b_trxn"
					+ " b WHERE b.opening_date BETWEEN '2018-08-16' AND  (to_date"
					+ "('"
					+ Utility.convertUtilDateToSQLDate(act.getFromDate())
					+ "','YYYY-MM-DD')  - "
					+ "INTERVAL '1' DAY) group by b.int_id, b.licence_no) x on a.loging_id = "
					+ "CAST (x.int_id AS INTEGER) and a.licence_no=x.licence_no where a.licence_type='FL2B') y "
					+ "LEFT JOIN (SELECT licence_no, fl2b650_d, fl2b650_r, fl2b500_d, fl2b500_r, fl2b330_d, "
					+ "fl2b330_r, fl2bother_d, fl2bother_r, int_id FROM fl2d.fl2b_trxn where opening_date="
					+ "'"
					+ Utility.convertUtilDateToSQLDate(act.getFromDate())
					+ "' and licence_type='FL2B') z "
					+ "on y.licence_no=z.licence_no) c group by c.licence_type, c.licence_no, c.description, "
					+ "c.licensee_name, c.fl2b650_opening, c.fl2b330_opening, c.fl2b500_opening, c.other_opening"
					+ ",c.fl2b650_d, c.fl2b650_r, c.fl2b500_d, c.fl2b500_r, c.fl2b330_d, c.fl2b330_r, c.fl2bother_d,"
					+ " c.fl2bother_r order by c.description";

			pst = con.prepareStatement(reportQuery);
			System.out.println("reportQuery-----FL2B---------" + reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",
						Utility.convertUtilDateToSQLDate(act.getFromDate()));

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "FL2B_WholesaleStockReport2.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "FL2B_WholesaleStockReport2" + "-" + n
								+ ".pdf");
				act.setPdfName("FL2B_WholesaleStockReport2" + "-" + n + ".pdf");
				act.setPrintFlag(true);
			} else {
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

	// --------------------------------generate excel for
	// CL2--------------------

	public boolean writeCL2(WholesaleStockReviewAction act) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double cl25_opn = 0;
		double cl36_opn = 0;
		double cl45_opn = 0; 

		double cl25_rcv = 0;
		double cl25_dis = 0;
		double cl36_rcv = 0; 

		double cl36_dis = 0;
		double cl45_rcv = 0;
		double cl45_dis = 0; 

		String sql = "";

		sql = " SELECT distinct  x.loging_id, x.licence_no, x.licensee_name, x.description, "
				+ " SUM(x.cl25_opn) as cl25_opn , SUM(x.cl36_opn) as cl36_opn, "
				+ " SUM(x.cl45_opn) as cl45_opn, SUM(x.cl25_rcv) as cl25_rcv , SUM(x.cl25_dis) as cl25_dis, "
				+ " SUM(x.cl36_rcv) as cl36_rcv, SUM(x.cl36_dis) as cl36_dis, SUM(x.cl45_rcv) as cl45_rcv, "
				+ " SUM(x.cl45_dis) as cl45_dis "
				+ " FROM "
				+ " (SELECT y.loging_id, y.licence_no, y.licensee_name, y.description, "
				+ " (COALESCE(y.cl25,0)+COALESCE(y.cl25_diff,0)) as cl25_opn, "
				+ " (COALESCE(y.cl36,0)+COALESCE(y.cl36_diff,0)) as cl36_opn, "
				+ " (COALESCE(y.cl45,0)+COALESCE(y.cl45_diff,0)) as cl45_opn, "
				+ " 0 as cl25_rcv, 0 as cl25_dis, 0 as cl36_rcv, 0 as cl36_dis, 0 as cl45_rcv, 0 as cl45_dis "
				+ " FROM "
				+ " (SELECT a.licence_no, a.licensee_name, a.no_of_box_25prc as cl25, "
				+ " a.no_of_box_36prc as cl36, a.no_of_box_42prc as cl45, a.loging_id, "
				+ " z.cl25_diff, z.cl36_diff, z.cl45_diff, "
				+ " (SELECT c.description FROM public.district c WHERE a.district_id=c.districtid) "
				+ " FROM fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (SELECT b.int_id, b.licence_no, "
				+ " (SUM(COALESCE(b.cl25r,0)-COALESCE(b.cl25d,0))) as cl25_diff, "
				+ " (SUM(COALESCE(b.cl36r,0)-COALESCE(b.cl36d,0))) as cl36_diff, "
				+ " (SUM(COALESCE(b.cl45r,0)-COALESCE(b.cl45d,0))) as cl45_diff  "
				+ " FROM fl2d.cl2_trxn b "
				+ " WHERE b.dt_date BETWEEN '2018-08-16' AND "
				+ " (to_date('"
				+ Utility.convertUtilDateToSQLDate(act.getFromDate())
				+ "','YYYY-MM-DD')  - INTERVAL '1' DAY) "
				+ " GROUP BY b.int_id, b.licence_no)z ON a.loging_id=z.int_id AND a.licence_no=z.licence_no "
				+ " WHERE a.licence_type='CL2')y "
				+ " UNION "
				+ " SELECT distinct d.int_id, e.licence_no, e.licensee_name, "
				+ " (SELECT c.description FROM public.district c WHERE e.district_id=c.districtid), "
				+ " 0 as cl25_opn, 0 as cl36_opn, 0 as cl45_opn, "
				+ " COALESCE(d.cl25r,0) as cl25_rcv, COALESCE(d.cl25d,0) as cl25_dis, "
				+ " COALESCE(d.cl36r,0) as cl36_rcv, COALESCE(d.cl36d,0) as cl36_dis, "
				+ " COALESCE(d.cl45r,0) as cl45_rcv, COALESCE(d.cl45d,0) as cl45_dis "
				+ " FROM fl2d.cl2_trxn d, fl2d.opening_stock_fl2_fl2b_cl e  "
				+ " WHERE d.licence_type='CL2' AND e.loging_id=d.int_id AND d.licence_no=e.licence_no "
				+ " AND d.dt_date='"
				+ Utility.convertUtilDateToSQLDate(act.getFromDate())
				+ "')x  "
				+ " GROUP BY x.description, x.loging_id, x.licence_no, x.licensee_name  "
				+ " ORDER BY x.description, x.licence_no";

		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
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
			worksheet.setColumnWidth(0, 2000);
			worksheet.setColumnWidth(1, 4300);
			worksheet.setColumnWidth(2, 4300);
			worksheet.setColumnWidth(3, 6300);
			worksheet.setColumnWidth(4, 7500);
			worksheet.setColumnWidth(5, 7500);
			worksheet.setColumnWidth(6, 7500);
			worksheet.setColumnWidth(7, 7500);
			worksheet.setColumnWidth(8, 7500);
			worksheet.setColumnWidth(9, 7500);
			worksheet.setColumnWidth(10, 7500);
			worksheet.setColumnWidth(11, 7500);
			worksheet.setColumnWidth(12, 7500);
			worksheet.setColumnWidth(13, 7500); 

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" CL2 Wholesale Stock Report on " + " "
					+ Utility.convertUtilDateToSQLDate(act.getFromDate()));

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
			cellhead3.setCellValue("License No.");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 3);
			cellhead8.setCellValue("License Name");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 4);
			cellhead9.setCellValue("Opening Of CL2 Stock 25%");
			cellhead9.setCellStyle(cellStyle);
			XSSFCell cellhead10 = rowhead.createCell((int) 5);
			cellhead10.setCellValue("Receipt Of CL2 Stock 25%");
			cellhead10.setCellStyle(cellStyle);
			XSSFCell cellhead11 = rowhead.createCell((int) 6);
			cellhead11.setCellValue("Dispatches Of CL2 Stock 25%");
			cellhead11.setCellStyle(cellStyle);

			XSSFCell cellhead12 = rowhead.createCell((int) 7);
			cellhead12.setCellValue("Opening Of CL2 Stock 36%");
			cellhead12.setCellStyle(cellStyle);
			XSSFCell cellhead13 = rowhead.createCell((int) 8);
			cellhead13.setCellValue("Receipt Of CL2 Stock 36%");
			cellhead13.setCellStyle(cellStyle);
			XSSFCell cellhead14 = rowhead.createCell((int) 9);
			cellhead14.setCellValue("Dispatches Of CL2 Stock 36%");
			cellhead14.setCellStyle(cellStyle);

			XSSFCell cellhead15 = rowhead.createCell((int) 10);
			cellhead15.setCellValue("Opening Of CL2 Stock 42%");
			cellhead15.setCellStyle(cellStyle);
			XSSFCell cellhead16 = rowhead.createCell((int) 11);
			cellhead16.setCellValue("Receipt Of CL2 Stock 42%");
			cellhead16.setCellStyle(cellStyle);
			XSSFCell cellhead17 = rowhead.createCell((int) 12);
			cellhead17.setCellValue("Dispatches Of CL2 Stock 42%");
			cellhead17.setCellStyle(cellStyle);

			int i = 0;
			while (rs.next()) {

				cl25_opn = cl25_opn + rs.getDouble("cl25_opn");
				cl36_opn = cl36_opn + rs.getDouble("cl36_opn");
				cl45_opn = cl45_opn + rs.getDouble("cl45_opn"); 
 				
				cl25_rcv = cl25_rcv + rs.getDouble("cl25_rcv");
				cl36_rcv = cl36_rcv + rs.getDouble("cl36_rcv");
				cl45_rcv = cl45_rcv + rs.getDouble("cl45_rcv"); 

				cl25_dis = cl25_dis + rs.getDouble("cl25_dis");
				cl36_dis = cl36_dis + rs.getDouble("cl36_dis");
				cl45_dis = cl45_dis + rs.getDouble("cl45_dis"); 
 
				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k - 1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("licence_no"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("licensee_name"));
 
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getDouble("cl25_opn"));
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("cl25_rcv"));
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getDouble("cl25_dis"));

				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("cl36_opn"));
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getDouble("cl36_rcv"));
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getDouble("cl36_dis"));

				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getDouble("cl45_opn"));
				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(rs.getDouble("cl45_rcv"));
				XSSFCell cellM1 = row1.createCell((int) 12);
				cellM1.setCellValue(rs.getDouble("cl45_dis"));
 
			}

			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "FL2_WholesaleStockReport.xls");
			act.setExlname(n + "FL2_WholesaleStockReport");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue("");
			cellA1.setCellStyle(cellStyle);

			XSSFCell cellA2 = row1.createCell((int) 1);
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 2);
			cellA3.setCellValue("");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 3);
			cellA4.setCellValue("Total");
			cellA4.setCellStyle(cellStyle);
 
			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(cl25_opn);
			cellA5.setCellStyle(cellStyle);
			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(cl25_rcv);
			cellA6.setCellStyle(cellStyle);
			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(cl25_dis);
			cellA7.setCellStyle(cellStyle);

			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(cl36_opn);
			cellA8.setCellStyle(cellStyle);
			XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(cl36_rcv);
			cellA9.setCellStyle(cellStyle);
			XSSFCell cellA10 = row1.createCell((int) 9);
			cellA10.setCellValue(cl36_dis);
			cellA10.setCellStyle(cellStyle);

			XSSFCell cellA11 = row1.createCell((int) 10);
			cellA11.setCellValue(cl45_opn);
			cellA11.setCellStyle(cellStyle);
			XSSFCell cellA12 = row1.createCell((int) 11);
			cellA12.setCellValue(cl45_rcv);
			cellA12.setCellStyle(cellStyle);
			XSSFCell cellA13 = row1.createCell((int) 12);
			cellA13.setCellValue(cl45_dis);
			cellA13.setCellStyle(cellStyle);
 
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

	// -----------------generate excel for FL2--------------------------

	public boolean writeFL2(WholesaleStockReviewAction act) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double fl750_opening = 0;
		double fl375_opening = 0;
		double fl180_opening = 0;
		double other_opening = 0;

		double fl750d = 0;
		double fl375d = 0;
		double fl180d = 0;
		double other_d = 0;

		double fl750r = 0;
		double fl375r = 0;
		double fl180r = 0;
		double other_r = 0;

		String sql = "";

		sql = " SELECT c.licence_type, c.licence_no, c.description, c.licensee_name,c.fl750_opening, c.fl375_opening,"
				+ " c.fl180_opening, c.other_opening, c.fl750d, c.fl750r,c.fl375d, c.fl375r, c.fl180d, c.fl180r,c.other_d, "
				+ " c.other_r from (SELECT y.licence_type, y.licence_no, y.description, y.licensee_name, "
				+ "(COALESCE(y.fl750,0)+y.fl2_750_ml) as fl750_opening, (COALESCE(y.fl375,0)+y.fl2_375_ml) as fl375_opening,  "
				+ "(COALESCE(y.fl180,0)+y.fl2_180_ml) as fl180_opening, (COALESCE(y.other,0)+y.fl2_other_ml) as other_opening, "
				+ " COALESCE(z.fl750d,0) as fl750d , COALESCE(z.fl750r,0) as fl750r, COALESCE(z.fl375d,0) as fl375d, "
				+ " COALESCE(z.fl375r,0) as fl375r, COALESCE(z.fl180d,0) as fl180d, "
				+ " COALESCE(z.fl180r,0) as fl180r, COALESCE(z.other_d,0) as other_d, COALESCE(z.other_r,0) as other_r "
				+ " from "
				+ " (SELECT a.licence_type, a.licence_no, a.licensee_name, a.fl2_750_ml, a.fl2_375_ml, "
				+ " a.fl2_180_ml, a.fl2_other_ml, a.loging_id, x.fl750, x.fl375, x.fl180, x.other, "
				+ " (SELECT c.description FROM public.district c where c.districtid=a.district_id) "
				+ " FROM fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN "
				+ " (SELECT (COALESCE(sum(b.fl750r),0)-COALESCE(sum(b.fl750d),0)) as fl750, (COALESCE(sum(b.fl375r),0)-COALESCE(sum(b.fl375d),0)) as fl375 , "
				+ " (COALESCE(sum(b.fl180r),0)-COALESCE(sum(b.fl180d),0)) as fl180, (COALESCE(sum(b.other_r),0)-COALESCE(sum(b.other_d),0)) as other, "
				+ " b.int_id, b.licence_no FROM fl2d.fl2_trxn b "
				+ " WHERE b.opening_date BETWEEN '2018-08-16' AND  "
				+ " (to_date('"
				+ Utility.convertUtilDateToSQLDate(act.getFromDate())
				+ "','YYYY-MM-DD')  - INTERVAL '1' DAY) group by b.int_id, b.licence_no) x "
				+ " on a.loging_id = CAST (x.int_id AS INTEGER) and a.licence_no=x.licence_no where a.licence_type='FL2' ) y "
				+ " LEFT JOIN "
				+ " (SELECT licence_no, fl750d, fl750r, fl375d, fl375r, fl180d, fl180r, other_d, other_r, int_id "
				+ " FROM fl2d.fl2_trxn where opening_date='"
				+ Utility.convertUtilDateToSQLDate(act.getFromDate())
				+ "' and licence_type='FL2') z "
				+ " on y.licence_no=z.licence_no) c group by c.licence_type, c.licence_no, c.description, "
				+ "c.licensee_name,c.fl750_opening, c.fl375_opening, c.fl180_opening, c.other_opening, "
				+ "c.fl750d, c.fl750r,c.fl375d, c.fl375r, c.fl180d, c.fl180r,c.other_d, c.other_r order by c.description ";

		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
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
			worksheet.setColumnWidth(0, 2000);
			worksheet.setColumnWidth(1, 4300);
			worksheet.setColumnWidth(2, 4300);
			worksheet.setColumnWidth(3, 6300);
			worksheet.setColumnWidth(4, 7500);
			worksheet.setColumnWidth(5, 7500);
			worksheet.setColumnWidth(6, 7500);
			worksheet.setColumnWidth(7, 7500);
			worksheet.setColumnWidth(8, 7500);
			worksheet.setColumnWidth(9, 7500);
			worksheet.setColumnWidth(10, 7500);
			worksheet.setColumnWidth(11, 7500);
			worksheet.setColumnWidth(12, 7500);
			worksheet.setColumnWidth(13, 7500);
			worksheet.setColumnWidth(14, 7500);
			worksheet.setColumnWidth(15, 7500);
			worksheet.setColumnWidth(16, 7500);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" FL2 Wholesale Stock Report on " + " "
					+ Utility.convertUtilDateToSQLDate(act.getFromDate()));

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
			cellhead3.setCellValue("License No.");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 3);
			cellhead8.setCellValue("License Name");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 4);
			cellhead9.setCellValue("Opening Of FL2 Stock 750(ml)");
			cellhead9.setCellStyle(cellStyle);
			XSSFCell cellhead10 = rowhead.createCell((int) 5);
			cellhead10.setCellValue("Receipt Of FL2 Stock 750(ml)");
			cellhead10.setCellStyle(cellStyle);
			XSSFCell cellhead11 = rowhead.createCell((int) 6);
			cellhead11.setCellValue("Dispatches Of FL2 Stock 750(ml)");
			cellhead11.setCellStyle(cellStyle);

			XSSFCell cellhead12 = rowhead.createCell((int) 7);
			cellhead12.setCellValue("Opening Of FL2 Stock 375(ml)");
			cellhead12.setCellStyle(cellStyle);
			XSSFCell cellhead13 = rowhead.createCell((int) 8);
			cellhead13.setCellValue("Receipt Of FL2 Stock 375(ml)");
			cellhead13.setCellStyle(cellStyle);
			XSSFCell cellhead14 = rowhead.createCell((int) 9);
			cellhead14.setCellValue("Dispatches Of FL2 Stock 375(ml)");
			cellhead14.setCellStyle(cellStyle);

			XSSFCell cellhead15 = rowhead.createCell((int) 10);
			cellhead15.setCellValue("Opening Of FL2 Stock 180(ml)");
			cellhead15.setCellStyle(cellStyle);
			XSSFCell cellhead16 = rowhead.createCell((int) 11);
			cellhead16.setCellValue("Receipt Of FL2 Stock 180(ml)");
			cellhead16.setCellStyle(cellStyle);
			XSSFCell cellhead17 = rowhead.createCell((int) 12);
			cellhead17.setCellValue("Dispatches Of FL2 Stock 180(ml)");
			cellhead17.setCellStyle(cellStyle);

			XSSFCell cellhead18 = rowhead.createCell((int) 13);
			cellhead18.setCellValue("Opening Of FL2 Stock Other(ml)");
			cellhead18.setCellStyle(cellStyle);
			XSSFCell cellhead19 = rowhead.createCell((int) 14);
			cellhead19.setCellValue("Receipt Of FL2 Stock Other(ml)");
			cellhead19.setCellStyle(cellStyle);
			XSSFCell cellhead20 = rowhead.createCell((int) 15);
			cellhead20.setCellValue("Dispatches Of FL2 Stock Other(ml)");
			cellhead20.setCellStyle(cellStyle);

			int i = 0;
			while (rs.next()) {

				fl750_opening = fl750_opening + rs.getDouble("fl750_opening");
				fl375_opening = fl375_opening + rs.getDouble("fl375_opening");
				fl180_opening = fl180_opening + rs.getDouble("fl180_opening");
				other_opening = other_opening + rs.getDouble("other_opening");

				fl750d = fl750d + rs.getDouble("fl750d");
				fl375d = fl375d + rs.getDouble("fl375d");
				fl180d = fl180d + rs.getDouble("fl180d");
				other_d = other_d + rs.getDouble("other_d");

				fl750r = fl750r + rs.getDouble("fl750r");
				fl375r = fl375r + rs.getDouble("fl375r");
				fl180r = fl180r + rs.getDouble("fl180r");
				other_r = other_r + rs.getDouble("other_r");

				/*
				 * total_fl_750 = total_fl_750 + rs.getDouble("stock_fl_750");
				 * total_fl_375 = total_fl_375 + rs.getDouble("stock_fl_375");
				 * total_fl_180 = total_fl_180 + rs.getDouble("stock_fl_180");
				 * total_fl_othr = total_fl_othr +
				 * rs.getDouble("stock_fl_othr");
				 */
				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k - 1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("licence_no"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("licensee_name"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getDouble("fl750_opening"));
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("fl750r"));
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getDouble("fl750d"));

				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("fl375_opening"));
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getDouble("fl375r"));
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getDouble("fl375d"));

				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getDouble("fl180_opening"));
				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(rs.getDouble("fl180r"));
				XSSFCell cellM1 = row1.createCell((int) 12);
				cellM1.setCellValue(rs.getDouble("fl180d"));

				XSSFCell cellN1 = row1.createCell((int) 13);
				cellN1.setCellValue(rs.getDouble("other_opening"));
				XSSFCell cellO1 = row1.createCell((int) 14);
				cellO1.setCellValue(rs.getDouble("other_r"));
				XSSFCell cellP1 = row1.createCell((int) 15);
				cellP1.setCellValue(rs.getDouble("other_d"));

			}

			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "FL2_WholesaleStockReport.xls");
			act.setExlname(n + "FL2_WholesaleStockReport");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue("");
			cellA1.setCellStyle(cellStyle);

			XSSFCell cellA2 = row1.createCell((int) 1);
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 2);
			cellA3.setCellValue("");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 3);
			cellA4.setCellValue("Total");
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(fl750_opening);
			cellA5.setCellStyle(cellStyle);
			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(fl750r);
			cellA6.setCellStyle(cellStyle);
			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(fl750d);
			cellA7.setCellStyle(cellStyle);

			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(fl375_opening);
			cellA8.setCellStyle(cellStyle);
			XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(fl375r);
			cellA9.setCellStyle(cellStyle);
			XSSFCell cellA10 = row1.createCell((int) 9);
			cellA10.setCellValue(fl375d);
			cellA10.setCellStyle(cellStyle);

			XSSFCell cellA11 = row1.createCell((int) 10);
			cellA11.setCellValue(fl180_opening);
			cellA11.setCellStyle(cellStyle);
			XSSFCell cellA12 = row1.createCell((int) 11);
			cellA12.setCellValue(fl180r);
			cellA12.setCellStyle(cellStyle);
			XSSFCell cellA13 = row1.createCell((int) 12);
			cellA13.setCellValue(fl180d);
			cellA13.setCellStyle(cellStyle);

			XSSFCell cellA14 = row1.createCell((int) 13);
			cellA14.setCellValue(other_opening);
			cellA14.setCellStyle(cellStyle);
			XSSFCell cellA15 = row1.createCell((int) 14);
			cellA15.setCellValue(other_r);
			cellA15.setCellStyle(cellStyle);
			XSSFCell cellA16 = row1.createCell((int) 15);
			cellA16.setCellValue(other_d);
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

	// -----------------excel for FL2B--------------------------

	public boolean writeFL2B(WholesaleStockReviewAction act) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double fl2b650_opening = 0;
		double fl2b330_opening = 0;
		double fl2b500_opening = 0;
		double other_opening = 0;

		double fl2b650_d = 0;
		double fl2b500_d = 0;
		double fl2b330_d = 0;
		double fl2bother_d = 0;

		double fl2b650_r = 0;
		double fl2b500_r = 0;
		double fl2b330_r = 0;
		double fl2bother_r = 0;

		String sql = "";
		sql = " SELECT c.licence_type, c.licence_no, c.description, c.licensee_name, c.fl2b650_opening,"
				+ " c.fl2b330_opening, c.fl2b500_opening, c.other_opening,c.fl2b650_d, c.fl2b650_r, c.fl2b500_d, "
				+ " c.fl2b500_r, c.fl2b330_d, c.fl2b330_r, c.fl2bother_d, c.fl2bother_r from"
				+ " (SELECT y.licence_type, y.licence_no, y.description, y.licensee_name, "
				+ " (COALESCE(y.fl2b650,0)+y.fl2b_650_ml) as fl2b650_opening, "
				+ " (COALESCE(y.fl2b330,0)+y.fl2b_330_ml) as fl2b330_opening, "
				+ " (COALESCE(y.fl2b500,0)+y.fl2b_500_ml) as fl2b500_opening, "
				+ " (COALESCE(y.other,0)+y.fl2b_other_ml) as other_opening, "
				+ " COALESCE(fl2b650_d, 0) as fl2b650_d, COALESCE(fl2b650_r, 0) as fl2b650_r, COALESCE(fl2b500_d, 0) as fl2b500_d, "
				+ " COALESCE(fl2b500_r,0) as fl2b500_r, COALESCE(fl2b330_d, 0) as fl2b330_d, COALESCE(fl2b330_r,0) as fl2b330_r,"
				+ " COALESCE(fl2bother_d,0) as fl2bother_d, COALESCE(fl2bother_r,0) as fl2bother_r from (SELECT a.licence_type, a.licence_no, a.licensee_name,"
				+ " a.fl2b_650_ml, a.fl2b_330_ml, a.fl2b_500_ml, a.fl2b_other_ml, a.loging_id, x.fl2b650,"
				+ " x.fl2b330, x.fl2b500, x.other, (SELECT c.description FROM public.district c where "
				+ "c.districtid=a.district_id) FROM fl2d.opening_stock_fl2_fl2b_cl a  LEFT JOIN "
				+ "(SELECT (COALESCE(sum(b.fl2b650_r),0)-COALESCE(sum(b.fl2b650_d),0)) as fl2b650, (COALESCE(sum(b.fl2b330_r),0)-COALESCE(sum(b.fl2b330_d),0))"
				+ " as fl2b330 , (COALESCE(sum(b.fl2b500_r),0)-COALESCE(sum(b.fl2b500_d),0)) as fl2b500, "
				+ "(COALESCE(sum(b.fl2bother_r),0)-COALESCE(sum(b.fl2bother_d),0)) as other, b.int_id, b.licence_no FROM fl2d.fl2b_trxn"
				+ " b WHERE b.opening_date BETWEEN '2018-08-16' AND  (to_date"
				+ "('"
				+ Utility.convertUtilDateToSQLDate(act.getFromDate())
				+ "','YYYY-MM-DD')  - "
				+ "INTERVAL '1' DAY) group by b.int_id, b.licence_no) x on a.loging_id = "
				+ "CAST (x.int_id AS INTEGER) and a.licence_no=x.licence_no where a.licence_type='FL2B') y "
				+ "LEFT JOIN (SELECT licence_no, fl2b650_d, fl2b650_r, fl2b500_d, fl2b500_r, fl2b330_d, "
				+ "fl2b330_r, fl2bother_d, fl2bother_r, int_id FROM fl2d.fl2b_trxn where opening_date="
				+ "'"
				+ Utility.convertUtilDateToSQLDate(act.getFromDate())
				+ "' and licence_type='FL2B') z "
				+ "on y.licence_no=z.licence_no) c group by c.licence_type, c.licence_no, c.description, "
				+ "c.licensee_name, c.fl2b650_opening, c.fl2b330_opening, c.fl2b500_opening, c.other_opening"
				+ ",c.fl2b650_d, c.fl2b650_r, c.fl2b500_d, c.fl2b500_r, c.fl2b330_d, c.fl2b330_r, c.fl2bother_d,"
				+ " c.fl2bother_r order by c.description";

		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
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
			worksheet.setColumnWidth(0, 2000);
			worksheet.setColumnWidth(1, 4300);
			worksheet.setColumnWidth(2, 4300);
			worksheet.setColumnWidth(3, 6300);
			worksheet.setColumnWidth(4, 7500);
			worksheet.setColumnWidth(5, 7500);
			worksheet.setColumnWidth(6, 7500);
			worksheet.setColumnWidth(7, 7500);
			worksheet.setColumnWidth(8, 7500);
			worksheet.setColumnWidth(9, 7500);
			worksheet.setColumnWidth(10, 7500);
			worksheet.setColumnWidth(11, 7500);
			worksheet.setColumnWidth(12, 7500);
			worksheet.setColumnWidth(13, 7500);
			worksheet.setColumnWidth(14, 7500);
			worksheet.setColumnWidth(15, 7500);
			worksheet.setColumnWidth(16, 7500);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" FL2B Wholesale Stock Report on " + " "
					+ Utility.convertUtilDateToSQLDate(act.getFromDate()));

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
			cellhead3.setCellValue("License No.");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 3);
			cellhead8.setCellValue("License Name");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 4);
			cellhead9.setCellValue("Opening Of FL2B Stock 650(ml)");
			cellhead9.setCellStyle(cellStyle);
			XSSFCell cellhead10 = rowhead.createCell((int) 5);
			cellhead10.setCellValue("Receipt Of FL2B Stock 650(ml)");
			cellhead10.setCellStyle(cellStyle);
			XSSFCell cellhead11 = rowhead.createCell((int) 6);
			cellhead11.setCellValue("Dispatches Of FL2B Stock 650(ml)");
			cellhead11.setCellStyle(cellStyle);

			XSSFCell cellhead12 = rowhead.createCell((int) 7);
			cellhead12.setCellValue("Opening Of FL2B Stock 500(ml)");
			cellhead12.setCellStyle(cellStyle);
			XSSFCell cellhead13 = rowhead.createCell((int) 8);
			cellhead13.setCellValue("Receipt Of FL2B Stock 500(ml)");
			cellhead13.setCellStyle(cellStyle);
			XSSFCell cellhead14 = rowhead.createCell((int) 9);
			cellhead14.setCellValue("Dispatches Of FL2B Stock 500(ml)");
			cellhead14.setCellStyle(cellStyle);

			XSSFCell cellhead15 = rowhead.createCell((int) 10);
			cellhead15.setCellValue("Opening Of FL2B Stock 330(ml)");
			cellhead15.setCellStyle(cellStyle);
			XSSFCell cellhead16 = rowhead.createCell((int) 11);
			cellhead16.setCellValue("Receipt Of FL2B Stock 330(ml)");
			cellhead16.setCellStyle(cellStyle);
			XSSFCell cellhead17 = rowhead.createCell((int) 12);
			cellhead17.setCellValue("Dispatches Of FL2B Stock 330(ml)");
			cellhead17.setCellStyle(cellStyle);

			XSSFCell cellhead18 = rowhead.createCell((int) 13);
			cellhead18.setCellValue("Opening Of FL2B Stock Other(ml)");
			cellhead18.setCellStyle(cellStyle);
			XSSFCell cellhead19 = rowhead.createCell((int) 14);
			cellhead19.setCellValue("Receipt Of FL2B Stock Other(ml)");
			cellhead19.setCellStyle(cellStyle);
			XSSFCell cellhead20 = rowhead.createCell((int) 15);
			cellhead20.setCellValue("Dispatches Of FL2B Stock Other(ml)");
			cellhead20.setCellStyle(cellStyle);

			int i = 0;
			while (rs.next()) {
				fl2b650_opening = fl2b650_opening + rs.getDouble("fl2b650_opening");
				fl2b330_opening = fl2b330_opening + rs.getDouble("fl2b330_opening");
				fl2b500_opening = fl2b500_opening + rs.getDouble("fl2b500_opening");
				other_opening = other_opening + rs.getDouble("other_opening");

				fl2b650_d = fl2b650_d + rs.getDouble("fl2b650_d");
				fl2b500_d = fl2b500_d + rs.getDouble("fl2b500_d");
				fl2b330_d = fl2b330_d + rs.getDouble("fl2b330_d");
				fl2bother_d = fl2bother_d + rs.getDouble("fl2bother_d");

				fl2b650_r = fl2b650_r + rs.getDouble("fl2b650_r");
				fl2b500_r = fl2b500_r + rs.getDouble("fl2b500_r");
				fl2b330_r = fl2b330_r + rs.getDouble("fl2b330_r");
				fl2bother_r = fl2bother_r + rs.getDouble("fl2bother_r");
 
				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k - 1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("licence_no"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("licensee_name"));
 
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getDouble("fl2b650_opening"));
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("fl2b650_r"));
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getDouble("fl2b650_d"));
				 
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("fl2b500_opening"));
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getDouble("fl2b500_r"));
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getDouble("fl2b500_d"));

				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getDouble("fl2b330_opening"));
				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(rs.getDouble("fl2b330_r"));
				XSSFCell cellM1 = row1.createCell((int) 12);
				cellM1.setCellValue(rs.getDouble("fl2b330_d"));

				XSSFCell cellN1 = row1.createCell((int) 13);
				cellN1.setCellValue(rs.getDouble("other_opening"));
				XSSFCell cellO1 = row1.createCell((int) 14);
				cellO1.setCellValue(rs.getDouble("fl2bother_r"));
				XSSFCell cellP1 = row1.createCell((int) 15);
				cellP1.setCellValue(rs.getDouble("fl2bother_d"));

			}

			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "FL2_WholesaleStockReport.xls");
			act.setExlname(n + "FL2_WholesaleStockReport");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue("");
			cellA1.setCellStyle(cellStyle);

			XSSFCell cellA2 = row1.createCell((int) 1);
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 2);
			cellA3.setCellValue("");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 3);
			cellA4.setCellValue("Total");
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(fl2b650_opening);
			cellA5.setCellStyle(cellStyle);
			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(fl2b650_r);
			cellA6.setCellStyle(cellStyle);
			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(fl2b650_d);
			cellA7.setCellStyle(cellStyle);
 		
			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(fl2b500_opening);
			cellA8.setCellStyle(cellStyle);
			XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(fl2b500_r);
			cellA9.setCellStyle(cellStyle);
			XSSFCell cellA10 = row1.createCell((int) 9);
			cellA10.setCellValue(fl2b500_d);
			cellA10.setCellStyle(cellStyle);

			XSSFCell cellA11 = row1.createCell((int) 10);
			cellA11.setCellValue(fl2b330_opening);
			cellA11.setCellStyle(cellStyle);
			XSSFCell cellA12 = row1.createCell((int) 11);
			cellA12.setCellValue(fl2b330_r);
			cellA12.setCellStyle(cellStyle);
			XSSFCell cellA13 = row1.createCell((int) 12);
			cellA13.setCellValue(fl2b330_d);
			cellA13.setCellStyle(cellStyle);

			XSSFCell cellA14 = row1.createCell((int) 13);
			cellA14.setCellValue(other_opening);
			cellA14.setCellStyle(cellStyle);
			XSSFCell cellA15 = row1.createCell((int) 14);
			cellA15.setCellValue(fl2bother_r);
			cellA15.setCellStyle(cellStyle);
			XSSFCell cellA16 = row1.createCell((int) 15);
			cellA16.setCellValue(fl2bother_d);
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
}
