package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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


import com.mentor.action.brand_label_tracking_action;
import com.mentor.datatable.brand_label_tracking_dt;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class brand_label_tracking_impl {
	


	// ------------------display applications in datatable----------------------

	   public boolean displayRegUsersImpl(brand_label_tracking_action act) {

		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean flag=false;
		String selQr = null;
		String filter = "";
		ResultSet rs1,rs2,rs3=null;double brndfee = 0,lablfee=0;PreparedStatement pstmt1,pstmt2,pstmt3=null;
		try {
			con = ConnectionToDataBase.getConnection();

			
			
			selQr = " SELECT coalesce(a.existing_brand_20_21,'NO') as existing_brand_20_21, " +
					" coalesce(a.brandtype,'X')brandtype,a.ac_rvrt_comment,a.challan_flg," +
					" COALESCE(a.brndchallan_name, 'NA') as brndchallan_name ,COALESCE(a.labelchallan_name, 'NA') as labelchallan_name ,COALESCE(a.labelchallan_path,'/doc/ExciseUp/CHALLAN/pdf/') as labelchallan_path ,COALESCE(a.brndchallan_path ,'/doc/ExciseUp/CHALLAN/pdf/') as brndchallan_path," +
					" a.digital_sign_pdf_name,a.digital_sign_date , a.digital_sign_time , a.renewed,a.digital_sign_pdf_name,a.app_id, a.app_date, a.domain_id, a.domain_name, a.unit_type, a.total_fees, a.total_no_of_labels, "
					+ " a.ac_rvrt_date,a.app_status, a.lic_type, a.lic_cat, a.username, a.unit_id, a.unit_name,a.digital_sign_date , a.unit_address,              "
					+ " a.user1_name, a.user1_remark, a.user1_date, a.user1_time,a.vch_license_no , a.unit_district_id, a.user2_name,               "
					+ " a.user2_remark, a.user2_date, a.user2_time, COALESCE(objection_flag,'N')AS objection_flag,                                                 "
					+ " a.user3_name, a.user3_remark, a.user3_date, a.user3_time,                                                 "
					+ " a.user4_name, a.user4_remark, a.user4_date, a.user4_time,                                                 "
					+ " a.user5_name, a.user5_remark, a.user5_date, a.user5_time,                                                 "
					+ " a.vch_forwarded, a.vch_approved, b.description, "
					+ " a.dec_distribution_remark, a.dec_distribution_name, a.dec_distribution_date, a.dec_distribution_time,     "
					+ " CASE WHEN a.unit_type='D' and a.username not like 'CSD%' THEN 'Distillery'  "
					+ " WHEN a.unit_type='B' and a.username not like 'CSD%' THEN 'Brewery' "
					+ " WHEN a.unit_type='D' and a.username like 'CSD%' THEN 'Distillery CSD' " 
					+ " WHEN a.unit_type='B' and a.username like  'CSD%' THEN 'Brewery CSD'  "
					+ " WHEN a.unit_type='IMPORTUNIT' THEN 'Import Unit'   "
					+ " WHEN a.unit_type='BWFL' THEN 'BWFL' end as type, a.licence_date, a.licence_time  "
					+ " FROM brandlabel.brand_label_applications a, public.district b                                            "
					+ " WHERE a.unit_district_id=b.districtid  AND a.label_flag=true and a.total_fees>=0 and a.challan_flg=true "
					+ " and  a.user5_date IS NOT NULL AND a.vch_approved='APPROVED' and a.digital_sign_pdf_name is not null " +
					" and a.digital_sign_date is not null "+
					" and  a.digital_sign_time is not null and a.vch_license_no ='"+act.getOrder_no()+"'";

           
			System.out.println("===selQr==="+selQr);
		  
			
			ps = con.prepareStatement(selQr);
			 
			rs = ps.executeQuery();

			if (rs.next()) 
			
			{
				
				brand_label_tracking_dt dt = new brand_label_tracking_dt();

				act.setOrder_no(rs.getString("vch_license_no"));
				act.setOrder_date(rs.getDate("digital_sign_date"));
				act.setUnitID(rs.getInt("unit_id"));
				act.setAppID(rs.getInt("app_id"));
				act.setShowApplicationID(rs.getString("app_id"));
				act.setUnitName(rs.getString("unit_name"));
				act.setUnitAddress(rs.getString("unit_address"));
				act.setUnitType(rs.getString("type"));
				act.setLiquorCategory(rs.getString("lic_cat"));
				act.setLicenseType(rs.getString("lic_type"));
				act.setUserDomain(rs.getString("domain_name"));
				act.setUser1Name(rs.getString("user1_name"));
				act.setUnitTypeOrg(rs.getString("unit_type"));
				act.setLicenseDate(rs.getDate("licence_date"));
				act.setBrndchallan(rs.getString("brndchallan_path"));
				act.setLabelchallan(rs.getString("labelchallan_path"));
				act.setOrder_no_view(rs.getString("digital_sign_pdf_name"));
				if(rs.getString("brandtype").equalsIgnoreCase("O") && rs.getBoolean("renewed") ==false){
					act.setBrandtype("Existing Brand with New Labels");
					act.setBrandtext("This Brand is already registered and only label is to be changed !");
				}
				else if( rs.getBoolean("renewed") ==true){
					act.setBrandtype("Renewed Brands with Renewed Labels");
					act.setBrandtext(" ");
				}
				else {	
					
					act.setBrandtext(" ");
					act.setBrandtype("New Brand with New Labels");
				}
			 
				if (rs.getBoolean("renewed") ==true) 
				{
					act.setRenew_new("Renew");
					 
				} else {
					act.setRenew_new("New");
				}

				SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
				if (rs.getDate("app_date") != null)
				{
					String appDate = date.format(Utility
							.convertSqlDateToUtilDate(rs.getDate("app_date")));
					act.setAppDate(appDate);
				} else {
					act.setAppDate("");
				}
                
				
				
				 brndfee = 0;
				 
				 lablfee=0;
							pstmt1=	con.prepareStatement(" SELECT  distinct brand_id from  brandlabel.brand_label_application_details  where app_id= "+rs.getInt("app_id")+"");			
							rs1=pstmt1.executeQuery();
							while(rs1.next())
							{
								brndfee=brndfee+(this.getFeesBrand(rs.getInt("domain_id"), 1, rs.getString("lic_cat"), rs.getString("unit_type"), rs1.getInt("brand_id"),rs.getBoolean("renewed")));
								pstmt2=	con.prepareStatement(" SELECT  distinct package_id,brand_id,quantity from  brandlabel.brand_label_application_details " +
										" where brand_id="+rs1.getInt("brand_id")+" and app_id= "+rs.getInt("app_id")+"");			
								rs2=pstmt2.executeQuery();
								while(rs2.next())
								{
									dt.setFeeslable(this.getFeesLable(rs.getInt("domain_id"), rs2.getInt("quantity"), rs.getString("lic_cat"), rs.getString("unit_type"), rs2.getInt("brand_id"),rs.getBoolean("renewed")));
									lablfee=lablfee+dt.getFeeslable();
									
								}
							act.setLabelchallanfee(lablfee);
							act.setBrnd_reg_in_20_21(rs.getString("existing_brand_20_21"));
							if(rs.getString("existing_brand_20_21").equalsIgnoreCase("YES")){
								act.setBrndchallanfee(0.0);
							}else{
							act.setBrndchallanfee(brndfee);
							}
			
				
			}
							
							flag=true ;
							
				}
			else
				
			{
				act.reset();
				flag=false ;
				
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
						" This is not a Valid Order Number !!"," This is not a Valid Order Number!!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;

	}
	   
	public void fee(brand_label_tracking_action act ,int appid) {

		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int j = 1;
		String selQr = null;
		String filter = "";
		ResultSet rs1,rs2,rs3=null;double brndfee = 0,lablfee=0;PreparedStatement pstmt1,pstmt2,pstmt3=null; 
		try {
			con = ConnectionToDataBase.getConnection();
  

			selQr = " SELECT  a.renewed, a.app_id, a.app_date, a.domain_id, a.domain_name, a.unit_type, a.total_fees, a.total_no_of_labels, "
					+ " a.app_status, a.lic_type, a.lic_cat, a.username, a.unit_id, a.unit_name,   "
					+ "       "
					+ " CASE WHEN a.unit_type='D' THEN 'Distillery' "
					+ " WHEN a.unit_type='B' THEN 'Brewery'  "
					+ " WHEN a.unit_type='O' THEN 'Import Unit'  "
					+ " WHEN a.unit_type='BWFL' THEN 'BWFL' end as type, a.licence_date, a.licence_time  "
					+ " FROM brandlabel.brand_label_applications a, public.district b                                            "
					+ " WHERE a.unit_district_id=b.districtid  AND a.label_flag=true  and a.app_id="+appid+"   and a.total_fees>0 and a.challan_flg=true "
					+ filter;

			ps = con.prepareStatement(selQr);
			 
			rs = ps.executeQuery();

			if (rs.next()) {
				pstmt3=	con.prepareStatement(" SELECT  e.unit_type as import_csd FROM brandlabel.brand_label_applications a ,brandlabel.brand_label_application_details b , " +
	                     " brandlabel.brand_registration  e WHERE a.app_id=b.app_id and b.brand_id=e.brand_id_pk AND a.app_id="+rs.getInt("app_id")+"");			

			  
			//////fee calc////
				  brndfee = 0;lablfee=0;
							pstmt1=	con.prepareStatement(" SELECT  distinct brand_id from  brandlabel.brand_label_application_details  where app_id= "+rs.getInt("app_id")+"");			
							rs1=pstmt1.executeQuery();
							while(rs1.next())
							{//lablfee=0;
								brndfee=brndfee+(this.getFeesBrand(rs.getInt("domain_id"), 1, rs.getString("lic_cat"), rs.getString("unit_type"), rs1.getInt("brand_id"),rs.getBoolean("renewed")));
								pstmt2=	con.prepareStatement(" SELECT  distinct package_id,brand_id,quantity from  brandlabel.brand_label_application_details " +
										" where brand_id="+rs1.getInt("brand_id")+" and app_id= "+rs.getInt("app_id")+"");			
								rs2=pstmt2.executeQuery();
								while(rs2.next())
								{
									lablfee=lablfee+(this.getFeesLable(rs.getInt("domain_id"), rs2.getInt("quantity"), rs.getString("lic_cat"), rs.getString("unit_type"), rs2.getInt("brand_id"),rs.getBoolean("renewed")));
									 
									
								}
							}
							
						
		/*
		 * 
		 * rs3=pstmt3.executeQuery();
if(rs3.next())
{

System.out.println("--checkkkkk---"+rs.getString("import_csd"));

act.setTypee(rs3.getString("import_csd"));

}if(!act.getTypee().equalsIgnoreCase("IU"))
		{act.setLabelchallanfee(lablfee);
				
		act.setBrndchallanfee(brndfee);
		
			
		}else{
			act.setFeeflg("T");
			act.setLabelchallanfee(0);
			act.setBrndchallanfee(brndfee);
			}*/
							
							act.setLabelchallanfee(lablfee);
							if(act.getBrnd_reg_in_20_21().equalsIgnoreCase("YES")){
								act.setBrndchallanfee(0.0);
							}else{
							act.setBrndchallanfee(brndfee);
							}
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		 

	}

	// ------------forward for application----------------------------

	public String forwardApplicationImpl(
			brand_label_tracking_action act) {

		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String queryList = "";

		try {
			con = ConnectionToDataBase.getConnection();
			con.setAutoCommit(false);
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String time = sdf.format(cal.getTime());

			if (ResourceUtil.getUserNameAllReq().trim().substring(0, 9)
					.equalsIgnoreCase("Excise-DL")
					|| ResourceUtil.getUserNameAllReq().trim().substring(0, 9)
					.equalsIgnoreCase("Excise-BR")
					|| ResourceUtil.getUserNameAllReq().trim().substring(0, 10)
					.equalsIgnoreCase("Excise-DEO")) {
				queryList = " UPDATE brandlabel.brand_label_applications "
						+ " SET user1_time=?, user1_remark=?,   "
						+ " user1_date=?, user2_name=?, vch_forwarded=?  WHERE app_id=?   ";

				pstmt = con.prepareStatement(queryList);
				saveStatus = 0;
				pstmt.setString(1, time);
				pstmt.setString(2, act.getFillRemrks());
				pstmt.setDate(3, Utility.convertUtilDateToSQLDate(new Date()));
				pstmt.setString(4, "Excise-DEC-Licence");
				pstmt.setString(5, "Forwarded to DEC-Licence");
				pstmt.setInt(6, act.getAppID());

				saveStatus = pstmt.executeUpdate();

				// System.out.println("Excise-DEO status--------------"+saveStatus);

			}

			else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-DEC-Licence")) {

				
				queryList = " UPDATE brandlabel.brand_label_applications "
						+ " SET user2_time=?, user2_remark=?,    "
						+ " user2_date=?, user3_name=?, vch_forwarded=?  WHERE app_id=?   ";

				pstmt = con.prepareStatement(queryList);
				saveStatus = 0;

				pstmt.setString(1, time);
				pstmt.setString(2, act.getFillRemrks());
				// pstmt.setDate(3,
				// Utility.convertUtilDateToSQLDate(act.getPhysicalRcvdate()));
				pstmt.setDate(3, Utility.convertUtilDateToSQLDate(new Date()));
				pstmt.setString(4, "Excise-JCHQ");
				pstmt.setString(5, "Forwarded to Excise-JCHQ");
				pstmt.setInt(6, act.getAppID());

			

				saveStatus = pstmt.executeUpdate();

				// System.out.println("Excise-DEO status--------------"+saveStatus);

			}

		/*	else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-DEC-Distribution")) {

				queryList = " UPDATE brandlabel.brand_label_applications "
						+ " SET dec_distribution_time=?, dec_distribution_remark=?, physical_rcvng_dt=?,   "
						+ " dec_distribution_date=?, user3_name=?, vch_forwarded=?  WHERE app_id=?   ";

				pstmt = con.prepareStatement(queryList);
				saveStatus = 0;

				pstmt.setString(1, time);
				pstmt.setString(2, act.getFillRemrks());
				pstmt.setDate(4, Utility.convertUtilDateToSQLDate(new Date()));
				pstmt.setDate(3, Utility.convertUtilDateToSQLDate(act
						.getPhysicalRcvdate()));
				pstmt.setString(5, "Excise-JCHQ");
				pstmt.setString(6, "Forwarded to Excise-JCHQ");
				pstmt.setInt(7, act.getAppID());

				saveStatus = pstmt.executeUpdate();

				// System.out.println("Excise-AC-License status--------------"+saveStatus);

			} */
			else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-JCHQ")) {

				queryList = " UPDATE brandlabel.brand_label_applications "
						+ " SET user3_time=?, user3_remark=?,   "
						+ " user3_date=?, user4_name=?, vch_forwarded=?  WHERE app_id=?   ";

				pstmt = con.prepareStatement(queryList);
				saveStatus = 0;

				pstmt.setString(1, time);
				pstmt.setString(2, act.getFillRemrks());
				pstmt.setDate(3, Utility.convertUtilDateToSQLDate(new Date()));
				pstmt.setString(4, "Excise-AC-License");
				pstmt.setString(5, "Forwarded to Excise AC-License");
				pstmt.setInt(6, act.getAppID());

				saveStatus = pstmt.executeUpdate();

				// System.out.println("Excise-AC-License status--------------"+saveStatus);

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-AC-License")) {

				queryList = " UPDATE brandlabel.brand_label_applications "
						+ " SET user4_time=?, user4_remark=?,   "
						+ " user4_date=?, user5_name=?, vch_forwarded=?  WHERE app_id=?   ";

				pstmt = con.prepareStatement(queryList);
				saveStatus = 0;

				pstmt.setString(1, time);
				pstmt.setString(2, act.getFillRemrks());
				pstmt.setDate(3, Utility.convertUtilDateToSQLDate(new Date()));
				pstmt.setString(4, "Excise-Commissioner");
				pstmt.setString(5, "Forwarded to Excise Commissioner");
				pstmt.setInt(6, act.getAppID());

				saveStatus = pstmt.executeUpdate();

				// System.out.println("Excise-AC-License status--------------"+saveStatus);

			}

			if (saveStatus > 0) {
				con.commit();
				act.closeApplication();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Recommendation Saved !!! ",
								"Recommendation Saved !!!"));

			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error !!! ", "Error!!!"));

				con.rollback();

			}
		} catch (Exception se) {
			se.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, se
							.getMessage(), se.getMessage()));

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return "";

	}

	/*// ------------paymentapprove for application----------------------------

	public String paymentapprove(brand_label_tracking_action act) {

		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String queryList = "";

		try {
			con = ConnectionToDataBase.getConnection();
			con.setAutoCommit(false);
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String time = sdf.format(cal.getTime());

			if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-AC-License")) {

				queryList = " UPDATE brandlabel.brand_label_applications "
						+ " SET user4_time=?, user4_remark=?,   "
						+ " user4_date=?, user5_name=?, vch_forwarded=?  WHERE app_id=?   ";

				pstmt = con.prepareStatement(queryList);
				saveStatus = 0;

				pstmt.setString(1, time);
				pstmt.setString(2, act.getFillRemrks());
				pstmt.setDate(3, Utility.convertUtilDateToSQLDate(new Date()));
				pstmt.setString(4, "Excise-Commissioner");
				pstmt.setString(5, "Forwarded to Excise Commissioner");
				pstmt.setInt(6, act.getAppID());

				saveStatus = pstmt.executeUpdate();

				// System.out.println("Excise-AC-License status--------------"+saveStatus);

			}

			if (saveStatus > 0) {
				con.commit();
				act.closeApplication();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Payment Recommendation Saved !!! ",
								"Payment Recommendation Saved !!!"));

			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error !!! ", "Error!!!"));

				con.rollback();

			}
		} catch (Exception se) {
			se.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, se
							.getMessage(), se.getMessage()));

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return "";

	}

	// ------------paymentreject for application----------------------------

	public String paymentreject(brand_label_tracking_action act) {

		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String queryList = "";

		try {
			con = ConnectionToDataBase.getConnection();
			con.setAutoCommit(false);
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String time = sdf.format(cal.getTime());

			if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-AC-License")) {

				queryList = " UPDATE brandlabel.brand_label_applications "
						+ " SET user4_time=?, user4_remark=?,   "
						+ " user4_date=?, " +
						// " user5_name=?, " +
						" vch_forwarded=?, paymentapprovalflag='R'  WHERE app_id=?   ";

				pstmt = con.prepareStatement(queryList);
				saveStatus = 0;

				pstmt.setString(1, time);
				pstmt.setString(2, act.getFillRemrks());
				pstmt.setDate(3, Utility.convertUtilDateToSQLDate(new Date()));
				// pstmt.setString(4, "Excise-AC-License");
				pstmt.setString(4, "Application Rejected Due to payment");
				pstmt.setInt(5, act.getAppID());

				saveStatus = pstmt.executeUpdate();

				// System.out.println("Excise-AC-License status--------------"+saveStatus);

			}

			if (saveStatus > 0) {
				con.commit();
				act.closeApplication();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Payment Recommendation Saved !!! ",
								"Payment Recommendation Saved !!!"));

			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error !!! ", "Error!!!"));

				con.rollback();

			}
		} catch (Exception se) {
			se.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, se
							.getMessage(), se.getMessage()));

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return "";

	}*/

	// ======================get max id
	// brandlabel.brand_approval_sequence=============================

	public int getMaxId(brand_label_tracking_action act) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;

		query = "select max(brand_id) as id from	distillery.brand_registration_20_21";


		int maxid = 0;
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				maxid = rs.getInt("id");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return maxid;

	}

	public int getMaxId1(brand_label_tracking_action act) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;

		query = "select max(package_id) as id from	distillery.packaging_details_20_21";


		int maxid = 0;
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				maxid = rs.getInt("id");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return maxid;

	}
	//======================get max id brandlabel.brand_approval_sequence=============================



	public int getMaxIdOnLiquorType(brand_label_tracking_action act) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
System.out.println(act.getLiquorCategory());

		if(act.getLiquorCategory().equalsIgnoreCase("CL"))
		{
			query = 	" SELECT max(cl_id) as id  FROM brandlabel.brand_approval_sequence ";
		}
		else if(act.getLiquorCategory().equalsIgnoreCase("FL"))
		{
			query = 	" SELECT max(fl_id) as id  FROM brandlabel.brand_approval_sequence ";
		}
		else if(act.getLiquorCategory().equalsIgnoreCase("IMFL") || act.getLiquorCategory().equalsIgnoreCase("IMPORTED FL") ) 
		{
			query = 	" SELECT max(fl_id) as id  FROM brandlabel.brand_approval_sequence ";
		}
		else if(act.getLiquorCategory().equalsIgnoreCase("Beer") || act.getLiquorCategory().equalsIgnoreCase("IMPORTED BEER"))
		{
			query = 	" SELECT max(beer_id) as id  FROM brandlabel.brand_approval_sequence ";
		}
		else if(act.getLiquorCategory().equalsIgnoreCase("LAB") || act.getLiquorCategory().equalsIgnoreCase("IMPORTED LAB"))
		{
			query = 	" SELECT max(lab_id) as id  FROM brandlabel.brand_approval_sequence ";
		}
		else if(act.getLiquorCategory().equalsIgnoreCase("Wine") || act.getLiquorCategory().equalsIgnoreCase("IMPORTED WINE"))
		{
			query = 	" SELECT max(wine_id) as id  FROM brandlabel.brand_approval_sequence ";
		}

		else{
			query = "";
		}

//System.out.println("====query==="+query);

		int maxid = 0;
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				maxid = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return maxid + 1;

	}
	
	// ====================approve application=========================

	public String approvedApplicationImpl(
			brand_label_tracking_action act) {

		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;ResultSet rs1 = null;
		String queryList = "";
		String updtQr = "";
		String filter1="";
		String filter2="";
		int seq = this.getMaxId(act);
		int seq2 = this.getMaxId1(act);
		int seq3=0;
		if(seq2<3000)
		{
			seq3 = 3000;
		}
		else if(seq2>=3000)
		{
			seq3 = seq2+1;

		}



		try {
			con = ConnectionToDataBase.getConnection();
			con.setAutoCommit(false);
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String time = sdf.format(cal.getTime());
			int seqlic = this.getMaxIdOnLiquorType(act);
			 
			if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-Commissioner")) {

				queryList = " UPDATE brandlabel.brand_label_applications "
						+ " SET user5_time=?, user5_remark=?,   "
						+ " user5_date=?, vch_approved=?, vch_forwarded=?, vch_license_no=? " +
						"  WHERE app_id=?   ";

				pstmt = con.prepareStatement(queryList);
				saveStatus = 0;

				pstmt.setString(1, time);
				pstmt.setString(2, act.getFillRemrks());
				pstmt.setDate(3, Utility.convertUtilDateToSQLDate(new Date()));
				pstmt.setString(4, "APPROVED");
				pstmt.setString(5, "Approved By Excise Commissioner and Pending For Digital Sign");
				pstmt.setString(6,act.getLiquorCategory()+"-"+ seqlic+"-2020-21");
				pstmt.setInt(7, act.getAppID());

				saveStatus = pstmt.executeUpdate();
				//System.out.println("=====saveStatus1======="+saveStatus);
				if (saveStatus > 0){
					saveStatus = 0;
					pstmt1 = con.prepareStatement("select brand_id   from brandlabel.brand_label_application_details   where app_id="+act.getAppID()+"");

					rs = pstmt1.executeQuery();

					while (rs.next()) {
				pstmt = con.prepareStatement("update brandlabel.brand_label_application_details set " +
						"vch_brand_approval_no=(select max(coalesce(vch_brand_approval_no,0))+1 from brandlabel.brand_label_application_details  ) " +
						" where app_id="+act.getAppID()+"  and brand_id="+rs.getInt("brand_id")+"");
				saveStatus = pstmt.executeUpdate();
					}
				}rs.close();
				//System.out.println("=====saveStatus2======="+saveStatus);
				if (saveStatus > 0) {
					saveStatus = 0;int i=1;
					 
				  pstmt1 = con.prepareStatement("select id   from brandlabel.brand_label_application_details   where app_id="+act.getAppID()+"");

					rs = pstmt1.executeQuery();

					while (rs.next()) {
						 
						pstmt1 = con.prepareStatement("update brandlabel.brand_label_application_details set " +
								"vch_package_approval_no=(select max(coalesce(vch_package_approval_no,0))+1 from brandlabel.brand_label_application_details  ) " +
								" where id="+rs.getInt("id")+"");
						
						saveStatus = pstmt1.executeUpdate(); 
						 
					}


				}//System.out.println("=====saveStatus3======="+saveStatus);
				if (saveStatus > 0){
					saveStatus = 0;


					if(act.getLiquorCategory().equalsIgnoreCase("CL"))
					{
						updtQr = 	" UPDATE brandlabel.brand_approval_sequence SET cl_id=?  ";
					}
					else if(act.getLiquorCategory().equalsIgnoreCase("FL"))
					{
						updtQr = 	" UPDATE brandlabel.brand_approval_sequence SET fl_id=? ";
					}
					else if(act.getLiquorCategory().equalsIgnoreCase("IMFL") || act.getLiquorCategory().equalsIgnoreCase("IMPORTED FL"))
					{
						updtQr = 	" UPDATE brandlabel.brand_approval_sequence SET fl_id=? ";
					}
					else if(act.getLiquorCategory().equalsIgnoreCase("Beer") || act.getLiquorCategory().equalsIgnoreCase("IMPORTED BEER"))
					{
						updtQr = 	" UPDATE brandlabel.brand_approval_sequence SET beer_id=? ";
					}
					else if(act.getLiquorCategory().equalsIgnoreCase("LAB") || act.getLiquorCategory().equalsIgnoreCase("IMPORTED LAB"))
					{
						updtQr = 	" UPDATE brandlabel.brand_approval_sequence SET lab_id=? ";
					}
					else if(act.getLiquorCategory().equalsIgnoreCase("Wine") || act.getLiquorCategory().equalsIgnoreCase("IMPORTED WINE"))
					{
						updtQr = 	" UPDATE brandlabel.brand_approval_sequence SET wine_id=? ";
					}

					else{
						updtQr = "";
					}

//System.out.println("=====updtQr==="+updtQr);
					pstmt = con.prepareStatement(updtQr);
					saveStatus = 0;

					pstmt.setInt(1, seqlic);


					saveStatus = pstmt.executeUpdate();


				}//System.out.println("=====saveStatus4======="+saveStatus);
				if (saveStatus > 0) {
					 

					if (act.getRenew_new().equalsIgnoreCase("Renew")) {
						String brand_query = " INSERT INTO distillery.brand_registration_20_21("
								+ " brand_id, brand_name, liquor_category, year, licencee_dtl, distillery_id, db_duty, approval,"
								+ " approvaldt, strength, liquor_type, license_category, vch_license_type, license_number, brewery_id, "
								+ " tracking_flg, int_bwfl_id, int_fl2d_id, manufacturer_details, int_fl2a_id, maped_unmaped_flag, "
								+ " unit_name, unit_type, for_csd_civil, brand_registration_no, domain, sub_type, "
								+ " etin_unit_id, created_dt, vch_dstillery_hindi, brand_name_hindi, unit_nm, fl2d_country_code, renewal_flg,app_id,old_brandid)"
								+ " select brand_id, REPLACE(brand_name,'''','') , liquor_category, year, licencee_dtl, distillery_id, db_duty, approval, "
								+ " approvaldt, strength, liquor_type, license_category, vch_license_type, license_number, brewery_id,"
								+ " tracking_flg, int_bwfl_id, int_fl2d_id, manufacturer_details, int_fl2a_id, maped_unmaped_flag, "
								+ " unit_name, unit_type, for_csd_civil, brand_registration_no, domain, sub_type, etin_unit_id, created_dt, "
								+ " vch_dstillery_hindi, brand_name_hindi, unit_nm, fl2d_country_code, renewal_flg,"+act.getAppID()+",brand_id  "
								+ " from distillery.brand_registration_19_20 where brand_id='"
								+ act.getBrandid() + "' ON CONFLICT on CONSTRAINT brand_registration_20_21_pkey DO NOTHING ";

	 

						String pak_queryDetail = " INSERT INTO distillery.packaging_details_20_21( "
								+ " package_name, package_type, mrp, edp, adduty, wsmargin, retmargin, brand_id_fk, "
								+ " package_id, sno, code_generate_through, box_id, quantity, duty, permit, export_box_size,  "
								+ " etin_no_flag, code, export, rounded_mrp,app_id,old_pckid) "
								+ " select REPLACE(package_name,'''','') , package_type, 0, 0, 0, 0, 0, "+act.getBrandid()+", "
								+ " package_id, sno, code_generate_through, box_id, quantity, 0, 0, export_box_size,  "
								+ " etin_no_flag, code, export, 0 ,"+act.getAppID()+",package_id  " +
										" from distillery.packaging_details_19_20 where  " +
						" package_id in (select distinct package_id from brandlabel.brand_label_application_details where app_id='" + act.getAppID() + "' ) on conflict on CONSTRAINT packaging_details_20_21_pkey do nothing ";

						con.setAutoCommit(false);
						pstmt = con.prepareStatement(brand_query);

						saveStatus += pstmt.executeUpdate();

						if (saveStatus > 0) {
							 

							pstmt = con.prepareStatement(pak_queryDetail);
							saveStatus += pstmt.executeUpdate();


						}

					} else if (act.getRenew_new().equalsIgnoreCase("New")) {


						String filter="";

						int seq1=0;
					 
						if(act.getUnittype().equalsIgnoreCase("D"))
						{
							filter="distillery_id,int_bwfl_id,brewery_id,int_fl2a_id,int_fl2d_id";
						}
						else if(act.getUnittype().equalsIgnoreCase("B"))
						{
							filter="brewery_id,int_bwfl_id,distillery_id,int_fl2a_id,int_fl2d_id";
						}
						else if(act.getUnittype().equalsIgnoreCase("BWFL"))
						{
							filter="int_bwfl_id,brewery_id,distillery_id,int_fl2a_id,int_fl2d_id";
						}
						else if(act.getUnittype().equalsIgnoreCase("importunit") && act.getLicencetype().equalsIgnoreCase("FL2A"))
						{
							filter="int_fl2a_id,int_fl2d_id,int_bwfl_id,brewery_id,distillery_id ";
						}
						else if(act.getUnittype().equalsIgnoreCase("importunit") && act.getLicencetype().equalsIgnoreCase("FL2D"))
						{
							filter="int_fl2d_id,int_fl2a_id,int_bwfl_id,brewery_id,distillery_id ";
						}

						
						if(seq<2000)
						{
							seq1 = 2000;
						}
						else if(seq>=2000)
						{
							seq1 = seq;

						}
						con.setAutoCommit(false);
						String br_queryDetail = " select COALESCE (unit_type,'NA') as unit_type_csd, brand_id_pk,REPLACE(brand_name,'''','') as  brand_name,   financial_year, unit_id,0,0, " +
								"  brand_strength, liquor_type,licence_type, licence_no,coalesce(bwflbrand_licno,'NA')bwflbrand_licno," +
								" brand_registration_no, industry_type,for_csd_civil,	 " +
								" domain_name,sub_type,created_date,"+act.getAppID()+" from brandlabel.brand_registration where brand_id_pk in (select distinct brand_id from brandlabel.brand_label_application_details where   app_id='" + act.getAppID() + "' ) ";

						pstmt1 = con.prepareStatement(br_queryDetail);

						rs = pstmt1.executeQuery();

						while (rs.next()) {
							if(act.getBrnd_reg_in_20_21().equalsIgnoreCase("YES")){
								
								String pak_queryDetail = " select package_id_pk, brand_id_fk, size_ml, package_type" +
										// " , remark, finalize_flag" +
										" from brandlabel.brand_registration_detail_packaging " +
										" where  brand_id_fk= "+rs.getInt("brand_id_pk")+" and  package_id_pk in ( select distinct package_id from brandlabel.brand_label_application_details where   app_id='" + act.getAppID() + "' )";
								 System.out.println("====pak_queryDetail----"+pak_queryDetail);
								pstmt1 = con.prepareStatement(pak_queryDetail);

								rs1 = pstmt1.executeQuery();

								while (rs1.next()) {String query=" ";

								 System.out.println("====getUnitType=1111=="+act.getUnitType());
								
								if(act.getUnitType().equalsIgnoreCase("BWFL")) {

									query=" INSERT INTO distillery.packaging_details_temp( brand_id_fk, package_id," +
											" package_name, package_type,quantity,app_id) values ((select brand_id from distillery.brand_registration_20_21 where brand_name='"+rs.getString("brand_name")+"' and  int_bwfl_id=  (select unit_id from bwfl.registration_of_bwfl_lic_holder_20_21   where vch_license_number='"+rs.getString("licence_no")+"' )),'"+seq3+"' ," +
											"'"+rs1.getInt("size_ml")+"'||'ML','"+rs1.getString("package_type")+"','"+rs1.getString("size_ml")+"','"+act.getAppID()+"')";

									 
								}else if(act.getUnitType().equalsIgnoreCase("Distillery")){
									
									query=" INSERT INTO distillery.packaging_details_20_21( brand_id_fk, package_id," +
											" package_name, package_type,quantity,app_id) values ((select brand_id from distillery.brand_registration_20_21 where brand_name='"+rs.getString("brand_name")+"'),'"+seq3+"' ," +
											"'"+rs1.getInt("size_ml")+"'||'ML','"+rs1.getString("package_type")+"','"+rs1.getString("size_ml")+"','"+act.getAppID()+"'" +
													"  )";
									
								}
								else {
									query=" INSERT INTO distillery.packaging_details_20_21( brand_id_fk, package_id," +
											" package_name, package_type,quantity,app_id) values ((select brand_id from distillery.brand_registration_20_21 where brand_name='"+rs.getString("brand_name")+"'),'"+seq3+"' ," +
											"'"+rs1.getInt("size_ml")+"'||'ML','"+rs1.getString("package_type")+"','"+rs1.getString("size_ml")+"','"+act.getAppID()+"')";
									 
								}
									
									pstmt1 = con.prepareStatement(query); System.out.println(" yes===="+query);
									saveStatus = pstmt1.executeUpdate();
									 
									pstmt1=null;
									saveStatus=0;
									 
									String pak_update = " update  brandlabel.brand_registration_detail_packaging " +
											" set new_package_id='"+seq3+"'  where  package_id_pk='"+rs1.getInt("package_id_pk")+"'";
									 
									pstmt1 = con.prepareStatement(pak_update);
									saveStatus = pstmt1.executeUpdate();
									
									 
									
									seq3=seq3+1;
								
								
							}
								}
							else{
								
							
							if(act.isBrandtypeflg()) {
								 if(act.getUnitType().equalsIgnoreCase("BWFL") && !rs.getString("bwflbrand_licno").equalsIgnoreCase("NA")) {
									 
										pstmt1 = con.prepareStatement("INSERT INTO distillery.brand_registration_temp( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
												"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
												" values ('"+rs.getString("licence_type")+"',(select max(brand_id) from distillery.brand_registration_19_20 where int_bwfl_id= (select unit_id from bwfl.registration_of_bwfl_lic_holder_19_20  where \r\n" + 
														"							 vch_license_number='"+rs.getString("bwflbrand_licno")+"' )  and   brand_name='"+rs.getString("brand_name")+"'),'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
														"'"+rs.getString("liquor_type")+"','"+rs.getString("licence_type")+"','"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
																"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+", 'F' ) ON CONFLICT on CONSTRAINT brand_registration_temp_pkey DO NOTHING");
										   
								 }
								/* else if(act.getUnitType().equalsIgnoreCase("BWFL") && rs.getString("bwflbrand_licno").equalsIgnoreCase("NA")) {
									 
										pstmt1 = con.prepareStatement("INSERT INTO distillery.brand_registration_temp( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
												"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid)" +
												" values ('"+rs.getString("licence_type")+"',(select max(brand_id) from distillery.brand_registration_19_20 where int_bwfl_id= (select unit_id from bwfl.registration_of_bwfl_lic_holder_19_20  where \r\n" + 
														"							 vch_license_number='"+rs.getString("bwflbrand_licno")+"' )  and   brand_name='"+rs.getString("brand_name")+"'),'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
														"'"+rs.getString("liquor_type")+"','"+rs.getString("licence_type")+"','"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
																"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ) ON CONFLICT on CONSTRAINT brand_registration_20_21_pkey DO NOTHING");
										   
								 }*/
								 else  if(act.getUnitType().equalsIgnoreCase("Import Unit")  ) {
									 
									 if(act.getUnittype().equalsIgnoreCase("importunit") && act.getLicencetype().equalsIgnoreCase("FL2A"))
										{
											filter1=" '"+rs.getString("licence_type")+"' ";
										}
										else if(act.getUnittype().equalsIgnoreCase("importunit") && act.getLicencetype().equalsIgnoreCase("FL2D"))
										{
											filter1=" 'IU' ";
										}
										pstmt1 = con.prepareStatement("INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
												"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
												" values ('"+rs.getString("liquor_type")+"',(select max(brand_id) from distillery.brand_registration_19_20 where brand_name='"+rs.getString("brand_name")+"'),'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
														"'"+rs.getString("liquor_type")+"',"+filter1+",'"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
																"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" , 'F')  ON CONFLICT on CONSTRAINT brand_registration_20_21_pkey DO NOTHING") ;
										
									 System.out.println("======CSD----1111==="+"INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
												"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
												" values ('"+rs.getString("liquor_type")+"',(select max(brand_id) from distillery.brand_registration_19_20 where brand_name='"+rs.getString("brand_name")+"'),'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
														"'"+rs.getString("liquor_type")+"',"+filter1+",'"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
																"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+",'F' )  ON CONFLICT on CONSTRAINT brand_registration_20_21_pkey DO NOTHING");
								 
								 
								 }else  if(act.getUnitType().equalsIgnoreCase("Distillery CSD") || act.getUnitType().equalsIgnoreCase("Brewery CSD")   ) {  
									 
									 pstmt1 = con.prepareStatement("INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
												"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
												" values ('"+rs.getString("liquor_type")+"',(select max(brand_id) from distillery.brand_registration_19_20 where brand_name='"+rs.getString("brand_name")+"'),'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
														"'"+rs.getString("liquor_type")+"','"+rs.getString("unit_type_csd")+"','"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
																"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+",'F' )  ON CONFLICT on CONSTRAINT brand_registration_20_21_pkey DO NOTHING") ;
										
									 System.out.println("======CSD--111--Distillery CSD==="+"INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
												"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
												" values ('"+rs.getString("liquor_type")+"',(select max(brand_id) from distillery.brand_registration_19_20 where brand_name='"+rs.getString("brand_name")+"'),'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
														"'"+rs.getString("liquor_type")+"','"+rs.getString("unit_type_csd")+"','"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
																"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ,'F')  ON CONFLICT on CONSTRAINT brand_registration_20_21_pkey DO NOTHING");
								 
								 }else if(act.getUnitType().equalsIgnoreCase("Distillery")){
									 String filter6="";
									 String filter7="";
									 if(rs.getString("liquor_type").equalsIgnoreCase("CL")){
										 filter6=" '"+rs.getString("liquor_type")+"' ";
										 filter7=" '"+rs.getString("liquor_type")+"','"+rs.getString("liquor_type")+"' ";
									 }else{
										 filter6=" '"+rs.getString("licence_type")+"' ";
										 filter7=" '"+rs.getString("liquor_type")+"','"+rs.getString("licence_type")+"' ";
									 }//liquor_type
									 
									 pstmt1 = con.prepareStatement("INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
												"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,etin_unit_id,approval)" +
												" values ("+filter6+",(select max(brand_id) from distillery.brand_registration_19_20 where brand_name='"+rs.getString("brand_name")+"'),'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
														""+filter7+" ,'"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
																"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ," +
																		" (SELECT  etin_unit_id from public.dis_mst_pd1_pd2_lic  where   int_app_id_f='"+rs.getString("unit_id")+"'),'F' )  ON CONFLICT on CONSTRAINT brand_registration_20_21_pkey DO NOTHING ");
										 System.out.println("--Distillery-else=11====");
									 
								 }
								 else {
								pstmt1 = con.prepareStatement("INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
										"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
										" values ('"+rs.getString("licence_type")+"',(select max(brand_id) from distillery.brand_registration_19_20 where brand_name='"+rs.getString("brand_name")+"'),'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
												"'"+rs.getString("liquor_type")+"', '"+rs.getString("licence_type")+"' ,'"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
														"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ,'F')  ON CONFLICT on CONSTRAINT brand_registration_20_21_pkey DO NOTHING ");
								 System.out.println("---else=11====");
								 }
								 	
							}else {
								seq1=seq1+1;
								 if(act.getUnitType().equalsIgnoreCase("BWFL")) {

									 pstmt1 = con.prepareStatement("INSERT INTO distillery.brand_registration_temp( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
											"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
											" values ('"+rs.getString("licence_type")+"',"+seq1+",'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
													"'"+rs.getString("liquor_type")+"','"+rs.getString("licence_type")+"','"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
															"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ,'F')");
								 
								
								 } else  if(act.getUnitType().equalsIgnoreCase("Import Unit")  ) {
									 
									 if(act.getUnittype().equalsIgnoreCase("importunit") && act.getLicencetype().equalsIgnoreCase("FL2A"))
										{
											filter2=" '"+rs.getString("licence_type")+"' ";
										}
										else if(act.getUnittype().equalsIgnoreCase("importunit") && act.getLicencetype().equalsIgnoreCase("FL2D"))
										{
											filter2=" 'IU' ";
										}
									 pstmt1 = con.prepareStatement("INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
												"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
												" values ('"+rs.getString("liquor_type")+"',"+seq1+",'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
														"'"+rs.getString("liquor_type")+"', "+filter2+" ,'"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
																"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ,'F')");
									 
									System.out.println("====CSD-----"+"INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
											"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
											" values ('"+rs.getString("liquor_type")+"',"+seq1+",'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
													"'"+rs.getString("liquor_type")+"', "+filter2+" ,'"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
															"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ,'F')");
								 
								 } else  if(act.getUnitType().equalsIgnoreCase("Distillery CSD") || act.getUnitType().equalsIgnoreCase("Brewery CSD")   ) {  
									 
									 
									 pstmt1 = con.prepareStatement("INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
												"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
												" values ('"+rs.getString("liquor_type")+"',"+seq1+",'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
														"'"+rs.getString("liquor_type")+"', '"+rs.getString("unit_type_csd")+"' ,'"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
																"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ,'F')");
									 
									System.out.println("====CSD--Distillery CSD---"+"INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
											"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
											" values ('"+rs.getString("liquor_type")+"',"+seq1+",'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
													"'"+rs.getString("liquor_type")+"', '"+rs.getString("unit_type_csd")+"' ,'"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
															"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ,'F')");
								 
								 }else if(act.getUnitType().equalsIgnoreCase("Distillery")){
									 String filter6="";
									 String filter7="";
									 if(rs.getString("liquor_type").equalsIgnoreCase("CL")){
										 filter6=" '"+rs.getString("liquor_type")+"' ";
										 filter7=" '"+rs.getString("liquor_type")+"','"+rs.getString("liquor_type")+"' ";
									 }else{
										 filter6=" '"+rs.getString("licence_type")+"' ";
										 filter7=" '"+rs.getString("liquor_type")+"','"+rs.getString("licence_type")+"' ";
									 }
									 pstmt1 = con.prepareStatement("INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
												"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,etin_unit_id,approval)" +
												" values ("+filter6+","+seq1+",'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
														" "+filter7+",'"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
																"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ," +
																		" (SELECT  etin_unit_id from public.dis_mst_pd1_pd2_lic  where   int_app_id_f='"+rs.getString("unit_id")+"'),'F') ");
									
									 System.out.println("---new distillery ---"+"INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
												"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,etin_unit_id,approval)" +
												" values ("+filter6+","+seq1+",'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
														""+filter7+",'"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
																"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ," +
																		" (SELECT  etin_unit_id from public.dis_mst_pd1_pd2_lic  where   int_app_id_f='"+rs.getString("unit_id")+"'),'F') ");
								 }
								 else {
								 pstmt1 = con.prepareStatement("INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
										"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
										" values ('"+rs.getString("licence_type")+"',"+seq1+",'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
												"'"+rs.getString("liquor_type")+"','"+rs.getString("licence_type")+"','"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
														"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ,'F')");
							 
						System.out.println("===else--222===="+"INSERT INTO distillery.brand_registration_20_21( license_category ,brand_id,brand_name,  year,  "+filter+", strength, " +
										"liquor_type,  vch_license_type, license_number, brand_registration_no,  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id,old_brandid,approval)" +
										" values ('"+rs.getString("licence_type")+"',"+seq1+",'"+rs.getString("brand_name")+"','"+rs.getString("financial_year")+"','"+rs.getString("unit_id")+"',0,0,0,0,'"+rs.getString("brand_strength")+"'," +
												"'"+rs.getString("liquor_type")+"','"+rs.getString("licence_type")+"','"+rs.getString("licence_no")+"','"+rs.getString("brand_registration_no")+"','"+rs.getString("industry_type")+"'," +
														"'"+rs.getString("for_csd_civil")+"','"+rs.getString("domain_name")+"','"+rs.getString("sub_type")+"','"+rs.getString("created_date")+"',"+act.getAppID()+" ,"+rs.getInt("brand_id_pk")+" ,'F')");
							 }}
							saveStatus = pstmt1.executeUpdate();	
							if(act.isBrandtypeflg()) {
								saveStatus=1;
							}
							
							if (saveStatus > 0) {
								saveStatus = 0;
								
								String pak_queryDetail = " select package_id_pk, brand_id_fk, size_ml, package_type" +
										// " , remark, finalize_flag" +
										" from brandlabel.brand_registration_detail_packaging " +
										" where  brand_id_fk= "+rs.getInt("brand_id_pk")+" and  package_id_pk in ( select distinct package_id from brandlabel.brand_label_application_details where   app_id='" + act.getAppID() + "' )";

								pstmt1 = con.prepareStatement(pak_queryDetail);

								rs1 = pstmt1.executeQuery();

								while (rs1.next()) {String query=" ";
									if(act.isBrandtypeflg()) {
										 if(act.getUnitType().equalsIgnoreCase("BWFL")) {

											 query=" INSERT INTO distillery.packaging_details_20_21( brand_id_fk, package_id," +
														" package_name, package_type,quantity,app_id,old_pckid)"
														+ " values ((select max(brand_id) from distillery.brand_registration_19_20 where  int_bwfl_id=(select unit_id from bwfl.registration_of_bwfl_lic_holder_19_20  where 	  vch_license_number='"+rs.getString("bwflbrand_licno")+"' )   and  brand_name='"+rs.getString("brand_name")+"'),"
																+ "(select package_id from distillery.packaging_details_19_20 where quantity='"+rs1.getString("size_ml")+"' and brand_id_fk in (select brand_id from distillery.brand_registration_19_20 where  int_bwfl_id=(select unit_id from bwfl.registration_of_bwfl_lic_holder_19_20  where 	  vch_license_number='"+rs.getString("bwflbrand_licno")+"' )   and  brand_name='"+rs.getString("brand_name")+"'))," +
														"'"+rs1.getInt("size_ml")+"'||'ML','"+rs1.getString("package_type")+"','"+rs1.getString("size_ml")+"',"+act.getAppID()+","+rs1.getInt("package_id_pk")+" "
																+ " ) on conflict on CONSTRAINT packaging_details_20_21_pkey do nothing ";
											 
										 }else  if(act.getUnitType().equalsIgnoreCase("Import Unit")) {

											 query=" INSERT INTO distillery.packaging_details_20_21( brand_id_fk, package_id," +
														" package_name, package_type,quantity,app_id,old_pckid)"
														+ " values ((select max(brand_id) from distillery.brand_registration_19_20 where  brand_name='"+rs.getString("brand_name")+"'),"
																+ "(select package_id from distillery.packaging_details_19_20 where quantity='"+rs1.getString("size_ml")+"' and brand_id_fk in (select brand_id from distillery.brand_registration_19_20 where  brand_name='"+rs.getString("brand_name")+"'))," +
														"'"+rs1.getInt("size_ml")+"'||'ML','"+rs1.getString("package_type")+"','"+rs1.getString("size_ml")+"',"+act.getAppID()+","+rs1.getInt("package_id_pk")+" "
																+ ")  on conflict on CONSTRAINT packaging_details_20_21_pkey do nothing ";
											 
										 }else {
										 query=" INSERT INTO distillery.packaging_details_20_21( brand_id_fk, package_id," +
													" package_name, package_type,quantity,app_id,old_pckid)"
													+ " values ((select max(brand_id) from distillery.brand_registration_19_20 where brand_name='"+rs.getString("brand_name")+"'),"
															+ "(select package_id from distillery.packaging_details_19_20 where quantity='"+rs1.getString("size_ml")+"' and brand_id_fk in (select max(brand_id) from distillery.brand_registration_19_20 where brand_name='"+rs.getString("brand_name")+"'))," +
													"'"+rs1.getInt("size_ml")+"'||'ML','"+rs1.getString("package_type")+"','"+rs1.getString("size_ml")+"',"+act.getAppID()+","+rs1.getInt("package_id_pk")+"  "
															//+ "(select code_generate_through from distillery.packaging_details_19_20 where quantity='"+rs1.getString("size_ml")+"' and brand_id_fk in (select max(brand_id) from distillery.brand_registration_19_20 where brand_name='"+rs.getString("brand_name")+"')))  on conflict on CONSTRAINT packaging_details_20_21_pkey do nothing ";
													+ " )  on conflict on CONSTRAINT packaging_details_20_21_pkey do nothing ";
										 }
									}else {
										System.out.println("====getUnitType==="+act.getUnitType());
										if(act.getUnitType().equalsIgnoreCase("BWFL")) {

											  query=" INSERT INTO distillery.packaging_details_temp( brand_id_fk, package_id," +
													" package_name, package_type,quantity,app_id,old_pckid) values ("+seq1+",'"+seq3+"' ," +
													"'"+rs1.getInt("size_ml")+"'||'ML','"+rs1.getString("package_type")+"','"+rs1.getString("size_ml")+"',"+act.getAppID()+","+rs1.getInt("package_id_pk")+")";

											
										}else if(act.getUnitType().equalsIgnoreCase("Distillery")) {

											query=" INSERT INTO distillery.packaging_details_20_21( brand_id_fk, package_id," +
													" package_name, package_type,quantity,app_id,old_pckid) values ("+seq1+",'"+seq3+"' ," +
													"'"+rs1.getInt("size_ml")+"'||'ML','"+rs1.getString("package_type")+"','"+rs1.getString("size_ml")+"',"+act.getAppID()+","+rs1.getInt("package_id_pk")+"" +
															" )";
											System.out.println("====distillery=etin====="+query);
											
										}
											else {
									  query=" INSERT INTO distillery.packaging_details_20_21( brand_id_fk, package_id," +
											" package_name, package_type,quantity,app_id,old_pckid) values ("+seq1+",'"+seq3+"' ," +
											"'"+rs1.getInt("size_ml")+"'||'ML','"+rs1.getString("package_type")+"','"+rs1.getString("size_ml")+"',"+act.getAppID()+","+rs1.getInt("package_id_pk")+")";

									}
									}
									pstmt1 = con.prepareStatement(query);System.out.println("======packaging===="+query);
									saveStatus = pstmt1.executeUpdate();
									 
									pstmt1=null;
									saveStatus=0;
									 
									String pak_update = " update  brandlabel.brand_registration_detail_packaging " +
											" set new_package_id='"+seq3+"'  where  package_id_pk='"+rs1.getInt("package_id_pk")+"'";
									 
									pstmt1 = con.prepareStatement(pak_update);
									saveStatus = pstmt1.executeUpdate();
									
									 
									
									seq3=seq3+1;
								}


							}
							
							}	
						}

					/*	String brand_query = " INSERT INTO distillery.brand_registration_20_21( " +
								"  brand_id,brand_name,  year,  "+filter+", " +
								" strength, liquor_type,  vch_license_type, license_number, brand_registration_no, " +
								//"    int_fl2a_id, " +
								"  unit_type, for_csd_civil, domain, sub_type, created_dt,app_id)" +
								" select "+seq1+", brand_name,   financial_year, unit_id,0,0, " +
								"  brand_strength, liquor_type,licence_type, licence_no," +
								" brand_registration_no, industry_type,for_csd_civil,	 " +
								" domain_name,sub_type,created_date,"+act.getAppID()+" from brandlabel.brand_registration where brand_id_pk in (select distinct brand_id from brandlabel.brand_label_application_details where   app_id='" + act.getAppID() + "' ) ";
					
						pstmt1 = con.prepareStatement(brand_query);
						saveStatus = pstmt1.executeUpdate();*/

						



					}
				}

			}

			if (saveStatus > 0) {
				con.commit();
				act.closeApplication();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								" Application Approved   !!! ",
								"Application Approved !!!"));

			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error !!! ", "Error!!!"));

				con.rollback();

			}
		} catch (Exception se) {
			se.printStackTrace();

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (pstmt1 != null)
					pstmt1.close();
				if (con != null)
					con.close();

			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return "";

	}
//==============================================
	
	public String getdata1(
			brand_label_tracking_action act) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String path = null;

		try {
			con = ConnectionToDataBase.getConnection();

			//brand_name,brand_strength,for_csd_civil,sub_type,forcsd_civilsubtype 
			
			String queryList = "select brand_id_pk, industry_type, licence_type, unit_id, licence_no, brand_registration_no, " +
					" brand_registration_date, brand_name, brand_strength, liquor_type, for_csd_civil, sub_type,forcsd_civilsubtype,financial_year, finalize_flag," +
					"  created_date, created_by, modify_date,  domain, countryid, stateid, districid," +
					"  domain_id, domain_name from	brandlabel.brand_registration where brand_id_pk='"+act.getBrandid()+"'";
//System.out.println("=========getdata1=============="+queryList);
			pstmt = con.prepareStatement(queryList);

			rs = pstmt.executeQuery();

			while (rs.next()) {

			

				act.setBrandname(rs.getString("brand_name"));
				act.setBrandstrength(rs.getString("brand_strength"));
				act.setLiquorsubCategory(rs.getString("sub_type"));
				act.setFor_(rs.getString("for_csd_civil"));
				
				if(rs.getString("forcsd_civilsubtype").equalsIgnoreCase("Y"))
				{
					act.setYesno("Yes");
					
				}
				else
				{
					act.setYesno("No");
				}
				//act.setYesno(rs.getString(""));
//System.out.println("==========sdfsfsfsdfsd2222222222------------"+act.getUnittype());

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

	

	//===================================================================

	public String getdata(
			brand_label_tracking_action act) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String path = null;

		try {
			con = ConnectionToDataBase.getConnection();

			String queryList = "select  bwflexisting_brand,coalesce(bwflbrand_licno,'NA')bwflbrand_licno,brand_id_pk, industry_type, licence_type, unit_id, licence_no, brand_registration_no, " +
					" brand_registration_date, brand_name, brand_strength, liquor_type, financial_year, finalize_flag," +
					"  created_date, created_by, modify_date, for_csd_civil, sub_type, domain, countryid, stateid, districid," +
					"  domain_id, domain_name from	brandlabel.brand_registration where brand_id_pk='"+act.getBrandid()+"'";

			pstmt = con.prepareStatement(queryList);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				 act.setUnittype(rs.getString("industry_type"));
				act.setUnit_id(rs.getInt("unit_id"));
				act.setLicencetype(rs.getString("licence_type"));

					
				 
				
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


	////////////////=============================================================

	// ====================reject application=========================

	public String rejectApplicationImpl(
			brand_label_tracking_action act) {

		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		String queryList = "";

		try {
			con = ConnectionToDataBase.getConnection();
			con.setAutoCommit(false);
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String time = sdf.format(cal.getTime());

			if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-Commissioner")) {

				queryList = " UPDATE brandlabel.brand_label_applications "
						+ " SET user5_time=?, user5_remark=?,   "
						+ " user5_date=?, vch_approved=?, vch_forwarded=?  WHERE app_id=?   ";

				pstmt = con.prepareStatement(queryList);
				saveStatus = 0;

				pstmt.setString(1, time);
				pstmt.setString(2, act.getFillRemrks());
				pstmt.setDate(3, Utility.convertUtilDateToSQLDate(new Date()));
				pstmt.setString(4, "REJECTED");
				pstmt.setString(5, "Rejected By Excise Commissioner");
				pstmt.setInt(6, act.getAppID());

				saveStatus = pstmt.executeUpdate();

			}

			if (saveStatus > 0) {
				con.commit();
				act.closeApplication();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								" Application Rejected !!! ",
								"Application Rejected !!!"));

			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error !!! ", "Error!!!"));

				con.rollback();

			}
		} catch (Exception se) {
			se.printStackTrace();

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return "";

	}

	// ------------------display label details in
	// datatable----------------------

	public ArrayList displayLabelDetailsImpl(brand_label_tracking_action act) 
	
	{

		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		int i = 1 ;
		String selQr = null;
		String filter = "";

		try {
			con = ConnectionToDataBase.getConnection();

		
				
			
			if (act.getRenew_new().equalsIgnoreCase("New"))
			{
				selQr = " SELECT(a.feeslable+a.feesbrand)as totalfee,0 as  unit_id,e.bwflexisting_brand,e.brand_name,coalesce(e.bwflbrand_licno,'NA')bwflbrand_licno,e.brand_strength as strength," +
					" COALESCE ( e.for_csd_civil,'NA') as for_csd_civil," +
					 " COALESCE (e.sub_type, 'NA') as sub_type," +
					//"  case when e.sub_type='0' then 'Other' when e.sub_type='1' then 'Economy' when e.sub_type='2' then 'Regular' when e.sub_type='3' then 'Medium' when e.sub_type='4' then 'Scotch' when e.sub_type='6' then 'Premium' when e.sub_type='7' then 'Super Premium' when e.sub_type='8' then 'Mild' when e.sub_type='9' then 'Strong' when e.sub_type='10' then 'Plain' when e.sub_type='11' then 'Masala' when e.sub_type='14' then 'Gim' when e.sub_type='15' then 'Vodka' when e.sub_type='16' then 'Wine' when e.sub_type='17' then 'Whisky' when e.sub_type='18' then 'Brandy' when e.sub_type='19' then 'Rum' when e.sub_type='21' then 'LAB' else 'NA' end as  sub_type," +
					" COALESCE (e.forcsd_civilsubtype,'NA') as forcsd_civilsubtype,a.feeslable,a.feesbrand,a.id, a.app_id, a.app_date, a.brand_id, a.brand_name, a.size, a.package_id,                                 "
					+ " CASE WHEN a.vch_approved='A' THEN 'Brand Approved'  "
					+ " WHEN a.vch_approved='R' THEN 'Brand Rejected'  "
					+ " WHEN a.vch_approved IS NULL THEN 'Pending' end as brand_status,  "
					//+ " a.package_type  ,  " 
					+"  a.quantity, a.fees, b.total_fees, b.total_no_of_labels,   b.domain_id, b.lic_cat,b.unit_type, b.renewed,a.brand_id,                                          "
					+ " CASE WHEN a.brand_name IS NULL THEN                                                                                "
					+ " (SELECT c.brand_name FROM brandlabel.brand_registration c WHERE a.brand_id=c.brand_id_pk)                          "
					+ " else a.brand_name end as brand,                                                                                    "
					//+ " CASE WHEN a.package_type IS NULL THEN                                                                              "
					//+ " (SELECT d.package_type FROM brandlabel.brand_registration_detail_packaging d WHERE a.package_id=d.package_id_pk)   "
					//+ " else a.package_type end as package                                                                                 "
					+" case when a.package_type='2' then 'CAN' when a.package_type='1' then 'Glass Bottle' when a.package_type='3' then 'Pet Bottle'                                                          "
					+" when a.package_type='6' then 'Keg' when a.package_type='5' then 'Sachet' when a.package_type='4' then 'Tetra Pack' else 'NA' end as package_type  "
				    + " FROM  brandlabel.brand_label_applications b ,brandlabel.brand_label_application_details a ,brandlabel.brand_registration  e                          "
					+ " WHERE a.app_id=b.app_id and a.brand_id=e.brand_id_pk AND a.app_id='"
					+ act.getAppID() + "' ORDER BY a.id ASC ";
			}
			
			else if (act.getRenew_new().equalsIgnoreCase("Renew"))
			{
				selQr = " SELECT (a.feeslable+a.feesbrand)as totalfee,b.unit_id,e.brand_id,'NO' as bwflexisting_brand,'' as bwflbrand_licno,e.brand_name,e.liquor_category,e.strength,e.liquor_type,COALESCE ( e.for_csd_civil,'NA') as for_csd_civil,'' as forcsd_civilsubtype, " +
						" COALESCE (e.sub_type, 'NA') as sub_type," +
						//" case when e.sub_type='0' then 'Other' when e.sub_type='1' then 'Economy' when e.sub_type='2' then 'Regular' when e.sub_type='3' then 'Medium' when e.sub_type='4' then 'Scotch' when e.sub_type='6' then 'Premium' when e.sub_type='7' then 'Super Premium' when e.sub_type='8' then 'Mild' when e.sub_type='9' then 'Strong' when e.sub_type='10' then 'Plain' when e.sub_type='11' then 'Masala' when e.sub_type='14' then 'Gim' when e.sub_type='15' then 'Vodka' when e.sub_type='16' then 'Wine' when e.sub_type='17' then 'Whisky' when e.sub_type='18' then 'Brandy' when e.sub_type='19' then 'Rum' when e.sub_type='21' then 'LAB' else 'NA' end as  sub_type," +
						" a.feeslable,a.feesbrand,a.id, a.app_id, a.app_date, a.brand_id, a.brand_name, a.size, a.package_id,                                 "
						+ " CASE WHEN a.vch_approved='A' THEN 'Brand Approved'  "
						+ " WHEN a.vch_approved='R' THEN 'Brand Rejected'  "
						+ " WHEN a.vch_approved IS NULL THEN 'Pending' end as brand_status,  "
						+" a.quantity, a.fees, b.total_fees, b.total_no_of_labels,   b.domain_id, b.lic_cat,b.unit_type, b.renewed,a.brand_id,                                        "
						+ " CASE WHEN a.brand_name IS NULL THEN                                                                                "
						+ " (SELECT c.brand_name FROM brandlabel.brand_registration c WHERE a.brand_id=c.brand_id_pk)                          "
						+ " else a.brand_name end as brand,                                                                                    "
						+" case when a.package_type='2' then 'CAN' when a.package_type='1' then 'Glass Bottle' when a.package_type='3' then 'Pet Bottle'                                                          "
						+" when a.package_type='6' then 'Keg' when a.package_type='5' then 'Sachet' when a.package_type='4' then 'Tetra Pack' else 'NA' end as package_type  "
						+ " FROM  brandlabel.brand_label_applications b ,brandlabel.brand_label_application_details a ,distillery.brand_registration_19_20  e                          "
						+ " WHERE a.app_id=b.app_id and a.brand_id=e.brand_id AND a.app_id='"
						+ act.getAppID() + "' ORDER BY a.id ASC ";
				
			}
			
			 
			ps = con.prepareStatement(selQr);
			 
			 rs = ps.executeQuery();
long totalfee=0;
			while (rs.next()) {

				brand_label_tracking_dt dt = new brand_label_tracking_dt();

				
				dt.setSrNo(i);
				dt.setBrndstrength(rs.getString("strength"));
				dt.setSubtype(rs.getString("sub_type"));
				dt.setForcivil(rs.getString("for_csd_civil"));

				dt.setLabelId_dt(rs.getInt("id"));
				dt.setBrandId_dt(rs.getInt("brand_id"));
				dt.setBrandName_dt(rs.getString("brand"));
				dt.setSize_dt(rs.getInt("size"));
				dt.setPckgID_dt(rs.getInt("package_id"));
				dt.setPckgType_dt(rs.getString("package_type"));
				dt.setNmbrOfLabels_dt(rs.getString("quantity"));
				act.setTotal_no_labels(rs.getInt("total_no_of_labels"));
			    totalfee+=rs.getDouble("totalfee");
				dt.setBrandStatus_dt(rs.getString("brand_status"));
				dt.setBrandid(rs.getInt("brand_id"));act.setBrandid(rs.getInt("brand_id"));
				if (rs.getString("brand_status").equalsIgnoreCase("Pending")) {
					act.setApproveDisblFlg(true);
				} else {
					act.setApproveDisblFlg(false);
				}
				if(rs.getString("forcsd_civilsubtype").equalsIgnoreCase("Y"))
				{
					dt.setYesno("Yes");
					
				}
				else if(rs.getString("forcsd_civilsubtype").equalsIgnoreCase("N"))
				{
					dt.setYesno("No");
				}
				else
				{
					dt.setYesno("Not Define");
				}
				
				dt.setSizeforenwal(rs.getString("bwflbrand_licno"));
				
				
				if(rs.getString("bwflbrand_licno")!=null && rs.getString("bwflbrand_licno").length()>0 && !rs.getString("bwflbrand_licno").equalsIgnoreCase("NA") ) {
					dt.setBrandnam(rs.getString("brand_name")+" - Bond already registered in 2019-20 with other parent Distillery/Brewery ( "+ rs.getString("bwflbrand_licno")+" )");
				}else 	if(rs.getString("bwflexisting_brand")!=null && rs.getString("bwflexisting_brand").equalsIgnoreCase("YES")) {
					dt.setBrandnam(rs.getString("brand_name")+" - Brand already registered and label is to be changed.");
				}else {
					dt.setBrandnam(rs.getString("brand_name"));
				}

				 
				
				i++;
				list.add(dt);
			}
			act.setTotal_fees(totalfee);
			
		} catch (Exception e) {
			e.printStackTrace();;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();;
			}
		}
		return list;

	}
	public Double getFeesLable(int domain, int num, String type,String unitType,int brand,boolean renew) {

		double fees = 0.0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String query = "";
			
			if(domain==1 && this.getcivilcsd(brand,  renew).equalsIgnoreCase("Civil") && (unitType.equalsIgnoreCase("D") || unitType.equalsIgnoreCase("brewery")   || unitType.equalsIgnoreCase("distillery") ||   unitType.equalsIgnoreCase("B")  ) )
			{
			 query = "SELECT  lableprice FROM brandlabel.brand_label_fees_master where id = '"
					+ domain + "' and upper(type) = '"+type+"' ";
			}else if(domain==1   && (  unitType.equalsIgnoreCase("bonds")   || unitType.equalsIgnoreCase("BWFL")) )
			{
				 query = "SELECT  lableprice FROM brandlabel.brand_label_fees_master where id = '"
						+ domain + "' and upper(type) = '"+type+"' ";
				}
			else if( this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil")  && (unitType.equalsIgnoreCase("IMPORTUNIT") ) )
			{
				 

				 query = "SELECT  lableprice FROM brandlabel.brand_label_fees_master where id = '"
						+ domain + "' and upper(type) = '"+type+"' ";
				
			}
			else if( !this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil")  && (unitType.equalsIgnoreCase("IMPORTUNIT") ) )
			{
				 

				 query = "SELECT  lableprice FROM brandlabel.brand_label_fees_master where id = '"
						+ domain + "' and upper(type) = '"+type+"' ";
				
			}
			/*else if( !this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil") && this.getcivilreg(brand).equalsIgnoreCase("Y")  )
			{
				 query = "SELECT distinct lableprice FROM brandlabel.brand_label_fees_master  where upper(type)='CSDNR'  ";
			
			}*//*else if( !this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil") && this.getcivilreg(brand).equalsIgnoreCase("N")  )
			{
				 query = "SELECT distinct lableprice FROM brandlabel.brand_label_fees_master where upper(type)='CSDNR'  ";
			
			}*/
			else if(domain==1 && !this.getcivilcsd(brand,  renew).equalsIgnoreCase("Civil") && (unitType.equalsIgnoreCase("D") || unitType.equalsIgnoreCase("brewery")   || unitType.equalsIgnoreCase("distillery") ||   unitType.equalsIgnoreCase("B")  ) )
			{
			 query = "SELECT  lableprice FROM brandlabel.brand_label_fees_master where id = '"
					+ domain + "' and upper(type) = '"+type+"' ";
			}
			else if( !this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil") && this.getcivilreg(brand).equalsIgnoreCase("Y")  )
			{
				 query = "SELECT distinct lableprice FROM brandlabel.brand_label_fees_master  where upper(type)='CSDNR'  ";
			
			}
			/*else if( !this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil")  && (unitType.equalsIgnoreCase("IMPORTUNIT") ) )
			{
				 

				 query = "SELECT  lableprice FROM brandlabel.brand_label_fees_master where id = '"
						+ domain + "' and upper(type) = '"+type+"' ";
				
			}*/
			else if(domain==2 ||  domain==3     )
			{
				 query = "SELECT  lableprice FROM brandlabel.brand_label_fees_master where id = '"
						+ domain + "' and upper(type) = '"+type+"' ";
				}
			conn = ConnectionToDataBase.getConnection();
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				fees = num * rs.getDouble("lableprice");
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
		return fees;
	}

	public Double getFeesBrand(int domain, int num, String type,String unitType,int brand,boolean renew) {

		double fees = 0.0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String query = "";
			
			if(domain==1 && this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil") && (unitType.equalsIgnoreCase("D") || unitType.equalsIgnoreCase("brewery") ||   unitType.equalsIgnoreCase("distillery") ||   unitType.equalsIgnoreCase("B")  ) )
			{
			 query = "SELECT brandprice FROM brandlabel.brand_label_fees_master where id = '"
					+ domain + "' and upper(type) = '"+type+"' ";
			}
			else if(domain==1   && (  unitType.equalsIgnoreCase("bonds")   || unitType.equalsIgnoreCase("BWFL")) )
			{
				 query = "SELECT  brandprice FROM brandlabel.brand_label_fees_master where id = '"
						+ domain + "' and upper(type) = '"+type+"' ";
				}
			else if( this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil")   && (unitType.equalsIgnoreCase("IMPORTUNIT") ) )
			{
				 
				query = "SELECT  brandprice FROM brandlabel.brand_label_fees_master where id = '"
						+ domain + "' and upper(type) = '"+type+"' ";
			}
			else if( !this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil")   && (unitType.equalsIgnoreCase("IMPORTUNIT") ) )
			{
				 
				query = "SELECT  brandprice FROM brandlabel.brand_label_fees_master where id = '"
						+ domain + "' and upper(type) = '"+type+"' ";
			}
			/*else if( !this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil") && this.getcivilreg(brand).equalsIgnoreCase("Y")  )
			{
				 query = "SELECT distinct  brandprice FROM brandlabel.brand_label_fees_master  where upper(type)='CSDR'  ";
			
			}*//*else if( !this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil") && this.getcivilreg(brand).equalsIgnoreCase("N")  )
			{
				 query = "SELECT distinct  brandprice FROM brandlabel.brand_label_fees_master   where upper(type)='CSDNR' ";
			
			}*/
			else if(domain==1 && !this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil") && (unitType.equalsIgnoreCase("D") || unitType.equalsIgnoreCase("brewery") ||   unitType.equalsIgnoreCase("distillery") ||   unitType.equalsIgnoreCase("B")  ) )
			{
				 query = "SELECT brandprice FROM brandlabel.brand_label_fees_master where id = '"
						+ domain + "' and upper(type) = '"+type+"' ";
				}
			else if( !this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil") && this.getcivilreg(brand).equalsIgnoreCase("Y")  )
			{
				 query = "SELECT distinct  brandprice FROM brandlabel.brand_label_fees_master  where upper(type)='CSDR'  ";
			
			}
				 
				/*else if( !this.getcivilcsd(brand,renew).equalsIgnoreCase("Civil")   && (unitType.equalsIgnoreCase("IMPORTUNIT") ) )
				{
					 
					query = "SELECT  brandprice FROM brandlabel.brand_label_fees_master where id = '"
							+ domain + "' and upper(type) = '"+type+"' ";
				}*/
			else if(domain==2 ||  domain==3     )
			{
				 query = "SELECT brandprice  FROM brandlabel.brand_label_fees_master where id = '"
						+ domain + "' and upper(type) = '"+type+"' ";
				}
			conn = ConnectionToDataBase.getConnection(); 
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				fees =   rs.getDouble("brandprice");
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
		return fees;
	}
	
	public String getcivilcsd(int a,boolean renew) {
		String id ="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";

		try {
			 if(renew==true){
				 query = "SELECT coalesce(for_csd_civil,'Civil')for_csd_civil FROM  distillery.brand_registration_19_20 where brand_id="+a+""; 
			 }else{
				 query = "SELECT coalesce(for_csd_civil,'Civil')for_csd_civil FROM brandlabel.brand_registration where brand_id_pk="+a+"";	 
			 }
				
		 	conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				id = rs.getString("for_csd_civil");
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
		return id;
	}
	public String getcivilreg(int a) {
		String id ="";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";

		try {
			 
				query = "SELECT coalesce(forCSD_Civilsubtype,'N')forCSD_Civilsubtype FROM brandlabel.brand_registration where brand_id_pk="+a+"";
		 	conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				id = rs.getString("forCSD_Civilsubtype");
			}else {
				id="N";
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
		return id;
	}
	// =====================popup to show uploaded
	// labels========================

	public ArrayList getUploadedLabels(
			brand_label_tracking_action act) {

		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 1;
String selQr="";
		if(act.getRenew_new()!=null)
		{
		if (act.getRenew_new().equalsIgnoreCase("New")) 
		{
			selQr = " SELECT d.size,a.id, a.app_id, a.brand_id, a.package_id, a.description, a.img, a.cr_date, "
					+ " (SELECT c.brand_name FROM brandlabel.brand_registration c WHERE a.brand_id=c.brand_id_pk) as brand_name, " 
					+ " COALESCE(vch_affidavit,'/doc/ExciseUp/LabelRegistration/pdf/') as vch_affidavit , "
					+ " COALESCE(approved_br , '/doc/ExciseUp/LabelRegistration/pdf/') as approved_br,  "
					+ " COALESCE(manual_reciept , '/doc/ExciseUp/LabelRegistration/pdf/') as manual_reciept "
					+ " FROM brandlabel.brand_label_uploading a , brandlabel.brand_label_application_details d"
					+ " WHERE a.app_id='"
					+ act.getAppID()
					+ "'  and a.app_id=d.app_id  and a.brand_id=d.brand_id and a.package_id=d.package_id ORDER BY d.size ,a.brand_id ";

		} else if (act.getRenew_new().equalsIgnoreCase("Renew")) {

			selQr = " SELECT d.size,a.id, a.app_id, a.brand_id, a.package_id, a.description, a.img, a.cr_date, "
					+ " (SELECT c.brand_name FROM distillery.brand_registration_19_20 c WHERE a.brand_id=c.brand_id) as brand_name, "
					+ " COALESCE(vch_affidavit,'/doc/ExciseUp/LabelRegistration/pdf/') as vch_affidavit ,  COALESCE(approved_br , "
					+ " '/doc/ExciseUp/LabelRegistration/pdf/') as approved_br,   COALESCE(manual_reciept ,"
					+ "   '/doc/ExciseUp/LabelRegistration/pdf/') as manual_reciept " +
					"  FROM brandlabel.brand_label_uploading a , brandlabel.brand_label_application_details d "
					+ " WHERE a.app_id='"
					+ act.getAppID()
					+ "' and a.app_id=d.app_id  and a.brand_id=d.brand_id and a.package_id=d.package_id ORDER BY d.size ,a.brand_id ";

		} else {
			selQr = "";
		}
		}
		else
		{
			//selQr = "";
		}
 try {
			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(selQr);
 
			// System.out.println("============getAppID----"+ac.getAppID());
			// System.out.println("------------getRegID-------"+ac.getRegID());

			rs = ps.executeQuery();

			while (rs.next()) {

				brand_label_tracking_dt dt = new brand_label_tracking_dt();

				dt.setShowUploadedSrNo(i);
				dt.setShowUploadedId(rs.getInt("id"));
				dt.setShowUploadedDescription(rs.getString("description"));
				dt.setShowUploadedImage(rs.getString("img"));
				dt.setShowUploadedAffidavit(rs.getString("vch_affidavit"));
				dt.setShowUploadedBR(rs.getString("approved_br"));
				dt.setShowUploadedManualRcpt(rs.getString("manual_reciept"));
				dt.setShowUploadedBrand(rs.getString("brand_name"));
				dt.setShowsize(rs.getString("size"));

				/*
				 * act.setShowUploadedAffidavit(rs.getString("vch_affidavit"));
				 * act.setShowUploadedBR(rs.getString("approved_br"));
				 * act.setShowUploadedManualRcpt
				 * (rs.getString("manual_reciept"));
				 */

				list.add(dt);

				i++;
			}

		} catch (Exception e) {
			e.getMessage();

		} finally {
			try {
				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.getMessage();
			}
		}
		return list;

	}

	// =====================get objection history on popup3===================

	public ArrayList getObjectionHistory(
			brand_label_tracking_action act) {

		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 1;

		String query = " SELECT app_id, objection_id, objection_title, objection_description, "
				+ " objection_time, objection_date, reply_on_objection, uploaded_file, reply_date, "
				+ " reply_time, objected_by, CONCAT(objection_title,'_',objection_description) as description  "
				+ " FROM brandlabel.brand_label_objection WHERE app_id='"
				+ act.getAppID() + "'   ";

		try {
			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(query);

		//	System.out.println("objection history---------------" + query);

			rs = ps.executeQuery();

			String path = "";

			while (rs.next()) {

				brand_label_tracking_dt dt = new brand_label_tracking_dt();

				// path =
				// "/doc/ExciseUp/Applications/appUpload/objection_DW/"+"DW_"+rs.getInt("reg_id")+"_"+rs.getInt("app_id")+"_"+rs.getInt("objection_id")+".pdf";

				dt.setShowUploadedSrNo(i);
				dt.setObject_by(rs.getString("objected_by"));
				dt.setDescription(rs.getString("description"));
				dt.setObject_date(rs.getDate("objection_date"));
				dt.setAction_taken(rs.getString("reply_on_objection"));
				dt.setAction_dt(rs.getDate("reply_date"));

				if (rs.getString("uploaded_file") != null) {

					dt.setUpload_objected_docs(rs.getString("uploaded_file"));
					dt.setUpload_objected_flag(true);

				} else {
					dt.setUpload_objected_flag(false);
				}

				list.add(dt);

				i++;
			}

		} catch (Exception e) {
			// e.printStackTrace();

		} finally {
			try {
				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return list;

	}

	// ======================get max id=============================

	public int getMaxId() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = " SELECT max(objection_id)as id  FROM brandlabel.brand_label_objection ";

		int maxid = 0;
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				maxid = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return maxid + 1;

	}

	// ======================raise objection===================================

	public void save_Objection(brand_label_tracking_action act) {

		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String queryList = "";

		int id = this.getMaxId();

		try {

			con = ConnectionToDataBase.getConnection();
			con.setAutoCommit(false);
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String time = sdf.format(cal.getTime());

			queryList = " INSERT INTO brandlabel.brand_label_objection( "
					+ " app_id, objection_id, objection_title, objection_description, objection_time, "
					+ " objection_date, objected_by) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

			pstmt = con.prepareStatement(queryList);

			saveStatus = 0;

			pstmt.setInt(1, act.getAppID());
			pstmt.setInt(2, id);
			pstmt.setString(3, act.getObjection_for());
			pstmt.setString(4, act.getObj_Description());
			pstmt.setString(5, time);
			pstmt.setDate(6, Utility.convertUtilDateToSQLDate(new Date()));
			pstmt.setString(7, ResourceUtil.getUserNameAllReq().trim());

			saveStatus = pstmt.executeUpdate();

			if (saveStatus > 0) {

				saveStatus = 0;

				String queryUpdate = " UPDATE brandlabel.brand_label_applications set objection_flag='O' ,vch_forwarded=? "
						+ " WHERE app_id=?";

				pstmt = con.prepareStatement(queryUpdate);

				saveStatus = 0;

				pstmt.setString(1, "Objection Raised By "
						+ ResourceUtil.getUserNameAllReq().trim());
				pstmt.setInt(2, act.getAppID());

				saveStatus = pstmt.executeUpdate();

			}

			if (saveStatus > 0) {
				con.commit();
				act.closeApplication();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Objection Raised Successfully ",
								"Objection Raised Successfully "));

			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error !!! ", "Error!!!"));

				con.rollback();

			}
		} catch (Exception se) {
			se.printStackTrace();

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception se) {
				se.printStackTrace();
			}
		}

	}

	// =======================get details on popup4===========================

	public String getObjectionReplies(
			brand_label_tracking_action act) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String path = null;

		try {
			con = ConnectionToDataBase.getConnection();

			String queryList = " SELECT  app_id, objection_id, objection_title, objection_description, "
					+ " reply_on_objection, uploaded_file, "
					+ " objection_title || '(Description:)' || objection_description as objected_for "
					+ " FROM brandlabel.brand_label_objection  "
					+ " WHERE app_id="
					+ act.getAppID()
					+ "   "
					+ " AND objection_id=(SELECT max(objection_id) FROM brandlabel.brand_label_objection "
					+ " WHERE app_id=" + act.getAppID() + ")  ";

			pstmt = con.prepareStatement(queryList);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				// path="//ExciseUp/Applications/appUpload/objection_DW/"+"DW_"+rs.getInt("reg_id")+"_"+rs.getInt("app_id")+"_"+rs.getInt("objection_id")+".pdf";

				// System.out.println("path---------------------------"+path);

				act.setPopup4ObjectedFor(rs.getString("objection_title"));
				act.setPopup4ActionTaken(rs.getString("reply_on_objection"));
				act.setPopup4objID(rs.getInt("objection_id"));
				if (rs.getString("uploaded_file") != null) {
					act.setPopup4ObjectedPdf(rs.getString("uploaded_file"));
					act.setViewpdfFlg(true);
				} else {
					act.setViewpdfFlg(false);
				}

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

	// =====================accept reply================================

	public void agreeReplyImpl(brand_label_tracking_action act) {

		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String queryList = "";

		try {

			con = ConnectionToDataBase.getConnection();
			con.setAutoCommit(false);

			queryList = " UPDATE brandlabel.brand_label_applications SET objection_flag='A', vch_forwarded=? "
					+ " WHERE app_id=? ";

			pstmt = con.prepareStatement(queryList);

			saveStatus = 0;

			pstmt.setString(1, "Objection Viewed & ACCEPTED By "
					+ ResourceUtil.getUserNameAllReq().trim());
			pstmt.setInt(2, act.getAppID());

			// System.out.println("--------------"+queryList);

			saveStatus = pstmt.executeUpdate();

			if (saveStatus > 0) {
				con.commit();
				act.closeApplication();

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Objection Removed ", "Objection Removed"));

			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error !!! ", "Error!!!"));

				con.rollback();

			}
		} catch (Exception se) {
			se.printStackTrace();

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception se) {
				se.printStackTrace();
			}
		}

	}

	// =====================decline reply================================

	public void declineReplyImpl(brand_label_tracking_action act) {

		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String queryList = "";

		try {

			con = ConnectionToDataBase.getConnection();
			con.setAutoCommit(false);

			queryList = " UPDATE brandlabel.brand_label_applications SET objection_flag='D', vch_forwarded=? "
					+ " WHERE app_id=? ";

			pstmt = con.prepareStatement(queryList);

			saveStatus = 0;

			pstmt.setString(1, "Objection Viewed & DECLINED By "
					+ ResourceUtil.getUserNameAllReq().trim());
			pstmt.setInt(2, act.getAppID());

			// System.out.println("--------------"+queryList);

			saveStatus = pstmt.executeUpdate();

			if (saveStatus > 0) {
				con.commit();
				act.closeApplication();

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Decline For Reply ", "Decline For Reply"));

			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error !!! ", "Error!!!"));

				con.rollback();

			}
		} catch (Exception se) {
			se.printStackTrace();

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception se) {
				se.printStackTrace();
			}
		}

	}

	public String getDocuments(brand_label_tracking_action act) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String path = null;

		try {
			con = ConnectionToDataBase.getConnection();

			String queryList = " SELECT  app_id, objection_id, objection_title, objection_description, "
					+ " reply_on_objection, uploaded_file, "
					+ " objection_title || '(Description:)' || objection_description as objected_for "
					+ " FROM brandlabel.brand_label_objection  "
					+ " WHERE app_id="
					+ act.getAppID()
					+ "   "
					+ " AND objection_id=(SELECT max(objection_id) FROM brandlabel.brand_label_objection "
					+ " WHERE app_id=" + act.getAppID() + ")  ";

			pstmt = con.prepareStatement(queryList);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				// path="//ExciseUp/Applications/appUpload/objection_DW/"+"DW_"+rs.getInt("reg_id")+"_"+rs.getInt("app_id")+"_"+rs.getInt("objection_id")+".pdf";

				// System.out.println("path---------------------------"+path);

				act.setPopup4ObjectedFor(rs.getString("objection_title"));
				act.setPopup4ActionTaken(rs.getString("reply_on_objection"));
				act.setPopup4objID(rs.getInt("objection_id"));
				if (rs.getString("uploaded_file") != null) {
					act.setPopup4ObjectedPdf(rs.getString("uploaded_file"));
					act.setViewpdfFlg(true);
				} else {
					act.setViewpdfFlg(false);
				}

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

	public String licdate(brand_label_tracking_action act, int id) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String queryList = "";

		try {

			con = ConnectionToDataBase.getConnection();
			con.setAutoCommit(false);

			queryList = " UPDATE brandlabel.brand_label_applications SET licence_date='"
					+ Utility.convertUtilDateToSQLDate(new Date())
					+ "',"
					+ " licence_time='"
					+ dateFormat.format(Utility
							.convertUtilDateToSQLDate(new Date()))
							+ "' "
							+ " WHERE app_id='" + id + "' ";

			pstmt = con.prepareStatement(queryList);

			saveStatus = 0;

			//System.out.println("--------------" + queryList);

			saveStatus = pstmt.executeUpdate();

			if (saveStatus > 0) {
				con.commit();

			} else {
				con.rollback();
			}

		} catch (Exception se) {
			se.printStackTrace();

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception se) {
				se.printStackTrace();
			}
		}
		return "";

	}

	/*public String printReport(brand_label_tracking_action act,String unitName,
			String unitAddress,String unitType,String userDomain,int id,String liccat,String district ,brand_label_tracking_dt dt) {
		String mypath=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH;
		
		
		String relativePath=mypath+File.separator+"ExciseUp"+File.separator+"Applications"+File.separator+"jasper";
		String relativePathpdf=mypath+File.separator+"ExciseUp"+File.separator+"Applications"+File.separator+"pdf";
		
		
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		try {
			con =ConnectionToDataBase.getConnection();
			 if(unitType.equalsIgnoreCase("BWFL") && dt.getRenew_new().equalsIgnoreCase("New")) {
				 reportQuery="SELECT distinct a.app_id,a.brand_id, a.brand_name,c.vch_license_no, a.liquor_category, d.approval_nmbr,e.description as label_desc,                                             "+
							" c.user3_date,a.approvaldt, a.strength, a.liquor_type, a.license_category, d.vch_brand_approval_no ,                                                                                    "+
							" d.vch_package_approval_no,a.vch_license_type, a.license_number, a.unit_name,  coalesce(a.for_csd_civil,'') as unit_type, a.brand_registration_no, a.domain, " +
							 " a.unit_nm,  a.renewal_flg,b.package_name,e.id as labelid,to_char(c.app_date, 'dd-MM-yy') as app_date,                                                                                  "+
							" CASE WHEN domain='EXP' THEN  'Sale OutSide U.P.' else 'Sale in U.P.' end as sale_type,  " +
							 "  (select description from distillery.liscence_category where  id::text=a.sub_type)   as sub_type,"+
							" case when b.package_type='2' then 'CAN' when b.package_type='1' then 'Glass Bottle' when b.package_type='3' then 'Pet Bottle'                                                          "+
							" when b.package_type='6' then 'Keg' when b.package_type='5' then 'Sachet' when b.package_type='4' then 'Tetra Pack' end as package_type                                                 "+
							" FROM   distillery.brand_registration_temp a  left join  distillery.liscence_category f on f.id::text=a.sub_type ,distillery.packaging_details_temp b ,                                                                     "+
							" brandlabel.brand_label_uploading e  ,brandlabel.brand_label_applications c  ,brandlabel.brand_label_application_details d                       					                     "+
							" WHERE  a.app_id='"+id+"' and a.brand_id=b.brand_id_fk and c.app_id=a.app_id and e.package_id=d.package_id                                                            "+
							" and e.app_id=a.app_id  and a.app_id=d.app_id and d.size=b.quantity and b.old_pckid=d.package_id and a.old_brandid=d.brand_id order by  a.brand_name,b.package_name,e.id                                       union                            "+
							"SELECT distinct a.app_id,a.brand_id, a.brand_name,c.vch_license_no, a.liquor_category, d.approval_nmbr,e.description as label_desc,                                             "+
							" c.user3_date,a.approvaldt, a.strength, a.liquor_type, a.license_category, d.vch_brand_approval_no ,                                                                                    "+
							" d.vch_package_approval_no,a.vch_license_type, a.license_number, a.unit_name,  coalesce(a.for_csd_civil,'') as unit_type, a.brand_registration_no, a.domain, " +
							 " a.unit_nm,  a.renewal_flg,b.package_name,e.id as labelid,to_char(c.app_date, 'dd-MM-yy') as app_date,                                                                                  "+
							" CASE WHEN domain='EXP' THEN  'Sale OutSide U.P.' else 'Sale in U.P.' end as sale_type,  " +
							 "  (select description from distillery.liscence_category where  id::text=a.sub_type)   as sub_type,"+
							" case when b.package_type='2' then 'CAN' when b.package_type='1' then 'Glass Bottle' when b.package_type='3' then 'Pet Bottle'                                                          "+
							" when b.package_type='6' then 'Keg' when b.package_type='5' then 'Sachet' when b.package_type='4' then 'Tetra Pack' end as package_type                                                 "+
							" FROM   distillery.brand_registration_20_21 a  left join  distillery.liscence_category f on f.id::text=a.sub_type ,distillery.packaging_details_20_21 b ,                                                                     "+
							" brandlabel.brand_label_uploading e  ,brandlabel.brand_label_applications c  ,brandlabel.brand_label_application_details d                       					                     "+
							" WHERE  a.app_id='"+id+"' and a.brand_id=b.brand_id_fk and c.app_id=a.app_id and e.package_id=d.package_id                                                            "+
							" and e.app_id=a.app_id  and a.app_id=d.app_id and d.size=b.quantity and b.old_pckid=d.package_id and a.old_brandid=d.brand_id order by  a.brand_name,b.package_name,e.id                                                                              ";
			 }else {
			 
				reportQuery="SELECT distinct a.app_id,a.brand_id, a.brand_name,c.vch_license_no, a.liquor_category, d.approval_nmbr,e.description as label_desc,                                             "+
					" c.user3_date,a.approvaldt, a.strength, a.liquor_type, a.license_category, d.vch_brand_approval_no ,                                                                                    "+
					" d.vch_package_approval_no,a.vch_license_type, a.license_number, a.unit_name,  coalesce(a.for_csd_civil,'') as unit_type, a.brand_registration_no, a.domain, " +
					 " a.unit_nm,  a.renewal_flg,b.package_name,e.id as labelid,to_char(c.app_date, 'dd-MM-yy') as app_date,                                                                                  "+
					" CASE WHEN domain='EXP' THEN  'Sale OutSide U.P.' else 'Sale in U.P.' end as sale_type,  " +
					 "  (select description from distillery.liscence_category where  id::text=a.sub_type)   as sub_type,"+
					" case when b.package_type='2' then 'CAN' when b.package_type='1' then 'Glass Bottle' when b.package_type='3' then 'Pet Bottle'                                                          "+
					" when b.package_type='6' then 'Keg' when b.package_type='5' then 'Sachet' when b.package_type='4' then 'Tetra Pack' end as package_type                                                 "+
					" FROM   distillery.brand_registration_20_21 a  left join  distillery.liscence_category f on f.id::text=a.sub_type ,distillery.packaging_details_20_21 b ,                                                                     "+
					" brandlabel.brand_label_uploading e  ,brandlabel.brand_label_applications c  ,brandlabel.brand_label_application_details d                       					                     "+
					" WHERE  a.app_id='"+id+"' and a.brand_id=b.brand_id_fk and c.app_id=a.app_id and e.package_id=d.package_id                                                            "+
					" and e.app_id=a.app_id  and a.app_id=d.app_id and d.size=b.quantity and b.old_pckid=d.package_id and a.old_brandid=d.brand_id order by  a.brand_name,b.package_name,e.id                                                                              ";
//System.out.println("=====reportQuery-----"+reportQuery);
			 }
			 pst=con.prepareStatement(reportQuery);
			rs=pst.executeQuery();
			
			if(rs.next())
			{
				rs=pst.executeQuery();
			Map parameters = new HashMap();
			parameters.put("REPORT_CONNECTION", con);
			parameters.put("SUBREPORT_DIR", relativePath+File.separator);
			parameters.put("image", relativePath+File.separator);
			parameters.put("unitName",unitName.replace("\n", " ") );
			parameters.put("unitAddress",unitAddress.replace("\n", " ") );
			parameters.put("unitType",unitType );
			parameters.put("userDomain",userDomain );
			parameters.put("liquorCategory",liccat );
			
			if(unitType.equalsIgnoreCase("Distillery") || unitType.equalsIgnoreCase("Brewery")){
			
			parameters.put("a","AEC_" );
			parameters.put("b", unitName);
			}else if(unitType.equalsIgnoreCase("BWFL")){
				parameters.put("a","DEO_" );
				parameters.put("b",district );
			}else
				//if(unitType.equalsIgnoreCase("Other Unit"))
				{
				parameters.put("a","DEC_" );
				parameters.put("b","Licensing" );
			}
			 
			JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
			
			
			
			if(liccat.equalsIgnoreCase("CL")){
				//System.out.println("lic cat  "+liccat);
			jasperReport = (JasperReport) JRLoader.loadObject(relativePath+File.separator+"ApprovedLicensingForCL.jasper");
			}else{
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+File.separator+"ApprovedLicensing.jasper");
			}
			
			JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);	
			  Random rand = new Random();
        	  int  n = rand.nextInt(250) + 1;

			JasperExportManager.exportReportToPdfFile(print,relativePathpdf+File.separator+id+"_ApprovedLicensing.pdf");
			dt.setPdfname(id+"_ApprovedLicensing.pdf");
			//System.out.println(""+dt.getPdfname());
			//act.setDisplayRegUsers(displayRegUsersImpl(act));
			
		//	dt.setPrintFlag(true);
			this.licdate(act, id);
			
			 
		}else
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found")); 	
			//dt.setPrintFlag(false);
		}
		
			
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return "F";


	}*/
	public String printReport(brand_label_tracking_action act,String unitName,
			String unitAddress,String unitType,String userDomain,int id,String liccat,String district ,brand_label_tracking_dt dt) {
		String mypath=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH;
		
		
		String relativePath=mypath+File.separator+"ExciseUp"+File.separator+"Applications"+File.separator+"jasper";
		String relativePathpdf=mypath+File.separator+"ExciseUp"+File.separator+"Applications"+File.separator+"pdf";
		
		
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;ResultSet rs1 = null;
		String reportQuery ,reportQuery1= null;
		try {
			con =ConnectionToDataBase.getConnection();
			/// if(unitType.equalsIgnoreCase("BWFL") && dt.getRenew_new().equalsIgnoreCase("New")) {
			 if(unitType.equalsIgnoreCase("BWFL")) {
				 
				 if (dt.getRenew_new().equalsIgnoreCase("Renew")) {
					 reportQuery="  SELECT distinct c.app_id,a.brand_id, a.brand_name,c.vch_license_no, a.liquor_category, d.approval_nmbr,e.description as label_desc,                                             \r\n" + 
					 		"c.user3_date,a.approvaldt, a.strength, a.liquor_type, a.license_category, d.vch_brand_approval_no ,                                                                                    \r\n" + 
					 		"d.vch_package_approval_no,a.vch_license_type, a.license_number, a.unit_name,  coalesce(a.for_csd_civil,'') as unit_type, a.brand_registration_no, a.domain,  \r\n" + 
					 		" a.unit_nm,  a.renewal_flg,b.package_name,e.id as labelid,to_char(c.app_date, 'dd-MM-yy') as app_date,                                                                                  \r\n" + 
					 		"CASE WHEN domain='EXP' THEN  'Sale OutSide U.P.' else 'Sale in U.P.' end as sale_type,   \r\n" + 
					 		"  (select description from distillery.liscence_category where  id::text=a.sub_type)   as sub_type,\r\n" + 
					 		"case when b.package_type='2' then 'CAN' when b.package_type='1' then 'Glass Bottle' when b.package_type='3' then 'Pet Bottle'                                                          \r\n" + 
					 		"when b.package_type='6' then 'Keg' when b.package_type='5' then 'Sachet' when b.package_type='4' then 'Tetra Pack' end as package_type                                                 \r\n" + 
					 		"FROM   distillery.brand_registration_19_20 a  left join  distillery.liscence_category f on f.id::text=a.sub_type ,distillery.packaging_details_19_20 b ,                                                                     \r\n" + 
					 		"brandlabel.brand_label_uploading e  ,brandlabel.brand_label_applications c  ,brandlabel.brand_label_application_details d                                           \r\n" + 
					 		"WHERE   a.brand_id in (select distinct brand_id from brandlabel.brand_label_application_details where app_id='"+id+"' )   and a.brand_id=b.brand_id_fk   and e.package_id=d.package_id  and b.package_id =d.package_id    and c.app_id='"+id+"'                                                      \r\n" + 
					 		"and e.app_id=c.app_id  and c.app_id=d.app_id and d.size=b.quantity  order by  a.brand_name,b.package_name,e.id     ";
				 }else {

				 reportQuery=" select distinct a.licence_type as vch_license_type,f.lic_type as license_category ,f.vch_license_no,a.brand_strength as strength,f.user3_date as approvaldt, a.brand_registration_no,f.user3_date,c.approval_nmbr,0 as liquor_category ,a.liquor_type, f.app_id, a.domain_name as sale_type, to_char(f.app_date, 'dd-MM-yy') as app_date" +
				 		     " ,a.brand_name,a.brand_strength as strength,size_ml as package_name,a.brand_id_pk as brand_id," +
				 		     " case when b.package_type='2' then 'CAN' when b.package_type='1' then 'Glass Bottle' when b.package_type='3' then 'Pet Bottle'                                                          " +
				 		     " when b.package_type='6' then 'Keg' when b.package_type='5' then 'Sachet' when b.package_type='4' then 'Tetra Pack' end as package_type," +
				 		     " (select description from distillery.liscence_category where  id::text=a.sub_type)   as sub_type,a.for_csd_civil as unit_type," +
				 		     " c.vch_brand_approval_no ,c.vch_package_approval_no,d.description as label_desc,d.id as labelid" +
				 		     " from brandlabel.brand_registration a,   brandlabel.brand_registration_detail_packaging b," +
				 		     " brandlabel.brand_label_application_details c,brandlabel.brand_label_uploading d,brandlabel.brand_label_applications f" +
				 		     " where f.app_id='"+id+"' and a.brand_id_pk=b.brand_id_fk  and a.unit_id=f.unit_id   " + //and a.created_by=f.username
				 		     " and a.brand_id_pk=d.brand_id and b.package_id_pk=c.package_id  and f.app_id=c.app_id " +
				 		     " and b.brand_id_fk=c.brand_id and c.size=b.size_ml and  d.app_id=f.app_id and d.brand_id=c.brand_id and d.package_id=c.package_id " +
				 		     " order by  a.brand_name,package_name,labelid";

				 } 
				
			 
			 }else {
			 
				reportQuery="SELECT distinct a.app_id,a.brand_id, a.brand_name,c.vch_license_no, a.liquor_category, d.approval_nmbr,e.description as label_desc,                                             "+
					" c.user3_date,a.approvaldt, a.strength, a.liquor_type, a.license_category, d.vch_brand_approval_no ,                                                                                    "+
					" d.vch_package_approval_no,a.vch_license_type, a.license_number, a.unit_name,  coalesce(a.for_csd_civil,'') as unit_type, a.brand_registration_no, a.domain, " +
					 " a.unit_nm,  a.renewal_flg,b.package_name,e.id as labelid,to_char(c.app_date, 'dd-MM-yy') as app_date,                                                                                  "+
					///" CASE WHEN domain='EXP' THEN  'Sale OutSide U.P.' else 'Sale in U.P.' end as sale_type,  " +
					"  case when domain='EXP'  then 'Sale OutSide U.P.' when domain='Export(Other State)' then 'Sale OutSide U.P.' when domain='Within UP' then 'Sale in U.P.' else domain end as sale_type , " +
					 "  (select description from distillery.liscence_category where  id::text=a.sub_type)   as sub_type,"+
					" case when b.package_type='2' then 'CAN' when b.package_type='1' then 'Glass Bottle' when b.package_type='3' then 'Pet Bottle'                                                          "+
					" when b.package_type='6' then 'Keg' when b.package_type='5' then 'Sachet' when b.package_type='4' then 'Tetra Pack' end as package_type                                                 "+
					" FROM   distillery.brand_registration_20_21 a  left join  distillery.liscence_category f on f.id::text=a.sub_type ,distillery.packaging_details_20_21 b ,                                                                     "+
					" brandlabel.brand_label_uploading e  ,brandlabel.brand_label_applications c  ,brandlabel.brand_label_application_details d                       					                     "+
					" WHERE  a.app_id='"+id+"' and a.brand_id=b.brand_id_fk and c.app_id=a.app_id and e.package_id=d.package_id                                                            "+
					" and e.app_id=a.app_id  and a.app_id=d.app_id and d.size=b.quantity and b.old_pckid=d.package_id and a.old_brandid=d.brand_id order by  a.brand_name,b.package_name,e.id                                                                              ";

			 }
			 pst=con.prepareStatement(reportQuery);
			rs=pst.executeQuery();
			
			if(rs.next())
			{
				rs=pst.executeQuery();
			Map parameters = new HashMap();
			parameters.put("REPORT_CONNECTION", con);
			parameters.put("SUBREPORT_DIR", relativePath+File.separator);
			parameters.put("image", relativePath+File.separator);
			parameters.put("unitName",unitName.replace("\n", " ") );
			if(unitAddress!=null && unitAddress.length()>0) {
			parameters.put("unitAddress",unitAddress.replace("\n", " ") );
			}else {
				parameters.put("unitAddress","NA" );
			}
			parameters.put("unitType",unitType );
			parameters.put("userDomain",userDomain );
			parameters.put("liquorCategory",liccat );
			
			if(unitType.equalsIgnoreCase("Distillery") || unitType.equalsIgnoreCase("Brewery")){
			
			parameters.put("a","AEC_" );
			parameters.put("b", unitName);
			}else if(unitType.equalsIgnoreCase("BWFL")){
				parameters.put("a","DEO_" );
				parameters.put("b",district );
			}else
				//if(unitType.equalsIgnoreCase("Other Unit"))
				{
				parameters.put("a","DEC_" );
				parameters.put("b","Licensing" );
			}
			 
			JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
			
			if(unitType.equalsIgnoreCase("BWFL") &&  (!dt.getRenew_new().equalsIgnoreCase("Renew")) ){
				if(liccat.equalsIgnoreCase("CL")){
					
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+File.separator+"ApprovedLicensingBWFLForCL.jasper");
				}else{
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath+File.separator+"ApprovedLicensingBWFL.jasper");
				}
				
			}
			else{
			if(liccat.equalsIgnoreCase("CL")){
				//System.out.println("lic cat  "+liccat);
			jasperReport = (JasperReport) JRLoader.loadObject(relativePath+File.separator+"ApprovedLicensingForCL.jasper");
			}else{
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+File.separator+"ApprovedLicensing.jasper");
			}
			}
			JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);	
			  Random rand = new Random();
        	  int  n = rand.nextInt(250) + 1;

			JasperExportManager.exportReportToPdfFile(print,relativePathpdf+File.separator+id+"_ApprovedLicensing.pdf");
			dt.setPdfname(id+"_ApprovedLicensing.pdf");
			//System.out.println(""+dt.getPdfname());
			//act.setDisplayRegUsers(displayRegUsersImpl(act));
			
		//	dt.setPrintFlag(true);
			this.licdate(act, id);
			
			 
		}else
		{
			
			
			 reportQuery1=" select distinct a.licence_type as vch_license_type,f.lic_type as license_category ,f.vch_license_no,a.brand_strength as strength,f.user3_date as approvaldt, a.brand_registration_no,f.user3_date,c.approval_nmbr,0 as liquor_category ,a.liquor_type, f.app_id, a.domain_name as sale_type, to_char(f.app_date, 'dd-MM-yy') as app_date" +
		 		     " ,a.brand_name,a.brand_strength as strength,size_ml as package_name,a.brand_id_pk as brand_id," +
		 		     " case when b.package_type='2' then 'CAN' when b.package_type='1' then 'Glass Bottle' when b.package_type='3' then 'Pet Bottle'                                                          " +
		 		     " when b.package_type='6' then 'Keg' when b.package_type='5' then 'Sachet' when b.package_type='4' then 'Tetra Pack' end as package_type," +
		 		     " (select description from distillery.liscence_category where  id::text=a.sub_type)   as sub_type,a.for_csd_civil as unit_type," +
		 		     " c.vch_brand_approval_no ,c.vch_package_approval_no,d.description as label_desc,d.id as labelid" +
		 		     " from brandlabel.brand_registration a,   brandlabel.brand_registration_detail_packaging b," +
		 		     " brandlabel.brand_label_application_details c,brandlabel.brand_label_uploading d,brandlabel.brand_label_applications f" +
		 		     " where f.app_id='"+id+"' and a.brand_id_pk=b.brand_id_fk and a.created_by=f.username and a.unit_id=f.unit_id   " +
		 		     " and a.brand_id_pk=d.brand_id and b.package_id_pk=c.package_id  and f.app_id=c.app_id " +
		 		     " and b.brand_id_fk=c.brand_id and c.size=b.size_ml and  d.app_id=f.app_id and d.brand_id=c.brand_id and d.package_id=c.package_id " +
		 		     " order by  a.brand_name,package_name,labelid";
			 pst=con.prepareStatement(reportQuery1);
			rs1=pst.executeQuery();
			if(rs1.next())
			{rs1=pst.executeQuery();
		Map parameters1 = new HashMap();
		parameters1.put("REPORT_CONNECTION", con);
		parameters1.put("SUBREPORT_DIR", relativePath+File.separator);
		parameters1.put("image", relativePath+File.separator);
		parameters1.put("unitName",unitName.replace("\n", " ") );
		if(unitAddress!=null && unitAddress.length()>0) {
		parameters1.put("unitAddress",unitAddress.replace("\n", " ") );
		}else {
			parameters1.put("unitAddress","NA" );
		}
		parameters1.put("unitType",unitType );
		parameters1.put("userDomain",userDomain );
		parameters1.put("liquorCategory",liccat );
		
		if(unitType.equalsIgnoreCase("Distillery") || unitType.equalsIgnoreCase("Brewery")){
		
		parameters1.put("a","AEC_" );
		parameters1.put("b", unitName);
		}else if(unitType.equalsIgnoreCase("BWFL")){
			parameters1.put("a","DEO_" );
			parameters1.put("b",district );
		}else
			//if(unitType.equalsIgnoreCase("Other Unit"))
			{
			parameters1.put("a","DEC_" );
			parameters1.put("b","Licensing" );
		}
		 
		JRResultSetDataSource jrrs1 = new JRResultSetDataSource(rs1);
		
		 
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+File.separator+"ApprovedLicensingBWFL.jasper");
			 
			
		 
		 
		JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters1, jrrs1);	
		  Random rand = new Random();
    	  int  n = rand.nextInt(250) + 1;

		JasperExportManager.exportReportToPdfFile(print,relativePathpdf+File.separator+id+"_ApprovedLicensing.pdf");
		dt.setPdfname(id+"_ApprovedLicensing.pdf");
		//System.out.println(""+dt.getPdfname());
		//act.setDisplayRegUsers1(displayRegUsers1Impl(act));
		
	//	dt.setPrintFlag(true);
		this.licdate(act, id);
		
			}else {
	
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found")); 	
			//dt.setPrintFlag(false);
		}
		}
			
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return "F";


	}
	// =====================approve label================================

	public boolean approveLabelImpl(
			brand_label_tracking_action act, int labelId, int srNo) {

		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String queryList = "";
		boolean isValid = false;

		try {

			con = ConnectionToDataBase.getConnection();
			con.setAutoCommit(false);

			queryList = " UPDATE brandlabel.brand_label_application_details SET vch_approved='A', approval_nmbr="
					+ srNo + " " + " WHERE id=" + labelId + " ";

			pstmt = con.prepareStatement(queryList);

			saveStatus = 0;

			// System.out.println("--------------"+queryList);

			saveStatus = pstmt.executeUpdate();

			if (saveStatus > 0) {
				con.commit();
				isValid = true;
				// act.closeApplication();

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Brand Approved ", "Brand Approved"));

			} else {

				con.rollback();
				isValid = false;

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error !!! ", "Error!!!"));

			}
		} catch (Exception se) {
			se.printStackTrace();

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception se) {
				se.printStackTrace();
			}
		}

		return isValid;
	}

	// =====================approve label================================

	public boolean rejectLabelImpl(
			brand_label_tracking_action act, int labelId, int srNo) {

		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String queryList = "";
		boolean isValid = false;

		try {

			con = ConnectionToDataBase.getConnection();
			con.setAutoCommit(false);

			queryList = " UPDATE brandlabel.brand_label_application_details SET vch_approved='R', approval_nmbr="
					+ srNo + " " + " WHERE id=" + labelId + " ";

			pstmt = con.prepareStatement(queryList);

			saveStatus = 0;

			// System.out.println("--------------"+queryList);

			saveStatus = pstmt.executeUpdate();

			if (saveStatus > 0) {
				con.commit();
				isValid = true;
				// act.closeApplication();

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Brand Rejected ", "Brand Rejected"));

			} else {

				con.rollback();
				isValid = false;

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error !!! ", "Error!!!"));

			}
		} catch (Exception se) {
			se.printStackTrace();

		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception se) {
				se.printStackTrace();
			}
		}

		return isValid;
	}
	
	
	//===================revert back comment================
	
	
	
		public void saveRvrtCmntImpl(brand_label_tracking_action act) {

			int saveStatus = 0;
			Connection con = null;
			PreparedStatement pstmt = null;
			String queryList = "";

			try {

				con = ConnectionToDataBase.getConnection();
				con.setAutoCommit(false);
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String time = sdf.format(cal.getTime());
				
				if (ResourceUtil.getUserNameAllReq().trim().equalsIgnoreCase("Excise-AC-License"))
				{
					queryList = " UPDATE brandlabel.brand_label_applications "+
								" SET ac_rvrt_time=?, ac_rvrt_comment=?, ac_rvrt_date=?, ac_rvrt_user=?,   "+
								" user3_time=?, user3_date=?, user4_name=?,  " +
								" vch_forwarded=?, rvrt_flag=?, objection_flag='X'  " +
								" WHERE app_id=?  ";

					pstmt = con.prepareStatement(queryList);
					saveStatus = 0;
		
					pstmt.setString(1, time);
					pstmt.setString(2, act.getRvrtCmntPopup());
					pstmt.setDate(3, Utility.convertUtilDateToSQLDate(new Date()));
					pstmt.setString(4, ResourceUtil.getUserNameAllReq().trim());
					//pstmt.setString(5, null);
					pstmt.setString(5, null);
					pstmt.setDate(6, null);
					pstmt.setString(7, null);
					pstmt.setString(8, "Reverted Back To JCHQ");
					pstmt.setString(9, "RT");
					pstmt.setInt(10, act.getAppID());
					
					
				}


				// System.out.println("--------------"+queryList);

				saveStatus = pstmt.executeUpdate();

				if (saveStatus > 0) {
					con.commit();
					act.closeApplication();

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Application Reverted Successfully ","Application Reverted Successfully"));

				} else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error !!! ", "Error!!!"));
					con.rollback();

				}
			} catch (Exception se) {
				se.printStackTrace();

			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();

				} catch (Exception se) {
					se.printStackTrace();
				}
			}

		}	
		
		///// Saddam======== Brand Challan Method////

		public ArrayList getbrandChallanList(brand_label_tracking_action act) {

			ArrayList list = new ArrayList();
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
			int j = 1;
			String selQr = null;
			String filter = "";

			try {
				con = ConnectionToDataBase.getConnection();

			    selQr = " select brndchallan_name,brndchallan_path,date from brandlabel.brand_chalan_detail "+
                        " where app_id="+act.getAppID()+"   ";
				
				
				
				System.out.println("barand Challan=="+selQr);
				
				ps = con.prepareStatement(selQr);
				 rs = ps.executeQuery();
	
				while (rs.next()) {

					brand_label_tracking_dt dt = new brand_label_tracking_dt();

					dt.setBrandSrNo(j);
					dt.setBrandChallan_dt(rs.getDate("date"));
					dt.setBrandChallan_id(rs.getString("brndchallan_name"));
					dt.setBrandChallan_pdf(rs.getString("brndchallan_path"));
					j++;
					list.add(dt);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (ps != null)
						ps.close();
					if (rs != null)
						rs.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;

		}
		
		public ArrayList getlabelChallanList(brand_label_tracking_action act) {

			ArrayList list = new ArrayList();
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs=null;
			int j = 1;
			String selQr = null;
			String filter = "";

			try {
				con = ConnectionToDataBase.getConnection();

			    selQr = " select labelchallan_name,labelchallan_path,date from brandlabel.label_chalan_detail "+
                        " where app_id="+act.getAppID()+"   ";
				
				
				
				System.out.println("barand Challan=="+selQr);
				
				ps = con.prepareStatement(selQr);
				 rs = ps.executeQuery();
	
				while (rs.next()) {

					brand_label_tracking_dt dt = new brand_label_tracking_dt();

					dt.setLabelSrNo(j);
					dt.setLabelChallan_dt(rs.getDate("date"));
					dt.setLabelChallan_id(rs.getString("labelchallan_name"));
					dt.setLabelChallan_pdf(rs.getString("labelchallan_path"));
					j++;
					list.add(dt);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (ps != null)
						ps.close();
					if (rs != null)
						rs.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;

		}

		
		
		public void updatesubtype(brand_label_tracking_action action, String subtype, int brand_id, 
				String brandname, String brndstrength) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int status = 0;

			String query = " update brandlabel.brand_registration  set sub_type='"+subtype+"' where " +
				         	" brand_name='"+brandname+"' and brand_strength='"+brndstrength+"'  ";

			String query1 = " update distillery.brand_registration_20_21  set sub_type='"+subtype+"' " +
					       "  where brand_name='"+brandname+"' and strength='"+brndstrength+"'  ";
			
			String query2="  update distillery.brand_registration_19_20 set sub_type='"+subtype+"' where " +
					      "  brand_name='"+brandname+"' and strength='"+brndstrength+"'  ";
			

			try {
				con = ConnectionToDataBase.getConnection();
				con.setAutoCommit(false);
				if (action.getRenew_new().equalsIgnoreCase("Renew")) {
				ps = con.prepareStatement(query2);
			
				}
				else{
					ps = con.prepareStatement(query);
					
				}
				status = ps.executeUpdate();

				if (status > 0) {
					status = 0;
					ps = con.prepareStatement(query1);
					status = ps.executeUpdate();
				
				}
				
				if (status > 0) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Subtype  Updated", "Subtype  Updated"));

					con.commit();
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(
									"Subtype not Updated",
									"Subtype not Updated"));
					con.rollback();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (ps != null)
						ps.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}	
		
		
		 
		 


		 

		public String reopen(brand_label_tracking_action action, brand_label_tracking_dt dt) {


		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String queryList = "";

		try {

		con = ConnectionToDataBase.getConnection();
		con.setAutoCommit(false);

		queryList = "UPDATE brandlabel.brand_label_applications SET  licence_time=null,  " +
		" digital_sign_date=null,digital_sign_time=null , licence_date=null,digital_sign_pdf_name=null WHERE app_id='"+dt.getAppID_dt()+"' ";






		pstmt = con.prepareStatement(queryList);



		//System.out.println("--------------" + queryList);

		saveStatus = pstmt.executeUpdate();

		if (saveStatus > 0) {
		con.commit();

		} else {
		con.rollback();
		}

		} catch (Exception se) {
		se.printStackTrace();

		} finally {
		try {
		if (pstmt != null)
		pstmt.close();
		if (con != null)
		con.close();

		} catch (Exception se) {
		se.printStackTrace();
		}
		}
		return "";

		} 
		
		
//=======================================================
		
		public void deletebrand(brand_label_tracking_action action, String subtype, int brand_id, 
				String brandname, String brndstrength) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int status = 0;

			String query = " delete  from brandlabel.brand_registration where" +
					       " brand_id_pk=(select distinct brand_id from brandlabel.brand_label_application_details  " +
					       " where app_id='"+action.getShowApplicationID()+"' and brand_id='"+brand_id+"') ";

			String query1 = " delete  from brandlabel.brand_registration_detail_packaging where " +
					        " brand_id_fk=(select distinct brand_id from brandlabel.brand_label_application_details " +
					        " where app_id='"+action.getShowApplicationID()+"' and brand_id='"+brand_id+"' ) ";
			
			String query2=" delete from brandlabel.brand_label_application_details  where app_id='"+action.getShowApplicationID()+"' and brand_id='"+brand_id+"'   ";
			
			String query3=" delete from brandlabel.brand_label_uploading where app_id='"+action.getShowApplicationID()+"'  and brand_id='"+brand_id+"' ";
           
			String query4=" update brandlabel.brand_label_applications set " +
            		      " total_no_of_labels= total_no_of_labels- (select count(*) from brandlabel.brand_label_uploading " +
            		      " where app_id='"+action.getShowApplicationID()+"'  and brand_id='"+brand_id+"' ) where app_id='"+action.getShowApplicationID()+"'";
			try {
				System.out.println("=====11111========"+query);
				System.out.println("=====22222========"+query1);
				System.out.println("=====33333========"+query2);
				System.out.println("=====44444========"+query3);
				System.out.println("=====55555========"+query4);
				con = ConnectionToDataBase.getConnection();
				con.setAutoCommit(false);
				
				ps = con.prepareStatement(query1);
				status = ps.executeUpdate();
				
				if(action.getRenew_new().equalsIgnoreCase("New")){
					
				
				if (status > 0) {
					status = 0;
					ps = con.prepareStatement(query);
					status = ps.executeUpdate();
				
				}
				}else{
					status=1;
				}
				if (status > 0) {
					status = 0;
					ps = con.prepareStatement(query2);
					status = ps.executeUpdate();
				
				}
				if (status > 0) {
					status = 0;
					ps = con.prepareStatement(query3);
					status = ps.executeUpdate();
				
				}
				if (status > 0) {
					status = 0;
					ps = con.prepareStatement(query4);
					status = ps.executeUpdate();
				
				}
				
				if (status > 0) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Brand Delete successfully !!!!!", "Brand Delete successfully !!!!!"));

					con.commit();
					action.setDisplayLabelDetails(this.displayLabelDetailsImpl(action));
					action.setShowUploadedLabels(this.getUploadedLabels(action));
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(
									"Brand not Delete !!!!!",
									"Brand not Delete !!!!!"));
					con.rollback();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (ps != null)
						ps.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}	
		
		
		
		
		public void deletepackage(brand_label_tracking_action action, int packageid, int brand_id, 
				String brandname, String brndstrength) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int status = 0;
/*
			String query = " delete  from brandlabel.brand_registration where" +
					       " brand_id_pk=(select distinct brand_id from brandlabel.brand_label_application_details  " +
					       " where app_id='"+action.getShowApplicationID()+"' and brand_id='"+brand_id+"') ";*/

			String query1 = " delete  from brandlabel.brand_registration_detail_packaging where " +
					        " brand_id_fk=(select distinct brand_id from brandlabel.brand_label_application_details " +
					        " where app_id='"+action.getShowApplicationID()+"' and brand_id='"+brand_id+"' ) " +
					        " and package_id_pk=(select distinct package_id from brandlabel.brand_label_application_details " +
					        " where app_id='"+action.getShowApplicationID()+"' and brand_id='"+brand_id+"' and  package_id='"+packageid+"')  ";
			
			String query2=" delete from brandlabel.brand_label_application_details  where app_id='"+action.getShowApplicationID()+"' and brand_id='"+brand_id+"'  " +
					      " and package_id='"+packageid+"'";
			
			String query3=" delete from brandlabel.brand_label_uploading where app_id='"+action.getShowApplicationID()+"' " +
					      "  and brand_id='"+brand_id+"' and package_id='"+packageid+"' ";
           
			String query4=" update brandlabel.brand_label_applications set " +
            		      " total_no_of_labels= total_no_of_labels- (select count(*) from brandlabel.brand_label_uploading " +
            		      " where app_id='"+action.getShowApplicationID()+"'  and brand_id='"+brand_id+"'  and package_id='"+packageid+"' ) where app_id='"+action.getShowApplicationID()+"'";
			try {
				
				System.out.println("=====22222========"+query1);
				System.out.println("=====33333========"+query2);
				System.out.println("=====44444========"+query3);
				System.out.println("=====55555========"+query4);
				con = ConnectionToDataBase.getConnection();
				con.setAutoCommit(false);
				

					/*ps = con.prepareStatement(query1);
					status = ps.executeUpdate();*/
				if(action.getRenew_new().equalsIgnoreCase("New")){
					ps = con.prepareStatement(query1);
					status = ps.executeUpdate();
				}else{
					status=1;
				}
				
				if (status > 0) {
					status = 0;
					ps = con.prepareStatement(query2);
					status = ps.executeUpdate();
				
				}
				if (status > 0) {
					status = 0;
					ps = con.prepareStatement(query3);
					status = ps.executeUpdate();
				
				}
				if (status > 0) {
					status = 0;
					ps = con.prepareStatement(query4);
					status = ps.executeUpdate();
				
				}
				
				if (status > 0) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Package Delete successfully !!!!!", "Package Delete successfully !!!!!"));

					con.commit();
					action.setDisplayLabelDetails(this.displayLabelDetailsImpl(action));
					action.setShowUploadedLabels(this.getUploadedLabels(action));
					/*action.setDisplayLabelDetails() = this.displayLabelDetailsImpl(action);	
					action.showUploadedLabels = this.getUploadedLabels(action);*/
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(
									"Package not Delete !!!!!",
									"Package not Delete !!!!!"));
					con.rollback();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
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
