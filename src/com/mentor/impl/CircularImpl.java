package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.mentor.action.CircularAction;
import com.mentor.datatable.CircularDatatable;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Utility;

public class CircularImpl {

	public ArrayList getcategorylist() {
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SelectItem item = new SelectItem();
		item.setLabel("--Select--");
		item.setValue("0");
		list.add(item);
		String SQl = "SELECT id, type FROM public.mst_category ";
		try {

			System.out.println("========list=========" + SQl);
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(SQl);
			rs = ps.executeQuery();
			while (rs.next()) {
				item = new SelectItem();
				item.setLabel(rs.getString("type"));
				item.setValue(rs.getString("id"));
				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (con != null)
					con.close();
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

	public ArrayList homenews(CircularAction action) {

		ArrayList list = new ArrayList();

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		try {
			if (action.getRadio().equalsIgnoreCase("A")) {

				sql = " select sn,newstext,description,ndate,links  from news where " +
					///  " type='Y' and " +
					" category_id is not null and " +
					  " ndate between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  and " +
					  " '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  order by ndate";

			} else if (action.getRadio().equalsIgnoreCase("C"))  {
				sql = " select sn,newstext,description,ndate,links "+
					  " from news where category_id='"+action.getCategory_id()+"' and ndate between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  and " +
					  " '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "' order by ndate";
			}
System.out.println("========datatable===="+sql);
			c = ConnectionToDataBase.getConnection();
			ps = c.prepareStatement(sql);

			rs = ps.executeQuery();
			int count = 0;
			while (rs.next()) {

				CircularDatatable fl = new CircularDatatable();
				count = count + 1;
				fl.setNewsserial(count);
				fl.setNewsfile(rs.getString("links"));
				fl.setNewsdesc(rs.getString("description"));
				fl.setNewshead(rs.getString("newstext"));
				fl.setNewsdt(Utility.convertSqlDateToUtilDate(rs
						.getDate("ndate")));

				list.add(fl);

			}
			if(count>0){
				
			}else{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found !!!!!", "No Data Found !!!!!"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

		return list;
	}

}
