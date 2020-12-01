
package com.mentor.utility;

import java.text.MessageFormat;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * This class contains all validate method
 * @author Abhishek 
 * Created 8/08/2013
 */
public class Validator {

	/**
	 * This method check that whether entered data is of valid type or not
	 * @param id is Name of field)
	 * @param value is value in field
	 * @param type which type numeric/Alphabets
	 * @return boolean
	 */
	public static boolean validateData(String id, String value, String type) {
		StringBuffer buffer = new StringBuffer(value);
		boolean flag = false;
		int i = 0;
		while (i < buffer.length() && !flag) {
			char ch = buffer.charAt(i);
			if (type.equals(Constants.ENTER_LETTERS)) {
				if (!Character.isDigit(ch))
					i++;
				else if (Character.isWhitespace(ch))
					buffer.deleteCharAt(i);
				else
					{flag = true;
					break;
					}
			} else if (type.equals(Constants.ENTER_NUMBER)) {
				if (!Character.isLetter(ch))
					i++;
				else if (Character.isWhitespace(ch))
					buffer.deleteCharAt(i);
			  	else
				{flag = true;
				break;}
				
			  }
			}	
		if (flag) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			String message = ResourceUtil.getMessagee(new String[] { id, type },	Constants.ENTER_ONLY);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,message, message);
			ctx.addMessage(id, msg);
			return true;
		}
		return false;
	}

	/**
	 * This method check valiad length of data enterd in the specified field
	 * @param id String
	 * @param to minimum lenghth
	 * @param from         int Max lenght
	 * @return boolean
	 */
	public static boolean validateRange( String id,String value, int to, int from) {

		if (value.length() < to || value.length() > from) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			String messageKey = ResourceUtil.getMessage(Constants.NOT_IN_RANGE);
			MessageFormat formatter = new MessageFormat(messageKey);
			String name = ResourceUtil.getMessage(id);
			String message=formatter.format(new String[] { name,"" + to, "" + from });
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					message, message);
			ctx.addMessage(id, msg);
			return true;
		}
		return false;

	}

	/**This method check for Reuired value
	 * @param id String () 
	 * @param value
	 * @return
	 */
	public static boolean validateRequired(String id, String value) {
		if (value == null || value.trim() == null || value.length() == 0) {
			ResourceUtil.addErrorMessage(id, Constants.VALUE_REQUIRED);
			return true;
		}
		return false;
	}

	/**This method valid whether field contains any special charecter 
	 * @param id String
	 * @param value String
	 * @return
	 */
	public static boolean ValidateWildChars( String id,String value)
     {
     	boolean flag=false;
     	String wildChar="";
     	for(int i=0;i<value.length();i++)
     	{
     	char c=value.charAt(i);
     	if((c>=32 && c<=47) || (c>=58 && c<=64) || (c>=91 && c<=96) || (c>=124 && c<=255))
     		flag=true;
     	wildChar=wildChar+c;
     	}
     	if (flag) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			String message = ResourceUtil.getMessagee(new String[] {id,wildChar },
					Constants.MESSAGE_FOR_WILD_CHAR);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					message, message);
			ctx.addMessage(id, msg);
			return true;
		}
     	return flag;
     }
	/**
	 * This method check whether start date is befor than End Date
	 * @param startDate String
	 * @param endDate String
	 * @return boolean 
	 */
//	public static boolean isStartDateBeforeEndDate(String startDate,String endDate) {
//		try {
//			java.util.Date startTime = DateConvertor.dateStringToUtilDate(startDate);
//			java.util.Date endTime = DateConvertor.dateStringToUtilDate(endDate);
//			if (!startTime.before(endTime))
//				return false;
//		} catch (ParseException pe) {
//			pe.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return true;
//
//	}
	/**
	 * This method is use to compare between two date
	 * 
	 * @param fromDate
	 *            String
	 * @param aDate
	 *            Date
	 * @param toDate
	 *            String
	 * @param bDate
	 *            Date
	 * @return boolean
	 */
	public static boolean isAfterToDate(String fromDate, java.util.Date aDate,
			String toDate, java.util.Date bDate) {
		boolean bool = false;
		String[] params = { fromDate, toDate };
		java.sql.Date aDateSql = new java.sql.Date(aDate.getTime());
		java.sql.Date bDateSql = new java.sql.Date(bDate.getTime());
		if (aDateSql.after(bDateSql)) {
			String message = ResourceUtil.getMessagee(new String[] { fromDate,
					toDate }, Constants.COMPARE_DATE);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					message, message);
			FacesContext.getCurrentInstance().addMessage(fromDate, msg);
			return true;

		}
		return bool;

	}
	
	public static boolean isBeforeToDate(String fromDate, java.util.Date aDate,
			String toDate, java.util.Date bDate) {
		boolean bool = false;
		String[] params = { fromDate, toDate };
		java.sql.Date aDateSql = new java.sql.Date(aDate.getTime());
		java.sql.Date bDateSql = new java.sql.Date(bDate.getTime());
		if (aDateSql.before(bDateSql)) {
			String message = ResourceUtil.getMessagee(new String[] { fromDate,
					toDate }, Constants.COMPARE_DATE);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					message, message);
			FacesContext.getCurrentInstance().addMessage(fromDate, msg);
			return true;
}
		return bool;

	}


	

	}