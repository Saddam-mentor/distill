package com.mentor.datatable;

import java.util.ArrayList;
import java.util.Date;

public class CircularDatatable {

	
	private int newsserial;
	private String newshead;
	private Date newsdt;
	private String newsdesc;
	private String newsfile; 
	private ArrayList news = new ArrayList();
	
	
	
	
	
	
	public int getNewsserial() {
		return newsserial;
	}
	public void setNewsserial(int newsserial) {
		this.newsserial = newsserial;
	}
	public String getNewshead() {
		return newshead;
	}
	public void setNewshead(String newshead) {
		this.newshead = newshead;
	}
	public Date getNewsdt() {
		return newsdt;
	}
	public void setNewsdt(Date newsdt) {
		this.newsdt = newsdt;
	}
	public String getNewsdesc() {
		return newsdesc;
	}
	public void setNewsdesc(String newsdesc) {
		this.newsdesc = newsdesc;
	}
	public String getNewsfile() {
		return newsfile;
	}
	public void setNewsfile(String newsfile) {
		this.newsfile = newsfile;
	}
	
	
	
}
