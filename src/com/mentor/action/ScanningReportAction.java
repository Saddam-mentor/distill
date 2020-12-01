package com.mentor.action;

import java.util.Date;

import com.mentor.impl.ScanningReportImpl;

public class ScanningReportAction {
	
	
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String url;
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public boolean isPrintFlag() {
		return printFlag;
	}
	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public void printReport()
	{
		try{
			new ScanningReportImpl().printReport(this);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void reset()
	{
		this.printFlag=false;
	}

}
