package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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

import com.mentor.action.ChallanDepositDetailSuccessRptAction;
import com.mentor.action.Distillery_BrewaryProductionReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class Distillery_BrewaryProductionReportImpl {

	
	public void printReport(Distillery_BrewaryProductionReportAction act) {

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
		String type = null;
		

		try {
			con = ConnectionToDataBase.getConnection();
			Date m1 =Utility.convertUtilDateToSQLDate(act.getProduction_dt());
			Calendar cal =Calendar.getInstance();
			cal.setTime(m1);
			cal.add(Calendar.DATE,-1);
			m1 =cal.getTime();
			System.out.println(m1);
			Date m2 =Utility.convertUtilDateToSQLDate(m1);
			//Calendar cal =Calendar.getInstance();
			cal.setTime(m2);
			cal.add(Calendar.DATE,-1);
			m2 =cal.getTime();
			System.out.println(m2);
			Date m3 =Utility.convertUtilDateToSQLDate(m2);
			//Calendar cal =Calendar.getInstance();
			cal.setTime(m3);
			cal.add(Calendar.DATE,-1);
			m3 =cal.getTime();
			System.out.println(m3);
			Date m4 =Utility.convertUtilDateToSQLDate(m3);
			//Calendar cal =Calendar.getInstance();
			cal.setTime(m4);
			cal.add(Calendar.DATE,-1);
			m4 =cal.getTime();
			System.out.println(m4);
			Date m5 =Utility.convertUtilDateToSQLDate(m4);
			//Calendar cal =Calendar.getInstance();
			cal.setTime(m5);
			cal.add(Calendar.DATE,-1);
			m5 =cal.getTime();
			System.out.println(m5);
			Date m6 =Utility.convertUtilDateToSQLDate(m5);
			//Calendar cal =Calendar.getInstance();
			cal.setTime(m6);
			cal.add(Calendar.DATE,-1);
			m6 =cal.getTime();
			System.out.println(m6);
			Date m7 =Utility.convertUtilDateToSQLDate(m6);
			//Calendar cal =Calendar.getInstance();
			cal.setTime(m7);
			cal.add(Calendar.DATE,-1);
			m7 =cal.getTime();
			System.out.println(m7);
			Date m8 =Utility.convertUtilDateToSQLDate(m7);
			//Calendar cal =Calendar.getInstance();
			cal.setTime(m8);
			cal.add(Calendar.DATE,-1);
			m8 =cal.getTime();
			System.out.println(m8);
			Date m9 =Utility.convertUtilDateToSQLDate(m8);
			//Calendar cal =Calendar.getInstance();
			cal.setTime(m9);
			cal.add(Calendar.DATE,-1);
			m9 =cal.getTime();
			System.out.println(m9);
			Date m10 =Utility.convertUtilDateToSQLDate(m9);
			//Calendar cal =Calendar.getInstance();
			cal.setTime(m10);
			cal.add(Calendar.DATE,-1);
			m10 =cal.getTime();
			System.out.println(m10);
			
       if(act.getRadioType().equalsIgnoreCase("BR")){
				
				type = "Brewery";
				
				reportQuery =" SELECT  a.distillery_id,  a.bottling_under,  b.brewery_nm,"+
						     " a.size_ml,  a.bottling_no_of_box, a.bottling_no_of_bottle,a.created_dt, "+
						     "((bottling_no_of_bottle *bottling_no_of_box)/1000) as bl "+
							 " FROM bwfl.daily_bottling_stock_19_20 a,public.bre_mst_b1_lic b where   "+
						     " a.distillery_id=b.vch_app_id_f and bottling_under='FL3' and a.created_dt " +
						     " BETWEEN  '"+Utility.convertUtilDateToSQLDate(m1)+"' AND  '"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"'" +
						     " order by a.created_dt"; 
							
			}
			else if(act.getRadioType().equalsIgnoreCase("DL")){
				
				type = "Distillery";
				
				reportQuery =" SELECT distillery_id as distillery_id_cl, "+
						" (select vch_undertaking_name from dis_mst_pd1_pd2_lic where  int_app_id_f=distillery_id ) as dist_nm_cl, "+
						" ((bottling_no_of_bottle *bottling_no_of_box)/1000) as BL_cl,bottling_under as bottling_under_cl, "+
						" size_ml as size_ml_cl,  bottling_no_of_box as bottling_no_of_box_cl, bottling_no_of_bottle as bottling_no_of_bottle_cl,"+
						" 0 as distillery_id_fl,  '' as dist_nm_fl,0 as BL_fl,'' as bottling_under_fl ,"+
						" 0 as size_ml_fl, 0 as bottling_no_of_box_fl,0 as bottling_no_of_bottle_fl,0 as distillery_id_fl3, '' as dist_nm_fl3,"+
						" 0 as BL_fl3,'' as bottling_under_fl3 ,0 as size_ml_fl3, 0 as bottling_no_of_box_fl3,0 as bottling_no_of_bottle_fl3"+
						" FROM distillery.daily_bottling_stock_19_20  where bottling_under='CL' and created_dt " +
						" BETWEEN  '"+Utility.convertUtilDateToSQLDate(m1)+"' AND  '"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' "+
						" union all  "+
						" SELECT  0 as distillery_id_cl,'' as dist_nm_cl,0 as BL_cl, '' as bottling_under_cl ,"+
						" 0 as size_ml_cl, 0 as bottling_no_of_box_cl, 0 as bottling_no_of_bottle_cl,distillery_id as distillery_id_fl, "+ 
						" (select vch_undertaking_name from dis_mst_pd1_pd2_lic where  int_app_id_f=distillery_id ) as dist_nm_fl,"+
						" ((bottling_no_of_bottle *bottling_no_of_box)/1000)  as BL_fl,bottling_under as bottling_under_fl , "+
						" size_ml as size_ml_fl,bottling_no_of_box as bottling_no_of_box_fl,bottling_no_of_bottle as bottling_no_of_bottle_fl, "+
						" 0 as distillery_id_fl3,'' as dist_nm_fl3,0 as BL_fl3,'' as bottling_under_fl3 ,"+
						" 0 as size_ml_fl3, 0 as bottling_no_of_box_fl3,0 as bottling_no_of_bottle_fl3 "+
						" FROM distillery.daily_bottling_stock_19_20  where bottling_under='FL3' and created_dt " +
						" BETWEEN  BETWEEN  '"+Utility.convertUtilDateToSQLDate(m1)+"' AND  '"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"'"+
						" union all "+
						" SELECT  0 as distillery_id_cl,'' as dist_nm_cl,0 as BL_cl,'' as bottling_under_cl , "+
						" 0 as size_ml_cl, 0 as bottling_no_of_box_cl, 0 as bottling_no_of_bottle_cl,0 as distillery_id_fl,  '' as dist_nm_fl, "+
						" 0 as BL_fl,'' as bottling_under_fl ,0 as size_ml_fl, 0 as bottling_no_of_box_fl,0 as bottling_no_of_bottle_fl, "+
						" distillery_id as distillery_id_fl3, (select vch_undertaking_name from dis_mst_pd1_pd2_lic where  int_app_id_f=distillery_id ) as dist_nm_fl3,"+
						" ((bottling_no_of_bottle *bottling_no_of_box)/1000)  as BL_fl3,bottling_under as bottling_under_fl3 ,"+
						" size_ml as size_ml_fl3,bottling_no_of_box as bottling_no_of_box_fl3,bottling_no_of_bottle as bottling_no_of_bottle_fl3 "+
						" FROM distillery.daily_bottling_stock_19_20  where bottling_under='FL3A' and created_dt " +
						" BETWEEN  '"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' AND  '"+Utility.convertUtilDateToSQLDate(m1)+"' ";
			}
	
			 System.out.println("======check======="+reportQuery);
			pst = con.prepareStatement(reportQuery);

			rs = pst.executeQuery();

			
			
			if (rs.next()) {
				
				

				rs = pst.executeQuery();
				Map parameters = new HashMap();
			    parameters.put("REPORT_CONNECTION", con);
				parameters.put("type", type);
				parameters.put("todate", act.getProduction_dt());
				parameters.put("formdate", act.getFormdate());
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				
			    if (act.getRadioType().equalsIgnoreCase("DL")){
					   
                	jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "DISTILLER_BREWERY_Production_report_distillery.jasper");

    				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
    				Random rand = new Random();
    				int n = rand.nextInt(250) + 1;
    				JasperExportManager.exportReportToPdfFile(print, relativePathpdf+ File.separator + "DISTILLER_BREWERY_Production_report_distillery"+ "-" + n + ".pdf");
    				act.setPdfName("DISTILLER_BREWERY_Production_report_distillery" + "-" + n + ".pdf");
    				act.setPrintFlag(true);
				
                }
                
                else{
                	
    				
    				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "DISTILLER_BREWERY_Production_report_brewery.jasper");

    				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
    				Random rand = new Random();
    				int n = rand.nextInt(250) + 1;
    				JasperExportManager.exportReportToPdfFile(print, relativePathpdf+ File.separator + "DISTILLER_BREWERY_Production_report_brewery"+ "-" + n + ".pdf");
    				act.setPdfName("DISTILLER_BREWERY_Production_report_brewery" + "-" + n + ".pdf");
    				act.setPrintFlag(true);
                }
				/*jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "Report_On_Dispatches.jasper");

			 
				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "Report_On_Dispatches" + "-" + n + ".pdf" );
				act.setPdfName("Report_On_Dispatches" + "-" + n + ".pdf");
				//act.setPrintFlag(true);
				act.setPrintFlag(true);*/
			
			} else {
				//act.setPrintFlag(false);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
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
	
	
	//-------------------------------------------------------------
	
	
	
	public void printExcel_Distillery(Distillery_BrewaryProductionReportAction act){


		Connection con = null;
		double cltotal_bl1 = 0.0;	
	    double cltotal_box1 = 0.0;
	    double cltotal_bl2 = 0.0;	
	    double cltotal_box2 = 0.0;
	    double cltotal_bl3 = 0.0;	
	    double cltotal_box3 = 0.0;
	    double cltotal_bl4 = 0.0;	
	    double cltotal_box4 = 0.0;
	    double cltotal_bl5 = 0.0;	
	    double cltotal_box5 = 0.0;
	    double cltotal_bl6 = 0.0;	
	    double cltotal_box6 = 0.0;
	    double cltotal_bl7 = 0.0;	
	    double cltotal_box7 = 0.0;
	    double cltotal_bl8 = 0.0;	
	    double cltotal_box8 = 0.0;
	    double cltotal_bl9 = 0.0;	
	    double cltotal_box9 = 0.0;
	    double cltotal_bl10 = 0.0;	
	    double cltotal_box10 = 0.0;	
		double total_bl1 = 0.0;	
	    double total_box1 = 0.0;
	    double total_bl2 = 0.0;	
	    double total_box2 = 0.0;
	    double total_bl3 = 0.0;	
	    double total_box3 = 0.0;
	    double total_bl4 = 0.0;	
	    double total_box4 = 0.0;
	    double total_bl5 = 0.0;	
	    double total_box5 = 0.0;
	    double total_bl6 = 0.0;	
	    double total_box6 = 0.0;
	    double total_bl7 = 0.0;	
	    double total_box7 = 0.0;
	    double total_bl8 = 0.0;	
	    double total_box8 = 0.0;
	    double total_bl9 = 0.0;	
	    double total_box9 = 0.0;
	    double total_bl10 = 0.0;	
	    double total_box10 = 0.0;	
	    
		String type="";
		String reportQuery="";
		
		Date m1 =Utility.convertUtilDateToSQLDate(act.getProduction_dt());
		Calendar cal =Calendar.getInstance();
		cal.setTime(m1);
		cal.add(Calendar.DATE,-1);
		m1 =cal.getTime();
		System.out.println(m1);
		Date m2 =Utility.convertUtilDateToSQLDate(m1);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m2);
		cal.add(Calendar.DATE,-1);
		m2 =cal.getTime();
		System.out.println(m2);
		Date m3 =Utility.convertUtilDateToSQLDate(m2);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m3);
		cal.add(Calendar.DATE,-1);
		m3 =cal.getTime();
		System.out.println(m3);
		Date m4 =Utility.convertUtilDateToSQLDate(m3);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m4);
		cal.add(Calendar.DATE,-1);
		m4 =cal.getTime();
		System.out.println(m4);
		Date m5 =Utility.convertUtilDateToSQLDate(m4);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m5);
		cal.add(Calendar.DATE,-1);
		m5 =cal.getTime();
		System.out.println(m5);
		Date m6 =Utility.convertUtilDateToSQLDate(m5);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m6);
		cal.add(Calendar.DATE,-1);
		m6 =cal.getTime();
		System.out.println(m6);
		Date m7 =Utility.convertUtilDateToSQLDate(m6);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m7);
		cal.add(Calendar.DATE,-1);
		m7 =cal.getTime();
		System.out.println(m7);
		Date m8 =Utility.convertUtilDateToSQLDate(m7);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m8);
		cal.add(Calendar.DATE,-1);
		m8 =cal.getTime();
		System.out.println(m8);
		Date m9 =Utility.convertUtilDateToSQLDate(m8);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m9);
		cal.add(Calendar.DATE,-1);
		m9 =cal.getTime();
		System.out.println(m9);
		Date m10 =Utility.convertUtilDateToSQLDate(m9);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m10);
		cal.add(Calendar.DATE,-1);
		m10 =cal.getTime();
		System.out.println(Utility.convertUtilDateToSQLDate(m10));
		
			
			if(act.getRadioType().equalsIgnoreCase("DL")){
				
				type = "Distillery";
				
				reportQuery = "	select a.distillery_id,(select vch_undertaking_name from dis_mst_pd1_pd2_lic where int_app_id_f=a.distillery_id) as distillery,a.CL_box as CL_box_a,a.CL_bl as CL_bl_a,a.FL_box as FL_box_a,a.FL_bl as FL_bl_a, "+
						 " b.CL_box as CL_box_b,b.CL_bl as CL_bl_b,b.FL_box as FL_box_b,b.FL_bl as FL_bl_b, "+
						 " c.CL_box as CL_box_c,c.CL_bl as CL_bl_c,c.FL_box as FL_box_c ,c.FL_bl as FL_bl_c, "+
						 " d.CL_box as CL_box_d,d.CL_bl as CL_bl_d,d.FL_box as FL_box_d,d.FL_bl as FL_bl_d, "+
						 " e.CL_box as CL_box_e,e.CL_bl as CL_bl_e,e.FL_box as FL_box_e,e.FL_bl as FL_bl_e, "+
						 " f.CL_box as CL_box_f,f.CL_bl as CL_bl_f,f.FL_box as FL_box_f,f.FL_bl as FL_bl_f,  "+
						 " g.CL_box as CL_box_g,g.CL_bl as CL_bl_g,g.FL_box as FL_box_g,g.FL_bl as FL_bl_g,  "+
						 " h.CL_box as CL_box_h,h.CL_bl as CL_bl_h,h.FL_box as FL_box_h,h.FL_bl as FL_bl_h,  "+
						 " i.CL_box as CL_box_i,i.CL_bl as CL_bl_i,i.FL_box as FL_box_i,i.FL_bl as FL_bl_i,  "+
						 " j.CL_box as CL_box_j,j.CL_bl as CL_bl_j,j.FL_box as FL_box_j,j.FL_bl as FL_bl_j from  "+ 
						 " (select  a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_20_21 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)a, "+
						 "   (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_20_21 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m1)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m1)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)b, "+
						 "  (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_20_21 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m2)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m2)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)c,  "+
						 "  (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_20_21 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m3)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m3)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)d, "+
						"  (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_20_21 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m4)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m4)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)e, "+
						" (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_20_21 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m5)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m5)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)f,  "+
						" (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_20_21 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m6)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m6)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)g,  "+
						" (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_20_21 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m7)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m7)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)h,  "+
						" (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_20_21 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m8)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m8)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)i,  "+
						" (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_20_21 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m9)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m9)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)j   "+
						"  where a.distillery_id=b.distillery_id and a.distillery_id=c.distillery_id and a.distillery_id=d.distillery_id and a.distillery_id=e.distillery_id and a.distillery_id=f.distillery_id and  "+
						"  a.distillery_id=g.distillery_id and a.distillery_id=h.distillery_id and a.distillery_id=i.distillery_id and a.distillery_id=j.distillery_id order by distillery";
						 
			}
			else {
				
				
			}
				
		System.out.println("excel query===  " + reportQuery);

		
		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long start = 0;
		long end = 0;
		boolean flag = false;
		long k = 0;
		

		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(reportQuery);

			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Distillery  Production Report");

			worksheet.setColumnWidth(0, 2000);
			worksheet.setColumnWidth(1, 12000);
			worksheet.setColumnWidth(2, 3000);
			worksheet.setColumnWidth(3, 3000);
			worksheet.setColumnWidth(4, 3000);
			worksheet.setColumnWidth(5, 3000);
			worksheet.setColumnWidth(6, 3000);
			worksheet.setColumnWidth(7, 3000);
			worksheet.setColumnWidth(8, 3000);
			worksheet.setColumnWidth(9, 3000);
			worksheet.setColumnWidth(10, 3000);
			worksheet.setColumnWidth(11, 3000);
			worksheet.setColumnWidth(12, 3000);
			worksheet.setColumnWidth(13, 3000);
			worksheet.setColumnWidth(14, 3000);
			worksheet.setColumnWidth(15, 3000);
			worksheet.setColumnWidth(16, 3000);
			worksheet.setColumnWidth(17, 3000);
			worksheet.setColumnWidth(18, 3000);
			worksheet.setColumnWidth(19, 3000);
			worksheet.setColumnWidth(20, 3000);
			worksheet.setColumnWidth(21, 3000);
			worksheet.setColumnWidth(22, 3000);
			worksheet.setColumnWidth(23, 3000);
			worksheet.setColumnWidth(24, 3000);
			worksheet.setColumnWidth(25, 3000);
			worksheet.setColumnWidth(26, 3000);
			worksheet.setColumnWidth(27, 3000);
			worksheet.setColumnWidth(28, 3000);
			worksheet.setColumnWidth(29, 3000);
			worksheet.setColumnWidth(30, 3000);
			worksheet.setColumnWidth(31, 3000);
			worksheet.setColumnWidth(32, 3000);
			worksheet.setColumnWidth(33, 3000);
			worksheet.setColumnWidth(34, 3000);
			worksheet.setColumnWidth(35, 3000);
			worksheet.setColumnWidth(36, 3000);
			worksheet.setColumnWidth(37, 3000);
			worksheet.setColumnWidth(38, 3000);
			worksheet.setColumnWidth(39, 3000);
			worksheet.setColumnWidth(40, 3000);
			worksheet.setColumnWidth(41, 3000);
			
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			
			cellhead0.setCellValue("Production Report  For " +type+ " From (Date"+Utility.convertUtilDateToSQLDate(m9)+ " To " +Utility.convertUtilDateToSQLDate(act.getProduction_dt())+")") ;
			
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
		
			
			
			XSSFRow rowhead1 = worksheet.createRow((int) 1);

			XSSFCell cellhead1_1 = rowhead1.createCell((int) 0);
			cellhead1_1.setCellValue("");
			cellhead1_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead2_1 = rowhead1.createCell((int) 1);
			cellhead2_1.setCellValue("");
			cellhead2_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead3_1 = rowhead1.createCell((int) 2);
			cellhead3_1.setCellValue("Date :-");
			cellhead3_1.setCellStyle(cellStyle);

			XSSFCell cellhead4_1 = rowhead1.createCell((int) 3);
			cellhead4_1.setCellValue(""+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+ "");
			cellhead4_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead5_1 = rowhead1.createCell((int) 4);
			cellhead5_1.setCellValue("Date :-");
			cellhead5_1.setCellStyle(cellStyle);

			XSSFCell cellhead6_1 = rowhead1.createCell((int) 5);
			cellhead6_1.setCellValue(""+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"");
			cellhead6_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead7_1 = rowhead1.createCell((int) 6);
			cellhead7_1.setCellValue("Date :-");
			cellhead7_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead8_1 = rowhead1.createCell((int) 7);
			cellhead8_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m1)+"");
			cellhead8_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead9_1 = rowhead1.createCell((int) 8);
			cellhead9_1.setCellValue("Date :-");
			cellhead9_1.setCellStyle(cellStyle);

			XSSFCell cellhead10_1 = rowhead1.createCell((int) 9);
			cellhead10_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m1)+"");
			cellhead10_1.setCellStyle(cellStyle);

			XSSFCell cellhead11_1 = rowhead1.createCell((int) 10);
			cellhead11_1.setCellValue("Date :-");
			cellhead11_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead12_1 = rowhead1.createCell((int) 11);
			cellhead12_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m2)+"");	
			cellhead12_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead13_1 = rowhead1.createCell((int) 12);
			cellhead13_1.setCellValue("Date :-");
			cellhead13_1.setCellStyle(cellStyle);

			XSSFCell cellhead14_1 = rowhead1.createCell((int) 13);
			cellhead14_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m2)+"");
			cellhead14_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead15_1 = rowhead1.createCell((int) 14);
			cellhead15_1.setCellValue("Date :-");
			cellhead15_1.setCellStyle(cellStyle);

			XSSFCell cellhead16_1 = rowhead1.createCell((int) 15);
			cellhead16_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m3)+"");
			cellhead16_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead17_1 = rowhead1.createCell((int) 16);
			cellhead17_1.setCellValue("Date :-");
			cellhead17_1.setCellStyle(cellStyle);

			XSSFCell cellhead18_1 = rowhead1.createCell((int) 17);
			cellhead18_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m3)+"");
			cellhead18_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead19_1 = rowhead1.createCell((int) 18);
			cellhead19_1.setCellValue("Date :-");
			cellhead19_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead20_1 = rowhead1.createCell((int) 19);
			cellhead20_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m4)+"");
			cellhead20_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead21_1 = rowhead1.createCell((int) 20);
			cellhead21_1.setCellValue("Date :-");
			cellhead21_1.setCellStyle(cellStyle);

			XSSFCell cellhead22_1 = rowhead1.createCell((int) 21);
			cellhead22_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m4)+"");
			cellhead22_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead23_1 = rowhead1.createCell((int) 22);
			cellhead23_1.setCellValue("Date :-");
			cellhead23_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead24_1 = rowhead1.createCell((int) 23);
			cellhead24_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m5)+"");
			cellhead24_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead25_1 = rowhead1.createCell((int) 24);
			cellhead25_1.setCellValue("Date :-");
			cellhead25_1.setCellStyle(cellStyle);

			XSSFCell cellhead26_1 = rowhead1.createCell((int) 25);
			cellhead26_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m5)+"");
			cellhead26_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead27_1 = rowhead1.createCell((int) 26);
			cellhead27_1.setCellValue("Date :-");
			cellhead27_1.setCellStyle(cellStyle);

			XSSFCell cellhead28_1 = rowhead1.createCell((int) 27);
			cellhead28_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m6)+"");
			cellhead28_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead29_1 = rowhead1.createCell((int) 28);
			cellhead29_1.setCellValue("Date :-");
			cellhead29_1.setCellStyle(cellStyle);

			XSSFCell cellhead30_1 = rowhead1.createCell((int) 29);
			cellhead30_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m6)+"");
			cellhead30_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead31_1 = rowhead1.createCell((int) 30);
			cellhead31_1.setCellValue("Date :-");
			cellhead31_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead32_1 = rowhead1.createCell((int) 31);
			cellhead32_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m7)+"");
			cellhead32_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead33_1 = rowhead1.createCell((int) 32);
			cellhead33_1.setCellValue("Date :-");
			cellhead33_1.setCellStyle(cellStyle);

			XSSFCell cellhead34_1= rowhead1.createCell((int) 33);
			cellhead34_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m7)+"");
			cellhead34_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead35_1 = rowhead1.createCell((int) 34);
			cellhead35_1.setCellValue("Date :-");
			cellhead35_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead36_1 = rowhead1.createCell((int) 35);
			cellhead36_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m8)+"");
			cellhead36_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead37_1 = rowhead1.createCell((int) 36);
			cellhead37_1.setCellValue("Date :-");
			cellhead37_1.setCellStyle(cellStyle);

			XSSFCell cellhead38_1 = rowhead1.createCell((int) 37);
			cellhead38_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m8)+"");
			cellhead38_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead39_1 = rowhead1.createCell((int) 38);
			cellhead39_1.setCellValue("Date :-");
			cellhead39_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead40_1 = rowhead1.createCell((int) 39);
			cellhead40_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m9)+"");
			cellhead40_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead41_1 = rowhead1.createCell((int) 40);
			cellhead41_1.setCellValue("Date :-");
			cellhead41_1.setCellStyle(cellStyle);

			XSSFCell cellhead42_1 = rowhead1.createCell((int) 41);
			cellhead42_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m9)+"");
			cellhead42_1.setCellStyle(cellStyle);
			
			XSSFRow rowhead = worksheet.createRow((int) 2);

			XSSFCell cellhead1 = rowhead.createCell((int) 0);
			cellhead1.setCellValue("Sr. No");
			cellhead1.setCellStyle(cellStyle);
			
			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("Distilletr Name");
			cellhead2.setCellStyle(cellStyle);
			
			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("CL Box");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("CL BL");
			cellhead4.setCellStyle(cellStyle);
			
			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("FL BOX");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("FL BL");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("CL BOX");
			cellhead7.setCellStyle(cellStyle);
			
			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("CL BL");
			cellhead8.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("FL BOX");
			cellhead9.setCellStyle(cellStyle);

			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("FL BL");
			cellhead10.setCellStyle(cellStyle);
			
			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("CL BOX");
			cellhead11.setCellStyle(cellStyle);
			
			XSSFCell cellhead12 = rowhead.createCell((int) 11);
			cellhead12.setCellValue("CL BL");
			cellhead12.setCellStyle(cellStyle);
			
			XSSFCell cellhead13 = rowhead.createCell((int) 12);
			cellhead13.setCellValue("FL BOX");
			cellhead13.setCellStyle(cellStyle);

			XSSFCell cellhead14 = rowhead.createCell((int) 13);
			cellhead14.setCellValue("FL BL");
			cellhead14.setCellStyle(cellStyle);
			
			XSSFCell cellhead15 = rowhead.createCell((int) 14);
			cellhead15.setCellValue("CL Box");
			cellhead15.setCellStyle(cellStyle);

			XSSFCell cellhead16 = rowhead.createCell((int) 15);
			cellhead16.setCellValue("CL BL");
			cellhead16.setCellStyle(cellStyle);
			
			XSSFCell cellhead17 = rowhead.createCell((int) 16);
			cellhead17.setCellValue("FL BOX");
			cellhead17.setCellStyle(cellStyle);

			XSSFCell cellhead18 = rowhead.createCell((int) 17);
			cellhead18.setCellValue("FL BL");
			cellhead18.setCellStyle(cellStyle);
			
			XSSFCell cellhead19 = rowhead.createCell((int) 18);
			cellhead19.setCellValue("CL BOX");
			cellhead19.setCellStyle(cellStyle);
			
			XSSFCell cellhead20 = rowhead.createCell((int) 19);
			cellhead20.setCellValue("CL BL");
			cellhead20.setCellStyle(cellStyle);
			
			XSSFCell cellhead21 = rowhead.createCell((int) 20);
			cellhead21.setCellValue("FL BOX");
			cellhead21.setCellStyle(cellStyle);

			XSSFCell cellhead22 = rowhead.createCell((int) 21);
			cellhead22.setCellValue("FL BL");
			cellhead22.setCellStyle(cellStyle);
			
			XSSFCell cellhead23 = rowhead.createCell((int) 22);
			cellhead23.setCellValue("CL BOX");
			cellhead23.setCellStyle(cellStyle);
			
			XSSFCell cellhead24 = rowhead.createCell((int) 23);
			cellhead24.setCellValue("CL BL");
			cellhead24.setCellStyle(cellStyle);
			
			XSSFCell cellhead25 = rowhead.createCell((int) 24);
			cellhead25.setCellValue("FL BOX");
			cellhead25.setCellStyle(cellStyle);

			XSSFCell cellhead26 = rowhead.createCell((int) 25);
			cellhead26.setCellValue("FL BL");
			cellhead26.setCellStyle(cellStyle);
			
			XSSFCell cellhead27 = rowhead.createCell((int) 26);
			cellhead27.setCellValue("CL Box");
			cellhead27.setCellStyle(cellStyle);

			XSSFCell cellhead28 = rowhead.createCell((int) 27);
			cellhead28.setCellValue("CL BL");
			cellhead28.setCellStyle(cellStyle);
			
			XSSFCell cellhead29 = rowhead.createCell((int) 28);
			cellhead29.setCellValue("FL BOX");
			cellhead29.setCellStyle(cellStyle);

			XSSFCell cellhead30 = rowhead.createCell((int) 29);
			cellhead30.setCellValue("FL BL");
			cellhead30.setCellStyle(cellStyle);
			
			XSSFCell cellhead31 = rowhead.createCell((int) 30);
			cellhead31.setCellValue("CL BOX");
			cellhead31.setCellStyle(cellStyle);
			
			XSSFCell cellhead32 = rowhead.createCell((int) 31);
			cellhead32.setCellValue("CL BL");
			cellhead32.setCellStyle(cellStyle);
			
			XSSFCell cellhead33 = rowhead.createCell((int) 32);
			cellhead33.setCellValue("FL BOX");
			cellhead33.setCellStyle(cellStyle);

			XSSFCell cellhead34= rowhead.createCell((int) 33);
			cellhead34.setCellValue("FL BL");
			cellhead34.setCellStyle(cellStyle);
			
			XSSFCell cellhead35 = rowhead.createCell((int) 34);
			cellhead35.setCellValue("CL BOX");
			cellhead35.setCellStyle(cellStyle);
			
			XSSFCell cellhead36 = rowhead.createCell((int) 35);
			cellhead36.setCellValue("CL BL");
			cellhead36.setCellStyle(cellStyle);
			
			XSSFCell cellhead37 = rowhead.createCell((int) 36);
			cellhead37.setCellValue("FL BOX");
			cellhead37.setCellStyle(cellStyle);

			XSSFCell cellhead38 = rowhead.createCell((int) 37);
			cellhead38.setCellValue("FL BL");
			cellhead38.setCellStyle(cellStyle);
			
			XSSFCell cellhead39 = rowhead.createCell((int) 38);
			cellhead39.setCellValue("CL BOX");
			cellhead39.setCellStyle(cellStyle);
			
			XSSFCell cellhead40 = rowhead.createCell((int) 39);
			cellhead40.setCellValue("CL BL");
			cellhead40.setCellStyle(cellStyle);
			
			XSSFCell cellhead41 = rowhead.createCell((int) 40);
			cellhead41.setCellValue("FL BOX");
			cellhead41.setCellStyle(cellStyle);

			XSSFCell cellhead42 = rowhead.createCell((int) 41);
			cellhead42.setCellValue("FL BL");
			cellhead42.setCellStyle(cellStyle);
			
			k = k + 2;
			int i = 0;
			
			while (rs.next()) 
			{
				//total = total + rs.getDouble("amount");
				cltotal_bl1 = cltotal_bl1 + rs.getDouble("CL_bl_a");
				System.out.println(cltotal_bl1);
				cltotal_box1 = cltotal_box1 + rs.getInt("CL_box_a");
				total_bl1 = total_bl1 + rs.getDouble("FL_bl_a");
				total_box1 = total_box1 + rs.getInt("FL_box_a");
				cltotal_bl2 = cltotal_bl2 + rs.getDouble("CL_bl_b");
				cltotal_box2 = cltotal_box2 + rs.getInt("CL_box_b");
				total_bl2 = total_bl2 + rs.getDouble("FL_bl_b");
				total_box2 = total_box2 + rs.getInt("FL_box_b");
				cltotal_bl3 = total_bl3 + rs.getDouble("CL_bl_c");
				cltotal_box3 = total_box3 + rs.getInt("CL_box_c");
				total_bl3 = total_bl3 + rs.getDouble("FL_bl_c");
				total_box3 = total_box3 + rs.getInt("FL_box_c");
				cltotal_bl4 = total_bl4 + rs.getDouble("CL_bl_d");
				cltotal_box4 = total_box4 + rs.getInt("CL_box_d");
				total_bl4 = total_bl4 + rs.getDouble("FL_bl_d");
				total_box4 = total_box4 + rs.getInt("FL_box_d");
				cltotal_bl5 = total_bl5 + rs.getDouble("CL_bl_e");
				cltotal_box5 = total_box5 + rs.getInt("CL_box_e");
				total_bl5 = total_bl5 + rs.getDouble("FL_bl_e");
				total_box5 = total_box5 + rs.getInt("FL_box_e");
				cltotal_bl6 = total_bl6 + rs.getDouble("CL_bl_f");
				cltotal_box6 = total_box6 + rs.getInt("CL_box_f");
				total_bl6 = total_bl6 + rs.getDouble("FL_bl_f");
				total_box6 = total_box6 + rs.getInt("FL_box_f");
				cltotal_bl7 = total_bl7 + rs.getDouble("CL_bl_g");
				cltotal_box7 = total_box7 + rs.getInt("CL_box_g");
				total_bl7 = total_bl7 + rs.getDouble("FL_bl_g");
				total_box7 = total_box7 + rs.getInt("FL_box_g");
				cltotal_bl8 = total_bl8 + rs.getDouble("CL_bl_h");
				cltotal_box8 = total_box8 + rs.getInt("CL_box_h");
				total_bl8 = total_bl8 + rs.getDouble("FL_bl_h");
				total_box8 = total_box8 + rs.getInt("FL_box_h");
				cltotal_bl9 = total_bl9 + rs.getDouble("CL_bl_i");
				cltotal_box9 = total_box9 + rs.getInt("CL_box_i");
				total_bl9 = total_bl9 + rs.getDouble("FL_bl_i");
				total_box9 = total_box9 + rs.getInt("FL_box_i");
				cltotal_bl10 = total_bl10 + rs.getDouble("CL_bl_j");
				cltotal_box10 = total_box10 + rs.getInt("CL_box_j");
				total_bl10 = total_bl10 + rs.getDouble("FL_bl_j");
				total_box10 = total_box10 + rs.getInt("FL_box_j");
			
				i++;
				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				XSSFCell cellA1 = row1.createCell((int) 0); //
				cellA1.setCellValue(k - 2); //
								
				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("distillery")); 
				
				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getInt("CL_box_a"));
				
				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getDouble("CL_bl_a"));
				
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("FL_box_a"));
				
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("FL_bl_a"));
				
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("CL_box_b"));
				
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("CL_bl_b"));
				
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getString("FL_box_b")); 
				
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getDouble("FL_bl_b"));
				
				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getString("CL_box_c"));
				
				
				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(rs.getDouble("CL_bl_c"));
				
				XSSFCell cellM1 = row1.createCell((int) 12);
				cellM1.setCellValue(rs.getString("FL_box_c"));
				
				XSSFCell cellN1 = row1.createCell((int) 13);
				cellN1.setCellValue(rs.getDouble("FL_bl_c"));
				
				XSSFCell cellO1 = row1.createCell((int) 14);
				cellO1.setCellValue(rs.getDouble("CL_box_d"));
				
				XSSFCell cellP1 = row1.createCell((int) 15);
				cellP1.setCellValue(rs.getDouble("CL_bl_d")); 
				
				XSSFCell cellQ1 = row1.createCell((int) 16);
				cellQ1.setCellValue(rs.getString("FL_box_d"));
				
				XSSFCell cellR1 = row1.createCell((int) 17);
				cellR1.setCellValue(rs.getString("FL_bl_d"));
				
				
				XSSFCell cellS1 = row1.createCell((int) 18);
				cellS1.setCellValue(rs.getString("CL_box_e"));
				
				XSSFCell cellT1 = row1.createCell((int) 19);
				cellT1.setCellValue(rs.getDouble("CL_bl_e"));
				
				XSSFCell cellU1 = row1.createCell((int) 20);
				cellU1.setCellValue(rs.getString("FL_box_e"));
				
				XSSFCell cellV1 = row1.createCell((int) 21);
				cellV1.setCellValue(rs.getDouble("FL_bl_e"));
				
				XSSFCell cellW1 = row1.createCell((int) 22);
				cellW1.setCellValue(rs.getString("CL_box_f")); 
				
				XSSFCell cellX1 = row1.createCell((int) 23);
				cellX1.setCellValue(rs.getDouble("CL_bl_f"));
				
				XSSFCell cellY1 = row1.createCell((int) 24);
				cellY1.setCellValue(rs.getString("FL_box_f"));
				
				
				XSSFCell cellZ1 = row1.createCell((int) 25);
				cellZ1.setCellValue(rs.getDouble("FL_bl_f"));
				
				XSSFCell cellZ2 = row1.createCell((int) 26);
				cellZ2.setCellValue(rs.getString("CL_box_g"));
				
				XSSFCell cellZ3 = row1.createCell((int) 27);
				cellZ3.setCellValue(rs.getDouble("CL_bl_g"));
				
				XSSFCell cellZ4 = row1.createCell((int) 28);
				cellZ4.setCellValue(rs.getDouble("FL_box_g"));
				
				XSSFCell cellZ5 = row1.createCell((int) 29);
				cellZ5.setCellValue(rs.getDouble("FL_bl_g")); 
				
				XSSFCell cellZ6 = row1.createCell((int) 30);
				cellZ6.setCellValue(rs.getString("CL_box_h"));
				
				XSSFCell cellZ7 = row1.createCell((int) 31);
				cellZ7.setCellValue(rs.getDouble("CL_bl_h"));
				
				
				XSSFCell cellZ8 = row1.createCell((int) 32);
				cellZ8.setCellValue(rs.getString("FL_box_h"));
				
				XSSFCell cellZ9 = row1.createCell((int) 33);
				cellZ9.setCellValue(rs.getDouble("FL_bl_h"));
				
				XSSFCell cellZ10 = row1.createCell((int) 34);
				cellZ10.setCellValue(rs.getString("CL_box_i"));
				
				XSSFCell cellZ11 = row1.createCell((int) 35);
				cellZ11.setCellValue(rs.getDouble("CL_bl_i"));
				
				XSSFCell cellZ12 = row1.createCell((int) 36);
				cellZ12.setCellValue(rs.getString("FL_box_i")); 
				
				XSSFCell cellZ13 = row1.createCell((int) 37);
				cellZ13.setCellValue(rs.getDouble("FL_bl_i"));
				
				XSSFCell cellZ14 = row1.createCell((int) 38);
				cellZ14.setCellValue(rs.getString("CL_box_j"));
				
				
				XSSFCell cellZ15 = row1.createCell((int) 39);
				cellZ15.setCellValue(rs.getDouble("CL_bl_j"));
				
				XSSFCell cellZ16 = row1.createCell((int) 40);
				cellZ16.setCellValue(rs.getString("FL_box_j"));
				
				XSSFCell cellZ17 = row1.createCell((int) 41);
				cellZ17.setCellValue(rs.getDouble("FL_bl_j"));
				
				
		
			//System.out.println("------------ "+Math.round(cases_of_last_year_sale));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n+ "_DISTILLER_BREWERY_Production_report_distillery.xls");

			act.setExlname(n+"_DISTILLER_BREWERY_Production_report_distillery.xls");
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			

			XSSFCell cellA2 = row1.createCell((int) 0);
			cellA2.setCellValue(" "); 
			cellA2.setCellStyle(cellStyle);
			
			XSSFCell cellA3 = row1.createCell((int) 1); 
			cellA3.setCellValue("TOTAL:- "); 
			cellA3.setCellStyle(cellStyle); 
			
			XSSFCell cellA4 = row1.createCell((int) 2); 
			cellA4.setCellValue(cltotal_box1); 
			cellA4.setCellStyle(cellStyle); 
			

			XSSFCell cellA5 = row1.createCell((int) 3); 
			cellA5.setCellValue(cltotal_bl1); 
			cellA5.setCellStyle(cellStyle); 
			
			XSSFCell cellA6 = row1.createCell((int) 4);
			cellA6.setCellValue(total_box1);
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 5);
			cellA7.setCellValue(total_bl1);
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 6);
			cellA8.setCellValue(cltotal_box2); 
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 7);
			cellA9.setCellValue(cltotal_bl2); 
			cellA9.setCellStyle(cellStyle);
			
			XSSFCell cellA10 = row1.createCell((int) 8); 
			cellA10.setCellValue(total_box2); 
			cellA10.setCellStyle(cellStyle); 
			
			XSSFCell cellA11 = row1.createCell((int) 9); 
			cellA11.setCellValue(total_bl2); 
			cellA11.setCellStyle(cellStyle); 
			

			XSSFCell cellA12 = row1.createCell((int) 10); 
			cellA12.setCellValue(cltotal_box3); 
			cellA12.setCellStyle(cellStyle); 
			
			XSSFCell cellA13 = row1.createCell((int) 11);
			cellA13.setCellValue(cltotal_bl3);
			cellA13.setCellStyle(cellStyle);
			
			XSSFCell cellA14 = row1.createCell((int) 12);
			cellA14.setCellValue(total_box3);
			cellA14.setCellStyle(cellStyle);
			
			XSSFCell cellA15 = row1.createCell((int) 13);
			cellA15.setCellValue(total_bl3); 
			cellA15.setCellStyle(cellStyle);
			
			XSSFCell cellA16 = row1.createCell((int) 14);
			cellA16.setCellValue(cltotal_box4); 
			cellA16.setCellStyle(cellStyle);
			
			XSSFCell cellA17 = row1.createCell((int) 15); 
			cellA17.setCellValue(cltotal_bl4); 
			cellA17.setCellStyle(cellStyle); 
			
			XSSFCell cellA18 = row1.createCell((int) 16); 
			cellA18.setCellValue(total_box4); 
			cellA18.setCellStyle(cellStyle); 
			

			XSSFCell cellA19 = row1.createCell((int) 17); 
			cellA19.setCellValue(total_bl4); 
			cellA19.setCellStyle(cellStyle); 
			
			XSSFCell cellA20 = row1.createCell((int) 18);
			cellA20.setCellValue(cltotal_box5);
			cellA20.setCellStyle(cellStyle);
			
			XSSFCell cellA21 = row1.createCell((int) 19);
			cellA21.setCellValue(cltotal_bl5);
			cellA21.setCellStyle(cellStyle);
			
			XSSFCell cellA22 = row1.createCell((int) 20);
			cellA22.setCellValue(total_box5); 
			cellA22.setCellStyle(cellStyle);
			
			XSSFCell cellA23 = row1.createCell((int) 21);
			cellA23.setCellValue(total_bl5); 
			cellA23.setCellStyle(cellStyle);
			
			XSSFCell cellA24 = row1.createCell((int) 22); 
			cellA24.setCellValue(cltotal_box6); 
			cellA24.setCellStyle(cellStyle); 
			
			XSSFCell cellA25 = row1.createCell((int) 23); 
			cellA25.setCellValue(cltotal_bl6); 
			cellA25.setCellStyle(cellStyle); 
			

			XSSFCell cellA26 = row1.createCell((int) 24); 
			cellA26.setCellValue(total_box6); 
			cellA26.setCellStyle(cellStyle); 
			
			XSSFCell cellA27 = row1.createCell((int) 25);
			cellA27.setCellValue(total_bl6);
			cellA27.setCellStyle(cellStyle);
			
			XSSFCell cellA28 = row1.createCell((int) 26);
			cellA28.setCellValue(cltotal_box7);
			cellA28.setCellStyle(cellStyle);
			
			XSSFCell cellA29 = row1.createCell((int) 27);
			cellA29.setCellValue(cltotal_bl7); 
			cellA29.setCellStyle(cellStyle);
			
			XSSFCell cellA30 = row1.createCell((int) 28);
			cellA30.setCellValue(total_box7); 
			cellA30.setCellStyle(cellStyle);
			
			XSSFCell cellA31 = row1.createCell((int) 29); 
			cellA31.setCellValue(total_bl7); 
			cellA31.setCellStyle(cellStyle); 
			
			XSSFCell cellA32 = row1.createCell((int) 30); 
			cellA32.setCellValue(cltotal_box8); 
			cellA32.setCellStyle(cellStyle); 
			

			XSSFCell cellA33 = row1.createCell((int) 31); 
			cellA33.setCellValue(cltotal_bl8); 
			cellA33.setCellStyle(cellStyle); 
			
			XSSFCell cellA34 = row1.createCell((int) 32);
			cellA34.setCellValue(total_box8);
			cellA34.setCellStyle(cellStyle);
			
			XSSFCell cellA35 = row1.createCell((int) 33);
			cellA35.setCellValue(total_bl8);
			cellA35.setCellStyle(cellStyle);
			
			XSSFCell cellA36 = row1.createCell((int) 34);
			cellA36.setCellValue(cltotal_box9); 
			cellA36.setCellStyle(cellStyle);
			
			XSSFCell cellA37 = row1.createCell((int) 35);
			cellA37.setCellValue(cltotal_bl9); 
			cellA37.setCellStyle(cellStyle);
			
			XSSFCell cellA38 = row1.createCell((int) 36); 
			cellA38.setCellValue(total_box9); 
			cellA38.setCellStyle(cellStyle); 
			
			XSSFCell cellA39 = row1.createCell((int) 37); 
			cellA39.setCellValue(total_box9); 
			cellA39.setCellStyle(cellStyle); 
			

			XSSFCell cellA40 = row1.createCell((int) 38); 
			cellA40.setCellValue(cltotal_box10); 
			cellA40.setCellStyle(cellStyle); 
			
			XSSFCell cellA41 = row1.createCell((int) 39);
			cellA41.setCellValue(cltotal_bl10);
			cellA41.setCellStyle(cellStyle);
			
			XSSFCell cellA42 = row1.createCell((int) 40);
			cellA42.setCellValue(total_box10);
			cellA42.setCellStyle(cellStyle);
			
			XSSFCell cellA43 = row1.createCell((int) 41);
			cellA43.setCellValue(total_bl10); 
			cellA43.setCellStyle(cellStyle);
			    
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			act.setExcelFlag(true);
			
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

    }
 
	
	
	//----------------------------------------------------
	
	
	

	public void printExcel_Brewery(Distillery_BrewaryProductionReportAction act){


		Connection con = null;
	
	    double total_bl1 = 0.0;	
	    double total_box1 = 0.0;
	    double total_bl2 = 0.0;	
	    double total_box2 = 0.0;
	    double total_bl3 = 0.0;	
	    double total_box3 = 0.0;
	    double total_bl4 = 0.0;	
	    double total_box4 = 0.0;
	    double total_bl5 = 0.0;	
	    double total_box5 = 0.0;
	    double total_bl6 = 0.0;	
	    double total_box6 = 0.0;
	    double total_bl7 = 0.0;	
	    double total_box7 = 0.0;
	    double total_bl8 = 0.0;	
	    double total_box8 = 0.0;
	    double total_bl9 = 0.0;	
	    double total_box9 = 0.0;
	    double total_bl10 = 0.0;	
	    double total_box10 = 0.0;
		String type="";
		String reportQuery="";
		
		Date m1 =Utility.convertUtilDateToSQLDate(act.getProduction_dt());
		Calendar cal =Calendar.getInstance();
		cal.setTime(m1);
		cal.add(Calendar.DATE,-1);
		m1 =cal.getTime();
		System.out.println(m1);
		Date m2 =Utility.convertUtilDateToSQLDate(m1);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m2);
		cal.add(Calendar.DATE,-1);
		m2 =cal.getTime();
		System.out.println(m2);
		Date m3 =Utility.convertUtilDateToSQLDate(m2);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m3);
		cal.add(Calendar.DATE,-1);
		m3 =cal.getTime();
		System.out.println(m3);
		Date m4 =Utility.convertUtilDateToSQLDate(m3);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m4);
		cal.add(Calendar.DATE,-1);
		m4 =cal.getTime();
		System.out.println(m4);
		Date m5 =Utility.convertUtilDateToSQLDate(m4);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m5);
		cal.add(Calendar.DATE,-1);
		m5 =cal.getTime();
		System.out.println(m5);
		Date m6 =Utility.convertUtilDateToSQLDate(m5);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m6);
		cal.add(Calendar.DATE,-1);
		m6 =cal.getTime();
		System.out.println(m6);
		Date m7 =Utility.convertUtilDateToSQLDate(m6);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m7);
		cal.add(Calendar.DATE,-1);
		m7 =cal.getTime();
		System.out.println(m7);
		Date m8 =Utility.convertUtilDateToSQLDate(m7);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m8);
		cal.add(Calendar.DATE,-1);
		m8 =cal.getTime();
		System.out.println(m8);
		Date m9 =Utility.convertUtilDateToSQLDate(m8);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m9);
		cal.add(Calendar.DATE,-1);
		m9 =cal.getTime();
		System.out.println(m9);
		Date m10 =Utility.convertUtilDateToSQLDate(m9);
		//Calendar cal =Calendar.getInstance();
		cal.setTime(m10);
		cal.add(Calendar.DATE,-1);
		m10 =cal.getTime();
		System.out.println(m10);
		
			
		/*	if(act.getRadioType().equalsIgnoreCase("DL")){
				
				type = "Distillery";
				
				reportQuery = "	select a.distillery_id,(select vch_undertaking_name from dis_mst_pd1_pd2_lic where int_app_id_f=a.distillery_id) as distillery,a.CL_box,a.CL_bl,a.FL_box,a.FL_bl, "+
						 " b.CL_box,b.CL_bl,b.FL_box,b.FL_bl, "+
						 " c.CL_box,c.CL_bl,c.FL_box,c.FL_bl, "+
						 " d.CL_box,d.CL_bl,d.FL_box,d.FL_bl, "+
						 " e.CL_box,e.CL_bl,e.FL_box,e.FL_bl, "+
						 " f.CL_box,f.CL_bl,f.FL_box,f.FL_bl,  "+
						 " g.CL_box,g.CL_bl,g.FL_box,g.FL_bl,  "+
						 " h.CL_box,h.CL_bl,h.FL_box,h.FL_bl,  "+
						 " i.CL_box,i.CL_bl,i.FL_box,i.FL_bl,  "+
						 " j.CL_box,j.CL_bl,j.FL_box,j.FL_bl from  "+ 
						 " (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_19_20 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m1)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m1)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)a, "+
						 "   (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_19_20 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m2)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m2)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)b, "+
						 "  (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_19_20 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m3)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m3)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)c,  "+
						 "  (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_19_20 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m4)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m4)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)d, "+
						"  (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_19_20 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m5)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m5)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)e, "+
						" (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_19_20 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m6)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m6)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)f,  "+
						" (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_19_20 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m7)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m7)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)g,  "+
						" (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_19_20 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m8)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m8)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)h,  "+
						" (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_19_20 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m9)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m9)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)i,  "+
						" (select a.distillery_id,COALESCE(x1.CL_box,0) as CL_box,COALESCE(x1.CL_BL,0) as CL_BL,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct distillery_id from distillery.brand_registration_19_20 where distillery_id>0)a left outer join (select distillery_id,sum(CL_box) as CL_box,sum(CL_BL) as CL_BL,sum(FL_box) as FL_box,sum(FL_bl) as FL_bl from (select distillery_id,sum(box) as CL_box,sum(bl) as CL_BL,0 as fl_box,0 as fl_bl  from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m10)+"' and bottling_under='CL' group by distillery_id,size_ml)x group by distillery_id union select distillery_id, 0 as CL_box,0 as CL_BL,sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from distillery.daily_bottling_stock_19_20 where date='"+Utility.convertUtilDateToSQLDate(m10)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id)y group by distillery_id)x1 on a.distillery_id=x1.distillery_id)j   "+
						"  where a.distillery_id=b.distillery_id and a.distillery_id=c.distillery_id and a.distillery_id=d.distillery_id and a.distillery_id=e.distillery_id and a.distillery_id=f.distillery_id and  "+
						"  a.distillery_id=g.distillery_id and a.distillery_id=h.distillery_id and a.distillery_id=i.distillery_id and a.distillery_id=j.distillery_id ";
						 
			}
			else*/ if(act.getRadioType().equalsIgnoreCase("BR")){
				
				type = "Brewery";
				
				reportQuery ="  select a.brewery_id,(select brewery_nm from public.bre_mst_b1_lic where vch_app_id_f=a.brewery_id) as distillery," +
						     "a.FL_box as FL_box_a,a.FL_bl as FL_bl_a,"+
						     " b.FL_box as FL_box_b,b.FL_bl as FL_bl_b," +
						     " c.FL_box as FL_box_c,c.FL_bl as FL_bl_c, " +
						     "d.FL_box as FL_box_d,d.FL_bl as FL_bl_d, " +
						     "e.FL_box as FL_box_e,e.FL_bl as FL_bl_e,   "+
						     "   f.FL_box as FL_box_f,f.FL_bl as FL_bl_f," +
						     " g.FL_box as FL_box_g,g.FL_bl as FL_bl_g, " +
						     "h.FL_box as FL_box_h,h.FL_bl as FL_bl_h, " +
						     "i.FL_box as FL_box_i,i.FL_bl as FL_bl_i, " +
						     "j.FL_box as FL_box_j,j.FL_bl as FL_bl_j from  "+
						     " (select a.brewery_id,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct brewery_id from distillery.brand_registration_20_21 where brewery_id>0)a left outer join (select distillery_id, sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from bwfl.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id )x1 on a.brewery_id=x1.distillery_id)a, "+
						     " (select a.brewery_id,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct brewery_id from distillery.brand_registration_20_21 where brewery_id>0)a left outer join (select distillery_id, sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from bwfl.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m1)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id )x1 on a.brewery_id=x1.distillery_id)b, "+
						     " (select a.brewery_id,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct brewery_id from distillery.brand_registration_20_21 where brewery_id>0)a left outer join (select distillery_id, sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from bwfl.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m2)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id )x1 on a.brewery_id=x1.distillery_id)c, "+
						     " (select a.brewery_id,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct brewery_id from distillery.brand_registration_20_21 where brewery_id>0)a left outer join (select distillery_id, sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from bwfl.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m3)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id )x1 on a.brewery_id=x1.distillery_id)d, "+
						     " (select a.brewery_id,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct brewery_id from distillery.brand_registration_20_21 where brewery_id>0)a left outer join (select distillery_id, sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from bwfl.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m4)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id )x1 on a.brewery_id=x1.distillery_id)e, "+
						     " (select a.brewery_id,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct brewery_id from distillery.brand_registration_20_21 where brewery_id>0)a left outer join (select distillery_id, sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from bwfl.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m5)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id )x1 on a.brewery_id=x1.distillery_id)f, "+
						     " (select a.brewery_id,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct brewery_id from distillery.brand_registration_20_21 where brewery_id>0)a left outer join (select distillery_id, sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from bwfl.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m6)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id )x1 on a.brewery_id=x1.distillery_id)g, "+
						     " (select a.brewery_id,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct brewery_id from distillery.brand_registration_20_21 where brewery_id>0)a left outer join (select distillery_id, sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from bwfl.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m7)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id )x1 on a.brewery_id=x1.distillery_id)h, "+
						     " (select a.brewery_id,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct brewery_id from distillery.brand_registration_20_21 where brewery_id>0)a left outer join (select distillery_id, sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from bwfl.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m8)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id )x1 on a.brewery_id=x1.distillery_id)i, "+
						     " (select a.brewery_id,COALESCE(x1.FL_box,0) as FL_box,COALESCE(x1.FL_bl,0) as FL_bl from (select distinct brewery_id from distillery.brand_registration_20_21 where brewery_id>0)a left outer join (select distillery_id, sum(box) as FL_box,sum(bl) as FL_bl from (select distillery_id,size_ml,sum(bottling_no_of_bottle) as bottle,sum(bottling_no_of_box) as box,(sum(bottling_no_of_bottle)*200)/1000 as bl  from bwfl.daily_bottling_stock_20_21 where date='"+Utility.convertUtilDateToSQLDate(m9)+"' and bottling_under in ('FL3','FL3A')  group by distillery_id,size_ml)y  group by distillery_id )x1 on a.brewery_id=x1.distillery_id)j  "+
						     " where a.brewery_id=b.brewery_id and a.brewery_id=c.brewery_id and a.brewery_id=d.brewery_id and a.brewery_id=e.brewery_id and a.brewery_id=f.brewery_id and  "+
						     " a.brewery_id=g.brewery_id and a.brewery_id=h.brewery_id and a.brewery_id=i.brewery_id and a.brewery_id=j.brewery_id order by distillery";
			}
				
			else {
				
				
			}
		System.out.println("excel query===  " + reportQuery);

		
		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long start = 0;
		long end = 0;
		boolean flag = false;
		long k = 0;
		

		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(reportQuery);

			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Brewery Production Report");

			worksheet.setColumnWidth(0, 2000);
			worksheet.setColumnWidth(1, 8000);
			worksheet.setColumnWidth(2, 3000);
			worksheet.setColumnWidth(3, 3000);
			worksheet.setColumnWidth(4, 3000);
			worksheet.setColumnWidth(5, 3000);
			worksheet.setColumnWidth(6, 3000);
			worksheet.setColumnWidth(7, 3000);
			worksheet.setColumnWidth(8, 3000);
			worksheet.setColumnWidth(9, 3000);
			worksheet.setColumnWidth(10, 3000);
			worksheet.setColumnWidth(11, 3000);
			worksheet.setColumnWidth(12, 3000);
			worksheet.setColumnWidth(13, 3000);
			worksheet.setColumnWidth(14, 3000);
			worksheet.setColumnWidth(15, 3000);
			worksheet.setColumnWidth(16, 3000);
			worksheet.setColumnWidth(17, 3000);
			worksheet.setColumnWidth(18, 3000);
			worksheet.setColumnWidth(19, 3000);
			worksheet.setColumnWidth(20, 3000);
			worksheet.setColumnWidth(21, 3000);
		
			
			
			
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			
			cellhead0.setCellValue("Production Report  For " +type+ " From (Date"+Utility.convertUtilDateToSQLDate(m9)+ " To " +Utility.convertUtilDateToSQLDate(act.getProduction_dt())+")") ;
			
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

			//k = k + 1;
			XSSFRow rowhead1 = worksheet.createRow((int) 1);

			XSSFCell cellhead1_1 = rowhead1.createCell((int) 0);
			cellhead1_1.setCellValue("");
			cellhead1_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead2_1 = rowhead1.createCell((int) 1);
			cellhead2_1.setCellValue("");
			cellhead2_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead3_1 = rowhead1.createCell((int) 2);
			cellhead3_1.setCellValue("Date :-");
			cellhead3_1.setCellStyle(cellStyle);

			XSSFCell cellhead4_1 = rowhead1.createCell((int) 3);
			cellhead4_1.setCellValue(""+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+ "");
			cellhead4_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead5_1 = rowhead1.createCell((int) 4);
			cellhead5_1.setCellValue("Date :-");
			cellhead5_1.setCellStyle(cellStyle);

			XSSFCell cellhead6_1 = rowhead1.createCell((int) 5);
			cellhead6_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m1)+"");
			cellhead6_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead7_1 = rowhead1.createCell((int) 6);
			cellhead7_1.setCellValue("Date :-");
			cellhead7_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead8_1 = rowhead1.createCell((int) 7);
			cellhead8_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m2)+"");
			cellhead8_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead9_1 = rowhead1.createCell((int) 8);
			cellhead9_1.setCellValue("Date :-");
			cellhead9_1.setCellStyle(cellStyle);

			XSSFCell cellhead10_1 = rowhead1.createCell((int) 9);
			cellhead10_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m3)+"");
			cellhead10_1.setCellStyle(cellStyle);

			XSSFCell cellhead11_1 = rowhead1.createCell((int) 10);
			cellhead11_1.setCellValue("Date :-");
			cellhead11_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead12_1 = rowhead1.createCell((int) 11);
			cellhead12_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m4)+"");	
			cellhead12_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead13_1 = rowhead1.createCell((int) 12);
			cellhead13_1.setCellValue("Date :-");
			cellhead13_1.setCellStyle(cellStyle);

			XSSFCell cellhead14_1 = rowhead1.createCell((int) 13);
			cellhead14_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m5)+"");
			cellhead14_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead15_1 = rowhead1.createCell((int) 14);
			cellhead15_1.setCellValue("Date :-");
			cellhead15_1.setCellStyle(cellStyle);

			XSSFCell cellhead16_1 = rowhead1.createCell((int) 15);
			cellhead16_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m6)+"");
			cellhead16_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead17_1 = rowhead1.createCell((int) 16);
			cellhead17_1.setCellValue("Date :-");
			cellhead17_1.setCellStyle(cellStyle);

			XSSFCell cellhead18_1 = rowhead1.createCell((int) 17);
			cellhead18_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m7)+"");
			cellhead18_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead19_1 = rowhead1.createCell((int) 18);
			cellhead19_1.setCellValue("Date :-");
			cellhead19_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead20_1 = rowhead1.createCell((int) 19);
			cellhead20_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m8)+"");
			cellhead20_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead21_1 = rowhead1.createCell((int) 20);
			cellhead21_1.setCellValue("Date :-");
			cellhead21_1.setCellStyle(cellStyle);
			
			XSSFCell cellhead22_1 = rowhead1.createCell((int) 21);
			cellhead22_1.setCellValue(""+Utility.convertUtilDateToSQLDate(m9)+"");
			cellhead22_1.setCellStyle(cellStyle);
			
			XSSFRow rowhead = worksheet.createRow((int) 2);

			XSSFCell cellhead1 = rowhead.createCell((int) 0);
			cellhead1.setCellValue("Sr. No");
			cellhead1.setCellStyle(cellStyle);
			
			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("Brewery Name");
			cellhead2.setCellStyle(cellStyle);
			
			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Beer Box");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Beer BL");
			cellhead4.setCellStyle(cellStyle);
			
			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Beer Box");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Beer BL");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Beer Box");
			cellhead7.setCellStyle(cellStyle);
			
			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("Beer BL");
			cellhead8.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("Beer Box");
			cellhead9.setCellStyle(cellStyle);

			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("Beer BL");
			cellhead10.setCellStyle(cellStyle);
			
			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("Beer Box");
			cellhead11.setCellStyle(cellStyle);
			
			XSSFCell cellhead12 = rowhead.createCell((int) 11);
			cellhead12.setCellValue("Beer BL");
			cellhead12.setCellStyle(cellStyle);
			
			XSSFCell cellhead13 = rowhead.createCell((int) 12);
			cellhead13.setCellValue("Beer Box");
			cellhead13.setCellStyle(cellStyle);

			XSSFCell cellhead14 = rowhead.createCell((int) 13);
			cellhead14.setCellValue("Beer BL");
			cellhead14.setCellStyle(cellStyle);
			
			XSSFCell cellhead15 = rowhead.createCell((int) 14);
			cellhead15.setCellValue("Beer Box");
			cellhead15.setCellStyle(cellStyle);

			XSSFCell cellhead16 = rowhead.createCell((int) 15);
			cellhead16.setCellValue("Beer BL");
			cellhead16.setCellStyle(cellStyle);
			
			XSSFCell cellhead17 = rowhead.createCell((int) 16);
			cellhead17.setCellValue("Beer Box");
			cellhead17.setCellStyle(cellStyle);

			XSSFCell cellhead18 = rowhead.createCell((int) 17);
			cellhead18.setCellValue("Beer BL");
			cellhead18.setCellStyle(cellStyle);
			
			XSSFCell cellhead19 = rowhead.createCell((int) 18);
			cellhead19.setCellValue("Beer Box");
			cellhead19.setCellStyle(cellStyle);
			
			XSSFCell cellhead20 = rowhead.createCell((int) 19);
			cellhead20.setCellValue("Beer BL");
			cellhead20.setCellStyle(cellStyle);
			
			XSSFCell cellhead21 = rowhead.createCell((int) 20);
			cellhead21.setCellValue("Beer Box");
			cellhead21.setCellStyle(cellStyle);
			
			XSSFCell cellhead22 = rowhead.createCell((int) 21);
			cellhead22.setCellValue("Beer BL");
			cellhead22.setCellStyle(cellStyle);
			
			
			k = k + 2;
			int i = 0;
			
			while (rs.next()) 
			{
				total_bl1 = total_bl1 + rs.getDouble("FL_bl_a");
				total_box1 = total_box1 + rs.getInt("FL_box_a");
				total_bl2 = total_bl2 + rs.getDouble("FL_bl_b");
				total_box2 = total_box2 + rs.getInt("FL_box_b");
				total_bl3 = total_bl3 + rs.getDouble("FL_bl_c");
				total_box3 = total_box3 + rs.getInt("FL_box_c");
				total_bl4 = total_bl4 + rs.getDouble("FL_bl_d");
				total_box4 = total_box4 + rs.getInt("FL_box_d");
				total_bl5 = total_bl5 + rs.getDouble("FL_bl_e");
				total_box5 = total_box5 + rs.getInt("FL_box_e");
				total_bl6 = total_bl6 + rs.getDouble("FL_bl_f");
				total_box6 = total_box6 + rs.getInt("FL_box_f");
				total_bl7 = total_bl7 + rs.getDouble("FL_bl_g");
				total_box7 = total_box7 + rs.getInt("FL_box_g");
				total_bl8 = total_bl8 + rs.getDouble("FL_bl_h");
				total_box8 = total_box8 + rs.getInt("FL_box_h");
				total_bl9 = total_bl9 + rs.getDouble("FL_bl_i");
				total_box9 = total_box9 + rs.getInt("FL_box_i");
				total_bl10 = total_bl10 + rs.getDouble("FL_bl_j");
				total_box10 = total_box10 + rs.getInt("FL_box_j");
			
				i++;
				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				XSSFCell cellA1 = row1.createCell((int) 0); //
				cellA1.setCellValue(k - 2); //
								
				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("distillery")); 
				
				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("FL_box_a"));
				
				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getDouble("FL_bl_a"));
				
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("FL_box_b"));
				
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("FL_bl_b"));
				
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("FL_box_c"));
				
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("FL_bl_c"));
				
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getString("FL_box_d")); 
				
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getDouble("FL_bl_d"));
				
				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getString("FL_box_e"));
				
			
				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(rs.getDouble("FL_bl_e"));
				
				XSSFCell cellM1 = row1.createCell((int) 12);
				cellM1.setCellValue(rs.getString("FL_box_f"));
				
				XSSFCell cellN1 = row1.createCell((int) 13);
				cellN1.setCellValue(rs.getDouble("FL_bl_f"));
				
				XSSFCell cellO1 = row1.createCell((int) 14);
				cellO1.setCellValue(rs.getDouble("FL_box_g"));
				
				XSSFCell cellP1 = row1.createCell((int) 15);
				cellP1.setCellValue(rs.getDouble("FL_bl_g")); 
				
				XSSFCell cellQ1 = row1.createCell((int) 16);
				cellQ1.setCellValue(rs.getString("FL_box_h"));
				
				XSSFCell cellR1 = row1.createCell((int) 17);
				cellR1.setCellValue(rs.getString("FL_bl_h"));
				
				
				XSSFCell cellS1 = row1.createCell((int) 18);
				cellS1.setCellValue(rs.getString("FL_box_i"));
				
				XSSFCell cellT1 = row1.createCell((int) 19);
				cellT1.setCellValue(rs.getDouble("FL_bl_i"));
				
				XSSFCell cellU1 = row1.createCell((int) 20);
				cellU1.setCellValue(rs.getString("FL_box_j"));
				
				XSSFCell cellV1 = row1.createCell((int) 21);
				cellV1.setCellValue(rs.getDouble("FL_bl_j"));
				
				
			//System.out.println("------------ "+Math.round(cases_of_last_year_sale));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n+ "_DISTILLER_BREWERY_Production_report_Brewery.xls");

			act.setExlname(n+"_DISTILLER_BREWERY_Production_report_Brewery.xls");
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			

			XSSFCell cellA2 = row1.createCell((int) 0);
			cellA2.setCellValue(" "); 
			cellA2.setCellStyle(cellStyle);
			
			XSSFCell cellA3 = row1.createCell((int) 1); 
			cellA3.setCellValue("TOTAL:-"); 
			cellA3.setCellStyle(cellStyle); 
			
			XSSFCell cellA4 = row1.createCell((int) 2); 
			cellA4.setCellValue(total_box1); 
			cellA4.setCellStyle(cellStyle); 
			

			XSSFCell cellA5 = row1.createCell((int) 3); 
			cellA5.setCellValue(total_bl1); 
			cellA5.setCellStyle(cellStyle); 
			
			XSSFCell cellA6 = row1.createCell((int) 4);
			cellA6.setCellValue(total_box2);
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 5);
			cellA7.setCellValue(total_bl2);
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 6);
			cellA8.setCellValue(total_box3); 
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 7);
			cellA9.setCellValue(total_bl3); 
			cellA9.setCellStyle(cellStyle);
			
			XSSFCell cellA10 = row1.createCell((int) 8); 
			cellA10.setCellValue(total_box4); 
			cellA10.setCellStyle(cellStyle); 
			
			XSSFCell cellA11 = row1.createCell((int) 9);
			cellA11.setCellValue(total_bl4);
			cellA11.setCellStyle(cellStyle);
			
			XSSFCell cellA12 = row1.createCell((int) 10);
			cellA12.setCellValue(total_box5);
			cellA12.setCellStyle(cellStyle);
			
			XSSFCell cellA13 = row1.createCell((int) 11);
			cellA13.setCellValue(total_bl5); 
			cellA13.setCellStyle(cellStyle);
			
			XSSFCell cellA14 = row1.createCell((int) 12);
			cellA14.setCellValue(total_box6); 
			cellA14.setCellStyle(cellStyle);
			
			XSSFCell cellA15 = row1.createCell((int) 13);
			cellA15.setCellValue(total_bl6);
			cellA15.setCellStyle(cellStyle);
			
			XSSFCell cellA16 = row1.createCell((int) 14);
			cellA16.setCellValue(total_box7);
			cellA16.setCellStyle(cellStyle);
			
			XSSFCell cellA17 = row1.createCell((int) 15);
			cellA17.setCellValue(total_bl7); 
			cellA17.setCellStyle(cellStyle);
			
			XSSFCell cellA18 = row1.createCell((int) 16);
			cellA18.setCellValue(total_box8); 
			cellA18.setCellStyle(cellStyle);
			
			
			XSSFCell cellA19 = row1.createCell((int) 17);
			cellA19.setCellValue(total_bl8); 
			cellA19.setCellStyle(cellStyle);
			
			XSSFCell cellA20 = row1.createCell((int) 18);
			cellA20.setCellValue(total_box9); 
			cellA20.setCellStyle(cellStyle);
			
			XSSFCell cellA21 = row1.createCell((int) 19);
			cellA21.setCellValue(total_bl9); 
			cellA21.setCellStyle(cellStyle);
			
			XSSFCell cellA22 = row1.createCell((int) 20);
			cellA22.setCellValue(total_box10); 
			cellA22.setCellStyle(cellStyle);
			
			XSSFCell cellA23 = row1.createCell((int) 21);
			cellA23.setCellValue(total_bl10); 
			cellA23.setCellStyle(cellStyle);
		    
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			act.setExcelFlag(true);
			
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

    }
	
}
