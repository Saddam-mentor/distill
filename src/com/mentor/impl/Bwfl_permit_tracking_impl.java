package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
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


import com.mentor.action.Bwfl_permit_tracking_action;
import com.mentor.datatable.Bwfl_permit_tracking_dt;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class Bwfl_permit_tracking_impl {

	// ------------------display applications in datatable----------------------

	public boolean checkpermit(Bwfl_permit_tracking_action act) {

		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selQr = null;
		String filter = "";
		boolean flag=false;

		
		
		try 
		
		{
			con = ConnectionToDataBase.getConnection();

		    selQr = " SELECT a.id, a.district_id, a.bwfl_id, a.unit_id, a.bwfl_type, a.import_fee, a.duty, a.add_duty, a.login_type,         "+
					" a.special_fee, a.cr_date, a.vch_approved, a.vch_status, a.deo_time, a.deo_remark, a.deo_user, a.deo_date,              "+
					" a.lic_no,  a.import_fee_challan_no, a.spcl_fee_challan_no, a.app_id,    "+
					" CASE WHEN a.bwfl_type=1 THEN 'BWFL2A'                                                                                  "+
					" WHEN a.bwfl_type=2 THEN 'BWFL2B'                                                                                       "+
					" WHEN a.bwfl_type=3 THEN 'BWFL2C'                                                                                       "+
					" WHEN a.bwfl_type=4 THEN 'BWFL2D'                                                                                       "+
					" WHEN a.bwfl_type=99 THEN 'FL2D' end as type,                                                                           "+
					" CASE WHEN a.login_type='FL2D' THEN                                                                                     "+
					" (SELECT d.vch_firm_name FROM licence.fl2_2b_2d_20_21 d WHERE a.bwfl_id=d.int_app_id)                                   "+
					" WHEN a.login_type='BWFL' THEN                                                                                          "+
					" (SELECT d.vch_firm_name FROM bwfl.registration_of_bwfl_lic_holder_20_21 d WHERE a.bwfl_id=d.int_id)                    "+
					" end as vch_firm_name, a.consignor_nm_adrs,                                                                                                 "+
					" c.description, a.bottlng_type, a.valid_upto, a.route_road_radio, a.route_detail,                                       "+
					" e.vch_indus_name, e.vch_reg_office_add, a.digital_sign_time, a.digital_sign_date,                                                                               "+
					" a.print_permit_dt, COALESCE(a.permit_nmbr,'NA')permit_nmbr, COALESCE(a.digital_sign_pdf_name,'NA')digital_sign_pdf_name,                                                           "+
					" (SELECT d.vch_state_name FROM public.state_ind d WHERE e.vch_reg_office_state=d.int_state_id::text)as vch_state_name,  "+
					" (SELECT SUM(COALESCE(dtl.no_of_cases,0)) FROM bwfl_license.import_permit_dtl_20_21 dtl                                       "+
					" WHERE a.id=dtl.fk_id AND a.app_id=dtl.app_id AND a.login_type=dtl.login_type AND a.district_id=dtl.district_id) as total_cases_detail                                                                            "+
					" FROM bwfl_license.import_permit_20_21 a, public.district c, public.other_unit_registration_20_21 e                                 "+
					" WHERE a.district_id=c.districtid AND a.unit_id=e.int_app_id_f  and CASE WHEN a.bwfl_type=1 THEN vch_indus_type='OUPD' " + 
					" WHEN a.bwfl_type=2  THEN vch_indus_type='OUPB'  \r\n" + 
					" WHEN a.bwfl_type=3  THEN vch_indus_type='OUPW'   \r\n" + 
					" WHEN a.bwfl_type=4  THEN vch_indus_type='OUPBU'  \r\n" + 
					" WHEN a.bwfl_type=99  THEN vch_indus_type='IU'  end                                                        "+
					" and a.login_type='BWFL' " +
					" AND a.deo_date IS NOT NULL AND a.vch_approved='APPROVED' and a.permit_nmbr='"+act.getPermit_no()+"'   "+
					" ORDER BY a.app_id  ";
			

            
		   // System.out.println("==========query============"+selQr);
		    
			ps = con.prepareStatement(selQr);
			 
			rs = ps.executeQuery();

			if (rs.next()) {

				act.setAppId(rs.getInt("app_id"));
				act.setRequestID(rs.getInt("id"));
				act.setBwflId(rs.getInt("bwfl_id"));
				act.setUnitID(rs.getInt("unit_id"));
				act.setLicenseType(rs.getString("type"));
				act.setLicenseNmbr(rs.getString("lic_no"));
				act.setDistrictId(rs.getInt("district_id"));
				act.setDistrictName(rs.getString("description"));
				act.setBwflName(rs.getString("vch_firm_name"));
				act.setParentUnitNm(rs.getString("vch_indus_name"));
				act.setParentUnitAdrs(rs.getString("vch_reg_office_add"));
				act.setParentUnitState(rs.getString("vch_state_name"));
				act.setTotalBoxes(rs.getInt("total_cases_detail"));
				act.setMapped_unmapped(rs.getString("bottlng_type"));
				act.setByRoad_byRoute(rs.getString("route_road_radio"));
				act.setRouteDetail(rs.getString("route_detail"));
				act.setPermitDate(rs.getDate("print_permit_dt"));
				act.setPermitNmbr(rs.getString("permit_nmbr"));
				act.setLoginType(rs.getString("login_type"));
				act.setCrDate(rs.getDate("cr_date"));
				act.setApprovalDate(rs.getDate("deo_date"));
				act.setApprovalTym(rs.getString("deo_time"));
				act.setApprovalUser(rs.getString("deo_user"));
				act.setImportFeeChalanNo(rs.getString("import_fee_challan_no"));
				act.setSpclFeeChalanNo(rs.getString("spcl_fee_challan_no"));
				act.setConsignorNmAdrs(rs.getString("consignor_nm_adrs"));
				act.setPermit_no(rs.getString("permit_nmbr"));

				SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
				if (rs.getDate("cr_date") != null) {
					String appDate = date.format(Utility.convertSqlDateToUtilDate(rs.getDate("cr_date")));
					act.setAppDate(appDate);
				} else {
					act.setAppDate("");
				}
				
				if (rs.getDate("valid_upto") != null) {
					String validDate = date.format(Utility.convertSqlDateToUtilDate(rs.getDate("valid_upto")));
					act.setValidUptoDt(validDate);
				} else {
					act.setValidUptoDt("Not Filled");
				}
				flag=true ;
				}
			else
				
			{
				act.reset();
				flag=false ;
				
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
						" This is not a Valid Permit Number !!"," This is not a Valid Permit Number!!"));
				
				
			}
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
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
	
	//======================get max id bwfl_license.import_permit_duty=============================
	

		public int getMaxIdPermit() {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query = null;
			
			
			query = " SELECT max(seq) as id  FROM bwfl_license.import_permit_duty ";

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
		
		public int maxIdMstBotlngPlan() {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		
			String query = " SELECT max(seq) as id FROM bwfl_license.mst_bottling_plan_20_21 ";
			int maxid = 0;
			try {
				con = ConnectionToDataBase.getConnection();
				pstmt = con.prepareStatement(query);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					maxid = rs.getInt("id");
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));	
				
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
		
		
		public int maxIdMstStockRcv() {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		
			String query = " SELECT max(seq) as id FROM fl2d.mst_stock_receive_20_21 ";
			int maxid = 0;
			try {
				con = ConnectionToDataBase.getConnection();
				pstmt = con.prepareStatement(query);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					maxid = rs.getInt("id");
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));	
				
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
		
		public int getRejctedSeq() {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query = null;
			
			
			query = " SELECT max(seq) as id  FROM bwfl_license.rejected_permit_after_approval_20_21 ";

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

		public String approveApplicationImpl(Bwfl_permit_tracking_action act){


			int saveStatus = 0;
			Connection con = null;
			PreparedStatement pstmt = null;			
			String queryList = "";
			String updtQr = "";
			String insQr = "";
			int seq = this.getMaxIdPermit();
			int bottlngPlanId = maxIdMstBotlngPlan();
			int maxStockRcv = maxIdMstStockRcv();
			String permitNmbr = act.getDistrictName().trim().replaceAll("\\s+","")+"_"+act.getAppId()+"_"+act.getLicenseNmbr();

			
			try {
				con = ConnectionToDataBase.getConnection();
				con.setAutoCommit(false);
				Date date = new Date();
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String time = sdf.format(cal.getTime());

				queryList = " INSERT INTO bwfl_license.import_permit_duty(seq, permit_nmbr, bwfl_id, duty, type) " +
							" VALUES (?, ?, ?, ?, ?) ";

				pstmt = con.prepareStatement(queryList);
				
				pstmt.setInt(1, seq);
				pstmt.setString(2, permitNmbr.trim());
				pstmt.setInt(3, act.getBwflId());
				pstmt.setDouble(4, act.getTotalDuty()+act.getTotalAddDuty());
				pstmt.setString(5, act.getLoginType());

				saveStatus = pstmt.executeUpdate();
				
				System.out.println("saveStatus=="+saveStatus);
				
					
				if (saveStatus > 0){
					saveStatus = 0;

					updtQr = 	" UPDATE bwfl_license.import_permit_20_21 "+
							 	" SET deo_time=?, deo_date=?, deo_remark=?,   "+
							 	" deo_user=?, vch_approved=?, vch_status=?, permit_nmbr=?, valid_upto=?  " +
							 	" WHERE id=? AND district_id=? AND login_type=? AND app_id=?  ";
										

					pstmt = con.prepareStatement(updtQr);


					pstmt.setString(1, time);
					pstmt.setDate(2, Utility.convertUtilDateToSQLDate(new Date()));
					pstmt.setString(3, act.getFillRemrks());	
					pstmt.setString(4, ResourceUtil.getUserNameAllReq().trim());
					pstmt.setString(5, "APPROVED");
					pstmt.setString(6, "Approved By DEO");
					pstmt.setString(7, permitNmbr.trim());
					pstmt.setDate(8, Utility.convertUtilDateToSQLDate(act.getFillValidDt()));
					pstmt.setInt(9, act.getRequestID());
					pstmt.setInt(10, act.getDistrictId());
					pstmt.setString(11, act.getLoginType());
					pstmt.setInt(12, act.getAppId());

					saveStatus = pstmt.executeUpdate();
					System.out.println("saveStatus==1111=="+saveStatus);
						
					}
				
				if (saveStatus > 0){
					for (int i = 0; i < act.getDisplayBrandDetails().size(); i++) {

						Bwfl_permit_tracking_dt dt = (Bwfl_permit_tracking_dt) act.getDisplayBrandDetails().get(i);
				
							saveStatus = 0;
							
							if(act.getLoginType().equalsIgnoreCase("BWFL")){
								
							insQr = 	" INSERT INTO bwfl_license.mst_bottling_plan_20_21( " +
										" int_distillery_id, int_brand_id, int_pack_id, int_quantity, int_planned_bottles, int_boxes,  " +
										" int_liquor_type, vch_license_type, plan_dt, licence_no, cr_date,  received_liqour, permitno, permitdt, " +
										" seq, gatepass_no, maped_unmaped_type, etin,  validity_date, permitno_entered,  entry_no_of_bottle_per_case, " +
										" seqfrom, seqto, caseseq, districtid, permit_fee, unit_id) " +
										" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
												

							pstmt = con.prepareStatement(insQr);


							pstmt.setInt(1, act.getBwflId());
							pstmt.setInt(2, dt.getBrandId_dt());
							pstmt.setInt(3, dt.getPckgID_dt());	
							pstmt.setInt(4, dt.getQuantity_dt());
							pstmt.setInt(5, dt.getNmbrOfBtl_dt());
							pstmt.setInt(6, dt.getNmbrOfBox_dt());
							if(act.getLicenseType().equalsIgnoreCase("BWFL2A")){
								pstmt.setInt(7, 1);
							}
							else if(act.getLicenseType().equalsIgnoreCase("BWFL2B")){
								pstmt.setInt(7, 2);
							}
							else if(act.getLicenseType().equalsIgnoreCase("BWFL2C")){
								pstmt.setInt(7, 3);
							}
							else if(act.getLicenseType().equalsIgnoreCase("BWFL2D")){
								pstmt.setInt(7, 4);
							}
							
							pstmt.setString(8, act.getLicenseType());
							pstmt.setDate(9, Utility.convertUtilDateToSQLDate(new Date()));
							pstmt.setString(10, act.getLicenseNmbr());
							pstmt.setDate(11, Utility.convertUtilDateToSQLDate(new Date()));
							pstmt.setInt(12, 0);
							pstmt.setString(13, permitNmbr.trim());
							pstmt.setDate(14, Utility.convertUtilDateToSQLDate(new Date()));
							pstmt.setInt(15, bottlngPlanId);
							pstmt.setString(16, String.valueOf(bottlngPlanId));
							pstmt.setString(17, act.getMapped_unmapped());
							pstmt.setString(18, dt.getEtinNo_dt());
							pstmt.setDate(19, Utility.convertUtilDateToSQLDate(act.getFillValidDt()));
							pstmt.setString(20, permitNmbr.trim());
							pstmt.setString(21, String.valueOf(dt.getSize_dt()));
							pstmt.setInt(22, 0);
							pstmt.setInt(23,0);
							pstmt.setInt(24,0);
							pstmt.setInt(25, act.getDistrictId());
							pstmt.setDouble(26, dt.getImportFees_dt());
							pstmt.setInt(27, act.getUnitID());

							saveStatus = pstmt.executeUpdate();
							
							System.out.println("saveStatus==22===="+saveStatus);
								
							}
							
							else if(act.getLoginType().equalsIgnoreCase("FL2D")){
								
								
								insQr = 	" INSERT INTO fl2d.mst_stock_receive_20_21( int_fl2d_id, int_brand_id, " +
											" int_pack_id, int_quantity, int_planned_bottles, int_boxes, " +
											" int_liquor_type, vch_license_type, plan_dt, licence_no, cr_date, seq, permit_no, " +
											" loginusr, district_id, seqfrom, seqto, caseseq, permit_fee, box_size,cbw_id) " +
											" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
												

							pstmt = con.prepareStatement(insQr);


							pstmt.setInt(1, act.getBwflId());
							pstmt.setInt(2, dt.getBrandId_dt());
							pstmt.setInt(3, dt.getPckgID_dt());	
							pstmt.setInt(4, dt.getQuantity_dt());
							pstmt.setInt(5, dt.getNmbrOfBtl_dt());
							pstmt.setInt(6, dt.getNmbrOfBox_dt());							
							pstmt.setInt(7, dt.getLiquorCatgry_dt());
							pstmt.setString(8, act.getLicenseType());
							pstmt.setDate(9, Utility.convertUtilDateToSQLDate(new Date()));
							pstmt.setString(10, act.getLicenseNmbr());
							pstmt.setDate(11, Utility.convertUtilDateToSQLDate(new Date()));
							pstmt.setInt(12, maxStockRcv);
							pstmt.setString(13, permitNmbr.trim());
							pstmt.setString(14, ResourceUtil.getUserNameAllReq().trim());
							pstmt.setInt(15, act.getDistrictId());
							pstmt.setInt(16, 0);
							pstmt.setInt(17,0);
							pstmt.setInt(18,0);
							pstmt.setDouble(19, dt.getImportFees_dt());
							pstmt.setInt(20, dt.getSize_dt());
							pstmt.setInt(21, act.getCustom_id());
							
							
							saveStatus = pstmt.executeUpdate();
							System.out.println("saveStatus==33====="+saveStatus);
							}

							
								
								
							}
						
						
					}


				if (saveStatus > 0) {
					con.commit();
					act.setPopupPermitNmbr(act.getAppId()+" and Permit No. is "+permitNmbr);
					act.closeApplication();
					act.setShowMainPanel_flg(true);
					/*FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
					" Application Approved Successfully. Your Application Id is "+ act.getAppId()+" and Permit No. is "+ permitNmbr+" !!! ",
					" Application Approved Successfully. Your Application Id is "+ act.getAppId()+" and Permit No. is "+ permitNmbr+" !!! "));*/
					

				} else {
					act.setShowMainPanel_flg(false);
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error !!! ", "Error!!!"));

					con.rollback();

				}
			} catch (Exception se) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(se.getMessage(), se.getMessage()));
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
		
		
		//================reject application====================
		
		
		public String rejectApplicationImpl(Bwfl_permit_tracking_action act){


			int saveStatus = 0;
			Connection con = null;
			PreparedStatement pstmt = null;			
			String updtQr = "";
			
			try {
				con = ConnectionToDataBase.getConnection();
				con.setAutoCommit(false);
				Date date = new Date();
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String time = sdf.format(cal.getTime());

					updtQr = 	" UPDATE bwfl_license.import_permit_20_21 "+
							 	" SET deo_time=?, deo_date=?, deo_remark=?,   "+
							 	" deo_user=?, vch_approved=?, vch_status=?, import_fee_challan_no=?, spcl_fee_challan_no=?  " +
							 	" WHERE id=? AND district_id=? AND login_type=? AND app_id=?  ";
										

					pstmt = con.prepareStatement(updtQr);


					pstmt.setString(1, time);
					pstmt.setDate(2, Utility.convertUtilDateToSQLDate(new Date()));
					pstmt.setString(3, act.getFillRemrks());	
					pstmt.setString(4, ResourceUtil.getUserNameAllReq().trim());
					pstmt.setString(5, "REJECTED");
					pstmt.setString(6, "Rejected By DEO");
					pstmt.setString(7, null);
					pstmt.setString(8, null);
					pstmt.setInt(9, act.getRequestID());
					pstmt.setInt(10, act.getDistrictId());
					pstmt.setString(11, act.getLoginType());
					pstmt.setInt(12, act.getAppId());

					saveStatus = pstmt.executeUpdate();
					
					System.out.println("status1------only----- "+saveStatus);
						


				if (saveStatus > 0) {
					con.commit();
					act.closeApplication();
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
					" Application Rejected   !!! ","Application Rejected !!!"));
					

				} else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error !!! ", "Error!!!"));

					con.rollback();

				}
			} catch (Exception se) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(se.getMessage(), se.getMessage()));
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
		
		// ====================reject application after approval=========================

		public String rejectApprovedApplication(Bwfl_permit_tracking_action act){


			int saveStatus = 0;
			Connection con = null;
			PreparedStatement pstmt = null;			
			String updtQr = "";
			String delQr = "";
			String insQr = "";
			int seq = getRejctedSeq();
			
			
			
			try {
				con = ConnectionToDataBase.getConnection();
				con.setAutoCommit(false);
				Date date = new Date();
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String time = sdf.format(cal.getTime());
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

					updtQr = 	" UPDATE bwfl_license.import_permit_20_21 "+
							 	" SET deo_time=?, deo_date=?, deo_remark=?,   "+
							 	" deo_user=?, vch_approved=?, vch_status=?, import_fee_challan_no=?, spcl_fee_challan_no=?  " +
							 	" WHERE id=? AND district_id=? AND login_type=? AND app_id=?  ";
										

					pstmt = con.prepareStatement(updtQr);


					pstmt.setString(1, time);
					pstmt.setDate(2, Utility.convertUtilDateToSQLDate(new Date()));
					pstmt.setString(3, act.getFillRemrks());	
					pstmt.setString(4, ResourceUtil.getUserNameAllReq().trim());
					pstmt.setString(5, "REJECTED");
					pstmt.setString(6, "Rejected By DEO after Approval");
					pstmt.setString(7, null);
					pstmt.setString(8, null);
					pstmt.setInt(9, act.getRequestID());
					pstmt.setInt(10, act.getDistrictId());
					pstmt.setString(11, act.getLoginType());
					pstmt.setInt(12, act.getAppId());
	
					saveStatus = pstmt.executeUpdate();
					
					System.out.println("status1------rejectttt----- "+saveStatus);
						
					if(saveStatus > 0){
					
						insQr = 	" INSERT INTO bwfl_license.rejected_permit_after_approval_20_21( " +
									" seq, district_id, bwfl_id, bwfl_type, cr_date, approval_time, approval_remark, " +
									" approval_user, approval_date, lic_no, permit_nmbr, login_type, reject_dt_time, reject_remark, app_id_fk) " +
									" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);  ";
									

					pstmt = con.prepareStatement(insQr);


					pstmt.setInt(1, seq);
					pstmt.setInt(2, act.getDistrictId());
					pstmt.setInt(3, act.getBwflId());
					pstmt.setString(4, act.getLicenseType());
					pstmt.setDate(5, Utility.convertUtilDateToSQLDate(new Date()));
					pstmt.setString(6, act.getApprovalTym());
					pstmt.setString(7, act.getDeoRemrks());
					pstmt.setString(8, act.getApprovalUser());
					pstmt.setDate(9, Utility.convertUtilDateToSQLDate(act.getApprovalDate()));
					pstmt.setString(10, act.getLicenseNmbr());	
					pstmt.setString(11, act.getPermitNmbr());
					pstmt.setString(12, act.getLoginType());
					pstmt.setString(13, dateFormat.format(new Date()));
					pstmt.setString(14, act.getFillRemrks());
					pstmt.setInt(15, act.getAppId());

					saveStatus = pstmt.executeUpdate();
					
					System.out.println("status2------rejectttt----- "+saveStatus);
				}
					
					
					if(saveStatus > 0){
						for (int i = 0; i < act.getDisplayBrandDetails().size(); i++) {

							Bwfl_permit_tracking_dt dt = (Bwfl_permit_tracking_dt) act.getDisplayBrandDetails().get(i);

							saveStatus = 0;
							
							if(act.getLoginType().equalsIgnoreCase("BWFL")){
								
							delQr = 	" DELETE FROM bwfl_license.mst_bottling_plan_20_21  "+								 	
								 		" WHERE int_distillery_id=? AND int_brand_id=? AND int_pack_id=? AND plan_dt=? " +
								 		" AND permitno=?  AND (finalized_flag!='F' or finalized_flag IS NULL)  ";
										

								pstmt = con.prepareStatement(delQr);
			
			
								pstmt.setInt(1, act.getBwflId());
								pstmt.setInt(2, dt.getBrandId_dt());
								pstmt.setInt(3, dt.getPckgID_dt());
								pstmt.setDate(4, Utility.convertUtilDateToSQLDate(act.getApprovalDate()));
								pstmt.setString(5, act.getPermitNmbr());	
			
								saveStatus = pstmt.executeUpdate();
								
							}
							else if(act.getLoginType().equalsIgnoreCase("FL2D")){
								
							delQr = 	" DELETE FROM fl2d.mst_stock_receive_20_21  "+								 	
								 		" WHERE int_fl2d_id=? AND int_brand_id=? AND int_pack_id=? AND plan_dt=? " +
								 		" AND permit_no=?  AND (finalized_flag!='F' or finalized_flag IS NULL)  ";
										

								pstmt = con.prepareStatement(delQr);
			
			
								pstmt.setInt(1, act.getBwflId());
								pstmt.setInt(2, dt.getBrandId_dt());
								pstmt.setInt(3, dt.getPckgID_dt());
								pstmt.setDate(4, Utility.convertUtilDateToSQLDate(act.getApprovalDate()));
								pstmt.setString(5, act.getPermitNmbr());	
			
								saveStatus = pstmt.executeUpdate();
								
							}
							
							
								
								System.out.println("status3------rejectttt----- "+saveStatus);
	
						}
					}

					

				if (saveStatus > 0) {
					con.commit();
					act.closeApplication();
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
					" Application Rejected   !!! ","Application Rejected !!!"));
					

				} else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error !!! ", "Error!!!"));

					con.rollback();

				}
			} catch (Exception se) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(se.getMessage(), se.getMessage()));
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
		
		// ------------------display brand details in datatable----------------------

		public ArrayList displayBrandDetailsImpl(Bwfl_permit_tracking_action act){


			ArrayList list = new ArrayList();
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int j = 1;
			String selQr = null;
			String filter = "";


			try {
				con = ConnectionToDataBase.getConnection();

				                                                                                                                             
				selQr = " SELECT distinct a.fk_id, a.district_id, a.brand_id, a.pckg_id, a.etin,a.scanning_fee, a.no_of_cases, a.no_of_bottle_per_case,         "+
						" a.pland_no_of_bottles, a.import_fee, (a.duty+a.add_duty)as duty, a.add_duty, a.special_fee, a.cr_date,        "+
						" b.import_fee as tot_import_fee, (b.duty+b.add_duty) as tot_duty, " +
						" b.add_duty as tot_adduty, b.special_fee as tot_spcl_fee, " +
						" br.brand_name, br.liquor_category, pk.package_name, pk.quantity  "+
						" FROM bwfl_license.import_permit_dtl_20_21 a, bwfl_license.import_permit_20_21 b,                                          "+
						" distillery.brand_registration_20_21 br, distillery.packaging_details_20_21 pk                                 "+
						" WHERE a.fk_id=b.id AND a.district_id=b.district_id AND a.login_type=b.login_type AND a.app_id=b.app_id        "+
						" AND br.brand_id=a.brand_id and br.brand_id=pk.brand_id_fk and a.pckg_id=pk.package_id                         "+
						" AND a.app_id="+act.getAppId()+"   " +
						" ORDER BY a.fk_id ";

				ps = con.prepareStatement(selQr);
				//System.out.println("brand query---------------" + selQr);
				rs = ps.executeQuery();

				while (rs.next()) {

					Bwfl_permit_tracking_dt dt = new Bwfl_permit_tracking_dt();

					dt.setSrNo(j);
					
					
					dt.setBrandId_dt(rs.getInt("brand_id"));
					dt.setBrandName_dt(rs.getString("brand_name"));
					dt.setSize_dt(rs.getInt("no_of_bottle_per_case"));
					dt.setEtinNo_dt(rs.getString("etin"));
					dt.setPckgID_dt(rs.getInt("pckg_id"));
					dt.setPckgType_dt(rs.getString("package_name"));
					dt.setNmbrOfBox_dt(rs.getInt("no_of_cases"));
					dt.setNmbrOfBtl_dt(rs.getInt("pland_no_of_bottles"));
					dt.setDuty_dt(rs.getDouble("duty"));
					dt.setAddDuty_dt(rs.getDouble("add_duty"));
					dt.setImportFees_dt(rs.getDouble("import_fee"));
					dt.setSpecialfees_dt(rs.getDouble("special_fee"));
					dt.setQuantity_dt(rs.getInt("quantity"));
					dt.setLiquorCatgry_dt(rs.getInt("liquor_category"));
					dt.setScanning_fee(rs.getDouble("scanning_fee"));

					act.setTotalDuty(rs.getDouble("tot_duty"));
					act.setTotalAddDuty(rs.getDouble("tot_adduty"));
					act.setTotalImportFee(rs.getDouble("tot_import_fee"));
					act.setTotalSpecialFee(rs.getDouble("tot_spcl_fee"));
					act.setTotal_scanning_fee(act.getTotal_scanning_fee()+rs.getDouble("scanning_fee"));


					j++;
					list.add(dt);
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
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

		
		// =============================print report============================

//		public boolean printReportBWFL(Bwfl_permit_tracking_action act, Bwfl_permit_tracking_dt dt){
//
//
//			String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
//			String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "Bond" + File.separator + "jasper";
//			String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "Bond" + File.separator + "BWFLpermit";
//			JasperReport jasperReport = null;
//			JasperPrint jasperPrint = null;
//			PreparedStatement pst = null;
//			Connection con = null;
//			ResultSet rs = null;
//			String reportQuery = null;
//			boolean printFlag = false;
//
//			try {
//				con = ConnectionToDataBase.getConnection();
//
//					
//				reportQuery = 	" SELECT DISTINCT a.import_fee_challan_no,case when br.strength>5 then 'Strong' else 'Lager' end as strength, " +
//						" a.spcl_fee_challan_no, "+
//						" (select amount from bwfl_license.chalan_deposit_bwfl_fl2d                                                                     "+
//						" where chalan_no=a.import_fee_challan_no and unit_id=a.bwfl_id and                                                             "+
//						" unit_type=CASE WHEN a.bwfl_type=1 then 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                                              "+
//						" WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' WHEN a.bwfl_type=99 THEN 'FL2D' END) as import_chalan_amnt, "+
//						" (select amount from bwfl_license.chalan_deposit_bwfl_fl2d                                                                     "+
//						" where chalan_no=a.spcl_fee_challan_no and unit_id=a.bwfl_id and                                                               "+
//						" unit_type=CASE WHEN a.bwfl_type=1 then 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                                              "+
//						" WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' WHEN a.bwfl_type=99 THEN 'FL2D' END) as spcl_chalan_amnt,   "+
//						" (select chalan_date from bwfl_license.chalan_deposit_bwfl_fl2d                                                                "+
//						" where chalan_no=a.import_fee_challan_no and unit_id=a.bwfl_id and                                                             "+
//						" unit_type=CASE WHEN a.bwfl_type=1 then 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                                              "+
//						" WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' WHEN a.bwfl_type=99 THEN 'FL2D' END) as import_chalan_date, "+ 
//						" (select chalan_date from bwfl_license.chalan_deposit_bwfl_fl2d                                                                "+
//						" where chalan_no=a.spcl_fee_challan_no and unit_id=a.bwfl_id and                                                               "+
//						" unit_type=CASE WHEN a.bwfl_type=1 then 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                                              "+
//						" WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' WHEN a.bwfl_type=99 THEN 'FL2D' END) as spcl_chalan_date,   "+
//						" a.id, a.district_id, a.bwfl_id, a.unit_id, a.bwfl_type,                                                "+
//						" a.import_fee as tot_import_fee,                                                                                        "+
//						" (a.duty+a.add_duty)as tot_duty, a.special_fee as tot_special_fee, a.cr_date,                                           "+
//						" a.vch_approved, a.vch_status, a.deo_time, a.deo_remark, a.deo_user, a.deo_date,                                        "+
//						" a.bottlng_type, a.valid_upto, a.route_road_radio, a.route_detail,                                                      "+
//						" CASE WHEN a.bwfl_type=1 THEN 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                                                 "+
//						" WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' end as type,                                         "+
//						" CASE WHEN a.route_road_radio='route' THEN 'By Train' "+
//						" WHEN a.route_road_radio='road' THEN 'By Road' end as route_type,  "+
//						" b.vch_firm_name, b.vch_license_number, b.vch_firm_add, c.description,                                                  "+
//						" CONCAT(b.vch_firm_name, '(' ,b.vch_firm_add,')')as consignee_nm_adrs,                                                  "+
//						" (SELECT d.vch_state_name FROM public.state_ind d WHERE f.vch_reg_office_state=d.int_state_id::text)as vch_state_name,  "+
//						" d.no_of_cases, d.no_of_bottle_per_case, d.pland_no_of_bottles, a.permit_nmbr, d.import_fee,                            "+
//						" (d.duty+d.add_duty) as duty, d.special_fee, a.import_fee_challan_no, a.spcl_fee_challan_no,                            "+
//						" f.vch_indus_name, f.vch_reg_office_add,                                                                                "+
//						" CONCAT(f.vch_indus_name, '(' ,f.vch_reg_office_add,')')as consignor_nm_adrs,                                           "+
//						" br.brand_name, pk.package_name, (pk.duty+pk.adduty) as duty_rate,                                                                                        "+
//						" round(CAST(float8(((d.pland_no_of_bottles)*pk.quantity)/1000) as numeric), 2) as bl                                    "+
//						" FROM bwfl_license.import_permit_20_21 a, bwfl.registration_of_bwfl_lic_holder_20_21 b,                                       "+
//						" public.district c, bwfl_license.import_permit_dtl_20_21 d,                                                                   "+
//						" public.other_unit_registration_20_21 f,                                                                                      "+
//						" distillery.brand_registration_20_21 br, distillery.packaging_details_20_21 pk                                          "+
//						" WHERE a.bwfl_id=b.int_id AND a.district_id=c.districtid  " +
//						" AND a.id=d.fk_id AND a.district_id=d.district_id AND a.login_type=d.login_type AND a.app_id=d.app_id              "+
//						" AND b.unit_id=f.int_app_id_f AND  a.custom_id=b.int_id  " +
//						/*" and CASE WHEN a.bwfl_type=1 THEN vch_indus_type='OUPD'  "
//						+ "WHEN a.bwfl_type=2  THEN vch_indus_type='OUPB'      WHEN a.bwfl_type=3  THEN vch_indus_type='OUPW'  "
//						+ "WHEN a.bwfl_type=4  THEN vch_indus_type='OUPBU'      WHEN a.bwfl_type=99  THEN vch_indus_type='IU'  end  " +*/
//						" AND br.brand_id=d.brand_id and br.brand_id=pk.brand_id_fk and d.pckg_id=pk.package_id                                  "+
//						" AND a.app_id="+dt.getAppId_dt()+" ";
//
//				
//					
//			//	System.out.println("reportQuery---------------------"+reportQuery);
//				pst = con.prepareStatement(reportQuery);
//				rs = pst.executeQuery();
//
//				if (rs.next()) {
//					rs = pst.executeQuery();
//
//					Map parameters = new HashMap();
//					parameters.put("REPORT_CONNECTION", con);
//					//parameters.put("SUBREPORT_DIR", relativePath+File.separator);
//					parameters.put("image", relativePath + File.separator);					
//					/*if(dt.getLoginType_dt().equalsIgnoreCase("BWFL")){
//						parameters.put("balImportFee", act.getBalRgstrImportFee());
//					}
//					else if(dt.getLoginType_dt().equalsIgnoreCase("FL2D")){
//						parameters.put("balImportFee", act.getBalFL2DImportFee());
//					}*/
//					
//
//					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
//					if(dt.getLicenseType_dt().equalsIgnoreCase("BWFL2B")){
//						
//						
//						jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator+ "BWFLImportPermitBwfl2B.jasper");
//						 
//						}
//						else{
//							jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator+ "BWFLImportPermit.jasper");
//						}
//					JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
//					
//					Random rand = new Random();
//					int n = rand.nextInt(250) + 1;
//
//					JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ dt.getPermitNmbr_dt().trim().replaceAll("\\s+","")+".pdf");
//					
//					//dt.setPdfName(dt.getAppId_dt()+ "-"+dt.getBwflID_dt()+"_"+dt.getLoginType_dt()+"ImportPermit.pdf");	
//					dt.setPdfName(dt.getPermitNmbr_dt()+".pdf");
//					this.updatePermit(act, dt.getRequestID_dt(),dt.getDistrictId_dt(), dt.getLoginType_dt(), dt.getAppId_dt(),dt.getPdfName());
//					printFlag = true;
//				} else {
//					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found"));				
//					printFlag = false;
//
//				}
//			} catch (JRException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
//				e.printStackTrace();
//			} finally {
//				try {
//					if (rs != null)
//						rs.close();
//					if (con != null)
//						con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			return printFlag;
//
//		
//		}
		public boolean printReportBWFL(Bwfl_permit_tracking_action act, Bwfl_permit_tracking_dt dt){


			String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
			String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "Bond" + File.separator + "jasper";
			String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "Bond" + File.separator + "BWFLpermit";
			JasperReport jasperReport = null;
			JasperPrint jasperPrint = null;
			PreparedStatement pst = null;
			Connection con = null;
			ResultSet rs = null;
			String reportQuery = null;
			boolean printFlag = false;

			try {
				con = ConnectionToDataBase.getConnection();

					
				reportQuery = 	" SELECT DISTINCT a.import_fee_challan_no,case when br.strength>5 then 'Strong' else 'Lager' end as strength, " +
						" a.spcl_fee_challan_no, "+
						" (select amount from bwfl_license.chalan_deposit_bwfl_fl2d                                                                     "+
						" where chalan_no=a.import_fee_challan_no and unit_id=a.bwfl_id and                                                             "+
						" unit_type=CASE WHEN a.bwfl_type=1 then 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                                              "+
						" WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' WHEN a.bwfl_type=99 THEN 'FL2D' END) as import_chalan_amnt, "+
						" (select amount from bwfl_license.chalan_deposit_bwfl_fl2d                                                                     "+
						" where chalan_no=a.spcl_fee_challan_no and unit_id=a.bwfl_id and                                                               "+
						" unit_type=CASE WHEN a.bwfl_type=1 then 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                                              "+
						" WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' WHEN a.bwfl_type=99 THEN 'FL2D' END) as spcl_chalan_amnt,   "+
						" (select chalan_date from bwfl_license.chalan_deposit_bwfl_fl2d                                                                "+
						" where chalan_no=a.import_fee_challan_no and unit_id=a.bwfl_id and                                                             "+
						" unit_type=CASE WHEN a.bwfl_type=1 then 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                                              "+
						" WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' WHEN a.bwfl_type=99 THEN 'FL2D' END) as import_chalan_date, "+ 
						" (select chalan_date from bwfl_license.chalan_deposit_bwfl_fl2d                                                                "+
						" where chalan_no=a.spcl_fee_challan_no and unit_id=a.bwfl_id and                                                               "+
						" unit_type=CASE WHEN a.bwfl_type=1 then 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                                              "+
						" WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' WHEN a.bwfl_type=99 THEN 'FL2D' END) as spcl_chalan_date,   "+
						" a.id, a.district_id, a.bwfl_id, a.unit_id, a.bwfl_type,                                                "+
						" a.import_fee as tot_import_fee,                                                                                        "+
						" (a.duty+a.add_duty)as tot_duty, a.special_fee as tot_special_fee, a.cr_date,                                           "+
						" a.vch_approved, a.vch_status, a.deo_time, a.deo_remark, a.deo_user, a.deo_date,                                        "+
						" a.bottlng_type, a.valid_upto, a.route_road_radio, a.route_detail,                                                      "+
						" CASE WHEN a.bwfl_type=1 THEN 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                                                 "+
						" WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' end as type,                                         "+
						" CASE WHEN a.route_road_radio='route' THEN 'By Train' "+
						" WHEN a.route_road_radio='road' THEN 'By Road' end as route_type,  "+
						" b.vch_firm_name, b.vch_license_number, b.vch_firm_add, c.description,                                                  "+
						" CONCAT(b.vch_firm_name, '(' ,b.vch_firm_add,')')as consignee_nm_adrs,                                                  "+
						" (SELECT d.vch_state_name FROM public.state_ind d WHERE f.vch_reg_office_state=d.int_state_id::text)as vch_state_name,  "+
						" d.no_of_cases, d.no_of_bottle_per_case, d.pland_no_of_bottles, a.permit_nmbr, d.import_fee,                            "+
						" (d.duty+d.add_duty) as duty, d.special_fee, a.import_fee_challan_no, a.spcl_fee_challan_no,                            "+
						" f.vch_indus_name, f.vch_reg_office_add,                                                                                "+
						" CONCAT(f.vch_indus_name, '(' ,f.vch_reg_office_add,')')as consignor_nm_adrs,                                           "+
						" br.brand_name, pk.package_name, (pk.duty+pk.adduty) as duty_rate,                                                                                        "+
						" round(CAST(float8(((d.pland_no_of_bottles)*pk.quantity)/1000) as numeric), 2) as bl ,                                   "+
						" CASE WHEN pk.package_type='1' THEN 'Glass Bottle' "+
						" WHEN pk.package_type='2' THEN 'CAN' "+
						" WHEN pk.package_type='3' THEN 'Pet Bottle' "+
						" WHEN pk.package_type='4' THEN 'Tetra Pack' "+
						" WHEN pk.package_type='5' THEN 'Sachet' "+
						" WHEN pk.package_type='6' THEN 'Keg' end as liqour_type "+
						" FROM bwfl_license.import_permit_20_21 a, bwfl.registration_of_bwfl_lic_holder_20_21 b,                                       "+
						" public.district c, bwfl_license.import_permit_dtl_20_21 d,                                                                   "+
						" public.other_unit_registration_20_21 f,                                                                                      "+
						" distillery.brand_registration_20_21 br, distillery.packaging_details_20_21 pk                                          "+
						" WHERE a.bwfl_id=b.int_id AND a.district_id=c.districtid  " +
						" AND a.id=d.fk_id AND a.district_id=d.district_id AND a.login_type=d.login_type AND a.app_id=d.app_id              "+
						" AND b.unit_id=f.int_app_id_f AND a.unit_id=b.unit_id   and CASE WHEN a.bwfl_type=1 THEN vch_indus_type='OUPD'  "
						+ "WHEN a.bwfl_type=2  THEN vch_indus_type='OUPB'      WHEN a.bwfl_type=3  THEN vch_indus_type='OUPW'  "
						+ "WHEN a.bwfl_type=4  THEN vch_indus_type='OUPBU'      WHEN a.bwfl_type=99  THEN vch_indus_type='IU'  end  " +
						" AND br.brand_id=d.brand_id and br.brand_id=pk.brand_id_fk and d.pckg_id=pk.package_id                                  "+
						" AND a.app_id="+dt.getAppId_dt()+" ";

				
					
			//	System.out.println("reportQuery---------------------"+reportQuery);
				pst = con.prepareStatement(reportQuery);
				rs = pst.executeQuery();

				if (rs.next()) {
					rs = pst.executeQuery();

					Map parameters = new HashMap();
					parameters.put("REPORT_CONNECTION", con);
					//parameters.put("SUBREPORT_DIR", relativePath+File.separator);
					parameters.put("image", relativePath + File.separator);					
					/*if(dt.getLoginType_dt().equalsIgnoreCase("BWFL")){
						parameters.put("balImportFee", act.getBalRgstrImportFee());
					}
					else if(dt.getLoginType_dt().equalsIgnoreCase("FL2D")){
						parameters.put("balImportFee", act.getBalFL2DImportFee());
					}*/
					

					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
					if(dt.getLicenseType_dt().equalsIgnoreCase("BWFL2B")){
						
						
						jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator+ "BWFLImportPermitBwfl2B.jasper");
						 
						}
						else{
							jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator+ "BWFLImportPermit.jasper");
						}
					JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
					
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;

					JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ dt.getPermitNmbr_dt().trim().replaceAll("\\s+","")+".pdf");
					
					//dt.setPdfName(dt.getAppId_dt()+ "-"+dt.getBwflID_dt()+"_"+dt.getLoginType_dt()+"ImportPermit.pdf");	
					dt.setPdfName(dt.getPermitNmbr_dt()+".pdf");
					this.updatePermit(act, dt.getRequestID_dt(),dt.getDistrictId_dt(), dt.getLoginType_dt(), dt.getAppId_dt(),dt.getPdfName());
					printFlag = true;
				} else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found"));				
					printFlag = false;

				}
			} catch (JRException e) {
				e.printStackTrace();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
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
		// =============================print report============================

		public boolean printReportFL2D(Bwfl_permit_tracking_action act, Bwfl_permit_tracking_dt dt){


			String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
			String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "Bond" + File.separator + "jasper";
			String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "Bond" + File.separator + "FL2Dpermit";
			JasperReport jasperReport = null;
			JasperPrint jasperPrint = null;
			PreparedStatement pst = null;
			Connection con = null;
			ResultSet rs = null;
			String reportQuery = null;
			boolean printFlag = false;

			try {
				con = ConnectionToDataBase.getConnection();

					                                                                                                                                           
	reportQuery = 	" SELECT DISTINCT a.import_fee_challan_no, a.spcl_fee_challan_no,                                          "+
				"(select DISTINCT amount from bwfl_license.chalan_deposit_bwfl_fl2d                                            "+
				" where chalan_no=a.import_fee_challan_no and unit_id=a.bwfl_id and                                            "+
				" unit_type=CASE WHEN a.bwfl_type=1 then 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                             "+
				" WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' WHEN a.bwfl_type=99                        "+
				" THEN 'FL2D' END) as import_chalan_amnt,                                                                      "+
				" (select DISTINCT amount from bwfl_license.chalan_deposit_bwfl_fl2d                                           "+
				"  where chalan_no=a.spcl_fee_challan_no and unit_id=a.bwfl_id and                                             "+
				"  unit_type=CASE WHEN a.bwfl_type=1 then 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                            "+
				"  WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' WHEN a.bwfl_type=99 THEN                  "+
				"  'FL2D' END) as spcl_chalan_amnt,                                                                            "+
				"  (select DISTINCT chalan_date from   bwfl_license.chalan_deposit_bwfl_fl2d                                   "+
				"   where chalan_no=a.import_fee_challan_no and unit_id=a.bwfl_id and                                          "+
				"   unit_type=CASE WHEN a.bwfl_type=1 then 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                           "+
				"   WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' WHEN a.bwfl_type=99                      "+
				"   THEN 'FL2D' END) as import_chalan_date,                                                                    "+
				"   (select DISTINCT chalan_date from  bwfl_license.chalan_deposit_bwfl_fl2d                                   "+
				"	where chalan_no=a.spcl_fee_challan_no and unit_id=a.bwfl_id and                                            "+
				"	unit_type=CASE WHEN a.bwfl_type=1 then 'BWFL2A' WHEN a.bwfl_type=2 THEN 'BWFL2B'                           "+
				"	WHEN a.bwfl_type=3 THEN 'BWFL2C' WHEN a.bwfl_type=4 THEN 'BWFL2D' WHEN a.bwfl_type=99                      "+
				"	THEN 'FL2D' END) as spcl_chalan_date,    a.id, a.district_id, a.bwfl_id, a.unit_id,                        "+
				"	a.bwfl_type, a.lic_no, a.import_fee as tot_import_fee,                                                     "+
				"	(a.duty+a.add_duty)as tot_duty, a.special_fee as tot_special_fee, a.cr_date,                               "+
				"	a.vch_approved, a.vch_status, a.deo_time, a.deo_remark, a.deo_user, a.deo_date,                            "+
				"	a.bottlng_type, a.valid_upto, a.route_road_radio, a.route_detail,                                          "+
				"	CASE WHEN a.bwfl_type=1 THEN 'BWFL2A'                                                                      "+
				"	WHEN a.bwfl_type=2 THEN 'BWFL2B'                                                                           "+
				"	WHEN a.bwfl_type=3 THEN 'BWFL2C'                                                                           "+
				"	WHEN a.bwfl_type=4 THEN 'BWFL2D'                                                                           "+
				"	WHEN a.bwfl_type=99 THEN 'FL2D' end as type,                                                               "+
				"	CASE WHEN a.route_road_radio='route' THEN 'By Train'                                                       "+
				"	WHEN a.route_road_radio='road' THEN 'By Road' end as route_type,                                           "+
				"	b.vch_firm_name,  b.vch_bond_address as vch_core_address, c.description,                                   "+
				"	CONCAT(b.vch_firm_name, '(' ,b.vch_bond_address,')')as licensee_nm_adrs,                                   "+
				"	(SELECT DISTINCT d.vch_state_name FROM public.state_ind d WHERE                                            "+
				"	 f.int_state_id=d.int_state_id)as vch_state_name,                                                          "+
				"	                                                                                                           "+
				"	  d.no_of_cases, d.no_of_bottle_per_case, d.pland_no_of_bottles, a.permit_nmbr,                            "+
				"	  d.import_fee,                               (d.duty+d.add_duty) as duty,                                 "+
				"	  d.special_fee, a.import_fee_challan_no, a.spcl_fee_challan_no,                                           "+
				"	  f.vch_applicant_name as vch_indus_name, f.vch_bond_address as vch_reg_office_add, a.consignor_nm_adrs,   "+
				"	  d.brand_id, d.pckg_id, br.brand_name,                                                                    "+
				"	  pk.package_name, (pk.permit) as duty_rate,                                                               "+
				"	  round(CAST(float8(((d.pland_no_of_bottles)*pk.quantity)/1000) as numeric), 2) as bl                      "+
				"	  FROM bwfl_license.import_permit_20_21 a, custom_bonds.custom_bonds_master b,                             "+
				"	  public.district c, bwfl_license.import_permit_dtl_20_21 d,                                               "+
				"	  custom_bonds.mst_regis_importing_unit f,                                                                 "+
				"	  distillery.brand_registration_20_21 br, distillery.packaging_details_20_21 pk                            "+
				"	  WHERE a.custom_id=b.int_id AND a.district_id=c.districtid                                                "+
				"	  AND a.id=d.fk_id AND a.district_id=d.district_id AND a.login_type=d.login_type                           "+
				"	  AND a.app_id=d.app_id  AND a.unit_id=f.int_id  and  a.custom_id=b.int_id                                 "+
				"	  AND br.brand_id=d.brand_id and br.brand_id=pk.brand_id_fk and d.pckg_id=pk.package_id                    "+
				"	  AND a.app_id="+dt.getAppId_dt()+" ";
					
				
		        System.out.println("reportQuery---------------------"+reportQuery);
				pst = con.prepareStatement(reportQuery);
				rs = pst.executeQuery();

				if (rs.next()) {
					rs = pst.executeQuery();

					Map parameters = new HashMap();
					parameters.put("REPORT_CONNECTION", con);
					//parameters.put("SUBREPORT_DIR", relativePath+File.separator);
					parameters.put("image", relativePath + File.separator);					

					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
					
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator+ "FL2DImportPermit.jasper");
					JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
					
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;

					JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ dt.getPermitNmbr_dt().trim().replaceAll("\\s+","")+".pdf");
					
					//dt.setPdfName(dt.getAppId_dt()+ "-"+dt.getBwflID_dt()+"_"+dt.getLoginType_dt()+"ImportPermit.pdf");	
					dt.setPdfName(dt.getPermitNmbr_dt()+".pdf");
					this.updatePermit(act, dt.getRequestID_dt(),dt.getDistrictId_dt(), dt.getLoginType_dt(), dt.getAppId_dt(),dt.getPdfName());
					printFlag = true;
				} else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found"));				
					printFlag = false;

				}
			} catch (JRException e) {
				e.printStackTrace();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
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
		
		public String updatePermit(Bwfl_permit_tracking_action act, int id, int districtId, String loginType, int appId, String pdfName){


			int saveStatus = 0;
			Connection con = null;
			PreparedStatement pstmt = null;			
			String updtQr = "";
			

			
			try {
				con = ConnectionToDataBase.getConnection();
				con.setAutoCommit(false);
				Date date = new Date();
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String time = sdf.format(cal.getTime());

					updtQr = 	" UPDATE bwfl_license.import_permit_20_21 "+
							 	" SET print_permit_time=?, print_permit_dt=?, print_permit_pdf=? "+
							 	" WHERE id="+id+" AND district_id="+districtId+"  " +
							 	" AND login_type='"+loginType+"' AND app_id="+appId+"  ";
										

					pstmt = con.prepareStatement(updtQr);

					//System.out.println("updtQr----------------"+updtQr);
					
					pstmt.setString(1, time);
					pstmt.setDate(2, Utility.convertUtilDateToSQLDate(new Date()));	
					pstmt.setString(3, pdfName);
					

					saveStatus = pstmt.executeUpdate();
						


				if (saveStatus > 0) {
					con.commit();
					//act.closeApplication();
					
					

				} else {
					con.rollback();

				}
			} catch (Exception se) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(se.getMessage(), se.getMessage()));
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
		
		
		// ------------------display challan details in datatable----------------------

		public ArrayList displayChalanDetailsImpl(Bwfl_permit_tracking_action act){


			ArrayList list = new ArrayList();
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int j = 1;
			String selQr = null;
			String filter = "";


			try {
				con = ConnectionToDataBase.getConnection();
                                                                                                                                           
				selQr = " SELECT DISTINCT a.int_id, b.id, a.unit_id, a.unit_type, a.amount,                                                "+
						" a.chalan_no, a.chalan_date, a.division, a.tres_id,                                                               "+
						" CASE WHEN a.chalan_no=b.import_fee_challan_no THEN CONCAT('IMPORT FEE Challan Number- ',b.import_fee_challan_no)  "+
						" WHEN a.chalan_no=b.spcl_fee_challan_no THEN CONCAT('SPECIAL FEE Challan Number- ',b.spcl_fee_challan_no) " +
						" END AS challan_nmbr, c.divname, d.tname                                                                          "+
						" FROM bwfl_license.chalan_deposit_bwfl_fl2d a, bwfl_license.import_permit_20_21 b,                                      "+
						" licence.division c, licence.treasury d                                                                           "+
						" WHERE a.unit_id=b.bwfl_id AND a.unit_type=                                                                       "+
						" CASE WHEN b.bwfl_type=1 then 'BWFL2A' WHEN b.bwfl_type=2 THEN 'BWFL2B'                                           "+
						" WHEN b.bwfl_type=3 THEN 'BWFL2C' WHEN b.bwfl_type=4 THEN 'BWFL2D' WHEN b.bwfl_type=99 THEN 'FL2D' END            "+
						" AND a.division=c.divcode AND a.tres_id=d.tcode AND c.divcode=d.divcode                                           "+
						" AND b.app_id="+act.getAppId()+"   " +
						" AND a.chalan_no in ('"+act.getImportFeeChalanNo()+"', '"+act.getSpclFeeChalanNo()+"')  ";                                    

			

				ps = con.prepareStatement(selQr);
				//System.out.println("challan query---------------" + selQr);
				rs = ps.executeQuery();

				while (rs.next()) {

					Bwfl_permit_tracking_dt dt = new Bwfl_permit_tracking_dt();

					dt.setSrNo(j);
					dt.setChallanId_dt(rs.getInt("int_id"));
					dt.setChallanNo_dt(rs.getString("challan_nmbr"));
					dt.setChallanAmnt_dt(rs.getDouble("amount"));
					dt.setDivName_dt(rs.getString("divname"));
					dt.setTreasryNm_dt(rs.getString("tname"));
					
					SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
					if (rs.getDate("chalan_date") != null) {
						String chalanDate = date.format(Utility.convertSqlDateToUtilDate(rs.getDate("chalan_date")));
						dt.setChallanDate_dt(chalanDate);
					} else {
						dt.setChallanDate_dt("");
					}


					j++;
					list.add(dt);
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
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
		
		public String getAdvancRgstrBalance(Bwfl_permit_tracking_action act){


			int id = 0;
			Connection con = null;
			PreparedStatement pstmt = null, ps2 = null;
			ResultSet rs = null, rs2 = null;
			String s = "";
			try {
				con = ConnectionToDataBase.getConnection();
				
				
			String selQr = 		" SELECT x.bwfl_id,                                                                                                    "+
								" SUM(x.bwfl_import_fee-(x.ap_import_fee+x.import_fee))as bwfl_import_fee_bal,                                         "+
								" SUM(x.bwfl_special_fee-(x.ap_special_fee+x.special_fee))as bwfl_special_fee_bal,                                     "+
								" SUM(x.fl2d_import_fee-(x.ap_import_fee+x.import_fee))as fl2d_import_fee_bal,                                         "+
								" SUM(x.fl2d_special_fee-(x.ap_special_fee+x.special_fee))as fl2d_special_fee_bal                                      "+
								" FROM                                                                                                                 "+
								" (SELECT a.int_distillery_id as bwfl_id, SUM(b.double_amt) as bwfl_import_fee, 0 as bwfl_special_fee,                 "+
								" 0 as fl2d_import_fee, 0 as fl2d_special_fee,                                                                         "+
								" 0 as ap_import_fee, 0 as ap_special_fee, 0 as import_fee, 0 as special_fee                                           "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                                "+
								" WHERE a.int_challan_id=b.int_challan_id AND a.vch_challan_type=b.vch_challan_type                                    "+
								" AND a.int_distillery_id='"+act.getBwflId()+"'                                                                        "+
								" AND b.vch_head_code in ('003900103030000','003900105040000','003900103030000') and b.vch_g6_head_code in ('01','03') "+
								" AND a.date_challan_date > '2019-06-16'                                                                               "+
								" group by bwfl_id                                                                                                     "+
								" UNION                                                                                                                "+
								" SELECT a.int_distillery_id as bwfl_id, 0 as bwfl_import_fee, SUM(b.double_amt) as bwfl_special_fee,                  "+
								" 0 as fl2d_import_fee, 0 as fl2d_special_fee,                                                                         "+
								" 0 as ap_import_fee, 0 as ap_special_fee, 0 as import_fee, 0 as special_fee                                           "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                                "+
								" WHERE a.int_challan_id=b.int_challan_id AND a.vch_challan_type=b.vch_challan_type                                    "+
								" AND a.int_distillery_id='"+act.getBwflId()+"'                                                                        "+
								" AND b.vch_head_code in ('003900105020000','003900103020000') and b.vch_g6_head_code in ('18','13')                   "+
								" AND a.date_challan_date > '2019-06-16'                                                                               "+
								" GROUP BY bwfl_id                                                                                                     "+
								" UNION                                                                                                                "+
								" SELECT a.int_distillery_id as bwfl_id, 0 as bwfl_import_fee, 0 as bwfl_special_fee,                                  "+
								" SUM(b.double_amt) as fl2d_import_fee, 0 as fl2d_special_fee,                                                         "+
								" 0 as ap_import_fee, 0 as ap_special_fee, 0 as import_fee, 0 as special_fee                                           "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                                "+
								" WHERE a.int_challan_id=b.int_challan_id AND a.vch_challan_type=b.vch_challan_type                                    "+
								" AND a.int_distillery_id='"+act.getBwflId()+"'                                                                        "+
								" AND b.vch_head_code in ('003900105040000','003900103030000','003900103030000') and b.vch_g6_head_code in ('04','05') "+
								" AND a.date_challan_date > '2019-06-16'                                                                               "+
								" GROUP BY bwfl_id                                                                                                     "+
								" UNION                                                                                                                "+
								" SELECT a.int_distillery_id as bwfl_id, 0 as bwfl_import_fee, 0 as bwfl_special_fee,                                  "+
								" 0 as fl2d_import_fee, SUM(b.double_amt) as fl2d_special_fee,                                                         "+
								" 0 as ap_import_fee, 0 as ap_special_fee, 0 as import_fee, 0 as special_fee                                           "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                                "+
								" WHERE a.int_challan_id=b.int_challan_id AND a.vch_challan_type=b.vch_challan_type                                    "+
								" AND a.int_distillery_id='"+act.getBwflId()+"'                                                                        "+
								" AND b.vch_head_code in ('003900105020000','003900103020000') and b.vch_g6_head_code in ('18','13')                   "+
								" AND a.date_challan_date > '2019-06-16'                                                                               "+
								" GROUP BY bwfl_id                                                                                                     "+
								" UNION                                                                                                                "+
								" SELECT a.bwfl_id, 0 as bwfl_import_fee, 0 as bwfl_special_fee,0 as fl2d_import_fee, 0 as fl2d_special_fee,           "+
								" SUM(a.import_fee) as ap_import_fee, SUM(COALESCE(a.special_fee,0)) as ap_special_fee,                                "+
								" 0 as import_fee, 0 as special_fee                                                                                    "+
								" FROM bwfl_license.import_permit_20_21 a                                                                                    "+
								" WHERE a.bwfl_id='"+act.getBwflId()+"' AND a.vch_approved='APPROVED'                                                  "+
								" GROUP BY a.bwfl_id                                                                                                   "+
								" UNION                                                                                                                "+
								" SELECT a.bwfl_id, 0 as bwfl_import_fee, 0 as bwfl_special_fee,0 as fl2d_import_fee, 0 as fl2d_special_fee,           "+
								" 0 as ap_import_fee, 0 as ap_special_fee,                                                                             "+
								" SUM(a.import_fee) as import_fee, SUM(a.special_fee) as special_fee                                                   "+
								" FROM bwfl_license.import_permit_20_21 a WHERE a.id='"+act.getRequestID()+"'                                                "+
								" GROUP BY a.bwfl_id)x GROUP BY x.bwfl_id  ";                                                                          
				
				
				
				/*String selQr = 	" SELECT x.bwfl_id, SUM(x.challan_duty-(x.ap_duty+x.duty))as bal_duty,                                 "+   
								" SUM(x.challan_import_fee-(x.ap_import_fee+x.import_fee))as bal_import_fees,                          "+     
								" SUM(x.challan_special_fee-(x.ap_special_fee+x.special_fee))as bal_special_fees                       "+     
								" FROM                                                                                                 "+ 
								" (SELECT a.int_distillery_id as bwfl_id, 0 as ap_import_fee, 0 as ap_duty,                            "+ 
								" 0 as ap_special_fee, 0 as import_fee, 0 as duty, 0 as special_fee,                                   "+
								" CASE                                                                                                 "+
								" WHEN b.vch_head_code='003900105010000' and 'BWFL2A'=a.vch_bwfl_shop_type THEN SUM(b.double_amt)      "+
								" WHEN b.vch_head_code='003900105010000' and 'BWFL2C'=a.vch_bwfl_shop_type THEN SUM(b.double_amt)      "+
								" WHEN b.vch_head_code='003900103010000' and 'BWFL2B'=a.vch_bwfl_shop_type THEN SUM(b.double_amt)      "+
								" WHEN b.vch_head_code='003900103010000' and 'BWFL2D'=a.vch_bwfl_shop_type                             "+
								" THEN SUM(b.double_amt) end as challan_duty,                                                          "+
								" CASE WHEN b.vch_head_code='003900105040000' and a.vch_bwfl_shop_type in ('BWFL2A','BWFL2C')          "+
								" THEN SUM(b.double_amt)                                                                               "+
								" WHEN b.vch_head_code='003900103030000' and a.vch_bwfl_shop_type in ('BWFL2B','BWFL2D')               "+
								" THEN SUM(b.double_amt) end as challan_import_fee,                                                    "+
								" CASE WHEN b.vch_head_code='003900102010000' and a.vch_bwfl_shop_type in ('BWFL2A','BWFL2C')          "+
								" THEN SUM(b.double_amt)                                                                               "+
								" WHEN b.vch_head_code='003900103030000' and a.vch_bwfl_shop_type in ('BWFL2B','BWFL2D')               "+
								" THEN SUM(b.double_amt) end as challan_special_fee                                                    "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                "+ 
								" WHERE a.int_challan_id=b.int_challan_id AND a.vch_challan_type=b.vch_challan_type                    "+
								" AND a.int_distillery_id='"+act.getBwflId()+"'                                                        "+                    
								" GROUP BY a.int_distillery_id, b.vch_head_code, a.vch_bwfl_shop_type                                  "+                       
								" UNION                                                                                                "+ 
								" SELECT a.bwfl_id, SUM(a.import_fee) as ap_import_fee , SUM(a.duty+a.add_duty) as ap_duty ,           "+ 
								" SUM(a.special_fee) as ap_special_fee, 0 as import_fee, 0 as duty, 0 as special_fee,                  "+ 
								" 0 as challan_duty, 0 as challan_import_fee, 0 as challan_special_fee                                 "+ 
								" FROM bwfl_license.import_permit a                                                                    "+
								" WHERE a.bwfl_id='"+act.getBwflId()+"' AND a.vch_approved='APPROVED'                                  "+
								" GROUP BY a.bwfl_id                                                                                   "+ 
								" UNION                                                                                                "+ 
								" SELECT a.bwfl_id, 0 as ap_import_fee, 0 as ap_duty, 0 as ap_special_fee,                             "+ 
								" SUM(a.import_fee) as import_fee , SUM(a.duty+a.add_duty) as duty, SUM(a.special_fee) as special_fee, "+ 
								" 0 as challan_duty, 0 as challan_import_fee, 0 as challan_special_fee                                 "+ 
								" FROM bwfl_license.import_permit a WHERE a.id='"+act.getRequestID()+"'                                "+                       
								" GROUP BY a.bwfl_id)x GROUP BY x.bwfl_id  ";*/

				

				pstmt = con.prepareStatement(selQr);

				rs = pstmt.executeQuery();
				
				System.out.println("==============bal recive=========="+selQr);

				while (rs.next()) {
					
					//act.setBalRgstrDuty(rs.getDouble("bal_duty"));
					act.setBalRgstrImportFee(rs.getDouble("bwfl_import_fee_bal"));
					act.setBalRgstrSpecialFee(rs.getDouble("bwfl_special_fee_bal"));
					act.setBalFL2DImportFee(rs.getDouble("fl2d_import_fee_bal"));
					act.setBalFL2DSpecialFee(rs.getDouble("fl2d_special_fee_bal"));
					
					System.out.println("setBalRgstrImportFee================"+act.getBalRgstrImportFee());
				}

			} catch (SQLException se) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(se.getMessage(), se.getMessage()));
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
		
		
		public String getAdvancRgstrBalanceForPrint(Bwfl_permit_tracking_action act){


			int id = 0;
			Connection con = null;
			PreparedStatement pstmt = null, ps2 = null;
			ResultSet rs = null, rs2 = null;
			String s = "";
			try {
				con = ConnectionToDataBase.getConnection();
				
				
			String selQr = 		" SELECT x.bwfl_id,                                                                                                    "+
								" SUM(x.bwfl_import_fee-(x.ap_import_fee+x.import_fee))as bwfl_import_fee_bal,                                         "+
								" SUM(x.bwfl_special_fee-(x.ap_special_fee+x.special_fee))as bwfl_special_fee_bal,                                     "+
								" SUM(x.fl2d_import_fee-(x.ap_import_fee+x.import_fee))as fl2d_import_fee_bal,                                         "+
								" SUM(x.fl2d_special_fee-(x.ap_special_fee+x.special_fee))as fl2d_special_fee_bal                                      "+
								" FROM                                                                                                                 "+
								" (SELECT a.int_distillery_id as bwfl_id, SUM(b.double_amt) as bwfl_import_fee, 0 as bwfl_special_fee,                 "+
								" 0 as fl2d_import_fee, 0 as fl2d_special_fee,                                                                         "+
								" 0 as ap_import_fee, 0 as ap_special_fee, 0 as import_fee, 0 as special_fee                                           "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                                "+
								" WHERE a.int_challan_id=b.int_challan_id AND a.vch_challan_type=b.vch_challan_type                                    "+
								" AND a.int_distillery_id='"+act.getBwflId()+"'                                                                        "+
								" AND b.vch_head_code in ('003900103030000','003900105040000','003900103030000') and b.vch_g6_head_code in ('01','03') "+
								" AND a.date_challan_date > '2019-06-16'                                                                               "+
								" group by bwfl_id                                                                                                     "+
								" UNION                                                                                                                "+
								" SELECT a.int_distillery_id as bwfl_id, 0 as bwfl_import_fee, SUM(b.double_amt) as bwfl_special_fee,                  "+
								" 0 as fl2d_import_fee, 0 as fl2d_special_fee,                                                                         "+
								" 0 as ap_import_fee, 0 as ap_special_fee, 0 as import_fee, 0 as special_fee                                           "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                                "+
								" WHERE a.int_challan_id=b.int_challan_id AND a.vch_challan_type=b.vch_challan_type                                    "+
								" AND a.int_distillery_id='"+act.getBwflId()+"'                                                                        "+
								" AND b.vch_head_code in ('003900105020000','003900103020000') and b.vch_g6_head_code in ('18','13')                   "+
								" AND a.date_challan_date > '2019-06-16'                                                                               "+
								" GROUP BY bwfl_id                                                                                                     "+
								" UNION                                                                                                                "+
								" SELECT a.int_distillery_id as bwfl_id, 0 as bwfl_import_fee, 0 as bwfl_special_fee,                                  "+
								" SUM(b.double_amt) as fl2d_import_fee, 0 as fl2d_special_fee,                                                         "+
								" 0 as ap_import_fee, 0 as ap_special_fee, 0 as import_fee, 0 as special_fee                                           "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                                "+
								" WHERE a.int_challan_id=b.int_challan_id AND a.vch_challan_type=b.vch_challan_type                                    "+
								" AND a.int_distillery_id='"+act.getBwflId()+"'                                                                        "+
								" AND b.vch_head_code in ('003900105040000','003900103030000','003900103030000') and b.vch_g6_head_code in ('04','05') "+
								" AND a.date_challan_date > '2019-06-16'                                                                               "+
								" GROUP BY bwfl_id                                                                                                     "+
								" UNION                                                                                                                "+
								" SELECT a.int_distillery_id as bwfl_id, 0 as bwfl_import_fee, 0 as bwfl_special_fee,                                  "+
								" 0 as fl2d_import_fee, SUM(b.double_amt) as fl2d_special_fee,                                                         "+
								" 0 as ap_import_fee, 0 as ap_special_fee, 0 as import_fee, 0 as special_fee                                           "+
								" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                                                "+
								" WHERE a.int_challan_id=b.int_challan_id AND a.vch_challan_type=b.vch_challan_type                                    "+
								" AND a.int_distillery_id='"+act.getBwflId()+"'                                                                        "+
								" AND b.vch_head_code in ('003900105020000','003900103020000') and b.vch_g6_head_code in ('18','13')                   "+
								" AND a.date_challan_date > '2019-06-16'                                                                               "+
								" GROUP BY bwfl_id                                                                                                     "+
								" UNION                                                                                                                "+
								" SELECT a.bwfl_id, 0 as bwfl_import_fee, 0 as bwfl_special_fee,0 as fl2d_import_fee, 0 as fl2d_special_fee,           "+
								" SUM(a.import_fee) as ap_import_fee, SUM(COALESCE(a.special_fee,0)) as ap_special_fee,                                "+
								" 0 as import_fee, 0 as special_fee                                                                                    "+
								" FROM bwfl_license.import_permit_20_21 a                                                                                    "+
								" WHERE a.bwfl_id='"+act.getBwflId()+"' AND a.vch_approved='APPROVED'                                                  "+		
								" GROUP BY a.bwfl_id)x GROUP BY x.bwfl_id  ";

				pstmt = con.prepareStatement(selQr);

				rs = pstmt.executeQuery();
				
				System.out.println("==============bal recive print=========="+selQr);

				while (rs.next()) {
					
					//act.setBalRgstrDuty(rs.getDouble("bal_duty"));
					act.setBalRgstrImportFee(rs.getDouble("bwfl_import_fee_bal"));
					act.setBalRgstrSpecialFee(rs.getDouble("bwfl_special_fee_bal"));
					act.setBalFL2DImportFee(rs.getDouble("fl2d_import_fee_bal"));
					act.setBalFL2DSpecialFee(rs.getDouble("fl2d_special_fee_bal"));
					
					System.out.println("setBalRgstrImportFee=======print========="+act.getBalRgstrImportFee());
				}

			} catch (SQLException se) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(se.getMessage(), se.getMessage()));
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

}
