package com.mentor.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.UIDataTable;

import com.mentor.datatable.Fl2a_csd_permit_tracking_dt;
import com.mentor.impl.Fl2a_csd_permit_tracking_impl;



public class Fl2a_csd_permit_tracking_Action {
	


	Fl2a_csd_permit_tracking_impl impl = new Fl2a_csd_permit_tracking_impl();

	private String hidden;
	private String radioType = "N";
	private int bwflId;
	private int unitID;
	private String bwflName;
	private String licenseType;
	private String licenseNmbr;
	private String appDate;
	private int districtId;
	private String districtName;
	private int requestID;
	private int totalBoxes;
	private int totalBotls;
	private String parentUnitNm;
	private String parentUnitAdrs;
	private String parentUnitState;
	private boolean hideDataTable = true;
	private boolean viewPanelFlg;
	private String fillRemrks;
	private String deoRemrks;
	private double totalDuty;
	private double totalAddDuty;
	private double totalImportFee;
	private double totalSpecialFee;
	private double balRgstrDuty;
	private double balRgstrImportFee;
	private double balRgstrSpecialFee;
	private String byRoad_byRoute;
	private String routeDetail;
	private String validUptoDt;
	private Date fillValidDt;
	private String mapped_unmapped;
	private ArrayList displayRegUsers = new ArrayList();
	private ArrayList displayBrandDetails = new ArrayList();
	private String csd_name;
	private String csd_address;
	private String brew_dist_name;
	private String brew_dist_addres;
	private String excDutyChallanNo;
	private boolean flag;
	private ArrayList registerList = new ArrayList();
	private boolean registerFlag;
	private double total_deposit_amt;
	private double total_permit_amt;
	private double total_diff;
	private boolean approveFlag;

	
	
	public boolean isApproveFlag() {
		return approveFlag;
	}

	public void setApproveFlag(boolean approveFlag) {
		this.approveFlag = approveFlag;
	}

	public double getTotal_deposit_amt() {
		return total_deposit_amt;
	}

	public void setTotal_deposit_amt(double total_deposit_amt) {
		this.total_deposit_amt = total_deposit_amt;
	}

	public double getTotal_permit_amt() {
		return total_permit_amt;
	}

	public void setTotal_permit_amt(double total_permit_amt) {
		this.total_permit_amt = total_permit_amt;
	}

	public double getTotal_diff() {
		return total_diff;
	}

	public void setTotal_diff(double total_diff) {
		this.total_diff = total_diff;
	}

	public boolean isRegisterFlag() {
		return registerFlag;
	}

	public void setRegisterFlag(boolean registerFlag) {
		this.registerFlag = registerFlag;
	}

	public ArrayList getRegisterList() {
		return registerList;
	}

	public void setRegisterList(ArrayList registerList) {
		this.registerList = registerList;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getExcDutyChallanNo() {
		return excDutyChallanNo;
	}

	public void setExcDutyChallanNo(String excDutyChallanNo) {
		this.excDutyChallanNo = excDutyChallanNo;
	}

	public String getCsd_name() {
		return csd_name;
	}

	public void setCsd_name(String csd_name) {
		this.csd_name = csd_name;
	}

	public String getCsd_address() {
		return csd_address;
	}

	public void setCsd_address(String csd_address) {
		this.csd_address = csd_address;
	}

	public String getBrew_dist_name() {
		return brew_dist_name;
	}

	public void setBrew_dist_name(String brew_dist_name) {
		this.brew_dist_name = brew_dist_name;
	}

	public String getBrew_dist_addres() {
		return brew_dist_addres;
	}

	public void setBrew_dist_addres(String brew_dist_addres) {
		this.brew_dist_addres = brew_dist_addres;
	}

	public Date getFillValidDt() {
		return fillValidDt;
	}

	public void setFillValidDt(Date fillValidDt) {
		this.fillValidDt = fillValidDt;
	}

	public String getHidden() {
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

	public int getBwflId() {
		return bwflId;
	}

	public void setBwflId(int bwflId) {
		this.bwflId = bwflId;
	}

	public int getUnitID() {
		return unitID;
	}

	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}

	public String getBwflName() {
		return bwflName;
	}

	public void setBwflName(String bwflName) {
		this.bwflName = bwflName;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getLicenseNmbr() {
		return licenseNmbr;
	}

	public void setLicenseNmbr(String licenseNmbr) {
		this.licenseNmbr = licenseNmbr;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public int getTotalBoxes() {
		return totalBoxes;
	}

	public void setTotalBoxes(int totalBoxes) {
		this.totalBoxes = totalBoxes;
	}

	public int getTotalBotls() {
		return totalBotls;
	}

	public void setTotalBotls(int totalBotls) {
		this.totalBotls = totalBotls;
	}

	public String getParentUnitNm() {
		return parentUnitNm;
	}

	public void setParentUnitNm(String parentUnitNm) {
		this.parentUnitNm = parentUnitNm;
	}

	public String getParentUnitAdrs() {
		return parentUnitAdrs;
	}

	public void setParentUnitAdrs(String parentUnitAdrs) {
		this.parentUnitAdrs = parentUnitAdrs;
	}

	public String getParentUnitState() {
		return parentUnitState;
	}

	public void setParentUnitState(String parentUnitState) {
		this.parentUnitState = parentUnitState;
	}

	public boolean isHideDataTable() {
		return hideDataTable;
	}

	public void setHideDataTable(boolean hideDataTable) {
		this.hideDataTable = hideDataTable;
	}

	public boolean isViewPanelFlg() {
		return viewPanelFlg;
	}

	public void setViewPanelFlg(boolean viewPanelFlg) {
		this.viewPanelFlg = viewPanelFlg;
	}

	public String getFillRemrks() {
		return fillRemrks;
	}

	public void setFillRemrks(String fillRemrks) {
		this.fillRemrks = fillRemrks;
	}

	public String getDeoRemrks() {
		return deoRemrks;
	}

	public void setDeoRemrks(String deoRemrks) {
		this.deoRemrks = deoRemrks;
	}

	public double getTotalDuty() {
		return totalDuty;
	}

	public void setTotalDuty(double totalDuty) {
		this.totalDuty = totalDuty;
	}

	public double getTotalAddDuty() {
		return totalAddDuty;
	}

	public void setTotalAddDuty(double totalAddDuty) {
		this.totalAddDuty = totalAddDuty;
	}

	public double getTotalImportFee() {
		return totalImportFee;
	}

	public void setTotalImportFee(double totalImportFee) {
		this.totalImportFee = totalImportFee;
	}

	public double getTotalSpecialFee() {
		return totalSpecialFee;
	}

	public void setTotalSpecialFee(double totalSpecialFee) {
		this.totalSpecialFee = totalSpecialFee;
	}

	public double getBalRgstrDuty() {
		return balRgstrDuty;
	}

	public void setBalRgstrDuty(double balRgstrDuty) {
		this.balRgstrDuty = balRgstrDuty;
	}

	public double getBalRgstrImportFee() {
		return balRgstrImportFee;
	}

	public void setBalRgstrImportFee(double balRgstrImportFee) {
		this.balRgstrImportFee = balRgstrImportFee;
	}

	public double getBalRgstrSpecialFee() {
		return balRgstrSpecialFee;
	}

	public void setBalRgstrSpecialFee(double balRgstrSpecialFee) {
		this.balRgstrSpecialFee = balRgstrSpecialFee;
	}

	public String getByRoad_byRoute() {
		return byRoad_byRoute;
	}

	public void setByRoad_byRoute(String byRoad_byRoute) {
		this.byRoad_byRoute = byRoad_byRoute;
	}

	public String getRouteDetail() {
		return routeDetail;
	}

	public void setRouteDetail(String routeDetail) {
		this.routeDetail = routeDetail;
	}

	public String getValidUptoDt() {
		return validUptoDt;
	}

	public void setValidUptoDt(String validUptoDt) {
		this.validUptoDt = validUptoDt;
	}

	public String getMapped_unmapped() {
		return mapped_unmapped;
	}

	public void setMapped_unmapped(String mapped_unmapped) {
		this.mapped_unmapped = mapped_unmapped;
	}

	private boolean listFlagForPrint = true;
	

	public boolean isListFlagForPrint() {
		return listFlagForPrint;
	}

	public void setListFlagForPrint(boolean listFlagForPrint) {
		this.listFlagForPrint = listFlagForPrint;
	}

	/*public ArrayList getDisplayRegUsers() {
		try {
			if (this.radioType != null) {
				if(this.listFlagForPrint == true)
				{
				this.displayRegUsers = impl.displayRegUsersImpl(this);
				this.listFlagForPrint = false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return displayRegUsers;
	}*/

	public void setDisplayRegUsers(ArrayList displayRegUsers) {
		this.displayRegUsers = displayRegUsers;
	}

	public ArrayList getDisplayBrandDetails() {
		return displayBrandDetails;
	}

	public void setDisplayBrandDetails(ArrayList displayBrandDetails) {
		this.displayBrandDetails = displayBrandDetails;
	}

	public void radioListener(ValueChangeEvent e) {

		try {
			String val = (String) e.getNewValue();
			this.viewPanelFlg = false;
			this.listFlagForPrint = true;
			this.setRadioType(val);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	
	private String consignorNmAdrs;

	public String getConsignorNmAdrs() {
		return consignorNmAdrs;
	}

	public void setConsignorNmAdrs(String consignorNmAdrs) {
		this.consignorNmAdrs = consignorNmAdrs;
	}
    public String permit_no ;
	
	public String getPermit_no() {
		return permit_no;
	}

	public void setPermit_no(String permit_no) {
		this.permit_no = permit_no;
	}

	/*public void viewDetails(ActionEvent e) {
		try {
			UIDataTable uiTable = (UIDataTable) e.getComponent().getParent()
					.getParent();
			Fl2a_csd_permit_tracking_dt dt = (Fl2a_csd_permit_tracking_dt) this.getDisplayRegUsers()
					.get(uiTable.getRowIndex());

			this.hideDataTable = false;
			this.viewPanelFlg = true;

			this.setAppId(dt.getAppId_dt());
			this.setRequestID(dt.getRequestID_dt());
			this.setUnitID(dt.getUnitID_dt());
			this.setBwflId(dt.getBwflID_dt());
			this.setAppDate(dt.getAppDate_dt());
			this.setBwflName(dt.getBwflName_dt());
			this.setDistrictId(dt.getDistrictId_dt());
			this.setDistrictName(dt.getDistrictName_dt());
			this.setParentUnitNm(dt.getParentUnitNm_dt());
			this.setParentUnitAdrs(dt.getParentUnitAdrs_dt());
			this.setParentUnitState(dt.getParentUnitState_dt());
			this.setLicenseType(dt.getLicenseType_dt());
			this.setLicenseNmbr(dt.getLicenseNmbr_dt());
			this.setMapped_unmapped(dt.getMapped_unmapped_dt());
			this.setByRoad_byRoute(dt.getByRoad_byRoute_dt());
			this.setRouteDetail(dt.getRouteDetail_dt());
			this.setValidUptoDt(dt.getValidUptoDt_dt());
			this.setDeoRemrks(dt.getDeoRemark_dt());
			this.setLoginType(dt.getLoginType_dt());
			this.setCrDate(dt.getCrDate_dt());
			this.setPermitNmbr(dt.getPermitNmbr_dt());
			this.setApprovalDate(dt.getApprovalDate_dt());
			this.setApprovalTym(dt.getApprovalTym_dt());
			this.setApprovalUser(dt.getApprovalUser_dt());
			this.setImportFeeChalanNo(dt.getImportFeeChalanNo_dt());
			this.setSpclFeeChalanNo(dt.getSpclFeeChalanNo_dt());
			this.setConsignorNmAdrs(dt.getConsignorNmAdrs_dt());
			this.setExcDutyChallanNo(dt.getExcDutyChallanNo_dt());
			if(dt.getUnitName_dt().equalsIgnoreCase("Distillery") || dt.getUnitName_dt().equalsIgnoreCase("Brewery")){
				this.flag=true;
			}else{
				this.flag=false;
			}
			
			if(dt.getLoginType_dt().equalsIgnoreCase("FL2D")){
				this.setFl2dDutyFlg(true);
			}else{
				this.setFl2dDutyFlg(false);
			}
			if(dt.getImportFeeChalanNo_dt().trim().replaceAll("-", "").equalsIgnoreCase("Adjust From Advanced Register")){
				this.registerFlag = true;
				this.registerList = impl.getRegisterDetail(this, dt.getBwflID_dt());
			}else{
				this.registerFlag = false;
			}

			//impl.getAdvancRgstrBalance(this);
			impl.getCsdDetail(this);
			this.displayBrandDetails = impl.displayBrandDetailsImpl(this);
			this.displayChalanDetails = impl.displayChalanDetailsImpl(this,dt);
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}*/

	public String approveApplication() {
		try {
			
				String st = this.appDate;
				Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(st);
			
				if (this.fillRemrks != null && this.fillRemrks.length() > 0 && this.fillValidDt != null) {
					if (this.fillValidDt.after(date1) || this.fillValidDt.compareTo(date1)==0 ) {
					
					impl.approveApplicationImpl(this);
					
					 }else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Validity Date Should Be greater than or equal to application date !!"," Validity Date Should Be greater than or equal to application date!!"));
				}}else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Please Fill Remarks And Date!!","Please Fill Remarks And Date!!"));
				}
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	public String rejectApplication() {
		try {

			if (this.fillRemrks != null && this.fillRemrks.length() > 0) {
				if(this.radioType.equalsIgnoreCase("N")){
					impl.rejectApplicationImpl(this);
				}
				else if(this.radioType.equalsIgnoreCase("A")){
					impl.rejectApprovedApplication(this);
				}
				
				this.registerFlag=false;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Please Fill Remarks!!","Please Fill Remarks!!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	//-------------------print permit-----------------
	
	/*public void printReport(ActionEvent e) {

		UIDataTable uiTable = (UIDataTable) e.getComponent().getParent().getParent();
		Fl2a_csd_permit_tracking_dt dt = (Fl2a_csd_permit_tracking_dt) this.getDisplayRegUsers().get(uiTable.getRowIndex());
		
		this.setBwflId(dt.getBwflID_dt());
		this.setRequestID(dt.getRequestID_dt());
		//impl.getAdvancRgstrBalanceForPrint(this);

		
			if (impl.printReportFL2D(this, dt) == true) {
				dt.setPrintFlag(true);
				this.displayRegUsers = impl.displayRegUsersImpl(this);
			} else {
				dt.setPrintFlag(false);
				

			}
		
		

	}*/

	// ----------------------close application-----------------------

	public void closeApplication() {

		//this.displayRegUsers = impl.displayRegUsersImpl(this);
		this.approveFlag=false;
		this.viewPanelFlg = false;
		this.hideDataTable = true;
		this.listFlagForPrint = true;
		this.fillRemrks = null;
		this.licenseType = null;
		this.totalAddDuty = 0.0;
		this.totalDuty = 0.0;
		this.totalImportFee = 0.0;
		this.totalSpecialFee = 0.0;
		this.balRgstrDuty = 0.0;
		this.balRgstrImportFee = 0.0;
		this.balRgstrSpecialFee = 0.0;
		this.balFL2DImportFee = 0.0;
		this.balFL2DSpecialFee = 0.0;
		this.totalBoxes = 0;
		this.totalBotls = 0;
		this.bwflId = 0;
		this.requestID = 0;
		this.unitID = 0;
		this.bwflName = null;
		this.appDate = null;
		this.districtId = 0;
		this.districtName = null;
		this.parentUnitAdrs = null;
		this.parentUnitNm = null;
		this.parentUnitState = null;
		this.licenseType = null;
		this.licenseNmbr = null;
		this.validUptoDt = null;
		this.byRoad_byRoute = null;
		this.mapped_unmapped = null;
		this.routeDetail = null;
		this.deoRemrks = null;
		this.displayBrandDetails.clear();
		this.displayChalanDetails.clear();
		this.loginType = null;
		this.crDate = null;
		this.permitNmbr = null;
		this.approvalDate = null;
		this.approvalTym = null;
		this.approvalUser = null;
		this.importFeeChalanNo = null;
		this.spclFeeChalanNo = null;
		this.appId = 0;
		this.fillValidDt = null;
		this.checkBox = false;
		this.registerFlag = false;


	}
	
	private boolean fl2dDutyFlg;
	private String loginType;
	private Date crDate;
	private String permitNmbr;
	private Date approvalDate;
	private String approvalTym;
	private String approvalUser;
	private double balFL2DImportFee;
	private double balFL2DSpecialFee;
	private boolean checkBox=true;
	private ArrayList displayChalanDetails = new ArrayList();
	private String importFeeChalanNo;
	private String spclFeeChalanNo;
	private int appId;

	public boolean isFl2dDutyFlg() {
		return fl2dDutyFlg;
	}

	public void setFl2dDutyFlg(boolean fl2dDutyFlg) {
		this.fl2dDutyFlg = fl2dDutyFlg;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public Date getCrDate() {
		return crDate;
	}

	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}

	public String getPermitNmbr() {
		return permitNmbr;
	}

	public void setPermitNmbr(String permitNmbr) {
		this.permitNmbr = permitNmbr;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getApprovalTym() {
		return approvalTym;
	}

	public void setApprovalTym(String approvalTym) {
		this.approvalTym = approvalTym;
	}

	public String getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	public double getBalFL2DImportFee() {
		return balFL2DImportFee;
	}

	public void setBalFL2DImportFee(double balFL2DImportFee) {
		this.balFL2DImportFee = balFL2DImportFee;
	}

	public double getBalFL2DSpecialFee() {
		return balFL2DSpecialFee;
	}

	public void setBalFL2DSpecialFee(double balFL2DSpecialFee) {
		this.balFL2DSpecialFee = balFL2DSpecialFee;
	}

	public boolean isCheckBox() {
		return checkBox;
	}

	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}

	public ArrayList getDisplayChalanDetails() {
		return displayChalanDetails;
	}

	public void setDisplayChalanDetails(ArrayList displayChalanDetails) {
		this.displayChalanDetails = displayChalanDetails;
	}

	public String getImportFeeChalanNo() {
		return importFeeChalanNo;
	}

	public void setImportFeeChalanNo(String importFeeChalanNo) {
		this.importFeeChalanNo = importFeeChalanNo;
	}

	public String getSpclFeeChalanNo() {
		return spclFeeChalanNo;
	}

	public void setSpclFeeChalanNo(String spclFeeChalanNo) {
		this.spclFeeChalanNo = spclFeeChalanNo;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}
	
	
	private boolean showMainPanel_flg=false;
	private String popupPermitNmbr;
	
	public boolean isShowMainPanel_flg() {
		return showMainPanel_flg;
	}

	public void setShowMainPanel_flg(boolean showMainPanel_flg) {
		this.showMainPanel_flg = showMainPanel_flg;
	}
	

	public String getPopupPermitNmbr() {
		return popupPermitNmbr;
	}

	public void setPopupPermitNmbr(String popupPermitNmbr) {
		this.popupPermitNmbr = popupPermitNmbr;
	}

	public void closePopup(){
		this.showMainPanel_flg = false;
		this.popupPermitNmbr = null;
		this.registerFlag = false;
	}
	
   public void print(ActionEvent e){
	   UIDataTable uiTable = (UIDataTable) e.getComponent().getParent().getParent();
		Fl2a_csd_permit_tracking_dt dt = (Fl2a_csd_permit_tracking_dt) this.getDisplayChalanDetails().get(uiTable.getRowIndex());
		
		impl.generateChallan(this, dt);
   }
	
  /* public void viewRegister(ActionEvent e){
	   
	    UIDataTable uiTable = (UIDataTable) e.getComponent().getParent()
				.getParent();
		Fl2a_csd_permit_tracking_dt dt = (Fl2a_csd_permit_tracking_dt) this.getDisplayRegUsers().get(uiTable.getRowIndex());
		 this.registerFlag=true;
	    this.registerList=impl.getRegisterDetail(this, dt.getBwflID_dt());
   }
*/
//===================================
    private double balance;
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getImport_fee() {
		return import_fee;
	}

	public void setImport_fee(double import_fee) {
		this.import_fee = import_fee;
	}

	public double getExcise_duty() {
		return excise_duty;
	}

	public void setExcise_duty(double excise_duty) {
		this.excise_duty = excise_duty;
	}

	public double getSpecial_fee() {
		return special_fee;
	}

	public void setSpecial_fee(double special_fee) {
		this.special_fee = special_fee;
	}

	public double getScanning_fee() {
		return scanning_fee;
	}

	public void setScanning_fee(double scanning_fee) {
		this.scanning_fee = scanning_fee;
	}

	public double getConsideration_fee() {
		return consideration_fee;
	}

	public void setConsideration_fee(double consideration_fee) {
		this.consideration_fee = consideration_fee;
	}

	public double getPermit_balance() {
		return permit_balance;
	}

	public void setPermit_balance(double permit_balance) {
		this.permit_balance = permit_balance;
	}

	private double import_fee;
	private double excise_duty;
	private double special_fee;
	private double scanning_fee;
	private double consideration_fee;
    private double permit_balance;
   public void viewDetails() {
	   
	    try
       {
		if(impl.checkpermit(this))
		{
			this.hideDataTable = false;
			this.viewPanelFlg = true;
            this.displayBrandDetails = impl.displayBrandDetailsImpl(this);
			this.displayChalanDetails = impl.displayChalanDetailsImpl(this);
		}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	

}
public String unit_type ;

public String getUnit_type() {
	return unit_type;
}

public void setUnit_type(String unit_type) {
	this.unit_type = unit_type;
}

public void reset()
   {
   this.permit_no=null;	
   }
   
   public void close()
	 
	 {
		 
		 this.hideDataTable = true;
		 this.viewPanelFlg = false; 
		 
	 }
   
   private int bwflID;

private Date permit_date ;

public Date getPermit_date() {
	return permit_date;
}

public void setPermit_date(Date permit_date) {
	this.permit_date = permit_date;
}

public String getPermit_view() {
	return permit_view;
}

public void setPermit_view(String permit_view) {
	this.permit_view = permit_view;
}

private String permit_view ;

public int getBwflID() {
	return bwflID;
}

public void setBwflID(int bwflID) {
	this.bwflID = bwflID;
}
}
