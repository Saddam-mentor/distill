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

import com.mentor.action.DispatcherReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class DispatcherReportImpl {
	
	
	

	public   ArrayList getAll( DispatcherReportAction ac ) {
		
		
		ArrayList list=new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String query =null;
		
		SelectItem item=new SelectItem();
		item.setLabel("--ALL--");
		item.setValue("99999");
		list.add(item);
		try
		{
			if(ac.getRadioCLandFL().equalsIgnoreCase("BWFL")){
				 query =
						 " select int_id as app_id , concat(vch_firm_name,'-(' ,vch_firm_district_name,')')  as app_name, " +
						 " vch_firm_district_name as district from bwfl.registration_of_bwfl_lic_holder " +
						 " where vch_approval='V' order by app_name ";
					}
					else if(ac.getRadioCLandFL().equalsIgnoreCase("FL2D")){
						
						query=
								" select int_app_id as app_id , concat( vch_firm_name ,'-(' ,vch_licence_no,')') as app_name "+
										 "   FROM licence.fl2_2b_2d where vch_license_type='FL2D' order by app_name " ;
					}
					else if(ac.getRadioCLandFL().equalsIgnoreCase("D") || ac.getRadioCLandFL().equalsIgnoreCase("DD")){
						
						query="  SELECT int_app_id_f as app_id, " +
								" concat( vch_undertaking_name ,'-(' ,vch_pd1_pd2_lic_no,')') as app_name "+
								" FROM public.dis_mst_pd1_pd2_lic where vch_finalize='F'  order by app_name ";
					}
					else if(ac.getRadioCLandFL().equalsIgnoreCase("B") || ac.getRadioCLandFL().equalsIgnoreCase("BD")){
						
						query=" select   vch_app_id_f as app_id  , "+
							"	concat( brewery_nm ,'-(' ,vch_license_no,')') as app_name "+
							"	from public.bre_mst_b1_lic m  where vch_finalize_flag ='F' order by app_name" ;
					}
		
		conn = ConnectionToDataBase.getConnection();
		pstmt=conn.prepareStatement(query);
		
		rs=pstmt.executeQuery();
		
		while(rs.next())
		{
		item=new SelectItem();
		
		item.setValue(rs.getString("app_id"));
		item.setLabel(rs.getString("app_name"));
		list.add(item);
		
		}
		
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		finally
		{
		try
		{	
		
		if(conn!=null)conn.close();
		if(pstmt!=null)pstmt.close();
		if(rs!=null)rs.close();
		
		
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		}
		return list;
		}

		
	
	
	
	
	
	
	
	//------------------------------- End ---------------------
	
	/*public void printReportCountryLiquor(DispatcherReportAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		// String
		// relativePath=mypath+File.separator+"ExciseUp"+File.separator+"reports";

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "pdf";

		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		try {
			con = ConnectionToDataBase.getConnection();

			reportQuery = 
						 * "	SELECT distinct a.int_dist_id, a.vch_distillary_name, a.vch_distillary_address,a.licensee_name, "
						 * +
						 * "	a.vch_gatepass_no, a.dt_date,a.licence_district,c.description, "
						 * +
						 * "	sum( b.dispatchd_bottl) as botl,sum( b.dispatchd_box) as box "
						 * +
						 * "	FROM distillery.gatepass_to_manufacturewholesale_cl a , "
						 * + "	 distillery.cl2_stock_trxn b, "+
						 * "	 public.district c "+
						 * "	where a.int_dist_id=b.int_dissleri_id "+
						 * "	and a.vch_gatepass_no=b.vch_gatepass_no "+
						 * "	and a.dt_date=b.dt_date and  a.dt_date between ? and ?  "
						 * +
						 * "	and a.licence_district=c.districtid    group by  a.int_dist_id, a.vch_distillary_name, a.vch_distillary_address,a.licensee_name, "
						 * +
						 * "	a.vch_gatepass_no, a.dt_date,a.licence_district,c.description, b.dt_date order by a.dt_date ,c.description "
						 * ;
						 

			"	SELECT distinct a.int_dist_id, a.vch_distillary_name, a.vch_distillary_address,a.licensee_name,  "
					+ "	a.vch_gatepass_no, a.dt_date, "
					+ "	sum( b.dispatchd_bottl) as botl,sum( b.dispatchd_box) as box , "
					+ "	a.db_total_duty as duty, "
					+ "	(  select c.description from  public.district c where d.vch_unit_dist=c.districtid :: text ) as district "
					+ "	 FROM distillery.gatepass_to_manufacturewholesale_cl a ,  "
					+ "	 distillery.cl2_stock_trxn b,  "
					+ "	public.dis_mst_pd1_pd2_lic d  "
					+ "	where a.int_dist_id=b.int_dissleri_id  and a.db_total_duty >0 "
					+ "	and a.vch_gatepass_no=b.vch_gatepass_no  "
					+ "	and a.dt_date=b.dt_date and  a.dt_date between ? and ?  "
					+ "	and a.int_dist_id=d.int_app_id_f "
					+ "	group by  a.int_dist_id, a.vch_distillary_name, "
					+ "	a.vch_distillary_address,a.licensee_name,  "
					+ "	a.vch_gatepass_no, a.dt_date,a.licence_district, b.dt_date,district order by a.dt_date, district  ";

			pst = con.prepareStatement(reportQuery);

			pst.setDate(1,
					Utility.convertUtilDateToSQLDate(action.getFromdate()));
			pst.setDate(2, Utility.convertUtilDateToSQLDate(action.getTodate()));
			rs = pst.executeQuery();
			if (rs.next()) {
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator
						+ "FL11B_DispatecheReportOfCountryLiquor.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "FL11B_DispatecheReportOfCountryLiquor" + n
								+ ".pdf");
				action.setPdfname("FL11B_DispatecheReportOfCountryLiquor" + n
						+ ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
				action.setPrintFlag(false);
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

	// ---------------------------- ForeignLiquor ---------------

	public void printReportForeignLiquor(DispatcherReportAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		// String
		// relativePath=mypath+File.separator+"ExciseUp"+File.separator+"reports";

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "pdf";

		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		try {
			con = ConnectionToDataBase.getConnection();

			reportQuery =

			
			 * "	select distinct  a.int_dist_id, a.vch_gatepass_no , a.licensee_name, "
			 * + "	a.dt_date, a.licencee_district ,  "+
			 * "	b.vch_gatepass_no ,sum(b.dispatch_box),b.dt , "+
			 * "	c.int_app_id_f, c.vch_undertaking_name, "+
			 * "	case when a.licencee_district is null then a.licensee_adrs else (select d.description from public.district d where a.licencee_district=d.districtid ) end as description     "
			 * + "	FROM distillery.gatepass_to_districtwholesale a , "+
			 * "	distillery.fl2_stock_trxn b, "+
			 * "	public.dis_mst_pd1_pd2_lic c  "+
			 * "	where a.int_dist_id=b.int_dissleri_id "+
			 * "	and a.vch_gatepass_no=b.vch_gatepass_no "+
			 * "	and a.dt_date=b.dt and  a.dt_date between ? and ?  "+
			 * "	and a.int_dist_id=c.int_app_id_f   group by  a.int_dist_id, a.vch_gatepass_no ,  "
			 * +
			 * "			a.dt_date, a.licencee_district ,b.vch_gatepass_no ,b.dt , 	c.int_app_id_f, c.vch_undertaking_name,a.licensee_name,a.licensee_adrs "
			 * + "  order by b.dt,c.vch_undertaking_name";
			 

			"	select distinct  a.int_dist_id, a.vch_gatepass_no ,  "
					+ "	a.dt_date,   "
					+ "	b.vch_gatepass_no ,sum(b.dispatchd_box),  "
					+ "	c.int_app_id_f, c.vch_undertaking_name , "
					+ "	 a.vch_to , "
					+ "	case when a.vch_to='EXPORT' then a.db_total_duty end as exportduty, "
					+ "	case when a.vch_to !='EXPORT' then a.db_total_duty end as db_total_duty, "
					+ "	 (  select d.description from  public.district d where c.vch_unit_dist=d.districtid :: text )  "
					+ "	       as district "
					+ "	FROM distillery.gatepass_to_manufacturewholesale a ,  "
					+ "	distillery.fl1_stock_trxn b,  "
					+ "	public.dis_mst_pd1_pd2_lic c   "
					+ "	where a.int_dist_id=b.int_dissleri_id  and a.db_total_duty>0  "
					+ "	and a.vch_gatepass_no=b.vch_gatepass_no  "
					+ "	and  a.dt_date between ? and ?  "
					+ "	and a.int_dist_id=c.int_app_id_f   group by  a.int_dist_id, a.vch_gatepass_no ,   "
					+ "			a.dt_date ,b.vch_gatepass_no ,  "
					+ "	        c.int_app_id_f, c.vch_undertaking_name "
					+ "	order by a.dt_date,c.vch_undertaking_name ";

			pst = con.prepareStatement(reportQuery);
			pst.setDate(1,
					Utility.convertUtilDateToSQLDate(action.getFromdate()));
			pst.setDate(2, Utility.convertUtilDateToSQLDate(action.getTodate()));
			rs = pst.executeQuery();

			if (rs.next()) {
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator
						+ "FL11B_DispatecheReportOfForeignLiquor.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "FL11B_DispatecheReportOfForeignLiquor" + n
								+ ".pdf");
				action.setPdfname("FL11B_DispatecheReportOfForeignLiquor" + n
						+ ".pdf");
				action.setPrintFlag(true);

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
				action.setPrintFlag(false);
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

	public void printReportBeer(DispatcherReportAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		// String
		// relativePath=mypath+File.separator+"ExciseUp"+File.separator+"reports";

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "pdf";

		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		try {
			con = ConnectionToDataBase.getConnection();

			reportQuery =
			
			 * "	select distinct  a.brewery_id as vch_app_id_f, a.vch_gatepass_no ,a.licensee_name,  "
			 * + "	a.dt_date, a.licencee_district ,  "+
			 * "	b.vch_gatepass_no ,sum(b.dispatch_box),b.dt , "+
			 * "	c.vch_app_id_f, c.brewery_nm as vch_undertaking_name, "+
			 * "	case when a.licencee_district is null then a.licensee_adrs else (select d.description from public.district d where a.licencee_district=d.districtid ) end as description     "
			 * + "	FROM bwfl.gatepass_to_districtwholesale a , "+
			 * "	bwfl.fl2_stock_trxn b, "+ "	public.bre_mst_b1_lic c  "+
			 * "	where a.brewery_id=b.brewery_id "+
			 * "	and a.vch_gatepass_no=b.vch_gatepass_no "+
			 * "	and a.dt_date=b.dt and  a.dt_date between ? and ?  "+
			 * "	and a.brewery_id=c.vch_app_id_f   group by  a.brewery_id, a.vch_gatepass_no ,  "
			 * +
			 * "			a.dt_date, a.licencee_district ,b.vch_gatepass_no ,a.licensee_name,b.dt , 	c.vch_app_id_f, c.brewery_nm,a.licensee_adrs "
			 * + "  order by b.dt,c.brewery_nm";
			 

			"	select distinct  a.int_brewery_id as vch_app_id_f, a.vch_gatepass_no ,  "
					+ "	a.dt_date,   "
					+ "	b.vch_gatepass_no ,sum(b.dispatchd_box) ,  "
					+ "	c.vch_app_id_f, c.brewery_nm as vch_undertaking_name, "
					+ "	a.vch_to, "
					+ "	case when a.vch_to='EXPORT' then a.db_total_duty end as exportduty, "
					+ "	case when a.vch_to !='EXPORT' then a.db_total_duty end as db_total_duty, "
					+ "	(select d.description from public.district d where c.int_reg_district_id=d.districtid ::text )  "
					+ "	as description    "
					+ "	FROM bwfl.gatepass_to_manufacturewholesale a ,  "
					+ "	bwfl.fl1_stock_trxn b,  "
					+ "	public.bre_mst_b1_lic c   "
					+ "	where a.int_brewery_id=b.int_brewery_id   and a.db_total_duty>0  "
					+ "	and a.vch_gatepass_no=b.vch_gatepass_no  "
					+ "	 and  a.dt_date between ? and ?  "
					+ "	and a.int_brewery_id=c.vch_app_id_f   group by  a.int_brewery_id, a.vch_gatepass_no ,  "
					+ "			a.dt_date  ,b.vch_gatepass_no  , "
					+ "	        c.vch_app_id_f, c.brewery_nm "
					+ "	order by a.dt_date,c.brewery_nm ";

			pst = con.prepareStatement(reportQuery);
			pst.setDate(1,
					Utility.convertUtilDateToSQLDate(action.getFromdate()));
			pst.setDate(2, Utility.convertUtilDateToSQLDate(action.getTodate()));
			rs = pst.executeQuery();

			if (rs.next()) {
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator
						+ "FL11B_DispatecheReportOfBeer.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(550) + 1;

				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "FL11B_DispatecheReportOfBeer" + n + ".pdf");
				action.setPdfname("FL11B_DispatecheReportOfBeer" + n + ".pdf");
				action.setPrintFlag(true);

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
				action.setPrintFlag(false);
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

	public boolean write(DispatcherReportAction action) {
		Connection con = null;
		String type = "";
		long tot = 0;
		long totalduty = 0;
		long totalExportduty = 0;

		con = ConnectionToDataBase.getConnection();
		String sql = "";
		if (action.getRadioCLandFL().equalsIgnoreCase("CL")) {
			type = "Country Liquor";

			sql = "	SELECT distinct a.int_dist_id, a.vch_distillary_name, a.vch_distillary_address,a.licensee_name,  "
					+ "	a.vch_gatepass_no, a.dt_date, "
					+ "	sum( b.dispatchd_bottl) as botl,sum( b.dispatchd_box) as box , "
					+ "	a.db_total_duty as duty, "
					+ "	(  select c.description from  public.district c where d.vch_unit_dist=c.districtid :: text ) as district "
					+ "	 FROM distillery.gatepass_to_manufacturewholesale_cl a ,  "
					+ "	 distillery.cl2_stock_trxn b,  "
					+ "	public.dis_mst_pd1_pd2_lic d  "
					+ "	where a.int_dist_id=b.int_dissleri_id  "
					+ "	and a.vch_gatepass_no=b.vch_gatepass_no  "
					+ "	and a.dt_date=b.dt_date and  a.dt_date between ? and ?  "
					+ "	and a.int_dist_id=d.int_app_id_f "
					+ "	group by  a.int_dist_id, a.vch_distillary_name, "
					+ "	a.vch_distillary_address,a.licensee_name,  "
					+ "	a.vch_gatepass_no, a.dt_date,a.licence_district, b.dt_date,district order by a.dt_date, district  ";

		} else if (action.getRadioCLandFL().equalsIgnoreCase("FL")) {
			type = "Foreign Liquor";

			sql =

			"	select distinct  a.int_dist_id, a.vch_gatepass_no ,  "
					+ "	a.dt_date,   "
					+ "	b.vch_gatepass_no, sum(b.dispatchd_box) as cases ,  "
					+ "	c.int_app_id_f, c.vch_undertaking_name , "
					+ "	 a.vch_to , "
					+ "	case when a.vch_to='EXPORT' then a.db_total_duty end as exportduty, "
					+ "	case when a.vch_to !='EXPORT' then a.db_total_duty end as db_total_duty, "
					+ "	 (  select d.description from  public.district d where c.vch_unit_dist=d.districtid :: text )  "
					+ "	       as district "
					+ "	FROM distillery.gatepass_to_manufacturewholesale a ,  "
					+ "	distillery.fl1_stock_trxn b,  "
					+ "	public.dis_mst_pd1_pd2_lic c   "
					+ "	where a.int_dist_id=b.int_dissleri_id  and a.db_total_duty>0  "
					+ "	and a.vch_gatepass_no=b.vch_gatepass_no  "
					+ "	and  a.dt_date between ? and ?  "
					+ "	and a.int_dist_id=c.int_app_id_f   group by  a.int_dist_id, a.vch_gatepass_no ,   "
					+ "			a.dt_date ,b.vch_gatepass_no ,  "
					+ "	        c.int_app_id_f, c.vch_undertaking_name "
					+ "	order by a.dt_date,c.vch_undertaking_name ";

		} else if (action.getRadioCLandFL().equalsIgnoreCase("B")) {
			type = "Beer";

			sql = "	select distinct  a.int_brewery_id as vch_app_id_f, a.vch_gatepass_no ,  "
					+ "	a.dt_date,   "
					+ "	b.vch_gatepass_no ,sum(b.dispatchd_box)  as cases ,  "
					+ "	c.vch_app_id_f, c.brewery_nm as vch_undertaking_name, "
					+ "	a.vch_to, "
					+ "	case when a.vch_to='EXPORT' then a.db_total_duty end as exportduty, "
					+ "	case when a.vch_to !='EXPORT' then a.db_total_duty end as db_total_duty, "
					+ "	(select d.description from public.district d where c.int_reg_district_id=d.districtid ::text )  "
					+ "	as description    "
					+ "	FROM bwfl.gatepass_to_manufacturewholesale a ,  "
					+ "	bwfl.fl1_stock_trxn b,  "
					+ "	public.bre_mst_b1_lic c   "
					+ "	where a.int_brewery_id=b.int_brewery_id   and a.db_total_duty>0  "
					+ "	and a.vch_gatepass_no=b.vch_gatepass_no  "
					+ "	 and  a.dt_date between ? and ?  "
					+ "	and a.int_brewery_id=c.vch_app_id_f   group by  a.int_brewery_id, a.vch_gatepass_no ,  "
					+ "			a.dt_date  ,b.vch_gatepass_no  , "
					+ "	        c.vch_app_id_f, c.brewery_nm "
					+ "	order by a.dt_date,c.brewery_nm ";

			System.out.println("----------  beer ----" + sql);

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
			pstmt.setDate(1,
					Utility.convertUtilDateToSQLDate(action.getFromdate()));
			pstmt.setDate(2,
					Utility.convertUtilDateToSQLDate(action.getTodate()));
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			// CellStyle unlockedCellStyle = workbook.createCellStyle();
			// unlockedCellStyle.setLocked(false);
			// worksheet.protectSheet("UP-EX-MIS");
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 8000);
			worksheet.setColumnWidth(2, 4000);
			worksheet.setColumnWidth(3, 9999);
			worksheet.setColumnWidth(4, 9999);
			worksheet.setColumnWidth(5, 4000);
			worksheet.setColumnWidth(6, 10000);
			worksheet.setColumnWidth(7, 5000);
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" Dispatch Of " + type);
			rowhead0.setHeight((short) 700);
			// cellhead0.setCellStyle(unlockedCellStyle);
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
			cellhead2.setCellValue("FL11 Pass No.");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Date");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Distillery Name");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Dispatch Type");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Number Of Boxes");
			cellhead6.setCellStyle(cellStyle);

			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Duty");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("Export Duty");
			cellhead8.setCellStyle(cellStyle);

			while (rs.next()) {
				tot = tot + rs.getLong("cases");
				totalduty = totalduty + rs.getLong("db_total_duty");
				totalExportduty = totalExportduty + rs.getLong("exportduty");

				Date dat = Utility.convertSqlDateToUtilDate(rs
						.getDate("dt_date"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);

				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("vch_gatepass_no"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(date);
				// cellC1.setCellStyle(unlockcellStyle);

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("vch_undertaking_name"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("vch_to"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("cases"));

				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("db_total_duty"));
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getString("exportduty"));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//WholeSale//Excel//"
					+ action.getRadioCLandFL() + n + "Dispatch.xls");
			action.setExlname(action.getRadioCLandFL() + n);
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			// XSSFCell cellA1 = row1.createCell((int) 0);
			// cellA1.setCellValue("End");
			// cellA1.setCellStyle(cellStyle);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);

			XSSFCell cellA2 = row1.createCell((int) 1);
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 2);
			cellA3.setCellValue(" ");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 3);
			cellA4.setCellValue(" ");
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue("Total ");
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(tot);
			cellA6.setCellStyle(cellStyle);

			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(totalduty);
			cellA7.setCellStyle(cellStyle);

			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(totalExportduty);
			cellA8.setCellStyle(cellStyle);

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			action.setExcelFlag(true);
			con.close();

		} catch (Exception e) {

			System.out.println("xls2" + e.getMessage());
			e.printStackTrace();
		}

		return flag;
	}
*/
	public synchronized long getcaseNo() {
		String sql = " select     nextval('public.case_seq')";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long serialNo = 0;
		try {
			conn = ConnectionToDataBase.getConnection3();

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				serialNo = rs.getInt(1);
				if (serialNo == 0) {
					serialNo = serialNo;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();

				if (pstmt != null)
					pstmt.close();

				if (conn != null)
					conn.close();

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage(), e.getMessage()));
				e.printStackTrace();
			}
		}

		return serialNo;
	}

	// --------------------------------- excel CL ---------------

	/*public boolean writeCL(DispatcherReportAction action) {
		Connection con = null;
		String type = "";
		long tot = 0;
		long totalduty = 0;
		con = ConnectionToDataBase.getConnection();
		String sql = "";
		if (action.getRadioCLandFL().equalsIgnoreCase("CL")) {
			type = "Country Liquor";

			System.out.println("------ cl ----");

			sql = " SELECT distinct a.int_dist_id, a.vch_distillary_name as vch_undertaking_name, a.vch_distillary_address,a.licensee_name,  "
					+ "	a.vch_gatepass_no, a.dt_date, "
					+ "	sum( b.dispatchd_bottl) as botl,sum( b.dispatchd_box) as cases , "
					+ "	 a.db_total_duty   as totalduty, "
					+ "	(  select c.description from  public.district c where d.vch_unit_dist=c.districtid :: text ) as description "
					+ "	 FROM distillery.gatepass_to_manufacturewholesale_cl a ,  "
					+ "	 distillery.cl2_stock_trxn b,  "
					+ "	public.dis_mst_pd1_pd2_lic d  "
					+ "	where a.int_dist_id=b.int_dissleri_id  and a.db_total_duty >0 "
					+ "	and a.vch_gatepass_no=b.vch_gatepass_no  "
					+ "	and a.dt_date=b.dt_date and  a.dt_date between ? and ?  "
					+ "	and a.int_dist_id=d.int_app_id_f "
					+ "	group by  a.int_dist_id, a.vch_distillary_name, "
					+ "	a.vch_distillary_address,a.licensee_name,  "
					+ "	a.vch_gatepass_no, a.dt_date,a.licence_district,description, b.dt_date order by a.dt_date ,description  ";

		} else {
			sql = "";
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
			pstmt.setDate(1,
					Utility.convertUtilDateToSQLDate(action.getFromdate()));
			pstmt.setDate(2,
					Utility.convertUtilDateToSQLDate(action.getTodate()));
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			// CellStyle unlockedCellStyle = workbook.createCellStyle();
			// unlockedCellStyle.setLocked(false);
			// worksheet.protectSheet("UP-EX-MIS");
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 8000);
			worksheet.setColumnWidth(2, 4000);
			worksheet.setColumnWidth(3, 9999);
			worksheet.setColumnWidth(4, 9999);
			worksheet.setColumnWidth(5, 4000);
			worksheet.setColumnWidth(6, 10000);
			worksheet.setColumnWidth(7, 5000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" Dispatch Of " + type);
			rowhead0.setHeight((short) 700);
			// cellhead0.setCellStyle(unlockedCellStyle);
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
			cellhead2.setCellValue("FL11 Pass No.");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Date");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Distillery Name");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Number Of Boxes");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Duty");
			cellhead6.setCellStyle(cellStyle);

			
			 * XSSFCell cellhead7 = rowhead.createCell((int) 6);
			 * cellhead7.setCellValue("Consignee Address");
			 * cellhead7.setCellStyle(cellStyle);
			 * 
			 * XSSFCell cellhead8 = rowhead.createCell((int) 7);
			 * cellhead8.setCellValue("No.of Cases");
			 * cellhead8.setCellStyle(cellStyle);
			 

			while (rs.next()) {
				tot = tot + rs.getLong("cases");

				totalduty = totalduty + rs.getLong("totalduty");

				Date dat = Utility.convertSqlDateToUtilDate(rs
						.getDate("dt_date"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);

				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("vch_gatepass_no"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(date);
				// cellC1.setCellStyle(unlockcellStyle);

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("vch_undertaking_name"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("cases"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("totalduty"));

				
				 * XSSFCell cellG1 = row1.createCell((int) 6);
				 * cellG1.setCellValue(rs.getString("licensee_adrs")); XSSFCell
				 * cellH1 = row1.createCell((int)7);
				 * cellH1.setCellValue(rs.getString("cases"));
				 

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//WholeSale//Excel//"
					+ action.getRadioCLandFL() + n + "Dispatch.xls");
			action.setExlname(action.getRadioCLandFL() + n);
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			// XSSFCell cellA1 = row1.createCell((int) 0);
			// cellA1.setCellValue("End");
			// cellA1.setCellStyle(cellStyle);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);

			XSSFCell cellA2 = row1.createCell((int) 1);
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 2);
			cellA3.setCellValue(" ");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 3);
			cellA4.setCellValue("Total  ");
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(tot);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(totalduty);
			cellA6.setCellStyle(cellStyle);

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			action.setExcelFlag(true);
			con.close();

		} catch (Exception e) {

			System.out.println("xls2" + e.getMessage());
			e.printStackTrace();
		}

		return flag;
	}

	// --------------------------------------- report Dispatches BWFL
*/
	public void printDispatchesBWFL(DispatcherReportAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String filter=action.getBwfl_FL2d_Id();
		
		if(action.getBwfl_FL2d_Id().equalsIgnoreCase("99999"))
		{
			
			reportQuery = "	select 	distinct d.brand_name, e.quantity, 	a.int_bwfl_id as vch_app_id_f, a.vch_gatepass_no, " +
						 " a.licensee_name, a.dt_date,a.vch_from, a.licence_district , "+
						  " b.vch_gatepass_no, sum(b.dispatch_box), sum(b.duty) "+
						  " as duty, b.dt, c.int_id, c.vch_firm_name, c.vch_applicant_name, c.vch_firm_district_name , "+
					     " case when a.vch_to='DW'  then  'District Warehouse' else 'Bar/Restaurant/Club' end as vch_to, "+
					     " (select d.description from public.district d where a.licence_district=d.districtid ) as description, a.vch_from "+
				
						" FROM bwfl_license.gatepass_to_districtwholesale_2017_18_bwfl_old_stock a, bwfl_license.fl2_stock_trxn_bwfl_old_stock b, "+
						" bwfl.registration_of_bwfl_lic_holder c, distillery.brand_registration d, distillery.packaging_details e "+
						" where a.int_bwfl_id=b.int_bwfl_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt and  "+
						" a.dt_date between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
						" and a.int_bwfl_id=c.int_id and b.int_brand_id=d.brand_id and "+
					   "  b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk     "+
                       "  group by e.quantity, d.brand_name, "+
					  " a.int_bwfl_id, a.vch_gatepass_no, a.dt_date, a.licence_district, b.vch_gatepass_no, a.licensee_name,   "+
					  "  b.dt, c.vch_firm_name, a.licensee_adrs, c.int_id, a.vch_to, a.vch_from order by b.dt, description ";

			
		}
		else{
			reportQuery = "	select 	distinct d.brand_name, e.quantity, 	a.int_bwfl_id as vch_app_id_f, a.vch_gatepass_no, " +
					 " a.licensee_name, a.dt_date,a.vch_from, a.licence_district, "+
					  " b.vch_gatepass_no, sum(b.dispatch_box), sum(b.duty) "+
					  " as duty, b.dt, c.int_id, c.vch_firm_name, c.vch_applicant_name, c.vch_firm_district_name , "+
				     " case when a.vch_to='DW'  then  'District Warehouse' else 'Bar/Restaurant/Club' end as vch_to, "+
				     " (select d.description from public.district d where a.licence_district=d.districtid ) as description, a.vch_from "+
			
					" FROM bwfl_license.gatepass_to_districtwholesale_2017_18_bwfl_old_stock a, bwfl_license.fl2_stock_trxn_bwfl_old_stock b, "+
					" bwfl.registration_of_bwfl_lic_holder c, distillery.brand_registration d, distillery.packaging_details e "+
					" where a.int_bwfl_id=b.int_bwfl_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt and  "+
					" a.dt_date between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
					" and a.int_bwfl_id=c.int_id and b.int_brand_id=d.brand_id and "+
				   "  b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk  and c.int_id='"+filter+"'   "+
                  "  group by e.quantity, d.brand_name, "+
				  " a.int_bwfl_id, a.vch_gatepass_no, a.dt_date, a.licence_district, b.vch_gatepass_no, a.licensee_name,   "+
				  "  b.dt, c.vch_firm_name, a.licensee_adrs, c.int_id, a.vch_to, a.vch_from order by b.dt, description ";

			
			
			
		}
		
		try {
			con = ConnectionToDataBase.getConnection();	
			pst = con.prepareStatement(reportQuery);
		
			System.out.println("====11111===="+reportQuery);
			rs = pst.executeQuery();
			if (rs.next()) {
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
System.out.println("-----------------------------------------------------------------------------");
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "ReportOfDispatchesBWFLOldStock.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "ReportOfDispatchesBWFLOldStock" + n + ".pdf");
				action.setPdfname("ReportOfDispatchesBWFLOldStock" + n + ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
				action.setPrintFlag(false);
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

	// --------------------------------------- report Dispatches FL2D
	

	public void printFL2D(DispatcherReportAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String filter=action.getBwfl_FL2d_Id();
		try {
			con = ConnectionToDataBase.getConnection();
			
			if(action.getBwfl_FL2d_Id().equalsIgnoreCase("99999"))
			{

				reportQuery = " select distinct d.brand_name, e.quantity, a.int_fl2d_id as vch_app_id_f, a.vch_gatepass_no, " +
							  " a.licensee_name, a.dt_date, a.licencee_district, b.vch_gatepass_no, sum(b.dispatch_box), " +
							  " sum(b.cal_duty) as duty, b.dt, c.int_app_id, c.vch_firm_name, c.vch_applicant_name, a.vch_to, " +
							  " (select d.description from public.district d where a.licencee_district=d.districtid ) " +
							  " as description, a.vch_from,  " +
							 " (select h.description from  public.district h where COALESCE(c.core_district_id,0)=h.districtid ) as fl2d_district_Name " +
							  " FROM fl2d.gatepass_to_districtwholesale_fl2d_oldstock a, fl2d.fl2d_stock_trxn_oldstock b, " +
							  " licence.fl2_2b_2d c, distillery.brand_registration d, distillery.packaging_details e " +
							  " where a.int_fl2d_id=b.int_fl2d_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt " +
							  " and a.dt_date between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
							  " AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
							  " and a.int_fl2d_id=c.int_app_id and b.int_brand_id=d.brand_id and " +
							  " b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk     group by d.brand_name, e.quantity, " +
							  " a.int_fl2d_id, a.vch_gatepass_no, a.tot_cal_duty, a.dt_date, a.licencee_district, b.vch_gatepass_no, " +
							  " a.licensee_name, b.dt, c.vch_firm_name, c.vch_applicant_name, a.licensee_adrs, c.int_app_id, " +
							  " a.vch_to, a.vch_from,fl2d_district_Name  order by b.dt, description ";
			}
			else{
			
			
			

			reportQuery = " select distinct d.brand_name, e.quantity, a.int_fl2d_id as vch_app_id_f, a.vch_gatepass_no, " +
						  " a.licensee_name, a.dt_date, a.licencee_district, b.vch_gatepass_no, sum(b.dispatch_box), " +
						  " sum(b.cal_duty) as duty, b.dt, c.int_app_id, c.vch_firm_name, c.vch_applicant_name, a.vch_to, " +
						  " (select d.description from public.district d where a.licencee_district=d.districtid ) " +
						  " as description, a.vch_from,  " +
						  " (select h.description from  public.district h where COALESCE(c.core_district_id,0)=h.districtid ) as fl2d_district_Name " +
						  " FROM fl2d.gatepass_to_districtwholesale_fl2d_oldstock a, fl2d.fl2d_stock_trxn_oldstock b, " +
						  " licence.fl2_2b_2d c, distillery.brand_registration d, distillery.packaging_details e " +
						  " where a.int_fl2d_id=b.int_fl2d_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt " +
						  " and a.dt_date between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
						  " AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
						  	" and a.int_fl2d_id=c.int_app_id and b.int_brand_id=d.brand_id and " +
						  " b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk  and c.int_app_id='"+filter+"'   group by d.brand_name, e.quantity, " +
						  " a.int_fl2d_id, a.vch_gatepass_no, a.tot_cal_duty, a.dt_date, a.licencee_district, b.vch_gatepass_no, " +
						  " a.licensee_name, b.dt, c.vch_firm_name, c.vch_applicant_name, a.licensee_adrs, c.int_app_id, " +
						  " a.vch_to, a.vch_from,fl2d_district_Name  order by b.dt, description ";
			}
			pst = con.prepareStatement(reportQuery);
			
		
		
			System.out.println("====22222======"+action.getBwfl_FL2d_Id()+"==="+reportQuery);
			rs = pst.executeQuery();
			if (rs.next()) {
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator + "ReportOfDispatches_FL2DOldStock.jasper");
				JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print, relativePathpdf + File.separator + "ReportOfDispatches_FL2DOldStock" + n + ".pdf");
				action.setPdfname("ReportOfDispatches_FL2DOldStock" + n + ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found"));
				action.setPrintFlag(false);
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)rs.close();
				if (con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void printD(DispatcherReportAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String filter=action.getBwfl_FL2d_Id();
		try {
			con = ConnectionToDataBase.getConnection();
			
			
			if(action.getBwfl_FL2d_Id().equalsIgnoreCase("99999"))
			{
				
				reportQuery="	select distinct a.int_dist_id as vch_app_id_f, a.vch_gatepass_no,  a.licensee_name, a.dt_date, a.licencee_district,a.vch_to, "+
							"b.vch_gatepass_no, sum(b.dispatch_box),"+
								  
								"  b.dt,  c.int_app_id_f, c.vch_undertaking_name, "+
								"d.brand_name, e.quantity, "+
				  
							" (select d.description from public.district d where a.licencee_district=d.districtid )  as description, a.vch_from,  "+
							" (select h.description from  public.district h where COALESCE(c.vch_unit_dist,0::text)=h.districtid::text ) as distllery_district_Name "+
						
							"  FROM distillery.gatepass_to_districtwholesale_imfl_old_stock a, distillery.fl2_stock_trxn_imfl_old_stock b, "+
							" 	 public.dis_mst_pd1_pd2_lic c, distillery.brand_registration d, distillery.packaging_details e "+
						
							" 	where a.int_dist_id=b.int_dissleri_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt "+
							"	 and a.dt_date between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
									" and a.int_dist_id=c.int_app_id_f and b.int_brand_id=d.brand_id and "+
							"	  b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk    "+
							"	  group by d.brand_name, e.quantity, "+
							"	   a.int_dist_id, a.vch_gatepass_no, "+
							"	   a.dt_date, a.licencee_district, b.vch_gatepass_no, "+
							"	   a.licensee_name, b.dt, c.vch_undertaking_name, "+
							"	  a.licensee_adrs, c.int_app_id_f, "+
							"	   a.vch_to, a.vch_from ,distllery_district_Name order by a.dt_date ,b.dt, description ";


			}
			else{
				
				reportQuery="	select distinct a.int_dist_id as vch_app_id_f, a.vch_gatepass_no,  a.licensee_name, a.dt_date, a.licencee_district,a.vch_to, "+
							"b.vch_gatepass_no, sum(b.dispatch_box),"+
								  
								"  b.dt,  c.int_app_id_f, c.vch_undertaking_name, "+
								"d.brand_name, e.quantity, "+
				  
							" (select d.description from public.district d where a.licencee_district=d.districtid )  as description, a.vch_from , "+
							" (select h.description from  public.district h where COALESCE(c.vch_unit_dist,0::text)=h.districtid::text ) as distllery_district_Name "+
						
							"  FROM distillery.gatepass_to_districtwholesale_imfl_old_stock a, distillery.fl2_stock_trxn_imfl_old_stock b, "+
							" 	 public.dis_mst_pd1_pd2_lic c, distillery.brand_registration d, distillery.packaging_details e "+
						
							" 	where a.int_dist_id=b.int_dissleri_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt "+
							"	 and a.dt_date between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
									" and a.int_dist_id=c.int_app_id_f and b.int_brand_id=d.brand_id and "+
							"	  b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk  and c.int_app_id_f='"+filter+"'  "+
							"	  group by d.brand_name, e.quantity, "+
							"	   a.int_dist_id, a.vch_gatepass_no, "+
							"	   a.dt_date, a.licencee_district, b.vch_gatepass_no, "+
							"	   a.licensee_name, b.dt, c.vch_undertaking_name, "+
							"	  a.licensee_adrs, c.int_app_id_f, "+
							"	   a.vch_to, a.vch_from ,distllery_district_Name order by a.dt_date ,b.dt, description ";

				
				
				
			}
			
			

		/*	reportQuery = "select distinct a.int_dist_id as vch_app_id_f, a.vch_gatepass_no,  a.licensee_name, a.dt_date, a.licencee_district,a.vch_to, "+
						  " b.vch_gatepass_no, sum(b.dispatch_box),"+
						  " -- sum(b.cal_duty) as duty, "+
						  " b.dt, "+
						  " c.int_app_id_f, c.vch_undertaking_name, d.brand_name, e.quantity, "+
						  
		  
						  "(select d.description from public.district d where a.licencee_district=d.districtid )  as description, a.vch_from , "+
						  " (select h.description from  public.district h where COALESCE(c.vch_unit_dist,0::text)=h.districtid::text ) as distllery_district_Name "+
				
						  " FROM distillery.gatepass_to_districtwholesale_imfl_old_stock a, distillery.fl2_stock_trxn_imfl_old_stock b, "+
						  " public.dis_mst_pd1_pd2_lic c, distillery.brand_registration d, distillery.packaging_details e "+
				
						  " where a.int_dist_id=b.int_dissleri_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt "+
						  " and a.dt_date between '"+action.getFromdate()+"' and '"+action.getTodate()+"' and a.int_dist_id=c.int_app_id_f and b.int_brand_id=d.brand_id and "+
						  " b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk  and c.int_app_id_f= '"+action.getBwfl_FL2d_Id()+"'  "+
						  " group by d.brand_name, e.quantity, "+
						  " a.int_dist_id, a.vch_gatepass_no, -- a.db_total_duty, "+
						  " a.dt_date, a.licencee_district, b.vch_gatepass_no, "+
						  " a.licensee_name, b.dt, c.vch_undertaking_name, "+
						  " a.licensee_adrs, c.int_app_id_f, "+
						  " a.vch_to, a.vch_from ,distllery_district_Name order by a.dt_date ,b.dt, description ";*/
		

			pst = con.prepareStatement(reportQuery);
			/*if(action.getBwfl_FL2d_Id().equalsIgnoreCase("99999"))
			{
				
			}
			else{
				pst.setInt(1, Integer.parseInt(action.getBwfl_FL2d_Id())); 
			}
			*/
			
			System.out.println("====33333===="+reportQuery);
			
			rs = pst.executeQuery();
			if (rs.next()) {
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator + "ReportOfDispatches_DistilleryOldStock.jasper");
				JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print, relativePathpdf + File.separator + "ReportOfDispatches_DistilleryOldStock" + n + ".pdf");
				action.setPdfname("ReportOfDispatches_DistilleryOldStock" + n + ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found"));
				action.setPrintFlag(false);
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)rs.close();
				if (con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void printDDuty(DispatcherReportAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String filter=action.getBwfl_FL2d_Id();
		
		
		if(action.getBwfl_FL2d_Id().equalsIgnoreCase("99999"))
		{
			reportQuery = "select distinct a.int_dist_id as vch_app_id_f, a.vch_gatepass_no,   a.dt_date, a.vch_to, "+
					  " b.vch_gatepass_no, sum(b.dispatchd_box), sum(b.duty) as duty,  "+
					  " c.int_app_id_f, c.vch_undertaking_name, d.brand_name, e.quantity, a.vch_from , "+
					  " (select h.description from  public.district h where COALESCE(c.vch_unit_dist,0::text)=h.districtid::text ) as distllery_district_Name "+
			
	 				" FROM distillery.gatepass_to_manufacturewholesale_imfl_old_stock a, distillery.fl1_stock_trxn_imfl_old_stock b, "+
	 				" public.dis_mst_pd1_pd2_lic c, distillery.brand_registration d, distillery.packaging_details e "+
			
					" where a.int_dist_id=b.int_dissleri_id and a.vch_gatepass_no=b.vch_gatepass_no and "+
		
			 		" a.dt_date between '"+action.getFromdate()+"' and '"+action.getTodate()+"'  and a.int_dist_id=c.int_app_id_f and b.int_brand_id=d.brand_id and "+
			 		" b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk   "+
			 		" group by d.brand_name, e.quantity, "+
			 		" a.int_dist_id, a.vch_gatepass_no, a.db_total_duty, "+
					" a.dt_date,  b.vch_gatepass_no, "+
					" c.vch_undertaking_name, "+
					" c.int_app_id_f, "+
					 "  a.vch_to, a.vch_from ,distllery_district_Name order by a.dt_date  ";
		}
		else{
			reportQuery = "select distinct a.int_dist_id as vch_app_id_f, a.vch_gatepass_no,   a.dt_date, a.vch_to, "+
					  " b.vch_gatepass_no, sum(b.dispatchd_box), sum(b.duty) as duty,  "+
					  " c.int_app_id_f, c.vch_undertaking_name, d.brand_name, e.quantity, a.vch_from , "+
					  " (select h.description from  public.district h where COALESCE(c.vch_unit_dist,0::text)=h.districtid::text ) as distllery_district_Name "+
			
	 				" FROM distillery.gatepass_to_manufacturewholesale_imfl_old_stock a, distillery.fl1_stock_trxn_imfl_old_stock b, "+
	 				" public.dis_mst_pd1_pd2_lic c, distillery.brand_registration d, distillery.packaging_details e "+
			
					" where a.int_dist_id=b.int_dissleri_id and a.vch_gatepass_no=b.vch_gatepass_no and "+
		
			 		" a.dt_date between '"+action.getFromdate()+"' and '"+action.getTodate()+"'  and a.int_dist_id=c.int_app_id_f and b.int_brand_id=d.brand_id and "+
			 		" b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk  and c.int_app_id_f='"+filter+"'  "+
			 		" group by d.brand_name, e.quantity, "+
			 		" a.int_dist_id, a.vch_gatepass_no, a.db_total_duty, "+
					" a.dt_date,  b.vch_gatepass_no, "+
					" c.vch_undertaking_name, "+
					" c.int_app_id_f, "+
					 "  a.vch_to, a.vch_from ,distllery_district_Name order by a.dt_date  ";
			
		}
		
		try {
			con = ConnectionToDataBase.getConnection();
			
			


			pst = con.prepareStatement(reportQuery);
			
			
			
			System.out.println("====33333===="+reportQuery);
			
			rs = pst.executeQuery();
			if (rs.next()) {
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator + "ReportOfDispatches_DistryDuty.jasper");
				JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print, relativePathpdf + File.separator + "ReportOfDispatches_DistryDuty" + n + ".pdf");
				action.setPdfname("ReportOfDispatches_DistryDuty" + n + ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found"));
				action.setPrintFlag(false);
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)rs.close();
				if (con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void printB(DispatcherReportAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String filter=action.getBwfl_FL2d_Id();
		if(action.getBwfl_FL2d_Id().equalsIgnoreCase("99999"))
		{
			reportQuery =	" select distinct a.brewery_id as vch_app_id_f, a.vch_gatepass_no,  a.licensee_name, a.dt_date, a.licencee_district,a.vch_to, "+
					" b.vch_gatepass_no, sum(b.dispatch_box),  c.vch_app_id_f, c.brewery_nm,  d.brand_name, e.quantity, "+
	
				"	(select d.description from public.district d where a.licencee_district=d.districtid )  as description, a.vch_from,  "+
				 " (select h.description from  public.district h where COALESCE(c.vch_dist_id,0::text)=h.districtid::text ) as bre_district_Name "+
			
				"  FROM  bwfl.gatepass_to_districtwholesale_old_stock_17_18 a, bwfl.fl2_stock_trxn_old_stock_17_18 b, "+
				"  public.bre_mst_b1_lic c, distillery.brand_registration d, distillery.packaging_details e "+
			
				"  where a.brewery_id=b.brewery_id and a.vch_gatepass_no=b.vch_gatepass_no and "+
				
				"	a.dt_date  between   '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
						" and a.brewery_id=c.vch_app_id_f and b.int_brand_id=d.brand_id and "+
				"	b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk    "+
				"	group by d.brand_name, e.quantity, "+
				"	a.brewery_id, a.vch_gatepass_no, "+
				"  a.dt_date, a.licencee_district, b.vch_gatepass_no, "+
				"	 a.licensee_name, b.dt, c.brewery_nm, "+
				"	 a.licensee_adrs, c.vch_app_id_f, "+
				"	 a.vch_to, a.vch_from,bre_district_Name  order by a.dt_date , description ";

		}
		else{
			reportQuery =	" select distinct a.brewery_id as vch_app_id_f, a.vch_gatepass_no,  a.licensee_name, a.dt_date, a.licencee_district,a.vch_to, "+
					" b.vch_gatepass_no, sum(b.dispatch_box),  c.vch_app_id_f, c.brewery_nm,  d.brand_name, e.quantity, "+
	
				"	(select d.description from public.district d where a.licencee_district=d.districtid )  as description, a.vch_from , "+
				 " (select h.description from  public.district h where COALESCE(c.vch_dist_id,0::text)=h.districtid::text ) as bre_district_Name "+
			
				"  FROM  bwfl.gatepass_to_districtwholesale_old_stock_17_18 a, bwfl.fl2_stock_trxn_old_stock_17_18 b, "+
				"  public.bre_mst_b1_lic c, distillery.brand_registration d, distillery.packaging_details e "+
			
				"  where a.brewery_id=b.brewery_id and a.vch_gatepass_no=b.vch_gatepass_no and "+
				
				"	a.dt_date  between  '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
				" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
				" and a.brewery_id=c.vch_app_id_f and b.int_brand_id=d.brand_id and "+
				"	b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk and c.vch_app_id_f='"+filter+"'   "+
				"	group by d.brand_name, e.quantity, "+
				"	a.brewery_id, a.vch_gatepass_no, "+
				"  a.dt_date, a.licencee_district, b.vch_gatepass_no, "+
				"	 a.licensee_name, b.dt, c.brewery_nm, "+
				"	 a.licensee_adrs, c.vch_app_id_f, "+
				"	 a.vch_to, a.vch_from ,bre_district_Name order by a.dt_date , description ";

			
			
			
		}
		
		try {
			con = ConnectionToDataBase.getConnection();	

			System.out.println("====4444===="+reportQuery);
			pst = con.prepareStatement(reportQuery);
		
		
			
			
			rs = pst.executeQuery();
			if (rs.next()) {
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator + "ReportOfDispatches_Brewary.jasper");
				JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print, relativePathpdf + File.separator + "ReportOfDispatches_Brewary" + n + ".pdf");
				action.setPdfname("ReportOfDispatches_Brewary" + n + ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found"));
				action.setPrintFlag(false);
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)rs.close();
				if (con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void printBDuty(DispatcherReportAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp" + File.separator + "WholeSale" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String filter=action.getBwfl_FL2d_Id();
		
		if(action.getBwfl_FL2d_Id().equalsIgnoreCase("99999"))
		{

			reportQuery =	"select distinct a.bre_id as app_id_f, a.vch_gatepass_no,  a.dt_date,a.vch_to, "+
							" b.vch_gatepass_no, sum(b.dispatchd_box),"+
							" sum(b.duty) as duty,"+
							" sum(a.db_total_duty) as addduty, "+
							" c.vch_app_id_f, c.brewery_nm,"+
							"	d.brand_name, e.quantity, a.vch_from , "+
							" (select h.description from  public.district h where COALESCE(c.vch_dist_id,0::text)=h.districtid::text) as bre_district_Name "+
	
							" FROM bwfl.gatepass_to_districtwholesale_2017_18_old_stock a, bwfl.fl1_stock_trxn_old_stock_17_18 b, "+
							" public.bre_mst_b1_lic c, distillery.brand_registration d, distillery.packaging_details e "+
	
							" where a.bre_id=b.int_brewery_id and a.vch_gatepass_no=b.vch_gatepass_no and "+
	
							" a.dt_date  between   '"+action.getFromdate()+"' and '"+action.getTodate()+"' and a.bre_id=c.vch_app_id_f and b.int_brand_id=d.brand_id and "+
							" b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk  "+
							" group by d.brand_name, e.quantity, "+
							" a.bre_id, a.vch_gatepass_no, a.db_total_duty,"+
							" a.dt_date,  b.vch_gatepass_no, "+
							" c.brewery_nm, "+
							"	c.vch_app_id_f, "+
							" a.vch_to, a.vch_from ,bre_district_Name order by a.dt_date ";
		
		
		}
		else{

			reportQuery =	"select distinct a.bre_id as app_id_f, a.vch_gatepass_no,  a.dt_date,a.vch_to, "+
							" b.vch_gatepass_no, sum(b.dispatchd_box),"+
							" sum(b.duty) as duty,"+
							" sum(a.db_total_duty) as addduty, "+
							" c.vch_app_id_f, c.brewery_nm,"+
							"	d.brand_name, e.quantity, a.vch_from , "+
							" (select h.description from  public.district h where COALESCE(c.vch_dist_id,0::text)=h.districtid::text) as bre_district_Name "+
	
							" FROM bwfl.gatepass_to_districtwholesale_2017_18_old_stock a, bwfl.fl1_stock_trxn_old_stock_17_18 b, "+
							" public.bre_mst_b1_lic c, distillery.brand_registration d, distillery.packaging_details e "+
	
							" where a.bre_id=b.int_brewery_id and a.vch_gatepass_no=b.vch_gatepass_no and "+
	
							" a.dt_date  between   '"+action.getFromdate()+"' and '"+action.getTodate()+"' and a.bre_id=c.vch_app_id_f and b.int_brand_id=d.brand_id and "+
							" b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk  and c.vch_app_id_f='"+filter+"' "+
							" group by d.brand_name, e.quantity, "+
							" a.bre_id, a.vch_gatepass_no, a.db_total_duty,"+
							" a.dt_date,  b.vch_gatepass_no, "+
							" c.brewery_nm, "+
							"	c.vch_app_id_f, "+
							" a.vch_to, a.vch_from ,bre_district_Name order by a.dt_date ";
		
		
			
	
		}
		
		
		
		try {
			con = ConnectionToDataBase.getConnection();
			
		



			System.out.println("====4444===="+reportQuery);
			pst = con.prepareStatement(reportQuery);
			
		
			
			
			rs = pst.executeQuery();
			if (rs.next()) {
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator + "ReportOfDispatches_BreDuty.jasper");
				JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print, relativePathpdf + File.separator + "ReportOfDispatches_BreDuty" + n + ".pdf");
				action.setPdfname("ReportOfDispatches_BreDuty" + n + ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No Data Found", "No Data Found"));
				action.setPrintFlag(false);
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)rs.close();
				if (con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// /------------------------------------- EXCEL report Dispatches BWFL ----
	// -----

	
	//----------------------------------------------------
	
	

	public boolean writeDispatchesExcelForFL2D(DispatcherReportAction action) {
		System.out.println("hiii");
		String relativePath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		Connection con = null;
		String type = "";
		long tot = 0;
		double totalduty = 0.0;
		
		String sql = "";
		String filter=action.getBwfl_FL2d_Id();
		type = "FL2D";
		
		if(action.getBwfl_FL2d_Id().equalsIgnoreCase("99999"))
		{

			sql = " select distinct d.brand_name, e.quantity, a.int_fl2d_id as vch_app_id_f, a.vch_gatepass_no, " +
						  " a.licensee_name, a.dt_date, a.licencee_district, b.vch_gatepass_no, sum(b.dispatch_box) as cases , " +
						  " sum(b.cal_duty) as duty, b.dt, c.int_app_id, c.vch_firm_name, c.vch_applicant_name, a.vch_to, " +
						  " (select d.description from public.district d where a.licencee_district=d.districtid ) " +
						  " as description, a.vch_from  " +
						  //" (select h.description from  public.district h where COALESCE(c.core_district_id,0)=h.districtid ) as fl2d_district_Name " +
						  " FROM fl2d.gatepass_to_districtwholesale_fl2d_oldstock a, fl2d.fl2d_stock_trxn_oldstock b, " +
						  " licence.fl2_2b_2d c, distillery.brand_registration d, distillery.packaging_details e " +
						  " where a.int_fl2d_id=b.int_fl2d_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt " +
						  " and a.dt_date between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
						  		" and a.int_fl2d_id=c.int_app_id and b.int_brand_id=d.brand_id and " +
						  " b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk     group by d.brand_name, e.quantity, " +
						  " a.int_fl2d_id, a.vch_gatepass_no, a.tot_cal_duty, a.dt_date, a.licencee_district, b.vch_gatepass_no, " +
						  " a.licensee_name, b.dt, c.vch_firm_name, c.vch_applicant_name, a.licensee_adrs, c.int_app_id, " +
						  " a.vch_to, a.vch_from  order by b.dt, description ";
		}
		else{
		
		

			sql = " select distinct d.brand_name, e.quantity, a.int_fl2d_id as vch_app_id_f, a.vch_gatepass_no, " +
					  " a.licensee_name, a.dt_date, a.licencee_district, b.vch_gatepass_no, sum(b.dispatch_box)  as cases , " +
					  " sum(b.cal_duty) as duty, b.dt, c.int_app_id, c.vch_firm_name, c.vch_applicant_name, a.vch_to, " +
					  " (select d.description from public.district d where a.licencee_district=d.districtid ) " +
					  " as description, a.vch_from  " +
					  //" (select h.description from  public.district h where COALESCE(c.core_district_id,0)=h.districtid ) as fl2d_district_Name " +
					  " FROM fl2d.gatepass_to_districtwholesale_fl2d_oldstock a, fl2d.fl2d_stock_trxn_oldstock b, " +
					  " licence.fl2_2b_2d c, distillery.brand_registration d, distillery.packaging_details e " +
					  " where a.int_fl2d_id=b.int_fl2d_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt " +
					  " and a.dt_date between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
					  		" and a.int_fl2d_id=c.int_app_id and b.int_brand_id=d.brand_id and " +
					  " b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk  and c.int_app_id='"+filter+"'   group by d.brand_name, e.quantity, " +
					  " a.int_fl2d_id, a.vch_gatepass_no, a.tot_cal_duty, a.dt_date, a.licencee_district, b.vch_gatepass_no, " +
					  " a.licensee_name, b.dt, c.vch_firm_name, c.vch_applicant_name, a.licensee_adrs, c.int_app_id, " +
					  " a.vch_to, a.vch_from  order by b.dt, description ";
		}
		System.out.println(sql);
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(sql);
		
			
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 8000);
			worksheet.setColumnWidth(2, 4000);
			worksheet.setColumnWidth(3, 20000);
			worksheet.setColumnWidth(4, 9999);
			
			worksheet.setColumnWidth(5, 20000);
			worksheet.setColumnWidth(6, 9000);
			
			worksheet.setColumnWidth(7, 15000);
			worksheet.setColumnWidth(8, 10000);
			worksheet.setColumnWidth(9, 9000);
			worksheet.setColumnWidth(10, 9000);
		
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" Dispatch Of " + type);
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
			cellhead2.setCellValue("Gate Pass No.");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Date");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("FL2D Name");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("From License");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Brand Name");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Quantity (ML)");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue(" Dispatch Type");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("Dispatch to");
			cellhead9.setCellStyle(cellStyle);

			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("District");
			cellhead10.setCellStyle(cellStyle);

			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("Number Of Boxes");
			cellhead11.setCellStyle(cellStyle);
			
			
			
		

			while (rs.next()) {
				tot = tot + rs.getLong("cases");
			
				Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt_date"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);

				k++;
				
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("vch_gatepass_no"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(date);

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("vch_firm_name"));
				
				XSSFCell cellJ1 = row1.createCell((int) 4);
				cellJ1.setCellValue(rs.getString("vch_from"));	
				
				
				XSSFCell cellK1 = row1.createCell((int) 5);
				cellK1.setCellValue(rs.getString("brand_name"));
				
				XSSFCell cellE1 = row1.createCell((int) 6);
				cellE1.setCellValue(rs.getString("quantity"));

				XSSFCell cellF1 = row1.createCell((int) 7);
				cellF1.setCellValue(rs.getString("vch_to"));

				XSSFCell cellG1 = row1.createCell((int) 8);
				cellG1.setCellValue(rs.getString("licensee_name"));

				XSSFCell cellH1 = row1.createCell((int) 9);
				cellH1.setCellValue(rs.getString("description"));

				XSSFCell cellI1 = row1.createCell((int) 10);
				cellI1.setCellValue(rs.getString("cases"));
				
				

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+"//ExciseUp//WholeSale//Excel//"+action.getRadioCLandFL()+n+"Dispatch_BWFL.xls");
			action.setExlname(action.getRadioCLandFL() + n);
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			
			XSSFCell cellA1 = row1.createCell((int) 0);			
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);
			
			XSSFCell cellA2 = row1.createCell((int) 1);			
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);
			
			XSSFCell cellA3 = row1.createCell((int) 2);			
			cellA3.setCellValue(" ");
			cellA3.setCellStyle(cellStyle);
			
			XSSFCell cellA4 = row1.createCell((int) 3);			
			cellA4.setCellValue(" ");
			cellA4.setCellStyle(cellStyle);
			
			XSSFCell cellA5 = row1.createCell((int) 4);			
			cellA5.setCellValue(" ");
			cellA5.setCellStyle(cellStyle);
			
			XSSFCell cellA6 = row1.createCell((int) 5);	
			cellA6.setCellValue(" ");
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 6);			
			cellA7.setCellValue(" ");
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 7);			
			cellA8.setCellValue(" ");
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 8);			
			cellA9.setCellValue(" ");
			cellA9.setCellStyle(cellStyle);
			
			XSSFCell cellA10 = row1.createCell((int) 9);			
			cellA10.setCellValue("Total ");
			cellA10.setCellStyle(cellStyle);
			
			XSSFCell cellA11 = row1.createCell((int) 10);			
			cellA11.setCellValue(tot);
			cellA11.setCellStyle(cellStyle);
			
			
		

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			action.setExcelFlag(true);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
		try
		{	
		
		if(con!=null)con.close();
		if(pstmt!=null)pstmt.close();
		if(rs!=null)rs.close();
		
		
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		}
		return flag;
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// /------------------------------------- EXCEL report Dispatches FL2D ----
	// -----

	public boolean writeDispatchesExcelBWFL(DispatcherReportAction action) {
		System.out.println("hiii");
		String relativePath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		Connection con = null;
		String type = "";
		long tot = 0;
		double totalduty = 0.0;
		
		String sql = "";
		String filter=action.getBwfl_FL2d_Id();
		type = "BWFL";
		
		if(action.getBwfl_FL2d_Id().equalsIgnoreCase("99999"))
		{
			sql = "	select 	distinct d.brand_name, e.quantity, 	a.int_bwfl_id as vch_app_id_f, a.vch_gatepass_no, " +
				  " a.licensee_name, a.dt_date,a.vch_from,  "+
				  " b.vch_gatepass_no, sum(b.dispatch_box) as cases,  "+
				  "  b.dt, c.int_id, c.vch_firm_name, c.vch_applicant_name, c.vch_firm_district_name , "+
				  " case when a.vch_to='DW'  then  'District Warehouse' else 'Bar/Restaurant/Club' end as vch_to, "+
				  " (select d.description from public.district d where a.licence_district=d.districtid ) as description, a.vch_from "+
				 	//" (select h.description from  public.district h where COALESCE(a.licence_district,0)=h.districtid ) as  bwfl_district_Name "+
					" FROM bwfl_license.gatepass_to_districtwholesale_2017_18_bwfl_old_stock a, bwfl_license.fl2_stock_trxn_bwfl_old_stock b, "+
					" bwfl.registration_of_bwfl_lic_holder c, distillery.brand_registration d, distillery.packaging_details e "+
					" where a.int_bwfl_id=b.int_bwfl_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt and  "+
					" a.dt_date between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
					" and a.int_bwfl_id=c.int_id and b.int_brand_id=d.brand_id and "+
				   "  b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk     "+
	             "  group by e.quantity, d.brand_name, "+
				  " a.int_bwfl_id, a.vch_gatepass_no, a.dt_date ,a.licence_district, b.vch_gatepass_no, a.licensee_name,   "+
				  "  b.dt, c.vch_firm_name, a.licensee_adrs, c.int_id, a.vch_to, a.vch_from order by b.dt, description ";
		}
		else
		{
			sql = "	select 	distinct d.brand_name, e.quantity, 	a.int_bwfl_id as vch_app_id_f, a.vch_gatepass_no, " +
					 " a.licensee_name, a.dt_date,a.vch_from,  "+
					  " b.vch_gatepass_no, sum(b.dispatch_box) as cases, "+
					  "  b.dt, c.int_id, c.vch_firm_name, c.vch_applicant_name, c.vch_firm_district_name , "+
				     " case when a.vch_to='DW'  then  'District Warehouse' else 'Bar/Restaurant/Club' end as vch_to, "+
				     " (select d.description from public.district d where a.licence_district=d.districtid ) as description, a.vch_from "+
				 	//" (select h.description from  public.district h where COALESCE(a.licence_district,0)=h.districtid ) as  bwfl_district_Name "+
					" FROM bwfl_license.gatepass_to_districtwholesale_2017_18_bwfl_old_stock a, bwfl_license.fl2_stock_trxn_bwfl_old_stock b, "+
					" bwfl.registration_of_bwfl_lic_holder c, distillery.brand_registration d, distillery.packaging_details e "+
					" where a.int_bwfl_id=b.int_bwfl_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt and  "+
					" a.dt_date between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
							" and a.int_bwfl_id=c.int_id and b.int_brand_id=d.brand_id and "+
				   "  b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk and c.int_id='"+filter+"'   "+
	             "  group by e.quantity, d.brand_name, "+
				  " a.int_bwfl_id, a.vch_gatepass_no, a.dt_date ,a.licence_district, b.vch_gatepass_no, a.licensee_name,   "+
				  "  b.dt, c.vch_firm_name, a.licensee_adrs, c.int_id, a.vch_to, a.vch_from order by b.dt, description ";
		}
		System.out.println(sql);
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(sql);
		
			
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 8000);
			worksheet.setColumnWidth(2, 4000);
			worksheet.setColumnWidth(3, 20000);
			worksheet.setColumnWidth(4, 9999);
			
			worksheet.setColumnWidth(5, 20000);
			worksheet.setColumnWidth(6, 9000);
			
			worksheet.setColumnWidth(7, 15000);
			worksheet.setColumnWidth(8, 10000);
			worksheet.setColumnWidth(9, 9000);
			worksheet.setColumnWidth(10, 9000);
		
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" Dispatch Of " + type);
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
			cellhead2.setCellValue("Gate Pass No.");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Date");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("BWFL Name");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("From License");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Brand Name");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Quantity (ML)");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue(" Dispatch Type");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("Dispatch to");
			cellhead9.setCellStyle(cellStyle);

			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("District");
			cellhead10.setCellStyle(cellStyle);

			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("Number Of Boxes");
			cellhead11.setCellStyle(cellStyle);
			
			
			
		

			while (rs.next()) {
				tot = tot + rs.getLong("cases");
			
				Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt_date"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);

				k++;
				
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("vch_gatepass_no"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(date);

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("vch_firm_name"));
				
				XSSFCell cellJ1 = row1.createCell((int) 4);
				cellJ1.setCellValue(rs.getString("vch_from"));
				
				XSSFCell cellK1 = row1.createCell((int) 5);
				cellK1.setCellValue(rs.getString("brand_name"));
				
				XSSFCell cellE1 = row1.createCell((int) 6);
				cellE1.setCellValue(rs.getString("quantity"));

				XSSFCell cellF1 = row1.createCell((int) 7);
				cellF1.setCellValue(rs.getString("vch_to"));

				XSSFCell cellG1 = row1.createCell((int) 8);
				cellG1.setCellValue(rs.getString("licensee_name"));

				XSSFCell cellH1 = row1.createCell((int) 9);
				cellH1.setCellValue(rs.getString("description"));

				XSSFCell cellI1 = row1.createCell((int) 10);
				cellI1.setCellValue(rs.getString("cases"));
				
				

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+"//ExciseUp//WholeSale//Excel//"+action.getRadioCLandFL()+n+"Dispatch_BWFL.xls");
			action.setExlname(action.getRadioCLandFL() + n);
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			
			XSSFCell cellA1 = row1.createCell((int) 0);			
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);
			
			XSSFCell cellA2 = row1.createCell((int) 1);			
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);
			
			XSSFCell cellA3 = row1.createCell((int) 2);			
			cellA3.setCellValue(" ");
			cellA3.setCellStyle(cellStyle);
			
			XSSFCell cellA4 = row1.createCell((int) 3);			
			cellA4.setCellValue(" ");
			cellA4.setCellStyle(cellStyle);
			
			XSSFCell cellA5 = row1.createCell((int) 4);			
			cellA5.setCellValue(" ");
			cellA5.setCellStyle(cellStyle);
			
			XSSFCell cellA6 = row1.createCell((int) 5);	
			cellA6.setCellValue(" ");
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 6);			
			cellA7.setCellValue(" ");
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 7);			
			cellA8.setCellValue(" ");
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 8);			
			cellA9.setCellValue(" ");
			cellA9.setCellStyle(cellStyle);
			
			XSSFCell cellA10 = row1.createCell((int) 9);			
			cellA10.setCellValue("Total ");
			cellA10.setCellStyle(cellStyle);
			
			XSSFCell cellA11 = row1.createCell((int) 10);			
			cellA11.setCellValue(tot);
			cellA11.setCellStyle(cellStyle);
			
			
		

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			action.setExcelFlag(true);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
		try
		{	
		
		if(con!=null)con.close();
		if(pstmt!=null)pstmt.close();
		if(rs!=null)rs.close();
		
		
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		}
		return flag;
	
	}
	
	public boolean writeDispatchesExcelDistillery(DispatcherReportAction action) {
	
		String relativePath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		Connection con = null;
		String type = "";
		long tot = 0;
	
		
		String sql = "";
		String filter=action.getBwfl_FL2d_Id();
		type = "Distillery";
		
		if(action.getBwfl_FL2d_Id().equalsIgnoreCase("99999"))
		{
			
			sql="	select distinct a.int_dist_id as vch_app_id_f, a.vch_gatepass_no,  a.licensee_name, a.dt_date, a.licencee_district,a.vch_to, "+
						"b.vch_gatepass_no, sum(b.dispatch_box) as cases,"+
							  
							"  b.dt,  c.int_app_id_f, c.vch_undertaking_name, "+
							"d.brand_name, e.quantity, "+
			  
						" (select d.description from public.district d where a.licencee_district=d.districtid )  as description, a.vch_from  "+
						//" (select h.description from  public.district h where COALESCE(c.vch_unit_dist,0::text)=h.districtid::text ) as distllery_district_Name "+
					
						"  FROM distillery.gatepass_to_districtwholesale_imfl_old_stock a, distillery.fl2_stock_trxn_imfl_old_stock b, "+
						" 	 public.dis_mst_pd1_pd2_lic c, distillery.brand_registration d, distillery.packaging_details e "+
					
						" 	where a.int_dist_id=b.int_dissleri_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt "+
						"	 and a.dt_date between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
								" and a.int_dist_id=c.int_app_id_f and b.int_brand_id=d.brand_id and "+
						"	  b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk    "+
						"	  group by d.brand_name, e.quantity, "+
						"	   a.int_dist_id, a.vch_gatepass_no, "+
						"	   a.dt_date, a.licencee_district, b.vch_gatepass_no, "+
						"	   a.licensee_name, b.dt, c.vch_undertaking_name, "+
						"	  a.licensee_adrs, c.int_app_id_f, "+
						"	   a.vch_to, a.vch_from  order by a.dt_date ,b.dt, description ";


		}
		else{
			
			sql="	select distinct a.int_dist_id as vch_app_id_f, a.vch_gatepass_no,  a.licensee_name, a.dt_date, a.licencee_district,a.vch_to, "+
						"b.vch_gatepass_no, sum(b.dispatch_box) as cases,"+
							  
							"  b.dt,  c.int_app_id_f, c.vch_undertaking_name, "+
							"d.brand_name, e.quantity, "+
			  
						" (select d.description from public.district d where a.licencee_district=d.districtid )  as description, a.vch_from  "+
						//" (select h.description from  public.district h where COALESCE(c.vch_unit_dist,0::text)=h.districtid::text ) as distllery_district_Name "+
					
						"  FROM distillery.gatepass_to_districtwholesale_imfl_old_stock a, distillery.fl2_stock_trxn_imfl_old_stock b, "+
						" 	 public.dis_mst_pd1_pd2_lic c, distillery.brand_registration d, distillery.packaging_details e "+
					
						" 	where a.int_dist_id=b.int_dissleri_id and a.vch_gatepass_no=b.vch_gatepass_no and a.dt_date=b.dt "+
						"	 and a.dt_date between '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
								" and a.int_dist_id=c.int_app_id_f and b.int_brand_id=d.brand_id and "+
						"	  b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk  and c.int_app_id_f='"+filter+"'  "+
						"	  group by d.brand_name, e.quantity, "+
						"	   a.int_dist_id, a.vch_gatepass_no, "+
						"	   a.dt_date, a.licencee_district, b.vch_gatepass_no, "+
						"	   a.licensee_name, b.dt, c.vch_undertaking_name, "+
						"	  a.licensee_adrs, c.int_app_id_f, "+
						"	   a.vch_to, a.vch_from  order by a.dt_date ,b.dt, description ";

			
			
			
		}
		
		
		System.out.println(sql);
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(sql);
		
			
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 8000);
			worksheet.setColumnWidth(2, 4000);
			worksheet.setColumnWidth(3, 20000);
			worksheet.setColumnWidth(4, 9999);
			
			worksheet.setColumnWidth(5, 20000);
			worksheet.setColumnWidth(6, 9000);
			
			worksheet.setColumnWidth(7, 15000);
			worksheet.setColumnWidth(8, 10000);
			worksheet.setColumnWidth(9, 9000);
			worksheet.setColumnWidth(10, 9000);
		
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" Dispatch Of " + type);
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
			cellhead2.setCellValue("Gate Pass No.");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Date");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Distillery Name");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("From License");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Brand Name");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Quantity (ML)");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue(" Dispatch Type");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("Dispatch to");
			cellhead9.setCellStyle(cellStyle);

			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("District");
			cellhead10.setCellStyle(cellStyle);

			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("Number Of Boxes");
			cellhead11.setCellStyle(cellStyle);
			
			
			
		

			while (rs.next()) {
				tot = tot + rs.getLong("cases");
			
				Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt_date"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);

				k++;
				
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("vch_gatepass_no"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(date);

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("vch_undertaking_name"));
				
				XSSFCell cellJ1 = row1.createCell((int) 4);
				cellJ1.setCellValue(rs.getString("vch_from"));
				
				XSSFCell cellK1 = row1.createCell((int) 5);
				cellK1.setCellValue(rs.getString("brand_name"));
				
				XSSFCell cellE1 = row1.createCell((int) 6);
				cellE1.setCellValue(rs.getString("quantity"));

				XSSFCell cellF1 = row1.createCell((int) 7);
				cellF1.setCellValue(rs.getString("vch_to"));

				XSSFCell cellG1 = row1.createCell((int) 8);
				cellG1.setCellValue(rs.getString("licensee_name"));

				XSSFCell cellH1 = row1.createCell((int) 9);
				cellH1.setCellValue(rs.getString("description"));

				XSSFCell cellI1 = row1.createCell((int) 10);
				cellI1.setCellValue(rs.getString("cases"));
				
				

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+"//ExciseUp//WholeSale//Excel//"+action.getRadioCLandFL()+n+"Dispatch_BWFL.xls");
			action.setExlname(action.getRadioCLandFL() + n);
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			
			XSSFCell cellA1 = row1.createCell((int) 0);			
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);
			
			XSSFCell cellA2 = row1.createCell((int) 1);			
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);
			
			XSSFCell cellA3 = row1.createCell((int) 2);			
			cellA3.setCellValue(" ");
			cellA3.setCellStyle(cellStyle);
			
			XSSFCell cellA4 = row1.createCell((int) 3);			
			cellA4.setCellValue(" ");
			cellA4.setCellStyle(cellStyle);
			
			XSSFCell cellA5 = row1.createCell((int) 4);			
			cellA5.setCellValue(" ");
			cellA5.setCellStyle(cellStyle);
			
			XSSFCell cellA6 = row1.createCell((int) 5);	
			cellA6.setCellValue(" ");
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 6);			
			cellA7.setCellValue(" ");
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 7);			
			cellA8.setCellValue(" ");
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 8);			
			cellA9.setCellValue(" ");
			cellA9.setCellStyle(cellStyle);
			
			XSSFCell cellA10 = row1.createCell((int) 9);			
			cellA10.setCellValue("Total ");
			cellA10.setCellStyle(cellStyle);
			
			XSSFCell cellA11 = row1.createCell((int) 10);			
			cellA11.setCellValue(tot);
			cellA11.setCellStyle(cellStyle);
			
			
		

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			action.setExcelFlag(true);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
		try
		{	
		
		if(con!=null)con.close();
		if(pstmt!=null)pstmt.close();
		if(rs!=null)rs.close();
		
		
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		}
		return flag;
	
	}
	public boolean writeDispatchesExcelBrewary(DispatcherReportAction action) {
		
		String relativePath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		Connection con = null;
		String type = "";
		long tot = 0;
	
		
		String sql = "";
		String filter=action.getBwfl_FL2d_Id();
		type = "Brewary";
		
		if(action.getBwfl_FL2d_Id().equalsIgnoreCase("99999")) 
		{
			sql =	" select distinct a.brewery_id as vch_app_id_f, a.vch_gatepass_no,  a.licensee_name, a.dt_date, a.licencee_district,a.vch_to, "+
					" b.vch_gatepass_no, sum(b.dispatch_box) as cases,  c.vch_app_id_f, c.brewery_nm,  d.brand_name, e.quantity, "+
	
				"	(select d.description from public.district d where a.licencee_district=d.districtid )  as description, a.vch_from  "+
				 //" (select h.description from  public.district h where COALESCE(c.vch_dist_id,0::text)=h.districtid::text ) as bre_district_Name "+
			
				"  FROM  bwfl.gatepass_to_districtwholesale_old_stock_17_18 a, bwfl.fl2_stock_trxn_old_stock_17_18 b, "+
				"  public.bre_mst_b1_lic c, distillery.brand_registration d, distillery.packaging_details e "+
			
				"  where a.brewery_id=b.brewery_id and a.vch_gatepass_no=b.vch_gatepass_no and "+
				
				"	a.dt_date  between   '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
						" and a.brewery_id=c.vch_app_id_f and b.int_brand_id=d.brand_id and "+
				"	b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk    "+
				"	group by d.brand_name, e.quantity, "+
				"	a.brewery_id, a.vch_gatepass_no, "+
				"  a.dt_date, a.licencee_district, b.vch_gatepass_no, "+
				"	 a.licensee_name, b.dt, c.brewery_nm, "+
				"	 a.licensee_adrs, c.vch_app_id_f, "+
				"	 a.vch_to, a.vch_from  order by a.dt_date , description ";

		}
		else{
			sql =	" select distinct a.brewery_id as vch_app_id_f, a.vch_gatepass_no,  a.licensee_name, a.dt_date, a.licencee_district,a.vch_to, "+
					" b.vch_gatepass_no, sum(b.dispatch_box) as cases,  c.vch_app_id_f, c.brewery_nm,  d.brand_name, e.quantity, "+
	
				"	(select d.description from public.district d where a.licencee_district=d.districtid )  as description, a.vch_from  "+
				// " (select h.description from  public.district h where COALESCE(c.vch_dist_id,0::text)=h.districtid::text ) as bre_district_Name "+
			
				"  FROM  bwfl.gatepass_to_districtwholesale_old_stock_17_18 a, bwfl.fl2_stock_trxn_old_stock_17_18 b, "+
				"  public.bre_mst_b1_lic c, distillery.brand_registration d, distillery.packaging_details e "+
			
				"  where a.brewery_id=b.brewery_id and a.vch_gatepass_no=b.vch_gatepass_no and "+
				
				"	a.dt_date  between   '"+ Utility.convertUtilDateToSQLDate(action.getFromdate())+ "'  " +
						" AND  '"+ Utility.convertUtilDateToSQLDate(action.getTodate())+ "'  " +
						" and a.brewery_id=c.vch_app_id_f and b.int_brand_id=d.brand_id and "+
				"	b.int_pckg_id=e.package_id and b.int_brand_id=e.brand_id_fk and c.vch_app_id_f='"+filter+"'   "+
				"	group by d.brand_name, e.quantity, "+
				"	a.brewery_id, a.vch_gatepass_no, "+
				"  a.dt_date, a.licencee_district, b.vch_gatepass_no, "+
				"	 a.licensee_name, b.dt, c.brewery_nm, "+
				"	 a.licensee_adrs, c.vch_app_id_f, "+
				"	 a.vch_to, a.vch_from  order by a.dt_date , description ";

			
			
			
		}
		
		
		
		System.out.println(sql);
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(sql);
		
			
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 8000);
			worksheet.setColumnWidth(2, 4000);
			worksheet.setColumnWidth(3, 20000);
			worksheet.setColumnWidth(4, 9999);
			
			worksheet.setColumnWidth(5, 20000);
			worksheet.setColumnWidth(6, 9000);
			
			worksheet.setColumnWidth(7, 15000);
			worksheet.setColumnWidth(8, 10000);
			worksheet.setColumnWidth(9, 9000);
			worksheet.setColumnWidth(10, 9000);
		
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" Dispatch Of " + type);
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
			cellhead2.setCellValue("Gate Pass No.");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Date");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Brewary Name");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("From License");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Brand Name");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Quantity (ML)");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue(" Dispatch Type");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("Dispatch to");
			cellhead9.setCellStyle(cellStyle);

			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("District");
			cellhead10.setCellStyle(cellStyle);

			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("Number Of Boxes");
			cellhead11.setCellStyle(cellStyle);
			
			
			
		

			while (rs.next()) {
				tot = tot + rs.getLong("cases");
			
				Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt_date"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);

				k++;
				
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("vch_gatepass_no"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(date);

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("brewery_nm"));
				
				XSSFCell cellJ1 = row1.createCell((int) 4);
				cellJ1.setCellValue(rs.getString("vch_from"));
				
				XSSFCell cellK1 = row1.createCell((int) 5);
				cellK1.setCellValue(rs.getString("brand_name"));
				
				XSSFCell cellE1 = row1.createCell((int) 6);
				cellE1.setCellValue(rs.getString("quantity"));

				XSSFCell cellF1 = row1.createCell((int) 7);
				cellF1.setCellValue(rs.getString("vch_to"));

				XSSFCell cellG1 = row1.createCell((int) 8);
				cellG1.setCellValue(rs.getString("licensee_name"));

				XSSFCell cellH1 = row1.createCell((int) 9);
				cellH1.setCellValue(rs.getString("description"));

				XSSFCell cellI1 = row1.createCell((int) 10);
				cellI1.setCellValue(rs.getString("cases"));
				
				

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+"//ExciseUp//WholeSale//Excel//"+action.getRadioCLandFL()+n+"Dispatch_BWFL.xls");
			action.setExlname(action.getRadioCLandFL() + n);
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			
			XSSFCell cellA1 = row1.createCell((int) 0);			
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);
			
			XSSFCell cellA2 = row1.createCell((int) 1);			
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);
			
			XSSFCell cellA3 = row1.createCell((int) 2);			
			cellA3.setCellValue(" ");
			cellA3.setCellStyle(cellStyle);
			
			XSSFCell cellA4 = row1.createCell((int) 3);			
			cellA4.setCellValue(" ");
			cellA4.setCellStyle(cellStyle);
			
			XSSFCell cellA5 = row1.createCell((int) 4);			
			cellA5.setCellValue(" ");
			cellA5.setCellStyle(cellStyle);
			
			XSSFCell cellA6 = row1.createCell((int) 5);	
			cellA6.setCellValue(" ");
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 6);			
			cellA7.setCellValue(" ");
			cellA7.setCellStyle(cellStyle);
			
			XSSFCell cellA8 = row1.createCell((int) 7);			
			cellA8.setCellValue(" ");
			cellA8.setCellStyle(cellStyle);
			
			XSSFCell cellA9 = row1.createCell((int) 8);			
			cellA9.setCellValue(" ");
			cellA9.setCellStyle(cellStyle);
			
			XSSFCell cellA10 = row1.createCell((int) 9);			
			cellA10.setCellValue("Total ");
			cellA10.setCellStyle(cellStyle);
			
			XSSFCell cellA11 = row1.createCell((int) 10);			
			cellA11.setCellValue(tot);
			cellA11.setCellStyle(cellStyle);
			
			
		

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			action.setExcelFlag(true);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
		try
		{	
		
		if(con!=null)
			con.close();
		if(pstmt!=null)
			pstmt.close();
		if(rs!=null)
			rs.close();
		
		
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		}
		return flag;
	
	}

}
