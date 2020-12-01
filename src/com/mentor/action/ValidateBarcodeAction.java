package com.mentor.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;

import org.richfaces.component.UIDataTable;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import com.mentor.datatable.NewCodeGenerationDatatable;
import com.mentor.datatable.ValidateBarcodeDT;
import com.mentor.impl.ValidateBarcodeImpl;
import com.mentor.utility.Validate;



public class ValidateBarcodeAction {

	private String errorMsg;
	private String succMsg;
	private String type;
	private String excelFilename;
	private String excelFilepath;
	private boolean uploaderFlag;
	private boolean submitFlag=true;
	private boolean cancelFlag;
	private boolean kindlyUploadFlag;
	private boolean finalsubmit;
	private int excelCases;
	
	private String radio_CL_FL_Beer="CL";
	private String fl_Dist_Bwfl2A="Tst";
	private String beer_Brewrey_Bwfl2B;
	private NewCodeGenerationDatatable dt;
	public NewCodeGenerationDatatable getDt() {
		return dt;
	}

	public void setDt(NewCodeGenerationDatatable dt) {
		this.dt = dt;
	}

	private String dist_Bwfl_brwryId ;
	private ArrayList dist_Bwfl_brwryList=new ArrayList();
	
	private boolean fl_Dist_Bwfl2A_Flag;
	private boolean beer_Brewrey_Bwfl2B_Flag;
	private boolean dist_Bwfl_brwryList_Flag=true;
	
	private boolean showdata_Flag=true;
	
	public ArrayList datatable=new ArrayList();
	
	
	
	
	
	public void getAcceptedDataTable()
	{
		
		
		try{
		//	this.datatable=new ValidateBarcodeImpl().getDataTableData(this);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList getDatatable() {
		
		
		this.datatable=new ValidateBarcodeImpl().getDataTableData(this);
		
		
		return datatable;
	}

	public void setDatatable(ArrayList datatable) {
		this.datatable = datatable;
	}

	public boolean isShowdata_Flag() {
		return showdata_Flag;
	}

	public void setShowdata_Flag(boolean showdata_Flag) {
		this.showdata_Flag = showdata_Flag;
	}

	public boolean isFl_Dist_Bwfl2A_Flag() {
		return fl_Dist_Bwfl2A_Flag;
	}

	public void setFl_Dist_Bwfl2A_Flag(boolean fl_Dist_Bwfl2A_Flag) {
		this.fl_Dist_Bwfl2A_Flag = fl_Dist_Bwfl2A_Flag;
	}

	public boolean isBeer_Brewrey_Bwfl2B_Flag() {
		return beer_Brewrey_Bwfl2B_Flag;
	}

	public void setBeer_Brewrey_Bwfl2B_Flag(boolean beer_Brewrey_Bwfl2B_Flag) {
		this.beer_Brewrey_Bwfl2B_Flag = beer_Brewrey_Bwfl2B_Flag;
	}

	public boolean isDist_Bwfl_brwryList_Flag() {
		return dist_Bwfl_brwryList_Flag;
	}

	public void setDist_Bwfl_brwryList_Flag(boolean dist_Bwfl_brwryList_Flag) {
		this.dist_Bwfl_brwryList_Flag = dist_Bwfl_brwryList_Flag;
	}

	public String getDist_Bwfl_brwryId() {
		return dist_Bwfl_brwryId;
	}

	public void setDist_Bwfl_brwryId(String dist_Bwfl_brwryId) {
		this.dist_Bwfl_brwryId = dist_Bwfl_brwryId;
	}

	public ArrayList getDist_Bwfl_brwryList() {
		try{
			if(this.radio_CL_FL_Beer !=null)
			{
				this.dist_Bwfl_brwryList=new ValidateBarcodeImpl().getDist_Brewery_BWFL_FL2DList(this);
			}
			
		}catch(Exception ex)
		{ex.printStackTrace();}
		return dist_Bwfl_brwryList;
	}

	public void setDist_Bwfl_brwryList(ArrayList dist_Bwfl_brwryList) {
		this.dist_Bwfl_brwryList = dist_Bwfl_brwryList;
	}

	public String getFl_Dist_Bwfl2A() {
		return fl_Dist_Bwfl2A;
	}

	public void setFl_Dist_Bwfl2A(String fl_Dist_Bwfl2A) {
		this.fl_Dist_Bwfl2A = fl_Dist_Bwfl2A;
	}

	public String getBeer_Brewrey_Bwfl2B() {
		return beer_Brewrey_Bwfl2B;
	}

	public void setBeer_Brewrey_Bwfl2B(String beer_Brewrey_Bwfl2B) {
		this.beer_Brewrey_Bwfl2B = beer_Brewrey_Bwfl2B;
	}

	public String getRadio_CL_FL_Beer() {
		return radio_CL_FL_Beer;
	}

	public void setRadio_CL_FL_Beer(String radio_CL_FL_Beer) {
		this.radio_CL_FL_Beer = radio_CL_FL_Beer;
	}




	public void showdta_Listnr(ValueChangeEvent e) {

		try {
			this.setShowdata_Flag(true);
			this.showdata.clear();
			
			String rt=(String)e.getNewValue();
			

		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}

	private ArrayList showdata=new ArrayList();
	

	public ArrayList getShowdata() {
		ValidateBarcodeImpl impl=new ValidateBarcodeImpl();
		if(this.dist_Bwfl_brwryId !=null && this.dist_Bwfl_brwryId.length() >0)
		{
			if(this.showdata_Flag==true)
			{
		this.showdata=impl.getdatatabale(this);
		this.showdata_Flag=false;
		}
		
		}
		
		return showdata;
	}

	public void setShowdata(ArrayList showdata) {
		this.showdata = showdata;
	}

	public int getExcelCases() {
		return excelCases;
	}

	public void setExcelCases(int excelCases) {
		this.excelCases = excelCases;
	}

	public boolean isCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(boolean cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public boolean isKindlyUploadFlag() {
		return kindlyUploadFlag;
	}

	public void setKindlyUploadFlag(boolean kindlyUploadFlag) {
		this.kindlyUploadFlag = kindlyUploadFlag;
	}

	public boolean isFinalsubmit() {
		return finalsubmit;
	}

	public void setFinalsubmit(boolean finalsubmit) {
		this.finalsubmit = finalsubmit;
	}

	public boolean isSubmitFlag() {
		return submitFlag;
	}

	public void setSubmitFlag(boolean submitFlag) {
		this.submitFlag = submitFlag;
	}

	public boolean isUploaderFlag() {
		return uploaderFlag;
	}

	public void setUploaderFlag(boolean uploaderFlag) {
		this.uploaderFlag = uploaderFlag;
	}

	public String getExcelFilename() {
		return excelFilename;
	}

	public void setExcelFilename(String excelFilename) {
		this.excelFilename = excelFilename;
	}

	public String getExcelFilepath() {
		return excelFilepath;
	}

	public void setExcelFilepath(String excelFilepath) {
		this.excelFilepath = excelFilepath;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getSuccMsg() {
		return succMsg;
	}

	public void setSuccMsg(String succMsg) {
		this.succMsg = succMsg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void uploadExcel(UploadEvent event) { 
		try {
			int size = 0;
			int counter = 0;
			UploadItem item = event.getUploadItem();

			String FullfileName = item.getFileName(); 
			String path = item.getFile().getPath(); 
			String fileName = FullfileName.substring(FullfileName
					.lastIndexOf("\\") + 1); 
			ExternalContext con = FacesContext.getCurrentInstance()
					.getExternalContext(); 
			ServletContext sCon = (ServletContext) con.getContext(); 
			System.out.println("filename" + FullfileName	+ "---------------filename" + fileName);
				
			size = item.getFileSize();
			this.excelFilename = FullfileName;
			this.excelFilepath = path; 
			this.submitFlag = true;
		} catch (Exception ee) {
			ee.printStackTrace();
			System.out.println("exception in upload@");
		} finally {

		}

	}

	public String importExcel() {
		if(isValidateInput1()){
		new ValidateBarcodeImpl().saveExcelData(this);
	}
		
		
		
		
		

		// this.tableFlag = true;
		this.submitFlag = false;
		this.cancelFlag = true;
		// this.gatePassFlag = false;
		this.kindlyUploadFlag = false;
		this.uploaderFlag = false;
		// this.scanUploadFlag=true;
		this.finalsubmit = true;
		this.uploaderFlag = false;

		return "";
	}
	
	
	
	
	
	private boolean validateInput1;
	public boolean isValidateInput1() {
		validateInput1=true;
		
		 if(!(Validate.validateStrReq("radio",this.getRadio_CL_FL_Beer())))validateInput1=false;
		
		if(this.radio_CL_FL_Beer.equalsIgnoreCase("CL"))
		{
		  if(!(Validate.validateStrReq("list",this.getDist_Bwfl_brwryId())))validateInput1=false;
		}
		else if(this.radio_CL_FL_Beer.equalsIgnoreCase("FL") && isFl_Dist_Bwfl2A_Flag())
		{
			if(this.getFl_Dist_Bwfl2A().equalsIgnoreCase("0"))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR," For" ," For !"));
					
				validateInput1=false;
			}
			
		//	 if(!(Validate.validateStrReq("for",this.getFl_Dist_Bwfl2A())))validateInput1=false;
			 else if(!(Validate.validateStrReq("list",this.getDist_Bwfl_brwryId())))validateInput1=false;
		}
		else if(this.radio_CL_FL_Beer.equalsIgnoreCase("Beer") && isBeer_Brewrey_Bwfl2B_Flag())
		{
			 if(!(Validate.validateStrReq("for",this.getBeer_Brewrey_Bwfl2B())))validateInput1=false;
			 else if(!(Validate.validateStrReq("list",this.getDist_Bwfl_brwryId())))validateInput1=false;
		}
		
		return validateInput1;
	}
	public void setValidateInput1(boolean validateInput1) {
		this.validateInput1 = validateInput1;
	}
	
	//----------------------------------
	
	
	public void radioListnr_cl_fl_beer(ValueChangeEvent e) {

		try {
			this.setShowdata_Flag(true);
			this.showdata.clear();
			
			String id = (String) e.getNewValue();
			if(id.equalsIgnoreCase("CL"))
			{
			this.dist_Bwfl_brwryList_Flag=true;
			this.beer_Brewrey_Bwfl2B_Flag=false;
			this.fl_Dist_Bwfl2A_Flag=false;
			}
			else if(id.equalsIgnoreCase("FL"))
			{
				this.beer_Brewrey_Bwfl2B_Flag=false;
				this.fl_Dist_Bwfl2A_Flag=true;
				this.dist_Bwfl_brwryList_Flag=false;
			}
			else if(id.equalsIgnoreCase("Beer"))
			{
				this.beer_Brewrey_Bwfl2B_Flag=true;
				this.fl_Dist_Bwfl2A_Flag=false;
				this.dist_Bwfl_brwryList_Flag=false;
			}

		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}
	
	
	
	public void list_clr_Listnr(ValueChangeEvent e) {

		try {
			this.setShowdata_Flag(true);
			this.showdata.clear();
			
			String rt=(String)e.getNewValue();
			this.setFl_Dist_Bwfl2A(rt);
			this.dist_Bwfl_brwryList.clear();
			this.dist_Bwfl_brwryList_Flag=true;

		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}
	
	public void list_clr_Listnr2(ValueChangeEvent e) {

		try {
			this.setShowdata_Flag(true);
			this.showdata.clear();
			
			String rt=(String)e.getNewValue();
			this.setBeer_Brewrey_Bwfl2B(rt);
			this.dist_Bwfl_brwryList.clear();
			this.dist_Bwfl_brwryList_Flag=true;

		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}
	
	
	
	
	//----------------------------------------reset ----------------------
	
	
	
	public void reset()
	{
		this. radio_CL_FL_Beer="CL";
		this. fl_Dist_Bwfl2A="Tst";
		this. beer_Brewrey_Bwfl2B=null;
		
		this. dist_Bwfl_brwryId=null;
		this. dist_Bwfl_brwryList.clear();
		
		this. fl_Dist_Bwfl2A_Flag=false;
		this. beer_Brewrey_Bwfl2B_Flag=false;
		this. dist_Bwfl_brwryList_Flag=true;
		this. printFlag=false;
		this.showdata_Flag=true;
		this.showdata.clear();
		this.radioExcl_Csv=null;
		
	}
	
	
	//-------------------------- print ------------------
	
	private boolean printFlag;
	private String pdfName;

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
	
	
	
	
	/*public void print()
	{
		if(isValidateInput1())
		{
			ValidateBarcodeImpl impl=new ValidateBarcodeImpl();
			impl.printReport(this);
			
		}
	}
	*/
	
	
	public void printDetail(ActionEvent e) {
		ValidateBarcodeImpl impl=new ValidateBarcodeImpl();

		UIDataTable uiTable = (UIDataTable) e.getComponent().getParent()
				.getParent();
		ValidateBarcodeDT dt = (ValidateBarcodeDT) this.getShowdata()	.get(uiTable.getRowIndex());
			

		

		if (impl.printReport(this,dt.getSeq()) == true) {
			dt.setPrintFlag(true);

		} else {

			dt.setPrintFlag(false);

		}

	}
	
	
	public String finalizeData()
	{
		
		try{
			NewCodeGenerationDatatable dt1=(NewCodeGenerationDatatable)this.dt;
			boolean f=new ValidateBarcodeImpl().finalizeData(dt1);
			if(f){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("New Code Updated SuccessFully", "New Code Updated SuccessFully"));
			}
			
			System.out.println();
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}	
	
	public String generateExcel()
	{
		
		try{
			NewCodeGenerationDatatable dt1=(NewCodeGenerationDatatable)this.dt;
			boolean f=new ValidateBarcodeImpl().write(dt1);
			if(f){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Excel Generated SuccessFully", "Excel Generated SuccessFully"));
			}
			
			System.out.println();
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}	
	
	
	
	
	
	//--------------------------------------------------------------------------------------------------------------
	
	
	
	
	

	//-------------------------- code for csv ---------------------
	
	private String radioExcl_Csv;
	
	public String getRadioExcl_Csv() {
		return radioExcl_Csv;
	}

	public void setRadioExcl_Csv(String radioExcl_Csv) {
		this.radioExcl_Csv = radioExcl_Csv;
	}
	
	
	// --------------------code for csv upload-------------------------
	
	
		private String csvFilename;
		private String csvFilepath;
		private String exclcsv;
		
		
		public String getCsvFilename() {
			return csvFilename;
		}

		public void setCsvFilename(String csvFilename) {
			this.csvFilename = csvFilename;
		}

		public String getCsvFilepath() {
			return csvFilepath;
		}

		public void setCsvFilepath(String csvFilepath) {
			this.csvFilepath = csvFilepath;
		}

		public String getExclcsv() {
			return exclcsv;
		}

		public void setExclcsv(String exclcsv) {
			this.exclcsv = exclcsv;
		}

		public void uploadCsv(UploadEvent event) {

			try {
				int size = 0;
				int counter = 0;
				UploadItem item = event.getUploadItem();

				String FullfileName = item.getFileName();

				String path = item.getFile().getPath();

				String fileName = FullfileName.substring(FullfileName.lastIndexOf("\\") + 1);

				ExternalContext con = FacesContext.getCurrentInstance().getExternalContext();

				ServletContext sCon = (ServletContext) con.getContext();

				System.out.println("filename" + FullfileName+ "-------- csv -------filename" + fileName);
				size = item.getFileSize();
				this.setCsvFilename(FullfileName);
				this.setCsvFilepath(path);

			} catch (Exception ee) {
				ee.printStackTrace();
				System.out.println("exception in upload@");
			} finally {

			}

		}
		
		
		public String csvSubmit() throws IOException {
			ValidateBarcodeImpl impl=new ValidateBarcodeImpl();
			
			if(isValidateInput1()){
			impl.saveCSV(this);
		}

			return "";
		}
	
	
	
	
	
	
	
	
	
	
	
	
}