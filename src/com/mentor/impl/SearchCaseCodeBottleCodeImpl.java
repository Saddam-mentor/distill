package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mentor.action.SearchCaseCodeBottleCodeAction;
import com.mentor.datatable.Search_For_BarCode_BottleCode_DataTable;
import com.mentor.resource.ConnectionToDataBase;

public class SearchCaseCodeBottleCodeImpl {

	public void searchBottleCode(SearchCaseCodeBottleCodeAction action,
			String bottleCode) {
		try {
			String type = checkType(bottleCode);
			Date date = null;
			Date brewery_start_date_20_21 = null;
			Date other_unit_start_date_20_21 = null;
			String last_digit = bottleCode.substring(bottleCode.length() - 1,
					bottleCode.length());

			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			try {
				if (bottleCode.length() == 35) {
					date = sdf.parse(bottleCode.substring(16, 22));
					brewery_start_date_20_21 = sdf.parse("200322");
					other_unit_start_date_20_21 = sdf.parse("200401");
					System.out.println("Date 11 " + date);
				} else if (bottleCode.length() == 32) {
					date = sdf.parse(bottleCode.substring(12, 18));
					brewery_start_date_20_21 = sdf.parse("200322");
					other_unit_start_date_20_21 = sdf.parse("200401");
					System.out.println("Date222  " + date + "last digit "
							+ last_digit);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (type.equals("B") && bottleCode != null) {

				if (bottleCode.length() == 32
						&& (date.after(brewery_start_date_20_21) || date
								.compareTo(other_unit_start_date_20_21) == 0)
						&& last_digit.equals("2")) {
					action.setList(getBrewaryBottleDetail_20_21(bottleCode,
							"BREWERY"));
				} else {
					action.setList(getBrewaryBottleDetail_19_20(bottleCode,
							"BREWERY"));
				}
			}
			if (type.equals("D") && bottleCode != null) {

				if (bottleCode.length() == 32
						&& date.before(other_unit_start_date_20_21)) {
					action.setList(getDisteleryBottleDetail_19_20(bottleCode,
							"DISTILLERY"));
					System.out.println("old");
				} else {
					action.setList(getDisteleryBottleDetail_20_21(bottleCode,
							"DISTILLERY"));
				}
			}
			if (type.equals("BWFL") && bottleCode != null)

			{

				if (bottleCode.length() == 32
						&& date.before(other_unit_start_date_20_21)) {
					action.setList(getBwflBottleDetail_19_20(bottleCode, "BWFL"));
				} else {
					action.setList(getBwflBottleDetail_20_21(bottleCode, "BWFL"));
				}
			}
			if (type.equals("OtherUnit")||type.equals("IMPORTUNIT") && bottleCode != null) {

				if (bottleCode.length() == 32
						&& date.before(other_unit_start_date_20_21)) {
					action.setList(getOtherUnitOrImportUnitDetailBottleDetail_19_20(
							bottleCode, type));
				} else {
					action.setList(getOtherUnitOrImportUnitDetailBottleDetail_20_21(bottleCode, type));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchCaseCode(SearchCaseCodeBottleCodeAction action,
			String casecode) {
		try {
			String type = checkType(casecode);

			Date date = null;
			Date brewery_start_date_20_21 = null;
			Date other_unit_start_date_20_21 = null;
			System.out.println("caseno  " + casecode);
			String last_digit = casecode.substring(casecode.length() - 1,
					casecode.length());

			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			try {
				if (casecode.length() == 35) {
					date = sdf.parse(casecode.substring(16, 22));
					brewery_start_date_20_21 = sdf.parse("200322");
					other_unit_start_date_20_21 = sdf.parse("200401");
					System.out.println("Date 11 " + date);
				} else if (casecode.length() == 32) {
					date = sdf.parse(casecode.substring(12, 18));
					brewery_start_date_20_21 = sdf.parse("200322");
					other_unit_start_date_20_21 = sdf.parse("200401");
					System.out.println("Date222  " + date + "last digit "
							+ last_digit);

				}

				if (type.equals("B") && casecode != null) {

					if (casecode.length() == 35
							&& (date.after(brewery_start_date_20_21) || date
									.compareTo(other_unit_start_date_20_21) == 0)
							&& last_digit.equals("2")) {

						action.setList(getBrewaryCaseDetail_20_21(casecode,
								"BREWERY"));

					} else {
						action.setList(getBrewaryCaseDetail_19_20(casecode,
								"BREWERY"));
					}
				}
				if (type.equals("D") && casecode != null) {

					if (casecode.length() == 35
							&& date.before(other_unit_start_date_20_21)) {

						System.out.println("hi atul come");
						action.setList(getDisteleryCaseDetail_19_20(casecode,
								"DISTILLERY"));
					} else {
						action.setList(getDisteleryCaseDetail_20_21(casecode,
								"DISTILLERY"));
					}
				}
				if (type.equals("BWFL") && casecode != null)

				{
					if (casecode.length() == 35
							&& date.before(other_unit_start_date_20_21)) {
						action.setList(getBwflCaseDetail_19_20(casecode, "BWFL"));
					} else {
						action.setList(getBwflCaseDetail_20_21(casecode, "BWFL"));
					}
				}
				if (type.equals("OtherUnit")||type.equals("IMPORTUNIT") && casecode != null) {
					if (casecode.length() == 35
							&& date.before(other_unit_start_date_20_21)) {
						action.setList(getOtherUnitOrImportUnitDetail_19_20(
								casecode, type));
					} else {
						action.setList(getOtherUnitOrImportUnitDetail_20_21(
								casecode, type));
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList getBrewaryBottleDetail_19_20(String caseno, String type)
			throws SQLException {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(23, caseno.length() - 1);
		String casecode1 = caseno.substring(21, caseno.length());

		String sql = "";
		int i = 1;
		ArrayList list = new ArrayList();
		String response = "";
		String plan_date = null;

		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(12, 18));
			plan_date = sdf6.format(date);

		} catch (Exception e) {

			e.printStackTrace();
		}

		String brewary_fl3 =

		" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
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

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3 where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
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

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		String brewary_fl3a =

		" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
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

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3a where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
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

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3a_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		try {

			if ((type.equals("BREWERY"))
					&& (licence_type.equalsIgnoreCase("1"))) {
				sql = brewary_fl3;

			} else if ((type.equals("BREWERY"))
					&& (licence_type.equalsIgnoreCase("2"))) {
				sql = brewary_fl3a;
			}
			conn = ConnectionToDataBase.getConnection3();
			conn1 = ConnectionToDataBase.getConnection();

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_19_20(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return list;
	}

	/*
	 * @author Date Purpose Atul 22-05-2020 This code is Used For
	 * SearchCaseCodeBottleCode
	 */

	public ArrayList getBrewaryBottleDetail_20_21(String caseno, String type)
			throws SQLException {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(23, caseno.length() - 1);
		String casecode1 = caseno.substring(21, caseno.length());
		ArrayList list = new ArrayList();
		int i = 0;

		String sql = "";

		String response = "";
		String plan_date = null;

		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(12, 18));
			plan_date = sdf6.format(date);

		} catch (Exception e) {

			e.printStackTrace();
		}

		String brewary_fl3 =

		" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
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

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3 where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
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

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		String brewary_fl3a =

		" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
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

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3a where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
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

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.brewary_unmap_fl3a_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		try {

			if ((type.equals("BREWERY"))
					&& (licence_type.equalsIgnoreCase("1"))) {
				sql = brewary_fl3;

			} else if ((type.equals("BREWERY"))
					&& (licence_type.equalsIgnoreCase("2"))) {
				sql = brewary_fl3a;
			}
			conn = ConnectionToDataBase.getConnection_20_21();
			conn1 = ConnectionToDataBase.getConnection();

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_20_21(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return list;
	}

	/*
	 * @Author Date Purpose
	 * 
	 * Atul 22-05-2020 This code is used for SearchCaseCodeBottleCode
	 */

	public ArrayList getDisteleryBottleDetail_20_21(String caseno, String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(23, caseno.length() - 1);
		String casecode1 = caseno.substring(21, caseno.length());
		int i = 1;
		ArrayList list = new ArrayList();
		String sql = "";

		String plan_date = null;
		String response = "";
		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(12, 18));
			plan_date = sdf6.format(date);

			System.out.println(("Plan Date" + plan_date));

		} catch (Exception e) {
			e.printStackTrace();

		}

		String disliry_cl =

		" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_cl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_cl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
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

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_cl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		String disliry_fl3 =

		" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
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
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3 where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		String disliry_fl3a =

		" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		try {

			if ((type.equals("DISTILLERY")) && (licence_type.equals("1"))) {
				sql = disliry_fl3;

			} else if ((type.equals("DISTILLERY"))
					&& (licence_type.equals("2"))) {
				sql = disliry_fl3a;

			} else if ((type.equals("DISTILLERY"))
					&& (licence_type.equals("3"))) {
				sql = disliry_cl;

			}

			conn = ConnectionToDataBase.getConnection_20_21();
			conn1 = ConnectionToDataBase.getConnection();

			pstmt = conn.prepareStatement(sql);
			System.out.println(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_20_21(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return list;

	}

	public ArrayList getDisteleryBottleDetail_19_20(String caseno, String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(23, caseno.length() - 1);
		String casecode1 = caseno.substring(21, caseno.length());
		ArrayList list = new ArrayList();
		int i = 1;
		String sql = "";

		String plan_date = null;

		String response = "";
		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(12, 18));
			plan_date = sdf6.format(date);

			System.out.println("Plan Date" + plan_date);

		} catch (Exception e) {
			e.printStackTrace();

		}

		String disliry_cl =

		" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_cl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_cl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
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

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_cl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		String disliry_fl3 =

		" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3 where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3 where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		String disliry_fl3a =

		" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date, boxing_seq                      "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq                                                                                              "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		try {

			if ((type.equals("DISTILLERY")) && (licence_type.equals("1"))) {
				sql = disliry_fl3;

			} else if ((type.equals("DISTILLERY"))
					&& (licence_type.equals("2"))) {
				sql = disliry_fl3a;

			} else if ((type.equals("DISTILLERY"))
					&& (licence_type.equals("3"))) {
				sql = disliry_cl;

			}

			conn = ConnectionToDataBase.getConnection3();
			conn1 = ConnectionToDataBase.getConnection();

			pstmt = conn.prepareStatement(sql);
			System.out.println(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_19_20(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return list;
	}

	public ArrayList getOtherUnitOrImportUnitDetailBottleDetail_19_20(
			String caseno, String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;

		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(23, caseno.length() - 1);
		String casecode1 = caseno.substring(21, caseno.length());
		int i = 1;
		ArrayList list = new ArrayList();
		if (caseno.length() == 34) {
			casecode = caseno.substring(25, caseno.length() - 1);
		}

		String sql = "";

		String plan_date = null;

		String response = "";
		int planid = 0;

		String fl2d_name = "";
		String fl2d_district = "";
		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(12, 18));
			plan_date = sdf6.format(date);

		} catch (Exception e) {
			e.printStackTrace();

		}
		String fl2d = " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.fl2d where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  x.bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.fl2d where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  x.bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.fl2d_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  x.bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.fl2d_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  x.bottle_codee='" + casecode1 + "'";

		;

		String bwfl = " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all "
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all "
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		try {
			conn = ConnectionToDataBase.getConnection3();
			conn1 = ConnectionToDataBase.getConnection();

			sql = fl2d;

			pstmt = conn.prepareStatement(sql);
			System.out.println(sql);

			rs = pstmt.executeQuery();

			pstmt1 = conn.prepareStatement(bwfl);
			System.out.println(sql);

			rs1 = pstmt1.executeQuery();

			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_19_20(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
			if (rs1.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs1.getInt("plan_id"));
				dt.setPlanDate(rs1.getDate("date_plan"));

				dt.setEtinNmbr(rs1.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_19_20(rs1
							.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

				dt.setCaseCode(rs1.getString("casecode"));
				dt.setBottleCode(rs1.getString("bottle_code"));

				dt.setFl36Gatepass(rs1.getString("fl36gatepass"));
				dt.setFl36Date(rs1.getDate("fl36_date"));

				dt.setFl11gatepass(rs1.getString("fl11gatepass"));
				dt.setFl11_date(rs1.getDate("fl11_date"));

				dt.setWs_gatepass(rs1.getString("ws_gatepass"));
				dt.setWs_date(rs1.getDate("ws_date"));

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return list;
	}

	/*
	 * @Author Date Purpose Atul 22-05-2020 This code is used to search case
	 * code bottle code
	 */

	public ArrayList getOtherUnitOrImportUnitDetailBottleDetail_20_21(
			String caseno, String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;

		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(23, caseno.length() - 1);
		String casecode1 = caseno.substring(21, caseno.length());
		int i = 1;
		ArrayList list = new ArrayList();
		if (caseno.length() == 34) {
			casecode = caseno.substring(25, caseno.length() - 1);
		}

		String sql = "";

		String plan_date = null;

		String response = "";
		int planid = 0;

		String fl2d_name = "";
		String fl2d_district = "";
		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(12, 18));
			plan_date = sdf6.format(date);

		} catch (Exception e) {
			e.printStackTrace();

		}
		String fl2d = " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.fl2d where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  x.bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.fl2d where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  x.bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.fl2d_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  x.bottle_codee='"
				+ casecode
				+ "'"
				+ "union all"
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.fl2d_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  x.bottle_codee='" + casecode1 + "'";

		;

		String bwfl = " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all "
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all "
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		try {
			conn = ConnectionToDataBase.getConnection_20_21();
			conn1 = ConnectionToDataBase.getConnection();

			sql = fl2d;

			pstmt = conn.prepareStatement(sql);
			System.out.println(sql);

			rs = pstmt.executeQuery();

			pstmt1 = conn.prepareStatement(bwfl);
			System.out.println(sql);

			rs1 = pstmt1.executeQuery();

			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_19_20(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
			if (rs1.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs1.getInt("plan_id"));
				dt.setPlanDate(rs1.getDate("date_plan"));

				dt.setEtinNmbr(rs1.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_19_20(rs1
							.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

				dt.setCaseCode(rs1.getString("casecode"));
				dt.setBottleCode(rs1.getString("bottle_code"));

				dt.setFl36Gatepass(rs1.getString("fl36gatepass"));
				dt.setFl36Date(rs1.getDate("fl36_date"));

				dt.setFl11gatepass(rs1.getString("fl11gatepass"));
				dt.setFl11_date(rs1.getDate("fl11_date"));

				dt.setWs_gatepass(rs1.getString("ws_gatepass"));
				dt.setWs_date(rs1.getDate("ws_date"));

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return list;
	}


	public ArrayList getBwflBottleDetail_19_20(String caseno, String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(23, caseno.length() - 1);
		String casecode1 = caseno.substring(21, caseno.length());
		ArrayList list = new ArrayList();
		int i = 1;
		String sql = "";

		String plan_date = null;

		String response = "";
		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(12, 18));
			plan_date = sdf6.format(date);

		} catch (Exception e) {
			e.printStackTrace();

		}
		String bwfl = " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all "
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all "
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		try {
			if (type.equals("BWFL")) {
				sql = bwfl;
			}

			conn = ConnectionToDataBase.getConnection3();
			conn1 = ConnectionToDataBase.getConnection();

			pstmt = conn.prepareStatement(sql);
			System.out.println((sql));

			rs = pstmt.executeQuery();
			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_19_20(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return list;

	}

	/*
	 * @Author Date Purpose
	 * 
	 * 
	 * Atul 22-05-2020 this code is used to Search Case Code And Bottle Code
	 */

	public ArrayList getBwflBottleDetail_20_21(String caseno, String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(23, caseno.length() - 1);
		String casecode1 = caseno.substring(21, caseno.length());
		int i = 1;
		String sql = "";
		ArrayList list = new ArrayList();

		String plan_date = null;

		String response = "";
		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(12, 18));
			plan_date = sdf6.format(date);

		} catch (Exception e) {
			e.printStackTrace();

		}
		String bwfl = " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all "
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode1
				+ "' union "
				+

				" SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='"
				+ casecode
				+ "'"
				+ "union all "
				+ " SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, x.bottle_codee, fl11gatepass,fl11_date, fl36gatepass, fl36_date                     "
				+ " from(SELECT bottle_code,recv_id,ws_date,ws_gatepass,shop_id,shop_type,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,regexp_split_to_table(bottle_code , '[\\|s,]+')as  bottle_codee,  "
				+ " fl11_date, fl36gatepass, fl36_date                                                                                              "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'   and date_plan='"
				+ plan_date
				+ "' ) x where  bottle_codee='" + casecode1 + "'";

		try {
			if (type.equals("BWFL")) {
				sql = bwfl;
			}

			conn = ConnectionToDataBase.getConnection_20_21();
			conn1 = ConnectionToDataBase.getConnection();

			pstmt = conn.prepareStatement(sql);
			System.out.println(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_20_21(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return list;

	}

	/*
	 * @author date Purpose atul 22-05-2020 This Code is written to Search Case
	 * code BottleCode page
	 */
	public ArrayList getBrewaryCaseDetail_20_21(String caseno, String type)
			throws SQLException {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(26, caseno.length());

		String sql = "";
		ArrayList list = new ArrayList();
		int i = 1;
		String response = "";
		String plan_date = null;

		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(16, 22));
			plan_date = sdf6.format(date);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String brewary_fl3 = "SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.brewary_unmap_fl3 where etin='"
				+ etin
				+ "' and  casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union "
				+

				" SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.brewary_unmap_fl3_backup where etin='"
				+ etin
				+ "' and  casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "' ";

		String brewary_fl3a = "SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.brewary_unmap_fl3a where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union  "
				+

				"  SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.brewary_unmap_fl3a_backup where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";

		try {

			if ((type.equals("BREWERY"))
					&& (licence_type.equalsIgnoreCase("1"))) {
				sql = brewary_fl3;

			} else if ((type.equals("BREWERY"))
					&& (licence_type.equalsIgnoreCase("2"))) {
				sql = brewary_fl3a;
			}
			conn = ConnectionToDataBase.getConnection_20_21();

			conn1 = ConnectionToDataBase.getConnection();

			System.out.println("Brewery Case Details");

			System.out.println("Query 1 :" + sql);

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_20_21(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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

		} finally {

			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return list;
	}

	public ArrayList getBrewaryCaseDetail_19_20(String caseno, String type)
			throws SQLException {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(26, caseno.length());

		String sql = "";
		int i = 1;
		ArrayList list = new ArrayList();
		String response = "";
		String plan_date = null;

		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(16, 22));
			plan_date = sdf6.format(date);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String brewary_fl3 = "SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.brewary_unmap_fl3 where etin='"
				+ etin
				+ "' and  casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union "
				+

				" SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.brewary_unmap_fl3_backup where etin='"
				+ etin
				+ "' and  casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "' ";

		String brewary_fl3a = "SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.brewary_unmap_fl3a where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union  "
				+

				"  SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.brewary_unmap_fl3a_backup where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";

		try {

			if ((type.equals("BREWERY"))
					&& (licence_type.equalsIgnoreCase("1"))) {
				sql = brewary_fl3;

			} else if ((type.equals("BREWERY"))
					&& (licence_type.equalsIgnoreCase("2"))) {
				sql = brewary_fl3a;
			}
			conn = ConnectionToDataBase.getConnection3();

			conn1 = ConnectionToDataBase.getConnection();

			System.out.println("Brewery Case Details");

			System.out.println("Query 1 :" + sql);

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_19_20(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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

		} finally {

			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return list;
	}

	public ArrayList getDisteleryCaseDetail_19_20(String caseno, String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(26, caseno.length());
		ArrayList list = new ArrayList();
		int i = 1;
		String sql = "";

		String plan_date = null;

		String response = "";

		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(16, 22));
			plan_date = sdf6.format(date);

			System.out.println("Plan Date" + plan_date);

		} catch (Exception e) {

			e.printStackTrace();
		}

		String disliry_fl3 = "SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.disliry_unmap_fl3 where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union "
				+ "SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.disliry_unmap_fl3_backup where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";

		String disliry_fl3a = "SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union  "
				+

				" SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a_backup where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";

		String disliry_cl = "SELECT plan_id, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date "
				+ " FROM bottling_unmapped.disliry_unmap_cl where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union  "
				+ "  SELECT plan_id, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date "
				+ " FROM bottling_unmapped.disliry_unmap_cl_backup where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";

		try {

			if ((type.equals("DISTILLERY")) && (licence_type.equals("1"))) {
				sql = disliry_fl3;

			} else if ((type.equals("DISTILLERY"))
					&& (licence_type.equals("2"))) {
				sql = disliry_fl3a;

			} else if ((type.equals("DISTILLERY"))
					&& (licence_type.equals("3"))) {
				sql = disliry_cl;

			}

			System.out.println("Query 1 :" + sql);

			conn = ConnectionToDataBase.getConnection3();
			conn1 = ConnectionToDataBase.getConnection();

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_19_20(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return list;
	}

	/*
	 * @author date purpose atul 22-05-2020 this code is written for search
	 * casecode and bottlecode page
	 */

	public ArrayList getDisteleryCaseDetail_20_21(String caseno, String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(26, caseno.length());

		String sql = "";
		int i = 1;
		ArrayList list = new ArrayList();
		String plan_date = null;

		String response = "";

		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(16, 22));
			plan_date = sdf6.format(date);

			System.out.println("Plan Date" + plan_date);

		} catch (Exception e) {

			e.printStackTrace();
		}

		String disliry_fl3 = "SELECT ws_gatepass,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.disliry_unmap_fl3 where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union "
				+ "SELECT ws_gatepass,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.disliry_unmap_fl3_backup where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";

		String disliry_fl3a = "SELECT ws_gatepass,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union  "
				+

				" SELECT ws_gatepass,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date, boxing_seq "
				+ " FROM bottling_unmapped.disliry_unmap_fl3a_backup where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";

		String disliry_cl = "SELECT ws_gatepass,plan_id, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date "
				+ " FROM bottling_unmapped.disliry_unmap_cl where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union  "
				+ "  SELECT ws_gatepass,plan_id, fl11gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, "
				+ " fl11_date, fl36gatepass, fl36_date "
				+ " FROM bottling_unmapped.disliry_unmap_cl_backup where etin='"
				+ etin
				+ "' and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";

		try {

			if ((type.equals("DISTILLERY")) && (licence_type.equals("1"))) {
				sql = disliry_fl3;

			} else if ((type.equals("DISTILLERY"))
					&& (licence_type.equals("2"))) {
				sql = disliry_fl3a;

			} else if ((type.equals("DISTILLERY"))
					&& (licence_type.equals("3"))) {
				sql = disliry_cl;

			}

			System.out.println("Query 1 :" + sql);

			conn = ConnectionToDataBase.getConnection_20_21();
			conn1 = ConnectionToDataBase.getConnection();

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_20_21(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return list;
	}

	public ArrayList getOtherUnitOrImportUnitDetail_19_20(String caseno,
			String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;

		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(26, caseno.length());

		String sql = "";
		int i = 1;
		ArrayList list = new ArrayList();
		String plan_date = null;

		String response = "";
		int planid = 0;
		int k = 0;
		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(16, 22));
			plan_date = sdf6.format(date);

		} catch (Exception e) {

			e.printStackTrace();
		}
		String fl2d = " SELECT ws_gatepass,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.fl2d where etin='"
				+ etin
				+ "'  and casecode= '"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union "
				+ " SELECT ws_gatepass,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.fl2d_backup where etin='"
				+ etin
				+ "'  and casecode= '"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";
		;

		String bwfl = " SELECT ws_gatepass,ws_date,recv_id,shop_type,shop_id, plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'  and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union "
				+ " SELECT ws_gatepass,ws_date,recv_id,shop_type,shop_id, plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'  and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";

		try {
			conn = ConnectionToDataBase.getConnection3();
			conn1 = ConnectionToDataBase.getConnection();

			sql = fl2d;

			pstmt = conn.prepareStatement(sql);
			System.out.println("Query 1" + sql);

			rs = pstmt.executeQuery();

			pstmt1 = conn.prepareStatement(bwfl);
			System.out.println("Query 1" + bwfl);

			rs1 = pstmt1.executeQuery();

			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_19_20(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
			if (rs1.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs1.getInt("plan_id"));
				dt.setPlanDate(rs1.getDate("date_plan"));

				dt.setEtinNmbr(rs1.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_19_20(rs1.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

				dt.setCaseCode(rs1.getString("casecode"));
				dt.setBottleCode(rs1.getString("bottle_code"));

				dt.setFl36Gatepass(rs1.getString("fl36gatepass"));
				dt.setFl36Date(rs1.getDate("fl36_date"));

				dt.setFl11gatepass(rs1.getString("fl11gatepass"));
				dt.setFl11_date(rs1.getDate("fl11_date"));

				dt.setWs_gatepass(rs1.getString("ws_gatepass"));
				dt.setWs_date(rs1.getDate("ws_date"));

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}

		return list;
	}

	public ArrayList getOtherUnitOrImportUnitDetail_20_21(String caseno,
			String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;

		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(26, caseno.length());

		String sql = "";
		int i = 1;
		ArrayList list = new ArrayList();
		String plan_date = null;

		String response = "";
		int planid = 0;
		int k = 0;
		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(16, 22));
			plan_date = sdf6.format(date);

		} catch (Exception e) {

			e.printStackTrace();
		}
		String fl2d = " SELECT ws_gatepass,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.fl2d where etin='"
				+ etin
				+ "'  and casecode= '"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union "
				+ " SELECT ws_gatepass,plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.fl2d_backup where etin='"
				+ etin
				+ "'  and casecode= '"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";
		;

		String bwfl = " SELECT ws_gatepass,ws_date,recv_id,shop_type,shop_id, plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'  and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union "
				+ " SELECT ws_gatepass,ws_date,recv_id,shop_type,shop_id, plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'  and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";

		try {
			conn = ConnectionToDataBase.getConnection_20_21();
			conn1 = ConnectionToDataBase.getConnection();

			sql = fl2d;

			pstmt = conn.prepareStatement(sql);
			System.out.println("Query 1" + sql);

			rs = pstmt.executeQuery();

			pstmt1 = conn.prepareStatement(bwfl);
			System.out.println("Query 1" + bwfl);

			rs1 = pstmt1.executeQuery();

			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_20_21(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
			if (rs1.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs1.getInt("plan_id"));
				dt.setPlanDate(rs1.getDate("date_plan"));

				dt.setEtinNmbr(rs1.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_20_21(rs1.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

				dt.setCaseCode(rs1.getString("casecode"));
				dt.setBottleCode(rs1.getString("bottle_code"));

				dt.setFl36Gatepass(rs1.getString("fl36gatepass"));
				dt.setFl36Date(rs1.getDate("fl36_date"));

				dt.setFl11gatepass(rs1.getString("fl11gatepass"));
				dt.setFl11_date(rs1.getDate("fl11_date"));

				dt.setWs_gatepass(rs1.getString("ws_gatepass"));
				dt.setWs_date(rs1.getDate("ws_date"));

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}

		return list;
	}

	/*
	 * @author @Date @purpose
	 * 
	 * atul 22-05-2020 This code is Developed For FL2d case Through
	 * SearchCaseCodeBottleCode Page
	 */
	public String getFl2dCaseDetail_20_21(String caseno, String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(26, caseno.length());

		String sql = "";

		String plan_date = null;

		String response = "";

		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(16, 22));
			plan_date = sdf6.format(date);

		} catch (Exception e) {

			e.printStackTrace();
		}
		String fl2d = " SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.fl2d where etin='"
				+ etin
				+ "'  and casecode= '"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union "
				+ " SELECT plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass,recv_id,ws_date,ws_gatepass,shop_id,shop_type, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.fl2d_backup where etin='"
				+ etin
				+ "'  and casecode= '"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";
		;

		try {
			conn = ConnectionToDataBase.getConnection_20_21();
			conn1 = ConnectionToDataBase.getConnection();

			sql = fl2d;

			pstmt = conn.prepareStatement(sql);
			System.out.println("Query 1" + sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
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
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}

		return response;
	}

	/*
	 * 
	 * @author Date Purpose
	 * 
	 * atul 22-05-2020 This code is written for bwfl case detail search through
	 * page
	 */

	public ArrayList getBwflCaseDetail_20_21(String caseno, String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(26, caseno.length());

		String sql = "";
		int i = 1;
		ArrayList list = new ArrayList();
		String plan_date = null;

		String response = "";

		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(16, 22));
			plan_date = sdf6.format(date);

		} catch (Exception e) {

			e.printStackTrace();
		}
		String bwfl = " SELECT ws_gatepass,ws_date,recv_id,shop_type,shop_id, plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'  and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union "
				+ " SELECT ws_gatepass,ws_date,recv_id,shop_type,shop_id, plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'  and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";

		try {
			if (type.equals("BWFL")) {
				sql = bwfl;
			}

			conn = ConnectionToDataBase.getConnection_20_21();
			conn1 = ConnectionToDataBase.getConnection();

			pstmt = conn.prepareStatement(sql);
			System.out.println(("Query 1" + bwfl));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_20_21(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return list;

	}

	public ArrayList getBwflCaseDetail_19_20(String caseno, String type) {

		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String etin = caseno.substring(0, 12);
		String licence_type = etin.substring(3, 4);
		String casecode = caseno.substring(26, caseno.length());

		String sql = "";
		int i = 1;
		ArrayList list = new ArrayList();
		String plan_date = null;

		String response = "";

		try {

			SimpleDateFormat sdf5 = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf5.parse(caseno.substring(16, 22));
			plan_date = sdf6.format(date);

		} catch (Exception e) {

			e.printStackTrace();
		}
		String bwfl = " SELECT ws_gatepass,ws_date,recv_id,shop_type,shop_id, plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.bwfl where etin='"
				+ etin
				+ "'  and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date
				+ "' union "
				+ " SELECT ws_gatepass,ws_date,recv_id,shop_type,shop_id, plan_id, date_plan, etin, casecode, bottle_code, fl11gatepass, fl11_date, fl36gatepass, fl36_date, serial_no_start, serial_no_end "
				+ " FROM bottling_unmapped.bwfl_backup where etin='"
				+ etin
				+ "'  and casecode='"
				+ casecode
				+ "'  and date_plan='"
				+ plan_date + "'";

		try {
			if (type.equals("BWFL")) {
				sql = bwfl;
			}

			conn = ConnectionToDataBase.getConnection3();
			conn1 = ConnectionToDataBase.getConnection();

			pstmt = conn.prepareStatement(sql);
			System.out.println(("Query 1" + bwfl));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Search_For_BarCode_BottleCode_DataTable dt = new Search_For_BarCode_BottleCode_DataTable();
				dt.setPlanId(rs.getInt("plan_id"));
				dt.setPlanDate(rs.getDate("date_plan"));

				dt.setEtinNmbr(rs.getString("etin"));
				try {
					dt.setUnit_Name(this.getUnitName_19_20(rs.getString("etin")));
				} catch (Exception e) {
					e.printStackTrace();
				}

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
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				conn1.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return list;

	}

	/*
	 * Check Unit Type
	 */

	/*
	 * @Author Date Purpose
	 * 
	 * Atul 22-05-2020 For Searching case code bottle code in bottling dataabse
	 */

	public String checkType(String casecode) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String type = "";
		String sql =

		"select distinct  a.unit_type from distillery.brand_registration_19_20 a , distillery.packaging_details_19_20 b "
				+ " where a.brand_id=b.brand_id_fk and b.code_generate_through='"
				+ casecode.substring(0, 12)
				+ "'"
				+ "union all "
				+ "select distinct  a.unit_type from distillery.brand_registration_20_21 a , distillery.packaging_details_20_21 b "
				+ " where a.brand_id=b.brand_id_fk and b.code_generate_through='"
				+ casecode.substring(0, 12) + "'";
		try {

			// System.out.println("sqllll "+sql);
			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				type = rs.getString("unit_type");
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
		return type;
	}

	public String getUnitName_19_20(String etin) {

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

	public String getUnitName_20_21(String etin) {

		String name = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			String queryList = " select  xx.id, xx.name , xx.code_generate_through  "
					+ " from "
					+ " ( "
					+ " select brewery_id as id,brewery_nm as name,code_generate_through from distillery.packaging_details_20_21 a, "
					+ " distillery.brand_registration_20_21 b,public.bre_mst_b1_lic c  "
					+ " where a.brand_id_fk=b.brand_id  "
					+ " and b.brewery_id=c.vch_app_id_f  "
					+

					" union			 "
					+

					" select distillery_id as id,vch_undertaking_name as name,code_generate_through from distillery.packaging_details_20_21 a,   "
					+ " distillery.brand_registration_20_21 b,public.dis_mst_pd1_pd2_lic c  "
					+ " where a.brand_id_fk=b.brand_id  "
					+ " and b.distillery_id=c.int_app_id_f   "
					+

					" union			 "
					+

					" select int_bwfl_id as id,vch_applicant_name as name,code_generate_through from distillery.packaging_details_20_21 a,   "
					+ " distillery.brand_registration_20_21 b,bwfl.registration_of_bwfl_lic_holder_19_20 c  "
					+ " where a.brand_id_fk=b.brand_id  "
					+ " and b.int_bwfl_id=c.int_id  "
					+

					" union		 "
					+

					" select b.int_fl2d_id as id,vch_firm_name as name,code_generate_through from distillery.packaging_details_20_21 a,  "
					+ " distillery.brand_registration_20_21 b,licence.fl2_2b_2d_20_21 c  "
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

}
