package com.mentor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;

public class BarGraphActionImpl {
	
	
	public void currentMonth(){
		

		Calendar cal = Calendar.getInstance();
   	 Calendar gc = new GregorianCalendar();
   	 
        gc.set(Calendar.MONTH,cal.get(Calendar.MONTH));
        gc.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = gc.getTime();
        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        Date monthEnd = gc.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int updtStatus=0;
		try
		{
			
		String query =	" UPDATE public.graphmonth SET startmonth='"+format.format(monthStart)+"'," +
				" endmonth='"+format.format(monthEnd)+"' WHERE sn=1 ";	
System.out.println("-----currentMonth-------"+query);
				
		conn = ConnectionToDataBase.getConnection();
		
		pstmt=conn.prepareStatement(query);
		//System.out.println("--test---"+query);
		/*pstmt.setLong(1,Long.parseLong(action.getOffPhon()));
		pstmt.setString(2,action.getOffFax());*/
		
		
		updtStatus=pstmt.executeUpdate();
		
		
    	
			
		
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
		
		finally
		{
	  		try
	  		{	
	  			if(pstmt!=null)pstmt.close();
	      		if(conn!=null)conn.close();      		
	      		if(pstmt!=null)pstmt.close();
	      		if(conn!=null)conn.close();
	  		}
	  		catch(Exception e)
	  		{
	  			e.printStackTrace();
	  		}
	  	}
	
		
	}

	public void mothupdate(String month){
		
		
		int year=0;
		if(month.equals("0")||month.equals("1")||month.equals("2"))
		{
			year=2021;
		}else{
			year=2020;
		}
		
		Calendar cal = Calendar.getInstance();
   	 Calendar gc = new GregorianCalendar();
   	 
        gc.set(Calendar.MONTH,Integer.parseInt(month));
        gc.set(Calendar.DAY_OF_MONTH, 1);
        gc.set(Calendar.YEAR, year);
        Date monthStart = gc.getTime();
        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        Date monthEnd = gc.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        

		
        
		System.out.println("month start "+format.format(monthStart)+" month end "+format.format(monthEnd));
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int updtStatus=0;
		try
		{
			
		String query =	" UPDATE public.graphmonth SET startmonth='"+format.format(monthStart)+"'," +
				" endmonth='"+format.format(monthEnd)+"' WHERE sn=1 ";	

				
		conn = ConnectionToDataBase.getConnection();
		conn.setAutoCommit(false);
		pstmt=conn.prepareStatement(query);
		
		/*pstmt.setLong(1,Long.parseLong(action.getOffPhon()));
		pstmt.setString(2,action.getOffFax());*/
		
		
		updtStatus=pstmt.executeUpdate();
		
		
    	if(updtStatus>0)
		{
			
			conn.commit();
			ResourceUtil.addMessage(Constants.SAVED_SUCESSFULLY,Constants.SAVED_SUCESSFULLY);
			conn.close();
			//action.Reset();
		}
		else
		{
			conn.rollback();
			ResourceUtil.addErrorMessage(Constants.NOT_SAVED,Constants.NOT_SAVED);
			conn.close();
		}
			
		
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
		
		finally
		{
	  		try
	  		{	
	  			if(pstmt!=null)pstmt.close();
	      		if(conn!=null)conn.close();      		
	      		if(pstmt!=null)pstmt.close();
	      		if(conn!=null)conn.close();
	  		}
	  		catch(Exception e)
	  		{
	  			e.printStackTrace();
	  		}
	  	}
	}
	
}
