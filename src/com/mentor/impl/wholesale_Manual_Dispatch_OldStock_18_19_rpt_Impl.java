package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.mentor.action.Wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class wholesale_Manual_Dispatch_OldStock_18_19_rpt_Impl { 
	
	
	
	
	public int getUserId(Wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action ac){

		int id = 0;
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		String s = "";
		try {
			con = ConnectionToDataBase.getConnection();

			String selQr = " SELECT int_app_id "
					+ " FROM licence.fl2_2b_2d_19_20 "
					+ " WHERE loginid='"
					+ ResourceUtil.getUserNameAllReq().trim() + "' ";
            // System.out.println("id query=="+selQr);   
			pstmt = con.prepareStatement(selQr);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				id=rs.getInt("int_app_id");
			}

		} catch (SQLException se) {
			//se.printStackTrace();
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
				//se.printStackTrace();
			}
		}
		return id;

	}
	
   public void printReportDistrict(Wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action ac){

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = "";
		String filter="";
		
        String brand_catagory="";
		
		if(ac.getLiqourType().equalsIgnoreCase("FL2B"))
			brand_catagory="Beer";
		else if(ac.getLiqourType().equalsIgnoreCase("FL2"))
			brand_catagory="FL";   
		if(ac.getShop_id()>0){
			filter=" AND a.shop_id='"+ac.getShop_id()+"' ";
		}

		try {
			con = ConnectionToDataBase.getConnection();

			if(ac.getLiqourType().equalsIgnoreCase("FL2D")){
				  
				  reportQuery= "	 select core_district_id,case when license_category in ('BEER','IMPORTED BEER','LAB') then 'BEER' else 'FL' end as license_category, " +
				 		"  vch_to,int_fl2_fl2b_id,vch_gatepass_no,dt_date,vch_to_lic_no,sum(dispatch_bottle) as bottal,sum(duty) as duty," +
				 		"   sum(bl) as bl,l.vch_licence_no, "+
						" '"+ac.getName()+"' as dist  from (select br.license_category, CASE WHEN a.vch_to='RT' THEN 'Retail Shop' WHEN  "+
						"  a.vch_to='BRC' THEN 'Hotel Bar Club' else a.vch_to end as vch_to,a.int_fl2d_id as int_fl2_fl2b_id,a.vch_gatepass_no,a.dt_date, "+
						"  a.vch_to_lic_no ,int_brand_id,int_pckg_id,size,dispatch_bottle,sum(b.duty+b.add_duty) as duty,                                 "+
						"  (dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,                         "+
						"  fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b,                                                                                    "+
						"  distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id                   "+
						"  and br.brand_id=p.brand_id_fk and                                                                                              "+
						"  b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no  and dt_date               "+
						"  between '"+Utility.convertUtilDateToSQLDate(ac.getFrom_dt())+"' and '"+Utility.convertUtilDateToSQLDate(ac.getTo_dt())+"'  AND a.vch_from='FL2D'                                                                   "+
						"  "+filter+" group by a.vch_to,a.int_fl2d_id,a.vch_gatepass_no,a.dt_date,a.vch_to_lic_no,a.vch_to_lic_no ,                                  "+
						"  int_brand_id,int_pckg_id,size,dispatch_bottle,p.quantity,br.license_category)x,licence.fl2_2b_2d_19_20 l where                 "+
						"  x.int_fl2_fl2b_id=l.int_app_id and l.core_district_id="+ac.getDistrict_id()+" group by dist,license_category,vch_to,int_fl2_fl2b_id,vch_gatepass_no,     "+
						"  dt_date,vch_to_lic_no,l.vch_licence_no,core_district_id order by dist,vch_licence_no,core_district_id,vch_to,                                      "+
						"  license_category,int_fl2_fl2b_id,vch_to_lic_no	";
				  }
			else{
			                                                                                                                                                              
			reportQuery =" select z.*,total_duty+total_adduty as duty,'"+brand_catagory+"' as license_category from (select vch_licence_no,vch_to,int_fl2_fl2b_id,vch_to_lic_no,vch_gatepass_no,dt_date,    "+
						"	sum(dispatch_bottle) as bottal,sum(bl) as bl from (select CASE WHEN a.vch_to='RT' THEN 'Retail Shop' WHEN    "+
						"	a.vch_to='BRC' THEN 'Hotel Bar Club' end as vch_to,l.vch_licence_no,a.int_fl2_fl2b_id,a.vch_gatepass_no,a.dt_date,            "+
						"	a.shop_id ,int_brand_id,int_pckg_id,size,dispatch_bottle,a.vch_to_lic_no,                                    "+      
						"	(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a,   "+
						"	fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b,licence.fl2_2b_2d_19_20 l, public.district d,                  "+        
						"	distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id     "+
						"	and br.brand_id=p.brand_id_fk and                                                                                "+
						"	b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no  and dt_date         "+
						"	between '"+Utility.convertUtilDateToSQLDate(ac.getFrom_dt())+"' and '"+Utility.convertUtilDateToSQLDate(ac.getTo_dt())+"'  "+
						"	and l.core_district_id="+ac.getDistrict_id()+" AND  l.core_district_id=d.districtid AND a.int_fl2_fl2b_id=l.int_app_id " +
						"  AND a.vch_from='"+ac.getLiqourType()+"' "+filter+"  "+                                          
						"	group by a.vch_to,l.vch_licence_no,a.int_fl2_fl2b_id,vch_to_lic_no,a.vch_gatepass_no,a.dt_date,a.vch_to_lic_no,a.shop_id ,  "+
						"	int_brand_id,int_pckg_id,size,dispatch_bottle,p.quantity)x group by vch_licence_no,vch_to,int_fl2_fl2b_id,vch_to_lic_no,  "+
						"	vch_gatepass_no,dt_date)z,fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 m where               "+
						"	m.vch_gatepass_no=z.vch_gatepass_no order by vch_licence_no,dt_date";
			
			}
											
			 System.out.println("query=="+reportQuery);
			  pst = con.prepareStatement(reportQuery);
	         rs = pst.executeQuery();
			if (rs.next()) {

				rs = pst.executeQuery();

				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate", ac.getFrom_dt());
				parameters.put("toDate", ac.getTo_dt());
				parameters.put("district", ac.getName());
				
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath
							+ File.separator
							+ "wholeasle_old_stock_manual_dispach_repot_18_19.jasper");
				
				
				
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(
						print,
						relativePathpdf + File.separator
								+ "wholeasle_old_stock_manual_dispach_repot_18_19"
								+ ac.getUser_id() + n + ".pdf");
				
				ac.setPdfName("wholeasle_old_stock_manual_dispach_repot_18_19"
						+ ac.getUser_id() + n + ".pdf");
				
				ac.setFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
		              ac.setFlag(false);
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
   
   public void printReportAll(Wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action ac){

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
       String brand_catagory="";
		
		if(ac.getLiqourType().equalsIgnoreCase("FL2B"))
			brand_catagory="Beer";
		else if(ac.getLiqourType().equalsIgnoreCase("FL2"))
			brand_catagory="FL";

		try {
			con = ConnectionToDataBase.getConnection();

			 if(ac.getLiqourType().equalsIgnoreCase("FL2D")){
				 
				 
				 reportQuery= "	 select core_district_id,case when license_category in ('BEER','IMPORTED BEER','LAB') then 'BEER' else 'FL' end as license_category," +
				 		"  vch_to,int_fl2_fl2b_id,vch_gatepass_no,dt_date,vch_to_lic_no,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty," +
				 		"   sum(bl) as bl,l.vch_licence_no, "+
						" (select description from district where districtid=l.core_district_id) as dist  from (select br.license_category, CASE WHEN a.vch_to='RT' THEN 'Retail Shop' WHEN  "+
						"  a.vch_to='BRC' THEN 'Hotel Bar Club' else a.vch_to end as vch_to,a.int_fl2d_id as int_fl2_fl2b_id,a.vch_gatepass_no,a.dt_date, "+
						"  a.vch_to_lic_no ,int_brand_id,int_pckg_id,size,dispatch_bottle,sum(b.duty+b.add_duty) as duty,                                 "+
						"  (dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,                         "+
						"  fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b,                                                                                    "+
						"  distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id                   "+
						"  and br.brand_id=p.brand_id_fk and                                                                                              "+
						"  b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no  and dt_date               "+
						"  between '"+Utility.convertUtilDateToSQLDate(ac.getFrom_dt())+"' and '"+Utility.convertUtilDateToSQLDate(ac.getTo_dt())+"'  AND a.vch_from='FL2D'                                                                   "+
						"  group by a.vch_to,a.int_fl2d_id,a.vch_gatepass_no,a.dt_date,a.vch_to_lic_no,a.vch_to_lic_no ,                                  "+
						"  int_brand_id,int_pckg_id,size,dispatch_bottle,p.quantity,br.license_category)x,licence.fl2_2b_2d_19_20 l where                 "+
						"  x.int_fl2_fl2b_id=l.int_app_id  group by dist,license_category,vch_to,int_fl2_fl2b_id,vch_gatepass_no,     "+
						"  dt_date,vch_to_lic_no,l.vch_licence_no,core_district_id order by dist,vch_licence_no,core_district_id,vch_to,                                      "+
						"  license_category,int_fl2_fl2b_id,vch_to_lic_no	";
				 
				}
			   else{
					reportQuery =  
						 "	 select mst.total_duty+mst.total_adduty as duty,'"+brand_catagory+"' as license_category, z.vch_to_lic_no,z.vch_to,z.int_fl2_fl2b_id,z.vch_gatepass_no,z.dt_date,sum(dispatch_bottle) as    "+
						"	 dispatch_bottle,sum(bl) as bl,z.vch_licence_no, z.dist from (select x.*,vch_licence_no,                                                     "+
						"				(select description from district where districtid=l.core_district_id) as dist  from                                             "+
						"				(select  CASE WHEN a.vch_to='RT' THEN 'Retail Shop' WHEN                                                                         "+
						"	a.vch_to='BRC' THEN 'Hotel Bar Club' end as vch_to,a.int_fl2_fl2b_id,a.vch_gatepass_no,a.dt_date,                                            "+
						"	a.vch_to_lic_no ,int_brand_id,int_pckg_id,size,dispatch_bottle,                                                                                    "+
						"	(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a,                                   "+
						"	fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b,                                                                                               "+
						"	distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id                                 "+
						"	and br.brand_id=p.brand_id_fk and                                                                                                            "+
						"	b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no  and dt_date                             "+
						"	between '"+Utility.convertUtilDateToSQLDate(ac.getFrom_dt())+"' and '"+Utility.convertUtilDateToSQLDate(ac.getTo_dt())+"'  AND a.vch_from='"+ac.getLiqourType()+"'" +
						"	group by a.vch_to,a.int_fl2_fl2b_id,a.vch_gatepass_no,a.dt_date,a.vch_to_lic_no,a.vch_to_lic_no ,                                                  "+
						"	int_brand_id,int_pckg_id,size,dispatch_bottle,p.quantity)x,licence.fl2_2b_2d_19_20 l where                                                   "+
						"	 x.int_fl2_fl2b_id=l.int_app_id)z, fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 mst where z.vch_gatepass_no=mst.vch_gatepass_no "+
						"	 group by z.vch_to_lic_no,z.vch_to,z.int_fl2_fl2b_id,z.vch_gatepass_no,z.dt_date,z.vch_licence_no, z.dist,mst.total_duty,mst.total_adduty          "+
						"	 ,license_category order by dist,vch_licence_no,dt_date	";
					 }
			System.out.println("query=="+reportQuery);
			  pst = con.prepareStatement(reportQuery);
	         rs = pst.executeQuery();
			if (rs.next()) {

				rs = pst.executeQuery();

				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate", ac.getFrom_dt());
				parameters.put("toDate", ac.getTo_dt());
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath
							+ File.separator
							+ "wholeasle_old_stock_manual_dispach_repot_18_19_all.jasper");
				
				
				
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(
						print,
						relativePathpdf + File.separator
								+ "wholeasle_old_stock_manual_dispach_repot_18_19_all"
								+ ac.getUser_id() + n + ".pdf");
				
				ac.setPdfName("wholeasle_old_stock_manual_dispach_repot_18_19_all"
						+ ac.getUser_id() + n + ".pdf");
				
				ac.setFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
		              ac.setFlag(false);
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
				//e.printStackTrace();
			}
		}
  }
   

	public void getDistrictName(Wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action ac, int distId ){

		int id = 0;
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		String s = "";
		try {
			con = ConnectionToDataBase.getConnection();

			String selQr = " SELECT description From district Where districtid="+distId+"";
					
            // System.out.println("id query=="+selQr);   
			pstmt = con.prepareStatement(selQr);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				ac.setName(rs.getString("description"));
			}

		} catch (SQLException se) {
			//se.printStackTrace();
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
				//se.printStackTrace();
			}
		}
		

	}
	
	public ArrayList districtList(Wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action ac){

        ArrayList list = new ArrayList();
		
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		SelectItem item = new SelectItem();
	    item.setLabel("--Select--");
	    item.setValue(0);
	    list.add(item);
		try {
			con = ConnectionToDataBase.getConnection();

			String selQr = " SELECT districtid,description From district order by description ";
					
            // System.out.println("id query=="+selQr);   
			pstmt = con.prepareStatement(selQr);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				
				item = new SelectItem();
				item.setLabel(rs.getString("description"));
				item.setValue(rs.getString("districtid"));
				list.add(item);
			}

		} catch (SQLException se) {
			//se.printStackTrace();
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
				//se.printStackTrace();
			}
		}
		

	  return list;
	}
	
	public ArrayList shopList(Wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action ac, int id){


        ArrayList list = new ArrayList();
		
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		SelectItem item = new SelectItem();
	    item.setLabel("--Select--");
	    item.setValue(0);
	    list.add(item);
		try {
			con = ConnectionToDataBase.getConnection();

			String selQr = " SELECT vch_name_of_shop,serial_no From retail.retail_shop Where district_id="+id+" order by vch_name_of_shop ";
					
            //System.out.println("id query=="+selQr);   
			pstmt = con.prepareStatement(selQr);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				item = new SelectItem();
				item.setLabel(rs.getString("vch_name_of_shop")+" ("+rs.getInt("serial_no")+")");
				item.setValue(rs.getInt("serial_no"));
				list.add(item);
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
		

	  return list;
	
	}
	
	public void printExcelDistrictWise(Wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action ac){

		Connection con = null;
		String reportQuery= "";
		String filter="";
		if(ac.getShop_id()>0){
			filter=" AND a.shop_id='"+ac.getShop_id()+"' ";
		}
		
		double total_bottel=0.0;
		double total_duty=0.0;
		double total_bl=0.0;
				
	     String brand_catagory="";
		
		if(ac.getLiqourType().equalsIgnoreCase("FL2B"))
			brand_catagory="Beer";
		else if(ac.getLiqourType().equalsIgnoreCase("FL2"))
			brand_catagory="FL";
		
	//System.out.println("action.getDateSelected()===="+action.getDateSelected());
	/*	double current_bl_per = 0;
		double current_bl = 0;
		
		double commulative_bl = 0;
		double commulative_bl_per = 0;
		

		double yearly_mgq = 0;
		double monthly_mgq = 0;*/
		
	  if(ac.getDistType().equalsIgnoreCase("A") || ac.getDistrict_id()==0)
	  { 
		  if(ac.getLiqourType().equalsIgnoreCase("FL2D")){
			 
			 
			 reportQuery= "	 select core_district_id,case when license_category in ('BEER','IMPORTED BEER','LAB') then 'BEER' else 'FL' end as license_category," +
			 		"  vch_to,int_fl2_fl2b_id,vch_gatepass_no,dt_date,vch_to_lic_no,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty," +
			 		"   sum(bl) as bl,l.vch_licence_no, "+
					" (select description from district where districtid=l.core_district_id) as dist  from (select br.license_category, CASE WHEN a.vch_to='RT' THEN 'Retail Shop' WHEN  "+
					"  a.vch_to='BRC' THEN 'Hotel Bar Club' else a.vch_to end as vch_to,a.int_fl2d_id as int_fl2_fl2b_id,a.vch_gatepass_no,a.dt_date, "+
					"  a.vch_to_lic_no ,int_brand_id,int_pckg_id,size,dispatch_bottle,sum(b.duty+b.add_duty) as duty,                                 "+
					"  (dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,                         "+
					"  fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b,                                                                                    "+
					"  distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id                   "+
					"  and br.brand_id=p.brand_id_fk and                                                                                              "+
					"  b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no  and dt_date               "+
					"  between '"+Utility.convertUtilDateToSQLDate(ac.getFrom_dt())+"' and '"+Utility.convertUtilDateToSQLDate(ac.getTo_dt())+"'  AND a.vch_from='FL2D'                                                                   "+
					"  group by a.vch_to,a.int_fl2d_id,a.vch_gatepass_no,a.dt_date,a.vch_to_lic_no,a.vch_to_lic_no ,                                  "+
					"  int_brand_id,int_pckg_id,size,dispatch_bottle,p.quantity,br.license_category)x,licence.fl2_2b_2d_19_20 l where                 "+
					"  x.int_fl2_fl2b_id=l.int_app_id  group by dist,license_category,vch_to,int_fl2_fl2b_id,vch_gatepass_no,     "+
					"  dt_date,vch_to_lic_no,l.vch_licence_no,core_district_id order by dist,vch_licence_no,core_district_id,vch_to,                                      "+
					"  license_category,int_fl2_fl2b_id,vch_to_lic_no	";
			 
			}
		   else{
				reportQuery =  
					 "	 select mst.total_duty+mst.total_adduty as duty,'"+brand_catagory+"' as license_category, z.vch_to_lic_no,z.vch_to,z.int_fl2_fl2b_id,z.vch_gatepass_no,z.dt_date,sum(dispatch_bottle) as    "+
					"	 dispatch_bottle,sum(bl) as bl,z.vch_licence_no, z.dist from (select x.*,vch_licence_no,                                                     "+
					"				(select description from district where districtid=l.core_district_id) as dist  from                                             "+
					"				(select  CASE WHEN a.vch_to='RT' THEN 'Retail Shop' WHEN                                                                         "+
					"	a.vch_to='BRC' THEN 'Hotel Bar Club' end as vch_to,a.int_fl2_fl2b_id,a.vch_gatepass_no,a.dt_date,                                            "+
					"	a.vch_to_lic_no ,int_brand_id,int_pckg_id,size,dispatch_bottle,                                                                                    "+
					"	(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a,                                   "+
					"	fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b,                                                                                               "+
					"	distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id                                 "+
					"	and br.brand_id=p.brand_id_fk and                                                                                                            "+
					"	b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no  and dt_date                             "+
					"	between '"+Utility.convertUtilDateToSQLDate(ac.getFrom_dt())+"' and '"+Utility.convertUtilDateToSQLDate(ac.getTo_dt())+"'  AND a.vch_from='"+ac.getLiqourType()+"'" +
					"	group by a.vch_to,a.int_fl2_fl2b_id,a.vch_gatepass_no,a.dt_date,a.vch_to_lic_no,a.vch_to_lic_no ,                                                  "+
					"	int_brand_id,int_pckg_id,size,dispatch_bottle,p.quantity)x,licence.fl2_2b_2d_19_20 l where                                                   "+
					"	 x.int_fl2_fl2b_id=l.int_app_id)z, fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 mst where z.vch_gatepass_no=mst.vch_gatepass_no "+
					"	 group by z.vch_to_lic_no,z.vch_to,z.int_fl2_fl2b_id,z.vch_gatepass_no,z.dt_date,z.vch_licence_no, z.dist,mst.total_duty,mst.total_adduty          "+
					"	 order by dist,vch_licence_no,dt_date,license_category	";
				 }}
	  
	  else
	  {
		  if(ac.getLiqourType().equalsIgnoreCase("FL2D")){
			  
			  reportQuery= "	 select core_district_id,case when license_category in ('BEER','IMPORTED BEER','LAB') then 'BEER' else 'FL' end as license_category, " +
			 		"  vch_to,int_fl2_fl2b_id,vch_gatepass_no,dt_date,vch_to_lic_no,sum(dispatch_bottle) as dispatch_bottle,sum(duty) as duty," +
			 		"   sum(bl) as bl,l.vch_licence_no, "+
					" '"+ac.getName()+"' as dist  from (select br.license_category, CASE WHEN a.vch_to='RT' THEN 'Retail Shop' WHEN  "+
					"  a.vch_to='BRC' THEN 'Hotel Bar Club' else a.vch_to end as vch_to,a.int_fl2d_id as int_fl2_fl2b_id,a.vch_gatepass_no,a.dt_date, "+
					"  a.vch_to_lic_no ,int_brand_id,int_pckg_id,size,dispatch_bottle,sum(b.duty+b.add_duty) as duty,                                 "+
					"  (dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2d_oldstock_18_19 a,                         "+
					"  fl2d.fl2d_stock_trxn_fl2d_oldstock_18_19 b,                                                                                    "+
					"  distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id                   "+
					"  and br.brand_id=p.brand_id_fk and                                                                                              "+
					"  b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no  and dt_date               "+
					"  between '"+Utility.convertUtilDateToSQLDate(ac.getFrom_dt())+"' and '"+Utility.convertUtilDateToSQLDate(ac.getTo_dt())+"'  AND a.vch_from='FL2D'                                                                   "+
					"  "+filter+" group by a.vch_to,a.int_fl2d_id,a.vch_gatepass_no,a.dt_date,a.vch_to_lic_no,a.vch_to_lic_no ,                                  "+
					"  int_brand_id,int_pckg_id,size,dispatch_bottle,p.quantity,br.license_category)x,licence.fl2_2b_2d_19_20 l where                 "+
					"  x.int_fl2_fl2b_id=l.int_app_id and l.core_district_id="+ac.getDistrict_id()+" group by dist,license_category,vch_to,int_fl2_fl2b_id,vch_gatepass_no,     "+
					"  dt_date,vch_to_lic_no,l.vch_licence_no,core_district_id order by dist,vch_licence_no,core_district_id,vch_to,                                      "+
					"  license_category,int_fl2_fl2b_id,vch_to_lic_no	";
			  }
		  else{
              
	reportQuery =   " select z.*,total_duty+total_adduty as duty,'"+ac.getName()+"' as dist,'"+brand_catagory+"' as license_category from (select vch_licence_no,vch_to,int_fl2_fl2b_id,vch_to_lic_no,vch_gatepass_no,dt_date,    "+
					"	sum(dispatch_bottle) as dispatch_bottle,sum(bl) as bl from (select CASE WHEN a.vch_to='RT' THEN 'Retail Shop' WHEN    "+
					"	a.vch_to='BRC' THEN 'Hotel Bar Club' end as vch_to,l.vch_licence_no,a.int_fl2_fl2b_id,a.vch_gatepass_no,a.dt_date,            "+
					"	a.shop_id ,int_brand_id,int_pckg_id,size,dispatch_bottle,a.vch_to_lic_no,                                    "+      
					"	(dispatch_bottle*p.quantity)/1000 as bl from fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 a,   "+
					"	fl2d.fl2_stock_trxn_fl2_fl2b_oldstock_18_19 b,licence.fl2_2b_2d_19_20 l, public.district d,                  "+        
					"	distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 p where b.int_brand_id=br.brand_id     "+
					"	and br.brand_id=p.brand_id_fk and                                                                                "+
					"	b.int_brand_id=p.brand_id_fk and b.int_pckg_id=p.package_id and a.vch_gatepass_no=b.vch_gatepass_no  and dt_date         "+
					"	between '"+Utility.convertUtilDateToSQLDate(ac.getFrom_dt())+"' and '"+Utility.convertUtilDateToSQLDate(ac.getTo_dt())+"'  "+
					"	and l.core_district_id="+ac.getDistrict_id()+" AND  l.core_district_id=d.districtid AND a.int_fl2_fl2b_id=l.int_app_id " +
					"  AND a.vch_from='"+ac.getLiqourType()+"' "+filter+"  "+                                          
					"	group by a.vch_to,l.vch_licence_no,a.int_fl2_fl2b_id,vch_to_lic_no,a.vch_gatepass_no,a.dt_date,a.vch_to_lic_no,a.shop_id ,  "+
					"	int_brand_id,int_pckg_id,size,dispatch_bottle,p.quantity)x group by vch_licence_no,vch_to,int_fl2_fl2b_id,vch_to_lic_no,  "+
					"	vch_gatepass_no,dt_date)z,fl2d.gatepass_to_districtwholesale_fl2_fl2b_oldstock_18_19 m where               "+
					"	m.vch_gatepass_no=z.vch_gatepass_no order by vch_licence_no,dt_date";

}
	  }
		  System.out.println("query==="+reportQuery);
		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long start = 0;
		long end = 0;
		boolean flag = false;
		long k = 0;
		String noOfUnit = "";
		String date = null;

		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(reportQuery);

			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook
					.createSheet("Wholesale Old Stock (2018-19) manual Dispatch Report");

			worksheet.setColumnWidth(0, 5000);
			worksheet.setColumnWidth(1, 7000);
			worksheet.setColumnWidth(2, 5000);
			worksheet.setColumnWidth(3, 5000);
			worksheet.setColumnWidth(4, 5000);
			worksheet.setColumnWidth(5, 5000);
			worksheet.setColumnWidth(6, 5000);
			worksheet.setColumnWidth(7, 5000);
			worksheet.setColumnWidth(8, 5000);
			worksheet.setColumnWidth(9, 5000);
			worksheet.setColumnWidth(10, 5000);
			worksheet.setColumnWidth(11, 5000);

	     
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			
			cellhead0.setCellValue("Wholesale Old Stock (2018-19) manual Dispatch Report (All District)"+" From " +
					" "+Utility.convertUtilDateToSQLDate(ac.getFrom_dt())+" To "+Utility.convertUtilDateToSQLDate(ac.getTo_dt()));
			
				
			rowhead0.setHeight((short) 700);
	    	
		    XSSFCellStyle cellStyl = workbook.createCellStyle();
			cellStyl = workbook.createCellStyle();
			XSSFFont hSSFFont = workbook.createFont();
			hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
			hSSFFont.setFontHeightInPoints((short) 12);
			hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			hSSFFont.setColor(HSSFColor.GREEN.index);
			cellStyl.setFont(hSSFFont);
			
			cellhead0.setCellStyle(cellStyl);
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			XSSFCellStyle unlockcellStyle = workbook.createCellStyle();
			unlockcellStyle.setLocked(false);

			k = k + 1;
			XSSFRow rowhead = worksheet.createRow((int) 1);

			XSSFCell cellhead1 = rowhead.createCell((int) 0);
			cellhead1.setCellValue("District");
			cellhead1.setCellStyle(cellStyle);
			
			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("Gatepass No");
			cellhead2.setCellStyle(cellStyle);
			
			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Gatepass Date");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Retail/Hotel");
			cellhead4.setCellStyle(cellStyle);
			
			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Shop Id/HBR No.");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Brand Catagory");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Bottel");
			cellhead7.setCellStyle(cellStyle);
			

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("Duty");
			cellhead8.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("BL");
			cellhead9.setCellStyle(cellStyle);
			

			
			

			int i = 0;
			
			while (rs.next()) 
			{
				total_bottel +=rs.getDouble("dispatch_bottle");
				total_duty += rs.getDouble("duty");
				total_bl += rs.getDouble("bl");
				
				Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt_date"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);
				

				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				XSSFCell cellA1 = row1.createCell((int) 0); //
                cellA1.setCellValue(rs.getString("dist"));
				
				

				XSSFCell cellB1 = row1.createCell((int) 1); //
				cellB1.setCellValue(rs.getString("vch_gatepass_no"));//
				
				
				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(date); 
				
				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("vch_to"));
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("vch_to_lic_no"));
				

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("license_category"));
				
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getDouble("dispatch_bottle"));
				
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("duty"));
				
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getDouble("bl"));
				
				
				
				
		
			//System.out.println("------------ "+Math.round(cases_of_last_year_sale));

			}
			
			
			
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n+ "_Ws_old_stock_manual_dispatch_report.xls");

			ac.setExlname(n+"_Ws_old_stock_manual_dispatch_report.xls");
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue(" "); 
			cellA1.setCellStyle(cellStyle);
			
			XSSFCell cellA2 = row1.createCell((int) 1); 
			cellA2.setCellValue(" "); 
			cellA2.setCellStyle(cellStyle); 
			
			XSSFCell cellA3 = row1.createCell((int) 2); 
			cellA3.setCellValue(""); 
			cellA3.setCellStyle(cellStyle); 
			

			XSSFCell cellA4 = row1.createCell((int) 3); 
			cellA4.setCellValue(""); 
			cellA4.setCellStyle(cellStyle); 
			
			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue("");
			cellA5.setCellStyle(cellStyle);
			
			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue("Toatl ");
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(total_bottel);
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(total_duty); 
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(total_bl);
			cellA9.setCellStyle(cellStyle);
			
			/*XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(bl_of_current_year_sale);
			cellA9.setCellStyle(cellStyle);
			
			
			XSSFCell cellA10 = row1.createCell((int) 9);
			cellA10.setCellValue(revenue_of_current_year_sale);
			cellA10.setCellStyle(cellStyle);*/
			
			/*XSSFCell cellA11 = row1.createCell((int) 10);	
			cellA11.setCellStyle(cellStyle);
			
			
			XSSFCell cellA12 = row1.createCell((int) 11);		
			cellA12.setCellStyle(cellStyle);
		
			XSSFCell cellA13 = row1.createCell((int) 12);		
			cellA13.setCellStyle(cellStyle);*/
		
		
		


			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			ac.setExcelFlag(true);
			//con.close();

		} catch (Exception e) {


			e.printStackTrace();


		} finally {
			
			try
			{
			if (con != null)
				con.close();
			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		}

		//return flag;

}
	
	public ArrayList shop(Wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action ac){



        ArrayList list = new ArrayList();
		
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		SelectItem item = new SelectItem();
	    item.setLabel("--Select--");
	    item.setValue(0);
	    list.add(item);
		try {
			con = ConnectionToDataBase.getConnection();

			String selQr = " SELECT vch_name_of_shop,serial_no From retail.retail_shop order by vch_name_of_shop ";
					
            //System.out.println("id query=="+selQr);   
			pstmt = con.prepareStatement(selQr);

			rs = pstmt.executeQuery();

			/*while(rs.next()) {
				item = new SelectItem();
				item.setLabel(rs.getString("vch_name_of_shop"));
				item.setValue(rs.getInt("serial_no"));
				list.add(item);
			}*/

		} catch (SQLException se) {
			//se.printStackTrace();
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
				//se.printStackTrace();
			}
		}
		

	  return list;
	
	
	}
}
