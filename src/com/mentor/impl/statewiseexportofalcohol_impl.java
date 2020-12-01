package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import com.mentor.action.statewiseexportofalcohol_action;
import com.mentor.datatable.statewiseexportofalcohol_dt;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;


public class statewiseexportofalcohol_impl {
	
public String getUserDetails(statewiseexportofalcohol_action act) {
        
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		
		try {
			con = ConnectionToDataBase.getConnection();

			String selQr = 	" SELECT int_app_id_f, vch_undertaking_name, vch_wrk_add  " +
							" FROM public.dis_mst_pd1_pd2_lic  " +
							" WHERE vch_wrk_phon='"+ ResourceUtil.getUserNameAllReq().trim() + "' ";

			pstmt = con.prepareStatement(selQr);

			//System.out.println("login details---------------" + selQr);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				act.setLoginUserId(rs.getInt("int_app_id_f"));
				act.setLoginUserNm(rs.getString("vch_undertaking_name"));
				act.setLoginUserAdrs(rs.getString("vch_wrk_add"));

			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (ps2 != null)
					ps2.close();
				if (rs != null)
					rs.close();
				if (rs2 != null)
					rs2.close();
				if (con != null)
					con.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return "";

	}	
    
    public ArrayList yearListImpl(statewiseexportofalcohol_action act) {
	ArrayList list = new ArrayList();
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	SelectItem item = new SelectItem();
	item.setLabel("--select--");
	item.setValue("");
	list.add(item);
	conn = ConnectionToDataBase.getConnection();

	try {
	
	
String query = " SELECT year, value FROM public.reporting_year;";

			ps = conn.prepareStatement(query);
		
		   rs = ps.executeQuery();
      
		while (rs.next()) {

			item = new SelectItem();

			item.setValue(rs.getString("value"));
			item.setLabel(rs.getString("year"));
			
			//act.setYearr(rs.getString("value"));
			
			list.add(item);
			
			//System.out.println("== get year List== "+query);

		}

	} catch (Exception e) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
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

//===================get Month
    
    public ArrayList getMonthList(statewiseexportofalcohol_action act)
	{

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue("");
		list.add(item);
		try {
			String query = " SELECT month_id, description FROM public.month_master ORDER BY month_id ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			
			//System.out.println("------------------get Month List-------------"+query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("month_id"));
				item.setLabel(rs.getString("description"));
				
				//act.setMontth(rs.getInt("month_id"));

				list.add(item);

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
    
 //==================
    
        public ArrayList showdata(statewiseexportofalcohol_action act) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		String query = "";
		int j = 1;
		try {
			     
			    query =  " select x.int_state_id , x.vch_state_name , y.alloted_bl ,y.alloted_al,y.actual_lift_al,y.actual_lift_bl from                               "+
                         " (select vch_state_name,int_state_id from public.state_ind where int_state_id not in (18,33,1)order by vch_state_name)x    "+
                         " left outer join (SELECT distillery_id ,year_id, month_id,state_id, alloted_bl, alloted_al, actual_lift_al, actual_lift_bl "+
                         " FROM distillery.statewise_export_of_alcohol where distillery_id='"+act.getLoginUserId()+"' and year_id='"+act.getYearr()+"' " +
                         " and month_id='"+ Integer.parseInt(act.getMontth())+"')y  on x.int_state_id = y.state_id ";                                                                                          
				
			    //System.out.println("===========To Display Data==============="+query);
			    
				conn = ConnectionToDataBase.getConnection();
				pstmt = conn.prepareStatement(query);
			
				
			    
				rs = pstmt.executeQuery();				
				while (rs.next()) 
				{
					statewiseexportofalcohol_dt dt = new statewiseexportofalcohol_dt();
					
					dt.setState_name(rs.getString("vch_state_name"));
					dt.setAllot_al(rs.getInt("alloted_al"));
					dt.setAllot_bl(rs.getInt("alloted_bl"));
					dt.setActual_al(rs.getInt("actual_lift_al"));
					dt.setActual_bl(rs.getInt("actual_lift_bl"));
					dt.setState_id(rs.getInt("int_state_id"));
					dt.setSr_no(j);
					j++ ;
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
    
 //=========================
    
    public ArrayList save(statewiseexportofalcohol_action act)
    {
    int saveStatus = 0;
 	Connection con = null;
 	PreparedStatement pstmt = null, pstmt1=null,  pstmt2=null;			
 	String insQr = "";
 	ArrayList list=new ArrayList();
 	ResultSet rs = null ;
 	int id = this.maxId();
 	String filter = "";
     
 	try {
 		
    	  
         String query1=  " SELECT * FROM distillery.statewise_export_of_alcohol where distillery_id='"+act.getLoginUserId()+"' " +
    	  		         " and year_id='"+act.getYearr()+"' and month_id='"+ Integer.parseInt(act.getMontth())+"' ";
    	  
     	 con = ConnectionToDataBase.getConnection();
     	 
     	 con.setAutoCommit(false);
     	 
     	 pstmt = con.prepareStatement(query1);
     	 
     	//System.err.println("===========Check Data==============="+query1);
     	
     	rs = pstmt.executeQuery();
     	
     	if (rs.next())
     		
     	{ 
     		
     		 	
     		  
     		 for (int i = 0; i < act.getDisplaylist().size(); i++) 
    	     {
    	    
     	    statewiseexportofalcohol_dt dt = (statewiseexportofalcohol_dt) act.getDisplaylist().get(i);
     	   
            String update=" UPDATE distillery.statewise_export_of_alcohol SET save_date=?, alloted_bl=?, " +
		                  " alloted_al=?, actual_lift_al=?, actual_lift_bl=? " +
		                  " WHERE distillery_id='"+act.getLoginUserId()+"' and year_id='"+act.getYearr()+"' " +
                          " and month_id='"+ Integer.parseInt(act.getMontth())+"' and state_id ='"+dt.getState_id()+"' ";

                 pstmt1 = con.prepareStatement(update);
                
                 pstmt1.setDate(1, Utility.convertUtilDateToSQLDate(new Date()));
                 pstmt1.setDouble(2, dt.getAllot_bl());
                 pstmt1.setDouble(3, dt.getAllot_al());
                 pstmt1.setDouble(4, dt.getActual_al());
                 pstmt1.setDouble(5, dt.getActual_bl());
         	  
                saveStatus = pstmt1.executeUpdate();

         // System.err.println("===========update Query==============="+update);

                  filter = "Record Updated Successfully" ;
                      
     	}	
     	}
     	else 
     	{
        
     		 
 		insQr =  " INSERT INTO distillery.statewise_export_of_alcohol(int_id, distillery_id, month_id, state_id, save_date, alloted_bl, alloted_al, " +
 				 " actual_lift_al, actual_lift_bl, year_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";          
 		
 		
 		
 		 for (int i = 0; i < act.getDisplaylist().size(); i++) 
	     {
	    
 	    statewiseexportofalcohol_dt dt = (statewiseexportofalcohol_dt) act.getDisplaylist().get(i);
 	    
 	    
 	    
	    pstmt2 = con.prepareStatement(insQr);
	    
	    pstmt2.setInt(1,id);
	    pstmt2.setInt(2, act.getLoginUserId());
	    pstmt2.setInt(3, Integer.parseInt(act.getMontth()));
	    pstmt2.setInt(4, dt.getState_id());
	    pstmt2.setDate(5, Utility.convertUtilDateToSQLDate(new Date()));
	    pstmt2.setDouble(6, dt.getAllot_bl());
	    pstmt2.setDouble(7, dt.getAllot_al());
	    pstmt2.setDouble(8, dt.getActual_al());
	    pstmt2.setDouble(9, dt.getActual_bl());
	    pstmt2.setString(10, act.getYearr());
 		
	    //System.out.println("===========Insert Query======"+insQr);
 		
	    saveStatus = pstmt2.executeUpdate();
 		
 		id++ ;
 		
 		filter = "Record Saved Successfully" ;
	    
	  }
     	}
 		if (saveStatus > 0) {
 			
 			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(filter,filter));
			con.commit();
             
			act.reset();

 		} else {
 			con.rollback();
 			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
 			"Error !!! ", "Error!!!"));

 			con.rollback();

 		}
 	} catch (Exception se) {
 		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(se.getMessage(), se.getMessage()));
 		se.printStackTrace();

 	} finally {
 		try {
 			if (pstmt != null)
 				pstmt.close();
 			if (pstmt1 != null)
 				pstmt1.close();
 			if (pstmt2 != null)
 				pstmt2.close();
 			if (con != null)
 				con.close();

 		} catch (Exception se) {					
 			se.printStackTrace();
 		}
 	}
      
      return list;   
 	
 }
 
//==========================
    
    public int maxId() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = " SELECT max(int_id) as id FROM distillery.statewise_export_of_alcohol ";
		int maxid = 0;
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				maxid = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return maxid + 1;

	}
    

}
