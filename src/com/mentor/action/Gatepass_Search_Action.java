package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;


import com.mentor.impl.Gatepass_Search_Impl;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Validate;

public class Gatepass_Search_Action 
{
	
	Gatepass_Search_Impl impl=new Gatepass_Search_Impl();
	
	private ArrayList getVal=new ArrayList();
	private ArrayList getCaseCode=new ArrayList();
	private Date gatepass_date=null;
	private String gatepassNo="";
	private String vch=null;
	private int int_dist_id;
	private Date dt_date;
	private String vch_from;
	private String vch_to;
	private String vch_from_lic_no;
	private String vch_to_lic_no;
	private String vch_gatepass_no;
	private String licenceenm;
	private String licenceeadd;
	private String routeDtl;
	private String vehicleNo;
	private String vehicleDrvrName;
	private String vehicleAgencyNmAdrs;
	private boolean flag;
	private boolean getCaseCodeFlag;
	private String licenceType;
	
	public void reset()
	{
		this.getVal=null;
		this.gatepass_date=null;
		this.gatepassNo="";
		this.vch=null;
		this.int_dist_id=0;
		this.dt_date=null;
		this.vch_from=null;
		this.vch_to=null;
		this.vch_from_lic_no=null;
		this.vch_to_lic_no=null;
		this.vch_gatepass_no=null;
		this.licenceenm=null;
		this.licenceeadd=null;
		this.routeDtl=null;
		this.vehicleNo=null;
		this.vehicleDrvrName=null;
		this.vehicleAgencyNmAdrs=null;
		this.flag=false;
		this.getCaseCodeFlag=false;
		this.licenceType=null;
	}
	
	
	
	
	public ArrayList getGetCaseCode() {
		return getCaseCode;
	}



	public void setGetCaseCode(ArrayList getCaseCode) {
		this.getCaseCode = getCaseCode;
	}



	public boolean isFlag() {
		return flag;
	}



	public void setFlag(boolean flag) {
		this.flag = flag;
	}



	public String getLicenceenm() {
		return licenceenm;
	}
	public void setLicenceenm(String licenceenm) {
		this.licenceenm = licenceenm;
	}
	public String getLicenceeadd() {
		return licenceeadd;
	}
	public void setLicenceeadd(String licenceeadd) {
		this.licenceeadd = licenceeadd;
	}
	public String getRouteDtl() {
		return routeDtl;
	}
	public void setRouteDtl(String routeDtl) {
		this.routeDtl = routeDtl;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getVehicleDrvrName() {
		return vehicleDrvrName;
	}
	public void setVehicleDrvrName(String vehicleDrvrName) {
		this.vehicleDrvrName = vehicleDrvrName;
	}
	public String getVehicleAgencyNmAdrs() {
		return vehicleAgencyNmAdrs;
	}
	public void setVehicleAgencyNmAdrs(String vehicleAgencyNmAdrs) {
		this.vehicleAgencyNmAdrs = vehicleAgencyNmAdrs;
	}
	public int getInt_dist_id() {
		return int_dist_id;
	}
	public void setInt_dist_id(int int_dist_id) {
		this.int_dist_id = int_dist_id;
	}
	public Date getDt_date() {
		return dt_date;
	}
	public void setDt_date(Date dt_date) {
		this.dt_date = dt_date;
	}
	public String getVch_from() {
		return vch_from;
	}
	public void setVch_from(String vch_from) {
		this.vch_from = vch_from;
	}
	public String getVch_to() {
		return vch_to;
	}
	public void setVch_to(String vch_to) {
		this.vch_to = vch_to;
	}
	public String getVch_from_lic_no() {
		return vch_from_lic_no;
	}
	public void setVch_from_lic_no(String vch_from_lic_no) {
		this.vch_from_lic_no = vch_from_lic_no;
	}
	public String getVch_to_lic_no() {
		return vch_to_lic_no;
	}
	public void setVch_to_lic_no(String vch_to_lic_no) {
		this.vch_to_lic_no = vch_to_lic_no;
	}
	public String getVch_gatepass_no() {
		return vch_gatepass_no;
	}
	public void setVch_gatepass_no(String vch_gatepass_no) {
		this.vch_gatepass_no = vch_gatepass_no;
	}
	
	
	

	public Date getGatepass_date() {
		return gatepass_date;
	}
	public void setGatepass_date(Date gatepass_date) {
		this.gatepass_date = gatepass_date;
	}

	public String getGatepassNo() {
		return gatepassNo;
	}
	public void setGatepassNo(String gatepassNo) {
		this.gatepassNo = gatepassNo;
	}
	public String getVch() {
		return vch;
	}
	public void setVch(String vch) {
		this.vch = vch;
	}
	public ArrayList getGetVal() {
		return getVal;
	}
	public void setGetVal(ArrayList getVal) {
		this.getVal = getVal;
	}



	public boolean isGetCaseCodeFlag() {
		return getCaseCodeFlag;
	}



	public void setGetCaseCodeFlag(boolean getCaseCodeFlag) {
		this.getCaseCodeFlag = getCaseCodeFlag;
	}



	public String getLicenceType() {
		return licenceType;
	}



	public void setLicenceType(String licenceType) {
		this.licenceType = licenceType;
	}
	
	
	public void radioListener(ValueChangeEvent ve)
	{
		String vch=(String)ve.getNewValue();
		this.setVch(vch);
		System.out.println("selected radio"+this.getVch());
		
	}
	
	public void viewCaseCode( )
	{
		
		if(this.vch.equalsIgnoreCase("D"))
		{
		
		this.getCaseCode=impl.getCaseCodeDis(this);
		System.out.println("selected radio"+this.getVch());
		
		}
		
		else if(this.vch.equalsIgnoreCase("B"))
		{
			this.getCaseCode=impl.getCaseCodeBre(this);

		}
		else if(this.vch.equalsIgnoreCase("WS"))
		{
			this.getCaseCode=impl.getCaseCodeWS(this);

		}
		
		else
		{
			this.getCaseCode=impl.getCaseCodeBWFL_FL2D(this);

		}
	
		
	
		//this.getCaseCodeFlag=true;
		
	}
	
	
	public void getData()
	{
	try
	{
		System.out.println("this.isValidateInput()()==="+isValidateInput());
	
		int x=0;
		
		
	if(this.getVch().equalsIgnoreCase("D") || this.getVch().equalsIgnoreCase("B") || this.getVch().equalsIgnoreCase("WS") )
	{
		if(this.licenceType==null)
		{
			x=1;
			
		
		}
	}
		
		
			
	if(!isValidateInput())	
	{
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Select Radio Button", "Select Radio Button"));
		
	}
	else
	{
		if(this.getGatepassNo()==null || this.getGatepassNo().length()==0)
		{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Enter GatePass No", "Enter GatePass No"));
			
		}else
		{
			if(this.gatepass_date==null)
			{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Select GatePass Date", "Select GatePass Date"));
				
				
			}	else
			{
				
				if(x==0)
				{
				this.getVal=impl.getDataFromimpl(this);
				}else
				{
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Select License Type ", "Select License Type"));
				}
				
			}
			
			
		}
		
		
	}

	}catch(Exception e)
	{
		
	}
	
	}
	
	private boolean validateInput;
	
	
	public boolean isValidateInput() 
	{
		validateInput = true;

		if (!(Validate.validateStrReq("radio", this.getVch())))
			validateInput = false;
	
		return validateInput;
	}

	public void setValidateInput1(boolean validateInput) {
		this.validateInput = validateInput;
	}
	
	
	


	
}
