package com.mentor.datatable;

public class DateWiseDatatable {
	private String pdfDraft;
	private boolean draftprintFlag;
	private boolean printFlag;
	private String pdfname;
	public String getPdfname() {
		return pdfname;
	}
	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
	}
	public boolean isPrintFlag() {
		return printFlag;
	}
	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}
	public String getPdfDraft() {
		return pdfDraft;
	}
	public void setPdfDraft(String pdfDraft) {
		this.pdfDraft = pdfDraft;
	}
	public boolean isDraftprintFlag() {
		return draftprintFlag;
	}
	public void setDraftprintFlag(boolean draftprintFlag) {
		this.draftprintFlag = draftprintFlag;
	}

}
