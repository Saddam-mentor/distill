package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.action.WholesaleCancelAllGatepassAction;
import com.mentor.datatable.WholesaleCancelAllGatepassDT;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Utility;

public class WholesaleCancelAllGatepassImpl {
	
	// ------------------display data in datatable----------------------

	public ArrayList displayAllGatepassImpl(WholesaleCancelAllGatepassAction act){


		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int j = 1;
		String selQr = null;
		String filter = "";

		try {
			con = ConnectionToDataBase.getConnection();
			
				selQr = 	" SELECT a.int_fl2_fl2b_id as unit_id, a.vch_gatepass_no, a.dt_date as gatepass_date, "+
							" a.vch_from as unit_type, mst.vch_firm_name as unit_name "+
							" FROM fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a, licence.fl2_2b_2d_19_20 mst  "+
							" WHERE a.vch_finalize IS NULL AND a.int_fl2_fl2b_id=mst.int_app_id  "+
							" AND a.vch_from=mst.vch_license_type AND a.dt_date < '"+Utility.convertUtilDateToSQLDate(act.getCrDate())+"' " +
							" ORDER BY a.dt_date ";
			
			ps = con.prepareStatement(selQr);
			System.out.println("selQr---------------" + selQr);
			rs = ps.executeQuery();

			while (rs.next()) {

				
				WholesaleCancelAllGatepassDT dt = new WholesaleCancelAllGatepassDT();

				dt.setSlNo(j);
				
				dt.setUnitID_dt(rs.getInt("unit_id"));
				dt.setUnitName_dt(rs.getString("unit_name"));
				dt.setUnitType_dt(rs.getString("unit_type"));				
				dt.setGatepassNmbr_dt(rs.getString("vch_gatepass_no"));
				dt.setGatepassDate_dt(rs.getDate("gatepass_date"));



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
	
	// =====================get max id sequence==============================

		public int seqCancel() {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query = " SELECT max(seq) as id FROM fl2d.duty_cancellation_fl2_fl2b_19_20 ";
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
	
	//=====================approval for cancel Gatepass CL===================
	
	@SuppressWarnings("resource")
	public String cancelGatepass(WholesaleCancelAllGatepassAction act){

		Connection con = null;
		Connection con1 = null;
		PreparedStatement ps = null, ps2 = null, ps3 = null, ps4 = null, ps5 = null;
		PreparedStatement pstmt = null;

		ResultSet rs = null;
		String sql = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "", sql41 = "", sql42 = "", sql43 = "",sql44 = "",sql45 = "";
		int tok1 = 0;
		String gatepass = null;

		int seq = this.seqCancel();

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			con = ConnectionToDataBase.getConnection();
			con1 = ConnectionToDataBase.getConnection19_20();
			con.setAutoCommit(false);
			con1.setAutoCommit(false);
			
			String selQr = 	" SELECT a.int_fl2_fl2b_id, a.dt_date, a.vch_gatepass_no, b.seq_fl2_fl2b, b.breakage, " +
							" b.int_brand_id, b.int_pckg_id, b.dispatch_bottle  " +
							" FROM fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a, fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b  " +
							" WHERE a.int_fl2_fl2b_id=b.int_fl2_fl2b_id AND a.vch_gatepass_no=b.vch_gatepass_no " +
							" AND a.dt_date=b.dt AND a.int_fl2_fl2b_id=? AND a.vch_gatepass_no=? ";
			
			sql = 	" INSERT INTO fl2d.duty_cancellation_fl2_fl2b_19_20( " +
					" seq, vch_gatepass_no, gatepass_dt, duty_cancellation_dt_tm, db_duty_amount, vch_type, db_add_duty_amount) " +
					" VALUES (?, ?, ?, ?, ?, ?, ?) ";
						
			sql2 = 	" DELETE FROM fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 " +
					" WHERE  vch_finalize is NULL AND vch_gatepass_no=? AND int_fl2_fl2b_id=? ";
			
			sql3 = 	" DELETE FROM fl2d.fl2_stock_trxn_fl2_fl2b_19_20 " +
					" WHERE vch_gatepass_no=? AND int_fl2_fl2b_id=?  ";

			if(act.getDisplayAllGatepass().size()>0){
				for(int i=0;i<act.getDisplayAllGatepass().size();i++){
					
					WholesaleCancelAllGatepassDT dt = (WholesaleCancelAllGatepassDT) act.getDisplayAllGatepass().get(i);
					
					gatepass=dt.getGatepassNmbr_dt().trim();
					
					if(dt.isCheckBox()){						
						
						pstmt = con.prepareStatement(selQr);
						
						pstmt.setInt(1, dt.getUnitID_dt());
						pstmt.setString(2, dt.getGatepassNmbr_dt().trim());
						
						rs = pstmt.executeQuery();
						
						while (rs.next()) {
							
							dt.setSeqDt(rs.getInt("seq_fl2_fl2b"));
							dt.setBrandIdDt(rs.getInt("int_brand_id"));
							dt.setPckgIdDt(rs.getInt("int_pckg_id"));
							dt.setDispatcBotlsDt(rs.getInt("dispatch_bottle"));
							dt.setBreakageDt(rs.getInt("breakage"));
							
							
							String updtQr = " UPDATE fl2d.fl2_2b_stock_19_20 SET  " +
											" dispatchbotl=COALESCE(dispatchbotl,0)-"+ (dt.getDispatcBotlsDt() + dt.getBreakageDt()) +" " +
											" WHERE id='"+dt.getUnitID_dt()+"' AND  " +
											" brand_id='"+dt.getBrandIdDt()+"' AND pckg_id='" + dt.getPckgIdDt() + "' ";

							ps5 = con.prepareStatement(updtQr);
							
							

							System.out.println("updtQr---------------"+updtQr);

							tok1 = ps5.executeUpdate();
							
							System.out.println("first status----------" + tok1);
							
						}
						
						if (tok1 > 0) {
							tok1 = 0;

							ps = con.prepareStatement(sql);

							ps.setInt(1, seq);
							ps.setString(2, dt.getGatepassNmbr_dt().trim());
							ps.setDate(3,Utility.convertUtilDateToSQLDate(dt.getGatepassDate_dt()));
							ps.setString(4, dateFormat.format(new Date()));
							ps.setDouble(5, 0.0);
							ps.setString(6, "FL36-FL2_FL2B_CL2");
							ps.setDouble(7, 0.0);

							tok1 = ps.executeUpdate();
							seq = seq+1;

							System.out.println("second status----------" + tok1);

						}
						
						if (tok1 > 0) {
							tok1 = 0;

							ps2 = con.prepareStatement(sql2);
							
							ps2.setString(1, dt.getGatepassNmbr_dt().trim());
							ps2.setInt(2, dt.getUnitID_dt());

							tok1 = ps2.executeUpdate();

							System.out.println("third status----------" + tok1);

						}
						
						if (tok1 > 0) {
							tok1 = 0;

							ps3 = con.prepareStatement(sql3);
							
							ps3.setString(1, dt.getGatepassNmbr_dt().trim());
							ps3.setInt(2, dt.getUnitID_dt());
							
							tok1 = ps3.executeUpdate();

							System.out.println("fourth status----------" + tok1);

						}
						
						if (tok1 > 0) {

							if (dt.getUnitType_dt().equalsIgnoreCase("FL2B")) {

								sql4 = "  update bottling_unmapped.brewary_unmap_fl3 set ws_date=null,ws_gatepass=null,shop_id=null,shop_type =null" +
										" where  ws_gatepass='"+gatepass.toUpperCase().trim()+ "' ";
								sql41 = "  update bottling_unmapped.brewary_unmap_fl3a set ws_date=null,ws_gatepass=null,shop_id=null,shop_type =null" +
										" where  ws_gatepass='"+gatepass.toUpperCase().trim()+ "' ";
								sql42= "  update bottling_unmapped.bwfl set ws_date=null,ws_gatepass=null,shop_id=null,shop_type =null" +
										" where  ws_gatepass='"+gatepass.toUpperCase().trim()+ "' ";
								sql43 = "  update bottling_unmapped.fl2d set ws_date=null,ws_gatepass=null,shop_id=null,shop_type =null" +
										" where  ws_gatepass='"+gatepass.toUpperCase().trim()+ "' ";
								sql44 = "  update bottling_unmapped.disliry_unmap_fl3 set ws_date=null,ws_gatepass=null,shop_id=null,shop_type =null" +
										" where  ws_gatepass='"+gatepass.toUpperCase().trim()+ "' ";
								sql45 = "  update bottling_unmapped.disliry_unmap_fl3a set ws_date=null,ws_gatepass=null,shop_id=null,shop_type =null" +
										" where  ws_gatepass='"+gatepass.toUpperCase().trim()+ "' ";
								

							}

							else if (dt.getUnitType_dt().equalsIgnoreCase("FL2")) {

							 
								sql4 = "  update bottling_unmapped.disliry_unmap_fl3 set ws_date=null,ws_gatepass=null,shop_id=null,shop_type =null" +
										" where  ws_gatepass='"+gatepass.toUpperCase().trim()+ "' ";
								sql41 = "  update bottling_unmapped.disliry_unmap_fl3a set ws_date=null,ws_gatepass=null,shop_id=null,shop_type =null" +
										" where  ws_gatepass='"+gatepass.toUpperCase().trim()+ "' ";
								sql42= "  update bottling_unmapped.bwfl set ws_date=null,ws_gatepass=null,shop_id=null,shop_type =null" +
										" where  ws_gatepass='"+gatepass.toUpperCase().trim()+ "' ";
								sql43 = "  update bottling_unmapped.fl2d set ws_date=null,ws_gatepass=null,shop_id=null,shop_type =null" +
										" where  ws_gatepass='"+gatepass.toUpperCase().trim()+ "' ";
							}

							else if (dt.getUnitType_dt().equalsIgnoreCase("CL2"))

							{
								sql4 = "  update bottling_unmapped.disliry_unmap_cl set ws_date=null,ws_gatepass=null,shop_id=null,shop_type =null" +
										" where ws_gatepass='"+gatepass.toUpperCase().trim()+"' ";


							}else{
								tok1=0;
							}

							if (dt.getUnitType_dt().equalsIgnoreCase("CL2")) {
								
								ps3 = con1.prepareStatement(sql4);
								ps3.executeUpdate();
																
							}
							else if (dt.getUnitType_dt().equalsIgnoreCase("FL2")) {
									 	ps3 = con1.prepareStatement(sql4);
										ps3.executeUpdate();
										ps3 = con1.prepareStatement(sql41);
										ps3.executeUpdate();
										ps3 = con1.prepareStatement(sql42);
										ps3.executeUpdate();
										ps3 = con1.prepareStatement(sql43);
										ps3.executeUpdate();
								 }
							else if (dt.getUnitType_dt().equalsIgnoreCase("FL2B")) {
									 ps3 = con1.prepareStatement(sql4);
										ps3.executeUpdate();
										ps3 = con1.prepareStatement(sql41);
										ps3.executeUpdate();
										ps3 = con1.prepareStatement(sql42);
										ps3.executeUpdate();
										ps3 = con1.prepareStatement(sql43);
										ps3.executeUpdate();
										ps3 = con1.prepareStatement(sql44);
										ps3.executeUpdate();
										ps3 = con1.prepareStatement(sql45);
										ps3.executeUpdate();
								}

						
						}
						
					}
				 	
				}
			}
			
			
			if (tok1 > 0) {
				con.commit();
				con1.commit();
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Gatepass Cancelled Successfully !!! ","Gatepass Cancelled Successfully !!!"));
				
				act.reset();
				
			}

			else {
				con.rollback();
				con1.rollback();
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Gatepass Not Cancelled !!! ", "Gatepass Not Cancelled !!!"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));

		} finally {
			try {
				if (con != null)
					con.close();
				if (con1 != null)
					con1.close();
				if (ps != null)
					ps.close();
				if (ps2 != null)
					ps2.close();
				if (ps5 != null)
					ps5.close();
				if (ps3 != null)
					ps3.close();
				if (ps4 != null)
					ps4.close();

			} catch (SQLException ex) {
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(ex.getMessage(), ex.getMessage()));

			}
		}

		return "";

	}

}
