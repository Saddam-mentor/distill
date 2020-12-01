package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.AthenalMovementImpl;

public class AthenalMovementAction {

	
	AthenalMovementImpl impl = new AthenalMovementImpl();

	private int dist_id=9999;
	private ArrayList distList;
	private ArrayList datalist;

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public boolean verfiFlag;

	private String name;
	private String adrss;
	private int depo_dist_id;
	public String radio;
	private boolean printFlag = false;
	private Date fromdate;
	private Date todate;
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

	public ArrayList getDatalist() {


		return datalist;
	}

	public void setDatalist(ArrayList datalist) {
		this.datalist = datalist;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public int getDepo_dist_id() {

		return depo_dist_id;
	}

	public void setDepo_dist_id(int depo_dist_id) {
		this.depo_dist_id = depo_dist_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdrss() {
		return adrss;
	}

	public void setAdrss(String adrss) {
		this.adrss = adrss;
	}

	private String hidden;

	public String getHidden() {
		try {

		} catch (Exception e) {

		}

		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public boolean isVerfiFlag() {
		return verfiFlag;
	}

	public void setVerfiFlag(boolean verfiFlag) {
		this.verfiFlag = verfiFlag;
	}

	public ArrayList showData = new ArrayList();;

	public ArrayList getShowData() {

		// this.showData = impl.getList(this);

		return showData;
	}

	public void setShowData(ArrayList showData) {
		this.showData = showData;
	}

	public int getDist_id() {

		// this.dist_id = depo_dist_id;
		return dist_id;
	}

	public void setDist_id(int dist_id) {
		this.dist_id = dist_id;
	}

	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public ArrayList getDistList() {

		this.distList = impl.getDistrictList1(this);

		return distList;
	}

	public void setDistList(ArrayList distList) {
		this.distList = distList;
	}

	public void chngval(ValueChangeEvent e) {

		this.fromdate = null;
		this.todate = null;
		this.printFlag = false;
		this.pdfname = "";
	}
	
	
public void print() {
		
		if(impl.printReportImpl(this)==true)
		{
			this.printFlag=true;
		}else
		{
			this.printFlag=false;
		}
	}
	
	
}
