package com.mentor.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import com.mentor.action.ProductionAndDispatchesDetailsAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Utility;

public class ProductionAndDispatchesDetailsImpl {

	public void getbasicdtlSecond(ProductionAndDispatchesDetailsAction ac) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			/*String queryList = 	" SELECT x.dt, SUM(x.cl_dispatchd_bottl) as cl_dispatchd_bottl, " +
								" SUM(x.cl_box) as cl_box, SUM(x.cl_sale) as cl_sale, SUM(x.cl_duty) as cl_duty, " +
								" SUM(x.fl_dispatch_bottle) as fl_dispatch_bottle, " +
								" SUM(x.fl_box) as fl_box, SUM(x.fl_sale) as fl_sale, SUM(x.fl_duty) as fl_duty, " +
								" SUM(x.beer_dispatch_bottle) as beer_dispatch_bottle, " +
								" SUM(x.beer_box) as beer_box, SUM(x.beer_sale) as beer_sale, SUM(x.beer_duty) as beer_duty, " +
								" (SUM(x.cl_duty) + SUM(x.fl_duty) + SUM(x.beer_duty)) as duty   " +
								" FROM " +
								" (SELECT a.dt_date as dt, SUM(a.dispatchd_bottl) as cl_dispatchd_bottl, " +
								" SUM(a.dispatchd_box) as cl_box, " +
								" SUM(ROUND(CAST(float8(((a.dispatchd_bottl)*j.quantity)/1000) as numeric), 2)) as cl_sale,  " +
								" SUM(ROUND(CAST(float8(a.duty + a.addduty) as numeric), 2)) as cl_duty, " +
								" 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale, 0 as fl_duty,  " +
								" 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, 0 as beer_duty " +
								" FROM distillery.cl2_stock_trxn a, distillery.packaging_details j  " +
								" WHERE  a.dt_date='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" AND  a.int_pckg_id=j.package_id  " +
								" GROUP BY a.dt_date  " +
								" UNION  " +
								" SELECT b.dt as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale, 0 as cl_duty, " +
								" SUM(b.dispatch_bottle) as fl_dispatch_bottle, SUM(b.dispatch_box) as fl_box,  " +
								" SUM(round( CAST(float8(((b.dispatch_bottle)*k.quantity)/1000) as numeric), 2)) as fl_sale, " +
								" 0 as fl_duty, 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, 0 as beer_duty " +
								" FROM distillery.fl2_stock_trxn b, distillery.packaging_details k  " +
								" WHERE b.dt='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" AND b.int_pckg_id=k.package_id " +
								" GROUP BY b.dt  " +
								" UNION  " +
								" SELECT e.dt_date as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale, " +
								" 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale,  " +
								" SUM(ROUND(CAST(float8(d.duty + d.addduty) as numeric), 2)) as fl_duty, " +
								" 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, 0 as beer_duty " +
								" FROM distillery.fl1_stock_trxn d,  distillery.gatepass_to_manufacturewholesale e  " +
								" WHERE  d.int_dissleri_id=e.int_dist_id AND d.vch_gatepass_no=e.vch_gatepass_no " +
								" AND e.dt_date='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" GROUP BY e.dt_date  " +
								" UNION " +
								" SELECT h.dt as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale, " +
								" 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale, 0 as fl_duty, " +
								" SUM(h.dispatch_bottle) as beer_dispatch_bottle, SUM(h.dispatch_box) as beer_box, " +
								" SUM(round(CAST(float8(((h.dispatch_bottle)*l.quantity)/1000) as numeric), 2)) as beer_sale, " +
								" 0 as beer_duty " +
								" FROM bwfl.fl2_stock_trxn h, distillery.packaging_details l " +
								" WHERE h.dt='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" AND h.int_pckg_id=l.package_id " +
								" GROUP BY h.dt  " +
								" UNION  " +
								" SELECT g.dt_date as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale, " +
								" 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale, 0 as fl_duty, " +
								" 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, " +
								" SUM(ROUND(CAST(float8(f.duty + f.addduty) as numeric), 2)) as beer_duty  " +
								" FROM bwfl.fl1_stock_trxn f, bwfl.gatepass_to_manufacturewholesale g  " +
								" WHERE f.int_brewery_id=g.int_brewery_id AND f.vch_gatepass_no=g.vch_gatepass_no " +
								" AND g.dt_date='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" GROUP BY g.dt_date )x " +
								" GROUP BY x.dt ORDER BY x.dt ";*/

			
			String queryList = 	" SELECT x.dt, SUM(x.cl_dispatchd_bottl) as cl_dispatchd_bottl, " +
								" SUM(x.cl_box) as cl_box, SUM(x.cl_sale) as cl_sale, SUM(x.cl_duty) as cl_duty, " +
					
								" SUM(x.fl_dispatch_bottle) as fl_dispatch_bottle, " +
								" SUM(x.fl_box) as fl_box, SUM(x.fl_sale) as fl_sale, SUM(x.fl_duty) as fl_duty, " +
								
								" SUM(x.beer_dispatch_bottle) as beer_dispatch_bottle, " +
								" SUM(x.beer_box) as beer_box, SUM(x.beer_sale) as beer_sale, SUM(x.beer_duty) as beer_duty, " +
								
								" SUM(x.bwfl2a_dispatchd_bottl) as bwfl2a_dispatchd_bottl, SUM(x.bwfl2a_box) as bwfl2a_box, " +
								" SUM(x.bwfl2a_sale) as bwfl2a_sale, SUM(x.bwfl2a_duty) as bwfl2a_duty, " +
								
								" SUM(x.bwfl2b_dispatchd_bottl) as bwfl2b_dispatchd_bottl, SUM(x.bwfl2b_box) as bwfl2b_box,  " +
								" SUM(x.bwfl2b_sale) as bwfl2b_sale, SUM(x.bwfl2b_duty) as bwfl2b_duty, " +
								
								" SUM(x.bwfl2c_dispatchd_bottl) as bwfl2c_dispatchd_bottl, SUM(x.bwfl2c_box) as bwfl2c_box,  " +
								" SUM(x.bwfl2c_sale) as bwfl2c_sale, SUM(x.bwfl2c_duty) as bwfl2c_duty, " +
								
								" SUM(x.bwfl2d_dispatchd_bottl) as bwfl2d_dispatchd_bottl, SUM(x.bwfl2d_box) as bwfl2d_box,  " +
								" SUM(x.bwfl2d_sale) as bwfl2d_sale, SUM(x.bwfl2d_duty) as bwfl2d_duty, " +
								
								" SUM(x.fl2d_dispatchd_bottl) as fl2d_dispatchd_bottl, SUM(x.fl2d_box) as fl2d_box,  " +
								" SUM(x.fl2d_sale) as fl2d_sale, SUM(x.fl2d_duty) as fl2d_duty, " +
								
								" (SUM(x.cl_duty)+SUM(x.fl_duty)+SUM(x.beer_duty)+SUM(x.bwfl2a_duty)+SUM(x.bwfl2b_duty)+  " +
								" SUM(x.bwfl2c_duty)+SUM(x.bwfl2d_duty)+SUM(x.fl2d_duty)) as total_duty " +
								" FROM " +
								" (SELECT a.dt_date as dt, SUM(a.dispatchd_bottl) as cl_dispatchd_bottl, " +
								" SUM(a.dispatchd_box) as cl_box, " +
								" SUM(ROUND(CAST(float8(((a.dispatchd_bottl)*j.quantity)/1000) as numeric), 2)) as cl_sale,  " +
								" SUM(ROUND(CAST(float8(a.duty + a.addduty) as numeric), 2)) as cl_duty, " +
								" 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale, 0 as fl_duty,  " +
								" 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, 0 as beer_duty," +
								" 0 as bwfl2a_dispatchd_bottl, 0 as bwfl2a_box, 0 as bwfl2a_sale, 0 as bwfl2a_duty, " +
								" 0 as bwfl2b_dispatchd_bottl, 0 as bwfl2b_box, 0 as bwfl2b_sale, 0 as bwfl2b_duty, " +
								" 0 as bwfl2c_dispatchd_bottl, 0 as bwfl2c_box, 0 as bwfl2c_sale, 0 as bwfl2c_duty, " +
								" 0 as bwfl2d_dispatchd_bottl, 0 as bwfl2d_box, 0 as bwfl2d_sale, 0 as bwfl2d_duty, " +
								" 0 as fl2d_dispatchd_bottl, 0 as fl2d_box, 0 as fl2d_sale, 0 as fl2d_duty " +
								" FROM distillery.cl2_stock_trxn a, distillery.packaging_details j  " +
								" WHERE  a.dt_date='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" AND  a.int_pckg_id=j.package_id  " +
								" GROUP BY a.dt_date  " +
								" UNION  " +								
								" SELECT b.dt as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale, 0 as cl_duty, " +
								" SUM(b.dispatch_bottle) as fl_dispatch_bottle, SUM(b.dispatch_box) as fl_box,  " +
								" SUM(round( CAST(float8(((b.dispatch_bottle)*k.quantity)/1000) as numeric), 2)) as fl_sale, " +
								" 0 as fl_duty, 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, 0 as beer_duty, " +
								" 0 as bwfl2a_dispatchd_bottl, 0 as bwfl2a_box, 0 as bwfl2a_sale, 0 as bwfl2a_duty, " +
								" 0 as bwfl2b_dispatchd_bottl, 0 as bwfl2b_box, 0 as bwfl2b_sale, 0 as bwfl2b_duty, " +
								" 0 as bwfl2c_dispatchd_bottl, 0 as bwfl2c_box, 0 as bwfl2c_sale, 0 as bwfl2c_duty, " +
								" 0 as bwfl2d_dispatchd_bottl, 0 as bwfl2d_box, 0 as bwfl2d_sale, 0 as bwfl2d_duty, " +
								" 0 as fl2d_dispatchd_bottl, 0 as fl2d_box, 0 as fl2d_sale, 0 as fl2d_duty " +
								" FROM distillery.fl2_stock_trxn b, distillery.packaging_details k  " +
								" WHERE b.dt='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" AND b.int_pckg_id=k.package_id " +
								" GROUP BY b.dt  " +
								" UNION  " +
								" SELECT e.dt_date as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale, " +
								" 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale,  " +
								" SUM(ROUND(CAST(float8(d.duty + d.addduty) as numeric), 2)) as fl_duty, " +
								" 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, 0 as beer_duty, " +
								" 0 as bwfl2a_dispatchd_bottl, 0 as bwfl2a_box, 0 as bwfl2a_sale, 0 as bwfl2a_duty, " +
								" 0 as bwfl2b_dispatchd_bottl, 0 as bwfl2b_box, 0 as bwfl2b_sale, 0 as bwfl2b_duty, " +
								" 0 as bwfl2c_dispatchd_bottl, 0 as bwfl2c_box, 0 as bwfl2c_sale, 0 as bwfl2c_duty, " +
								" 0 as bwfl2d_dispatchd_bottl, 0 as bwfl2d_box, 0 as bwfl2d_sale, 0 as bwfl2d_duty, " +
								" 0 as fl2d_dispatchd_bottl, 0 as fl2d_box, 0 as fl2d_sale, 0 as fl2d_duty " +
								" FROM distillery.fl1_stock_trxn d,  distillery.gatepass_to_manufacturewholesale e  " +
								" WHERE  d.int_dissleri_id=e.int_dist_id AND d.vch_gatepass_no=e.vch_gatepass_no " +
								" AND e.dt_date='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" GROUP BY e.dt_date  " +
								" UNION " +
								" SELECT h.dt as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale, " +
								" 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale, 0 as fl_duty, " +
								" SUM(h.dispatch_bottle) as beer_dispatch_bottle, SUM(h.dispatch_box) as beer_box, " +
								" SUM(round(CAST(float8(((h.dispatch_bottle)*l.quantity)/1000) as numeric), 2)) as beer_sale, " +
								" 0 as beer_duty, " +
								" 0 as bwfl2a_dispatchd_bottl, 0 as bwfl2a_box, 0 as bwfl2a_sale, 0 as bwfl2a_duty, " +
								" 0 as bwfl2b_dispatchd_bottl, 0 as bwfl2b_box, 0 as bwfl2b_sale, 0 as bwfl2b_duty, " +
								" 0 as bwfl2c_dispatchd_bottl, 0 as bwfl2c_box, 0 as bwfl2c_sale, 0 as bwfl2c_duty, " +
								" 0 as bwfl2d_dispatchd_bottl, 0 as bwfl2d_box, 0 as bwfl2d_sale, 0 as bwfl2d_duty, " +
								" 0 as fl2d_dispatchd_bottl, 0 as fl2d_box, 0 as fl2d_sale, 0 as fl2d_duty " +
								" FROM bwfl.fl2_stock_trxn h, distillery.packaging_details l " +
								" WHERE h.dt='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" AND h.int_pckg_id=l.package_id " +
								" GROUP BY h.dt  " +
								" UNION  " +
								" SELECT g.dt_date as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale, " +
								" 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale, 0 as fl_duty, " +
								" 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, " +
								" SUM(ROUND(CAST(float8(f.duty + f.addduty) as numeric), 2)) as beer_duty,  " +
								" 0 as bwfl2a_dispatchd_bottl, 0 as bwfl2a_box, 0 as bwfl2a_sale, 0 as bwfl2a_duty, " +
								" 0 as bwfl2b_dispatchd_bottl, 0 as bwfl2b_box, 0 as bwfl2b_sale, 0 as bwfl2b_duty, " +
								" 0 as bwfl2c_dispatchd_bottl, 0 as bwfl2c_box, 0 as bwfl2c_sale, 0 as bwfl2c_duty, " +
								" 0 as bwfl2d_dispatchd_bottl, 0 as bwfl2d_box, 0 as bwfl2d_sale, 0 as bwfl2d_duty, " +
								" 0 as fl2d_dispatchd_bottl, 0 as fl2d_box, 0 as fl2d_sale, 0 as fl2d_duty " +
								" FROM bwfl.fl1_stock_trxn f, bwfl.gatepass_to_manufacturewholesale g  " +
								" WHERE f.int_brewery_id=g.int_brewery_id AND f.vch_gatepass_no=g.vch_gatepass_no " +
								" AND g.dt_date='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" GROUP BY g.dt_date " +
								" UNION " +
								" SELECT s.dt as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale,  " +
								" 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale, 0 as fl_duty, " + 
								" 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, 0 as beer_duty, " +
								" SUM(s.dispatch_bottle) as bwfl2a_dispatchd_bottl,  SUM(s.dispatch_box) as bwfl2a_box, " +
								" SUM(round(CAST(float8(((s.dispatch_bottle)*t.quantity)/1000) as numeric), 2)) as bwfl2a_sale, " +
								" SUM(ROUND(CAST(float8(s.duty+s.add_duty) as numeric), 2)) as bwfl2a_duty, " +
								" 0 as bwfl2b_dispatchd_bottl, 0 as bwfl2b_box, 0 as bwfl2b_sale, 0 as bwfl2b_duty, "+
								" 0 as bwfl2c_dispatchd_bottl, 0 as bwfl2c_box, 0 as bwfl2c_sale, 0 as bwfl2c_duty, "+
								" 0 as bwfl2d_dispatchd_bottl, 0 as bwfl2d_box, 0 as bwfl2d_sale, 0 as bwfl2d_duty, "+
								" 0 as fl2d_dispatchd_bottl, 0 as fl2d_box, 0 as fl2d_sale, 0 as fl2d_duty " +
								" FROM bwfl_license.fl2_stock_trxn_bwfl s, distillery.packaging_details t, " +
								" bwfl.registration_of_bwfl_lic_holder w  " +
								" WHERE s.dt='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"'  " +
								" AND s.int_pckg_id=t.package_id AND s.int_bwfl_id=w.int_id AND w.vch_license_type='1'  " +
								" GROUP BY s.dt " +
								" UNION " +
								" SELECT s.dt as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale,  " +
								" 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale, 0 as fl_duty, " + 
								" 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, 0 as beer_duty, " +
								" 0 as bwfl2a_dispatchd_bottl, 0 as bwfl2a_box, 0 as bwfl2a_sale, 0 as bwfl2a_duty, "+
								" SUM(s.dispatch_bottle) as bwfl2b_dispatchd_bottl,  SUM(s.dispatch_box) as bwfl2b_box, " +
								" SUM(round(CAST(float8(((s.dispatch_bottle)*t.quantity)/1000) as numeric), 2)) as bwfl2b_sale, " +
								" SUM(ROUND(CAST(float8(s.duty+s.add_duty) as numeric), 2)) as bwfl2b_duty, " +								
								" 0 as bwfl2c_dispatchd_bottl, 0 as bwfl2c_box, 0 as bwfl2c_sale, 0 as bwfl2c_duty, "+
								" 0 as bwfl2d_dispatchd_bottl, 0 as bwfl2d_box, 0 as bwfl2d_sale, 0 as bwfl2d_duty, "+
								" 0 as fl2d_dispatchd_bottl, 0 as fl2d_box, 0 as fl2d_sale, 0 as fl2d_duty " +
								" FROM bwfl_license.fl2_stock_trxn_bwfl s, distillery.packaging_details t, " +
								" bwfl.registration_of_bwfl_lic_holder w  " +
								" WHERE s.dt='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"'  " +
								" AND s.int_pckg_id=t.package_id AND s.int_bwfl_id=w.int_id AND w.vch_license_type='2'  " +
								" GROUP BY s.dt " +
								" UNION " +
								" SELECT s.dt as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale,  " +
								" 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale, 0 as fl_duty, " + 
								" 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, 0 as beer_duty, " +
								" 0 as bwfl2a_dispatchd_bottl, 0 as bwfl2a_box, 0 as bwfl2a_sale, 0 as bwfl2a_duty, "+
								" 0 as bwfl2b_dispatchd_bottl, 0 as bwfl2b_box, 0 as bwfl2b_sale, 0 as bwfl2b_duty, "+
								" SUM(s.dispatch_bottle) as bwfl2c_dispatchd_bottl,  SUM(s.dispatch_box) as bwfl2c_box, " +
								" SUM(round(CAST(float8(((s.dispatch_bottle)*t.quantity)/1000) as numeric), 2)) as bwfl2c_sale, " +
								" SUM(ROUND(CAST(float8(s.duty+s.add_duty) as numeric), 2)) as bwfl2c_duty, " +								
								" 0 as bwfl2d_dispatchd_bottl, 0 as bwfl2d_box, 0 as bwfl2d_sale, 0 as bwfl2d_duty, "+
								" 0 as fl2d_dispatchd_bottl, 0 as fl2d_box, 0 as fl2d_sale, 0 as fl2d_duty " +
								" FROM bwfl_license.fl2_stock_trxn_bwfl s, distillery.packaging_details t, " +
								" bwfl.registration_of_bwfl_lic_holder w  " +
								" WHERE s.dt='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"'  " +
								" AND s.int_pckg_id=t.package_id AND s.int_bwfl_id=w.int_id AND w.vch_license_type='3'  " +
								" GROUP BY s.dt " +
								" UNION " +
								" SELECT s.dt as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale,  " +
								" 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale, 0 as fl_duty, " + 
								" 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, 0 as beer_duty, " +
								" 0 as bwfl2a_dispatchd_bottl, 0 as bwfl2a_box, 0 as bwfl2a_sale, 0 as bwfl2a_duty, "+
								" 0 as bwfl2b_dispatchd_bottl, 0 as bwfl2b_box, 0 as bwfl2b_sale, 0 as bwfl2b_duty, "+
								" 0 as bwfl2c_dispatchd_bottl, 0 as bwfl2c_box, 0 as bwfl2c_sale, 0 as bwfl2c_duty, "+
								" SUM(s.dispatch_bottle) as bwfl2d_dispatchd_bottl,  SUM(s.dispatch_box) as bwfl2d_box, " +
								" SUM(round(CAST(float8(((s.dispatch_bottle)*t.quantity)/1000) as numeric), 2)) as bwfl2d_sale, " +
								" SUM(ROUND(CAST(float8(s.duty+s.add_duty) as numeric), 2)) as bwfl2d_duty, " +								
								" 0 as fl2d_dispatchd_bottl, 0 as fl2d_box, 0 as fl2d_sale, 0 as fl2d_duty " +
								" FROM bwfl_license.fl2_stock_trxn_bwfl s, distillery.packaging_details t, " +
								" bwfl.registration_of_bwfl_lic_holder w  " +
								" WHERE s.dt='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"'  " +
								" AND s.int_pckg_id=t.package_id AND s.int_bwfl_id=w.int_id AND w.vch_license_type='4'  " +
								" GROUP BY s.dt " +
								" UNION " +
								" SELECT u.dt as dt, 0 as cl_dispatchd_bottl, 0 as cl_box, 0 as cl_sale,  " + 
								" 0 as cl_duty, 0 as fl_dispatch_bottle, 0 as fl_box, 0 as fl_sale, 0 as fl_duty,   " +
								" 0 as beer_dispatch_bottle, 0 as beer_box, 0 as beer_sale, 0 as beer_duty, " +
								" 0 as bwfl2a_dispatchd_bottl, 0 as bwfl2a_box, 0 as bwfl2a_sale, 0 as bwfl2a_duty, " +
								" 0 as bwfl2b_dispatchd_bottl, 0 as bwfl2b_box, 0 as bwfl2b_sale, 0 as bwfl2b_duty, " +
								" 0 as bwfl2c_dispatchd_bottl, 0 as bwfl2c_box, 0 as bwfl2c_sale, 0 as bwfl2c_duty, " +
								" 0 as bwfl2d_dispatchd_bottl, 0 as bwfl2d_box, 0 as bwfl2d_sale, 0 as bwfl2d_duty, " +
								" SUM(u.dispatch_bottle) as fl2d_dispatchd_bottl, SUM(u.dispatch_box) as fl2d_box, " +
								" SUM(ROUND(CAST(float8(((u.dispatch_bottle)*v.quantity)/1000) as numeric), 2)) as fl2d_sale, " +
								" SUM(ROUND(CAST(float8(u.duty+u.cal_duty) as numeric), 2)) as fl2d_duty " +
								" FROM fl2d.fl2d_stock_trxn u, distillery.packaging_details v " +
								" WHERE u.dt='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" AND u.int_pckg_id=v.package_id  " +
								" GROUP BY u.dt )x " +
								" GROUP BY x.dt ORDER BY x.dt ";
			
			
			
			String queryList1= 	" SELECT x.dt, SUM(x.cl_bl) as cl_bl, SUM(x.cl_bottles) as cl_bottles, " +
								" SUM(x.cl_box) as cl_box, SUM(x.fl_bl) as fl_bl, SUM(x.fl_bottles) as fl_bottles, " +
								" SUM(x.fl_box) as fl_box, SUM(x.beer_bl) as beer_bl, SUM(x.beer_bottles) as beer_bottles, " +
								" SUM(x.beer_box) as beer_box, SUM(x.cl_duty+x.fl_duty +x.beer_duty) as duty " +
								" FROM " +
								" (SELECT a.date_currunt_date as dt, SUM(b.int_no_bottle) as fl_bottles, " +
								" SUM(b.double_quantity_bl) as fl_bl, " +
								" SUM(ROUND(CAST(float8(a.double_duty) as numeric), 2)) as fl_duty, " +
								" SUM(ROUND((b.int_no_bottle/c.box_size)+0.4))as fl_box, " +
								" 0 as cl_bottles, 0 as cl_bl, 0 as cl_duty, 0 as cl_box, 0 as beer_bottles, " +
								" 0 as beer_bl, 0 as beer_duty, 0 as beer_box " +
								" FROM distillery.bottling_master a, distillery.bottling_dtl b, distillery.box_size_details c " +
								" WHERE a.int_id=b.bottoling_masterid_fk AND a.int_dissleri_id=b.int_dissleri_id " +
								" AND b.double_quantity=c.qnt_ml_detail " +
								" AND a.date_currunt_date='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" GROUP BY a.date_currunt_date " +
								" UNION " +
								" SELECT a.date_currunt_date as dt, 0 as fl_bottles, 0 as fl_bl, 0 as fl_duty, 0 as fl_box, " +
								" SUM(b.int_no_bottle) as cl_bottles, SUM(b.double_quantity_bl) as cl_bl,  " +
								" SUM(ROUND(CAST(float8(a.double_duty) as numeric), 2)) as cl_duty, " +
								" SUM(ROUND((b.int_no_bottle/c.box_size)+0.4))as cl_box, " +
								" 0 as beer_bottles, 0 as beer_bl, 0 as beer_duty, 0 as beer_box " +
								" FROM distillery.bottling_master_cl a, distillery.bottling_dtl_cl b, distillery.box_size_details c " +
								" WHERE a.int_id=b.bottoling_masterid_fk AND a.int_dissleri_id=b.int_dissleri_id " +
								" AND b.double_quantity=c.qnt_ml_detail " +
								" AND a.date_currunt_date='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" GROUP BY a.date_currunt_date " +
								" UNION " +
								" SELECT a.date_currunt_date as dt, 0 as fl_bottles, 0 as fl_bl, 0 as fl_duty, " +
								" 0 as fl_box, 0 as cl_bottles, 0 as cl_bl, 0 as cl_duty, 0 as cl_box, " +
								" SUM(b.int_no_bottle) as beer_bottles, SUM(b.double_quantity_bl) as beer_bl, " +
								" SUM(ROUND(CAST(float8(a.double_duty) as numeric), 2)) as beer_duty, " +
								" SUM(ROUND((b.int_no_bottle/c.box_size)+0.4))as beer_box " +
								" FROM bwfl.bottling_master a, bwfl.bottling_dtl b, distillery.box_size_details c " +
								" WHERE a.int_id=b.bottoling_masterid_fk AND a.int_brewery_id=b.int_brewery_id " +
								" AND b.double_quantity=c.qnt_ml_detail " +
								" AND a.date_currunt_date='"+ Utility.convertUtilDateToSQLDate(ac.getDate_dt()) +"' " +
								" GROUP BY a.date_currunt_date )x " +
								" GROUP BY x.dt ORDER BY x.dt "; 

			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(queryList);
			//System.out.println("dispatch-------------"+queryList);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				ac.setBl_cl(rs.getDouble("cl_sale"));
				ac.setBl_fl(rs.getDouble("fl_sale"));
				ac.setBl_beer(rs.getDouble("beer_sale"));
				ac.setCases_cl(rs.getInt("cl_box"));
				ac.setCases_fl(rs.getInt("fl_box"));
				ac.setCases_beer(rs.getInt("beer_box"));
				ac.setBottles_cl(rs.getInt("cl_dispatchd_bottl"));
				ac.setBottles_fl(rs.getInt("fl_dispatch_bottle"));
				ac.setBottles_beer(rs.getInt("beer_dispatch_bottle"));						
				ac.setBl_bwfl2a(rs.getDouble("bwfl2a_sale"));
				ac.setCases_bwfl2a(rs.getInt("bwfl2a_box"));
				ac.setBottles_bwfl2a(rs.getInt("bwfl2a_dispatchd_bottl"));
				ac.setBl_bwfl2b(rs.getDouble("bwfl2b_sale"));
				ac.setCases_bwfl2b(rs.getInt("bwfl2b_box"));
				ac.setBottles_bwfl2b(rs.getInt("bwfl2b_dispatchd_bottl"));
				ac.setBl_bwfl2c(rs.getDouble("bwfl2c_sale"));
				ac.setCases_bwfl2c(rs.getInt("bwfl2c_box"));
				ac.setBottles_bwfl2c(rs.getInt("bwfl2c_dispatchd_bottl"));
				ac.setBl_bwfl2d(rs.getDouble("bwfl2d_sale"));
				ac.setCases_bwfl2d(rs.getInt("bwfl2d_box"));
				ac.setBottles_bwfl2d(rs.getInt("bwfl2d_dispatchd_bottl"));				
				ac.setBl_fl2d(rs.getDouble("fl2d_sale"));
				ac.setCases_fl2d(rs.getInt("fl2d_box"));
				ac.setBottles_fl2d(rs.getInt("fl2d_dispatchd_bottl"));				
				ac.setTempdue1(rs.getDouble("total_duty"));
				//System.out.println("-------------rs value"+ac.getTempdue1());

			}

			pstmt = con.prepareStatement(queryList1);
			//System.out.println("production-------------"+queryList1);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				ac.setBl_cl2(rs.getDouble("cl_bl"));
				ac.setBl_fl2(rs.getDouble("fl_bl"));
				ac.setBl_beer2(rs.getDouble("beer_bl"));
				ac.setCases_cl2(rs.getInt("cl_box"));
				ac.setCases_fl2(rs.getInt("fl_box"));
				ac.setCases_beer2(rs.getInt("beer_box"));
				ac.setBottles_cl2(rs.getInt("cl_bottles"));
				ac.setBottles_fl2(rs.getInt("fl_bottles"));
				ac.setBottles_beer2(rs.getInt("beer_bottles"));				
				ac.setTempdue2(rs.getDouble("duty"));

			}
			DecimalFormat df = new DecimalFormat("####0.00");

			//ac.setDue(df.format(new BigDecimal(ac.getTempdue1()+ ac.getTempdue2())));
			ac.setDue(df.format(new BigDecimal(ac.getTempdue1())));
			

			this.deposit1(ac);
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public void deposit1(ProductionAndDispatchesDetailsAction ac) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			String queryList = 	" SELECT SUM(x.amt) as amt FROM  " +
								" (SELECT SUM(b.double_amt) as amt " +
								" FROM public.challan_deposit a, public.challn_deposit_detail b " +
								" WHERE a.int_challan_id=b.int_challan_id " +
								" AND a.vch_challan_type=b.vch_challan_type AND a.vch_challan_type='D' " +
								" AND a.date_challan_date='"+Utility.convertUtilDateToSQLDate( ac.getDate_dt())+"'  " +
								" UNION  " +
								" SELECT SUM(b.double_amt) as amt  " +
								" FROM bwfl.challan_deposit_brewery a, bwfl.challn_deposit_brewery_detail b  " +
								" WHERE a.int_challan_id=b.int_challan_id AND a.vch_challan_type=b.vch_challan_type  " +
								" AND a.date_challan_date='"+Utility.convertUtilDateToSQLDate( ac.getDate_dt())+"')x ";
					
				

			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(queryList);
			// System.out.println(queryList);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ac.setTempDeposit(rs.getDouble("amt"));
			}

			DecimalFormat df = new DecimalFormat("####0.00");

			ac.setDeposit(df.format(new BigDecimal(ac.getTempDeposit())));

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
