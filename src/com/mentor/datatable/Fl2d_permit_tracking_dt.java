package com.mentor.datatable;

import java.util.Date;

public class Fl2d_permit_tracking_dt {

	private int srNo;
	private String appDate_dt;
	private int bwflID_dt;
	private int unitID_dt;
	private int requestID_dt;
	private String bwflName_dt;
	private String licenseNmbr_dt;
	private String licenseType_dt;
	private String status_dt;
	private String deoRemark_dt;
	private int districtId_dt;
	private String districtName_dt;
	private String parentUnitNm_dt;
	private String parentUnitAdrs_dt;
	private String parentUnitState_dt;
	private int totalBoxes_dt;
	private String byRoad_byRoute_dt;
	private String routeDetail_dt;
	private String validUptoDt_dt;
	private String mapped_unmapped_dt;
	private int custom_id; 
	private String scanning_fee_challan_no;
    private double scanning_fee;
    
    
	public double getScanning_fee() {
		return scanning_fee;
	}

	public void setScanning_fee(double scanning_fee) {
		this.scanning_fee = scanning_fee;
	}

	public String getScanning_fee_challan_no() {
		return scanning_fee_challan_no;
	}

	public void setScanning_fee_challan_no(String scanning_fee_challan_no) {
		this.scanning_fee_challan_no = scanning_fee_challan_no;
	}

	public int getCustom_id() {
		return custom_id;
	}

	public void setCustom_id(int custom_id) {
		this.custom_id = custom_id;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public int getUnitID_dt() {
		return unitID_dt;
	}

	public void setUnitID_dt(int unitID_dt) {
		this.unitID_dt = unitID_dt;
	}

	public int getTotalBoxes_dt() {
		return totalBoxes_dt;
	}

	public void setTotalBoxes_dt(int totalBoxes_dt) {
		this.totalBoxes_dt = totalBoxes_dt;
	}

	public String getAppDate_dt() {
		return appDate_dt;
	}

	public void setAppDate_dt(String appDate_dt) {
		this.appDate_dt = appDate_dt;
	}

	public int getBwflID_dt() {
		return bwflID_dt;
	}

	public void setBwflID_dt(int bwflID_dt) {
		this.bwflID_dt = bwflID_dt;
	}

	public int getRequestID_dt() {
		return requestID_dt;
	}

	public void setRequestID_dt(int requestID_dt) {
		this.requestID_dt = requestID_dt;
	}

	public String getBwflName_dt() {
		return bwflName_dt;
	}

	public void setBwflName_dt(String bwflName_dt) {
		this.bwflName_dt = bwflName_dt;
	}

	public String getLicenseNmbr_dt() {
		return licenseNmbr_dt;
	}

	public void setLicenseNmbr_dt(String licenseNmbr_dt) {
		this.licenseNmbr_dt = licenseNmbr_dt;
	}

	public String getLicenseType_dt() {
		return licenseType_dt;
	}

	public void setLicenseType_dt(String licenseType_dt) {
		this.licenseType_dt = licenseType_dt;
	}

	public String getStatus_dt() {
		return status_dt;
	}

	public void setStatus_dt(String status_dt) {
		this.status_dt = status_dt;
	}

	public String getDeoRemark_dt() {
		return deoRemark_dt;
	}

	public void setDeoRemark_dt(String deoRemark_dt) {
		this.deoRemark_dt = deoRemark_dt;
	}

	public int getDistrictId_dt() {
		return districtId_dt;
	}

	public void setDistrictId_dt(int districtId_dt) {
		this.districtId_dt = districtId_dt;
	}

	public String getDistrictName_dt() {
		return districtName_dt;
	}

	public void setDistrictName_dt(String districtName_dt) {
		this.districtName_dt = districtName_dt;
	}

	public String getParentUnitNm_dt() {
		return parentUnitNm_dt;
	}

	public void setParentUnitNm_dt(String parentUnitNm_dt) {
		this.parentUnitNm_dt = parentUnitNm_dt;
	}

	public String getParentUnitAdrs_dt() {
		return parentUnitAdrs_dt;
	}

	public void setParentUnitAdrs_dt(String parentUnitAdrs_dt) {
		this.parentUnitAdrs_dt = parentUnitAdrs_dt;
	}

	public String getParentUnitState_dt() {
		return parentUnitState_dt;
	}

	public void setParentUnitState_dt(String parentUnitState_dt) {
		this.parentUnitState_dt = parentUnitState_dt;
	}

	public String getByRoad_byRoute_dt() {
		return byRoad_byRoute_dt;
	}

	public void setByRoad_byRoute_dt(String byRoad_byRoute_dt) {
		this.byRoad_byRoute_dt = byRoad_byRoute_dt;
	}

	public String getRouteDetail_dt() {
		return routeDetail_dt;
	}

	public void setRouteDetail_dt(String routeDetail_dt) {
		this.routeDetail_dt = routeDetail_dt;
	}

	public String getValidUptoDt_dt() {
		return validUptoDt_dt;
	}

	public void setValidUptoDt_dt(String validUptoDt_dt) {
		this.validUptoDt_dt = validUptoDt_dt;
	}

	public String getMapped_unmapped_dt() {
		return mapped_unmapped_dt;
	}

	public void setMapped_unmapped_dt(String mapped_unmapped_dt) {
		this.mapped_unmapped_dt = mapped_unmapped_dt;
	}

	// ==============brand details===================

	private int brandId_dt;
	private String brandName_dt;
	private int size_dt;
	private String etinNo_dt;
	private int pckgID_dt;
	private String pckgType_dt;
	private int nmbrOfBox_dt;
	private int nmbrOfBtl_dt;
	private double importFees_dt = 0.0;
	private double duty_dt = 0.0;
	private double specialfees_dt = 0.0;
	private double addDuty_dt = 0.0;
	private int quantity_dt;

	public int getBrandId_dt() {
		return brandId_dt;
	}

	public void setBrandId_dt(int brandId_dt) {
		this.brandId_dt = brandId_dt;
	}

	public String getBrandName_dt() {
		return brandName_dt;
	}

	public void setBrandName_dt(String brandName_dt) {
		this.brandName_dt = brandName_dt;
	}

	public int getSize_dt() {
		return size_dt;
	}

	public void setSize_dt(int size_dt) {
		this.size_dt = size_dt;
	}

	public String getEtinNo_dt() {
		return etinNo_dt;
	}

	public void setEtinNo_dt(String etinNo_dt) {
		this.etinNo_dt = etinNo_dt;
	}

	public int getPckgID_dt() {
		return pckgID_dt;
	}

	public void setPckgID_dt(int pckgID_dt) {
		this.pckgID_dt = pckgID_dt;
	}

	public String getPckgType_dt() {
		return pckgType_dt;
	}

	public void setPckgType_dt(String pckgType_dt) {
		this.pckgType_dt = pckgType_dt;
	}

	public int getNmbrOfBox_dt() {
		return nmbrOfBox_dt;
	}

	public void setNmbrOfBox_dt(int nmbrOfBox_dt) {
		this.nmbrOfBox_dt = nmbrOfBox_dt;
	}

	public int getNmbrOfBtl_dt() {
		return nmbrOfBtl_dt;
	}

	public void setNmbrOfBtl_dt(int nmbrOfBtl_dt) {
		this.nmbrOfBtl_dt = nmbrOfBtl_dt;
	}

	public double getImportFees_dt() {
		return importFees_dt;
	}

	public void setImportFees_dt(double importFees_dt) {
		this.importFees_dt = importFees_dt;
	}

	public double getDuty_dt() {
		return duty_dt;
	}

	public void setDuty_dt(double duty_dt) {
		this.duty_dt = duty_dt;
	}

	public double getSpecialfees_dt() {
		return specialfees_dt;
	}

	public void setSpecialfees_dt(double specialfees_dt) {
		this.specialfees_dt = specialfees_dt;
	}

	public double getAddDuty_dt() {
		return addDuty_dt;
	}

	public void setAddDuty_dt(double addDuty_dt) {
		this.addDuty_dt = addDuty_dt;
	}

	public int getQuantity_dt() {
		return quantity_dt;
	}

	public void setQuantity_dt(int quantity_dt) {
		this.quantity_dt = quantity_dt;
	}

	private boolean printFlag;
	private String pdfName;
	private Date permitDate_dt;
	private String permitNmbr_dt;

	public Date getPermitDate_dt() {
		return permitDate_dt;
	}

	public void setPermitDate_dt(Date permitDate_dt) {
		this.permitDate_dt = permitDate_dt;
	}

	public String getPermitNmbr_dt() {
		return permitNmbr_dt;
	}

	public void setPermitNmbr_dt(String permitNmbr_dt) {
		this.permitNmbr_dt = permitNmbr_dt;
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

	private String loginType_dt;
	private Date crDate_dt;
	private Date approvalDate_dt;
	private String approvalTym_dt;
	private String approvalUser_dt;
	private int liquorCatgry_dt;

	public String getLoginType_dt() {
		return loginType_dt;
	}

	public void setLoginType_dt(String loginType_dt) {
		this.loginType_dt = loginType_dt;
	}

	public Date getCrDate_dt() {
		return crDate_dt;
	}

	public void setCrDate_dt(Date crDate_dt) {
		this.crDate_dt = crDate_dt;
	}

	public Date getApprovalDate_dt() {
		return approvalDate_dt;
	}

	public void setApprovalDate_dt(Date approvalDate_dt) {
		this.approvalDate_dt = approvalDate_dt;
	}

	public String getApprovalTym_dt() {
		return approvalTym_dt;
	}

	public void setApprovalTym_dt(String approvalTym_dt) {
		this.approvalTym_dt = approvalTym_dt;
	}

	public String getApprovalUser_dt() {
		return approvalUser_dt;
	}

	public void setApprovalUser_dt(String approvalUser_dt) {
		this.approvalUser_dt = approvalUser_dt;
	}

	public int getLiquorCatgry_dt() {
		return liquorCatgry_dt;
	}

	public void setLiquorCatgry_dt(int liquorCatgry_dt) {
		this.liquorCatgry_dt = liquorCatgry_dt;
	}

	private String importFeeChalanNo_dt;
	private String spclFeeChalanNo_dt;
	private int challanId_dt;
	private String challanNo_dt;
	private String challanDate_dt;
	private double challanAmnt_dt;
	private String divName_dt;
	private String treasryNm_dt;
	private int appId_dt;
	private String digitalSignPdfNm_dt;
	private Date digitalSignPdfDate_dt;

	public String getImportFeeChalanNo_dt() {
		return importFeeChalanNo_dt;
	}

	public void setImportFeeChalanNo_dt(String importFeeChalanNo_dt) {
		this.importFeeChalanNo_dt = importFeeChalanNo_dt;
	}

	public String getSpclFeeChalanNo_dt() {
		return spclFeeChalanNo_dt;
	}

	public void setSpclFeeChalanNo_dt(String spclFeeChalanNo_dt) {
		this.spclFeeChalanNo_dt = spclFeeChalanNo_dt;
	}

	public int getChallanId_dt() {
		return challanId_dt;
	}

	public void setChallanId_dt(int challanId_dt) {
		this.challanId_dt = challanId_dt;
	}

	public String getChallanNo_dt() {
		return challanNo_dt;
	}

	public void setChallanNo_dt(String challanNo_dt) {
		this.challanNo_dt = challanNo_dt;
	}

	public String getChallanDate_dt() {
		return challanDate_dt;
	}

	public void setChallanDate_dt(String challanDate_dt) {
		this.challanDate_dt = challanDate_dt;
	}

	public double getChallanAmnt_dt() {
		return challanAmnt_dt;
	}

	public void setChallanAmnt_dt(double challanAmnt_dt) {
		this.challanAmnt_dt = challanAmnt_dt;
	}

	public String getDivName_dt() {
		return divName_dt;
	}

	public void setDivName_dt(String divName_dt) {
		this.divName_dt = divName_dt;
	}

	public String getTreasryNm_dt() {
		return treasryNm_dt;
	}

	public void setTreasryNm_dt(String treasryNm_dt) {
		this.treasryNm_dt = treasryNm_dt;
	}

	public int getAppId_dt() {
		return appId_dt;
	}

	public void setAppId_dt(int appId_dt) {
		this.appId_dt = appId_dt;
	}

	public String getDigitalSignPdfNm_dt() {
		return digitalSignPdfNm_dt;
	}

	public void setDigitalSignPdfNm_dt(String digitalSignPdfNm_dt) {
		this.digitalSignPdfNm_dt = digitalSignPdfNm_dt;
	}

	public Date getDigitalSignPdfDate_dt() {
		return digitalSignPdfDate_dt;
	}

	public void setDigitalSignPdfDate_dt(Date digitalSignPdfDate_dt) {
		this.digitalSignPdfDate_dt = digitalSignPdfDate_dt;
	}

	private String consignorNmAdrs_dt;

	public String getConsignorNmAdrs_dt() {
		return consignorNmAdrs_dt;
	}

	public void setConsignorNmAdrs_dt(String consignorNmAdrs_dt) {
		this.consignorNmAdrs_dt = consignorNmAdrs_dt;
	}

}
