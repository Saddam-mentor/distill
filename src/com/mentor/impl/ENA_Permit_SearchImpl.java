package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.action.ENA_Permit_SearchAction;
import com.mentor.resource.ConnectionToDataBase;


public class ENA_Permit_SearchImpl {
	
	public boolean search(ENA_Permit_SearchAction act){
		boolean a=false;
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String query=null;
		try{
			query="SELECT  permit_no,user3_dt,(select vch_undertaking_name from public.dis_mst_pd1_pd2_lic where login_dis_id=int_app_id_f)as pur_dis," +
					" (select vch_undertaking_name from public.dis_mst_pd1_pd2_lic where from_dis_id=int_app_id_f)as seller_dis," +
					" ena,enatype,purpose,date,user3_qty,used_ena,digital_sign_pdf" +
					" FROM distillery.online_ena_purchase a " +
					" where permit_no='"+act.getOrderNo()+"' and user3_dt='"+act.getOrderDate()+"'";
			
			con = ConnectionToDataBase.getConnection();
			pstmt=con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs.next()){
				act.setPur_dist(rs.getString("pur_dis"));
				act.setSeller_dist(rs.getString("seller_dis"));
				act.setEna_type(rs.getString("enatype"));
				act.setPur_ena(rs.getDouble("ena"));
				act.setRequest_Date(rs.getDate("date"));
				act.setApproved_ena(rs.getDouble("user3_qty"));
				act.setLifted_ena( rs.getDouble("used_ena"));
				act.setPdf(rs.getString("digital_sign_pdf"));
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
