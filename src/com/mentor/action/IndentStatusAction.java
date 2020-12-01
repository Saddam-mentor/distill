package com.mentor.action;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.IndentStatusImpl;


public class IndentStatusAction {

	private String radioCheck;
	private String pdfname;

	private boolean printFlag=false;
	
	
	public boolean isPrintFlag() {
		return printFlag;
	}


	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}

	
	public String getPdfname() {
		return pdfname;
	}

	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
	}

	public void chngval(ValueChangeEvent ee) {

	}

	public String getRadioCheck() {
		return radioCheck;
	}
	
	public void savePdf()
	{
		if(this.getRadioCheck()!=null)
		{
			new IndentStatusImpl().printReport(this);
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Please Select Radio Type!!", "Please Select Radio Type!!"));
		}
    }
	
	
	
	public void setRadioCheck(String radioCheck) {
		this.radioCheck = radioCheck;
	}
}
