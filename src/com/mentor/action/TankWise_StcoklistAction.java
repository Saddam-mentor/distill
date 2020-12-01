package com.mentor.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.TankWise_StcoklistImpl;

public class TankWise_StcoklistAction {

	TankWise_StcoklistImpl impl = new TankWise_StcoklistImpl();

	private String radio;
	private String hidden;
	private int loginUserId;
	private String loginUserNm;
	private String loginUserAdrs;
	private String loginUserType;

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getHidden() {
		try {
			impl.getDistillery(this);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public int getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(int loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginUserNm() {
		return loginUserNm;
	}

	public void setLoginUserNm(String loginUserNm) {
		this.loginUserNm = loginUserNm;
	}

	public String getLoginUserAdrs() {
		return loginUserAdrs;
	}

	public void setLoginUserAdrs(String loginUserAdrs) {
		this.loginUserAdrs = loginUserAdrs;
	}

	public String getLoginUserType() {
		return loginUserType;
	}

	public void setLoginUserType(String loginUserType) {
		this.loginUserType = loginUserType;
	}

	public void change_radio(ValueChangeEvent vce) {
		String val = (String) vce.getNewValue();
		this.setRadio(val);
		this.todate = null;
		this.formdate = null;
		if (this.radio != null) {
			this.vatList = impl.getVatList(this);
			this.viewflg="F";
			this.printFlag = false;

		} else {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							" Please Select Radio !!",
							" Please Select Radio !!"));
		}
	}

	private boolean printFlag;
	private String pdfName;

	public void print() {

		try {
			impl.getopening(this);
			if (this.radio != null) {
				if (this.vatNo != null && this.vatNo.length() > 0) {
					System.out.println("========print action====");
					String sDate1 = "14-07-2018";
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd-MM-yyyy");
					Date date1 = formatter.parse(sDate1);
					this.fixdate = date1;
					if (this.formdate.after(this.fixdate)
							&& this.todate != null) {
						impl.printReport(this);
					} else {

						FacesContext
								.getCurrentInstance()
								.addMessage(
										null,
										new FacesMessage(
												FacesMessage.SEVERITY_ERROR,
												" Please Select From Date Greater Then Or Equal To '15-07-2020'  !!",
												" Please Select From Date Greater Then Or Equal To '15-07-2020'  !!"));
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Please Select Vat List !!",
									"Please Select Vat List !!"));
				}
			} else {

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" Please Select Radio !!",
								" Please Select Radio !!"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
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

	public void reset() {
		try {
			/*
			 * this.formdate = null; this.todate = null; this.radioType = null;
			 * this.excelFlag = false;
			 */
			this.viewflg="F";
			this.printFlag = false;
			this.todate = null;
			this.formdate = null;
			this.radio = "SV";
			this.vatNo = null;

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private double bal_bl;
	private double bal_al;

	public double getBal_bl() {
		return bal_bl;
	}

	public void setBal_bl(double bal_bl) {
		this.bal_bl = bal_bl;
	}

	public double getBal_al() {
		return bal_al;
	}

	public void setBal_al(double bal_al) {
		this.bal_al = bal_al;
	}

	private Date formdate;
	private Date todate;

	public Date getFormdate() {
		/*
		 * try{ String sDate1 = "15-07-2018"; SimpleDateFormat formatter = new
		 * SimpleDateFormat("dd-MM-yyyy"); Date date1 = formatter.parse(sDate1);
		 * this.formdate = date1; } catch (Exception e){
		 * 
		 * }
		 */
		return formdate;
	}

	public void setFormdate(Date formdate) {
		this.formdate = formdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public double getOpening_bl() {
		return opening_bl;
	}

	public void setOpening_bl(double opening_bl) {
		this.opening_bl = opening_bl;
	}

	public double getOpening_al() {
		return opening_al;
	}

	public void setOpening_al(double opening_al) {
		this.opening_al = opening_al;
	}

	private double opening_bl;
	private double opening_al;
	private String vatNo;
	private ArrayList vatList = new ArrayList();

	public String getVatNo() {
		return vatNo;
	}

	public Date getFixdate() {
		return fixdate;
	}

	public void setFixdate(Date fixdate) {
		this.fixdate = fixdate;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	public ArrayList getVatList() {
		// this.vatList=impl.getVatList(this);
		return vatList;
	}

	public void setVatList(ArrayList vatList) {
		this.vatList = vatList;
	}

	private Date fixdate;
	
	private ArrayList showDataTablelist = new ArrayList();
	public ArrayList getShowDataTablelist() {
		//this.showDataTablelist=impl.getShowData(this);
		return showDataTablelist;
	}

	public void setShowDataTablelist(ArrayList showDataTablelist) {
		this.showDataTablelist = showDataTablelist;
	}

	public void showdata() {

		try {
			impl.getopening(this);
			if (this.radio != null) {
				if (this.vatNo != null && this.vatNo.length() > 0) {
					System.out.println("========print action====");
					String sDate1 = "14-07-2020";
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd-MM-yyyy");
					Date date1 = formatter.parse(sDate1);
					this.fixdate = date1;
					if (this.formdate.after(this.fixdate)
							&& this.todate != null) {
						this.viewflg="T";
						this.showDataTablelist=impl.getShowData(this);
					} else {

						FacesContext
								.getCurrentInstance()
								.addMessage(
										null,
										new FacesMessage(
												FacesMessage.SEVERITY_ERROR,
												" Please Select From Date Greater Then Or Equal To '15-07-2020'  !!",
												" Please Select From Date Greater Then Or Equal To '15-07-2020'  !!"));
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Please Select Vat List !!",
									"Please Select Vat List !!"));
				}
			} else {

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" Please Select Radio !!",
								" Please Select Radio !!"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	private double total_al;
	private double total_bl;

	public double getTotal_al() {
		return total_al;
	}

	public void setTotal_al(double total_al) {
		this.total_al = total_al;
	}

	public double getTotal_bl() {
		return total_bl;
	}

	public void setTotal_bl(double total_bl) {
		this.total_bl = total_bl;
	}
	
	public String getViewflg() {
		return viewflg;
	}

	public void setViewflg(String viewflg) {
		this.viewflg = viewflg;
	}

	private String viewflg="F";

}
