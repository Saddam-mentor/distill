package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.UIDataTable;

import com.mentor.datatable.Eoi_app_expo_report_dt;
import com.mentor.impl.Eoi_app_expo_report_impl;


public class Eoi_app_expo_report_action {
	
	Eoi_app_expo_report_impl impl = new Eoi_app_expo_report_impl();
	
	
	private String distilleryId;
	
	private ArrayList distilleryList = new ArrayList();
	
	public ArrayList getDistilleryList() {
		
		try {
			this.distilleryList = impl.getDistilleryList(this);	
			System.out.println("===list chal gyis");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return distilleryList;
	}


	public void setDistilleryList(ArrayList distilleryList) {
		this.distilleryList = distilleryList;
	}
	
	
	
	public String getDistilleryId() {
		return distilleryId;
	}
	public void setDistilleryId(String distilleryId) {
		this.distilleryId = distilleryId;
	}



	private String radio="BA";

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}
	
       public void radioListener(ValueChangeEvent e) 
       {
		String val = (String) e.getNewValue();
		this.radio = val ;
		this.setFlag1(false);
		this.setFlag2(false);
		}
      
       private ArrayList displaylist = new ArrayList();
       
       private ArrayList displaylist1 = new ArrayList();
       
       private ArrayList displaylist2 = new ArrayList();
       
       private ArrayList displaylist3 = new ArrayList();
       
	 

	public ArrayList getDisplaylist() {
		 if(this.distilleryId!=null)
			{
			  this.displaylist = impl.beforeApproval(this); 
			}
		return displaylist;
	}


	public void setDisplaylist(ArrayList displaylist) {
		this.displaylist = displaylist;
	}


	public ArrayList getDisplaylist1()
	{
		
		if(this.distilleryId!=null)
		{
		this.displaylist1 = impl.afterApproval(this);
		}
		return displaylist1;
	}

	public void setDisplaylist1(ArrayList displaylist1) {
		this.displaylist1 = displaylist1;
	}

	public ArrayList getDisplaylist2() {
		return displaylist2;
	}

	public void setDisplaylist2(ArrayList displaylist2) {
		this.displaylist2 = displaylist2;
	}

	public ArrayList getDisplaylist3() {
		return displaylist3;
	}

	public void setDisplaylist3(ArrayList displaylist3) {
		this.displaylist3 = displaylist3;
	}

	  public void view(ActionEvent ae)
	{
    		UIDataTable uiTable = (UIDataTable) ae.getComponent().getParent().getParent();
    		int rowId = uiTable.getRowIndex();
            Eoi_app_expo_report_dt dt = (Eoi_app_expo_report_dt) this.displaylist1.get(rowId);
    		this.displaylist2=impl.viewImpl(this,dt.getImp_no());
    		this.import_no=dt.getImp_no();
    		this.import_date=dt.getImp_dt();
    		this.setFlag1(true);
    		this.setFlag2(false);
    	}
       
	    public void view1(ActionEvent ae)
	    {
		UIDataTable uiTable = (UIDataTable) ae.getComponent().getParent().getParent();
		int rowId = uiTable.getRowIndex();
        Eoi_app_expo_report_dt dt = (Eoi_app_expo_report_dt) this.displaylist2.get(rowId);
        
        //System.out.println("==dt.getEx_no()=="+dt.getEx_no());
        
		this.displaylist3=impl.viewImpl_new(this,dt.getEx_no());
		this.export_no=dt.getEx_no();
		this.export_date=dt.getEx_dt();
		
		this.setFlag2(true);
	}
       
private boolean flag1 ;

private boolean flag3 ;

private boolean flag4 ;

public boolean isFlag3() {
	return flag3;
}


public void setFlag3(boolean flag3) {
	this.flag3 = flag3;
}


public boolean isFlag4() {
	return flag4;
}


public void setFlag4(boolean flag4) {
	this.flag4 = flag4;
}



private boolean flag2 ;

public boolean isFlag1() {
	return flag1;
}

public void setFlag1(boolean flag1) {
	this.flag1 = flag1;
}

public boolean isFlag2() {
	return flag2;
}

public void setFlag2(boolean flag2) {
	this.flag2 = flag2;
}

public void reset()
{
	this.displaylist.clear();
	this.displaylist1.clear();
	this.displaylist2.clear();
	this.displaylist3.clear();
	this.setFlag1(false);
	this.setFlag2(false);
}
public String import_no ;

public String export_no ;

public String getImport_no() {
	return import_no;
}


public void setImport_no(String import_no) {
	this.import_no = import_no;
}


public String getExport_no() {
	return export_no;
}


public void setExport_no(String export_no) {
	this.export_no = export_no;
}
private Date import_date ;

private Date export_date ;

public Date getImport_date() {
	return import_date;
}


public void setImport_date(Date import_date) {
	this.import_date = import_date;
}


public Date getExport_date() {
	return export_date;
}


public void setExport_date(Date export_date) {
	this.export_date = export_date;
}


}
