package com.mentor.datatable;

import java.util.Date;

public class ValidateBarcodeDT {
	private int seq;
	private String barcode;
	private String status;
	private String d_and_b_id;
	private String type;
	public int getUploadedExcelId() {
		return uploadedExcelId;
	}
	public void setUploadedExcelId(int uploadedExcelId) {
		this.uploadedExcelId = uploadedExcelId;
	}
	private Date cr_date;
	private boolean printFlag = false;
    private int uploadedExcelId;
	
	
	
	
	public Date getCr_date() {
		return cr_date;
	}
	public void setCr_date(Date cr_date) {
		this.cr_date = cr_date;
	}
	public boolean isPrintFlag() {
		return printFlag;
	}
	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getD_and_b_id() {
		return d_and_b_id;
	}
	public void setD_and_b_id(String d_and_b_id) {
		this.d_and_b_id = d_and_b_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
