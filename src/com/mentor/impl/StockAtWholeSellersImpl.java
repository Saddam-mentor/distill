package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 
import com.mentor.action.StockAtWholeSellersAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class StockAtWholeSellersImpl {
	public void getDetails(StockAtWholeSellersAction ac) {
		String imfl = "";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select  distinct b.description ,b.districtid  from public.district b "
				+ "where    b.deo='"
				+ ResourceUtil.getUserNameAllReq().trim()
				+ "'";
		
		// System.out.println("query="+query);
		try {
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				/*ac.setLicence_type(rs.getString("vch_license_type"));
				ac.setInt_id(rs.getInt("int_app_id"));
				ac.setLicence_no(rs.getString("vch_licence_no"));
				ac.setApplicant_name(rs.getString("vch_applicant_name"));*/
				ac.setDistrict(rs.getString("description"));

				ac.setDistrictId(rs.getInt("districtid"));

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
	}

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

	public void printReport(StockAtWholeSellersAction act) {

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
		String filter = null; 
		String dist=null;
		//System.out.println("radio="+act.getRadio());
		if (ResourceUtil.getUserNameAllReq().trim().substring(0, 10)
				.equalsIgnoreCase("Excise-DEO")) {
			//filter = "and d.core_district_id='" + act.getDistrictId() + "'";
			filter="'"+String.valueOf(act.getDistrictId())+"'";
			} else if(Integer.parseInt(act.getDistid())== 9999){
			filter = null;
			
		}else{
			filter="'"+act.getDistid()+"'";
			//filter = "and d.core_district_id=" + Integer.parseInt(act.getDistid()) + "";
		}
		 

		try {
			con = ConnectionToDataBase.getConnection(); 
		 
			Date FromDate=Utility.convertUtilDateToSQLDate(act.getFromDate());
			Date toDate=Utility.convertUtilDateToSQLDate(act.getToDate());
			
			 reportQuery = "select * from fl2d.getstock('"+FromDate+"','" + toDate+ "','"+act.getRadio()+"', "+filter+")";
			

			 
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
				parameters.put("vch_licence_no", act.getLicence_no());
				parameters.put("vch_applicant_name", act.getApplicant_name());
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				/*ac.setLicence_no(rs.getString("vch_licence_no"));
				ac.setApplicant_name(rs.getString("vch_applicant_name"));
				*/
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator
						+ "StockReportAtWholesellers.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator + "StockReportAtWholesellers" + "-" + n + ".pdf");
				act.setPdfname("StockReportAtWholesellers" + "-" + n
						+ ".pdf");
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

}
