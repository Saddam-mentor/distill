package com.mentor.action;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.UIDataTable;



import com.mentor.impl.Fl2d_permit_tracking_impl;

import com.mentor.utility.Validate;

public class Fl2d_permit_tracking_action {

	Fl2d_permit_tracking_impl impl = new Fl2d_permit_tracking_impl();

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
	private int custom_id; 
	private String scanning_fee_challan_no;
	private double total_scanning_fee;
	
	public double getTotal_scanning_fee() {
		return total_scanning_fee;
	}

	public void setTotal_scanning_fee(double total_scanning_fee) {
		this.total_scanning_fee = total_scanning_fee;
	}

	public String getScanning_fee_challan_no() {
		return scanning_fee_challan_no;
	}

	public void setScanning_fee_challan_no(String scanning_fee_challan_no) {
		this.scanning_fee_challan_no = scanning_fee_challan_no;
	}

	private ArrayList displayRegUsers = new ArrayList();
	private ArrayList displayBrandDetails = new ArrayList();
	

	
	
	public int getCustom_id() {
		return custom_id;
	}

	public void setCustom_id(int custom_id) {
		this.custom_id = custom_id;
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

    public void viewDetails() {
    	
        try
        {
		if(impl.checkpermit(this))
		{
			this.hideDataTable = false;
			this.viewPanelFlg = true;
           // this.displayRegUsers = impl.displayRegUsersImpl(this);
			this.displayBrandDetails = impl.displayBrandDetailsImpl(this);
			this.displayChalanDetails = impl.displayChalanDetailsImpl(this);
		}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	

}
	
public void close()

{
 
 this.hideDataTable = true;
 this.viewPanelFlg = false; 
 
}

private String permit_no ;

public String getPermit_no() {
	return permit_no;
}

public void setPermit_no(String permit_no) {
	this.permit_no = permit_no;
}

public void reset()
{
this.permit_no=null;	
}
public Date PermitDate ;

public Date getPermitDate() {
	return PermitDate;
}

public void setPermitDate(Date permitDate) {
	PermitDate = permitDate;
}
	/*public String approveApplication() {
		try {
			
				String st = this.appDate;
				Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(st);
			
				if (this.fillRemrks != null && this.fillRemrks.length() > 0 && this.fillValidDt != null) {
					if (this.fillValidDt.after(date1) || this.fillValidDt.compareTo(date1)==0 ) {
					if (this.checkBox == true) {
					impl.approveApplicationImpl(this);
					} else {
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Make Sure Challan Verified and Certified !!","Make Sure Challan Verified and Certified !!"));
				} }else {
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

	}*/

	public String rejectApplication() {
		try {

			if (this.fillRemrks != null && this.fillRemrks.length() > 0) {
				if(this.radioType.equalsIgnoreCase("N")){
					
						impl.rejectApplicationImpl(this);
					
				}
				else if(this.radioType.equalsIgnoreCase("A")){
					impl.rejectApprovedApplication(this);
				}
				
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
	
	/*public void printReport(ActionEvent e) throws ParseException {

		UIDataTable uiTable = (UIDataTable) e.getComponent().getParent().getParent();
		Fl2d_permit_tracking_dt dt = (Fl2d_permit_tracking_dt) this.getDisplayRegUsers().get(uiTable.getRowIndex());
		
		this.setBwflId(dt.getBwflID_dt());
		this.setRequestID(dt.getRequestID_dt());
		//impl.getAdvancRgstrBalanceForPrint(this);

		if(dt.getLoginType_dt().equalsIgnoreCase("BWFL")){
			if (impl.printReportBWFL(this, dt) == true) {
				dt.setPrintFlag(true);
				this.displayRegUsers = impl.displayRegUsersImpl(this);
			} else {
				dt.setPrintFlag(false);
				

			}
		}else if(dt.getLoginType_dt().equalsIgnoreCase("FL2D")){
			if (impl.printReportFL2D(this, dt) == true) {
				dt.setPrintFlag(true);
				this.displayRegUsers = impl.displayRegUsersImpl(this);
			} else {
				dt.setPrintFlag(false);
				

			}
		}
		

	}*/

	// ----------------------close application-----------------------

	public void closeApplication() {

		//this.displayRegUsers = impl.displayRegUsersImpl(this);
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
	private boolean checkBox;
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
	}
	

}
