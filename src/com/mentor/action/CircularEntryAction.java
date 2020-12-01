package com.mentor.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;


import com.mentor.datatable.CircularEntryDT;
import com.mentor.impl.CircularEntryImpl;
import com.mentor.utility.Constants;

public class CircularEntryAction {

	
	CircularEntryImpl impl = new CircularEntryImpl();
	   private Date cir_date;
	   private String heading_str;
	   private String discription_str;
	   private String pdf_str;
	   private ArrayList circular_list = new ArrayList();
		private String fileName;
		private String mypathphoto = "";
		
		private String filePathphoto;
		
		public boolean doc1upload = false;
		private boolean img1 = false;
		private int srNo;
		private static BufferedInputStream apidoc1 = null;

		private boolean modifyFlag=false;
		private boolean flag=true;
		private boolean flag1=true;
	   
			   
		public boolean isFlag1() {
			return flag1;
		}
		public void setFlag1(boolean flag1) {
			this.flag1 = flag1;
		}
		public boolean isFlag() {
			return flag;
		}
		public void setFlag(boolean flag) {
			this.flag = flag;
		}
		public boolean isModifyFlag() {
			return modifyFlag;
		}
		public void setModifyFlag(boolean modifyFlag) {
			this.modifyFlag = modifyFlag;
		}

		public boolean isImg1() {
			return img1;
		}
		public void setImg1(boolean img1) {
			this.img1 = img1;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getMypathphoto() {
			return mypathphoto;
		}
		public void setMypathphoto(String mypathphoto) {
			this.mypathphoto = mypathphoto;
		}
		public String getFilePathphoto() {
			return filePathphoto;
		}
		public void setFilePathphoto(String filePathphoto) {
			this.filePathphoto = filePathphoto;
		}
		public boolean isDoc1upload() {
			return doc1upload;
		}
		public void setDoc1upload(boolean doc1upload) {
			this.doc1upload = doc1upload;
		}
		
		
		public ArrayList getCircular_list() {
			
			try{
			if(this.flag1=true){
			this.circular_list= impl.getCircularDetail();
			this.flag1=false;
			} 
			}catch (Exception ex) {
				ex.printStackTrace();
			}
			
			return circular_list;
	        }
			public void setCircular_list(ArrayList circular_list) {
				this.circular_list = circular_list;
			}
		public Date getCir_date() {
			return cir_date;
		}
		public void setCir_date(Date cir_date) {
			this.cir_date = cir_date;
		}
		public String getHeading_str() {
			return heading_str;
		}
		public void setHeading_str(String heading_str) {
			this.heading_str = heading_str;
		}
		public String getDiscription_str() {
			return discription_str;
		}
		public void setDiscription_str(String discription_str) {
			this.discription_str = discription_str;
		}
		public String getPdf_str() {
			return pdf_str;
		}
		public void setPdf_str(String pdf_str) {
			this.pdf_str = pdf_str;
		}
		
		public void doc1uploadMethod(UploadEvent event) throws Exception {

			this.fileName = "";
			InputStream inFile = null;
			UploadItem item = event.getUploadItem();
			String FullfileName = item.getFileName();
			String FullfileExt = null;
			int srNo=0;
			if(this.modifyFlag=true){
				srNo=this.getSrNo();
			}else{
				srNo=impl.maxid(this);
			}

			String arr[] = FullfileName.split(".pdf");
			this.fileName = arr[0];

			// System.out.print("FullfileName ---- " + arr[0]);

			if (FullfileName != null && FullfileName.length() > 4) {
				FullfileExt = FullfileName.substring(FullfileName.lastIndexOf("."));
			}

			String recordFile ="Circular" +"_" +srNo+ ".pdf";

			// System.out.print("recordFile --------------- " + recordFile);
			String path = item.getFile().getAbsolutePath();
			filePathphoto = item.getFile().getPath();
			if (filePathphoto != null && (FullfileExt.equalsIgnoreCase(".pdf"))) {

				inFile = new FileInputStream(path);
				boolean success = false;

				try {
					String mypath = Constants.JBOSS_SERVER_PATH
							+ Constants.JBOSS_LINX_PATH + File.separator
							+ "ExciseUp" + File.separator + "pdf" ;
					mypathphoto = mypath + File.separator + recordFile;

					if (!(new File(mypath).exists())) {
						File file = new File(mypath);
						success = file.mkdirs();
					}
					inFile = new FileInputStream(path);
					apidoc1 = new BufferedInputStream(inFile);
					if (apidoc1.available() > 0) {

						FileOutputStream out = new FileOutputStream(mypath
								+ File.separator + recordFile);
						BufferedOutputStream outb = new BufferedOutputStream(out);
						int z = 0;
						while ((z = apidoc1.read()) != -1) {
							outb.write(z);
							doc1upload = true;
						}
						outb.flush();
						outb.close();

					} else {
						img1 = true;
						doc1upload = false;
					}
					// System.out.print("doc1 uploaded success fully");
					// this.setCount(this.getCount()+1);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				// System.out.print("1 NO FILE READ STARTED.");
				doc1upload = false;
			}
	    
		}
		
		
		public int getSrNo() {
			return srNo;
		}
		public void setSrNo(int srNo) {
			this.srNo = srNo;
		}
		public void save(){
			///if(this.modifyFlag=false){
			
			if(this.srNo==0){
				this.srNo=impl.maxid(this);
			}
			    if(this.cir_date!=null){
			    	if(this.category_id!=null){
			    	if(this.getHeading_str()!=null && this.getHeading_str().length()>0 && this.getHeading_str().length()<201){
			    		if(this.discription_str!=null && this.getDiscription_str().length()>0){
			    	        if(this.doc1upload==true){
			    	        impl.saveImpl(this);
			    	        this.flag=true;
			    			this.reset();
			    			this.circular_list= impl.getCircularDetail();
			    	        }else{
			    	        	FacesContext.getCurrentInstance().addMessage(null,
			    						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Upload Document !", 
			    											"Upload Document !"));
			    	        }
			    		}else{
			    			FacesContext.getCurrentInstance().addMessage(null,
			    					new FacesMessage(FacesMessage.SEVERITY_ERROR," Fill The Discription !" , 
											"Fill The Discription !"));
			    		}
			    	}else{
			    		FacesContext.getCurrentInstance().addMessage(null,
			    				new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Heading Length!", 
										"Invalid Heading Length !"));
			    	}
			    }
			    	else{
				    	FacesContext.getCurrentInstance().addMessage(null,
				    	     new FacesMessage(FacesMessage.SEVERITY_ERROR,"Select Category !", 
								   "Select Category !"));
				    }
			    }else{
			    	FacesContext.getCurrentInstance().addMessage(null,
			    	     new FacesMessage(FacesMessage.SEVERITY_ERROR,"Select Date !", 
							   "Select Date !"));
			    }
			    this.doc1upload=false;
			    this.srNo=impl.maxid(this);
			    
			/*}else{
				impl.update(this);
				
			}*/
			
		}
		
		public void reset(){
			this.cir_date=null;
			this.heading_str=null;
			this.discription_str=null;	
			this.doc1upload=false;
			this.category_id=null;
			
		}
		
		public void modify(ActionEvent e){
			
			UIDataTable uiTable = (UIDataTable) e.getComponent().getParent().getParent();
			CircularEntryDT dt = (CircularEntryDT) this.getCircular_list().get(uiTable.getRowIndex());
			
			
			this.setModifyFlag(true);
			this.setSrNo(dt.getSrNo_int());
			this.setCir_date(dt.getDate());
			this.setHeading_str(dt.getHeading_str_dt());
			this.setDiscription_str(dt.getDiscription_str_dt());
			this.setCategory_id(dt.getCategory_id());
			this.doc1upload=false;
			
			
		}
		
		public void delete(ActionEvent e){
			
			UIDataTable uiTable = (UIDataTable) e.getComponent().getParent().getParent();
			CircularEntryDT dt = (CircularEntryDT) this.getCircular_list().get(uiTable.getRowIndex());
		    impl.deleteMethod(dt.getSrNo_int());	
		}
		
		public void lengthcheck(ActionEvent e){
			System.out.println("length=="+this.getHeading_str().length());
			if(this.getHeading_str().length()>200){
				FacesContext.getCurrentInstance().addMessage(null,
			    	     new FacesMessage(FacesMessage.SEVERITY_ERROR,"Heading Length Shoul NOt Be Greater Than 200 Character!", 
							   "Heading Length Shoul NOt Be Greater Than 200 Character !"));
			}
		}
	
	
		private String category_id;
		private ArrayList categoryList = new ArrayList();
		

		public ArrayList getCategoryList() {
			try{
				this.categoryList=impl.getcategorylist();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return categoryList;
		}
		public void setCategoryList(ArrayList categoryList) {
			this.categoryList = categoryList;
		}
		public String getCategory_id() {
			return category_id;
		}
		public void setCategory_id(String category_id) {
			this.category_id = category_id;
		}
	
	
	
	
	
}
