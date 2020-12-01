package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.mentor.action.BWFL_StockRegisterAction; 
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class BWFL_StockRegisterImpl {
	 public void printReport(BWFL_StockRegisterAction act) {

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
	 

			try {
				con = ConnectionToDataBase.getConnection(); 
			 
				Date FromDate=Utility.convertUtilDateToSQLDate(act.getFromDate());
				Date toDate=Utility.convertUtilDateToSQLDate(act.getToDate());
				//Date dt = new Date();
			     		Calendar c = Calendar.getInstance(); 
			c.setTime(act.getFromDate()); 
			c.add(Calendar.DATE, -1);
			//dt = c.getTime();
			Date BeforeFromDate=Utility.convertUtilDateToSQLDate(c.getTime());
			//System.out.println("BeforeFromDate="+BeforeFromDate);
				 
				// reportQuery = "select * from fl2d.getstock('"+FromDate+"','" + toDate+ "','"+act.getRadio()+"', "+filter+")";
			 
reportQuery= " select x.int_bwfl_id,int_brand_id,int_pckg_id, dt,desca,quantity,dispatch_bottle,receipt,brand_name," +
		" vch_firm_name,vch_license_number from (select int_bwfl_id,int_brand_id,int_pckg_id,dt,concat" +
		" ('Dispatch- ',vch_gatepass_no) as desca,sum(dispatch_bottle) as dispatch_bottle,0 as receipt " +
		" from bwfl_license.fl2_stock_trxn_bwfl_20_21" +
		"  where dt  between  '2020-04-01'  and '"+toDate+"'and int_bwfl_id= '"+this.getId()+"' "+
		" group by int_bwfl_id,int_brand_id,int_pckg_id,dt,vch_gatepass_no" +
		" union " +
		" select int_bwfl_id,int_brand_id,int_pack_id,receiving_date,concat('Receipt - ',gatepass)" +
		" as desc,0 as dispatch_bottle, sum(int_recieved_bottles) as receipt from bwfl_license.mst_receipt_20_21 " +
		" where plan_dt  between  '2020-04-01' and '"+toDate+"'  and int_bwfl_id= '"+this.getId()+"' "+
		" group by int_bwfl_id,int_brand_id,int_pack_id,receiving_date,gatepass)x,bwfl.registration_of_bwfl_lic_holder_20_21 " +
		" cc,distillery.brand_registration_20_21 aa,distillery.packaging_details_20_21 bb where aa.brand_id=bb.brand_id_fk " +
		" and aa.brand_id=x.int_brand_id and x.int_pckg_id=bb.package_id and x.int_bwfl_id=cc.int_id order by brand_name,dt";
  





 /*" select x.int_bwfl_id,x.int_brand_id,aa.brand_name,y.vch_firm_district_id,bb.quantity,x.gatepass,x.dt,x.dispatch_box,x.int_planned_boxes,coalesce(x.opening,0)opening, " +
 " y.vch_firm_name, (select description from district where districtid::text=y.vch_firm_district_id)description " +
 " from bwfl.registration_of_bwfl_lic_holder_20_21 y, " +
 " (select p.int_bwfl_id,p.int_brand_id,p.int_pack_id,'Opening' as gatepass, '"+FromDate+"'  as dt, 0 as dispatch_box,0 as int_planned_boxes,p.opening from( " +
 " select  xx.int_bwfl_id,xx.int_brand_id,xx.int_pack_id, sum(xx.int_planned_boxes)-sum(xx.dispatch_box) as opening " +
 " from ( " +
 " select a.int_bwfl_id,a.int_brand_id,a.int_pack_id,0 as dispatch_box,coalesce(a.int_planned_boxes,0)int_planned_boxes " +
 " from bwfl_license.mst_receipt_20_21 a where a.plan_dt between '2019-04-01' and '"+BeforeFromDate+"'  and a.int_bwfl_id='"+this.getId()+"' " +
 " union all " +
 " select a.int_bwfl_id,a.int_brand_id,a.int_pckg_id,coalesce(a.dispatch_box,0)dispatch_box, 0 as int_planned_boxes " +
 " from bwfl_license.fl2_stock_trxn_bwfl_20_21 a where a.dt  between '2019-04-01' and '"+BeforeFromDate+"'  and a.int_bwfl_id='"+this.getId()+"' " +
 " )xx group by xx.int_bwfl_id,xx.int_brand_id,xx.int_pack_id )p " +
 " union all " +
 " select a.int_bwfl_id,a.int_brand_id,a.int_pack_id,a.gatepass,a.plan_dt as dt,0 as dispatch_box, " +
 " coalesce(a.int_planned_boxes,0)int_planned_boxes, 0 as opening " +
 " from bwfl_license.mst_receipt_20_21 a where a.plan_dt between  '"+FromDate+"' and '"+toDate+"'  and a.int_bwfl_id='"+this.getId()+"' " +
 " union all " +
 " select a.int_bwfl_id,a.int_brand_id,a.int_pckg_id , a.vch_gatepass_no as gatepass,a.dt,coalesce(a.dispatch_box,0)dispatch_box, 0 as int_planned_boxes, 0 as opening " +
 " from bwfl_license.fl2_stock_trxn_bwfl_20_21 a  " +
 " where a.dt between  '"+FromDate+"' and '"+toDate+"'  and a.int_bwfl_id='"+this.getId()+"'  " +
 " )x,distillery.brand_registration_19_20 aa,distillery.packaging_details_19_20 bb  where x.int_bwfl_id=y.int_id and aa.brand_id=x.int_brand_id and  " +
 " aa.brand_id=bb.brand_id_fk and x.int_pack_id=bb.package_id " +
 " order by description,y.vch_firm_name,aa.brand_name,bb.quantity,x.dt,x.opening desc,x.gatepass,x.int_planned_boxes,x.dispatch_box ";
		*/
			System.out.println("reportQuery="+reportQuery);                                                                                                                               
			 
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
					
						JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

					jasperReport = (JasperReport) JRLoader.loadObject(relativePath
							+ File.separator+ "BWFL_Stock_Register.jasper");

					JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager.exportReportToPdfFile(print,
							relativePathpdf + File.separator + "BWFL_Stock_Register" + "-" + n + ".pdf");
					act.setPdfname("BWFL_Stock_Register" + "-" + n+ ".pdf");
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
	 public int getId() {

			int id = 0;
			Connection con = null;
			PreparedStatement pstmt = null, ps2 = null;
			ResultSet rs = null, rs2 = null;
			String s = "";
			try {
				con = ConnectionToDataBase.getConnection();

				String selQr = " SELECT mobile_number, vch_firm_name, vch_firm_add, int_id, vch_license_type "
						+ " FROM bwfl.registration_of_bwfl_lic_holder_19_20 "
						+ " WHERE  mobile_number='"
						+ ResourceUtil.getUserNameAllReq().trim() + "' AND vch_approval='V' ";
System.out.println("selQr="+selQr);
				pstmt = con.prepareStatement(selQr);

				rs = pstmt.executeQuery();

				if (rs.next()) {
					id=rs.getInt("int_id");
			
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
			System.out.println("id="+id);
			return id;

		}
	}
