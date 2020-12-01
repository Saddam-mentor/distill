package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.mentor.action.CircularEntryAction;
import com.mentor.datatable.CircularEntryDT;
import com.mentor.resource.ConnectionToDataBase;

import com.mentor.utility.Utility;

public class CircularEntryImpl {

	
	
	public ArrayList getCircularDetail(){

		ArrayList list = new ArrayList();
		Connection con = null;
		 PreparedStatement ps = null;
		 ResultSet rs = null;
		 String query="";
		 int i=0;
		 
		 try
		 {	
			 
			
				 
			 query = "SELECT ndate, sn, newstext, type, links, description, category_id, " +
			 		" (select type from public.mst_category where category_id=id) as categorytype "+
	                 " FROM public.news  where sn not in ('0') order by sn";
		     con = ConnectionToDataBase.getConnection();
			 ps = con.prepareStatement(query);
			 
		     rs = ps.executeQuery();
		     while(rs.next())
		     {
		    	 CircularEntryDT dt= new CircularEntryDT();
		 
		       dt.setSrNo_int(rs.getInt("sn"));
		       dt.setDate(rs.getDate("ndate"));
		       dt.setHeading_str_dt(rs.getString("newstext"));
		       dt.setDiscription_str_dt(rs.getString("description"));
		       dt.setCategory_id(rs.getString("category_id"));
		       dt.setCategory_type(rs.getString("categorytype"));
		       
		       if(rs.getString("links")!=null && rs.getString("links").length()>0)
		       {
		       dt.setPdf_str_dt(rs.getString("links"));
		       }else{
		    	   dt.setPdf_str_dt("doc/ExciseUp/PdfHome/NA");  
		       }
		       list.add(dt);
				
		     }
		    
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
			{
			try
			{	
				
			if(con!=null)con.close();
			if(ps!=null)ps.close();
			if(rs!=null)rs.close();
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		 return list;
	 
	}
	
	public void saveImpl(CircularEntryAction act){

		
		Connection con = null;
		 PreparedStatement ps = null;
		 String query="";
		 int saveStatus=0;
		  
		 
		 try
		 {	
			 
			///ON CONFLICT ON CONSTRAINT customers_name_key 
				 
			 query = "INSERT INTO  public.news(ndate, sn, newstext, type, links, description, category_id ) VALUES (?, ?, ?, ?, ?, ?,?) " +
			 		" ON CONFLICT ON CONSTRAINT news_sn  DO UPDATE SET ndate=?, newstext=?, links=?, description=? ,category_id=? " +
			 		" ";
	                 
		     con = ConnectionToDataBase.getConnection();
			 ps = con.prepareStatement(query);
			 ps.setDate(1, Utility.convertUtilDateToSQLDate(act.getCir_date()));
			 ps.setInt(2, act.getSrNo());
			 ps.setString(3, act.getHeading_str());
			 ps.setString(4, "Y");
			 ps.setString(5, "/doc/ExciseUp/pdf/Circular_"+act.getSrNo()+".pdf");
			 ps.setString(6, act.getDiscription_str());
			 ps.setDate(8,  Utility.convertUtilDateToSQLDate(act.getCir_date()));
			 ps.setString(9, act.getHeading_str());
			 ps.setString(10, "/doc/ExciseUp/pdf/Circular_"+act.getSrNo()+".pdf");
			 ps.setString(11, act.getDiscription_str());
			 ps.setInt(7, Integer.parseInt(act.getCategory_id()));
			 ps.setInt(12, Integer.parseInt(act.getCategory_id()));
			 saveStatus=ps.executeUpdate();
			 System.out.println("====saveStatus===="+saveStatus);
			 if(saveStatus>0){
				 FacesContext.getCurrentInstance().addMessage(null,
 						new FacesMessage("Successfully Saved", 
 											"Successfully Saved"));
			 }else{
				 con.rollback();
				 FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Not Saved !",
									"Not Saved !"));
			 }
		     
		    
		    
		 }
		 catch(Exception e)
		 {
			  
			 e.printStackTrace();
		 }
		 finally
			{
			try
			{	
				
			if(con!=null)con.close();
			if(ps!=null)ps.close();
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	
	 
	}
	
	
	public int maxid(CircularEntryAction act){

		Connection con = null;
		 PreparedStatement ps = null;
		 ResultSet rs = null;
		 String query="";
		 int i=0;
		 
		 try
		 {	
			 
			
				 
			 query = "SELECT  max(sn) FROM public.news";
		     con = ConnectionToDataBase.getConnection();
			 ps = con.prepareStatement(query);
			System.out.println("==== "+query);
		     rs = ps.executeQuery();
		     if(rs.next())
		     {
		      i=rs.getInt(1)+1;
				
		     }
		    
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
			{
			try
			{	
				
			if(con!=null)con.close();
			if(ps!=null)ps.close();
			if(rs!=null)rs.close();
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		 return i;
	}
	
	
	public void deleteMethod(int srNo){

		
		Connection con = null;
		 PreparedStatement ps = null;
		 String query="";
		 int saveStatus=0;
		 
		 try
		 {	
			 query = "DELETE FROM public.news WHERE sn="+srNo+" ";
	                 
		     con = ConnectionToDataBase.getConnection();
		     ps = con.prepareStatement(query);
			 saveStatus = ps.executeUpdate();
			 
			 if(saveStatus>0){
				 FacesContext.getCurrentInstance().addMessage(null,
 						new FacesMessage("Successfully Deleted", 
 											"Successfully Deleted"));
			 }else{
				 FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Not Deleted !",
									"Not Deleted !"));
			 }
		     
		    
		    
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
			{
			try
			{	
			if(con!=null)con.close();
			if(ps!=null)ps.close();
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	
	 
	}
	
	public void update(CircularEntryAction act){

		 act.setModifyFlag(false);
		 Connection con = null;
		 PreparedStatement ps = null;
		 String query="";
		 int saveStatus=0;
		 System.out.println("sr no=="+act.getSrNo());
		 try
		 {	
			 query = "UPDATE public.news SET ndate=?, sn=?, newstext=?, type=?, links=?, description=? WHERE sn="+ act.getSrNo()+" ";
	                 
		     con = ConnectionToDataBase.getConnection();
		     ps = con.prepareStatement(query);
		     ps.setDate(1, Utility.convertUtilDateToSQLDate(act.getCir_date()));
		     ps.setInt(2, act.getSrNo());
		     ps.setString(3, act.getHeading_str());
		     ps.setString(4, "Y");
		     ps.setString(5,  "/doc/ExciseUp/Distillery/NewsPdf/Circular_"+act.getSrNo());
		     ps.setString(6, act.getDiscription_str());
			 saveStatus = ps.executeUpdate();
			 
			 if(saveStatus>0){
				 System.out.println("Updatee===");
			 }else{
				 System.out.println("Not Updatee===");
			 }
		     
		    
		    
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
			{
			try
			{	
			if(con!=null)con.close();
			if(ps!=null)ps.close();
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	
	 
	}
	
	
	
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
	
	
}
