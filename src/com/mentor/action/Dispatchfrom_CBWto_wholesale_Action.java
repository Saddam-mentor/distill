package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.Dispatchfrom_CBWto_wholesale_impl;

public class Dispatchfrom_CBWto_wholesale_Action {
	
	Dispatchfrom_CBWto_wholesale_impl impl=new Dispatchfrom_CBWto_wholesale_impl();
	
	
	private Date fromdate;
	private Date todate;
	
	private boolean excelFlag = false;
	private String exlname;
	private String fl2DId;
	private String district_id;
 	private ArrayList distList = new ArrayList();
	
	

	public ArrayList getDistList() {
		try {
			this.distList = impl.distList(this);
				} catch (Exception e) {
			 e.printStackTrace();
			}
			return distList;
}

	public void setDistList(ArrayList distList) {
		this.distList = distList;
	}

	public String getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(String district_id) {
		this.district_id = district_id;
	}

	public String getFl2DId() {
		return fl2DId;
	}

	public void setFl2DId(String fl2dId) {
		fl2DId = fl2dId;
	}

	public void setFl2DList(ArrayList fl2dList) {
		fl2DList = fl2dList;
	}

	public ArrayList fl2DList=new ArrayList();
	
	
	public String getExlname() {
		return exlname;
	}

	public void setExlname(String exlname) {
		this.exlname = exlname;
	}

	public boolean isExcelFlag() {
		return excelFlag;
	}

	public void setExcelFlag(boolean excelFlag) {
		this.excelFlag = excelFlag;
	}

	
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

	
	
	public void excel() {
		if(this.district_id!=null && this.district_id.length()>0)
		{
			 impl.write_excel(this);
		}
		 else{
			  impl.Data_excel(this) ;
		  }
	    }

	
	
	public void reset() {

		this.excelFlag = false;
		this.exlname = null;
		this.fromdate = null;
		this.todate = null;
		
		
		
	}

	public void radioListiner(ValueChangeEvent e) {
		this.excelFlag = false;
		
		
	}

      //=====================================FL@D===================         

        public ArrayList getFl2DList() {
        	try {
        		if(this.district_id!=null && this.district_id.length()>0)
		              this.fl2DList = impl.getFL2DList(this);
      	} catch (Exception e) {
		 e.printStackTrace();
	    }
	  return fl2DList;
        }







}


//========================================================================





	