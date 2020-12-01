package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.mentor.action.Hbr_19_20_list_action;
import com.mentor.datatable.Hbr_19_20_list_dt;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class Hbr_19_20_list_impl {

            public ArrayList showdata(Hbr_19_20_list_action action) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList list = new ArrayList();
			String query = "";
			int j = 1;
			try {
				     
				    query = 	" SELECT id,mobile_number,name_of_hbr FROM hotel_bar_rest.registration_for_hotels_bars_restraunt order by id ";
					
					conn = ConnectionToDataBase.getConnection();
					pstmt = conn.prepareStatement(query);
					rs = pstmt.executeQuery();				
					while (rs.next()) 
					{
						
						Hbr_19_20_list_dt dt = new Hbr_19_20_list_dt();
						
						dt.setHbr_id(rs.getInt("id"));
						dt.setHbr_nm(rs.getString("name_of_hbr"));
						dt.setMobile_num(rs.getString("mobile_number"));
					
						list.add(dt);
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null)conn.close();
					if (pstmt != null)pstmt.close();
					if (rs != null)rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		}


}
