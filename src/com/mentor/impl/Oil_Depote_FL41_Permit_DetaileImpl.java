package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.action.Oil_Depote_FL41_Permit_DetaileAction;
import com.mentor.resource.ConnectionToDataBase;

public class Oil_Depote_FL41_Permit_DetaileImpl {
	
	public boolean search(Oil_Depote_FL41_Permit_DetaileAction act){
		boolean a=false;
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String query=null;
		try{
			query="select indent_no,a.approval_date,CONCAT(company_name,' - ',depo_name) as oil_dep_name," +
					" description as dis_name,vch_undertaking_name,approved_qty,lifted_qty,mollasses_type" +
					" from fl41.fl41_indent_detail_approved a,fl41.fl41_registration_approval b," +
					" public.district c,public.dis_mst_pd1_pd2_lic d" +
					" where b.depo_dist_id=c.districtid and a.fl41_id=b.int_id and " +
					" int_app_id_f=dist_id and indent_no='"+act.getOrderNo()+"' and a.approval_date='"+act.getOrderDate()+"' ";
			
			con = ConnectionToDataBase.getConnection();
			pstmt=con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs.next()){
				act.setDistrict(rs.getString("dis_name"));
				act.setDitillery_name(rs.getString("vch_undertaking_name"));
				act.setEna_type(rs.getString("mollasses_type"));
				act.setLifted_qty(rs.getDouble("lifted_qty"));
				act.setOil_dep_name(rs.getString("oil_dep_name"));
				act.setApprove_qty(rs.getDouble("approved_qty"));
				a=true;
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No Data Found!!", "No Data Found!!"));
				a=false;
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			try{
				if(con!=null)con.close();
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		return a;
	}

}
