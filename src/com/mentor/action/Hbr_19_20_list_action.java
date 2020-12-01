package com.mentor.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.impl.Hbr_19_20_list_impl;

public class Hbr_19_20_list_action {
	
	Hbr_19_20_list_impl impl = new Hbr_19_20_list_impl();


ArrayList displaylist = new ArrayList();


	public ArrayList getDisplaylist() {
		
		displaylist = impl.showdata(this);

		return displaylist;
	}
	public void setDisplaylist(ArrayList displaylist) {
		this.displaylist = displaylist;
	}

}
