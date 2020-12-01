package com.mentor.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import com.mentor.action.Ena_purchase_tracking_Action;
import com.mentor.resource.ConnectionToDataBase;


public class Ena_purchase_tracking_impl {
	
	
	//==============Import data 
	
		public boolean getDataimport(Ena_purchase_tracking_Action act) {

			String id = "";
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query = "";
			boolean flag=false;

		query = " select  a.digital_sign_pdf,a.later_pdf ,a.date as user_date,a.user3_qty as user_qty,a.digital_sign_dt ,a.permit_no,a.aec_acceptance,a.imp_country,a.imp_state,a.imp_consent_letter_date,a.imp_distillery_nm,a.imp_distillery_address,a.imp_consent_letter_no,        "+
				" case when a.type='WUP' then (select  x.vch_undertaking_name FROM   public.dis_mst_pd1_pd2_lic x   WHERE x.int_app_id_f= a.from_dis_id) when a.type='OUP' then (select  x.vch_undertaking_name FROM   public.dis_mst_pd1_pd2_lic x   WHERE x.int_app_id_f= a.from_dis_id)  else a.imp_distillery_nm end as from_dis,"
				+ " case when 	a.imp_state>0 and a.imp_country=0 then	(SELECT  'From Other State -'||vch_state_name FROM public.state_ind c "
				+ " where c.int_state_id=a.imp_state ) when a.imp_state=0 and a.imp_country>0 then	"
				+ " (SELECT 'From Other Country - '||vch_country_name FROM public.country_mst d where d.int_country_id=a.imp_country)"
				+ "  when coalesce(a.imp_state,0)=0 and coalesce(a.imp_country,0)=0 and a.type='WUP' then 'For UP'  when coalesce(a.imp_state,0)=0 and coalesce(a.imp_country,0)=0 and a.type='OUP' then 'For Export'  end as typee,"  
				+ "  " +
				" a.cap_imp_dist,a.imp_consent_letter,a.req_id, case  when a.type='OUP' then a.purchaser_oup  else  b.vch_undertaking_name end as vch_undertaking_name ,   "+
				" a.ena,a.letter_no,a.letter_dt,a.enatype,a.purpose,a.instaldcap_potable,                                                                                  "+
				" a.instaldcap_ind,a.production_potable,a.production_ind, a.production_ind_seller,a.production_potable_seller,a.instaldcap_ind_seller,                     "+
				" a.instaldcap_potable_seller,a.purchase_potable_seller,a.purchase_ind_seller,a.sale_prtble_indus_cap,                                                     "+
				" case when 	a.imp_state>0 and a.imp_country=0 then	(SELECT  vch_state_name FROM public.state_ind c                                                    "+
				" where c.int_state_id=a.imp_state ) when a.imp_state=0 and a.imp_country>0 then	                                                                       "+
				" (SELECT vch_country_name FROM public.country_mst d where d.int_country_id=a.imp_country)                                                                 "+
				" when a.imp_state=0 and a.imp_country=0 then 'For UP' end as type                                                                                         "+
				" , a.purchase_potable,a.purchase_ind ,a.later_pdf                                                                                                         "+
				" FROM  distillery.online_ena_purchase a                                                                                                                   "+
				" left outer join  public.dis_mst_pd1_pd2_lic b                                                                                                            "+
				" on a.login_dis_id=b.int_app_id_f                                                                                                                         "+
				" WHERE user2_date is not null and coalesce(approve_flag,'X') in ('A') and digital_sign_dt is not null " +
				" and a.permit_no ='"+act.getPermit_no()+"'                "+
				" and (a.type NOT IN ('OUP', 'WUP') or a.type is null) ";                                                                                                    

			
	      // System.out.println("===========datatable---import================="+query);
			try 
			{
				con = ConnectionToDataBase.getConnection();
				pstmt = con.prepareStatement(query);

				rs = pstmt.executeQuery();

				if (rs.next()) {
					
					act.setPdfName(rs.getString("later_pdf"));
					act.setPermit(rs.getString("digital_sign_pdf"));
					act.setFrom_distillery(rs.getString("from_dis"));
					act.setReq_date(rs.getDate("user_date"));
					act.setPermit_date(rs.getDate("digital_sign_dt"));
					act.setPermit_no(rs.getString("permit_no"));
					act.setReq_id(rs.getInt("req_id"));
					act.setApproving_qty(rs.getDouble("user_qty"));
					act.setRequested_qty(rs.getDouble("ena"));
					act.setBy_distillery(rs.getString("vch_undertaking_name"));
					act.setNoc_no(rs.getString("letter_no"));
					act.setProdindstrial(rs.getDouble("production_ind"));
					act.setProdpotable(rs.getDouble("production_potable"));
					act.setIndustrialpotable(rs.getDouble("instaldcap_ind"));
					act.setInstaldpotable(rs.getDouble("instaldcap_potable"));
					act.setPurchsepotable(rs.getDouble("purchase_potable"));
					act.setPurchsindustrial(rs.getDouble("purchase_ind"));
					act.setRad1(rs.getString("enatype"));
					act.setRad2(rs.getString("purpose"));
					act.setNoc_date(rs.getDate("letter_dt"));
					// ///-----------------rahul 05-12-2019
					act.setProdindstrial_sel(rs.getDouble("production_ind_seller"));
					act.setProdpotable_sel(rs
							.getDouble("production_potable_seller"));
					act.setIndustrialpotable_sel(rs
							.getDouble("instaldcap_ind_seller"));
					act.setInstaldpotable_sel(rs
							.getDouble("instaldcap_potable_seller"));
					act.setPurchsepotable_sel(rs
							.getDouble("purchase_potable_seller"));
					act.setPurchsindustrial_sel(rs.getDouble("purchase_ind_seller"));
					act.setPdf( ((rs.getString("imp_consent_letter_no"))));
					act.setCapacity_import(rs.getDouble("cap_imp_dist"));
					act.setExport_dist_no(rs.getString("imp_consent_letter_no"));
					act.setDated(rs.getDate("imp_consent_letter_date"));
					act.setDist_nam(rs.getString("imp_distillery_nm"));
					act.setDist_add(rs.getString("imp_distillery_address"));
					act.setType(rs.getString("type"));

					if (rs.getDate("imp_consent_letter_date") != null) {
						act.setFlg(true);
					} else {
						act.setFlg(false);
					}

					if (rs.getString("sale_prtble_indus_cap") != null) {
						if (rs.getString("sale_prtble_indus_cap").equalsIgnoreCase(
								"P")) {
							act.setSalefrom("Portable Capacity");
						} else if (rs.getString("sale_prtble_indus_cap")
								.equalsIgnoreCase("I")) {
							act.setSalefrom("Industrial Capacity");
						}
					} else {

					}act.setAcc_pdfName(rs.getString("aec_acceptance"));
			
					flag=true ;
				
				    }
				else
					
				{
					act.reset();
					flag=false ;
					
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
							" This is not a Valid Permit Number !!"," This is not a Valid Permit Number!!"));
				}	
			}
			catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
					if (rs != null)
						con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			return flag;

		}

		
		
	///------------------ for export 
		
		
		public boolean getDataexport(Ena_purchase_tracking_Action act) {

			String id = "";
			ArrayList list = new ArrayList();
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query = "";
			boolean flag=false;
			
          query = "  select  a.digital_sign_pdf,a.later_pdf ,a.date as user_date,a.user3_qty as user_qty,a.digital_sign_dt ,a.permit_no,a.aec_acceptance,a.imp_country,a.imp_state,a.imp_consent_letter_date,a.imp_distillery_nm,a.imp_distillery_address,a.imp_consent_letter_no,      "+
        		  " case when a.type='WUP' then (select  x.vch_undertaking_name FROM   public.dis_mst_pd1_pd2_lic x   WHERE x.int_app_id_f= a.from_dis_id) when a.type='OUP' then (select  x.vch_undertaking_name FROM   public.dis_mst_pd1_pd2_lic x   WHERE x.int_app_id_f= a.from_dis_id)  else a.imp_distillery_nm end as from_dis,"
					+ " case when 	a.imp_state>0 and a.imp_country=0 then	(SELECT  'From Other State -'||vch_state_name FROM public.state_ind c "
					+ " where c.int_state_id=a.imp_state ) when a.imp_state=0 and a.imp_country>0 then	"
					+ " (SELECT 'From Other Country - '||vch_country_name FROM public.country_mst d where d.int_country_id=a.imp_country)"
					+ "  when coalesce(a.imp_state,0)=0 and coalesce(a.imp_country,0)=0 and a.type='WUP' then 'For UP'  when coalesce(a.imp_state,0)=0 and coalesce(a.imp_country,0)=0 and a.type='OUP' then 'For Export'  end as typee,"  
					+ "  " +
        		 "	a.cap_imp_dist,a.imp_consent_letter,a.req_id, case  when a.type='OUP' then a.purchaser_oup  else  b.vch_undertaking_name end as vch_undertaking_name ,  "+
            	"	a.ena,a.letter_no,a.letter_dt,a.enatype,a.purpose,a.instaldcap_potable,                                                                                 "+
            	"	a.instaldcap_ind,a.production_potable,a.production_ind, a.production_ind_seller,a.production_potable_seller,a.instaldcap_ind_seller,                    "+
            	"	a.instaldcap_potable_seller,a.purchase_potable_seller,a.purchase_ind_seller,a.sale_prtble_indus_cap,                                                    "+
            	"	case when 	a.imp_state>0 and a.imp_country=0 then	(SELECT  vch_state_name FROM public.state_ind c                                                     "+
            	"	where c.int_state_id=a.imp_state ) when a.imp_state=0 and a.imp_country>0 then	                                                                        "+
            	"	(SELECT vch_country_name FROM public.country_mst d where d.int_country_id=a.imp_country)                                                                "+
            	"	when a.imp_state=0 and a.imp_country=0 then 'For UP' end as type                                                                                        "+
            	"	, a.purchase_potable,a.purchase_ind ,a.later_pdf                                                                                                        "+
            	"	FROM  distillery.online_ena_purchase a                                                                                                                  "+
            	"	left outer join  public.dis_mst_pd1_pd2_lic b                                                                                                           "+
            	"	on a.login_dis_id=b.int_app_id_f                                                                                                                        "+
            	"	WHERE user2_date is not null and coalesce(approve_flag,'X') in ('A') and digital_sign_dt is not null " +
            	"   and a.permit_no ='"+act.getPermit_no()+"'               "+
            	"	and a.type='OUP' ";
                  
			
	// System.out.println("===========datatable---export================="+query);
			try {
				con = ConnectionToDataBase.getConnection();
				pstmt = con.prepareStatement(query);

				rs = pstmt.executeQuery();

				if (rs.next()) {
					
					act.setPdfName(rs.getString("later_pdf"));
					act.setPermit(rs.getString("digital_sign_pdf"));
					act.setFrom_distillery(rs.getString("from_dis"));
					act.setReq_date(rs.getDate("user_date"));
					act.setPermit_date(rs.getDate("digital_sign_dt"));
					act.setPermit_no(rs.getString("permit_no"));
					act.setReq_id(rs.getInt("req_id"));
					act.setApproving_qty(rs.getDouble("user_qty"));
					act.setRequested_qty(rs.getDouble("ena"));
					act.setBy_distillery(rs.getString("vch_undertaking_name"));
					act.setNoc_no(rs.getString("letter_no"));
					act.setProdindstrial(rs.getDouble("production_ind"));
					act.setProdpotable(rs.getDouble("production_potable"));
					act.setIndustrialpotable(rs.getDouble("instaldcap_ind"));
					act.setInstaldpotable(rs.getDouble("instaldcap_potable"));
					act.setPurchsepotable(rs.getDouble("purchase_potable"));
					act.setPurchsindustrial(rs.getDouble("purchase_ind"));
					act.setRad1(rs.getString("enatype"));
					act.setRad2(rs.getString("purpose"));
					act.setNoc_date(rs.getDate("letter_dt"));
					// ///-----------------rahul 05-12-2019
					act.setProdindstrial_sel(rs.getDouble("production_ind_seller"));
					act.setProdpotable_sel(rs
							.getDouble("production_potable_seller"));
					act.setIndustrialpotable_sel(rs
							.getDouble("instaldcap_ind_seller"));
					act.setInstaldpotable_sel(rs
							.getDouble("instaldcap_potable_seller"));
					act.setPurchsepotable_sel(rs
							.getDouble("purchase_potable_seller"));
					act.setPurchsindustrial_sel(rs.getDouble("purchase_ind_seller"));
					act.setPdf( ((rs.getString("imp_consent_letter_no"))));
					act.setCapacity_import(rs.getDouble("cap_imp_dist"));
					act.setExport_dist_no(rs.getString("imp_consent_letter_no"));
					act.setDated(rs.getDate("imp_consent_letter_date"));
					act.setDist_nam(rs.getString("imp_distillery_nm"));
					act.setDist_add(rs.getString("imp_distillery_address"));
					act.setType(rs.getString("type"));

					if (rs.getDate("imp_consent_letter_date") != null) {
						act.setFlg(true);
					} else {
						act.setFlg(false);
					}

					if (rs.getString("sale_prtble_indus_cap") != null) {
						if (rs.getString("sale_prtble_indus_cap").equalsIgnoreCase(
								"P")) {
							act.setSalefrom("Portable Capacity");
						} else if (rs.getString("sale_prtble_indus_cap")
								.equalsIgnoreCase("I")) {
							act.setSalefrom("Industrial Capacity");
						}
					} else {

					}act.setAcc_pdfName(rs.getString("aec_acceptance"));
			
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
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
					if (rs != null)
						con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			return flag;

		}
				
				
				
		///------------------ Within UP
		
				public boolean getDataup(Ena_purchase_tracking_Action act) {

					String id = "";
					ArrayList list = new ArrayList();
					Connection con = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String query = "";
					String filter = "";
					boolean flag=false;

				query = " select  a.digital_sign_pdf,a.later_pdf,a.date as user_date,a.user3_qty as user_qty ,a.digital_sign_dt,a.permit_no , a.aec_acceptance,a.imp_country,a.imp_state,a.imp_consent_letter_date,a.imp_distillery_nm,a.imp_distillery_address,a.imp_consent_letter_no,       "+
						" case when a.type='WUP' then (select  x.vch_undertaking_name FROM   public.dis_mst_pd1_pd2_lic x   WHERE x.int_app_id_f= a.from_dis_id) when a.type='OUP' then (select  x.vch_undertaking_name FROM   public.dis_mst_pd1_pd2_lic x   WHERE x.int_app_id_f= a.from_dis_id)  else a.imp_distillery_nm end as from_dis,"
						+ " case when 	a.imp_state>0 and a.imp_country=0 then	(SELECT  'From Other State -'||vch_state_name FROM public.state_ind c "
						+ " where c.int_state_id=a.imp_state ) when a.imp_state=0 and a.imp_country>0 then	"
						+ " (SELECT 'From Other Country - '||vch_country_name FROM public.country_mst d where d.int_country_id=a.imp_country)"
						+ "  when coalesce(a.imp_state,0)=0 and coalesce(a.imp_country,0)=0 and a.type='WUP' then 'For UP'  when coalesce(a.imp_state,0)=0 and coalesce(a.imp_country,0)=0 and a.type='OUP' then 'For Export'  end as typee,"  
						+ "  " +
						" a.cap_imp_dist,a.imp_consent_letter,a.req_id, case  when a.type='OUP' then a.purchaser_oup  else  b.vch_undertaking_name end as vch_undertaking_name ,  "+
						" a.ena,a.letter_no,a.letter_dt,a.enatype,a.purpose,a.instaldcap_potable,                                                                                 "+
						" a.instaldcap_ind,a.production_potable,a.production_ind, a.production_ind_seller,a.production_potable_seller,a.instaldcap_ind_seller,                    "+
						" a.instaldcap_potable_seller,a.purchase_potable_seller,a.purchase_ind_seller,a.sale_prtble_indus_cap,                                                    "+
						" case when 	a.imp_state>0 and a.imp_country=0 then	(SELECT  vch_state_name FROM public.state_ind c                                                   "+
						" where c.int_state_id=a.imp_state ) when a.imp_state=0 and a.imp_country>0 then	                                                                      "+
						" (SELECT vch_country_name FROM public.country_mst d where d.int_country_id=a.imp_country)                                                                "+
						" when a.imp_state=0 and a.imp_country=0 then 'For UP' end as type                                                                                        "+
						" , a.purchase_potable,a.purchase_ind ,a.later_pdf                                                                                                        "+
						" FROM  distillery.online_ena_purchase a                                                                                                                  "+
						" left outer join  public.dis_mst_pd1_pd2_lic b                                                                                                           "+
						" on a.login_dis_id=b.int_app_id_f                                                                                                                        "+
						" WHERE user2_date is not null and coalesce(approve_flag,'X') in ('A') and digital_sign_dt is not null and a.permit_no ='"+act.getPermit_no()+"'               "+
						" and a.type='WUP' ";

				try {
						con = ConnectionToDataBase.getConnection();
						pstmt = con.prepareStatement(query);

						rs = pstmt.executeQuery();

						if (rs.next()) {
							
							act.setPdfName(rs.getString("later_pdf"));
							act.setPermit(rs.getString("digital_sign_pdf"));
							act.setFrom_distillery(rs.getString("from_dis"));
							act.setReq_date(rs.getDate("user_date"));
							act.setFrom_distillery(rs.getString("from_dis"));
							act.setPermit_date(rs.getDate("digital_sign_dt"));
							act.setPermit_no(rs.getString("permit_no"));
							act.setReq_id(rs.getInt("req_id"));
							act.setApproving_qty(rs.getDouble("user_qty"));
							act.setRequested_qty(rs.getDouble("ena"));
							act.setBy_distillery(rs.getString("vch_undertaking_name"));
							act.setNoc_no(rs.getString("letter_no"));
							act.setProdindstrial(rs.getDouble("production_ind"));
							act.setProdpotable(rs.getDouble("production_potable"));
							act.setIndustrialpotable(rs.getDouble("instaldcap_ind"));
							act.setInstaldpotable(rs.getDouble("instaldcap_potable"));
							act.setPurchsepotable(rs.getDouble("purchase_potable"));
							act.setPurchsindustrial(rs.getDouble("purchase_ind"));
							act.setRad1(rs.getString("enatype"));
							act.setRad2(rs.getString("purpose"));
							act.setNoc_date(rs.getDate("letter_dt"));
							// ///-----------------rahul 05-12-2019
							act.setProdindstrial_sel(rs.getDouble("production_ind_seller"));
							act.setProdpotable_sel(rs
									.getDouble("production_potable_seller"));
							act.setIndustrialpotable_sel(rs
									.getDouble("instaldcap_ind_seller"));
							act.setInstaldpotable_sel(rs
									.getDouble("instaldcap_potable_seller"));
							act.setPurchsepotable_sel(rs
									.getDouble("purchase_potable_seller"));
							act.setPurchsindustrial_sel(rs.getDouble("purchase_ind_seller"));
							act.setPdf( ((rs.getString("imp_consent_letter_no"))));
							act.setCapacity_import(rs.getDouble("cap_imp_dist"));
							act.setExport_dist_no(rs.getString("imp_consent_letter_no"));
							act.setDated(rs.getDate("imp_consent_letter_date"));
							act.setDist_nam(rs.getString("imp_distillery_nm"));
							act.setDist_add(rs.getString("imp_distillery_address"));
							act.setType(rs.getString("type"));

							if (rs.getDate("imp_consent_letter_date") != null) {
								act.setFlg(true);
							} else {
								act.setFlg(false);
							}

							if (rs.getString("sale_prtble_indus_cap") != null) {
								if (rs.getString("sale_prtble_indus_cap").equalsIgnoreCase(
										"P")) {
									act.setSalefrom("Portable Capacity");
								} else if (rs.getString("sale_prtble_indus_cap")
										.equalsIgnoreCase("I")) {
									act.setSalefrom("Industrial Capacity");
								}
							} else {

							}act.setAcc_pdfName(rs.getString("aec_acceptance"));
					
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
						e.printStackTrace();
					} finally {
						try {
							if (pstmt != null)
								pstmt.close();
							if (con != null)
								con.close();
							if (rs != null)
								con.close();
						} catch (SQLException se) {
							se.printStackTrace();
						}
					}
					return flag;

				}	
	
	}
