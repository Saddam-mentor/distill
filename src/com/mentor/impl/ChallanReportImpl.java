package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.action.ChallanReportAction;
import com.mentor.datatable.ChallanReportDatatable;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class ChallanReportImpl {

	public ArrayList getData() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		int count = 1;
		String sql =

		"	select y.challanno,y.challandate,y.entry_by,y.purpose,y.treasuryid,y.headcode,y.g6code,y.amount ,y.type,                                      "
				+ "	y.name,y.address,y.division                                                                                                                   "
				+ "	from (                                                                                                                                        "
				+ "	select chalan_no as challanno,chalan_date as challandate,unit_type  as entry_by ,                                                                   "
				+ "	purpose as purpose,tres_id as treasuryid,                                                                                                     "
				+ "	head_code as headcode, g6_code as g6code ,amount as amount,'BWFLFL2D' as type,                                                                "
				+ "	unit_id ||'-'||unit_type  as name,'NA THIS ENTRY EITHER IN BWFL OR FL2D REGISTERED UNIT YOU CAN FILTER ON UNIT TYPE IN NAME COLUMN' as address,"
				+ "	division as division from bwfl_license.chalan_deposit_bwfl_fl2d where vch_approve_flag is null                                                "
				+ "	union all                                                                                                                                     "
				+ "	select a.vch_challan_no as challanno ,a.date_challan_date as challandate ,a.vch_user_id as entryby ,                                          "
				+ "	a.vch_purpose as purpose ,a.vch_tresiory_name as treasuryid ,b.vch_head_code as headcode,                                                     "
				+ "	b.vch_g6_head_code as g6code,b.double_amt as amount,  'DEO' as type,a.vch_depositor_name as name,                                             "
				+ "	a.vch_depositor_address as address,a.vch_tresiory_disst as division                                                                           "
				+ "	from revenue.g6_challan_Deposit a,revenue.g6_challn_deposit_Detail b                                                                          "
				+ "	where a.int_challan_id=b.int_challan_id and a.vch_approve_flag is null                                                                        "
				+ "	union all                                                                                                                                     "
				+ "	select a.vch_challan_no as challanno ,a.date_challan_date as challandate,                                                                     "
				+ "	a.vch_user_id || 'Brewary' as entryby ,                                                                                                       "
				+ "	a.vch_purpose as purpose,a.vch_tresiory_name as treasuryid ,b.vch_head_code as headcode,                                                      "
				+ "	b.vch_g6_head_code as g6code,                                                                                                                 "
				+ "	b.double_amt as amount, 'BREWARY'as type,a.vch_depositor_name as name,                                                                        "
				+ "	a.vch_depositor_address as address,a.vch_tresiory_disst as division                                                                           "
				+ "	from bwfl.challan_deposit_brewery a,bwfl.challn_deposit_brewery_detail b                                                                      "
				+ "	where a.int_challan_id=b.int_challan_id and a.vch_approve_flag is null                                                                        "
				+ "	union all                                                                                                                                     "
				+ "	select a.vch_challan_no as challanno ,a.date_challan_date as challandate,case "+
                "  when a.vch_challan_type='H' then COALESCE(a.vch_user_id,'') ||' HBR'  " +
                "  when a.vch_challan_type='S' then COALESCE(a.vch_user_id,'') ||' Sugarmill' " +
                "  when a.vch_challan_type='D' then COALESCE(a.vch_user_id,'') ||'Distillery' " +
                "    end as entryby  ,  "                          
				+ "	a.vch_purpose as purpose ,a.vch_tresiory_name as treasuryid,b.vch_head_code as headcode,b.vch_g6_head_code as g6code,                         "
				+ "	b.double_amt as amount,'DISTILLERY'as type,a.vch_depositor_name as name,a.vch_depositor_address as address,                                   "
				+ "	a.vch_tresiory_disst as division from public.challan_Deposit a,public.challn_deposit_Detail  b                                                "
				+ "	where a.int_challan_id=b.int_challan_id and a.vch_approve_flag is null                                                                        "
				+ "	)y , licence.treasury z ,public.district k where y.treasuryid=z.tcode and z.district_id=k.districtid "+
                " and k.deo=?";                                                                                                                                                 

		try {

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ResourceUtil.getUserNameAllReq());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ChallanReportDatatable crd = new ChallanReportDatatable();

				crd.setSrno(count);
				crd.setAmount(rs.getString("amount"));
				crd.setChallandate(Utility.convertSqlDateToUtilDate(rs
						.getDate("challandate")));
				crd.setChallanNo(rs.getString("challanno"));
				crd.setEntry_by(rs.getString("entry_by"));
				crd.setG6code(rs.getString("g6code"));
				crd.setHeadcode(rs.getString("headcode"));
				crd.setPurpose(rs.getString("purpose"));
				crd.setTreasuryid(rs.getInt("treasuryid"));
				crd.setType(rs.getString("type"));
				crd.setName(rs.getString("name"));
				crd.setAddress(rs.getString("address"));
				crd.setDivision(rs.getInt("division"));
				list.add(crd);
				count++;

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

	public ArrayList getApprovedData() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		int count = 1;
		String sql =

		"	select y.challanno,y.challandate,y.entry_by,y.purpose,y.treasuryid,y.headcode,y.g6code,y.amount ,y.type,                                      "
				+ "	y.name,y.address,y.division                                                                                                                   "
				+ "	from (                                                                                                                                        "
				+ "	select chalan_no as challanno,chalan_date as challandate,COALESCE(unit_type,'NA') as entry_by ,                                                                   "
				+ "	purpose as purpose,tres_id as treasuryid,                                                                                                     "
				+ "	head_code as headcode, g6_code as g6code ,amount as amount,'BWFLFL2D' as type,                                                                "
				+ "	unit_id ||'-'||unit_type  as name,'NA THIS ENTRY EITHER IN BWFL OR FL2D REGISTRED UNIT YOU CAN FILTER ON UNIT TYPE IN NAME COLUMN' as address,"
				+ "	division as division from bwfl_license.chalan_deposit_bwfl_fl2d where vch_approve_flag ='A'                                                "
				+ "	union all                                                                                                                                     "
				+ "	select a.vch_challan_no as challanno ,a.date_challan_date as challandate ,COALESCE(a.vch_user_id,'NA')  as entryby ,                                          "
				+ "	a.vch_purpose as purpose ,a.vch_tresiory_name as treasuryid ,b.vch_head_code as headcode,                                                     "
				+ "	b.vch_g6_head_code as g6code,b.double_amt as amount,  'DEO' as type,a.vch_depositor_name as name,                                             "
				+ "	a.vch_depositor_address as address,a.vch_tresiory_disst as division                                                                           "
				+ "	from revenue.g6_challan_Deposit a,revenue.g6_challn_deposit_Detail b                                                                          "
				+ "	where a.int_challan_id=b.int_challan_id and a.vch_approve_flag ='A'                                                                       "
				+ "	union all                                                                                                                                     "
				+ "	select a.vch_challan_no as challanno ,a.date_challan_date as challandate,                                                                     "
				+ "	COALESCE(a.vch_user_id,'NA') || 'Brewary' as entryby ,                                                                                                       "
				+ "	a.vch_purpose as purpose,a.vch_tresiory_name as treasuryid ,b.vch_head_code as headcode,                                                      "
				+ "	b.vch_g6_head_code as g6code,                                                                                                                 "
				+ "	b.double_amt as amount, 'BREWARY'as type,a.vch_depositor_name as name,                                                                        "
				+ "	a.vch_depositor_address as address,a.vch_tresiory_disst as division                                                                           "
				+ "	from bwfl.challan_deposit_brewery a,bwfl.challn_deposit_brewery_detail b                                                                      "
				+ "	where a.int_challan_id=b.int_challan_id and a.vch_approve_flag ='A'                                                                      "
				+ "	union all                                                                                                                                     "
				+ "	select a.vch_challan_no as challanno ,a.date_challan_date" +
				" as challandate,case "+
                "  when a.vch_challan_type='H' then COALESCE(a.vch_user_id,'') ||' HBR'  " +
                "  when a.vch_challan_type='S' then COALESCE(a.vch_user_id,'') ||' Sugarmill' " +
                "  when a.vch_challan_type='D' then COALESCE(a.vch_user_id,'') ||'Distillery' " +
                "    end as entryby  ,  "                       
				+ "	a.vch_purpose as purpose ,a.vch_tresiory_name as treasuryid,b.vch_head_code as headcode,b.vch_g6_head_code as g6code,                         "
				+ "	b.double_amt as amount,'DISTILLERY'as type,a.vch_depositor_name as name,a.vch_depositor_address as address,                                   "
				+ "	a.vch_tresiory_disst as division from public.challan_Deposit a,public.challn_deposit_Detail  b                                                "
				+ "	where a.int_challan_id=b.int_challan_id and a.vch_approve_flag ='A'                                                                       "
				+ "	)y , licence.treasury z ,public.district k where y.treasuryid=z.tcode and z.district_id=k.districtid "+
                      " and k.deo=?";                                                                                                                                         
                      				try {

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ResourceUtil.getUserNameAllReq());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ChallanReportDatatable crd = new ChallanReportDatatable();

				crd.setSrno(count);
				crd.setAmount(rs.getString("amount"));
				crd.setChallandate(Utility.convertSqlDateToUtilDate(rs
						.getDate("challandate")));
				crd.setChallanNo(rs.getString("challanno"));
				crd.setEntry_by(rs.getString("entry_by"));
				crd.setG6code(rs.getString("g6code"));
				crd.setHeadcode(rs.getString("headcode"));
				crd.setPurpose(rs.getString("purpose"));
				crd.setTreasuryid(rs.getInt("treasuryid"));
				crd.setType(rs.getString("type"));
				crd.setName(rs.getString("name"));
				crd.setAddress(rs.getString("address"));
				crd.setDivision(rs.getInt("division"));
				list.add(crd);
				count++;

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

	public void approveChallan(ChallanReportAction action,
			ChallanReportDatatable dt) {
		Connection conn = null;
		PreparedStatement pstmt = null;
        int status=0;
		String sql=
	"	INSERT INTO bwfl_license.challan_approval(                          "+
	"	dat_challandate, vch_entry_by, vch_purpose, int_treasuryid,         "+
	"	vch_headcode, vch_g6code, vch_amount, vch_type, int_division_code,  "+
	"	created_date, created_by, vch_address, vch_challan_no)              "+
	"	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);                     ";

		
		String bwfl_fl2d = "update 	bwfl_license.chalan_deposit_bwfl_fl2d set vch_approve_flag ='A' where chalan_no=?";
		String deo = "update revenue.g6_challan_Deposit set 	vch_approve_flag ='A' where vch_challan_no=?";

		String brewary = "update bwfl.challan_deposit_brewery set 	vch_approve_flag ='A' where vch_challan_no=?";

		String distillery = "update public.challan_Deposit set 	vch_approve_flag ='A' where vch_challan_no=?";	
		
		String sql_update="";
		
		try {
			
			if(dt.getType().equalsIgnoreCase("BWFLFL2D"))
			{
				sql_update=	bwfl_fl2d;
			}else if(dt.getType().equalsIgnoreCase("DEO"))
			{
				sql_update=	deo;
			}
			else if(dt.getType().equalsIgnoreCase("BREWARY"))
			{
				sql_update=	brewary;
			}
			else if(dt.getType().equalsIgnoreCase("DISTILLERY"))
			{
				sql_update=	distillery;
			}
			
			
			
			
            conn=ConnectionToDataBase.getConnection();
            conn.setAutoCommit(false);
            pstmt=conn.prepareStatement(sql);
            pstmt.setDate(1,Utility.convertUtilDateToSQLDate(dt.getChallandate()));
            pstmt.setString(2,dt.getEntry_by());
            pstmt.setString(3, dt.getPurpose());
            pstmt.setInt(4, dt.getTreasuryid());
            pstmt.setString(5, dt.getHeadcode());
            pstmt.setString(6, dt.getG6code());
            pstmt.setString(7, dt.getAmount());
            pstmt.setString(8, dt.getType());
            pstmt.setInt(9, dt.getDivision());
            
            pstmt.setDate(10, Utility.convertUtilDateToSQLDate(new Date()));
            pstmt.setString(11, ResourceUtil.getUserNameAllReq());
            pstmt.setString(12, dt.getAddress());
            pstmt.setString(13, dt.getChallanNo());
		status=	pstmt.executeUpdate();
		if(status>0)
		{
			status=0;
		pstmt=	conn.prepareStatement(sql_update);
		pstmt.setString(1, dt.getChallanNo());
		status=pstmt.executeUpdate();
		
		if(status>0)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Approved SuccessFully","Approved SuccessFully"));
			conn.commit();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Not Approved","Not Approved "));
			conn.rollback();
		}
		}

		

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
