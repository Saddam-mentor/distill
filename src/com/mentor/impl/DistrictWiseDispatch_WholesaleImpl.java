package com.mentor.impl;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mentor.action.DistrictWiseDispatch_WholesaleAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class DistrictWiseDispatch_WholesaleImpl {

	public void printExcel(DistrictWiseDispatch_WholesaleAction act) {

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
		int mth = 0;
		String type = "";
		String reportQuery = "";
		
		Date today = Utility.convertUtilDateToSQLDate(act.getProduction_dt()); // Fri Jun 17 14:54:28 PDT 2016 
		Calendar cal_1 = Calendar.getInstance(); 
		cal_1.setTime(today);
		int month = cal_1.get(Calendar.MONTH); //
		mth =month+1;
		System.out.println("===="+cal_1);
		System.out.println("======month" + today);
		System.out.println("======month" + mth);
		System.out.println("======month" + month);

		Date m1 = Utility.convertUtilDateToSQLDate(act.getProduction_dt());
		Calendar cal = Calendar.getInstance();
		cal.setTime(m1);
		cal.add(Calendar.DATE, -1);
		// int month = cal.get(Calendar.MONTH);
		// /System.out.println("print mounth "+month);
		m1 = cal.getTime();
		System.out.println(m1);
		Date m2 = Utility.convertUtilDateToSQLDate(m1);
		// Calendar cal =Calendar.getInstance();
		cal.setTime(m2);
		cal.add(Calendar.DATE, -1);
		m2 = cal.getTime();
		System.out.println(m2);
		Date m3 = Utility.convertUtilDateToSQLDate(m2);
		// Calendar cal =Calendar.getInstance();
		cal.setTime(m3);
		cal.add(Calendar.DATE, -1);
		m3 = cal.getTime();
		System.out.println(m3);
		Date m4 = Utility.convertUtilDateToSQLDate(m3);
		// Calendar cal =Calendar.getInstance();
		cal.setTime(m4);
		cal.add(Calendar.DATE, -1);
		m4 = cal.getTime();
		System.out.println(m4);
		Date m5 = Utility.convertUtilDateToSQLDate(m4);
		// Calendar cal =Calendar.getInstance();
		cal.setTime(m5);
		cal.add(Calendar.DATE, -1);
		m5 = cal.getTime();
		System.out.println(m5);
		Date m6 = Utility.convertUtilDateToSQLDate(m5);
		// Calendar cal =Calendar.getInstance();
		cal.setTime(m6);
		cal.add(Calendar.DATE, -1);
		m6 = cal.getTime();
		System.out.println(m6);
		Date m7 = Utility.convertUtilDateToSQLDate(m6);
		// Calendar cal =Calendar.getInstance();
		cal.setTime(m7);
		cal.add(Calendar.DATE, -1);
		m7 = cal.getTime();
		System.out.println(m7);
		Date m8 = Utility.convertUtilDateToSQLDate(m7);
		// Calendar cal =Calendar.getInstance();
		cal.setTime(m8);
		cal.add(Calendar.DATE, -1);
		m8 = cal.getTime();
		System.out.println(m8);
		Date m9 = Utility.convertUtilDateToSQLDate(m8);
		// Calendar cal =Calendar.getInstance();
		cal.setTime(m9);
		cal.add(Calendar.DATE, -1);
		m9 = cal.getTime();
		System.out.println(m9);
		Date m10 = Utility.convertUtilDateToSQLDate(m9);
		// Calendar cal =Calendar.getInstance();
		cal.setTime(m10);
		cal.add(Calendar.DATE, -1);
		m10 = cal.getTime();
		System.out.println(m10);
		// int month = localDate.getMonthValue();
		if (act.getRadioType().equalsIgnoreCase("CL")) {
			System.out.println("===its CL value====");

			// type = "Brewery";

			reportQuery = " select x.description,sum(box_a) as box_a,sum(bl_a) as bl_a,sum(box_b) as box_b,sum(bl_b) as bl_b,sum(box_c) as box_c, "
					+ " sum(bl_c) as bl_c,sum(box_d) as box_d,sum(bl_d) as bl_d,sum(box_e) as box_e,sum(bl_e) as bl_e, "
					+ " sum(box_f) as box_f,sum(bl_f) as bl_f, "
					+ " sum(box_g) as box_g,sum(bl_g) as bl_g, "
					+ " sum(cummulative_box) as cummulative_box,sum(cummulative_bl) as cummulative_bl from "
					+ " (select a.description,a.box as box_a,a.bl as bl_a,b.box as box_b,b.bl as bl_b,c.box as box_c,c.bl as bl_c,d.box as box_d,d.bl as bl_d, "
					+ " e.box as box_e,e.bl as bl_e, "
					+ " f.box as box_f,f.bl as bl_f,g.box as box_g,g.bl as bl_g,h.box as cummulative_box,h.bl as  "
					+ " cummulative_bl from  "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(act.getProduction_dt())
					+ "' and a.vch_gatepass_no like '%"
					+ act.getRadioType()
					+ "%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)a, "
					+ "(select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(m1)
					+ "' and a.vch_gatepass_no like '%"
					+ act.getRadioType()
					+ "%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)b,  "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(m2)
					+ "' and a.vch_gatepass_no like '%"
					+ act.getRadioType()
					+ "%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)c, "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(m3)
					+ "' and a.vch_gatepass_no like '%"
					+ act.getRadioType()
					+ "%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)d,  "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(m4)
					+ "' and a.vch_gatepass_no like '%"
					+ act.getRadioType()
					+ "%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)e,  "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(m5)
					+ "' and a.vch_gatepass_no like '%"
					+ act.getRadioType()
					+ "%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)f,  "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(m6)
					+ "' and a.vch_gatepass_no like '%"
					+ act.getRadioType()
					+ "%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)g,  "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '2020-"
					+ mth
					+ "-01' and '"
					+ Utility.convertUtilDateToSQLDate(act.getProduction_dt())
					+ "' and a.vch_gatepass_no like '%"
					+ act.getRadioType()
					+ "%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)h "
					+ " where a.description=b.description and a.description=c.description and a.description=d.description and a.description=e.description and "
					+ " a.description=f.description and a.description=g.description and a.description=h.description and a.description not like '%NA%'  "
					+

					" union all "
					+

					" select a.description,a.box as box_a,a.bl as bl_a,b.box as box_b,b.bl as bl_b,c.box as box_c,c.bl as bl_c,d.box as box_d,d.bl as bl_d,  "
					+ " e.box as box_e,e.bl as bl_e, "
					+ " f.box as box_f,f.bl as bl_f,g.box as box_g,g.bl as bl_g,h.box as cummulative_box,h.bl as  "
					+ " cummulative_bl from  "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2d_id from fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(act.getProduction_dt())
					+ "' and a.vch_to like '%-RT-%' group by quantity,int_pckg_id,a.int_fl2d_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2d_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)a, "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2d_id from fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(m1)
					+ "' and a.vch_to like '%-RT-%' group by quantity,int_pckg_id,a.int_fl2d_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2d_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)b, "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2d_id from fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(m2)
					+ "' and a.vch_to like '%-RT-%' group by quantity,int_pckg_id,a.int_fl2d_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2d_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)c, "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2d_id from fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(m3)
					+ "' and a.vch_to like '%-RT-%' group by quantity,int_pckg_id,a.int_fl2d_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2d_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)d, "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2d_id from fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(m4)
					+ "' and a.vch_to like '%-RT-%' group by quantity,int_pckg_id,a.int_fl2d_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2d_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)e, "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2d_id from fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(m5)
					+ "' and a.vch_to like '%-RT-%' group by quantity,int_pckg_id,a.int_fl2d_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2d_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)f, "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2d_id from fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"
					+ Utility.convertUtilDateToSQLDate(m6)
					+ "' and a.vch_to like '%-RT-%' group by quantity,int_pckg_id,a.int_fl2d_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2d_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)g, "
					+ " (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2d_id from fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '2020-"
					+ mth
					+ "-01' and '"
					+ Utility.convertUtilDateToSQLDate(act.getProduction_dt())
					+ "' and a.vch_to like '%-RT-%' group by quantity,int_pckg_id,a.int_fl2d_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2d_id=n.int_app_id group by vch_distttt)z on description=vch_distttt  order by description)h "
					+ " where a.description=b.description and a.description=c.description and a.description=d.description and a.description=e.description and  "
					+ "  a.description=f.description and a.description=g.description and a.description=h.description and a.description not like '%NA%')x group by x.description order by x.description";

		} else if (act.getRadioType().equalsIgnoreCase("FL")) {
			
			System.out.println("===its fl value====");
			
			reportQuery=" select x.description,sum(box_a) as box_a,sum(bl_a) as bl_a,sum(box_b) as box_b,sum(bl_b) as bl_b,sum(box_c) as box_c,  sum(bl_c) as "+
					    " bl_c,sum(box_d) as box_d,sum(bl_d) as bl_d,sum(box_e) as box_e,sum(bl_e) as bl_e,  sum(box_f) as box_f,sum(bl_f) as bl_f,  sum(box_g) "+
					    " as box_g,sum(bl_g) as bl_g,  sum(cummulative_box) as cummulative_box,sum(cummulative_bl) as cummulative_bl from  (  "+
					    " select a.description,a.box as box_a,a.bl as bl_a,b.box as box_b,b.bl as bl_b,c.box as box_c,c.bl as bl_c,d.box as box_d,d.bl as bl_d,  e.box as box_e,e.bl as bl_e,  f.box as box_f,f.bl as bl_f,g.box as box_g,g.bl as bl_g,h.box as cummulative_box,h.bl as   cummulative_bl from   "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)a, "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m1)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m1)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)b,  "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m2)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m2)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)c,  "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m3)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m3)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)d,  "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m4)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m4)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)e,  "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m5)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m5)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)f,  "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m6)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m6)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)g,  "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '2020-"+mth+"-01' and '"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '2020-"+mth+"-01' and '"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)h "+
					    " where a.description=b.description and a.description=c.description and a.description=d.description and a.description=e.description and  a.description=f.description and a.description=g.description and a.description=h.description and a.description not like '%NA%'    "+
					    " union all  "+
					    " select a.description,a.box as box_a,a.bl as bl_a,b.box as box_b,b.bl as bl_b,c.box as box_c,c.bl as bl_c,d.box as box_d,d.bl as bl_d,  e.box as box_e,e.bl as bl_e,  f.box as box_f,f.bl as bl_f,g.box as box_g,g.bl as bl_g,h.box as cummulative_box,h.bl as   cummulative_bl from   "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)a, "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m1)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m1)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)b,  "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m2)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m2)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)c,  "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m3)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m3)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)d,  "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m4)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m4)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)e,  "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m5)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m5)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)f,  "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m6)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m6)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)g,  "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '2020-"+mth+"-01' and '"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('FL','WINE','IMPORTED WINE','IMPORTED FL'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '2020-"+mth+"-01' and '"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)h "+
					    " where a.description=b.description and a.description=c.description and a.description=d.description and a.description=e.description and  a.description=f.description and a.description=g.description and a.description=h.description and a.description not like '%NA%' )x group by x.description order by x.description ";


		}

		else if (act.getRadioType().equalsIgnoreCase("FL2B")) {
			
			System.out.println("===its beer value====");
			
			reportQuery=" select x.description,sum(box_a) as box_a,sum(bl_a) as bl_a,sum(box_b) as box_b,sum(bl_b) as bl_b,sum(box_c) as box_c,  sum(bl_c) as "+
					    " bl_c,sum(box_d) as box_d,sum(bl_d) as bl_d,sum(box_e) as box_e,sum(bl_e) as bl_e,  sum(box_f) as box_f,sum(bl_f) as bl_f,  sum(box_g) "+
					    " as box_g,sum(bl_g) as bl_g,  sum(cummulative_box) as cummulative_box,sum(cummulative_bl) as cummulative_bl from  (  "+
					    " select a.description,a.box as box_a,a.bl as bl_a,b.box as box_b,b.bl as bl_b,c.box as box_c,c.bl as bl_c,d.box as box_d,d.bl as bl_d,  e.box as box_e,e.bl as bl_e,  f.box as box_f,f.bl as bl_f,g.box as box_g,g.bl as bl_g,h.box as cummulative_box,h.bl as   cummulative_bl from  "+  
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)a,"+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m1)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)b, "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m2)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)c, "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m3)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)d, "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m4)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)e, "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m5)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)f, "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m6)+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)g, "+
					    " (select description , sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '2020-"+mth+"-01' and '"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0  union all select description,box,bottle,bl from district d left outer join (select sum(box) as box,sum(bottle) as bottle,sum(bl) as bl,vch_distttt from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,a.int_fl2_fl2b_id from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '2020-"+mth+"-01' and '"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_gatepass_no like '%"+act.getRadioType()+"%' group by quantity,int_pckg_id,a.int_fl2_fl2b_id)z,licence.fl2_2b_2d_20_21 n where z.int_fl2_fl2b_id=n.int_app_id group by vch_distttt)z on description=vch_distttt where districtid>0)z group by description)h "+
					    " where a.description=b.description and a.description=c.description and a.description=d.description and a.description=e.description and  a.description=f.description and a.description=g.description and a.description=h.description and a.description not like '%NA%'   "+
					    " union all  "+
					    " select a.description,a.box as box_a,a.bl as bl_a,b.box as box_b,b.bl as bl_b,c.box as box_c,c.bl as bl_c,d.box as box_d,d.bl as bl_d,  e.box as box_e,e.bl as bl_e,  f.box as box_f,f.bl as bl_f,g.box as box_g,g.bl as bl_g,h.box as cummulative_box,h.bl as   cummulative_bl from   "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)a, "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m1)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m1)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)b, "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m2)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m2)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)c, "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m3)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m3)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)d, "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m4)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m4)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)e, "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m5)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m5)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)f, "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m6)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date='"+Utility.convertUtilDateToSQLDate(m6)+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)g, "+
					    " (select d.description,box,bottle, bl from district d left outer join ( select description,sum(box) as box,sum(bottle) as bottle,sum(bl)as bl from (select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b,  (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br, distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '2020-"+mth+"-01' and '"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district union select vch_district as description,sum(box) as box,sum(bottle) as bottle,sum(bl) as bl from (select sum(dispatch_box) as box,sum(dispatch_bottle) as bottle,sum(dispatch_bottle)*quantity/1000 as bl,int_pckg_id ,vch_to,shop_id,vch_district from fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20 b, (select quantity,package_id,brand_id_fk,brand_id,liquor_type from distillery.brand_registration_19_20 br,distillery.packaging_details_19_20 pkg  where br.brand_id=pkg.brand_id_fk and br.int_fl2d_id>0 and liquor_type in ('IMPORTED BEER','BEER'))p,retail.retail_shop rt where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '2020-"+mth+"-01' and '"+Utility.convertUtilDateToSQLDate(act.getProduction_dt())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no  group by int_pckg_id ,vch_to,shop_id,quantity,vch_district)x group by vch_district)x group by description)z on d.description=z.description where districtid>0 order by d.description)h "+
					    " where a.description=b.description and a.description=c.description and a.description=d.description and a.description=e.description and  a.description=f.description and a.description=g.description and a.description=h.description and a.description not like '%NA%' )x group by x.description order by x.description ";
    



		} else {

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
			XSSFSheet worksheet = workbook
					.createSheet("District Wise Dispatches Wholesale Report");

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
			worksheet.setColumnWidth(16, 8000);
			worksheet.setColumnWidth(17, 8000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);

			cellhead0.setCellValue("District Wise Dispatches Report  For "
					+ act.getRadioType() + " From (Date"
					+ Utility.convertUtilDateToSQLDate(m7) + " To "
					+ Utility.convertUtilDateToSQLDate(act.getProduction_dt())
					+ ")");

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

			// k = k + 1;
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
			cellhead4_1.setCellValue(""
					+ Utility.convertUtilDateToSQLDate(act.getProduction_dt())
					+ "");
			cellhead4_1.setCellStyle(cellStyle);

			XSSFCell cellhead5_1 = rowhead1.createCell((int) 4);
			cellhead5_1.setCellValue("Date :-");
			cellhead5_1.setCellStyle(cellStyle);

			XSSFCell cellhead6_1 = rowhead1.createCell((int) 5);
			cellhead6_1.setCellValue("" + Utility.convertUtilDateToSQLDate(m1)
					+ "");
			cellhead6_1.setCellStyle(cellStyle);

			XSSFCell cellhead7_1 = rowhead1.createCell((int) 6);
			cellhead7_1.setCellValue("Date :-");
			cellhead7_1.setCellStyle(cellStyle);

			XSSFCell cellhead8_1 = rowhead1.createCell((int) 7);
			cellhead8_1.setCellValue("" + Utility.convertUtilDateToSQLDate(m2)
					+ "");
			cellhead8_1.setCellStyle(cellStyle);

			XSSFCell cellhead9_1 = rowhead1.createCell((int) 8);
			cellhead9_1.setCellValue("Date :-");
			cellhead9_1.setCellStyle(cellStyle);

			XSSFCell cellhead10_1 = rowhead1.createCell((int) 9);
			cellhead10_1.setCellValue("" + Utility.convertUtilDateToSQLDate(m3)
					+ "");
			cellhead10_1.setCellStyle(cellStyle);

			XSSFCell cellhead11_1 = rowhead1.createCell((int) 10);
			cellhead11_1.setCellValue("Date :-");
			cellhead11_1.setCellStyle(cellStyle);

			XSSFCell cellhead12_1 = rowhead1.createCell((int) 11);
			cellhead12_1.setCellValue("" + Utility.convertUtilDateToSQLDate(m4)
					+ "");
			cellhead12_1.setCellStyle(cellStyle);

			XSSFCell cellhead13_1 = rowhead1.createCell((int) 12);
			cellhead13_1.setCellValue("Date :-");
			cellhead13_1.setCellStyle(cellStyle);

			XSSFCell cellhead14_1 = rowhead1.createCell((int) 13);
			cellhead14_1.setCellValue("" + Utility.convertUtilDateToSQLDate(m5)
					+ "");
			cellhead14_1.setCellStyle(cellStyle);

			XSSFCell cellhead15_1 = rowhead1.createCell((int) 14);
			cellhead15_1.setCellValue("Date :-");
			cellhead15_1.setCellStyle(cellStyle);

			XSSFCell cellhead16_1 = rowhead1.createCell((int) 15);
			cellhead16_1.setCellValue("" + Utility.convertUtilDateToSQLDate(m6)
					+ "");
			cellhead16_1.setCellStyle(cellStyle);

			XSSFCell cellhead21_1 = rowhead1.createCell((int) 16);
			cellhead21_1.setCellValue(" ");
			cellhead21_1.setCellStyle(cellStyle);

			XSSFCell cellhead22_1 = rowhead1.createCell((int) 17);
			cellhead22_1.setCellValue("");
			cellhead22_1.setCellStyle(cellStyle);

			XSSFRow rowhead = worksheet.createRow((int) 2);

			XSSFCell cellhead1 = rowhead.createCell((int) 0);
			cellhead1.setCellValue("Sr. No");
			cellhead1.setCellStyle(cellStyle);

			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("District Name");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Box");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("BL");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Box");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("BL");
			cellhead6.setCellStyle(cellStyle);

			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Box");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("BL");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("Box");
			cellhead9.setCellStyle(cellStyle);

			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("BL");
			cellhead10.setCellStyle(cellStyle);

			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("Box");
			cellhead11.setCellStyle(cellStyle);

			XSSFCell cellhead12 = rowhead.createCell((int) 11);
			cellhead12.setCellValue("BL");
			cellhead12.setCellStyle(cellStyle);

			XSSFCell cellhead13 = rowhead.createCell((int) 12);
			cellhead13.setCellValue("Box");
			cellhead13.setCellStyle(cellStyle);

			XSSFCell cellhead14 = rowhead.createCell((int) 13);
			cellhead14.setCellValue("BL");
			cellhead14.setCellStyle(cellStyle);

			XSSFCell cellhead15 = rowhead.createCell((int) 14);
			cellhead15.setCellValue("Box");
			cellhead15.setCellStyle(cellStyle);

			XSSFCell cellhead16 = rowhead.createCell((int) 15);
			cellhead16.setCellValue("BL");
			cellhead16.setCellStyle(cellStyle);

			XSSFCell cellhead17 = rowhead.createCell((int) 16);
			cellhead17.setCellValue("Month Cumulative Box");
			cellhead17.setCellStyle(cellStyle);

			XSSFCell cellhead18 = rowhead.createCell((int) 17);
			cellhead18.setCellValue("Month Cumulative BL");
			cellhead18.setCellStyle(cellStyle);

			k = k + 2;
			int i = 0;

			while (rs.next()) {
				total_bl1 = total_bl1 + rs.getDouble("bl_a");
				total_box1 = total_box1 + rs.getInt("box_a");
				total_bl2 = total_bl2 + rs.getDouble("bl_b");
				total_box2 = total_box2 + rs.getInt("box_b");
				total_bl3 = total_bl3 + rs.getDouble("bl_c");
				total_box3 = total_box3 + rs.getInt("box_c");
				total_bl4 = total_bl4 + rs.getDouble("bl_d");
				total_box4 = total_box4 + rs.getInt("box_d");
				total_bl5 = total_bl5 + rs.getDouble("bl_e");
				total_box5 = total_box5 + rs.getInt("box_e");
				total_bl6 = total_bl6 + rs.getDouble("bl_f");
				total_box6 = total_box6 + rs.getInt("box_f");
				total_bl7 = total_bl7 + rs.getDouble("bl_g");
				total_box7 = total_box7 + rs.getInt("box_g");
				total_bl8 = total_bl8 + rs.getDouble("cummulative_bl");
				total_box8 = total_box8 + rs.getInt("cummulative_box");
				/*
				 * total_bl9 = total_bl9 + rs.getDouble("FL_bl_i"); total_box9 =
				 * total_box9 + rs.getInt("FL_box_i"); total_bl10 = total_bl10 +
				 * rs.getDouble("FL_bl_j"); total_box10 = total_box10 +
				 * rs.getInt("FL_box_j");
				 */

				i++;
				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				XSSFCell cellA1 = row1.createCell((int) 0); //
				cellA1.setCellValue(k - 2); //

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getDouble("box_a"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getDouble("bl_a"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getDouble("box_b"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("bl_b"));

				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getDouble("box_c"));

				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("bl_c"));

				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getDouble("box_d"));

				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getDouble("bl_d"));

				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getDouble("box_e"));

				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(rs.getDouble("bl_e"));

				XSSFCell cellM1 = row1.createCell((int) 12);
				cellM1.setCellValue(rs.getDouble("box_f"));

				XSSFCell cellN1 = row1.createCell((int) 13);
				cellN1.setCellValue(rs.getDouble("bl_f"));

				XSSFCell cellO1 = row1.createCell((int) 14);
				cellO1.setCellValue(rs.getDouble("box_g"));

				XSSFCell cellP1 = row1.createCell((int) 15);
				cellP1.setCellValue(rs.getDouble("bl_g"));

				XSSFCell cellQ1 = row1.createCell((int) 16);
				cellQ1.setCellValue(rs.getDouble("cummulative_box"));

				XSSFCell cellR1 = row1.createCell((int) 17);
				cellR1.setCellValue(rs.getDouble("cummulative_bl"));

				// System.out.println("------------ "+Math.round(cases_of_last_year_sale));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "_DISTRICT_WISE_DISPATCHS_WHOLESALE.xls");

			act.setExlname(n + "_DISTRICT_WISE_DISPATCHS_WHOLESALE.xls");
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

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			act.setExcelFlag(true);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
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
