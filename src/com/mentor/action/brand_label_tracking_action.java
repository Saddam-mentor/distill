package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.UIDataTable;


import com.mentor.datatable.brand_label_tracking_dt;
import com.mentor.impl.brand_label_tracking_impl;

import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Validate;

public class brand_label_tracking_action {
	

	
	brand_label_tracking_impl impl = new brand_label_tracking_impl();

	private String hidden;
	private String radioType = "Y";
	private String appDate;
	private int unitID;
	private int appID;
	private String showApplicationID;
	private String unitName;
	private String unitAddress;
	private String unitType;
	private String liquorCategory;
	private String licenseType;
	private String userDomain;
	private boolean viewPanelFlg;
	private boolean hideDataTable = true;
	private int total_no_labels =0 ;;
	private long total_fees = 0;
	private String unitTypeOrg;
	
	private ArrayList displayRegUsers = new ArrayList();
	private ArrayList displayLabelDetails = new ArrayList();
	
	
	private String user1Remark;
	private String user2Remark;
	private String user3Remark;
	private String user4Remark;
	private String user5Remark;
	private String fillRemrks;
	private Date physicalRcvdate=new Date();	
	private boolean disableObjRaisedFlg=false;
	private boolean approvingFlg;
	private boolean paymentflgFlg;
	private boolean objRaisedflg;
	private boolean objRplyFlg;
	private boolean checkBox;
	private boolean checkBoxFlg;
	private String decDistributionRemark;
	private boolean decRemarkFlg;
	private ArrayList labelChallanList= new ArrayList();
	private boolean bwfldisplayflg;
	
private String brandtext;
private String licencetype;

public String getLicencetype() {
	return licencetype;
}

public void setLicencetype(String licencetype) {
	this.licencetype = licencetype;
}


    
    
    
	public String getBrandtext() {
		return brandtext;
	}

	public void setBrandtext(String brandtext) {
		this.brandtext = brandtext;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public boolean isBwfldisplayflg() {
		return bwfldisplayflg;
	}

	public void setBwfldisplayflg(boolean bwfldisplayflg) {
		this.bwfldisplayflg = bwfldisplayflg;
	}

	public ArrayList getLabelChallanList() {
		return labelChallanList;
	}

	public void setLabelChallanList(ArrayList labelChallanList) {
		this.labelChallanList = labelChallanList;
	}

	public boolean isPaymentflgFlg() {
		return paymentflgFlg;
	}

	public void setPaymentflgFlg(boolean paymentflgFlg) {
		this.paymentflgFlg = paymentflgFlg;
	}

	public boolean isDecRemarkFlg() {
		return decRemarkFlg;
	}

	public void setDecRemarkFlg(boolean decRemarkFlg) {
		this.decRemarkFlg = decRemarkFlg;
	}
	
	public String getDecDistributionRemark() {
		return decDistributionRemark;
	}

	public void setDecDistributionRemark(String decDistributionRemark) {
		this.decDistributionRemark = decDistributionRemark;
	}
	
	public String getUnitTypeOrg() {
		return unitTypeOrg;
	}

	public void setUnitTypeOrg(String unitTypeOrg) {
		this.unitTypeOrg = unitTypeOrg;
	}

	public boolean isCheckBoxFlg() {
		return checkBoxFlg;
	}

	public void setCheckBoxFlg(boolean checkBoxFlg) {
		this.checkBoxFlg = checkBoxFlg;
	}

	public boolean isCheckBox() {
		return checkBox;
	}

	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	

	public boolean isDisableObjRaisedFlg() {
		return disableObjRaisedFlg;
	}

	public void setDisableObjRaisedFlg(boolean disableObjRaisedFlg) {
		this.disableObjRaisedFlg = disableObjRaisedFlg;
	}

	public boolean isApprovingFlg() {
		return approvingFlg;
	}

	public void setApprovingFlg(boolean approvingFlg) {
		this.approvingFlg = approvingFlg;
	}

	public boolean isObjRaisedflg() {
		return objRaisedflg;
	}

	public void setObjRaisedflg(boolean objRaisedflg) {
		this.objRaisedflg = objRaisedflg;
	}

	public boolean isObjRplyFlg() {
		return objRplyFlg;
	}

	public void setObjRplyFlg(boolean objRplyFlg) {
		this.objRplyFlg = objRplyFlg;
	}

	public Date getPhysicalRcvdate() {
		return physicalRcvdate;
	}

	public void setPhysicalRcvdate(Date physicalRcvdate) {
		this.physicalRcvdate = physicalRcvdate;
	}

	public String getUser1Remark() {
		return user1Remark;
	}

	public void setUser1Remark(String user1Remark) {
		this.user1Remark = user1Remark;
	}

	public String getUser2Remark() {
		return user2Remark;
	}

	public void setUser2Remark(String user2Remark) {
		this.user2Remark = user2Remark;
	}

	public String getUser3Remark() {
		return user3Remark;
	}

	public void setUser3Remark(String user3Remark) {
		this.user3Remark = user3Remark;
	}

	public String getUser4Remark() {
		return user4Remark;
	}

	public void setUser4Remark(String user4Remark) {
		this.user4Remark = user4Remark;
	}

	public String getUser5Remark() {
		return user5Remark;
	}

	public void setUser5Remark(String user5Remark) {
		this.user5Remark = user5Remark;
	}

	public String getFillRemrks() {
		return fillRemrks;
	}

	public void setFillRemrks(String fillRemrks) {
		this.fillRemrks = fillRemrks;
	}

	public int getTotal_no_labels() {
		return total_no_labels;
	}

	public void setTotal_no_labels(int total_no_labels) {
		this.total_no_labels = total_no_labels;
	}


	public long getTotal_fees() {
		return total_fees;
	}

	public void setTotal_fees(long total_fees) {
		this.total_fees = total_fees;
	}

	public String getHidden() {
		if (ResourceUtil.getUserNameAllReq().trim().equalsIgnoreCase("Excise-Commissioner")) {
			approvingFlg = true;

		} else {
			approvingFlg = false;
		}
		
		if (ResourceUtil.getUserNameAllReq().trim().equalsIgnoreCase("Excise-DEC-Licence")) {
			
			checkBoxFlg = true;
			this.setDelete_brand_pack("T");

		} else {
			checkBoxFlg = false;
		}
		if (ResourceUtil.getUserNameAllReq().trim().equalsIgnoreCase("Excise-AC-License")) {
			paymentflgFlg = true;
			revertFlg = true;
		} else {
			paymentflgFlg = false;
			revertFlg = false;
		}
		return hidden;
	}

	public void setHidden(String hidden) {		
		this.hidden = hidden;
	}

	public String getRadioType() {
		return radioType;
	}

	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}


	public int getUnitID() {
		return unitID;
	}

	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}

	public int getAppID() {
		return appID;
	}

	public void setAppID(int appID) {
		this.appID = appID;
	}

	public String getShowApplicationID() {
		return showApplicationID;
	}

	public void setShowApplicationID(String showApplicationID) {
		this.showApplicationID = showApplicationID;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitAddress() {
		return unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getLiquorCategory() {
		return liquorCategory;
	}

	public void setLiquorCategory(String liquorCategory) {
		this.liquorCategory = liquorCategory;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}

	public boolean isViewPanelFlg() {
		return viewPanelFlg;
	}

	public void setViewPanelFlg(boolean viewPanelFlg) {
		this.viewPanelFlg = viewPanelFlg;
	}

	public boolean isHideDataTable() {
		return hideDataTable;
	}

	public void setHideDataTable(boolean hideDataTable) {
		this.hideDataTable = hideDataTable;
	}

	public ArrayList getDisplayRegUsers() {
		/*try {
			if (this.radioType != null) {
				this.displayRegUsers = impl.displayRegUsersImpl(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return displayRegUsers;
	}

	public void setDisplayRegUsers(ArrayList displayRegUsers) {
		this.displayRegUsers = displayRegUsers;
	}

	public ArrayList getDisplayLabelDetails() {
		return displayLabelDetails;
	}

	public void setDisplayLabelDetails(ArrayList displayLabelDetails) {
		this.displayLabelDetails = displayLabelDetails;
	}

	

	public void radioListener(ValueChangeEvent e) {

		try {
			String val = (String) e.getNewValue();
			this.viewPanelFlg = false;
			this.setRadioType(val);
			
			//this.displayRegUsers = impl.displayRegUsersImpl(this);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	  private String brandtype;
	  private boolean brandtypeflg;
		

	    
	    
	    
		public boolean isBrandtypeflg() {
		return brandtypeflg;
	}

	public void setBrandtypeflg(boolean brandtypeflg) {
		this.brandtypeflg = brandtypeflg;
	}

		public String getBrandtype() {
			return brandtype;
		}

		public void setBrandtype(String brandtype) {
			this.brandtype = brandtype;
		}
		
		

	/*public void viewDetails(ActionEvent e) {
		
		try{
			UIDataTable uiTable = (UIDataTable) e.getComponent().getParent().getParent();
			brand_label_tracking_dt dt = (brand_label_tracking_dt) this.getDisplayRegUsers().get(uiTable.getRowIndex());
			
			this.hideDataTable = false;
			this.viewPanelFlg = true;

			this.setAppID(dt.getAppID_dt());
			this.setUnitID(dt.getUnitID_dt());
			this.setShowApplicationID(dt.getShowApplicationID_dt());
			this.setAppDate(dt.getAppDate_dt());
			this.setUnitName(dt.getUnitName_dt());
			this.setUnitAddress(dt.getUnitAddress_dt());
			this.setUnitType(dt.getUnitType_dt());
			this.setUnitTypeOrg(dt.getUnitTypeOrg_dt());
			this.setLiquorCategory(dt.getLiquorCategory_dt());
			this.setLicenseType(dt.getLicenseType_dt());
			this.setUserDomain(dt.getUserDomain_dt());
			this.setUser1Name(dt.getUser1Name_dt());
			this.setUser1Remark(dt.getUser1Remark_dt().trim());
			this.setUser2Remark(dt.getUser2Remark_dt().trim());
			this.setUser3Remark(dt.getUser3Remark_dt().trim());
			this.setUser4Remark(dt.getUser4Remark_dt().trim());
			this.setUser4Remark_dtrevert(dt.getUser4Remark_dtrevert().trim());
			this.setUser5Remark(dt.getUser5Remark_dt().trim());
			this.setBrndchallan(dt.getBrndchallan());
			this.setLabelchallan(dt.getLabelchallan());
            this.setRenew_new(dt.getRenew_new());
            this.setBrandtext(dt.getBrandtext());
            this.setBrandtype(dt.getBrandtype());
            this.setBrnd_reg_in_20_21(dt.getBrnd_reg_in_20_21());
          
            if(brandtype.equalsIgnoreCase("Existing Brand with New Labels")) 
           {
        	   this.setBrandtypeflg(true);
           }
            this.setLicenseDate(dt.getLicenseDate());
			this.setDecDistributionRemark(dt.getDecDistributionRemark_dt());
			
			this.displayLabelDetails = impl.displayLabelDetailsImpl(this);	
			this.showUploadedLabels = impl.getUploadedLabels(this);
			this.brandChallaList = impl.getbrandChallanList(this);
			this.labelChallanList= impl.getlabelChallanList(this);
			impl.fee(this,dt.getAppID_dt());	
			
			impl.getdata(this);
			
			
			this.showObjection.clear();
			if (dt.getObjRaised_Status().equalsIgnoreCase("N")) {
				this.setObjHistoryFlg(false);

			} else {
				this.setObjHistoryFlg(true);

			}
			if (ResourceUtil.getUserNameAllReq().trim().equalsIgnoreCase("Excise-Commissioner")) {
				fillRemrks="Approved as Proposed";
			}
			
			
			if (dt.getObjRaised_Status().equalsIgnoreCase("O")|| dt.getObjRaised_Status().equalsIgnoreCase("R")) {
				this.setDisableObjRaisedFlg(true);
				if (dt.getObjRaised_Status().equalsIgnoreCase("R")) {
					this.setObjRplyFlg(true);
				} else {
					this.setObjRplyFlg(false);
				}

			} else {
				this.setObjRplyFlg(false);
				this.setDisableObjRaisedFlg(false);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}*/

	// ------------------to raise objection popup-----------------

	private String objection_for;
	private String obj_Description;
	

	public String getObjection_for() {
		return objection_for;
	}

	public void setObjection_for(String objection_for) {
		this.objection_for = objection_for;
	}

	public String getObj_Description() {
		return obj_Description;
	}

	public void setObj_Description(String obj_Description) {
		this.obj_Description = obj_Description;
	}

	public void submit_objection() {

		try {
			if (this.objection_for != null && this.objection_for.length() > 0 && this.obj_Description != null && this.obj_Description.length() > 0) 
			{
				impl.save_Objection(this);
			}else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Please Fill Objection Title & Description !!","Please Fill Objection Title & Description !!"));
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ----------------show uploaded labels popup----------------------

	public ArrayList showUploadedLabels = new ArrayList();
	public ArrayList brandChallaList = new ArrayList();
	private String popup2Hidden;
	private String showUploadedAffidavit="/doc/ExciseUp/LabelRegistration/pdf/";
	private String showUploadedBR="/doc/ExciseUp/LabelRegistration/pdf/";
	private String showUploadedManualRcpt="/doc/ExciseUp/LabelRegistration/pdf/";
	
	
	public ArrayList getBrandChallaList() {
		return brandChallaList;
	}

	public void setBrandChallaList(ArrayList brandChallaList) {
		this.brandChallaList = brandChallaList;
	}
	public String getPopup2Hidden() {
		/*try {
			this.showUploadedLabels = impl.getUploadedLabels(this);
		} catch (Exception e) {

		}*/
		return popup2Hidden;
	}

	public void setPopup2Hidden(String popup2Hidden) {
		this.popup2Hidden = popup2Hidden;
	}

	public String getShowUploadedAffidavit() {
		return showUploadedAffidavit;
	}

	public void setShowUploadedAffidavit(String showUploadedAffidavit) {
		this.showUploadedAffidavit = showUploadedAffidavit;
	}

	public String getShowUploadedBR() {
		return showUploadedBR;
	}

	public void setShowUploadedBR(String showUploadedBR) {
		this.showUploadedBR = showUploadedBR;
	}
	

	public String getShowUploadedManualRcpt() {
		return showUploadedManualRcpt;
	}

	public void setShowUploadedManualRcpt(String showUploadedManualRcpt) {
		this.showUploadedManualRcpt = showUploadedManualRcpt;
	}

	public ArrayList getShowUploadedLabels() {
		if (this.showUploadedLabels.size() == 0) {

			

		} else {

		}
		return showUploadedLabels;
	}

	public void setShowUploadedLabels(ArrayList showUploadedLabels) {
		this.showUploadedLabels = showUploadedLabels;
	}

	// ----------------view objection reply popup------------------

	private String popup4ObjectedFor;
	private String popup4ActionTaken;
	private String popup4Hidden;
	private int popup4objID;
	private String popup4ObjectedPdf;
	private boolean viewpdfFlg;

	
	

	public String getPopup4ObjectedFor() {
		return popup4ObjectedFor;
	}

	public void setPopup4ObjectedFor(String popup4ObjectedFor) {
		this.popup4ObjectedFor = popup4ObjectedFor;
	}

	public String getPopup4ActionTaken() {
		return popup4ActionTaken;
	}

	public void setPopup4ActionTaken(String popup4ActionTaken) {
		this.popup4ActionTaken = popup4ActionTaken;
	}

	public String getPopup4Hidden() {
		try {
			impl.getObjectionReplies(this);
		} catch (Exception e) {

		}
		return popup4Hidden;
	}

	public void setPopup4Hidden(String popup4Hidden) {
		this.popup4Hidden = popup4Hidden;
	}

	public int getPopup4objID() {
		return popup4objID;
	}

	public void setPopup4objID(int popup4objID) {
		this.popup4objID = popup4objID;
	}

	public String getPopup4ObjectedPdf() {
		return popup4ObjectedPdf;
	}

	public void setPopup4ObjectedPdf(String popup4ObjectedPdf) {
		this.popup4ObjectedPdf = popup4ObjectedPdf;
	}

	public boolean isViewpdfFlg() {
		return viewpdfFlg;
	}

	public void setViewpdfFlg(boolean viewpdfFlg) {
		this.viewpdfFlg = viewpdfFlg;
	}
	
	
	public void agreeReply() {

		try {

			impl.agreeReplyImpl(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void declineReply() {

		try {

			impl.declineReplyImpl(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	//---------------------view objection history popup------------------
	
	
	public ArrayList showObjection = new ArrayList();
	public boolean objHistoryFlg;

	public ArrayList getShowObjection() {
		if (this.showObjection.size() == 0) {

			this.showObjection = impl.getObjectionHistory(this);

		} else {

		}
		return showObjection;
	}

	public void setShowObjection(ArrayList showObjection) {
		this.showObjection = showObjection;
	}

	public boolean isObjHistoryFlg() {
		return objHistoryFlg;
	}

	public void setObjHistoryFlg(boolean objHistoryFlg) {
		this.objHistoryFlg = objHistoryFlg;
	}
	
	
	private boolean validateInput;
	private String user1Name;
		

	public String getUser1Name() {
		return user1Name;
	}

	public void setUser1Name(String user1Name) {
		this.user1Name = user1Name;
	}

	public boolean isValidateInput() {
		
		validateInput = true;

		if (!(Validate.validateStrReq("remark", this.getFillRemrks())))
			validateInput = false;	
		
		if(isCheckBoxFlg()){
			if (!(Validate.validateDate("rcvngDt", this.getPhysicalRcvdate())))
				validateInput = false;
		}

		return validateInput;
	}

	public void setValidateInput(boolean validateInput) {
		this.validateInput = validateInput;
	}

	public String forwardApplication() {

		try {
			if (isValidateInput()) 
			{
				if(isCheckBoxFlg() && !renew_new.equalsIgnoreCase("Renew"))
				{
					if (this.checkBox == true) 
					{
						impl.forwardApplicationImpl(this);
					}
					 else {
							FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Verify that the labels uploaded have been recieved physically !!","Verify that the labels uploaded have been recieved physically !!"));
						}
				}else{
					impl.forwardApplicationImpl(this);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}
	
	
	public String approveApplication() {
		try {
			if (this.fillRemrks != null && this.fillRemrks.length() > 0) {				
				impl.getdata(this);
				impl.approvedApplicationImpl(this);
						
				}else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Please Fill Remarks!!", "Please Fill Remarks!!"));
					}				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	public String rejectApplication() {
		try {
			if (this.fillRemrks != null && this.fillRemrks.length() > 0) {
					impl.rejectApplicationImpl(this);
				}
			else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Please Fill Remarks!!", "Please Fill Remarks!!"));
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/*public String paymentreject() {
		try {
			if (this.fillRemrks != null && this.fillRemrks.length() > 0) {
					impl.paymentreject(this);
				}
			else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Please Fill Remarks!!", "Please Fill Remarks!!"));
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}	
	
	
	public String paymentapprove() {
		try {
			if (this.fillRemrks != null && this.fillRemrks.length() > 0) {
					impl.paymentapprove(this);
				}
			else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Please Fill Remarks!!", "Please Fill Remarks!!"));
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}		
	*/
	
	//----------------------close application-----------------------
	
	
	

	public void closeApplication() {
		
		//this.displayRegUsers = impl.displayRegUsersImpl(this);
		this.viewPanelFlg = false;
		this.hideDataTable=true;
		this.approvingFlg = false;
		this.checkBoxFlg = false;
		this.fillRemrks = null;
		this.appID = 0;
		this.unitID = 0;
		this.showApplicationID=null;
		this.unitName=null;
		this.unitAddress=null;
		this.unitType=null;
		this.liquorCategory=null;
		this.licenseType=null;
		this.userDomain=null;
		this.objection_for = null;
		this.obj_Description = null;
		this.popup4ObjectedFor = null;
		this.popup4ActionTaken = null;
		this.popup4objID = 0;
		this.popup4ObjectedPdf = null;
		this.viewpdfFlg = false;
		this.physicalRcvdate=new Date();
		this.disableObjRaisedFlg=false;
		this.checkBox=false;
		this.displayLabelDetails.clear();
		this.displayLabelDetails .clear();
		this.showUploadedLabels.clear(); 
		this.brandChallaList.clear(); 
		this.labelChallanList.clear();
		this.setBwfl_yes_no(null);
		this.brandtypeflg=false;brandtype=null;
		this.setDelete_brand_pack("F");
		

	}
	
	
	public void print(ActionEvent e) {
			
			try{
				UIDataTable uiTable = (UIDataTable) e.getComponent().getParent().getParent();
				brand_label_tracking_dt dt = (brand_label_tracking_dt) this.getDisplayRegUsers().get(uiTable.getRowIndex());
				
			
				if(impl.printReport(this,dt.getUnitName_dt(),dt.getUnitAddress_dt(),dt.getUnitType_dt(),
						dt.getUserDomain_dt(),dt.getAppID_dt(),dt.getLiquorCategory_dt(),dt.getDistrict(),dt).equalsIgnoreCase("F")){
				//	this.displayRegUsers = impl.displayRegUsersImpl(this);
				}
			
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
	
		}


	public void approveLabel(ActionEvent e) {
		
		try{
			UIDataTable uiTable = (UIDataTable) e.getComponent().getParent().getParent();
			brand_label_tracking_dt dt = (brand_label_tracking_dt) this.getDisplayLabelDetails().get(uiTable.getRowIndex());
			
			if(impl.approveLabelImpl(this, dt.getLabelId_dt(), dt.getSrNo()) == true){
				this.displayLabelDetails = impl.displayLabelDetailsImpl(this);	
			}
			
			//impl.approveLabelImpl(this, dt.getLabelId_dt(), dt.getSrNo());

			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	
	}
	
	public void rejectLabel(ActionEvent e) {
			
			try{
				UIDataTable uiTable = (UIDataTable) e.getComponent().getParent().getParent();
				brand_label_tracking_dt dt = (brand_label_tracking_dt) this.getDisplayLabelDetails().get(uiTable.getRowIndex());
				
				if(impl.rejectLabelImpl(this, dt.getLabelId_dt(), dt.getSrNo()) == true){
					this.displayLabelDetails = impl.displayLabelDetailsImpl(this);	
				}
				
				//impl.rejectLabelImpl(this, dt.getLabelId_dt(), dt.getSrNo());
	
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
		
		}
	
	private boolean approveDisblFlg=true;
     private String renew_new;
     private int brandid;
private String unittype;
private int unit_id;
private Date licenseDate;


public Date getLicenseDate() {
	return licenseDate;
}

public void setLicenseDate(Date licenseDate) {
	this.licenseDate = licenseDate;
}
public void digitalsign()
{
	
}
 	
 	

 	public String getUnittype() {
	return unittype;
}

public void setUnittype(String unittype) {
	this.unittype = unittype;
}

public int getUnit_id() {
	return unit_id;
}

public void setUnit_id(int unit_id) {
	this.unit_id = unit_id;
}

	public int getBrandid() {
 		return brandid;
 	}

 	public void setBrandid(int brandid) {
 		this.brandid = brandid;
 	}

	
	

	


	public String getRenew_new() {
		return renew_new;
	}

	public void setRenew_new(String renew_new) {
		this.renew_new = renew_new;
	}

	public boolean isApproveDisblFlg() {
		return approveDisblFlg;
	}

	public void setApproveDisblFlg(boolean approveDisblFlg) {
		this.approveDisblFlg = approveDisblFlg;
	}
	//private String labelchallan="/doc/ExciseUp/CHALLAN/pdf/";
	//private String brndchallan="/doc/ExciseUp/CHALLAN/pdf/";
	private String labelchallan;
	private String brndchallan;
	private String brandname;
	private String brandstrength;
	private String yesno;
	private String liquorsubCategory;
	private String for_;
	private double labelchallanfee;
	private double brndchallanfee;




	public double getLabelchallanfee() {
		return labelchallanfee;
	}

	public void setLabelchallanfee(double labelchallanfee) {
		this.labelchallanfee = labelchallanfee;
	}

	public double getBrndchallanfee() {
		return brndchallanfee;
	}

	public void setBrndchallanfee(double brndchallanfee) {
		this.brndchallanfee = brndchallanfee;
	}

	public String getFor_() {
		return for_;
	}

	public void setFor_(String for_) {
		this.for_ = for_;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getBrandstrength() {
		return brandstrength;
	}

	public void setBrandstrength(String brandstrength) {
		this.brandstrength = brandstrength;
	}

	public String getYesno() {
		return yesno;
	}

	public void setYesno(String yesno) {
		this.yesno = yesno;
	}

	public String getLiquorsubCategory() {
		return liquorsubCategory;
	}

	public void setLiquorsubCategory(String liquorsubCategory) {
		this.liquorsubCategory = liquorsubCategory;
	}

	public String getLabelchallan() {
		return labelchallan;
	}

	public void setLabelchallan(String labelchallan) {
		this.labelchallan = labelchallan;
	}

	public String getBrndchallan() {
		return brndchallan;
	}

	public void setBrndchallan(String brndchallan) {
		this.brndchallan = brndchallan;
	}
	
	
	
	private String feeflg="N";


	public String getFeeflg() {
		return feeflg;
	}

	public void setFeeflg(String feeflg) {
		this.feeflg = feeflg;
	}
	// ------------------revert comment-----------------

		private String acRevertComment;
		private String rvrtCmntPopup;
		private boolean revertFlg;

		public boolean isRevertFlg() {
			return revertFlg;
		}

		public void setRevertFlg(boolean revertFlg) {
			this.revertFlg = revertFlg;
		}

		public String getAcRevertComment() {
			return acRevertComment;
		}

		public void setAcRevertComment(String acRevertComment) {
			this.acRevertComment = acRevertComment;
		}

		public String getRvrtCmntPopup() {
			return rvrtCmntPopup;
		}

		public void setRvrtCmntPopup(String rvrtCmntPopup) {
			this.rvrtCmntPopup = rvrtCmntPopup;
		}

		public void saveRvrtCmnt() {

			try {
				if (this.rvrtCmntPopup != null && this.rvrtCmntPopup.length() > 0) {
					impl.saveRvrtCmntImpl(this);
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Please Fill Comment !!",
									"Please Fill Comment !!"));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		private String user4Remark_dtrevert;




		public String getUser4Remark_dtrevert() {
			return user4Remark_dtrevert;
		}

		public void setUser4Remark_dtrevert(String user4Remark_dtrevert) {
			this.user4Remark_dtrevert = user4Remark_dtrevert;
		}
		
		private boolean reverflg;
		
		
		public boolean isReverflg() {
			return reverflg;
		}

		public void setReverflg(boolean reverflg) {
			this.reverflg = reverflg;
		}
		private String typee;


		public String getTypee() {
			return typee;
		}

		public void setTypee(String typee) {
			this.typee = typee;
		}
	private String bwfl_yes_no;

	public String getBwfl_yes_no() {
		return bwfl_yes_no;
	}

	public void setBwfl_yes_no(String bwfl_yes_no) {
		this.bwfl_yes_no = bwfl_yes_no;
	}
	
	
	brand_label_tracking_dt dt;

	public brand_label_tracking_dt getDt() {
		return dt;
	}

	public void setDt(brand_label_tracking_dt dt) {
		this.dt = dt;
	}
	
	
	public void updatesubtype() {
		try{

		impl.updatesubtype(this,dt.getSubtype(),dt.getBrandid(),dt.getBrandName_dt(),dt.getBrndstrength());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

	}
	public void reopen(ActionEvent e) {

		try{
		UIDataTable uiTable = (UIDataTable) e.getComponent().getParent().getParent();
		brand_label_tracking_dt dt = (brand_label_tracking_dt) this.getDisplayRegUsers().get(uiTable.getRowIndex());

		impl.reopen(this,dt);
		//this.displayRegUsers = impl.displayRegUsersImpl(this);

		}catch(Exception ex){
		ex.printStackTrace();
		}

		}
///================================================
	
	private String brnd_reg_in_20_21;



	public String getBrnd_reg_in_20_21() {
		return brnd_reg_in_20_21;
	}

	public void setBrnd_reg_in_20_21(String brnd_reg_in_20_21) {
		this.brnd_reg_in_20_21 = brnd_reg_in_20_21;
	}
	
///==========================================================
	
	
	public void delete_brand() {
		try{

		impl.deletebrand(this,dt.getSubtype(),dt.getBrandid(),dt.getBrandName_dt(),dt.getBrndstrength());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

	}
	
	public void delete_package() {
		try{

		impl.deletepackage(this,dt.getPckgID_dt(),dt.getBrandid(),dt.getBrandName_dt(),dt.getBrndstrength());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

	}
	
	private String delete_brand_pack;

	public String getDelete_brand_pack() {
		return delete_brand_pack;
	}

	public void setDelete_brand_pack(String delete_brand_pack) {
		this.delete_brand_pack = delete_brand_pack;
	}
	
	
//===================Aman
	
	private boolean tableflag = true;
	
	private boolean panelflag ;
	
	
	public boolean isTableflag() {
		return tableflag;
	}

	public void setTableflag(boolean tableflag) {
		this.tableflag = tableflag;
	}

	public boolean isPanelflag() {
		return panelflag;
	}

	public void setPanelflag(boolean panelflag) {
		this.panelflag = panelflag;
	}
	private String order_no ;

	
	
    public void close()
	 
	 {
		  this.tableflag = true;
		  this.panelflag = false; 
	 }				

    public void reset() {

		this.order_no=null;
	}
    
    public Date order_date ;

    public String order_no_view ;

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public String getOrder_no_view() {
		return order_no_view;
	}

	public void setOrder_no_view(String order_no_view) {
		this.order_no_view = order_no_view;
	}


	public void viewDetails() 
	
	{
	      try
        
		 {     
	    	  if(impl.displayRegUsersImpl(this))	 
					{
						this.tableflag = false;
						this.panelflag = true;
						this.displayLabelDetails = impl.displayLabelDetailsImpl(this);	
						this.showUploadedLabels = impl.getUploadedLabels(this);
						this.brandChallaList = impl.getbrandChallanList(this);
						this.labelChallanList= impl.getlabelChallanList(this);
						impl.fee(this,dt.getAppID_dt());	
						impl.getdata(this);
			           
					}
		 }
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
}	

}
