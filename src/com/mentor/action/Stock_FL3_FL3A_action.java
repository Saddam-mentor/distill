package com.mentor.action;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.Stock_FL3_FL3A_impl;
import com.mentor.utility.Validate;

public class Stock_FL3_FL3A_action {
	
	Stock_FL3_FL3A_impl impl = new Stock_FL3_FL3A_impl();
	
	private String name;
	private int dist_id ;
	private String address ;
	public String vch_from_lic_no;
	private boolean lic_disable_flag;
	private boolean lic_disable_flag2;
	public String vch_from; 
	private String hidden;
	private String exlname;
    private boolean excelFlag; 
	public Stock_FL3_FL3A_impl getImpl() {
		return impl;
	}


	public void setImpl(Stock_FL3_FL3A_impl impl) {
		this.impl = impl;
	}


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


	ArrayList fromliclist = new ArrayList();
	private String pdfname;
	public String getPdfname() {
		return pdfname;
	}


	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
	}


	public Boolean getPrintFlag() {
		return printFlag;
	}


	public void setPrintFlag(Boolean printFlag) {
		this.printFlag = printFlag;
	}


	private Boolean printFlag;
	public ArrayList getFromliclist() {
		return fromliclist;
	}


	public void setFromliclist(ArrayList fromliclist) {
		this.fromliclist = fromliclist;
	}


	public ArrayList getDisplaylist() {
		return displaylist;
	}


	public void setDisplaylist(ArrayList displaylist) {
		this.displaylist = displaylist;
	}


	public ArrayList displaylist = new ArrayList();
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getDist_id() {
		return dist_id;
	}


	public void setDist_id(int dist_id) {
		this.dist_id = dist_id;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getVch_from_lic_no() {
		return vch_from_lic_no;
	}


	public void setVch_from_lic_no(String vch_from_lic_no) {
		this.vch_from_lic_no = vch_from_lic_no;
	}


	public boolean isLic_disable_flag() {
		return lic_disable_flag;
	}


	public void setLic_disable_flag(boolean lic_disable_flag) {
		this.lic_disable_flag = lic_disable_flag;
	}


	public boolean isLic_disable_flag2() {
		return lic_disable_flag2;
	}


	public void setLic_disable_flag2(boolean lic_disable_flag2) {
		this.lic_disable_flag2 = lic_disable_flag2;
	}


	public String getVch_from() {
		return vch_from;
	}


	public void setVch_from(String vch_from) {
		this.vch_from = vch_from;
	}


	public String getHidden() {
		impl.getDetails(this);
		return hidden;
	}


	public void setHidden(String hidden) {
		this.hidden = hidden;
	}


	
	// setVch for Radio 
	
	public String fromListMethod(ValueChangeEvent vce) {
		try {
			
			Object obj = vce.getNewValue();
			String s = (String) obj;
			this.setVch_from(s);

			this.getAll_List=impl.fromliclistImpl(this);
			this.lic_disable_flag2 = false;
			this.reset();

			/*if (this.getVch_from().equalsIgnoreCase("FL3A")) {
				
				this.fromliclist = impl.fromliclistImpl(this);
		
			}

			else if (this.getVch_from().equalsIgnoreCase("FL3")) {
				
				this.fromliclist = impl.fromliclistImpl(this);


				// System.out.println("ijhfejrfeijfei");

			}*/

			/*else {
			}*/

		} catch (Exception e) {

		}

		return "";
	}
	
	
	public String listMethod(ValueChangeEvent vce) {
		String val = (String) vce.getNewValue();
		this.vch_from_lic_no = val;

		if (this.vch_from_lic_no==null || this.vch_from_lic_no.equalsIgnoreCase(""))
			this.lic_disable_flag2 = false;
		else
			this.lic_disable_flag2 = true;
		return "";
	}
	
	
ArrayList getAll_List=new ArrayList();
	
	
	public ArrayList getGetAll_List() {
		if(this.vch_from !=null && this.vch_from.length()>0)
		{
			this.getAll_List=impl.fromliclistImpl(this);
		}
		return getAll_List;
	}



	public void setGetAll_List(ArrayList getAll_List) {
		this.getAll_List = getAll_List;
	}

	
	
	public void print()
	{
		if (isValidateInput()) {
			

		
			
			if(this.vch_from.equalsIgnoreCase("CL")){
				impl.printCL(this);
			}else{
			    impl.print(this);
			}
		}
		else{}
		
		
	}
	
	private boolean validateInput;

	public boolean isValidateInput() {
		
		  this.validateInput=true;
		  
		  if(!(Validate.validateStrReq("Radio",this.getVch_from())))validateInput=false;
		  if(!this.vch_from.equalsIgnoreCase("CL")){
			  if(!(Validate.validateStrReq("lic_no",this.getLic_id())))validateInput=false;  
		  }
		  
		 
		
			//else if (!(Validate.validateStrReq("Radio", this.getBwfl_FL2d_Id())))validateInput=false; 
		  
		

		
		return validateInput;
	}



	public void setValidateInput(boolean validateInput) {
		this.validateInput = validateInput;
	}
private String lic_id;
public String getLic_id() {
	return lic_id;
}


public void setLic_id(String lic_id) {
	this.lic_id = lic_id;
}


   public void reset(){
	 
			this.printFlag = false;
			this.pdfname = null;
			this.excelFlag=false;
			this.exlname=null;
            this.lic_id=null;
            this.vch_from=null;
		
	   
   }
   
 //============================
   
	public void excel() {

		try {
			if (isValidateInput()) {
				
                if(this.vch_from.equalsIgnoreCase("CL")){
				
                	impl.generateexcelCL(this);
				}
                else
                {
				    impl.generateexcel(this);
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								" Please Select All Fields !!",
								" Please Select All Fields !!"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}


	}
}

