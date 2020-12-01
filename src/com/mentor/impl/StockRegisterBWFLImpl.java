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

import com.mentor.action.StockRegisterBWFLAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;
public class StockRegisterBWFLImpl {
	

	
	
	public void getDetails(StockRegisterBWFLAction ac) {
		//String imfl = "";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query =  " SELECT mobile_number, vch_firm_name, vch_firm_add, int_id, vch_license_type,vch_firm_district_id "
				+ " FROM bwfl.registration_of_bwfl_lic_holder_20_21 "
				+ " WHERE  mobile_number='"
				+ ResourceUtil.getUserNameAllReq().trim() + "' AND vch_approval='V'";
		
		 // System.out.println("=====query==Login===="+query);
		
		  try {
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				ac.setLicence_type(rs.getString("vch_license_type"));
				ac.setInt_id(rs.getInt("int_id"));
				//ac.setLicence_no(rs.getString("vch_licence_no"));
				ac.setApplicant_name(rs.getString("vch_firm_name"));
				//ac.setDistrict(rs.getString("description"));

				ac.setDistrictId(rs.getInt("vch_firm_district_id"));

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
	
	
	
	public ArrayList getBrandList(StockRegisterBWFLAction act) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue("");
		list.add(item);

		try {
			String query = /*" select distinct b.brand_id,brand_name,package_name,a.int_pack_id,  brand_name || '(' || package_name || ')'  " +
					       " as brandpack   from fl2d.mst_stock_receive_20_21 a , distillery.brand_registration b,distillery.packaging_details c " +
					       " where a.int_brand_id=b.brand_id and a.int_pack_id=c.package_id and a.int_fl2d_id='"+act.getInt_id()+"' order by brandpack ";
			*/
					" select distinct a.int_brand_id || '/' || a.int_pack_id as id,brand_name,package_name,a.int_pack_id,  brand_name || '(' || package_name || ')'              "+
					" as brandpack   from bwfl_license.mst_receipt_20_21 a , distillery.brand_registration_20_21 b,distillery.packaging_details_20_21 c  "+
					" where a.int_brand_id=b.brand_id and c.brand_id_fk=b.brand_id and a.int_pack_id=c.package_id and a.int_bwfl_id='"+act.getInt_id()+"'           "+
					" order by brandpack";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			 
			//System.out.println("=====query==BrandList===="+query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("id"));
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

	public void printReport(StockRegisterBWFLAction act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		  String arr[] = act.getPackId().split("/");
          String brand_id  = arr[0];
          String package_id  = arr[1];

		
		try {
			con = ConnectionToDataBase.getConnection();
			
			reportQuery =
					
					"select x.dispatch_bottle ,x.received,x.dt,x.des,br.brand_name || '(' || pk.package_name || ')' as brand_name from                                                                                   "+
                    " (select int_bwfl_id ,int_pckg_id,sum(dispatch_bottle) as dispatch_bottle,0 as received,int_brand_id,dt,vch_gatepass_no as des                                    "+
                    " from bwfl_license.fl2_stock_trxn_bwfl_20_21 where int_bwfl_id='"+act.getInt_id()+"' and int_brand_id='"+brand_id+"' and int_pckg_id="+package_id+" and dt                                                    "+
                    " between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' group by int_brand_id,dt,int_bwfl_id,vch_gatepass_no,int_pckg_id                                                          "+
                    " union                                                                                                                                               "+
                    " select int_bwfl_id,int_pack_id,0 as dispatch_bottle,sum(int_recieved_bottles) as int_recieved_bottles,int_brand_id,receiving_date,gatepass                      "+
                    " from bwfl_license.mst_receipt_20_21 where int_bwfl_id='"+act.getInt_id()+"' and int_brand_id='"+brand_id+"' and int_pack_id="+package_id+" and    "+
                    " receiving_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' " +
                    " group by int_brand_id,receiving_date,int_bwfl_id,gatepass,int_pack_id)x, distillery.brand_registration_20_21 br, "+
                    " distillery.packaging_details_20_21 pk  "+
                    " where x.int_brand_id=br.brand_id  and pk.brand_id_fk=br.brand_id and x.int_pckg_id=pk.package_id order by dt";

			
			System.out.println("queryy==="+reportQuery);
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

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "Stock_register_for_bwfl.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "Stock_register_for_bwfl" + "-" + n + ".pdf");
				act.setPdfName("Stock_register_for_bwfl" + "-" + n + ".pdf");
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
	
	
	public void getopening(StockRegisterBWFLAction ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		 String arr[] = ac.getPackId().split("/");
         String brand_id  = arr[0];
         String package_id  = arr[1];
         
		String query =  
				
				 " SELECT 	COALESCE(sum(Y.recv_bottels - Y.dispatch_bottle),0) as opening FROM(select distinct x.date ,          "+
                 " d.brand_name,c.package_name,x.brand_id,                                                                        "+
                 " x.gatepass_no,SUM(x.recv_bottels) as recv_bottels,SUM(x.dispatch_bottle) as dispatch_bottle                    "+
                 " from(select distinct a.receiving_date as date ,a.int_brand_id as brand_id, a.int_pack_id,                      "+
                 "	a.int_recieved_bottles as recv_bottels ,a.gatepass as gatepass_no,0 as dispatch_bottle	                      "+
                 "	from bwfl_license.mst_receipt_20_21 a,bwfl_license.fl2_stock_trxn_bwfl_20_21 b	                              "+
                 "	where a.int_bwfl_id='"+ac.getInt_id()+"' and a.cr_date between '2020-04-01' and '"+Utility.convertUtilDateToSQLDate(ac.getFromDate())+"' 	                                                      "+
                 "	and a.int_pack_id=b.int_pckg_id and a.int_brand_id=b.int_brand_id and a.int_bwfl_id::int=b.int_bwfl_id        "+
                 "	union		                                                                                                  "+
                 "	select distinct b.dt as date ,b.int_brand_id as brand_id,a.int_pack_id, 0 as recv_bottels ,b.vch_gatepass_no  "+
                 "    as gatepass_no , b.dispatch_bottle as dispatch_bottle                                                       "+
                 "	from bwfl_license.mst_receipt_20_21 a, bwfl_license.fl2_stock_trxn_bwfl_20_21 b	                              "+
                 "	where b.int_bwfl_id = '"+ac.getInt_id()+"' and b.dt between '2020-04-01' and '"+Utility.convertUtilDateToSQLDate(ac.getFromDate())+"' 	                                                          "+
                 "	and a.int_pack_id=b.int_pckg_id and a.int_brand_id=b.int_brand_id and a.int_bwfl_id::int=b.int_bwfl_id)x ,    "+
                 "	distillery.packaging_details_20_21 c, distillery.brand_registration_20_21 d	                                              "+
                 "	where  x.brand_id=d.brand_id  and c.brand_id_fk=d.brand_id and c.package_id=x.int_pack_id  and x.brand_id='"+brand_id+"' and x.int_pack_id='"+package_id+"'	                                  "+
                 "  group by x.date,d.brand_name,c.package_name,x.brand_id,x.gatepass_no) y ";
		
		try {
			//System.out.println("==================Get=====Opening===>"+query);
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
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
