/**
 * This class is general util class for validation. 
 * 
 * @version 1.0 
 * File: Validate.java
 * Date            Author            Changes
 * Sep 04 2008   Satyendra Prakash pandey      Created
 */
package com.mentor.utility;


import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;









public class Validate {
	
	 
	public static boolean validateMinLength(String nameToCheck,String stringToCheck,int length) {

		boolean bool= true;
     	if(stringToCheck.length()<10)
     	   {ResourceUtil.addErrorMessage(nameToCheck,Constants.LENGH_SHORT);bool=false;return bool;}
     	return bool;
	
	}
	public static boolean emailFieldValidate(String str) {
	       
  		Pattern pattern=null;
  		Matcher matcher=null;
  		boolean flag=true;
  		
  		 pattern = Pattern.compile(Constants.EMAIL_PATTERN);
  		 
  		String email = (String) str;
  		
  		matcher = pattern.matcher(email);
		      
        
           //throw new ValidatorException(new FacesMessage("Invalid Email"));
        
        return matcher.matches();
     }
	public static boolean validateStng(String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
     	if(stringToCheck==null || stringToCheck.trim()=="" || stringToCheck.trim().length()==0)
    		{ResourceUtil.addErrorMessage(nameToCheck,Constants.VALUE_REQUIRED);bool=false;return bool;}
          	return bool;
		
	}
	
	public static boolean validateDateRow(int i,String nameToCheck,Date aDate)
	{
		boolean bool= true;
//     	java.sql.Date aDate=Utility.convertDateAsDBFormat(stringToCheck);
     	if(aDate==null){
     		ResourceUtil.addRowErrorMessage(i,nameToCheck,Constants.DATE_REQUIRED);bool=false;return bool;
     	}
     	return bool;
		
	}	
    
	public static boolean validateDouble(String nameToCheck, double bookPrice)
	{
		boolean bool= true;
		if(bookPrice==0.0){
     		ResourceUtil.addErrorMessage(nameToCheck,Constants.VALUE_REQUIRED);
    		bool=false;
    		return bool;
    		}
		return bool;
    		}
	
	public static boolean validateDoubleString(String nameToCheck, String bookPrice)
	{
		boolean bool= true;
		if(bookPrice==null){
     		ResourceUtil.addErrorMessage(nameToCheck,Constants.VALUE_REQUIRED);
    		bool=false;
    		return bool;
    		}
		return bool;
    		}
	
	
  	public static boolean validateDoubleGr3(String id2,double max,double min ) {
  	 	boolean boolFlag= true;
  	 	if (min>=max){
 			ResourceUtil.addErrorMessage(id2, Constants.LENGH_EXCEED1);
 			boolFlag=false;
 		}
  	 	return boolFlag;
  	 }
  	public static boolean validateDoubleRange(String id1, String id2,double basicPay) {
  	 	boolean boolFlag= false;
  	 	String str = id2;
  	 	int i=0;
  	 	double []d=new double[2];
  	 	System.out.println("id2 "+id2);
  	 	StringTokenizer stoken=new StringTokenizer(str,"-");
  	while(stoken.hasMoreElements())
  	{
  	String token=stoken.nextToken();
  	d[i]=Double.parseDouble(token);
  	System.out.println("Validate "+token);
    i++;
  	}
  	if(basicPay>=d[0] && basicPay<=d[1])
  	{
  		boolFlag=true;
  		return boolFlag;
  	 }
  	else
  	{
  		ResourceUtil.addErrorMessage(id1, Constants.BASIC_PAY);
  	return boolFlag;
  	}
  	}
	 public static boolean validateDoubleGr1(int rowNumber,String id, String id2,double max,double min ) {
 	 	boolean boolFlag= true;
 	 	if (min>=max){
			ResourceUtil.addRowErrorMessage(rowNumber,id2, Constants.LENGH_EXCEED1);
			boolFlag=false;
		}
 	 	return boolFlag;
 	 }
	
	 public static boolean validateDoubleGr2(int rowNumber,String id, String id2,double max,double min ) {
 	 	boolean boolFlag= true;
 	 	if (min>=max){
			ResourceUtil.addRowErrorMessage(rowNumber,id2, Constants.LENGH_EXCEED2);
			boolFlag=false;
		}
 	 	return boolFlag;
 	 }
	 public static Date compareDateMethod(Date aDate,Date bDate,Date cDate)
		{
			Date returnedDate = null;
	     	try{
		     		
		    		if(aDate.compareTo(bDate)<=0){
		    			returnedDate = aDate;
		    		}else{
		    			returnedDate = bDate;
		    		}
		    		if(returnedDate.compareTo(cDate)<=0){
		    			return returnedDate;
		    		}
		     		else{
		     			returnedDate = cDate;
		     		}
	     		}catch(Exception e){e.printStackTrace();}
	     	
		return returnedDate;	
		}
	 public static boolean compareDate(Date aDate,Date bDate)
		{
			boolean bool= false;
			try{
	     	if(aDate.after(bDate)){
	     		bool=true;return bool;
	     	}
	     	}catch(Exception e){e.printStackTrace();}
	     	return bool;
			
		}
	/*
  	 * Method to Compare
  	 * date
  	 * @author Satyendra
  	 */
 	public static boolean compareDate(String aDateName,Date aDate,String bDateName,Date bDate)
	{
		boolean bool= true;
		String[] params={ResourceUtil.getMessage(aDateName),ResourceUtil.getMessage(bDateName)};
     	try{
     	if(aDate.after(bDate)){
     		ResourceUtil.addErrorMessage(aDateName,Constants.COMPARE_DATE1,params);bool=false;return bool;
     	}
     	}catch(Exception e){e.printStackTrace();}
     	return bool;
		
	}
 	
 	

 	public static boolean compareTime(int rowNumber,String aTimeName,Date aTime,String bTimeName,Date bTime)
	{
		boolean bool= true;
		String[] params={ResourceUtil.getMessage(aTimeName),ResourceUtil.getMessage(bTimeName)};
//     	java.sql.Date aDateSql=DateConvertor.convertUtilDateToSQLDate(aDate);
//     	java.sql.Date bDateSql=DateConvertor.convertUtilDateToSQLDate(bDate);
     	try{
     	if(aTime.after(bTime)){
     		ResourceUtil.addErrorMessageRow(rowNumber,aTimeName,Constants.COMPARE_TIME,params);bool=false;return bool;
     	}
     	}catch(Exception e){e.printStackTrace();}
     	return bool;
		
	}
 	
	public static boolean compareDates(String aDateName,Date aDate)
	{
		boolean bool= true;
		Date bDate=new Date();
		if(aDate !=null){
     	if(aDate.after(bDate)){
     		ResourceUtil.addErrorMessage(aDateName,Constants.COMPARE_DATE);bool=false;return bool;
     	}
		}
     	return bool;
		
	}
    /**
     * This method is used for check validation for text box.
     * @param nameToCheck
     * @param stringToCheck
     * @return boolean 
     */

    public static boolean validateStringLen(int strLength,String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
			if(stringToCheck!=null)
        	if(stringToCheck.length()>strLength)
     	   {ResourceUtil.addErrorMessageSec(strLength, nameToCheck ,Constants.LENGH_EXCEED);bool=false;return bool;}
     	
     	return bool;
		
	}
    public static boolean validateStringLenRow(int i,int strLength,String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
		
        	if(stringToCheck.length()>strLength)
     	   {ResourceUtil.addRowErrorMessageSec(i,strLength, nameToCheck ,Constants.LENGH_EXCEED);bool=false;return bool;}
     	
     	return bool;
		
	}
    public static boolean validateStrReq(String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
     	if(stringToCheck==null || stringToCheck.trim()=="" || stringToCheck.trim().length()==0)
    		{ResourceUtil.addErrorMessage(nameToCheck,Constants.VALUE_REQUIRED);bool=false;return bool;}
          	return bool;
		
	}
    public static boolean validateStrReqRow(int i,String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
     	if(stringToCheck==null || stringToCheck.trim()=="" || stringToCheck.trim().length()==0)
    		{ResourceUtil.addRowErrorMessage(i,nameToCheck,Constants.VALUE_REQUIRED);bool=false;return bool;}
          	return bool;
		
	}
    
    
    public static boolean validateStrEdit(String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
     	if(stringToCheck==null || stringToCheck.trim()=="" || stringToCheck.trim().length()==0)
    		{ResourceUtil.addErrorMessage(nameToCheck,Constants.RECORD_EDIT_MODE);bool=false;return bool;}
          	return bool;
		
	}
    public static boolean validateBoolean(String nameToCheck,boolean stringToCheck)
	{
		boolean bool= true;
     	if(stringToCheck==false)
    		{
     		ResourceUtil.addErrorMessage(nameToCheck,Constants.VALUE_REQUIRED);bool=false;return bool;}
          	return bool;
		
	}
    public static boolean validateBooleanRow(int i,String nameToCheck,boolean stringToCheck)
	{
		boolean bool= true;
     	if(stringToCheck==false)
    		{
     		ResourceUtil.addRowErrorMessage(i,nameToCheck,Constants.VALUE_REQUIRED);bool=false;return bool;}
          	return bool;
		
	}
    /**
     * This method is used for check validation for text box.
     * @param nameToCheck
     * @param stringToCheck
     * @return boolean 
     */
    
    public static boolean validateCombo(String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
     	if(stringToCheck==null || stringToCheck.trim()=="" || stringToCheck.trim().length()==0)
    		{ResourceUtil.addErrorMessage(nameToCheck,Constants.SELECTBOX_REQUIRED);bool=false;return bool;}   	
     	return bool;
		
	}
    public static boolean validateComboRow(int i,String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
     	if(stringToCheck==null || stringToCheck.trim()=="" || stringToCheck.trim().length()==0)
    		{ResourceUtil.addRowErrorMessage(i,nameToCheck,Constants.SELECTBOX_REQUIRED);bool=false;return bool;}   	
     	return bool;
		
	}
     /**
     * This method is used for check validation for text area.
     * @param nameToCheck
     * @param stringToCheck
     * @return boolean
     */
    
    
	public static boolean validateText(String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
     	if(stringToCheck==null || stringToCheck.trim()=="" || stringToCheck.trim().length()==0)
    		{ResourceUtil.addErrorMessage(nameToCheck,Constants.VALUE_REQUIRED);bool=false;return bool;}
     	
     	if(onlyInteger(stringToCheck))
 	    {ResourceUtil.addErrorMessage(nameToCheck,Constants.NOT_INT);bool=false;return bool;}
     	
     	return bool;
		
	}
	
	/**
     * This method is used for check validation for integer.
     * @param nameToCheck
     * @param stringToCheck
     * @return boolean
     */
	
	
	public static boolean validateOnlyInt(String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
		
     	if(!onlyInteger(stringToCheck))
 	    {ResourceUtil.addErrorMessage(nameToCheck,Constants.ONLY_INT);bool=false;return bool;}
     	    	
     	return bool;
		
	}
	
	public static boolean validateOnlyDouble(String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
		if (stringToCheck.matches("[-+]?[0-9]*\\.?[0-9]+")) { // You can use the `\\d` instead of `0-9` too!
		    // Is a number - int, float, double    
			
		}else{
			ResourceUtil.addErrorMessage(nameToCheck,Constants.ONLY_NO);bool=false;return bool;
		}
     	     	
     	return bool;
		
	}
	 
	
	  public static boolean validateInteger(String nameToCheck,int a)
	 	{
	 		boolean bool= true;
	 		if(a ==0)
	  	    {ResourceUtil.addErrorMessage(nameToCheck,Constants.ONLY_INT);bool=false;return bool;}
	 		if(a <= 0)
	  	    {ResourceUtil.addErrorMessage(nameToCheck,Constants.ONLY_INT);bool=false;return bool;}   	
	      	return bool;
	 		
	 	}
	
	public static boolean validateAddRow(String nameToCheck,int size1,int size2)
	{
		boolean bool= true;
		
     	if(size1==size2-1)
 	    {ResourceUtil.addErrorMessage(nameToCheck,Constants.ROW_NOT_ADD);
 	   ResourceUtil.addErrorMessage(nameToCheck,Constants.SELECT_ALL_PASS);
 	    bool=false;return bool;}
     	    	
     	return bool;
		
	}
	
	
	
	/**
	 * This method is used check validation for special character.
	 * @param nameToCheck
	 * @param stringToCheck
	 * @return
	 */
	public static boolean validateNoSpecialCharRow(int i,String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
          	if(checkSpecial(stringToCheck))
 	    {ResourceUtil.addRowErrorMessage(i,nameToCheck,Constants.VALUE_SPECIAL_CHARACTOR);bool=false;return bool;}
          
     	  	return bool;
		
	}
	
	
	public static boolean validateNoSpecialChar(String nameToCheck,String stringToCheck)
	{
		boolean bool= true;
          	if(checkSpecial(stringToCheck))
 	    {ResourceUtil.addErrorMessage(nameToCheck,Constants.VALUE_SPECIAL_CHARACTOR);bool=false;return bool;}
          
     	  	return bool;
		
	}
	
	/**
     * This method is used to validate date
     * @param categoryName
     * @return
     */ 
    public static boolean validateDate(String nameToCheck,Date aDate)
	{
		boolean bool= true;
     	if(aDate==null){
     		ResourceUtil.addErrorMessage(nameToCheck,Constants.DATE_REQUIRED);bool=false;return bool;
     	}
     	return bool;
		
	}
    public static boolean validateDateOfBirth(String nameToCheck,Date aDate)
    {
    	boolean bool= true;
    	
    	//java.text.SimpleDateFormat formate = new java.text.SimpleDateFormat("dd/MM/yyyy");   
    	java.util.Date birthDay = null;
    	java.util.Date now = new java.util.Date();
    	try {
    	    birthDay = aDate;
    	    
    	    java.util.Calendar c1 = java.util.Calendar.getInstance();
    	    java.util.Calendar c2 = java.util.Calendar.getInstance();
    	    c1.setTime(now);
    	    c2.setTime(now);
    	    c1.add(java.util.Calendar.YEAR, -60);
    	    c2.add(java.util.Calendar.YEAR, -18);
    	    if (c1.getTime().getTime() >= birthDay.getTime()) {
    	       
    	        ResourceUtil.addErrorMessage(nameToCheck,Constants.DATE_OF_BIRTH);bool=false;return bool;
    	    }
    	    else if(c2.getTime().getTime() <= birthDay.getTime())
    	    {
    	    	
    	        ResourceUtil.addErrorMessage(nameToCheck,Constants.DATE_OF_BIRTH);bool=false;return bool;
    	    }
    	   
    	} catch (Exception pe) {
    	    System.out.println("Error parsing date");
    	}
    	return bool;
    }
    
/**
 * This method is used to check Numbers
 * @param categoryName
 * @return
 */
 public static boolean validateStrReqWithDateOfBirth(String nameToCheck,String stringToCheck,Date aDate)
 {
 	boolean bool= true;
 	
 	//java.text.SimpleDateFormat formate = new java.text.SimpleDateFormat("dd/MM/yyyy");   
 	java.util.Date birthDay = null;
 	java.util.Date now = new java.util.Date();
 	try {
 	    birthDay = aDate;
 	    System.out.println("bd = " + birthDay);
 	    
 	    java.util.Calendar c1 = java.util.Calendar.getInstance();
 	    
 	    c1.setTime(now);
 	   
 	    c1.add(java.util.Calendar.YEAR, -18);
 	   
 	   if(c1.getTime().getTime() <= birthDay.getTime())
 	    {
 	   if(stringToCheck==null || stringToCheck.trim()=="" || stringToCheck.trim().length()==0)
 	   {
 	        ResourceUtil.addErrorMessage(nameToCheck,Constants.VALUE_REQUIRED);bool=false;return bool;
 	    }
 	    }
 	    
 	} catch (Exception pe) {
 	    System.out.println("Error parsing date");
 	}
 	return bool;
 }
	private static boolean checkNumber(String categoryName)
     {
     	boolean flag=false;
     	for(int i=0;i<categoryName.length();i++)
     	{
     	char c=categoryName.charAt(i);
     	if(c>=48 && c<=57)flag=true;
     	}
     	return flag;
     }
     
/**
 * This method is used to check Special Charectors
 * @param categoryName
 * @return
 */
	 
     private static boolean checkSpecial(String categoryName)
     {
     	boolean flag=false;
     	for(int i=0;i<categoryName.length();i++)
     	{
     	char c=categoryName.charAt(i);
     	if((c>32 && c<=47) || (c>=58 && c<=64) || (c>=91 && c<=96) || (c>=124 && c<=255))flag=true;
    	}
     	return flag;
     }
     
     

     /**
      * This method is used to check if there is only integers
      * @param categoryName
      * @return
      */ 
     
     private static boolean onlyInteger(String categoryName)
     {
     	boolean flag=true;
     	for(int i=0;i<categoryName.length();i++)
     	{
     	char c=categoryName.charAt(i);
     	if(c!=0)
     	if(c>=48 && c<=57){}else flag=false;
    	}
     	return flag;
     }
     

/**
 * This method is used to check validation when delete button is pressed and any check box is not checked.
 *
 */    
     
     public static void validateCheckbox()
     {
    	ResourceUtil.addErrorMessage("Checkbox",Constants.CHECKBOX);
     }

   
     
     public static boolean validateRequired(String id, String value) {
		if (value == null || value.trim() == null || value.length() == 0) {
			ResourceUtil.addErrorMessage(id, Constants.VALUE_REQUIRED);
			return true;
		}
		return false;
	}
 	
         
     public static boolean validateSelectBox(String nameToCheck,String stringToCheck)
 	{
 		boolean bool= true;
      	if(stringToCheck.equals(Constants.SELECTBOX_NOTSELECTED_VALUE))
     		{ResourceUtil.addErrorMessage(nameToCheck,Constants.SELECTBOX_REQUIRED);bool=false;return bool;}
      	return bool;
 		
 	}
     public static boolean validateDoubleValue(String id, double value) {
	
     	boolean bool=true;
		if (value == 0.00f) {
			ResourceUtil.addErrorMessage(id, Constants.VALUE_REQUIRED);
			bool=false;
		}
		return bool;
	}
     
     public static boolean validateDoubleValueRow(int i,String id, double value) {
    	
         	boolean bool=true;
    		if (value == 0.00f) {
    			ResourceUtil.addRowErrorMessage(i,id, Constants.VALUE_REQUIRED);
    			bool=false;
    		}
    		return bool;
    	}
     // Its using in MF4 Gate Pass For Dispatch Molasses and rest Molasses.
     
     public static boolean validateDoubleGr(String id, String id2,double max,double min ) {
 	 	boolean boolFlag= true;
 	 	if (min>max){
			ResourceUtil.addErrorMessageTwoID(id,id2, Constants.LENGH_EXCEED_DEC);
			boolFlag=false;
		}
 	 	return boolFlag;
 	 }


     // Its using in MF4 Gate Pass
 	public static boolean validateCompareDates(String aDateName,Date aDate)
	{
		boolean bool= true;
		Date bDate=new Date();
		if(aDate !=null){
     	if(aDate.after(bDate)){
     		ResourceUtil.addErrorMessage(aDateName,Constants.COMPARE_DATE);bool=false;return bool;
     	}
		}
     	return bool;
		
	}
 	public static boolean validateCompareTwoDates(String aDateName,Date aDate,Date bDate)
	{
		boolean bool= true;
		
		if(aDate !=null){
     	if(aDate.after(bDate)){
     		ResourceUtil.addErrorMessage(aDateName,Constants.AFTER_DATE);bool=false;return bool;
     	}
		}
     	return bool;
		
	}
 	
 	public static boolean validateOnlyInt(String nameToCheck,Integer a)
	{
		boolean bool= true;
     	if(a==null)
    		{ResourceUtil.addErrorMessage(nameToCheck,Constants.VALUE_REQUIRED);bool=false;return bool;}
     	if(a.intValue()<=0)
     	   {ResourceUtil.addErrorMessage(nameToCheck,Constants.VALUE_REQUIRED);bool=false;return bool;}
     	 	return bool;
		
	}
 	
 	
 	
 	
 	public static boolean validatePercentage(String str, double rate) {
		boolean boll = true;
		//		if (rate>=0)
		//		{
		//			return true;
		//		}
		//		else
		if (rate == 0.0) {
			ResourceUtil.addErrorMessage(str, Constants.VALUE_REQUIRED);
			boll=false;
		}
		return boll;
	}
 	
 	public static boolean validateMaxLength(String nameToCheck,String stringToCheck,int length)
	{
		boolean bool= true;
     	if(stringToCheck.length()>length)
     	   {ResourceUtil.addErrorMessage(nameToCheck,Constants.LENGH_EXCEED);bool=false;return bool;}
     	return bool;
	}
 	
 	 public static boolean validateTextChar(String stringToCheck,String categoryName)
     {
     	boolean flag=false;
     	int countChar=0;
     	for(int i=0;i<categoryName.length();i++)
     	{
     	char c=categoryName.charAt(i);
     	if(c>=48 && c<=57){
     		countChar++;
     		flag=false;
     		}
     	
     	
     	//if((c>=65 && c<=90) || (c>=97 && c<=122)){}else flag=false;
    	}
     	if(countChar>0)
  	   {
     		ResourceUtil.addErrorMessage(stringToCheck,Constants.CHAR_ONLY); 
     		
     	}
     	if (countChar==0)
     	{
     		flag=true;
     	}
  	
     	
     	return flag;
     }
 	 
	  public static boolean validateIntegerRow(int i,String nameToCheck,int a)
	 	{
	 		boolean bool= true;
	 		if(a ==0)
	  	    {ResourceUtil.addRowErrorMessage(i,nameToCheck,Constants.ONLY_INT);bool=false;return bool;}
	 		if(a <= 0)
	  	    {ResourceUtil.addRowErrorMessage(i,nameToCheck,Constants.ONLY_INT);bool=false;return bool;}   	
	      	return bool;
	 		
	 	}
	  
	  
	  
	  private static boolean onlyDouble(String categoryName)
	    {
	    	boolean flag=true;
	    	for(int i=0;i<categoryName.length();i++)
	    	{
	    	char c=categoryName.charAt(i);
	    	if((c>=48 && c<=57) || (c==46)){
	    		
	    	}else
	    		flag=false;;
	   	}
	    	return flag;
	    }
	public static boolean validateOnlyDoubleValueRow(int i,String id, String value) {
	   	
   	boolean bool=true;
		if(!onlyDouble(value)) {
			ResourceUtil.addRowErrorMessage(i,id, Constants.ONLY_INT);
			
			bool=false;
		}
		   	
		return bool;
	}

	public static boolean validateCompareDatesGreater(String aDateName,java.util.Date aDate,String bDateName,java.util.Date bDate)
	{
		boolean bool= true;
		
		String[] params={ResourceUtil.getMessage(aDateName),ResourceUtil.getMessage(bDateName)};

     	if(aDate.after(bDate)){
     		ResourceUtil.addErrorMessage(aDateName,"javax.faces.component.UIInput.compared_date_dynamic",params);
     		bool=false;
     		return bool;
     	}
     	return bool;
		
	}
	public static boolean validateCompareDatesLesser(String aDateName,java.util.Date aDate,String bDateName,java.util.Date bDate)
	{
		boolean bool= true;
		
		String[] params={ResourceUtil.getMessage(aDateName),ResourceUtil.getMessage(bDateName)};

     	if(bDate.after(aDate)){
     		ResourceUtil.addErrorMessage(aDateName,"javax.faces.component.UIInput.compared_date_Lesser",params);
     		bool=false;
     		return bool;
     	}
     	return bool;
		
	}





}
