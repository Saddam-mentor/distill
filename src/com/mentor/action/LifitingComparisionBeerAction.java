package com.mentor.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.LifitingComparisionBeerImpl; 

public class LifitingComparisionBeerAction {

	LifitingComparisionBeerImpl impl = new LifitingComparisionBeerImpl();
	private boolean printFlag;
	private String pdfName;

	private String radio = "S";

	private String distid = "99";
	private String qtrFlag;
	private String monthFlag;
	public String getQtrFlag() {
		return qtrFlag;
	}

	public String getMonthFlag() {
		return monthFlag;
	}

	public void setQtrFlag(String qtrFlag) {
		this.qtrFlag = qtrFlag;
	}

	public void setMonthFlag(String monthFlag) {
		this.monthFlag = monthFlag;
	}

	public String getDistid() {
		return distid;
	}

	public void setDistid(String distid) {
		this.distid = distid;
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

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public void radioListener(ValueChangeEvent e) {

		this.distid = "99";

		String o = (String) e.getNewValue();
		try {

			if (o.length() > 0 || o != null) {

				this.printFlag = false;
				// this.shopList = impl.getShop(this, o);

				this.sectorList = impl.getSectorList(this, o);
				this.shopList = impl.getShopL(this, o);

				this.setDistid(o);

			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}

	}

	public void radioListenerSector(ValueChangeEvent e) {
		String o = (String) e.getNewValue();
		try {
			if (o == null) {
				 
			}

			else {

				  

				this.printFlag = false;

				this.shopList = impl.getShop(this, o);

			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	public void shopname(ValueChangeEvent e) {

	}

	private String shopId = "0";

	private ArrayList shopList = new ArrayList();

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public ArrayList getShopList() {
		
		
		return shopList;
	}

	public void setShopList(ArrayList shopList) {
		this.shopList = shopList;
	}

	private boolean excelFlag = false;
	private String exlname;

	public boolean isExcelFlag() {
		return excelFlag;
	}

	public void setExcelFlag(boolean excelFlag) {
		this.excelFlag = excelFlag;
	}

	public String getExlname() {
		return exlname;
	}

	public void setExlname(String exlname) {
		this.exlname = exlname;
	}

	private ArrayList sectorList = new ArrayList();

	public void setSectorList(ArrayList sectorList) {
		this.sectorList = sectorList;
	}

	public ArrayList getSectorList() {

		return sectorList;
	}

	private String sectorId = "0";

	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}

	private ArrayList districtList = new ArrayList();

	public ArrayList getDistrictList() {
		this.districtList = impl.getDistList();
		return districtList;
	}

	public void setDistrictList(ArrayList districtList) {
		this.districtList = districtList;
	}

	private int selectedMonth = 0;
	private ArrayList monthList = new ArrayList();

	public int getSelectedMonth() {

		return selectedMonth;
	}

	public void setSelectedMonth(int selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public ArrayList getMonthList() {
		this.monthList = impl.selectedMonth();
		return monthList;
	}

	public void setMonthList(ArrayList monthList) {
		this.monthList = monthList;
	}

	
	
	
	private int selectedQuarter = 0;

	public int getSelectedQuarter() {
		return selectedQuarter;
	}

	public void setSelectedQuarter(int selectedQuarter) {
		this.selectedQuarter = selectedQuarter;
	}
	/*
	 * public void monthListener(ValueChangeEvent ve) { //String
	 * month=(String)ve.getNewValue();
	 * 
	 * this.setSelectedMonth((Integer)ve.getNewValue());
	 * 
	 * 
	 * 
	 * if(this.getSelectedMonth()==1) { this.dateSelected="31/01/2020";
	 * this.setMonthName("January"); }
	 * 
	 * if(this.getSelectedMonth()==2) { this.dateSelected="28/02/2020";
	 * this.setMonthName("February"); }
	 * 
	 * if(this.getSelectedMonth()==3) { this.dateSelected="31/03/2020";
	 * this.setMonthName("March"); }
	 * 
	 * if(this.getSelectedMonth()==4) { this.dateSelected="30/04/2020";
	 * this.setMonthName("April"); }
	 * 
	 * else if(this.getSelectedMonth()==5) { this.setDateSelected("31/05/2020");
	 * this.setMonthName("May");
	 * 
	 * }
	 * 
	 * else if(this.getSelectedMonth()==6) { this.dateSelected="30/06/2020";
	 * this.setMonthName("June"); }
	 * 
	 * else if(this.getSelectedMonth()==7) { this.dateSelected="31/07/2020";
	 * this.setMonthName("July"); }
	 * 
	 * else if(this.getSelectedMonth()==8) { this.dateSelected="31/08/2020";
	 * this.setMonthName("August"); } else if(this.getSelectedMonth()==9) {
	 * this.dateSelected="30/09/2020"; this.setMonthName("September"); } else
	 * if(this.getSelectedMonth()==10) { this.dateSelected="31/10/2020";
	 * this.setMonthName("October"); } else if(this.getSelectedMonth()==11) {
	 * this.dateSelected="30/11/2020"; this.setMonthName("November"); } else
	 * if(this.getSelectedMonth()==12) { this.dateSelected="31/12/2020";
	 * this.setMonthName("December"); }
	 * 
	 * }
	 */
	public void monthListener(ValueChangeEvent ve) {
		// String month=(String)ve.getNewValue();

		this.setSelectedMonth((Integer) ve.getNewValue());
if(this.getYear().equalsIgnoreCase("19_20")) {

	if (this.getSelectedMonth() == 1) {
		this.dateSelected = "2020/01/31";
		this.setMonthName("January");
	}

	if (this.getSelectedMonth() == 2) {
		this.dateSelected = "2020/02/28";
		this.setMonthName("February");
	}

	if (this.getSelectedMonth() == 3) {
		this.dateSelected = "2020/03/31";
		this.setMonthName("March");
	}

	if (this.getSelectedMonth() == 4) {
		this.dateSelected = "2020/04/30";
		this.setMonthName("April");
	}

	else if (this.getSelectedMonth() == 5) {
		this.setDateSelected("2019/05/31");
		this.setMonthName("May");

	}

	else if (this.getSelectedMonth() == 6) {
		this.dateSelected = "2019/06/30";
		this.setMonthName("June");
	}

	else if (this.getSelectedMonth() == 7) {
		this.dateSelected = "2019/07/31";
		this.setMonthName("July");
	}

	else if (this.getSelectedMonth() == 8) {
		this.dateSelected = "2019/08/31";
		this.setMonthName("August");
	} else if (this.getSelectedMonth() == 9) {
		this.dateSelected = "2019/09/30";
		this.setMonthName("September");
	} else if (this.getSelectedMonth() == 10) {
		this.dateSelected = "2019/10/31";
		this.setMonthName("October");
	} else if (this.getSelectedMonth() == 11) {
		this.dateSelected = "2019/11/30";
		this.setMonthName("November");
	} else if (this.getSelectedMonth() == 12) {
		this.dateSelected = "2019/12/31";
		this.setMonthName("December");
	}

	
	this.setQtrFlag("F");
	this.selectedQuarter = 0;

	
}else {
		if (this.getSelectedMonth() == 1) {
			this.dateSelected = "2021/01/31";
			this.setMonthName("January");
		}

		if (this.getSelectedMonth() == 2) {
			this.dateSelected = "2021/02/28";
			this.setMonthName("February");
		}

		if (this.getSelectedMonth() == 3) {
			this.dateSelected = "2021/03/31";
			this.setMonthName("March");
		}

		if (this.getSelectedMonth() == 4) {
			this.dateSelected = "2021/04/30";
			this.setMonthName("April");
		}

		else if (this.getSelectedMonth() == 5) {
			this.setDateSelected("2020/05/31");
			this.setMonthName("May");

		}

		else if (this.getSelectedMonth() == 6) {
			this.dateSelected = "2020/06/30";
			this.setMonthName("June");
		}

		else if (this.getSelectedMonth() == 7) {
			this.dateSelected = "2020/07/31";
			this.setMonthName("July");
		}

		else if (this.getSelectedMonth() == 8) {
			this.dateSelected = "2020/08/31";
			this.setMonthName("August");
		} else if (this.getSelectedMonth() == 9) {
			this.dateSelected = "2020/09/30";
			this.setMonthName("September");
		} else if (this.getSelectedMonth() == 10) {
			this.dateSelected = "2020/10/31";
			this.setMonthName("October");
		} else if (this.getSelectedMonth() == 11) {
			this.dateSelected = "2020/11/30";
			this.setMonthName("November");
		} else if (this.getSelectedMonth() == 12) {
			this.dateSelected = "2020/12/31";
			this.setMonthName("December");
		}

		
		this.setQtrFlag("F");
		this.selectedQuarter = 0;
	}
	}
	
	
	public void quarterListener(ValueChangeEvent e ){
		
		
		this.setSelectedQuarter((Integer) e.getNewValue());
		
		this.setMonthFlag("F");
		this.setSelectedMonth(0);
		
		System.out.println("Selected Quarter----"+this.getSelectedQuarter());
		System.out.println("Selected Month ====" + this.getSelectedMonth());
	}
	
	

	private String dateSelected = null;

	public String getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(String dateSelected) {
		this.dateSelected = dateSelected;
	}

	private String monthName;

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public void reset() {
		this.printFlag = false;
		this.excelFlag = false;
		this.pdfName = null;
		this.shopList.clear();
		this.sectorList.clear();
		this.monthName = "";
		this.dateSelected = "";
		this.selectedMonth = 0;
	}

	public void print() {
		if(this.getYear().equalsIgnoreCase("19_20")) {
 if (this.radioDwCons.equalsIgnoreCase("D")) {
			if (this.selectedMonth > 0 && this.selectedQuarter>0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Kindly Select Any One Month Or Quarter !!",
								"Kindly Select Any One Month Or Quarter!!"));
			}
			else{
				if (this.selectedMonth> 0 || this.selectedQuarter>0) {
				//System.out.println("---"+this.distid+"--");
				//if (!this.getDistid().equalsIgnoreCase("99") && !this.getDistid().equalsIgnoreCase("9999")) {
					
					if (this.radioDwCons.equalsIgnoreCase("D")) {
						impl.printReportShopWise(this);
					} else {
						impl.printReportDistrictWise(this);
					}

			
			/*	}else{
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Kindly Select District!!",
									"Kindly Select District!!"));
				}*/

		
			}
			else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Kindly Select Any One Month or Quarter!!",
				                "Kindly Select Any One Month or Quarter!!"));
			}
		}} else {
			impl.printReportDistrictWise(this);
		}}else {

			 if (this.radioDwCons.equalsIgnoreCase("D")) {
						if (this.selectedMonth > 0 && this.selectedQuarter>0) {
							FacesContext.getCurrentInstance().addMessage(
									null,
									new FacesMessage(FacesMessage.SEVERITY_ERROR,
											"Kindly Select Any One Month Or Quarter !!",
											"Kindly Select Any One Month Or Quarter!!"));
						}
						else{
							if (this.selectedMonth> 0 || this.selectedQuarter>0) {
							//System.out.println("---"+this.distid+"--");
							//if (!this.getDistid().equalsIgnoreCase("99") && !this.getDistid().equalsIgnoreCase("9999")) {
								
								if (this.radioDwCons.equalsIgnoreCase("D")) {
									impl.printReportShopWise2020(this);
								} else {
									impl.printReportDistrictWise2020(this);
								}

						
							/*}else{
								FacesContext.getCurrentInstance().addMessage(
										null,
										new FacesMessage(FacesMessage.SEVERITY_ERROR,
												"Kindly Select District!!",
												"Kindly Select District!!"));
							}*/

					
						}
						else {
							FacesContext.getCurrentInstance().addMessage(
									null,
									new FacesMessage(FacesMessage.SEVERITY_ERROR,
											"Kindly Select Any One Month or Quarter!!",
							                "Kindly Select Any One Month or Quarter!!"));
						}
					}} else {
						impl.printReportDistrictWise2020(this);
					}
		}
			
	}

	public void excel() throws ParseException {
		if(this.getYear().equalsIgnoreCase("19_20")) {
		if (this.radioDwCons.equalsIgnoreCase("D")) {
		if (this.selectedMonth > 0 && this.selectedQuarter>0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Kindly Select Any One Month Or Quarter !!",
							"Kindly Select Any One Month Or Quarter!!"));
		}
		else   {if  (this.selectedMonth > 0 || this.selectedQuarter>0) {
		//	System.out.println("---"+this.distid+"--");
			//if (!this.getDistid().equalsIgnoreCase("99") && !this.getDistid().equalsIgnoreCase("9999")) {
				
			if (this.radioDwCons.equalsIgnoreCase("D")) {
				impl.excelShopWise(this);
			} else {
				impl.excelDitrictWise(this);
			}
	/*		}
		 else{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Kindly Select District!!",
							"Kindly Select District!!"));
		}*/
		} 
			else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Kindly Select Any One Month or Quarter!!", "Kindly Select Any One Month or Quarter!!"));
		}
		}
		} else {
			impl.excelDitrictWise(this);
		}}else{

			if (this.radioDwCons.equalsIgnoreCase("D")) {
			if (this.selectedMonth > 0 && this.selectedQuarter>0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Kindly Select Any One Month Or Quarter !!",
								"Kindly Select Any One Month Or Quarter!!"));
			}
			else   {if  (this.selectedMonth > 0 || this.selectedQuarter>0) {
			//	System.out.println("---"+this.distid+"--");
				//if (!this.getDistid().equalsIgnoreCase("99") && !this.getDistid().equalsIgnoreCase("9999")) {
					
				if (this.radioDwCons.equalsIgnoreCase("D")) {
					impl.excelShopWise2020(this);
				} else {
					impl.excelDitrictWise2020(this);
				}
//				}
//			 else{
//				FacesContext.getCurrentInstance().addMessage(
//						null,
//						new FacesMessage(FacesMessage.SEVERITY_ERROR,
//								"Kindly Select District!!",
//								"Kindly Select District!!"));
//			}
			} 
				else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Kindly Select Any One Month or Quarter!!", "Kindly Select Any One Month or Quarter!!"));
			}
			}
			} else {
				impl.excelDitrictWise2020(this);
			}
		}
	}

	private String radioDwCons="D";

	public String getRadioDwCons() {
		return radioDwCons;
	}

	public void setRadioDwCons(String radioDwCons) {
		this.radioDwCons = radioDwCons;
	}

	public void chngval(ValueChangeEvent ee) {

	}
//=================================================
	
	private String Year;
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

    public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
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
