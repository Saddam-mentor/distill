package com.mentor.impl;

import java.io.File;
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

import com.mentor.action.FL1_1A_StockReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class FL1_1A_StockReportImpl {

	// --------------------get user id---------------------
	
	
	 
	
	//--------------------get user role--------------------------
	
	
	 
	//---------------------get details of distillery and brewery---------------------
	
	
	public String getDetails(FL1_1A_StockReportAction act)
	{

		int id = 0;
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		String queryList = null;
		String s = "";
		try {
			con = ConnectionToDataBase.getConnection();
			
			
			//System.out.println("----------------------role "+this.getUserRole());
			
			
			 queryList = " SELECT int_distillery_id as id,'' as name,'' as adrs FROM licence.fl3a_fl1a " +
						" WHERE   loginuser='"+ResourceUtil.getUserNameAllReq().trim()+"' ";
			 
			 

			pstmt = con.prepareStatement(queryList);

			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				
					act.setName(rs.getString("name"));
					act.setLoginId(rs.getInt("id"));
					act.setAddress(rs.getString("adrs"));
				

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
	
	
	// ------------------------------get fl1a license nmbr  list---------------------

	public ArrayList getFL1ALicenseNmbr(FL1_1A_StockReportAction act)
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
			
			 	
				query = " SELECT vch_license_fl1a FROM licence.fl3a_fl1a " +
						" WHERE vch_licence_type='"+act.getRadio()+"' AND loginuser='"+ResourceUtil.getUserNameAllReq().trim()+"' "
								+ "union "
								+ " select vch_licence_no from licence.licence_entery_fl3_fl1 where loginuser='"+ResourceUtil.getUserNameAllReq().trim()+"' ";
			 
			
			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("vch_license_fl1a"));
				item.setLabel(rs.getString("vch_license_fl1a"));

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
	
	// =======================print report for distillery=================================

	public void printReportDist(FL1_1A_StockReportAction act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "Distillery" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "Distillery" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String reportQuery1 = null;
		
		

		try {
			con = ConnectionToDataBase.getConnection();
						
			 
			
			reportQuery1 = 		" SELECT distinct  c.licence_no as vch_to_fl1_fl1a, a.package_name,c.brand as int_brand_id , b.brand_name, "
						+ " a.quantity, a.code_generate_through, c.stock_bottles-coalesce(c.dispatch_bottles,0)  as int_bottle_avaliable,(c.stock_box-c.dispatch_box) as no_boxes_avaliable  "
						+ " FROM distillery.packaging_details_20_21 a, distillery.brand_registration_20_21 b, "
						+ " distillery.fl2_stock_20_21 c  "
						+ " WHERE a.brand_id_fk=b.brand_id AND a.brand_id_fk=c.brand "
						+ " AND a.package_id=c.package AND b.brand_id=c.brand "
						+ " AND c.int_dist_id='"+act.getLoginId()+"'  and  c.licence_no= '"+act.getLicenseNo().trim()+"' AND c.stock_bottles-COALESCE(c.dispatch_bottles,0)>0  "
						+ "  group by   a.package_name, "+
							" b.brand_name, a.package_id ,c.brand,c.package, c.stock_bottles,c.dispatch_bottles, "+
							" c.stock_box,c.dispatch_box, a.quantity,c.licence_no , a.code_generate_through ,c.size ,c.int_dist_id  ";

	 pst = con.prepareStatement(reportQuery1);
			 		rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("distnm", act.getName());
				parameters.put("grpnm", act.getRadio());
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "FL1_1A_Stock_Dist.jasper");
				 

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "FL1_1A_Stock_Dist" + "-" + n + ".pdf");
				act.setPdfname("FL1_1A_Stock_Dist" + "-" + n + ".pdf");
				act.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found!!", "No Data Found!!"));
				act.setPrintFlag(false);
			}
		} catch (JRException e) {
			 
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), e.getMessage()));
		} catch (Exception e) {
			 
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), e.getMessage()));
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				 
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
						e.getMessage(), e.getMessage()));
			}
		}

	}
	
	
	
	// =======================print report for brewery=================================

	public void printReportBrew(FL1_1A_StockReportAction act)
	{


		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "Distillery" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "Distillery" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String reportQuery1 = null;
		
		

		try {
			con = ConnectionToDataBase.getConnection();
						
			reportQuery = 	" SELECT (select vch_licence_no from licence.licence_entery_fl3_fl1 " +
							" WHERE int_distillery_id='"+act.getLoginId()+"' AND vch_licence_type='FL1') as vch_lic_no, " +
							" c.int_pckg_id, c.int_brand_id, a.package_name, " +
							" b.brand_name, a.quantity,a.code_generate_through, " +
							" (SUM(COALESCE(c.dispatchd_bottl,0))-SUM(COALESCE(c.dispatch_36,0))) as avlbottle, " +
							" SUM(round(CAST(float8(((COALESCE(c.dispatchd_bottl,0))-COALESCE(c.dispatch_36,0))/d.box_size) as numeric), 0)) as avl_box " +
							" FROM distillery.packaging_details_20_21 a, distillery.brand_registration_20_21 b,  " +
							" distillery.fl1_stock_20_21 c, distillery.box_size_details d  " +
							" WHERE a.brand_id_fk=b.brand_id AND a.brand_id_fk=c.int_brand_id " +
							" AND a.package_id=c.int_pckg_id AND b.brand_id=c.int_brand_id " +
							" AND c.int_dist_id='"+act.getLoginId()+"' AND c.vch_to_fl1_fl1a='"+act.getRadio()+"'" +
							" AND COALESCE(c.dispatch_36,0)<c.dispatchd_bottl  " +
							" AND a.box_id=d.box_id AND a.quantity=d.qnt_ml_detail " +
							" GROUP BY c.int_brand_id, c.int_pckg_id, d.box_size , " +
							" b.brand_name, a.package_name, a.quantity, a.code_generate_through  " +
							" ORDER BY b.brand_name ";
			
			
			reportQuery1 = 	" SELECT c.vch_to_fl1_fl1a, c.int_pckg_id, c.int_brand_id, a.package_name, " +
							" b.brand_name, a.quantity,a.code_generate_through, " +
							" (SUM(COALESCE(c.dispatchd_bottl,0))-SUM(COALESCE(c.dispatch_36,0))) as avlbottle, " +
							" SUM(round(CAST(float8(((COALESCE(c.dispatchd_bottl,0))-COALESCE(c.dispatch_36,0))/d.box_size) as numeric), 0)) as avl_box " +
							" FROM distillery.packaging_details_20_21 a, distillery.brand_registration_20_21 b,  " +
							" distillery.fl1_stock_20_21 c, distillery.box_size_details d  " +
							" WHERE a.brand_id_fk=b.brand_id AND a.brand_id_fk=c.int_brand_id " +
							" AND a.package_id=c.int_pckg_id AND b.brand_id=c.int_brand_id " +
							" AND c.int_dist_id='"+act.getLoginId()+"' AND c.vch_to_fl1_fl1a='"+act.getRadio()+"' " +
							" AND c.lic_no='"+act.getLicenseNo()+"' " +
							" AND COALESCE(c.dispatch_36,0)<c.dispatchd_bottl  " +
							" AND a.box_id=d.box_id AND a.quantity=d.qnt_ml_detail " +
							" GROUP BY c.int_brand_id, c.int_pckg_id, d.box_size, c.vch_to_fl1_fl1a, " +
							" b.brand_name, a.package_name, a.quantity, a.code_generate_through  " +
							" ORDER BY b.brand_name ";
			
			
			if(act.getRadio().equalsIgnoreCase("FL1"))
			{
				pst = con.prepareStatement(reportQuery);
				//System.out.println("reportQuery------------"+reportQuery);
			}
			else if(act.getRadio().equalsIgnoreCase("FL1A"))
			{
				pst = con.prepareStatement(reportQuery1);
				//System.out.println("reportQuery1-----11-------"+reportQuery1);
			}

				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("distnm", act.getName());
				parameters.put("grpnm", act.getRadio());
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "FL1_1A_Stock_Brew.jasper");
				 

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "FL1_1A_Stock_Brew" + "-" + n + ".pdf");
				act.setPdfname("FL1_1A_Stock_Brew" + "-" + n + ".pdf");
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
