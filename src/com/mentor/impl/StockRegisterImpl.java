package com.mentor.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.mentor.action.StockRegisterAction;
import com.mentor.action.DelayedExceptionReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class StockRegisterImpl {
	
	public String getDetails(StockRegisterAction ac) {
		int id = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String s = "";
		try {
			con = ConnectionToDataBase.getConnection();
			String queryList = " SELECT int_app_id, vch_applicant_name, vch_firm_name ,"
					+ " vch_mobile_no,vch_license_type, vch_licence_no "
					+ " FROM licence.fl2_2b_2d WHERE loginid= ? ";

			pstmt = con.prepareStatement(queryList);
			pstmt.setString(1, ResourceUtil.getUserNameAllReq().trim());

			rs = pstmt.executeQuery();

			while (rs.next()) {

				ac.setName(rs.getString("vch_firm_name"));
				ac.setDisId(rs.getInt("int_app_id"));

			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();

				if (rs != null)
					rs.close();

				if (con != null)
					con.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return "";

	}

	

	
		
		
		public void printReport(StockRegisterAction act) {


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
				
				reportQuery = "select x.gatepass, x.bottle,x.box,x.dt,x.brand_name,x.package_name,type " +
						" from(" +
						"						 SELECT DISTINCT a.vch_gatepass_no as gatepass, SUM(a.dispatch_bottle) as bottle,SUM(a.dispatch_box) as box,a.dt,b.brand_name ,c.package_name " +
						"						,'Dispatch' as type FROM fl2d.fl2_stock_trxn_fl2_fl2b a,distillery.brand_registration b,distillery.packaging_details c" +
						"						 where b.brand_id = a.int_brand_id  and c.package_id = a.int_pckg_id and a.int_fl2_fl2b_id='"+ act.getDisId() +"' " +
						"						and a.dt  BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+"' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+"' " +
						"						 group by a.dt, a.vch_gatepass_no,b.brand_name ,c.package_name " +
						"							 union all" +
						"				 SELECT DISTINCT a.gatepass_no as gatepass, SUM(a.total_recv_bottels) as bottle,SUM(a.recv_box) as box,a.created_date as dt,b.brand_name ,c.package_name " +
						"						 ,'Reciept' as type FROM fl2d.fl2_2b_receiving_stock_trxn a,distillery.brand_registration b,distillery.packaging_details c" +
						"						 where b.brand_id = a.brand_id  and c.package_id = a.pckg_id and a.fl2_2bid='"+ act.getDisId() +"' " +
						"						and a.created_date  BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+"' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+"'  " +
						"						 group by a.created_date,a.gatepass_no,b.brand_name ,c.package_name" +
						"  )x group by dt,type,gatepass,bottle,box,brand_name,package_name";
						
						
					/*	
						"SELECT DISTINCT a.gatepass_no as gatepass, SUM(a.total_recv_bottels) as bottle,SUM(a.recv_box) as box,a.created_date as dt,b.brand_name ,c.package_name " +
						" FROM fl2d.fl2_2b_receiving_stock_trxn a,distillery.brand_registration b,distillery.packaging_details c " +
						" where b.brand_id = a.brand_id  and c.package_id = a.pckg_id and a.fl2_2bid='"+ act.getDisId() +"' " +
						"and a.created_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+"' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+"' " +
						" group by a.gatepass_no,a.created_date,b.brand_name ,c.package_name";
						
*/
				
					pst = con.prepareStatement(reportQuery);
					
			 	
					rs = pst.executeQuery();

				if (rs.next()) {

					rs = pst.executeQuery();
					Map parameters = new HashMap();
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("SUBREPORT_DIR", relativePath + File.separator);
					parameters.put("image", relativePath + File.separator);
					parameters.put("radio",act.getRadio());
					parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
					parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
					parameters.put("disId",act.getDisId());
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
					

					
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "StockRegister.jasper");
					

					JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "StockRegister" + "-" + n + ".pdf");
					act.setPdfName("StockRegister" + "-" + n + ".pdf");
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
		
		
		

	}

	


	

