package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.UIDataTable;

import com.mentor.datatable.IndentStatusMISDT;
import com.mentor.impl.IndentStatusMISImpl;

public class IndentStatusMISAction {

	IndentStatusMISImpl impl = new IndentStatusMISImpl();

	private String hidden;
	private String indentNmbr;
	private int wholesaleId;
	private String type;
	private String radioType = "P";
	private ArrayList indentDetailsList = new ArrayList();

	private String cate_type;
	private ArrayList dropdownList = new ArrayList();
	private String dropdown="";
 
	public String getCate_type() {
		return cate_type;
	}

	public void setCate_type(String cate_type) {
		this.cate_type = cate_type;
	}

	public void cate_typeLisnr(ValueChangeEvent ep) {

		try {
			System.out.println("2-"+this.radioType);
			String val = (String) ep.getNewValue();
			this.cate_type = val;
			this.dropdown="0";
			 
			this.dropdownList = impl.getList(val);
			if(!this.dropdown.equalsIgnoreCase("0") && this.dropdown!=null){
				this.indentDetailsList = impl.displayIndentDetails(this);
			}
			
			 
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	public void dropdownLisnr(ValueChangeEvent es) {

		try {
			System.out.println("1-"+this.radioType);
			 String val = (String) es.getNewValue();
			 this.dropdown = val;
			 if(!this.dropdown.equalsIgnoreCase("0") && this.dropdown!=null){
				 this.indentDetailsList = impl.displayIndentDetails(this);
			 }
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
 	public ArrayList getDropdownList() {
		return dropdownList;
	}

	public void setDropdownList(ArrayList dropdownList) {
		this.dropdownList = dropdownList;
	}

	public String getDropdown() {
		return dropdown;
	}

	public void setDropdown(String dropdown) {
		this.dropdown = dropdown;
	}

	public String getRadioType() {
		return radioType;
	}

	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public String getIndentNmbr() {
		return indentNmbr;
	}

	public void setIndentNmbr(String indentNmbr) {
		this.indentNmbr = indentNmbr;
	}

	public int getWholesaleId() {
		return wholesaleId;
	}

	public void setWholesaleId(int wholesaleId) {
		this.wholesaleId = wholesaleId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList getIndentDetailsList() {
		try {
			/*
			 * if (this.listFlagForPrint == true) { this.indentDetailsList =
			 * impl.displayIndentDetails(this); this.listFlagForPrint = false; }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indentDetailsList;
	}

	public void setIndentDetailsList(ArrayList indentDetailsList) {
		this.indentDetailsList = indentDetailsList;
	}

	public void radioListener(ValueChangeEvent e) {

		try {
			String val = (String) e.getNewValue();
			this.setRadioType(val);
			this.viewEditDtaFlg = false;
			this.viewPanelFlg = false;
			// this.listFlagForPrint = true;
			this.indentDetailsList = impl.displayIndentDetails(this);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	private boolean viewPanelFlg;

	public boolean isViewPanelFlg() {
		return viewPanelFlg;
	}

	public void setViewPanelFlg(boolean viewPanelFlg) {
		this.viewPanelFlg = viewPanelFlg;
	}

	public void viewObjDetails(ActionEvent e) {
		try {

			UIDataTable uiTable = (UIDataTable) e.getComponent().getParent()
					.getParent();
			IndentStatusMISDT dt = (IndentStatusMISDT) this
					.getIndentDetailsList().get(uiTable.getRowIndex());

			this.viewPanelFlg = true;

			this.setWholesaleId(dt.getWholesaleId_dt());
			this.setIndentNmbr(dt.getIndentNmbr_dt());
			this.setType(dt.getType_dt());

			this.setObjectionID(dt.getObjectionID_dt());
			this.setObjIssue(dt.getObjIssue_dt());
			this.setObjDescription(dt.getObjDescription_dt());
			this.setObjDate_dt(dt.getObjDate_dt());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// -------------raise objection popup------------------

	private int objectionID;
	private String objDescription;
	private String objIssue;
	private String objRemark;
	private String popupHidden;
	private String objDate_dt;

	public String getPopupHidden() {
		try {
			impl.viewObjectionImpl(this);
		} catch (Exception e) {

		}
		return popupHidden;
	}

	public void setPopupHidden(String popupHidden) {
		this.popupHidden = popupHidden;
	}

	public int getObjectionID() {
		return objectionID;
	}

	public void setObjectionID(int objectionID) {
		this.objectionID = objectionID;
	}

	public String getObjDescription() {
		return objDescription;
	}

	public void setObjDescription(String objDescription) {
		this.objDescription = objDescription;
	}

	public String getObjIssue() {
		return objIssue;
	}

	public void setObjIssue(String objIssue) {
		this.objIssue = objIssue;
	}

	public String getObjRemark() {
		return objRemark;
	}

	public void setObjRemark(String objRemark) {
		this.objRemark = objRemark;
	}

	public String getObjDate_dt() {
		return objDate_dt;
	}

	public void setObjDate_dt(String objDate_dt) {
		this.objDate_dt = objDate_dt;
	}

	public void clear_objection() {

		try {
			if (this.objRemark != null && this.objRemark.length() > 0) {
				impl.clearObjectionImpl(this);
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" Please Fill Remarks !!",
								"Please Fill Remarks !!"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void cancel_objection() {

		try {
			if (this.objRemark != null && this.objRemark.length() > 0) {
				impl.cancelObjectionImpl(this);
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" Please Fill Remarks !!",
								"Please Fill Remarks !!"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean listFlagForPrint = true;
	private String pdfName;

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public boolean isListFlagForPrint() {
		return listFlagForPrint;
	}

	public void setListFlagForPrint(boolean listFlagForPrint) {
		this.listFlagForPrint = listFlagForPrint;
	}

	public void printReport(ActionEvent e) {

		UIDataTable uiTable = (UIDataTable) e.getComponent().getParent()
				.getParent();
		IndentStatusMISDT dt = (IndentStatusMISDT) this.getIndentDetailsList()
				.get(uiTable.getRowIndex());

		this.setWholesaleId(dt.getWholesaleId_dt());
		this.setIndentNmbr(dt.getIndentNmbr_dt());
		this.setType(dt.getType_dt());

		if (impl.printReport(this, dt) == true) {

			dt.setPrintFlag(true);

		} else {
			dt.setPrintFlag(false);

		}

	}

	private boolean viewEditDtaFlg;

	public boolean isViewEditDtaFlg() {
		return viewEditDtaFlg;
	}

	public void setViewEditDtaFlg(boolean viewEditDtaFlg) {
		this.viewEditDtaFlg = viewEditDtaFlg;
	}

	public void modifyDetails(ActionEvent e) {
		try {

			UIDataTable uiTable = (UIDataTable) e.getComponent().getParent()
					.getParent();
			IndentStatusMISDT dt = (IndentStatusMISDT) this
					.getIndentDetailsList().get(uiTable.getRowIndex());

			this.viewEditDtaFlg = true;

			this.setWholesaleId(dt.getWholesaleId_dt());
			this.setIndentNmbr(dt.getIndentNmbr_dt());
			this.setType(dt.getType_dt());
			this.setLic_type(dt.getLicenseType_dt());

			if (dt.getType_dt().equalsIgnoreCase("D")) {
				this.unit_type = "Distillery";
			} else if (dt.getType_dt().equalsIgnoreCase("B")) {
				this.unit_type = "Brewery";
			} else if (dt.getType_dt().equalsIgnoreCase("BWFL")) {
				this.setBwfl_unit_id(dt.getBwfl_unit_id());
				this.unit_type = "BWFL";
			} else if (dt.getType_dt().equalsIgnoreCase("FL2D")) {
				this.unit_type = "FL2D";
			}

			this.setUnit_name(dt.getUnit_name());
			this.setDraft_no(dt.getInstrumentNo_dt());
			this.setPayment_Date(dt.getPaymentDate_dt());
			this.setBank_name(dt.getBank_name());
			// this.setTot_cast(dt.getAmunt());
			this.setPay_mode(dt.getVch_mood_pay());
			this.setTot_cast(dt.getAmunt());
			this.setUnit_id(dt.getUnitId_dt());
			this.setAmount_pay(dt.getPaidAmount_dt());
			this.setIndentDate(dt.getIndentDate());
			// this.SetIndentNmbr(dt.getIndentNmbr_dt());

			this.showBrandList = impl.viewdetailImpl1(this);

			// this.setTot_cast(dt.get)

			/*
			 * this.setObjectionID(dt.getObjectionID_dt());
			 * this.setObjIssue(dt.getObjIssue_dt());
			 * this.setObjDescription(dt.getObjDescription_dt());
			 * this.setObjDate_dt(dt.getObjDate_dt());
			 */

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void reset() {
		this.indentDetailsList = impl.displayIndentDetails(this);
		this.viewPanelFlg = false;
		this.viewEditDtaFlg = false;
		this.wholesaleId = 0;
		this.indentNmbr = null;
		this.type = null;
		this.objectionID = 0;
		this.objDate_dt = null;
		this.objIssue = null;
		this.objDescription = null;
		this.objRemark = null;
		this.modifyRemark = null;
		this.amount_pay = 0.0;
		this.bank_name = null;
		this.lic_type = null;
		this.unit_name = null;
		this.tot_cast = 0.0;
		this.pay_mode = null;
		this.amount_pay = 0.0;
		this.payment_Date = null;
		this.draft_no = null;
		this.unit_id = 0;
		this.showBrandList.clear();
		this.totalCases = 0;
		this.totalMRP = 0;
		this.dataFalg = true;
		this.rejectRemark = null;
	}

	// faizal----------------------

	private String lic_type;
	private String unit_type;
	private String unit_name;
	private Double tot_cast;
	private String pay_mode;
	private Double amount_pay;
	private ArrayList showBrandList = new ArrayList();
	private String payment_Date;
	private String draft_no;
	private String bank_name;
	private int unit_id;
	private int bwfl_unit_id;

	public int getBwfl_unit_id() {
		return bwfl_unit_id;
	}

	public void setBwfl_unit_id(int bwfl_unit_id) {
		this.bwfl_unit_id = bwfl_unit_id;
	}

	public int getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(int unit_id) {
		this.unit_id = unit_id;
	}

	public String getPayment_Date() {
		return payment_Date;
	}

	public void setPayment_Date(String payment_Date) {
		this.payment_Date = payment_Date;
	}

	public String getDraft_no() {
		return draft_no;
	}

	public void setDraft_no(String draft_no) {
		this.draft_no = draft_no;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	private boolean dataFalg = true;

	public boolean isDataFalg() {
		return dataFalg;
	}

	public void setDataFalg(boolean dataFalg) {
		this.dataFalg = dataFalg;
	}

	public ArrayList getShowBrandList() {

		if (this.indentNmbr != null && dataFalg == true) {

			this.showBrandList = impl.viewdetailImpl1(this);
			dataFalg = false;
		}

		return showBrandList;
	}

	public void setShowBrandList(ArrayList showBrandList) {
		this.showBrandList = showBrandList;
	}

	public String getLic_type() {
		return lic_type;
	}

	public void setLic_type(String lic_type) {
		this.lic_type = lic_type;
	}

	public String getUnit_type() {
		return unit_type;
	}

	public void setUnit_type(String unit_type) {
		this.unit_type = unit_type;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public Double getTot_cast() {
		return tot_cast;
	}

	public void setTot_cast(Double tot_cast) {
		this.tot_cast = tot_cast;
	}

	public String getPay_mode() {
		return pay_mode;
	}

	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
	}

	public Double getAmount_pay() {
		return amount_pay;
	}

	public void setAmount_pay(Double amount_pay) {
		this.amount_pay = amount_pay;
	}

	private int sumCase;
	private int totalCases;

	public int getSumCase() {
		return sumCase;
	}

	public void setSumCase(int sumCase) {
		this.sumCase = sumCase;
	}

	public void calculateTotalCases(ActionEvent ae) {

		this.setSumCase(0);
		for (int i = 0; i < this.showBrandList.size(); i++) {
			IndentStatusMISDT dt = (IndentStatusMISDT) this.showBrandList
					.get(i);

			if (dt.getNoOfCases_dt() != dt.getNewBoxes_no_Temp()
					|| dt.getPckgId_dt() != dt.getNewPckg_Id()) {
				this.setSumCase(this.getSumCase() + dt.getNewBoxes_no_Temp());
			} else {
				this.setSumCase(this.getSumCase() + dt.getNoOfCases_dt());
			}

		}

	}

	public int getTotalCases() {
		int cases = 0;
		try {

			for (int i = 0; i < this.showBrandList.size(); i++) {
				IndentStatusMISDT dt = (IndentStatusMISDT) this.showBrandList
						.get(i);
				if (dt.getNoOfCases_dt() != dt.getNewBoxes_no_Temp()
						|| dt.getPckgId_dt() != dt.getNewPckg_Id()) {
					cases += dt.getNewBoxes_no_Temp();
				} else {
					cases += dt.getNoOfCases_dt();
				}

			}

			totalCases = cases;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	private boolean validate;
	private Date indentDate;
	private String modifyRemark;

	public Date getIndentDate() {
		return indentDate;
	}

	public void setIndentDate(Date indentDate) {
		this.indentDate = indentDate;
	}

	public String getModifyRemark() {
		return modifyRemark;
	}

	public void setModifyRemark(String modifyRemark) {
		this.modifyRemark = modifyRemark;
	}

	public boolean isValidate() {
		this.validate = true;

		try {

			int sumBottles = 0;
			int sumBoxes = 0;

			if (this.totalMRP > this.oldTotalMRP) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Total MRP should not be greater than Old MRP !! ",
										"Total MRP should not be greater than Old MRP !!"));
				validate = false;
			}

			/*
			 * if (this.showBrandList.size() > 0) { for (int i = 0; i <
			 * this.showBrandList.size(); i++) { IndentStatusMISDT table = new
			 * IndentStatusMISDT(); table = (IndentStatusMISDT)
			 * showBrandList.get(i);
			 * 
			 * sumBottles += table.getNewBottles_no(); sumBoxes +=
			 * table.getNewBoxes_no();
			 * 
			 * if (table.getNewBoxes_no_Temp()==0) {
			 * FacesContext.getCurrentInstance().addMessage(null,new
			 * FacesMessage(FacesMessage.SEVERITY_ERROR,
			 * "Boxes Should Not Be Zero !! ","Boxes Should Not Be Zero !!"));
			 * validate = false; }
			 * 
			 * }
			 * 
			 * 
			 * }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String updateData() {
		try {
			if (isValidate()) {
				if (this.modifyRemark != null && this.modifyRemark.length() > 0) {
					impl.updateData(this);
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Please Fill Remarks!!",
									"Please Fill Remarks!!"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public double mrptot = 0.0;

	public double getMrptot() {
		return mrptot;
	}

	public void setMrptot(double mrptot) {
		this.mrptot = mrptot;
	}

	public void calculateTotalmrp(ActionEvent ae) {

		this.setMrptot(0.0);
		for (int i = 0; i < this.showBrandList.size(); i++) {
			IndentStatusMISDT dt = (IndentStatusMISDT) this.showBrandList
					.get(i);

			if (dt.getNoOfCases_dt() != dt.getNewBoxes_no_Temp()
					|| dt.getPckgId_dt() != dt.getNewPckg_Id()) {

				mrptot += dt.getNewBottles_no() * dt.getNewRounded_mrp();

			} else {

				mrptot += dt.getNoOfBottles_dt() * dt.getNewRounded_mrp();

			}

			// this.setMrptot(this.getMrptot() + dt.getNewRounded_mrp());

		}

	}

	private long totalMRP = 0;
	private long oldTotalMRP = 0;

	public long getTotalMRP() {

		long duty = 0;
		try {

			for (int i = 0; i < this.showBrandList.size(); i++) {
				IndentStatusMISDT dt = (IndentStatusMISDT) this.showBrandList
						.get(i);

				if (dt.getNoOfCases_dt() != dt.getNewBoxes_no_Temp()
						|| dt.getPckgId_dt() != dt.getNewPckg_Id()) {
					duty += dt.getNewBottles_no() * dt.getNewRounded_mrp();

				} else {
					duty += dt.getNoOfBottles_dt() * dt.getNewRounded_mrp();
				}

			}

			this.totalMRP = duty;

			// //System.out.println("mrrrrrrrrrr----------------"+this.totalMRP);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalMRP;
	}

	public void setTotalMRP(long totalMRP) {
		this.totalMRP = totalMRP;
	}

	public long getOldTotalMRP() {
		return oldTotalMRP;
	}

	public void setOldTotalMRP(long oldTotalMRP) {
		this.oldTotalMRP = oldTotalMRP;
	}

	/*
	 * private double nag_cost = 0.0;
	 * 
	 * 
	 * DecimalFormat df2 = new DecimalFormat("#.##"); NumberFormat formatter =
	 * new DecimalFormat("#0.00");
	 * 
	 * public double getNag_cost() { long duty = 0; try {
	 * 
	 * for (int i = 0; i < this.showBrandList.size(); i++) { IndentStatusMISDT
	 * dt = (IndentStatusMISDT) this.showBrandList.get(i);
	 * 
	 * if (dt.getNoOfCases_dt() != dt.getNewBoxes_no_Temp() || dt.getPckgId_dt()
	 * != dt.getNewPckg_Id()) { duty += dt.getNewBottles_no() *
	 * dt.getNewRounded_mrp();
	 * 
	 * } else { duty += dt.getNoOfBottles_dt() * dt.getNewRounded_mrp(); }
	 * 
	 * }
	 * 
	 * this.nag_cost = duty;
	 * 
	 * 
	 * //System.out.println("dutyy----------------"+nag_cost);
	 * //nag_cost=Double.parseDouble(formatter.format(duty));
	 * //this.nag_cost=Double.parseDouble(df2.format(duty));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return nag_cost; }
	 * 
	 * public void setNag_cost(double nag_cost) { this.nag_cost = nag_cost; }
	 */

	public String addRowMethodBranding() {

		IndentStatusMISDT dt = new IndentStatusMISDT();
		dt.setSlno(showBrandList.size() + 1);
		showBrandList.add(dt);

		return "";
	}

	public void deleteRowMethodBranding(ActionEvent e) {
		UIDataTable uiTable = (UIDataTable) e.getComponent().getParent()
				.getParent();
		IndentStatusMISDT dt = (IndentStatusMISDT) this.getShowBrandList().get(
				uiTable.getRowIndex());
		this.showBrandList.remove(dt);
		if (this.getShowBrandList().size() > 0) {
			if (impl.deleteBrandPckg(dt, this) == true) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("Brand Deleted Successfully",
								"Brand Deleted Successfully"));
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("Brand Not Deleted",
								"Brand Not Deleted"));
			}
		} else {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									"You have only one Brand on this Indent.So it cannot be deleted !!",
									"You have only one Brand on this Indent.So it cannot be deleted !!"));
		}

	}

	private boolean deleteFlag = false;

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void getRowValue(ActionEvent e) {
		try {
			UIDataTable uiTable = (UIDataTable) e.getComponent().getParent()
					.getParent();
			IndentStatusMISDT dt = (IndentStatusMISDT) this
					.getIndentDetailsList().get(uiTable.getRowIndex());

			this.setWholesaleId(dt.getWholesaleId_dt());
			this.setIndentNmbr(dt.getIndentNmbr_dt());
			this.setType(dt.getType_dt());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ==================rejection of indent=========================

	private String rejectRemark;

	public String getRejectRemark() {
		return rejectRemark;
	}

	public void setRejectRemark(String rejectRemark) {
		this.rejectRemark = rejectRemark;
	}

	public void rejectIndent() {

		try {
			if (this.rejectRemark != null && this.rejectRemark.length() > 0) {
				impl.reject_IndentImpl(this);
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" Please Fill Rejection Remark !!",
								"Please Fill Rejection Remark !!"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
 

}
