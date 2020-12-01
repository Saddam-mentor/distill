package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mentor.action.ReportOnSpclAdditinalConsidrationFeeAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class ReportOnSpclAdditinalConsidrationFeeImpl {
	public void printReport(ReportOnSpclAdditinalConsidrationFeeAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null; 
		String unitTypeFilte="";
 

		try {
			con = ConnectionToDataBase.getConnection(); 
		 
			Date FromDate=Utility.convertUtilDateToSQLDate(act.getFromDate());
			Date toDate=Utility.convertUtilDateToSQLDate(act.getToDate());
			if(act.getUnitType()== null|| act.getUnitType().equalsIgnoreCase("")){
				unitTypeFilte="";
			}else{
				unitTypeFilte="and a.vch_challan_type='"+act.getUnitType()+"'";
			}
			String loginFilter="";
			if(ResourceUtil.getUserNameAllReq()!=null){
				
				
				if(ResourceUtil.getUserNameAllReq().length()>8){
					
				if(ResourceUtil.getUserNameAllReq().trim().substring(0,10).equalsIgnoreCase("Excise-DEO")){
					 loginFilter=" a.vch_user_id='"+ResourceUtil.getUserNameAllReq().trim()+"' and ";
				 }else{
					 loginFilter="";
				 }
				 }else{
					 loginFilter=""; 
				 }
			}
			if(act.getRadio().equalsIgnoreCase("D")){
			reportQuery="select x.distillery_id,deduct_amt,amount,head_type,cast(dist_type as character varying),(select vch_undertaking_name from dis_mst_pd1_pd2_lic where int_app_id_f=x.distillery_id)as name from              "+
						"  (select 'Distillry CL' as dist_type, a.distillery_id ,deduct_amt,vch_duty_type from (select distinct distillery_id from  distillery.brand_registration_19_20 where distillery_id>0) a left outer join (  "+
						"  select sum(int_value) as deduct_amt,int_distillery_id,vch_duty_type from distillery.duty_register_19_20 where                                                                                            "+
						"  	vch_duty_type='CL_SOCIAL_DUTY' and  date_crr_date between '"+FromDate+"' and '"+toDate+"' group by                                                                                                        "+   
						"  	int_distillery_id,vch_duty_type)b on b.int_distillery_id=a.distillery_id group by b.int_distillery_id,                                                                                                  "+
						"   b.vch_duty_type,a.distillery_id,b.deduct_amt)x,                                                                                                                                                         "+
						"                                                                                                                                                                                                           "+
						"  (select a.distillery_id,amount,head_type from (select distinct distillery_id from distillery.brand_registration_19_20                                                                                    "+
						"   where distillery_id>0) a  left outer join                                                                                                                                                               "+
						"   (select sum(a.vch_total_amount::decimal) as amount,a.vch_mill_name,b.head_type from licence.mst_challan_master a ,licence.challan_head_details  b                                                       "+
						"    where a.vch_challan_id=b.vch_challan_id and a.vch_trn_status='SUCCESS' and a.vch_mill_name is not null and a.vch_mill_type='Distillery'                                                                "+
						"    and b.head_type='CL_SOCIAL_DUTY' and a.dat_created_date between '"+FromDate+"' and '"+toDate+"' group by a.vch_mill_name,b.head_type) b on a.distillery_id::text=b.vch_mill_name)y                       "+    
						"     where x.distillery_id=y.distillery_id                                                                                                                                                                 "+
						"                                                                                                                                                                                                           "+
						"                                                                                                                                                                                                           "+
						"  union  all                                                                                                                                                                                               "+  
						"                                                                                                                                                                                                           "+
						"  select m.distillery_id,deduct_amt,amount,head_type,cast(dist_type as character varying),(select vch_undertaking_name from dis_mst_pd1_pd2_lic where int_app_id_f=m.distillery_id)as name from            "+
						"  (select 'Distillry FL' as dist_type, a.distillery_id ,deduct_amt,vch_duty_type from                                                                                                                      "+
						"   (select distinct distillery_id from  distillery.brand_registration_19_20 where distillery_id>0) a left outer join (                                                                                     "+
						"  select sum(int_value) as deduct_amt,int_distillery_id,vch_duty_type from distillery.duty_register_19_20 where                                                                                            "+
						"  	vch_duty_type='FL_SOCIAL_DUTY' and  date_crr_date between '"+FromDate+"' and '"+toDate+"' group by                                                                                                        "+   
						"  	int_distillery_id,vch_duty_type)b on b.int_distillery_id=a.distillery_id group by b.int_distillery_id,                                                                                                  "+
						"   b.vch_duty_type,a.distillery_id,b.deduct_amt)m,                                                                                                                                                         "+
						"                                                                                                                                                                                                           "+
						"  (select a.distillery_id,amount,head_type from (select distinct distillery_id from distillery.brand_registration_19_20                                                                                    "+
						"   where distillery_id>0) a  left outer join                                                                                                                                                               "+
						"   (select sum(a.vch_total_amount::decimal) as amount,a.vch_mill_name,b.head_type from licence.mst_challan_master a ,licence.challan_head_details  b                                                       "+
						"    where a.vch_challan_id=b.vch_challan_id and a.vch_trn_status='SUCCESS' and a.vch_mill_name is not null and a.vch_mill_type='Distillery'                                                                "+
						"    and b.head_type='FL_SOCIAL_DUTY' and a.dat_created_date between '"+FromDate+"' and '"+toDate+"' group by a.vch_mill_name,b.head_type) b on a.distillery_id::text=b.vch_mill_name)n                       "+    
						"     where m.distillery_id=n.distillery_id                                                                                                                                                                 "+
						"                                                                                                                                                                                                           "+
						"    union all                                                                                                                                                                                              "+
						"                                                                                                                                                                                                           "+
						"    select x.brewery_id,deduct_amt,amount,head_type,cast(dist_type as character varying),(select brewery_nm from bre_mst_b1_lic where vch_app_id_f=x.brewery_id)as name from                               "+
						"    (select 'Brewery' as dist_type, a.brewery_id ,deduct_amt,vch_duty_type from (select distinct brewery_id from                                                                                           "+
						"   distillery.brand_registration_19_20 where brewery_id>0) a left outer join (                                                                                                                             "+
						"   select sum(int_value) as deduct_amt,int_brewery_id,vch_duty_type from bwfl.duty_register_19_20 where                                                                                                    "+
						"  	vch_duty_type='BEER_SOCIAL_DUTY' and  date_crr_date between '"+FromDate+"' and '"+toDate+"' group by                                                                                                      "+   
						"  	int_brewery_id,vch_duty_type)b on b.int_brewery_id=a.brewery_id group by b.int_brewery_id,                                                                                                              "+
						"   b.vch_duty_type,a.brewery_id,b.deduct_amt)x,                                                                                                                                                            "+
						"                                                                                                                                                                                                           "+
						"   (select a.brewery_id,amount,head_type from (select distinct brewery_id from distillery.brand_registration_19_20                                                                                         "+
						"   where brewery_id>0) a  left outer join                                                                                                                                                                  "+
						"   (select sum(a.vch_total_amount::decimal) as amount,a.vch_mill_name,b.head_type from licence.mst_challan_master a ,licence.challan_head_details  b                                                       "+
						"    where a.vch_challan_id=b.vch_challan_id and a.vch_trn_status='SUCCESS' and a.vch_mill_name is not null and a.vch_mill_type='Brewery'                                                                   "+
						"    and b.head_type='BEER_SOCIAL_DUTY' and a.dat_created_date between '"+FromDate+"' and '"+toDate+"' group by a.vch_mill_name,b.head_type) b on a.brewery_id::text=b.vch_mill_name)y                        "+     
						"     where x.brewery_id=y.brewery_id                                                                                                                                                                       "+
						"                                                                                                                                                                                                           "+
						"   union all                                                                                                                                                                                               "+
						"                                                                                                                                                                                                           "+
						" select z.*,vch_firm_name as name from                                                                                                                                                                     "+
						" (select x.int_bwfl_id,deduct_amt,amount,head_type,cast(dist_type as character varying) from                                                                                                               "+
						"  (select 'BOND' as dist_type, a.int_bwfl_id ,deduct_amt,vch_duty_type from (select distinct int_bwfl_id from  distillery.brand_registration_19_20 where int_bwfl_id>0) a left outer join (                "+
						"  select sum(int_value) as deduct_amt,int_bwfl_id,vch_duty_type from distillery.duty_register_19_20,bwfl.registration_of_bwfl_lic_holder_19_20  where                                                      "+                                           
						"  vch_duty_type IN ('BWFL2B_SOCIAL_DUTY','BWFL2A_SOCIAL_DUTY','BWFL2C_SOCIAL_DUTY') and  date_crr_date between '"+FromDate+"' and '"+toDate+"' group by                                                      "+                                                     
						"  int_bwfl_id,vch_duty_type)b on b.int_bwfl_id=a.int_bwfl_id group by b.int_bwfl_id,                                                                                                                       "+
						"   b.vch_duty_type,a.int_bwfl_id,b.deduct_amt)x,                                                                                                                                                        "+ 
						"                                                                                                                                                                                                        "+ 
						"    (select a.int_bwfl_id,amount,head_type from (select distinct int_bwfl_id from distillery.brand_registration_19_20                                                                                   "+ 
						"   where int_bwfl_id>0) a  left outer join                                                                                                                                                              "+ 
						"   (select sum(a.vch_total_amount::decimal) as amount,a.vch_mill_name,b.head_type from licence.mst_challan_master a ,licence.challan_head_details  b                                                    "+ 
						"    where a.vch_challan_id=b.vch_challan_id and a.vch_trn_status='SUCCESS' and a.vch_mill_name is not null and a.vch_mill_type='Bwfl'                                                                   "+ 
						"    and b.head_type IN ('BWFL2B_SOCIAL_DUTY','BWFL2A_SOCIAL_DUTY','BWFL2C_SOCIAL_DUTY') and a.dat_created_date between '"+FromDate+"' and '"+toDate+"' group by a.vch_mill_name,b.head_type)              "+
						"   b on a.int_bwfl_id::text=b.vch_mill_name)y                                                                                                                                                           "+
						"     where x.int_bwfl_id=y.int_bwfl_id )z,bwfl.registration_of_bwfl_lic_holder_19_20 bwfl where z.int_bwfl_id=bwfl.unit_id                                                                              "+                                                                                    
						"     order by dist_type ";
			
			}else{
				reportQuery="   select x.dat_created_date, sum(coalesce(x.cl,0))cl, sum(coalesce(x.fl,0))fl,                                            "+
							"	sum(coalesce(x.beer,0))beer, sum(coalesce(x.bond,0)) bond from                                                        "+
							"	(select distinct b.dat_created_date,                                                                                  "+
							"	Case When a.head_type='CL_SOCIAL_DUTY' Then sum(a.amount) end as cl ,                                                      "+
							"	Case When a.head_type='FL_SOCIAL_DUTY' Then sum(a.amount) end as fl,                                                       "+
							"	Case When a.head_type='BEER_SOCIAL_DUTY' Then sum(a.amount) end as beer,                                                   "+
							"	Case When a.head_type IN ('BWFL2B_SOCIAL_DUTY','BWFL2A_SOCIAL_DUTY','BWFL2C_SOCIAL_DUTY') Then sum(a.amount) end as bond                                                    "+
							"	from licence.challan_head_details a, licence.mst_challan_master b                                                     "+
							"	where a.vch_challan_id=b.vch_challan_id                                                                               "+
							"	and b.vch_trn_status='SUCCESS' and b.dat_created_date between '"+FromDate+"' and '"+toDate+"'                           "+
							"	 and a.head_type IN ('CL_SOCIAL_DUTY','FL_SOCIAL_DUTY','BEER_SOCIAL_DUTY','BWFL2B_SOCIAL_DUTY','BWFL2A_SOCIAL_DUTY','BWFL2C_SOCIAL_DUTY')   "+
							"	 group by b.dat_created_date,a.head_type                                                                              "+
							"	)x                                                                                                                    "+
							"	group by x.dat_created_date order by x.dat_created_date";
	
			}
			 
			System.out.println("reportQuery---------" + reportQuery);
			pst = con.prepareStatement(reportQuery);
			

			rs = pst.executeQuery();

			if (rs.next()) {
				
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate", Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate", Utility.convertUtilDateToSQLDate(act.getToDate()));
				//parameters.put("type", type);
			 	JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
			 	if(act.getRadio().equalsIgnoreCase("D")){
			 	jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator+ "Addition_Considration_fee_detail.jasper");
			 	}else{
			 		jasperReport = (JasperReport) JRLoader.loadObject(relativePath
							+ File.separator+ "Addition_Considration_fee.jasper");
			 	}
				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator + "Addition_Considration_fee" + "-" + n + ".pdf");
				act.setPdf_name("Addition_Considration_fee" + "-" + n+ ".pdf");
				act.setPrintFlag(true);
				
			} else { 
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found!!", "No Data Found!!"));
				act.setPrintFlag(false);
			}
		} catch (JRException e) {
			act.setPrintFlag(false);
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
		} catch (Exception e) {
			act.setPrintFlag(false);
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
			}
		}
	}
	
	public boolean writeExcel(ReportOnSpclAdditinalConsidrationFeeAction act) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double total_due=0.0;
		double total_dispatch=0.0;
		double total_cl=0.0;
		double total_fl=0.0;
		double total_beer=0.0;
		double total_bond=0.0;

		String sql = "";

	 
		String loginFilter="";
		if(ResourceUtil.getUserNameAllReq()!=null){/*
			if(ResourceUtil.getUserNameAllReq().trim().substring(0,10).equalsIgnoreCase("Excise-DEO")){
				 loginFilter=" a.vch_user_id='"+ResourceUtil.getUserNameAllReq().trim()+"' and ";
			 }else{
				 loginFilter="";
			 }
		*/

			
			
			if(ResourceUtil.getUserNameAllReq().length()>8){
				
			if(ResourceUtil.getUserNameAllReq().trim().substring(0,10).equalsIgnoreCase("Excise-DEO")){
				 loginFilter=" a.vch_user_id='"+ResourceUtil.getUserNameAllReq().trim()+"' and ";
			 }else{
				 loginFilter="  ";
			 }
			 }else{
				 loginFilter=""; 
			 }
		
		
		}	
		if(act.getRadio().equalsIgnoreCase("D")){
			sql="select x.distillery_id,deduct_amt,amount,head_type,cast(dist_type as character varying),(select vch_undertaking_name from dis_mst_pd1_pd2_lic where int_app_id_f=x.distillery_id)as name from              "+
					"  (select 'Distillry CL' as dist_type, a.distillery_id ,deduct_amt,vch_duty_type from (select distinct distillery_id from  distillery.brand_registration_19_20 where distillery_id>0) a left outer join (  "+
					"  select sum(int_value) as deduct_amt,int_distillery_id,vch_duty_type from distillery.duty_register_19_20 where                                                                                            "+
					"  	vch_duty_type='CL_SOCIAL_DUTY' and  date_crr_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' group by                                                                                                        "+   
					"  	int_distillery_id,vch_duty_type)b on b.int_distillery_id=a.distillery_id group by b.int_distillery_id,                                                                                                  "+
					"   b.vch_duty_type,a.distillery_id,b.deduct_amt)x,                                                                                                                                                         "+
					"                                                                                                                                                                                                           "+
					"  (select a.distillery_id,amount,head_type from (select distinct distillery_id from distillery.brand_registration_19_20                                                                                    "+
					"   where distillery_id>0) a  left outer join                                                                                                                                                               "+
					"   (select sum(a.vch_total_amount::decimal) as amount,a.vch_mill_name,b.head_type from licence.mst_challan_master a ,licence.challan_head_details  b                                                       "+
					"    where a.vch_challan_id=b.vch_challan_id and a.vch_trn_status='SUCCESS' and a.vch_mill_name is not null and a.vch_mill_type='Distillery'                                                                "+
					"    and b.head_type='CL_SOCIAL_DUTY' and a.dat_created_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' group by a.vch_mill_name,b.head_type) b on a.distillery_id::text=b.vch_mill_name)y                       "+    
					"     where x.distillery_id=y.distillery_id                                                                                                                                                                 "+
					"                                                                                                                                                                                                           "+
					"                                                                                                                                                                                                           "+
					"  union  all                                                                                                                                                                                               "+  
					"                                                                                                                                                                                                           "+
					"  select m.distillery_id,deduct_amt,amount,head_type,cast(dist_type as character varying),(select vch_undertaking_name from dis_mst_pd1_pd2_lic where int_app_id_f=m.distillery_id)as name from            "+
					"  (select 'Distillry FL' as dist_type, a.distillery_id ,deduct_amt,vch_duty_type from                                                                                                                      "+
					"   (select distinct distillery_id from  distillery.brand_registration_19_20 where distillery_id>0) a left outer join (                                                                                     "+
					"  select sum(int_value) as deduct_amt,int_distillery_id,vch_duty_type from distillery.duty_register_19_20 where                                                                                            "+
					"  	vch_duty_type='FL_SOCIAL_DUTY' and  date_crr_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' group by                                                                                                        "+   
					"  	int_distillery_id,vch_duty_type)b on b.int_distillery_id=a.distillery_id group by b.int_distillery_id,                                                                                                  "+
					"   b.vch_duty_type,a.distillery_id,b.deduct_amt)m,                                                                                                                                                         "+
					"                                                                                                                                                                                                           "+
					"  (select a.distillery_id,amount,head_type from (select distinct distillery_id from distillery.brand_registration_19_20                                                                                    "+
					"   where distillery_id>0) a  left outer join                                                                                                                                                               "+
					"   (select sum(a.vch_total_amount::decimal) as amount,a.vch_mill_name,b.head_type from licence.mst_challan_master a ,licence.challan_head_details  b                                                       "+
					"    where a.vch_challan_id=b.vch_challan_id and a.vch_trn_status='SUCCESS' and a.vch_mill_name is not null and a.vch_mill_type='Distillery'                                                                "+
					"    and b.head_type='FL_SOCIAL_DUTY' and a.dat_created_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' group by a.vch_mill_name,b.head_type) b on a.distillery_id::text=b.vch_mill_name)n                       "+    
					"     where m.distillery_id=n.distillery_id                                                                                                                                                                 "+
					"                                                                                                                                                                                                           "+
					"    union all                                                                                                                                                                                              "+
					"                                                                                                                                                                                                           "+
					"    select x.brewery_id,deduct_amt,amount,head_type,cast(dist_type as character varying),(select brewery_nm from bre_mst_b1_lic where vch_app_id_f=x.brewery_id)as name from                               "+
					"    (select 'Brewery' as dist_type, a.brewery_id ,deduct_amt,vch_duty_type from (select distinct brewery_id from                                                                                           "+
					"   distillery.brand_registration_19_20 where brewery_id>0) a left outer join (                                                                                                                             "+
					"   select sum(int_value) as deduct_amt,int_brewery_id,vch_duty_type from bwfl.duty_register_19_20 where                                                                                                    "+
					"  	vch_duty_type='BEER_SOCIAL_DUTY' and  date_crr_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' group by                                                                                                      "+   
					"  	int_brewery_id,vch_duty_type)b on b.int_brewery_id=a.brewery_id group by b.int_brewery_id,                                                                                                              "+
					"   b.vch_duty_type,a.brewery_id,b.deduct_amt)x,                                                                                                                                                            "+
					"                                                                                                                                                                                                           "+
					"   (select a.brewery_id,amount,head_type from (select distinct brewery_id from distillery.brand_registration_19_20                                                                                         "+
					"   where brewery_id>0) a  left outer join                                                                                                                                                                  "+
					"   (select sum(a.vch_total_amount::decimal) as amount,a.vch_mill_name,b.head_type from licence.mst_challan_master a ,licence.challan_head_details  b                                                       "+
					"    where a.vch_challan_id=b.vch_challan_id and a.vch_trn_status='SUCCESS' and a.vch_mill_name is not null and a.vch_mill_type='Brewery'                                                                   "+
					"    and b.head_type='BEER_SOCIAL_DUTY' and a.dat_created_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' group by a.vch_mill_name,b.head_type) b on a.brewery_id::text=b.vch_mill_name)y                        "+     
					"     where x.brewery_id=y.brewery_id                                                                                                                                                                       "+
					"                                                                                                                                                                                                           "+
					"   union all                                                                                                                                                                                               "+
					"                                                                                                                                                                                                           "+
					" select z.*,vch_firm_name as name from                                                                                                                                                                     "+
					" (select x.int_bwfl_id,deduct_amt,amount,head_type,cast(dist_type as character varying) from                                                                                                               "+
					"  (select 'BOND' as dist_type, a.int_bwfl_id ,deduct_amt,vch_duty_type from (select distinct int_bwfl_id from  distillery.brand_registration_19_20 where int_bwfl_id>0) a left outer join (                "+
					"  select sum(int_value) as deduct_amt,int_bwfl_id,vch_duty_type from distillery.duty_register_19_20,bwfl.registration_of_bwfl_lic_holder_19_20  where                                                      "+                                           
					"  vch_duty_type IN ('BWFL2B_SOCIAL_DUTY','BWFL2A_SOCIAL_DUTY','BWFL2C_SOCIAL_DUTY') and  date_crr_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' group by                                                      "+                                                     
					"  int_bwfl_id,vch_duty_type)b on b.int_bwfl_id=a.int_bwfl_id group by b.int_bwfl_id,                                                                                                                       "+
					"   b.vch_duty_type,a.int_bwfl_id,b.deduct_amt)x,                                                                                                                                                        "+ 
					"                                                                                                                                                                                                        "+ 
					"    (select a.int_bwfl_id,amount,head_type from (select distinct int_bwfl_id from distillery.brand_registration_19_20                                                                                   "+ 
					"   where int_bwfl_id>0) a  left outer join                                                                                                                                                              "+ 
					"   (select sum(a.vch_total_amount::decimal) as amount,a.vch_mill_name,b.head_type from licence.mst_challan_master a ,licence.challan_head_details  b                                                    "+ 
					"    where a.vch_challan_id=b.vch_challan_id and a.vch_trn_status='SUCCESS' and a.vch_mill_name is not null and a.vch_mill_type='Bwfl'                                                                   "+ 
					"    and b.head_type IN ('BWFL2B_SOCIAL_DUTY','BWFL2A_SOCIAL_DUTY','BWFL2C_SOCIAL_DUTY') and a.dat_created_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' group by a.vch_mill_name,b.head_type)              "+
					"   b on a.int_bwfl_id::text=b.vch_mill_name)y                                                                                                                                                           "+
					"     where x.int_bwfl_id=y.int_bwfl_id )z,bwfl.registration_of_bwfl_lic_holder_19_20 bwfl where z.int_bwfl_id=bwfl.unit_id                                                                              "+                                                                                    
					"     order by dist_type ";
		
		}else{
			sql="   select x.dat_created_date, sum(coalesce(x.cl,0))cl, sum(coalesce(x.fl,0))fl,                                            "+
					"	sum(coalesce(x.beer,0))beer, sum(coalesce(x.bond,0)) bond from                                                        "+
					"	(select distinct b.dat_created_date,                                                                                  "+
					"	Case When a.head_type='CL_SOCIAL_DUTY' Then sum(a.amount) end as cl ,                                                      "+
					"	Case When a.head_type='FL_SOCIAL_DUTY' Then sum(a.amount) end as fl,                                                       "+
					"	Case When a.head_type='BEER_SOCIAL_DUTY' Then sum(a.amount) end as beer,                                                   "+
					"	Case When a.head_type IN ('BWFL2B_SOCIAL_DUTY','BWFL2A_SOCIAL_DUTY','BWFL2C_SOCIAL_DUTY') Then sum(a.amount) end as bond                                                    "+
					"	from licence.challan_head_details a, licence.mst_challan_master b                                                     "+
					"	where a.vch_challan_id=b.vch_challan_id                                                                               "+
					"	and b.vch_trn_status='SUCCESS' and b.dat_created_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                           "+
					"	 and a.head_type IN ('CL_SOCIAL_DUTY','FL_SOCIAL_DUTY','BEER_SOCIAL_DUTY','BWFL2B_SOCIAL_DUTY','BWFL2A_SOCIAL_DUTY','BWFL2C_SOCIAL_DUTY')   "+
					"	 group by b.dat_created_date,a.head_type                                                                              "+
					"	)x                                                                                                                    "+
					"	group by x.dat_created_date order by x.dat_created_date";

	}

		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			worksheet.setColumnWidth(0, 2000);
			worksheet.setColumnWidth(1, 4300);
			worksheet.setColumnWidth(2, 5000);
			worksheet.setColumnWidth(3, 5000);
			worksheet.setColumnWidth(4, 4000);
			worksheet.setColumnWidth(5, 4000);
			worksheet.setColumnWidth(6, 4000);
			worksheet.setColumnWidth(7, 4000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("Special Additional Considration Fee From "+ Utility.convertUtilDateToSQLDate(act.getFromDate())+" to "+Utility.convertUtilDateToSQLDate(act.getToDate()));

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
			cellhead1.setCellValue("S.No.");
			cellhead1.setCellStyle(cellStyle);

			if(act.getRadio().equalsIgnoreCase("D")){
				
				XSSFCell cellhead2 = rowhead.createCell((int) 1);
				cellhead2.setCellValue("Unit Type");
				cellhead2.setCellStyle(cellStyle);
				
				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("Unit Name");
				cellhead3.setCellStyle(cellStyle);
	
				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("Total Due");
				cellhead4.setCellStyle(cellStyle);
	
				XSSFCell cellhead5 = rowhead.createCell((int) 4);
				cellhead5.setCellValue("Total Dispatch");
				cellhead5.setCellStyle(cellStyle);
				
			}else{
				XSSFCell cellhead2 = rowhead.createCell((int) 1);
				cellhead2.setCellValue("Date");
				cellhead2.setCellStyle(cellStyle);
				
				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("CL");
				cellhead3.setCellStyle(cellStyle);
	
				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("FL");
				cellhead4.setCellStyle(cellStyle);
				
				XSSFCell cellhead5 = rowhead.createCell((int) 4);
				cellhead5.setCellValue("BEER");
				cellhead5.setCellStyle(cellStyle);
				
				XSSFCell cellhead6 = rowhead.createCell((int) 5);
				cellhead6.setCellValue("BOND");
				cellhead6.setCellStyle(cellStyle);
			}

			int i = 0;
			while (rs.next()) {
				if(act.getRadio().equalsIgnoreCase("D")){
				total_due=total_due+rs.getDouble("deduct_amt");
				total_dispatch=total_dispatch+rs.getDouble("amount");
				}else{
				total_cl=total_cl+rs.getDouble("cl");
				total_fl=total_fl+rs.getDouble("fl");
				total_beer=total_beer+rs.getDouble("beer");
				total_bond=total_bond+rs.getDouble("bond");
				}
				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k - 1);

				
				if(act.getRadio().equalsIgnoreCase("D")){
					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("dist_type"));
					XSSFCell cellC1 = row1.createCell((int) 2);
					cellC1.setCellValue(rs.getString("name"));
					XSSFCell cellD1 = row1.createCell((int) 3);
					cellD1.setCellValue(rs.getDouble("deduct_amt"));
					XSSFCell cellE1 = row1.createCell((int) 4);
					cellE1.setCellValue(rs.getDouble("amount"));
					
				}else{

					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("dat_created_date"));
					XSSFCell cellC1 = row1.createCell((int) 2);
					cellC1.setCellValue(rs.getDouble("cl"));
					XSSFCell cellD1 = row1.createCell((int) 3);
					cellD1.setCellValue(rs.getDouble("fl"));
					XSSFCell cellE1 = row1.createCell((int) 4);
					cellE1.setCellValue(rs.getDouble("beer"));
					XSSFCell cellF1 = row1.createCell((int) 5);
					cellF1.setCellValue(rs.getDouble("bond"));
				}
			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "AdditionalConsidrationFee.xls");
			act.setExlname(n + "AdditionalConsidrationFee");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue("");
			cellA1.setCellStyle(cellStyle);
			if(act.getRadio().equalsIgnoreCase("D")){
				XSSFCell cellA2 = row1.createCell((int) 1);
				cellA2.setCellValue(" ");
				cellA2.setCellStyle(cellStyle);
			
				XSSFCell cellA3 = row1.createCell((int) 2);
				cellA3.setCellValue("Total");
				cellA3.setCellStyle(cellStyle);
	
				XSSFCell cellA4 = row1.createCell((int) 3);
				cellA4.setCellValue(total_due);
				cellA4.setCellStyle(cellStyle);
	 
				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue(total_dispatch);
				cellA5.setCellStyle(cellStyle);
			}else{
				XSSFCell cellA2 = row1.createCell((int) 1);
				cellA2.setCellValue("Total");
				cellA2.setCellStyle(cellStyle);
			
				XSSFCell cellA3 = row1.createCell((int) 2);
				cellA3.setCellValue(total_cl);
				cellA3.setCellStyle(cellStyle);
	
				XSSFCell cellA4 = row1.createCell((int) 3);
				cellA4.setCellValue(total_fl);
				cellA4.setCellStyle(cellStyle);
	 
				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue(total_beer);
				cellA5.setCellStyle(cellStyle);
				XSSFCell cellA6 = row1.createCell((int) 5);
				cellA6.setCellValue(total_bond);
				cellA6.setCellStyle(cellStyle);
			}
 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

			flag = true;
			act.setExcelFlag(true);
			con.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return flag;

	}
}
