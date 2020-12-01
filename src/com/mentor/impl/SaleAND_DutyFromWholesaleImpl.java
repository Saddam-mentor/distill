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
import com.mentor.action.SaleAND_DutyFromWholesaleAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class SaleAND_DutyFromWholesaleImpl {

	public void printReport(SaleAND_DutyFromWholesaleAction act) {

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
			if (act.getRadioType().equalsIgnoreCase("C")) {

				type = "Consolidated";

				reportQuery =
				"	select x.dt_date,  sum(x.box_cl) as box_cl,sum(x.bottle_cl) as bottle_cl ,sum(duty_cl)/10000000 as duty_cl ,sum(x.box_fl) as box_fl,sum(x.bottle_fl) as bottle_fl ,sum(duty_fl)/10000000 as duty_fl ,sum(box_beer) as box_beer,sum(bottle_beer) as bottle_beer,sum(duty_beer)/10000000 as duty_beer,sum(cl_bl)/100000  as cl_bl,sum(fl_bl)/100000 as fl_bl,sum(beer_bl)/100000 as beer_bl                                                                                                                                                                                                                        "+
				"	from (                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     "+
				"	select a.dt_date,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,sum(dispatch_bottle)*p.quantity/1000 as cl_bl,0 as fl_bl,0 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id and a.vch_from='CL2' group by a.dt_date,duty,adduty,p.quantity                                    "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
				"	select a.dt_date,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer ,sum(dispatch_bottle)*p.quantity/1000 as cl_bl,0 as fl_bl,0 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date >= '2020-05-01' and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='CL2' group by a.dt_date,duty,adduty,p.quantity     "+
				"	union all                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  "+
                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+
				"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl  from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2' group by a.dt_date,duty,adduty,p.quantity                                "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
				"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.permit)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT'    group by a.dt_date,p.permit,p.quantity                                                                                                                 "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
				"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l   where p.package_id=b.int_pckg_id  and dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and l.int_app_id=a.int_fl2_fl2b_id  and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_from='FL2' group by a.dt_date,duty,adduty,p.quantity    "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
				"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT' and a.dt_date >= '2020-05-01' group by a.dt_date,p.duty,p.adduty,p.quantity                                                                        "+
                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+
				"	union all                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  "+
                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+
				"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p ,licence.fl2_2b_2d_20_21 l  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2B' group by a.dt_date,duty,adduty,p.quantity                                "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
				"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.permit)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT'   group by a.dt_date,p.permit ,p.quantity                                                                                                                 "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
				"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p ,licence.fl2_2b_2d_20_21 l  where p.package_id=b.int_pckg_id  and a.dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2B' group by a.dt_date,duty,adduty,p.quantity "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
				"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT' and a.dt_date >= '2020-05-01'   group by a.dt_date,p.duty,p.adduty,p.quantity                                                                      "+
                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+
				"	)x  group by x.dt_date                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ";
						/* " select x.dt_date,sum(x.box_cl) as box_cl,sum(x.bottle_cl) as bottle_cl ,sum(duty_cl) as duty_cl ,sum(x.box_fl) as box_fl,sum(x.bottle_fl) as bottle_fl ,sum(duty_fl) as duty_fl ,sum(box_beer) as box_beer,sum(bottle_beer) as bottle_beer,sum(duty_beer) as duty_beer "+                                                                                                                                                                                                                                                                                                                                                                                                                                         "
					     " from ( "+                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           "
						 "	select a.dt_date,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_from='CL2' group by a.dt_date,duty,adduty "+                                                                               "
						 "	union  "+
						 "	select a.dt_date,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date >= '2020-05-01' and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_from='CL2' group by a.dt_date,duty,adduty "+
						 "	union all "+
						 "	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_from='FL2' group by a.dt_date,duty,adduty "+
						 "	union   "+
						 "	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty "+
						 "	union "+
						 "	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p   where p.package_id=b.int_pckg_id  and dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_from='FL2' group by a.dt_date,duty,adduty "+
						"	union "+
						"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_to='RT' and a.dt_date >= '2020-05-01' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty"+
						"	union all "+
						"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_from='FL2B' group by a.dt_date,duty,adduty "+
						"	union "+
						"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_beer from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty "+
						"	union "+
						"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p   where p.package_id=b.int_pckg_id  and a.dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_from='FL2B' group by a.dt_date,duty,adduty "+
						"	union "+
						"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_beer from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_to='RT' and a.dt_date >= '2020-05-01' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty "+
						"	)x group by x.dt_date ";*/

			} else if (act.getRadioType().equalsIgnoreCase("D")) {

				type = "Detailed";

				reportQuery =
					
				"	select x.dt_date,y.description, sum(x.box_cl) as box_cl,sum(x.bottle_cl) as bottle_cl ,sum(duty_cl)/10000000 as duty_cl ,sum(x.box_fl) as box_fl,sum(x.bottle_fl) as bottle_fl ,sum(duty_fl)/10000000 as duty_fl ,sum(box_beer) as box_beer,sum(bottle_beer) as bottle_beer,sum(duty_beer)/10000000 as duty_beer,sum(cl_bl)/100000  as cl_bl,sum(fl_bl)/100000 as fl_bl,sum(beer_bl)/100000 as beer_bl                                                                                                                                                                                                                                                                                                                                                                                          "+
				"	from (                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           "+
				"	select a.dt_date,l.core_district_id::int as district_id,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,sum(dispatch_bottle)*p.quantity/1000 as cl_bl,0 as fl_bl,0 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id and a.vch_from='CL2' group by a.dt_date,duty,adduty,l.core_district_id,p.quantity                                                "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
				"	select a.dt_date,l.core_district_id::int as district_id,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer ,sum(dispatch_bottle)*p.quantity/1000 as cl_bl,0 as fl_bl,0 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date >= '2020-05-01' and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='CL2' group by a.dt_date,duty,adduty,l.core_district_id,p.quantity                 "+
				"	union all                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "+
                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    "+
				"	select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl  from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2' group by a.dt_date,duty,adduty,l.core_district_id,p.quantity                                            "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
				"	select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.permit)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.permit,rt.district_id,p.quantity                                                 "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
				"	select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l   where p.package_id=b.int_pckg_id  and dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and l.int_app_id=a.int_fl2_fl2b_id  and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_from='FL2' group by a.dt_date,duty,adduty,l.core_district_id,p.quantity                "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
				"	select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT' and a.dt_date >= '2020-05-01' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty,rt.district_id,p.quantity     "+
                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    "+
				"	union all                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "+
                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    "+
				"	select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p ,licence.fl2_2b_2d_20_21 l  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2B' group by a.dt_date,duty,adduty,l.core_district_id,p.quantity                                            "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
				"	select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.permit)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.permit ,rt.district_id,p.quantity                                                          "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
				"	select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p ,licence.fl2_2b_2d_20_21 l  where p.package_id=b.int_pckg_id  and a.dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2B' group by a.dt_date,duty,adduty,l.core_district_id,p.quantity             "+
				"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
				"	select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT' and a.dt_date >= '2020-05-01' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty,rt.district_id,p.quantity               "+
                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    "+
				"	)x,district y where x.district_id=y.districtid  group by x.dt_date,x.district_id,y.description order by y.description,x.dt_date";
						
						
						
						/*" select x.dt_date,y.description, sum(x.box_cl) as box_cl,sum(x.bottle_cl) as bottle_cl ,sum(duty_cl) as duty_cl ,sum(x.box_fl) as box_fl,sum(x.bottle_fl) as bottle_fl ,sum(duty_fl) as duty_fl ,sum(box_beer) as box_beer,sum(bottle_beer) as bottle_beer,sum(duty_beer) as duty_beer "+
						" from ( "+
						" select a.dt_date,l.core_district_id::int as district_id,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and l.int_app_id=a.int_fl2_fl2b_id and a.vch_from='CL2' group by a.dt_date,duty,adduty,l.core_district_id "+
						" union "+
						" select a.dt_date,l.core_district_id::int as district_id,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date >= '2020-05-01' and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='CL2' group by a.dt_date,duty,adduty,l.core_district_id "+
						" union all "+
						" select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2' group by a.dt_date,duty,adduty,l.core_district_id "+
						" union   "+
						" select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty,rt.district_id "+
						" union "+
						" select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l   where p.package_id=b.int_pckg_id  and dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and l.int_app_id=a.int_fl2_fl2b_id  and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_from='FL2' group by a.dt_date,duty,adduty,l.core_district_id "+
						" union "+
						" select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_to='RT' and a.dt_date >= '2020-05-01' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty,rt.district_id "+
						" union all "+
						" select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p ,licence.fl2_2b_2d_20_21 l  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2B' group by a.dt_date,duty,adduty,l.core_district_id "+
						" union "+
						" select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_beer from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty,rt.district_id "+
						" union "+
						" select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p ,licence.fl2_2b_2d_20_21 l  where p.package_id=b.int_pckg_id  and a.dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2B' group by a.dt_date,duty,adduty,l.core_district_id "+
						" union "+
						" select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_beer from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_to='RT' and a.dt_date >= '2020-05-01' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty,rt.district_id "+
						" )x,district y where x.district_id=y.districtid  group by x.dt_date,x.district_id,y.description ";*/
			
			}

			System.out.println("======check=======" + reportQuery);
			pst = con.prepareStatement(reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("image", relativePath + File.separator);
				parameters.put("type", type);
				parameters.put("todate", act.getTodate());
				parameters.put("formdate", act.getFormdate());
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				if (act.getRadioType().equalsIgnoreCase("C")) {

					jasperReport = (JasperReport) JRLoader
							.loadObject(relativePath + File.separator
									+ "Sale_AND-Duty_From_Wholesale.jasper");

					JasperPrint print = JasperFillManager.fillReport(
							jasperReport, parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager.exportReportToPdfFile(print,
							relativePathpdf + File.separator
									+ "Sale_AND-Duty_From_Wholesale" + "-" + n
									+ ".pdf");
					act.setPdfName("Sale_AND-Duty_From_Wholesale" + "-" + n
							+ ".pdf");
					act.setPrintFlag(true);

				}

				else {

					jasperReport = (JasperReport) JRLoader
							.loadObject(relativePath + File.separator
									+ "Sale_AND-Duty_From_Wholesale_Detailed.jasper");

					JasperPrint print = JasperFillManager.fillReport(
							jasperReport, parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager.exportReportToPdfFile(print,
							relativePathpdf + File.separator
									+ "Sale_AND-Duty_From_Wholesale_Detailed" + "-" + n
									+ ".pdf");
					act.setPdfName("Sale_AND-Duty_From_Wholesale_Detailed" + "-" + n
							+ ".pdf");
					act.setPrintFlag(true);
				}
				/*
				 * jasperReport = (JasperReport)
				 * JRLoader.loadObject(relativePath + File.separator +
				 * "Report_On_Dispatches.jasper");
				 * 
				 * 
				 * JasperPrint print =
				 * JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				 * Random rand = new Random(); int n = rand.nextInt(250) + 1;
				 * JasperExportManager.exportReportToPdfFile(print,
				 * relativePathpdf + File.separator + "Report_On_Dispatches" +
				 * "-" + n + ".pdf" ); act.setPdfName("Report_On_Dispatches" +
				 * "-" + n + ".pdf"); //act.setPrintFlag(true);
				 * act.setPrintFlag(true);
				 */

			} else {
				// act.setPrintFlag(false);
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

	// -------------------------------------------------------------

	/*public void printExcel_Detailed(SaleAND_DutyFromWholesaleAction act) {

		Connection con = null;
		double box_total_cl = 0.0;
		double duty_total_cl = 0.0;
		double box_total_fl = 0.0;
		double duty_total_fl = 0.0;
		double box_total_beer = 0.0;
		double duty_total_beer = 0.0;

		String type = "";
		String reportQuery = "";

		if (act.getRadioType().equalsIgnoreCase("D")) {

			type = "Detailed";

			reportQuery = " select x.dt_date,y.description, sum(x.box_cl) as box_cl,sum(x.bottle_cl) as bottle_cl ,sum(duty_cl) as duty_cl ,sum(x.box_fl) as box_fl,sum(x.bottle_fl) as bottle_fl ,sum(duty_fl) as duty_fl ,sum(box_beer) as box_beer,sum(bottle_beer) as bottle_beer,sum(duty_beer) as duty_beer "+
					" from ( "+
					" select a.dt_date,l.core_district_id::int as district_id,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and l.int_app_id=a.int_fl2_fl2b_id and a.vch_from='CL2' group by a.dt_date,duty,adduty,l.core_district_id "+
					" union "+
					" select a.dt_date,l.core_district_id::int as district_id,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date >= '2020-05-01' and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='CL2' group by a.dt_date,duty,adduty,l.core_district_id "+
					" union all "+
					" select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2' group by a.dt_date,duty,adduty,l.core_district_id "+
					" union   "+
					" select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty,rt.district_id "+
					" union "+
					" select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l   where p.package_id=b.int_pckg_id  and dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and l.int_app_id=a.int_fl2_fl2b_id  and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_from='FL2' group by a.dt_date,duty,adduty,l.core_district_id "+
					" union "+
					" select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_to='RT' and a.dt_date >= '2020-05-01' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty,rt.district_id "+
					" union all "+
					" select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p ,licence.fl2_2b_2d_20_21 l  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2B' group by a.dt_date,duty,adduty,l.core_district_id "+
					" union "+
					" select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_beer from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty,rt.district_id "+
					" union "+
					" select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p ,licence.fl2_2b_2d_20_21 l  where p.package_id=b.int_pckg_id  and a.dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2B' group by a.dt_date,duty,adduty,l.core_district_id "+
					" union "+
					" select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_beer from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' and a.vch_to='RT' and a.dt_date >= '2020-05-01' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty,rt.district_id "+
					" )x,district y where x.district_id=y.districtid  group by x.dt_date,x.district_id,y.description ";
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
					.createSheet("Sale AND  Duty From Wholesale For Detailed");

			worksheet.setColumnWidth(0, 4000);
			worksheet.setColumnWidth(1, 12000);
			worksheet.setColumnWidth(2, 3000);
			worksheet.setColumnWidth(3, 3000);
			worksheet.setColumnWidth(4, 3000);
			worksheet.setColumnWidth(5, 3000);
			worksheet.setColumnWidth(6, 3000);
			worksheet.setColumnWidth(7, 3000);
			worksheet.setColumnWidth(8, 3000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);

			cellhead0.setCellValue("Sale AND  Duty From Wholesale For " + type
					+ " From (Date"
					+ Utility.convertUtilDateToSQLDate(act.getFormdate())
					+ " To "
					+ Utility.convertUtilDateToSQLDate(act.getTodate()) + ")");

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

			XSSFRow rowhead = worksheet.createRow((int) 1);

			XSSFCell cellhead1 = rowhead.createCell((int) 0);
			cellhead1.setCellValue("Sr. No");
			cellhead1.setCellStyle(cellStyle);

			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("Date");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Description");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("CL BOX");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("CL Duty");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("FL BOX");
			cellhead6.setCellStyle(cellStyle);

			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("FL Duty");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("BEER BOX");
			cellhead8.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("BEER Duty");
			cellhead9.setCellStyle(cellStyle);

			k = k + 1;
			int i = 0;

			while (rs.next()) {
				// total = total + rs.getDouble("amount");
				 box_total_cl = box_total_cl + rs.getDouble("box_cl");
				 duty_total_cl = duty_total_cl + rs.getDouble("duty_cl");
				 box_total_fl = box_total_fl + rs.getDouble("box_fl");
				 duty_total_fl = duty_total_fl + rs.getDouble("duty_fl");
				 box_total_beer = box_total_beer + rs.getDouble("box_beer");
				 duty_total_beer = duty_total_beer + rs.getDouble("box_beer");
					//case_total = case_total + rs.getDouble("no_of_cases");
					System.out.println(box_total_cl);
					//fees_total = fees_total + rs.getInt("import_fee");

				i++;
				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				XSSFCell cellA1 = row1.createCell((int) 0); //
				cellA1.setCellValue(k - 1); //

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("dt_date"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getInt("description"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getDouble("box_cl"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("duty_cl"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("box_fl"));

				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("duty_fl"));

				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("box_beer"));
				
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getDouble("duty_beer"));

				// System.out.println("------------ "+Math.round(cases_of_last_year_sale));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "Sale_AND_DutyFromWholesale_Detailed.xls");

			act.setExlname(n + "Sale_AND_DutyFromWholesale_Detailed.xls");
			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA2 = row1.createCell((int) 0);
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 1);
			cellA3.setCellValue("TOTAL:- ");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 2);
			cellA4.setCellValue(box_total_cl);
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 3);
			cellA5.setCellValue(duty_total_beer);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 4);
			cellA6.setCellValue(box_total_cl);
			cellA6.setCellStyle(cellStyle);

			XSSFCell cellA7 = row1.createCell((int) 5);
			cellA7.setCellValue(duty_total_beer);
			cellA7.setCellStyle(cellStyle);

			XSSFCell cellA8 = row1.createCell((int) 6);
			cellA8.setCellValue(box_total_cl);
			cellA8.setCellStyle(cellStyle);

			XSSFCell cellA9 = row1.createCell((int) 7);
			cellA9.setCellValue(duty_total_beer);
			cellA9.setCellStyle(cellStyle);

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

	}*/
	//-------------------------------------------------------
	
	public void printExcel_Detailed(SaleAND_DutyFromWholesaleAction act) {

		Connection con = null;

		double box_total_cl = 0.0;
		double bl_total_cl = 0.0;
		double duty_total_cl = 0.0;
		double box_total_fl = 0.0;
		double bl_total_fl = 0.0;
		double duty_total_fl = 0.0;
		double box_total_beer = 0.0;
		double bl_total_beer = 0.0;
		double duty_total_beer = 0.0;
		double total_duty = 0.0;
		double sub_total_duty = 0.0;
		String type = "";
		String reportQuery = "";
		if (act.getRadioType().equalsIgnoreCase("D")) {

			type = "Detailed";

			reportQuery =
					"	select x.dt_date,y.description, sum(x.box_cl) as box_cl,sum(x.bottle_cl) as bottle_cl ,sum(duty_cl)/10000000 as duty_cl ,sum(x.box_fl) as box_fl,sum(x.bottle_fl) as bottle_fl ,sum(duty_fl)/10000000 as duty_fl ,sum(box_beer) as box_beer,sum(bottle_beer) as bottle_beer,sum(duty_beer)/10000000 as duty_beer,sum(cl_bl)/100000  as cl_bl,sum(fl_bl)/100000 as fl_bl,sum(beer_bl)/100000 as beer_bl                                                                                                                                                                                                                                                                                                                                                                                          "+
							"	from (                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           "+
							"	select a.dt_date,l.core_district_id::int as district_id,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,sum(dispatch_bottle)*p.quantity/1000 as cl_bl,0 as fl_bl,0 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id and a.vch_from='CL2' group by a.dt_date,duty,adduty,l.core_district_id,p.quantity                                                "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
							"	select a.dt_date,l.core_district_id::int as district_id,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer ,sum(dispatch_bottle)*p.quantity/1000 as cl_bl,0 as fl_bl,0 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date >= '2020-05-01' and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='CL2' group by a.dt_date,duty,adduty,l.core_district_id,p.quantity                 "+
							"	union all                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "+
			                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    "+
							"	select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl  from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2' group by a.dt_date,duty,adduty,l.core_district_id,p.quantity                                            "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
							"	select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.permit)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.permit,rt.district_id,p.quantity                                                 "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
							"	select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l   where p.package_id=b.int_pckg_id  and dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and l.int_app_id=a.int_fl2_fl2b_id  and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_from='FL2' group by a.dt_date,duty,adduty,l.core_district_id,p.quantity                "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
							"	select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT' and a.dt_date >= '2020-05-01' and rt.vch_shop_type in ('Foreign Liquor','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty,rt.district_id,p.quantity     "+
			                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    "+
							"	union all                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        "+
			                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    "+
							"	select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p ,licence.fl2_2b_2d_20_21 l  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2B' group by a.dt_date,duty,adduty,l.core_district_id,p.quantity                                            "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
							"	select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.permit)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.permit ,rt.district_id,p.quantity                                                          "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
							"	select a.dt_date,l.core_district_id::int as district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p ,licence.fl2_2b_2d_20_21 l  where p.package_id=b.int_pckg_id  and a.dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2B' group by a.dt_date,duty,adduty,l.core_district_id,p.quantity             "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+
							"	select a.dt_date,rt.district_id,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p ,retail.retail_shop rt  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT' and a.dt_date >= '2020-05-01' and rt.vch_shop_type in ('Beer','Model Shop') and a.shop_id::int=serial_no group by a.dt_date,p.duty,p.adduty,rt.district_id,p.quantity               "+
			                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    "+
							"	)x,district y where x.district_id=y.districtid  group by x.dt_date,x.district_id,y.description order by y.description,x.dt_date";
									
									
									
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
			XSSFSheet worksheet = workbook
					.createSheet("Sale AND  Duty From Wholesale For Detailed");

			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 3000);
			worksheet.setColumnWidth(2, 12000);
			worksheet.setColumnWidth(3, 6000);
			worksheet.setColumnWidth(4, 6000);
			worksheet.setColumnWidth(5, 6000);
			worksheet.setColumnWidth(6, 6000);
			worksheet.setColumnWidth(7, 6000);
			worksheet.setColumnWidth(8, 6000);
			worksheet.setColumnWidth(9, 6000);
			worksheet.setColumnWidth(10, 6000);
			worksheet.setColumnWidth(11, 6000);
			worksheet.setColumnWidth(12, 6000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);

			cellhead0.setCellValue("Sale AND  Duty From Wholesale For " + type
					+ " From (Date"
					+ Utility.convertUtilDateToSQLDate(act.getFormdate())
					+ " To "
					+ Utility.convertUtilDateToSQLDate(act.getTodate()) + ")");

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
			cellhead1.setCellValue("Sr. No");
			cellhead1.setCellStyle(cellStyle);

			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("Date");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Description");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("CL BOX");
			cellhead4.setCellStyle(cellStyle);
           
			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("CL BL");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("CL DUTY");
			cellhead6.setCellStyle(cellStyle);

			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("FL BOX");
			cellhead7.setCellStyle(cellStyle);
			
			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("FL BL");
			cellhead8.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("FL DUTY");
			cellhead9.setCellStyle(cellStyle);
			
			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("BEER BOX");
			cellhead10.setCellStyle(cellStyle);
			
			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("BEER BL");
			cellhead11.setCellStyle(cellStyle);
			
			XSSFCell cellhead12 = rowhead.createCell((int) 11);
			cellhead12.setCellValue("BEER DUTY");
			cellhead12.setCellStyle(cellStyle);
			
			XSSFCell cellhead13 = rowhead.createCell((int) 12);
			cellhead13.setCellValue("TOTAL DUTY");
			cellhead13.setCellStyle(cellStyle);

			// k = k + 2;
			int i = 0;

			while (rs.next()) {
				
				box_total_cl = box_total_cl + rs.getDouble("box_cl");
				 bl_total_cl = bl_total_cl+rs.getDouble("cl_bl");
				 duty_total_cl = duty_total_cl + rs.getDouble("duty_cl");
				 box_total_fl = box_total_fl + rs.getDouble("box_fl");
				 bl_total_fl = bl_total_fl+rs.getDouble("fl_bl");
				 duty_total_fl = duty_total_fl + rs.getDouble("duty_fl");
				 box_total_beer = box_total_beer + rs.getDouble("box_beer");
				 bl_total_beer = bl_total_beer+rs.getDouble("beer_bl");
				 duty_total_beer = duty_total_beer + rs.getDouble("duty_beer");
				 total_duty = rs.getDouble("duty_cl")+rs.getDouble("duty_fl")+rs.getDouble("duty_beer");
				 System.out.println("=total_duty===="+total_duty);
				 sub_total_duty = duty_total_cl+duty_total_fl+duty_total_beer;
				//case_total = case_total + rs.getDouble("no_of_cases");
				System.out.println(box_total_cl);
				//fees_total = fees_total + rs.getInt("import_fee");

				i++;
				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				XSSFCell cellA1 = row1.createCell((int) 0); //
				cellA1.setCellValue(k - 1); //

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("dt_date"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("description"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("box_cl"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getDouble("cl_bl"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("duty_cl"));

				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("box_fl"));
				
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getString("fl_bl"));
				
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getString("duty_fl"));
				
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getString("box_beer"));
				
				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getString("beer_bl"));
				 
				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(rs.getString("duty_beer"));
				
				XSSFCell cellM1 = row1.createCell((int) 12);
				cellM1.setCellValue(total_duty);
				// System.out.println("------------ "+Math.round(cases_of_last_year_sale));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "Sale_AND_DutyFromWholesale_Detailed.xls");

			act.setExlname(n + "Sale_AND_DutyFromWholesale_Detailed.xls");
			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA2 = row1.createCell((int) 0);
			cellA2.setCellValue("");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 1);
			cellA3.setCellValue("");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 2);
			cellA4.setCellValue("TOTAL:-");
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 3);
			cellA5.setCellValue(box_total_cl);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 4);
			cellA6.setCellValue(bl_total_cl);
			cellA6.setCellStyle(cellStyle);

			XSSFCell cellA7 = row1.createCell((int) 5);
			cellA7.setCellValue(duty_total_cl);
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 6);
			cellA8.setCellValue(box_total_fl);
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 7);
			cellA9.setCellValue(bl_total_fl);
			cellA9.setCellStyle(cellStyle);
			
			XSSFCell cellA10 = row1.createCell((int) 8);
			cellA10.setCellValue(duty_total_fl);
			cellA10.setCellStyle(cellStyle);
		
			XSSFCell cellA11 = row1.createCell((int) 9);
			cellA11.setCellValue(box_total_beer);
			cellA11.setCellStyle(cellStyle);
			
			XSSFCell cellA12 = row1.createCell((int) 10);
			cellA12.setCellValue(bl_total_beer);
			cellA12.setCellStyle(cellStyle);
			
			XSSFCell cellA13 = row1.createCell((int) 11);
			cellA13.setCellValue(duty_total_beer);
			cellA13.setCellStyle(cellStyle);
			
			XSSFCell cellA14 = row1.createCell((int) 12);
			cellA14.setCellValue(sub_total_duty);
			cellA14.setCellStyle(cellStyle);
		

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

	

	// ----------------------------------------------------

	public void printExcel_Consolidated(SaleAND_DutyFromWholesaleAction act) {

		Connection con = null;

		double box_total_cl = 0.0;
		double bl_total_cl = 0.0;
		double duty_total_cl = 0.0;
		double box_total_fl = 0.0;
		double bl_total_fl = 0.0;
		double duty_total_fl = 0.0;
		double box_total_beer = 0.0;
		double bl_total_beer = 0.0;
		double duty_total_beer = 0.0;
		double total_duty = 0.0;
		double sub_total_duty = 0.0;
		

		String type = "";
		String reportQuery = "";

		if (act.getRadioType().equalsIgnoreCase("C")) {

			type = "Consolidated";

			reportQuery =
					"	select x.dt_date,  sum(x.box_cl) as box_cl,sum(x.bottle_cl) as bottle_cl ,sum(duty_cl)/10000000 as duty_cl ,sum(x.box_fl) as box_fl,sum(x.bottle_fl) as bottle_fl ,sum(duty_fl)/10000000 as duty_fl ,sum(box_beer) as box_beer,sum(bottle_beer) as bottle_beer,sum(duty_beer)/10000000 as duty_beer,sum(cl_bl)/100000  as cl_bl,sum(fl_bl)/100000 as fl_bl,sum(beer_bl)/100000 as beer_bl                                                                                                                                                                                                                        "+
							"	from (                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     "+
							"	select a.dt_date,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,sum(dispatch_bottle)*p.quantity/1000 as cl_bl,0 as fl_bl,0 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id and a.vch_from='CL2' group by a.dt_date,duty,adduty,p.quantity                                    "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
							"	select a.dt_date,sum(dispatch_box) as box_cl,sum(dispatch_bottle) as bottle_cl  ,(sum(dispatch_bottle)*(duty+adduty))as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer ,sum(dispatch_bottle)*p.quantity/1000 as cl_bl,0 as fl_bl,0 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date >= '2020-05-01' and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'   and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='CL2' group by a.dt_date,duty,adduty,p.quantity     "+
							"	union all                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  "+
			                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+
							"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl  from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2' group by a.dt_date,duty,adduty,p.quantity                                "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
							"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.permit)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT'    group by a.dt_date,p.permit,p.quantity                                                                                                                 "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
							"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(duty+adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p,licence.fl2_2b_2d_20_21 l   where p.package_id=b.int_pckg_id  and dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and l.int_app_id=a.int_fl2_fl2b_id  and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_from='FL2' group by a.dt_date,duty,adduty,p.quantity    "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
							"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,sum(dispatch_box) as box_fl,sum(dispatch_bottle) as bottle_fl ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_fl,0 as box_beer,0 as bottle_beer,0 as duty_beer,0 as cl_bl,sum(dispatch_bottle)*p.quantity/1000 as fl_bl,0 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT' and a.dt_date >= '2020-05-01' group by a.dt_date,p.duty,p.adduty,p.quantity                                                                        "+
			                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+
							"	union all                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  "+
			                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+
							"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b,distillery.packaging_details_20_21 p ,licence.fl2_2b_2d_20_21 l  where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2B' group by a.dt_date,duty,adduty,p.quantity                                "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
							"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.permit)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_20_21 a,fl2d.fl2d_stock_trxn_20_21  b,distillery.packaging_details_20_21 p   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT'   group by a.dt_date,p.permit ,p.quantity                                                                                                                 "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
							"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(duty+adduty)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from   fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b,distillery.packaging_details_20_21 p ,licence.fl2_2b_2d_20_21 l  where p.package_id=b.int_pckg_id  and a.dt_date >= '2020-05-01' and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and l.int_app_id=a.int_fl2_fl2b_id  and a.vch_from='FL2B' group by a.dt_date,duty,adduty,p.quantity "+
							"	union                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+
							"	select a.dt_date,0 as box_cl,0 as bottle_cl,0 as duty_cl,0 as box_fl,0 as bottle_fl,0 as duty_fl,sum(dispatch_box) as box_beer,sum(dispatch_bottle) as bottle_beer ,(sum(dispatch_bottle)*(p.duty+p.adduty)) as duty_beer,0 as cl_bl,0 as fl_bl,sum(dispatch_bottle)*p.quantity/1000 as beer_bl from  fl2d.gatepass_to_districtwholesale_fl2d_19_20 a,fl2d.fl2d_stock_trxn_19_20  b,distillery.packaging_details_20_21 p   where p.package_id=b.int_pckg_id and a.vch_gatepass_no=b.vch_gatepass_no and dt_date between '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"'  and a.vch_to='RT' and a.dt_date >= '2020-05-01'   group by a.dt_date,p.duty,p.adduty,p.quantity                                                                      "+
			                "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+
							"	)x  group by x.dt_date                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ";
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
			XSSFSheet worksheet = workbook
					.createSheet("Sale AND  Duty From Wholesale For Consolidated ");

			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 3000);
			worksheet.setColumnWidth(2, 12000);
			worksheet.setColumnWidth(3, 6000);
			worksheet.setColumnWidth(4, 6000);
			worksheet.setColumnWidth(5, 6000);
			worksheet.setColumnWidth(6, 6000);
			worksheet.setColumnWidth(7, 6000);
			worksheet.setColumnWidth(8, 6000);
			worksheet.setColumnWidth(9, 6000);
			worksheet.setColumnWidth(10, 6000);
			worksheet.setColumnWidth(11, 6000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);

			cellhead0.setCellValue("Sale AND DutyFromWholesale Consolideted For " + type
					+ " From (Date"
					+ Utility.convertUtilDateToSQLDate(act.getFormdate())
					+ " To "
					+ Utility.convertUtilDateToSQLDate(act.getTodate()) + ")");

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
			cellhead1.setCellValue("Sr. No");
			cellhead1.setCellStyle(cellStyle);

			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("Date");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("CL BOX");
			cellhead3.setCellStyle(cellStyle);
           
			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("CL BL");
			cellhead4.setCellStyle(cellStyle);
			
			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("CL DUTY");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("FL BOX");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("FL BL");
			cellhead7.setCellStyle(cellStyle);
			
			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("FL DUTY");
			cellhead8.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("BEER BOX");
			cellhead9.setCellStyle(cellStyle);
			
			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("BEER BL");
			cellhead10.setCellStyle(cellStyle);
			
			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("BEER DUTY");
			cellhead11.setCellStyle(cellStyle);
			
			XSSFCell cellhead12 = rowhead.createCell((int) 11);
			cellhead12.setCellValue("TOTAL DUTY");
			cellhead12.setCellStyle(cellStyle);

			// k = k + 2;
			int i = 0;

			while (rs.next()) {
				
			 box_total_cl = box_total_cl + rs.getDouble("box_cl");
			 bl_total_cl = bl_total_cl+rs.getDouble("cl_bl");
			 duty_total_cl = duty_total_cl + rs.getDouble("duty_cl");
			 box_total_fl = box_total_fl + rs.getDouble("box_fl");
			 bl_total_fl = bl_total_fl+rs.getDouble("fl_bl");
			 duty_total_fl = duty_total_fl + rs.getDouble("duty_fl");
			 box_total_beer = box_total_beer + rs.getDouble("box_beer");
			 bl_total_beer = bl_total_beer+rs.getDouble("beer_bl");
			 duty_total_beer = duty_total_beer + rs.getDouble("duty_beer");
			 total_duty = rs.getDouble("duty_cl")+rs.getDouble("duty_fl")+rs.getDouble("duty_beer");
			 System.out.println("=total_duty===="+total_duty);
			 sub_total_duty = duty_total_cl+duty_total_fl+duty_total_beer;
			 System.out.println("=sub_total_duty===="+sub_total_duty);
				//case_total = case_total + rs.getDouble("no_of_cases");
				System.out.println(box_total_cl);
				//fees_total = fees_total + rs.getInt("import_fee");

				i++;
				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				XSSFCell cellA1 = row1.createCell((int) 0); //
				cellA1.setCellValue(k - 1); //

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("dt_date"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("box_cl"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getDouble("cl_bl"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("duty_cl"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("box_fl"));
				
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("fl_bl"));
				
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getString("duty_fl"));
				
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getString("box_beer"));
				
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getString("beer_bl"));
				 
				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getString("duty_beer"));
				
				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(total_duty);
				// System.out.println("------------ "+Math.round(cases_of_last_year_sale));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "Sale_AND_DutyFromWholesale_Consolideted.xls");

			act.setExlname(n + "Sale_AND_DutyFromWholesale_Consolideted.xls");
			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA2 = row1.createCell((int) 0);
			cellA2.setCellValue("");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 1);
			cellA3.setCellValue("TOTAL:-");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 2);
			cellA4.setCellValue(box_total_cl);
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 3);
			cellA5.setCellValue(bl_total_cl);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 4);
			cellA6.setCellValue(duty_total_cl);
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 5);
			cellA7.setCellValue(box_total_fl);
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 6);
			cellA8.setCellValue(bl_total_fl);
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 7);
			cellA9.setCellValue(duty_total_fl);
			cellA9.setCellStyle(cellStyle);
		
			XSSFCell cellA10 = row1.createCell((int) 8);
			cellA10.setCellValue(box_total_beer);
			cellA10.setCellStyle(cellStyle);
			
			XSSFCell cellA11 = row1.createCell((int) 9);
			cellA11.setCellValue(bl_total_beer);
			cellA11.setCellStyle(cellStyle);
			
			XSSFCell cellA12 = row1.createCell((int) 10);
			cellA12.setCellValue(duty_total_beer);
			cellA12.setCellStyle(cellStyle);
			
			XSSFCell cellA13 = row1.createCell((int) 11);
			cellA13.setCellValue(sub_total_duty);
			cellA13.setCellStyle(cellStyle);


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
