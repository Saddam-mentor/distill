package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.mentor.action.WholesaleStockRegisterBrandWiseAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class WholesaleStockRegisterBrandWiseImpl {

	public String getUserDetails(WholesaleStockRegisterBrandWiseAction act) {

		int id = 0;
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		String s = "";
		try {
			con = ConnectionToDataBase.getConnection();

			String selQr = 	" SELECT int_app_id, vch_licence_no, vch_firm_name, vch_license_type, " +
							" vch_core_address, vch_mobile_no  " +
							" FROM licence.fl2_2b_2d_20_21  " +
							" WHERE loginid='"+ ResourceUtil.getUserNameAllReq().trim() + "' ";

			pstmt = con.prepareStatement(selQr);

			System.out.println("login details---------------" + selQr);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				act.setLoginUserId(rs.getInt("int_app_id"));
				act.setLoginUserNm(rs.getString("vch_firm_name"));
				act.setLoginUserType(rs.getString("vch_license_type"));
				if (rs.getString("vch_license_type").equalsIgnoreCase("FL2")) {
					act.setRadioType("FL2");

				} else if (rs.getString("vch_license_type").equalsIgnoreCase("FL2B")) {
					act.setRadioType("FL2B");

				} else if (rs.getString("vch_license_type").equalsIgnoreCase("CL2")) {
					act.setRadioType("CL2");
				}
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

	public ArrayList getBrandList(WholesaleStockRegisterBrandWiseAction act) {

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

			query = " SELECT DISTINCT a.brand_id, a.pckg_id, CONCAT(b.brand_name,'-',c.package_name) as brand   "+
					" FROM fl2d.fl2_2b_receiving_stock_trxn_20_21 a, distillery.brand_registration_20_21 b,  "+
					" distillery.packaging_details_20_21 c   "+
					" WHERE a.brand_id=b.brand_id AND a.pckg_id=c.package_id AND b.brand_id=c.brand_id_fk   "+
					" AND a.fl2_2bid='"+act.getLoginUserId()+"'   "+
					" ORDER BY brand ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);

			System.out.println("wholeasle query---------------------" + query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();
				item.setValue(rs.getString("pckg_id"));
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

	public void printReport(WholesaleStockRegisterBrandWiseAction act){



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
		
			//---------------with breakage----------------
			
		reportQuery = 	" SELECT z.brand_id, z.pckg_id, z.fl2_2bid, z.dt, z.gatepass, br.brand_name, pk.package_name,                              "+
						" CASE WHEN z.shop_nm='' then 'HBR/BRC' else z.shop_nm end as shop_nm,                                                     "+
						" z.opening, z.reciving, z.dispatch, z.breakage,                                                                           "+       
						" CONCAT(ms.vch_firm_name,' - ',ms.vch_licence_no)as firm_name, dd.description as district                                 "+
						" FROM                                                                                                                     "+
						" (SELECT y.brand_id, y.pckg_id, y.fl2_2bid, y.dt, y.gatepass, y.shop_nm,                                                  "+
						" y.opening, sum(y.reciving)reciving, sum(y.dispatch)dispatch, sum(y.breakage)breakage                                     "+                    
						" FROM                                                                                                                     "+
						" (SELECT x.brand_id, x.pckg_id, x.fl2_2bid,                                                                               "+
						" (to_date('"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"','YYYY-MM-DD')  - INTERVAL '1' DAY) as dt,             "+
						" 'OPENING' as gatepass, '------' as shop_nm,                                                                              "+
						" SUM(x.recieving_opn-x.dispatch_opn-x.breakage_opn) as opening, 0 as reciving, 0 as dispatch, 0 as breakage               "+                        
						" FROM                                                                                                                     "+
						" (SELECT a.brand_id as brand_id,  a.pckg_id as pckg_id, a.fl2_2bid as fl2_2bid,                                           "+
						" SUM(COALESCE(a.total_recv_bottels,0)) as recieving_opn, 0 as dispatch_opn, 0 as breakage_opn                             "+              
						" FROM fl2d.fl2_2b_receiving_stock_trxn_20_21 a                                                                            "+
						" WHERE a.created_date < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                         "+
						" AND a.fl2_2btype='"+act.getRadioType()+"' AND a.fl2_2bid='"+act.getLoginUserId()+"'                                      "+
						" AND a.pckg_id='"+act.getBrandId()+"'        																		       "+
						" GROUP BY a.brand_id, a.pckg_id, a.fl2_2bid                                                                               "+
						" UNION                                                                                                                    "+
						" SELECT b.int_brand_id as brand_id, b.int_pckg_id as pckg_id, b.int_fl2_fl2b_id::text  as fl2_2bid,                       "+
						" 0 as recieving_opn, SUM(COALESCE(b.dispatch_bottle,0)) as dispatch_opn, 0 as breakage_opn                                "+               
						" FROM fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a                            "+                                                
						" WHERE b.dt < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'   and a.vch_gatepass_no=b.vch_gatepass_no         "+                                     
						" AND a.vch_from='"+act.getRadioType()+"' AND b.int_fl2_fl2b_id='"+act.getLoginUserId()+"'                                 "+
						" AND b.int_pckg_id='"+act.getBrandId()+"'                                                                                 "+
						" GROUP BY b.int_brand_id, b.int_pckg_id, b.int_fl2_fl2b_id                                                                "+
						" UNION                                                                                                                    "+
						" SELECT b.brand_id as brand_id, b.pckg_id as pckg_id, b.fl2_2b_id::text  as fl2_2bid,                                     "+
						" 0 as recieving_opn, 0 as dispatch_opn, SUM(b.breakage) as breakage_opn                                                   "+
						" FROM fl2d.wholesale_godown_stock_breakage b                                                                              "+
						" WHERE b.cr_date < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' AND b.fl2_2b_id='"+act.getLoginUserId()+"'   "+
						" AND b.pckg_id='"+act.getBrandId()+"' AND b.fl2_2b_type='"+act.getRadioType()+"'                                          "+
						" GROUP BY b.brand_id, b.pckg_id, b.fl2_2b_id)x                                                                            "+
						" GROUP BY x.brand_id, x.pckg_id, x.fl2_2bid                                                                               "+
						"                                                                                                                          "+
						" UNION ALL                                                                                                                "+
						"                                                                                                                          "+
						" SELECT a.brand_id as brand_id,  a.pckg_id as pckg_id, a.fl2_2bid as fl2_2bid,                                            "+
						" a.created_date as dt, a.gatepass_no as gatepass, '------' as shop_nm, 0 as opening,                                      "+
						" SUM(COALESCE(a.total_recv_bottels,0)) as reciving, 0 as dispatch, 0 as breakage                                          "+          
						" FROM fl2d.fl2_2b_receiving_stock_trxn_20_21 a                                                                            "+
						" WHERE a.created_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                   "+
						" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' AND a.fl2_2btype='"+act.getRadioType()+"'                    "+
						" AND a.fl2_2bid='"+act.getLoginUserId()+"' AND a.pckg_id='"+act.getBrandId()+"'                                           "+   
						" GROUP BY a.brand_id, a.pckg_id, a.fl2_2bid, a.created_date, a.gatepass_no                                                "+
						" UNION                                                                                                                    "+
						" SELECT b.int_brand_id as brand_id, b.int_pckg_id as pckg_id, b.int_fl2_fl2b_id::text as fl2_2bid,                        "+
						" b.dt as dt, b.vch_gatepass_no as gatepass, c.shop_nm, 0 as opening, 0 as reciving,                                       "+
						" SUM(COALESCE(b.dispatch_bottle,0)) as dispatch, 0 as breakage                                                            "+          
						" FROM fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b, fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 c                           "+
						" WHERE b.vch_gatepass_no=c.vch_gatepass_no AND b.dt=c.dt_date AND b.int_fl2_fl2b_id=c.int_fl2_fl2b_id                     "+
						" AND b.dt BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                               "+
						" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' AND c.vch_from='"+act.getRadioType()+"'                      "+                                           
						" AND b.int_fl2_fl2b_id='"+act.getLoginUserId()+"' AND b.int_pckg_id='"+act.getBrandId()+"'                                "+   
						" GROUP BY b.int_brand_id, b.int_pckg_id, b.int_fl2_fl2b_id, b.dt, b.vch_gatepass_no, c.shop_nm                            "+
						" UNION                                                                                                                    "+
						" SELECT b.brand_id as brand_id, b.pckg_id as pckg_id, b.fl2_2b_id::text as fl2_2bid,                                      "+
						" b.cr_date as dt, 'BREAKAGE STOCK' as gatepass, '------' as shop_nm, 0 as opening, 0 as reciving,                         "+         
						" 0 as dispatch, SUM(b.breakage) as breakage                                                                               "+
						" FROM fl2d.wholesale_godown_stock_breakage b                                                                              "+
						" WHERE b.cr_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                        "+
						" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' AND b.fl2_2b_type='"+act.getRadioType()+"'                   "+                                              
						" AND b.fl2_2b_id='"+act.getLoginUserId()+"' AND b.pckg_id='"+act.getBrandId()+"'                                          "+
						" GROUP BY b.brand_id, b.pckg_id, b.fl2_2b_id, b.cr_date  UNION                                                                "+
							" SELECT  b.brand_id as brand_id, b.package_id as pckg_id, b.unit_id::text as fl2_2bid,b.finalize_dt as dt,                 "+
							"   'ROLLOVER STOCK' as gatepass, '------' as shop_nm, 0 as opening,                                                        "+
							"   b.rollover_bottles as reciving,   0 as dispatch, 0 as breakage                                                          "+
							"	FROM fl2d.rollover_fl_stock b    WHERE b.finalize_dt BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'    "+
							"   AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' AND b.finaliz_flg='T'  AND b.unit_id='"+act.getLoginUserId()+"' AND b.package_id='"+act.getBrandId()+"'  "+   
							"   GROUP BY b.brand_id, b.package_id, b.unit_id, b.finalize_dt , b.rollover_bottles                                      "+                                                                  
						" )y                                                                                                                       "+
						" GROUP BY y.brand_id, y.pckg_id, y.fl2_2bid, y.dt, y.gatepass, y.shop_nm, y.opening)z,                                    "+
						" distillery.brand_registration_20_21 br, distillery.packaging_details_20_21 pk,                                           "+
						" licence.fl2_2b_2d_20_21 ms, public.district dd                                                                           "+
						" WHERE br.brand_id=z.brand_id and br.brand_id=pk.brand_id_fk and z.pckg_id=pk.package_id                                  "+
						" AND z.fl2_2bid=ms.int_app_id::text AND dd.districtid=ms.core_district_id                                                 "+
						" AND ms.vch_license_type='"+act.getRadioType()+"' AND ms.int_app_id='"+act.getLoginUserId()+"'                            "+     
						" ORDER BY z.dt, z.shop_nm ";	
			
			
			
			//---------------without breakage----------------
			                                                                                                                            
		/*reportQuery = 	" SELECT z.brand_id, z.pckg_id, z.fl2_2bid, z.dt, z.gatepass, br.brand_name, pk.package_name,                "+
						" case when z.shop_nm='' then 'HBR/BRC' else z.shop_nm end as shop_nm,   "+
						" z.opening, z.reciving, z.dispatch,                                                                                   "+
						" CONCAT(ms.vch_firm_name,' - ',ms.vch_licence_no)as firm_name, dd.description as district                             "+
						" FROM                                                                                                                 "+
						" (SELECT y.brand_id, y.pckg_id, y.fl2_2bid, y.dt, y.gatepass, y.shop_nm,                                              "+
						" y.opening, sum(y.reciving)reciving, sum(y.dispatch)dispatch                                                          "+
						" FROM                                                                                                                 "+
						" (SELECT x.brand_id, x.pckg_id, x.fl2_2bid,                                                                           "+
						" (to_date('"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"','YYYY-MM-DD')  - INTERVAL '1' DAY) as dt,         "+
						" 'OPENING' as gatepass, '------' as shop_nm,                                                                          "+
						" SUM(x.recieving_opn-x.dispatch_opn) as opening, 0 as reciving, 0 as dispatch                                         "+
						" FROM                                                                                                                 "+
						" (SELECT a.brand_id as brand_id,  a.pckg_id as pckg_id, a.fl2_2bid as fl2_2bid,                                       "+
						" SUM(COALESCE(a.total_recv_bottels,0)) as recieving_opn, 0 as dispatch_opn                                            "+
						" FROM fl2d.fl2_2b_receiving_stock_trxn_19_20 a                                                                        "+
						" WHERE a.created_date < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                     "+
						" AND a.fl2_2btype='"+act.getRadioType()+"'  AND a.fl2_2bid='"+act.getLoginUserId()+"'                                 "+
						" AND a.pckg_id='"+act.getBrandId()+"'        																		   "+
						" GROUP BY a.brand_id, a.pckg_id, a.fl2_2bid                                                                           "+
						" UNION                                                                                                                "+
						" SELECT b.int_brand_id as brand_id, b.int_pckg_id as pckg_id, b.int_fl2_fl2b_id::text  as fl2_2bid,                   "+
						" 0 as recieving_opn, SUM(COALESCE(b.dispatch_bottle,0)) as dispatch_opn                                               "+
						" FROM fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a                                                                             "+
						" WHERE b.dt < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'   and a.vch_gatepass_no=b.vch_gatepass_no                                               "+
						" AND b.int_fl2_fl2b_id='"+act.getLoginUserId()+"' AND b.int_pckg_id='"+act.getBrandId()+"'                                    "+
						" GROUP BY b.int_brand_id, b.int_pckg_id, b.int_fl2_fl2b_id)x                                                          "+
						" GROUP BY x.brand_id, x.pckg_id, x.fl2_2bid                                                                           "+
						"                                                                                                                      "+
						" UNION ALL                                                                                                            "+
						"                                                                                                                      "+
						" SELECT a.brand_id as brand_id,  a.pckg_id as pckg_id, a.fl2_2bid as fl2_2bid,                                        "+
						" a.created_date as dt, a.gatepass_no as gatepass, '------' as shop_nm, 0 as opening,                                  "+
						" SUM(COALESCE(a.total_recv_bottels,0)) as reciving, 0 as dispatch                                                     "+
						" FROM fl2d.fl2_2b_receiving_stock_trxn_19_20 a                                                                        "+
						" WHERE a.created_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                               "+
						" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' AND a.fl2_2btype='"+act.getRadioType()+"'                    "+
						" AND a.fl2_2bid='"+act.getLoginUserId()+"' AND a.pckg_id='"+act.getBrandId()+"'                                               "+
						" GROUP BY a.brand_id, a.pckg_id, a.fl2_2bid, a.created_date, a.gatepass_no                                            "+
						" UNION                                                                                                                "+
						" SELECT b.int_brand_id as brand_id, b.int_pckg_id as pckg_id, b.int_fl2_fl2b_id::text as fl2_2bid,                    "+
						" b.dt as dt, b.vch_gatepass_no as gatepass, c.shop_nm, 0 as opening, 0 as reciving,                                   "+
						" SUM(COALESCE(b.dispatch_bottle,0)) as dispatch                                                                       "+
						" FROM fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b, fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 c                       "+
						" WHERE b.vch_gatepass_no=c.vch_gatepass_no AND b.dt=c.dt_date AND b.int_fl2_fl2b_id=c.int_fl2_fl2b_id                 "+
						" AND b.dt BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                           "+
						" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' AND c.vch_from='"+act.getRadioType()+"'                      "+
						" AND b.int_fl2_fl2b_id='"+act.getLoginUserId()+"' AND b.int_pckg_id='"+act.getBrandId()+"'                                    "+
						" GROUP BY b.int_brand_id, b.int_pckg_id, b.int_fl2_fl2b_id, b.dt, b.vch_gatepass_no, c.shop_nm)y                      "+
						" GROUP BY y.brand_id, y.pckg_id, y.fl2_2bid, y.dt, y.gatepass, y.shop_nm, y.opening)z,                                "+
						" distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pk,                                       "+
						" licence.fl2_2b_2d_19_20 ms, public.district dd                                                                       "+
						" WHERE br.brand_id=z.brand_id and br.brand_id=pk.brand_id_fk and z.pckg_id=pk.package_id                              "+
						" AND z.fl2_2bid=ms.int_app_id::text AND dd.districtid=ms.core_district_id                                             "+
						" AND ms.vch_license_type='"+act.getRadioType()+"' AND ms.int_app_id='"+act.getLoginUserId()+"'                                  "+
						" ORDER BY z.dt, z.shop_nm ";   */                                                                           
			

			

			pst = con.prepareStatement(reportQuery);
			System.out.println("reportQuery-----FL2--gbgd-------" + reportQuery);

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

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "WholesellerStockRegister.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print, relativePathpdf+ File.separator + "WholesellerStockRegisterBrandWise"+ "-" +n+ act.getLoginUserId() + ".pdf");
				act.setPdfName("WholesellerStockRegisterBrandWise"+ "-" +n+ act.getLoginUserId() + ".pdf");
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
	
	
	//--------------------------------Excel
	

	// ----------------------------------------------------

	public void printExcel(WholesaleStockRegisterBrandWiseAction act) {

		Connection con = null;

		double box_total_cl = 0.0;
		double balance = 0.0;
		double tot_recv = 0.0;
		double tot_dis = 0.0;
		double tot_balance = 0.0;
		
		
		

		String type = "";
		String reportQuery = "";

		if (act.getRadioType() != null) {

			reportQuery =	" SELECT z.brand_id, z.pckg_id, z.fl2_2bid, z.dt, z.gatepass, br.brand_name, pk.package_name,                              "+
					" CASE WHEN z.shop_nm='' then 'HBR/BRC' else z.shop_nm end as shop_nm,                                                     "+
					" z.opening, z.reciving, z.dispatch, z.breakage,                                                                           "+       
					" CONCAT(ms.vch_firm_name,' - ',ms.vch_licence_no)as firm_name, dd.description as district                                 "+
					" FROM                                                                                                                     "+
					" (SELECT y.brand_id, y.pckg_id, y.fl2_2bid, y.dt, y.gatepass, y.shop_nm,                                                  "+
					" y.opening, sum(y.reciving)reciving, sum(y.dispatch)dispatch, sum(y.breakage)breakage                                     "+                    
					" FROM                                                                                                                     "+
					" (SELECT x.brand_id, x.pckg_id, x.fl2_2bid,                                                                               "+
					" (to_date('"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"','YYYY-MM-DD')  - INTERVAL '1' DAY) as dt,             "+
					" 'OPENING' as gatepass, '------' as shop_nm,                                                                              "+
					" SUM(x.recieving_opn-x.dispatch_opn-x.breakage_opn) as opening, 0 as reciving, 0 as dispatch, 0 as breakage               "+                        
					" FROM                                                                                                                     "+
					" (SELECT a.brand_id as brand_id,  a.pckg_id as pckg_id, a.fl2_2bid as fl2_2bid,                                           "+
					" SUM(COALESCE(a.total_recv_bottels,0)) as recieving_opn, 0 as dispatch_opn, 0 as breakage_opn                             "+              
					" FROM fl2d.fl2_2b_receiving_stock_trxn_20_21 a                                                                            "+
					" WHERE a.created_date < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                         "+
					" AND a.fl2_2btype='"+act.getRadioType()+"' AND a.fl2_2bid='"+act.getLoginUserId()+"'                                      "+
					" AND a.pckg_id='"+act.getBrandId()+"'        																		       "+
					" GROUP BY a.brand_id, a.pckg_id, a.fl2_2bid                                                                               "+
					" UNION                                                                                                                    "+
					" SELECT b.int_brand_id as brand_id, b.int_pckg_id as pckg_id, b.int_fl2_fl2b_id::text  as fl2_2bid,                       "+
					" 0 as recieving_opn, SUM(COALESCE(b.dispatch_bottle,0)) as dispatch_opn, 0 as breakage_opn                                "+               
					" FROM fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a                            "+                                                
					" WHERE b.dt < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'   and a.vch_gatepass_no=b.vch_gatepass_no         "+                                     
					" AND a.vch_from='"+act.getRadioType()+"' AND b.int_fl2_fl2b_id='"+act.getLoginUserId()+"'                                 "+
					" AND b.int_pckg_id='"+act.getBrandId()+"'                                                                                 "+
					" GROUP BY b.int_brand_id, b.int_pckg_id, b.int_fl2_fl2b_id                                                                "+
					" UNION                                                                                                                    "+
					" SELECT b.brand_id as brand_id, b.pckg_id as pckg_id, b.fl2_2b_id::text  as fl2_2bid,                                     "+
					" 0 as recieving_opn, 0 as dispatch_opn, SUM(b.breakage) as breakage_opn                                                   "+
					" FROM fl2d.wholesale_godown_stock_breakage b                                                                              "+
					" WHERE b.cr_date < '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' AND b.fl2_2b_id='"+act.getLoginUserId()+"'   "+
					" AND b.pckg_id='"+act.getBrandId()+"' AND b.fl2_2b_type='"+act.getRadioType()+"'                                          "+
					" GROUP BY b.brand_id, b.pckg_id, b.fl2_2b_id)x                                                                            "+
					" GROUP BY x.brand_id, x.pckg_id, x.fl2_2bid                                                                               "+
					"                                                                                                                          "+
					" UNION ALL                                                                                                                "+
					"                                                                                                                          "+
					" SELECT a.brand_id as brand_id,  a.pckg_id as pckg_id, a.fl2_2bid as fl2_2bid,                                            "+
					" a.created_date as dt, a.gatepass_no as gatepass, '------' as shop_nm, 0 as opening,                                      "+
					" SUM(COALESCE(a.total_recv_bottels,0)) as reciving, 0 as dispatch, 0 as breakage                                          "+          
					" FROM fl2d.fl2_2b_receiving_stock_trxn_20_21 a                                                                            "+
					" WHERE a.created_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                   "+
					" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' AND a.fl2_2btype='"+act.getRadioType()+"'                    "+
					" AND a.fl2_2bid='"+act.getLoginUserId()+"' AND a.pckg_id='"+act.getBrandId()+"'                                           "+   
					" GROUP BY a.brand_id, a.pckg_id, a.fl2_2bid, a.created_date, a.gatepass_no                                                "+
					" UNION                                                                                                                    "+
					" SELECT b.int_brand_id as brand_id, b.int_pckg_id as pckg_id, b.int_fl2_fl2b_id::text as fl2_2bid,                        "+
					" b.dt as dt, b.vch_gatepass_no as gatepass, c.shop_nm, 0 as opening, 0 as reciving,                                       "+
					" SUM(COALESCE(b.dispatch_bottle,0)) as dispatch, 0 as breakage                                                            "+          
					" FROM fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b, fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 c                           "+
					" WHERE b.vch_gatepass_no=c.vch_gatepass_no AND b.dt=c.dt_date AND b.int_fl2_fl2b_id=c.int_fl2_fl2b_id                     "+
					" AND b.dt BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                               "+
					" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' AND c.vch_from='"+act.getRadioType()+"'                      "+                                           
					" AND b.int_fl2_fl2b_id='"+act.getLoginUserId()+"' AND b.int_pckg_id='"+act.getBrandId()+"'                                "+   
					" GROUP BY b.int_brand_id, b.int_pckg_id, b.int_fl2_fl2b_id, b.dt, b.vch_gatepass_no, c.shop_nm                            "+
					" UNION                                                                                                                    "+
					" SELECT b.brand_id as brand_id, b.pckg_id as pckg_id, b.fl2_2b_id::text as fl2_2bid,                                      "+
					" b.cr_date as dt, 'BREAKAGE STOCK' as gatepass, '------' as shop_nm, 0 as opening, 0 as reciving,                         "+         
					" 0 as dispatch, SUM(b.breakage) as breakage                                                                               "+
					" FROM fl2d.wholesale_godown_stock_breakage b                                                                              "+
					" WHERE b.cr_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                                        "+
					" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' AND b.fl2_2b_type='"+act.getRadioType()+"'                   "+                                              
					" AND b.fl2_2b_id='"+act.getLoginUserId()+"' AND b.pckg_id='"+act.getBrandId()+"'                                          "+
					" GROUP BY b.brand_id, b.pckg_id, b.fl2_2b_id, b.cr_date   UNION                                                                "+
					" SELECT  b.brand_id as brand_id, b.package_id as pckg_id, b.unit_id::text as fl2_2bid,b.finalize_dt as dt,                 "+
					"   'ROLLOVER STOCK' as gatepass, '------' as shop_nm, 0 as opening,                                                        "+
					"   b.rollover_bottles as reciving,   0 as dispatch, 0 as breakage                                                          "+
					"	FROM fl2d.rollover_fl_stock b    WHERE b.finalize_dt BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'    "+
					"   AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' AND b.finaliz_flg='T'  AND b.unit_id='"+act.getLoginUserId()+"' AND b.package_id='"+act.getBrandId()+"'  "+   
					"   GROUP BY b.brand_id, b.package_id, b.unit_id, b.finalize_dt , b.rollover_bottles                                      "+
					" )y                                                                                                                       "+
					" GROUP BY y.brand_id, y.pckg_id, y.fl2_2bid, y.dt, y.gatepass, y.shop_nm, y.opening)z,                                    "+
					" distillery.brand_registration_20_21 br, distillery.packaging_details_20_21 pk,                                           "+
					" licence.fl2_2b_2d_20_21 ms, public.district dd                                                                           "+
					" WHERE br.brand_id=z.brand_id and br.brand_id=pk.brand_id_fk and z.pckg_id=pk.package_id                                  "+
					" AND z.fl2_2bid=ms.int_app_id::text AND dd.districtid=ms.core_district_id                                                 "+
					" AND ms.vch_license_type='"+act.getRadioType()+"' AND ms.int_app_id='"+act.getLoginUserId()+"'                            "+     
					" ORDER BY z.dt, z.shop_nm ";	
		
							}
		else {
			
		}

		
		System.out.println("excel query===  " + reportQuery);

		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long start = 0;
		long end = 0;
		boolean flag = false;
		long k = 0;

		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(reportQuery);

			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook
					.createSheet("WholesellerStockRegisterBrandWise");

			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 3000);
			worksheet.setColumnWidth(2, 12000);
			worksheet.setColumnWidth(3, 6000);
			worksheet.setColumnWidth(4, 6000);
			worksheet.setColumnWidth(5, 6000);
			worksheet.setColumnWidth(6, 6000);
			worksheet.setColumnWidth(7, 6000);
			worksheet.setColumnWidth(8, 6000);
			worksheet.setColumnWidth(9, 6000);
			worksheet.setColumnWidth(10, 6000);
		//	worksheet.setColumnWidth(11, 6000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);

			cellhead0.setCellValue("WholesellerStockRegisterBrandWise For " + act.getRadioType()
					+ " From (Date"
					+ Utility.convertUtilDateToSQLDate(act.getFromDate())
					+ " To "
					+ Utility.convertUtilDateToSQLDate(act.getToDate()) + ")");

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
			cellhead1.setCellValue("Sr. No");
			cellhead1.setCellStyle(cellStyle);

			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("District");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Wholeseller Name ");
			cellhead3.setCellStyle(cellStyle);
           
			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Brand Name ");
			cellhead4.setCellStyle(cellStyle);
			
			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("GatePass NO");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Date");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Shop Name");
			cellhead7.setCellStyle(cellStyle);
			
			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("Recive");
			cellhead8.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("Dispatch");
			cellhead9.setCellStyle(cellStyle);
			
			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("Balance");
			cellhead10.setCellStyle(cellStyle);
			/*
			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("BEER DUTY");
			cellhead11.setCellStyle(cellStyle);
			
			XSSFCell cellhead12 = rowhead.createCell((int) 11);
			cellhead12.setCellValue("TOTAL DUTY");
			cellhead12.setCellStyle(cellStyle);
*/
			// k = k + 2;
			int i = 0;

			while (rs.next()) {
		SimpleDateFormat showDate = new SimpleDateFormat("dd-MM-yyyy");
		String displayDate = showDate.format(Utility.convertUtilDateToSQLDate(rs.getDate("dt")));

			 balance = rs.getDouble("reciving")-rs.getDouble("dispatch");
			 
			 tot_recv = tot_recv + rs.getDouble("reciving");
			 tot_dis =  tot_dis + rs.getDouble("dispatch");
			 tot_balance = tot_recv-tot_dis;
				//case_total = case_total + rs.getDouble("no_of_cases");
				System.out.println(box_total_cl);
				//fees_total = fees_total + rs.getInt("import_fee");

				i++;
				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				XSSFCell cellA1 = row1.createCell((int) 0); //
				cellA1.setCellValue(k - 1); //

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("district"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("firm_name"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("brand_name"));   

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("gatepass"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(displayDate);
				
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("shop_nm"));
				
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("reciving"));  //z.reciving, z.dispatch,
				
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getDouble("dispatch"));
				
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(balance);
			/*	 
				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getString("duty_beer"));
				
				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(total_duty);*/
				// System.out.println("------------ "+Math.round(cases_of_last_year_sale));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "WholesellerStockRegisterBrandWise.xls");
			//String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		//	act.setPdfName("WholesellerStockRegisterBrandWise"+ "-" +n+ act.getLoginUserId() + ".pdf");
			act.setExlname(n + "WholesellerStockRegisterBrandWise.xls");
			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA2 = row1.createCell((int) 0);
			cellA2.setCellValue("");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 1);
			cellA3.setCellValue("TOTAL:-");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 2);
		//	cellA4.setCellValue();
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 3);
		//	cellA5.setCellValue();
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 4);
		//	cellA6.setCellValue();
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 5);
		//	cellA7.setCellValue();
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 6);
		//	cellA8.setCellValue();
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 7);
			cellA9.setCellValue(tot_recv);
			cellA9.setCellStyle(cellStyle);
		
			XSSFCell cellA10 = row1.createCell((int) 8);
			cellA10.setCellValue(tot_dis);
			cellA10.setCellStyle(cellStyle);
			
			XSSFCell cellA11 = row1.createCell((int) 9);
			cellA11.setCellValue(tot_balance);
			cellA11.setCellStyle(cellStyle);
			
/*			XSSFCell cellA12 = row1.createCell((int) 10);
			cellA12.setCellValue(duty_total_beer);
			cellA12.setCellStyle(cellStyle);
			
			XSSFCell cellA13 = row1.createCell((int) 11);
			cellA13.setCellValue(sub_total_duty);
			cellA13.setCellStyle(cellStyle);*/


			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			act.setExcelFlag(true);

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

	}

}
