package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mentor.action.FL2_2B_CL2_DistrictWiseAction;
import com.mentor.action.Report_On_Dispatches_Action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class FL2_2B_CL2_DistrictWiseImpl {


	// =======================print report=================================

	public void printReport(FL2_2B_CL2_DistrictWiseAction act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String year = act.getYear();
		
		try {
			con = ConnectionToDataBase.getConnection();
			
	  reportQuery= " 		SELECT   "+
		 " kf.description,  "+
" sum(kf.boxes_cl) as boxes_cl,sum(kf.bl_cl) as bl_cl,sum(kf.totbottl_cl) as totbottl_cl,sum(kf.totduty_cl) as totduty_cl,  "+

" 		sum(kf.boxes_fl2b) as boxes_fl2b, sum(kf.bl_fl2b) as bl_fl2b,sum(kf.totbottl_fl2b) as totbottl_fl2b, sum(kf.totduty_fl2b) as totduty_fl2b,  "+


" 		sum(kf.boxes_FL2) as boxes_FL2,sum(kf.bl_FL2) as bl_FL2 ,sum(kf.totbottl_FL2) as totbottl_FL2,sum(kf.totduty_FL2) as totduty_FL2  "+

" 		from  "+

" 			(SELECT   "+
" 			 a.districtid, a.description,   "+
" 			 SUM(COALESCE(x.boxes,0)) as boxes_cl, SUM(COALESCE(x.bl,0)) as bl_cl , SUM(COALESCE(x.bottl,0))as totbottl_cl,  "+
" 			 SUM(COALESCE(x.totduty,0))as totduty_cl,  "+
		 
" 			0 as boxes_fl2b,0 as  bl_fl2b, 0 as totbottl_fl2b,0 as totduty_fl2b,  "+
" 			0 as boxes_FL2, 0 as bl_FL2,   0 as totbottl_FL2,  0 as totduty_FL2  "+
		 
" 			 FROM public.district a LEFT OUTER JOIN  (SELECT f.core_district_id as lic_district,  "+
" 			 SUM(c.dispatchd_box) as boxes, SUM(round(CAST(float8(((c.dispatchd_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl ,   "+
" 			 SUM(c.dispatchd_bottl) as bottl, sum(c.duty + c.addduty)as totduty   FROM distillery.gatepass_to_manufacturewholesale_cl_"+year+" b,   "+
" 			 distillery.cl2_stock_trxn_"+year+" c,  distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f  "+
" 			 WHERE b.int_dist_id=c.int_dissleri_id AND b.vch_gatepass_no=c.vch_gatepass_no 	AND b.dt_date=c.dt_date AND b.vch_to='CL2'  "+
" 			 AND c.int_pckg_id=d.package_id AND b.vch_to_lic_no=f.vch_licence_no AND b.vch_to=f.vch_license_type	  "+
" 			 AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'    GROUP BY f.core_district_id)x ON a.districtid=x.lic_district   "+
" 			 WHERE a.districtid !=0  GROUP BY a.districtid, a.description   "+

//---------------------------------------------------------------------------------------------------------------------
		 
" 			 union  "+

" 			 select   "+
		 
" 			 f.districtid,f.description,  "+
		 
" 			  0 as boxes_CL2, 0 as bl_CL2,   0 as totbottl_CL2,  0 as totduty_CL2,  "+
		 
" 			 f.boxes_fl2b,f.bl_fl2b,f.totbottl_fl2b,f.totduty_fl2b,  "+
" 			 0 as boxes_FL2, 0 as bl_FL2,   0 as totbottl_FL2,  0 as totduty_FL2  "+
" 			from   "+
" 			 ( select k.districtid,k.description as description,k.boxes as boxes_fl2b,k.bl as bl_fl2b,k.totbottl as totbottl_fl2b,k.totduty as totduty_fl2b  "+
" 			from   "+
" 			 (SELECT a.districtid, a.description, SUM(COALESCE(x.boxes,0)) as boxes, SUM(COALESCE(x.bl,0)) as bl,SUM(COALESCE(x.totbottl,0))as totbottl,   "+
" 			 SUM(COALESCE(x.totduty,0))as totduty FROM public.district a LEFT OUTER JOIN  (SELECT f.core_district_id as lic_district, SUM(c.dispatch_box) as boxes,  "+
" 			 SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl, SUM(c.dispatch_bottle)as totbottl,   "+
" 			 SUM(c.dispatch_bottle) * (d.duty + d.adduty)as totduty FROM distillery.gatepass_to_districtwholesale_"+year+" b, distillery.fl2_stock_trxn_"+year+" c,    "+
" 			 distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f   "+
" 			 WHERE b.int_dist_id=c.int_dissleri_id AND b.vch_gatepass_no=c.vch_gatepass_no  AND b.dt_date=c.dt   "+
" 			 AND b.vch_to='FL2B' AND c.int_pckg_id=d.package_id  AND  b.vch_to_lic_no=f.vch_licence_no AND b.vch_to=f.vch_license_type  "+
" 			 AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'     GROUP BY f.core_district_id ,d.duty , d.adduty   "+
		 
" 			 UNION    "+
		 
" 			 SELECT f.core_district_id as lic_district, SUM(c.dispatch_box) as boxes,    "+
" 			 SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl,     "+
" 			 SUM(c.dispatch_bottle)as totbottl, SUM(c.dispatch_bottle) * (d.duty + d.adduty)as totduty  FROM bwfl_license.gatepass_to_districtwholesale_bwfl_"+year+" b,  "+
" 			 bwfl_license.fl2_stock_trxn_bwfl_"+year+" c,  distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f   "+
" 			 WHERE b.int_bwfl_id=c.int_bwfl_id AND b.vch_gatepass_no=c.vch_gatepass_no AND b.dt_date=c.dt AND b.vch_from='BWFL2B' AND c.int_pckg_id=d.package_id   "+
" 			 AND  b.vch_to_lic_no=f.vch_licence_no AND f.vch_license_type='FL2B'  AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'      "+
" 			 GROUP BY f.core_district_id, d.duty , d.adduty   "+
		 
" 			 UNION    "+
		 
" 			 SELECT f.core_district_id as lic_district, SUM(c.dispatch_box) as boxes,  "+
" 			 SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl,    "+
" 			 SUM(c.dispatch_bottle)as totbottl,(c.cal_duty)as totduty  FROM fl2d.gatepass_to_districtwholesale_fl2d_"+year+" b, fl2d.fl2d_stock_trxn c,   "+
" 			 distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f   WHERE b.int_fl2d_id=c.int_fl2d_id AND b.vch_gatepass_no=c.vch_gatepass_no    "+
" 			 AND b.dt_date=c.dt AND b.vch_to='FL2B' AND c.int_pckg_id=d.package_id  AND  b.vch_to_lic_no=f.vch_licence_no AND b.vch_to=f.vch_license_type   "+
" 			 AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'    GROUP BY f.core_district_id ,cal_duty   "+ 
		 
" 			 UNION    "+
		 
" 			 SELECT f.core_district_id as lic_district,   "+
" 			 SUM(c.dispatch_box) as boxes,  SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl ,    "+
" 			 SUM(c.dispatch_bottle)as totbottl, SUM(c.dispatch_bottle) * (d.duty + d.adduty)as totduty  FROM bwfl.gatepass_to_districtwholesale_"+year+" b,   "+
" 			 bwfl.fl2_stock_trxn_"+year+" c,  distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f     "+
" 			 WHERE b.brewery_id=c.brewery_id AND b.vch_gatepass_no=c.vch_gatepass_no    "+
" 			 AND b.dt_date=c.dt AND b.vch_to='FL2B' AND c.int_pckg_id=d.package_id  AND  b.vch_to_lic_no=f.vch_licence_no AND b.vch_to=f.vch_license_type    "+
" 			 AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'      "+
" 			 GROUP BY f.core_district_id,d.duty, d.adduty  )x on a.districtid=x.lic_district  WHERE a.districtid !=0    "+
" 			 GROUP BY a.districtid, a.description)k)f  "+
		 
//---------------------------------------------------------------------------------------------------------------------
		 
" 			 union  "+
		 

		 

" 			select   "+
" 			  gf.districtid,gf.description,  "+
" 			   0 as boxes_CL2, 0 as bl_CL2,   0 as totbottl_CL2,  0 as totduty_CL2,   "+
" 			 0 as boxes_fl2b,0 as  bl_fl2b, 0 as totbottl_fl2b,0 as totduty_fl2b,  "+
" 			gf.boxes_FL2,gf.bl_FL2,gf.totbottl_FL2,gf.totduty_FL2  "+
" 			 from  "+
" 			 (  "+
" 			 SELECT a.districtid, a.description, SUM(COALESCE(x.boxes,0)) as boxes_FL2, SUM(COALESCE(x.bl,0)) as bl_FL2,SUM(COALESCE(x.totbottl,0))as totbottl_FL2,   "+
" 			 SUM(COALESCE(x.totduty,0))as totduty_FL2  FROM public.district a LEFT OUTER JOIN  (SELECT f.core_district_id as lic_district,  "+
" 			 SUM(c.dispatch_box) as boxes, SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl,   "+
" 			 SUM(c.dispatch_bottle)as totbottl, SUM(c.dispatch_bottle) * (d.duty + d.adduty) as totduty  from distillery.gatepass_to_districtwholesale_"+year+" b,  "+
" 			 distillery.fl2_stock_trxn_"+year+" c,  distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f  "+
" 			 WHERE b.int_dist_id=c.int_dissleri_id AND b.vch_gatepass_no=c.vch_gatepass_no  AND b.dt_date=c.dt AND b.vch_to='FL2'   "+
" 			 AND c.int_pckg_id=d.package_id  AND  b.vch_to_lic_no=f.vch_licence_no AND b.vch_to=f.vch_license_type  "+
" AND b.dt_date BETWEEN 	'"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'   "+
" 			  GROUP BY f.core_district_id ,d.duty , d.adduty   "+

" 			 UNION   "+
		 
" 			 SELECT f.core_district_id as lic_district,   "+
" 			 SUM(c.dispatch_box) as boxes, SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl,   "+
" 			 SUM(c.dispatch_bottle)as totbottl, SUM(c.dispatch_bottle) * (d.duty + d.adduty)as totduty  "+
" 			 from bwfl_license.gatepass_to_districtwholesale_bwfl_"+year+" b, bwfl_license.fl2_stock_trxn_bwfl_"+year+" c,   "+
" 			 distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f  WHERE b.int_bwfl_id=c.int_bwfl_id AND b.vch_gatepass_no=c.vch_gatepass_no  "+
" 			 AND b.dt_date=c.dt AND c.int_pckg_id=d.package_id  AND  b.vch_to_lic_no=f.vch_licence_no AND f.vch_license_type='FL2'    "+
" 			 AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'     GROUP BY f.core_district_id, d.duty , d.adduty    "+
		 
" 			 UNION   "+

" 			 SELECT f.core_district_id as lic_district, SUM(c.dispatch_box) as boxes,  "+
" 			 SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl,   "+
" 			 SUM(c.dispatch_bottle)as totbottl,(c.cal_duty)as totduty from fl2d.gatepass_to_districtwholesale_fl2d_"+year+" b, fl2d.fl2d_stock_trxn c,   "+
" 			 distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f   WHERE b.int_fl2d_id=c.int_fl2d_id AND b.vch_gatepass_no=c.vch_gatepass_no   "+
" 			 AND b.dt_date=c.dt AND b.vch_to='FL2' AND c.int_pckg_id=d.package_id   AND  b.vch_to_lic_no=f.vch_licence_no AND b.vch_to=f.vch_license_type   "+
" 			 AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'     GROUP BY f.core_district_id,cal_duty  )x on a.districtid=x.lic_district    "+
" 			 WHERE a.districtid !=0  GROUP BY a.districtid, a.description)gf  "+


" 		)kf group by kf.description ";
			
				pst = con.prepareStatement(reportQuery);
				
				
				//System.out.println("reportQuery----------"+reportQuery);
				
				
				
				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("radioType",act.getRadio());
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "FL2_FL2B_CL2_DistrictWise_Report.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "FL2_FL2B_CL2_DistrictWise_Report" + "-" + n + ".pdf");
				act.setPdfName("FL2_FL2B_CL2_DistrictWise_Report" + "-" + n + ".pdf");
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
	
	//----------------------generate excel for bwfl and fl2d wise report------------------------------
	
	public boolean writeExcel(FL2_2B_CL2_DistrictWiseAction act)
	{



		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double boxesTotal_fl = 0;
		double blTotal_fl = 0;
		double totbot_fl = 0;
		double totduty_fl= 0;
		
		double boxesTotal_fl2b = 0;
		double blTotal_fl2b = 0;
		double totbot_fl2b = 0;
		double totduty_fl2b= 0;
		
		
		
		double boxesTotal_cl2 = 0;
		double blTotal_cl2 = 0;
		double totbot_cl2 = 0;
		double totduty_cl2 = 0;
		String excelQuery = null;	
		String year = act.getYear();
		String type="";

		

		excelQuery = 		" 		SELECT   "+
		 " kf.description,  "+
" sum(kf.boxes_cl) as boxes_cl,sum(kf.bl_cl) as bl_cl,sum(kf.totbottl_cl) as totbottl_cl,sum(kf.totduty_cl) as totduty_cl,  "+

" 		sum(kf.boxes_fl2b) as boxes_fl2b, sum(kf.bl_fl2b) as bl_fl2b,sum(kf.totbottl_fl2b) as totbottl_fl2b, sum(kf.totduty_fl2b) as totduty_fl2b,  "+


" 		sum(kf.boxes_FL2) as boxes_FL2,sum(kf.bl_FL2) as bl_FL2 ,sum(kf.totbottl_FL2) as totbottl_FL2,sum(kf.totduty_FL2) as totduty_FL2  "+

" 		from  "+

" 			(SELECT   "+
" 			 a.districtid, a.description,   "+
" 			 SUM(COALESCE(x.boxes,0)) as boxes_cl, SUM(COALESCE(x.bl,0)) as bl_cl , SUM(COALESCE(x.bottl,0))as totbottl_cl,  "+
" 			 SUM(COALESCE(x.totduty,0))as totduty_cl,  "+
		 
" 			0 as boxes_fl2b,0 as  bl_fl2b, 0 as totbottl_fl2b,0 as totduty_fl2b,  "+
" 			0 as boxes_FL2, 0 as bl_FL2,   0 as totbottl_FL2,  0 as totduty_FL2  "+
		 
" 			 FROM public.district a LEFT OUTER JOIN  (SELECT f.core_district_id as lic_district,  "+
" 			 SUM(c.dispatchd_box) as boxes, SUM(round(CAST(float8(((c.dispatchd_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl ,   "+
" 			 SUM(c.dispatchd_bottl) as bottl, sum(c.duty + c.addduty)as totduty   FROM distillery.gatepass_to_manufacturewholesale_cl_"+year+" b,   "+
" 			 distillery.cl2_stock_trxn_"+year+" c,  distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f  "+
" 			 WHERE b.int_dist_id=c.int_dissleri_id AND b.vch_gatepass_no=c.vch_gatepass_no 	AND b.dt_date=c.dt_date AND b.vch_to='CL2'  "+
" 			 AND c.int_pckg_id=d.package_id AND b.vch_to_lic_no=f.vch_licence_no AND b.vch_to=f.vch_license_type	  "+
" 			 AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'    GROUP BY f.core_district_id)x ON a.districtid=x.lic_district   "+
" 			 WHERE a.districtid !=0  GROUP BY a.districtid, a.description   "+

//---------------------------------------------------------------------------------------------------------------------
		 
" 			 union  "+

" 			 select   "+
		 
" 			 f.districtid,f.description,  "+
		 
" 			  0 as boxes_CL2, 0 as bl_CL2,   0 as totbottl_CL2,  0 as totduty_CL2,  "+
		 
" 			 f.boxes_fl2b,f.bl_fl2b,f.totbottl_fl2b,f.totduty_fl2b,  "+
" 			 0 as boxes_FL2, 0 as bl_FL2,   0 as totbottl_FL2,  0 as totduty_FL2  "+
" 			from   "+
" 			 ( select k.districtid,k.description as description,k.boxes as boxes_fl2b,k.bl as bl_fl2b,k.totbottl as totbottl_fl2b,k.totduty as totduty_fl2b  "+
" 			from   "+
" 			 (SELECT a.districtid, a.description, SUM(COALESCE(x.boxes,0)) as boxes, SUM(COALESCE(x.bl,0)) as bl,SUM(COALESCE(x.totbottl,0))as totbottl,   "+
" 			 SUM(COALESCE(x.totduty,0))as totduty FROM public.district a LEFT OUTER JOIN  (SELECT f.core_district_id as lic_district, SUM(c.dispatch_box) as boxes,  "+
" 			 SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl, SUM(c.dispatch_bottle)as totbottl,   "+
" 			 SUM(c.dispatch_bottle) * (d.duty + d.adduty)as totduty FROM distillery.gatepass_to_districtwholesale_"+year+" b, distillery.fl2_stock_trxn_"+year+" c,    "+
" 			 distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f   "+
" 			 WHERE b.int_dist_id=c.int_dissleri_id AND b.vch_gatepass_no=c.vch_gatepass_no  AND b.dt_date=c.dt   "+
" 			 AND b.vch_to='FL2B' AND c.int_pckg_id=d.package_id  AND  b.vch_to_lic_no=f.vch_licence_no AND b.vch_to=f.vch_license_type  "+
" 			 AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'     GROUP BY f.core_district_id ,d.duty , d.adduty   "+
		 
" 			 UNION    "+
		 
" 			 SELECT f.core_district_id as lic_district, SUM(c.dispatch_box) as boxes,    "+
" 			 SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl,     "+
" 			 SUM(c.dispatch_bottle)as totbottl, SUM(c.dispatch_bottle) * (d.duty + d.adduty)as totduty  FROM bwfl_license.gatepass_to_districtwholesale_bwfl_"+year+" b,  "+
" 			 bwfl_license.fl2_stock_trxn_bwfl_"+year+" c,  distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f   "+
" 			 WHERE b.int_bwfl_id=c.int_bwfl_id AND b.vch_gatepass_no=c.vch_gatepass_no AND b.dt_date=c.dt AND b.vch_from='BWFL2B' AND c.int_pckg_id=d.package_id   "+
" 			 AND  b.vch_to_lic_no=f.vch_licence_no AND f.vch_license_type='FL2B'  AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'      "+
" 			 GROUP BY f.core_district_id, d.duty , d.adduty   "+
		 
" 			 UNION    "+
		 
" 			 SELECT f.core_district_id as lic_district, SUM(c.dispatch_box) as boxes,  "+
" 			 SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl,    "+
" 			 SUM(c.dispatch_bottle)as totbottl,(c.cal_duty)as totduty  FROM fl2d.gatepass_to_districtwholesale_fl2d_"+year+" b, fl2d.fl2d_stock_trxn c,   "+
" 			 distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f   WHERE b.int_fl2d_id=c.int_fl2d_id AND b.vch_gatepass_no=c.vch_gatepass_no    "+
" 			 AND b.dt_date=c.dt AND b.vch_to='FL2B' AND c.int_pckg_id=d.package_id  AND  b.vch_to_lic_no=f.vch_licence_no AND b.vch_to=f.vch_license_type   "+
" 			 AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'    GROUP BY f.core_district_id ,cal_duty   "+ 
		 
" 			 UNION    "+
		 
" 			 SELECT f.core_district_id as lic_district,   "+
" 			 SUM(c.dispatch_box) as boxes,  SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl ,    "+
" 			 SUM(c.dispatch_bottle)as totbottl, SUM(c.dispatch_bottle) * (d.duty + d.adduty)as totduty  FROM bwfl.gatepass_to_districtwholesale_"+year+" b,   "+
" 			 bwfl.fl2_stock_trxn_"+year+" c,  distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f     "+
" 			 WHERE b.brewery_id=c.brewery_id AND b.vch_gatepass_no=c.vch_gatepass_no    "+
" 			 AND b.dt_date=c.dt AND b.vch_to='FL2B' AND c.int_pckg_id=d.package_id  AND  b.vch_to_lic_no=f.vch_licence_no AND b.vch_to=f.vch_license_type    "+
" 			 AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'      "+
" 			 GROUP BY f.core_district_id,d.duty, d.adduty  )x on a.districtid=x.lic_district  WHERE a.districtid !=0    "+
" 			 GROUP BY a.districtid, a.description)k)f  "+
		 
//---------------------------------------------------------------------------------------------------------------------
		 
" 			 union  "+
		 

		 

" 			select   "+
" 			  gf.districtid,gf.description,  "+
" 			   0 as boxes_CL2, 0 as bl_CL2,   0 as totbottl_CL2,  0 as totduty_CL2,   "+
" 			 0 as boxes_fl2b,0 as  bl_fl2b, 0 as totbottl_fl2b,0 as totduty_fl2b,  "+
" 			gf.boxes_FL2,gf.bl_FL2,gf.totbottl_FL2,gf.totduty_FL2  "+
" 			 from  "+
" 			 (  "+
" 			 SELECT a.districtid, a.description, SUM(COALESCE(x.boxes,0)) as boxes_FL2, SUM(COALESCE(x.bl,0)) as bl_FL2,SUM(COALESCE(x.totbottl,0))as totbottl_FL2,   "+
" 			 SUM(COALESCE(x.totduty,0))as totduty_FL2  FROM public.district a LEFT OUTER JOIN  (SELECT f.core_district_id as lic_district,  "+
" 			 SUM(c.dispatch_box) as boxes, SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl,   "+
" 			 SUM(c.dispatch_bottle)as totbottl, SUM(c.dispatch_bottle) * (d.duty + d.adduty) as totduty  from distillery.gatepass_to_districtwholesale_"+year+" b,  "+
" 			 distillery.fl2_stock_trxn_"+year+" c,  distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f  "+
" 			 WHERE b.int_dist_id=c.int_dissleri_id AND b.vch_gatepass_no=c.vch_gatepass_no  AND b.dt_date=c.dt AND b.vch_to='FL2'   "+
" 			 AND c.int_pckg_id=d.package_id  AND  b.vch_to_lic_no=f.vch_licence_no AND b.vch_to=f.vch_license_type  "+
" AND b.dt_date BETWEEN 	'"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'   "+
" 			  GROUP BY f.core_district_id ,d.duty , d.adduty   "+

" 			 UNION   "+
		 
" 			 SELECT f.core_district_id as lic_district,   "+
" 			 SUM(c.dispatch_box) as boxes, SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl,   "+
" 			 SUM(c.dispatch_bottle)as totbottl, SUM(c.dispatch_bottle) * (d.duty + d.adduty)as totduty  "+
" 			 from bwfl_license.gatepass_to_districtwholesale_bwfl_"+year+" b, bwfl_license.fl2_stock_trxn_bwfl_"+year+" c,   "+
" 			 distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f  WHERE b.int_bwfl_id=c.int_bwfl_id AND b.vch_gatepass_no=c.vch_gatepass_no  "+
" 			 AND b.dt_date=c.dt AND c.int_pckg_id=d.package_id  AND  b.vch_to_lic_no=f.vch_licence_no AND f.vch_license_type='FL2'    "+
" 			 AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'     GROUP BY f.core_district_id, d.duty , d.adduty    "+
		 
" 			 UNION   "+

" 			 SELECT f.core_district_id as lic_district, SUM(c.dispatch_box) as boxes,  "+
" 			 SUM(round(CAST(float8(((c.dispatch_box*c.size::int)*d.quantity)/1000) as numeric), 2)) as bl,   "+
" 			 SUM(c.dispatch_bottle)as totbottl,(c.cal_duty)as totduty from fl2d.gatepass_to_districtwholesale_fl2d_"+year+" b, fl2d.fl2d_stock_trxn c,   "+
" 			 distillery.packaging_details_"+year+" d, licence.fl2_2b_2d_"+year+" f   WHERE b.int_fl2d_id=c.int_fl2d_id AND b.vch_gatepass_no=c.vch_gatepass_no   "+
" 			 AND b.dt_date=c.dt AND b.vch_to='FL2' AND c.int_pckg_id=d.package_id   AND  b.vch_to_lic_no=f.vch_licence_no AND b.vch_to=f.vch_license_type   "+
" 			 AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'     GROUP BY f.core_district_id,cal_duty  )x on a.districtid=x.lic_district    "+
" 			 WHERE a.districtid !=0  GROUP BY a.districtid, a.description)gf  "+


" 		)kf group by kf.description ";
			
		String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		

		try {			
			
			
			pstmt = con.prepareStatement(excelQuery);
			
			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");

			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 7000);
			worksheet.setColumnWidth(2, 7000);
			worksheet.setColumnWidth(3, 7000);
			
			worksheet.setColumnWidth(4, 7000);
			worksheet.setColumnWidth(5, 7000);
			
			worksheet.setColumnWidth(6, 7000);
			worksheet.setColumnWidth(7, 9000);
			worksheet.setColumnWidth(8, 9000);
			

			worksheet.setColumnWidth(9, 7000);
			worksheet.setColumnWidth(10, 9000);
			worksheet.setColumnWidth(11, 9000);
			
			worksheet.setColumnWidth(12, 7000);
			worksheet.setColumnWidth(13, 9000);
			worksheet.setColumnWidth(14, 9000);
			
			
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);			
			
			cellhead0.setCellValue(" District Wise Report Of " + type + " " + " From " + " " + Utility.convertUtilDateToSQLDate(act.getFromDate())+ " " + 
			" To " + " "+ Utility.convertUtilDateToSQLDate(act.getToDate()));
			
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

			
			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("District");
			cellhead2.setCellStyle(cellStyle);
		
			
			//--------------------------------------------------------------------------
			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("FL Boxes");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("FL Bottles");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("FL Duty");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("FL BL");
			cellhead6.setCellStyle(cellStyle);
			
			//--------------------------------------------------------------------------
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("FL2B Boxes");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("FL2B Bottles");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("FL2B Duty");
			cellhead9.setCellStyle(cellStyle);
			
			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("FL2B BL");
			cellhead10.setCellStyle(cellStyle);
			
			//--------------------------------------------------------------------------
			
			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("CL Boxes");
			cellhead11.setCellStyle(cellStyle);

			XSSFCell cellhead12 = rowhead.createCell((int) 11);
			cellhead12.setCellValue("CL Bottles");
			cellhead12.setCellStyle(cellStyle);

			XSSFCell cellhead13 = rowhead.createCell((int) 12);
			cellhead13.setCellValue("CL Duty");
			cellhead13.setCellStyle(cellStyle);
			
			XSSFCell cellhead14 = rowhead.createCell((int) 13);
			cellhead14.setCellValue("CL BL");
			cellhead14.setCellStyle(cellStyle);
			
		//-------------------------------------------------------------
		
			

			while (rs.next()) {

			

				boxesTotal_fl = boxesTotal_fl + rs.getDouble("boxes_FL2");
				blTotal_fl = blTotal_fl + rs.getDouble("bl_FL2");
				totbot_fl = totbot_fl + rs.getDouble("totbottl_FL2");
				totduty_fl = totduty_fl + rs.getDouble("totduty_FL2");
				
				
				boxesTotal_fl2b = boxesTotal_fl2b + rs.getDouble("boxes_FL2B");
				blTotal_fl2b = blTotal_fl2b + rs.getDouble("bl_FL2B");
				totbot_fl2b = totbot_fl2b + rs.getDouble("totbottl_FL2B");
				totduty_fl2b = totduty_fl2b + rs.getDouble("totduty_FL2B");
				
				boxesTotal_cl2 = boxesTotal_cl2 + rs.getDouble("boxes_cl");
				blTotal_cl2 = blTotal_cl2 + rs.getDouble("bl_cl");
				totbot_cl2 = totbot_cl2 + rs.getDouble("totbottl_cl");
				totduty_cl2 = totduty_cl2 + rs.getDouble("totduty_cl");
				
				
				
				
				k++;

				XSSFRow row1 = worksheet.createRow((int) k);

				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));
				
				//-----------------------------------------------
				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getInt("boxes_FL2"));

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getInt("totbottl_FL2"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getDouble("totduty_FL2"));
				
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("bl_FL2"));
				
				//-----------------------------------------------
				XSSFCell cellG1 = row1.createCell((int)6);
				cellG1.setCellValue(rs.getInt("boxes_fl2b"));

				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getInt("totbottl_fl2b"));

				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getDouble("totduty_fl2b"));
				
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getDouble("bl_fl2b"));
				
				//-----------------------------------------------
				XSSFCell cellK1 = row1.createCell((int)10);
				cellK1.setCellValue(rs.getInt("boxes_cl"));

				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(rs.getInt("totbottl_cl"));

				XSSFCell cellM1 = row1.createCell((int) 12);
				cellM1.setCellValue(rs.getDouble("totduty_cl"));
				
				XSSFCell cellN1 = row1.createCell((int)13);
				cellN1.setCellValue(rs.getDouble("bl_cl"));
								
			
			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "-FL2_FL2B_CL2_DistrictWise_Report.xlsx");
			act.setExlname(n + "-FL2_FL2B_CL2_DistrictWise_Report");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);

			XSSFCell cellA2 = row1.createCell((int) 1);
			cellA2.setCellValue("Total");
			cellA2.setCellStyle(cellStyle);

			
			
			
			
			
			
		
			//----------------------fl-----tot----------------------
			XSSFCell cellA3 = row1.createCell((int) 2);
			cellA3.setCellValue(boxesTotal_fl);
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 3);
			cellA4.setCellValue(totbot_fl);
			cellA4.setCellStyle(cellStyle);
			
			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(totduty_fl);
			cellA5.setCellStyle(cellStyle);
			
			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(blTotal_fl);
			cellA6.setCellStyle(cellStyle);
			
			//----------------------fl2b-----tot----------------------
			
			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(boxesTotal_fl2b);
			cellA7.setCellStyle(cellStyle);

			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(totbot_fl2b);
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(totduty_fl2b);
			cellA9.setCellStyle(cellStyle);
			
			XSSFCell cellA10 = row1.createCell((int) 9);
			cellA10.setCellValue(blTotal_fl2b);
			cellA10.setCellStyle(cellStyle);
			
			
			
			//----------------------cl2----tot----------------------
			
			XSSFCell cellA11 = row1.createCell((int) 10);
			cellA11.setCellValue(boxesTotal_cl2);
			cellA11.setCellStyle(cellStyle);

			XSSFCell cellA12 = row1.createCell((int) 11);
			cellA12.setCellValue(totbot_cl2);
			cellA12.setCellStyle(cellStyle);
			
			XSSFCell cellA13 = row1.createCell((int) 12);
			cellA13.setCellValue(totduty_cl2);
			cellA13.setCellStyle(cellStyle);
			
			XSSFCell cellA14 = row1.createCell((int) 13);
			cellA14.setCellValue(blTotal_cl2);
			cellA14.setCellStyle(cellStyle);
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
	
			flag = true;
			act.setExcelFlag(true);
			con.close();
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
		return flag;

	
	
	}
	//======================================Year List========================================
	public ArrayList yearListImpl(FL2_2B_CL2_DistrictWiseAction act) {
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
	public String getDetails(FL2_2B_CL2_DistrictWiseAction act) {

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
