package com.mentor.action;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.WholesaleANDBondsApprovalImpl;

public class WholesaleANDBondsApprovalAction {
	
	WholesaleANDBondsApprovalImpl impl = new WholesaleANDBondsApprovalImpl();
	
	private String radio_type;
	private String radioS;
	private String radioView="T";
	
	

	public String getRadioView() {
		return radioView;
	}

	public void setRadioView(String radioView) {
		this.radioView = radioView;
	}

	public String getRadio_type() {
		return radio_type;
	}

	public void setRadio_type(String radio_type) {
		this.radio_type = radio_type;
	}

	public String getRadioS() {
		return radioS;
	}
	

	public void setRadioS(String radioS) {
		this.radioS = radioS;
	}
	
	
	public void radioVal(ValueChangeEvent ee) {

		String val = (String) ee.getNewValue();
		this.setRadio_type(val);
		this.radioView="F";
        this.pdfName = null;
		this.setPrintFlag(false);
		this.exlname = null ;
		this.excelFlag = false;
		

	}
	
	public void radioValS(ValueChangeEvent ee) {

		String val = (String) ee.getNewValue();
		this.setRadioS(val);

	}
	
	public void print() {

		try {
			if (this.radio_type != null && this.radioS != null) {
				impl.printReport(this);
			} else {
				/*FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								" Please Select Dates !!",
								" Please Select Dates !!"));*/


				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" Please Select Radio  !!", " Please Select Radio !!"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	//-------------------------------------------
	private String exlname;
	public String getExlname() {
		return exlname;
	}

	public void setExlname(String exlname) {
		this.exlname = exlname;
	}
	private boolean excelFlag = false;

	public boolean isExcelFlag() {
		return excelFlag;
	}

	public void setExcelFlag(boolean excelFlag) {
		this.excelFlag = excelFlag;
	}

	public void excel() {
	
		System.out.println("------ENTER EXCEL---");
	//	impl.printexcelSummary(this);
		
		if(this.radioS != null && this.radio_type !=null){

	    	if(this.radioS != null  && this.radioS.equalsIgnoreCase("S")){
	    		System.out.println("------up summary---");
	    		impl.printexcelSummary(this);
	    	
	    	}
	    	
	    else if (this.radio_type != null  && this.radio_type.equalsIgnoreCase("UP") )
	    {
	    	if( this.radioS != null  && this.radioS.equalsIgnoreCase("D")){
	    		impl.printexcelDetail(this);
	    		System.out.println("------up  Detail---");
	    	}
	    }
	    else if( this.radio_type != null  && this.radio_type.equalsIgnoreCase("W")){
	    	if (this.radioS != null  && this.radioS.equalsIgnoreCase("D")){
	    		impl.printexcelWholeseal(this);
	    		System.out.println("------ Wholseal Detail---");
	    	}
	    }
	    
	    
	} else {
		/*FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						" Please Select Dates !!",
						" Please Select Dates !!"));*/


		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						" Please Select Radio  !!", " Please Select Radio !!"));
	}
		
	
		}
	
	
	//------------------------------------------
	
	public void reset(){
		this.radio_type=null;
		this.pdfName = null;
		this.radioView="T";
		this.setPrintFlag(false);
		this.exlname = null ;
		this.excelFlag = false;
	}
	private boolean printFlag;
	private String pdfName;



	public boolean isPrintFlag() {
		return printFlag;
	}

	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

}
