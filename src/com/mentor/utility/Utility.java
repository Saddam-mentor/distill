/*
 * Created on Sep 27, 2007
 * * @author Satyendra
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mentor.utility;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;
 








public class Utility {
	public static String currentMonth(){
		
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		
		Locale locale = Locale.getDefault();
	    
	    DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
	    String[] monthNames = dateFormatSymbols.getMonths();
//		create an array of months    
		/*String[] strMonths = new String[]{"January", 
										  "February", 
										  "March", 
										  "April",  
										  "May", 
										  "June",  
										  "July",  
										  "August", 
										  "September",  
										  "October",   
										  "November", 
										  "December"   
										  };
		//String currentMonth = String.valueOf(month);
*/		
	    
	    String currentMonth = monthNames[month];
		
	  
	return 	currentMonth;
	}
	/*
	   * Method to get server date from the database
	   * @return connection con 
	   */
		
	 
	 public static java.util.Date currentTime(String dateFormat) {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    return cal.getTime();

	  }
	/**
	* Convert the java.util.Date to java.sql.Time.
	* @param utilDate the time.
	* @return the converted time.
	*/

	public static final java.sql.Time utilDateToSqlTime(java.util.Date utilDate) {
	return new java.sql.Time(utilDate.getTime());
	}

	/**
	 * This method convert Sql date to string date
	 * @param date  java.sql.Date
	 * @return String
	 */
	public static String convertDateAsCalender(Date date) {
		String dateString = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					Constants.DATE_PATTERN);
			dateString = formatter.format(date);
		} catch (Exception e) {
			System.out.println("Exception=" + e.getMessage());
		}
		return dateString;
	}

	/**
	 * This method is use to convert string date to sql Date
	 * @param date String
	 * @return java.sql.Date
	 * @throws java.text.ParseException
	 */
	public static java.sql.Date convertDateAsDBFormat(String date)
	{
	    try {
			if (!isNullOrEmpty(date)) {
				SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN);
				sdf.setLenient(false); // this is required else it will convert
				java.util.Date dt = sdf.parse(date);
				java.sql.Date sqldt = new java.sql.Date(dt.getTime());
				return sqldt;
			}

		} catch (ParseException e) {
	
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method checks whether text is null or empty
	 * 
	 * @param text
	 *            String
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String text) {
		if (text != null) {
			if (text.trim().length() == 0)
				return true;
			return false;
		}
		return true;
	}

	/**
	 *This method convert String date to Util date
	 * @param date String
	 * @return  java.util.Date
	 * @exception java.text.ParseException
	 */
	//String date="October 24, 2007";
		public static java.util.Date dateStringToUtilDate(String date)
		throws ParseException {

			java.util.Date dt = null;
			if (!isNullOrEmpty(date)) {
				SimpleDateFormat dtFormat = new SimpleDateFormat(
			    Constants.DATE_PATTERN);
				dt = dtFormat.parse(date);
			}
return dt;
}


	/**
	 * This method check whether start date is befor than End Date
	 * @param startDate String
	 * @param endDate String
	 * @return boolean 
	 */
	public static boolean isStartDateBeforeEndDate(String startDate,
			String endDate) {
		try {
			java.util.Date startTime = dateStringToUtilDate(startDate);
			java.util.Date endTime = dateStringToUtilDate(endDate);
			if (!startTime.before(endTime))
				return false;
		} catch (ParseException pe) {
			System.out.println("ParseException=" + pe.getMessage());
		} catch (Exception e) {
			System.out.println("Exception=" + e.getMessage());
		}
		return true;

	}
	

	/**
	 * This method get year from selected data 
	 * @param date 
	 * @return year String
	 * @throws java.text.ParseException
	 */
	public static String getYear(String date) throws java.text.ParseException {

		if(!isNullOrEmpty(date))
		{
			java.sql.Date date1 = Utility.convertDateAsDBFormat(date);
			StringTokenizer st=new StringTokenizer(date1.toString(), "-");
			return st.nextToken();
		}
		else
			return null;
	}
	
	/**
	 * This method to check Whether the date is within calendar year 
	 * @param date 
	 * @param fyear -calendar year
	 * @return boolean
	 * @throws java.text.ParseException
	 */
	public static boolean isCalendarDate(String strDate , int fyear) {
	   boolean bool=true;
		try {
			java.util.Date startTime = dateStringToUtilDate(strDate);
			
			java.util.Date fyearStartDate = dateStringToUtilDate("January 1, " + fyear );
			java.util.Date fyearEndDate = dateStringToUtilDate("December 31, " + fyear);
			
			if (startTime.before(fyearStartDate) || startTime.after(fyearEndDate))
			{
			    bool=false;
			}
			
			
		} catch (ParseException pe) {
			System.out.println("ParseException=" + pe.getMessage());
		} catch (Exception e) {
			System.out.println("Exception=" + e.getMessage());
		}
		return bool;

	}
	
	/**This method convert util date to Sql date
	 * @param date util 
	 * @return java.sql.Date
	 */
	public static java.sql.Date convertUtilDateToSQLDate(java.util.Date date){
		if(date!=null){
		java.sql.Date d1=new java.sql.Date(date.getTime());
		return d1;
		}
	return null;
	}
	
	
		  

		  public static String getTime() {
		  	//public  final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss");
		    return sdf.format(cal.getTime());

		  }
		  
		/**
		 * This method convert Sql date to string date
		 * @param date  java.sql.Date
		 * @return String
		 */
		public static String convertDateAsCalenderShort(Date date) {
			String dateString = "";
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(
						Constants.DATE_PATTERN_N);
				dateString = formatter.format(date);
			} catch (Exception e) {
				System.out.println("Exception=" + e.getMessage());
			}
			return dateString;
		}
	
		public static java.util.Date convertSqlDateToUtilDate(java.sql.Date date) {
			
			try {
				if(date!=null){
				return new java.util.Date(date.getTime());
				}
			} catch (Exception e) {
				//System.out.println("Exception=" + e.getMessage());
			}
			return null;
		}

			
	
	
	
}
