package com.mentor.datatable;

import java.util.ArrayList;
import java.util.Date;

import com.mentor.impl.IndentStatusMISImpl; 

public class IndentStatusMISDT {

	IndentStatusMISImpl impl = new IndentStatusMISImpl();

	private int srNo;
	private String indentNmbr_dt;
	private String indentDate_dt;
	private int wholesaleId_dt;
	private int districtId_dt;
	private String districtName_dt;
	private String wholesaleType_dt;
	private int nmbrOfCases_dt;
	private int nmbrOfbottles_dt;
	private String status_dt;
	private String type_dt;
	private String indentDtTym_dt;
	private String licenseeName_dt;
	private String paymentDate_dt;
	private String instrumentNo_dt;
	private double paidAmount_dt;
	private int nmbrOfCasesSuplied_dt;
	private String wholesaleLicensenmbr_dt;
	private String unit_name;
	private String bank_name;
	private double amunt;
	private String vch_mood_pay;
	private int bwfl_unit_id;
	public int getBwfl_unit_id() {
		return bwfl_unit_id;
	}

	public void setBwfl_unit_id(int bwfl_unit_id) {
		this.bwfl_unit_id = bwfl_unit_id;
	}

	private ArrayList brandPackagingData_BrandList = new ArrayList();

	public ArrayList getBrandPackagingData_BrandList() {

		this.brandPackagingData_BrandList = impl.getBrandName();
		return brandPackagingData_BrandList;
	}

	public void setBrandPackagingData_BrandList(
			ArrayList brandPackagingData_BrandList) {
		this.brandPackagingData_BrandList = brandPackagingData_BrandList;
	}

	public String getVch_mood_pay() {
		return vch_mood_pay;
	}

	public void setVch_mood_pay(String vch_mood_pay) {
		this.vch_mood_pay = vch_mood_pay;
	}

	public double getAmunt() {
		return amunt;
	}

	public void setAmunt(double amunt) {
		this.amunt = amunt;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getIndentNmbr_dt() {
		return indentNmbr_dt;
	}

	public void setIndentNmbr_dt(String indentNmbr_dt) {
		this.indentNmbr_dt = indentNmbr_dt;
	}

	public String getIndentDate_dt() {
		return indentDate_dt;
	}

	public void setIndentDate_dt(String indentDate_dt) {
		this.indentDate_dt = indentDate_dt;
	}

	public int getWholesaleId_dt() {
		return wholesaleId_dt;
	}

	public void setWholesaleId_dt(int wholesaleId_dt) {
		this.wholesaleId_dt = wholesaleId_dt;
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

	public String getWholesaleType_dt() {
		return wholesaleType_dt;
	}

	public void setWholesaleType_dt(String wholesaleType_dt) {
		this.wholesaleType_dt = wholesaleType_dt;
	}

	public int getNmbrOfCases_dt() {
		return nmbrOfCases_dt;
	}

	public void setNmbrOfCases_dt(int nmbrOfCases_dt) {
		this.nmbrOfCases_dt = nmbrOfCases_dt;
	}

	public int getNmbrOfbottles_dt() {
		return nmbrOfbottles_dt;
	}

	public void setNmbrOfbottles_dt(int nmbrOfbottles_dt) {
		this.nmbrOfbottles_dt = nmbrOfbottles_dt;
	}

	public String getStatus_dt() {
		return status_dt;
	}

	public void setStatus_dt(String status_dt) {
		this.status_dt = status_dt;
	}

	public String getType_dt() {
		return type_dt;
	}

	public void setType_dt(String type_dt) {
		this.type_dt = type_dt;
	}

	private int objectionID_dt;
	private String objDescription_dt;
	private String objIssue_dt;
	private String objDate_dt;

	public int getObjectionID_dt() {
		return objectionID_dt;
	}

	public void setObjectionID_dt(int objectionID_dt) {
		this.objectionID_dt = objectionID_dt;
	}

	public String getObjDescription_dt() {
		return objDescription_dt;
	}

	public void setObjDescription_dt(String objDescription_dt) {
		this.objDescription_dt = objDescription_dt;
	}

	public String getObjIssue_dt() {
		return objIssue_dt;
	}

	public void setObjIssue_dt(String objIssue_dt) {
		this.objIssue_dt = objIssue_dt;
	}

	public String getObjDate_dt() {
		return objDate_dt;
	}

	public void setObjDate_dt(String objDate_dt) {
		this.objDate_dt = objDate_dt;
	}

	public String getIndentDtTym_dt() {
		return indentDtTym_dt;
	}

	public void setIndentDtTym_dt(String indentDtTym_dt) {
		this.indentDtTym_dt = indentDtTym_dt;
	}

	public String getLicenseeName_dt() {
		return licenseeName_dt;
	}

	public void setLicenseeName_dt(String licenseeName_dt) {
		this.licenseeName_dt = licenseeName_dt;
	}

	public String getPaymentDate_dt() {
		return paymentDate_dt;
	}

	public void setPaymentDate_dt(String paymentDate_dt) {
		this.paymentDate_dt = paymentDate_dt;
	}

	public String getInstrumentNo_dt() {
		return instrumentNo_dt;
	}

	public void setInstrumentNo_dt(String instrumentNo_dt) {
		this.instrumentNo_dt = instrumentNo_dt;
	}

	public double getPaidAmount_dt() {
		return paidAmount_dt;
	}

	public void setPaidAmount_dt(double paidAmount_dt) {
		this.paidAmount_dt = paidAmount_dt;
	}

	public int getNmbrOfCasesSuplied_dt() {
		return nmbrOfCasesSuplied_dt;
	}

	public void setNmbrOfCasesSuplied_dt(int nmbrOfCasesSuplied_dt) {
		this.nmbrOfCasesSuplied_dt = nmbrOfCasesSuplied_dt;
	}

	public String getWholesaleLicensenmbr_dt() {
		return wholesaleLicensenmbr_dt;
	}

	public void setWholesaleLicensenmbr_dt(String wholesaleLicensenmbr_dt) {
		this.wholesaleLicensenmbr_dt = wholesaleLicensenmbr_dt;
	}

	private boolean printFlag;
	private String pdfName;
	private int unitId_dt;
	private String unitName_dt;
	private String licenseType_dt;

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

	public int getUnitId_dt() {
		return unitId_dt;
	}

	public void setUnitId_dt(int unitId_dt) {
		this.unitId_dt = unitId_dt;
	}

	public String getUnitName_dt() {
		return unitName_dt;
	}

	public void setUnitName_dt(String unitName_dt) {
		this.unitName_dt = unitName_dt;
	}

	public String getLicenseType_dt() {
		return licenseType_dt;
	}

	public void setLicenseType_dt(String licenseType_dt) {
		this.licenseType_dt = licenseType_dt;
	}

	private int slno;

	public int getSlno() {
		return slno;
	}

	public void setSlno(int slno) {
		this.slno = slno;
	}

	private int brandId_dt;

	public int getBrandId_dt() {
		return brandId_dt;
	}

	public void setBrandId_dt(int brandId_dt) {
		this.brandId_dt = brandId_dt;
	}

	public int getPckgId_dt() {
		return pckgId_dt;
	}

	public void setPckgId_dt(int pckgId_dt) {
		this.pckgId_dt = pckgId_dt;
	}

	public int getBoxSize_dt() {
		return boxSize_dt;
	}

	public void setBoxSize_dt(int boxSize_dt) {
		this.boxSize_dt = boxSize_dt;
	}

	public String getBrandName_dt() {
		return brandName_dt;
	}

	public void setBrandName_dt(String brandName_dt) {
		this.brandName_dt = brandName_dt;
	}

	public String getPckgName_dt() {
		return pckgName_dt;
	}

	public void setPckgName_dt(String pckgName_dt) {
		this.pckgName_dt = pckgName_dt;
	}

	public String getPckgType_dt() {
		return pckgType_dt;
	}

	public void setPckgType_dt(String pckgType_dt) {
		this.pckgType_dt = pckgType_dt;
	}

	public double getRoundMRP_dt() {
		return roundMRP_dt;
	}

	public void setRoundMRP_dt(double roundMRP_dt) {
		this.roundMRP_dt = roundMRP_dt;
	}

	public int getNoOfBottles_dt() {

		return noOfBottles_dt;
	}

	public void setNoOfBottles_dt(int noOfBottles_dt) {
		this.noOfBottles_dt = noOfBottles_dt;
	}

	public int getNoOfCases_dt() {
		return noOfCases_dt;
	}

	public void setNoOfCases_dt(int noOfCases_dt) {
		this.noOfCases_dt = noOfCases_dt;
	}

	private int pckgId_dt;
	private int boxSize_dt;
	private String brandName_dt;
	private String pckgName_dt;
	private String pckgType_dt;
	private double roundMRP_dt;
	private int noOfBottles_dt;
	private int noOfCases_dt;
	private ArrayList brandPackagingData_PackagingList = new ArrayList();
	private ArrayList brandPackagingData_QuantityList = new ArrayList();
	private int newBrand_Id;
	private int newPckg_Id;
	private int newSize_Id;
	//private int newBoxes_no;
	private int newBottles_no;
	private int newRounded_mrp;

	public int getNewBrand_Id() {
		return newBrand_Id;
	}

	public void setNewBrand_Id(int newBrand_Id) {
		this.newBrand_Id = newBrand_Id;
	}

	public int getNewPckg_Id() {
		return newPckg_Id;
	}

	public void setNewPckg_Id(int newPckg_Id) {
		this.newPckg_Id = newPckg_Id;
	}

	public int getNewSize_Id() {

		if (this.newPckg_Id != 0 && this.newBrand_Id != 0) {
			this.newSize_Id = impl.getqty(newBrand_Id, newPckg_Id);
		}
		return newSize_Id;
	}

	public void setNewSize_Id(int newSize_Id) {
		this.newSize_Id = newSize_Id;
	}

	/*public int getNewBoxes_no() {
		return newBoxes_no;
	}

	public void setNewBoxes_no(int newBoxes_no) {
		this.newBoxes_no = newBoxes_no;
	}*/

	public int getNewBottles_no() {

		this.newBottles_no = this.newBoxes_no_Temp * this.newSize_Id;

		return newBottles_no;
	}

	public void setNewBottles_no(int newBottles_no) {
		this.newBottles_no = newBottles_no;
	}

	public int getNewRounded_mrp() {

		if (this.newPckg_Id != 0 && this.newBrand_Id != 0) {

			// this.newRounded_mrp = this.newBottles_no * this.newRounded_mrp;
			this.newRounded_mrp = impl.getmrp(newBrand_Id, newPckg_Id);
		}

		return newRounded_mrp;
	}

	public void setNewRounded_mrp(int newRounded_mrp) {
		this.newRounded_mrp = newRounded_mrp;
	}

	public double calTotalMrp_dt = 0;

	public double getCalTotalMrp_dt() {

		this.calTotalMrp_dt = this.newBottles_no * this.newRounded_mrp;

		return calTotalMrp_dt;
	}

	public void setCalTotalMrp_dt(double calTotalMrp_dt) {
		this.calTotalMrp_dt = calTotalMrp_dt;
	}

	public ArrayList getBrandPackagingData_QuantityList() {

		if (this.newPckg_Id != 0 && this.newBrand_Id != 0) {
			// this.brandPackagingData_QuantityList=impl.getquantity(newBrand_Id,newPckg_Id);
		}

		return brandPackagingData_QuantityList;
	}

	public void setBrandPackagingData_QuantityList(
			ArrayList brandPackagingData_QuantityList) {
		this.brandPackagingData_QuantityList = brandPackagingData_QuantityList;
	}

	public ArrayList getBrandPackagingData_PackagingList() {

		if (String.valueOf(this.newBrand_Id)!=null) {
			//this.newBoxes_no_Temp = 0;
			this.brandPackagingData_PackagingList = impl.getPackagingName(newBrand_Id);
		}
		return brandPackagingData_PackagingList;
	}

	public void setBrandPackagingData_PackagingList(
			ArrayList brandPackagingData_PackagingList) {
		this.brandPackagingData_PackagingList = brandPackagingData_PackagingList;
	}

	private Date indentDate;
	private String showIndentDateTym_dt;

	public Date getIndentDate() {
		return indentDate;
	}

	public void setIndentDate(Date indentDate) {
		this.indentDate = indentDate;
	}

	public String getShowIndentDateTym_dt() {
		return showIndentDateTym_dt;
	}

	public void setShowIndentDateTym_dt(String showIndentDateTym_dt) {
		this.showIndentDateTym_dt = showIndentDateTym_dt;
	}

	private int newBoxes_no_Temp;

	public int getNewBoxes_no_Temp() {

		return newBoxes_no_Temp;
	}

	public void setNewBoxes_no_Temp(int newBoxes_no_Temp) {
		this.newBoxes_no_Temp = newBoxes_no_Temp;
	}

	
	private boolean deliveredFlg;
	
	public boolean isDeliveredFlg() {
		return deliveredFlg;
	}

	public void setDeliveredFlg(boolean deliveredFlg) {
		this.deliveredFlg = deliveredFlg;
	}
	
	
	

}
