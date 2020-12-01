package com.mentor.action;

import com.mentor.impl.PointInformationReportImpl;

public class PointInformationReportAction {

	PointInformationReportImpl impl = new PointInformationReportImpl();
	
	private String hidden;
	private boolean printFlag;
	private String pdfName;
	private boolean excelFlag;
	private String excelName;

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
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

	public boolean isExcelFlag() {
		return excelFlag;
	}

	public void setExcelFlag(boolean excelFlag) {
		this.excelFlag = excelFlag;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
	
	
	public void print() {
		try {

			impl.printReport(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void excel() {
		try {

			impl.writeExcel(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.excelFlag = false;
		this.excelName = null;

	}

}
