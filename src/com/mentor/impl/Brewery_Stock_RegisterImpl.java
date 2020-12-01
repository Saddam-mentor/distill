package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.mentor.action.Brewery_Stock_RegisterAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class Brewery_Stock_RegisterImpl {
	
	public String getDetails(Brewery_Stock_RegisterAction ac)
	{
		int id = 0;
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		String s = "";
		try {
			con = ConnectionToDataBase.getConnection();

			String queryList = " SELECT vch_app_id_f, brewery_nm, vch_reg_mobile, vch_reg_address," +
					" (select description from public.district where districtid::text=vch_dist_id) as district_name" +
					"  FROM  public.bre_mst_b1_lic WHERE vch_reg_mobile='"+ResourceUtil.getUserNameAllReq().trim()+"'  ";
			
			System.out.println("license details====="+queryList);

			pstmt = con.prepareStatement(queryList);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ac.setApplicant_name(rs.getString("brewery_nm"));
				ac.setInt_id(rs.getInt("vch_app_id_f"));
				ac.setDistrict(rs.getString("district_name"));
				//ac.setVch_licence_no(rs.getString("vch_licence_no"));
				//ac.setVch_from(rs.getString("vch_firm_name"));

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

	

	public ArrayList getBrandList(Brewery_Stock_RegisterAction act) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue(0);
		list.add(item);

		try {
			String query = " select distinct brand_name,b.brand_id  " +
					" from bwfl.fl2_stock_trxn_19_20 a , distillery.brand_registration_19_20 b " +
					" where a.int_brand_id=b.brand_id  and  b.brewery_id='"+act.getInt_id()+"' order by brand_name ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			 

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("brand_id"));
				item.setLabel(rs.getString("brand_name"));
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

	public void printReport(Brewery_Stock_RegisterAction act)
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
			
			reportQuery = "select x.brand_name || ' ( ' || x.package_name || ' )' as brandpack ,x.date ," +
					"(case when type='B' then 'Boxing id-'|| x.boxing_id else 'Gate pass no-'|| x.boxing_id End) as boxing_id ," +
					" x.distillery_id, x.brand_name,   x.brand_id, x.package_name, x.packaging_id,  sum(x.recv_box) as recv_box ," +
					" sum(x.dispatch_box) as dispatch_box , x.type, dd.vch_undertaking_name , dc.description from " +
					" (  SELECT a.date ,a.seq:: text as boxing_id , a.distillery_id, br.brand_name,  a.brand_id, " +
					"pa.package_name, a.packaging_id,  a.bottling_no_of_box  as recv_box, 0 as dispatch_box, 'B' as type" +
					"  from bwfl.daily_bottling_stock_19_20 a , distillery.brand_registration_19_20 br, " +
					"distillery.packaging_details_19_20 pa where  a.brand_id=br.brand_id  and br.brand_id=pa.brand_id_fk" +
					" and a.packaging_id=pa.package_id and a.distillery_id='"+act.getInt_id()+"' and br.brand_id='"+act.getPackId()+"' and  " +
					" a.date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'" +
					"union " +
					"select a.dt_date, a.vch_gatepass_no, b.brewery_id,br.brand_name,  b.int_brand_id," +
					" pa.package_name, b.int_pckg_id, 0 as recv_box, b.dispatch_box as dispatch_box  ," +
					"'G' as type from bwfl.gatepass_to_districtwholesale_19_20 a , " +
					"bwfl.fl2_stock_trxn_19_20 b , distillery.brand_registration_19_20 br," +
					" distillery.packaging_details_19_20 pa where a.vch_gatepass_no=b.vch_gatepass_no " +
					"and b.int_brand_id=br.brand_id  and br.brand_id=pa.brand_id_fk and" +
					" b.int_pckg_id=pa.package_id and br.brand_id='"+act.getPackId()+"' and a.brewery_id='"+act.getInt_id()+"' " +
					" and a.dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' )x, public.dis_mst_pd1_pd2_lic dd ," +
					" public.district dc  where x.distillery_id=dd.int_app_id_f and " +
					"dc.districtid::text=dd.vch_unit_dist and dd.vch_unit_state='1' group by x.date," +
					" x.boxing_id , x.distillery_id, x.brand_name, x.brand_id, x.package_name, x.packaging_id ," +
					"  x.type ,dd.vch_undertaking_name ,  dc.description order by brandpack,boxing_id";

			System.out.println("reportQuery-faizal--- "+reportQuery);
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
				parameters.put("opening",act.getNewb());
				parameters.put("exDate",Utility.convertUtilDateToSQLDate(act.getLastDate()));
				parameters.put("user",ResourceUtil.getUserNameAllReq().trim());

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "Distillery_Stock_Register.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "Brewery_Stock_Register" + "-" + n + ".pdf");
				act.setPdfName("Brewery_Stock_Register" + "-" + n + ".pdf");
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
	
	
	public void getopening(Brewery_Stock_RegisterAction ac) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(ac.getFromDate());
	    calendar.add(Calendar.DATE, -1);
	    Date yesterday = calendar.getTime();
	    System.out.println("yesterday===if not equal fix date=="+yesterday);
	    
	    
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query =" select (sum(x.recv_box)-sum(x.dispatch_box)) as opning from" +
				" (SELECT sum(a.bottling_no_of_box) as recv_box, 0 as dispatch_box " +
				"from bwfl.daily_bottling_stock_19_20 a , distillery.brand_registration_19_20 br," +
				" distillery.packaging_details_19_20 pa where a.brand_id=br.brand_id and br.brand_id='"+ac.getPackId()+"'" +
				" and a.packaging_id=pa.package_id and a.distillery_id='"+ac.getInt_id()+"' " +
				"and a.date < '"+Utility.convertUtilDateToSQLDate(ac.getFromDate())+"' group by  a.distillery_id, a.brand_id, " +
				"a.packaging_id , br.brand_name , pa.package_name " +
				"union  " +
				"select 0 as recv_box, sum(b.dispatch_box) as dispatch_box from " +
				"bwfl.gatepass_to_districtwholesale_19_20 a , bwfl.fl2_stock_trxn_19_20  b ,  " +
				"distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pa" +
				" where  a.vch_gatepass_no=b.vch_gatepass_no and b.int_brand_id=br.brand_id and br.brand_id='"+ac.getPackId()+"'" +
				" and b.int_pckg_id=pa.package_id and a.brewery_id='"+ac.getInt_id()+"' " +
				"and a.dt_date < '"+Utility.convertUtilDateToSQLDate(ac.getFromDate())+"' " +
				"group by b.brewery_id, b.int_brand_id, b.int_pckg_id ,br.brand_name, pa.package_name )x ";
				
				
				
				/*" SELECT 	COALESCE(sum(Y.recv_bottels - Y.dispatch_bottle),0) as opening FROM(select distinct x.date ,d.brand_name,c.package_name, x.pckg_id,x.brand_id," +
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
				"group by x.date,d.brand_name,c.package_name,x.pckg_id,x.brand_id,x.gatepass_no ) y ";*/
		
		try {
			System.out.println("check query===>"+query);
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				ac.setBalance(rs.getInt("opning"));
				ac.setNewb(rs.getInt("opning"));
				

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
