package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import com.mentor.action.Currnt_Cumulativ_TillNow_ReportAction;
import com.mentor.action.Currnt_Cumulativ_TillNow_ReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class Currnt_Cumulativ_TillNow_ReportImpl {
	
	
	//====================get month list================
	
	
	public ArrayList getMonthList(Currnt_Cumulativ_TillNow_ReportAction act)
	{

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue("");
		list.add(item);
		try {
			String query = " SELECT month_id, description FROM public.month_master ORDER BY month_id ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			
			//System.out.println("query-------------"+query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("month_id"));
				item.setLabel(rs.getString("description"));

				list.add(item);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;

	}

	// =======================print report=================================

	public void printReport(Currnt_Cumulativ_TillNow_ReportAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String year = act.getYear();
		//System.out.println(" print Report ");

		try {
			con = ConnectionToDataBase.getConnection();
			
			
			int index = Integer.parseInt(act.getMonthID());
	        
	        String s1, s2, s3, s4;
	        
	        Calendar c = Calendar.getInstance();    
	        c.set(Calendar.MONTH, ((index-1)-1));	        
	        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
	        
	        c.set(Calendar.MONTH, ((index-1)));	
	        int days1 = c.getActualMaximum(Calendar.DAY_OF_MONTH);
	        
	        s1 = "2019/"+(index-1)+"/1";
	        s2 = "2019/"+(index-1)+"/"+days;
	        
	        s3 = "2019/"+(index)+"/1";
	        s4 = "2019/"+(index)+"/"+days1;
	        
	        
			//System.out.println("reportQuery----------" + reportQuery);

	        
	        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	        
	      
	          //  System.out.println("previous month first date----------"+formatter.parse(s1));
	         //   System.out.println("previous month last date----------"+formatter.parse(s2));
	            
	            //System.out.println("current month first date----------"+formatter.parse(s3));
	          //  System.out.println("current month last date----------"+formatter.parse(s4));
	            
	            
	            
	        reportQuery = 	" SELECT y.group_type, y.app_id, y.dist_name,                                                                              "+
							" SUM(y.dispatchd_bottl_cumltv) as dispatchd_bottl_cumltv, SUM(y.dispatchd_box_cumltv) as dispatchd_box_cumltv,            "+
							" round(CAST(float8(SUM(y.sale_cumltv)/100000)  as numeric), 2) as sale_cumltv,                                            "+
							" round(CAST(float8(SUM(y.duty_cumltv)/10000000)  as numeric), 2) as duty_cumltv,                                          "+
							" SUM(y.dispatchd_bottl_curnt) as dispatchd_bottl_curnt, SUM(y.dispatchd_box_curnt) as dispatchd_box_curnt,                "+
							" round(CAST(float8(SUM(y.sale_curnt)/100000)  as numeric), 2) as sale_curnt,                                              "+
							" round(CAST(float8(SUM(y.duty_curnt)/10000000)  as numeric), 2)  as duty_curnt                                            "+
							" FROM                                                                                                                     "+
							" (SELECT m.vch_undertaking_name||'(DISTILLERY)' as dist_name, x.*                                                         "+
							" FROM                                                                                                                     "+
							" (SELECT a.int_dissleri_id as app_id, 'Distillery CL' as group_type,                                                      "+
							" SUM(a.dispatchd_bottl) as dispatchd_bottl_cumltv, SUM(a.dispatchd_box)as dispatchd_box_cumltv,                           "+
							" SUM((a.duty)+(a.addduty)) as duty_cumltv,                                                                                "+
							" SUM(round(CAST(float8(((a.dispatchd_bottl)*j.quantity)/1000) as numeric), 2)) as sale_cumltv,                            "+
							" 0 as dispatchd_bottl_curnt, 0 as dispatchd_box_curnt, 0 as duty_curnt, 0 as sale_curnt                                   "+
							" FROM distillery.cl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j,                                            "+
							" distillery.gatepass_to_manufacturewholesale_cl_"+year+" z                                                                   "+
							" WHERE a.dt_date  BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                    "+
							" AND a.int_pckg_id=j.package_id                                                                                           "+
							" AND z.int_dist_id=a.int_dissleri_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt_date                      "+
							" GROUP BY a.int_dissleri_id                                                                                               "+
							" UNION                                                                                                                    "+
							" SELECT a.int_dissleri_id as app_id, 'Distillery CL' as group_type,                                                       "+
							" 0 as dispatchd_bottl_cumltv, 0 as dispatchd_box_cumltv, 0 as duty_cumltv, 0 as sale_cumltv,                              "+
							" SUM(a.dispatchd_bottl) as dispatchd_bottl_curnt, SUM(a.dispatchd_box)as dispatchd_box_curnt,                             "+
							" SUM((a.duty)+(a.addduty)) as duty_curnt,                                                                                 "+
							" SUM(round(CAST(float8(((a.dispatchd_bottl)*j.quantity)/1000) as numeric), 2)) as sale_curnt                              "+
							" FROM distillery.cl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j,                                            "+
							" distillery.gatepass_to_manufacturewholesale_cl_"+year+" z                                                                   "+
							" WHERE a.dt_date  BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'                                     "+
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"' AND a.int_pckg_id=j.package_id                           "+
							" AND z.int_dist_id=a.int_dissleri_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt_date                      "+
							" GROUP BY a.int_dissleri_id)x, public.dis_mst_pd1_pd2_lic m                                                               "+
							" WHERE x.app_id=m.int_app_id_f                                                                                            "+
							"                                                                                                                          "+
							" union                                                                                                                    "+
							"                                                                                                                          "+
							" SELECT m.vch_undertaking_name||'(DISTILLERY)' as dist_name, x.*                                                          "+
							" FROM                                                                                                                     "+
							" (SELECT a.int_dissleri_id as app_id, 'Distillery FL' as group_type,                                                      "+
							" SUM(a.dispatch_bottle) as dispatchd_bottl_cumltv, SUM(a.dispatch_box)as dispatchd_box_cumltv,                            "+
							" 0 as duty_cumltv,                                                                                                        "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale_cumltv,                            "+
							" 0 as dispatchd_bottl_curnt, 0 as dispatchd_box_curnt, 0 as duty_curnt, 0 as sale_curnt                                   "+
							" FROM distillery.fl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j,                                            "+
							" distillery.gatepass_to_districtwholesale_"+year+" z                                                                         "+
							" WHERE a.dt  BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                         "+
							" AND a.int_pckg_id=j.package_id                                                                                           "+
							" AND z.int_dist_id=a.int_dissleri_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt                           "+
							" GROUP BY a.int_dissleri_id                                                                                               "+
							" UNION                                                                                                                    "+
							" SELECT a.int_dissleri_id as app_id, 'Distillery FL' as group_type,                                                       "+
							" 0 as dispatchd_bottl_cumltv, 0 as dispatchd_box_cumltv,                                                                  "+
							" SUM((a.duty)+(a.addduty)) as duty_cumltv, 0 as sale_cumltv,                                                              "+
							" 0 as dispatchd_bottl_curnt, 0 as dispatchd_box_curnt, 0 as duty_curnt, 0 as sale_curnt                                   "+
							" FROM distillery.fl1_stock_trxn_"+year+" a, distillery.gatepass_to_manufacturewholesale_"+year+" z                              "+
							" WHERE z.dt_date  BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                    "+
							" AND z.int_dist_id=a.int_dissleri_id AND z.vch_gatepass_no=a.vch_gatepass_no                                              "+
							" GROUP BY a.int_dissleri_id                                                                                               "+
							" UNION                                                                                                                    "+
							" SELECT a.int_dissleri_id as app_id, 'Distillery FL' as group_type,                                                       "+
							" 0 as dispatchd_bottl_cumltv, 0 as dispatchd_box_cumltv, 0 as duty_cumltv, 0 as sale_cumltv,                              "+
							" SUM(a.dispatch_bottle) as dispatchd_bottl_curnt, SUM(a.dispatch_box) as dispatchd_box_curnt, 0 as duty_curnt,            "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale_curnt                              "+
							" FROM distillery.fl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j,                                            "+
							" distillery.gatepass_to_districtwholesale_"+year+" z                                                                         "+
							" WHERE a.dt  BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'                                          "+
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"' AND a.int_pckg_id=j.package_id                           "+
							" AND z.int_dist_id=a.int_dissleri_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt                           "+
							" GROUP BY a.int_dissleri_id                                                                                               "+
							" UNION                                                                                                                    "+
							" SELECT a.int_dissleri_id as app_id, 'Distillery FL' as group_type,                                                       "+
							" 0 as dispatchd_bottl_cumltv, 0 as dispatchd_box_cumltv, 0 as duty_cumltv, 0 as sale_cumltv,                              "+
							" 0 as dispatchd_bottl_curnt, 0 as dispatchd_box_curnt, SUM((a.duty)+(a.addduty)) as duty_curnt, 0 as sale_curnt           "+
							" FROM distillery.fl1_stock_trxn_"+year+" a, distillery.gatepass_to_manufacturewholesale_"+year+" z                              "+
							" WHERE z.dt_date  BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'                                     "+
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                                                          "+
							" AND z.int_dist_id=a.int_dissleri_id AND z.vch_gatepass_no=a.vch_gatepass_no                                              "+
							" GROUP BY a.int_dissleri_id)x, public.dis_mst_pd1_pd2_lic m                                                               "+
							" WHERE x.app_id=m.int_app_id_f                                                                                            "+
							"                                                                                                                          "+
							" union                                                                                                                    "+
							"                                                                                                                          "+
							" SELECT m.brewery_nm||'(BREWERY)' as dist_name, x.*                                                                       "+
							" FROM                                                                                                                     "+
							" (SELECT a.brewery_id as app_id, 'BEER' as group_type,                                                                    "+
							" SUM(a.dispatch_bottle) as dispatchd_bottl_cumltv, SUM(a.dispatch_box)as dispatchd_box_cumltv,                            "+
							" 0 as duty_cumltv,                                                                                                        "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale_cumltv,                            "+
							" 0 as dispatchd_bottl_curnt, 0 as dispatchd_box_curnt, 0 as duty_curnt, 0 as sale_curnt                                   "+
							" FROM bwfl.fl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j,                                                  "+
							" bwfl.gatepass_to_manufacturewholesale_"+year+" z                                                                            "+
							" WHERE a.dt  BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                         "+
							" AND a.int_pckg_id=j.package_id                                                                                           "+
							" AND z.int_brewery_id=a.brewery_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt                             "+
							" GROUP BY a.brewery_id                                                                                                    "+
							" UNION                                                                                                                    "+
							" SELECT a.int_brewery_id as app_id, 'BEER' as group_type,                                                                 "+
							" 0 as dispatchd_bottl_cumltv, 0 as dispatchd_box_cumltv,                                                                  "+
							" SUM((a.duty)+(a.addduty)) as duty_cumltv, 0 as sale_cumltv,                                                              "+
							" 0 as dispatchd_bottl_curnt, 0 as dispatchd_box_curnt, 0 as duty_curnt, 0 as sale_curnt                                   "+
							" FROM bwfl.fl1_stock_trxn_"+year+" a, bwfl.gatepass_to_manufacturewholesale_"+year+" z                                          "+
							" WHERE z.dt_date  BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                    "+
							" AND z.int_brewery_id=a.int_brewery_id AND z.vch_gatepass_no=a.vch_gatepass_no                                            "+
							" GROUP BY a.int_brewery_id                                                                                                "+
							" UNION                                                                                                                    "+
							" SELECT a.brewery_id as app_id, 'BEER' as group_type,                                                                     "+
							" 0 as dispatchd_bottl_cumltv, 0 as dispatchd_box_cumltv, 0 as duty_cumltv, 0 as sale_cumltv,                              "+
							" SUM(a.dispatch_bottle) as dispatchd_bottl_curnt, SUM(a.dispatch_box) as dispatchd_box_curnt, 0 as duty_curnt,            "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale_curnt                              "+
							" FROM bwfl.fl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j,                                                  "+
							" bwfl.gatepass_to_manufacturewholesale_"+year+" z                                                                            "+
							" WHERE a.dt  BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'                                          "+
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"' AND a.int_pckg_id=j.package_id                           "+
							" AND z.int_brewery_id=a.brewery_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt                             "+
							" GROUP BY a.brewery_id                                                                                                    "+
							" UNION                                                                                                                    "+
							" SELECT a.int_brewery_id as app_id, 'BEER' as group_type,                                                                 "+
							" 0 as dispatchd_bottl_cumltv, 0 as dispatchd_box_cumltv, 0 as duty_cumltv, 0 as sale_cumltv,                              "+
							" 0 as dispatchd_bottl_curnt, 0 as dispatchd_box_curnt, SUM((a.duty)+(a.addduty)) as duty_curnt, 0 as sale_curnt           "+
							" FROM bwfl.fl1_stock_trxn_"+year+" a, bwfl.gatepass_to_manufacturewholesale_"+year+" z                                          "+
							" WHERE z.dt_date  BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'                                     "+
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                                                          "+
							" AND z.int_brewery_id=a.int_brewery_id AND z.vch_gatepass_no=a.vch_gatepass_no                                            "+
							" GROUP BY a.int_brewery_id)x, public.bre_mst_b1_lic m                                                                     "+
							" WHERE x.app_id=m.vch_app_id_f                                                                                            "+
							"                                                                                                                          "+
							" union                                                                                                                   "+
							"                                                                                                                          "+
							" SELECT CASE WHEN m.vch_license_type='1' THEN m.vch_firm_name || '(BWFL2A)'                                               "+
							" WHEN m.vch_license_type='3' THEN m.vch_firm_name || '(BWFL2C)'                                                           "+
							" end as dist_name, x.*                                                                                                    "+
							" FROM                                                                                                                     "+
							" (SELECT a.int_bwfl_id as app_id, 'BWFL2A-2C' as group_type,                                                              "+
							" SUM(a.dispatch_bottle) as dispatchd_bottl_cumltv, SUM(a.dispatch_box)as dispatchd_box_cumltv,                            "+
							" SUM((a.duty)+(a.add_duty)) as duty_cumltv,                                                                               "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale_cumltv,                            "+
							" 0 as dispatchd_bottl_curnt, 0 as dispatchd_box_curnt, 0 as duty_curnt, 0 as sale_curnt                                   "+
							" FROM bwfl_license.fl2_stock_trxn_bwfl_"+year+" a, distillery.packaging_details_"+year+" j,                                     "+
							" bwfl_license.gatepass_to_districtwholesale_bwfl_"+year+" z                                                                  "+
							" WHERE a.dt  BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                         "+
							" AND a.int_pckg_id=j.package_id                                                                                           "+
							" AND z.int_bwfl_id=a.int_bwfl_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt                               "+
							" GROUP BY a.int_bwfl_id                                                                                                   "+
							" UNION                                                                                                                    "+
							" SELECT a.int_bwfl_id as app_id, 'BWFL2A-2C' as group_type,                                                               "+
							" 0 as dispatchd_bottl_cumltv, 0 as dispatchd_box_cumltv, 0 as duty_cumltv, 0 as sale_cumltv,                              "+
							" SUM(a.dispatch_bottle) as dispatchd_bottl_curnt, SUM(a.dispatch_box)as dispatchd_box_curnt,                              "+
							" SUM((a.duty)+(a.add_duty)) as duty_curnt,                                                                                "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale_curnt                              "+
							" FROM bwfl_license.fl2_stock_trxn_bwfl_"+year+" a, distillery.packaging_details_"+year+" j,                                     "+
							" bwfl_license.gatepass_to_districtwholesale_bwfl_"+year+" z                                                                  "+
							" WHERE a.dt  BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'                                          "+
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"' AND a.int_pckg_id=j.package_id                           "+
							" AND z.int_bwfl_id=a.int_bwfl_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt                               "+
							" GROUP BY a.int_bwfl_id)x, bwfl.registration_of_bwfl_lic_holder_"+year+" m                                                   "+
							" WHERE x.app_id=m.int_id AND m.vch_license_type IN ('1','3')                                                              "+
							"                                                                                                                          "+
							" union                                                                                                                    "+
							"                                                                                                                          "+
							" SELECT CASE WHEN m.vch_license_type='2' THEN m.vch_firm_name || '(BWFL2B)'                                               "+
							" WHEN m.vch_license_type='4' THEN m.vch_firm_name || '(BWFL2D)'                                                           "+
							" end as dist_name, x.*                                                                                                    "+
							" FROM                                                                                                                     "+
							" (SELECT a.int_bwfl_id as app_id, 'BWFL2B-2D' as group_type,                                                              "+
							" SUM(a.dispatch_bottle) as dispatchd_bottl_cumltv, SUM(a.dispatch_box)as dispatchd_box_cumltv,                            "+
							" SUM((a.duty)+(a.add_duty)) as duty_cumltv,                                                                               "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale_cumltv,                            "+
							" 0 as dispatchd_bottl_curnt, 0 as dispatchd_box_curnt, 0 as duty_curnt, 0 as sale_curnt                                   "+
							" FROM bwfl_license.fl2_stock_trxn_bwfl_"+year+" a, distillery.packaging_details_"+year+" j,                                     "+
							" bwfl_license.gatepass_to_districtwholesale_bwfl_"+year+" z                                                                  "+
							" WHERE a.dt  BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                         "+
							" AND a.int_pckg_id=j.package_id                                                                                           "+
							" AND z.int_bwfl_id=a.int_bwfl_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt                               "+
							" GROUP BY a.int_bwfl_id                                                                                                   "+
							" UNION                                                                                                                    "+
							" SELECT a.int_bwfl_id as app_id, 'BWFL2B-2D' as group_type,                                                               "+
							" 0 as dispatchd_bottl_cumltv, 0 as dispatchd_box_cumltv, 0 as duty_cumltv, 0 as sale_cumltv,                              "+
							" SUM(a.dispatch_bottle) as dispatchd_bottl_curnt, SUM(a.dispatch_box)as dispatchd_box_curnt,                              "+
							" SUM((a.duty)+(a.add_duty)) as duty_curnt,                                                                                "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale_curnt                              "+
							" FROM bwfl_license.fl2_stock_trxn_bwfl_"+year+" a, distillery.packaging_details_"+year+" j,                                     "+
							" bwfl_license.gatepass_to_districtwholesale_bwfl_"+year+" z                                                                  "+
							" WHERE a.dt  BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'                                          "+
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"' AND a.int_pckg_id=j.package_id                           "+
							" AND z.int_bwfl_id=a.int_bwfl_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt                               "+
							" GROUP BY a.int_bwfl_id)x, bwfl.registration_of_bwfl_lic_holder_"+year+" m                                                   "+
							" WHERE x.app_id=m.int_id AND m.vch_license_type IN ('2','4')                                                              "+
							"                                                                                                                          "+
							" union                                                                                                                    "+
							"                                                                                                                          "+
							" SELECT m.vch_firm_name ||'(FL2D)' as dist_name, x.*                                                                      "+
							" FROM                                                                                                                     "+
							" (SELECT a.int_fl2d_id as app_id, 'FL2D' as group_type,                                                                   "+
							" SUM(a.dispatch_bottle) as dispatchd_bottl_cumltv, SUM(a.dispatch_box)as dispatchd_box_cumltv,                            "+
							" SUM(a.cal_duty) as duty_cumltv,                                                                                          "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale_cumltv,                            "+
							" 0 as dispatchd_bottl_curnt, 0 as dispatchd_box_curnt, 0 as duty_curnt, 0 as sale_curnt                                   "+
							" FROM fl2d.fl2d_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j,                                                 "+
							" fl2d.gatepass_to_districtwholesale_fl2d_"+year+" z                                                                          "+
							" WHERE a.dt BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                          "+
							" AND a.int_pckg_id=j.package_id                                                                                           "+
							" AND z.int_fl2d_id=a.int_fl2d_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt                               "+
							" GROUP BY a.int_fl2d_id                                                                                                   "+
							" UNION                                                                                                                    "+
							" SELECT a.int_fl2d_id as app_id, 'FL2D' as group_type,                                                                    "+
							" 0 as dispatchd_bottl_cumltv, 0 as dispatchd_box_cumltv, 0 as duty_cumltv, 0 as sale_cumltv,                              "+
							" SUM(a.dispatch_bottle) as dispatchd_bottl_curnt, SUM(a.dispatch_box)as dispatchd_box_curnt,                              "+
							" SUM(a.cal_duty) as duty_curnt,                                                                                           "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as sale_curnt                              "+
							" FROM fl2d.fl2d_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j,                                                 "+
							" fl2d.gatepass_to_districtwholesale_fl2d_"+year+" z                                                                          "+
							" WHERE a.dt  BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'                                          "+
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"' AND a.int_pckg_id=j.package_id                           "+
							" AND z.int_fl2d_id=a.int_fl2d_id AND z.vch_gatepass_no=a.vch_gatepass_no AND z.dt_date=a.dt                               "+
							" GROUP BY a.int_fl2d_id)x, licence.fl2_2b_2d_"+year+" m                                                                      "+
							" WHERE x.app_id=m.int_app_id)y                                                                                            "+
							" GROUP BY y.group_type, y.app_id, y.dist_name ORDER BY y.group_type, y.dist_name ";
	        
	            
	        /*reportQuery = 	"SELECT y.type, y.app_id, y.dist_name, SUM(y.cl_dispatchd_bottl) as cl_dispatchd_bottl,                                           "+
							" SUM(y.cl_dispatchd_box) as cl_dispatchd_box,                                                                                    "+
							" round(CAST(float8(SUM(y.cl_sale)/100000)  as numeric), 2) as cl_sale,                                                           "+
							" round(CAST(float8(SUM(y.cl_duty)/10000000)  as numeric), 2) as cl_duty,                                                         "+
							" SUM(y.fl_dispatch_bottle) as fl_dispatch_bottle, SUM(y.fl_dispatchd_box) as fl_dispatchd_box,                                   "+
							" round(CAST(float8(SUM(y.fl_sale)/100000)  as numeric), 2) as fl_sale,                                                           "+
							" round(CAST(float8(SUM(y.fl_duty)/10000000)  as numeric), 2)  as fl_duty,                                                        "+
							" SUM(y.beer_dispatch_bottle) as beer_dispatch_bottle, SUM(y.beer_dispatchd_box) as beer_dispatchd_box,                           "+
							" round(CAST(float8(SUM(y.beer_sale)/100000)  as numeric), 2) as beer_sale,                                                       "+
							" round(CAST(float8(SUM(y.beer_duty)/10000000)  as numeric), 2) as beer_duty                                                      "+
							" FROM                                                                                                                            "+
							" (select m.vch_firm_name ||'(FL2D)' as dist_name,x.* from                                                                        "+
							" (select a.int_fl2d_id as app_id, 'Cumulative' as type, 0 as cl_dispatchd_bottl ,                                                "+
							" 0 as cl_dispatchd_box, 0 as cl_duty,coalesce(fl_dispatch_bottle,0) as fl_dispatch_bottle,                                       "+
							" coalesce(fl_dispatchd_box,0) as fl_dispatchd_box,coalesce(fl_duty,0) as fl_duty,                                                "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box, 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                "+
							" coalesce(fl_sale,0) as fl_sale, 0 as beer_sale                                                                                  "+
							" from (select distinct int_fl2d_id from fl2d.fl2d_stock_trxn_"+year+" where dt<=(SELECT CURRENT_DATE))a                             "+
							" left outer join                                                                                                                 "+
							" (select a.int_fl2d_id, SUM(a.dispatch_bottle) as fl_dispatch_bottle,                                                            "+
							" SUM(a.dispatch_box) as fl_dispatchd_box, SUM(a.cal_duty) as fl_duty,                                                            "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as fl_sale                                        "+
							" from fl2d.fl2d_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j                                                         "+
							" WHERE a.dt BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'  " +
							" AND a.int_pckg_id=j.package_id                                        "+
							" group by a.int_fl2d_id)b on a.int_fl2d_id=b.int_fl2d_id                                                                         "+
							" union                                                                                                                           "+
							" select a.int_fl2d_id as app_id, 'Till Last Month' as type,0 as cl_dispatchd_bottl ,                                             "+
							" 0 as cl_dispatchd_box, 0 as cl_duty,coalesce(fl_dispatch_bottle,0) as fl_dispatch_bottle,                                       "+
							" coalesce(fl_dispatchd_box,0) as fl_dispatchd_box,coalesce(fl_duty,0) as fl_duty,                                                "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box, 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                "+
							" coalesce(fl_sale,0) as fl_sale, 0 as beer_sale                                                                                  "+
							" from (select distinct int_fl2d_id from fl2d.fl2d_stock_trxn_"+year+" where dt<=(SELECT CURRENT_DATE))a                             "+
							" left outer join                                                                                                                 "+
							" (select a.int_fl2d_id, SUM(a.dispatch_bottle) as fl_dispatch_bottle, SUM(a.dispatch_box) as fl_dispatchd_box,                   "+
							" SUM(a.cal_duty) as fl_duty,                                                                                                     "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as fl_sale                                        "+
							" from fl2d.fl2d_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j                                                         "+
							" WHERE a.dt BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s2))+"'                                             "+
							" AND a.int_pckg_id=j.package_id                                                                                                  "+
							" group by a.int_fl2d_id)b on a.int_fl2d_id=b.int_fl2d_id                                                                         "+
							" union                                                                                                                           "+
							" select a.int_fl2d_id as app_id,'Current month' as type,0 as cl_dispatchd_bottl ,                                                "+
							" 0 as cl_dispatchd_box, 0 as cl_duty,coalesce(fl_dispatch_bottle,0) as fl_dispatch_bottle,                                       "+
							" coalesce(fl_dispatchd_box,0) as fl_dispatchd_box,coalesce(fl_duty,0) as fl_duty,                                                "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box, 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                "+
							" coalesce(fl_sale,0) as fl_sale, 0 as beer_sale                                                                                  "+
							" from (select distinct int_fl2d_id from fl2d.fl2d_stock_trxn_"+year+" where dt<=(SELECT CURRENT_DATE))a                             "+
							" left outer join                                                                                                                 "+
							" (select a.int_fl2d_id, SUM(a.dispatch_bottle) as fl_dispatch_bottle,                                                            "+
							" SUM(a.dispatch_box) as fl_dispatchd_box, SUM(a.cal_duty) as fl_duty,                                                            "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as fl_sale                                        "+
							" from fl2d.fl2d_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j                                                         "+
							" WHERE a.dt BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'  " +
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                           "+
							" AND a.int_pckg_id=j.package_id                                                                                                  "+
							" group by a.int_fl2d_id)b on a.int_fl2d_id=b.int_fl2d_id)x,licence.fl2_2b_2d_"+year+" m where x.app_id=m.int_app_id                 "+
							"                                                                                                                                 "+
							" union                                                                                                                           "+
							"                                                                                                                                 "+
							" SELECT CASE WHEN m.vch_license_type='1' THEN m.vch_firm_name || '(BWFL2A)'                                                      "+
							" WHEN m.vch_license_type='2' THEN m.vch_firm_name || '(BWFL2A)'                                                                  "+
							" WHEN m.vch_license_type='3' THEN m.vch_firm_name || '(BWFL2C)'                                                                  "+
							" WHEN m.vch_license_type='4' THEN m.vch_firm_name || '(BWFL2A)'                                                                  "+
							" end as dist_name, x.*                                                                                                           "+
							" FROM                                                                                                                            "+
							" (select a.int_bwfl_id as app_id, 'Cumulative' as type, 0 as cl_dispatchd_bottl ,                                                "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, 0 as fl_dispatch_bottle,                                                                   "+
							" 0 as fl_dispatchd_box, 0 as fl_duty,                                                                                            "+
							" coalesce(beer_dispatch_bottle,0) as beer_dispatch_bottle, coalesce(beer_dispatchd_box,0) as beer_dispatchd_box,                 "+
							" coalesce(beer_duty,0) as beer_duty, 0 as quantity, 0 as cl_sale,                                                                "+
							" 0 as fl_sale, coalesce(beer_sale,0) as beer_sale                                                                                "+
							" from (select distinct int_bwfl_id from bwfl_license.fl2_stock_trxn_bwfl_"+year+" where dt<=(SELECT CURRENT_DATE))a                 "+
							" left outer join                                                                                                                 "+
							" (select a.int_bwfl_id, SUM(a.dispatch_bottle) as beer_dispatch_bottle, SUM(a.dispatch_box) as beer_dispatchd_box,               "+
							" SUM((a.duty)+(a.add_duty)) as beer_duty,                                                                                        "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as beer_sale                                      "+
							" from bwfl_license.fl2_stock_trxn_bwfl_"+year+" a, distillery.packaging_details_"+year+" j                                             "+
							" WHERE a.dt BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'  " +
							" AND a.int_pckg_id=j.package_id                                        "+
							" group by a.int_bwfl_id)b on a.int_bwfl_id=b.int_bwfl_id                                                                         "+
							" UNION                                                                                                                           "+
							" select a.int_bwfl_id as app_id, 'Till Last Month' as type, 0 as cl_dispatchd_bottl ,                                            "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, 0 as fl_dispatch_bottle,                                                                   "+
							" 0 as fl_dispatchd_box, 0 as fl_duty,                                                                                            "+
							" coalesce(beer_dispatch_bottle,0) as beer_dispatch_bottle, coalesce(beer_dispatchd_box,0) as beer_dispatchd_box,                 "+
							" coalesce(beer_duty,0) as beer_duty, 0 as quantity, 0 as cl_sale,                                                                "+
							" 0 as fl_sale, coalesce(beer_sale,0) as beer_sale                                                                                "+
							" from (select distinct int_bwfl_id from bwfl_license.fl2_stock_trxn_bwfl_"+year+" where dt<=(SELECT CURRENT_DATE))a                 "+
							" left outer join                                                                                                                 "+
							" (select a.int_bwfl_id, SUM(a.dispatch_bottle) as beer_dispatch_bottle, SUM(a.dispatch_box) as beer_dispatchd_box,               "+
							" SUM((a.duty)+(a.add_duty)) as beer_duty,                                                                                        "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as beer_sale                                      "+
							" from bwfl_license.fl2_stock_trxn_bwfl_"+year+" a, distillery.packaging_details_"+year+" j                                             "+
							" WHERE a.dt BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s2))+"' " +
							" AND a.int_pckg_id=j.package_id              "+
							" group by a.int_bwfl_id)b on a.int_bwfl_id=b.int_bwfl_id                                                                         "+
							" UNION                                                                                                                           "+
							" select a.int_bwfl_id as app_id, 'Current Month' as type, 0 as cl_dispatchd_bottl ,                                              "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, 0 as fl_dispatch_bottle,                                                                   "+
							" 0 as fl_dispatchd_box, 0 as fl_duty,                                                                                            "+
							" coalesce(beer_dispatch_bottle,0) as beer_dispatch_bottle, coalesce(beer_dispatchd_box,0) as beer_dispatchd_box,                 "+
							" coalesce(beer_duty,0) as beer_duty, 0 as quantity, 0 as cl_sale,                                                                "+
							" 0 as fl_sale, coalesce(beer_sale,0) as beer_sale                                                                                "+
							" from (select distinct int_bwfl_id from bwfl_license.fl2_stock_trxn_bwfl_"+year+" where dt<=(SELECT CURRENT_DATE))a                 "+
							" left outer join                                                                                                                 "+
							" (select a.int_bwfl_id, SUM(a.dispatch_bottle) as beer_dispatch_bottle, SUM(a.dispatch_box) as beer_dispatchd_box,               "+
							" SUM((a.duty)+(a.add_duty)) as beer_duty,                                                                                        "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as beer_sale                                      "+
							" from bwfl_license.fl2_stock_trxn_bwfl_"+year+" a, distillery.packaging_details_"+year+" j                                             "+
							" WHERE a.dt BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'       " +
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                              "+
							"AND a.int_pckg_id=j.package_id                                                                                                   "+
							" group by a.int_bwfl_id)b on a.int_bwfl_id=b.int_bwfl_id)x, bwfl.registration_of_bwfl_lic_holder_"+year+" m                         "+
							" WHERE x.app_id=m.int_id AND m.vch_license_type IN ('2','4')                                                                     "+
							"                                                                                                                                 "+
							" union                                                                                                                           "+
							"                                                                                                                                 "+
							" SELECT CASE WHEN m.vch_license_type='1' THEN m.vch_firm_name || '(BWFL2A)'                                                      "+
							" WHEN m.vch_license_type='2' THEN m.vch_firm_name || '(BWFL2A)'                                                                  "+
							" WHEN m.vch_license_type='3' THEN m.vch_firm_name || '(BWFL2C)'                                                                  "+
							" WHEN m.vch_license_type='4' THEN m.vch_firm_name || '(BWFL2A)'                                                                  "+
							" end as dist_name, x.*                                                                                                           "+
							" FROM                                                                                                                            "+
							" (select a.int_bwfl_id as app_id, 'Cumulative' as type, 0 as cl_dispatchd_bottl ,                                                "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, coalesce(fl_dispatch_bottle,0) as fl_dispatch_bottle,                                      "+
							" coalesce(fl_dispatchd_box,0) as fl_dispatchd_box, coalesce(fl_duty,0) as fl_duty,                                               "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box, 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                "+
							" coalesce(fl_sale,0) as fl_sale, 0 as beer_sale                                                                                  "+
							" from (select distinct int_bwfl_id from bwfl_license.fl2_stock_trxn_bwfl_"+year+" where dt<=(SELECT CURRENT_DATE))a                 "+
							" left outer join                                                                                                                 "+
							" (select a.int_bwfl_id, SUM(a.dispatch_bottle) as fl_dispatch_bottle, SUM(a.dispatch_box) as fl_dispatchd_box,                   "+
							" SUM((a.duty)+(a.add_duty)) as fl_duty,                                                                                          "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as fl_sale                                        "+
							" from bwfl_license.fl2_stock_trxn_bwfl_"+year+" a, distillery.packaging_details_"+year+" j                                             "+
							" WHERE a.dt BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'  " +
							" AND a.int_pckg_id=j.package_id                                        "+
							" group by a.int_bwfl_id)b on a.int_bwfl_id=b.int_bwfl_id                                                                         "+
							" UNION                                                                                                                           "+
							" select a.int_bwfl_id as app_id, 'Till Last Month' as type, 0 as cl_dispatchd_bottl ,                                            "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, coalesce(fl_dispatch_bottle,0) as fl_dispatch_bottle,                                      "+
							" coalesce(fl_dispatchd_box,0) as fl_dispatchd_box, coalesce(fl_duty,0) as fl_duty,                                               "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box, 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                "+
							" coalesce(fl_sale,0) as fl_sale, 0 as beer_sale                                                                                  "+
							" from (select distinct int_bwfl_id from bwfl_license.fl2_stock_trxn_bwfl_"+year+" where dt<=(SELECT CURRENT_DATE))a                 "+
							" left outer join                                                                                                                 "+
							" (select a.int_bwfl_id, SUM(a.dispatch_bottle) as fl_dispatch_bottle, SUM(a.dispatch_box) as fl_dispatchd_box,                   "+
							" SUM((a.duty)+(a.add_duty)) as fl_duty,                                                                                          "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as fl_sale                                        "+
							" from bwfl_license.fl2_stock_trxn_bwfl_"+year+" a, distillery.packaging_details_"+year+" j                                             "+
							" WHERE a.dt BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s2))+"' AND a.int_pckg_id=j.package_id              "+
							" group by a.int_bwfl_id)b on a.int_bwfl_id=b.int_bwfl_id                                                                         "+
							" UNION                                                                                                                           "+
							" select a.int_bwfl_id as app_id, 'Current Month' as type, 0 as cl_dispatchd_bottl ,                                              "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, coalesce(fl_dispatch_bottle,0) as fl_dispatch_bottle,                                      "+
							" coalesce(fl_dispatchd_box,0) as fl_dispatchd_box, coalesce(fl_duty,0) as fl_duty,                                               "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box, 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                "+
							" coalesce(fl_sale,0) as fl_sale, 0 as beer_sale                                                                                  "+
							" from (select distinct int_bwfl_id from bwfl_license.fl2_stock_trxn_bwfl_"+year+" where dt<=(SELECT CURRENT_DATE))a                 "+
							" left outer join                                                                                                                 "+
							" (select a.int_bwfl_id, SUM(a.dispatch_bottle) as fl_dispatch_bottle, SUM(a.dispatch_box) as fl_dispatchd_box,                   "+
							" SUM((a.duty)+(a.add_duty)) as fl_duty,                                                                                          "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as fl_sale                                        "+
							" from bwfl_license.fl2_stock_trxn_bwfl_"+year+" a, distillery.packaging_details_"+year+" j                                             "+
							" WHERE a.dt BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'   " +
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                           "+
							" AND a.int_pckg_id=j.package_id                                                                                                  "+
							" group by a.int_bwfl_id)b on a.int_bwfl_id=b.int_bwfl_id)x, bwfl.registration_of_bwfl_lic_holder_"+year+" m                         "+
							" WHERE x.app_id=m.int_id AND m.vch_license_type IN ('1','3')                                                                     "+
							"                                                                                                                                 "+
							" union                                                                                                                           "+
							"                                                                                                                                 "+
							" SELECT m.brewery_nm||'(BREWERY)' as dist_name, x.*                                                                              "+
							" FROM                                                                                                                            "+
							" (select a.brewery_id as app_id, 'Cumulative' as type, 0 as cl_dispatchd_bottl ,                                                 "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_dispatchd_box, 0 as fl_duty,                              "+
							" coalesce(beer_dispatch_bottle,0) as beer_dispatch_bottle, coalesce(beer_dispatchd_box,0) as beer_dispatchd_box,                 "+
							" 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                                                                    "+
							" 0 as fl_sale, coalesce(beer_sale,0) as beer_sale                                                                                "+
							" from (select distinct brewery_id from bwfl.fl2_stock_trxn_"+year+" where dt<=(SELECT CURRENT_DATE))a                               "+
							" left outer join                                                                                                                 "+
							" (select a.brewery_id, SUM(a.dispatch_bottle) as beer_dispatch_bottle, SUM(a.dispatch_box) as beer_dispatchd_box,                "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as beer_sale                                      "+
							" from bwfl.fl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j                                                          "+
							" WHERE a.dt BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'  " +
							" AND a.int_pckg_id=j.package_id                                        "+
							" group by a.brewery_id)b on a.brewery_id=b.brewery_id                                                                            "+
							" UNION                                                                                                                           "+
							" select a.int_brewery_id as app_id, 'Cumulative' as type, 0 as cl_dispatchd_bottl ,                                              "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_dispatchd_box, 0 as fl_duty,                              "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box,                                                                             "+
							" coalesce(beer_duty,0) as beer_duty, 0 as quantity, 0 as cl_sale,                                                                "+
							" 0 as fl_sale, 0 as beer_sale                                                                                                    "+
							" from (select distinct int_brewery_id from bwfl.gatepass_to_manufacturewholesale_"+year+"                                           "+
							" where dt_date<=(SELECT CURRENT_DATE))a                                                                                          "+
							" left outer join                                                                                                                 "+
							" (select a.int_brewery_id, SUM((a.duty)+(a.addduty)) as beer_duty                                                                "+
							" from bwfl.fl1_stock_trxn_"+year+" a, bwfl.gatepass_to_manufacturewholesale_"+year+" g                                                 "+
							" WHERE a.int_brewery_id=g.int_brewery_id AND a.vch_gatepass_no=g.vch_gatepass_no                                                 "+
							" AND g.dt_date BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                                                                    "+
							" group by a.int_brewery_id)b on a.int_brewery_id=b.int_brewery_id                                                                "+
							" UNION                                                                                                                           "+
							" select a.brewery_id as app_id, 'Till Last Month' as type, 0 as cl_dispatchd_bottl ,                                             "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_dispatchd_box, 0 as fl_duty,                              "+
							" coalesce(beer_dispatch_bottle,0) as beer_dispatch_bottle, coalesce(beer_dispatchd_box,0) as beer_dispatchd_box,                 "+
							" 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                                                                    "+
							" 0 as fl_sale, coalesce(beer_sale,0) as beer_sale                                                                                "+
							" from (select distinct brewery_id from bwfl.fl2_stock_trxn_"+year+" where dt<=(SELECT CURRENT_DATE))a                               "+
							" left outer join                                                                                                                 "+
							" (select a.brewery_id, SUM(a.dispatch_bottle) as beer_dispatch_bottle, SUM(a.dispatch_box) as beer_dispatchd_box,                "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as beer_sale                                      "+
							" from bwfl.fl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j                                                          "+
							" WHERE a.dt BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s2))+"'  " +
							" AND a.int_pckg_id=j.package_id              "+
							" group by a.brewery_id)b on a.brewery_id=b.brewery_id                                                                            "+
							" UNION                                                                                                                           "+
							" select a.int_brewery_id as app_id, 'Till Last Month' as type, 0 as cl_dispatchd_bottl ,                                         "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_dispatchd_box, 0 as fl_duty,                              "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box,                                                                             "+
							" coalesce(beer_duty,0) as beer_duty, 0 as quantity, 0 as cl_sale,                                                                "+
							" 0 as fl_sale, 0 as beer_sale                                                                                                    "+
							" from (select distinct int_brewery_id from bwfl.gatepass_to_manufacturewholesale_"+year+"                                           "+
							" where dt_date<=(SELECT CURRENT_DATE))a                                                                                          "+
							" left outer join                                                                                                                 "+
							" (select a.int_brewery_id, SUM((a.duty)+(a.addduty)) as beer_duty                                                                "+
							" from bwfl.fl1_stock_trxn_"+year+" a, bwfl.gatepass_to_manufacturewholesale_"+year+" g                                                 "+
							" WHERE a.int_brewery_id=g.int_brewery_id AND a.vch_gatepass_no=g.vch_gatepass_no                                                 "+
							" AND g.dt_date BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s2))+"'                                          "+
							" group by a.int_brewery_id)b on a.int_brewery_id=b.int_brewery_id                                                                "+
							" UNION                                                                                                                           "+
							" select a.brewery_id as app_id, 'Current Month' as type, 0 as cl_dispatchd_bottl ,                                               "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_dispatchd_box, 0 as fl_duty,                              "+
							" coalesce(beer_dispatch_bottle,0) as beer_dispatch_bottle, coalesce(beer_dispatchd_box,0) as beer_dispatchd_box,                 "+
							" 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                                                                    "+
							" 0 as fl_sale, coalesce(beer_sale,0) as beer_sale                                                                                "+
							" from (select distinct brewery_id from bwfl.fl2_stock_trxn_"+year+" where dt<=(SELECT CURRENT_DATE))a                               "+
							" left outer join                                                                                                                 "+
							" (select a.brewery_id, SUM(a.dispatch_bottle) as beer_dispatch_bottle, SUM(a.dispatch_box) as beer_dispatchd_box,                "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as beer_sale                                      "+
							" from bwfl.fl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j                                                          "+
							" WHERE a.dt BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'   " +
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                           "+
							" AND a.int_pckg_id=j.package_id                                                                                                  "+
							" group by a.brewery_id)b on a.brewery_id=b.brewery_id                                                                            "+
							" UNION                                                                                                                           "+
							" select a.int_brewery_id as app_id, 'Current Month' as type, 0 as cl_dispatchd_bottl ,                                           "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_dispatchd_box, 0 as fl_duty,                              "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box,                                                                             "+
							" coalesce(beer_duty,0) as beer_duty, 0 as quantity, 0 as cl_sale,                                                                "+
							" 0 as fl_sale, 0 as beer_sale                                                                                                    "+
							" from (select distinct int_brewery_id from bwfl.gatepass_to_manufacturewholesale_"+year+"                                           "+
							" where dt_date<=(SELECT CURRENT_DATE))a                                                                                          "+
							" left outer join                                                                                                                 "+
							" (select a.int_brewery_id, SUM((a.duty)+(a.addduty)) as beer_duty                                                                "+
							" from bwfl.fl1_stock_trxn_"+year+" a, bwfl.gatepass_to_manufacturewholesale_"+year+" g                                                 "+
							" WHERE a.int_brewery_id=g.int_brewery_id AND a.vch_gatepass_no=g.vch_gatepass_no                                                 "+
							" AND g.dt_date BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'   " +
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                            "+
							" group by a.int_brewery_id)b on a.int_brewery_id=b.int_brewery_id)x, public.bre_mst_b1_lic m                                     "+
							" WHERE x.app_id=m.vch_app_id_f                                                                                                   "+
							"                                                                                                                                 "+
							" union                                                                                                                           "+
							"                                                                                                                                 "+
							" SELECT m.vch_undertaking_name||'(DISTILLERY)' as dist_name, x.*                                                                 "+
							" FROM                                                                                                                            "+
							" (select a.int_dissleri_id as app_id, 'Cumulative' as type, 0 as cl_dispatchd_bottl ,                                            "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, coalesce(fl_dispatch_bottle,0) as fl_dispatch_bottle,                                      "+
							" coalesce(fl_dispatchd_box,0) as fl_dispatchd_box, 0 as fl_duty,                                                                 "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box, 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                "+
							" coalesce(fl_sale,0) as fl_sale, 0 as beer_sale                                                                                  "+
							" from (select distinct int_dissleri_id from distillery.fl2_stock_trxn_"+year+" where dt<=(SELECT CURRENT_DATE))a                    "+
							" left outer join                                                                                                                 "+
							" (select a.int_dissleri_id, SUM(a.dispatch_bottle) as fl_dispatch_bottle, SUM(a.dispatch_box) as fl_dispatchd_box,               "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as fl_sale                                        "+
							" from distillery.fl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j                                                    "+
							" WHERE a.dt BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'  " +
							" AND a.int_pckg_id=j.package_id                                        "+
							" group by a.int_dissleri_id)b on a.int_dissleri_id=b.int_dissleri_id                                                             "+
							" UNION                                                                                                                           "+
							" select a.int_dist_id as app_id, 'Cumulative' as type, 0 as cl_dispatchd_bottl ,                                                 "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, 0 as fl_dispatch_bottle,                                                                   "+
							" 0 as fl_dispatchd_box, coalesce(fl_duty,0) as fl_duty,                                                                          "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box, 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                "+
							" 0 as fl_sale, 0 as beer_sale                                                                                                    "+
							" from (select distinct int_dist_id from distillery.gatepass_to_manufacturewholesale_"+year+"                                        "+
							" where dt_date<=(SELECT CURRENT_DATE))a                                                                                          "+
							" left outer join                                                                                                                 "+
							" (select a.int_dissleri_id, SUM((a.duty)+(a.addduty)) as fl_duty                                                                 "+
							" from distillery.fl1_stock_trxn_"+year+" a, distillery.gatepass_to_manufacturewholesale_"+year+" e                                     "+
							" WHERE a.int_dissleri_id=e.int_dist_id AND a.vch_gatepass_no=e.vch_gatepass_no                                                   "+
							" AND e.dt_date BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                                                                    "+
							" group by a.int_dissleri_id)b on a.int_dist_id=b.int_dissleri_id                                                                 "+
							" UNION                                                                                                                           "+
							" select a.int_dissleri_id as app_id, 'Till Last Month' as type, 0 as cl_dispatchd_bottl ,                                        "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, coalesce(fl_dispatch_bottle,0) as fl_dispatch_bottle,                                      "+
							" coalesce(fl_dispatchd_box,0) as fl_dispatchd_box, 0 as fl_duty,                                                                 "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box, 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                "+
							" coalesce(fl_sale,0) as fl_sale, 0 as beer_sale                                                                                  "+
							" from (select distinct int_dissleri_id from distillery.fl2_stock_trxn_"+year+" where dt<=(SELECT CURRENT_DATE))a                    "+
							" left outer join                                                                                                                 "+
							" (select a.int_dissleri_id, SUM(a.dispatch_bottle) as fl_dispatch_bottle, SUM(a.dispatch_box) as fl_dispatchd_box,               "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as fl_sale                                        "+
							" from distillery.fl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j                                                    "+
							" WHERE a.dt BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s2))+"'  " +
							" AND a.int_pckg_id=j.package_id              "+
							" group by a.int_dissleri_id)b on a.int_dissleri_id=b.int_dissleri_id                                                             "+
							" UNION                                                                                                                           "+
							" select a.int_dist_id as app_id, 'Till Last Month' as type, 0 as cl_dispatchd_bottl ,                                            "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, 0 as fl_dispatch_bottle,                                                                   "+
							" 0 as fl_dispatchd_box, coalesce(fl_duty,0) as fl_duty,                                                                          "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box, 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                "+
							" 0 as fl_sale, 0 as beer_sale                                                                                                    "+
							" from (select distinct int_dist_id from distillery.gatepass_to_manufacturewholesale_"+year+"                                        "+
							" where dt_date<=(SELECT CURRENT_DATE))a                                                                                          "+
							" left outer join                                                                                                                 "+
							" (select a.int_dissleri_id, SUM((a.duty)+(a.addduty)) as fl_duty                                                                 "+
							" from distillery.fl1_stock_trxn_"+year+" a, distillery.gatepass_to_manufacturewholesale_"+year+" e                                     "+
							" WHERE a.int_dissleri_id=e.int_dist_id AND a.vch_gatepass_no=e.vch_gatepass_no                                                   "+
							" AND e.dt_date BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s2))+"'                                          "+
							" group by a.int_dissleri_id)b on a.int_dist_id=b.int_dissleri_id                                                                 "+
							" UNION                                                                                                                           "+
							"                                                                                                                                 "+
							" select a.int_dissleri_id as app_id, 'Current Month' as type, 0 as cl_dispatchd_bottl ,                                          "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, coalesce(fl_dispatch_bottle,0) as fl_dispatch_bottle,                                      "+
							" coalesce(fl_dispatchd_box,0) as fl_dispatchd_box, 0 as fl_duty,                                                                 "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box, 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                "+
							" coalesce(fl_sale,0) as fl_sale, 0 as beer_sale                                                                                  "+
							" from (select distinct int_dissleri_id from distillery.fl2_stock_trxn_"+year+" where dt<=(SELECT CURRENT_DATE))a                    "+
							" left outer join                                                                                                                 "+
							" (select a.int_dissleri_id, SUM(a.dispatch_bottle) as fl_dispatch_bottle, SUM(a.dispatch_box) as fl_dispatchd_box,               "+
							" SUM(round(CAST(float8(((a.dispatch_bottle)*j.quantity)/1000) as numeric), 2)) as fl_sale                                        "+
							" from distillery.fl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j                                                    "+
							" WHERE a.dt BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'   " +
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                           "+
							" AND a.int_pckg_id=j.package_id                                                                                                  "+
							" group by a.int_dissleri_id)b on a.int_dissleri_id=b.int_dissleri_id                                                             "+
							" UNION                                                                                                                           "+
							" select a.int_dist_id as app_id, 'Current Month' as type, 0 as cl_dispatchd_bottl ,                                              "+
							" 0 as cl_dispatchd_box, 0 as cl_duty, 0 as fl_dispatch_bottle,                                                                   "+
							" 0 as fl_dispatchd_box, coalesce(fl_duty,0) as fl_duty,                                                                          "+
							" 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box, 0 as beer_duty, 0 as quantity, 0 as cl_sale,                                "+
							" 0 as fl_sale, 0 as beer_sale                                                                                                    "+
							" from (select distinct int_dist_id from distillery.gatepass_to_manufacturewholesale_"+year+"                                        "+
							" where dt_date<=(SELECT CURRENT_DATE))a                                                                                          "+
							" left outer join                                                                                                                 "+
							" (select a.int_dissleri_id, SUM((a.duty)+(a.addduty)) as fl_duty                                                                 "+
							" from distillery.fl1_stock_trxn_"+year+" a, distillery.gatepass_to_manufacturewholesale_"+year+" e                                     "+
							" WHERE a.int_dissleri_id=e.int_dist_id AND a.vch_gatepass_no=e.vch_gatepass_no                                                   "+
							" AND e.dt_date BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'   " +
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                        "+
							" group by a.int_dissleri_id)b on a.int_dist_id=b.int_dissleri_id)x, public.dis_mst_pd1_pd2_lic m                                 "+
							" WHERE x.app_id=m.int_app_id_f                                                                                                   "+
							"                                                                                                                                 "+
							" union                                                                                                                           "+
							"                                                                                                                                 "+
							" SELECT m.vch_undertaking_name||'(DISTILLERY)' as dist_name, x.*                                                                 "+
							" FROM                                                                                                                            "+
							" (select a.int_dissleri_id as app_id, 'Cumulative' as type, coalesce(cl_dispatchd_bottl,0) as cl_dispatchd_bottl ,               "+
							" coalesce(cl_dispatchd_box,0) as cl_dispatchd_box, coalesce(cl_duty,0) as cl_duty, 0 as fl_dispatch_bottle,                      "+
							" 0 as fl_dispatchd_box, 0 as fl_duty, 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box,                                        "+
							" 0 as beer_duty, 0 as quantity, coalesce(cl_sale,0) as cl_sale, 0 as fl_sale, 0 as beer_sale                                     "+
							" from (select distinct int_dissleri_id from distillery.cl2_stock_trxn_"+year+" where dt_date<=(SELECT CURRENT_DATE))a               "+
							" left outer join                                                                                                                 "+
							" (select a.int_dissleri_id, SUM(a.dispatchd_bottl) as cl_dispatchd_bottl, SUM(a.dispatchd_box) as cl_dispatchd_box,              "+
							" SUM((a.duty)+(a.addduty)) as cl_duty,                                                                                           "+
							" SUM(round(CAST(float8(((a.dispatchd_bottl)*j.quantity)/1000) as numeric), 2)) as cl_sale                                        "+
							" from distillery.cl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j                                                    "+
							" WHERE a.dt_date BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'  " +
							" AND a.int_pckg_id=j.package_id                                   "+
							" group by a.int_dissleri_id)b on a.int_dissleri_id=b.int_dissleri_id                                                             "+
							" UNION                                                                                                                           "+
							" select a.int_dissleri_id as app_id, 'Till Last Month' as type, coalesce(cl_dispatchd_bottl,0) as cl_dispatchd_bottl ,           "+
							" coalesce(cl_dispatchd_box,0) as cl_dispatchd_box, coalesce(cl_duty,0) as cl_duty, 0 as fl_dispatch_bottle,                      "+
							" 0 as fl_dispatchd_box, 0 as fl_duty, 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box,                                        "+
							" 0 as beer_duty, 0 as quantity, coalesce(cl_sale,0) as cl_sale, 0 as fl_sale, 0 as beer_sale                                     "+
							" from (select distinct int_dissleri_id from distillery.cl2_stock_trxn_"+year+" where dt_date<=(SELECT CURRENT_DATE))a               "+
							" left outer join                                                                                                                 "+
							" (select a.int_dissleri_id, SUM(a.dispatchd_bottl) as cl_dispatchd_bottl, SUM(a.dispatchd_box) as cl_dispatchd_box,              "+
							" SUM((a.duty)+(a.addduty)) as cl_duty,                                                                                           "+
							" SUM(round(CAST(float8(((a.dispatchd_bottl)*j.quantity)/1000) as numeric), 2)) as cl_sale                                        "+
							" from distillery.cl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j                                                    "+
							" WHERE a.dt_date BETWEEN '2019-04-01' AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s2))+"'                                        "+
							" AND a.int_pckg_id=j.package_id                                                                                                  "+
							" group by a.int_dissleri_id)b on a.int_dissleri_id=b.int_dissleri_id                                                             "+
							" UNION                                                                                                                           "+
							" select a.int_dissleri_id as app_id, 'Current Month' as type, coalesce(cl_dispatchd_bottl,0) as cl_dispatchd_bottl ,             "+
							" coalesce(cl_dispatchd_box,0) as cl_dispatchd_box, coalesce(cl_duty,0) as cl_duty, 0 as fl_dispatch_bottle,                      "+
							" 0 as fl_dispatchd_box, 0 as fl_duty, 0 as beer_dispatch_bottle, 0 as beer_dispatchd_box,                                        "+
							" 0 as beer_duty, 0 as quantity, coalesce(cl_sale,0) as cl_sale, 0 as fl_sale, 0 as beer_sale                                     "+
							" from (select distinct int_dissleri_id from distillery.cl2_stock_trxn_"+year+" where dt_date<=(SELECT CURRENT_DATE))a               "+
							" left outer join                                                                                                                 "+
							" (select a.int_dissleri_id, SUM(a.dispatchd_bottl) as cl_dispatchd_bottl, SUM(a.dispatchd_box) as cl_dispatchd_box,              "+
							" SUM((a.duty)+(a.addduty)) as cl_duty,                                                                                           "+
							" SUM(round(CAST(float8(((a.dispatchd_bottl)*j.quantity)/1000) as numeric), 2)) as cl_sale                                        "+
							" from distillery.cl2_stock_trxn_"+year+" a, distillery.packaging_details_"+year+" j                                                    "+
							" WHERE a.dt_date BETWEEN '"+Utility.convertUtilDateToSQLDate(formatter.parse(s3))+"'   " +
							" AND '"+Utility.convertUtilDateToSQLDate(formatter.parse(s4))+"'                      "+
							" AND a.int_pckg_id=j.package_id                                                                                                  "+
							" group by a.int_dissleri_id)b on a.int_dissleri_id=b.int_dissleri_id)x, public.dis_mst_pd1_pd2_lic m                             "+
							" WHERE x.app_id=m.int_app_id_f)y                                                                                                 "+
							" GROUP BY y.app_id, y.dist_name, y.type ORDER BY y.dist_name, y.type DESC";*/



			pst = con.prepareStatement(reportQuery);
			//System.out.println("reportQuery----------" + reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {
       
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);


				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "Currnt_Cumulativ_TillNow_Report.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "Currnt_Cumulativ_TillNow_Report" + "-" + n + ".pdf");
				act.setPdfName("Currnt_Cumulativ_TillNow_Report" + "-" + n + ".pdf");
				act.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
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
	
	
	//======================================Year List========================================
	public ArrayList yearListImpl(Currnt_Cumulativ_TillNow_ReportAction act) {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue("");
		list.add(item);
		conn = ConnectionToDataBase.getConnection();

		try {
		
		
	String query = " SELECT year, value FROM public.reporting_year;";

				ps = conn.prepareStatement(query);
			// ps.setDate(1,
			// Utility.convertUtilDateToSQLDate(act.getDt_date()));
			rs = ps.executeQuery();
          
			while (rs.next()) {

				item = new SelectItem();

				item.setValue(rs.getString("value"));
				item.setLabel(rs.getString("year"));
				
				list.add(item);
				//System.out.println("== year== "+query);

			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
			e.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}	
	

	

}
