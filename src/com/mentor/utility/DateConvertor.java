/*
 * Created on Sep 10 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mentor.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author abhishek gautam
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DateConvertor {
	private static String FORMAT="MMMM dd, yyyy";
	 public final static long SECOND_MILLIS = 1000;
	    public final static long MINUTE_MILLIS = SECOND_MILLIS*60;
	public static java.sql.Date stringToSqlDate(String aString)
	{
		SimpleDateFormat aSimpleDateFormat=new SimpleDateFormat(FORMAT);
		try
		{
		java.util.Date utilDate=aSimpleDateFormat.parse(aString);
		java.sql.Date sqlDate=new java.sql.Date(utilDate.getTime());
		return sqlDate;
		}catch(Exception e)
		{
			System.out.println("Exception in stringToSqlDate method in DateConvertor class::"+e.getMessage());
		}
		return null;
	}
	
	public static java.util.Date convertSqlDateToUtilDate(java.sql.Date date) {
		
		try {
			return new java.util.Date(date.getTime());
		} catch (Exception e) {
			System.out.println("Exception=" + e.getMessage());
		}
		return null;
	}
	
	public static String sqlDateToString(java.sql.Date aDate)
	{
		
			 SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
			 try
			 {
		     String aString = DateFormat.getDateInstance().format(aDate);
		     return aString;
			 }catch(Exception e)
			 {
			 	System.out.println("Exception in sqlDateToString method in DateConvertor class::"+e.getMessage());
			 }
				return null;
		
		
	}
	/**This method convert util date to Sql date
	 * @param date util 
	 * @return java.sql.Date
	 */
	public static java.sql.Date convertUtilDateToSQLDate(java.util.Date date){
		if(date==null)
		{
		return null;	
		}
		else{
			
		java.sql.Date d1=new java.sql.Date(date.getTime());
		return d1;
		}
	}

	

}
