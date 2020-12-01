package com.mentor.impl;

/////=========rahul 03-12-2019(print report)

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

import com.mentor.action.ChallanDepositDetailSuccessRptAction;
import com.mentor.action.ProdOfSpiritReportAction;

import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class ProdOfSpiritReportImpl {

	public void validateDistillery(ProdOfSpiritReportAction act) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			String q = " SELECT 'D' as login_type,int_app_id_f as app_id, vch_undertaking_name as name                    "
					+ " from public.dis_mst_pd1_pd2_lic                                                                          "
					+ " where vch_wrk_phon::text='"
					+ ResourceUtil.getUserNameAllReq().trim() + "'";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(q);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				act.dist_id = rs.getInt("app_id");
				System.out.println(rs.getInt("app_id"));
				act.dist_login = true;
			}

			else {
				act.dist_id = 0;
				act.dist_login = false;
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
	}

	public ArrayList getDistillery() {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue("0");
		list.add(item);
		try {
			String query = " select int_app_id_f,vch_undertaking_name  FROM  public.dis_mst_pd1_pd2_lic where vch_verify_flag='V' order by trim(vch_undertaking_name)";
			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				item = new SelectItem();
				item.setValue(rs.getString(1));
				item.setLabel(rs.getString(2));
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

	// ///=========rahul 03-12-2019

	public boolean printReportImpl(ProdOfSpiritReportAction act) {
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

			if (act.getRadio().equalsIgnoreCase("S")) {

				reportQuery = "select distinct dist_id,dist_name,sum(bl) as produ_bl,sum(al) as produ_al,sum(al2) as al2, sum(bl2) as bl2,sum(al3) as al3,sum(bl3) as bl3,(sum(al)-sum(al2)+sum(al3)) as total_prod_al, "
						+ " (sum(bl)-sum(bl2)+sum(bl3)) as total_prod_bl from  (select int_dist_id as dist_id,vch_undertaking_name as dist_name,"
						+ " db_produced_bl as bl,db_produced_al as al, 0 as al2, 0 as bl2, 0 as al3, 0 as bl3 from distillery.alchohal_production  left join public.dis_mst_pd1_pd2_lic on "
						+ " int_dist_id=int_app_id_f where int_dist_id=int_app_id_f and vch_verify_flag='V' and  dt_date between '"
						+ Utility.convertUtilDateToSQLDate(act.getFromdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'"
						+ " UNION  "
						+ " select distillery_id as dist_id,vch_undertaking_name as dist_name, recv_bl as bl,recv_al as al,0 as al2, 0 as bl2, 0 as al3, 0 as bl3 "
						+ " from distillery.import_spirit_in_state left join public.dis_mst_pd1_pd2_lic on distillery_id=int_app_id_f where distillery_id=int_app_id_f and vch_verify_flag='V' and dt_created between '"
						+ Utility.convertUtilDateToSQLDate(act.getFromdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "' "
						+ " UNION "
						+ " select int_dist_id as dist_id,vch_undertaking_name as dist_name, 0 as  bl, 0 as al ,trnsfer_bl as al2, trnsfer_al as bl2, 0 as al3, 0 as bl3 from  "
						+ " distillery.re_distillation_of_spirit_master left join public.dis_mst_pd1_pd2_lic on int_dist_id=int_app_id_f  where int_dist_id=int_app_id_f and vch_verify_flag='V' and created_date "
						+ " between '"
						+ Utility.convertUtilDateToSQLDate(act.getFromdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'  "
						+ " UNION "
						+ " select int_distillery_id as dist_id,vch_undertaking_name as dist_name, 0 as  bl,  0 as al ,0 as al2, 0 as bl2, "
						+ " quantity_al as al3, quantity_bl as bl3 from   distillery.received_from_plant_master "
						+ " left join public.dis_mst_pd1_pd2_lic on int_distillery_id=int_app_id_f  "
						+ " where int_distillery_id=int_app_id_f and vch_verify_flag='V' and created_date between '"
						+ Utility.convertUtilDateToSQLDate(act.getFromdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'"
						+ " UNION "
						+ " select distillery_id as dist_id,vch_undertaking_name as dist_name, "
						+ " quantity as bl, quantity_al as al ,0 as al2, 0 as bl2, 0 as al3, 0 as bl3 "
						+ " from distillery.spirit_import left join public.dis_mst_pd1_pd2_lic on distillery_id=int_app_id_f  "
						+ " where distillery_id=int_app_id_f and vch_verify_flag='V'  and dt_created between '"
						+ Utility.convertUtilDateToSQLDate(act.getFromdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'"
						+ " order by dist_name,dist_id)x group by dist_id,dist_name  order by dist_name";
			} else if (act.getRadio().equalsIgnoreCase("D")) {

				reportQuery = "select dist_id,dist_name,date,sum(bl) as produ_bl,sum(al) as produ_al,sum(al2) as al2,sum(bl2) as bl2,sum(al3) as al3,sum(bl3) as bl3,(sum(al)-sum(al2)+sum(al3)) as total_prod_al,"
						+ " (sum(bl)-sum(bl2)+sum(bl3)) as total_prod_bl from "
						+ " (select int_dist_id as dist_id,vch_undertaking_name as dist_name,dt_date as date, "
						+ " db_produced_bl as bl,db_produced_al as al, 0 as al2, 0 as bl2, 0 as al3, 0 as bl3 from distillery.alchohal_production "
						+ " left join public.dis_mst_pd1_pd2_lic on int_dist_id=int_app_id_f where int_dist_id='"
						+ act.dist_id
						+ "' and  dt_date between '"
						+ Utility.convertUtilDateToSQLDate(act.getFromdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'"
						+ " UNION "
						+ " select distillery_id as dist_id,vch_undertaking_name as dist_name,dt_created as date, recv_bl as bl,recv_al as al,0 as al2, 0 as bl2, 0 as al3, 0 as bl3"
						+ " from distillery.import_spirit_in_state left join public.dis_mst_pd1_pd2_lic on distillery_id=int_app_id_f where distillery_id='"
						+ act.dist_id
						+ "' and dt_created"
						+ " between '"
						+ Utility.convertUtilDateToSQLDate(act.getFromdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'"
						+ " UNION"
						+ " select int_dist_id as dist_id,vch_undertaking_name as dist_name, created_date as date, 0 as  bl,  0 as al ,trnsfer_bl as al2, trnsfer_al as bl2, 0 as al3,"
						+ " 0 as bl3 from   distillery.re_distillation_of_spirit_master left join public.dis_mst_pd1_pd2_lic on int_dist_id=int_app_id_f "
						+ " where int_dist_id='"
						+ act.dist_id
						+ "' and created_date between '"
						+ Utility.convertUtilDateToSQLDate(act.getFromdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "' "
						+ " UNION "
						+ " select int_distillery_id as dist_id,vch_undertaking_name as dist_name, "
						+ " created_date as date, 0 as  bl,  0 as al ,0 as al2, 0 as bl2, quantity_al as al3, quantity_bl as bl3 from   distillery.received_from_plant_master left join public.dis_mst_pd1_pd2_lic on"
						+ " int_distillery_id=int_app_id_f "
						+ " where int_distillery_id='"
						+ act.dist_id
						+ "' and created_date between '"
						+ Utility.convertUtilDateToSQLDate(act.getFromdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "' "
						+ " UNION "
						+ " select distillery_id as dist_id,vch_undertaking_name as dist_name,dt_created as date, "
						+ " quantity as bl, quantity_al as al ,0 as al2, 0 as bl2, 0 as al3, 0 as bl3 "
						+ " from distillery.spirit_import left join public.dis_mst_pd1_pd2_lic on distillery_id=int_app_id_f "
						+ " where distillery_id='"
						+ act.dist_id
						+ "' and dt_created between '"
						+ Utility.convertUtilDateToSQLDate(act.getFromdate())
						+ "' and '"
						+ Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'order by date)x group by date,dist_id,dist_name order by date";
			}

			pst = con.prepareStatement(reportQuery);

			rs = pst.executeQuery();
			if (rs.next()) {

				rs = pst.executeQuery();

				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("backgroundimg", relativePath + File.separator);
				parameters.put("fromDate",
						Utility.convertUtilDateToSQLDate(act.getFromdate()));
				parameters.put("toDate",
						Utility.convertUtilDateToSQLDate(act.getTodate()));

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				if (act.getRadio().equalsIgnoreCase("S")) {
					jasperReport = (JasperReport) JRLoader
							.loadObject(relativePath + File.separator
									+ "ProdOfSpiritReportSummery.jasper");

					JasperPrint print = JasperFillManager.fillReport(
							jasperReport, parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;

					JasperExportManager.exportReportToPdfFile(print,
							relativePathpdf + File.separator
									+ "ProdOfSpiritReportSummery" + n + ".pdf");

					// DateWiseDatatable dt = new DateWiseDatatable();

					act.setPdfname("ProdOfSpiritReportSummery" + n + ".pdf");
					act.setPrintFlag(true);
					printFlag = true;
				} else if (act.getRadio().equalsIgnoreCase("D")) {
					jasperReport = (JasperReport) JRLoader
							.loadObject(relativePath + File.separator
									+ "ProdOfSpiritReportDetail.jasper");

					JasperPrint print = JasperFillManager.fillReport(
							jasperReport, parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;

					JasperExportManager.exportReportToPdfFile(print,
							relativePathpdf + File.separator
									+ "ProdOfSpiritReportDetail" + n + ".pdf");

					// DateWiseDatatable dt = new DateWiseDatatable();

					act.setPdfname("ProdOfSpiritReportDetail" + n + ".pdf");
					act.setPrintFlag(true);
					printFlag = true;
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
				act.setPrintFlag(false);
				printFlag = false;
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

	// -----------------------generate excel---------------------------------

}
