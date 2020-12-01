package com.mentor.datatable;

import java.util.Date;

public class Ena_purchase_tracking_dt {
	


	private int request_id;
	private String from_distillery;
	private String by_distillery;
	private Date request_date;
	private double bl;
	private int year;
	private String pdfName;
	private String user1 = "PENDING";
	private String user2 = "PENDING";
	private String user3 = "PENDING";
	private String user4;
	private boolean flag;
	private boolean printFlagNew;
	private String status;
	private boolean viewFlag = false;
	private boolean pdfFlag;
	private String pdfReport;
	private String permit_no;
	private String order;
	private String oupstate="0";
private Date validTill;
private String mainServiceId;private String maincntrlId;private String mainunitId;
private String requestId;

	
	
	
	public String getMainServiceId() {
	return mainServiceId;
}

public void setMainServiceId(String mainServiceId) {
	this.mainServiceId = mainServiceId;
}

public String getMaincntrlId() {
	return maincntrlId;
}

public void setMaincntrlId(String maincntrlId) {
	this.maincntrlId = maincntrlId;
}

public String getMainunitId() {
	return mainunitId;
}

public void setMainunitId(String mainunitId) {
	this.mainunitId = mainunitId;
}

public String getRequestId() {
	return requestId;
}

public void setRequestId(String requestId) {
	this.requestId = requestId;
}

	public Date getValidTill() {
		return validTill;
	}

	public void setValidTill(Date validTill) {
		this.validTill = validTill;
	}
	
	public String getOupstate() {
		return oupstate;
	}

	public void setOupstate(String oupstate) {
		this.oupstate = oupstate;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getPermit_no() {
		return permit_no;
	}

	public void setPermit_no(String permit_no) {
		this.permit_no = permit_no;
	}

	public String getPdfReport() {
		return pdfReport;
	}

	public void setPdfReport(String pdfReport) {
		this.pdfReport = pdfReport;
	}

	public boolean isPdfFlag() {
		return pdfFlag;
	}

	public void setPdfFlag(boolean pdfFlag) {
		this.pdfFlag = pdfFlag;
	}

	public boolean isPrintFlagNew() {
		return printFlagNew;
	}

	public void setPrintFlagNew(boolean printFlagNew) {
		this.printFlagNew = printFlagNew;
	}

	public boolean isViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(boolean viewFlag) {
		this.viewFlag = viewFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getUser4() {
		return user4;
	}

	public void setUser4(String user4) {
		this.user4 = user4;
	}

	public String getUser1() {
		return user1;
	}

	public void setUser1(String user1) {
		this.user1 = user1;
	}

	public String getUser2() {
		return user2;
	}

	public void setUser2(String user2) {
		this.user2 = user2;
	}

	public String getUser3() {
		return user3;
	}

	public void setUser3(String user3) {
		this.user3 = user3;
	}

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public String getFrom_distillery() {
		return from_distillery;
	}

	public void setFrom_distillery(String from_distillery) {
		this.from_distillery = from_distillery;
	}

	public String getBy_distillery() {
		return by_distillery;
	}

	public void setBy_distillery(String by_distillery) {
		this.by_distillery = by_distillery;
	}

	public Date getRequest_date() {
		return request_date;
	}

	public void setRequest_date(Date request_date) {
		this.request_date = request_date;
	}

	public double getBl() {
		return bl;
	}

	public void setBl(double bl) {
		this.bl = bl;
	}

	// ----------------rahul 18-12-2019
	private String state;
	private String country;
	private String pdfNameimp;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPdfNameimp() {
		return pdfNameimp;
	}

	public void setPdfNameimp(String pdfNameimp) {
		this.pdfNameimp = pdfNameimp;
	}
//////////abhishek code for export print///////////////
	
private String printtype;
public String getPrinttype() {
return printtype;
}

public void setPrinttype(String printtype) {
this.printtype = printtype;
}


//////rahul-27-12-2019 for revert back

private String acRvrtRmrk_dt;
private String decRvrtRmrk_dt;

public String getAcRvrtRmrk_dt() {
	return acRvrtRmrk_dt;
}

public void setAcRvrtRmrk_dt(String acRvrtRmrk_dt) {
	this.acRvrtRmrk_dt = acRvrtRmrk_dt;
}

public String getDecRvrtRmrk_dt() {
	return decRvrtRmrk_dt;
}

public void setDecRvrtRmrk_dt(String decRvrtRmrk_dt) {
	this.decRvrtRmrk_dt = decRvrtRmrk_dt;
}
private String purpose;



public String getPurpose() {
	return purpose;
}

public void setPurpose(String purpose) {
	this.purpose = purpose;
}





//rahul 24-09-2020 for raise query and remove


private String userQ;
private String queryQ;
private String statusQ;
private String responseQ;
private String docQ;
private Date queryDtQ;
private Date responseDtQ;
private int snoQ;
private boolean usrflg;




public String getUserQ() {
	return userQ;
}

public void setUserQ(String userQ) {
	this.userQ = userQ;
}

public String getQueryQ() {
	return queryQ;
}

public void setQueryQ(String queryQ) {
	this.queryQ = queryQ;
}

public String getStatusQ() {
	return statusQ;
}

public void setStatusQ(String statusQ) {
	this.statusQ = statusQ;
}

public String getResponseQ() {
	return responseQ;
}

public void setResponseQ(String responseQ) {
	this.responseQ = responseQ;
}

public String getDocQ() {
	return docQ;
}

public void setDocQ(String docQ) {
	this.docQ = docQ;
}

public Date getQueryDtQ() {
	return queryDtQ;
}

public void setQueryDtQ(Date queryDtQ) {
	this.queryDtQ = queryDtQ;
}

public Date getResponseDtQ() {
	return responseDtQ;
}

public void setResponseDtQ(Date responseDtQ) {
	this.responseDtQ = responseDtQ;
}

public int getSnoQ() {
	return snoQ;
}

public void setSnoQ(int snoQ) {
	this.snoQ = snoQ;
}

public boolean isUsrflg() {
	return usrflg;
}

public void setUsrflg(boolean usrflg) {
	this.usrflg = usrflg;
}






}
