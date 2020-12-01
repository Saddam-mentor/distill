package com.mentor.action;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.faces.event.ActionEvent;

import com.mentor.datatable.HomePageDatatable;
import com.mentor.impl.HomePageImpl;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
 
 

public class HomePageAction {
	
	
	HomePageImpl impl = new HomePageImpl();
	private boolean baseNameFlag=true;
	String userStatic = null;
	//private String baseName=ConnectionToDataBase.getHomeBundle3(userStatic);
	public static int login=0;
	private String langvalue="";
	private int renderFlag  =1;
	private boolean sugarMillImageFlag=false;
	private boolean sugarMillImageFlagEng=true;
	private boolean disstImageFlag=false;
	private boolean disstImageFlagEng=true;
	private boolean breweriesImageFlag=false;
	private boolean breweriesImageFlagEng=true;
	private boolean shopsImageFlag=false;
	private boolean shopsImageFlagEng=true;
	private boolean industriesImageFlag=false;
	private boolean industriesImageFlagEng=true;
	private String val;
	private boolean deomsg=false;
	private boolean approvalflg;
	private int brandcount  =0;
	private int renewbondcount  =0;
	private int newbondcount  =0;
	private boolean niveshflg;
	private boolean janhitflg;
	private boolean liqourindntflg;
	private int enacount  =0;

	
	
	public int getEnacount() {
		return enacount;
	}



	public void setEnacount(int enacount) {
		this.enacount = enacount;
	}



	public boolean isNiveshflg() {
		return niveshflg;
	}



	public void setNiveshflg(boolean niveshflg) {
		this.niveshflg = niveshflg;
	}



	public boolean isJanhitflg() {
		return janhitflg;
	}



	public void setJanhitflg(boolean janhitflg) {
		this.janhitflg = janhitflg;
	}



	public boolean isLiqourindntflg() {
		return liqourindntflg;
	}



	public void setLiqourindntflg(boolean liqourindntflg) {
		this.liqourindntflg = liqourindntflg;
	}



	public int getBrandcount() {
		return brandcount;
	}



	public void setBrandcount(int brandcount) {
		this.brandcount = brandcount;
	}



	public int getRenewbondcount() {
		return renewbondcount;
	}



	public void setRenewbondcount(int renewbondcount) {
		this.renewbondcount = renewbondcount;
	}



	public int getNewbondcount() {
		return newbondcount;
	}



	public void setNewbondcount(int newbondcount) {
		this.newbondcount = newbondcount;
	}



	public boolean isDeomsg() {
		return deomsg;
	}



	public boolean isApprovalflg() {
		return approvalflg;
	}



	public void setApprovalflg(boolean approvalflg) {
		this.approvalflg = approvalflg;
	}



	public void setDeomsg(boolean deomsg) {
		this.deomsg = deomsg;
	}
	public String getVal() {
		return val;
	}



	public void setVal(String val) {
		this.val = val;
	}


	private ArrayList descList = new ArrayList();
	
	
	public ArrayList getDescList() {

		 
	//	descList=new Cl_ListPriceImpl().descListImpl();
		
		
	 
	
		return descList;
	}



	public void setDescList(ArrayList descList) {
		this.descList = descList;
	}



 
	
	public boolean isDisstImageFlag() {
		return disstImageFlag;
	}

	public boolean isDisstImageFlagEng() {
		return disstImageFlagEng;
	}

	public boolean isBreweriesImageFlag() {
		return breweriesImageFlag;
	}

	public boolean isBreweriesImageFlagEng() {
		return breweriesImageFlagEng;
	}

	public boolean isShopsImageFlag() {
		return shopsImageFlag;
	}

	public boolean isShopsImageFlagEng() {
		return shopsImageFlagEng;
	}

	public boolean isIndustriesImageFlag() {
		return industriesImageFlag;
	}

	public boolean isIndustriesImageFlagEng() {
		return industriesImageFlagEng;
	}

	public void setDisstImageFlag(boolean disstImageFlag) {
		this.disstImageFlag = disstImageFlag;
	}

	public void setDisstImageFlagEng(boolean disstImageFlagEng) {
		this.disstImageFlagEng = disstImageFlagEng;
	}

	public void setBreweriesImageFlag(boolean breweriesImageFlag) {
		this.breweriesImageFlag = breweriesImageFlag;
	}

	public void setBreweriesImageFlagEng(boolean breweriesImageFlagEng) {
		this.breweriesImageFlagEng = breweriesImageFlagEng;
	}

	public void setShopsImageFlag(boolean shopsImageFlag) {
		this.shopsImageFlag = shopsImageFlag;
	}

	public void setShopsImageFlagEng(boolean shopsImageFlagEng) {
		this.shopsImageFlagEng = shopsImageFlagEng;
	}

	public void setIndustriesImageFlag(boolean industriesImageFlag) {
		this.industriesImageFlag = industriesImageFlag;
	}

	public void setIndustriesImageFlagEng(boolean industriesImageFlagEng) {
		this.industriesImageFlagEng = industriesImageFlagEng;
	}

	public boolean isSugarMillImageFlagEng() {
		return sugarMillImageFlagEng;
	}

	public void setSugarMillImageFlagEng(boolean sugarMillImageFlagEng) {
		this.sugarMillImageFlagEng = sugarMillImageFlagEng;
	}

	public boolean isSugarMillImageFlag() {
		return sugarMillImageFlag;
	}

	public void setSugarMillImageFlag(boolean sugarMillImageFlag) {
		this.sugarMillImageFlag = sugarMillImageFlag;
	}

	public int getRenderFlag() {
		return renderFlag;
	}

	public void setRenderFlag(int renderFlag) {
		this.renderFlag = renderFlag;
	}

	public String getLangvalue() {
		return langvalue;
	}

	public void setLangvalue(String langvalue) {
		this.langvalue = langvalue;
	}

	/*public String getBaseName() {
	
		try{
			Object request =FacesContext.getCurrentInstance() .getExternalContext().getRequest();
			RenderRequestImpl reqRRI=null;
	
			 ActionRequestImpl reqARI=null;
			if(request instanceof RenderRequestImpl)
			{
				reqRRI=(RenderRequestImpl)request;
				userStatic=reqRRI.getRemoteUser();							
				if(baseNameFlag && userStatic !=null){
					ConnectionToDataBase.updateUserBundle(userStatic);
					this.baseNameFlag = false;
				}
				if(userStatic==null){					
					this.baseNameFlag = true;	
					this.baseName = ConnectionToDataBase.getHomeBundle3(userStatic);
				}
				
			}else if(request instanceof ActionRequestImpl)
			{
				reqARI=(ActionRequestImpl)request;
				userStatic=reqARI.getRemoteUser();
				if(baseNameFlag && userStatic !=null){
					
					ConnectionToDataBase.updateUserBundle(userStatic);
					this.baseNameFlag = false;
				}
				if(userStatic==null){					
					this.baseNameFlag = true;		
					this.baseName = ConnectionToDataBase.getHomeBundle3(userStatic);
				}
			}
			else 
			{
				userStatic=FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
				this.baseName = ConnectionToDataBase.getHomeBundle3(userStatic);
			}
				
			int check=this.baseName.compareToIgnoreCase("com.mentor.upExcise.home.nl.homepagemsg_hi_IN");
			if(check==0)
			{
				this.sugarMillImageFlag=true;
				this.sugarMillImageFlagEng=false;
				this.disstImageFlag=true;
				this.disstImageFlagEng=false;
				this.shopsImageFlag=true;
				this.shopsImageFlagEng=false;
				this.industriesImageFlag=true;
				this.industriesImageFlagEng=false;
				this.breweriesImageFlag=true;
				this.breweriesImageFlagEng=false;
			}
			else
			{
				this.sugarMillImageFlag=false;
				this.sugarMillImageFlagEng=true;
				this.disstImageFlag=false;
				this.disstImageFlagEng=true;
				this.shopsImageFlag=false;
				this.shopsImageFlagEng=true;
				this.industriesImageFlag=false;
				this.industriesImageFlagEng=true;
				this.breweriesImageFlag=false;
				this.breweriesImageFlagEng=true;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		//==============================
		this.baseName = ConnectionToDataBase.getHomeBundle3(userStatic);
		
		 if(this.baseName.endsWith("hi_IN")){
			 this.renderFlag =2;
			
		 }
		 else{
			 this.renderFlag =1;		
			 }
		return baseName;
		}
	
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}
*/
	//
	 
	private String loginCheck="";
	private boolean loginFlg=false;
	private String loginUsrNm="";
	private String loginUsrRole="";
	private boolean loginDashboardFlg=false;
	
	
	
	public boolean isLoginDashboardFlg() {
		return loginDashboardFlg;
	}

	public void setLoginDashboardFlg(boolean loginDashboardFlg) {
		this.loginDashboardFlg = loginDashboardFlg;
	}

	 
	public String getLoginUsrNm() {
		return loginUsrNm;
	}
	public void setLoginUsrNm(String loginUsrNm) {
		this.loginUsrNm = loginUsrNm;
	}
	public String getLoginUsrRole() {
		return loginUsrRole;
	}
	public void setLoginUsrRole(String loginUsrRole) {
		this.loginUsrRole = loginUsrRole;
	}
 
	public boolean isLoginFlg() {
		return loginFlg;
	}
	public void setLoginFlg(boolean loginFlg) {
		this.loginFlg = loginFlg;
	}
	/*public String getLoginCheck() {
		try{
		if(ResourceUtil.getUserNameAllReq()==null){		
			loginDashboardFlg=false;
			loginFlg=false;
		}else if(ResourceUtil.getUserNameAllReq().equalsIgnoreCase("Psec-Excise")){	
			loginFlg=false;
			loginDashboardFlg=true;
		}	
		else{loginDashboardFlg=false;
			loginFlg=true;
			loginUsrNm=ResourceUtil.getUserNameAllReq().toUpperCase()+".";
		 
		}			
		
	}catch (Exception e) {
		e.printStackTrace();
	}

		return loginCheck;
	}*/
	public void setLoginCheck(String loginCheck) {
		this.loginCheck = loginCheck;
	}
	 
	
	private String text;
	
	private ArrayList  dispList=new ArrayList();

	private ArrayList  sgrList=new ArrayList();
	private ArrayList  distList=new ArrayList();
	private ArrayList  othrList=new ArrayList();



	
	public ArrayList getSgrList() {
		try{
			this.sgrList=new HomePageImpl().getSgrmillList();	
		}catch(Exception e){
			
		}
		return sgrList;
	}

	public void setSgrList(ArrayList sgrList) {
		this.sgrList = sgrList;
	}

	public ArrayList getDistList() {
		try{
			this.distList=new HomePageImpl().getDistilryList();
		}catch(Exception e){
			
		}
		
		return distList;
	}

	public void setDistList(ArrayList distList) {
		this.distList = distList;
	}

	public ArrayList getOthrList() {
		try{
			this.othrList=new HomePageImpl().getOtherList();
		}catch(Exception e){
			
		}
		return othrList;
	}

	public void setOthrList(ArrayList othrList) {
		this.othrList = othrList;
	}

	public ArrayList getDispList() {
		
		//this.dispList=new HomePageImpl().getDisplayList();
		return dispList;
	}
	public void setDispList(ArrayList dispList) {
		this.dispList = dispList;
	}
	public boolean isPdfRender() {
		return pdfRender;
	}
	public void setPdfRender(boolean pdfRender) {
		this.pdfRender = pdfRender;
	}
	public String getSrcMethod() {
		return srcMethod;
	}
	public void setSrcMethod(String srcMethod) {
		this.srcMethod = srcMethod;
	}
	public boolean isBackRendered() {
		return backRendered;
	}
	public void setBackRendered(boolean backRendered) {
		this.backRendered = backRendered;
	}

	private boolean pdfRender= false ;
	private String srcMethod;
	private boolean backRendered=false;
	private String src;
	private String pdfName;
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	public String pdfVied()
	{
		return "";
	}
	public void pdfViedlistener(ActionEvent e)
	{
		String mypath=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH;
		String newFilePath=File.separator+"doc"+File.separator+"ExciseUp"+File.separator+"PdfHome"+File.separator+"UPLOAD"; 		
		String a = (String)e.getComponent().getAttributes().get("pdfName");
	    
		this.pdfRender=true;
		//System.out.println("dfdgffgh"+a);
		this.src=newFilePath+File.separator+a;
		String docId=(String)e.getComponent().getAttributes().get("pdfName");
	////System.out.println("sdkldsklklsdkl"+docId);
		
		//System.out.println(this.getSrc());
	}
	 private String newsText;
	 private String newsText1;
	 private String newsText2;
	 private String newsText3;

	 private String newslink1;
	 private String newslink2;
	 private String newslink3;

	 
	 
	public String getNewslink1() {
		return newslink1;
	}



	public void setNewslink1(String newslink1) {
		this.newslink1 = newslink1;
	}



	public String getNewslink2() {
		return newslink2;
	}



	public void setNewslink2(String newslink2) {
		this.newslink2 = newslink2;
	}



	public String getNewslink3() {
		return newslink3;
	}



	public void setNewslink3(String newslink3) {
		this.newslink3 = newslink3;
	}



	public String getNewsText2() {
		
		return newsText2;
	}



	public void setNewsText2(String newsText2) {
		this.newsText2 = newsText2;
	}



	public String getNewsText3() {
		
		return newsText3;
	}



	public void setNewsText3(String newsText3) {
		this.newsText3 = newsText3;
	}



	public String getNewsText1() {
		
		return newsText1;
	}



	public void setNewsText1(String newsText1) {
		this.newsText1 = newsText1;
	}



	public String getNewsText() {
		this.newsText=new HomePageImpl().getNews();
		return newsText;
	}
	public void setNewsText(String newsText) {
		this.newsText = newsText;
	}
	public String  backMethod()
	   {
		  
		   this.pdfRender=false;
		   this.backRendered=false;
		   return "";
		   
	    }
	private boolean columnRender1 = false;
	private boolean columnRender2 = false;
	private boolean columnRender3 = false;
	private boolean columnRender4 = false;
	private boolean columnRender5 = false;
	private boolean columnRender6 = false;
	
	
	

public boolean isColumnRender1() {
		return columnRender1;
	}

	public void setColumnRender1(boolean columnRender1) {
		this.columnRender1 = columnRender1;
	}

	public boolean isColumnRender2() {
		return columnRender2;
	}

	public void setColumnRender2(boolean columnRender2) {
		this.columnRender2 = columnRender2;
	}

	public boolean isColumnRender3() {
		return columnRender3;
	}

	public void setColumnRender3(boolean columnRender3) {
		this.columnRender3 = columnRender3;
	}

	public boolean isColumnRender4() {
		return columnRender4;
	}

	public void setColumnRender4(boolean columnRender4) {
		this.columnRender4 = columnRender4;
	}

	public boolean isColumnRender5() {
		return columnRender5;
	}

	public void setColumnRender5(boolean columnRender5) {
		this.columnRender5 = columnRender5;
	}

	public boolean isColumnRender6() {
		return columnRender6;
	}

	public void setColumnRender6(boolean columnRender6) {
		this.columnRender6 = columnRender6;
	}

private ArrayList<HomePageDatatable> menuLinkList = new ArrayList<HomePageDatatable>();
	
 
	
	
	public ArrayList<HomePageDatatable> getMenuLinkList() {			
		HomePageImpl impl = new HomePageImpl();		
		 //System.out.println("11111111");
		try{
		if(ResourceUtil.getUserNameAllReq()==null){			
			this.loginFlg = false;		
			loginDashboardFlg=false;
			 //System.out.println("222222222222");
			this.menuLinkList = new ArrayList<HomePageDatatable>();
		}else{			
			 
			this.loginFlg = true;
			this.menuLinkList = impl.displayMenuList(this);
			
		}			
		
	}catch (Exception e) {
		e.printStackTrace();
	}
		return menuLinkList;
	}
	
	
	/////////////////////dashboard variables///////////////////////////
	

private ArrayList<HomePageDatatable> menuLinkList1 = new ArrayList<HomePageDatatable>();
	
 
	
	
	public ArrayList<HomePageDatatable> getMenuLinkList1() {			
		HomePageImpl impl = new HomePageImpl();		
		 //System.out.println("11111111");
		try{
		if(ResourceUtil.getUserNameAllReq()==null){			
			 	
			loginDashboardFlg=false;
			loginFlg=false;
			this.menuLinkList1 = new ArrayList<HomePageDatatable>();
		}else	if(ResourceUtil.getUserNameAllReq().equalsIgnoreCase("Psec-Excise")) {			
			loginDashboardFlg=true;
			loginFlg=false;
			this.menuLinkList1 = impl.displayMenuList1(this);
			
		}			
		
	}catch (Exception e) {
		e.printStackTrace();
	}
		return menuLinkList1;
	}
	
	public void setMenuLinkList1(ArrayList<HomePageDatatable> menuLinkList1) {
		this.menuLinkList1 = menuLinkList1;
	}

	private String dashhidden;
	private double totstoraecap=0;
	private double openingBal=0;
	private double prodTillDt=0;
	private double supplyTillDt=0;
	private double balance=0;
	
	private double distApplied=0;
	private double distApprovd=0;
	private double distLifted=0;
	private double distRejected=0;
	private double distProcess=0;
	
	private double indApplied=0;
	private double indApprovd=0;
	private double indLifted=0;
	private double indRejected=0;
	private double indProcess=0;
	
	
	private double expRejected=0;
	private double expProcess=0;
	private double expApplied=0;
	private double expApprovd=0;
	private double expLifted=0;
	
	private double apprecvd=0;
	private double appprocced=0;
	private double appapprvd=0;

	private int sgrmillcount=0;
	private int distcount=0;
	private int indcount=0;
	private int expcount=0;
	private int shopcount=0;
	private boolean dashcomm;
	private int indcountoup=0;
	private int brewery=0;
	private int fl2d=0;
	private int bwfl2a=0;
	private int bwfl2b=0;
	private int bwfl2c=0;
	private int bwfl2d=0;
	private int mufl=0;
	private int mubeer=0;
	private int mucl=0;
	private int wcl=0;
	private int wbeer=0;
	private int wfl=0;
	private int bond1=0;
	private int bond2=0;
	 
	
	
	public int getMufl() {
		return mufl;
	}



	public void setMufl(int mufl) {
		this.mufl = mufl;
	}



	public int getMubeer() {
		return mubeer;
	}



	public void setMubeer(int mubeer) {
		this.mubeer = mubeer;
	}



	public int getMucl() {
		return mucl;
	}



	public void setMucl(int mucl) {
		this.mucl = mucl;
	}



	public int getWcl() {
		return wcl;
	}



	public void setWcl(int wcl) {
		this.wcl = wcl;
	}



	public int getWbeer() {
		return wbeer;
	}



	public void setWbeer(int wbeer) {
		this.wbeer = wbeer;
	}



	public int getWfl() {
		return wfl;
	}



	public void setWfl(int wfl) {
		this.wfl = wfl;
	}



	public int getBond1() {
		return bond1;
	}



	public void setBond1(int bond1) {
		this.bond1 = bond1;
	}



	public int getBond2() {
		return bond2;
	}



	public void setBond2(int bond2) {
		this.bond2 = bond2;
	}


	private boolean psec;
	
	 

	public boolean isPsec() {
		return psec;
	}



	public void setPsec(boolean psec) {
		this.psec = psec;
	}



	public int getIndcountoup() {
		return indcountoup;
	}



	public void setIndcountoup(int indcountoup) {
		this.indcountoup = indcountoup;
	}



	public int getBrewery() {
		return brewery;
	}



	public void setBrewery(int brewery) {
		this.brewery = brewery;
	}



	public int getFl2d() {
		return fl2d;
	}



	public void setFl2d(int fl2d) {
		this.fl2d = fl2d;
	}



	public int getBwfl2a() {
		return bwfl2a;
	}



	public void setBwfl2a(int bwfl2a) {
		this.bwfl2a = bwfl2a;
	}



	public int getBwfl2b() {
		return bwfl2b;
	}



	public void setBwfl2b(int bwfl2b) {
		this.bwfl2b = bwfl2b;
	}



	public int getBwfl2c() {
		return bwfl2c;
	}



	public void setBwfl2c(int bwfl2c) {
		this.bwfl2c = bwfl2c;
	}



	public int getBwfl2d() {
		return bwfl2d;
	}



	public void setBwfl2d(int bwfl2d) {
		this.bwfl2d = bwfl2d;
	}



	public void setSgrmillcount(int sgrmillcount) {
		this.sgrmillcount = sgrmillcount;
	}



	public void setDistcount(int distcount) {
		this.distcount = distcount;
	}



	public void setIndcount(int indcount) {
		this.indcount = indcount;
	}



	public void setExpcount(int expcount) {
		this.expcount = expcount;
	}



	public boolean isDashcomm() {
		return dashcomm;
	}

	public void setDashcomm(boolean dashcomm) {
		this.dashcomm = dashcomm;
	}

	public double getDistRejected() {
		return distRejected;
	}

	public void setDistRejected(double distRejected) {
		this.distRejected = distRejected;
	}

	public double getDistProcess() {
		return distProcess;
	}

	public void setDistProcess(double distProcess) {
		this.distProcess = distProcess;
	}

	public double getIndRejected() {
		return indRejected;
	}

	public void setIndRejected(double indRejected) {
		this.indRejected = indRejected;
	}

	public double getIndProcess() {
		return indProcess;
	}

	public void setIndProcess(double indProcess) {
		this.indProcess = indProcess;
	}

	public double getExpRejected() {
		return expRejected;
	}

	public void setExpRejected(double expRejected) {
		this.expRejected = expRejected;
	}

	public double getExpProcess() {
		return expProcess;
	}

	public void setExpProcess(double expProcess) {
		this.expProcess = expProcess;
	}

	

	public int getShopcount() {
		return shopcount;
	}



	public void setShopcount(int shopcount) {
		this.shopcount = shopcount;
	}



	public int getSgrmillcount() {
		return sgrmillcount;
	}



	public int getDistcount() {
		return distcount;
	}



	public int getIndcount() {
		return indcount;
	}



	public int getExpcount() {
		return expcount;
	}



	public double getApprecvd() {
		return apprecvd;
	}

	public void setApprecvd(double apprecvd) {
		this.apprecvd = apprecvd;
	}

	public double getAppprocced() {
		return appprocced;
	}

	public void setAppprocced(double appprocced) {
		this.appprocced = appprocced;
	}

	public double getAppapprvd() {
		return appapprvd;
	}

	public void setAppapprvd(double appapprvd) {
		this.appapprvd = appapprvd;
	}

	public double getDistApplied() {
		return distApplied;
	}

	public void setDistApplied(double distApplied) {
		this.distApplied = distApplied;
	}

	public double getDistApprovd() {
		return distApprovd;
	}

	public void setDistApprovd(double distApprovd) {
		this.distApprovd = distApprovd;
	}

	public double getDistLifted() {
		return distLifted;
	}

	public void setDistLifted(double distLifted) {
		this.distLifted = distLifted;
	}

	public double getIndApplied() {
		return indApplied;
	}

	public void setIndApplied(double indApplied) {
		this.indApplied = indApplied;
	}

	public double getIndApprovd() {
		return indApprovd;
	}

	public void setIndApprovd(double indApprovd) {
		this.indApprovd = indApprovd;
	}

	public double getIndLifted() {
		return indLifted;
	}

	public void setIndLifted(double indLifted) {
		this.indLifted = indLifted;
	}

	public double getExpApplied() {
		return expApplied;
	}

	public void setExpApplied(double expApplied) {
		this.expApplied = expApplied;
	}

	public double getExpApprovd() {
		return expApprovd;
	}

	public void setExpApprovd(double expApprovd) {
		this.expApprovd = expApprovd;
	}

	public double getExpLifted() {
		return expLifted;
	}

	public void setExpLifted(double expLifted) {
		this.expLifted = expLifted;
	}

	public double getTotstoraecap() {
		return totstoraecap;
	}

	public void setTotstoraecap(double totstoraecap) {
		this.totstoraecap = totstoraecap;
	}

	public double getOpeningBal() {
		return openingBal;
	}

	public void setOpeningBal(double openingBal) {
		this.openingBal = openingBal;
	}

	public double getProdTillDt() {
		return prodTillDt;
	}

	public void setProdTillDt(double prodTillDt) {
		this.prodTillDt = prodTillDt;
	}

	public double getSupplyTillDt() {
		return supplyTillDt;
	}

	public void setSupplyTillDt(double supplyTillDt) {
		this.supplyTillDt = supplyTillDt;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getDashhidden() {
		/*HomePageImpl impl = new HomePageImpl();		
		impl.getDashboard1Details(this);
		impl.getDashboard2Details(this);
		impl.getDashboard3Details(this);
		impl.getDashboard4Details(this);
		impl.getDashboard5Details(this);
		impl.getDashboard6Details(this);
		impl.getDashboard7Details(this);
		this.balance=this.openingBal+this.prodTillDt-this.supplyTillDt;
		this.distProcess=this.distApplied-this.distApprovd-this.distRejected;
		this.indProcess=this.indApplied-this.indApprovd-this.indRejected;
		this.expProcess=this.expApplied-this.expApprovd-this.expRejected;*/
		return dashhidden;
	}

	public void setDashhidden(String dashhidden) {
		this.dashhidden = dashhidden;
	}
	
	////////////////////raheela//////////////////////
	private boolean rederReportLink=false;	private boolean janhitlink=false;
	
	
	public boolean isJanhitlink() {
		return janhitlink;
	}

	public void setJanhitlink(boolean janhitlink) {
		this.janhitlink = janhitlink;
	}

	public boolean isRederReportLink() {
		return rederReportLink;
	}

	public void setRederReportLink(boolean rederReportLink) {
		this.rederReportLink = rederReportLink;
	}
	
	private int applicationstatus=0;
	private int applicationstatus1=0;
	private int applicationstatus2=0;
	private int applicationstatus3=0;
	private int applicationstatustot=0;
	private int applicationstatus1tot=0;
	private int applicationstatus2tot=0;
	private int applicationstatus3tot=0;
	private int applicationstatustotp=0;
	private int applicationstatus1totp=0;
	private int applicationstatus2totp=0;
	private int applicationstatus3totp=0;
	private int applicationstatustotr=0;
	private int applicationstatus1totr=0;
	private int applicationstatus2totr=0;
	private int applicationstatus3totr=0;
	private boolean flclflg=false;
	private int janhit=0;
	private int nivesh=0;
	
	
	
	
	
	
	
	 

	public int getApplicationstatus() {
		return applicationstatus;
	}



	public void setApplicationstatus(int applicationstatus) {
		this.applicationstatus = applicationstatus;
	}



	public int getApplicationstatus1() {
		return applicationstatus1;
	}



	public void setApplicationstatus1(int applicationstatus1) {
		this.applicationstatus1 = applicationstatus1;
	}



	public int getApplicationstatus2() {
		return applicationstatus2;
	}



	public void setApplicationstatus2(int applicationstatus2) {
		this.applicationstatus2 = applicationstatus2;
	}



	public int getApplicationstatus3() {
		return applicationstatus3;
	}



	public void setApplicationstatus3(int applicationstatus3) {
		this.applicationstatus3 = applicationstatus3;
	}



	public int getApplicationstatustot() {
		return applicationstatustot;
	}



	public void setApplicationstatustot(int applicationstatustot) {
		this.applicationstatustot = applicationstatustot;
	}



	public int getApplicationstatus1tot() {
		return applicationstatus1tot;
	}



	public void setApplicationstatus1tot(int applicationstatus1tot) {
		this.applicationstatus1tot = applicationstatus1tot;
	}



	public int getApplicationstatus2tot() {
		return applicationstatus2tot;
	}



	public void setApplicationstatus2tot(int applicationstatus2tot) {
		this.applicationstatus2tot = applicationstatus2tot;
	}



	public int getApplicationstatus3tot() {
		return applicationstatus3tot;
	}



	public void setApplicationstatus3tot(int applicationstatus3tot) {
		this.applicationstatus3tot = applicationstatus3tot;
	}



	public int getApplicationstatustotp() {
		return applicationstatustotp;
	}



	public void setApplicationstatustotp(int applicationstatustotp) {
		this.applicationstatustotp = applicationstatustotp;
	}



	public int getApplicationstatus1totp() {
		return applicationstatus1totp;
	}



	public void setApplicationstatus1totp(int applicationstatus1totp) {
		this.applicationstatus1totp = applicationstatus1totp;
	}



	public int getApplicationstatus2totp() {
		return applicationstatus2totp;
	}



	public void setApplicationstatus2totp(int applicationstatus2totp) {
		this.applicationstatus2totp = applicationstatus2totp;
	}



	public int getApplicationstatus3totp() {
		return applicationstatus3totp;
	}



	public void setApplicationstatus3totp(int applicationstatus3totp) {
		this.applicationstatus3totp = applicationstatus3totp;
	}



	public int getApplicationstatustotr() {
		return applicationstatustotr;
	}



	public void setApplicationstatustotr(int applicationstatustotr) {
		this.applicationstatustotr = applicationstatustotr;
	}



	public int getApplicationstatus1totr() {
		return applicationstatus1totr;
	}



	public void setApplicationstatus1totr(int applicationstatus1totr) {
		this.applicationstatus1totr = applicationstatus1totr;
	}



	public int getApplicationstatus2totr() {
		return applicationstatus2totr;
	}



	public void setApplicationstatus2totr(int applicationstatus2totr) {
		this.applicationstatus2totr = applicationstatus2totr;
	}



	public int getApplicationstatus3totr() {
		return applicationstatus3totr;
	}



	public void setApplicationstatus3totr(int applicationstatus3totr) {
		this.applicationstatus3totr = applicationstatus3totr;
	}



	public int getNivesh() {
		return nivesh;
	}

	public void setNivesh(int nivesh) {
		this.nivesh = nivesh;
	}

	public int getJanhit() {
		return janhit;
	}

	public void setJanhit(int janhit) {
		this.janhit = janhit;
	}

	public boolean isFlclflg() {
		return flclflg;
	}

	public void setFlclflg(boolean flclflg) {
		this.flclflg = flclflg;
	}

	 
	private boolean onetimerunflg;
	
	public boolean isOnetimerunflg() {
		return onetimerunflg;
	}



	public void setOnetimerunflg(boolean onetimerunflg) {
		this.onetimerunflg = onetimerunflg;
	}



	public String getLoginCheck() {
		try{
		if(ResourceUtil.getUserNameAllReq()==null){		
			loginDashboardFlg=false;
			loginFlg=false; 
			 if(newsText1==null) { 
				 this.newsText1=new HomePageImpl().getNews1(this);
			 }
			if(newsText2==null) {
				this.newsText2=new HomePageImpl().getNews2(this);
						 }
			if(newsText3==null) {
				this.newsText3=new HomePageImpl().getNews3(this);
			}
			
			
			
			 
		} 	
		else{
			if(!onetimerunflg) {
				onetimerunflg=true;
			loginDashboardFlg=false;
			loginFlg=true;
			loginUsrNm=ResourceUtil.getUserNameAllReq().toUpperCase()+".";
			if( ResourceUtil.getUserNameAllReq().equalsIgnoreCase("Excise-Commissioner") || ResourceUtil.getUserNameAllReq().equalsIgnoreCase("Excise-COMM") )
			{   
				rederReportLink=true;
				 this.dashcomm=true;	this.psec=false	;
				 impl.getAppdata(this);
				 impl.getDashboard8Details(this);
				 //impl.getDashboard1Details(this);
					//impl.getDashboard2Details(this);
					//impl.getDashboard3Details(this);
					//.getDashboard4Details(this);
					//impl.getDashboard5Details(this);
					//impl.getDashboard6Details(this);
					//impl.getDashboard7Details(this);
					//impl.getJanhit(this);
					//impl.getoccasonal(this);		
					//impl.getNivesh(this);this.flclflg=true;impl.getJanhitSpirit(this);impl.getpwalcohol(this);
					
					//impl.getLiquorIndent(this);
					
				}
			 
			else{this.dashcomm=false;this.psec=false	;
					this.flclflg=false;
				}
		   } 
		}		
		
	}catch (Exception e) {
		e.printStackTrace();
	}

		return loginCheck;
	}
	
	
	//////////////Commisioner dashboard//////////////
	
	
	private int janhitwinetot=0;
	private int janhitspirittot=0;
	private int janhitdugtot=0;
	private int janhitwineaprvd=0;
	private int janhitspiritaprvd=0;
	private int janhitdugaprvd=0;
	private int janhitwinepen=0;
	private int janhitspiritpen=0;
	private int janhitdugpen=0;
	private int janhitwinerej=0;
	private int janhitspiritrej=0;
	private int janhitdugrej=0;
	
	
	
	private int spiritalchotot=0;
	private int distestbtot=0;
	private int distoptot=0;
	private int spiritalchoaprvd=0;
	private int distestbaprvd=0;
	private int distopaprvd=0;
	private int spiritalchopen=0;
	private int distestbpen=0;
	private int distoppen=0;
	private int spiritalchorej=0;
	private int distestbrej=0;
	private int brewoprej=0;
	private int brewestbrej=0;
	private int brewopaprvd=0;
	private int brewoptot=0;
	private int brewoppend=0;
	private int brewestbtot=0;
	private int brewestbopn=0;
	private int brewestbpen=0;
	private int distoprej=0;

private int spirit=0;
private int narcotic=0;
private int wine=0;


private int occpen=0;
private int occrej=0;
private int occaprv=0;


private double pwindentd=0;
private double pwlifted=0;
private double pwexp=0;
private double pwallotd=0;



private int pencomm01=0;
private int pencomm05=0;
private int pencomm07=0;
private int pencomm08=0;
private int pencomm10=0;
	 




	public int getPencomm01() {
	return pencomm01;
}



public void setPencomm01(int pencomm01) {
	this.pencomm01 = pencomm01;
}



public int getPencomm05() {
	return pencomm05;
}



public void setPencomm05(int pencomm05) {
	this.pencomm05 = pencomm05;
}



public int getPencomm07() {
	return pencomm07;
}



public void setPencomm07(int pencomm07) {
	this.pencomm07 = pencomm07;
}



public int getPencomm08() {
	return pencomm08;
}



public void setPencomm08(int pencomm08) {
	this.pencomm08 = pencomm08;
}



public int getPencomm10() {
	return pencomm10;
}



public void setPencomm10(int pencomm10) {
	this.pencomm10 = pencomm10;
}



	public double getPwindentd() {
	return pwindentd;
}



public void setPwindentd(double pwindentd) {
	this.pwindentd = pwindentd;
}



public double getPwlifted() {
	return pwlifted;
}



public void setPwlifted(double pwlifted) {
	this.pwlifted = pwlifted;
}



public double getPwexp() {
	return pwexp;
}



public void setPwexp(double pwexp) {
	this.pwexp = pwexp;
}



public double getPwallotd() {
	return pwallotd;
}



public void setPwallotd(double pwallotd) {
	this.pwallotd = pwallotd;
}





	public int getOccpen() {
	return occpen;
}



public void setOccpen(int occpen) {
	this.occpen = occpen;
}



public int getOccrej() {
	return occrej;
}



public void setOccrej(int occrej) {
	this.occrej = occrej;
}



public int getOccaprv() {
	return occaprv;
}



public void setOccaprv(int occaprv) {
	this.occaprv = occaprv;
}



	public int getSpirit() {
	return spirit;
}



public void setSpirit(int spirit) {
	this.spirit = spirit;
}



public int getNarcotic() {
	return narcotic;
}



public void setNarcotic(int narcotic) {
	this.narcotic = narcotic;
}



public int getWine() {
	return wine;
}



public void setWine(int wine) {
	this.wine = wine;
}



	public int getJanhitwinetot() {
		return janhitwinetot;
	}

	public void setJanhitwinetot(int janhitwinetot) {
		this.janhitwinetot = janhitwinetot;
	}

	 

	public int getJanhitdugtot() {
		return janhitdugtot;
	}

	public void setJanhitdugtot(int janhitdugtot) {
		this.janhitdugtot = janhitdugtot;
	}

	public int getJanhitwineaprvd() {
		return janhitwineaprvd;
	}

	public void setJanhitwineaprvd(int janhitwineaprvd) {
		this.janhitwineaprvd = janhitwineaprvd;
	}

	 
	public int getJanhitdugaprvd() {
		return janhitdugaprvd;
	}

	public void setJanhitdugaprvd(int janhitdugaprvd) {
		this.janhitdugaprvd = janhitdugaprvd;
	}

	public int getJanhitwinepen() {
		return janhitwinepen;
	}

	public void setJanhitwinepen(int janhitwinepen) {
		this.janhitwinepen = janhitwinepen;
	}



	public int getJanhitdugpen() {
		return janhitdugpen;
	}

	public void setJanhitdugpen(int janhitdugpen) {
		this.janhitdugpen = janhitdugpen;
	}

	public int getJanhitwinerej() {
		return janhitwinerej;
	}

	public void setJanhitwinerej(int janhitwinerej) {
		this.janhitwinerej = janhitwinerej;
	}

	 
	public int getJanhitspirittot() {
		return janhitspirittot;
	}

	public void setJanhitspirittot(int janhitspirittot) {
		this.janhitspirittot = janhitspirittot;
	}

	public int getJanhitspiritaprvd() {
		return janhitspiritaprvd;
	}

	public void setJanhitspiritaprvd(int janhitspiritaprvd) {
		this.janhitspiritaprvd = janhitspiritaprvd;
	}

	public int getJanhitspiritpen() {
		return janhitspiritpen;
	}

	public void setJanhitspiritpen(int janhitspiritpen) {
		this.janhitspiritpen = janhitspiritpen;
	}

	public int getJanhitspiritrej() {
		return janhitspiritrej;
	}

	public void setJanhitspiritrej(int janhitspiritrej) {
		this.janhitspiritrej = janhitspiritrej;
	}

	public int getJanhitdugrej() {
		return janhitdugrej;
	}

	public void setJanhitdugrej(int janhitdugrej) {
		this.janhitdugrej = janhitdugrej;
	}

	public int getSpiritalchotot() {
		return spiritalchotot;
	}

	public void setSpiritalchotot(int spiritalchotot) {
		this.spiritalchotot = spiritalchotot;
	}

	public int getDistestbtot() {
		return distestbtot;
	}

	public void setDistestbtot(int distestbtot) {
		this.distestbtot = distestbtot;
	}

	public int getDistoptot() {
		return distoptot;
	}

	public void setDistoptot(int distoptot) {
		this.distoptot = distoptot;
	}

	public int getSpiritalchoaprvd() {
		return spiritalchoaprvd;
	}

	public void setSpiritalchoaprvd(int spiritalchoaprvd) {
		this.spiritalchoaprvd = spiritalchoaprvd;
	}

	public int getDistestbaprvd() {
		return distestbaprvd;
	}

	public void setDistestbaprvd(int distestbaprvd) {
		this.distestbaprvd = distestbaprvd;
	}

	public int getDistopaprvd() {
		return distopaprvd;
	}

	public void setDistopaprvd(int distopaprvd) {
		this.distopaprvd = distopaprvd;
	}

	public int getSpiritalchopen() {
		return spiritalchopen;
	}

	public void setSpiritalchopen(int spiritalchopen) {
		this.spiritalchopen = spiritalchopen;
	}

	public int getDistestbpen() {
		return distestbpen;
	}

	public void setDistestbpen(int distestbpen) {
		this.distestbpen = distestbpen;
	}

	public int getDistoppen() {
		return distoppen;
	}

	public void setDistoppen(int distoppen) {
		this.distoppen = distoppen;
	}

	public int getSpiritalchorej() {
		return spiritalchorej;
	}

	public void setSpiritalchorej(int spiritalchorej) {
		this.spiritalchorej = spiritalchorej;
	}

	public int getDistestbrej() {
		return distestbrej;
	}

	public void setDistestbrej(int distestbrej) {
		this.distestbrej = distestbrej;
	}

	public int getBrewoprej() {
		return brewoprej;
	}

	public void setBrewoprej(int brewoprej) {
		this.brewoprej = brewoprej;
	}

	public int getBrewestbrej() {
		return brewestbrej;
	}

	public void setBrewestbrej(int brewestbrej) {
		this.brewestbrej = brewestbrej;
	}

	public int getBrewopaprvd() {
		return brewopaprvd;
	}

	public void setBrewopaprvd(int brewopaprvd) {
		this.brewopaprvd = brewopaprvd;
	}

	public int getBrewoptot() {
		return brewoptot;
	}

	public void setBrewoptot(int brewoptot) {
		this.brewoptot = brewoptot;
	}

	public int getBrewoppend() {
		return brewoppend;
	}

	public void setBrewoppend(int brewoppend) {
		this.brewoppend = brewoppend;
	}

	public int getBrewestbtot() {
		return brewestbtot;
	}

	public void setBrewestbtot(int brewestbtot) {
		this.brewestbtot = brewestbtot;
	}

	public int getBrewestbopn() {
		return brewestbopn;
	}

	public void setBrewestbopn(int brewestbopn) {
		this.brewestbopn = brewestbopn;
	}

	public int getBrewestbpen() {
		return brewestbpen;
	}

	public void setBrewestbpen(int brewestbpen) {
		this.brewestbpen = brewestbpen;
	}

	public int getDistoprej() {
		return distoprej;
	}

	public void setDistoprej(int distoprej) {
		this.distoprej = distoprej;
	}
	
	
	
	
	
	
	
	
	

	//------------ vivek code ---------------
	
	
	
	private int clTot_P=0;
	private int flTot_P=0;
	private int beerTot_P=0;
	private int cl_1Day=0;
	private int cl_2Day=0;
	private int cl_3Day=0;
	private int cl_4Day=0;
	private int cl_5Day=0;
	private int cl_MoreDay=0;
	private int fl_1Day=0;
	private int fl_2Day=0;
	private int fl_3Day=0;
	private int fl_4Day=0;
	private int fl_5Day=0;
	private int fl_MoreDay=0;
	private int beer_1Day=0;
	private int beer_2Day=0;
	private int beer_3Day=0;
	private int beer_4Day=0;
	private int beer_5Day=0;
	private int beer_MoreDay=0;
	
	
	private String cl_Oldest_Date;
	private String fl_Oldest_Date;
	private String beer_Oldest_Date;

	private int clprod=0;
	private int clprod1=0;
	private int flprod=0;
	private int flprod1=0;
	private int beerprod=0;
	private int beerprod1=0;
	private int cldipt=0;
	private int cldipt1=0;
	private int fldipt=0;
	private int fldipt1=0;
	private int beerdipt=0;
	private int beerdipt1=0;
	
	
	private int clduty, clduty1, flduty , flduty1, beerduty, beerduty1;
	
	private String ystrdy;
	private String befrystrdy;






	public String getYstrdy() {
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		try {
		    
		    cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
		    ystrdy  = simpleDateFormat.format(cal.getTime());
		    
		} catch (Exception e) {
		}
		return ystrdy;
	}



	public void setYstrdy(String ystrdy) {
		this.ystrdy = ystrdy;
	}



	public String getBefrystrdy() 
	{String pattern = "dd-MM-yyyy";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	java.util.Calendar cal = java.util.Calendar.getInstance();
	try {
	    
	    cal.add(java.util.Calendar.DAY_OF_MONTH, -2);
	    befrystrdy  = simpleDateFormat.format(cal.getTime());
	    
	} catch (Exception e) {
	}return befrystrdy;
	}



	public void setBefrystrdy(String befrystrdy) {
		this.befrystrdy = befrystrdy;
	}



	public int getClprod() {
		return clprod;
	}



	public void setClprod(int clprod) {
		this.clprod = clprod;
	}



	public int getClprod1() {
		return clprod1;
	}



	public void setClprod1(int clprod1) {
		this.clprod1 = clprod1;
	}



	public int getFlprod() {
		return flprod;
	}



	public void setFlprod(int flprod) {
		this.flprod = flprod;
	}



	public int getFlprod1() {
		return flprod1;
	}



	public void setFlprod1(int flprod1) {
		this.flprod1 = flprod1;
	}
	
	
	



	



	

	


	public int getClduty() {
		return clduty;
	}



	public void setClduty(int clduty) {
		this.clduty = clduty;
	}



	public int getClduty1() {
		return clduty1;
	}



	public void setClduty1(int clduty1) {
		this.clduty1 = clduty1;
	}



	public int getFlduty() {
		return flduty;
	}



	public void setFlduty(int flduty) {
		this.flduty = flduty;
	}



	public int getFlduty1() {
		return flduty1;
	}



	public void setFlduty1(int flduty1) {
		this.flduty1 = flduty1;
	}



	public int getBeerduty() {
		return beerduty;
	}



	public void setBeerduty(int beerduty) {
		this.beerduty = beerduty;
	}



	public int getBeerduty1() {
		return beerduty1;
	}



	public void setBeerduty1(int beerduty1) {
		this.beerduty1 = beerduty1;
	}



	public int getBeerprod() {
		return beerprod;
	}



	public void setBeerprod(int beerprod) {
		this.beerprod = beerprod;
	}



	public int getBeerprod1() {
		return beerprod1;
	}



	public void setBeerprod1(int beerprod1) {
		this.beerprod1 = beerprod1;
	}



	public int getCldipt() {
		return cldipt;
	}



	public void setCldipt(int cldipt) {
		this.cldipt = cldipt;
	}



	public int getCldipt1() {
		return cldipt1;
	}



	public void setCldipt1(int cldipt1) {
		this.cldipt1 = cldipt1;
	}



	public int getFldipt() {
		return fldipt;
	}



	public void setFldipt(int fldipt) {
		this.fldipt = fldipt;
	}



	public int getFldipt1() {
		return fldipt1;
	}



	public void setFldipt1(int fldipt1) {
		this.fldipt1 = fldipt1;
	}



	public int getBeerdipt() {
		return beerdipt;
	}



	public void setBeerdipt(int beerdipt) {
		this.beerdipt = beerdipt;
	}



	public int getBeerdipt1() {
		return beerdipt1;
	}



	public void setBeerdipt1(int beerdipt1) {
		this.beerdipt1 = beerdipt1;
	}



	public int getClTot_P() {
		return clTot_P;
	}



	public void setClTot_P(int clTot_P) {
		this.clTot_P = clTot_P;
	}



	public int getFlTot_P() {
		return flTot_P;
	}



	public void setFlTot_P(int flTot_P) {
		this.flTot_P = flTot_P;
	}



	public int getBeerTot_P() {
		return beerTot_P;
	}



	public void setBeerTot_P(int beerTot_P) {
		this.beerTot_P = beerTot_P;
	}



	public int getCl_1Day() {
		return cl_1Day;
	}



	public void setCl_1Day(int cl_1Day) {
		this.cl_1Day = cl_1Day;
	}



	public int getCl_2Day() {
		return cl_2Day;
	}



	public void setCl_2Day(int cl_2Day) {
		this.cl_2Day = cl_2Day;
	}



	public int getCl_3Day() {
		return cl_3Day;
	}



	public void setCl_3Day(int cl_3Day) {
		this.cl_3Day = cl_3Day;
	}



	public int getCl_4Day() {
		return cl_4Day;
	}



	public void setCl_4Day(int cl_4Day) {
		this.cl_4Day = cl_4Day;
	}



	public int getCl_5Day() {
		return cl_5Day;
	}



	public void setCl_5Day(int cl_5Day) {
		this.cl_5Day = cl_5Day;
	}



	public int getCl_MoreDay() {
		return cl_MoreDay;
	}



	public void setCl_MoreDay(int cl_MoreDay) {
		this.cl_MoreDay = cl_MoreDay;
	}



	public int getFl_1Day() {
		return fl_1Day;
	}



	public void setFl_1Day(int fl_1Day) {
		this.fl_1Day = fl_1Day;
	}



	public int getFl_2Day() {
		return fl_2Day;
	}



	public void setFl_2Day(int fl_2Day) {
		this.fl_2Day = fl_2Day;
	}



	public int getFl_3Day() {
		return fl_3Day;
	}



	public void setFl_3Day(int fl_3Day) {
		this.fl_3Day = fl_3Day;
	}



	public int getFl_4Day() {
		return fl_4Day;
	}



	public void setFl_4Day(int fl_4Day) {
		this.fl_4Day = fl_4Day;
	}



	public int getFl_5Day() {
		return fl_5Day;
	}



	public void setFl_5Day(int fl_5Day) {
		this.fl_5Day = fl_5Day;
	}



	public int getFl_MoreDay() {
		return fl_MoreDay;
	}



	public void setFl_MoreDay(int fl_MoreDay) {
		this.fl_MoreDay = fl_MoreDay;
	}



	public int getBeer_1Day() {
		return beer_1Day;
	}



	public void setBeer_1Day(int beer_1Day) {
		this.beer_1Day = beer_1Day;
	}



	public int getBeer_2Day() {
		return beer_2Day;
	}



	public void setBeer_2Day(int beer_2Day) {
		this.beer_2Day = beer_2Day;
	}



	public int getBeer_3Day() {
		return beer_3Day;
	}



	public void setBeer_3Day(int beer_3Day) {
		this.beer_3Day = beer_3Day;
	}



	public int getBeer_4Day() {
		return beer_4Day;
	}



	public void setBeer_4Day(int beer_4Day) {
		this.beer_4Day = beer_4Day;
	}



	public int getBeer_5Day() {
		return beer_5Day;
	}



	public void setBeer_5Day(int beer_5Day) {
		this.beer_5Day = beer_5Day;
	}



	public int getBeer_MoreDay() {
		return beer_MoreDay;
	}



	public void setBeer_MoreDay(int beer_MoreDay) {
		this.beer_MoreDay = beer_MoreDay;
	}



	public String getCl_Oldest_Date() {
		return cl_Oldest_Date;
	}



	public void setCl_Oldest_Date(String cl_Oldest_Date) {
		this.cl_Oldest_Date = cl_Oldest_Date;
	}



	public String getFl_Oldest_Date() {
		return fl_Oldest_Date;
	}



	public void setFl_Oldest_Date(String fl_Oldest_Date) {
		this.fl_Oldest_Date = fl_Oldest_Date;
	}



	public String getBeer_Oldest_Date() {
		return beer_Oldest_Date;
	}



	public void setBeer_Oldest_Date(String beer_Oldest_Date) {
		this.beer_Oldest_Date = beer_Oldest_Date;
	}
	
	
	private boolean flag_detail;
	private String detail_header;
	private boolean flag_btn=true;
	
	
	
	
	
	public boolean isFlag_btn() {
		return flag_btn;
	}



	public void setFlag_btn(boolean flag_btn) {
		this.flag_btn = flag_btn;
	}



	public String getDetail_header() {
		return detail_header;
	}



	public void setDetail_header(String detail_header) {
		this.detail_header = detail_header;
	}



	public boolean isFlag_detail() {
		return flag_detail;
	}



	public void setFlag_detail(boolean flag_detail) {
		this.flag_detail = flag_detail;
	}



	public void details_cl(){
		this.approvalflg=false;
		this.unit_type = "1";
		this.flag_detail = true;
		this.detail_header = "Details For CL(BL)";
		
		
	}
	
	
	
	
	public void details_fl(){
		
		this.unit_type = "2";
		this.flag_detail = true;
		this.detail_header = "Details For FL(BL)";

		
	}
	
	public void details_beer(){
		
		this.unit_type = "3";
		this.flag_detail = true;

		this.detail_header = "Details For BEER(BL)";
	}
	
	public void back(){
		this.unit_type = "0";
		this.flag_detail = false;	
	}
	
	private String unit_type;
	
	

	
	public String getUnit_type() {
		return unit_type;
	}



	public void setUnit_type(String unit_type) {
		this.unit_type = unit_type;
	}


	private ArrayList tableData = new ArrayList();

	public ArrayList getTableData() {
		
		if(this.unit_type==null || this.unit_type.length()==0)
			return tableData;
		
		this.tableData = new HomePageImpl().getTableData(this);
		return tableData;
	}

	public void setTableData(ArrayList tableData) {
		this.tableData = tableData;
	}
	

	
	
	
	
	
	//------------- end vivek code --------------
	public void gobtn(){
		this.flag_btn = false;	 unitflg=false;
		this.flag_detail = false;	//new HomePageImpl().getdetdash(this);
		this.approvalflg=false;
		 
		reportflg=false;
		 
		trackflg=false;
	}
	public void gonivesh(){
		this.flag_btn = false;	
		this.flag_detail = false;	 
		this.approvalflg=false;
		 niveshflg=true;
		reportflg=false;
		 janhitflg=false; unitflg=false;
		trackflg=false;
		liqourindntflg=false;
		impl.getNivesh(this);
	}
	public void gojanhit(){
		this.flag_btn = false;	
		this.flag_detail = false;	 
		this.approvalflg=false;
		 niveshflg=false;
		reportflg=false;
		 janhitflg=true;
		trackflg=false; unitflg=false;
		liqourindntflg=false;
		impl.getJanhit(this);
		impl.getJanhitSpirit(this);
		//rahul 03-10-2020
		impl.getoccasonal(this);
		//rahul 14-10-2020
		impl.getotherservices(this);
		impl.getotherservices_ena_imp_exp_imfl(this);
		
	}
	public void goliqrindnt(){
		this.flag_btn = false;	
		this.flag_detail = false;	 
		this.approvalflg=false;
		 niveshflg=false;
		reportflg=false;
		 janhitflg=false;
		trackflg=false; unitflg=false;
		liqourindntflg=true;
		impl.getLiquorIndent(this);
	}
	public void backbtn(){
		this.flag_btn = true;	
		this.flag_detail = false;
		 janhitflg=false;
			trackflg=false;
			liqourindntflg=false;
			 niveshflg=false;
			 unitflg=false;
	}
	public void gounits(){
		unitflg=true;
		this.flag_detail = false;
		 janhitflg=false;
			trackflg=false;this.flag_btn = false;
			liqourindntflg=false;
			 niveshflg=false;
			 
		
	 
		impl.getDashboard6Details(this);
		
	}
	private boolean reportflg;
	private boolean trackflg;
	
	private boolean unitflg;
	
	
	public boolean isUnitflg() {
		return unitflg;
	}



	public void setUnitflg(boolean unitflg) {
		this.unitflg = unitflg;
	}



	public boolean isTrackflg() {
		return trackflg;
	}



	public void setTrackflg(boolean trackflg) {
		this.trackflg = trackflg;
	}



	public boolean isReportflg() {
			return reportflg;
		}



		public void setReportflg(boolean reportflg) {
			this.reportflg = reportflg;
		}



	public void appv(){
			this.approvalflg=true;
			flag_detail=false;
			reportflg=false;
			flag_btn=true;
			 janhitflg=false;
				trackflg=false;
				liqourindntflg=false;
				 niveshflg=false;
		}
	
	public void reprt(){
		this.approvalflg=false;
		flag_detail=false;
		reportflg=true;
		flag_btn=true;
		 janhitflg=false;
			trackflg=false;
			liqourindntflg=false;
			 niveshflg=false;
	}
	public void trck(){
		this.approvalflg=false;
		flag_detail=false;		reportflg=false;
		flag_btn=true;
		trackflg=true;
		 janhitflg=false;
			 
			liqourindntflg=false;
			 niveshflg=false;
	}
	public void bck(){
		this.approvalflg=false;
		flag_detail=false;
		this.flag_btn = true;	
		reportflg=false;
		 janhitflg=false;
			trackflg=false;
			liqourindntflg=false;
			 niveshflg=false;unitflg=false;
	}
	
//rahul 30-09-2020	
	
	private String dist_mufl_flg="F";
	private String w_mufl_flg="F";
	private String main_flg="T";
	private String type;
	private String type_;
	
	
	
public String getType_() {
		return type_;
	}



	public void setType_(String type_) {
		this.type_ = type_;
	}



public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



public String getMain_flg() {
		return main_flg;
	}



	public void setMain_flg(String main_flg) {
		this.main_flg = main_flg;
	}



public String getW_mufl_flg() {
		return w_mufl_flg;
	}



	public void setW_mufl_flg(String w_mufl_flg) {
		this.w_mufl_flg = w_mufl_flg;
	}



public String getDist_mufl_flg() {
		return dist_mufl_flg;
	}
	public void setDist_mufl_flg(String dist_mufl_flg) {
		this.dist_mufl_flg = dist_mufl_flg;
	}

	private ArrayList dist_mufllist = new ArrayList();
	private ArrayList bond_wholesale_mufllist = new ArrayList();
	

public ArrayList getBond_wholesale_mufllist() {
		return bond_wholesale_mufllist;
	}



	public void setBond_wholesale_mufllist(ArrayList bond_wholesale_mufllist) {
		this.bond_wholesale_mufllist = bond_wholesale_mufllist;
	}



public ArrayList getDist_mufllist() {
	try{
	//dist_mufllist=impl.getdist_mufllist(this);
	}catch(Exception e){
		e.printStackTrace();
	}
		return dist_mufllist;
	}

	public void setDist_mufllist(ArrayList dist_mufllist) {
		this.dist_mufllist = dist_mufllist;
	}
public void	close_dist_mufl(){
	dist_mufl_flg="F";
	 main_flg="T";
	 w_mufl_flg="F";
	 dist_mufllist.clear();
	 bond_wholesale_mufllist.clear();
}



public void dist_mufl(){
	type_="Manufacturing Unit";
	type="FL";
	dist_mufl_flg="T";
	 main_flg="F";
	 try{
			dist_mufllist=impl.getdist_mufllist(this);
			}catch(Exception e){
				e.printStackTrace();
			}
	
}
public void dist_mucl(){
	type_="Manufacturing Unit";
	type="CL";
	dist_mufl_flg="T";
	 main_flg="F";
	 try{
			dist_mufllist=impl.getdist_mufllist(this);
			}catch(Exception e){
				e.printStackTrace();
			}
	
}
public void dist_mubeer(){
	type_="Manufacturing Unit";
	type="BEER";
	dist_mufl_flg="T";
	 main_flg="F";
	 try{
			dist_mufllist=impl.getdist_mufllist(this);
			}catch(Exception e){
				e.printStackTrace();
			}
}


 
public void bond_bond2(){
	type_="Bonds";
	type="BOND2";
	w_mufl_flg="T";
	 main_flg="F";
	 try{
		 bond_wholesale_mufllist=impl.getdist_wsllist(this);
			}catch(Exception e){
				e.printStackTrace();
			}
}
public void bond_bond1(){
	type_="Bonds";
	type="BOND1";
	w_mufl_flg="T";
	 main_flg="F";
	 try{
		 bond_wholesale_mufllist=impl.getdist_wsllist(this);
			}catch(Exception e){
				e.printStackTrace();
			}
}
public void wholesale_wfl(){
	type_="Wholesale";
	type="WFL";
	w_mufl_flg="T";
	 main_flg="F";
	 try{
		 bond_wholesale_mufllist=impl.getdist_wsllist(this);
			}catch(Exception e){
				e.printStackTrace();
			}
}
public void wholesale_wbeer(){
	type_="Wholesale";
	type="WBEER";
	w_mufl_flg="T";
	 main_flg="F";
	 try{
		 bond_wholesale_mufllist=impl.getdist_wsllist(this);
			}catch(Exception e){
				e.printStackTrace();
			}
}
public void wholesale_wcl(){
	type_="Wholesale";
	type="WCL";
	w_mufl_flg="T";
	 main_flg="F";
	 try{
		 bond_wholesale_mufllist=impl.getdist_wsllist(this);
			}catch(Exception e){
				e.printStackTrace();
			}
}

//rahul 01-10-2020
private int newimpotunitcount  =0;
private int fl2drenewcount=0;
private int niveshmitracount=0;
private int usefulpubliccount=0;



public int getNewimpotunitcount() {
	return newimpotunitcount;
}
public void setNewimpotunitcount(int newimpotunitcount) {
	this.newimpotunitcount = newimpotunitcount;
}
public int getFl2drenewcount() {
	return fl2drenewcount;
}
public void setFl2drenewcount(int fl2drenewcount) {
	this.fl2drenewcount = fl2drenewcount;
}
public int getNiveshmitracount() {
	return niveshmitracount;
}
public void setNiveshmitracount(int niveshmitracount) {
	this.niveshmitracount = niveshmitracount;
}
public int getUsefulpubliccount() {
	return usefulpubliccount;
}
public void setUsefulpubliccount(int usefulpubliccount) {
	this.usefulpubliccount = usefulpubliccount;
}
	
//rahul 03-10-2020

private int janhitwineaprvdwthin7day=0;
private int janhitdugaprvdwithin7day=0;
private int janhitdugpenexp=0;
private int janhitdugaprvdexp=0;
private int janhitdugaprvdexpwithin7day=0;
private int janhitdugrejexp=0;
private int occaprvwithin7day=0;
private int janhitspiritaprvdwithin7day=0;


public int getJanhitwineaprvdwthin7day() {
	return janhitwineaprvdwthin7day;
}
public void setJanhitwineaprvdwthin7day(int janhitwineaprvdwthin7day) {
	this.janhitwineaprvdwthin7day = janhitwineaprvdwthin7day;
}
public int getJanhitdugaprvdwithin7day() {
	return janhitdugaprvdwithin7day;
}
public void setJanhitdugaprvdwithin7day(int janhitdugaprvdwithin7day) {
	this.janhitdugaprvdwithin7day = janhitdugaprvdwithin7day;
}
public int getJanhitdugpenexp() {
	return janhitdugpenexp;
}
public void setJanhitdugpenexp(int janhitdugpenexp) {
	this.janhitdugpenexp = janhitdugpenexp;
}
public int getJanhitdugaprvdexp() {
	return janhitdugaprvdexp;
}
public void setJanhitdugaprvdexp(int janhitdugaprvdexp) {
	this.janhitdugaprvdexp = janhitdugaprvdexp;
}
public int getJanhitdugaprvdexpwithin7day() {
	return janhitdugaprvdexpwithin7day;
}
public void setJanhitdugaprvdexpwithin7day(int janhitdugaprvdexpwithin7day) {
	this.janhitdugaprvdexpwithin7day = janhitdugaprvdexpwithin7day;
}
public int getJanhitdugrejexp() {
	return janhitdugrejexp;
}
public void setJanhitdugrejexp(int janhitdugrejexp) {
	this.janhitdugrejexp = janhitdugrejexp;
}
public int getOccaprvwithin7day() {
	return occaprvwithin7day;
}
public void setOccaprvwithin7day(int occaprvwithin7day) {
	this.occaprvwithin7day = occaprvwithin7day;
}
public int getJanhitspiritaprvdwithin7day() {
	return janhitspiritaprvdwithin7day;
}
public void setJanhitspiritaprvdwithin7day(int janhitspiritaprvdwithin7day) {
	this.janhitspiritaprvdwithin7day = janhitspiritaprvdwithin7day;
}


private int otherbwflpen=0;
private int otherbwflaprvd=0;
private int otherbwflaprvdwthin7day=0;
private int otherbwflrej=0;

private int otherfl2dpen=0;
private int otherfl2daprvd=0;
private int otherfl2daprvdwithin7day=0;
private int otherfl2drej=0;

private int otherenaimppen=0;
private int otherenaimpaprvd=0;
private int otherenaimpaprvdwithin7day=0;
private int otherenaimprej=0;

private int otherenaexppen=0;
private int otherenaexpaprv=0;
private int otherenaexpaprvwithin7day=0;
private int otherenaexprej=0;

private int otherimflexppen=0;
private int otherimflexpaprvd=0;
private int otherimflexpaprvdwithin7day=0;
private int otherimflexprej=0;



public int getOtherbwflpen() {
	return otherbwflpen;
}



public void setOtherbwflpen(int otherbwflpen) {
	this.otherbwflpen = otherbwflpen;
}



public int getOtherbwflaprvd() {
	return otherbwflaprvd;
}



public void setOtherbwflaprvd(int otherbwflaprvd) {
	this.otherbwflaprvd = otherbwflaprvd;
}



public int getOtherbwflaprvdwthin7day() {
	return otherbwflaprvdwthin7day;
}



public void setOtherbwflaprvdwthin7day(int otherbwflaprvdwthin7day) {
	this.otherbwflaprvdwthin7day = otherbwflaprvdwthin7day;
}



public int getOtherbwflrej() {
	return otherbwflrej;
}



public void setOtherbwflrej(int otherbwflrej) {
	this.otherbwflrej = otherbwflrej;
}



public int getOtherfl2dpen() {
	return otherfl2dpen;
}



public void setOtherfl2dpen(int otherfl2dpen) {
	this.otherfl2dpen = otherfl2dpen;
}



public int getOtherfl2daprvd() {
	return otherfl2daprvd;
}



public void setOtherfl2daprvd(int otherfl2daprvd) {
	this.otherfl2daprvd = otherfl2daprvd;
}



public int getOtherfl2daprvdwithin7day() {
	return otherfl2daprvdwithin7day;
}



public void setOtherfl2daprvdwithin7day(int otherfl2daprvdwithin7day) {
	this.otherfl2daprvdwithin7day = otherfl2daprvdwithin7day;
}



public int getOtherfl2drej() {
	return otherfl2drej;
}



public void setOtherfl2drej(int otherfl2drej) {
	this.otherfl2drej = otherfl2drej;
}



public int getOtherenaimppen() {
	return otherenaimppen;
}



public void setOtherenaimppen(int otherenaimppen) {
	this.otherenaimppen = otherenaimppen;
}



public int getOtherenaimpaprvd() {
	return otherenaimpaprvd;
}



public void setOtherenaimpaprvd(int otherenaimpaprvd) {
	this.otherenaimpaprvd = otherenaimpaprvd;
}



public int getOtherenaimpaprvdwithin7day() {
	return otherenaimpaprvdwithin7day;
}



public void setOtherenaimpaprvdwithin7day(int otherenaimpaprvdwithin7day) {
	this.otherenaimpaprvdwithin7day = otherenaimpaprvdwithin7day;
}



public int getOtherenaimprej() {
	return otherenaimprej;
}



public void setOtherenaimprej(int otherenaimprej) {
	this.otherenaimprej = otherenaimprej;
}



public int getOtherenaexppen() {
	return otherenaexppen;
}



public void setOtherenaexppen(int otherenaexppen) {
	this.otherenaexppen = otherenaexppen;
}



public int getOtherenaexpaprv() {
	return otherenaexpaprv;
}



public void setOtherenaexpaprv(int otherenaexpaprv) {
	this.otherenaexpaprv = otherenaexpaprv;
}



public int getOtherenaexpaprvwithin7day() {
	return otherenaexpaprvwithin7day;
}



public void setOtherenaexpaprvwithin7day(int otherenaexpaprvwithin7day) {
	this.otherenaexpaprvwithin7day = otherenaexpaprvwithin7day;
}



public int getOtherenaexprej() {
	return otherenaexprej;
}



public void setOtherenaexprej(int otherenaexprej) {
	this.otherenaexprej = otherenaexprej;
}



public int getOtherimflexppen() {
	return otherimflexppen;
}



public void setOtherimflexppen(int otherimflexppen) {
	this.otherimflexppen = otherimflexppen;
}



public int getOtherimflexpaprvd() {
	return otherimflexpaprvd;
}



public void setOtherimflexpaprvd(int otherimflexpaprvd) {
	this.otherimflexpaprvd = otherimflexpaprvd;
}



public int getOtherimflexpaprvdwithin7day() {
	return otherimflexpaprvdwithin7day;
}



public void setOtherimflexpaprvdwithin7day(int otherimflexpaprvdwithin7day) {
	this.otherimflexpaprvdwithin7day = otherimflexpaprvdwithin7day;
}



public int getOtherimflexprej() {
	return otherimflexprej;
}



public void setOtherimflexprej(int otherimflexprej) {
	this.otherimflexprej = otherimflexprej;
}




	
}