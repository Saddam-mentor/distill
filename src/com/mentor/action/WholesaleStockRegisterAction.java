package com.mentor.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.WholesaleStockRegisterImpl;
import com.mentor.utility.ResourceUtil;

public class WholesaleStockRegisterAction {
	
	
	WholesaleStockRegisterImpl impl = new WholesaleStockRegisterImpl();

	private String hidden;
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	private boolean excelFlag = false;
	private String radio = "FL2";
	private String exlName;
	private String wholesaleId;
	private String wholesaleName;
	private boolean districtFlag;
	private boolean wsFlag;
	private String districtId;
	private String districtName;
	private ArrayList districtList = new ArrayList();
	private ArrayList wholesaleList = new ArrayList();

	public String getHidden() {
		try {
			
			
	
		String role=impl.getUserRole(ResourceUtil.getUserNameAllReq());
			
			if(role.equals("207"))
			{
				
				this.districtList = impl.getDistList1("207");
				this.districtFlag = true;
				this.wsFlag = true;
			}
			if(role.equals("199"))
			{
				this.districtList = impl.getDistList1("199");
				this.districtFlag = true;
				this.wsFlag = true;
			}else if(impl.getWs(this)){
				this.districtFlag = false;
				this.wsFlag = false;
			}
			else if(!role.equals("199")&&!role.equals("207"))
			{
				this.districtList = impl.getDistList1("NA");
				this.districtFlag = true;
				this.wsFlag = true;
			}
			
			else if(role.equals("NA"))
			{
				this.districtList = impl.getDistList1("NA");
				this.districtFlag = true;
				this.wsFlag = true;
			}
			
			
			
			
			/*if (ResourceUtil.getUserNameAllReq().trim().length()>=10 && ResourceUtil.getUserNameAllReq().trim().substring(0, 10).equalsIgnoreCase("Excise-DEO")) 
			{
				impl.getDetails(this);
				this.districtFlag = false;
				this.wsFlag = true;
			} 
			else if(impl.getWs(this)){
				this.districtFlag = false;
				this.wsFlag = false;
			}
			else 
			{
				this.districtFlag = true;
				this.wsFlag = true;
			}*/
		} catch (Exception ex) {
			ex.getMessage();
		}
		return hidden;
	}
	
	

	public boolean isWsFlag() {
		return wsFlag;
	}



	public void setWsFlag(boolean wsFlag) {
		this.wsFlag = wsFlag;
	}



	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
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

	public boolean isExcelFlag() {
		return excelFlag;
	}

	public void setExcelFlag(boolean excelFlag) {
		this.excelFlag = excelFlag;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getExlName() {
		return exlName;
	}

	public void setExlName(String exlName) {
		this.exlName = exlName;
	}

	public String getWholesaleId() {
		return wholesaleId;
	}

	public void setWholesaleId(String wholesaleId) {
		this.wholesaleId = wholesaleId;
	}

	public boolean isDistrictFlag() {
		return districtFlag;
	}

	public void setDistrictFlag(boolean districtFlag) {
		this.districtFlag = districtFlag;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	
	

	public String getWholesaleName() {
		return wholesaleName;
	}

	public void setWholesaleName(String wholesaleName) {
		this.wholesaleName = wholesaleName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public ArrayList getDistrictList() {
		try {
			//this.districtList = impl.getDistList();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return districtList;
	}

	public void setDistrictList(ArrayList districtList) {
		this.districtList = districtList;
	}

	public ArrayList getWholesaleList() {
		
		try {
			this.wholesaleList=impl.getWholesaleList(this);
		} catch (Exception e) {			
			e.printStackTrace();
		}

		return wholesaleList;
	}

	public void setWholesaleList(ArrayList wholesaleList) {
		this.wholesaleList = wholesaleList;
	}
	
	public void radioListener(ValueChangeEvent e) {

		String val = (String) e.getNewValue();		
		this.setRadio(val);
		this.printFlag = false;
		
	}
	
	public void print() {
		impl.getDetailsyr(this);
		Date st = this.getStart_dt();
		Date et = this.getEnd_dt();
		if(this.districtId.equalsIgnoreCase("9999"))
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"District is required!!", "District is required!!"));
			return;
		}
		if(this.getFromDate().before(st) || this.getToDate().after(et) || this.getToDate().before(this.getFromDate()))
		{	
		   FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Date should not be less than '"+ this.getStart_dt()+ "' And more than '"+ this.getEnd_dt()+ "' ",
								"Date should not be less than '"+ this.getStart_dt()+ "' And more than '"+ this.getEnd_dt()+ "' "));
		 }
		else
		{
		impl.printReportFL2(this);
	}
	}
	
	private Date toOpningDate;
	
	
	
	public Date getToOpningDate() throws ParseException {
		
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(expiredDate);
		this.toOpningDate = date1;
		return toOpningDate;
	}

	public void setToOpningDate(Date toOpningDate) {
		this.toOpningDate = toOpningDate;
	}


	private ArrayList showData = new ArrayList();

	public ArrayList getShowData() {
		return showData;
	}

	public void setShowData(ArrayList showData) {
		this.showData = showData;
	}

	String st = "01/04/2019";

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String expiredDate = null;

	public String getdata() throws ParseException {

		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(st);

	

			if (this.fromDate.before(date1)) {

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"From Date should not be less than 01 APRIL 2019",
								"From Date should not be less than 01 APRIL 2019"));

				this.showData.clear();

			} else {

				String currentDate = dateFormat.format(this.fromDate);
				Calendar cal = Calendar.getInstance();

				try {
					cal.setTime(dateFormat.parse(currentDate));
					cal.add(Calendar.DATE, -1);
					expiredDate = dateFormat.format(cal.getTimeInMillis());

					if(this.fromDate!=null){

					}else{
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"All Field Mandatory", "All Field Mandatory"));
					}
					
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				

			}

		

		return "";
	}
	
	
	
	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.fromDate = null;
		this.toDate = null;
		this.exlName = null;
		this.radio = "FL2" ;
		this.excelFlag = false;
		this.wholesaleId="8888";
		this.districtId="9999";

	}
/*========================Aman===========================*/
	private Date start_dt;
	private Date end_dt ;
	
	
	public Date getStart_dt() {
		return start_dt;
	}

	public void setStart_dt(Date start_dt) {
		this.start_dt = start_dt;
	}

	public Date getEnd_dt() {
		return end_dt;
	}

	public void setEnd_dt(Date end_dt) {
		this.end_dt = end_dt;
	}
		private String year;
		 
		 
	public String getYear() {
			return year;
		}



		public void setYear(String year) {
			this.year = year;
		}

	ArrayList getAll_List=new ArrayList();
		
		
		public ArrayList getGetAll_List() {
			
		this.getAll_List=impl.yearListImpl(this);
		
			return getAll_List;
		}
	  
		public void setGetAll_List(ArrayList getAll_List) {
			this.getAll_List = getAll_List;
		}	
	
	
}
