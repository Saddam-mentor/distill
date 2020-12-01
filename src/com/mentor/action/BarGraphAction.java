package com.mentor.action;

import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.BarGraphActionImpl;


public class BarGraphAction {
	
	BarGraphActionImpl impl=new BarGraphActionImpl();
	
	String type="N";
	private String hidden;
	
	public String getHidden() {
		if(this.getSelectMonth().equalsIgnoreCase("K")){
			
			impl.currentMonth();
		}
		
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void filter1(ValueChangeEvent e){
		
		String id = (String) e.getNewValue();
		 this.type=id;
		 
		 System.out.println("Faizal ===== "+type);
			
	}
	
public String total() {
		
		
		String name="total";
		
		return name;
		
	}
	
public String month() {
	
	
	String name="month";
	
	return name;
	
}
public String today() {
	
	
	String name="today";
	
	return name;
	
}

public String seedetail() {
	
	
	String name="seedetail";
	
	return name;
	
}

public String seedetail1() {
	
	
	String name="seedetail1";
	
	return name;
	
}
public String seedetail2() {
	
	
	String name="seedetail2";
	
	return name;
	
}
public String seedetail3() {
	
	
	String name="seedetail3";
	
	return name;
	
}
public String seedetail4() {
	
	
	String name="seedetail4";
	
	return name;
	
}

public String seedetail5() {
	
	
	String name="seedetail5";
	
	return name;
	
}
public String back() {
	
	this.seedetail();
	
	
	String name="barGrapht";
	
	return name;
	
}

public String graph() {
	
	
	String name="graph";
	
	return name;
	
}

private String selectMonth="K";

public String getSelectMonth() {
	
	return selectMonth;
}

public void setSelectMonth(String selectMonth) {
	this.selectMonth = selectMonth;
}

public void chngval(ValueChangeEvent e) {

	String val = (String) e.getNewValue();
	try {
		impl.mothupdate(val);
	} catch (Exception ex) {
		ex.printStackTrace();
	}
}

public String currentDay()
{

	return "currentDay";
}


}
