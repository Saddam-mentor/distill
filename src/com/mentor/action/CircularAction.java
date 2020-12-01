package com.mentor.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;


import com.mentor.impl.CircularImpl;
import com.mentor.utility.Validate;

public class CircularAction {

	CircularImpl impl = new CircularImpl();
	
	
	private String radio="A";
	private String category_id;
	private ArrayList categoryList = new ArrayList();
	private ArrayList news = new ArrayList();
	private Date fromdate;
	private Date todate=new Date();
	

	public Date getFromdate() {
		return fromdate;
	}
/*public Date getFromdate() throws ParseException{
		
		
		String nov = "01/04/2020";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(nov);
		this.fromdate = date1;
		
		
		
		return fromdate;
	}*/
	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}
	public Date getTodate() {
		return todate;
	}
	public void setTodate(Date todate) {
		this.todate = todate;
	}
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public ArrayList getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(ArrayList categoryList) {
		this.categoryList = categoryList;
	}
	public ArrayList getNews() {
		
		return news;
	}
	public void setNews(ArrayList news) {
		this.news = news;
	}
	public void datalist(ValueChangeEvent e) {
	
		String val = (String) e.getNewValue();
	    this.setRadio(val);
	    if(this.radio.equalsIgnoreCase("C")){
	    	this.categoryList=impl.getcategorylist();
	    }
	    this.news.clear();
	   
	
	}
	public void datalist1() throws ParseException {
		///System.out.println("===this.getRadio()==="+this.getRadio()+"=========="+this.category_id);
	/*	String startDate = "31/03/2020";
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
		
		if(this.fromdate.after(date1)){*/
		if(this.getRadio().equalsIgnoreCase("C")){
			if(this.category_id!=null && !this.category_id.equalsIgnoreCase("0")){
				 this.news=impl.homenews(this);
			}else{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Please select Category !", "Please select Category !"));
			}
			
		}else if(this.getRadio().equalsIgnoreCase("A")){
			
	    this.news=impl.homenews(this);
	    
		}
		/*}else{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Please select Date after 31-03-2020 !!!!", "Please select Date after 31-03-2020 !"));
			
		}*/
	
	}
	
	
}
