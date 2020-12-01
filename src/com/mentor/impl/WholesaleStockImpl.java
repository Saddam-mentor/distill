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

import com.mentor.action.WholesaleStockAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class WholesaleStockImpl {

	// ---------------------get district--------------------------

	public ArrayList getDistList() {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--ALL--");
		item.setValue(9999);
		list.add(item);
		try {
			String query = " SELECT DistrictID, Description FROM district order by Description ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("DistrictID"));
				item.setLabel(rs.getString("Description"));

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

	// =======================print report consolidated
	// =================================

	public void printReportConsolidated(WholesaleStockAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;

		try {
			con = ConnectionToDataBase.getConnection();

			/*reportQuery =

			"	select x.cl25, x.cl36, x.cl42, x.description, x.mgq ,x.fl2_750ml, x.fl2_375ml, x.fl2_180ml, x.fl2_otherml ,  "
					+ "	x.fl2b_650ml, x.fl2b_500ml, x.fl2b_330ml, x.fl2b_otherml "
					+ "	from( "
					+ "	select ((b.no_of_box_25prc + a.cl25r)-a.cl25d) as cl25, "
					+ "	((b.no_of_box_36prc + a.cl36r)-a.cl36d) as cl36, "
					+ "	((b.no_of_box_42prc + a.cl45r)-a.cl45d) as cl42, "
					+ "	a.int_id as id_t, "
					+ "	c.description, d.mgq , "
					+ "	0 as fl2_750ml, 0 as fl2_375ml, 0 as fl2_180ml , 0 as fl2_otherml, "
					+ "	0 as fl2b_650ml, 0 as fl2b_500ml, 0 as fl2b_330ml, 0 as fl2b_otherml "
					+ "	FROM fl2d.ws_current a , fl2d.opening_stock_fl2_fl2b_cl b , "
					+ "	 public.district c ,fl2d.mst_mgq d "
					+ "	where a.int_id=b.loging_id "
					+ "	and b.district_id=c.districtid  "
					+ "	and c.districtid =d.int_district_id  "
					+ "	and a.opening_date='"
					+ Utility.convertUtilDateToSQLDate(act.getDtDate())
					+ "' "
					+ "	and a.licence_type='CL2' "
					+ "	union "
					+ " "
					+ "	select 0 as cl25 ,0 as cl36, 0 as cl42,0 as id_t, "
					+ "	c.description,d.mgq, "
					+ "	((b.fl2_750_ml + a.fl750r)-a.fl750d) as fl2_750ml, "
					+ "	((b.fl2_375_ml + a.fl375r)-a.fl375d) as fl2_375ml, "
					+ "	((b.fl2_180_ml + a.fl180r)-a.fl180d) as fl2_180ml, "
					+ " 	((b.fl2_other_ml + a.flothr)-a.flothd) as fl2_otherml, "
					+ "	0 as fl2b_650ml, 0 as fl2b_500ml, 0 as fl2b_330ml, 0 as fl2b_otherml "
					+ "	FROM fl2d.ws_current a , fl2d.opening_stock_fl2_fl2b_cl b , "
					+ "	 public.district c ,fl2d.mst_mgq d "
					+ "	where a.int_id=b.loging_id "
					+ "	and b.district_id=c.districtid  "
					+ "	and c.districtid =d.int_district_id  "
					+ "	and a.opening_date='"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  "
					+ "	and a.licence_type='FL2' "
					+ "	UNION "
					+ "	select 0 as cl25 ,0 as cl36, 0 as cl42,0 as id_t, c.description, d.mgq , "
					+ "	0 as fl2_750ml, 0 as fl2_375ml, 0 as fl2_180ml, 0 as fl2_otherml, "
					+ "	((b.fl2b_650_ml + a.flb650r)-a.flb650d) as fl2b_650ml, "
					+ "	((b.fl2b_500_ml + a.flb500r)-a.flb500d) as fl2b_500ml, "
					+ "	((b.fl2b_330_ml + a.flb330r)-a.flb330d) as fl2b_330ml, "
					+ "	((b.fl2b_other_ml + a.flbothr)-a.flbothd) as fl2b_otherml "
					+ "	FROM fl2d.ws_current a , fl2d.opening_stock_fl2_fl2b_cl b , "
					+ "	 public.district c ,fl2d.mst_mgq d "
					+ "	where a.int_id=b.loging_id "
					+ "	and b.district_id=c.districtid "
					+ "	and c.districtid =d.int_district_id  "
					+ "	and a.opening_date='"
					+ Utility.convertUtilDateToSQLDate(act.getDtDate())
					+ "' "
					+ "	and a.licence_type='FL2B' ) x order by x.description ";*/
			
			
			
			reportQuery =                                                                                                                                   
			"	SELECT sum(x.cl25) as cl25, sum(x.cl36) as cl36, sum(x.cl45) as cl42, x.description, sum(x.mgq) as mgq,                                         "
		+"	sum(x.fl750) as fl2_750ml, sum(x.fl375) as fl2_375ml ,                                                                                          "
		+"	sum(x.fl180) as fl2_180ml, sum(x.other) as fl2_otherml,sum(x.fl2b650) as fl2b_650ml,                                                            "
		+"	sum(x.fl2b500) as fl2b_500ml, sum(x.fl2b330) as fl2b_330ml, sum(x.fl2bother) as fl2b_otherml                                                    "
		+"	FROM (                                                                                                                                          "
		+"	select                                                                                                                                          "
		+"	a.district_id, c.description,  d.mgq,                                                                                                           "
		+"	(COALESCE(a.fl2_750_ml,0)+COALESCE(b.fl750r,0)-COALESCE(b.fl750d,0)) as fl750  ,                                                                "
		+"	(COALESCE(a.fl2_375_ml,0)+COALESCE(b.fl375r,0)-COALESCE(b.fl375d,0) ) as fl375,                                                                 "
		+"	(COALESCE(a.fl2_180_ml,0)+COALESCE(b.fl180r,0)-COALESCE(b.fl180d,0) ) as fl180 ,                                                                "
		+"	(COALESCE(a.fl2_other_ml,0)+COALESCE(b.other_r,0) -COALESCE(b.other_d,0) ) as other,0 as fl2b650,0 as fl2b500,0 as fl2b330,0 as fl2bother,      "
		+"	0 as  cl25,0 as cl36 ,0 as  cl45                                                                                                                "
		+"	FROM public.district c , fl2d.mst_mgq d ,fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (select b.int_id, sum(COALESCE(b.fl750r,0))fl750r,     "
		+"sum(COALESCE(b.fl750d,0))fl750d ,sum(COALESCE(b.fl375r,0))fl375r,                                                                                  "
		+"	sum(COALESCE(b.fl375d,0))fl375d, sum(COALESCE(b.fl180r,0))fl180r,sum(COALESCE(b.fl180d,0))fl180d ,sum(COALESCE(b.other_r,0))other_r ,       "
		+"sum(COALESCE(b.other_d,0))other_d                                                                                                                  "
		+"	FROM fl2d.fl2_trxn b where   b.opening_date <='"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "' group by b.int_id)b on a.loging_id::text=b.int_id                                    "
		+"	WHERE a.licence_type='FL2'  and    a.district_id=c.districtid                                                                                   "
		+"	GROUP BY c.description,   a.district_id,d.mgq, a.fl2_750_ml,b.fl750r,b.fl750d  ,                                                                "
		+"	a.fl2_375_ml,b.fl375r,b.fl375d,                                                                                                                 "
		+"	a.fl2_180_ml,b.fl180r,b.fl180d ,                                                                                                                "
		+"	a.fl2_other_ml,b.other_r,b.other_d                                                                                                              "
		+"	union                                                                                                                                           "
		+"	select                                                                                                                                          "
		+"	a.district_id, c.description, d.mgq,  0 as fl750,0 as fl375,0 as fl180,0 as other,                                                              "
		+"	(COALESCE(a.fl2b_650_ml,0)+COALESCE(b.fl2b650_r,0)-COALESCE(b.fl2b650_d,0)) as fl2b650  ,                                                       "
		+"	(COALESCE(a.fl2b_500_ml,0)+COALESCE(b.fl2b500_r,0)-COALESCE(b.fl2b500_d,0) ) as fl2b500,                                                        "
		+"	(COALESCE(a.fl2b_330_ml,0)+COALESCE(b.fl2b330_r,0)-COALESCE(b.fl2b330_d,0) ) as fl2b330 ,                                                       "
		+"	(COALESCE(a.fl2b_other_ml,0)+COALESCE(b.fl2bother_r,0) -COALESCE(b.fl2bother_d,0) ) as fl2bother,0 as  cl25,0 as cl36 ,0 as  cl45               "
		+"	FROM public.district c , fl2d.mst_mgq d ,fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (select b.int_id, sum(COALESCE(b.fl2b650_r,0))fl2b650_r,    " 
		+"sum(COALESCE(b.fl2b650_d,0))fl2b650_d ,sum(COALESCE(b.fl2b500_r,0))fl2b500_r,                                                                      "
		+"	sum(COALESCE(b.fl2b500_d,0))fl2b500_d, sum(COALESCE(b.fl2b330_r,0))fl2b330_r,sum(COALESCE(b.fl2b330_d,0))fl2b330_d ,                         "
		+"sum(COALESCE(b.fl2bother_r,0))fl2bother_r ,sum(COALESCE(b.fl2bother_d,0))fl2bother_d                                                               "
		+"	FROM fl2d.fl2b_trxn b where   b.opening_date <='"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "' group by b.int_id)b on a.loging_id=b.int_id                                         "
		+"	WHERE a.licence_type='FL2B'  and    a.district_id=c.districtid                                                                                  "
		+"	GROUP BY c.description,   a.district_id, d.mgq, a.fl2b_650_ml,b.fl2b650_r,b.fl2b650_d  ,                                                        "
		+"	a.fl2b_500_ml,b.fl2b500_r,b.fl2b500_d,                                                                                                          "
		+"	a.fl2b_330_ml,b.fl2b330_r,b.fl2b330_d ,                                                                                                         "
		+"	a.fl2b_other_ml,b.fl2bother_r,b.fl2bother_d                                                                                                     "
		+"	union                                                                                                                                           "
		+"	select                                                                                                                                          "
		+"	a.district_id, c.description,   d.mgq, 0 as fl750,0 as fl375,0 as fl180,0 as other,0 as fl2b650,0 as fl2b500,0 as fl2b330,0 as fl2bother,       "
		+"	(COALESCE(a.no_of_box_25prc,0)+COALESCE(b.cl25r,0)-COALESCE(b.cl25d,0)) as cl25  ,                                                              "
		+"	(COALESCE(a.no_of_box_36prc,0)+COALESCE(b.cl36r,0)-COALESCE(b.cl36d,0) ) as cl36,                                                               "
		+"	(COALESCE(a.no_of_box_42prc,0)+COALESCE(b.cl45r,0)-COALESCE(b.cl45d,0) ) as cl45                                                                "
		+"	FROM public.district c  , fl2d.mst_mgq d,fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (select b.int_id, sum(COALESCE(b.cl25r,0))cl25r,        "
		+"sum(COALESCE(b.cl25d,0))cl25d ,sum(COALESCE(b.cl36r,0))cl36r,                                                                                      "
		+"	sum(COALESCE(b.cl36d,0))cl36d, sum(COALESCE(b.cl45r,0))cl45r,sum(COALESCE(b.cl45d,0))cl45d                                                      "
		+"	FROM fl2d.cl2_trxn b where   b.dt_date <='"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "' group by b.int_id)b on a.loging_id=b.int_id                                               "
		+"	WHERE a.licence_type='CL2'  and    a.district_id=c.districtid                                                                                   "
		+"	GROUP BY c.description,   a.district_id,d.mgq, a.no_of_box_25prc,b.cl25r,b.cl25d,                                                               "
		+"	a.no_of_box_36prc,b.cl36r,b.cl36d,                                                                                                              "
		+"	a.no_of_box_42prc,b.cl45r,b.cl45d )x                                                                                                            "
		+"	group  BY x.district_id, x.description,   x.mgq ORDER BY x.description   ";
			pst = con.prepareStatement(reportQuery);
			 System.out.println("reportQuery-----consolidated---------"+ reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getDtDate()));
				// parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "Consolidated_WholesaleStock.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "Consolidated_WholesaleStock" + "-" + n+ ".pdf");
				act.setPdfname("Consolidated_WholesaleStock" + "-" + n + ".pdf");
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

	// =======================print report CL2 =================================

	public void printReportCl2(WholesaleStockAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String filter = "";
		if (act.getDistrict().equalsIgnoreCase("9999")) {
			filter = "";
		} else {
			filter = "and  a.district_id='"+ Integer.parseInt(act.getDistrict()) + "' ";
		}

		try {
			con = ConnectionToDataBase.getConnection();

			reportQuery =	
	"	select  '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  as opening_date, a.licence_type, a.licence_no, a.licensee_name,                                          "+
	"	a.district_id, c.description,     '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  as dt_date,                                                                                            "+
	"	(COALESCE(a.no_of_box_25prc,0)+COALESCE(b.cl25r,0)-COALESCE(b.cl25d,0)) as stock_cl25,	                                          "+
	"	(COALESCE(a.no_of_box_36prc,0)+COALESCE(b.cl36r,0)-COALESCE(b.cl36d,0) ) as stock_cl36,	                                        "+
	"	(COALESCE(a.no_of_box_42prc,0)+COALESCE(b.cl45r,0)-COALESCE(b.cl45d,0) ) as stock_cl45                                              "+
	"	FROM public.district c  ,fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (select b.int_id, sum(COALESCE(b.cl25r,0))cl25r,     "+
	"sum(COALESCE(b.cl25d,0))cl25d ,sum(COALESCE(b.cl36r,0))cl36r,                                                                   "+
	"	sum(COALESCE(b.cl36d,0))cl36d, sum(COALESCE(b.cl45r,0))cl45r,sum(COALESCE(b.cl45d,0))cl45d                                   "+
	"	FROM fl2d.cl2_trxn b where   b.dt_date <= '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  group by b.int_id)b on a.loging_id=b.int_id                            "+
	"	WHERE a.licence_type='CL2'  and    a.district_id=c.districtid  "+filter+"                                                               "+
	"	GROUP BY c.description,   a.district_id, a.licence_type,                                                                     "+
	"	a.licence_no, a.licensee_name, a.no_of_box_25prc,b.cl25r,b.cl25d  ,                                                          "+
	"	a.no_of_box_36prc,b.cl36r,b.cl36d,                                                                                           "+
	"	a.no_of_box_42prc,b.cl45r,b.cl45d  ,dt_date                                                                                           "+
	"	ORDER BY c.description, a.licence_no  ";                                                                                      
			

			pst = con.prepareStatement(reportQuery);
		  System.out.println("reportQuery-----CL2---------" + reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getDtDate()));
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "CL_WholeSaleStockReport.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "CL_WholeSaleStockReport" + "-" + n + ".pdf");
				act.setPdfname("CL_WholeSaleStockReport" + "-" + n + ".pdf");
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

	// =======================print report FL2 =================================

	public void printReportFl2(WholesaleStockAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;

		String filter = "";

		if (act.getDistrict().equalsIgnoreCase("9999")) {
			filter = "";
		} else {
			filter = "and  a.district_id='"+ Integer.parseInt(act.getDistrict()) + "' ";
		}

		try {
			con = ConnectionToDataBase.getConnection();

			reportQuery =

		"	select '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  as opening_date, a.licence_type, a.licence_no, a.licensee_name, " +
		"	a.district_id, c.description,  " +
		"	(COALESCE(a.fl2_750_ml,0)+COALESCE(b.fl750r,0)-COALESCE(b.fl750d,0)) as stock_fl_750  , " +
		"	(COALESCE(a.fl2_375_ml,0)+COALESCE(b.fl375r,0)-COALESCE(b.fl375d,0) ) as stock_fl_375, " +
		"	(COALESCE(a.fl2_180_ml,0)+COALESCE(b.fl180r,0)-COALESCE(b.fl180d,0) ) as stock_fl_180 ," +
		"	(COALESCE(a.fl2_other_ml,0)+COALESCE(b.other_r,0) -COALESCE(b.other_d,0) ) as stock_fl_othr" +
		"	FROM public.district c  ,fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (select b.int_id, sum(COALESCE(b.fl750r,0))fl750r,sum(COALESCE(b.fl750d,0))fl750d ,sum(COALESCE(b.fl375r,0))fl375r, " +
		"	sum(COALESCE(b.fl375d,0))fl375d, sum(COALESCE(b.fl180r,0))fl180r,sum(COALESCE(b.fl180d,0))fl180d ,sum(COALESCE(b.other_r,0))other_r ,sum(COALESCE(b.other_d,0))other_d " +
		"	FROM fl2d.fl2_trxn b where   b.opening_date <='"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  group by b.int_id)b on a.loging_id::text=b.int_id " +
		"	WHERE a.licence_type='FL2'  and    a.district_id=c.districtid "+filter+" " +
		"	GROUP BY c.description,   a.district_id, a.licence_type, " +
		"	a.licence_no, a.licensee_name, a.fl2_750_ml,b.fl750r,b.fl750d  , " +
		"	a.fl2_375_ml,b.fl375r,b.fl375d, " +
		"	a.fl2_180_ml,b.fl180r,b.fl180d ," +
		"	a.fl2_other_ml,b.other_r,b.other_d" +
		"	ORDER BY c.description, a.licence_no " ;
			
			
			
			/*reportQuery =	" SELECT DISTINCT b.opening_date, a.licence_type, a.licence_no, a.licensee_name, " +
							" a.district_id, c.description, " +
							" ((COALESCE(a.fl2_750_ml,0)+COALESCE(b.fl750r,0))-COALESCE(b.fl750d,0)) as stock_fl_750, " +
							" ((COALESCE(a.fl2_375_ml,0)+COALESCE(b.fl375r,0))-COALESCE(b.fl375d,0)) as stock_fl_375, " +
							" ((COALESCE(a.fl2_180_ml,0)+COALESCE(b.fl180r,0))-COALESCE(b.fl180d,0)) as stock_fl_180, " +
							" ((COALESCE(a.fl2_other_ml,0)+COALESCE(b.other_r,0))-COALESCE(b.other_d,0)) as stock_fl_othr " +
							" FROM fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN fl2d.fl2_trxn b ON  " +
							" a.loging_id::text=b.int_id and b.opening_date= '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "' , public.district c  " +
							" WHERE a.licence_type='FL2' AND a.district_id=c.districtid "+filter+"  " +
							"  " +
							" GROUP BY c.description, b.opening_date, a.district_id, a.licence_type, " +
							" a.licence_no, a.licensee_name, a.fl2_750_ml, a.fl2_375_ml, a.fl2_180_ml, " +
							" a.fl2_other_ml, b.fl750d, b.fl750r, b.fl375d, b.fl375r,  " +
							" b.fl180d, b.fl180r, b.other_d, b.other_r " +
							" ORDER BY c.description, a.licence_no  ";*/

					

			pst = con.prepareStatement(reportQuery);
			//System.out.println("reportQuery-----FL2---------" + reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getDtDate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "FL2_WholesaleStockReport.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print, relativePathpdf+ File.separator + "FL2_WholesaleStockReport"+ "-" + n + ".pdf");
				act.setPdfname("FL2_WholesaleStockReport" + "-" + n + ".pdf");
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

	// =======================print report FL2B
	// =================================

	public void printReportFl2B(WholesaleStockAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;

		String filter = "";

		if (act.getDistrict().equalsIgnoreCase("9999")) {
			filter = "";
		} else {
			filter = "and  a.district_id='"+ Integer.parseInt(act.getDistrict()) + "' ";
		}

		try {
			con = ConnectionToDataBase.getConnection();
 
			
			reportQuery =	
	" select  '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  as opening_date, a.licence_type, a.licence_no, a.licensee_name,                                                  "+
	" a.district_id, c.description,                                                                                                        "+
	" (COALESCE(a.fl2b_650_ml,0)+COALESCE(b.fl2b650_r,0)-COALESCE(b.fl2b650_d,0)) as stock_fl2b_650  ,                                              "+
	" (COALESCE(a.fl2b_500_ml,0)+COALESCE(b.fl2b500_r,0)-COALESCE(b.fl2b500_d,0) ) as stock_fl2b_500,                                               "+
	" (COALESCE(a.fl2b_330_ml,0)+COALESCE(b.fl2b330_r,0)-COALESCE(b.fl2b330_d,0) ) as stock_fl2b_330 ,                                              "+
	" (COALESCE(a.fl2b_other_ml,0)+COALESCE(b.fl2bother_r,0) -COALESCE(b.fl2bother_d,0) ) as stock_fl2b_othr                                         "+
	" FROM public.district c  ,fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (select b.int_id, sum(COALESCE(b.fl2b650_r,0))fl2b650_r,         "+
	"sum(COALESCE(b.fl2b650_d,0))fl2b650_d ,sum(COALESCE(b.fl2b500_r,0))fl2b500_r,                                                         "+
	" sum(COALESCE(b.fl2b500_d,0))fl2b500_d, sum(COALESCE(b.fl2b330_r,0))fl2b330_r,sum(COALESCE(b.fl2b330_d,0))fl2b330_d ,                 "+
	"sum(COALESCE(b.fl2bother_r,0))fl2bother_r ,sum(COALESCE(b.fl2bother_d,0))fl2bother_d                                                  "+
	" FROM fl2d.fl2b_trxn b where   b.opening_date <= '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  group by b.int_id)b on a.loging_id=b.int_id                              "+
	" WHERE a.licence_type='FL2B'  and    a.district_id=c.districtid "+filter+"                                                                       "+
	" GROUP BY c.description,   a.district_id, a.licence_type,                                                                             "+
	" a.licence_no, a.licensee_name, a.fl2b_650_ml,b.fl2b650_r,b.fl2b650_d  ,                                                              "+
	" a.fl2b_500_ml,b.fl2b500_r,b.fl2b500_d,                                                                                               "+
	" a.fl2b_330_ml,b.fl2b330_r,b.fl2b330_d ,                                                                                              "+
	" a.fl2b_other_ml,b.fl2bother_r,b.fl2bother_d                                                                                          "+
	" ORDER BY c.description, a.licence_no " ;                                                                                              
						
			pst = con.prepareStatement(reportQuery);
			 

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getDtDate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "FL2B_WholesaleStockReport.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "FL2B_WholesaleStockReport" + "-" + n+ ".pdf");
				act.setPdfname("FL2B_WholesaleStockReport" + "-" + n + ".pdf");
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

	// ----------------------generate excel for date wise
	// report------------------------------

	public boolean writeConsolidated(WholesaleStockAction act) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double tot_cl25 = 0;
		double tot_cl36 = 0;
		double tot_cl42 = 0;
		double tot_fl2_750 = 0;
		double tot_fl2_375 = 0;
		double tot_fl2_180 = 0;
		double tot_fl2_othr = 0;
		double tot_fl2b_650 = 0;
		double tot_fl2b_500 = 0;
		double tot_fl2b_330 = 0;
		double tot_fl2b_othr = 0;

		String sql =  
			                                                                                                                           
				"	SELECT sum(x.cl25) as cl25, sum(x.cl36) as cl36, sum(x.cl45) as cl42, x.description, sum(x.mgq) as mgq,                                         "
			+"	sum(x.fl750) as fl2_750ml, sum(x.fl375) as fl2_375ml ,                                                                                          "
			+"	sum(x.fl180) as fl2_180ml, sum(x.other) as fl2_otherml,sum(x.fl2b650) as fl2b_650ml,                                                            "
			+"	sum(x.fl2b500) as fl2b_500ml, sum(x.fl2b330) as fl2b_330ml, sum(x.fl2bother) as fl2b_otherml                                                    "
			+"	FROM (                                                                                                                                          "
			+"	select                                                                                                                                          "
			+"	a.district_id, c.description,  d.mgq,                                                                                                           "
			+"	(COALESCE(a.fl2_750_ml,0)+COALESCE(b.fl750r,0)-COALESCE(b.fl750d,0)) as fl750  ,                                                                "
			+"	(COALESCE(a.fl2_375_ml,0)+COALESCE(b.fl375r,0)-COALESCE(b.fl375d,0) ) as fl375,                                                                 "
			+"	(COALESCE(a.fl2_180_ml,0)+COALESCE(b.fl180r,0)-COALESCE(b.fl180d,0) ) as fl180 ,                                                                "
			+"	(COALESCE(a.fl2_other_ml,0)+COALESCE(b.other_r,0) -COALESCE(b.other_d,0) ) as other,0 as fl2b650,0 as fl2b500,0 as fl2b330,0 as fl2bother,      "
			+"	0 as  cl25,0 as cl36 ,0 as  cl45                                                                                                                "
			+"	FROM public.district c , fl2d.mst_mgq d ,fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (select b.int_id, sum(COALESCE(b.fl750r,0))fl750r,     "
			+"sum(COALESCE(b.fl750d,0))fl750d ,sum(COALESCE(b.fl375r,0))fl375r,                                                                                  "
			+"	sum(COALESCE(b.fl375d,0))fl375d, sum(COALESCE(b.fl180r,0))fl180r,sum(COALESCE(b.fl180d,0))fl180d ,sum(COALESCE(b.other_r,0))other_r ,       "
			+"sum(COALESCE(b.other_d,0))other_d                                                                                                                  "
			+"	FROM fl2d.fl2_trxn b where   b.opening_date <='"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "' group by b.int_id)b on a.loging_id::text=b.int_id                                    "
			+"	WHERE a.licence_type='FL2'  and    a.district_id=c.districtid                                                                                   "
			+"	GROUP BY c.description,   a.district_id,d.mgq, a.fl2_750_ml,b.fl750r,b.fl750d  ,                                                                "
			+"	a.fl2_375_ml,b.fl375r,b.fl375d,                                                                                                                 "
			+"	a.fl2_180_ml,b.fl180r,b.fl180d ,                                                                                                                "
			+"	a.fl2_other_ml,b.other_r,b.other_d                                                                                                              "
			+"	union                                                                                                                                           "
			+"	select                                                                                                                                          "
			+"	a.district_id, c.description, d.mgq,  0 as fl750,0 as fl375,0 as fl180,0 as other,                                                              "
			+"	(COALESCE(a.fl2b_650_ml,0)+COALESCE(b.fl2b650_r,0)-COALESCE(b.fl2b650_d,0)) as fl2b650  ,                                                       "
			+"	(COALESCE(a.fl2b_500_ml,0)+COALESCE(b.fl2b500_r,0)-COALESCE(b.fl2b500_d,0) ) as fl2b500,                                                        "
			+"	(COALESCE(a.fl2b_330_ml,0)+COALESCE(b.fl2b330_r,0)-COALESCE(b.fl2b330_d,0) ) as fl2b330 ,                                                       "
			+"	(COALESCE(a.fl2b_other_ml,0)+COALESCE(b.fl2bother_r,0) -COALESCE(b.fl2bother_d,0) ) as fl2bother,0 as  cl25,0 as cl36 ,0 as  cl45               "
			+"	FROM public.district c , fl2d.mst_mgq d ,fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (select b.int_id, sum(COALESCE(b.fl2b650_r,0))fl2b650_r,    " 
			+"sum(COALESCE(b.fl2b650_d,0))fl2b650_d ,sum(COALESCE(b.fl2b500_r,0))fl2b500_r,                                                                      "
			+"	sum(COALESCE(b.fl2b500_d,0))fl2b500_d, sum(COALESCE(b.fl2b330_r,0))fl2b330_r,sum(COALESCE(b.fl2b330_d,0))fl2b330_d ,                         "
			+"sum(COALESCE(b.fl2bother_r,0))fl2bother_r ,sum(COALESCE(b.fl2bother_d,0))fl2bother_d                                                               "
			+"	FROM fl2d.fl2b_trxn b where   b.opening_date <='"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "' group by b.int_id)b on a.loging_id=b.int_id                                         "
			+"	WHERE a.licence_type='FL2B'  and    a.district_id=c.districtid                                                                                  "
			+"	GROUP BY c.description,   a.district_id, d.mgq, a.fl2b_650_ml,b.fl2b650_r,b.fl2b650_d  ,                                                        "
			+"	a.fl2b_500_ml,b.fl2b500_r,b.fl2b500_d,                                                                                                          "
			+"	a.fl2b_330_ml,b.fl2b330_r,b.fl2b330_d ,                                                                                                         "
			+"	a.fl2b_other_ml,b.fl2bother_r,b.fl2bother_d                                                                                                     "
			+"	union                                                                                                                                           "
			+"	select                                                                                                                                          "
			+"	a.district_id, c.description,   d.mgq, 0 as fl750,0 as fl375,0 as fl180,0 as other,0 as fl2b650,0 as fl2b500,0 as fl2b330,0 as fl2bother,       "
			+"	(COALESCE(a.no_of_box_25prc,0)+COALESCE(b.cl25r,0)-COALESCE(b.cl25d,0)) as cl25  ,                                                              "
			+"	(COALESCE(a.no_of_box_36prc,0)+COALESCE(b.cl36r,0)-COALESCE(b.cl36d,0) ) as cl36,                                                               "
			+"	(COALESCE(a.no_of_box_42prc,0)+COALESCE(b.cl45r,0)-COALESCE(b.cl45d,0) ) as cl45                                                                "
			+"	FROM public.district c  , fl2d.mst_mgq d,fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (select b.int_id, sum(COALESCE(b.cl25r,0))cl25r,        "
			+"sum(COALESCE(b.cl25d,0))cl25d ,sum(COALESCE(b.cl36r,0))cl36r,                                                                                      "
			+"	sum(COALESCE(b.cl36d,0))cl36d, sum(COALESCE(b.cl45r,0))cl45r,sum(COALESCE(b.cl45d,0))cl45d                                                      "
			+"	FROM fl2d.cl2_trxn b where   b.dt_date <='"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "' group by b.int_id)b on a.loging_id=b.int_id                                               "
			+"	WHERE a.licence_type='CL2'  and    a.district_id=c.districtid                                                                                   "
			+"	GROUP BY c.description,   a.district_id,d.mgq, a.no_of_box_25prc,b.cl25r,b.cl25d,                                                               "
			+"	a.no_of_box_36prc,b.cl36r,b.cl36d,                                                                                                              "
			+"	a.no_of_box_42prc,b.cl45r,b.cl45d )x                                                                                                            "
			+"	group  BY x.district_id, x.description,   x.mgq ORDER BY x.description   ";
		
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
			worksheet.setColumnWidth(1, 6000);
			worksheet.setColumnWidth(2, 8000);
			worksheet.setColumnWidth(3, 8000);
			worksheet.setColumnWidth(4, 8000);
			worksheet.setColumnWidth(5, 8000);
			worksheet.setColumnWidth(6, 8000);
			worksheet.setColumnWidth(7, 8000);
			worksheet.setColumnWidth(8, 8000);
			worksheet.setColumnWidth(9, 8000);
			worksheet.setColumnWidth(10, 8000);
			worksheet.setColumnWidth(11, 8000);
			worksheet.setColumnWidth(12, 8000);
			worksheet.setColumnWidth(13, 8000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" Consolidated Stock Report Of FL2, FL2B and CL2  On DATE : "+ " "+ Utility.convertUtilDateToSQLDate(act.getDtDate()));

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

			XSSFCell cellhead7 = rowhead.createCell((int) 2);
			cellhead7.setCellValue("MGQ");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 3);
			cellhead8.setCellValue("Stock Of CL(25%)");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 4);
			cellhead9.setCellValue("Stock Of CL(36%)");
			cellhead9.setCellStyle(cellStyle);

			XSSFCell cellhead10 = rowhead.createCell((int) 5);
			cellhead10.setCellValue("Stock Of CL(42%)");
			cellhead10.setCellStyle(cellStyle);

			XSSFCell cellhead11 = rowhead.createCell((int) 6);
			cellhead11.setCellValue("Stock Of FL2 750(ml)");
			cellhead11.setCellStyle(cellStyle);

			XSSFCell cellhead12 = rowhead.createCell((int) 7);
			cellhead12.setCellValue("Stock Of FL2 375(ml)");
			cellhead12.setCellStyle(cellStyle);

			XSSFCell cellhead13 = rowhead.createCell((int) 8);
			cellhead13.setCellValue("Stock Of FL2 180(ml)");
			cellhead13.setCellStyle(cellStyle);

			XSSFCell cellhead14 = rowhead.createCell((int) 9);
			cellhead14.setCellValue("Stock Of FL2 Other");
			cellhead14.setCellStyle(cellStyle);

			XSSFCell cellhead15 = rowhead.createCell((int) 10);
			cellhead15.setCellValue("Stock Of FL2B 650(ml)");
			cellhead15.setCellStyle(cellStyle);

			XSSFCell cellhead16 = rowhead.createCell((int) 11);
			cellhead16.setCellValue("Stock Of FL2B 500(ml)");
			cellhead16.setCellStyle(cellStyle);

			XSSFCell cellhead17 = rowhead.createCell((int) 12);
			cellhead17.setCellValue("Stock Of FL2B 330(ml)");
			cellhead17.setCellStyle(cellStyle);

			XSSFCell cellhead18 = rowhead.createCell((int) 13);
			cellhead18.setCellValue("Stock Of FL2B Other");
			cellhead18.setCellStyle(cellStyle);

			int i = 0;
			while (rs.next()) {
				/*
				 * Date dat =
				 * Utility.convertSqlDateToUtilDate(rs.getDate("dt"));
				 * DateFormat formatter; formatter = new
				 * SimpleDateFormat("dd/MM/yyyy"); date = formatter.format(dat);
				 */

				tot_cl25 = tot_cl25 + rs.getDouble("cl25");
				tot_cl36 = tot_cl36 + rs.getDouble("cl36");
				tot_cl42 = tot_cl42 + rs.getDouble("cl42");
				tot_fl2_750 = tot_fl2_750 + rs.getDouble("fl2_750ml");
				tot_fl2_375 = tot_fl2_375 + rs.getDouble("fl2_375ml");
				tot_fl2_180 = tot_fl2_180 + rs.getDouble("fl2_180ml");
				tot_fl2_othr = tot_fl2_othr + rs.getDouble("fl2_otherml");
				tot_fl2b_650 = tot_fl2b_650 + rs.getDouble("fl2b_650ml");
				tot_fl2b_500 = tot_fl2b_500 + rs.getDouble("fl2b_500ml");
				tot_fl2b_330 = tot_fl2b_330 + rs.getDouble("fl2b_330ml");
				tot_fl2b_othr = tot_fl2b_othr + rs.getDouble("fl2b_otherml");

				k++;
				XSSFRow row1 = worksheet.createRow((int) k);

				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k - 1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("mgq"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getDouble("cl25"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getDouble("cl36"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("cl42"));

				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getDouble("fl2_750ml"));

				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("fl2_375ml"));

				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getDouble("fl2_180ml"));

				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getDouble("fl2_otherml"));

				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getDouble("fl2b_650ml"));

				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(rs.getDouble("fl2b_500ml"));

				XSSFCell cellM1 = row1.createCell((int) 12);
				cellM1.setCellValue(rs.getDouble("fl2b_330ml"));

				XSSFCell cellN1 = row1.createCell((int) 13);
				cellN1.setCellValue(rs.getDouble("fl2b_otherml"));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "_Consolidated_WholesaleStock.xls");
			act.setExlname(n + "_Consolidated_WholesaleStock");

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
			cellA4.setCellValue(tot_cl25);
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(tot_cl36);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(tot_cl42);
			cellA6.setCellStyle(cellStyle);

			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(tot_fl2_750);
			cellA7.setCellStyle(cellStyle);

			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(tot_fl2_375);
			cellA8.setCellStyle(cellStyle);

			XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(tot_fl2_180);
			cellA9.setCellStyle(cellStyle);

			XSSFCell cellA10 = row1.createCell((int) 9);
			cellA10.setCellValue(tot_fl2_othr);
			cellA10.setCellStyle(cellStyle);

			XSSFCell cellA11 = row1.createCell((int) 10);
			cellA11.setCellValue(tot_fl2b_650);
			cellA11.setCellStyle(cellStyle);

			XSSFCell cellA12 = row1.createCell((int) 11);
			cellA12.setCellValue(tot_fl2b_500);
			cellA12.setCellStyle(cellStyle);

			XSSFCell cellA13 = row1.createCell((int) 12);
			cellA13.setCellValue(tot_fl2b_330);
			cellA13.setCellStyle(cellStyle);

			XSSFCell cellA14 = row1.createCell((int) 13);
			cellA14.setCellValue(tot_fl2b_othr);
			cellA14.setCellStyle(cellStyle);

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

	// --------------------------------generate excel for CL2--------------------

	public boolean writeCL2(WholesaleStockAction act) {

		Connection con = null;
		String sql = "";

		double stock_cl25 = 0;
		double stock_cl36 = 0;
		double stock_cl45 = 0;

		String filter = "";

		if (act.getDistrict().equalsIgnoreCase("9999")) {
			filter = "";
		} else {
			filter = "and  a.district_id='"+ Integer.parseInt(act.getDistrict()) + "' ";

		}

		/*sql =

		"	SELECT a.opening_date, a.licence_type, a.licence_no, a.licensee_name, b.dt_date, "
				+ "	((a.no_of_box_25prc+b.cl25r)-b.cl25d) as stock_cl25, "
				+ "	((a.no_of_box_36prc+b.cl36r)-b.cl36d) as stock_cl36, "
				+ "	((a.no_of_box_42prc+b.cl45r)-b.cl45d) as stock_cl45,c.description,a.district_id "
				+ "	FROM fl2d.opening_stock_fl2_fl2b_cl a, fl2d.cl2_trxn b, public.district c  "
				+ "	WHERE a.licence_type='CL2' AND a.loging_id=b.int_id AND a.district_id=c.districtid "
				+ "	 "
				+ filter
				+ " AND b.dt_date='"
				+ Utility.convertUtilDateToSQLDate(act.getDtDate())
				+ "'    "
				+ "	GROUP BY c.description, a.opening_date, a.district_id, a.licence_type, a.licence_no, a.licensee_name,  "
				+ "	a.no_of_box_25prc, a.no_of_box_36prc, a.no_of_box_42prc, b.cl25r, b.cl36r, b.cl45r,  "
				+ "	b.cl25d, b.cl36d, b.cl45d,b.dt_date  "
				+ "	ORDER BY a.licence_no ";*/

		
		sql =		"	select  '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  as opening_date, a.licence_type, a.licence_no, a.licensee_name,                                          "+
				"	a.district_id, c.description,     '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  as dt_date,                                                                                            "+
				"	(COALESCE(a.no_of_box_25prc,0)+COALESCE(b.cl25r,0)-COALESCE(b.cl25d,0)) as stock_cl25,	                                          "+
				"	(COALESCE(a.no_of_box_36prc,0)+COALESCE(b.cl36r,0)-COALESCE(b.cl36d,0) ) as stock_cl36,	                                        "+
				"	(COALESCE(a.no_of_box_42prc,0)+COALESCE(b.cl45r,0)-COALESCE(b.cl45d,0) ) as stock_cl45                                              "+
				"	FROM public.district c  ,fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (select b.int_id, sum(COALESCE(b.cl25r,0))cl25r,     "+
				"sum(COALESCE(b.cl25d,0))cl25d ,sum(COALESCE(b.cl36r,0))cl36r,                                                                   "+
				"	sum(COALESCE(b.cl36d,0))cl36d, sum(COALESCE(b.cl45r,0))cl45r,sum(COALESCE(b.cl45d,0))cl45d                                   "+
				"	FROM fl2d.cl2_trxn b where   b.dt_date <= '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  group by b.int_id)b on a.loging_id=b.int_id                            "+
				"	WHERE a.licence_type='CL2'  and    a.district_id=c.districtid  "+filter+"                                                               "+
				"	GROUP BY c.description,   a.district_id, a.licence_type,                                                                     "+
				"	a.licence_no, a.licensee_name, a.no_of_box_25prc,b.cl25r,b.cl25d  ,                                                          "+
				"	a.no_of_box_36prc,b.cl36r,b.cl36d,                                                                                           "+
				"	a.no_of_box_42prc,b.cl45r,b.cl45d  ,dt_date                                                                                           "+
				"	ORDER BY c.description, a.licence_no  ";                                                                                      
						                                                                 
						
		String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = "";
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			worksheet.setColumnWidth(0, 5000);
			worksheet.setColumnWidth(1, 5000);
			worksheet.setColumnWidth(2, 4000);
			worksheet.setColumnWidth(3, 6000);
			worksheet.setColumnWidth(4, 8000);
			worksheet.setColumnWidth(5, 8000);
			worksheet.setColumnWidth(6, 8000);
			worksheet.setColumnWidth(7, 9000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);

			cellhead0.setCellValue("CL2 Stock Report At Wholesale On DATE :"+ Utility.convertUtilDateToSQLDate(act.getDtDate()));

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
			cellhead3.setCellValue("Licence No");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Licensee Name");
			cellhead4.setCellStyle(cellStyle);
			

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Detail OF Stock 25%");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Detail Of Stock 36%");
			cellhead6.setCellStyle(cellStyle);

			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Detail Of stock 42%");
			cellhead7.setCellStyle(cellStyle);			

			int i = 0;

			while (rs.next()) {

				/*Date dat = Utility.convertSqlDateToUtilDate(rs
						.getDate("dt_date"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);*/

				stock_cl25 = stock_cl25 + (rs.getInt("stock_cl25"));
				stock_cl36 = stock_cl36 + (rs.getInt("stock_cl36"));
				stock_cl45 = stock_cl45 + (rs.getInt("stock_cl45"));

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
				cellE1.setCellValue(rs.getDouble("stock_cl25"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("stock_cl36"));

				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getDouble("stock_cl45"));
				

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+ "/ExciseUp/MIS/Excel/" + n + "_CL_WholeSaleStock.xls");
			act.setExlname(n + "_CL_WholeSaleStock");

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
			cellA4.setCellValue("Total");
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(stock_cl25);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(stock_cl36);
			cellA6.setCellStyle(cellStyle);

			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(stock_cl45);
			cellA7.setCellStyle(cellStyle);

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

			flag = true;
			act.setExcelFlag(true);
			con.close();
		} catch (Exception e) {
			//System.out.println("xls2" + e.getMessage());
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

	// -----------------excel for FL2B--------------------------

	public boolean writeFL2B(WholesaleStockAction act) {

		Connection con = null;		

		String sql = "";

		// String reportQuery = null;
		String filter = "";

		double stock_fl2b_650 = 0;
		double stock_fl2b_500 = 0;
		double stock_fl2b_330 = 0;
		double stock_fl2b_othr = 0;

		if (act.getDistrict().equalsIgnoreCase("9999")) {
			filter = "";
		} else {
			filter = "and  a.district_id='"+ Integer.parseInt(act.getDistrict()) + "' ";
		}

		/*sql =

				"	SELECT a.licence_type, a.licence_no, a.licensee_name, b.opening_date, "
						+ "	((a.fl2b_650_ml+b.fl2b650_r)-b.fl2b650_d) as stock_fl2b_650, "
						+ "	((a.fl2b_500_ml+b.fl2b500_r)-b.fl2b500_d) as stock_fl2b_500, "
						+ "	((a.fl2b_330_ml+b.fl2b330_r)-b.fl2b330_d) as stock_fl2b_330, "
						+ "	((a.fl2b_other_ml+b.fl2bother_r)-b.fl2bother_d) as stock_fl2b_othr, "
						+ "	c.description,a.district_id "
						+ "	FROM fl2d.opening_stock_fl2_fl2b_cl a, fl2d.fl2b_trxn b, public.district c  "
						+ "	WHERE a.licence_type='FL2B' AND a.loging_id=b.int_id AND a.district_id=c.districtid "
						+ "	"
						+ filter
						+ " AND b.opening_date='"
						+ Utility.convertUtilDateToSQLDate(act.getDtDate())
						+ "'  "
						+ "	GROUP BY c.description, a.opening_date, a.district_id, a.licence_type, a.licence_no, a.licensee_name,  "
						+ "	a.fl2b_650_ml, a.fl2b_500_ml, a.fl2b_330_ml, a.fl2b_other_ml, b.fl2b650_d, b.fl2b650_r, b.fl2b500_d,   "
						+ "	b.fl2b500_r, b.fl2b330_d, b.fl2b330_r, b.fl2bother_d, b.fl2bother_r, b.opening_date   "
						+ "	ORDER BY a.licence_no ";*/
		
		
		sql =	" select  '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  as opening_date, a.licence_type, a.licence_no, a.licensee_name,                                                  "+
				" a.district_id, c.description,                                                                                                        "+
				" (COALESCE(a.fl2b_650_ml,0)+COALESCE(b.fl2b650_r,0)-COALESCE(b.fl2b650_d,0)) as stock_fl2b_650  ,                                              "+
				" (COALESCE(a.fl2b_500_ml,0)+COALESCE(b.fl2b500_r,0)-COALESCE(b.fl2b500_d,0) ) as stock_fl2b_500,                                               "+
				" (COALESCE(a.fl2b_330_ml,0)+COALESCE(b.fl2b330_r,0)-COALESCE(b.fl2b330_d,0) ) as stock_fl2b_330 ,                                              "+
				" (COALESCE(a.fl2b_other_ml,0)+COALESCE(b.fl2bother_r,0) -COALESCE(b.fl2bother_d,0) ) as stock_fl2b_othr                                         "+
				" FROM public.district c  ,fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (select b.int_id, sum(COALESCE(b.fl2b650_r,0))fl2b650_r,         "+
				"sum(COALESCE(b.fl2b650_d,0))fl2b650_d ,sum(COALESCE(b.fl2b500_r,0))fl2b500_r,                                                         "+
				" sum(COALESCE(b.fl2b500_d,0))fl2b500_d, sum(COALESCE(b.fl2b330_r,0))fl2b330_r,sum(COALESCE(b.fl2b330_d,0))fl2b330_d ,                 "+
				"sum(COALESCE(b.fl2bother_r,0))fl2bother_r ,sum(COALESCE(b.fl2bother_d,0))fl2bother_d                                                  "+
				" FROM fl2d.fl2b_trxn b where   b.opening_date <= '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  group by b.int_id)b on a.loging_id=b.int_id                              "+
				" WHERE a.licence_type='FL2B'  and    a.district_id=c.districtid "+filter+"                                                                       "+
				" GROUP BY c.description,   a.district_id, a.licence_type,                                                                             "+
				" a.licence_no, a.licensee_name, a.fl2b_650_ml,b.fl2b650_r,b.fl2b650_d  ,                                                              "+
				" a.fl2b_500_ml,b.fl2b500_r,b.fl2b500_d,                                                                                               "+
				" a.fl2b_330_ml,b.fl2b330_r,b.fl2b330_d ,                                                                                              "+
				" a.fl2b_other_ml,b.fl2bother_r,b.fl2bother_d                                                                                          "+
				" ORDER BY c.description, a.licence_no " ;     


		
		String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = "";
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(sql);
			// System.out.println("==SQL=11111=" + sql);
			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			worksheet.setColumnWidth(0, 5000);
			worksheet.setColumnWidth(1, 5000);
			worksheet.setColumnWidth(2, 4000);
			worksheet.setColumnWidth(3, 6000);
			worksheet.setColumnWidth(4, 8000);
			worksheet.setColumnWidth(5, 8000);
			worksheet.setColumnWidth(6, 8000);
			worksheet.setColumnWidth(7, 9000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);

			cellhead0.setCellValue("FL2B Stock Report At Wholesale On DATE :"+ Utility.convertUtilDateToSQLDate(act.getDtDate()));

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
			cellhead3.setCellValue("Licence No");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Licensee Name");
			cellhead4.setCellStyle(cellStyle);
			

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Detail OF FL2B Stock 650(ml)");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Detail Of FL2B Stock 500(ml)");
			cellhead6.setCellStyle(cellStyle);

			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Detail Of FL2B stock 330(ml)");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("Detail Of FL2B stock Other");
			cellhead8.setCellStyle(cellStyle);


			int i = 0;

			while (rs.next()) {

				/*Date dat = Utility.convertSqlDateToUtilDate(rs
						.getDate("dt_date"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);*/

				stock_fl2b_650 = stock_fl2b_650 + (rs.getInt("stock_fl2b_650"));
				stock_fl2b_500 = stock_fl2b_500 + (rs.getInt("stock_fl2b_500"));
				stock_fl2b_330 = stock_fl2b_330 + (rs.getInt("stock_fl2b_330"));
				stock_fl2b_othr = stock_fl2b_othr+ (rs.getInt("stock_fl2b_othr"));

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
				cellE1.setCellValue(rs.getDouble("stock_fl2b_650"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("stock_fl2b_500"));

				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getDouble("stock_fl2b_330"));

				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("stock_fl2b_othr"));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;

			fileOut = new FileOutputStream(relativePath+ "/ExciseUp/MIS/Excel/" + n + "_FL2B_WholesaleStock.xls");

			act.setExlname(n + "_FL2B_WholesaleStock");

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
			cellA4.setCellValue("Total");
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(stock_fl2b_650);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(stock_fl2b_500);
			cellA6.setCellStyle(cellStyle);

			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(stock_fl2b_330);
			cellA7.setCellStyle(cellStyle);

			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(stock_fl2b_othr);
			cellA8.setCellStyle(cellStyle);

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

			flag = true;
			act.setExcelFlag(true);
			con.close();
		} catch (Exception e) {
			//System.out.println("xls2" + e.getMessage());
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

	// -----------------generate excel for FL2--------------------------

	public boolean writeFL2(WholesaleStockAction act) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double total_fl_750 = 0;
		double total_fl_375 = 0;
		double total_fl_180 = 0;
		double total_fl_othr = 0;
		String sql = "";

		String filter = "";

		if (act.getDistrict().equalsIgnoreCase("9999")) {
			filter = "";
		} else {
			filter = "and  a.district_id='"+ Integer.parseInt(act.getDistrict()) + "' ";
		}

		/*sql =

		"	SELECT b.opening_date, a.licence_type, a.licence_no, a.licensee_name, a.district_id,c.description, "
				+ "	((a.fl2_750_ml+b.fl750r)-b.fl750d) as stock_fl_750, "
				+ "	((a.fl2_375_ml+b.fl375r)-b.fl375d) as stock_fl_375, "
				+ "	((a.fl2_180_ml+b.fl180r)-b.fl180d) as stock_fl_180, "
				+ "	((a.fl2_other_ml+b.other_r)-b.other_d) as stock_fl_othr "
				+ "	FROM fl2d.opening_stock_fl2_fl2b_cl a, fl2d.fl2_trxn b, public.district c  "
				+ "	WHERE a.licence_type='FL2' AND a.loging_id::text=b.int_id AND a.district_id=c.districtid "
				+ "	"
				+ filter
				+ "  and b.opening_date=  '"
				+ Utility.convertUtilDateToSQLDate(act.getDtDate())
				+ "'  "
				+ "	GROUP BY c.description, b.opening_date, a.district_id, a.licence_type, a.licence_no, a.licensee_name, "
				+ "	a.fl2_750_ml, a.fl2_375_ml, a.fl2_180_ml, a.fl2_other_ml, b.fl750d, b.fl750r, b.fl375d, b.fl375r,  "
				+ "	b.fl180d, b.fl180r, b.other_d, b.other_r "
				+ "	ORDER BY a.licence_no ";*/
		
		
		
		sql =	"	select '"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  as opening_date, a.licence_type, a.licence_no, a.licensee_name, " +
				"	a.district_id, c.description,  " +
				"	(COALESCE(a.fl2_750_ml,0)+COALESCE(b.fl750r,0)-COALESCE(b.fl750d,0)) as stock_fl_750  , " +
				"	(COALESCE(a.fl2_375_ml,0)+COALESCE(b.fl375r,0)-COALESCE(b.fl375d,0) ) as stock_fl_375, " +
				"	(COALESCE(a.fl2_180_ml,0)+COALESCE(b.fl180r,0)-COALESCE(b.fl180d,0) ) as stock_fl_180 ," +
				"	(COALESCE(a.fl2_other_ml,0)+COALESCE(b.other_r,0) -COALESCE(b.other_d,0) ) as stock_fl_othr" +
				"	FROM public.district c  ,fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN (select b.int_id, sum(COALESCE(b.fl750r,0))fl750r,sum(COALESCE(b.fl750d,0))fl750d ,sum(COALESCE(b.fl375r,0))fl375r, " +
				"	sum(COALESCE(b.fl375d,0))fl375d, sum(COALESCE(b.fl180r,0))fl180r,sum(COALESCE(b.fl180d,0))fl180d ,sum(COALESCE(b.other_r,0))other_r ,sum(COALESCE(b.other_d,0))other_d " +
				"	FROM fl2d.fl2_trxn b where   b.opening_date <='"+ Utility.convertUtilDateToSQLDate(act.getDtDate())+ "'  group by b.int_id)b on a.loging_id::text=b.int_id " +
				"	WHERE a.licence_type='FL2'  and    a.district_id=c.districtid "+filter+" " +
				"	GROUP BY c.description,   a.district_id, a.licence_type, " +
				"	a.licence_no, a.licensee_name, a.fl2_750_ml,b.fl750r,b.fl750d  , " +
				"	a.fl2_375_ml,b.fl375r,b.fl375d, " +
				"	a.fl2_180_ml,b.fl180r,b.fl180d ," +
				"	a.fl2_other_ml,b.other_r,b.other_d" +
				"	ORDER BY c.description, a.licence_no " ;

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
			cellhead0.setCellValue(" FL2 Wholesale Stock Report on " + " "+ Utility.convertUtilDateToSQLDate(act.getDtDate()));

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
			cellhead9.setCellValue("Details Of FL2 Stock 750(ml)");
			cellhead9.setCellStyle(cellStyle);

			XSSFCell cellhead10 = rowhead.createCell((int) 5);
			cellhead10.setCellValue("Details Of FL2 Stock 375(ml)");
			cellhead10.setCellStyle(cellStyle);

			XSSFCell cellhead11 = rowhead.createCell((int) 6);
			cellhead11.setCellValue("Details Of FL2 Stock 180(ml)");
			cellhead11.setCellStyle(cellStyle);

			XSSFCell cellhead12 = rowhead.createCell((int) 7);
			cellhead12.setCellValue("Details Of FL2 Stock Other(ml)");
			cellhead12.setCellStyle(cellStyle);
			int i = 0;
			while (rs.next()) {

				total_fl_750 = total_fl_750 + rs.getDouble("stock_fl_750");
				total_fl_375 = total_fl_375 + rs.getDouble("stock_fl_375");
				total_fl_180 = total_fl_180 + rs.getDouble("stock_fl_180");
				total_fl_othr = total_fl_othr + rs.getDouble("stock_fl_othr");
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
				cellE1.setCellValue(rs.getDouble("stock_fl_750"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("stock_fl_375"));

				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getDouble("stock_fl_180"));

				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("stock_fl_othr"));
			}

			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "FL2_WholesaleStockReport.xls");
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
			cellA5.setCellValue(total_fl_750);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(total_fl_375);
			cellA6.setCellStyle(cellStyle);

			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(total_fl_180);
			cellA7.setCellStyle(cellStyle);

			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(total_fl_othr);
			cellA8.setCellStyle(cellStyle);

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
