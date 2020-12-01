package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.event.ValueChangeEvent;
 
import com.mentor.impl.DispatcherReportImpl;
import com.mentor.utility.Validate;


public class DispatcherReportAction {
	
	
	
	
	DispatcherReportImpl impl=new DispatcherReportImpl();
	
	private String pdfname;
	private boolean printFlag=false;
	private String radiogroup="S";
	private Date fromdate;
	private Date todate;
	private String radioCLandFL;
	private boolean excelFlag=false;
	private String exlname;
	
	
	private String bwfl_FL2d_Id;
	//private ArrayList bwfl_FL2d_List=new ArrayList();
	
	
	private boolean validateInput;

	public boolean isValidateInput() {
		
		  this.validateInput=true;
		  
		  if(!(Validate.validateDate("FromDate",this.getFromdate())))validateInput=false;
		  else if(!(Validate.validateDate("Todate",this.getTodate())))validateInput=false;
		 
		
			else if (!(Validate.validateStrReq("Radio", this.getBwfl_FL2d_Id())))validateInput=false; 
		  
		

		
		return validateInput;
	}



	public void setValidateInput(boolean validateInput) {
		this.validateInput = validateInput;
	}
	
	
	
	
	
	
	
	
	public String getBwfl_FL2d_Id() {
		return bwfl_FL2d_Id;
	}



	public void setBwfl_FL2d_Id(String bwfl_FL2d_Id) {
		this.bwfl_FL2d_Id = bwfl_FL2d_Id;
	}


	ArrayList getAll_List=new ArrayList();
	
	
	public ArrayList getGetAll_List() {
		if(this.radioCLandFL !=null && this.radioCLandFL.length()>0)
		{
			this.getAll_List=impl.getAll(this);
		}
		return getAll_List;
	}



	public void setGetAll_List(ArrayList getAll_List) {
		this.getAll_List = getAll_List;
	}



	


	public String getExlname() {
		return exlname;
	}



	public void setExlname(String exlname) {
		this.exlname = exlname;
	}



	public boolean isExcelFlag() {
		return excelFlag;
	}



	public void setExcelFlag(boolean excelFlag) {
		this.excelFlag = excelFlag;
	}



	public String getRadioCLandFL() {
		return radioCLandFL;
	}



	public void setRadioCLandFL(String radioCLandFL) {
		this.radioCLandFL = radioCLandFL;
	}



	public Date getFromdate() {
		return fromdate;
	}



	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}



	public Date getTodate() {
		return todate;
	}



	public void setTodate(Date todate) {
		this.todate = todate;
	}



	public String getRadiogroup() {
		return radiogroup;
	}



	public void setRadiogroup(String radiogroup) {
		this.radiogroup = radiogroup;
	}



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


/*
	public void print()
	{
		if(this.radioCLandFL.equalsIgnoreCase("CL"))
		{
			impl.printReportCountryLiquor(this);
		}
		else if(this.radioCLandFL.equalsIgnoreCase("FL"))
		{
			impl.printReportForeignLiquor(this);		
		}
		else if(this.radioCLandFL.equalsIgnoreCase("B"))
		{
			impl.printReportBeer(this);		
		}
	}
	*/
	

	/*public void excel()
	{ 
		if(this.radioCLandFL.equalsIgnoreCase("CL"))
		{
			impl.writeCL(this);
		}else{
			impl.write(this);
		}

	}
	*/
//-------------------------------------------------------------
	

	
	
	
	public void print()
	{
		if (isValidateInput()) {
			

		if(this.radioCLandFL.equalsIgnoreCase("BWFL")){
			
			
			System.out.println("come in bwfl");
			impl.printDispatchesBWFL(this);
		}
		else if(this.radioCLandFL.equalsIgnoreCase("FL2D")){
		
			impl.printFL2D(this); 
		}
		else if(this.radioCLandFL.equalsIgnoreCase("D")){
			
			impl.printD(this); 
		}
		else if(this.radioCLandFL.equalsIgnoreCase("B")){
			
			impl.printB(this); 
		}
		
		else{}
		}
		
	}
	
	
	
	public void excel()
	{
		if (isValidateInput()) {
		if(this.radioCLandFL.equalsIgnoreCase("BWFL")){
			impl.writeDispatchesExcelBWFL(this); 
		}
		else if(this.radioCLandFL.equalsIgnoreCase("FL2D")){
			impl.writeDispatchesExcelForFL2D(this); 
		}
		else if(this.radioCLandFL.equalsIgnoreCase("D")){
			impl.writeDispatchesExcelDistillery(this); 
		}
		else if(this.radioCLandFL.equalsIgnoreCase("B")){
			impl.writeDispatchesExcelBrewary(this); 
		}
		else{}
		}
		
	}
	
	
	public void reset()
	{
	this.radiogroup="S";
	this.printFlag=false;
	this.pdfname="";
	this.fromdate=null;
	this.todate=null;
	this.radioCLandFL="BWFL";
	this.excelFlag=false;
	this.exlname=null;
	this.fromdate=null;
	this.todate=null;
	this.bwfl_FL2d_Id=null;
	this.getAll_List.clear();
	}

	public void radioListiner(ValueChangeEvent e)
	{
		this.excelFlag=false;
		this.printFlag=false;
		this.pdfname="";
		this.getAll_List.clear();
	}
	
//-----------------------------------------


}
