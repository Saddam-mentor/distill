package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mentor.action.HomePageAction;
import com.mentor.datatable.HomePageDatatable;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class HomePageImpl {

	public ArrayList getDisplayList() {
		ArrayList list = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT text,pdf from currentaffair order by id desc";
		try {
			// System.out.println("query"+query);
			list = new ArrayList();
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			int i = 1;
			while (rs.next()) {
				if (i < 11) {
					HomePageDatatable dt = new HomePageDatatable();
					dt.setText(rs.getString("text"));
					dt.setPdfName(rs.getString("pdf"));
					list.add(dt);
					i++;
				} else {
					break;
				}

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
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public ArrayList getSgrmillList() {
		ArrayList list = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT ('SM-'||vch_app_id_f || ' - ' || sugarmill_nm ) as code  	FROM public.sugarmill_mst_sm1_lic where finalise='F' order by sugarmill_nm asc";
		try {

			list = new ArrayList();
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				HomePageDatatable dt = new HomePageDatatable();
				dt.setSgtext(rs.getString("code"));

				list.add(dt);

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
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public ArrayList getDistilryList() {
		ArrayList list = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT ('D-'||int_app_id_f || ' - '||vch_undertaking_name) as code, vch_undertaking_name FROM public.dis_mst_pd1_pd2_lic where vch_finalize='F' order by vch_undertaking_name ";
		try {

			list = new ArrayList();
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				HomePageDatatable dt = new HomePageDatatable();
				dt.setDttext(rs.getString("code"));

				list.add(dt);

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
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public ArrayList getOtherList() {
		ArrayList list = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT  case when vch_indus_type='1' then ('C-'||int_app_id_f || ' - '||vch_indus_name)  else ('Y-'||int_app_id_f || ' - '||vch_indus_name)end as code, vch_indus_name FROM public.mst_industry_register where vch_verify_flag='V' order by vch_indus_name ";
		try {

			list = new ArrayList();
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				HomePageDatatable dt = new HomePageDatatable();
				dt.setOttext(rs.getString("code"));

				list.add(dt);

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
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public String getDashboard1Details(HomePageAction ac) {

		String news = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ConnectionToDataBase.getConnection();
			String query = "select sum(b.int_cap)   "
					+ "	from public.sugarmill_mst_sm1_lic a   ,public.sugar_molasses_cap b "
					+ "	where a.vch_verify_flag='V' and a.vch_app_id_f=b.int_sgmill_id";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setTotstoraecap(rs.getDouble(1));

			}
			rs = null;
			pstmt = null;

			query = "select  sum(c.mollases_opening_bal) "
					+ "	from public.sugarmill_mst_sm1_lic a  left outer join public.sessionwise_mollases_stock c "
					+ "	on c.int_mil_id =a.vch_app_id_f and int_session=(SELECT sesn_id::int FROM public.mst_season where active='a') "
					+ "	  " + "	where a.vch_verify_flag='V'  ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				ac.setOpeningBal(rs.getDouble(1));
			}
			rs = null;
			pstmt = null;

			query = "select sum(c.produced_mollases)"
					+ "	from public.sugarmill_mst_sm1_lic a  , public.molassesproductionmst c "
					+ "	where a.vch_verify_flag='V'  and c.sugarmill_id =a.vch_app_id_f and c.season=(SELECT sesn_id::int FROM public.mst_season where active='a')";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setProdTillDt(rs.getDouble(1));

			}
			rs = null;
			pstmt = null;

			query = "select sum(c.db_net_quantity) "
					+ " from public.sugarmill_mst_sm1_lic a  , public.sugarmill_mf4gatepass c  "
					+ " where a.vch_verify_flag='V' and c.sugarmill_id =a.vch_app_id_f and c.molasses_season=(SELECT sesn_id::int FROM public.mst_season where active='a') ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setSupplyTillDt(rs.getDouble(1));

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
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return news;
	}

	public String getDashboard2Details(HomePageAction ac) {

		String news = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ConnectionToDataBase.getConnection();
			String query = "select sum(x.dis),sum(x.disoup),sum(x.industry) "
					+ "	from( "
					+ "	select sum(b.db_capacity_applied) as dis,0 as disoup,0 as industry "
					+ "	from distillery.mst_molasses_application a ,distillery.mollasses_application_detail b "
					+ "	where a.int_app_id=b.int_app_id and a.year=(SELECT sesn_id::int FROM public.mst_season where active='a')"
					+ "	union  "
					+ "	select 0 as dis,sum(a.db_capacity_applied) as disoup,0 as industry "
					+ "	from distillery.mst_molasses_applicationoup a "
					+ "	where  a.year=(SELECT sesn_id::int FROM public.mst_season where active='a') "
					+ "	union "
					+ "	select 0 as dis,0 as disoup,sum(a.db_capacity_applied) as industry "
					+ "	from industry.ind_mst_molasses_application a "
					+ "	where  a.year=(SELECT sesn_id::int FROM public.mst_season where active='a')   )x ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setDistApplied(rs.getDouble(1));
				ac.setExpApplied(rs.getDouble(2));
				ac.setIndApplied(rs.getDouble(3));
			}
			rs = null;
			pstmt = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return news;
	}

	public String getDashboard3Details(HomePageAction ac) {

		String news = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ConnectionToDataBase.getConnection();
			String query = "select sum(x.dis),sum(x.disoup),sum(x.industry)"
					+ "	from( "
					+ "	select sum(c.pending_lifting_qty) as dis,0 as disoup,0 as industry "
					+ "	from distillery.mst_molasses_application a ,distillery.mollasses_application_detail b,distillery.dist_permit_detail c "
					+ "	where a.int_app_id=b.int_app_id and a.year=(SELECT sesn_id::int FROM public.mst_season where active='a') and a.vch_forwarded_commexc_hq3='Approved'  "
					+ "	    and a.int_app_id=c.applicationid and c.by='HQ3' and c.db_capacity_applied=b.db_capacity_applied "
					+ "	union  "
					+ "	select 0 as dis,sum(c.pending_lifting_qty) as disoup,0 as industry "
					+ "	from distillery.mst_molasses_applicationoup a,distillery.dist_permit_detail_oup c "
					+ "	where  a.year=(SELECT sesn_id::int FROM public.mst_season where active='a') and a.vch_forwarded_commexc_hq3='Approved'  "
					+ "	 and a.int_app_id=c.applicationid::int and c.by='HQ3' and c.db_capacity_applied=a.db_capacity_applied "
					+ "	union "
					+ "	select 0 as dis,0 as disoup,sum(c.db_capacity_applied) as industry "
					+ "	from industry.ind_mst_molasses_application a,industry.ind_dist_permit_detail c "
					+ "	where  a.year=(SELECT sesn_id::int FROM public.mst_season where active='a') and a.status='Approved' "
					+ "	and a.int_app_id=c.application   and c.db_capacity_applied=a.db_capacity_applied)x";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setDistLifted(rs.getDouble(1));
				ac.setExpLifted(rs.getDouble(2));
				ac.setIndLifted(rs.getDouble(3));
			}
			rs = null;
			pstmt = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return news;
	}

	public String getDashboard4Details(HomePageAction ac) {

		String news = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ConnectionToDataBase.getConnection();
			String query = "select sum(x.dis),sum(x.disoup),sum(x.industry) "
					+ "	from( "
					+ "	select sum(b.db_capacity_applied) as dis,0 as disoup,0 as industry "
					+ "	from distillery.mst_molasses_application a ,distillery.mollasses_application_detail b "
					+ "	where a.int_app_id=b.int_app_id and a.year=(SELECT sesn_id::int FROM public.mst_season where active='a') and a.vch_forwarded_commexc_hq3='Approved'  "
					+ "	union  "
					+ "	select 0 as dis,sum(a.db_capacity_applied) as disoup,0 as industry "
					+ "	from distillery.mst_molasses_applicationoup a "
					+ "	where  a.year=(SELECT sesn_id::int FROM public.mst_season where active='a') and a.vch_forwarded_commexc_hq3='Approved'  "
					+ "	union "
					+ "	select 0 as dis,0 as disoup,sum(a.db_capacity_applied) as industry "
					+ "	from industry.ind_mst_molasses_application a "
					+ "	where  a.year=(SELECT sesn_id::int FROM public.mst_season where active='a') and a.status='Approved' )x";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setDistApprovd(rs.getDouble(1));
				ac.setExpApprovd(rs.getDouble(2));
				ac.setIndApprovd(rs.getDouble(3));
			}
			rs = null;
			pstmt = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return news;
	}

	public String getDashboard7Details(HomePageAction ac) {

		String news = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ConnectionToDataBase.getConnection();
			String query = "select sum(x.dis),sum(x.disoup),sum(x.industry) "
					+ "	from( "
					+ "	select sum(b.db_capacity_applied) as dis,0 as disoup,0 as industry "
					+ "	from distillery.mst_molasses_application a ,distillery.mollasses_application_detail b "
					+ "	where a.int_app_id=b.int_app_id and a.year=(SELECT sesn_id::int FROM public.mst_season where active='a') and a.vch_forwarded_commexc_hq3='Rejected'  "
					+ "	union  "
					+ "	select 0 as dis,sum(a.db_capacity_applied) as disoup,0 as industry "
					+ "	from distillery.mst_molasses_applicationoup a "
					+ "	where  a.year=(SELECT sesn_id::int FROM public.mst_season where active='a') and a.vch_forwarded_commexc_hq3='Rejected'  "
					+ "	union "
					+ "	select 0 as dis,0 as disoup,sum(a.db_capacity_applied) as industry "
					+ "	from industry.ind_mst_molasses_application a "
					+ "	where  a.year=(SELECT sesn_id::int FROM public.mst_season where active='a') and a.status='Rejected' )x";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setDistRejected(rs.getDouble(1));
				ac.setExpRejected(rs.getDouble(2));
				ac.setIndRejected(rs.getDouble(3));
			}
			rs = null;
			pstmt = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return news;
	}

	public String getDashboard5Details(HomePageAction ac) {

		String news = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ConnectionToDataBase.getConnection();
			String query = "select count(id) "
					+ "	  from hotel_bar_rest.request_for_occasional_bar_license "
					+ "	 WHERE vch_challan_no is not null";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setApprecvd(rs.getDouble(1));

			}
			rs = null;
			pstmt = null;

			query = " select count(id)  "
					+ " from hotel_bar_rest.request_for_occasional_bar_license "
					+ " WHERE vch_approve is not  null ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setAppprocced(rs.getDouble(1));

			}
			rs = null;
			pstmt = null;

			query = " select count(id)  "
					+ " from hotel_bar_rest.request_for_occasional_bar_license "
					+ " WHERE vch_approve='Approved'";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setAppapprvd(rs.getDouble(1));

			}
			rs = null;
			pstmt = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return news;
	}

	public String getDashboard6Details(HomePageAction ac) {

		String news = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ConnectionToDataBase.getConnection();
			String query = "select count(vch_app_id_f) "
					+ "	 from public.sugarmill_mst_sm1_lic where vch_verify_flag='V' ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setSgrmillcount(rs.getInt(1));

			}
			rs = null;
			pstmt = null;

			query = "select count(int_app_id_f) "
					+ "	 from public.dis_mst_pd1_pd2_lic where vch_verify_flag='V' and vch_unit_state::int=1";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setDistcount(rs.getInt(1));

			}
			rs = null;
			pstmt = null;

			query = " select count(*)  "
					+ " from public.mst_industry_register where vch_wrk_office_district::int>0"
					+ " and vch_verify_flag='V' ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setIndcount(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = " select count(*)  "
					+ " from public.mst_industry_register where vch_wrk_office_district::int=0"
					+ " and vch_verify_flag='V' ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setIndcountoup(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = "select count(int_app_id_f) "
					+ "	 from public.dis_mst_pd1_pd2_lic where vch_verify_flag='V' and vch_unit_state::int>1";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setExpcount(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = "select count(int_app_id_f) "
					+ "	 from public.dis_mst_pd1_pd2_lic where vch_verify_flag='V' and vch_unit_state::int=1";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setDistcount(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = " select count(vch_name_of_shop)  "
					+ " from  retail.retail_shop " + "  ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setShopcount(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = " select count(*)  "
					+ " from  public.bre_mst_b1_lic where vch_verify_flag='V' "
					+ "  ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setBrewery(rs.getInt(1));

			}
			rs = null;
			pstmt = null;

			query = " select count(*)  "
					+ " from bwfl.registration_of_bwfl_lic_holder where vch_approval='V' "
					+ " and vch_license_type='1' ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setBwfl2a(rs.getInt(1));

			}
			rs = null;
			pstmt = null;

			query = " select count(*)  "
					+ " from bwfl.registration_of_bwfl_lic_holder where vch_approval='V' "
					+ " and vch_license_type='2' ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setBwfl2b(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = " select count(*)  "
					+ " from bwfl.registration_of_bwfl_lic_holder where vch_approval='V' "
					+ " and vch_license_type='3' ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setBwfl2c(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = " select count(*)  "
					+ " from bwfl.registration_of_bwfl_lic_holder where vch_approval='V' "
					+ " and vch_license_type='4' ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setBwfl2d(rs.getInt(1));

			}
			rs = null;
			pstmt = null;

			rs = null;
			pstmt = null;
			query = " select count(*)  "
					+ " from  licence.fl2_2b_2d where vch_approval='V' "
					+ " and vch_license_type='FL2D' ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setFl2d(rs.getInt(1));

			}
			rs = null;
			pstmt = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return news;
	}
	
	public String getDashboard8Details(HomePageAction ac) {

		String news = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ConnectionToDataBase.getConnection();
			String query = "select sum(x.avlbottle) from (select sum(round(((c.int_stock-c.int_dispatched)+.5)/size)) as avlbottle from distillery.boxing_stock_20_21 c  where c.vch_lic_type not in ('CL') union  select sum(round(((c.stock_bottles-coalesce(c.dispatch_bottles,0))+.5)/size)) as avlbottle from  distillery.fl2_stock_20_21 c)x; ";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setMufl(rs.getInt(1));

			}
			rs = null;
			pstmt = null;

			  query = "select sum(x.avlbottle) from (select sum(round(((c.int_stock-c.int_dispatched)+.5)/size)) as avlbottle from distillery.boxing_stock_20_21 c  where c.vch_lic_type='CL' union  select sum(round(((c.stock_bottles-coalesce(c.dispatch_bottles,0))+.5)/size)) as avlbottle from  distillery.fl2_stock_20_21 c)x; ";


			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setMucl(rs.getInt(1));
			}
			rs = null;
			pstmt = null;

			query = "select sum(x.avlbottle) from (select sum(round(((c.int_stock-c.int_dispatched)+.5)/e.box_size)) as avlbottle from bwfl.boxing_stock_20_21 c,distillery.packaging_details_20_21 d,distillery.box_size_details e   where c.vch_lic_type not in ('CL') and c.int_pckg_id=d.package_id  and  d.box_id=e.box_id union  select sum(c.stock_box-coalesce(c.dispatch_box,0) ) as avlbottle from  bwfl.fl2_stock_20_21 c)x; ";


			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setMubeer(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = "select  sum(round(((c.recv_total_bottels-c.dispatchbotl)+.5)/size))    from fl2d.fl2_2b_stock_20_21 c, licence.fl2_2b_2d_20_21 d where int_app_id=c.id and vch_license_type='FL2'; ";


			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setWfl(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = "select sum(c.recv_total_bottels-COALESCE(c.dispatchbotl,0)) from fl2d.fl2_2b_stock_20_21 c, licence.fl2_2b_2d_20_21 d where int_app_id=c.id and vch_license_type='FL2B'; ";


			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setWbeer(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = "select sum(c.recv_total_bottels-COALESCE(c.dispatchbotl,0)) from fl2d.fl2_2b_stock_20_21 c, licence.fl2_2b_2d_20_21 d where int_app_id=c.id and vch_license_type='CL2'; ";


			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setWcl(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = "select sum(c.recv_total_bottels-COALESCE(c.dispatchbotl,0)) from fl2d.fl2_2b_stock_20_21 c, licence.fl2_2b_2d_20_21 d where int_app_id=c.id and vch_license_type='CL2'; ";


			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setWcl(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = "select sum(c.int_recieved_bottles-coalesce(c.dispatch_36,0) ) as avlbottle  from bwfl_license.mst_receipt_20_21 c where  c.vch_license_type in ('BWFL2A','BWFL2C')  ";


			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setBond1(rs.getInt(1));

			}
			rs = null;
			pstmt = null;
			query = "select sum(c.int_recieved_bottles-coalesce(c.dispatch_36,0))  as avlbottle  from bwfl_license.mst_receipt_20_21 c where    c.vch_license_type in ('BWFL2B','BWFL2D')   ";


			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ac.setBond2(rs.getInt(1));

			}
			rs = null;
			pstmt = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return news;
	}
	
	public String getNews1(HomePageAction ac) {

		String news = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT ndate||' : '||NewsText ,links from news where type='Y' and sn=1 ";
		try {

			con = ConnectionToDataBase.getConnection();

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				news = rs.getString(1);
				ac.setNewslink1(rs.getString(2));
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
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return news;
	}
	public String getNews3(HomePageAction ac) {

		String news = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT ndate||' : '||NewsText,links from news where type='Y' and sn=3 ";
		try {

			con = ConnectionToDataBase.getConnection();

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				news = rs.getString(1);
				ac.setNewslink3(rs.getString(2));
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
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return news;
	}
	public String getNews2(HomePageAction ac) {

		String news = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT ndate||' : '||NewsText,links from news where type='Y' and sn=2";
		try {

			con = ConnectionToDataBase.getConnection();

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ac.setNewslink2(rs.getString(2));
				news = rs.getString(1);

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
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return news;
	}
	public String getNews() {

		String news = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT ndate||' : '||NewsText from news where type not in ('Y')";
		try {

			con = ConnectionToDataBase.getConnection();

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				news = rs.getString(1);

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
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return news;
	}

	public ArrayList<HomePageDatatable> displayMenuList(HomePageAction action) {
		ArrayList<HomePageDatatable> list = new ArrayList<HomePageDatatable>();
		HomePageDatatable dto = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = ConnectionToDataBase.getConnection2();

		try {
			char c = '"';
			String contxtURL = null;

			String mainContxt = "./auth/portal/";
			String getUserRole = "SELECT DISTINCT B.JBP_NAME FROM JBP_USERS A, "
					+ "  JBP_ROLE_MEMBERSHIP C  "
					+ " LEFT OUTER JOIN JBP_ROLES B ON  "
					+ " B.JBP_RID = C.JBP_RID "
					+ " WHERE C.JBP_UID = A.JBP_UID " + " AND A.JBP_UNAME = ? ";
			String userRoles = " WHERE A." + c + "ROLE" + c + " IN( ";
			pstm = conn.prepareStatement(getUserRole);
			// //System.out.println("userRoles===="+userRoles);
			pstm.setString(1, ResourceUtil.getUserNameAllReq());
			rs = pstm.executeQuery();
			while (rs.next()) {
				userRoles = userRoles + "'" + rs.getString("JBP_NAME") + "', ";
			}
			if (userRoles != null && userRoles.length() > 19) {
				userRoles = userRoles + "'0')";
			} else {
				userRoles = "";
				// //System.out.println(" No Role found for this user STEP 1 "+userRoles);
				return list;
			}

			String jointSql = " AND A.PK IN(";
			String queryMain = " SELECT DISTINCT A.NODE_KEY FROM "
					+ " JBP_OBJECT_NODE_SEC A " + userRoles;
			// //System.out.println("queryMain---------"+queryMain);
			pstm = conn.prepareStatement(queryMain);
			rs = pstm.executeQuery();
			while (rs.next()) {
				jointSql = jointSql + rs.getString("NODE_KEY") + ", ";
			}
			if (jointSql != null && jointSql.length() > 14) {
				jointSql = jointSql + "0)";
			} else {
				jointSql = "";
				// System.out.println(" NO Menu found for this user STEP 2 "+jointSql);
				return list;
			}

			String query = " SELECT DISTINCT  A.NAME,A." + c + "PATH" + c
					+ ", A." + c + "ICON_IMG" + c + ",A.PK FROM "
					+ " JBP_OBJECT_NODE A WHERE A.PARENT_KEY = 1 "
					+ "  AND A.PK >1162 AND A." + c + "ICON_IMG" + c
					+ " is not null  " + jointSql + " ORDER BY A.NAME ASC ";
			pstm = conn.prepareStatement(query.toUpperCase());
			rs = pstm.executeQuery();
			// //System.out.println("query---------"+query);

			while (rs.next()) {
				dto = new HomePageDatatable();
				action.setColumnRender1(true);
				contxtURL = rs.getString("PATH").replace(".", "/");
				contxtURL = contxtURL.replace(" ", "+");
				dto.setCellDisplay1(rs.getString("NAME"));
				/*
				 * if(action.getBaseName().compareToIgnoreCase(
				 * "com.mentor.upExcise.home.nl.homepagemsg_hi_IN")==0) {
				 * dto.setLinkIcon1
				 * ("/img/"+rs.getString("ICON_IMG")+"H1"+".png"); } else {
				 */
				dto.setLinkIcon1("/img/" + rs.getString("ICON_IMG") + ".png");
				// }
				dto.setLinkURL1(mainContxt + contxtURL);
				dto.setCellRender1(true);
				if (rs.next()) {
					action.setColumnRender2(true);

					if (rs.getString("ICON_IMG").equalsIgnoreCase("newRelease")) {

						dto.setCellDisplay2(rs.getString("NAME"));
						dto.setLinkIcon2("/img/" + rs.getString("ICON_IMG")
								+ ".png");

						dto.setLinkURL2("https://www.upexciseonline.in/doc/ExciseUp/UpdatesReleased.pdf");

						dto.setCellRender2(true);

					} else {
						contxtURL = rs.getString("PATH").replace(".", "/");
						contxtURL = contxtURL.replace(" ", "+");
						dto.setCellDisplay2(rs.getString("NAME"));
						dto.setLinkIcon2("/img/" + rs.getString("ICON_IMG")
								+ ".png");

						dto.setLinkURL2(mainContxt + contxtURL);
						dto.setCellRender2(true);
					}
				}

				if (rs.next()) {
					action.setColumnRender3(true);
					if (rs.getString("ICON_IMG").equalsIgnoreCase("newRelease")) {

						dto.setCellDisplay3(rs.getString("NAME"));
						dto.setLinkIcon3("/img/" + rs.getString("ICON_IMG")
								+ ".png");

						dto.setLinkURL3("/https://www.upexciseonline.in/doc/ExciseUp/UpdatesReleased.pdf");

						dto.setCellRender3(true);

					} else {
						contxtURL = rs.getString("PATH").replace(".", "/");
						contxtURL = contxtURL.replace(" ", "+");
						dto.setCellDisplay3(rs.getString("NAME"));
						dto.setLinkIcon3("/img/" + rs.getString("ICON_IMG")
								+ ".png");

						dto.setLinkURL3(mainContxt + contxtURL);
						dto.setCellRender3(true);
					}
				}

				if (rs.next()) {
					action.setColumnRender4(true);
					if (rs.getString("ICON_IMG").equalsIgnoreCase("newRelease")) {

						dto.setCellDisplay4(rs.getString("NAME"));
						dto.setLinkIcon4("/img/" + rs.getString("ICON_IMG")
								+ ".png");

						dto.setLinkURL4("/https://www.upexciseonline.in/doc/ExciseUp/UpdatesReleased.pdf");

						dto.setCellRender4(true);

					} else {
						contxtURL = rs.getString("PATH").replace(".", "/");
						contxtURL = contxtURL.replace(" ", "+");
						dto.setCellDisplay4(rs.getString("NAME"));
						dto.setLinkIcon4("/img/" + rs.getString("ICON_IMG")
								+ ".png");

						dto.setLinkURL4(mainContxt + contxtURL);

						dto.setCellRender4(true);
					}
				}
				if (rs.next()) {
					action.setColumnRender5(true);
					if (rs.getString("ICON_IMG").equalsIgnoreCase("newRelease")) {

						dto.setCellDisplay5(rs.getString("NAME"));
						dto.setLinkIcon5("/img/" + rs.getString("ICON_IMG")
								+ ".png");

						dto.setLinkURL5("/https://www.upexciseonline.in/doc/ExciseUp/UpdatesReleased.pdf");

						dto.setCellRender5(true);

					} else {
						contxtURL = rs.getString("PATH").replace(".", "/");
						contxtURL = contxtURL.replace(" ", "+");
						dto.setCellDisplay5(rs.getString("NAME"));
						dto.setLinkIcon5("/img/" + rs.getString("ICON_IMG")
								+ ".png");

						dto.setLinkURL5(mainContxt + contxtURL);
						dto.setCellRender5(true);
					}
				}
				if (rs.next()) {

					action.setColumnRender6(true);
					if (rs.getString("ICON_IMG").equalsIgnoreCase("newRelease")) {

						dto.setCellDisplay6(rs.getString("NAME"));
						dto.setLinkIcon6("/img/" + rs.getString("ICON_IMG")
								+ ".png");

						dto.setLinkURL6("/https://www.upexciseonline.in/doc/ExciseUp/UpdatesReleased.pdf");

						dto.setCellRender6(true);

					} else {
						contxtURL = rs.getString("PATH").replace(".", "/");
						contxtURL = contxtURL.replace(" ", "+");
						dto.setCellDisplay6(rs.getString("NAME"));
						dto.setLinkIcon6("/img/" + rs.getString("ICON_IMG")
								+ ".png");

						dto.setLinkURL6(mainContxt + contxtURL);

						dto.setCellRender6(true);
					}
				}

				list.add(dto);
			}

			// Runtime.getRuntime().gc();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (pstm != null)
				try {
					pstm.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		// System.out.println("List Size : ==>  : "+list.size());
		return list;
	}

	public ArrayList<HomePageDatatable> displayMenuList1(HomePageAction action) {
		ArrayList<HomePageDatatable> list = new ArrayList<HomePageDatatable>();
		HomePageDatatable dto = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = ConnectionToDataBase.getConnection2();

		try {
			char c = '"';
			String contxtURL = null;

			String mainContxt = "./auth/portal/";
			String getUserRole = "SELECT DISTINCT B.JBP_NAME FROM JBP_USERS A, "
					+ "  JBP_ROLE_MEMBERSHIP C  "
					+ " LEFT OUTER JOIN JBP_ROLES B ON  "
					+ " B.JBP_RID = C.JBP_RID "
					+ " WHERE C.JBP_UID = A.JBP_UID " + " AND A.JBP_UNAME = ? ";
			String userRoles = " WHERE A." + c + "ROLE" + c + " IN( ";
			pstm = conn.prepareStatement(getUserRole);
			// //System.out.println("userRoles===="+userRoles);
			pstm.setString(1, ResourceUtil.getUserNameAllReq());
			rs = pstm.executeQuery();
			while (rs.next()) {
				userRoles = userRoles + "'" + rs.getString("JBP_NAME") + "', ";
			}
			if (userRoles != null && userRoles.length() > 19) {
				userRoles = userRoles + "'0')";
			} else {
				userRoles = "";
				// //System.out.println(" No Role found for this user STEP 1 "+userRoles);
				return list;
			}

			String jointSql = " AND A.PK IN(";
			String queryMain = " SELECT DISTINCT A.NODE_KEY FROM "
					+ " JBP_OBJECT_NODE_SEC A " + userRoles;
			// //System.out.println("queryMain---------"+queryMain);
			pstm = conn.prepareStatement(queryMain);
			rs = pstm.executeQuery();
			while (rs.next()) {
				jointSql = jointSql + rs.getString("NODE_KEY") + ", ";
			}
			if (jointSql != null && jointSql.length() > 14) {
				jointSql = jointSql + "0)";
			} else {
				jointSql = "";
				// System.out.println(" NO Menu found for this user STEP 2 "+jointSql);
				return list;
			}

			String query = " SELECT DISTINCT  A.NAME,A." + c + "PATH" + c
					+ ", A." + c + "ICON_IMG" + c + ",A.PK FROM "
					+ " JBP_OBJECT_NODE A WHERE A.PARENT_KEY = 1 "
					+ "  AND A.PK >1162 AND A." + c + "ICON_IMG" + c
					+ " is not null  " + jointSql + " ORDER BY A.NAME ASC ";
			pstm = conn.prepareStatement(query.toUpperCase());
			rs = pstm.executeQuery();
			// //System.out.println("query---------"+query);
			while (rs.next()) {
				dto = new HomePageDatatable();
				action.setColumnRender1(true);
				contxtURL = rs.getString("PATH").replace(".", "/");
				contxtURL = contxtURL.replace(" ", "+");
				dto.setCellDisplay1(rs.getString("NAME"));
				/*
				 * if(action.getBaseName().compareToIgnoreCase(
				 * "com.mentor.upExcise.home.nl.homepagemsg_hi_IN")==0) {
				 * dto.setLinkIcon1
				 * ("/img/"+rs.getString("ICON_IMG")+"H1"+".png"); } else {
				 */
				dto.setLinkIcon1("/img/" + rs.getString("ICON_IMG") + ".png");
				// }
				dto.setLinkURL1(mainContxt + contxtURL);
				dto.setCellRender1(true);
				/*
				 * if(rs.next()){ action.setColumnRender2(true); contxtURL =
				 * rs.getString("PATH").replace(".", "/"); contxtURL =
				 * contxtURL.replace(" ", "+");
				 * dto.setCellDisplay2(rs.getString("NAME"));
				 * dto.setLinkIcon2("/img/"+rs.getString("ICON_IMG")+".png");
				 * 
				 * dto.setLinkURL2(mainContxt+contxtURL);
				 * dto.setCellRender2(true); }
				 * 
				 * if(rs.next()){ action.setColumnRender3(true); contxtURL =
				 * rs.getString("PATH").replace(".", "/"); contxtURL =
				 * contxtURL.replace(" ", "+");
				 * dto.setCellDisplay3(rs.getString("NAME"));
				 * dto.setLinkIcon3("/img/"+rs.getString("ICON_IMG")+".png");
				 * 
				 * dto.setLinkURL3(mainContxt+contxtURL);
				 * dto.setCellRender3(true); }
				 * 
				 * if(rs.next()){ action.setColumnRender4(true); contxtURL =
				 * rs.getString("PATH").replace(".", "/"); contxtURL =
				 * contxtURL.replace(" ", "+");
				 * dto.setCellDisplay4(rs.getString("NAME"));
				 * dto.setLinkIcon4("/img/"+rs.getString("ICON_IMG")+".png");
				 * 
				 * dto.setLinkURL4(mainContxt+contxtURL);
				 * 
				 * dto.setCellRender4(true); } if(rs.next()){
				 * action.setColumnRender5(true); contxtURL =
				 * rs.getString("PATH").replace(".", "/"); contxtURL =
				 * contxtURL.replace(" ", "+");
				 * dto.setCellDisplay5(rs.getString("NAME"));
				 * dto.setLinkIcon5("/img/"+rs.getString("ICON_IMG")+".png");
				 * 
				 * dto.setLinkURL5(mainContxt+contxtURL);
				 * dto.setCellRender5(true); } if(rs.next()){
				 * action.setColumnRender6(true); contxtURL =
				 * rs.getString("PATH").replace(".", "/"); contxtURL =
				 * contxtURL.replace(" ", "+");
				 * dto.setCellDisplay6(rs.getString("NAME"));
				 * dto.setLinkIcon6("/img/"+rs.getString("ICON_IMG")+".png");
				 * 
				 * dto.setLinkURL6(mainContxt+contxtURL);
				 * dto.setCellRender6(true); }
				 */

				list.add(dto);
			}
			// System.out.println("STEP 3");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (pstm != null)
				try {
					pstm.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		// System.out.println("List Size : ==>  : "+list.size());
		return list;
	}

	// /////////////////

	public void getApplicationcount(HomePageAction ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			String filter = "";
			int role = 0;

			rs = null;
			ps = null;
			// System.out.println("testing------------------------"+ResourceUtil.getUserNameAllReq().trim());

			if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-DC")) {
				ac.setRederReportLink(true);
				filter = "  hq1_dt is null  and vch_dc_remark is  null  and vch_jec_remark is not null and    hq1_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "' ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-AC-Admin")) {
				ac.setRederReportLink(true);
				filter = "  hq2_dt is null  and vch_acadmin_remark is   null  and hq2_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "' ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-Commissioner")
					|| ResourceUtil.getUserNameAllReq().trim()
							.equalsIgnoreCase("Excise-COMM")) {
				ac.setRederReportLink(true);
				filter = "  hq3_dt is null  and hq3_remark is  null  and hq3_user ='Excise-Commissioner' ";

			} else {
				ac.setRederReportLink(false);
			}

			String query = "select count(*) as number from industry.ind_mst_molasses_application msta where  "
					+ filter + "   ";

			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatus((rs.getInt("number")));
			}

			ps.close();
			rs.close();
			query = "select count(*) as number from industry.ind_mst_molasses_application   where hq3_dt is null  ";
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatustot((rs.getInt("number")));
			}

			ps.close();
			rs.close();
			query = "select count(*) as number from industry.ind_mst_molasses_application   where vch_approve_flag='R'  ";
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatustotr((rs.getInt("number")));
			}

			ps.close();
			rs.close();
			query = "select count(*) as number from industry.ind_mst_molasses_application   where vch_approve_flag='A' ";
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatustotp((rs.getInt("number")));
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public void getApplicationcountexport(HomePageAction ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			String filter = "";
			int role = 0;

			rs = null;
			ps = null;
			// System.out.println("testing------------------------"+ResourceUtil.getUserNameAllReq().trim());

			if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-DC")) {
				ac.setRederReportLink(true);
				filter = " hq1_dt is null  and vch_dc_remark is  null    and    hq1_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "' ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-AC-Admin")) {
				ac.setRederReportLink(true);
				filter = " hq2_dt is null  and vch_acadmin_remark is   null  and hq2_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "' ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-Commissioner")
					|| ResourceUtil.getUserNameAllReq().trim()
							.equalsIgnoreCase("Excise-COMM")|| ResourceUtil.getUserNameAllReq().trim()
							.equalsIgnoreCase("Psec-Excise")) {
				ac.setRederReportLink(true);
				filter = " hq3_dt is null  and hq3_remark is  null  and hq3_user ='Excise-Commissioner' ";

			} else {
				ac.setRederReportLink(false);
			}

			String query = "select count(*) as number from industry.ind_mst_molasses_application_oup msta where "
					+ filter + "   ";

			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatus3((rs.getInt("number")));
			}
			rs.close();
			ps.close();
			query = "select count(*) as number from industry.ind_mst_molasses_application_oup where hq3_dt is null  and  sent_to_sm='Excise-DC'  ";

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatus3tot((rs.getInt("number")));
			}

			rs.close();
			ps.close();
			query = "select count(*) as number from industry.ind_mst_molasses_application_oup where hq3_dt is not null   and vch_approve_flag='A' ";

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatus3totp((rs.getInt("number")));
			}
			rs.close();
			ps.close();
			query = "select count(*) as number from industry.ind_mst_molasses_application_oup where hq3_dt is not null   and vch_approve_flag ='R'";

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatus3totr((rs.getInt("number")));
			}
			rs.close();
			ps.close();

		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public void getApplicationcountdist(HomePageAction ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			String filter = "";
			int role = 0;

			rs = null;
			ps = null;
			// System.out.println("testing------------------------"+ResourceUtil.getUserNameAllReq().trim());

			if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-DC")) {
				ac.setRederReportLink(true);
				filter = "  hq1_dt is null  and hq1_remarks is  null   and   hq1_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "' ";
				// and hq1_dt is null and hq1_remarks is null and hq1_user
				// ='"+ResourceUtil.getUserNameAllReq().trim()+
			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-AC-Admin")) {
				ac.setRederReportLink(true);
				filter = "   hq2_dt is null  and hq2_remark is   null  and hq2_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "' ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-Commissioner")
					|| ResourceUtil.getUserNameAllReq().trim()
							.equalsIgnoreCase("Excise-COMM")|| ResourceUtil.getUserNameAllReq().trim()
							.equalsIgnoreCase("Psec-Excise")) {
				ac.setRederReportLink(true);
				filter = "  hq3_dt is null  and hq3_remark is  null  and hq3_user ='Excise-Commissioner' ";

			} else {
				ac.setRederReportLink(false);
			}

			String query = "select count(*) as number from distillery.mst_molasses_applicationoup a where "
					+ filter + "   ";

			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatus1((rs.getInt("number")));
			}

			query = "select count(*) as number from distillery.mst_molasses_applicationoup   where hq3_dt is not null  and vch_hq2_pdf_name is not null ";

			ps.close();
			rs.close();
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setApplicationstatus1totp((rs.getInt("number")));
			}
			query = "select count(*) as number from distillery.mst_molasses_applicationoup   where hq3_dt is not null  and vch_hq2_pdf_name is   null ";

			ps.close();
			rs.close();
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setApplicationstatus1totr((rs.getInt("number")));
			}

			query = "select count(*) as number from distillery.mst_molasses_applicationoup   where hq3_dt is null and  sent_to_sm='Excise-DC' ";

			ps.close();
			rs.close();
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setApplicationstatus1tot((rs.getInt("number")));
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public void getJanhit(HomePageAction ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String filter = "";
			int role = 0;

			rs = null;
			ps = null;
			// System.out.println("testing------------------------"+ResourceUtil.getUserNameAllReq().trim());

			if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-JCHQ")) {
				ac.setJanhitlink(true);
				filter = "   jc_dt is null and jc_remarks is null and jc_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-DC")) {
				ac.setJanhitlink(true);
				filter = "   hq1_dt is null  and hq1_remarks is  null    and    hq1_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-AC-License")) {
				ac.setJanhitlink(true);
				filter = "   hq2_dt is null  and hq2_remark is   null  and hq2_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-Commissioner")
					|| ResourceUtil.getUserNameAllReq().trim()
							.equalsIgnoreCase("Excise-COMM")
							|| ResourceUtil.getUserNameAllReq().trim()
							.equalsIgnoreCase("Psec-Excise")) {
				ac.setJanhitlink(true);
				filter = "   hq3_dt is null  and hq3_remark is  null  and hq3_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-DEC")) {
				ac.setJanhitlink(true);
				filter = "      dec_dt is null  and dec_remarks is  null  and dec_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

			} else if (ResourceUtil.getUserNameAllReq().trim().substring(0, 10)
					.equalsIgnoreCase("Excise-DEO")) {
				ac.setJanhitlink(true);
				filter = "   district_id=(select districtid from public.district "
						+ "where deo = '"
						+ ResourceUtil.getUserNameAllReq().trim()
						+ "')  and deo_dt is null "
						+ " and deo_remarks is  null  ";

			}

			String q = " SELECT  count(*) FROM janhit.permit_for_alcohol_spirit   where   "
					+ filter;

			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(q);

			rs = ps.executeQuery();

			if (rs.next()) {

				role = (rs.getInt(1));
			}

			if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-AC-License")) {
				ac.setJanhitlink(true);
				filter = "   hq2_dt is null  and hq2_remark is   null  and hq2_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-Commissioner")|| ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Psec-Excise")) {
				ac.setJanhitlink(true);
				filter = "   hq3_dt is null  and hq3_remark is  null  and hq3_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'  ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-Tech2")) {
				ac.setJanhitlink(true);
				filter = "      dec_dt is null  and dec_remarks is  null  and dec_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

			} else if (ResourceUtil.getUserNameAllReq().trim().substring(0, 10)
					.equalsIgnoreCase("Excise-DEO")) {
				ac.setJanhitlink(true);
				filter = "   a.districtid::int=(select districtid from public.district where deo = '"
						+ ResourceUtil.getUserNameAllReq().trim()
						+ "')  and deo_dt is null  "
						+ " and deo_remarks is  null   ";

			}

			else {
				filter = "   hq3_user='NNAA' ";
			}

			q = " SELECT   count(*) FROM janhit.permit_for_narcotic_drug a  where   "
					+ filter;

			if (rs.next()) {

				role = role + (rs.getInt(1));
			}
			if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-JCHQ")) {
				ac.setJanhitlink(true);
				filter = "   jc_dt is null and jc_remarks is null and jc_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'  ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-AC-License")) {
				ac.setJanhitlink(true);
				filter = "   hq2_dt is null  and hq2_remark is   null  and hq2_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-Commissioner")|| ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Psec-Excise")) {
				ac.setJanhitlink(true);
				filter = "   hq3_dt is null  and hq3_remark is  null  and hq3_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-DEC")) {
				ac.setJanhitlink(true);
				filter = "      dec_dt is null  and dec_remarks is  null  and dec_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

			} else if (ResourceUtil.getUserNameAllReq().trim().substring(0, 10)
					.equalsIgnoreCase("Excise-DEO")) {
				ac.setJanhitlink(true);
				filter = "   district_id::int=(select districtid from public.district where deo = '"
						+ ResourceUtil.getUserNameAllReq().trim()
						+ "')  and deo_dt is null  and deo_remarks is  null   ";

			}

			else {
				filter = "   hq3_user='NNAA' ";
			}

			q = " SELECT count(*) FROM janhit.applicationformissuance  where   "
					+ filter;

			if (rs.next()) {

				role = role + (rs.getInt(1));
			}

			ac.setJanhit(role);
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public void getApplicationcountdist1(HomePageAction ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			String filter = "";
			int role = 0;

			rs = null;
			ps = null;
			// System.out.println("testing------------------------"+ResourceUtil.getUserNameAllReq().trim());

			if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-DC")) {
				ac.setRederReportLink(true);
				filter = "  hq1_dt is null  and hq1_remarks is  null  and dd_remarks is not null and    hq1_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "' ";
				// and hq1_dt is null and hq1_remarks is null and hq1_user
				// ='"+ResourceUtil.getUserNameAllReq().trim()+
			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-AC-Admin")) {
				ac.setRederReportLink(true);
				filter = " hq2_dt is null  and hq2_remark is   null  and hq2_user ='"
						+ ResourceUtil.getUserNameAllReq().trim() + "' ";

			} else if (ResourceUtil.getUserNameAllReq().trim()
					.equalsIgnoreCase("Excise-Commissioner")
					|| ResourceUtil.getUserNameAllReq().trim()
							.equalsIgnoreCase("Excise-COMM")|| ResourceUtil.getUserNameAllReq().trim()
							.equalsIgnoreCase("Psec-Excise")) {
				ac.setRederReportLink(true);
				filter = " hq3_dt is null  and hq3_remark is  null  and hq3_user ='Excise-Commissioner' ";

			} else {
				ac.setRederReportLink(false);
			}

			con = ConnectionToDataBase.getConnection();

			String query = "select count(*) as number from distillery.mst_molasses_application a where "
					+ filter + "   ";
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatus2((rs.getInt("number")));
			}

			rs.close();
			ps.close();
			query = "select count(*) as number from distillery.mst_molasses_application where hq3_dt is null    ";
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatus2tot((rs.getInt("number")));
			}

			rs.close();
			ps.close();
			query = "select count(*) as number from distillery.mst_molasses_application where hq3_dt    is not null and vch_hq2_pdf_name is not null   ";
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatus2totp((rs.getInt("number")));
			}

			rs.close();
			ps.close();
			query = "select count(*) as number from distillery.mst_molasses_application where hq3_dt    is not null and vch_hq2_pdf_name is   null   ";
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setApplicationstatus2totr((rs.getInt("number")));
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public int getJanhitSpirit(HomePageAction ac) {

		Connection c = null;
		PreparedStatement p = null;
		ResultSet r = null;
		String filter = "";

		/*
		 * if (ResourceUtil.getUserNameAllReq().trim()
		 * .equalsIgnoreCase("Excise-JCHQ")) {
		 * 
		 * filter = "  jc_dt is null and jc_remarks is null and jc_user ='" +
		 * ResourceUtil.getUserNameAllReq().trim() + "'   ";
		 * 
		 * } else if (ResourceUtil.getUserNameAllReq().trim()
		 * .equalsIgnoreCase("Excise-DC")) {
		 * 
		 * filter =
		 * "  hq1_dt is null  and hq1_remarks is  null  and dd_remarks is not null and    hq1_user ='"
		 * + ResourceUtil.getUserNameAllReq().trim() + "'   ";
		 * 
		 * } else if (ResourceUtil.getUserNameAllReq().trim()
		 * .equalsIgnoreCase("Excise-AC-License")) {
		 * 
		 * filter =
		 * "  hq2_dt is null  and hq2_remark is   null  and hq2_user ='" +
		 * ResourceUtil.getUserNameAllReq().trim() + "'   ";
		 * 
		 * 
		 * } else if (ResourceUtil.getUserNameAllReq().trim()
		 * .equalsIgnoreCase("Excise-Commissioner")) {
		 * 
		 * filter = "  hq3_dt is null  and hq3_remark is  null  and hq3_user ='"
		 * + ResourceUtil.getUserNameAllReq().trim() + "'   ";
		 * 
		 * } else if (ResourceUtil.getUserNameAllReq().trim()
		 * .equalsIgnoreCase("Excise-DEC")) {
		 * 
		 * filter =
		 * "     dec_dt is null  and dec_remarks is  null  and dec_user ='" +
		 * ResourceUtil.getUserNameAllReq().trim() + "'  ";
		 * 
		 * } else if
		 * (ResourceUtil.getUserNameAllReq().trim().substring(0,10).equalsIgnoreCase
		 * ("Excise-DEO")) {
		 * 
		 * filter =
		 * "  district_id=(select districtid from public.district where deo = '"
		 * + ResourceUtil.getUserNameAllReq().trim()+
		 * "')  and deo_dt is null  and deo_remarks is  null ";
		 * 
		 * }
		 */

		String q = "select sum(x.pending),sum(x.approved),sum(x.rej),sum(x.pending1),sum(x.approved1),sum(x.rej1),sum(x.pending2),sum(x.approved2),sum(x.rej2),sum(x.spirit),sum(x.wine),sum(x.drug),sum(x.drugimp) as drugimp,sum(drugimpapvd) as drugimpapvd ,sum(drugimprej) as drugimprej,sum(approvedwithin7day) as approvedwithin7day ,sum(approved1_7day) as approved1_7day,sum(approved2_7days) as approved2_7days ,sum(drugimpapvd_days) as drugimpapvd_days from ("
				+ " SELECT distinct  count(*) as pending ,0 as approved , 0 as rej,0 as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp,0 as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.applicationformissuanceimportpermit   where   hq3_dt is null  and hq3_remark is  null  and hq3_user in ('Excise-Commissioner')  and application_date>='2020-04-01' "
				+ " union "
				+ " SELECT distinct 0 as pending, count(*) as approved, 0 as rej,0 as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp ,0 as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.permit_for_alcohol_spirit   where   vch_approved='APPROVED' and hq3_dt is not null   and appdt >='2020-04-01' "
				+ " union "
				+ " SELECT distinct 0 as pending,0 as approved,  count(*) as rej,0 as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp,0 as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.permit_for_alcohol_spirit   where   hq3_dt is not null  and vch_approved='REJECTED'  and appdt >='2020-04-01'  "
				+ "union "
				+ " SELECT distinct  0 as pending ,0 as approved , 0 as rej, count(*) as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp,0 as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.applicationformissuance   where   hq3_dt is null  and hq3_remark is  null  and hq3_user in ('Excise-Commissioner') and cr_dt >='2020-04-01'"
				+ " union "
				+ " SELECT distinct 0 as pending, 0 as approved, 0 as rej,0 as pending1 , count(*) as approved1 , 0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp,0 as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days  FROM janhit.applicationformissuance   where   vch_approved='APPROVED' and hq3_dt is not null  and hq3_user in ('Excise-Commissioner') and cr_dt >='2020-04-01' "
				+ " union "
				+ " SELECT distinct 0 as pending,0 as approved, 0 as rej,0 as pending1 ,0 as approved1 ,  count(*) as rej1,0 as pending2 ,0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp,0 as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.applicationformissuance   where   hq3_dt is not null  and vch_approved='REJECTED' and hq3_user in ('Excise-Commissioner')  and cr_dt >='2020-04-01' "
				+ "union "
				+ " SELECT distinct  0 as pending ,0 as approved , 0 as rej,0 as pending1 ,0 as approved1 , 0 as rej1, count(*) as pending2 ,0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp,0 as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.permit_for_narcotic_drug   where   hq3_dt is null  and hq3_remark is  null  and hq3_user in ('Excise-Commissioner') and dateofapplication >='2020-04-01'"
				+ " union "
				+ " SELECT distinct 0 as pending,0 as approved, 0 as rej,0 as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 , count(*) as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp,0 as drugimpapvd ,0 as drugimprej ,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.permit_for_narcotic_drug   where   vch_approved='APPROVED' and hq3_dt is not null  and hq3_user in ('Excise-Commissioner') and dateofapplication >='2020-04-01' "
				+ " union "
				+ " SELECT distinct 0 as pending,0 as approved,  0 as rej,0 as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 ,0 as approved2 , count(*) as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp,0 as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.permit_for_narcotic_drug   where   hq3_dt is not null  and vch_approved='REJECTED' and hq3_user in ('Excise-Commissioner') and dateofapplication >='2020-04-01' "
				+ "union"
				+ " SELECT distinct  0 as pending ,0 as approved , 0 as rej,0 as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2,count(*) as spirit ,0 as wine,0 as drug,0 as drugimp,0 as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.permit_for_alcohol_spirit   where  vch_approved is null and appdt >='2020-04-01' "
				+ "union"
				+ " SELECT distinct  0 as pending ,0 as approved , 0 as rej,0 as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2,0 as  spirit,count(*) as wine,0 as drug,0 as drugimp,0 as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.applicationformissuance   where   hq3_dt is null  and hq3_remark is  null   and cr_dt >='2020-04-01' "
				+ "union"
				+ " SELECT distinct  0 as pending ,0 as approved , 0 as rej,0 as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2,0 as spirit, 0 as wine,count(*) as drug,0 as drugimp,0 as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.permit_for_narcotic_drug   where   hq3_dt is null  and hq3_remark is  null  and dateofapplication >='2020-04-01' "
				+ "union"
				+ " SELECT distinct  0 as pending ,0 as approved , 0 as rej,0 as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2,0 as spirit, 0 as wine,0 as drug,count(*) as drugimp,0 as drugimpapvd ,0 as drugimprej ,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.applicationformissuanceimportpermit    where   hq3_dt is null  and hq3_remark is  null  and application_date>='2020-04-01'  "
				+ " union "
				+ " SELECT distinct 0 as pending,0 as approved, 0 as rej,0 as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp,count(*) as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.applicationformissuanceimportpermit   where   vch_approved='APPROVED' and hq3_dt is not null  and hq3_user in ('Excise-Commissioner') and application_date>='2020-04-01' "
				+ " union "
				+ " SELECT distinct 0 as pending,0 as approved, 0 as rej,0 as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 , 0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp,0 as drugimpapvd ,count(*) as drugimprej,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,0 as drugimpapvd_days FROM janhit.applicationformissuanceimportpermit   where   vch_approved='REJECTED' and hq3_dt is not null  and hq3_user in ('Excise-Commissioner') and application_date>='2020-04-01' "
				+ " union " +
				"  SELECT distinct 0 as pending,0 as approved, 0 as rej,0 as pending1 ,0 as approved1 ,0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp ,0 as drugimpapvd ,0 as drugimprej,count(*) as approvedwithin7day,0 as approved1_7day,0 as approved2_7days,0 as drugimpapvd_days   FROM janhit.permit_for_alcohol_spirit  where   vch_approved='APPROVED' and hq3_dt is not null     and  7 >=(hq3_dt-appdt) and appdt >='2020-04-01' " +
				" union   " +
				" SELECT distinct 0 as pending, 0 as approved, 0 as rej,0 as pending1 , 0 as approved1 ,0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug,0 as drugimp,0 as drugimpapvd ,0 as drugimprej ,0 as approvedwithin7day,count(*) as approved1_7day,0 as approved2_7days,0 as drugimpapvd_days    FROM janhit.applicationformissuance  where   vch_approved='APPROVED' and hq3_dt is not null  and hq3_user in ('Excise-Commissioner')  and 7>=(hq3_dt-cr_dt) and cr_dt >='2020-04-01' " +
				" union    " +
				" SELECT distinct 0 as pending,0 as approved, 0 as rej,0 as pending1 ,0 as approved1 , 0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug, 0 as drugimp,0 as drugimpapvd ,0 as drugimprej,0 as approvedwithin7day,0 as approved1_7day,count(*) as approved2_7days ,0 as drugimpapvd_days  FROM janhit.permit_for_narcotic_drug   where   vch_approved='APPROVED' and hq3_dt is not null  and hq3_user in ('Excise-Commissioner') and 7>=  (hq3_dt-dateofapplication)  and dateofapplication >='2020-04-01' " +
				" union   " +
				" SELECT distinct 0 as pending,0 as approved, 0 as rej,0 as pending1 ,0 as approved1 ,  0 as rej1,0 as pending2 ,0 as approved2 , 0 as rej2 ,0 as spirit, 0 as wine,0 as drug, 0 as drugimp,0 as drugimpapvd ,0 as drugimprej ,0 as approvedwithin7day,0 as approved1_7day,0 as approved2_7days ,count(*) as drugimpapvd_days  FROM janhit.applicationformissuanceimportpermit  where   vch_approved='APPROVED' and hq3_dt is not null  and hq3_user in ('Excise-Commissioner') and 7>=(hq3_dt-application_date) and application_date>='2020-04-01'  " +
				"  ) x";
System.out.println("counttttttttt===rahul====="+q);
		int i = 0;
		try {
			c = ConnectionToDataBase.getConnection();
			p = c.prepareStatement(q);

			r = p.executeQuery();
			if (r.next()) {
				ac.setJanhitspiritaprvd(r.getInt(2));
				ac.setJanhitspiritpen(r.getInt(10));
				ac.setJanhitspiritrej(r.getInt(3));
				//ac.setJanhitdugaprvd(r.getInt(8) + r.getInt("drugimpapvd"));
				//ac.setJanhitdugpen(r.getInt(12) + r.getInt("drugimp"));
				//ac.setJanhitdugrej(r.getInt(9) + r.getInt("drugimprej"));
			    ac.setJanhitdugaprvd(r.getInt("drugimpapvd"));
				ac.setJanhitdugpen(r.getInt("drugimp"));
				ac.setJanhitdugrej(r.getInt("drugimprej"));
				ac.setJanhitwineaprvd(r.getInt(5));
				ac.setJanhitwinepen(r.getInt(11));
				ac.setJanhitwinerej(r.getInt(6));
				ac.setSpirit(r.getInt(1));
				ac.setWine(r.getInt(4));
				ac.setNarcotic(r.getInt(7));
				ac.setJanhitdugaprvdexp(r.getInt(8));
				ac.setJanhitdugpenexp(r.getInt(12));
				ac.setJanhitdugrejexp(r.getInt(9));
				ac.setJanhitwineaprvdwthin7day(r.getInt("approved1_7day"));
				ac.setJanhitdugaprvdwithin7day(r.getInt("drugimpapvd_days"));
				ac.setJanhitdugaprvdexpwithin7day(r.getInt("approved2_7days"));
				ac.setJanhitspiritaprvdwithin7day(r.getInt("approvedwithin7day"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (r != null)
					r.close();
				if (p != null)
					p.close();
				if (c != null)
					c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	public int getTotalWine(HomePageAction ac) {

		Connection c = null;
		PreparedStatement p = null;
		ResultSet r = null;
		String filter = "";

		if (ResourceUtil.getUserNameAllReq().trim()
				.equalsIgnoreCase("Excise-JCHQ")) {

			filter = "  jc_dt is null and jc_remarks is null and jc_user ='"
					+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

		} else if (ResourceUtil.getUserNameAllReq().trim()
				.equalsIgnoreCase("Excise-AC-License")) {

			filter = "  hq2_dt is null  and hq2_remark is   null  and hq2_user ='"
					+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

		} else if (ResourceUtil.getUserNameAllReq().trim()
				.equalsIgnoreCase("Excise-Commissioner")|| ResourceUtil.getUserNameAllReq().trim()
				.equalsIgnoreCase("Psec-Excise")) {

			filter = "  hq3_dt is null  and hq3_remark is  null  and hq3_user ='"
					+ ResourceUtil.getUserNameAllReq().trim() + "'  ";

		} else if (ResourceUtil.getUserNameAllReq().trim()
				.equalsIgnoreCase("Excise-DEC")) {

			filter = "     dec_dt is null  and dec_remarks is  null  and dec_user ='"
					+ ResourceUtil.getUserNameAllReq().trim() + "' ";

		} else if (ResourceUtil.getUserNameAllReq().trim().substring(0, 10)
				.equalsIgnoreCase("Excise-DEO")) {

			filter = "  district_id::int=(select districtid from public.district where deo = '"
					+ ResourceUtil.getUserNameAllReq().trim()
					+ "') "
					+ " and deo_dt is null  and deo_remarks is  null  ";

		}

		else {
			filter = "  hq3_user='NNAA' ";
		}

		String q = " SELECT distinct  count(*) FROM janhit.applicationformissuance  where   "
				+ filter;

		int i = 0;
		try {
			c = ConnectionToDataBase.getConnection();
			p = c.prepareStatement(q);

			r = p.executeQuery();
			if (r.next()) {
				i = r.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (r != null)
					r.close();
				if (p != null)
					p.close();
				if (c != null)
					c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	public int getTotalDrug(HomePageAction ac) {

		Connection c = null;
		PreparedStatement p = null;
		ResultSet r = null;
		String filter = "";

		if (ResourceUtil.getUserNameAllReq().trim()
				.equalsIgnoreCase("Excise-AC-License")) {

			filter = "  hq2_dt is null  and hq2_remark is   null  and hq2_user ='"
					+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

		} else if (ResourceUtil.getUserNameAllReq().trim()
				.equalsIgnoreCase("Excise-Commissioner")|| ResourceUtil.getUserNameAllReq().trim()
				.equalsIgnoreCase("Psec-Excise")) {

			filter = "  hq3_dt is null  and hq3_remark is  null  and hq3_user ='"
					+ ResourceUtil.getUserNameAllReq().trim() + "'   ";

		} else if (ResourceUtil.getUserNameAllReq().trim()
				.equalsIgnoreCase("Excise-Tech2")) {

			filter = "     dec_dt is null  and dec_remarks is  null  and dec_user ='"
					+ ResourceUtil.getUserNameAllReq().trim() + "'  ";

		} else if (ResourceUtil.getUserNameAllReq().trim().substring(0, 10)
				.equalsIgnoreCase("Excise-DEO")) {

			filter = "  a.districtid::int=(select districtid from public.district where deo = '"
					+ ResourceUtil.getUserNameAllReq().trim()
					+ "') "
					+ " and deo_dt is null  and deo_remarks is  null  ";

		}

		String q = " SELECT  count(*)  FROM janhit.permit_for_narcotic_drug a where    "
				+ filter;

		int i = 0;
		try {
			c = ConnectionToDataBase.getConnection();
			p = c.prepareStatement(q);

			r = p.executeQuery();
			if (r.next()) {
				i = r.getInt(1);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			try {
				if (r != null)
					r.close();
				if (p != null)
					p.close();
				if (c != null)
					c.close();
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return i;
	}

	public void getNivesh(HomePageAction ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			rs = null;
			ps = null;

			String query = "select sum(x.SC18001pen),sum(x.SC18001apr),sum(x.SC18001rej),sum(x.SC18005pen),sum(x.SC18005apr),sum(x.SC18005rej),sum(x.SC18007pen),sum(x.SC18007apr),sum(x.SC18007rej),"
					+ "sum(x.SC18008pen),sum(x.SC18008apr),sum(x.SC18008rej),sum(x.SC180010pen),sum(x.SC180010apr), sum(x.SC180010rej)  from ("
					+ "select coalesce(count(*),0) as SC18001pen,0 as SC18001apr,0 as SC18001rej,0 as SC18005pen,0 as SC18005apr,0 as SC18005rej,0 as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "0 as SC18008pen,0 as SC18008apr,0 as SC18008rej ,0 as SC180010pen,0 as SC180010apr,  0 as SC180010rej  "
					+ " from industry.form_one_a_two a  WHERE  "
					+ "     a.status_code not in ('06','07')  "
					+ " union "
					+ "select 0 as SC18001pen,coalesce(count(*),0) as SC18001apr, 0 as SC18001rej,0 as SC18005pen,0 as SC18005apr,0 as SC18005rej,0 as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "0 as SC18008pen,0 as SC18008apr,0 as SC18008rej,0 as SC180010pen,0 as SC180010apr,  0 as SC180010rej  from industry.form_one_a_two a  WHERE   "
					+ "      a.status_code='06'"
					+ " union "
					+ "select 0 as SC18001pen,0 as SC18001apr, coalesce(count(*),0) as SC18001rej,0 as SC18005pen,0 as SC18005apr,0 as SC18005rej,0 as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "0 as SC18008pen,0 as SC18008apr,0 as SC18008rej,0 as SC180010pen,0 as SC180010apr,  0 as SC180010rej  from industry.form_one_a_two a  WHERE  "
					+ "     a.status_code='07'"
					+ " union "
					+ " select   0 as SC18001pen,0 as SC18001apr, 0 as SC18001rej,coalesce(count(*),0) as SC18005pen,0 as SC18005apr,0 as SC18005rej,0 as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "0 as SC18008pen,0 as SC18008apr,0 as SC18008rej,0 as SC180010pen,0 as SC180010apr,  0 as SC180010rej  FROM industry.form33_application_to_establish_a_distillery a "
					+ " WHERE a.status_code not in ('06','07') "
					+ " union "
					+ " select   0 as SC18001pen,0 as SC18001apr, 0 as SC18001rej,0 as SC18005pen,coalesce(count(*),0) as SC18005apr,0 as SC18005rej,0 as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "0 as SC18008pen,0 as SC18008apr,0 as SC18008rej,0 as SC180010pen,0 as SC180010apr,  0 as SC180010rej    FROM industry.form33_application_to_establish_a_distillery a  "
					+ " WHERE   a.status_code='06' "
					+ " union "
					+ " select   0 as SC18001pen,0 as SC18001apr,0  as SC18001rej,0 as SC18005pen,0 as SC18005apr,coalesce(count(*),0) as SC18005rej,0 as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "0 as SC18008pen,0 as SC18008apr,0 as SC18008rej,0 as SC180010pen,0 as SC180010apr,  0 as SC180010rej  FROM industry.form33_application_to_establish_a_distillery a "
					+ " WHERE   a.status_code='07' "
					+ " union "
					+ " select   0 as SC18001pen,0 as SC18001apr, coalesce(count(*),0) as SC18001rej,0 as SC18005pen,0 as SC18005apr,0 as SC18005rej,coalesce(count(*),0) as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "0 as SC18008pen,0 as SC18008apr,0 as SC18008rej,0 as SC180010pen,0 as SC180010apr,  0 as SC180010rej   FROM industry.license_for_distillery a "
					+ " WHERE a.status_code not in ('06','07')  "
					+ " union "
					+ " select   0 as SC18001pen,0 as SC18001apr, coalesce(count(*),0) as SC18001rej,0 as SC18005pen,0 as SC18005apr,0 as SC18005rej,0 as SC18007pen,coalesce(count(*),0) as SC18007apr,0 as SC18007rej,"
					+ "0 as SC18008pen,0 as SC18008apr,0 as SC18008rej,0 as SC180010pen,0 as SC180010apr,  0 as SC180010rej   FROM industry.license_for_distillery a  "
					+ " WHERE   a.status_code='06' "
					+ " union "
					+ " select   0 as SC18001pen,0 as SC18001apr, 0 as SC18001rej,0 as SC18005pen,0 as SC18005apr,0 as SC18005rej,0 as SC18007pen,0 as SC18007apr,coalesce(count(*),0) as SC18007rej,"
					+ "0 as SC18008pen,0 as SC18008apr,0 as SC18008rej,0 as SC180010pen,0 as SC180010apr,  0 as SC180010rej   FROM industry.license_for_distillery a "
					+ " WHERE   a.status_code='07' "
					+ " union "
					+ " select   0 as SC18001pen,0 as SC18001apr, coalesce(count(*),0) as SC18001rej,0 as SC18005pen,0 as SC18005apr,0 as SC18005rej,0 as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "coalesce(count(*),0)  as SC18008pen,0 as SC18008apr,0 as SC18008rej,0 as SC180010pen,0 as SC180010apr,  0 as SC180010rej    FROM industry.application_to_establish_a_brewery a  "
					+ " WHERE a.status_code not in ('06','07')"
					+ " union "
					+ " select 0 as SC18001pen,0 as SC18001apr, coalesce(count(*),0) as SC18001rej,0 as SC18005pen,0 as SC18005apr,0 as SC18005rej,0 as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "0 as SC18008pen,coalesce(count(*),0)  as SC18008apr,0 as SC18008rej,0 as SC180010pen,0 as SC180010apr,  0 as SC180010rej  FROM industry.application_to_establish_a_brewery a  "
					+ " WHERE   a.status_code='06' "
					+ " union "
					+ " select 0 as SC18001pen,0 as SC18001apr, coalesce(count(*),0) as SC18001rej,0 as SC18005pen,0 as SC18005apr,0 as SC18005rej,0 as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "0 as SC18008pen,0 as SC18008apr,coalesce(count(*),0)  as SC18008rej,0 as SC180010pen,0 as SC180010apr,  0 as SC180010rej  FROM industry.application_to_establish_a_brewery a  "
					+ " WHERE   a.status_code='07'"
					+ " union "
					+ " select   0 as SC18001pen,0 as SC18001apr, coalesce(count(*),0) as SC18001rej,0 as SC18005pen,0 as SC18005apr,0 as SC18005rej,0 as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "0  as SC18008pen,0 as SC18008apr,0 as SC18008rej ,coalesce(count(*),0)  as SC180010pen,0  as SC180010apr, 0 as SC180010rej   FROM industry.license_for_brewery a "
					+ " WHERE a.status_code not in ('06','07') "
					+ " union "
					+ " select 0 as SC18001pen,0 as SC18001apr, coalesce(count(*),0) as SC18001rej,0 as SC18005pen,0 as SC18005apr,0 as SC18005rej,0 as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "0 as SC18008pen,0  as SC18008apr,0 as SC18008rej ,0 as SC180010pen,coalesce(count(*),0)  as SC180010apr,0 as SC180010rej FROM industry.license_for_brewery a "
					+ " WHERE   a.status_code='06' "
					+ " union "
					+ " select 0 as SC18001pen,0 as SC18001apr,0 as SC18001rej,0 as SC18005pen,0 as SC18005apr,0 as SC18005rej,0 as SC18007pen,0 as SC18007apr,0 as SC18007rej,"
					+ "0 as SC18008pen,0 as SC18008apr,0  as SC18008rej ,0 as SC180010pen,0 as SC180010apr,  coalesce(count(*),0) as SC180010rej FROM industry.license_for_brewery a  "
					+ " WHERE   a.status_code='07' " + "  )x ";

			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setSpiritalchopen(rs.getInt(1));
				ac.setSpiritalchoaprvd(rs.getInt(2));
				ac.setSpiritalchorej(rs.getInt(3));

				ac.setDistestbpen(rs.getInt(4));
				ac.setDistestbaprvd(rs.getInt(5));
				ac.setDistestbrej(rs.getInt(6));
				ac.setDistoppen(rs.getInt(7));
				ac.setDistopaprvd(rs.getInt(8));
				ac.setDistoprej(rs.getInt(9));

				ac.setBrewestbpen(rs.getInt(10));
				ac.setBrewestbopn(rs.getInt(11));
				ac.setBrewestbrej(rs.getInt(12));
				ac.setBrewoppend(rs.getInt(13));
				ac.setBrewopaprvd(rs.getInt(14));
				ac.setBrewoprej(rs.getInt(15));

			}
			rs = null;

			query = "select sum(x.SC18001pen),sum(x.SC18005pen), sum(x.SC18007pen),sum(x.SC18008pen),sum(x.SC180010pen)  from ("
					+ "select coalesce(count(*),0) as SC18001pen, 0 as SC18005pen, 0 as SC18007pen, 0 as SC18008pen, 0 as SC180010pen  "
					+ " from industry.form_one_a_two a,industry.license_application_forwarding b WHERE  "
					+ "     a.id=b.int_form_id AND  a.service_id=b.serviceid    "
					+ " and b.usernm in ('Excise-Commissioner','Excise-COMM') and a.status_code not in ('06','07') "
					+

					" union "
					+ " select   0 as SC18001pen, coalesce(count(*),0) as SC18005pen, 0 as SC18007pen, "
					+ "0 as SC18008pen, 0 as SC180010pen  "
					+ " FROM industry.form33_application_to_establish_a_distillery a,industry.license_application_forwarding b WHERE  "
					+ "     a.pk=b.int_form_id AND  a.service_id=b.serviceid    "
					+ " and b.usernm in ('Excise-Commissioner','Excise-COMM') and a.status_code not in ('06','07') "
					+ " union "
					+ "select 0 as SC18001pen, 0 as SC18005pen, coalesce(count(*),0) as SC18007pen, 0 as SC18008pen, 0 as SC180010pen   FROM industry.license_for_distillery a ,industry.license_application_forwarding b WHERE  "
					+ "     a.int_app_id_f=b.int_form_id AND  a.service_id=b.serviceid    "
					+ " and b.usernm in ('Excise-Commissioner','Excise-COMM') and a.status_code not in ('06','07') "
					+ " union "
					+

					"select 0 as SC18001pen, 0 as SC18005pen, 0 as SC18007pen, coalesce(count(*),0) as SC18008pen, 0 as SC180010pen   "
					+ " FROM industry.application_to_establish_a_brewery a  ,industry.license_application_forwarding b WHERE  "
					+ "     a.id_pk=b.int_form_id AND  a.service_id=b.serviceid    "
					+ " and b.usernm in ('Excise-Commissioner','Excise-COMM') and a.status_code not in ('06','07') "
					+

					" union "
					+

					"select 0 as SC18001pen, 0 as SC18005pen, 0 as SC18007pen, 0 as SC18008pen, coalesce(count(*),0) as SC180010pen   "
					+ "  FROM industry.license_for_brewery a ,industry.license_application_forwarding b WHERE  "
					+ "     a.int_app_id_f=b.int_form_id AND  a.service_id=b.serviceid    "
					+ " and b.usernm in ('Excise-Commissioner','Excise-COMM') and a.status_code not in ('06','07') "
					+ "  )x ";

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setPencomm10(rs.getInt(5));
				ac.setPencomm01(rs.getInt(1));
				ac.setPencomm05(rs.getInt(2));

				ac.setPencomm07(rs.getInt(3));
				ac.setPencomm08(rs.getInt(4));

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public void getoccasonal(HomePageAction ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			rs = null;
			ps = null;

			String query = "select sum(totalapprove) as approve, sum(totalreject) as reject, sum(tapplicant) as toalapp , sum(totalapprove_7day) as totalapprove_7day "
					+ "	from  "
					+ "	(select count(id) as totalapprove,0 as totalreject,0 as tapplicant ,0 as totalapprove_7day "
					+ "	from hotel_bar_rest.request_for_occasional_bar_license                      "
					+ "   where vch_approve='Approved' and vch_challan_no is not null    and application_date>='2020-04-01'                "
					+ "   union                                                                      "
					+ "   select 0 as totalapprove,  count(id) as totalreject,0 as tapplicant ,0 as totalapprove_7day from  "
					+ "   hotel_bar_rest.request_for_occasional_bar_license                       "
					+ "   where vch_approve='Rejected' and vch_challan_no is not null     and application_date>='2020-04-01'            "
					+ "   union                                                                   "
					+ "   select 0 as totalapprove,  0 as totalreject,count(id) as tapplicant ,0 as totalapprove_7day from   "
					+ "   hotel_bar_rest.request_for_occasional_bar_license               "
					+ "   where  vch_challan_no is not null and vch_approve is null  and application_date>='2020-04-01'    " +
					" union " +
					" select 0 as totalapprove,0 as totalreject,0 as tapplicant ,count(id) as totalapprove_7day " +
					" from hotel_bar_rest.request_for_occasional_bar_license   where vch_approve='Approved' and vch_challan_no is not null  " +
					" and 7>=(approved_date-application_date)  and application_date>='2020-04-01'    )x ";

			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setOccaprv(rs.getInt(1));
				ac.setOccrej(rs.getInt(2));
				ac.setOccpen(rs.getInt(3));
				ac.setOccaprvwithin7day(rs.getInt("totalapprove_7day"));

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public boolean getflclcheck(HomePageAction ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean flg = false;
		try {

			rs = null;
			ps = null;

			String queryList = " SELECT  vch_license_type "
					+ " FROM licence.fl2_2b_2d WHERE loginid='"
					+ ResourceUtil.getUserNameAllReq().trim() + "'";
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(queryList);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("vch_license_type").trim()
						.equalsIgnoreCase("CL2")) {
					ac.setFlclflg(true);
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return false;
	}

	public void getpwalcohol(HomePageAction ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			rs = null;
			ps = null;

			String query = "SELECT ceil((sum(a.current_alloted)/1000)) as alloted_quantity, ceil((sum(b.approved_qty)/1000)) as indent_quantity, ceil((sum(b.lifted_qty)/1000)) as lifted_quantity "
					+ " FROM fl41.fl41_registration_approval a,fl41.fl41_indent_detail_approved b "
					+ " WHERE a.finantial_year::text=(SELECT sesn_id FROM public.mst_financial_year WHERE active='a') "
					+ " AND a.int_id=b.fl41_id AND a.finantial_year=b.financial_yr_id  "
					+ "  ";

			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {

				ac.setPwindentd(rs.getDouble(2));
				ac.setPwlifted(rs.getDouble(3));
				ac.setPwallotd(rs.getDouble(1));

			}

			query = "SELECT ceil((sum(recv_bl)/1000))   FROM distillery.export_denatured_spirit   WHERE sprit_type=14 ";

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setPwexp(rs.getDouble(1));

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	// ------------ vivek code ----------------

	/*
	 * 
	 * public void getLiquorIndent(HomePageAction ac) { Connection con = null;
	 * PreparedStatement ps = null; ResultSet rs =null;
	 * 
	 * try { rs=null;ps=null;
	 * 
	 * 
	 * String querycl=
	 * " select count(*) as cl_pending from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='CL2' and vch_action_taken='A' and finalize_flag='F' "
	 * ;
	 * 
	 * 
	 * con = ConnectionToDataBase.getConnection(); ps=
	 * con.prepareStatement(querycl);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { ac.setClTot_P(rs.getInt(1));
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * String queryfl=
	 * "	 select   count(*) as fl_pending  from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='FL2' and vch_action_taken='A' and finalize_flag='F' "
	 * ;
	 * 
	 * ps= con.prepareStatement(queryfl);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { ac.setFlTot_P(rs.getInt(1));
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * String querybeer=
	 * "  select  count(*) as beer_pending from fl2d.indent_for_wholesale where vch_type='B' and vch_licence_type='FL2B' and vch_action_taken='A' and finalize_flag='F' "
	 * ;
	 * 
	 * ps= con.prepareStatement(querybeer);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { ac.setBeerTot_P(rs.getInt(1));
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * String queryCLDay=
	 * 
	 * 
	 * "	select sum(x.cl_1day) as cl_1day, sum(x.cl_2day) as cl_2day , sum(x.cl_3day) as cl_3day , sum(x.cl_4day) as cl_4day , sum(x.cl_5day ) as cl_5day, sum(x.cl_moreday) as cl_moreday  from  "
	 * + "	( "+
	 * 
	 * "		select   count(*) as cl_1day , 0 as cl_2day , 0 as cl_3day ,0 as cl_4day , 0 as cl_5day , 0 as cl_moreday from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='CL2' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -1))  "
	 * + "		union "+
	 * "		select  0 as cl_1day, count(*) as cl_2day , 0 as cl_3day, 0 as cl_4day , 0 as cl_5day , 0 as cl_moreday from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='CL2' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -2))  "
	 * + "		union "+
	 * "		select  0 as cl_1day, 0 as cl_2day,  count(*) as cl_3day , 0 as cl_4day , 0 as cl_5day , 0 as cl_moreday from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='CL2' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -3))  "
	 * + "		union  "+
	 * "		select  0 as cl_1day, 0 as cl_2day, 0 as cl_3day ,count(*) as cl_4day , 0 as cl_5day, 0 as cl_moreday  from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='CL2' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -4))  "
	 * + "		union  "+
	 * "		select  0 as cl_1day, 0 as cl_2day, 0 as cl_3day , 0 as cl_4day,  count(*) as cl_5day, 0 as cl_moreday   from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='CL2' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -5))  "
	 * + "		union  "+
	 * "		select  0 as cl_1day, 0 as cl_2day, 0 as cl_3day , 0 as cl_4day , 0 as cl_5day , count(*) as cl_moreday  from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='CL2' and vch_action_taken='A' and finalize_flag='F' and cr_date < (select (CURRENT_DATE -5))  "
	 * + "		)x ";
	 * 
	 * ps= con.prepareStatement(queryCLDay);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { ac.setCl_1Day(rs.getInt("cl_1day"));
	 * ac.setCl_2Day(rs.getInt("cl_2day")); ac.setCl_3Day(rs.getInt("cl_3day"));
	 * ac.setCl_4Day(rs.getInt("cl_4day")); ac.setCl_5Day(rs.getInt("cl_5day"));
	 * ac.setCl_MoreDay(rs.getInt("cl_moreday"));
	 * 
	 * 
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * 
	 * String queryFLDay=
	 * 
	 * "	select sum(x.fl_1day) as fl_1day, sum(x.fl_2day) as fl_2day , sum(x.fl_3day) as fl_3day , sum(x.fl_4day) as fl_4day , sum(x.fl_5day ) as fl_5day, sum(x.fl_moreday) as fl_moreday  from  "
	 * + "	( "+
	 * 
	 * "	select   count(*) as fl_1day , 0 as fl_2day , 0 as fl_3day ,0 as fl_4day , 0 as fl_5day , 0 as fl_moreday from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='FL2' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -1))  "
	 * + "	union "+
	 * "	select  0 as fl_1day, count(*) as fl_2day , 0 as fl_3day, 0 as fl_4day , 0 as fl_5day , 0 as fl_moreday from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='FL2' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -2)) "
	 * + "	union "+
	 * "	select  0 as fl_1day, 0 as fl_2day,  count(*) as fl_3day , 0 as fl_4day , 0 as fl_5day , 0 as fl_moreday from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='FL2' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -3)) "
	 * + "	union  "+
	 * "	select  0 as fl_1day, 0 as fl_2day, 0 as fl_3day ,count(*) as fl_4day , 0 as fl_5day, 0 as fl_moreday  from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='FL2' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -4)) "
	 * + "	union  "+
	 * "	select  0 as fl_1day, 0 as fl_2day, 0 as fl_3day , 0 as fl_4day,  count(*) as fl_5day, 0 as fl_moreday   from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='FL2' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -5)) "
	 * + "	union "+
	 * "	select  0 as fl_1day, 0 as fl_2day, 0 as fl_3day , 0 as fl_4day , 0 as fl_5day , count(*) as fl_moreday  from fl2d.indent_for_wholesale where vch_type='D' and vch_licence_type='FL2' and vch_action_taken='A' and finalize_flag='F' and cr_date < (select (CURRENT_DATE -5))  "
	 * + "	) x ";
	 * 
	 * 
	 * ps= con.prepareStatement(queryFLDay);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { ac.setFl_1Day(rs.getInt("fl_1day"));
	 * ac.setFl_2Day(rs.getInt("fl_2day")); ac.setFl_3Day(rs.getInt("fl_3day"));
	 * ac.setFl_4Day(rs.getInt("fl_4day")); ac.setFl_5Day(rs.getInt("fl_5day"));
	 * ac.setFl_MoreDay(rs.getInt("fl_moreday"));
	 * 
	 * 
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * 
	 * String queryBeerDay=
	 * 
	 * 
	 * "	select sum(x.beer_1day) as beer_1day, sum(x.beer_2day) as beer_2day , sum(x.beer_3day) as beer_3day , sum(x.beer_4day) as beer_4day , sum(x.beer_5day ) as beer_5day, sum(x.beer_moreday) as beer_moreday  from  "
	 * + "	( "+
	 * 
	 * "	select   count(*) as beer_1day , 0 as beer_2day , 0 as beer_3day ,0 as beer_4day , 0 as beer_5day , 0 as beer_moreday from fl2d.indent_for_wholesale where vch_type='B' and vch_licence_type='FL2B' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -1)) "
	 * + "	union "+
	 * "	select  0 as beer_1day, count(*) as beer_2day , 0 as beer_3day, 0 as beer_4day , 0 as beer_5day , 0 as beer_moreday from fl2d.indent_for_wholesale where vch_type='B' and vch_licence_type='FL2B' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -2)) "
	 * + "	union "+
	 * "	select  0 as beer_1day, 0 as beer_2day,  count(*) as beer_3day , 0 as beer_4day , 0 as beer_5day , 0 as beer_moreday from fl2d.indent_for_wholesale where vch_type='B' and vch_licence_type='FL2B' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -3)) "
	 * + "	union  "+
	 * "	select  0 as beer_1day, 0 as beer_2day, 0 as beer_3day ,count(*) as beer_4day , 0 as beer_5day, 0 as beer_moreday  from fl2d.indent_for_wholesale where vch_type='B' and vch_licence_type='FL2B' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -4))  "
	 * + "	union  "+
	 * "	select  0 as beer_1day, 0 as beer_2day, 0 as beer_3day , 0 as beer_4day,  count(*) as beer_5day, 0 as beer_moreday   from fl2d.indent_for_wholesale where vch_type='B' and vch_licence_type='FL2B' and vch_action_taken='A' and finalize_flag='F' and cr_date= (select (CURRENT_DATE -5))  "
	 * + "	union  "+
	 * "	select  0 as beer_1day, 0 as beer_2day, 0 as beer_3day , 0 as beer_4day , 0 as beer_5day , count(*) as beer_moreday  from fl2d.indent_for_wholesale where vch_type='B' and vch_licence_type='FL2B' and vch_action_taken='A' and finalize_flag='F' and cr_date < (select (CURRENT_DATE -5)) "
	 * + "	)x ";
	 * 
	 * 
	 * ps= con.prepareStatement(queryBeerDay);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { ac.setBeer_1Day(rs.getInt("beer_1day"));
	 * ac.setBeer_2Day(rs.getInt("beer_2day"));
	 * ac.setBeer_3Day(rs.getInt("beer_3day"));
	 * ac.setBeer_4Day(rs.getInt("beer_4day"));
	 * ac.setBeer_5Day(rs.getInt("beer_5day"));
	 * ac.setBeer_MoreDay(rs.getInt("beer_moreday"));
	 * 
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * }
	 * 
	 * catch (Exception e) { e.printStackTrace(); } finally { try { con.close();
	 * } catch (Exception e2) { e2.printStackTrace(); } }
	 * 
	 * }
	 */

	// ------------ end vivek code ----------------

	// ------------ vivek code ----------------

	/*
	 * 
	 * 
	 * public void getLiquorIndent(HomePageAction ac) { Connection con = null;
	 * PreparedStatement ps = null; ResultSet rs =null;
	 * 
	 * try { rs=null;ps=null;
	 * 
	 * 
	 * SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
	 * 
	 * 
	 * String querycl=
	 * " select count(*) as cl_pending from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' "
	 * +
	 * " and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) "
	 * ;
	 * 
	 * 
	 * 
	 * con = ConnectionToDataBase.getConnection(); ps=
	 * con.prepareStatement(querycl);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { ac.setClTot_P(rs.getInt(1));
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * String queryfl=
	 * "	 select   count(*) as fl_pending  from fl2d.indent_for_wholesale a  where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' "
	 * +
	 * " 		and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no= a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) "
	 * ;
	 * 
	 * 
	 * ps= con.prepareStatement(queryfl);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { ac.setFlTot_P(rs.getInt(1));
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * String querybeer=
	 * "  select  count(*) as beer_pending from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' "
	 * +
	 * " 		and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) "
	 * ;
	 * 
	 * 
	 * ps= con.prepareStatement(querybeer);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { ac.setBeerTot_P(rs.getInt(1));
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * //------------- oldest Date ----------------
	 * 
	 * String querycl_oldestDate=
	 * 
	 * " select min(a.cr_date)  as cl_oldest from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' "
	 * +
	 * " and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) "
	 * ;
	 * 
	 * ps= con.prepareStatement(querycl_oldestDate);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { String appDate =
	 * date.format(Utility.convertSqlDateToUtilDate(rs.getDate(1)));
	 * ac.setCl_Oldest_Date(appDate); // ac.setCl_Oldest_Date(rs.getString(1));
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * 
	 * 
	 * String queryfl_oldestDate=
	 * "	 select   min(a.cr_date)  as fl_oldest  from fl2d.indent_for_wholesale a  where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' "
	 * +
	 * " 		and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no= a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) "
	 * ;
	 * 
	 * 
	 * ps= con.prepareStatement(queryfl_oldestDate);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { String appDate =
	 * date.format(Utility.convertSqlDateToUtilDate(rs.getDate(1)));
	 * ac.setFl_Oldest_Date(appDate); // ac.setFl_Oldest_Date(rs.getString(1));
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * String querybeer_oldestDate=
	 * "  select  min(a.cr_date)  as beer_oldest from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' "
	 * +
	 * " 		and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) "
	 * ;
	 * 
	 * 
	 * ps= con.prepareStatement(querybeer_oldestDate);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { String appDate =
	 * date.format(Utility.convertSqlDateToUtilDate(rs.getDate(1)));
	 * ac.setBeer_Oldest_Date(appDate); //
	 * ac.setBeer_Oldest_Date(rs.getString(1));
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * 
	 * 
	 * //-------------End oldest Date ----------------
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * String queryCLDay=
	 * 
	 * 
	 * "	select sum(x.cl_1day) as cl_1day, sum(x.cl_2day) as cl_2day , sum(x.cl_3day) as cl_3day , sum(x.cl_4day) as cl_4day , sum(x.cl_5day ) as cl_5day, sum(x.cl_moreday) as cl_moreday  from  "
	 * + "	( "+
	 * 
	 * "		select   count(*) as cl_1day , 0 as cl_2day , 0 as cl_3day ,0 as cl_4day , 0 as cl_5day , 0 as cl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -1))  "
	 * + "		union "+
	 * "		select  0 as cl_1day, count(*) as cl_2day , 0 as cl_3day, 0 as cl_4day , 0 as cl_5day , 0 as cl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -2))  "
	 * + "		union "+
	 * "		select  0 as cl_1day, 0 as cl_2day,  count(*) as cl_3day , 0 as cl_4day , 0 as cl_5day , 0 as cl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -3))  "
	 * + "		union  "+
	 * "		select  0 as cl_1day, 0 as cl_2day, 0 as cl_3day ,count(*) as cl_4day , 0 as cl_5day, 0 as cl_moreday  from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -4))  "
	 * + "		union  "+
	 * "		select  0 as cl_1day, 0 as cl_2day, 0 as cl_3day , 0 as cl_4day,  count(*) as cl_5day, 0 as cl_moreday   from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -5))  "
	 * + "		union  "+
	 * "		select  0 as cl_1day, 0 as cl_2day, 0 as cl_3day , 0 as cl_4day , 0 as cl_5day , count(*) as cl_moreday  from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date < (select (CURRENT_DATE -5))  "
	 * + "		)x ";
	 * 
	 * 
	 * 
	 * 
	 * ps= con.prepareStatement(queryCLDay);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { ac.setCl_1Day(rs.getInt("cl_1day"));
	 * ac.setCl_2Day(rs.getInt("cl_2day")); ac.setCl_3Day(rs.getInt("cl_3day"));
	 * ac.setCl_4Day(rs.getInt("cl_4day")); ac.setCl_5Day(rs.getInt("cl_5day"));
	 * ac.setCl_MoreDay(rs.getInt("cl_moreday"));
	 * 
	 * 
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * 
	 * String queryFLDay=
	 * 
	 * 
	 * 
	 * 
	 * 
	 * "	select sum(x.fl_1day) as fl_1day, sum(x.fl_2day) as fl_2day , sum(x.fl_3day) as fl_3day , sum(x.fl_4day) as fl_4day , sum(x.fl_5day ) as fl_5day, sum(x.fl_moreday) as fl_moreday  from  "
	 * + "	( "+
	 * 
	 * "	select   count(*) as fl_1day , 0 as fl_2day , 0 as fl_3day ,0 as fl_4day , 0 as fl_5day , 0 as fl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -1))  "
	 * + "	union "+
	 * "	select  0 as fl_1day, count(*) as fl_2day , 0 as fl_3day, 0 as fl_4day , 0 as fl_5day , 0 as fl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -2)) "
	 * + "	union "+
	 * "	select  0 as fl_1day, 0 as fl_2day,  count(*) as fl_3day , 0 as fl_4day , 0 as fl_5day , 0 as fl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -3)) "
	 * + "	union  "+
	 * "	select  0 as fl_1day, 0 as fl_2day, 0 as fl_3day ,count(*) as fl_4day , 0 as fl_5day, 0 as fl_moreday  from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -4)) "
	 * + "	union  "+
	 * "	select  0 as fl_1day, 0 as fl_2day, 0 as fl_3day , 0 as fl_4day,  count(*) as fl_5day, 0 as fl_moreday   from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -5)) "
	 * + "	union "+
	 * "	select  0 as fl_1day, 0 as fl_2day, 0 as fl_3day , 0 as fl_4day , 0 as fl_5day , count(*) as fl_moreday  from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date < (select (CURRENT_DATE -5))  "
	 * + "	) x ";
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * ps= con.prepareStatement(queryFLDay);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { ac.setFl_1Day(rs.getInt("fl_1day"));
	 * ac.setFl_2Day(rs.getInt("fl_2day")); ac.setFl_3Day(rs.getInt("fl_3day"));
	 * ac.setFl_4Day(rs.getInt("fl_4day")); ac.setFl_5Day(rs.getInt("fl_5day"));
	 * ac.setFl_MoreDay(rs.getInt("fl_moreday"));
	 * 
	 * 
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * 
	 * 
	 * 
	 * String queryBeerDay=
	 * 
	 * 
	 * 
	 * "	select sum(x.beer_1day) as beer_1day, sum(x.beer_2day) as beer_2day , sum(x.beer_3day) as beer_3day , sum(x.beer_4day) as beer_4day , sum(x.beer_5day ) as beer_5day, sum(x.beer_moreday) as beer_moreday  from  "
	 * + "	( "+
	 * 
	 * "	select   count(*) as beer_1day , 0 as beer_2day , 0 as beer_3day ,0 as beer_4day , 0 as beer_5day , 0 as beer_moreday from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -1)) "
	 * + "	union "+
	 * "	select  0 as beer_1day, count(*) as beer_2day , 0 as beer_3day, 0 as beer_4day , 0 as beer_5day , 0 as beer_moreday from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -2)) "
	 * + "	union "+
	 * "	select  0 as beer_1day, 0 as beer_2day,  count(*) as beer_3day , 0 as beer_4day , 0 as beer_5day , 0 as beer_moreday from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -3)) "
	 * + "	union  "+
	 * "	select  0 as beer_1day, 0 as beer_2day, 0 as beer_3day ,count(*) as beer_4day , 0 as beer_5day, 0 as beer_moreday  from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -4))  "
	 * + "	union  "+
	 * "	select  0 as beer_1day, 0 as beer_2day, 0 as beer_3day , 0 as beer_4day,  count(*) as beer_5day, 0 as beer_moreday   from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date= (select (CURRENT_DATE -5))  "
	 * + "	union  "+
	 * "	select  0 as beer_1day, 0 as beer_2day, 0 as beer_3day , 0 as beer_4day , 0 as beer_5day , count(*) as beer_moreday  from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date < (select (CURRENT_DATE -5)) "
	 * + "	)x ";
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * ps= con.prepareStatement(queryBeerDay);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * 
	 * if(rs.next()) { ac.setBeer_1Day(rs.getInt("beer_1day"));
	 * ac.setBeer_2Day(rs.getInt("beer_2day"));
	 * ac.setBeer_3Day(rs.getInt("beer_3day"));
	 * ac.setBeer_4Day(rs.getInt("beer_4day"));
	 * ac.setBeer_5Day(rs.getInt("beer_5day"));
	 * ac.setBeer_MoreDay(rs.getInt("beer_moreday"));
	 * 
	 * 
	 * } ps.close(); rs.close();
	 * 
	 * }
	 * 
	 * catch (Exception e) { e.printStackTrace(); } finally { try { con.close();
	 * } catch (Exception e2) { e2.printStackTrace(); } }
	 * 
	 * }
	 */

	public void getLiquorIndent(HomePageAction ac) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			rs = null;
			ps = null;

			SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
			
			String querycl = 	" select count(*) as cl_pending from fl2d.indent_for_wholesale a where a.vch_type='D'  "+
								" and a.vch_licence_type='CL2' and a.finalize_flag='F'  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases > (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) )";
				

			/*String querycl = " select count(*) as cl_pending from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' "
					+ " and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) ";*/

			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(querycl);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setClTot_P(rs.getInt(1));

			}
			ps.close();
			rs.close();
			
			String queryfl = 	" select count(*) as cl_pending from fl2d.indent_for_wholesale a where a.vch_type='D'  "+
								" and a.vch_licence_type='FL2' and a.finalize_flag='F'  "+
								"  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases > (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) ";

			/*String queryfl = "	 select   count(*) as fl_pending  from fl2d.indent_for_wholesale a  where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' "
					+ " 		and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no= a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) ";*/

			ps = con.prepareStatement(queryfl);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setFlTot_P(rs.getInt(1));

			}
			ps.close();
			rs.close();
			
			String querybeer = 	" select count(*) as cl_pending from fl2d.indent_for_wholesale a where a.vch_type='B'  "+
								" and a.vch_licence_type='FL2B' and a.finalize_flag='F'  "+
								"  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases > (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) ";

			/*String querybeer = "  select  count(*) as beer_pending from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' "
					+ " 		and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) ";*/

			ps = con.prepareStatement(querybeer);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setBeerTot_P(rs.getInt(1));

			}
			ps.close();
			rs.close();

			// ------------- oldest Date ----------------
			
			String querycl_oldestDate = " select min(a.cr_date)  as cl_oldest from fl2d.indent_for_wholesale a where a.vch_type='D'                              "+
										" and a.vch_licence_type='CL2' and a.finalize_flag='F'                                                                  "+
										"  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases > (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) )  ";

			/*String querycl_oldestDate =

			" select min(a.cr_date)  as cl_oldest from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' "
					+ " and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) ";*/

			ps = con.prepareStatement(querycl_oldestDate);

			rs = ps.executeQuery();

			if (rs.next()) {
				String appDate = date.format(Utility
						.convertSqlDateToUtilDate(rs.getDate(1)));
				ac.setCl_Oldest_Date(appDate);
				// ac.setCl_Oldest_Date(rs.getString(1));

			}
			ps.close();
			rs.close();
			
			

			// -------------End oldest Date ----------------
			
			
			String queryCLDay =

			"	select sum(x.cl_1day) as cl_1day, sum(x.cl_2day) as cl_2day , sum(x.cl_3day) as cl_3day , sum(x.cl_4day) as cl_4day , sum(x.cl_5day ) as cl_5day, sum(x.cl_moreday) as cl_moreday  from  "
					+ "	( "
					+

					"		select   count(*) as cl_1day , 0 as cl_2day , 0 as cl_3day ,0 as cl_4day , 0 as cl_5day , 0 as cl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and a.finalize_flag='F'  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-3)) and (select (CURRENT_DATE)) "
					+ "		union "
					+ "		select  0 as cl_1day, count(*) as cl_2day , 0 as cl_3day, 0 as cl_4day , 0 as cl_5day , 0 as cl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and a.finalize_flag='F'  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-6)) and (select (CURRENT_DATE-4))  "
					+ "		union "
					+ "		select  0 as cl_1day, 0 as cl_2day,  count(*) as cl_3day , 0 as cl_4day , 0 as cl_5day , 0 as cl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and a.finalize_flag='F'  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-10)) and (select (CURRENT_DATE-7))  "
					+ "		union  "
					+ "		select  0 as cl_1day, 0 as cl_2day, 0 as cl_3day ,count(*) as cl_4day , 0 as cl_5day, 0 as cl_moreday  from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and a.finalize_flag='F'  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-13)) and (select (CURRENT_DATE-11)) "
					+ "		union  "
					+ "		select  0 as cl_1day, 0 as cl_2day, 0 as cl_3day , 0 as cl_4day,  count(*) as cl_5day, 0 as cl_moreday   from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and a.finalize_flag='F'  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-15)) and (select (CURRENT_DATE-14))  "
					+ "		union  "
					+ "		select  0 as cl_1day, 0 as cl_2day, 0 as cl_3day , 0 as cl_4day , 0 as cl_5day , count(*) as cl_moreday  from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and a.finalize_flag='F'  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date < (select (CURRENT_DATE -15))  "
					+ "		)x ";
			
			

			/*String queryCLDay =

			"	select sum(x.cl_1day) as cl_1day, sum(x.cl_2day) as cl_2day , sum(x.cl_3day) as cl_3day , sum(x.cl_4day) as cl_4day , sum(x.cl_5day ) as cl_5day, sum(x.cl_moreday) as cl_moreday  from  "
					+ "	( "
					+

					"		select   count(*) as cl_1day , 0 as cl_2day , 0 as cl_3day ,0 as cl_4day , 0 as cl_5day , 0 as cl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-3)) and (select (CURRENT_DATE)) "
					+ "		union "
					+ "		select  0 as cl_1day, count(*) as cl_2day , 0 as cl_3day, 0 as cl_4day , 0 as cl_5day , 0 as cl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-6)) and (select (CURRENT_DATE-4))  "
					+ "		union "
					+ "		select  0 as cl_1day, 0 as cl_2day,  count(*) as cl_3day , 0 as cl_4day , 0 as cl_5day , 0 as cl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-10)) and (select (CURRENT_DATE-7))  "
					+ "		union  "
					+ "		select  0 as cl_1day, 0 as cl_2day, 0 as cl_3day ,count(*) as cl_4day , 0 as cl_5day, 0 as cl_moreday  from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-13)) and (select (CURRENT_DATE-11)) "
					+ "		union  "
					+ "		select  0 as cl_1day, 0 as cl_2day, 0 as cl_3day , 0 as cl_4day,  count(*) as cl_5day, 0 as cl_moreday   from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-15)) and (select (CURRENT_DATE-14))  "
					+ "		union  "
					+ "		select  0 as cl_1day, 0 as cl_2day, 0 as cl_3day , 0 as cl_4day , 0 as cl_5day , count(*) as cl_moreday  from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='CL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date < (select (CURRENT_DATE -15))  "
					+ "		)x ";*/

			// System.out.println("cl"+queryCLDay);

			ps = con.prepareStatement(queryCLDay);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setCl_1Day(rs.getInt("cl_1day"));
				ac.setCl_2Day(rs.getInt("cl_2day"));
				ac.setCl_3Day(rs.getInt("cl_3day"));
				ac.setCl_4Day(rs.getInt("cl_4day"));
				ac.setCl_5Day(rs.getInt("cl_5day"));
				ac.setCl_MoreDay(rs.getInt("cl_moreday"));

			}
			ps.close();
			rs.close();
			
			
			
			String queryFLDay =

			"	select sum(x.fl_1day) as fl_1day, sum(x.fl_2day) as fl_2day , sum(x.fl_3day) as fl_3day , sum(x.fl_4day) as fl_4day , sum(x.fl_5day ) as fl_5day, sum(x.fl_moreday) as fl_moreday  from  "
					+ "	( "
					+

					"	select   count(*) as fl_1day , 0 as fl_2day , 0 as fl_3day ,0 as fl_4day , 0 as fl_5day , 0 as fl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2'  and a.finalize_flag='F' and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-3)) and (select (CURRENT_DATE)) "
					+ "	union "
					+ "	select  0 as fl_1day, count(*) as fl_2day , 0 as fl_3day, 0 as fl_4day , 0 as fl_5day , 0 as fl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2'  and a.finalize_flag='F'  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-6)) and (select (CURRENT_DATE-4))  "
					+ "	union "
					+ "	select  0 as fl_1day, 0 as fl_2day,  count(*) as fl_3day , 0 as fl_4day , 0 as fl_5day , 0 as fl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2'  and a.finalize_flag='F' and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-10)) and (select (CURRENT_DATE-7))  "
					+ "	union  "
					+ "	select  0 as fl_1day, 0 as fl_2day, 0 as fl_3day ,count(*) as fl_4day , 0 as fl_5day, 0 as fl_moreday  from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2'  and a.finalize_flag='F' and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-13)) and (select (CURRENT_DATE-11)) "
					+ "	union  "
					+ "	select  0 as fl_1day, 0 as fl_2day, 0 as fl_3day , 0 as fl_4day,  count(*) as fl_5day, 0 as fl_moreday   from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2'  and a.finalize_flag='F' and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-15)) and (select (CURRENT_DATE-14))  "
					+ "	union "
					+ "	select  0 as fl_1day, 0 as fl_2day, 0 as fl_3day , 0 as fl_4day , 0 as fl_5day , count(*) as fl_moreday  from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2'  and a.finalize_flag='F' and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date < (select (CURRENT_DATE -15))  "
					+ "	) x ";

			/*String queryFLDay =

			"	select sum(x.fl_1day) as fl_1day, sum(x.fl_2day) as fl_2day , sum(x.fl_3day) as fl_3day , sum(x.fl_4day) as fl_4day , sum(x.fl_5day ) as fl_5day, sum(x.fl_moreday) as fl_moreday  from  "
					+ "	( "
					+

					"	select   count(*) as fl_1day , 0 as fl_2day , 0 as fl_3day ,0 as fl_4day , 0 as fl_5day , 0 as fl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-3)) and (select (CURRENT_DATE)) "
					+ "	union "
					+ "	select  0 as fl_1day, count(*) as fl_2day , 0 as fl_3day, 0 as fl_4day , 0 as fl_5day , 0 as fl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-6)) and (select (CURRENT_DATE-4))  "
					+ "	union "
					+ "	select  0 as fl_1day, 0 as fl_2day,  count(*) as fl_3day , 0 as fl_4day , 0 as fl_5day , 0 as fl_moreday from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-10)) and (select (CURRENT_DATE-7))  "
					+ "	union  "
					+ "	select  0 as fl_1day, 0 as fl_2day, 0 as fl_3day ,count(*) as fl_4day , 0 as fl_5day, 0 as fl_moreday  from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-13)) and (select (CURRENT_DATE-11)) "
					+ "	union  "
					+ "	select  0 as fl_1day, 0 as fl_2day, 0 as fl_3day , 0 as fl_4day,  count(*) as fl_5day, 0 as fl_moreday   from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-15)) and (select (CURRENT_DATE-14))  "
					+ "	union "
					+ "	select  0 as fl_1day, 0 as fl_2day, 0 as fl_3day , 0 as fl_4day , 0 as fl_5day , count(*) as fl_moreday  from fl2d.indent_for_wholesale a where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date < (select (CURRENT_DATE -15))  "
					+ "	) x ";*/

			// System.out.println("Fl"+queryFLDay);

			ps = con.prepareStatement(queryFLDay);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setFl_1Day(rs.getInt("fl_1day"));
				ac.setFl_2Day(rs.getInt("fl_2day"));
				ac.setFl_3Day(rs.getInt("fl_3day"));
				ac.setFl_4Day(rs.getInt("fl_4day"));
				ac.setFl_5Day(rs.getInt("fl_5day"));
				ac.setFl_MoreDay(rs.getInt("fl_moreday"));

			}
			ps.close();
			rs.close();
			
			
			String queryBeerDay =

			"	select sum(x.beer_1day) as beer_1day, sum(x.beer_2day) as beer_2day , sum(x.beer_3day) as beer_3day , sum(x.beer_4day) as beer_4day , sum(x.beer_5day ) as beer_5day, sum(x.beer_moreday) as beer_moreday  from  "
					+ "	( "
					+

					"	select   count(*) as beer_1day , 0 as beer_2day , 0 as beer_3day ,0 as beer_4day , 0 as beer_5day , 0 as beer_moreday from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B'  and a.finalize_flag='F' and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-3)) and (select (CURRENT_DATE)) "
					+ "	union "
					+ "	select  0 as beer_1day, count(*) as beer_2day , 0 as beer_3day, 0 as beer_4day , 0 as beer_5day , 0 as beer_moreday from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B'  and a.finalize_flag='F' and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-6)) and (select (CURRENT_DATE-4))  "
					+ "	union "
					+ "	select  0 as beer_1day, 0 as beer_2day,  count(*) as beer_3day , 0 as beer_4day , 0 as beer_5day , 0 as beer_moreday from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B'  and a.finalize_flag='F' and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-10)) and (select (CURRENT_DATE-7))  "
					+ "	union  "
					+ "	select  0 as beer_1day, 0 as beer_2day, 0 as beer_3day ,count(*) as beer_4day , 0 as beer_5day, 0 as beer_moreday  from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B'  and a.finalize_flag='F' and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-13)) and (select (CURRENT_DATE-11)) "
					+ "	union  "
					+ "	select  0 as beer_1day, 0 as beer_2day, 0 as beer_3day , 0 as beer_4day,  count(*) as beer_5day, 0 as beer_moreday   from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B'  and a.finalize_flag='F' and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date between (select (CURRENT_DATE-15)) and (select (CURRENT_DATE-14))  "
					+ "	union  "
					+ "	select  0 as beer_1day, 0 as beer_2day, 0 as beer_3day , 0 as beer_4day , 0 as beer_5day , count(*) as beer_moreday  from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B'  and a.finalize_flag='F' and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases != (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) and cr_date < (select (CURRENT_DATE -15)) "
					+ "	)x ";
			

			/*String queryBeerDay =

			"	select sum(x.beer_1day) as beer_1day, sum(x.beer_2day) as beer_2day , sum(x.beer_3day) as beer_3day , sum(x.beer_4day) as beer_4day , sum(x.beer_5day ) as beer_5day, sum(x.beer_moreday) as beer_moreday  from  "
					+ "	( "
					+

					"	select   count(*) as beer_1day , 0 as beer_2day , 0 as beer_3day ,0 as beer_4day , 0 as beer_5day , 0 as beer_moreday from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-3)) and (select (CURRENT_DATE)) "
					+ "	union "
					+ "	select  0 as beer_1day, count(*) as beer_2day , 0 as beer_3day, 0 as beer_4day , 0 as beer_5day , 0 as beer_moreday from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-6)) and (select (CURRENT_DATE-4))  "
					+ "	union "
					+ "	select  0 as beer_1day, 0 as beer_2day,  count(*) as beer_3day , 0 as beer_4day , 0 as beer_5day , 0 as beer_moreday from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-10)) and (select (CURRENT_DATE-7))  "
					+ "	union  "
					+ "	select  0 as beer_1day, 0 as beer_2day, 0 as beer_3day ,count(*) as beer_4day , 0 as beer_5day, 0 as beer_moreday  from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-13)) and (select (CURRENT_DATE-11)) "
					+ "	union  "
					+ "	select  0 as beer_1day, 0 as beer_2day, 0 as beer_3day , 0 as beer_4day,  count(*) as beer_5day, 0 as beer_moreday   from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date between (select (CURRENT_DATE-15)) and (select (CURRENT_DATE-14))  "
					+ "	union  "
					+ "	select  0 as beer_1day, 0 as beer_2day, 0 as beer_3day , 0 as beer_4day , 0 as beer_5day , count(*) as beer_moreday  from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) and cr_date < (select (CURRENT_DATE -15)) "
					+ "	)x ";*/

			// System.out.println("beer"+queryBeerDay);

			ps = con.prepareStatement(queryBeerDay);

			rs = ps.executeQuery();

			if (rs.next()) {
				ac.setBeer_1Day(rs.getInt("beer_1day"));
				ac.setBeer_2Day(rs.getInt("beer_2day"));
				ac.setBeer_3Day(rs.getInt("beer_3day"));
				ac.setBeer_4Day(rs.getInt("beer_4day"));
				ac.setBeer_5Day(rs.getInt("beer_5day"));
				ac.setBeer_MoreDay(rs.getInt("beer_moreday"));

			}
			ps.close();
			rs.close();
			
							String queryfl_oldestDate = " select min(a.cr_date)  as cl_oldest from fl2d.indent_for_wholesale a where a.vch_type='D'                             "+
									" and a.vch_licence_type='FL2' and a.finalize_flag='F'                                                                  "+
									"  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases > (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) )";
				
				/*String queryfl_oldestDate = "	 select   min(a.cr_date)  as fl_oldest  from fl2d.indent_for_wholesale a  where a.vch_type='D' and a.vch_licence_type='FL2' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' "
				+ " 		and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no= a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) ";*/
				
				ps = con.prepareStatement(queryfl_oldestDate);
				
				rs = ps.executeQuery();
				
				if (rs.next()) {
				String appDate = date.format(Utility
					.convertSqlDateToUtilDate(rs.getDate(1)));
				ac.setFl_Oldest_Date(appDate);
				// ac.setFl_Oldest_Date(rs.getString(1));
				
				}
				ps.close();
				rs.close();
				
				String querybeer_oldestDate =   " select min(a.cr_date)  as cl_oldest from fl2d.indent_for_wholesale a where a.vch_type='B'                             "+
									" and a.vch_licence_type='FL2B' and a.finalize_flag='F'                                                                  "+
									"  and (a.vch_action_taken  in ('A','O') or a.vch_action_taken is null or  (a.vch_action_taken  in ('RJ') and a.chrg_reject_dt is null) )and ( a.total_cases > (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=a.indent_no ) ) ";
				
				/*String querybeer_oldestDate = "  select  min(a.cr_date)  as beer_oldest from fl2d.indent_for_wholesale a where a.vch_type='B' and a.vch_licence_type='FL2B' and ( a.vch_action_taken !='C' or a.vch_action_taken is null ) and a.finalize_flag='F' "
				+ " 		and (select SUM(COALESCE(b.no_of_box,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no  ) > (select SUM(COALESCE(b.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  b where b.indent_no=a.indent_no ) ";*/
				
				ps = con.prepareStatement(querybeer_oldestDate);
				
				rs = ps.executeQuery();
				
				if (rs.next()) {
				String appDate = date.format(Utility
					.convertSqlDateToUtilDate(rs.getDate(1)));
				ac.setBeer_Oldest_Date(appDate);
				// ac.setBeer_Oldest_Date(rs.getString(1));
				
				}
				ps.close();
				rs.close();

		}

		catch (Exception e) {
			e.printStackTrace();
			//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage() ,e.getMessage()));
		} finally {
			try {
				con.close();
			} catch (Exception e2) {

			}
		}

	}

	public int getdetdash(HomePageAction ac) {

		Connection c = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		String filter = "";

		
		String q = "select ceil((sum(m.salecl))/100000) ,ceil((sum(m.bottlingcl))/100000) ,ceil((sum(m.salefl))/100000) ,ceil((sum(m.bottlingfl) )/100000),                                                                                                                                                                                                                                                                                                                                                                                                                                             "
				+ "						ceil((sum(m.salebeer) )/100000),ceil((sum(m.bottlingbeer))/100000) ,ceil((sum(m.salecl1))/100000) ,ceil((sum(m.bottlingcl1) )/100000),                                                                                                                                                                                                                                                                                                                                                                                                                                      "
				+ "						ceil((sum(m.salefl1) )/100000),ceil((sum(m.bottlingfl1))/100000) ,ceil((sum(m.salebeer1))/100000) ,ceil((sum(m.bottlingbeer1))/100000),                                                                                                                                                                                                                                                                                                                                                                                                                                     "
				+ "						ceil((sum(dutycl))/10000000), ceil((sum(dutycl1))/10000000), ceil((sum(dutyfl))/10000000), ceil((sum(dutyfl1))/10000000), ceil((sum(dutybeer))/10000000),                                                                                                                                                                                                                                                                                                                                                                                                                   "
				+ "						ceil((sum(dutybeer1))/10000000) 				                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "
				+ "						from( 	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    "
				+ "			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "
				+ "			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "
				+ "						                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "
				+ "			             select sum(salecl) as salecl, (select sum(xx.bottling_no_of_box) from (select case when c.strength = 25 then (coalesce(a.bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g, distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=a.packaging_id )/1000,0)*25)/36 when c.strength=42.8                                                                                                                                                                                                                            "
				+ "			          then (coalesce(a.bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g, distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=a.packaging_id )/1000,0)*42.8)/36 else coalesce(a.bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g, distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=a.packaging_id )/1000,0) end as bottling_no_of_box   from  distillery.daily_bottling_stock_19_20 a, distillery.brand_registration_19_20 c                              "
				+ "						where  a.brand_id=c.brand_id and bottling_under in('CL') and date<=(now() - interval '1 day')::date)xx) as bottlingcl,0 as salefl,0 as bottlingfl,0 as salebeer,                                                                                                                                                                                                                                                                                                                                                                                                            "
				+ "			         0 as bottlingbeer,0 as salecl1,0 as bottlingcl1,0 as salefl1,0 as bottlingfl1,0 as salebeer1,0 as bottlingbeer1,                                                                                                                                                                                                                                                                                                                                                                                                                                                               "
				+ "			         sum(dutycl) as dutycl,0 as dutycl1,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "
				+ "			         0 as dutyfl, 0 as dutyfl1, 0 as dutybeer, 0 as dutybeer1                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       "
				+ "						   from                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     "
				+ "			         (select case when c.strength = 25 then (coalesce(a.dispatchd_bottl*a.size::numeric/1000,0)*25)/36 when c.strength=42.8                                                                                                                                                                                                                                                                                                                                                                                                                                                         "
				+ "			          then (coalesce(a.dispatchd_bottl*a.size::numeric/1000,0)*42.8)/36 else coalesce(a.dispatchd_bottl*a.size::numeric/1000,0) end as salecl                                                                                                                                                                                                                                                                                                                                                                                                                                       "
				+ "			          , 0 as salefl,0 as bottlingfl,0 as salebeer,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  "
				+ "						0 as bottlingbeer,0 as salecl1,0 as bottlingcl1,0 as salefl1,0 as bottlingfl1,0 as salebeer1,0 as bottlingbeer1,                                                                                                                                                                                                                                                                                                                                                                                                                                                            "
				+ "						( coalesce(a.duty,0)+coalesce(a.addduty,0)) as dutycl,0 as dutycl1, 0 as dutyfl, 0 as dutyfl1, 0 as dutybeer, 0 as dutybeer1                                                                                                                                                                                                                                                                                                                                                                                                                                                "
				+ "						from distillery.cl2_stock_trxn_19_20 a, distillery.gatepass_to_manufacturewholesale_cl_19_20 b, distillery.brand_registration_19_20 c                                                                                                                                                                                                                                                                                                                                                                                                                                       "
				+ "			            where a.int_brand_id = c.brand_id and  a.vch_gatepass_no=b.vch_gatepass_no and b.dt_date <=(now() - interval '1 day')::date)xx                                                                                                                                                                                                                                                                                                                                                                                                                                              "
				+ "						                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "
				+ "			                union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   "
				+ "			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "
				+ "			                select 0 as salecl,  0 as bottlingcl,sum(coalesce(a.dispatch_bottle*a.size::numeric/1000,0)) as salefl,(select sum((bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g,                                                                                                                                                                                                                                                                                                                                                                 "
				+ "                        distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=cc.packaging_id ))/1000)                                                                                                                                                                                                                                                                                                                                                                                                                                                                    "
				+ "						from distillery.daily_bottling_stock_19_20 cc    where bottling_under in('FL3', 'FL3A') and date<=(now() - interval '1 day')::date ) as bottlingfl,                                                                                                                                                                                                                                                                                                                                                                                                                         "
				+ "						0 as salebeer,0 as bottlingbeer,0 as salecl1,0 as bottlingcl1,0 as salefl1,0 as bottlingfl1,0 as salebeer1,0 as bottlingbeer1,                                                                                                                                                                                                                                                                                                                                                                                                                                              "
				+ "						0 as dutycl,0 as dutycl1, (select sum(coalesce(x.duty,0))+sum(coalesce(x.addduty,0)) from distillery.fl1_stock_trxn_19_20 x ,                                                                                                                                                                                                                                                                                                                                                                                                                                               "
				+ "						distillery.gatepass_to_manufacturewholesale_19_20 y    where  x.vch_gatepass_no=y.vch_gatepass_no and y.dt_date <= (now() - interval '1 day')::date   )                                                                                                                                                                                                                                                                                                                                                                                                                     "
				+ "						as dutyfl, 0 as dutyfl1, 0 as dutybeer, 0 as dutybeer1  from distillery.fl2_stock_trxn_19_20 a,distillery.gatepass_to_districtwholesale_19_20 b                                                                                                                                                                                                                                                                                                                                                                                                                             "
				+ "						where  a.vch_gatepass_no=b.vch_gatepass_no and b.dt_date <= (now() - interval '1 day')::date                                                                                                                                                                                                                                                                                                                                                                                                                                                                                "
				+ "			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "
				+ "			                union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   "
				+ "			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "
				+ "			                select  0 as salecl,  0 as bottlingcl,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  "
				+ "						0 as salefl,0 as bottlingfl,sum(coalesce(a.dispatch_bottle*a.size::numeric/1000,0)) as salebeer,      (select sum((bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g,                                                                                                                                                                                                                                                                                                                                                                      "
				+ "                        distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=cc.packaging_id ))/1000)                                                                                                                                                                                                                                                                                                                                                                                                                                                                    "
				+ "						from bwfl.daily_bottling_stock_19_20 cc where bottling_under in('FL3', 'FL3A') and date<=(now() - interval '1 day')::date ) as bottlingbeer,                                                                                                                                                                                                                                                                                                                                                                                                                                "
				+ "						0 as salecl1,0 as bottlingcl1,0 as salefl1,0 as bottlingfl1,0 as salebeer1,0 as bottlingbeer1 ,       0 as dutycl,0 as dutycl1, 0 as dutyfl,                                                                                                                                                                                                                                                                                                                                                                                                                                "
				+ "						0 as dutyfl1, (select sum(coalesce(x.duty,0))+sum(coalesce(x.addduty,0)) from bwfl.fl1_stock_trxn_19_20 x,                                                                                                                                                                                                                                                                                                                                                                                                                                                                  "
				+ "						bwfl.gatepass_to_manufacturewholesale_19_20 y  where  x.vch_gatepass_no=y.vch_gatepass_no and y.dt_date <= (now() - interval '1 day')::date   ) as dutybeer,                                                                                                                                                                                                                                                                                                                                                                                                                "
				+ "						0 as dutybeer1    from bwfl.fl2_stock_trxn_19_20 a,bwfl.gatepass_to_districtwholesale_19_20 b where  a.vch_gatepass_no=b.vch_gatepass_no and                                                                                                                                                                                                                                                                                                                                                                                                                                "
				+ "						b.dt_date <= (now() - interval '1 day')::date                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               "
				+ "			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "
				+ "			                union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   "
				+ "			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "
				+ "			             select 0 as salecl,0 as bottlingcl ,0 as salefl,0 as bottlingfl,0 as salebeer,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             "
				+ "			         0 as bottlingbeer,sum(salecl1) as salecl1,(select sum(xx.bottling_no_of_box) from (select case when c.strength = 25 then (coalesce(a.bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g, distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=a.packaging_id )/1000,0)*25)/36 when c.strength=42.8                                                                                                                                                                                                                    "
				+ "			          then (coalesce(a.bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g, distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=a.packaging_id )/1000,0)*42.8)/36 else coalesce(a.bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g, distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=a.packaging_id )/1000,0) end as bottling_no_of_box   from  distillery.daily_bottling_stock_19_20 a, distillery.brand_registration_19_20 c                              "
				+ "						where  a.brand_id=c.brand_id and bottling_under in('CL') and date<=(now() - interval '2 day')::date)xx) as bottlingcl1,0 as salefl1,0 as bottlingfl1,0 as salebeer1,0 as bottlingbeer1,                                                                                                                                                                                                                                                                                                                                                                                     "
				+ "			         0 dutycl,sum(dutycl1) as dutycl1,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "
				+ "			         0 as dutyfl, 0 as dutyfl1, 0 as dutybeer, 0 as dutybeer1                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       "
				+ "						   from                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     "
				+ "			         (select case when c.strength = 25 then (coalesce(a.dispatchd_bottl*a.size::numeric/1000,0)*25)/36 when c.strength=42.8                                                                                                                                                                                                                                                                                                                                                                                                                                                         "
				+ "			          then (coalesce(a.dispatchd_bottl*a.size::numeric/1000,0)*42.8)/36 else coalesce(a.dispatchd_bottl*a.size::numeric/1000,0) end as salecl1                                                                                                                                                                                                                                                                                                                                                                                                                                      "
				+ "			          , 0 as salefl,0 as bottlingfl,0 as salebeer,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  "
				+ "						0 as bottlingbeer,0 as salecl,0 as bottlingcl1,0 as salefl1,0 as bottlingfl1,0 as salebeer1,0 as bottlingbeer1,                                                                                                                                                                                                                                                                                                                                                                                                                                                             "
				+ "						( coalesce(a.duty,0)+coalesce(a.addduty,0)) as dutycl1,0 as dutycl, 0 as dutyfl, 0 as dutyfl1, 0 as dutybeer, 0 as dutybeer1                                                                                                                                                                                                                                                                                                                                                                                                                                                "
				+ "						from distillery.cl2_stock_trxn_19_20 a, distillery.gatepass_to_manufacturewholesale_cl_19_20 b, distillery.brand_registration_19_20 c                                                                                                                                                                                                                                                                                                                                                                                                                                       "
				+ "			            where a.int_brand_id = c.brand_id and  a.vch_gatepass_no=b.vch_gatepass_no and b.dt_date <=(now() - interval '2 day')::date)xx                                                                                                                                                                                                                                                                                                                                                                                                                                              "
				+ "						                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "
				+ "			                union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   "
				+ "			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "
				+ "						select  0 as salecl,  0 as bottlingcl, 0 as salefl,0 as bottlingfl,0 as salebeer, 0 as bottlingbeer,0 as salecl1,0 as bottlingcl1,sum(coalesce(a.dispatch_bottle*a.size::numeric/1000,0)) as salefl1,                                                                                                                                                                                                                                                                                                                                                                       "
				+ "						(select sum((bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g,                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "
				+ "                        distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=cc.packaging_id ))/1000)  from distillery.daily_bottling_stock_19_20  cc   where bottling_under in('FL3', 'FL3A') and date<=(now() - interval '2 day')::date ) as bottlingfl1,0 as salebeer1,0 as bottlingbeer1,                                                                                                                                                                                                                                                                            "
				+ "					 0 as dutycl,0 as dutycl1, 0 as dutyfl, (select sum(coalesce(x.duty,0))+sum(coalesce(x.addduty,0)) from distillery.fl1_stock_trxn_19_20 x,distillery.gatepass_to_manufacturewholesale_19_20 y                                                                                                                                                                                                                                                                                                                                                                                   "
				+ "						where  x.vch_gatepass_no=y.vch_gatepass_no and y.dt_date <= (now() - interval '2 day')::date      ) as dutyfl1, 0 as dutybeer, 0 as dutybeer1    from distillery.fl2_stock_trxn_19_20 a,distillery.gatepass_to_districtwholesale_19_20 b                                                                                                                                                                                                                                                                                                                                    "
				+ "						where  a.vch_gatepass_no=b.vch_gatepass_no and b.dt_date <= (now() - interval '2 day')::date                                                                                                                                                                                                                                                                                                                                                                                                                                                                                "
				+ "			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "
				+ "			                union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   "
				+ "			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "
				+ "			                select 0 as salecl,  0 as bottlingcl,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   "
				+ "						0 as salefl,0 as bottlingfl,0 as salebeer, 0 as bottlingbeer,0 as salecl1,0 as bottlingcl1,0 as salefl1,0 as bottlingfl1,                                                                                                                                                                                                                                                                                                                                                                                                                                                   "
				+ "						sum(coalesce(a.dispatch_bottle*a.size::numeric/1000,0)) as salebeer1,   (select sum((bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g,                                                                                                                                                                                                                                                                                                                                                                                                    "
				+ "                        distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=cc.packaging_id ))/1000)  from bwfl.daily_bottling_stock_19_20 cc where                                                                                                                                                                                                                                                                                                                                                                                                                     "
				+ "						bottling_under in('FL3', 'FL3A') and date<=(now() - interval '2 day')::date ) as bottlingbeer1 ,         0 as dutycl,0 as dutycl1,                                                                                                                                                                                                                                                                                                                                                                                                                                          "
				+ "						0 as dutyfl, 0 as dutyfl1, 0 as dutybeer, (select sum(coalesce(x.duty,0))+sum(coalesce(x.addduty,0)) from bwfl.fl1_stock_trxn_19_20 x,                                                                                                                                                                                                                                                                                                                                                                                                                                      "
				+ "						bwfl.gatepass_to_manufacturewholesale_19_20 y  where  x.vch_gatepass_no=y.vch_gatepass_no and y.dt_date <= (now() - interval '2 day')::date  ) as dutybeer1                                                                                                                                                                                                                                                                                                                                                                                                                 "
				+ "						from bwfl.fl2_stock_trxn_19_20 a,bwfl.gatepass_to_districtwholesale_19_20 b where  a.vch_gatepass_no=b.vch_gatepass_no                                                                                                                                                                                                                                                                                                                                                                                                                                                      "
				+ "						and b.dt_date<=(now() - interval '2 day')::date )m    ; ";

		int i = 0;
		try {
			c = ConnectionToDataBase.getConnection();
			p = c.prepareStatement(q);
			  System.out.println(q);
			rs = p.executeQuery();
			if (rs.next()) {
				ac.setClprod(rs.getInt(2));
				ac.setFlprod(rs.getInt(4));
				ac.setClprod1(rs.getInt(8));
				ac.setFlprod1(rs.getInt(10));
				ac.setBeerprod(rs.getInt(6));
				ac.setBeerprod1(rs.getInt(12));
				ac.setCldipt(rs.getInt(1));
				ac.setFldipt(rs.getInt(3));
				ac.setCldipt1(rs.getInt(7));
				ac.setFldipt1(rs.getInt(9));
				ac.setBeerdipt(rs.getInt(5));
				ac.setBeerdipt1(rs.getInt(11));
				ac.setClduty(rs.getInt(13));
				ac.setClduty1(rs.getInt(14));
				ac.setFlduty(rs.getInt(15));
				ac.setFlduty1(rs.getInt(16));
				ac.setBeerduty(rs.getInt(17));
				ac.setBeerduty1(rs.getInt(18));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (p != null)
					p.close();
				if (c != null)
					c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	// ------------ end vivek code ----------------

	public ArrayList getTableData(HomePageAction ac) {

		Connection c = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		int i = 0;

		try {
			String q = getValidQuery(Integer.parseInt(ac.getUnit_type()));
			c = ConnectionToDataBase.getConnection();
			p = c.prepareStatement(q);
			rs = p.executeQuery();

			while (rs.next()) {

				i = i + 1;

				HomePageDatatable dt = new HomePageDatatable();

				dt.setSno(i);
				dt.setUnit_id(rs.getInt("unit_id"));
				dt.setUnit_name(rs.getString("unit_name"));
				dt.setDuty(rs.getString("duty"));
				dt.setDuty1(rs.getString("duty1"));
				dt.setSale(rs.getString("sale"));
				dt.setSale1(rs.getString("sale1"));
				dt.setBottling(rs.getString("bottling"));
				dt.setBottling1(rs.getString("bottling1"));
				list.add(dt);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (p != null)
					p.close();
				if (c != null)
					c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	public void getAppdata(HomePageAction ac) {

		Connection c = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		PreparedStatement p1 = null;
		ResultSet rs1 = null;
		PreparedStatement p2 = null;
		ResultSet rs2 = null;
		PreparedStatement p3 = null;
		ResultSet rs3 = null;
		PreparedStatement p4 = null;
		ResultSet rs4 = null;
		PreparedStatement p5 = null;
		ResultSet rs5 = null;
		PreparedStatement p6 = null;
		ResultSet rs6 = null;
		ArrayList list = new ArrayList();
		int i = 0;

		try {
			String q = "SELECT   count(distinct vch_application_no) filter (where old_vch_application_no is null)   as newapp ,"
					+ "count(distinct vch_application_no) filter (where old_vch_application_no is not null)   as renewapp  "
					+ "	FROM bwfl_license.upbond_applications_new where  "
					+ "   user4_name='Excise-Commissioner' and user4_dt is null";

			c = ConnectionToDataBase.getConnection();
			p = c.prepareStatement(q);
			rs = p.executeQuery();

			if (rs.next()) {
				ac.setRenewbondcount(rs.getInt("renewapp"));
				ac.setNewbondcount(rs.getInt("newapp"));
			}
			rs.close();p.close();
			 
			p1 = c.prepareStatement("select count(app_id) from brandlabel.brand_label_applications where user5_name='Excise-Commissioner' and user5_date is null");
			rs1 = p1.executeQuery();

			if (rs1.next()) {
				ac.setBrandcount(rs1.getInt(1));
			 
			}
			
			rs1.close();p1.close();
			 
			p1 = c.prepareStatement("select count(*) from distillery.app_request_reserve_adjustment_ena where user4_name='Excise-Commissioner' and user4_date is null");
			rs1 = p1.executeQuery();

			if (rs1.next()) {
				ac.setEnacount(rs1.getInt(1));
			 
			}
//rahul 01-10-2020
			rs1.close();p1.close();
			 
			p2 = c.prepareStatement("select count(*) from custom_bonds.mst_regis_importing_unit where user4_nm='Excise-Commissioner' and user4_dt is  null");
			rs2 = p2.executeQuery();

			if (rs2.next()) {
				ac.setNewimpotunitcount(rs2.getInt(1));
			 
			}
			
			rs2.close();p2.close();
			
			 
			p3 = c.prepareStatement(" select count(*) from bwfl_license.district_warehouse_applications_new where user4_name ='Excise-Commissioner' and user4_dt IS NULL AND  license_type='FL2D' and old_vch_application_no is not null ");
			rs3 = p3.executeQuery();

			if (rs3.next()) {
				ac.setFl2drenewcount(rs3.getInt(1));
			 
			}
			
			rs3.close();p3.close();
			
			 
			p4 = c.prepareStatement(" select sum(x.app_count)  from (select distinct count(*) as app_count from  industry.form_one_a_two a, industry.license_application_forwarding b " +
					                " WHERE  b.usernm = 'Excise-Commissioner' and a.id=b.int_form_id AND a.service_id=b.serviceid AND b.receipntforwardingdt IS NULL AND a.vch_file_path IS NULL" +
					                " AND a.forwarding_flag not in ('A','R')   and  a.status_code not in ('06','07') " +
					                " union  " +
					                " select distinct  count(*) as app_count FROM industry.application_to_establish_a_brewery a, industry.license_application_forwarding c    " +
					                " WHERE  c.usernm = 'Excise-Commissioner' and a.id_pk=c.int_form_id  AND a.service_id=c.serviceid AND c.receipntforwardingdt IS NULL AND a.vch_file_path IS NULL    " +
					                " AND a.forwarding_flag not in ('A','R')   and  a.status_code not in ('06','07')   " +
					                " union   " +
					                " select distinct  count(*) as app_count FROM industry.form33_application_to_establish_a_distillery a,   " +
					                " industry.license_application_forwarding c WHERE  c.usernm = 'Excise-Commissioner'  AND a.pk = c.int_form_id AND a.service_id=c.serviceid AND c.receipntforwardingdt IS NULL " +
					                " AND a.vch_file_path IS NULL  AND a.forwarding_flag not in ('A','R')   and  a.status_code not in ('06','07') " +
					                " union    " +
					                " select distinct  count(*) as app_count FROM industry.license_for_brewery a,industry.license_application_forwarding c     " +
					                " WHERE  c.usernm = 'Excise-Commissioner'   and a.int_app_id_f=c.int_form_id AND a.service_id=c.serviceid  " +
					                " AND c.receipntforwardingdt IS NULL AND a.vch_file_path IS NULL  AND a.forwarding_flag not in ('A','R')   and  a.status_code not in ('06','07')  " +
					                " union   " +
					                " select distinct  count(*) as app_count FROM industry.license_for_distillery a,industry.license_application_forwarding c   " +
					                " WHERE c.usernm = 'Excise-Commissioner' and a.int_app_id_f=c.int_form_id AND a.service_id=c.serviceid AND c.receipntforwardingdt IS NULL   " +
					                " AND a.vch_file_path IS NULL  AND a.forwarding_flag not in ('A','R')   and  a.status_code not in ('06','07'))x");
			
			rs4 = p4.executeQuery();

			if (rs4.next()) {
				ac.setNiveshmitracount(rs4.getInt(1));
			 
			}
			
			rs4.close();p4.close();
			
			 
			p5 = c.prepareStatement(" select sum(x.app_count) from ( " +
					                " select count(*) as app_count from janhit.permit_for_alcohol_spirit where  hq3_dt is null  and hq3_remark is  null " +
					                " and hq3_user ='Excise-Commissioner'   " +
					                " union    " +
					                " select count(*) as app_count from janhit.applicationformissuance where hq3_dt is null  and hq3_remark is  null     " +
					                " and hq3_user ='Excise-Commissioner'    " +
					                " union     " +
					                " select count(*) as app_count from janhit.permit_for_narcotic_drug  where   hq3_dt is null  and hq3_remark is  null     " +
					                " and hq3_user ='Excise-Commissioner'    " +
					                " union     " +
					                " select count(*) as app_count from janhit.applicationformissuanceimportpermit where  hq3_dt is null  and hq3_remark is  null     " +
					                " and hq3_user ='Excise-Commissioner')x ");
			rs5 = p5.executeQuery();

			if (rs5.next()) {
				ac.setUsefulpubliccount(rs5.getInt(1));
			 
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (p != null)
					p.close();
				if (c != null)
					c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		 
	}
	public String getValidQuery(int type) {
		String a = "";

		switch (type) {

		case 1:
			a = " select int_dissleri_id as unit_id,vch_undertaking_name as unit_name, round(sum(salecl)::numeric,2) as sale, round(sum(salecl1)::numeric,2) as sale1 , round(sum(bottlingcl)::numeric,2) as bottling,                                                                      "
					+ "				 round(sum(bottlingcl1)::numeric,2) as bottling1,    "
					+ "				 round(sum(dutycl)::numeric,2) as duty, round(sum(dutycl1)::numeric,2) as duty1  from                                                                                                                                                                              "
					+ "				 (select int_dissleri_id,sum(salecl) as salecl,0 as salecl1, (select sum(xx.bottling_no_of_bottle) from (select                                                                                                                                                                                                                                           "
					+ "				  (coalesce(a.bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g, distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=a.packaging_id )/1000,0)*c.strength)/36  as bottling_no_of_bottle   from  distillery.daily_bottling_stock_19_20 a, distillery.brand_registration_19_20 c                  "
					+ "				 where  a.distillery_id=xx.int_dissleri_id and  a.brand_id=c.brand_id and bottling_under in('CL') and date<=(now() - interval '1 day')::date)xx) as bottlingcl,                                                                                                                                                                                           "
					+ "				 0 as bottlingcl1, sum(dutycl) as dutycl,0 as dutycl1                                                                                                                                                                                                                                                                                                     "
					+ "				 from                                                                                                                                                                                                                                                                                                                                                     "
					+ "				 (select int_dissleri_id,case when c.strength = 25 then (coalesce((a.dispatchd_bottl*a.size::numeric)/1000,0)*25)/36 when c.strength=42.8                                                                                                                                                                                                                 "
					+ "				  then (coalesce((a.dispatchd_bottl*a.size::numeric)/1000,0)*42.8)/36 else coalesce((a.dispatchd_bottl*a.size::numeric)/1000,0) end as salecl,                                                                                                                                                                                                            "
					+ "				 ( coalesce(a.duty,0)+coalesce(a.addduty,0)) as dutycl                                                                                                                                                                                                                                                                                                    "
					+ "				 from distillery.cl2_stock_trxn_19_20 a, distillery.gatepass_to_manufacturewholesale_cl_19_20 b,                                                                                                                                                                                                                                                          "
					+ "				  distillery.brand_registration_19_20 c                                                                                                                                                                                                                                                                                                                   "
					+ "				 where a.int_brand_id = c.brand_id and  a.vch_gatepass_no=b.vch_gatepass_no and b.dt_date <=(now() - interval '1 day')::date)xx                                                                                                                                                                                                                           "
					+ "				 group by xx.int_dissleri_id                                                                                                                                                                                                                                                                                                                              "
					+ "				                                                                                                                                                                                                                                                                                                                                                          "
					+ "				 UNION                                                                                                                                                                                                                                                                                                                                                    "
					+ "				                                                                                                                                                                                                                                                                                                                                                          "
					+ "				 select int_dissleri_id,0 as salecl,sum(salecl1) as salecl1,0 as bottlingcl ,(select sum(xx.bottling_no_of_bottle) from (select                                                                                                                                                                                                                           "
					+ "				  (coalesce(a.bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g, distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=a.packaging_id )/1000,0)*c.strength)/36  as bottling_no_of_bottle   from  distillery.daily_bottling_stock_19_20 a, distillery.brand_registration_19_20 c                  "
					+ "				 where  a.distillery_id=xx.int_dissleri_id and  a.brand_id=c.brand_id and bottling_under in('CL') and date<=(now() - interval '2 day')::date)xx) as bottlingcl1,                                                                                                                                                                                          "
					+ "				 0 dutycl,sum(dutycl1) as dutycl1                                                                                                                                                                                                                                                                                                                         "
					+ "				 from                                                                                                                                                                                                                                                                                                                                                     "
					+ "				 (select int_dissleri_id, case when c.strength = 25 then (coalesce((a.dispatchd_bottl*a.size::numeric)/1000,0)*25)/36 when c.strength=42.8                                                                                                                                                                                                                "
					+ "				  then (coalesce((a.dispatchd_bottl*a.size::numeric)/1000,0)*42.8)/36 else coalesce((a.dispatchd_bottl*a.size::numeric)/1000,0) end as salecl1,                                                                                                                                                                                                           "
					+ "				 ( coalesce(a.duty,0)+coalesce(a.addduty,0)) as dutycl1,0 as dutycl, 0 as dutyfl, 0 as dutyfl1, 0 as dutybeer, 0 as dutybeer1                                                                                                                                                                                                                             "
					+ "				 from distillery.cl2_stock_trxn_19_20 a, distillery.gatepass_to_manufacturewholesale_cl_19_20 b, distillery.brand_registration_19_20 c                                                                                                                                                                                                                    "
					+ "				 where a.int_brand_id = c.brand_id and  a.vch_gatepass_no=b.vch_gatepass_no and                                                                                                                                                                                                                                                                           "
					+ "				  b.dt_date <=(now() - interval '2 day')::date)xx                                                                                                                                                                                                                                                                                                         "
					+ "				  group by xx.int_dissleri_id                                                                                                                                                                                                                                                                                                                             "
					+ "				                                                                                                                                                                                                                                                                                                                                                          "
					+ "				  order by int_dissleri_id)yy left join public.dis_mst_pd1_pd2_lic on yy.int_dissleri_id=int_app_id_f                                                                                                                                                                                                                                                     "
					+ "				  group by yy.int_dissleri_id, vch_undertaking_name order by vch_undertaking_name;;";
			break;

		case 2:
			a = "select int_dissleri_id as unit_id,vch_undertaking_name as unit_name, round(sum(salefl)::numeric,2) as sale , round(sum(salefl1)::numeric,2) as sale1, round(sum(bottlingfl)::numeric,2) as bottling,                                                                                                                                                                                                                                                                                          "
					+ "				 round(sum(bottlingfl1)::numeric,2) as bottling1,                                                                                                                                                                                                                                                                                                                                                                                                        "
					+ "				 round(sum(dutyfl)::numeric,2) as duty, round(sum(dutyfl1)::numeric,2) as duty1  from                                                                                                                                                                                                                                                                                                                                                  "
					+ "				 ( select int_dissleri_id,0 as salecl,  0 as bottlingcl,sum(coalesce((a.dispatch_bottle*size)/1000,0)) as salefl,(select sum((bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g, distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=cc.packaging_id ))/1000)                                                                                                                "
					+ "				 from distillery.daily_bottling_stock_19_20 cc  where distillery_id=int_dissleri_id and bottling_under in('FL3', 'FL3A') and date<=(now() - interval '1 day')::date ) as bottlingfl,                                                                                                                                                                                                                                                    "
					+ "				 0 as salebeer,0 as bottlingbeer,0 as salecl1,0 as bottlingcl1,0 as salefl1,0 as bottlingfl1,0 as salebeer1,0 as bottlingbeer1,                                                                                                                                                                                                                                                                                                        "
					+ "				 0 as dutycl,0 as dutycl1, (select sum(coalesce(x.duty,0))+sum(coalesce(x.addduty,0)) from distillery.fl1_stock_trxn_19_20 x ,                                                                                                                                                                                                                                                                                                         "
					+ "				 distillery.gatepass_to_manufacturewholesale_19_20 y    where a.int_dissleri_id=x.int_dissleri_id and  x.vch_gatepass_no=y.vch_gatepass_no and y.dt_date <= (now() - interval '1 day')::date   )                                                                                                                                                                                                                                       "
					+ "				 as dutyfl, 0 as dutyfl1, 0 as dutybeer, 0 as dutybeer1  from distillery.fl2_stock_trxn_19_20 a,distillery.gatepass_to_districtwholesale_19_20 b                                                                                                                                                                                                                                                                                       "
					+ "				 where  a.vch_gatepass_no=b.vch_gatepass_no and b.dt_date <= (now() - interval '1 day')::date                                                                                                                                                                                                                                                                                                                                          "
					+ "				 group by int_dissleri_id                                                                                                                                                                                                                                                                                                                                                                                                              "
					+ "				                                                                                                                                                                                                                                                                                                                                                                                                                                       "
					+ "				 union                                                                                                                                                                                                                                                                                                                                                                                                                                 "
					+ "				                                                                                                                                                                                                                                                                                                                                                                                                                                       "
					+ "				 select  int_dissleri_id,0 as salecl,  0 as bottlingcl, 0 as salefl,0 as bottlingfl,0 as salebeer, 0 as bottlingbeer,0 as salecl1,0 as bottlingcl1,sum(coalesce((a.dispatch_bottle*size)/1000,0)) as salefl1,                                                                                                                                                                                                                          "
					+ "				 (select sum((bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g, distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=cc.packaging_id ))/1000)  from distillery.daily_bottling_stock_19_20 cc    where distillery_id=int_dissleri_id and bottling_under in('FL3', 'FL3A') and date<=(now() - interval '2 day')::date ) as bottlingfl1,0 as salebeer1,0 as bottlingbeer1,       "
					+ "				 0 as dutycl,0 as dutycl1, 0 as dutyfl, (select sum(coalesce(x.duty,0))+sum(coalesce(x.addduty,0)) from distillery.fl1_stock_trxn_19_20 x,distillery.gatepass_to_manufacturewholesale_19_20 y                                                                                                                                                                                                                                          "
					+ "				 where a.int_dissleri_id=x.int_dissleri_id and x.vch_gatepass_no=y.vch_gatepass_no and y.dt_date <= (now() - interval '2 day')::date      ) as dutyfl1, 0 as dutybeer, 0 as dutybeer1    from distillery.fl2_stock_trxn_19_20 a,distillery.gatepass_to_districtwholesale_19_20 b                                                                                                                                                       "
					+ "				 where  a.vch_gatepass_no=b.vch_gatepass_no and b.dt_date <= (now() - interval '2 day')::date                                                                                                                                                                                                                                                                                                                                          "
					+ "				 group by int_dissleri_id)yy left join public.dis_mst_pd1_pd2_lic on yy.int_dissleri_id=int_app_id_f                                                                                                                                                                                                                                                                                                                                   "
					+ "				  group by yy.int_dissleri_id, vch_undertaking_name order by vch_undertaking_name;";
			break;

		case 3:
			a = "select brewery_nm as unit_name, yy.brewery_id as unit_id, round(sum(salebeer)::numeric,2) as sale, round(sum(salebeer1)::numeric,2) as sale1,                                                                                                                                                                                                                      "
					+ "				 round(sum(bottlingbeer)::numeric,2) as bottling, round(sum(bottlingbeer1)::numeric,2) as bottling1, round(sum(dutybeer)::numeric,2) as duty,round(sum(dutybeer1)::numeric,2) as duty1                                                                                                                                                                          "
					+ "				 from                                                                                                                                                                                                                                                                                                                       "
					+ "                                                                                                                                                                                                                                                                                                                                          "
					+ "                 (select  a.brewery_id, sum(coalesce((a.dispatch_bottle*size)/1000,0)) as salebeer, 0 as salebeer1,     (select sum((bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g, distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=cc.packaging_id ))/1000)           "
					+ "				 from bwfl.daily_bottling_stock_19_20 cc where a.brewery_id=distillery_id and bottling_under in('FL3', 'FL3A') and date<=(now() - interval '1 day')::date )                                                                                                                                                                 "
					+ "				 as bottlingbeer,0 as bottlingbeer1,                                                                                                                                                                                                                                                                                        "
					+ "				 (select sum(coalesce(x.duty,0))+sum(coalesce(x.addduty,0)) from bwfl.fl1_stock_trxn_19_20 x ,                                                                                                                                                                                                                              "
					+ "				 bwfl.gatepass_to_manufacturewholesale_19_20 y  where x.int_brewery_id=a.brewery_id  and  x.vch_gatepass_no=y.vch_gatepass_no and                                                                                                                                                                                           "
					+ "				 y.dt_date <= (now() - interval '1 day')::date   ) as dutybeer,                                                                                                                                                                                                                                                             "
					+ "				 0 as dutybeer1    from bwfl.fl2_stock_trxn_19_20 a,bwfl.gatepass_to_districtwholesale_19_20 b where                                                                                                                                                                                                                        "
					+ "				 a.vch_gatepass_no=b.vch_gatepass_no and                                                                                                                                                                                                                                                                                    "
					+ "				 b.dt_date <= (now() - interval '1 day')::date                                                                                                                                                                                                                                                                              "
					+ "				 group by a.brewery_id                                                                                                                                                                                                                                                                                                      "
					+ "				 union                                                                                                                                                                                                                                                                                                                      "
					+ "				 select  a.brewery_id,0 as salebeer,  sum(coalesce((a.dispatch_bottle*a.size)/1000,0)) as salebeer1,  0 as bottlingbeer,                                                                                                                                                                                                    "
					+ "				 (select sum((bottling_no_of_bottle*(select box_size::numeric from distillery.box_size_details g, distillery.packaging_details_19_20 h where g.box_id=h.box_id and h.package_id=cc.packaging_id ))/1000)                                                                                                                    "
					+ "				 from bwfl.daily_bottling_stock_19_20 cc where a.brewery_id=distillery_id and bottling_under in('FL3', 'FL3A') and date<=(now() - interval '2 day')::date )                                                                                                                                                                 "
					+ "				 as bottlingbeer1,                                                                                                                                                                                                                                                                                                          "
					+ "				 0 as dutybeer,                                                                                                                                                                                                                                                                                                             "
					+ "				 (select sum(coalesce(x.duty,0))+sum(coalesce(x.addduty,0)) from bwfl.fl1_stock_trxn_19_20 x ,                                                                                                                                                                                                                              "
					+ "				 bwfl.gatepass_to_manufacturewholesale_19_20 y  where x.int_brewery_id=a.brewery_id  and  x.vch_gatepass_no=y.vch_gatepass_no and                                                                                                                                                                                           "
					+ "				 y.dt_date <= (now() - interval '2 day')::date   ) as dutybeer1    from bwfl.fl2_stock_trxn_19_20 a,bwfl.gatepass_to_districtwholesale_19_20 b where                                                                                                                                                                        "
					+ "				 a.vch_gatepass_no=b.vch_gatepass_no and                                                                                                                                                                                                                                                                                    "
					+ "				 b.dt_date <= (now() - interval '2 day')::date                                                                                                                                                                                                                                                                              "
					+ "				 group by a.brewery_id)yy left join public.bre_mst_b1_lic on brewery_id=vch_app_id_f                                                                                                                                                                                                                                        "
					+ "				 group by brewery_id, brewery_nm order by brewery_nm";
			break;

		default:
			break;
		}

		return a;
	}

	
	
//rahul 30-09-2020
	
	public ArrayList getdist_mufllist(HomePageAction ac) {

		Connection c = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		int i = 0;

		try {
			String fl = " select 'FL' as type,sum(x.avlbottle)as avlbottle ,int_dist_id,vch_undertaking_name from (select int_dissleri_id as int_dist_id ,d.vch_undertaking_name," +
					   " sum(round(((c.int_stock-c.int_dispatched)+.5)/size)) as avlbottle " +
					   " from distillery.boxing_stock_20_21 c ,public.dis_mst_pd1_pd2_lic d " +
					   " where c.vch_lic_type not in ('CL')  and int_dissleri_id=d.int_app_id_f " +
					   " group by int_dissleri_id,vch_undertaking_name " +
					   " union  " +
					   " select int_dist_id,d.vch_undertaking_name, " +
					   " sum(round(((c.stock_bottles-coalesce(c.dispatch_bottles,0))+.5)/size)) as avlbottle  " +
					   " from  distillery.fl2_stock_20_21 c ,public.dis_mst_pd1_pd2_lic d where 	c.int_dist_id=d.int_app_id_f " +
					   " group by int_dist_id,vch_undertaking_name)x  where avlbottle>0   group by int_dist_id,vch_undertaking_name   order by vch_undertaking_name";
			
			
			String cl=" select 'CL' as type,sum(x.avlbottle) as avlbottle,int_dist_id,vch_undertaking_name from (" +
					  " select int_dissleri_id as int_dist_id ,f.vch_undertaking_name,sum(round(((c.int_stock-c.int_dispatched)+.5)/size)) as avlbottle    " +
					  " from distillery.boxing_stock_20_21 c ,public.dis_mst_pd1_pd2_lic f    " +
					  " where c.vch_lic_type='CL' and c.int_dissleri_id=f.int_app_id_f  group by int_dissleri_id,vch_undertaking_name  " +
					  " union    " +
					  " select int_dist_id,d.vch_undertaking_name, sum(round(((c.stock_bottles-coalesce(c.dispatch_bottles,0))+.5)/size)) as avlbottle    " +
					  " from  distillery.fl2_stock_20_21 c,public.dis_mst_pd1_pd2_lic d where 	c.int_dist_id=d.int_app_id_f    " +
					  " group by int_dist_id,vch_undertaking_name)x   where avlbottle>0 group by int_dist_id,vch_undertaking_name   order by vch_undertaking_name "; 
			
			String beer=" select 'BEER' as type, sum(x.avlbottle)as avlbottle,int_dist_id,vch_undertaking_name from ( " +
					    " select int_dissleri_id as int_dist_id ,f.vch_undertaking_name,sum(round(((c.int_stock-c.int_dispatched)+.5)/e.box_size)) as avlbottle   " +
					    " from bwfl.boxing_stock_20_21 c,distillery.packaging_details_20_21 d,distillery.box_size_details e  ,public.dis_mst_pd1_pd2_lic f   " +
					    " where c.vch_lic_type not in ('CL') and c.int_pckg_id=d.package_id  and  d.box_id=e.box_id  and c.int_dissleri_id=f.int_app_id_f  " +
					    " group by int_dissleri_id,vch_undertaking_name  " +
					    " union   " +
					    " select int_dist_id,d.vch_undertaking_name, sum(c.stock_box-coalesce(c.dispatch_box,0) ) as avlbottle from  bwfl.fl2_stock_20_21 c " +
					    " ,public.dis_mst_pd1_pd2_lic d where 	c.int_dist_id=d.int_app_id_f " +
					    " group by int_dist_id,vch_undertaking_name)x  where avlbottle>0 group by int_dist_id,vch_undertaking_name order by vch_undertaking_name ";
			
			
			c = ConnectionToDataBase.getConnection();
			if(ac.getType().equalsIgnoreCase("FL")){
				p = c.prepareStatement(fl);
			}else if(ac.getType().equalsIgnoreCase("CL")){
				p = c.prepareStatement(cl);
			}else if(ac.getType().equalsIgnoreCase("BEER")){
				
				p = c.prepareStatement(beer);
				}
			
			rs = p.executeQuery();

			while (rs.next()) {

				i = i + 1;

				HomePageDatatable dt = new HomePageDatatable();

				dt.setSno_mufl(i);
				dt.setDistillery_id_mufl(rs.getInt("int_dist_id"));
				dt.setDistillery_name_mufl(rs.getString("vch_undertaking_name"));
				dt.setMu_fl(rs.getInt("avlbottle"));
				dt.setType(rs.getString("type"));
				 
				list.add(dt);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (p != null)
					p.close();
				if (c != null)
					c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	public ArrayList getdist_wsllist(HomePageAction ac) {

		Connection c = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		int i = 0;

		try {
			String wfl = " select 'FL' as type,e.description ,sum(round(((c.recv_total_bottels-c.dispatchbotl)+.5)/size))  as avlbottle  " +
					     " from fl2d.fl2_2b_stock_20_21 c, licence.fl2_2b_2d_20_21 d ,public.district e  " +
					     " where int_app_id=c.id and vch_license_type='FL2' and d.core_district_id=e.districtid  " +
					     " group by e.description  order by e.description";
			
			
			String wbeer=" select 'BEER' as type,e.description ,sum(c.recv_total_bottels-COALESCE(c.dispatchbotl,0)) as avlbottle from fl2d.fl2_2b_stock_20_21 c, " +
					    " licence.fl2_2b_2d_20_21 d,public.district e where int_app_id=c.id and vch_license_type='FL2B'  and   " +
					    " d.core_district_id=e.districtid  group by e.description  order by e.description "; 
			
			String wcl=" select 'CL' as type,e.description ,sum(c.recv_total_bottels-COALESCE(c.dispatchbotl,0)) as avlbottle  from fl2d.fl2_2b_stock_20_21 c,  " +
					  " licence.fl2_2b_2d_20_21 d ,public.district e where int_app_id=c.id and vch_license_type='CL2'   and " +
					  " d.core_district_id=e.districtid group by e.description order by e.description ";

			String bond1=" select 'WINE/FL' as type,e.description,sum(c.int_recieved_bottles-coalesce(c.dispatch_36,0) ) as avlbottle " +
					     " from bwfl_license.mst_receipt_20_21 c,bwfl.registration_of_bwfl_lic_holder_20_21  d ,public.district e" +
					     " where  c.vch_license_type in ('BWFL2A','BWFL2C')  and d.vch_firm_district_id::int=e.districtid and d.int_id=c.int_bwfl_id" +
					     " group by e.description order by e.description";
			
		    String bond2=" select 'LAB/BEER' as type,e.description, sum(c.int_recieved_bottles-coalesce(c.dispatch_36,0))  as avlbottle " +
		    		     " from bwfl_license.mst_receipt_20_21 c ,bwfl.registration_of_bwfl_lic_holder_20_21  d,public.district e where    c.vch_license_type in ('BWFL2B','BWFL2D')   " +
		    		     " and d.vch_firm_district_id::int=e.districtid and d.int_id=c.int_bwfl_id " +
		    		     " group by e.description  order by e.description";
			c = ConnectionToDataBase.getConnection();
			//BOND1
			if(ac.getType().equalsIgnoreCase("WFL")){
				p = c.prepareStatement(wfl);
			}else if(ac.getType().equalsIgnoreCase("WCL")){
				p = c.prepareStatement(wcl);
			}else if(ac.getType().equalsIgnoreCase("WBEER")){
				p = c.prepareStatement(wbeer);
			
		    }else if(ac.getType().equalsIgnoreCase("BOND1")){
			p = c.prepareStatement(bond1);
		    }else if(ac.getType().equalsIgnoreCase("BOND2")){
			p = c.prepareStatement(bond2);
		    }
			rs = p.executeQuery();

			while (rs.next()) {

				i = i + 1;

				HomePageDatatable dt = new HomePageDatatable();

				dt.setSrno(i);
				 
				dt.setDistrict_name(rs.getString("description"));
				dt.setAvalbottles(rs.getInt("avlbottle"));
				dt.setWtype(rs.getString("type"));
				 
				list.add(dt);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (p != null)
					p.close();
				if (c != null)
					c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	
	public void getotherservices(HomePageAction action) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			rs = null;
			ps = null;

			String query = " select sum(totalapprove) as approve, sum(totalreject) as reject, sum(tapplicant) as pend_app , sum(totalapprove_7day) as totalapprove_7day , " +
					       " sum(fl2d_totalapprove) as fl2d_approve,sum(fl2d_totalreject) as fl2d_rejecte,sum(fl2d_tapplicant) as fl2d_pend_app,sum(fl2d_totalapprove_7day) as fl2d_totalapprove_7day   " +
					      
					       " from  (select count(app_id) as totalapprove,0 as totalreject,0 as tapplicant ,0 as totalapprove_7day ," +
					       " 0 fl2d_totalapprove,0 as fl2d_totalreject,0 as fl2d_tapplicant ,0 as fl2d_totalapprove_7day " +
					       " from bwfl_license.import_permit_20_21 where vch_approved='APPROVED'    and cr_date>='2020-04-01' and  login_type='BWFL'           " +
					      
					       " union                                                                      " +
					      
					       " select 0 as totalapprove,  count(app_id) as totalreject,0 as tapplicant ,0 as totalapprove_7day," +
					       "  0 fl2d_totalapprove,0 as fl2d_totalreject,0 as fl2d_tapplicant ,0 as fl2d_totalapprove_7day  from  " +
					       " bwfl_license.import_permit_20_21  where vch_approved='REJECTED'    and cr_date>='2020-04-01' and login_type='BWFL'          " +
					      
					       " union                                        " +
					       
					       " select 0 as totalapprove,  0 as totalreject,count(app_id) as tapplicant ,0 as totalapprove_7day," +
					       "  0 fl2d_totalapprove,0 as fl2d_totalreject,0 as fl2d_tapplicant ,0 as fl2d_totalapprove_7day  from   " +
					       " bwfl_license.import_permit_20_21              " +
					       " where  vch_approved is null  and cr_date>='2020-04-01' and login_type='BWFL'    " +
					       
					       " union  " +
					      
					       " select 0 as totalapprove,0 as totalreject,0 as tapplicant ,count(app_id) as totalapprove_7day ," +
					       "  0 fl2d_totalapprove,0 as fl2d_totalreject,0 as fl2d_tapplicant ,0 as fl2d_totalapprove_7day  " +
					       " from bwfl_license.import_permit_20_21  where vch_approved='APPROVED' and  login_type='BWFL' " +
					       " and 7>=(deo_date-cr_date) and cr_date>='2020-04-01'    " +
					       
					       " union     " +
					       
					       " select 0 as totalapprove,0 as totalreject,0 as tapplicant ,0 as totalapprove_7day , " +
					       " count(app_id) as fl2d_totalapprove,0 as fl2d_totalreject,0 as fl2d_tapplicant ,0 as fl2d_totalapprove_7day  " +
					       " from bwfl_license.import_permit_20_21                     " +
					       " where vch_approved='APPROVED'    and cr_date>='2020-04-01' and  login_type='FL2D'          " +
					       
					       " union                                                                      " +
					      
					       " select 0 as totalapprove,  0 as totalreject,0 as tapplicant ,0 as totalapprove_7day , " +
					       " 0 as fl2d_totalapprove,count(app_id) as fl2d_totalreject,0 as fl2d_tapplicant ,0 as fl2d_totalapprove_7day  " +
					       "  from  bwfl_license.import_permit_20_21                       " +
					       " where vch_approved='REJECTED'    and cr_date>='2020-04-01' and login_type='FL2D'           " +
					       
					       " union                                                                   " +
					       
					       " select 0 as totalapprove,  0 as totalreject,0 as tapplicant ,0 as totalapprove_7day , " +
					       " 0 as fl2d_totalapprove,0 as fl2d_totalreject,count(app_id) as fl2d_tapplicant ,0 as fl2d_totalapprove_7day  " +
					       " from   bwfl_license.import_permit_20_21               " +
					       " where  vch_approved is null  and cr_date>='2020-04-01' and login_type='FL2D'  " +
					       
					       " union   " +
					       
					       " select 0 as totalapprove,0 as totalreject,0 as tapplicant ,0 as totalapprove_7day  , " +
					       " 0 as fl2d_totalapprove,0 as fl2d_totalreject,0 as fl2d_tapplicant ,count(app_id)  as fl2d_totalapprove_7day    " +
					       " from bwfl_license.import_permit_20_21  where vch_approved='APPROVED' and  login_type='FL2D' " +
					       " and 7>=(deo_date-cr_date) and cr_date>='2020-04-01')x ";
			
			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {
				action.setOtherbwflpen(rs.getInt("pend_app"));
				action.setOtherbwflaprvd(rs.getInt("approve"));
				action.setOtherbwflrej(rs.getInt("reject"));
				action.setOtherbwflaprvdwthin7day(rs.getInt("totalapprove_7day"));
				action.setOtherfl2dpen(rs.getInt("fl2d_pend_app"));
				action.setOtherfl2daprvd(rs.getInt("fl2d_approve"));
				action.setOtherfl2drej(rs.getInt("fl2d_rejecte"));
				action.setOtherfl2daprvdwithin7day(rs.getInt("fl2d_totalapprove_7day"));
				

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
	
	public void getotherservices_ena_imp_exp_imfl(HomePageAction action) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			rs = null;
			ps = null;

			String query = " select sum(totalapprove) as approve, sum(totalreject) as reject, sum(tapplicant) as pend_app , sum(totalapprove_7day) as totalapprove_7day , " +
					       " sum(ena_imp_totalapprove) as ena_imp_totalapprove,sum(ena_imp_totalreject) as ena_imp_totalreject," +
					       " sum(ena_imp_tapplicant) as ena_imp_tapplicant ,sum(ena_imp_totalapprove_7day) as ena_imp_totalapprove_7day ," +
					       " sum(ena_exp_totalapprove) as ena_exp_totalapprove,sum(ena_exp_totalreject) as ena_exp_totalreject," +
					       " sum(ena_exp_tapplicant) as ena_exp_tapplicant ,sum(ena_exp_totalapprove_7day) as ena_exp_totalapprove_7day   " +
					       
					       " from  (select count(app_id) as totalapprove,0 as totalreject,0 as tapplicant ,0 as totalapprove_7day ," +
					       " 0 ena_imp_totalapprove,0 as ena_imp_totalreject,0 as ena_imp_tapplicant ,0 as ena_imp_totalapprove_7day ," +
					       " 0 ena_exp_totalapprove,0 as ena_exp_totalreject,0 as ena_exp_tapplicant ,0 as ena_exp_totalapprove_7day " +
					       " from distillery.eoi_app_for_export_order where approve_flag='A'    and created_date>='2020-04-01'             " +
					       
					       " union                                                                      " +
					      
					       " select 0 as totalapprove,  count(app_id) as totalreject,0 as tapplicant ,0 as totalapprove_7day," +
					       " 0 ena_imp_totalapprove,0 as ena_imp_totalreject,0 as ena_imp_tapplicant ,0 as ena_imp_totalapprove_7day ," +
					       " 0 ena_exp_totalapprove,0 as ena_exp_totalreject,0 as ena_exp_tapplicant ,0 as ena_exp_totalapprove_7day  from  " +
					       " distillery.eoi_app_for_export_order where approve_flag='R'    and created_date>='2020-04-01'          " +
					       
					       " union                                        " +
					      
					       " select 0 as totalapprove,  0 as totalreject,count(app_id) as tapplicant ,0 as totalapprove_7day, " +
					       " 0 ena_imp_totalapprove,0 as ena_imp_totalreject,0 as ena_imp_tapplicant ,0 as ena_imp_totalapprove_7day , " +
					       " 0 ena_exp_totalapprove,0 as ena_exp_totalreject,0 as ena_exp_tapplicant ,0 as ena_exp_totalapprove_7day  from   " +
					       " distillery.eoi_app_for_export_order where (approve_flag is null  or approve_flag not in ('A','R')  ) and created_date>='2020-04-01'     " +
					      
					       " union                                        " +
					      
					       " select 0 as totalapprove,  0 as totalreject,0 as tapplicant ,count(app_id) as totalapprove_7day," +
					       " 0 ena_imp_totalapprove,0 as ena_imp_totalreject,0 as ena_imp_tapplicant ,0 as ena_imp_totalapprove_7day ,   " +
					       " 0 ena_exp_totalapprove,0 as ena_exp_totalreject,0 as ena_exp_tapplicant ,0 as ena_exp_totalapprove_7day  from      " +
					       " distillery.eoi_app_for_export_order where approve_flag='A' and 7>=(user4_dt-created_date)   and created_date>='2020-04-01'     " +
					      
					       " union     " +
					       
					       " select 0 as totalapprove,0 as totalreject,0 as tapplicant ,0 as totalapprove_7day ,    " +
					       " count(req_id) as  ena_imp_totalapprove,0 as ena_imp_totalreject,0 as ena_imp_tapplicant ,0 as ena_imp_totalapprove_7day ,    " +
					       " 0 ena_exp_totalapprove,0 as ena_exp_totalreject,0 as ena_exp_tapplicant ,0 as ena_exp_totalapprove_7day     " +
					       "  from distillery.online_ena_purchase     " +
					       " where letter_dt is not null  and (type NOT IN ('OUP', 'WUP') or type is null) and approve_flag='A'  and date>='2020-04-01'    " +
					      
					       " union       " +
					       
					       " select 0 as totalapprove,0 as totalreject,0 as tapplicant ,0 as totalapprove_7day ,  " +
					       " 0 ena_imp_totalapprove,count(req_id) as ena_imp_totalreject,0 as ena_imp_tapplicant ,0 as ena_imp_totalapprove_7day ,  " +
					       " 0 ena_exp_totalapprove,0 as ena_exp_totalreject,0 as ena_exp_tapplicant ,0 as ena_exp_totalapprove_7day    " +
					       " from distillery.online_ena_purchase    where letter_dt is not null  and (type NOT IN ('OUP', 'WUP') or type is null) and approve_flag='R'  and date>='2020-04-01'            " +
					      
					       " union                                                                      " +
					      
					       " select 0 as totalapprove,  0 as totalreject,0 as tapplicant ,0 as totalapprove_7day , " +
					       " 0 ena_imp_totalapprove,0 as ena_imp_totalreject,count(req_id) as ena_imp_tapplicant ,0 as ena_imp_totalapprove_7day ,  " +
					       " 0 ena_exp_totalapprove,0 as ena_exp_totalreject,0 as ena_exp_tapplicant ,0 as ena_exp_totalapprove_7day   " +
					       " from distillery.online_ena_purchase  where letter_dt is not null  and (type NOT IN ('OUP', 'WUP') or type is null) and " +
					       "    (approve_flag is null or approve_flag not  in('A','R'))    and date>='2020-04-01'    " +
					       
					       "  union   " +
					       
					       " select 0 as totalapprove,0 as totalreject,0 as tapplicant ,0 as totalapprove_7day ,   " +
					       " 0 as  ena_imp_totalapprove,0 as ena_imp_totalreject,0 as ena_imp_tapplicant ,count(req_id) as ena_imp_totalapprove_7day ,  " +
					       " 0 ena_exp_totalapprove,0 as ena_exp_totalreject,0 as ena_exp_tapplicant ,0 as ena_exp_totalapprove_7day    " +
					       " from distillery.online_ena_purchase    where letter_dt is not null  and (type NOT IN ('OUP', 'WUP') or type is null) and approve_flag='A'  " +
					       " 	   and 7>=(user3_dt-date::date)  and date>='2020-04-01'   " +
					      
					       " union                                                                    " +
					      
					       " select 0 as totalapprove,  0 as totalreject,0 as tapplicant ,0 as totalapprove_7day ,  " +
					       " 0 ena_imp_totalapprove,0 as ena_imp_totalreject,0 as ena_imp_tapplicant ,0 as ena_imp_totalapprove_7day ,  " +
					       " count(req_id) as ena_exp_totalapprove,0 as ena_exp_totalreject,0 as ena_exp_tapplicant ,0 as ena_exp_totalapprove_7day    " +
					       " from distillery.online_ena_purchase    where  letter_dt is not null and type='OUP' and approve_flag='A'   and date>='2020-04-01'    " +
					       
					       "  union     " +
					       
					       " select 0 as totalapprove,0 as totalreject,0 as tapplicant ,0 as totalapprove_7day ,  0 ena_imp_totalapprove,0 as ena_imp_totalreject,0 as ena_imp_tapplicant ,0 as ena_imp_totalapprove_7day ,   " +
					       " 0 ena_exp_totalapprove,count(req_id) as ena_exp_totalreject,0 as ena_exp_tapplicant ,0 as ena_exp_totalapprove_7day     " +
					       " from distillery.online_ena_purchase    where  letter_dt is not null and type='OUP' and approve_flag='R'  and date>='2020-04-01'    " +
					       
					       " union   " +
					       
					       " select 0 as totalapprove,0 as totalreject,0 as tapplicant ,0 as totalapprove_7day ,   " +
					       " 0 ena_imp_totalapprove,0 as ena_imp_totalreject,0 as ena_imp_tapplicant ,0 as ena_imp_totalapprove_7day ,   " +
					       " 0 ena_exp_totalapprove,0 as ena_exp_totalreject,count(req_id) as ena_exp_tapplicant ,0 as ena_exp_totalapprove_7day     " +
					       " from distillery.online_ena_purchase  where  letter_dt is not null and type='OUP' and (approve_flag is null or approve_flag not in ('A','R'))  and date>='2020-04-01'    " +
					       
					       " union   " +
					       
					       " select 0 as totalapprove,  0 as totalreject,0 as tapplicant ,0 as totalapprove_7day ,  " +
					       " 0 ena_imp_totalapprove,0 as ena_imp_totalreject,0 as ena_imp_tapplicant ,0 as ena_imp_totalapprove_7day ,   " +
					       " 0 as ena_exp_totalapprove,0 as ena_exp_totalreject,0 as ena_exp_tapplicant ,count(req_id) as ena_exp_totalapprove_7day    " +
					       " from distillery.online_ena_purchase   where  letter_dt is not null and type='OUP' and approve_flag='A'   and 7>=(user3_dt-date::date) and date>='2020-04-01'  	 )x ";
			
			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs.next()) {
				
				action.setOtherimflexppen(rs.getInt("pend_app"));
				action.setOtherimflexpaprvd(rs.getInt("approve"));
				action.setOtherimflexprej(rs.getInt("reject"));
				action.setOtherimflexpaprvdwithin7day(rs.getInt("totalapprove_7day"));
				action.setOtherenaimppen(rs.getInt("ena_imp_tapplicant"));
				action.setOtherenaimpaprvd(rs.getInt("ena_imp_totalapprove"));
				action.setOtherenaimprej(rs.getInt("ena_imp_totalreject"));
				action.setOtherenaimpaprvdwithin7day(rs.getInt("ena_imp_totalapprove_7day"));
				action.setOtherenaexppen(rs.getInt("ena_exp_tapplicant"));
				action.setOtherenaexpaprv(rs.getInt("ena_exp_totalapprove"));
				action.setOtherenaexprej(rs.getInt("ena_exp_totalreject"));
				action.setOtherenaexpaprvwithin7day(rs.getInt("ena_exp_totalapprove_7day"));
				

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
	
}
