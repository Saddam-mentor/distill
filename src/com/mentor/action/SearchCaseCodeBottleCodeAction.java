package com.mentor.action;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.impl.SearchCaseCodeBottleCodeImpl;

public class SearchCaseCodeBottleCodeAction {

	private String radio;
	private String casecode_bottle;
	private boolean validate;
    public boolean isValidate() {
    	
    	try{
    		
    		this.validate=true;
    		if(this.radio==null)
    		{
    			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please Select One Radion Bottle Or Casecode", "Please Select One Radion Bottle Or Casecode"));
    			this.validate=false;
    			return  validate;
    		
    		}else if(this.radio.equals("B")&&this.casecode_bottle.length()<32)
    		{
    			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please Enter Correct Bottle Code 32 Digit", "Please Enter Correct Bottle Code 32 Digit"));	
    			this.validate=false;
    			return  validate;
    		
    		}else if(this.radio.equals("C")&&this.casecode_bottle.length()<35)
    		{ 
    			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please Enter Correct Case Code 35 Digit", "Please Enter Correct Case Code 35 Digit"));	
    		
    			this.validate=false;
    			return  validate;
    		}
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}


	private ArrayList list=new ArrayList();
	public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}

	public String getCasecode_bottle() {
		return casecode_bottle;
	}

	public void setCasecode_bottle(String casecode_bottle) {
		this.casecode_bottle = casecode_bottle;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public void search() {
		try {
			if(isValidate())
			{
			if(this.radio.equals("B"))
			{
			new SearchCaseCodeBottleCodeImpl().searchBottleCode(this,this.getCasecode_bottle());
			}else{
			new SearchCaseCodeBottleCodeImpl().	searchCaseCode(this,this.getCasecode_bottle());
				
			}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void reset()
	{
		try{
			this.casecode_bottle="";
			this.list.clear();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
