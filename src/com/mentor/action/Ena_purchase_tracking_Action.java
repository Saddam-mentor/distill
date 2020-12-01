package com.mentor.action;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.event.ValueChangeEvent;

import com.mentor.datatable.Ena_purchase_tracking_dt;
import com.mentor.impl.Ena_purchase_tracking_impl;
import com.mentor.utility.ResourceUtil;

public class Ena_purchase_tracking_Action {
	

	Ena_purchase_tracking_impl impl = new Ena_purchase_tracking_impl();
	Ena_purchase_tracking_dt dt;
	private int req_id;
	private Date req_date;
	private Date noc_date;
	private String by_distillery;
	private String noc_no;
	private String remarks;
	private String radio;
	private boolean tableflag = true;
	private String designation;
	private String name;
	private Double approving_qty;
	private String hidden;
	private String file;
	private boolean approve_flag;
	private boolean panelflag;
	private boolean viewFlag;
	private String pdfName;
	private boolean decflg = true;
	private double instaldpotable = 0.0;
	private double industrialpotable = 0.0;
	private double prodpotable = 0.0;
	private double prodindstrial = 0.0;
	private double purchsepotable = 0.0;
	private double purchsindustrial = 0.0;
	private double indussum = 0.0;
	private double potablesum = 0.0;
	private String rad1;
	private String rad2;
	private String oupstate;
	private String mainServiceId;
	private String maincntrlId;
	private String mainunitId;
	private String requestId;
	 
	 
	public String getMainServiceId() {
		return mainServiceId;
	}

	public void setMainServiceId(String mainServiceId) {
		this.mainServiceId = mainServiceId;
	}

	public String getMaincntrlId() {
		return maincntrlId;
	}

	public void setMaincntrlId(String maincntrlId) {
		this.maincntrlId = maincntrlId;
	}

	public String getMainunitId() {
		return mainunitId;
	}

	public void setMainunitId(String mainunitId) {
		this.mainunitId = mainunitId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getOupstate() {
		return oupstate;
	}

	public void setOupstate(String oupstate) {
		this.oupstate = oupstate;
	}
	public Date getReq_date() {
		return req_date;
	}

	public void setReq_date(Date req_date) {
		this.req_date = req_date;
	}

	public double getInstaldpotable() {
		return instaldpotable;
	}

	public void setInstaldpotable(double instaldpotable) {
		this.instaldpotable = instaldpotable;
	}

	public double getIndustrialpotable() {
		return industrialpotable;
	}

	public void setIndustrialpotable(double industrialpotable) {
		this.industrialpotable = industrialpotable;
	}

	public double getProdpotable() {
		return prodpotable;
	}

	public void setProdpotable(double prodpotable) {
		this.prodpotable = prodpotable;
	}

	public double getProdindstrial() {
		return prodindstrial;
	}

	public void setProdindstrial(double prodindstrial) {
		this.prodindstrial = prodindstrial;
	}

	public double getPurchsepotable() {
		return purchsepotable;
	}

	public void setPurchsepotable(double purchsepotable) {
		this.purchsepotable = purchsepotable;
	}

	public double getPurchsindustrial() {
		return purchsindustrial;
	}

	public void setPurchsindustrial(double purchsindustrial) {
		this.purchsindustrial = purchsindustrial;
	}

	public double getIndussum() {
		return indussum;
	}

	public void setIndussum(double indussum) {
		this.indussum = indussum;
	}

	public double getPotablesum() {
		return potablesum;
	}

	public void setPotablesum(double potablesum) {
		this.potablesum = potablesum;
	}

	public String getRad1() {
		return rad1;
	}

	public void setRad1(String rad1) {
		this.rad1 = rad1;
	}

	public String getRad2() {
		return rad2;
	}

	public void setRad2(String rad2) {
		this.rad2 = rad2;
	}

	public boolean isDecflg() {
		if (ResourceUtil.getUserNameAllReq().trim()
				.equalsIgnoreCase("Excise-DEC")) {
			decflg = false;
		}
		return decflg;
	}

	public void setDecflg(boolean decflg) {
		this.decflg = decflg;
	}

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	private int year;
	private String radio_flag = "T";
	private double requested_qty;
	private String pdfname;
	private String from_distillery;
	private String user1 = "PENDING";
	private String user2 = "PENDING";
	private String user3 = "PENDING";
	private String user4;
	private boolean pdfnameNew;

	public boolean isPdfnameNew() {
		return pdfnameNew;
	}

	public void setPdfnameNew(boolean pdfnameNew) {
		this.pdfnameNew = pdfnameNew;
	}

	private boolean printFlag = false;
	Ena_purchase_tracking_dt prt;

	public Ena_purchase_tracking_dt getPrt() {
		return prt;
	}

	public void setPrt(Ena_purchase_tracking_dt prt) {
		this.prt = prt;
	}

	public boolean isPrintFlag() {
		return printFlag;
	}

	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}

	public String getUser4() {
		return user4;
	}

	public void setUser4(String user4) {
		this.user4 = user4;
	}

	public String getUser1() {
		return user1;
	}

	public void setUser1(String user1) {
		this.user1 = user1;
	}

	public String getUser2() {
		return user2;
	}

	public void setUser2(String user2) {
		this.user2 = user2;
	}

	public String getUser3() {
		return user3;
	}

	public void setUser3(String user3) {
		this.user3 = user3;
	}

	public String getFrom_distillery() {
		return from_distillery;
	}

	public void setFrom_distillery(String from_distillery) {
		this.from_distillery = from_distillery;
	}

	public String getPdfname() {
		return pdfname;
	}

	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
	}

	public double getRequested_qty() {
		return requested_qty;
	}

	public void setRequested_qty(double requested_qty) {
		this.requested_qty = requested_qty;
	}

	public String getRadio_flag() {
		return radio_flag;
	}

	public void setRadio_flag(String radio_flag) {
		this.radio_flag = radio_flag;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public boolean isViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(boolean viewFlag) {
		this.viewFlag = viewFlag;
	}

	public boolean isPanelflag() {
		return panelflag;
	}

	public void setPanelflag(boolean panelflag) {
		this.panelflag = panelflag;
	}

	public boolean isApprove_flag() {
		return approve_flag;
	}

	public void setApprove_flag(boolean approve_flag) {
		this.approve_flag = approve_flag;
	}

	public boolean isForward_flag() {
		return forward_flag;
	}

	public void setForward_flag(boolean forward_flag) {
		this.forward_flag = forward_flag;
	}

	private boolean forward_flag;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getHidden() {

		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public Double getApproving_qty() {
		return approving_qty;
	}

	public void setApproving_qty(Double approving_qty) {
		this.approving_qty = approving_qty;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public boolean isTableflag() {
		return tableflag;
	}

	public void setTableflag(boolean tableflag) {
		this.tableflag = tableflag;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public int getReq_id() {
		return req_id;
	}

	public void setReq_id(int req_id) {
		this.req_id = req_id;
	}

	public Date getNoc_date() {
		return noc_date;
	}

	public void setNoc_date(Date noc_date) {
		this.noc_date = noc_date;
	}

	public String getBy_distillery() {
		return by_distillery;
	}

	public void setBy_distillery(String by_distillery) {
		this.by_distillery = by_distillery;
	}

	public String getNoc_no() {
		return noc_no;
	}

	public void setNoc_no(String noc_no) {
		this.noc_no = noc_no;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

/*	public void mthd(ValueChangeEvent ev) {
		String radio = (String) ev.getNewValue();
		this.radio = radio;

		// this.requestList=impl.getData(this);
		if (this.getRadio().equalsIgnoreCase("F")) {
			this.setViewFlag(false);
		} else
			this.setViewFlag(true);
		this.setTableflag(true);
		this.setPanelflag(false);
		this.requestList = impl.getData(this);
		this.requestListimport = impl.getDataimport(this);
		this.requestListexport = impl.getDataexport(this);
		this.requestListup = impl.getDataup(this);

	}*/

	

	

	

	

	// /-----------rahul 05-12-2019

	private double instaldpotable_sel = 0.0;
	private double industrialpotable_sel = 0.0;
	private double prodpotable_sel = 0.0;
	private double prodindstrial_sel = 0.0;
	private double purchsepotable_sel = 0.0;
	private double purchsindustrial_sel = 0.0;
	private String salefrom;

	public String getSalefrom() {
		return salefrom;
	}

	public void setSalefrom(String salefrom) {
		this.salefrom = salefrom;
	}

	public double getInstaldpotable_sel() {
		return instaldpotable_sel;
	}

	public void setInstaldpotable_sel(double instaldpotable_sel) {
		this.instaldpotable_sel = instaldpotable_sel;
	}

	public double getIndustrialpotable_sel() {
		return industrialpotable_sel;
	}

	public void setIndustrialpotable_sel(double industrialpotable_sel) {
		this.industrialpotable_sel = industrialpotable_sel;
	}

	public double getProdpotable_sel() {
		return prodpotable_sel;
	}

	public void setProdpotable_sel(double prodpotable_sel) {
		this.prodpotable_sel = prodpotable_sel;
	}

	public double getProdindstrial_sel() {
		return prodindstrial_sel;
	}

	public void setProdindstrial_sel(double prodindstrial_sel) {
		this.prodindstrial_sel = prodindstrial_sel;
	}

	public double getPurchsepotable_sel() {
		return purchsepotable_sel;
	}

	public void setPurchsepotable_sel(double purchsepotable_sel) {
		this.purchsepotable_sel = purchsepotable_sel;
	}

	public double getPurchsindustrial_sel() {
		return purchsindustrial_sel;
	}

	public void setPurchsindustrial_sel(double purchsindustrial_sel) {
		this.purchsindustrial_sel = purchsindustrial_sel;
	}

	// /-----------rahul 18-12-2019
	private double capacity_import;
	private String export_dist_no;
	private Date dated;
	private String dist_nam;
	private String dist_add;
	private boolean flg;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isFlg() {
		return flg;
	}

	public void setFlg(boolean flg) {
		this.flg = flg;
	}

	public double getCapacity_import() {
		return capacity_import;
	}

	public void setCapacity_import(double capacity_import) {
		this.capacity_import = capacity_import;
	}

	public String getExport_dist_no() {
		return export_dist_no;
	}

	public void setExport_dist_no(String export_dist_no) {
		this.export_dist_no = export_dist_no;
	}

	public Date getDated() {
		return dated;
	}

	public void setDated(Date dated) {
		this.dated = dated;
	}

	private String pdf;
	private String pdfNameimp;

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getPdfNameimp() {
		return pdfNameimp;
	}

	public void setPdfNameimp(String pdfNameimp) {
		this.pdfNameimp = pdfNameimp;
	}

	public String getDist_nam() {
		return dist_nam;
	}

	public void setDist_nam(String dist_nam) {
		this.dist_nam = dist_nam;
	}

	public String getDist_add() {
		return dist_add;
	}

	public void setDist_add(String dist_add) {
		this.dist_add = dist_add;
	}
	
	
	// ------------------revert comment----rahul---27-12-2019----------

	
		private String rvrtCmntPopup;

		public String getRvrtCmntPopup() {
			return rvrtCmntPopup;
		}

		public void setRvrtCmntPopup(String rvrtCmntPopup) {
			this.rvrtCmntPopup = rvrtCmntPopup;
		}
		
	
		private boolean decFlg1;
		private String acRvrtRmrk;
		private String decRvrtRmrk;
		private boolean deleteFlg;
		
		

		public boolean isDeleteFlg() {
			return deleteFlg;
		}

		public void setDeleteFlg(boolean deleteFlg) {
			this.deleteFlg = deleteFlg;
		}

		public String getAcRvrtRmrk() {
			return acRvrtRmrk;
		}

		public void setAcRvrtRmrk(String acRvrtRmrk) {
			this.acRvrtRmrk = acRvrtRmrk;
		}

		public String getDecRvrtRmrk() {
			return decRvrtRmrk;
		}

		public void setDecRvrtRmrk(String decRvrtRmrk) {
			this.decRvrtRmrk = decRvrtRmrk;
		}

		public boolean isDecFlg1() {
			return decFlg1;
		}

		public void setDecFlg1(boolean decFlg1) {
			this.decFlg1 = decFlg1;
		}
		public void closeApplication() {

		
		//	this.requestList = impl.getData(this);
		//	this.requestListimport = impl.getDataimport(this);
		//	this.requestListexport = impl.getDataexport(this);
		//	this.requestListup = impl.getDataup(this);
			this.setTableflag(true);
			this.setPanelflag(false);
		}
		
		
		private boolean expflg1;

		public boolean isExpflg1() {
			return expflg1;
		}

		public void setExpflg1(boolean expflg1) {
			this.expflg1 = expflg1;
		}
		
//------------------------------------03-01-2020---rahull----for import jsp ----	
		
		
		
		
		
		
		//----------for export jsp
		
		

		
		
		
		//----------for UP jsp
		
				
				
			

			
//=======rajeev==10/1/2020
				
				
				/*** for doc upload ***/

				private boolean doc1upload = false;
				private static BufferedInputStream apidoc1 = null;

				public boolean isDoc1upload() {
					return doc1upload;
				}
				public void setDoc1upload(boolean doc1upload) {
					this.doc1upload = doc1upload;
				}
				public static BufferedInputStream getApidoc1() {
					return apidoc1;
				}
				/*public static void setApidoc1(BufferedInputStream apidoc1) {
					RequestForBLAction.apidoc1 = apidoc1;
				}*/
				private boolean flagupload = true;
				public boolean isFlagupload() {
					return flagupload;
				}
				public void setFlagupload(boolean flagupload) {
					this.flagupload = flagupload;
				}
			
				
				private boolean pdfUploaderFlag;

				public boolean isPdfUploaderFlag() {
					return pdfUploaderFlag;
				}
				public void setPdfUploaderFlag(boolean pdfUploaderFlag) {
					this.pdfUploaderFlag = pdfUploaderFlag;
				}
				
				private boolean flagdl=false;

				public boolean isFlagdl() {
					return flagdl;
				}

				public void setFlagdl(boolean flagdl) {
					this.flagdl = flagdl;
				}
				private String acc_no;
				public String getAcc_no() {
					return acc_no;
				}

				public void setAcc_no(String acc_no) {
					this.acc_no = acc_no;
				}

				public String getAcc_pdfName() {
					return acc_pdfName;
				}

				public void setAcc_pdfName(String acc_pdfName) {
					this.acc_pdfName = acc_pdfName;
				}

				private String acc_pdfName;
				
//======================Aman
	
	 public void viewDetails() {
	     
		 try
         
		 {   
			 if(this.getRadio().equalsIgnoreCase("EX"))
			 {  
				   if(impl.getDataexport(this))	 
					{
						this.tableflag = false;
						this.panelflag = true;
			           
					}
		      }
			 
			 if(this.getRadio().equalsIgnoreCase("IM"))
			 {  
				   if(impl.getDataimport(this))	 
					{
						this.tableflag = false;
						this.panelflag = true;
			       }
		      }
		  
		   if(this.getRadio().equalsIgnoreCase("WUP"))	 
		   {  
		     if(impl.getDataup(this))	 
			  {
				this.tableflag = false;
				this.panelflag = true;
	           
			  }
		  }
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		

 }
	private String permit_no ;

	public String getPermit_no() {
		return permit_no;
	}

	public void setPermit_no(String permit_no) {
		this.permit_no = permit_no;
	}
	
    public void close()
	 
	 {
		  this.tableflag = true;
		  this.panelflag = false; 
	 }				

    public void reset() {

		this.permit_no=null;
	}
    
        public void clickRadio(ValueChangeEvent e){
		String val = (String) e.getNewValue();
		this.radio=val;
	}
        
    public Date permit_date ;

   public Date getPermit_date() {
		return permit_date;
	}

	public void setPermit_date(Date permit_date) {
		this.permit_date = permit_date;
	}  
	
	public String Permit ;


	public String getPermit() {
		return Permit;
	}

	public void setPermit(String permit) {
		Permit = permit;
	}
}
