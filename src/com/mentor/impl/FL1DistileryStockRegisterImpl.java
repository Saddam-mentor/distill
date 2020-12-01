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


import com.mentor.action.FL1DistileryStockRegisterAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class FL1DistileryStockRegisterImpl {


	public String getUserDetails(FL1DistileryStockRegisterAction act) {

		int id = 0;
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		String s = "";
		try {
			con = ConnectionToDataBase.getConnection();

			String selQr = 	" SELECT int_app_id_f, vch_undertaking_name, vch_wrk_add  " +
							" FROM public.dis_mst_pd1_pd2_lic  " +
							" WHERE vch_wrk_phon='"+ ResourceUtil.getUserNameAllReq().trim() + "' ";

			pstmt = con.prepareStatement(selQr);

			//System.out.println("login details---------------" + selQr);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				act.setLoginUserId(rs.getInt("int_app_id_f"));
				act.setLoginUserNm(rs.getString("vch_undertaking_name"));
				act.setLoginUserAdrs(rs.getString("vch_wrk_add"));

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

	// ===================get brand list======================

	public ArrayList getBrandList(FL1DistileryStockRegisterAction act) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = 	"";
		SelectItem item = new SelectItem();
		item.setLabel("--SELECT--");
		item.setValue("0");
		list.add(item);

		
		try {
			
			query = " SELECT DISTINCT a.int_brand_id, a.int_pckg_id, CONCAT(b.brand_name,'-',c.package_name) as brand "+  
					" FROM distillery.fl1_stock_trxn_19_20 a, distillery.brand_registration_19_20 b,                  "+
					" distillery.packaging_details_19_20 c                                                            "+
					" WHERE a.int_brand_id=b.brand_id AND a.int_pckg_id=c.package_id AND b.brand_id=c.brand_id_fk     "+
					" AND a.int_dissleri_id='"+act.getLoginUserId()+"'                                                "+
					" ORDER BY brand ";	
			

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);

			//System.out.println("brand query---------------------" + query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();
				item.setValue(rs.getString("int_pckg_id"));
				item.setLabel(rs.getString("brand"));

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
	
	// ===================get brand list======================

		public ArrayList getFL1LicenseNmbr(FL1DistileryStockRegisterAction act) {

			ArrayList list = new ArrayList();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query = 	"";
			SelectItem item = new SelectItem();
			item.setLabel("--SELECT--");
			item.setValue("0");
			list.add(item);

			
			try {

				query = " SELECT vch_licence_no FROM licence.licence_entery_fl3_fl1   "+						
						" WHERE vch_lic_unit_type='D' AND vch_licence_type='FL1'   "+
						" AND int_distillery_id='"+act.getLoginUserId()+"'   "+
						" ORDER BY vch_licence_no ";

				conn = ConnectionToDataBase.getConnection();
				pstmt = conn.prepareStatement(query);

				//System.out.println("license query---------------------" + query);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					item = new SelectItem();
					item.setValue(rs.getString("vch_licence_no"));
					item.setLabel(rs.getString("vch_licence_no"));

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
	
	// ===================get brand list======================

	public void printReport(FL1DistileryStockRegisterAction act){



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
				
			                                                                                                                            
		reportQuery = 	" SELECT z.brand_id, z.pckg_id, z.dist_id, z.license_no, z.dt, z.gatepass,                                       "+
						" br.brand_name, pk.package_name, z.opening, z.reciving, z.dispatch,                                             "+                                     
						" ms.vch_undertaking_name as firm_name                                                                           "+
						" FROM                                                                                                           "+
						" (SELECT y.brand_id, y.pckg_id, y.dist_id, y.license_no, y.dt, y.gatepass,                                      "+       
						" y.opening, SUM(y.recieving)reciving, SUM(y.dispatch)dispatch                                                   "+      
						" FROM                                                                                                           "+
						" (SELECT x.brand_id, x.pckg_id, x.dist_id, x.license_no,                                                        "+
						" (to_date('"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"','YYYY-MM-DD')  - INTERVAL '1' DAY) as dt,   "+     
						" 'OPENING' as gatepass,                                                                                         "+
						" SUM(x.recieving_opn-x.dispatch_opn) as opening, 0 as recieving, 0 as dispatch                                  "+      
						" FROM                                                                                                           "+
						" (SELECT a.int_brand_id as brand_id, a.int_pckg_id as pckg_id, a.int_dissleri_id as dist_id,                    "+                   
						" b.vch_to_lic_no as license_no, SUM(COALESCE(a.dispatchd_bottl,0)) as recieving_opn, 0 as dispatch_opn          "+                                  
						" FROM distillery.fl1_stock_trxn_19_20 a, distillery.gatepass_to_manufacturewholesale_19_20 b                    "+                                                  
						" WHERE a.int_dissleri_id=b.int_dist_id AND a.vch_gatepass_no=b.vch_gatepass_no                                  "+
						" AND b.dt_date < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                      "+
						" AND b.vch_to_lic_no='"+act.getLicenseNo()+"' AND b.int_dist_id='"+act.getLoginUserId()+"'                      "+
						" AND a.int_pckg_id='"+act.getBrandId()+"'        																 "+		   
						" GROUP BY a.int_brand_id, a.int_pckg_id, a.int_dissleri_id, b.vch_to_lic_no                                     "+
						" UNION                                                                                                          "+
						" SELECT a.int_brand_id as brand_id, a.int_pckg_id as pckg_id, a.int_dissleri_id as dist_id,                     "+                 
						" b.vch_from_lic_no as license_no, 0 as recieving_opn, SUM(COALESCE(a.dispatch_bottle,0)) as dispatch_opn        "+                                   
						" FROM distillery.fl2_stock_trxn_19_20 a, distillery.gatepass_to_districtwholesale_19_20 b                       "+                                               
						" WHERE a.int_dissleri_id=b.int_dist_id AND a.vch_gatepass_no=b.vch_gatepass_no                                  "+
						" AND b.dt_date < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                      "+
						" AND b.vch_from_lic_no='"+act.getLicenseNo()+"' AND b.int_dist_id='"+act.getLoginUserId()+"'                    "+
						" AND a.int_pckg_id='"+act.getBrandId()+"'        																 "+		   
						" GROUP BY a.int_brand_id, a.int_pckg_id, a.int_dissleri_id, b.vch_from_lic_no)x                                 "+
						" GROUP BY x.brand_id, x.pckg_id, x.dist_id, x.license_no                                                        "+
						"                                                                                                                "+
						" UNION ALL                                                                                                      "+
						"                                                                                                                "+
						" SELECT a.int_brand_id as brand_id, a.int_pckg_id as pckg_id, a.int_dissleri_id as dist_id,                     "+
						" b.vch_to_lic_no as license_no, b.dt_date as dt, b.vch_gatepass_no as gatepass, 0 as opening,                   "+
						" SUM(COALESCE(a.dispatchd_bottl,0)) as recieving, 0 as dispatch                                                 "+
						" FROM distillery.fl1_stock_trxn_19_20 a, distillery.gatepass_to_manufacturewholesale_19_20 b                    "+                                                  
						" WHERE a.int_dissleri_id=b.int_dist_id AND a.vch_gatepass_no=b.vch_gatepass_no                                  "+
						" AND b.dt_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                "+
						" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' AND b.vch_to_lic_no='"+act.getLicenseNo()+"'       "+                           
						" AND b.int_dist_id='"+act.getLoginUserId()+"' AND a.int_pckg_id='"+act.getBrandId()+"'        					 "+													   
						" GROUP BY a.int_brand_id, a.int_pckg_id, a.int_dissleri_id, b.vch_to_lic_no,                                    "+
						" b.dt_date, b.vch_gatepass_no                                                                                   "+
						" UNION                                                                                                          "+
						" SELECT a.int_brand_id as brand_id, a.int_pckg_id as pckg_id, a.int_dissleri_id as dist_id,                     "+
						" b.vch_from_lic_no as license_no, b.dt_date as dt, b.vch_gatepass_no as gatepass, 0 as opening,                 "+
						" 0 as recieving, SUM(COALESCE(a.dispatch_bottle,0)) as dispatch                                                 "+
						" FROM distillery.fl2_stock_trxn_19_20 a, distillery.gatepass_to_districtwholesale_19_20 b                       "+                                               
						" WHERE a.int_dissleri_id=b.int_dist_id AND a.vch_gatepass_no=b.vch_gatepass_no                                  "+
						" AND b.dt_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                "+
						" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' AND b.vch_from_lic_no='"+act.getLicenseNo()+"'     "+                              
						" AND b.int_dist_id='"+act.getLoginUserId()+"' AND a.int_pckg_id='"+act.getBrandId()+"'        					 "+													   
						" GROUP BY a.int_brand_id, a.int_pckg_id, a.int_dissleri_id, b.vch_from_lic_no,                                  "+
						" b.dt_date, b.vch_gatepass_no)y                                                                                 "+
						" GROUP BY y.brand_id, y.pckg_id, y.dist_id, y.license_no, y.dt, y.gatepass, y.opening)z,                        "+
						" distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pk,                                 "+
						" public.dis_mst_pd1_pd2_lic ms                                                                                  "+
						" WHERE br.brand_id=z.brand_id and br.brand_id=pk.brand_id_fk and z.pckg_id=pk.package_id                        "+
						" AND z.dist_id=ms.int_app_id_f AND ms.int_app_id_f='"+act.getLoginUserId()+"'                                   "+
						" ORDER BY z.dt ";                                                                                               
			

			

			pst = con.prepareStatement(reportQuery);
			//System.out.println("reportQuery-----FL1---------" + reportQuery);

			rs = pst.executeQuery();

			if(rs.next()) {

				
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "FL1DistilleryStockRegister.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print, relativePathpdf+ File.separator + "FL1DistilleryStockRegister"+"-"+act.getLoginUserId()+"_"+n+".pdf");
				act.setPdfName("FL1DistilleryStockRegister"+"-"+act.getLoginUserId()+"_"+n+".pdf");
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
