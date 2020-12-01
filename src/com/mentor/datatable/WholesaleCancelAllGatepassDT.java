package com.mentor.datatable;

import java.util.Date;

public class WholesaleCancelAllGatepassDT {

	private int slNo;
	private int unitID_dt;
	private String unitType_dt;
	private String unitName_dt;
	private String gatepassNmbr_dt;
	private Date gatepassDate_dt;
	private boolean checkBox;
	private int dispatcBotlsDt;
	private int brandIdDt;
	private int pckgIdDt;
	private int seqDt;
	private int breakageDt = 0;

	public int getSlNo() {
		return slNo;
	}

	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}

	public int getUnitID_dt() {
		return unitID_dt;
	}

	public void setUnitID_dt(int unitID_dt) {
		this.unitID_dt = unitID_dt;
	}

	public String getUnitType_dt() {
		return unitType_dt;
	}

	public void setUnitType_dt(String unitType_dt) {
		this.unitType_dt = unitType_dt;
	}

	public String getUnitName_dt() {
		return unitName_dt;
	}

	public void setUnitName_dt(String unitName_dt) {
		this.unitName_dt = unitName_dt;
	}

	public String getGatepassNmbr_dt() {
		return gatepassNmbr_dt;
	}

	public void setGatepassNmbr_dt(String gatepassNmbr_dt) {
		this.gatepassNmbr_dt = gatepassNmbr_dt;
	}

	public Date getGatepassDate_dt() {
		return gatepassDate_dt;
	}

	public void setGatepassDate_dt(Date gatepassDate_dt) {
		this.gatepassDate_dt = gatepassDate_dt;
	}

	public boolean isCheckBox() {
		return checkBox;
	}

	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}

	public int getDispatcBotlsDt() {
		return dispatcBotlsDt;
	}

	public void setDispatcBotlsDt(int dispatcBotlsDt) {
		this.dispatcBotlsDt = dispatcBotlsDt;
	}

	public int getBrandIdDt() {
		return brandIdDt;
	}

	public void setBrandIdDt(int brandIdDt) {
		this.brandIdDt = brandIdDt;
	}

	public int getPckgIdDt() {
		return pckgIdDt;
	}

	public void setPckgIdDt(int pckgIdDt) {
		this.pckgIdDt = pckgIdDt;
	}

	public int getSeqDt() {
		return seqDt;
	}

	public void setSeqDt(int seqDt) {
		this.seqDt = seqDt;
	}

	public int getBreakageDt() {
		return breakageDt;
	}

	public void setBreakageDt(int breakageDt) {
		this.breakageDt = breakageDt;
	}

}
