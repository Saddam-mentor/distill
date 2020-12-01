package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import com.mentor.action.productionandconsumptionofalcohol_action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class productionandconsumptionofalcohol_impl {
	
//==============To get Login
	
	public String getUserDetails(productionandconsumptionofalcohol_action act) {
        
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
	
//==============================get data 
	
	public String getdata(productionandconsumptionofalcohol_action act) {
        
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		
		try {
			con = ConnectionToDataBase.getConnection();

			String selQr = 	" SELECT produ_bl, produ_al, importoutstate_bl, importoutstate_al, importoutindia_bl, importoutindia_al, consum_bl, " +
					        " consum_al, saleinupdrink_bl, saleinupdrink_al, saleinupother_bl, saleinupother_al, saleoutstatedrink_bl, saleoutstatedrink_al, " +
					        " saleoutstateother_bl, saleoutstateother_al, saleoutcountrydrink_bl, saleoutcountrydrink_al, saleoutcountryother_bl, saleoutcountryother_al, " +
					        " wastage_bl, wastage_al FROM distillery.productionandconsumptionofalcohol where distillery_id='"+act.getLoginUserId()+"' and "+
					        " month_id='"+ Integer.parseInt(act.getMontth())+"' and year_id='"+act.getYearr()+"' and spirit_type= '"+act.getSpriit_type()+"'";

			pstmt = con.prepareStatement(selQr);

			//System.out.println("======get data------Display---------" + selQr);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				
             act.setProduction_bl(rs.getInt("produ_bl"));
             act.setProduction_al(rs.getInt("produ_al"));
             act.setImport_outofstate_bl(rs.getInt("importoutstate_bl"));
             act.setImport_outofstate_al(rs.getInt("importoutstate_al"));
             act.setImport_outofindia_bl(rs.getInt("importoutindia_bl"));
             act.setImport_outofindia_al(rs.getInt("importoutindia_al"));
             act.setConsumption_bl(rs.getInt("consum_bl"));
             
             act.setConsumption_al(rs.getInt("consum_al"));
             act.setSaleinup_drink_bl(rs.getInt("saleinupdrink_bl"));
             act.setSaleinup_drink_al(rs.getInt("saleinupdrink_al"));
             act.setSaleinup_other_bl(rs.getInt("saleinupother_bl"));
             act.setSaleinup_other_al(rs.getInt("saleinupother_al"));
             act.setSaleoutstate_drink_bl(rs.getInt("saleoutstatedrink_bl"));
             act.setSaleoutstate_drink_al(rs.getInt("saleoutstatedrink_al"));
             
             act.setSaleoutstate_other_bl(rs.getInt("saleoutstateother_bl"));
             act.setSaleoutstate_other_al(rs.getInt("saleoutstateother_al"));
             act.setSaleoutcountry_drink_bl(rs.getInt("saleoutcountrydrink_bl"));
             act.setSaleoutcountry_drink_al(rs.getInt("saleoutcountrydrink_al"));
             act.setSaleoutcountry_other_bl(rs.getInt("saleoutcountryother_bl"));
             act.setSaleoutcountry_other_al(rs.getInt("saleoutcountryother_al"));
             act.setWastage_bl(rs.getInt("wastage_bl"));
             act.setWastage_al(rs.getInt("wastage_al"));

			}
			else
			{
				
				act.clear();
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
	
//===============================year list
	
	    public ArrayList yearListImpl(productionandconsumptionofalcohol_action act) {
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
	    
	    public ArrayList getMonthList(productionandconsumptionofalcohol_action act)
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
	    
//==================To Save Data 
	    
   public void save(productionandconsumptionofalcohol_action act)
   {
    int saveStatus = 0;
	Connection con = null;
	PreparedStatement pstmt = null;			
	String insQr = "";
	ResultSet rs = null ;
	String filter = "";
	int id = this.maxId();
     try {	
	        String query1 = " select * from distillery.productionandconsumptionofalcohol where distillery_id='"+act.getLoginUserId()+"' and " +
	        		        " month_id='"+ Integer.parseInt(act.getMontth())+"' and year_id='"+act.getYearr()+"' and spirit_type= '"+act.getSpriit_type()+"' ";
   
    con = ConnectionToDataBase.getConnection();
	
    con.setAutoCommit(false);
    
    pstmt = con.prepareStatement(query1);
    
    //System.err.println("===========Check Data==============="+query1);
    
    rs = pstmt.executeQuery();
    
    if (rs.next())
    	
    {
    	
    pstmt = null ;	
	
    String update = " UPDATE distillery.productionandconsumptionofalcohol SET produ_bl=?, produ_al=?, "+
                    " importoutstate_bl=?, importoutstate_al=?, importoutindia_bl=?, importoutindia_al=?, consum_bl=?, consum_al=?, "+
                    " saleinupdrink_bl=?, saleinupdrink_al=?, saleinupother_bl=?, saleinupother_al=?, saleoutstatedrink_bl=?, "+
                    " saleoutstatedrink_al=?, saleoutstateother_bl=?, saleoutstateother_al=?, saleoutcountrydrink_bl=?, saleoutcountrydrink_al=?, "+
                    " saleoutcountryother_bl=?, saleoutcountryother_al=?, wastage_bl=?, wastage_al=?, save_date=?,int_id=? "+
                    " WHERE distillery_id='"+act.getLoginUserId()+"' and month_id='"+ Integer.parseInt(act.getMontth())+"' and year_id='"+act.getYearr()+"' "+
					" and spirit_type= '"+act.getSpriit_type()+"' "; 
    
    pstmt = con.prepareStatement(update);
	
    pstmt.setDouble(1, act.getProduction_bl());
    pstmt.setDouble(2, act.getProduction_al());
    pstmt.setDouble(3, act.getImport_outofstate_bl());
    pstmt.setDouble(4, act.getImport_outofstate_al());

    pstmt.setDouble(5, act.getImport_outofindia_bl());
    pstmt.setDouble(6, act.getImport_outofindia_al());
    pstmt.setDouble(7, act.getConsumption_bl());
    pstmt.setDouble(8, act.getConsumption_al());
    pstmt.setDouble(9, act.getSaleinup_drink_bl());
    pstmt.setDouble(10, act.getSaleinup_drink_al());

    pstmt.setDouble(11, act.getSaleinup_other_bl());
    pstmt.setDouble(12, act.getSaleinup_other_al());
    pstmt.setDouble(13, act.getSaleoutstate_drink_bl());
    pstmt.setDouble(14, act.getSaleoutstate_drink_al());
    pstmt.setDouble(15, act.getSaleoutstate_other_bl());

    pstmt.setDouble(16, act.getSaleoutstate_other_al());
    pstmt.setDouble(17, act.getSaleoutcountry_drink_bl());
    pstmt.setDouble(18, act.getSaleoutcountry_drink_al());
    pstmt.setDouble(19, act.getSaleoutcountry_other_bl());

    pstmt.setDouble(20, act.getSaleoutcountry_other_al());
    pstmt.setDouble(21, act.getWastage_bl());
    pstmt.setDouble(22, act.getWastage_al());
    pstmt.setDate(23, Utility.convertUtilDateToSQLDate(new Date()));
    pstmt.setDouble(24,id);
	
	saveStatus = pstmt.executeUpdate();
    
   // System.err.println("===========update Query==============="+update);
  
	filter = "Record Updated Successfully" ;
    
    }
    
    else 
    	
    {
    pstmt = null ;
	
	String query2 = " INSERT INTO distillery.productionandconsumptionofalcohol(                                                "+
                    " distillery_id, year_id, month_id, produ_bl, produ_al, importoutstate_bl, importoutstate_al,              "+
                    " importoutindia_bl, importoutindia_al, consum_bl, consum_al, saleinupdrink_bl, saleinupdrink_al,          "+
                    " saleinupother_bl, saleinupother_al, saleoutstatedrink_bl, saleoutstatedrink_al, saleoutstateother_bl,    "+
                    " saleoutstateother_al, saleoutcountrydrink_bl, saleoutcountrydrink_al, saleoutcountryother_bl,            "+
                    " saleoutcountryother_al, wastage_bl, wastage_al, save_date, spirit_type, int_id)                          "+
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";  
	
	
	saveStatus = 0;
	pstmt = con.prepareStatement(query2);	
   
   // System.err.println("===========Insert Query==============="+query2);
	
	pstmt.setInt(1, act.getLoginUserId());
    pstmt.setString(2, act.getYearr());
    pstmt.setInt(3, Integer.parseInt(act.getMontth()));
    pstmt.setDouble(4, act.getProduction_bl());
    pstmt.setDouble(5, act.getProduction_al());
    pstmt.setDouble(6, act.getImport_outofstate_bl());
    pstmt.setDouble(7, act.getImport_outofstate_al());

    pstmt.setDouble(8, act.getImport_outofindia_bl());
    pstmt.setDouble(9, act.getImport_outofindia_al());
    pstmt.setDouble(10, act.getConsumption_bl());
    pstmt.setDouble(11, act.getConsumption_al());
    pstmt.setDouble(12, act.getSaleinup_drink_bl());
    pstmt.setDouble(13, act.getSaleinup_drink_al());

    pstmt.setDouble(14, act.getSaleinup_other_bl());
    pstmt.setDouble(15, act.getSaleinup_other_al());
    pstmt.setDouble(16, act.getSaleoutstate_drink_bl());
    pstmt.setDouble(17, act.getSaleoutstate_drink_al());
    pstmt.setDouble(18, act.getSaleoutstate_other_bl());

    pstmt.setDouble(19, act.getSaleoutstate_other_al());
    pstmt.setDouble(20, act.getSaleoutcountry_drink_bl());
    pstmt.setDouble(21, act.getSaleoutcountry_drink_al());
    pstmt.setDouble(22, act.getSaleoutcountry_other_bl());

    pstmt.setDouble(23, act.getSaleoutcountry_other_al());
    pstmt.setDouble(24, act.getWastage_bl());
    pstmt.setDouble(25, act.getWastage_al());
    pstmt.setDate(26, Utility.convertUtilDateToSQLDate(new Date()));
    pstmt.setString(27, act.getSpriit_type());
    pstmt.setDouble(28,id);
	
    saveStatus = pstmt.executeUpdate();
    filter = "Record Saved Successfully" ;
    }
if (saveStatus > 0) {
	FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(filter,filter));
	con.commit();
	act.reset();
} else {
	con.rollback();
	FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Record Not Saved","Record Not Saved"));
}
} catch (Exception se) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(se.getMessage(), se.getMessage()));
		se.printStackTrace();

	} finally {
		try {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();

		} catch (Exception se) {					
			se.printStackTrace();
		}
	}
	
}
   
//========================Max id
   
   public int maxId() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = " SELECT max(int_id) as id FROM distillery.productionandconsumptionofalcohol ";
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
