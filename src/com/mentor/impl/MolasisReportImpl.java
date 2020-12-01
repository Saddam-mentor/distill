package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.mentor.action.MolasisReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;


public class MolasisReportImpl {
	
	
	
	
	public void printReport(MolasisReportAction action)
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		JasperPrint  jasperPrint=null;
		
		String filter="";
		String filterB = "";
		String type=null;
		
		
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "pdf";
		
		
		
		
		if(action.getRadioh().equalsIgnoreCase("B")){
			
			filter="and a.bheavy_flag='T' and a.bheavy_flag=b.bheavy_flag";
			type="For B-Heavy";
			
			System.out.println("------B filter--------"+filter);
			
		}else{
				
			filter="and (a.bheavy_flag != 'T' or  a.bheavy_flag is null)";
			type="For C-Heavy";
			System.out.println("------C filter--------"+filter);
			
		}
		
		
			
			if(action.getRadio().equalsIgnoreCase("D"))
			{
				
				String s[]=action.getSelectDistillery().split("\\-");
				if(!action.getSelectDistillery().trim().equalsIgnoreCase("NA"))
				{
				filterB=" and a.int_dist_id="+s[0];
				}
				
			}else if(action.getRadio().equalsIgnoreCase("I"))
			{
				String s[]=action.getSelectIndustry().split("\\-");
				if(!action.getSelectIndustry().trim().equalsIgnoreCase("NA"))
				{
				filterB=" and a.int_ind_id="+s[0];
				}
			}else if(action.getRadio().equalsIgnoreCase("DOUP"))
			{
				String s[]=action.getSelectDOUP().split("\\-");
				if(!action.getSelectDOUP().trim().equalsIgnoreCase("NA"))
				{
				filterB=" and int_dist_id="+s[0]+" and vch_undertaking_name='"+s[1]+"'" ;
				}
			}else if(action.getRadio().equalsIgnoreCase("IOUP"))
			{
				String s[]=action.getSelectIOUP().split("\\-");
				if(!action.getSelectIOUP().trim().equalsIgnoreCase("NA"))
				{
				filterB=" and int_ind_id="+s[0]+" and vch_indus_name='"+s[1]+"'" ;
				}
			}
			System.out.println("filterB="+filterB);
			
			/*else if(action.getRadio().equalsIgnoreCase("DOUP"))
			{
				String s[]=action.getSelectDOUP().split("\\-");
				if(!action.getSelectDOUP().trim().equalsIgnoreCase("NA"))
				{
				filterB=" where unitid="+s[0]+" and dis_ind_name='"+s[1]+"'" ;
				}
			}*/
		
		
		
		String inside_up_dis=
			" select c.sugarmill_nm as sugarmill_name,d.vch_undertaking_name as dis_ind_name,b.vch_permit_no as permit_no,a.int_dist_id as unitid,  "+
			" b.permit_dt as permit_dt,sum(b.db_capacity_applied) as approve_qty,                                           "+
			" sum(b.pending_lifting_qty) as lifting,                                                                        "+
			" (sum(b.db_capacity_applied)-sum(b.pending_lifting_qty))as balance                                             "+
			" from distillery.dist_permit_mst a,                                                                            "+
			" distillery.dist_permit_detail b,public.sugarmill_mst_sm1_lic c ,public.dis_mst_pd1_pd2_lic d                  "+
			"  where b.int_sgmill_id=c.vch_app_id_f and a.int_app_id=b.int_app_id " +
			" and  a.int_dist_id=d.int_app_id_f  "+filter+"  and b.dist=a.int_dist_id" +
			"  and  b.permit_dt  between ? and ?"+ filterB    + " and session_id="+action.getSeason()+
         " group by c.sugarmill_nm,d.vch_undertaking_name,b.vch_permit_no,b.permit_dt ,a.int_dist_id                                  "+
			" order by b.permit_dt ";
		String inside_up_ind=
			"	select b.sugarmill_nm as sugarmill_name,c.vch_indus_name as dis_ind_name,a.vch_permit_no as permit_no ,a.int_ind_id as unitid ,    "+
			"	a.permit_dt as permit_dt,sum(a.db_capacity_applied) as approve_qty,                                        "+
			"	sum(a.pending_lifting_qty) as lifting,                                                                     "+
			"	(sum(a.db_capacity_applied)-sum(a.pending_lifting_qty))as balance                                          "+
			"	from                                                                                                       "+
			"	industry.ind_dist_permit_detail a,                                                                         "+
			"	public.sugarmill_mst_sm1_lic b ,                                                                           "+
			"	public.mst_industry_register c where a.int_sgmill_id=b.vch_app_id_f " +
			"   and a.int_ind_id=c.int_app_id_f  and a.permit_dt between ? and ?     "+filterB    + " and session="+action.getSeason() + 
			"	group by b.sugarmill_nm,c.vch_indus_name,a.vch_permit_no,a.permit_dt,a.int_ind_id order by a.permit_dt                  ";
	String export="	select sugarmill_name,dis_ind_name,permit_no,permit_dt,approve_qty,lifting,balance,unitid from                        "+
				"	(select c.sugarmill_nm as sugarmill_name,d.vch_undertaking_name as dis_ind_name,b.vch_permit_no as permit_no,  "+
				"	b.permit_dt as permit_dt,sum(b.db_capacity_applied) as approve_qty,                                            "+
				"	sum(b.pending_lifting_qty) as lifting,                                                                         "+
				"	(sum(b.db_capacity_applied)-sum(b.pending_lifting_qty))as balance  ,b.int_dist_id as unitid                                            "+
				"	from                                                                                                           "+
				"	distillery.dist_permit_detail_oup b,public.sugarmill_mst_sm1_lic c ,public.dis_mst_pd1_pd2_lic d               "+
				"	 where b.int_sgmill_id=c.vch_app_id_f  and  b.int_dist_id=d.int_app_id_f and b.permit_dt between ? and ?       "+
				"	group by b.permit_dt,b.vch_permit_no,c.sugarmill_nm,d.vch_undertaking_name ,b.int_dist_id                                    "+
				"	 union all                                                                                                     "+
				"	 select b.sugarmill_nm as sugarmill_name,c.vch_indus_name as dis_ind_name,a.vch_permit_no as permit_no ,       "+
				"	a.permit_dt as permit_dt,sum(a.db_capacity_applied) as approve_qty,                                            "+
				"	sum(a.pending_lifting_qty) as lifting,                                                                         "+
				"	(sum(a.db_capacity_applied)-sum(a.pending_lifting_qty))as balance ,a.int_ind_id as   unitid                                           "+
				"	from                                                                                                           "+
				"	industry.ind_dist_permit_detail_oup a,                                                                         "+
				"	public.sugarmill_mst_sm1_lic b ,                                                                               "+
				"	public.mst_industry_register c where a.int_sgmill_id=b.vch_app_id_f  and a.int_ind_id=c.int_app_id_f            "+
				"	 and a.permit_dt between ? and ?  "+                                                                             
				"	group by a.permit_dt,a.vch_permit_no,b.sugarmill_nm,c.vch_indus_name,a.int_ind_id )x "+filterB    +" order by permit_dt                     ";
		                                                                                                           
		
		String IOUP="select b.sugarmill_nm as sugarmill_name,c.vch_indus_name as dis_ind_name,a.vch_permit_no as permit_no ,          " +
				"a.permit_dt as permit_dt,sum(a.db_capacity_applied) as approve_qty,                                              " +
				"sum(a.pending_lifting_qty) as lifting,                                                                          " +
				"(sum(a.db_capacity_applied)-sum(a.pending_lifting_qty))as balance ,a.int_ind_id as   unitid                            " +
				"from                                                                                                              " +
				"industry.ind_dist_permit_detail_oup a,                                                                          " +
				"public.sugarmill_mst_sm1_lic b ,                                                                               " +
				"public.mst_industry_register c where a.int_sgmill_id=b.vch_app_id_f  and a.int_ind_id=c.int_app_id_f          " +
				" and a.permit_dt between ? and ?  "+filterB+"  and session="+action.getSeason()+ "                                                                             " +
				" group by a.permit_dt,a.vch_permit_no,b.sugarmill_nm,c.vch_indus_name,a.int_ind_id order by permit_dt ";
		 String DOUP="select   c.sugarmill_nm as sugarmill_name,d.vch_undertaking_name as dis_ind_name,b.vch_permit_no as permit_no,     " +
		 		"b.permit_dt as permit_dt,sum(b.db_capacity_applied) as approve_qty,                                                 " +
		 		"sum(b.pending_lifting_qty) as lifting,                                                                " +
		 		"(sum(b.db_capacity_applied)-sum(b.pending_lifting_qty))as balance  ,b.int_dist_id as unitid                            " +
		 		"from                                                                                                                " +
		 		"distillery.dist_permit_detail_oup b,public.sugarmill_mst_sm1_lic c ,public.dis_mst_pd1_pd2_lic d                 " +
		 		" where b.int_sgmill_id=c.vch_app_id_f  and  b.int_dist_id=d.int_app_id_f and b.permit_dt between ? and ?   "+filterB+" and int_session="+action.getSeason()+ 
		 		" group by b.permit_dt,b.vch_permit_no,c.sugarmill_nm,d.vch_undertaking_name ,b.int_dist_id  order by permit_dt ";                                                                                                                  
		
		
		try{
	
			
			
			conn=ConnectionToDataBase.getConnection();
			
			
			
			if(action.getRadio().equalsIgnoreCase("D"))
	        {
	      pstmt=  conn.prepareStatement(inside_up_dis)	 ;
	      System.out.println("-----DIs-----"+inside_up_dis);
	    //  System.out.println("queryyy"+inside_up_dis);
	      
	      pstmt.setDate(1,Utility.convertUtilDateToSQLDate(action.getFromDate()));
	      pstmt.setDate(2,Utility.convertUtilDateToSQLDate(action.getToDate()));
	      
	    //  System.out.println("-----DIs-----"+inside_up_dis);
	      
	        }else if(action.getRadio().equalsIgnoreCase("I"))
	        {
	        	 pstmt=  conn.prepareStatement(inside_up_ind)	 ;
	        	  System.out.println("-----ind-----"+inside_up_ind);
	        	 System.out.println("queryyy"+inside_up_ind);
	        	  pstmt.setDate(1,Utility.convertUtilDateToSQLDate(action.getFromDate()));
	    	      pstmt.setDate(2,Utility.convertUtilDateToSQLDate(action.getToDate()));
	    	      
	    	      System.out.println("-----ind-----"+inside_up_ind);
	    	      
	        }else if(action.getRadio().equalsIgnoreCase("DOUP")){
	        	System.out.println("-----DOUP------"+DOUP);
	        	 pstmt=  conn.prepareStatement(DOUP)	 ;
	        	 System.out.println("DOUP"+DOUP);
	        	 pstmt.setDate(1,Utility.convertUtilDateToSQLDate(action.getFromDate()));
	    	      pstmt.setDate(2,Utility.convertUtilDateToSQLDate(action.getToDate()));
	    	     // pstmt.setDate(3,Utility.convertUtilDateToSQLDate(action.getFromDate()));
	    	     // pstmt.setDate(4,Utility.convertUtilDateToSQLDate(action.getToDate()));
	    	      
	    	      System.out.println("-----DOUP------"+DOUP);
	        }else if(action.getRadio().equalsIgnoreCase("IOUP")){
	        	System.out.println("-----IOUP------"+IOUP);
	        	 pstmt=  conn.prepareStatement(IOUP)	 ;
	        	 System.out.println("IOUP"+IOUP);
	        	 pstmt.setDate(1,Utility.convertUtilDateToSQLDate(action.getFromDate()));
	    	      pstmt.setDate(2,Utility.convertUtilDateToSQLDate(action.getToDate()));
	    	     // pstmt.setDate(3,Utility.convertUtilDateToSQLDate(action.getFromDate()));
	    	    //  pstmt.setDate(4,Utility.convertUtilDateToSQLDate(action.getToDate()));
	    	      
	    	      System.out.println("-----IOUP------"+IOUP);
	        }
			
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
	       // map.put("toDate", Utility.convertUtilDateToSQLDate(action.getToDate()));
	        
	        if(action.getRadio().equalsIgnoreCase("D"))
{
	map.put("inside_outside_export", "Inside U.P Distillery");	
}else if(action.getRadio().equalsIgnoreCase("I"))
{
	map.put("inside_outside_export", "Inside U.P Industry");	
}else if(action.getRadio().equalsIgnoreCase("E"))
{
	map.put("inside_outside_export", "Export Industry And Distillery");		
}else if(action.getRadio().equalsIgnoreCase("DOUP"))
{
	map.put("inside_outside_export", "Distillery Outside State");		
}else if(action.getRadio().equalsIgnoreCase("IOUP"))
{
	 map.put("inside_outside_export", "Industry Outside State");		
}
	        Random rand = new Random();
      	  int  n = rand.nextInt(250) + 1;
	          action.setPdfName("MolasisReport"+n+".pdf");
	 jasperPrint=JasperFillManager.fillReport(relativePath+File.separator+"MolasisReport.jasper", map, jrRs);
	 
	 JasperExportManager.exportReportToPdfFile(jasperPrint, relativePathpdf+File.separator+action.getPdfName());
		
			action.setPrintFlag(true);
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Print Successfully!!", "Print Successfully!!"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No Data Found!!", "No Data Found!!"));
				action.setPrintFlag(false);
			}
			
			}catch(Exception e)
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

	
	
	public ArrayList getSelectList(MolasisReportAction action)
	{
		
		
		String insideupdistillery=
				
				
				"select distinct d.vch_undertaking_name as unitname,d.int_app_id_f   as appid "+
				"from distillery.dist_permit_detail a,public.dis_mst_pd1_pd2_lic d where a.dist=d.int_app_id_f order by unitname";
		
		
		String insideupindustry=
				
				
				"select distinct c.vch_indus_name as unitname,c.int_app_id_f  as appid " +
				"from industry.ind_dist_permit_detail a,public.mst_industry_register c "+ 
				"where a.int_ind_id=c.int_app_id_f order by unitname";
		
		
		String export=
				
			"	select unitname,appid                                                                 "+
			"	from (select distinct d.vch_undertaking_name as unitname,d.int_app_id_f  as appid     "+
			"	from distillery.dist_permit_detail_oup a,public.dis_mst_pd1_pd2_lic d                 "+
			"	where a.int_dist_id=d.int_app_id_f                                                    "+
			"	union all                                                                             "+
			"	select distinct c.vch_indus_name as unitname,                                         "+
			"	 c.int_app_id_f as appid from industry.ind_dist_permit_detail_oup a,                  "+
			"	public.mst_industry_register c                                                        "+
			"	where a.int_ind_id=c.int_app_id_f )z order by unitname                                ";
		
		String DOUP="select distinct d.vch_undertaking_name as unitname,d.int_app_id_f  as appid " +
				" from distillery.dist_permit_detail_oup a,public.dis_mst_pd1_pd2_lic d " +
				"where a.int_dist_id=d.int_app_id_f order by unitname";
		String IOUP="select distinct c.vch_indus_name as unitname,c.int_app_id_f as appid " +
				" from industry.ind_dist_permit_detail_oup a,public.mst_industry_register c " +
				"where a.int_ind_id=c.int_app_id_f order by unitname";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		ArrayList list=new ArrayList();
		
		SelectItem item=new SelectItem();
		
		item.setLabel("Select");
		item.setValue("NA");
		
		list.add(item);
		
		try{
			conn=ConnectionToDataBase.getConnection();
			if(action.getRadio().equalsIgnoreCase("D"))
			{
				pstmt=conn.prepareStatement(insideupdistillery);
			}
			else if(action.getRadio().equalsIgnoreCase("I"))
			{
				pstmt=conn.prepareStatement(insideupindustry);
			}
				
			else if(action.getRadio().equalsIgnoreCase("DOUP"))
			{
				System.out.println("DOUP"+DOUP);
				pstmt=conn.prepareStatement(DOUP);	
			}else if(action.getRadio().equalsIgnoreCase("IOUP"))
			{
				System.out.println("IOUP"+IOUP);
				pstmt=conn.prepareStatement(IOUP);	
			}
			
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				item=new SelectItem();
				item.setLabel(rs.getString("unitname"));
				item.setValue(rs.getString("appid")+"-"+rs.getString("unitname"));
				list.add(item);
				
				//System.out.print("fghgfhfghgfhfg");
			}	
		}catch(Exception e)
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
		
		return list;
	}
	
	
	
	public static ArrayList getSeason() {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		/*
		 * item.setLabel("--select--"); item.setValue(""); list.add(item);
		 */
		try {
			String query = "SELECT distinct sesn_id,frm_yr,to_yr FROM mst_season order by frm_yr desc";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("sesn_id"));
				item.setLabel(rs.getString("frm_yr") + "-"
						+ rs.getString("to_yr"));

				list.add(item);

			}

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return list;
	}
	
	
	
	
	
	
	
}
