package com.mentor.action;


import javax.faces.event.ValueChangeEvent;




import java.util.ArrayList;

import com.mentor.datatable.ChallanReportDatatable;
import com.mentor.impl.ChallanReportImpl;

public class ChallanReportAction {
	
	
public String radio;
private ArrayList list=new ArrayList();
private ChallanReportDatatable dt;
public ChallanReportDatatable getDt() {
	return dt;
}

public void setDt(ChallanReportDatatable dt) {
	this.dt = dt;
}

public ArrayList getList() {
	
	/*if(this.radio.equals("P"))
	{
		this.list=new ChallanReportImpl().getData();
	}else if(this.radio.equals("A")){
		this.list=new ChallanReportImpl().getApprovedData();
	}
	*/
	return list;
}

public void setList(ArrayList list) {
	this.list = list;
}

public String getRadio() {
	return radio;
}

public void setRadio(String radio) {
	this.radio = radio;
}


public void approveData()
{
	
try{
new ChallanReportImpl()	.approveChallan(this, dt);
}catch(Exception e)
{
	e.printStackTrace();
	
}


}


public String  datatableData(ValueChangeEvent event)
{
	Object o=event.getNewValue();
	String s=(String)o;
	System.out.println( "sdfdsfdsfdsfsdfsdfdsf   "+o);
	if(s.equals("P"))
	{
		this.list=new ChallanReportImpl().getData();
	}else if(s.equals("V")){
		this.list=new ChallanReportImpl().getApprovedData();
	}	
return "";
}





}
