package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.mentor.action.SaleInCasesAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class SaleInCasesImpl {
	
	private Date yesterday(Date date) {
	    final Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_YEAR , -1);
	    return cal.getTime();
	}
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("yyyy-MM-dd").parse(date);
	     } catch (Exception e) {
	         return null;
	     }
	  }
	
	private static long daysBetween(Date one, Date two) {
        long difference =  (one.getTime()-two.getTime())/86400000;
        return Math.abs(difference);
    }


	public void printReport(SaleInCasesAction act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String yearl = act.getYear();
		
		Date date1, date2, date3;
		
		Long f;
		
		String[] mon = {"box_jan", "box_feb", "box_march", "box_april", "box_may", "box_june", "box_july", "box_aug", "box_sep", "box_oct", "box_nov", "box_dec" };
		
		String[] month_name = {"January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		
		
		
		try {
			
			date1 = yesterday(act.getDate());
			
			date2 =  yesterday(date1);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(act.getDate());
			
			int month = cal.get(Calendar.MONTH)+1;
			int year = cal.get(Calendar.YEAR);
			int max_days =  cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			date3 = parseDate(year+"-"+month+"-01");
			
		//	System.out.println(year+"-"+month+"-01");
			
			f = daysBetween(date3, act.getDate())+1;
			
		//	System.out.println("days-"+f);
			
			con = ConnectionToDataBase.getConnection();
			
			if (act.getRadio().equalsIgnoreCase("CL"))
			{

			
				
			reportQuery = "select d.distillery_id, d.vch_undertaking_name," +
					"(select  ceil((box_april+ box_may+ box_june+" +
					" box_july+ box_aug+ box_sep+ box_oct+" +
					" box_nov+ box_dec+ box_jan+box_feb+box_march)/365) " +
					" from distillery.cl_monthly_dispatch_18_19 where distillery_id=d.distillery_id) as avg_sale_prev ," +
					"  (select ceil("+mon[month-1]+"/"+max_days+") from distillery.cl_monthly_dispatch_18_19 where distillery_id=d.distillery_id ) as avg_sale_prev_month ," +
							" d.sale_date1," +
					" d.sale_date2,d.sale_date3,d.sum_sale," +
					" d.avg_sale,  (select ceil(sum(bottled/ceil(int_planned_bottles/int_boxes)) ) from distillery.mst_bottling_plan_"+yearl+" " +
					" where vch_license_type='CL' and plan_dt<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and int_distillery_id=d.distillery_id) - d.dispatched_stock  as closing_stock,0 as csdexp," +
					" (select count(xy.indent_no) from fl2d.indent_for_wholesale xy where xy.unit_id=d.distillery_id and xy.cr_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and xy.vch_licence_type='CL2' and xy.finalize_flag='F' and xy.vch_type='D'" +
							"and (  xy.vch_action_taken is null or xy.vch_action_taken='A' or xy.vch_action_taken='O') and xy.total_cases > (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where  d.indent_no=xy.indent_no)) as pending, " +
					"(select min(z.cr_date) from fl2d.indent_for_wholesale z where z.unit_id=d.distillery_id and (  z.vch_action_taken = 'A' or z.vch_action_taken = 'O' or z.vch_action_taken is null)  and z.cr_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and z.vch_licence_type='CL2' and z.finalize_flag='F' and z.vch_type='D' and" +
					" z.total_cases > (select SUM(COALESCE(dd.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  dd where  dd.indent_no=z.indent_no )) as oldest_date,"+
					" coalesce(sum(e.total_cases),0)-(select sum(coalesce(y.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn y" +
					" where y.indent_no in(select xy.indent_no from fl2d.indent_for_wholesale xy where (   xy.vch_action_taken !='C' or xy.vch_action_taken is null) and xy.unit_id=d.distillery_id and xy.cr_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and xy.vch_licence_type='CL2' and xy.finalize_flag='F' and xy.vch_type='D' " +
							"and xy.total_cases > (select SUM(COALESCE(xd.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  xd where xd.indent_no=xy.indent_no )))as indented from" +
					" (select c.distillery_id, c.vch_undertaking_name,sum(c.sale_date1) as sale_date1," +
					" sum(c.sale_date2) as sale_date2,sum(c.sale_date3) as sale_date3,sum(c.sum_sale) as sum_sale," +
					" ceil(sum(c.sum_sale)/"+f+") as avg_sale, sum(c.dispatched_stock) as dispatched_stock from" +
					" (select b.distillery_id, b.vch_undertaking_name, b.vch_gatepass_no,b.dt_date," +
					" case when b.dt_date='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' then c.dispatchd_box else 0 end as sale_date1," +
					" case when b.dt_date='"+ Utility.convertUtilDateToSQLDate(date1)+"' then c.dispatchd_box else 0  end as sale_date2," +
					" case when b.dt_date='"+ Utility.convertUtilDateToSQLDate(date2)+"' then c.dispatchd_box else 0  end  as sale_date3," +
					" case when b.dt_date between '"+ Utility.convertUtilDateToSQLDate(date3)+"' and '"+ Utility.convertUtilDateToSQLDate(act.getDate())+"'" +
					" then c.dispatchd_box else 0 end as sum_sale," +
					" case when b.dt_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' then c.dispatchd_box else 0  end as dispatched_stock from" +
					" (select  a.distillery_id, a.vch_undertaking_name, vch_gatepass_no, dt_date from" +
					" (select distinct a.distillery_id, b.vch_undertaking_name from" +
					" distillery.brand_registration_"+yearl+" a, public.dis_mst_pd1_pd2_lic b" +
					" where a.distillery_id=b.int_app_id_f and a.vch_license_type='CL' order by a.distillery_id )a left outer join" +
					" distillery.gatepass_to_manufacturewholesale_cl_"+yearl+" on a.distillery_id=int_dist_id  and vch_finalize='F' " +
					" order by vch_gatepass_no )b left outer join distillery.cl2_stock_trxn_"+yearl+" c on" +
					" b.vch_gatepass_no=c.vch_gatepass_no )c" +
					" group by c.distillery_id, c.vch_undertaking_name  order by  c.vch_undertaking_name)d left outer join fl2d.indent_for_wholesale e" +
					" on (e.vch_action_taken !='C' or e.vch_action_taken is null ) and e.unit_id=d.distillery_id and e.cr_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and e.finalize_flag='F' and e.vch_type='D' and e.vch_licence_type='CL2' and" +
							" e.total_cases > (select SUM(COALESCE(xd.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  xd where xd.indent_no=e.indent_no ) group by d.distillery_id, d.vch_undertaking_name, d.sale_date1," +
					" d.sale_date2,d.sale_date3,d.sum_sale," +
					" d.avg_sale, d.dispatched_stock order by d.avg_sale desc ;  "; 
			
			//System.out.println("=================reportQuerycl========="+reportQuery);
			}
			else if (act.getRadio().equalsIgnoreCase("FL"))
			{

			
				
			reportQuery = 	"  select d.distillery_id, d.vch_undertaking_name, d.sale_date1," +
					"(select  ceil((box_april+ box_may+ box_june+" +
					" box_july+ box_aug+ box_sep+ box_oct+" +
					" box_nov+ box_dec+ box_jan+box_feb+box_march)/365) " +
					" from distillery.fl_monthly_dispatch_18_19 where int_dist_id=d.distillery_id) as avg_sale_prev ," +
					"  (select ceil("+mon[month-1]+"/"+max_days+") from distillery.fl_monthly_dispatch_18_19 where int_dist_id=d.distillery_id ) as avg_sale_prev_month ," +
					
					" d.sale_date2,d.sale_date3,d.sum_sale," +
					" d.avg_sale,   (select ceil(sum(bottled/ceil(int_planned_bottles/int_boxes)) ) from distillery.mst_bottling_plan_"+yearl+" " +
					" where vch_license_type in ('FL3','FL3A') and plan_dt<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and int_distillery_id=d.distillery_id)-(select coalesce((sum(b.dispatchd_box)) ,0) from distillery.gatepass_to_manufacturewholesale_"+yearl+" a,distillery.fl1_stock_trxn_"+yearl+" b  where   a.vch_to in ('EXPORT','EOI','FL2A') and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date <='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and int_dist_id=d.distillery_id) - d.dispatched_stock  as closing_stock,"
							+ "(select coalesce((sum(b.dispatchd_box)) ,0) from distillery.gatepass_to_manufacturewholesale_"+yearl+" a,distillery.fl1_stock_trxn_"+yearl+" b  where   a.vch_to in ('EXPORT','EOI','FL2A') and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date between '"+ Utility.convertUtilDateToSQLDate(date3)+"' and '"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and int_dist_id=d.distillery_id) as csdexp," +
					
					" (select count(xy.indent_no) from fl2d.indent_for_wholesale xy where xy.unit_id=d.distillery_id and xy.cr_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and xy.vch_licence_type='FL2' and xy.finalize_flag='F' and xy.vch_type='D'" +
							"and (  xy.vch_action_taken is null or xy.vch_action_taken='A' or xy.vch_action_taken='O') and xy.total_cases > (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=xy.indent_no)) as pending, " +
					"(select min(z.cr_date) from fl2d.indent_for_wholesale z where z.unit_id=d.distillery_id and (   z.vch_action_taken = 'A' or z.vch_action_taken = 'O' or z.vch_action_taken is null)  and z.cr_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and z.vch_licence_type='FL2' and z.finalize_flag='F' and z.vch_type='D' and" +
					" z.total_cases > (select SUM(COALESCE(dd.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  dd where  dd.indent_no=z.indent_no )) as oldest_date,"+
					" coalesce(sum(e.total_cases),0)-(select sum(coalesce(y.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn y" +
					" where y.indent_no in(select xy.indent_no from fl2d.indent_for_wholesale xy where ( xy.vch_action_taken !='C' or xy.vch_action_taken is null  ) and xy.unit_id=d.distillery_id and xy.cr_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and xy.finalize_flag='F' and xy.vch_licence_type='FL2' and xy.vch_type='D' and" +
							" xy.total_cases > (select SUM(COALESCE(xd.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  xd where xd.indent_no=xy.indent_no ) ))as indented from" +
					" (select c.distillery_id, c.vch_undertaking_name,sum(c.sale_date1) as sale_date1," +
					" sum(c.sale_date2) as sale_date2,sum(c.sale_date3) as sale_date3,sum(c.sum_sale) as sum_sale," +
					" ceil(sum(c.sum_sale)/"+f+") as avg_sale, sum(c.dispatched_stock) as dispatched_stock from" +
					" (select b.distillery_id, b.vch_undertaking_name, b.vch_gatepass_no,b.dt_date," +
					" case when b.dt_date='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' then c.dispatch_box else 0 end as sale_date1," +
					" case when b.dt_date='"+ Utility.convertUtilDateToSQLDate(date1)+"' then c.dispatch_box else 0  end as sale_date2," +
					" case when b.dt_date='"+ Utility.convertUtilDateToSQLDate(date2)+"' then c.dispatch_box else 0  end  as sale_date3," +
					" case when b.dt_date between '"+ Utility.convertUtilDateToSQLDate(date3)+"' and '"+ Utility.convertUtilDateToSQLDate(act.getDate())+"'" +
					" then c.dispatch_box else 0 end as sum_sale," +
					" case when b.dt_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' then c.dispatch_box else 0  end as dispatched_stock from" +
					" (select  a.distillery_id, a.vch_undertaking_name, vch_gatepass_no, dt_date from" +
					" (select distinct a.distillery_id, b.vch_undertaking_name from" +
					" distillery.brand_registration_"+yearl+" a, public.dis_mst_pd1_pd2_lic b" +
					" where a.distillery_id=b.int_app_id_f and a.vch_license_type in ('FL3','FL3A') order by a.distillery_id )a left outer join" +
					" distillery.gatepass_to_districtwholesale_"+yearl+" on a.distillery_id=int_dist_id and vch_finalize='F' " +
					" order by vch_gatepass_no )b left outer join distillery.fl2_stock_trxn_"+yearl+" c on" +
					" b.vch_gatepass_no=c.vch_gatepass_no )c" +
					" group by c.distillery_id, c.vch_undertaking_name  order by  c.vch_undertaking_name)d left outer join fl2d.indent_for_wholesale e" +
					" on (e.vch_action_taken !='C' or e.vch_action_taken is null ) and e.unit_id=d.distillery_id and e.cr_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and e.finalize_flag='F' and e.vch_type='D' and e.vch_licence_type='FL2' and" +
							" e.total_cases > (select SUM(COALESCE(xd.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  xd where xd.indent_no=e.indent_no ) group by d.distillery_id, d.vch_undertaking_name, d.sale_date1," +
					" d.sale_date2,d.sale_date3,d.sum_sale," +
					" d.avg_sale, d.dispatched_stock  order by d.avg_sale desc;"; 
			
			 System.out.println("===========reportQueryfl=========="+reportQuery);
			}
			else if (act.getRadio().equalsIgnoreCase("BEER"))
			{

			
				
			reportQuery = 	" select d.distillery_id, d.vch_undertaking_name, d.sale_date1," +
					"(select  ceil((box_april+ box_may+ box_june+" +
					" box_july+ box_aug+ box_sep+ box_oct+" +
					" box_nov+ box_dec+ box_jan+box_feb+box_march)/365) " +
					" from distillery.beer_monthly_dispatch_18_19 where brewery_id=d.distillery_id) as avg_sale_prev ," +
					"  (select ceil("+mon[month-1]+"/"+max_days+") from distillery.beer_monthly_dispatch_18_19 where brewery_id=d.distillery_id ) as avg_sale_prev_month ," +
					
					" d.sale_date2,d.sale_date3,d.sum_sale," +
					" d.avg_sale,  (select ceil(sum(bottled/ceil(int_planned_bottles/int_boxes)) ) from bwfl.mst_bottling_plan_"+yearl+" " +
					" where vch_license_type in ('FL3','FL3A') and plan_dt<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and int_brewery_id=d.distillery_id) -(select coalesce((sum(b.dispatchd_box)) ,0) from bwfl.gatepass_to_manufacturewholesale_"+yearl+" a,bwfl.fl1_stock_trxn_"+yearl+" b  where   a.vch_to in ('EXPORT','EOI','FL2A') and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date <=   '"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and a.int_brewery_id=d.distillery_id)- d.dispatched_stock  as closing_stock,(select coalesce((sum(b.dispatchd_box)) ,0) from bwfl.gatepass_to_manufacturewholesale_"+yearl+" a,bwfl.fl1_stock_trxn_"+yearl+" b  where   a.vch_to in ('EXPORT','EOI','FL2A') and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date between '"+ Utility.convertUtilDateToSQLDate(date3)+"' and '"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and a.int_brewery_id=d.distillery_id) as csdexp," +
					
					" (select count(xy.indent_no) from fl2d.indent_for_wholesale xy where xy.unit_id=d.distillery_id and xy.cr_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and xy.vch_licence_type='FL2B' and xy.finalize_flag='F' and xy.vch_type='B'" +
							"and (  xy.vch_action_taken is null or xy.vch_action_taken = 'A' or xy.vch_action_taken = 'O') and xy.total_cases > (select SUM(COALESCE(d.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  d where d.indent_no=xy.indent_no)) as pending, " +
					"(select min(z.cr_date) from fl2d.indent_for_wholesale z where z.unit_id=d.distillery_id and (   z.vch_action_taken = 'A' or z.vch_action_taken = 'O' or z.vch_action_taken is null)  and z.cr_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and z.vch_licence_type='FL2B' and z.finalize_flag='F' and z.vch_type='B' and" +
					" z.total_cases > (select SUM(COALESCE(dd.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  dd where   dd.indent_no=z.indent_no )) as oldest_date,"+
					" coalesce(sum(e.total_cases),0)-(select sum(coalesce(y.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn y" +
					" where y.indent_no in(select xy.indent_no from fl2d.indent_for_wholesale xy where (    xy.vch_action_taken !='C' or xy.vch_action_taken is null  ) and xy.unit_id=d.distillery_id and xy.cr_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and xy.vch_licence_type='FL2B' and  xy.vch_type='B' and xy.finalize_flag='F' and" +
							" xy.total_cases > (select SUM(COALESCE(xd.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  xd where xd.indent_no=xy.indent_no ) ))as indented from" +
					" (select c.distillery_id, c.vch_undertaking_name,sum(c.sale_date1) as sale_date1," +
					" sum(c.sale_date2) as sale_date2,sum(c.sale_date3) as sale_date3,sum(c.sum_sale) as sum_sale," +
					" ceil(sum(c.sum_sale)/"+f+") as avg_sale, sum(c.dispatched_stock) as dispatched_stock from" +
					" (select b.distillery_id, b.vch_undertaking_name, b.vch_gatepass_no,b.dt_date," +
					" case when b.dt_date='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' then c.dispatch_box else 0 end as sale_date1," +
					" case when b.dt_date='"+ Utility.convertUtilDateToSQLDate(date1)+"' then c.dispatch_box else 0  end as sale_date2," +
					" case when b.dt_date='"+ Utility.convertUtilDateToSQLDate(date2)+"' then c.dispatch_box else 0  end  as sale_date3," +
					" case when b.dt_date between '"+ Utility.convertUtilDateToSQLDate(date3)+"' and '"+ Utility.convertUtilDateToSQLDate(act.getDate())+"'" +
					" then c.dispatch_box else 0 end as sum_sale," +
					" case when b.dt_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' then c.dispatch_box else 0  end as dispatched_stock from" +
					" (select  a.distillery_id, a.vch_undertaking_name, vch_gatepass_no, dt_date from" +
					" (select distinct a.brewery_id as distillery_id, b.brewery_nm as vch_undertaking_name from" +
					" distillery.brand_registration_"+yearl+" a, public.bre_mst_b1_lic b" +
					" where a.brewery_id=b.vch_app_id_f and a.vch_license_type in ('FL3','FL3A') order by a.brewery_id )a left outer join" +
					" bwfl.gatepass_to_districtwholesale_"+yearl+" on a.distillery_id=brewery_id and vch_finalize='F' " +
					" order by vch_gatepass_no )b left outer join bwfl.fl2_stock_trxn_"+yearl+" c on" +
					" b.vch_gatepass_no=c.vch_gatepass_no )c" +
					" group by c.distillery_id, c.vch_undertaking_name  order by  c.vch_undertaking_name)d left outer join fl2d.indent_for_wholesale e" +
					" on ( e.vch_action_taken !='C' or e.vch_action_taken is null ) and e.unit_id=d.distillery_id and e.cr_date<='"+ Utility.convertUtilDateToSQLDate(act.getDate())+"' and e.finalize_flag='F' and e.vch_type='B' and e.vch_licence_type='FL2B' and" +
							" e.total_cases > (select SUM(COALESCE(xd.finalize_indent,0)) from fl2d.indent_for_wholesale_trxn  xd where xd.indent_no=e.indent_no ) group by d.distillery_id, d.vch_undertaking_name, d.sale_date1," +
					" d.sale_date2,d.sale_date3,d.sum_sale," +
					" d.avg_sale, d.dispatched_stock order by d.avg_sale desc;"; 
			//System.out.println("===================reportQueryBeer================"+reportQuery);
			
			}
			
		
			else{
				reportQuery= "";
			}

				pst = con.prepareStatement(reportQuery);
				 
				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("radioType",act.getRadio()+" Dispatches to Wholesale(in Cases)");
				parameters.put("month",month_name[month-1]);
				parameters.put("date","Sale on "+Utility.convertUtilDateToSQLDate(act.getDate()));
				
				parameters.put("date1","Sale on "+Utility.convertUtilDateToSQLDate(date1));
				parameters.put("date2","Sale on "+Utility.convertUtilDateToSQLDate(date2));
				
				
				parameters.put("date3",Utility.convertUtilDateToSQLDate(act.getDate()));

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "Sale_in_cases_Report.jasper");
				//jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "FL2_FL2B_CL2_Strength_Report.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "Sale_in_cases_Report" + "-" + n + ".pdf");
				act.setPdfName("Sale_in_cases_Report" + "-" + n + ".pdf");
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
	
	public ArrayList yearListImpl(SaleInCasesAction act) {
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
           // System.out.println("before rs===  "+rs);
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
	
	//==============================================
		public String getDetails(SaleInCasesAction act) {

			Connection con = null;
			PreparedStatement pstmt = null, ps2 = null;
			ResultSet rs = null, rs2 = null;

			try {
				con = ConnectionToDataBase.getConnection();

				String queryList = " SELECT start_dt, end_dt FROM public.reporting_year where " +
						           " value='"+ act.getYear()+ "' ";
			   // System.out.println("========DateValidation===========" + queryList);
				pstmt = con.prepareStatement(queryList);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					act.setStart_dt(rs.getDate("start_dt"));
					act.setEnd_dt(rs.getDate("end_dt"));
					
					 //System.out.println("========StartDate==========" +act.getStart_dt());
					// System.out.println("========EndDate==========" +act.getEnd_dt());
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
		
		
}
