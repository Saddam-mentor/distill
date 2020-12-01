package com.mentor.action;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import com.mentor.impl.DistrictWiseRevenueReceiptImpl;
public class DistrictWiseRevenueReceiptAction {
private String finanMonth;
private String hidden;
private String districtName;
private String district_id;
private String pdfname;
private boolean printFlag;

public String getPdfname() {
	return pdfname;
}

public void setPdfname(String pdfname) {
	this.pdfname = pdfname;
}

public boolean isPrintFlag() {
	
	return printFlag;
}

public void setPrintFlag(boolean printFlag) {
	this.printFlag = printFlag;
}
DistrictWiseRevenueReceiptImpl impl=new DistrictWiseRevenueReceiptImpl();



public String getDistrictName() {
	return districtName;
}

public void setDistrictName(String districtName) {
	this.districtName = districtName;
}

public String getDistrict_id() {
	return district_id;
}

public void setDistrict_id(String district_id) {
	this.district_id = district_id;
}

public String getHidden() {
	try {
		impl.getUserDetails(this);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return hidden;
}

public void setHidden(String hidden) {
	this.hidden = hidden;
}

public String getFinanMonth() {
	return finanMonth;
}

public void setFinanMonth(String finanMonth) {
	this.finanMonth = finanMonth;
}
public void changelis(ValueChangeEvent val) {
	//String value = (String) val.getNewValue();
	
	this.setPrintFlag(false);
}
public void print()
{   
	if(finanMonth==null || finanMonth=="0" || finanMonth.equalsIgnoreCase("0")){
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
				"Please Select Financial Month !!","Please Select Financial Month !!"));
	}
	else{	
	impl.printReport(this);
	}
}
private String radio = "D";

public String getRadio() {
	return radio;
}

public void setRadio(String radio) {
	this.radio = radio;
}
public void radioListener(ValueChangeEvent e) {

	try {
		String val = (String) e.getNewValue();
		this.printFlag=false;
	} catch (Exception e1) {
		e1.printStackTrace();
	}

} 
}
