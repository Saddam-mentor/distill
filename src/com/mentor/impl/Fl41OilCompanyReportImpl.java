package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.mentor.action.Fl41OilCompanyReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class Fl41OilCompanyReportImpl {
	
	
	
	
	
	
	public void printReport(Fl41OilCompanyReportAction action)
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		JasperPrint  jasperPrint=null;
		
		String filter=null;
		String filterB = null;
		String type=null;
		
		
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "pdf";
		
		
		String sql=
				
			"	select a.approval_date,a.indent_no,b.depo_name as                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             company_name,c.vch_undertaking_name,sum(coalesce(approved_qty,0))as approved_qty," +
			"  sum(coalesce(lifted_qty,0))as lifted_qty,"+
			"	(sum(coalesce(approved_qty,0))-sum(coalesce(lifted_qty,0)))as balance  from "+
			"	fl41.fl41_indent_detail_approved a, "+
			"	fl41.fl41_registration_approval b, public.dis_mst_pd1_pd2_lic c "+
			"	where a.fl41_id=b.int_id and a.dist_id=c.int_app_id_f and a.approval_date between ? and ?  group by a.approval_date,a.indent_no,b.company_name,c.vch_undertaking_name,b.depo_name";
		
		
		try{
			conn=ConnectionToDataBase.getConnection();
			
			 pstmt=  conn.prepareStatement(sql)	 ;
		      
		      pstmt.setDate(1,Utility.convertUtilDateToSQLDate(action.getFromDate()));
		      pstmt.setDate(2,Utility.convertUtilDateToSQLDate(action.getToDate()));
		      rs=pstmt.executeQuery();
		      if(rs.next())
				{
		    	  
		    	  rs=pstmt.executeQuery();
					
					
					
					
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
			          HashMap map=new HashMap();
			          
			          
			          map.put("image", relativePath + File.separator);
			          map.put("bgimage", relativePath + File.separator);
			         map.put("fromDate", Utility.convertUtilDateToSQLDate(action.getFromDate()));
			        map.put("toDate", Utility.convertUtilDateToSQLDate(action.getToDate()));
			        map.put("type", type);
			        
			        Random rand = new Random();
			      	  int  n = rand.nextInt(250) + 1;
				          action.setPdfName("Fl41PermitForOilCompanies"+n+".pdf");
				 jasperPrint=JasperFillManager.fillReport(relativePath+File.separator+"Fl41PermitForOilCompanies.jasper", map, jrRs);
				 
				 JasperExportManager.exportReportToPdfFile(jasperPrint, relativePathpdf+File.separator+action.getPdfName());
					
						action.setPrintFlag(true);
				}else{
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"No Data Found!!", "No Data Found!!"));
					action.setPrintFlag(false);
				}
				
		
}
			catch(Exception e)
			{
				e.printStackTrace();
			}finally{
				
				
				try{
					if(conn!=null)conn.close();
					if(pstmt!=null)pstmt.close();
					if(rs!=null)rs.close();
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}


