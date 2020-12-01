package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
 
import com.mentor.action.DispatchReportDIST_BRE_OLDSTOCK_Action;
import com.mentor.action.IndentStatusMISAction; 
import com.mentor.datatable.IndentStatusMISDT;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class IndentStatusMISImpl {

	
	
	//==========================display indent details in datatable========================
	
	
		public ArrayList displayIndentDetails(IndentStatusMISAction act)
		{

			ArrayList list = new ArrayList();
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int j = 1;
			String selQr = null;
			String filter = ""; 
		 
			try {
				con = ConnectionToDataBase.getConnection();
				//System.out.println("act.getRadioType()="+act.getRadioType()+"--");
				if(act.getRadioType()==null){
					act.setRadioType("");
				}
				if (act.getRadioType().equalsIgnoreCase("O")) 
				{
					filter = 	" AND a.vch_action_taken='O' AND aec_reject_dt IS NULL   " +
								" ORDER BY show_indent_date_time ASC ";
					
				}else if (act.getRadioType().equalsIgnoreCase("C")) 
				{
					filter = 	" AND (a.vch_action_taken='C' or  ( a.vch_action_taken='RJ' and chrg_reject_dt is not null and final_rejection is not null ) ) " +
								" AND a.total_cases!=(SELECT SUM(COALESCE(b.finalize_indent,0)) FROM fl2d.indent_for_wholesale_trxn b WHERE a.indent_no=b.indent_no) " +
								" ORDER BY show_indent_date_time ASC ";
					
				}else if (act.getRadioType().equalsIgnoreCase("A")) 
				{
					filter = 	" AND a.vch_action_taken='A' AND aec_reject_dt IS NULL " +
								" AND a.total_cases!=(SELECT SUM(COALESCE(b.finalize_indent,0)) FROM fl2d.indent_for_wholesale_trxn b WHERE a.indent_no=b.indent_no)  " +
								" ORDER BY show_indent_date_time ASC ";
					
				}else if (act.getRadioType().equalsIgnoreCase("P")) 
				{
					filter = 	" AND a.vch_action_taken IS NULL AND aec_reject_dt IS NULL  " +
								" ORDER BY show_indent_date_time ASC ";
					
				}else if (act.getRadioType().equalsIgnoreCase("D")) 
				{
					filter = 	" AND a.total_cases=(SELECT SUM(COALESCE(b.finalize_indent,0)) FROM fl2d.indent_for_wholesale_trxn b WHERE a.indent_no=b.indent_no)  " +
								" ORDER BY show_indent_date_time ASC ";
								
					
				}else if (act.getRadioType().equalsIgnoreCase("RJ")) 
				{
					filter = 	" AND a.vch_action_taken='RJ' AND chrg_reject_dt is null and final_rejection is null " +
								" ORDER BY show_indent_date_time ASC ";
					
				}
				
				
				selQr = " SELECT distinct CASE WHEN a.vch_type='BWFL' THEN  " +
						" (SELECT z.int_bwfl_id FROM distillery.brand_registration_19_20 z WHERE d.int_brand_id=z.brand_id)end as bwfl_unit_id ," +
						" a.bank_name,a.seq, a.user_id, a.indent_no, a.cr_date, a.vch_type, a.vch_licence_no, a.vch_licence_type,  " +
						" a.district_id, a.db_negotiation_cost, a.vch_mode_pay, a.db_amount, a.payment_dt, a.instrument_no,   " +
						" a.user_1, COALESCE(a.vch_action_taken,'N') as vch_action_taken, a.vch_status, a.unit_id,  " +
						//" ( select distinct sum(f.no_of_box) from fl2d.indent_for_wholesale_trxn f where a.indent_no=f.indent_no)as total_cases, " +
						" a.total_cases, COALESCE(a.total_cases_supplied,0) as total_cases_supplied, a.indent_dt_time, a.vch_licensee_nm, " +
						" CASE WHEN a.vch_type='D' THEN                                                                              "+
						" (SELECT x.vch_undertaking_name FROM public.dis_mst_pd1_pd2_lic x WHERE x.int_app_id_f=a.unit_id)           "+
						" WHEN a.vch_type='B' THEN                                                                                   "+
						" (SELECT y.brewery_nm FROM public.bre_mst_b1_lic y WHERE y.vch_app_id_f=a.unit_id )                         "+
						" WHEN a.vch_type='BWFL' THEN                                                                                "+
						" (SELECT z.vch_firm_name FROM bwfl.registration_of_bwfl_lic_holder_19_20 z WHERE z.int_id=a.unit_id)        "+
						" WHEN a.vch_type='FL2D' THEN                                                                                "+
						" (SELECT v.vch_firm_name FROM licence.fl2_2b_2d_19_20 v WHERE v.int_app_id=a.unit_id)                       "+
						" end as unit_name,                                                                                      " +
						" CASE WHEN a.vch_type='D' THEN 'Distillery' "+
						" WHEN a.vch_type='B' THEN 'Brewery'  "+ 
						" WHEN a.vch_type='BWFL' THEN 'BWFL'  "+
						" WHEN a.vch_type='FL2D' THEN 'FL2D' end as type,  "+ 
						" CASE WHEN a.indent_no like 'M-%' THEN a.cr_date::text   "+
						" else a.indent_dt_time end as show_indent_date_time,  "+
						" (SELECT b.description FROM public.district b WHERE a.district_id=b.districtid) as district_nm,  "+
						" (SELECT SUM(COALESCE(b.finalize_indent,0)) FROM fl2d.indent_for_wholesale_trxn b   " +
						" WHERE a.indent_no=b.indent_no) as total_cases_supplied_detail,  "+
						" b.objection_id, b.objection_issue, b.objection_description, b.objection_date "+
						" FROM fl2d.indent_for_wholesale_trxn d , fl2d.indent_for_wholesale a LEFT OUTER JOIN fl2d.objection_for_wholsale_indent b  " +
						" ON a.indent_no=b.indent_nmbr " +
						" WHERE a.indent_no=d.indent_no AND a.finalize_flag='F' AND a.total_cases >0    " +
						//" AND a.user_1='"+ ResourceUtil.getUserNameAllReq().trim()+ "' " +
						" and a.vch_type='"+act.getCate_type()+"' AND a.unit_id='"+act.getDropdown()+"' "+filter ;
				
				ps = con.prepareStatement(selQr);
				 System.out.println("objection selQr---------------" + selQr);
				rs = ps.executeQuery();

				while (rs.next()) {

					IndentStatusMISDT dt = new IndentStatusMISDT();

					dt.setSrNo(j);
					dt.setWholesaleId_dt(rs.getInt("user_id"));
					dt.setIndentNmbr_dt(rs.getString("indent_no"));
					dt.setDistrictId_dt(rs.getInt("district_id"));
					dt.setDistrictName_dt(rs.getString("district_nm"));
					dt.setType_dt(rs.getString("vch_type"));
					dt.setWholesaleType_dt(rs.getString("type"));
					dt.setNmbrOfCases_dt(rs.getInt("total_cases"));					
					dt.setObjectionID_dt(rs.getInt("objection_id"));
					//dt.setVch_taken(rs.getString("vch_action_taken"));
					dt.setObjIssue_dt(rs.getString("objection_issue"));
					dt.setObjDescription_dt(rs.getString("objection_description"));					
					dt.setWholesaleLicensenmbr_dt(rs.getString("vch_licence_no"));
					dt.setLicenseeName_dt(rs.getString("vch_licensee_nm"));
					dt.setPaidAmount_dt(rs.getDouble("db_amount"));
					dt.setInstrumentNo_dt(rs.getString("instrument_no"));
					dt.setIndentDtTym_dt(rs.getString("indent_dt_time"));
					dt.setNmbrOfCasesSuplied_dt(rs.getInt("total_cases_supplied_detail"));
					dt.setUnit_name(rs.getString("unit_name"));
					dt.setBank_name(rs.getString("bank_name"));
					dt.setAmunt(rs.getDouble("db_negotiation_cost"));					
					dt.setVch_mood_pay(rs.getString("vch_mode_pay"));
					
					dt.setIndentDate(rs.getDate("cr_date"));
					dt.setUnitId_dt(rs.getInt("unit_id"));
					dt.setLicenseType_dt(rs.getString("vch_licence_type"));
					dt.setShowIndentDateTym_dt(rs.getString("show_indent_date_time"));
					
					
					if(rs.getInt("total_cases_supplied_detail")==0){
						dt.setDeliveredFlg(true);
					}else{
						dt.setDeliveredFlg(false);
					}

					SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
					if(rs.getDate("cr_date") != null){
						String appDate = date.format(Utility.convertSqlDateToUtilDate(rs.getDate("cr_date")));
						dt.setIndentDate_dt(appDate);
					}else{
						dt.setIndentDate_dt("");
					}
					
					if(rs.getDate("objection_date") != null){
						String objDate = date.format(Utility.convertSqlDateToUtilDate(rs.getDate("objection_date")));
						dt.setObjDate_dt(objDate);
					}else{
						dt.setObjDate_dt("");
					}
					
					if(rs.getDate("payment_dt") != null){
						String paymentDate = date.format(Utility.convertSqlDateToUtilDate(rs.getDate("payment_dt")));
						dt.setPaymentDate_dt(paymentDate);
					}else{
						dt.setPaymentDate_dt("");
					}

					if(rs.getString("vch_status")!=null && rs.getString("vch_status").length()>0){
						if(rs.getInt("total_cases")==rs.getInt("total_cases_supplied_detail")){
							dt.setStatus_dt("Indent Delivered");
						}
						else{
							dt.setStatus_dt(rs.getString("vch_status"));
						}
					}else{
						dt.setStatus_dt("Pending");
					}
					
					dt.setBwfl_unit_id(rs.getInt("bwfl_unit_id"));


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
		
		//====================view objection=================
		
		public String viewObjectionImpl(IndentStatusMISAction act){

			////////System.out.println("come into impl-0------------");
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			
			try {
				con = ConnectionToDataBase.getConnection();

				String queryList = 	" SELECT indent_nmbr, objection_id, objection_issue, objection_description,  " +
									" objection_time, objection_date, objected_by, reply_on_objection, reply_date,  " +
									" reply_time, replied_by FROM fl2d.objection_for_wholsale_indent " +
									" WHERE indent_nmbr='"+act.getIndentNmbr()+"'  ";

				pstmt = con.prepareStatement(queryList);
							

				rs = pstmt.executeQuery();

				while (rs.next()) {
					
					act.setObjectionID(rs.getInt("objection_id"));
					act.setObjIssue(rs.getString("objection_issue"));
					act.setObjDescription(rs.getString("objection_description"));
		
					SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
					if(rs.getDate("objection_date") != null){
						String objDate = date.format(Utility.convertSqlDateToUtilDate(rs.getDate("objection_date")));
						act.setObjDate_dt(objDate);
					}else{
						act.setObjDate_dt("");
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
		
		
		//================clear objection===========================
		
		
		public void clearObjectionImpl(IndentStatusMISAction act)
		{

			int saveStatus = 0;
			Connection con = null;
			PreparedStatement pstmt = null;			
			String updtQr ="";


			try {
				

				con = ConnectionToDataBase.getConnection();
				con.setAutoCommit(false);
				
				Date date = new Date();
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String time = sdf.format(cal.getTime());

				
				String query = 	" UPDATE fl2d.objection_for_wholsale_indent " +
								" SET reply_on_objection=?, reply_date=?, reply_time=?, replied_by=? " +
								" WHERE indent_nmbr='"+act.getIndentNmbr()+"'  ";
				
				pstmt = con.prepareStatement(query);

				saveStatus = 0;

				pstmt.setString(1, act.getObjRemark());
				pstmt.setDate(2, Utility.convertUtilDateToSQLDate(new Date()));
				pstmt.setString(3, time);
				pstmt.setString(4, ResourceUtil.getUserNameAllReq().trim());

				saveStatus = pstmt.executeUpdate();			 

				if (saveStatus > 0) {

					saveStatus=0;
					
					updtQr = 	" UPDATE fl2d.indent_for_wholesale SET vch_action_taken='R', vch_status=? " +
								" WHERE indent_no=? ";

					

					pstmt = con.prepareStatement(updtQr);

					saveStatus = 0;

					pstmt.setString(1, "Objection Removed");
					pstmt.setString(2, act.getIndentNmbr());



					saveStatus = pstmt.executeUpdate();

				}


				if (saveStatus > 0) {
					con.commit();
					act.reset();
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Objection Cleared Successfully ","Objection Cleared Successfully "));


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
		
		
//================cancel objection===========================


		public void cancelObjectionImpl(IndentStatusMISAction act)
		{

			int saveStatus = 0;
			Connection con = null;
			PreparedStatement pstmt = null;			
			String updtQr ="";


			try {
				

				con = ConnectionToDataBase.getConnection();
				con.setAutoCommit(false);
				
				Date date = new Date();
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String time = sdf.format(cal.getTime());

				
				String query = 	" UPDATE fl2d.objection_for_wholsale_indent " +
								" SET reply_on_objection=?, reply_date=?, reply_time=?, replied_by=? " +
								" WHERE indent_nmbr='"+act.getIndentNmbr()+"'  ";
				
				pstmt = con.prepareStatement(query);

				saveStatus = 0;

				pstmt.setString(1, act.getObjRemark());
				pstmt.setDate(2, Utility.convertUtilDateToSQLDate(new Date()));
				pstmt.setString(3, time);
				pstmt.setString(4, ResourceUtil.getUserNameAllReq().trim());

				saveStatus = pstmt.executeUpdate();			 

				if (saveStatus > 0) {

					saveStatus=0;
					
					updtQr = 	" UPDATE fl2d.indent_for_wholesale SET vch_action_taken='C', vch_status=? " +
								" WHERE indent_no=? ";

					
					pstmt = con.prepareStatement(updtQr);

					saveStatus = 0;

					pstmt.setString(1, "Objection Cancelled");
					pstmt.setString(2, act.getIndentNmbr());



					saveStatus = pstmt.executeUpdate();

				}


				if (saveStatus > 0) {
					con.commit();
					act.reset();
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Objection Cancelled ","Objection Cancelled "));


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
		
		
	// =============================print report============================

	public boolean printReport(IndentStatusMISAction act, IndentStatusMISDT dt){


		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "WholesaleIndent" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "WholesaleIndent" + File.separator + "pdf";
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		boolean printFlag = false;

		try {
			con = ConnectionToDataBase.getConnection();

			
			reportQuery = 	" SELECT a.seq, a.user_id, a.indent_no, a.cr_date, a.vch_type, a.vch_licence_no, a.vch_licence_type,          "+
							" a.district_id, a.db_negotiation_cost, a.vch_mode_pay, a.db_amount, a.payment_dt, a.instrument_no,          "+
							" a.user_1, COALESCE(a.vch_action_taken,'N') as vch_action_taken, a.vch_status, ( select distinct sum(f.no_of_box) from fl2d.indent_for_wholesale_trxn f where a.indent_no=f.indent_no)as total_cases,              "+
							" COALESCE(a.total_cases_supplied,0) as total_cases_supplied, a.indent_dt_time,   a.vch_licensee_nm,         "+
							" a.confirm_payment_flag, a.confirm_payment_date, a.bank_name,                                                            "+
							" CASE WHEN a.vch_type='D' THEN 'Distillery'                                                                 "+
							" WHEN a.vch_type='B' THEN 'Brewery'                                                                         "+
							" WHEN a.vch_type='BWFL' THEN 'BWFL'                                                                         "+
							" WHEN a.vch_type='FL2D' THEN 'FL2D' end as type,                                                            "+
							" CASE WHEN a.vch_type='D' THEN                                                                              "+
							" (SELECT x.vch_undertaking_name FROM public.dis_mst_pd1_pd2_lic x WHERE x.int_app_id_f=a.unit_id)           "+
							" WHEN a.vch_type='B' THEN                                                                                   "+
							" (SELECT y.brewery_nm FROM public.bre_mst_b1_lic y WHERE y.vch_app_id_f=a.unit_id )                         "+
							" WHEN a.vch_type='BWFL' THEN                                                                                "+
							" (SELECT z.vch_firm_name FROM bwfl.registration_of_bwfl_lic_holder_19_20 z WHERE z.int_id=a.unit_id)        "+
							" WHEN a.vch_type='FL2D' THEN                                                                                "+
							" (SELECT v.vch_firm_name FROM licence.fl2_2b_2d_19_20 v WHERE v.int_app_id=a.unit_id)                       "+
							" end as unit_name,                                                                                          "+
							" (SELECT w.vch_core_address FROM licence.fl2_2b_2d_19_20 w WHERE w.int_app_id=a.user_id) as login_user_adrs,"+
							" (SELECT b.description FROM public.district b WHERE a.district_id=b.districtid) as district_nm,             "+
							" b.int_brand_id, b.int_pckg_id, b.no_of_bottle, b.no_of_box, b.size,                                        "+
							" c.brand_name, d.package_name,                                                                              "+
							" CASE WHEN d.package_type='1' THEN 'Glass Bottle'                                                           "+
							" WHEN d.package_type='2' THEN 'CAN'                                                                         "+
							" WHEN d.package_type='3' THEN 'Pet Bottle'                                                                  "+
							" WHEN d.package_type='4' THEN 'Tetra Pack'                                                                  "+
							" WHEN d.package_type='5' THEN 'Sache' end as package_type                                                   "+
							" FROM fl2d.indent_for_wholesale a, fl2d.indent_for_wholesale_trxn b,                                        "+
							" distillery.brand_registration_19_20 c, distillery.packaging_details_19_20 d                                "+
							" WHERE a.indent_no=b.indent_no AND a.user_id=b.user_id AND b.int_brand_id=c.brand_id                        "+
							" AND b.int_pckg_id=d.package_id AND c.brand_id=d.brand_id_fk                                                "+
							" AND a.indent_no='"+act.getIndentNmbr().trim()+"'   ";
			
			////////System.out.println("reportQuery---------------------"+reportQuery);
			pst = con.prepareStatement(reportQuery);
			rs = pst.executeQuery();

			if (rs.next()) {
				rs = pst.executeQuery();

				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				// parameters.put("SUBREPORT_DIR", relativePath+File.separator);
				parameters.put("image", relativePath + File.separator);

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator+ "WholesaleIndentReport.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "WholesaleIndentReport_AEC_"+act.getIndentNmbr().trim().replaceAll("\\s+","") + ".pdf");

				
				act.setPdfName("WholesaleIndentReport_AEC_"+act.getIndentNmbr().trim().replaceAll("\\s+","") + ".pdf");
				dt.setPdfName("WholesaleIndentReport_AEC_"+act.getIndentNmbr().trim().replaceAll("\\s+","") + ".pdf");

				printFlag = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found"));				
				printFlag = false;

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
		return printFlag;
	
	}
	
	
	public ArrayList getBrandName() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		IndentStatusMISAction bof = (IndentStatusMISAction) facesContext
				.getApplication().createValueBinding("#{replyToIndentObjectionAction}")
				.getValue(facesContext);

		String lic = bof.getLic_type();

		String indent_no = bof.getIndentNmbr();

		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SelectItem item = new SelectItem();
		item.setLabel("--SELECT--");
		item.setValue("");
		list.add(item);
		String selQr = "";

		try {

			if(bof.getType().equalsIgnoreCase("D")){
	
			
				if(bof.getLic_type().equalsIgnoreCase("CL2")){
		         
        		selQr =     "   select brand_id,brand_name " +
        				" from distillery.brand_registration_19_20 " +
        				" where ( for_csd_civil !='CSD' or for_csd_civil is null ) and (domain!='EXP' or domain is null) and distillery_id='"+bof.getUnit_id()+"'   and vch_license_type ='CL'  ";
				}else if(bof.getLic_type().equalsIgnoreCase("FL2")){
				
				selQr =     "   select brand_id,brand_name " +
        				" from distillery.brand_registration_19_20" +
        				"  where ( for_csd_civil !='CSD' or for_csd_civil is null ) and (domain!='EXP' or domain is null) and distillery_id='"+bof.getUnit_id()+"' and  vch_license_type in ('FL3','FL3A')  ";
				}
				
			}else if(bof.getType().equalsIgnoreCase("B")){
				
				 if(bof.getLic_type().equalsIgnoreCase("FL2")){
					selQr =     "   select brand_id,brand_name " +
            				" from distillery.brand_registration_19_20 " +
            				" where ( for_csd_civil !='CSD' or for_csd_civil is null ) and (domain!='EXP' or domain is null) and brewery_id='"+bof.getUnit_id()+"'   and vch_license_type in ('FL3','FL3A')    ";
					
					
				}else if(bof.getLic_type().equalsIgnoreCase("FL2B")){
					selQr =     "   select brand_id,brand_name " +
            				" from distillery.brand_registration_19_20 " +
            				" where ( for_csd_civil !='CSD' or for_csd_civil is null ) and (domain!='EXP' or domain is null) and brewery_id='"+bof.getUnit_id()+"'  and vch_license_type in ('FL3','FL3A')    ";
					
					
				}
			}
			else if(bof.getType().equalsIgnoreCase("BWFL")){
				
				 if(bof.getLic_type().equalsIgnoreCase("FL2")){
						selQr =     "   select brand_id,brand_name " +
	            				" from distillery.brand_registration_19_20 " +
	            				" where ( for_csd_civil !='CSD' or for_csd_civil is null ) and (domain!='EXP' or domain is null) and int_bwfl_id='"+bof.getBwfl_unit_id()+"'   and vch_license_type in ('BWFL2A','BWFL2C','BWFL2D')     ";
						
						
					}else if(bof.getLic_type().equalsIgnoreCase("FL2B")){
						selQr =     "   select brand_id,brand_name " +
	            				" from distillery.brand_registration_19_20 " +
	            				" where ( for_csd_civil !='CSD' or for_csd_civil is null ) and (domain!='EXP' or domain is null) and int_bwfl_id='"+bof.getBwfl_unit_id()+"'  and vch_license_type in ('BWFL2A','BWFL2B','BWFL2C','BWFL2D')    ";
						
						
					}
			}else if(bof.getType().equalsIgnoreCase("FL2D")){
				
				 if(bof.getLic_type().equalsIgnoreCase("FL2")){
					selQr =     "   select brand_id,brand_name " +
           				" from distillery.brand_registration_19_20 " +
           				" where ( for_csd_civil !='CSD' or for_csd_civil is null ) and (domain!='EXP' or domain is null) and int_fl2d_id>0    and liquor_type in ('IMPORTED FL','IMPORTED WINE','LAB')  and brewery_id=0 and int_bwfl_id=0 and distillery_id=0 and int_fl2a_id=0     ";
					
					
				}else if(bof.getLic_type().equalsIgnoreCase("FL2B")){
					selQr =     "   select brand_id,brand_name " +
           				" from distillery.brand_registration_19_20 " +
           				" where  ( for_csd_civil !='CSD' or for_csd_civil is null ) and (domain!='EXP' or domain is null) and int_fl2d_id>0   and liquor_type in ('IMPORTED FL','IMPORTED WINE','LAB')  and brewery_id=0 and int_bwfl_id=0 and distillery_id=0 and int_fl2a_id=0     ";
					
					
				}
			}

			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(selQr);
			
			if(bof.getUnit_type().equalsIgnoreCase("D")){
				
				ps.setInt(1, bof.getUnit_id());
				//ps.setString(2, licNo.trim());
				
			}
			
			
			
			
			
			/*if ((lic.equalsIgnoreCase("CL")) || (lic.equalsIgnoreCase("FL3"))) {
				
				ps.setInt(1, this.getSugarmill_Id());
				ps.setString(2, licNo.trim());
			} else {
				
				// ps.setString(1, ResourceUtil.getUserNameAllReq().trim());

				ps.setInt(1, this.getSugarmill_Id());
				ps.setString(2, licNo.trim());

			}*/
			rs = ps.executeQuery();
			while (rs.next()) {
				item = new SelectItem();
				item.setLabel(rs.getString("brand_name"));
				item.setValue(rs.getString("brand_id"));
				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	
	public ArrayList getPackagingName(int brand_id) {
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SelectItem item = new SelectItem();
		item.setLabel("--SELECT--");
		item.setValue(0);
		list.add(item);
		String SQl = "SELECT a.brand_id, a.brand_name ,b.quantity, b.package_name ,b.package_id "
				+ "	from distillery.brand_registration_19_20 a , "
				+ "	distillery.packaging_details_19_20 b "
				+ "	where a.brand_id=b.brand_id_fk  " +
				"	and brand_id =? ";
		try {
			//////System.out.println("---PackagingName---"+SQl);
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(SQl);
			// ps.setInt(1, this.getSugarmill_Id());
			ps.setInt(1, brand_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				item = new SelectItem();
				item.setLabel(rs.getString("package_name"));
				item.setValue(rs.getString("package_id"));
				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	
	public int getqty(int brand_Id, int packging_Id) {
		int qty = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			String queryList =

			"SELECT c.box_size,a.brand_id, a.brand_name ,b.quantity, b.package_name ,b.package_id "
					+ "	from distillery.brand_registration_19_20 a , "
					+ "	distillery.packaging_details_19_20 b,distillery.box_size_details c "
					+ "	where a.brand_id=b.brand_id_fk  " +
					// "	and a.distillery_id=?  "+
					"	and brand_id =?  and b.package_id=? and c.box_id=b.box_id";

			con = ConnectionToDataBase.getConnection();

			pstmt = con.prepareStatement(queryList);

			pstmt.setInt(1, brand_Id);
			pstmt.setInt(2, packging_Id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				qty = rs.getInt("box_size");

			}

			// pstmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return qty;

	}

	
	public int getmrp(int brand_Id, int packging_Id) {
		int mrp = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			String queryList =

			"SELECT b.rounded_mrp,a.brand_id, a.brand_name ,b.quantity, b.package_name ,b.package_id "
					+ "	from distillery.brand_registration_19_20 a , "
					+ "	distillery.packaging_details_19_20 b,distillery.box_size_details c "
					+ "	where a.brand_id=b.brand_id_fk  " +
					// "	and a.distillery_id=?  "+
					"	and brand_id ='"+brand_Id+"'  and b.package_id='"+packging_Id+"' and c.box_id=b.box_id";
			////////System.out.println("--getmrp---"+queryList);
			con = ConnectionToDataBase.getConnection();

			pstmt = con.prepareStatement(queryList);

			/*pstmt.setInt(1, brand_Id);
			pstmt.setInt(2, packging_Id);*/

			rs = pstmt.executeQuery();

			while (rs.next()) {

				mrp = rs.getInt("rounded_mrp");

			}

			// pstmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return mrp;

	}

	
	public ArrayList viewdetailImpl1(IndentStatusMISAction act) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selQr = null;
		int i = 1, cases = 0;
	 
		try {
			
			
	             
        		/*selQr =     "  select distinct bank_name,instrument_no,payment_dt,total_cases,db_negotiation_cost,db_amount,vch_mode_pay,no_of_bottle," +
        				"    c.indent_no,c.no_of_box,package_id,brand_id,brand_name,package_name,package_type,distillery_id,brewery_id,int_bwfl_id,int_fl2d_id,int_fl2a_id" +
        				"        				 from distillery.brand_registration_19_20 a,distillery.packaging_details_19_20 b " +
        				"        				 ,fl2d.indent_for_wholesale_trxn c ,fl2d.indent_for_wholesale D" +
        				"							where a.brand_id=b.brand_id_fk   " +
        				"						 and c.int_brand_id=a.brand_id and  c.int_pckg_id=b.package_id and  c.indent_no=d.indent_no  and  c.indent_no='"+indent_no+"' ";*/
        		
			
			
				selQr = " SELECT CASE WHEN a.vch_type='BWFL' THEN  (SELECT z.int_bwfl_id FROM distillery.brand_registration_19_20 z WHERE b.int_brand_id=z.brand_id)end as bwfl_unit_id ," +
						"a.seq, a.user_id, a.indent_no, a.cr_date, a.vch_type, a.vch_licence_no, a.vch_licence_type,          "+
						" a.district_id, a.db_negotiation_cost, a.vch_mode_pay, a.db_amount, a.payment_dt, a.instrument_no,          "+
						" a.user_1, COALESCE(a.vch_action_taken,'N') as vch_action_taken, a.vch_status,  a.total_cases,              "+
						" COALESCE(a.total_cases_supplied,0) as total_cases_supplied, a.indent_dt_time,   a.vch_licensee_nm,         "+
						" a.confirm_payment_flag, a.confirm_payment_date, a.bank_name, COALESCE(d.rounded_mrp,0)as r_mrp,                                                           "+
						" CASE WHEN a.vch_type='D' THEN 'Distillery'                                                                 "+
						" WHEN a.vch_type='B' THEN 'Brewery'                                                                         "+
						" WHEN a.vch_type='BWFL' THEN 'BWFL'                                                                         "+
						" WHEN a.vch_type='FL2D' THEN 'FL2D' end as type,                                                            "+
						" CASE WHEN a.vch_type='D' THEN                                                                              "+
						" (SELECT x.vch_undertaking_name FROM public.dis_mst_pd1_pd2_lic x WHERE x.int_app_id_f=a.unit_id)           "+
						" WHEN a.vch_type='B' THEN                                                                                   "+
						" (SELECT y.brewery_nm FROM public.bre_mst_b1_lic y WHERE y.vch_app_id_f=a.unit_id )                         "+
						" WHEN a.vch_type='BWFL' THEN                                                                                "+
						" (SELECT z.vch_firm_name FROM bwfl.registration_of_bwfl_lic_holder_19_20 z WHERE z.int_id=a.unit_id)        "+
						" WHEN a.vch_type='FL2D' THEN                                                                                "+
						" (SELECT v.vch_firm_name FROM licence.fl2_2b_2d_19_20 v WHERE v.int_app_id=a.unit_id)                       "+
						" end as unit_name,                                                                                          "+
						" (SELECT w.vch_core_address FROM licence.fl2_2b_2d_19_20 w WHERE w.int_app_id=a.user_id) as login_user_adrs,"+
						" (SELECT b.description FROM public.district b WHERE a.district_id=b.districtid) as district_nm,             "+
						" b.int_brand_id, b.int_pckg_id, b.no_of_bottle, b.no_of_box, b.size,                                        "+
						" c.brand_name, d.package_name,                                                                              "+
						" CASE WHEN d.package_type='1' THEN 'Glass Bottle'                                                           "+
						" WHEN d.package_type='2' THEN 'CAN'                                                                         "+
						" WHEN d.package_type='3' THEN 'Pet Bottle'                                                                  "+
						" WHEN d.package_type='4' THEN 'Tetra Pack'                                                                  "+
						" WHEN d.package_type='5' THEN 'Sache' end as package_type                                                   "+
						" FROM fl2d.indent_for_wholesale a, fl2d.indent_for_wholesale_trxn b,                                        "+
						" distillery.brand_registration_19_20 c, distillery.packaging_details_19_20 d                                "+
						" WHERE a.indent_no=b.indent_no AND a.user_id=b.user_id AND b.int_brand_id=c.brand_id                        "+
						" AND b.int_pckg_id=d.package_id AND c.brand_id=d.brand_id_fk                                                "+
						" AND a.indent_no='"+act.getIndentNmbr().trim()+"' AND COALESCE(b.finalize_indent,0)=0  ";
						//" AND b.no_of_box>0     ";
			
			
	 	 ////System.out.println("apppppppppppp-------------- "+selQr);
 
			conn = ConnectionToDataBase.getConnection();
			ps = conn.prepareStatement(selQr);
	 
			rs = ps.executeQuery();
			while (rs.next()) {
				IndentStatusMISDT dt = new IndentStatusMISDT();
				
				dt.setBrandName_dt(rs.getString("brand_name"));
				dt.setPckgName_dt(rs.getString("package_name"));
				dt.setNoOfBottles_dt(rs.getInt("no_of_bottle"));
				dt.setNoOfCases_dt(rs.getInt("no_of_box"));
				dt.setBrandId_dt(rs.getInt("int_brand_id"));
				dt.setPckgId_dt(rs.getInt("int_pckg_id"));
				dt.setRoundMRP_dt(rs.getDouble("r_mrp"));
				dt.setBoxSize_dt(rs.getInt("size"));
				dt.setNewBrand_Id(rs.getInt("int_brand_id"));
				dt.setNewPckg_Id(rs.getInt("int_pckg_id"));
				dt.setNewBottles_no(rs.getInt("no_of_bottle"));
				dt.setNewBoxes_no_Temp(rs.getInt("no_of_box"));
				dt.setBwfl_unit_id(rs.getInt("bwfl_unit_id"));
				//dt.setNewBoxes_no(rs.getInt("no_of_box"));
				
				act.setTotalMRP(rs.getLong("db_negotiation_cost"));
				act.setTotalCases(rs.getInt("total_cases"));
				act.setOldTotalMRP(rs.getLong("db_negotiation_cost"));
				
				dt.setSlno(i);
				cases++;
			//	act.setRecieveCases(act.getRecieveCases() + cases);

				list.add(dt);
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;

	}
	
	
	public int getMaxIndentModifyId() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = " SELECT max(seq) as id FROM fl2d.indent_brand_modification ";
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
	
	public int getMaxIndentDetailId() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = " SELECT max(seq) as id FROM fl2d.indent_for_wholesale_trxn ";
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
	
	
	public String updateData(IndentStatusMISAction act){


		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		int saveStatus=0;
		int seq=getMaxIndentModifyId();
		int seqDetail =getMaxIndentDetailId();
		double totalMRP=0.0;
		int totalBoxes=0;
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		try
		{
			conn = ConnectionToDataBase.getConnection();
			conn.setAutoCommit(false);


		if(act.getShowBrandList().size()>0)
		{
			for(int i=0;i<act.getShowBrandList().size();i++)
			{
			
		
			IndentStatusMISDT dt = (IndentStatusMISDT) act.getShowBrandList().get(i);

			if(dt.getNoOfCases_dt()!=dt.getNewBoxes_no_Temp() || dt.getPckgId_dt()!=dt.getNewPckg_Id()){

				String insQr = 	" INSERT INTO fl2d.indent_brand_modification( " +
								" seq, user_id, indent_no, indent_date, int_brand_id, int_pckg_id, no_of_bottle,  " +
								" no_of_box, size, modify_date_time, modify_remark) " +
								" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

				
				String updtQr = " INSERT INTO fl2d.indent_for_wholesale_trxn( " +
								" seq, user_id, indent_no, cr_date, int_brand_id,  " +
								" int_pckg_id, no_of_bottle, no_of_box, size) " +
								" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
								" ON CONFLICT ON CONSTRAINT indent_for_wholesale_trxn_pkey " +
								" DO UPDATE SET int_brand_id=?, int_pckg_id=?, no_of_bottle=?, no_of_box=?, size=? ";
				
				
				/*updtQr= 		" UPDATE fl2d.indent_for_wholesale_trxn " +
								" SET int_brand_id=?, int_pckg_id=?, no_of_bottle=?, no_of_box=?, size=? " +
								" WHERE indent_no='"+act.getIndentNmbr().trim()+"' AND int_brand_id='"+dt.getBrandId_dt()+"'  " +
								" AND  int_pckg_id='"+dt.getPckgId_dt()+"' ";*/

		
			saveStatus=0;
			
			
			pstmt=conn.prepareStatement(insQr);

			pstmt.setInt(1, seq);
			pstmt.setInt(2, act.getWholesaleId());
			pstmt.setString(3, act.getIndentNmbr());
			pstmt.setDate(4, Utility.convertUtilDateToSQLDate(act.getIndentDate()));
			pstmt.setInt(5, dt.getBrandId_dt());
			pstmt.setInt(6, dt.getPckgId_dt());
			pstmt.setInt(7, dt.getNoOfBottles_dt());
			pstmt.setInt(8, dt.getNoOfCases_dt());
			pstmt.setInt(9, dt.getBoxSize_dt());
			pstmt.setString(10, dateFormat.format(new Date()));
			pstmt.setString(11, act.getModifyRemark());

			saveStatus=pstmt.executeUpdate();
			
			seq=seq+1;
			
			
			////System.out.println("--------  insert first status --------"+saveStatus);

			
			
			if(saveStatus>0){
				
				saveStatus=0;
				
				
				pstmt=conn.prepareStatement(updtQr);
				
				////System.out.println("--------  update query --------"+updtQr);
				
				pstmt.setInt(1, seqDetail);
				pstmt.setInt(2, act.getWholesaleId());
				pstmt.setString(3, act.getIndentNmbr());
				pstmt.setDate(4, Utility.convertUtilDateToSQLDate(act.getIndentDate()));
				pstmt.setInt(5, dt.getNewBrand_Id());
				pstmt.setInt(6, dt.getNewPckg_Id());
				pstmt.setInt(7, dt.getNewBottles_no());
				pstmt.setInt(8, dt.getNewBoxes_no_Temp());
				pstmt.setInt(9, dt.getNewSize_Id());
				
				
				pstmt.setInt(10, dt.getNewBrand_Id());
				pstmt.setInt(11, dt.getNewPckg_Id());
				pstmt.setInt(12, dt.getNewBottles_no());
				pstmt.setInt(13, dt.getNewBoxes_no_Temp());
				pstmt.setInt(14, dt.getNewSize_Id());

				saveStatus=pstmt.executeUpdate();
				
				seqDetail = seqDetail+1;
				
				totalMRP+= dt.getCalTotalMrp_dt();
				totalBoxes+=dt.getNewBoxes_no_Temp();
				
				////System.out.println("--------  update second status --------"+saveStatus);
				
			}
			
			}
			}
		}
		
		if(saveStatus > 0)
		{
			saveStatus = 0;
			
			 String indentQuery = 	" UPDATE fl2d.indent_for_wholesale " +
									" SET db_negotiation_cost=?, total_cases=?, modify_dt_time=?   " +
									" WHERE indent_no='"+act.getIndentNmbr().trim()+"' ";
			 
			 
			 pstmt=conn.prepareStatement(indentQuery);
			 
			 
			 pstmt.setDouble(1, act.getTotalMRP());
			 pstmt.setInt(2, act.getTotalCases());
			 pstmt.setString(3, dateFormat.format(new Date()));	
				

			saveStatus=pstmt.executeUpdate();
			
			////System.out.println("--------  update third status --------"+saveStatus);
		}
		
		
		if(saveStatus>0)
		{
			conn.commit();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Data Updated Successfully !! ", "Update Successfully !!"));
			act.reset();

		}
		else
		{
			conn.rollback();
			//act.reset_Buttn();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Row Updated !! ", "No Row Updated !!"));
	
		}
		}catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
		}
		finally
		{
      		try
      		{	
      			
          		if(conn!=null)conn.close();
          		if(pstmt!=null)pstmt.close();
          		if(rs!=null)rs.close();
          		
          		
      		}
      		catch(Exception e)
      		{
      			e.printStackTrace();
      		}
      	}
	return "";	
	
	
	}
	
	
	public boolean deleteBrandPckg(IndentStatusMISDT dt, IndentStatusMISAction act) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		int status = 0;
		String delQrBrand=null;
		String updtQrBrand=null;
		boolean validFlg=false;
		
		
		DecimalFormat df2 = new DecimalFormat("#.##");
		long mrp = (long) (dt.getNoOfBottles_dt()*dt.getRoundMRP_dt());
		
		////System.out.println("mrp==============="+mrp);

		
		delQrBrand = 	" DELETE FROM fl2d.indent_for_wholesale_trxn " +
						" WHERE indent_no='"+act.getIndentNmbr()+"'  " +
						" AND int_brand_id='"+dt.getNewBrand_Id()+"' AND int_pckg_id='"+dt.getNewPckg_Id()+"'  " +
						" AND COALESCE(finalize_indent,0)=0 ";
		
		updtQrBrand = 	" UPDATE fl2d.indent_for_wholesale " +
						" SET db_negotiation_cost=db_negotiation_cost-"+mrp+",  " +
						" total_cases=total_cases-"+dt.getNoOfCases_dt()+"  " +
						" WHERE indent_no='"+act.getIndentNmbr()+"' ";
		
		
		////System.out.println("delQrBrand-------------"+delQrBrand);
		
		////System.out.println("updtQrBrand-------------"+updtQrBrand);
		
		
		try {
			con = ConnectionToDataBase.getConnection();		
			con.setAutoCommit(false);
			
			 ps = con.prepareStatement(delQrBrand);
			 status=ps.executeUpdate();
 
			 
			 ////System.out.println("status1---------------"+status);
			
			 
			 if(status>0)
			 {
				 status=0;
				 ps = con.prepareStatement(updtQrBrand);
				 status=ps.executeUpdate(); 
				 
				 ////System.out.println("status2---------------"+status);
			 }
			 
			if (status > 0) {
				//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Brand Deleted Successfully","Brand Deleted Successfully"));				
				con.commit();
				//act.reset();
				validFlg = true;
				act.setDeleteFlag(true);
			}  
			else {
				//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Brand Not Deleted","Brand Not Deleted"));
				con.rollback();
				validFlg = false;
				act.setDeleteFlag(false);
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
		return validFlg;
	
	}
	
	
	//=====================reject indent===================
	
	public void reject_IndentImpl(IndentStatusMISAction act){
		


		int saveStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;		
		String queryList = "";	
		String updtQr ="";


		try {
			

			con = ConnectionToDataBase.getConnection();
			con.setAutoCommit(false);
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String time = sdf.format(cal.getTime());

			
			updtQr = 	" UPDATE fl2d.indent_for_wholesale SET   vch_action_taken='RJ',vch_status=?,  " +
						" aec_reject_rmrk=?, aec_reject_time=?, aec_reject_dt=?, aec_reject_user=?, chrg_reject_user=? " +
						" WHERE indent_no='"+act.getIndentNmbr().trim()+"' ";

			

			pstmt = con.prepareStatement(updtQr);



			saveStatus = 0;

			pstmt.setString(1, "Indent Rejected By AEC");
			pstmt.setString(2, act.getRejectRemark());
			pstmt.setString(3, time);
			pstmt.setDate(4, Utility.convertUtilDateToSQLDate(new Date()));
			pstmt.setString(5, ResourceUtil.getUserNameAllReq().trim());
			pstmt.setString(6, "Excise-CH-"+this.getChargeDetails(act));


			saveStatus = pstmt.executeUpdate();			 

			if (saveStatus > 0) {
				con.commit();
				act.reset();
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Indent Rejected ","Indent Rejected "));


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
	
	
	
	// -------------------get district of indent unit----------------------

		public String getUnitDistrict(IndentStatusMISAction act){


			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query = "";
			String districtId = "";

			try {
				
					
			query = 		" SELECT                                                                                                       "+
							" CASE WHEN a.vch_type='D' THEN                                                                                "+
							" (SELECT b.vch_unit_dist FROM public.dis_mst_pd1_pd2_lic b WHERE b.int_app_id_f=a.unit_id)                    "+
							" WHEN a.vch_type='B' THEN                                                                                     "+
							" (SELECT c.int_reg_district_id FROM public.bre_mst_b1_lic c WHERE c.vch_app_id_f=a.unit_id)                   "+
							" WHEN a.vch_type='BWFL' THEN                                                                                  "+
							" (SELECT d.vch_firm_district_id FROM bwfl.registration_of_bwfl_lic_holder_19_20 d WHERE d.int_id=a.unit_id)   "+
							" WHEN a.vch_type='FL2D' THEN                                                                                  "+
							" (SELECT e.core_district_id FROM licence.fl2_2b_2d_19_20 e WHERE e.int_app_id=a.unit_id)::text                "+
							" end as district                                                                                              "+
							" FROM fl2d.indent_for_wholesale a WHERE a.indent_no='"+act.getIndentNmbr().trim()+"' ";

				
				

				conn = ConnectionToDataBase.getConnection();
				pstmt = conn.prepareStatement(query);
				
				////System.out.println("query------------"+query);

				rs = pstmt.executeQuery();
				if (rs.next()) {

					districtId = rs.getString("district");

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
			return districtId;

		
		}
		
		
// -------------------get charge district----------------------

		public String getChargeDetails(IndentStatusMISAction act){


			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query = "";
			String chargeDistrict = "";

			try {
				
					
			query = 		" SELECT b.description as user FROM public.district a, public.charge b  " +
							" WHERE a.chargeid=b.chargeid AND a.districtid='"+getUnitDistrict(act)+"'  ";

				
				

				conn = ConnectionToDataBase.getConnection();
				pstmt = conn.prepareStatement(query);
				
				//System.out.println("query------------"+query);

				rs = pstmt.executeQuery();
				if (rs.next()) {

					chargeDistrict = rs.getString("user");

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
			return chargeDistrict;

		
		}
		public ArrayList getList(String type) {

			ArrayList list = new ArrayList();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			SelectItem item = new SelectItem();
			item.setLabel("--select--");
			item.setValue("0");
			list.add(item);
			String query="";
			try {
				if(type!=null){
				 if(type.equalsIgnoreCase("B")){
					query = " SELECT vch_app_id_f,brewery_nm  FROM public.bre_mst_b1_lic ORDER BY brewery_nm ";
				}else if(type.equalsIgnoreCase("BWFL")){
				query = " SELECT DISTINCT a.int_id, " +
						" concat(concat(a.vch_firm_name,' - '),b.description) as vch_firm_name " +
						" FROM  bwfl.registration_of_bwfl_lic_holder_19_20 a LEFT OUTER JOIN public.district b " +
						" ON a.vch_firm_district_id=b.districtid::text  " +
						" WHERE  a.vch_approval='V' ORDER BY vch_firm_name ";
				} else if(type.equalsIgnoreCase("FL2D")){
				 query = 	" SELECT DISTINCT a.int_app_id, " +
							" concat(concat(a.vch_firm_name,' - '),b.description) as vch_firm_name " +
							" FROM licence.fl2_2b_2d_19_20 a LEFT OUTER JOIN public.district b " +
							" ON COALESCE(a.core_district_id,0)=b.districtid " +
							" WHERE a.vch_license_type='FL2D' " +
							//"AND a.vch_approval='V' " +
							" ORDER BY vch_firm_name ";
				}else{
					 query = " SELECT int_app_id_f,vch_undertaking_name FROM public.dis_mst_pd1_pd2_lic "
								+ " where vch_verify_flag='V' order by trim(vch_undertaking_name) ";
				}
				}
				System.out.println("list="+query);
				conn = ConnectionToDataBase.getConnection();
				pstmt = conn.prepareStatement(query);
				// pstmt.setInt(1,id);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					item = new SelectItem();

					item.setValue(rs.getString(1));
					item.setLabel(rs.getString(2));

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
		
	

}
