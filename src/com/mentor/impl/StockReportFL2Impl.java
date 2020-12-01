package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
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

import com.mentor.action.DispatchReportDIST_BRE_OLDSTOCK_Action;
import com.mentor.action.StockAtWholeSellersAction;
import com.mentor.action.StockReportFL2Action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class StockReportFL2Impl {
	
	
	public void getDetails(StockReportFL2Action ac) {
		String imfl = "";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select  a.vch_license_type,a.int_app_id, a.vch_licence_no,a.vch_applicant_name,b.description,a.core_district_id  "
				+ " from licence.fl2_2b_2d a,public.district b "
				+ "where   b.districtid=a.core_district_id "
				+ "and  a.loginid='"
				+ ResourceUtil.getUserNameAllReq().trim()
				+ "'";
		
		// System.out.println("query="+query);
		try {
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				ac.setLicence_type(rs.getString("vch_license_type"));
				ac.setInt_id(rs.getInt("int_app_id"));
				ac.setLicence_no(rs.getString("vch_licence_no"));
				ac.setApplicant_name(rs.getString("vch_applicant_name"));
				ac.setDistrict(rs.getString("description"));

				ac.setDistrictId(rs.getInt("core_district_id"));

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
	
	
	
	public ArrayList getBrandList(StockReportFL2Action act) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue(0);
		list.add(item);

		try {
			String query = " select distinct brand_name,package_name,a.pckg_id,  brand_name || '(' || package_name || ')'||' - '||b.unit_name as brandpack  " +
					" from fl2d.fl2_2b_receiving_stock_trxn a , distillery.brand_registration b,distillery.packaging_details c" +
					" where a.brand_id=b.brand_id and c.brand_id_fk=b.brand_id and a.pckg_id=c.package_id and fl2_2bid='"+act.getInt_id()+"' order by brandpack ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			 

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("pckg_id"));
				item.setLabel(rs.getString("brandpack"));
				//act.setBrandpack(rs.getString("brandpack"));

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

	public void printReport(StockReportFL2Action act)
	{

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
			
			reportQuery = "select distinct d.brand_name || '(' || c.package_name || ')'||' - '||d.unit_name as brandpack ,x.date ,d.brand_name,c.package_name, x.pckg_id,x.brand_id,x.gatepass_no,SUM(x.recv_bottels) as recv_bottels,SUM(x.dispatch_bottle) as dispatch_bottle " +
					" from(select distinct a.created_date as date ,a.pckg_id as pckg_id,a.brand_id as brand_id,a.total_recv_bottels as recv_bottels" +
					"			,a.gatepass_no as gatepass_no,0  as dispatch_bottle" +
					"			from fl2d.fl2_2b_receiving_stock_trxn a,fl2d.fl2_stock_trxn_fl2_fl2b b" +
					"			where a.fl2_2bid='"+act.getInt_id()+"' and a.created_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'" +
					"	 		and a.pckg_id=b.int_pckg_id and a.brand_id=b.int_brand_id and a.fl2_2bid::int=b.int_fl2_fl2b_id" +
					"			union" +
					"			select distinct b.dt as date ,b.int_pckg_id as pckg_id,b.int_brand_id as brand_id, 0 as recv_bottels ," +
					"			b.vch_gatepass_no as gatepass_no ,b.dispatch_bottle as dispatch_bottle" +
					"			from fl2d.fl2_2b_receiving_stock_trxn a, fl2d.fl2_stock_trxn_fl2_fl2b b" +
					"			where b.int_fl2_fl2b_id = '"+act.getInt_id()+"' and b.dt between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'" +
					"	        and a.pckg_id=b.int_pckg_id and a.brand_id=b.int_brand_id and a.fl2_2bid::int=b.int_fl2_fl2b_id)x ," +
					"			 distillery.packaging_details c, distillery.brand_registration d" +
					"			 where x.pckg_id=c.package_id and x.brand_id=d.brand_id and x.pckg_id='"+act.getPackId()+"'" +
					"			group by x.date,d.brand_name,d.unit_name,c.package_name,x.pckg_id,x.brand_id,x.gatepass_no ";

			
				pst = con.prepareStatement(reportQuery);
				
				rs = pst.executeQuery();
				 

			if (rs.next()) {
				

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("brandName",act.getBrandpack());
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				parameters.put("balance",act.getNewb());
				parameters.put("exDate",Utility.convertUtilDateToSQLDate(act.getLastDate()));
				parameters.put("user",ResourceUtil.getUserNameAllReq().trim());

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "Stock_FL2_FL2BReport.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "Stock_FL2_FL2BReport" + "-" + n + ".pdf");
				act.setPdfName("Stock_FL2_FL2BReport" + "-" + n + ".pdf");
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
	
	
	public void getopening(StockReportFL2Action ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = " SELECT 	COALESCE(sum(Y.recv_bottels - Y.dispatch_bottle),0) as opening FROM(select distinct x.date ,d.brand_name,c.package_name, x.pckg_id,x.brand_id," +
				" x.gatepass_no,SUM(x.recv_bottels) as recv_bottels,SUM(x.dispatch_bottle) as dispatch_bottle" +
				" from(select distinct a.created_date as date ,a.pckg_id as pckg_id,a.brand_id as brand_id," +
				"	 a.total_recv_bottels as recv_bottels ,a.gatepass_no as gatepass_no,0  as dispatch_bottle	" +
				"	 from fl2d.fl2_2b_receiving_stock_trxn a,fl2d.fl2_stock_trxn_fl2_fl2b b	" +
				"	 where a.fl2_2bid='"+ac.getInt_id()+"' and a.created_date < '"+Utility.convertUtilDateToSQLDate(ac.getFromDate())+"' 	" +
				"	 and a.pckg_id=b.int_pckg_id and a.brand_id=b.int_brand_id and a.fl2_2bid::int=b.int_fl2_fl2b_id" +
				"	 union		" +
				"	 select distinct b.dt as date ,b.int_pckg_id as pckg_id,b.int_brand_id as brand_id, 0 as recv_bottels ,			b.vch_gatepass_no as gatepass_no ,b.dispatch_bottle as dispatch_bottle" +
				"	 from fl2d.fl2_2b_receiving_stock_trxn a, fl2d.fl2_stock_trxn_fl2_fl2b b	" +
				"	 where b.int_fl2_fl2b_id = '"+ac.getInt_id()+"' and b.dt < '"+Utility.convertUtilDateToSQLDate(ac.getFromDate())+"' 	 " +
				"	 and a.pckg_id=b.int_pckg_id and a.brand_id=b.int_brand_id and a.fl2_2bid::int=b.int_fl2_fl2b_id)x ," +
				"	 distillery.packaging_details c, distillery.brand_registration d	" +
				"	 where x.pckg_id=c.package_id and x.brand_id=d.brand_id and x.pckg_id='"+ac.getPackId()+"'		" +
				"group by x.date,d.brand_name,c.package_name,x.pckg_id,x.brand_id,x.gatepass_no ) y ";
		
		try {
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			System.out.println("check query===>"+query);
			if (rs.next()) {
				ac.setBalance(rs.getInt("opening"));
				ac.setNewb(rs.getInt("opening"));
				

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
	
	

}
