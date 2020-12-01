package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
 
import com.mentor.impl.MolasisReportImpl;

public class MolasisReportAction {
	
	private String radio="D";
	private String radioh="C";
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	
	private String insideUpDistilleryFilter;
	private String insideUpIndustryFilter;
	private String exportFilter;
	
	
	private String selectDistillery;
	private String selectIndustry;
	private String selectDOUP;
	private String selectIOUP;
	
	
	public ArrayList selectmenu=new ArrayList();
	
	
	
	public ArrayList getSelectmenu() {
		
		try{
			this.selectmenu=new MolasisReportImpl().getSelectList(this);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return selectmenu;
	}
	public void setSelectmenu(ArrayList selectmenu) {
		
		
		
		
		this.selectmenu = selectmenu;
	}
	public String getSelectDistillery() {
		return selectDistillery;
	}
	public void setSelectDistillery(String selectDistillery) {
		this.selectDistillery = selectDistillery;
	}
	public String getSelectIndustry() {
		return selectIndustry;
	}
	public void setSelectIndustry(String selectIndustry) {
		this.selectIndustry = selectIndustry;
	}
	
	public String getSelectDOUP() {
		return selectDOUP;
	}
	public void setSelectDOUP(String selectDOUP) {
		this.selectDOUP = selectDOUP;
	}
	public String getSelectIOUP() {
		return selectIOUP;
	}
	public void setSelectIOUP(String selectIOUP) {
		this.selectIOUP = selectIOUP;
	}
	public String getInsideUpDistilleryFilter() {
		return insideUpDistilleryFilter;
	}
	public void setInsideUpDistilleryFilter(String insideUpDistilleryFilter) {
		this.insideUpDistilleryFilter = insideUpDistilleryFilter;
	}
	public String getInsideUpIndustryFilter() {
		return insideUpIndustryFilter;
	}
	public void setInsideUpIndustryFilter(String insideUpIndustryFilter) {
		this.insideUpIndustryFilter = insideUpIndustryFilter;
	}
	public String getExportFilter() {
		return exportFilter;
	}
	public void setExportFilter(String exportFilter) {
		this.exportFilter = exportFilter;
	}
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
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	
	public String getRadioh() {
		return radioh;
	}
	public void setRadioh(String radioh) {
		this.radioh = radioh;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	
	
	
	
	
	public String printReport()
	{
		
		if((this.fromDate != null)&&(this.toDate != null)){
		
		try{
			new MolasisReportImpl().printReport(this);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		}else{
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Please Select Date!!", "Please Select Date!!"));
			
			System.out.println("----------hehe");
			
		}
		return "";
	}
	
	
	
	public String reset()
	{
		try{
			this.printFlag=false;
			this.fromDate=null;
			this.toDate=null;
			this.radio="D";
			this.selectDistillery="0";
			this.selectIndustry="0";
			this.selectDOUP="0";
			this.selectIOUP="0";
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	
	public void radix(ValueChangeEvent e)
	{
		this.reset();
		

	}
	private ArrayList seasonList = new ArrayList();
private String season;


	public String getSeason() {
	return season;
}
public void setSeason(String season) {
	this.season = season;
}
	public ArrayList getSeasonList() {
		try {
			this.seasonList= new MolasisReportImpl().getSeason();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
		}
		return seasonList;
	}
	public void setSeasonList(ArrayList seasonList) {
		this.seasonList = seasonList;
	}
	

}
