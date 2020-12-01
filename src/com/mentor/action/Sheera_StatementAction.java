package com.mentor.action;

import com.mentor.impl.Sheera_StatementImpl;

public class Sheera_StatementAction {
	
	private String pdfname;
	private boolean printFlag=false;
	
	
	
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
	Sheera_StatementImpl impl=new Sheera_StatementImpl();
	public void print()
	{
		
			impl.printReportCountryLiquor(this);
		
	}
	public com.mentor.impl.Sheera_StatementImpl getImpl() {
		return impl;
	}
	public void setImpl(com.mentor.impl.Sheera_StatementImpl impl) {
		this.impl = impl;
	}
	
}
