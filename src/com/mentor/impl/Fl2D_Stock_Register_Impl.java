package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


import com.mentor.action.Fl2D_Stock_Register_Action;

import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class Fl2D_Stock_Register_Impl {
	
	public String getDetails(Fl2D_Stock_Register_Action ac)
	{
		int id = 0;
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		String s = "";
		try {
			con = ConnectionToDataBase.getConnection();

			String queryList = " select  int_app_id, vch_licence_no,vch_firm_name, "+
							" (select description from public.district where districtid=core_district_id) as district_name "+
							" from licence.fl2_2b_2d_20_21 where  vch_license_type='FL2D' and" +
							" vch_mobile_no = '"+ResourceUtil.getUserNameAllReq().trim()+"'  ";
			
			System.out.println("license details====="+queryList);

			pstmt = con.prepareStatement(queryList);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ac.setName(rs.getString("vch_firm_name"));
				ac.setFl2d_id(rs.getInt("int_app_id"));
				ac.setDistrict_Name(rs.getString("district_name"));
				//ac.setVch_licence_no(rs.getString("vch_licence_no"));
				//ac.setVch_from(rs.getString("vch_firm_name"));

			}

			} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (ps2 != null)
					ps2.close();
				if (rs != null)
					rs.close();
				if (rs2 != null)
					rs2.close();
				if (con != null)
					con.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return "";

	}

	/*public void printReport(Fl2D_Stock_Register_Action act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		int openng=0;
		
		int stockBox=0;
		int dispatchBox=0;
		
		try {
			con = ConnectionToDataBase.getConnection();
			
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(act.getFromDate());
		    calendar.add(Calendar.DATE, -1);
		    Date yesterday = calendar.getTime();
		    System.out.println("yesterday====="+yesterday);//
		    
		    calendar.setTime(act.getToDate());
		    calendar.add(Calendar.DATE, -1);
		    Date oneDayeBefore = calendar.getTime();
		    
		    System.out.println("oneDayeBefore===="+oneDayeBefore);
			
			
		    String sDate1="01/04/2020";  
		    Date dateFix=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);  
			
			
			
			
			String opening = " select b.int_brand_id,b.int_pckg_id,a.licencee_district, " +
					" (sum(c.int_boxes)-sum(b.dispatch_box) ) as opening "+

				" FROM fl2d.gatepass_to_districtwholesale_fl2d_20_21 a, " +
				" fl2d.fl2d_stock_trxn_20_21 b,fl2d.mst_stock_receive_20_21 c "+
										
				" 	where c.finalized_flag='F'  and a.int_fl2d_id=b.int_fl2d_id "+
				"     and b.int_fl2d_id=c.int_fl2d_id and a.vch_finalize='F'"+
				
				" 	 and a.dt_date=b.dt "+
				
				" 	and b.int_brand_id = c.int_brand_id and b.int_pckg_id = c.int_pack_id  "+
				"   and a.licencee_district=c.district_id and a.int_fl2d_id="+act.getFl2d_id()+" " +
				" and a.dt_date between '"+Utility.convertUtilDateToSQLDate(dateFix)+"' " +
						" and  '"+Utility.convertUtilDateToSQLDate(yesterday)+"'" +
								" group by b.int_brand_id,b.int_pckg_id,a.licencee_district ";
					
					
			System.out.println("opening for fl2d==="+opening);
				
			
			pst = con.prepareStatement(opening);
			rs = pst.executeQuery();
			
		while(rs.next())
			{
			openng=rs.getInt("opening");
			int brandId=rs.getInt("int_brand_id");
			int packgId=rs.getInt("int_pckg_id");
			int districtId=rs.getInt("licencee_district");
			
			System.out.println("openng="+openng+"brandId=="+brandId+"packgId=="+packgId+"districtId=="+districtId);
			
	
				
				
			reportQuery= " select  a.dt_date,   "+
						" (select description from public.district where districtid=a.licencee_district) as district_name,"+
						" (SELECT vch_firm_name FROM licence.fl2_2b_2d_20_21 WHERE int_app_id=a.int_fl2d_id) as fl2d_name,"+
						" (SELECT  brand_name FROM distillery.brand_registration_20_21 where brand_id=b.int_brand_id) as brand_name,"+
            
					" c.int_boxes as stock_box,b.dispatch_box,c.permit_no "+
  
					" FROM fl2d.gatepass_to_districtwholesale_fl2d_20_21 a, " +
					" fl2d.fl2d_stock_trxn_20_21 b,fl2d.mst_stock_receive_20_21 c "+
											
					" 	where c.finalized_flag='F'  and a.int_fl2d_id=b.int_fl2d_id "+
					"     and b.int_fl2d_id=c.int_fl2d_id and a.vch_finalize='F'"+
					
					" 	 and a.dt_date=b.dt and a.int_fl2d_id="+act.getFl2d_id()+" "+
					"   and b.int_brand_id ="+brandId+" and b.int_pckg_id ="+packgId+"  and a.licencee_district="+districtId+" "+
					" 	and b.int_brand_id = c.int_brand_id and b.int_pckg_id = c.int_pack_id  "+
					"   and a.licencee_district=c.district_id " +
					" and a.dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' " +
							" and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' "
                    
					;
                    
			System.out.println("reportQuery for fl2d==="+reportQuery);
			
				pst = con.prepareStatement(reportQuery);
				 
				rs = pst.executeQuery();

			while(rs.next()) 
			{
				System.out.println("hiiii");
				
				 stockBox=rs.getInt("stock_box");
				 dispatchBox=rs.getInt("dispatch_box");
				 
			
		
			
				rs = pst.executeQuery();
				
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("image", relativePath + File.separator);
			
				
				parameters.put("opening", openng);//opening
		
				//parameters.put("new_opening",(openng+stockBox)-dispatchBox);
				
			
				
				parameters.put("oneDayeBefore", yesterday);
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + 
						File.separator+ "Fl2d_Stock_Register.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf +
						File.separator+ "Fl2d_Stock_Register" + "-" + n + ".pdf");
				
				act.setPdfName("Fl2d_Stock_Register" + "-" + n + ".pdf");
				
				act.setPrintFlag(true);
			
			
				
			} 
			
			
		}
			else 
			{
				FacesContext.getCurrentInstance().addMessage
				(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found!!", "No Data Found!!"));
				act.setPrintFlag(false);
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	
	}*/
	public void printReport(Fl2D_Stock_Register_Action act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		int openng=0;
		
		int stockBox=0;
		int dispatchBox=0;
		
		try {
			con = ConnectionToDataBase.getConnection();
			
			
			
			
			
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(act.getFromDate());
		    calendar.add(Calendar.DATE, -1);
		    Date yesterday = calendar.getTime();
		  //  System.out.println("yesterday===if not equal fix date=="+yesterday);//
		    
		    calendar.setTime(act.getToDate());
		    calendar.add(Calendar.DATE, -1);
		    Date oneDayeBefore = calendar.getTime();
		    
		   // System.out.println("oneDayeBefore===="+oneDayeBefore);
			
			
		    String sDate1="01/04/2020";  
		    Date dateFix=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);  
			
			
		    if(act.getFromDate().equals(dateFix))
			{
		    	   yesterday=dateFix;
		    	   System.out.println("yesterday if equal fix date====="+yesterday);//
			}
		    
		    
		    String opening =  
		    	
		    " select xz.int_fl2d_id,xz.dt_date, xz.int_brand_id, "+
		    " xz.int_pack_id, xz.description,xz.stock_box,xz.dispatch_box,xz.opening  ,"+

		    " (SELECT  brand_name FROM distillery.brand_registration_20_21 where brand_id=xz.int_brand_id) as brand_name, "+
		    " (SELECT package_name FROM distillery.packaging_details_20_21 where package_id=xz.int_pack_id) as package_id "+
		    " from   "+

		    " (  SELECT int_fl2d_id,cr_date  as dt_date, int_brand_id, int_pack_id,       "+
		     "             permit_no as description,int_boxes as stock_box,0 as dispatch_box, 0 as opening      "+
		     "             FROM fl2d.mst_stock_receive_20_21 where cr_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' " +
		     		"    and  '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'  "+
		     " 					and int_fl2d_id="+act.getFl2d_id()+" "+
		     
		     "        union  		"+
		     		
		     " 			SELECT int_fl2d_id, dt  as dt_date, int_brand_id, int_pckg_id,        "+
		      "            vch_gatepass_no as description,0 as stock_box,dispatch_box,0 as opening  	"+
		       "           FROM fl2d.fl2d_stock_trxn_20_21  where dt between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'  and  " +
		       "  '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'  "+
		     " 				and int_fl2d_id="+act.getFl2d_id()+" 	"+
		              
		     " union  		select     x.int_fl2d_id as int_fl2d_id,to_date('"+Utility.convertUtilDateToSQLDate(yesterday)+"', 'yyyy/mm/dd') as dt_date,  "+
		      "            x.int_brand_id ,x.int_pack_id,    ' ' as description,    0 as stock_box,0 as dispatch_box,   "+
		      "            (coalesce(x.stock_box,0)-coalesce(y.dispatch_box,0)) as opening     from(      "+
		      "                select sum(int_boxes) as stock_box ,int_fl2d_id,int_brand_id, int_pack_id      "+
		      "                from fl2d.mst_stock_receive_20_21 where cr_date between '"+Utility.convertUtilDateToSQLDate(dateFix)+"' " +
		      		"  and   '"+Utility.convertUtilDateToSQLDate(yesterday)+"'  "+
		      "                and int_fl2d_id="+act.getFl2d_id()+" "+
		      "                group by int_fl2d_id,int_brand_id, int_pack_id )x     	left outer join      	(       "+
		      "                    select sum(dispatch_box) as dispatch_box,int_fl2d_id, "+
		      "                    int_brand_id,int_pckg_id from fl2d.fl2d_stock_trxn_20_21          "+
		       "                   where dt between '"+Utility.convertUtilDateToSQLDate(dateFix)+"' " +
		       		"   and   '"+Utility.convertUtilDateToSQLDate(yesterday)+"'      and int_fl2d_id="+act.getFl2d_id()+"     "+
		       "                   group by int_fl2d_id,int_brand_id, int_pckg_id )y           "+  
		      "            on x.int_fl2d_id=y.int_fl2d_id and x.int_brand_id=y.int_brand_id and x.int_pack_id=y.int_pckg_id  )xz   "+
		          
		        "          group by xz.int_brand_id,xz.int_pack_id,xz.dt_date,xz.int_fl2d_id,	  	"+
		        "          xz.stock_box,xz.dispatch_box,xz.description, xz.opening  "+
		    "  order by xz.int_fl2d_id,xz.int_brand_id,xz.dt_date,xz.int_pack_id,xz.stock_box,xz.dispatch_box,xz.opening ";		                
		    				                
					
					
			//System.out.println("opening for fl2d==="+opening);
				
			
			pst = con.prepareStatement(opening);
			rs = pst.executeQuery();
			
		

			if(rs.next()) 
			{
				 
				
				openng=rs.getInt("opening");
				// dispatchBox=rs.getInt("dispatch_box");
				 
			
				String brnd=rs.getString("brand_name");
				String pack=rs.getString("package_id");
				
				rs = pst.executeQuery();
				
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate", act.getFromDate());//opening
				parameters.put("toDate", act.getToDate());//opening
				parameters.put("opening", openng);//opening
		
				parameters.put("district_name", act.getDistrict_Name());//opening
				//fl2d_name
				parameters.put("fl2d_name", act.getName());//opening
			
			//	parameters.put("brand_pack", brnd+"-"+pack);//opening
				
				parameters.put("oneDayeBefore", yesterday);
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + 
						File.separator+ "Fl2d_Stock_Register.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf +
						File.separator+ "Fl2d_Stock_Register" + "-" + n + ".pdf");
				
				act.setPdfName("Fl2d_Stock_Register" + "-" + n + ".pdf");
				
				act.setPrintFlag(true);
			
			
				
			} 
			else 
			{
				FacesContext.getCurrentInstance().addMessage
				(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found!!", "No Data Found!!"));
				act.setPrintFlag(false);
			}
			
		
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	
	}

}
