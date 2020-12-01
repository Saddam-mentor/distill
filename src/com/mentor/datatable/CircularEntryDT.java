package com.mentor.datatable;

import java.util.Date;

public class CircularEntryDT {
	
	private int srNo_int;
	private Date date;
	private String heading_str_dt;
	private String discription_str_dt;
	private String pdf_str_dt;
	
	public int getSrNo_int() {
		return srNo_int;
	}
	public void setSrNo_int(int srNo_int) {
		this.srNo_int = srNo_int;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getHeading_str_dt() {
		return heading_str_dt;
	}
	public void setHeading_str_dt(String heading_str_dt) {
		this.heading_str_dt = heading_str_dt;
	}
	public String getDiscription_str_dt() {
		return discription_str_dt;
	}
	public void setDiscription_str_dt(String discription_str_dt) {
		this.discription_str_dt = discription_str_dt;
	}
	public String getPdf_str_dt() {
		return pdf_str_dt;
	}
	public void setPdf_str_dt(String pdf_str_dt) {
		this.pdf_str_dt = pdf_str_dt;
	}
	
	private String category_id;
	private String category_type;
	

	public String getCategory_type() {
		return category_type;
	}
	public void setCategory_type(String category_type) {
		this.category_type = category_type;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

}
