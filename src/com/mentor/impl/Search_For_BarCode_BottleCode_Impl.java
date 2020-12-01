package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mentor.action.Search_For_BarCode_BottleCode_Action;
import com.mentor.datatable.Search_For_BarCode_BottleCode_DataTable;

import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Utility;

public class Search_For_BarCode_BottleCode_Impl {

	public ArrayList bottlesDetailCaseCode(
			Search_For_BarCode_BottleCode_Action act) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selQr = null;
		int i = 1;
		String filter = "";
		String vch_license_type = "";
		String etin = "";
		String casecode = "";
		String licence_type = "";
		try {

			if (act.getRadio().equalsIgnoreCase("BRC")) {
				String datenew = act.getBottleCode().trim().substring(16, 22)
						.trim();
				datenew = "20" + datenew;
				datenew = datenew.substring(0, 4) + "-"
						+ datenew.substring(4, 6) + "-" + datenew.substring(6);
				Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(datenew);

				casecode = act.getBottleCode().trim().substring(26);
				etin = act.getBottleCode().trim().substring(0, 12);

				filter = " date_plan='"
						+ Utility.convertUtilDateToSQLDate(date1) + "' "
						+ "	and  etin='" + etin + "'" + "  and  casecode='"
						+ casecode + "' ";

				licence_type = etin.substring(3, 4);
			}
			/*
			 * else if(act.getRadio().equalsIgnoreCase("BCOD")) {
			 * filter="bottle_code='"+act.getBottleCode().trim()+"' ";
			 * 
			 * act.getBottleCode().trim().substring(0,
			 * act.getBottleCode().trim().indexOf("|"));
			 * 
			 * }
			 */

			vch_license_type = this.getEtinDetaill(etin);

			// String name_unit=vch_license_type.substring(0,
			// vch_license_type.indexOf("-"));
			// String
			// vch=vch_license_type.substring(vch_license_type.indexOf("-")+1);

			String sql = "";

			String dislery_cl =

			"SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,  "
					+ "fl11_date, fl36gatepass, fl36_date, boxing_seq , ws_gatepass, ws_date  "
					+ "FROM bottling_unmapped.disliry_unmap_cl where " + filter
			
			  + " union  " +
			  "SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,  "
			  +
			  "fl11_date, fl36gatepass, fl36_date, boxing_seq  , ws_gatepass, ws_date  "
			  +
			  "FROM bottling_unmapped.disliry_unmap_cl_backup where "+filter;
			 

			String dislery_fl3 =

			"SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, "
					+ "fl11_date, fl36gatepass, fl36_date, boxing_seq  , ws_gatepass, ws_date "
					+ "FROM bottling_unmapped.disliry_unmap_fl3 where "
					+ filter 
			
			  + " union " +
			  "SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, "
			  +
			  "fl11_date, fl36gatepass, fl36_date, boxing_seq , ws_gatepass, ws_date  "
			  +
			  "FROM bottling_unmapped.disliry_unmap_fl3_backup where "+filter
			  ;
			

			String dislery_fl3a =

			" SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,  "
					+ " fl11_date, fl36gatepass, fl36_date, boxing_seq  , ws_gatepass, ws_date  "
					+ "	 FROM bottling_unmapped.disliry_unmap_fl3a where "
					+ filter 
			  + " union  " +
			  "	 SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,  "
			  +
			  "	 fl11_date, fl36gatepass, fl36_date, boxing_seq  , ws_gatepass, ws_date  "
			  +
			  "	 FROM bottling_unmapped.disliry_unmap_fl3a_backup where "+filter;
			 

			String brewary_fl3 = "SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, "
					+ " fl11_date, fl36gatepass, fl36_date, boxing_seq  , ws_gatepass, ws_date "
					+ " FROM bottling_unmapped.brewary_unmap_fl3 where "
					+ filter
			  + " union  " +
			  "	 SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,  "
			  +
			  "	 fl11_date, fl36gatepass, fl36_date, boxing_seq   , ws_gatepass, ws_date "
			  +
			  "	 FROM bottling_unmapped.brewary_unmap_fl3_backup where "+filter ;
			 

			String brewary_fl3a = "SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, "
					+ " fl11_date, fl36gatepass, fl36_date, boxing_seq  , ws_gatepass, ws_date "
					+ " FROM bottling_unmapped.brewary_unmap_fl3a where "
					+ filter 
			  + " union  " +
			  "	 SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,  "
			  +
			  "	 fl11_date, fl36gatepass, fl36_date, boxing_seq  , ws_gatepass, ws_date  "
			  +
			  "	 FROM bottling_unmapped.brewary_unmap_fl3a_backup where "+filter
			  ;
			 

			String bwfl = " SELECT plan_id, date_plan, etin, casecode, bottle_code,"
					+ "  fl11gatepass, fl11_date, fl36gatepass, fl36_date, serial_no_start, serial_no_end  , ws_gatepass, ws_date "
					+ " FROM bottling_unmapped.bwfl where " + filter 
					+ " union  " +
					" SELECT plan_id, date_plan, etin, casecode, bottle_code,"
					+ "  fl11gatepass, fl11_date, fl36gatepass, fl36_date, serial_no_start, serial_no_end  , ws_gatepass, ws_date "
					+ " FROM bottling_unmapped.bwfl_backup where " + filter ;

			String fl2d = " SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, "
					+ " fl11_date, fl36gatepass, fl36_date, serial_no_start, serial_no_end  , ws_gatepass, ws_date "
					+ " FROM bottling_unmapped.fl2d where " + filter
					+ " union  " + 
					" SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, "
					+ " fl11_date, fl36gatepass, fl36_date, serial_no_start, serial_no_end  , ws_gatepass, ws_date "
					+ " FROM bottling_unmapped.fl2d_backup where " + filter;

			if (vch_license_type.equals("DISTILLERY")
					&& (licence_type.equals("1"))) {
				sql = dislery_fl3;
			} else if ((vch_license_type.equals("DISTILLERY"))
					&& (licence_type.equals("2"))) {
				sql = dislery_fl3a;
			} else if ((vch_license_type.equals("DISTILLERY"))
					&& (licence_type.equals("8"))) {
				sql = dislery_cl;

			} else if ((vch_license_type.equals("BREWERY"))
					&& (licence_type.equals("1"))) {
				sql = brewary_fl3;

			} else if ((vch_license_type.equals("BREWERY"))
					&& (licence_type.equals("2"))) {
				sql = brewary_fl3a;
			} else if ((vch_license_type.equalsIgnoreCase("FL2D"))
					&& (licence_type.equals("3"))) {
				sql = fl2d;
			} else if (vch_license_type.equals("BWFL")) {
				sql = bwfl;
			}

			/*
			 * if (vch_license_type.equals("Dist") &&
			 * (licence_type.equals("1"))) { sql = dislery_fl3; } else if
			 * ((vch_license_type.equals("Dist")) && (licence_type.equals("2")))
			 * { sql = dislery_fl3a; } else if
			 * ((vch_license_type.equals("Dist")) && (licence_type.equals("8")))
			 * { sql = dislery_cl;
			 * 
			 * } else if ((vch_license_type.equals("Bre")) &&
			 * (licence_type.equals("1"))) { sql = brewary_fl3;
			 * 
			 * } else if ((vch_license_type.equals("Bre")) &&
			 * (licence_type.equals("2"))) { sql = brewary_fl3a; } else if
			 * ((vch_license_type.equalsIgnoreCase("FL2D"))&&
			 * (licence_type.equals("3"))) { sql = fl2d;
			 * 
			 * } else if (vch_license_type.equals("BWFL")) { sql = bwfl; }
			 */

			conn = ConnectionToDataBase.getConnection19_20();
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();
			while (rs.next()) {

				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));

				dt.setUnit_Name(this.getUnitName(rs.getString("etin")));

				dt.setCaseCode(rs.getString("casecode"));
				dt.setBottleCode(rs.getString("bottle_code"));

				dt.setFl36Gatepass(rs.getString("fl36gatepass"));
				dt.setFl36Date(rs.getDate("fl36_date"));

				dt.setFl11gatepass(rs.getString("fl11gatepass"));
				dt.setFl11_date(rs.getDate("fl11_date"));

				dt.setWs_gatepass(rs.getString("ws_gatepass"));
				dt.setWs_date(rs.getDate("ws_date"));

				dt.setSlno(i);
				i++;
				list.add(dt);
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

	public String getUnitName(String etin) {

		String name = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			String queryList = " select  xx.id, xx.name , xx.code_generate_through  "
					+ " from "
					+ " ( "
					+ " select brewery_id as id,brewery_nm as name,code_generate_through from distillery.packaging_details_19_20 a, "
					+ " distillery.brand_registration_19_20 b,public.bre_mst_b1_lic c  "
					+ " where a.brand_id_fk=b.brand_id  "
					+ " and b.brewery_id=c.vch_app_id_f  "
					+

					" union			 "
					+

					" select distillery_id as id,vch_undertaking_name as name,code_generate_through from distillery.packaging_details_19_20 a,   "
					+ " distillery.brand_registration_19_20 b,public.dis_mst_pd1_pd2_lic c  "
					+ " where a.brand_id_fk=b.brand_id  "
					+ " and b.distillery_id=c.int_app_id_f   "
					+

					" union			 "
					+

					" select int_bwfl_id as id,vch_applicant_name as name,code_generate_through from distillery.packaging_details_19_20 a,   "
					+ " distillery.brand_registration_19_20 b,bwfl.registration_of_bwfl_lic_holder_19_20 c  "
					+ " where a.brand_id_fk=b.brand_id  "
					+ " and b.int_bwfl_id=c.int_id  "
					+

					" union		 "
					+

					" select b.int_fl2d_id as id,vch_firm_name as name,code_generate_through from distillery.packaging_details_19_20 a,  "
					+ " distillery.brand_registration_19_20 b,licence.fl2_2b_2d_19_20 c  "
					+ " where a.brand_id_fk=b.brand_id "
					+ " and b.int_fl2d_id=c.int_app_id )xx where xx.code_generate_through='"
					+ etin.trim() + "' ";

			System.out.println("etin=======" + queryList);

			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(queryList);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				name = rs.getString("name");
			}

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
		return name;

	}

	public String vchLicenseType(String etin) {

		String name = "";
		String type = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		
		
		ResultSet rs = null;
		try {

			String queryList = " select  b.vch_license_type,"
					+ " coalesce(b.int_bwfl_id,0) as int_bwfl_id, coalesce(b.int_fl2d_id,0) as int_fl2d_id,"
					+ " coalesce(b.distillery_id,0) as distillery_id,coalesce(b.brewery_id,0) as brewery_id "
					+ " from distillery.packaging_details_19_20 a, "
					+ " distillery.brand_registration_19_20 b "
					+ " where a.brand_id_fk=b.brand_id   and a.code_generate_through ='"
					+ etin.trim() + "' ";

			System.out.println("etin=======" + queryList);

			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(queryList);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				if (rs.getInt("int_bwfl_id") != 0
						&& rs.getInt("int_fl2d_id") == 0
						&& rs.getInt("distillery_id") == 0
						&& rs.getInt("brewery_id") == 0) {
					name = "BWFL";
					// type=rs.getString("vch_license_type");
				} else if (rs.getInt("int_bwfl_id") == 0
						&& rs.getInt("int_fl2d_id") != 0
						&& rs.getInt("distillery_id") == 0
						&& rs.getInt("brewery_id") == 0) {
					name = "FL2D";
					// type=rs.getString("vch_license_type");
				}

				else if (rs.getInt("int_bwfl_id") == 0
						&& rs.getInt("int_fl2d_id") == 0
						&& rs.getInt("distillery_id") != 0
						&& rs.getInt("brewery_id") == 0) {
					name = "Dist";
					// type=rs.getString("vch_license_type");
				} else if (rs.getInt("int_bwfl_id") == 0
						&& rs.getInt("int_fl2d_id") == 0
						&& rs.getInt("distillery_id") == 0
						&& rs.getInt("brewery_id") != 0) {
					name = "Bre";
					// type=rs.getString("vch_license_type");
				}

			}

			System.out.println("name============" + name);

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
		return name;

	}

	public String getEtinDetaill(String etin) {
		String sql = "select coalesce(x.mrp,0)AS mrp,coalesce(x.brand_name,'NA')AS brand_name,                                                                         "
				+ "      		coalesce(x.licencee_dtl,'NA')AS licencee_dtl,  coalesce(x.strength,'0')as strength,                                                              "
				+ "      		coalesce(x.license_category,'NA')AS license_category,                                                                                            "
				+ "      		coalesce(x.license_number)AS license_number,coalesce(x.manufacturer_details,'NA')AS manufacturer_details,                                        "
				+ "      		coalesce(x.package_name,'NA')AS package_name,coalesce(x.quantity,'0')AS quantity,                                                                "
				+ "      		coalesce(x.code_generate_through,'NA')AS code_generate_through , coalesce(x.typee,'NA')AS typee,                                                 "
				+ "      		coalesce(x.distrct,'NA')AS distrct ,                                                                                                             "
				+ "      		coalesce(x.vch_undertaking_name,'NA')AS vch_undertaking_name                                                                                     "
				+ "      		from (select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                             "
				+ "      			  license_number,manufacturer_details,                                                                                                       "
				+ "      			   case when b.package_type='1' then 'Glass Bottle'                                                                                         "
				+ "      			   when b.package_type='2' then 'CAN'                                                                                                        "
				+ "      			       when b.package_type='3' then 'Pet Bottle'                                                                                             "
				+ "      			   when b.package_type='4' then 'Tetra Pack'                                                                                                 "
				+ "      			   when b.package_type is NULL then 'NA'                                                                                                     "
				+ "      			   end as package_name,quantity,code_generate_through ,                                                                                      "
				+ "      			   c.vch_undertaking_name,'DISTILLERY' as typee  ,                                                                                           "
				+ "      			   d.description  as distrct                                                                                                                 "
				+ "      			   from  distillery.brand_registration_19_20 a,distillery.packaging_details_19_20 b ,                                                                    "
				+ "      			   public.dis_mst_pd1_pd2_lic c    left outer join public.district d   on c.vch_unit_dist=d.districtid::text                                 "
				+ "      			   where a.brand_id=b.brand_id_fk  and a.distillery_id=c.int_app_id_f   and a.license_category not in ('BEER')                               "
				+ "      			   union all                                                                                                                                 "
				+ "      			   select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                            "
				+ "      			   license_number,manufacturer_details,                                                                                                      "
				+ "      			    case when b.package_type='1' then 'Glass Bottle'                                                                                         "
				+ "      				when b.package_type='2' then 'CAN'                                                                                                       "
				+ "      				when b.package_type is NULL then 'NA'                                                                                                    "
				+ "      				when b.package_type='3' then 'Pet Bottle'                                                                                                "
				+ "      				when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                           "
				+ "      		      c.brewery_nm as vch_undertaking_name,                                                                                                      "
				+ "      				'BREWERY' as typee ,d.description  as distrct  from  distillery.brand_registration_19_20 a,distillery.packaging_details_19_20 b ,                    "
				+ "      				public.bre_mst_b1_lic c     ,public.district d                                                                                           "
				+ "      				where a.brand_id=b.brand_id_fk  and a.brewery_id=c.vch_app_id_f::numeric and a.license_category in ('BEER')                              "
				+ "      				and c.int_upkrm_district_id=d.districtid::text                                                                                                     "
				+ "      				UNION ALL                                                                                                                                "
				+ "      				select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                           "
				+ "      				license_number,manufacturer_details,                                                                                                     "
				+ "      				 case when b.package_type='1' then 'Glass Bottle'                                                                                        "
				+ "      				 when b.package_type='2' then 'CAN'                                                                                                      "
				+ "      				 when b.package_type is NULL then 'NA'                                                                                                   "
				+ "      				 when b.package_type='3' then 'Pet Bottle'                                                                                               "
				+ "      				 when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                          "
				+ "      				 c.vch_firm_name as vch_undertaking_name,'BWFL' as typee,d.description as distrct                                                        "
				+ "      				 from  distillery.brand_registration_19_20 a,distillery.packaging_details_19_20 b ,                                                                  "
				+ "      		            bwfl.registration_of_bwfl_lic_holder_19_20 c  ,public.district d                                                                                                                        "
				+ "      				 where a.brand_id=b.brand_id_fk  and a.int_bwfl_id=c.unit_id                                                                              "
				+ "      				 and c.vch_firm_district_id=d.districtid::text                                                                                           "
				+ "      				 UNION ALL                                                                                                                               "
				+ "      				 select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                          "
				+ "      				 license_number,manufacturer_details,                                                                                                   "
				+ "      				  case when b.package_type='1' then 'Glass Bottle'                                                                                       "
				+ "      				  when b.package_type='2' then 'CAN'                                                                                                     "
				+ "      				  when b.package_type is NULL then 'NA'                                                                                                  "
				+ "      				  when b.package_type='3' then 'Pet Bottle'                                                                                              "
				+ "      				  when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                         "
				+ "      		      c.vch_indus_name as vch_undertaking_name,                                                                                                   "
				+ "      				  'Fl2D' as typee ,d.description as distrct  from  distillery.brand_registration_19_20 a,                                                      "
				+ "      		      distillery.packaging_details_19_20 b ,                                                                                                           "
				+ "      				  public.other_unit_registration c  left outer join public.district d   on 0=d.districtid                                            "
				+ "      				 where a.brand_id=b.brand_id_fk  and a.int_fl2d_id=c.int_app_id_f   "
				+ "      				 UNION ALL                                                                                                                               "
				+ "      				 select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                          "
				+ "      				 license_number,manufacturer_details,                                                                                                   "
				+ "      				  case when b.package_type='1' then 'Glass Bottle'                                                                                       "
				+ "      				  when b.package_type='2' then 'CAN'                                                                                                     "
				+ "      				  when b.package_type is NULL then 'NA'                                                                                                  "
				+ "      				  when b.package_type='3' then 'Pet Bottle'                                                                                              "
				+ "      				  when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                         "
				+ "      		      c.vch_indus_name as vch_undertaking_name,                                                                                                   "
				+ "      				  'Fl2A' as typee ,d.description as distrct  from  distillery.brand_registration_19_20 a,                                                      "
				+ "      		      distillery.packaging_details_19_20 b ,                                                                                                           "
				+ "      				  public.other_unit_registration c  left outer join public.district d   on 0=d.districtid                                            "
				+ "      				 where a.brand_id=b.brand_id_fk  and a.int_fl2a_id=c.int_app_id_f) x where x.code_generate_through='"
				+ etin + "'";

		String type = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				/*
				 * job.put("etin", rs.getString("code_generate_through"));
				 * job.put("brand_name", rs.getString("brand_name"));
				 * job.put("licencee", rs.getString("licencee_dtl"));
				 * job.put("strength", rs.getString("strength"));
				 * job.put("licence_category",
				 * rs.getString("license_category")); job.put("licence_number",
				 * rs.getString("license_number"));
				 * job.put("manufacturer_detail"
				 * ,rs.getString("manufacturer_details"));
				 * job.put("package_name", rs.getString("package_name"));
				 * job.put("quantity", rs.getString("quantity"));
				 * job.put("name", rs.getString("vch_undertaking_name"));
				 */
				type = rs.getString("typee");

			}

		} catch (Exception e) {
			e.printStackTrace();

			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return type;
	}

	public ArrayList getQrDetail(Search_For_BarCode_BottleCode_Action act) {

		String etin = act.getBottleCode().trim().substring(0, 12);
		String licence_type = etin.substring(3, 4);
		
		String casecode = act.getBottleCode().trim()
				.substring(23, act.getBottleCode().trim().length() - 1);
		String casecode1 = act.getBottleCode().trim()
				.substring(21, act.getBottleCode().trim().length() - 1);

		int unitid = 0;
		int unitid1 = 0;

		try {
			if (act.getBottleCode().trim().length() == 35) {
				unitid = Integer.parseInt(act.getBottleCode().trim()
						.substring(4, 6));
				unitid1 = Integer.parseInt(act.getBottleCode().trim()
						.substring(13, 16));
			} else {
				unitid = Integer.parseInt(act.getBottleCode().trim()
						.substring(4, 6));
				unitid1 = Integer.parseInt(act.getBottleCode().trim()
						.substring(18, 21));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String plan_date = null;
		ArrayList list = new ArrayList();
		try {
			String s1 = act.getBottleCode().trim().substring(12, 18);
			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			Date d = sdf5.parse(s1);
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			plan_date = sdf6.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String vch_license_type = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Connection conn1=null;
		PreparedStatement pstmt1=null;
		ResultSet rs1=null;
		
		
		
		
		String sql = "";
//QUERY CHANGE KRAYI GYI BY PRASAHNT SIR KYO KI BWFL UNIT NAME GLT AA RHA THA MAIL PE QUERY DI H
		String sql1 = /*"select coalesce(x.mrp,0)AS mrp, coalesce(x.brand_name,'NA')AS brand_name,                                                                           "
				+ " coalesce(x.licencee_dtl,'NA')AS licencee_dtl,  coalesce(x.strength,'0')as strength,                                                                                  "
				+ " coalesce(x.license_category,'NA')AS license_category,                                                                                                                "
				+ " coalesce(x.license_number)AS license_number,coalesce(x.manufacturer_details,'NA')AS manufacturer_details,                                                            "
				+ " coalesce(x.package_name,'NA')AS package_name,coalesce(x.quantity,'0')AS quantity,                                                                                    "
				+ " coalesce(x.code_generate_through,'NA')AS code_generate_through , coalesce(x.typee,'NA')AS typee,                                                                     "
				+ " coalesce(x.distrct,'NA')AS distrct ,                                                                                                                                 "
				+ " coalesce(x.vch_undertaking_name,'NA')AS vch_undertaking_name ,                                                                                                       "
				+ " coalesce(x.distrct2,'NA')AS distrct2                                                                                                                                 "
				+ " from (select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                                  "
				+ " license_number,c.vch_undertaking_name as manufacturer_details,                                                                                                       "
				+ "  case when b.package_type='1' then 'Glass Bottle'                                                                                                                    "
				+ "  when b.package_type='2' then 'CAN'                                                                                                                                  "
				+ "      when b.package_type='3' then 'Pet Bottle'                                                                                                                       "
				+ "  when b.package_type='4' then 'Tetra Pack'                                                                                                                           "
				+ "  when b.package_type is NULL then 'NA'                                                                                                                               "
				+ "  end as package_name,quantity,code_generate_through ,                                                                                                                "
				+ "  c.vch_undertaking_name,'DISTILLERY' as typee  ,                                                                                                                     "
				+ "  d.description  as distrct  ,  d.description  as distrct2                                                                                                            "    
				+ "  from  distillery.brand_registration_19_20 a,distillery.packaging_details_19_20 b ,                                                                                  "
				+ "  public.dis_mst_pd1_pd2_lic c    left outer join public.district d   on c.vch_unit_dist=d.districtid::text                                                           "
				+ "  where a.brand_id=b.brand_id_fk     and a.license_category not in ('BEER') and a.distillery_id=c.int_app_id_f                                   "
				+ "  union all                                                                                                                                                           "
				+ "  select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                                       "
				+ "  license_number,c.brewery_nm as manufacturer_details,                                                                                                                "
				+ "   case when b.package_type='1' then 'Glass Bottle'                                                                                                                   "
				+ " when b.package_type='2' then 'CAN'                                                                                                                                   "
				+ " when b.package_type is NULL then 'NA'                                                                                                                                "
				+ " when b.package_type='3' then 'Pet Bottle'                                                                                                                            "
				+ " when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                                                       "
				+ "       c.brewery_nm as vch_undertaking_name,                                                                                                                          "
				+ " 'BREWERY' as typee ,d.description  as distrct , d.description  as distrct2 from  distillery.brand_registration_19_20 a,distillery.packaging_details_19_20 b ,        "            
				+ " public.bre_mst_b1_lic c     ,public.district d                                                                                                                       "
				+ " where a.brand_id=b.brand_id_fk  and a.brewery_id=c.vch_app_id_f::numeric and a.license_category in ('BEER')                                                          "
				+ " and c.int_upkrm_district_id=d.districtid::text   and c.etin_unit_id='"+unitid+"'                                                                                              " 
				+ " UNION ALL                                                                                                                                                            "
				+ "                       select distinct b.rounded_mrp as mrp,                                                                                                          "
				+ " a.brand_name,a.licencee_dtl,a.strength,license_category,                                                                                                             "
				+ " license_number, e.vch_indus_name as  manufacturer_details,                                                                                                           "
				+ " case when b.package_type='1' then 'Glass Bottle'                                                                                                                     "
				+ " when b.package_type='2' then 'CAN'                                                                                                                                   "
				+ " when b.package_type is NULL then 'NA'                                                                                                                                "
				+ " when b.package_type='3' then 'Pet Bottle'                                                                                                                            "
				+ " when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                                                       "
				+ " c.vch_firm_name as vch_undertaking_name,'BWFL' as typee,d.description as distrct,                                                                                    "
				+ " 	  (                                                                                                                                                              "
				+ " select vch_state_name from public.state_ind where int_state_id = e.vch_reg_office_state::int ) as distrct2                                                           "
				+ " from  distillery.brand_registration_19_20 a,distillery.packaging_details_19_20 b ,                                                                                   "
				+ "  bwfl.registration_of_bwfl_lic_holder_19_20 c  ,public.district d , public.other_unit_registration e                                                                 "                                                      
				+ "  where a.brand_id=b.brand_id_fk  and a.int_bwfl_id=c.int_id  and e.int_app_id_f= c.unit_id                                                                          "  
				+ " and c.vch_firm_district_id=d.districtid::text   and  c.int_id='"+unitid1+"'       " +
						"" +
						" union all " 
						+ "                       select distinct b.rounded_mrp as mrp,                                                                                                          "
						+ " a.brand_name,a.licencee_dtl,a.strength,license_category,                                                                                                             "
						+ " license_number, e.vch_indus_name as  manufacturer_details,                                                                                                           "
						+ " case when b.package_type='1' then 'Glass Bottle'                                                                                                                     "
						+ " when b.package_type='2' then 'CAN'                                                                                                                                   "
						+ " when b.package_type is NULL then 'NA'                                                                                                                                "
						+ " when b.package_type='3' then 'Pet Bottle'                                                                                                                            "
						+ " when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                                                       "
						+ " c.vch_firm_name as vch_undertaking_name,'BWFL' as typee,d.description as distrct,                                                                                    "
						+ " 	  (                                                                                                                                                              "
						+ " select vch_state_name from public.state_ind where int_state_id = e.vch_reg_office_state::int ) as distrct2                                                           "
						+ " from  distillery.brand_registration_19_20 a,distillery.packaging_details_19_20 b ,                                                                                   "
						+ "  bwfl.registration_of_bwfl_lic_holder_19_20 c  ,public.district d , public.other_unit_registration e                                                                 "                                                      
						+ "  where a.brand_id=b.brand_id_fk  and a.int_bwfl_id=c.int_id  and e.int_app_id_f= c.unit_id                                                                          "  
						+ " and c.vch_firm_district_id=d.districtid::text   and  c.int_id='"+unitid+"'       " 
				+ "  UNION ALL                                                                                                                                                           "
				+ "  select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                 "         
				+ "  license_number, c.vch_indus_name as manufacturer_details,                                                                                                           "
				+ "  case when b.package_type='1' then 'Glass Bottle'                                                                                                                    "
				+ "  when b.package_type='2' then 'CAN'                                                                                                                                  "
				+ "  when b.package_type is NULL then 'NA'                                                                                                                               "
				+ "  when b.package_type='3' then 'Pet Bottle'                                                                                                                           "
				+ "  when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                                                      "
				+ "  e.vch_firm_name as vch_undertaking_name,                                                                                                                            "
				+ "  'FL2D' as typee ,d.description as distrct ,                                                                                                                         "
				+ " 	  (Select                                                                                                                                                        "
				+ "  vch_country_name from public.country_mst                                                                                                                            "
				+ "  where int_country_id=c.vch_wrk_office_country::int) as distrct2 from  distillery.brand_registration_19_20 a,                                                        "
				+ "  distillery.packaging_details_19_20 b ,      licence.fl2_2b_2d_19_20 e,                                                                                              "       
				+ "  public.other_unit_registration c  , public.district d                                                                                                               "
				+ "  where e.core_district_id=d.districtid                                                                                                                               "
				+ "  and a.brand_id=b.brand_id_fk  and a.int_fl2d_id=c.int_app_id_f  and                                                                                                 "
				+ "  e.vch_license_type = 'FL2D' and e.etin_unit_id='"+unitid1+"'                                                                                                                   "
				+ "  UNION ALL                                                                                                                                                           "
				+ "  select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                                       "
				+ "  license_number,manufacturer_details,                                                                                                                                "
				+ " 	case when b.package_type='1' then 'Glass Bottle'                                                                                                                 "
				+ " 	when b.package_type='2' then 'CAN'                                                                                                                               "
				+ " 	when b.package_type is NULL then 'NA'                                                                                                                            "
				+ " 	when b.package_type='3' then 'Pet Bottle'                                                                                                                        "
				+ " 	when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                                                   "
				+ "       c.vch_indus_name as vch_undertaking_name,                                                                                                                      "
				+ " 	'Fl2A' as typee ,d.description as distrct, d.description as distrct2  from  distillery.brand_registration_19_20 a,                                               "       
				+ "       distillery.packaging_details_19_20 b ,                                                                                                                         "
				+ " 	public.other_unit_registration c  left outer join public.district d   on 0=d.districtid                                                                          "
				+ "  where a.brand_id=b.brand_id_fk  and a.int_fl2a_id=c.int_app_id_f) x where x.code_generate_through='"+act.getBottleCode().trim().substring(0, 12)+"'"; */

				
				
				
				
				
				"select coalesce(x.mrp,0)AS mrp, coalesce(x.brand_name,'NA')AS brand_name,                                                                           "
				+ " coalesce(x.licencee_dtl,'NA')AS licencee_dtl,  coalesce(x.strength,'0')as strength,                                                                                  "
				+ " coalesce(x.license_category,'NA')AS license_category,                                                                                                                "
				+ " coalesce(x.license_number)AS license_number,coalesce(x.manufacturer_details,'NA')AS manufacturer_details,                                                            "
				+ " coalesce(x.package_name,'NA')AS package_name,coalesce(x.quantity,'0')AS quantity,                                                                                    "
				+ " coalesce(x.code_generate_through,'NA')AS code_generate_through , coalesce(x.typee,'NA')AS typee,                                                                     "
				+ " coalesce(x.distrct,'NA')AS distrct ,                                                                                                                                 "
				+ " coalesce(x.vch_undertaking_name,'NA')AS vch_undertaking_name ,                                                                                                       "
				+ " coalesce(x.distrct2,'NA')AS distrct2                                                                                                                                 "
				+ " from (select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                                  "
				+ " license_number,c.vch_undertaking_name as manufacturer_details,                                                                                                       "
				+ "  case when b.package_type='1' then 'Glass Bottle'                                                                                                                    "
				+ "  when b.package_type='2' then 'CAN'                                                                                                                                  "
				+ "      when b.package_type='3' then 'Pet Bottle'                                                                                                                       "
				+ "  when b.package_type='4' then 'Tetra Pack'                                                                                                                           "
				+ "  when b.package_type is NULL then 'NA'                                                                                                                               "
				+ "  end as package_name,quantity,code_generate_through ,                                                                                                                "
				+ "  c.vch_undertaking_name,'DISTILLERY' as typee  ,                                                                                                                     "
				+ "  d.description  as distrct  ,  d.description  as distrct2                                                                                                            "    
				+ "  from  distillery.brand_registration_19_20 a,distillery.packaging_details_19_20 b ,                                                                                  "
				+ "  public.dis_mst_pd1_pd2_lic c    left outer join public.district d   on c.vch_unit_dist=d.districtid::text                                                           "
				+ "  where a.brand_id=b.brand_id_fk     and a.license_category not in ('BEER') and a.distillery_id=c.int_app_id_f                                   "
				+ "  union all                                                                                                                                                           "
				+ "  select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                                       "
				+ "  license_number,c.brewery_nm as manufacturer_details,                                                                                                                "
				+ "   case when b.package_type='1' then 'Glass Bottle'                                                                                                                   "
				+ " when b.package_type='2' then 'CAN'                                                                                                                                   "
				+ " when b.package_type is NULL then 'NA'                                                                                                                                "
				+ " when b.package_type='3' then 'Pet Bottle'                                                                                                                            "
				+ " when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                                                       "
				+ "       c.brewery_nm as vch_undertaking_name,                                                                                                                          "
				+ " 'BREWERY' as typee ,d.description  as distrct , d.description  as distrct2 from  distillery.brand_registration_19_20 a,distillery.packaging_details_19_20 b ,        "            
				+ " public.bre_mst_b1_lic c     ,public.district d                                                                                                                       "
				+ " where a.brand_id=b.brand_id_fk  and a.brewery_id=c.vch_app_id_f::numeric and a.license_category in ('BEER')                                                          "
				+ " and c.int_upkrm_district_id=d.districtid::text   and c.etin_unit_id='"+unitid+"'                                                                                              " 
				+ " UNION ALL                                                                                                                                                            "+
"	select distinct b.rounded_mrp as mrp,                                                                                            "+              
	"	a.brand_name,a.licencee_dtl,a.strength,license_category,                                                                         "+                                    
	"	license_number, e.vch_indus_name as  manufacturer_details,                                                                       "+                                    
	"	case when b.package_type='1' then 'Glass Bottle'                                                                                 "+                                    
	"	when b.package_type='2' then 'CAN'                                                                                               "+                                    
	"	when b.package_type is NULL then 'NA'                                                                                            "+                                    
	"	when b.package_type='3' then 'Pet Bottle'                                                                                        "+                                    
	"	when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                   "+                                    
	"	c.vch_firm_name as vch_undertaking_name,'BWFL' as typee,d.description as distrct,                                                "+                                    
	"	 (select vch_state_name from public.state_ind where int_state_id = e.vch_reg_office_state::int) as distrct2                      "+                                     
	"	from  distillery.brand_registration_19_20 a,distillery.packaging_details_19_20 b ,                                               "+                                    
	"	 bwfl.registration_of_bwfl_lic_holder_19_20 c  ,public.district d , public.other_unit_registration e                             "+                                                                                          
	"	 where a.brand_id=b.brand_id_fk  and a.int_bwfl_id=c.unit_id  and e.int_app_id_f= c.unit_id  and a.int_bwfl_id=e.int_app_id_f    "+
 "  UNION ALL                                                                                                                                                           "
				+ "  select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                 "         
				+ "  license_number, c.vch_indus_name as manufacturer_details,                                                                                                           "
				+ "  case when b.package_type='1' then 'Glass Bottle'                                                                                                                    "
				+ "  when b.package_type='2' then 'CAN'                                                                                                                                  "
				+ "  when b.package_type is NULL then 'NA'                                                                                                                               "
				+ "  when b.package_type='3' then 'Pet Bottle'                                                                                                                           "
				+ "  when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                                                      "
				+ "  e.vch_firm_name as vch_undertaking_name,                                                                                                                            "
				+ "  'FL2D' as typee ,d.description as distrct ,                                                                                                                         "
				+ " 	  (Select                                                                                                                                                        "
				+ "  vch_country_name from public.country_mst                                                                                                                            "
				+ "  where int_country_id=c.vch_wrk_office_country::int) as distrct2 from  distillery.brand_registration_19_20 a,                                                        "
				+ "  distillery.packaging_details_19_20 b ,      licence.fl2_2b_2d_19_20 e,                                                                                              "       
				+ "  public.other_unit_registration c  , public.district d                                                                                                               "
				+ "  where e.core_district_id=d.districtid                                                                                                                               "
				+ "  and a.brand_id=b.brand_id_fk  and a.int_fl2d_id=c.int_app_id_f  and                                                                                                 "
				+ "  e.vch_license_type = 'FL2D' and e.etin_unit_id='"+unitid1+"'                                                                                                                   "
				+ "  UNION ALL                                                                                                                                                           "
				+ "  select distinct b.rounded_mrp as mrp,a.brand_name,a.licencee_dtl,a.strength,license_category,                                                                       "
				+ "  license_number,manufacturer_details,                                                                                                                                "
				+ " 	case when b.package_type='1' then 'Glass Bottle'                                                                                                                 "
				+ " 	when b.package_type='2' then 'CAN'                                                                                                                               "
				+ " 	when b.package_type is NULL then 'NA'                                                                                                                            "
				+ " 	when b.package_type='3' then 'Pet Bottle'                                                                                                                        "
				+ " 	when b.package_type='4' then 'Tetra Pack' end as package_name,quantity,code_generate_through ,                                                                   "
				+ "       c.vch_indus_name as vch_undertaking_name,                                                                                                                      "
				+ " 	'Fl2A' as typee ,d.description as distrct, d.description as distrct2  from  distillery.brand_registration_19_20 a,                                               "       
				+ "       distillery.packaging_details_19_20 b ,                                                                                                                         "
				+ " 	public.other_unit_registration c  left outer join public.district d   on 0=d.districtid                                                                          "
				+ "  where a.brand_id=b.brand_id_fk  and a.int_fl2a_id=c.int_app_id_f) x where x.code_generate_through='"+act.getBottleCode().trim().substring(0, 12)+"'";		
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
		String brewary_fl3 =

		" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3 where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'   "
				+ "  union all "
				+

				" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3 where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "' " +
						"union " +
				" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'   "
				+ "  union all "
				+

				" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		String brewary_fl3a =

		" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3a where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ " union all "
				+

				" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3a where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "' " +
						" union " +
				" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3a_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ " union all "
				+

				" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3a_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		String dislery_cl =

		" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_cl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+

				" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_cl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'" +
				" union " +
				" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_cl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+

				" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_cl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		String dislery_fl3 =

		" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3 where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3 where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'" +
						" union SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		String dislery_fl3a =

		" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'" +
						" union SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		String bwfl = " SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all "
				+ " SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'" +
						" union " +
				" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all "
				+ " SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		String fl2d = " SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.fl2d where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  x.bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.fl2d where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  x.bottle_codee='" + casecode1 + "'" +
				" union " +
				" SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.fl2d_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  x.bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.fl2d_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  x.bottle_codee='" + casecode1 + "'";

		;
try{
		conn1=ConnectionToDataBase.getConnection();
		pstmt1=conn1.prepareStatement(sql1);
		rs1=pstmt1.executeQuery();
System.out.println("etin query execute sql1 "+sql1 );
		if(rs1.next())
		{
		//vch_license_type = this.getEtinDetaill(etin);
			vch_license_type =	rs1.getString("typee");
			
			System.out.println("etin query execute reult vch_license_type "+rs1.getString("typee"));
		System.out.println("this.getEtinDetaill(etin)==============="
				+ vch_license_type);

		if (vch_license_type.equals("DISTILLERY") && (licence_type.equals("1"))) {
			sql = dislery_fl3;
		} else if ((vch_license_type.equals("DISTILLERY"))
				&& (licence_type.equals("2"))) {
			sql = dislery_fl3a;
		} else if ((vch_license_type.equals("DISTILLERY"))
				&& (licence_type.equals("8"))) {
			sql = dislery_cl;

		} else if ((vch_license_type.equals("BREWERY"))
				&& (licence_type.equals("1"))) {
			sql = brewary_fl3;

		} else if ((vch_license_type.equals("BREWERY"))
				&& (licence_type.equals("2"))) {
			sql = brewary_fl3a;
		} else if ((vch_license_type.equalsIgnoreCase("FL2D"))
				&& (licence_type.equals("3"))) {
			sql = fl2d;
		} else if (vch_license_type.equals("BWFL")) {
			sql = bwfl;
		}

		try {
			conn = ConnectionToDataBase.getConnection19_20();

			pstmt = conn.prepareStatement(sql);

			// pstmt.setString(1, etin.trim());
			rs = pstmt.executeQuery();

			System.out.println(etin.trim() + "===sql===" + sql);

			int i = 1;
			while (rs.next()) {

				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));

				//dt.setUnit_Name(this.getUnitName(rs.getString("etin")));
				dt.setUnit_Name(rs1.getString("vch_undertaking_name"));
				dt.setCaseCode(rs.getString("casecode"));
				dt.setBottleCode(rs.getString("bottle_codee"));
				dt.setFl36Gatepass(rs.getString("fl36gatepass"));
				dt.setFl36Date(rs.getDate("fl36_date"));

				dt.setFl11gatepass(rs.getString("fl11gatepass"));
				dt.setFl11_date(rs.getDate("fl11_date"));

				dt.setWs_gatepass(rs.getString("ws_gatepass"));
				dt.setWs_date(rs.getDate("ws_date"));

				dt.setSlno(i);
				i++;
				list.add(dt);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
		}
		
}catch(Exception e)
		{
			e.printStackTrace();
			
		}finally {

			try {
				if (conn != null)
					conn.close();
				
				if (conn1 != null)
					conn1.close();
				
				if(pstmt1!=null)pstmt1.close();
				if(rs1!=null)rs1.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
