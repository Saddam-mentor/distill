package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
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

import com.mentor.action.CL_MCQ_Shop_Cummulative_FilteredAction;
import com.mentor.action.LifitingComparisionFLAction; 
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;

public class LifitingComparisionFLImpl {
	
	

public void printReportShopWise(LifitingComparisionFLAction act) {
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
	String filterDistrict = "";
	 String filterSector="";
	 String filterShop="";


	try {
		con = ConnectionToDataBase.getConnection();
 
		
		if(!act.getDistid().equalsIgnoreCase("99") && !act.getDistid().equalsIgnoreCase("9999"))
		{
			filterDistrict=" and x.district_id="+ Integer.parseInt(act.getDistid())+ "";
			
			
			
			if(act.getSectorId().equalsIgnoreCase("0"))
			{
				filterSector ="";
				
				if(act.getShopId().equalsIgnoreCase("0"))
				{
					filterShop="";
				}
				else
				{
					filterShop=" and x.serial_no='"+act.getShopId()+"' ";
				}
			}
				
			else
			{
				filterSector = " and x.sector='"+act.getSectorId()+"' ";
				
				
				if(act.getShopId().equalsIgnoreCase("0"))
				{
					filterShop="";
				}
				else
				{
					filterShop=" and x.serial_no='"+act.getShopId()+"' ";
				}
				
				
			}
			
			
			
		}
		else
		{
			System.out.println("123");
			filterDistrict="";
			filterSector = "";
			filterShop="";
		}
		
		 
   
		 if (act.getSelectedQuarter()==0){
	reportQuery="" +
			" select x.serial_no,(x.vch_name_of_shop||' - '||x.vch_shop_type) as vch_name_of_shop,(SELECT sector_name FROM public.mst_sector_master where sector_id= x.sector) as sector_name, " +
			" (select description from public.district d where d.districtid=x.district_id) as description, " +
			//" y.new_box,y.new_bl,y.new_duty,y.old_box,y.old_bl,y.old_duty " +
			" COALESCE(y.new_box,0)new_box,COALESCE(y.new_bl,0)new_bl,COALESCE(y.new_duty,0)new_duty," +
			" round(CAST(float8(COALESCE(y.old_box,0)) as numeric), 0)as old_box, "+
			//"COALESCE(y.old_box,0)old_box," +
			"COALESCE(y.old_bl,0)old_bl,COALESCE(y.old_duty,0)old_duty " +
			" ,case when COALESCE(y.old_box,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_box,0)/y.old_box)*100)) as numeric), 2) end as per_box " +
			" ,case when COALESCE(y.old_bl,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_bl,0)/y.old_bl)*100)) as numeric), 2)  end as per_bl " +
			" ,case when COALESCE(y.old_duty,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_duty,0)/y.old_duty)*100)) as numeric), 2) end as per_duty  " +
			" from retail.retail_shop x left join  " +
			" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
			" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
			" select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(br.bottle)) as duty " +
			" from  " +
			" (select a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
			" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
			" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
			" where ar.vch_to='RT' and ar.vch_from='FL2'  and ar.dt_date<'2020-05-01' and EXTRACT(MONTH FROM ar.dt_date)="+act.getSelectedMonth()+")a " +
			" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b " +
			" where  a.vch_gatepass_no=b.vch_gatepass_no group by a.shop_id,b.int_pckg_id)br, distillery.packaging_details_19_20 d " +
			" where d.package_id=br.int_pckg_id " +
			" group by br.shop_id,d.quantity,d.duty,d.adduty " +
			" )cr group by cr.shop_id " +
			" union all " +
			"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
			" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,sum(br.duty) as duty from  " +
			" (select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
			" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
			" from fl2d.gatepass_to_districtwholesale_fl2d_19_20 ar where ar.vch_to='RT' and ar.dt_date<'2020-05-01'  and EXTRACT(MONTH FROM ar.dt_date)="+act.getSelectedMonth()+")a  " +
			" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_19_20 az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00  group by a.shop_id, b.int_pckg_id) br, " +
			" distillery.packaging_details_19_20 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity)cr " +
			" group by cr.shop_id " +
			" union all  " +
			" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, " +
			" case when '1'='"+act.getSelectedMonth()+"' then sum(jan_19_box) when '2'='"+act.getSelectedMonth()+"' then sum(feb_19_box) when '3'='"+act.getSelectedMonth()+"' then sum(march_19_box) when '4'='"+act.getSelectedMonth()+"' then sum(april_18_box) when '5'='"+act.getSelectedMonth()+"' then sum(may_18_box) when '6'='"+act.getSelectedMonth()+"' then sum(june_18_box) when '7'='"+act.getSelectedMonth()+"' then sum(july_18_box) when '8'='"+act.getSelectedMonth()+"' then sum(august_18_box) when '9'='"+act.getSelectedMonth()+"' then sum(sept_18_box) when '10'='"+act.getSelectedMonth()+"' then sum(oct_18_box) when '11'='"+act.getSelectedMonth()+"' then sum(nov_18_box)  when '12'='"+act.getSelectedMonth()+"' then sum(dec_18_box) end as old_box " +
			" ,case when '1'='"+act.getSelectedMonth()+"' then sum(jan_19_bl) when '2'='"+act.getSelectedMonth()+"' then sum(feb_19_bl) when '3'='"+act.getSelectedMonth()+"' then sum(march_19_bl) when '4'='"+act.getSelectedMonth()+"' then sum(april_18_bl) when '5'='"+act.getSelectedMonth()+"' then sum(may_18_bl) when '6'='"+act.getSelectedMonth()+"' then sum(june_18_bl) when '7'='"+act.getSelectedMonth()+"' then sum(july_18_bl) when '8'='"+act.getSelectedMonth()+"' then sum(august_18_bl) when '9'='"+act.getSelectedMonth()+"' then sum(sept_18_bl) when '10'='"+act.getSelectedMonth()+"' then sum(oct_18_bl) when '11'='"+act.getSelectedMonth()+"' then sum(nov_18_bl)  when '12'='"+act.getSelectedMonth()+"' then sum(dec_18_bl) end as old_bl   " +
			" ,case when '1'='"+act.getSelectedMonth()+"' then sum(jan_19_duty) when '2'='"+act.getSelectedMonth()+"' then sum(feb_19_duty) when '3'='"+act.getSelectedMonth()+"' then sum(march_19_duty) when '4'='"+act.getSelectedMonth()+"' then sum(april_18_duty) when '5'='"+act.getSelectedMonth()+"' then sum(may_18_duty) when '6'='"+act.getSelectedMonth()+"' then sum(june_18_duty) when '7'='"+act.getSelectedMonth()+"' then sum(july_18_duty) when '8'='"+act.getSelectedMonth()+"' then sum(august_18_duty) when '9'='"+act.getSelectedMonth()+"' then sum(sept_18_duty) when '10'='"+act.getSelectedMonth()+"' then sum(oct_18_duty) when '11'='"+act.getSelectedMonth()+"' then sum(nov_18_duty)  when '12'='"+act.getSelectedMonth()+"' then sum(dec_18_duty) end as old_duty " +
			" from retail.retail_shop_lifting_duty_18_19 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
			" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
			" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  "+filterDistrict +filterSector +filterShop+"  order by description,x.vch_name_of_shop  ";
		
		  
		 }
		 
		 else{
	reportQuery="" +
			"  " +
			" select x.serial_no,(x.vch_name_of_shop||' - '||x.vch_shop_type) as vch_name_of_shop,(SELECT sector_name FROM public.mst_sector_master where sector_id= x.sector) as sector_name, " +
			" (select description from public.district d where d.districtid=x.district_id) as description, " +
			//" y.new_box,y.new_bl,y.new_duty,y.old_box,y.old_bl,y.old_duty " +
			" COALESCE(y.new_box,0)new_box,COALESCE(y.new_bl,0)new_bl,COALESCE(y.new_duty,0)new_duty," +
			" round(CAST(float8(COALESCE(y.old_box,0)) as numeric), 0)as old_box, "+
			//"COALESCE(y.old_box,0)old_box," +
			"COALESCE(y.old_bl,0)old_bl,COALESCE(y.old_duty,0)old_duty " +
			" ,case when COALESCE(y.old_box,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_box,0)/y.old_box)*100)) as numeric), 2) end as per_box " +
			" ,case when COALESCE(y.old_bl,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_bl,0)/y.old_bl)*100)) as numeric), 2)  end as per_bl " +
			" ,case when COALESCE(y.old_duty,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_duty,0)/y.old_duty)*100)) as numeric), 2) end as per_duty  " +
			" from retail.retail_shop x left join  " +
			" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
			" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
			" select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(br.bottle)) as duty " +
			" from  " +
			" (select a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
			" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
			" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
			" where ar.vch_to='RT'  and ar.dt_date<'2020-05-01' and ar.vch_from='FL2' and " +
			" case when 4="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
			" when 1="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
			" when 2="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
			" when 3="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end )a " +
			" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b " +
			" where  a.vch_gatepass_no=b.vch_gatepass_no group by a.shop_id,b.int_pckg_id)br, distillery.packaging_details_19_20 d " +
			" where d.package_id=br.int_pckg_id " +
			" group by br.shop_id,d.quantity,d.duty,d.adduty " +
			" )cr group by cr.shop_id " +
			" union all " +
			"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
			" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,sum(br.duty) as duty from  " +
			" (select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
			" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
			" from fl2d.gatepass_to_districtwholesale_fl2d_19_20 ar where ar.vch_to='RT'  and ar.dt_date<'2020-05-01' and  " +
			" case when 4="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
			" when 1="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
			" when 2="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
			" when 3="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
			" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_19_20 az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00 group by a.shop_id, b.int_pckg_id) br, " +
			" distillery.packaging_details_19_20 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity)cr " +
			" group by cr.shop_id " +
			" union all  " +
			" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, " +
			" case when '1'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(april_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(may_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(june_18_box,0)) as numeric), 0)) when '2'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(july_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(august_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(sept_18_box ,0)) as numeric), 0)) when '3'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(oct_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(nov_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(dec_18_box ,0)) as numeric), 0)) when '4'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(jan_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(feb_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(march_19_box ,0)) as numeric), 0)) end as old_box " +
			" ,case when '1'='"+act.getSelectedQuarter()+"' then sum(april_18_bl + may_18_bl + june_18_bl) when '2'='"+act.getSelectedQuarter()+"' then sum(july_18_bl+ august_18_bl + sept_18_bl) when '3'='"+act.getSelectedQuarter()+"' then sum(oct_18_bl + nov_18_bl + dec_18_bl) when '4'='"+act.getSelectedQuarter()+"' then sum(jan_19_bl + feb_19_bl+ march_19_bl) end as old_bl "+
			" ,case when '1'='"+act.getSelectedQuarter()+"' then sum(april_18_duty + may_18_duty + june_18_duty) when '2'='"+act.getSelectedQuarter()+"' then sum(july_18_duty+ august_18_duty + sept_18_duty) when '3'='"+act.getSelectedQuarter()+"' then sum(oct_18_duty + nov_18_duty + dec_18_duty) when '4'='"+act.getSelectedQuarter()+"' then sum(jan_19_duty + feb_19_duty+ march_19_duty) end as old_duty " +
			" from retail.retail_shop_lifting_duty_18_19 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
			" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
			" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  "+filterDistrict +filterSector +filterShop+"  order by description,x.vch_name_of_shop  ";
		 
		 
		 }
	 
		pst = con.prepareStatement(reportQuery);
		rs = pst.executeQuery();

		if (rs.next()) {

			rs = pst.executeQuery();
			Map parameters = new HashMap();
			parameters.put("REPORT_CONNECTION", con);
			parameters.put("SUBREPORT_DIR", relativePath + File.separator);
			parameters.put("image", relativePath + File.separator);
			parameters.put("radioType", act.getRadio());
			if(act.getSelectedMonth()>0) {
				parameters.put("monthName1", act.getMonthName());
				}
				if(act.getSelectedQuarter()>0) {
					parameters.put("monthName1","Quarter- " + act.getSelectedQuarter());
				}
			if(act.getSelectedMonth()>0) {
				if(act.getSelectedMonth()==1 ||act.getSelectedMonth()==2 ||act.getSelectedMonth()==3||act.getSelectedMonth()==4) {
					parameters.put("monthName", act.getMonthName()+"-2020");
				}else {
					parameters.put("monthName", act.getMonthName()+"-2019");
				}
			}
			else {
			if(act.getSelectedQuarter()==1 ||act.getSelectedQuarter()==2 ||act.getSelectedQuarter()==3 ) {
				parameters.put("monthName","Quarter- " +  act.getSelectedQuarter()+"-2019");
			}else {
				parameters.put("monthName", "Quarter- " + act.getSelectedQuarter()+"-2020");
			}

			 	 
			}

			JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

			jasperReport = (JasperReport) JRLoader.loadObject(relativePath
					+ File.separator + "fl_lifting_comparision_shopwise.jasper");

			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, jrRs);
			Random rand = new Random();
			int n = rand.nextInt(250) + 1;
			JasperExportManager.exportReportToPdfFile(print,
					relativePathpdf + File.separator
							+ "fl_lifting_comparision_shopwise_"+act.getBwfl_id()+"" + "-" + n + ".pdf");
			act.setPdfName("fl_lifting_comparision_shopwise_" +act.getBwfl_id()+""+ "-" + n + ".pdf");
			act.setPrintFlag(true);
		} else {
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
public void printReportShopWise2021(LifitingComparisionFLAction act) {
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
	String filterDistrict = "";
	 String filterSector="";
	 String filterShop="";


	try {
		con = ConnectionToDataBase.getConnection();
 
		
		if(!act.getDistid().equalsIgnoreCase("99") && !act.getDistid().equalsIgnoreCase("9999"))
		{
			filterDistrict=" and x.district_id="+ Integer.parseInt(act.getDistid())+ "";
			
			
			
			if(act.getSectorId().equalsIgnoreCase("0"))
			{
				filterSector ="";
				
				if(act.getShopId().equalsIgnoreCase("0"))
				{
					filterShop="";
				}
				else
				{
					filterShop=" and x.serial_no='"+act.getShopId()+"' ";
				}
			}
				
			else
			{
				filterSector = " and x.sector='"+act.getSectorId()+"' ";
				
				
				if(act.getShopId().equalsIgnoreCase("0"))
				{
					filterShop="";
				}
				else
				{
					filterShop=" and x.serial_no='"+act.getShopId()+"' ";
				}
				
				
			}
			
			
			
		}
		else
		{
			 
			filterDistrict="";
			filterSector = "";
			filterShop="";
		}
		
		 
   
		 if (act.getSelectedQuarter()==0){
	reportQuery="" +
			" select x.serial_no,(x.vch_name_of_shop||' - '||x.vch_shop_type) as vch_name_of_shop,(SELECT sector_name FROM public.mst_sector_master where sector_id= x.sector) as sector_name, " +
			" (select description from public.district d where d.districtid=x.district_id) as description, " +
			//" y.new_box,y.new_bl,y.new_duty,y.old_box,y.old_bl,y.old_duty " +
			" COALESCE(y.new_box,0)new_box,COALESCE(y.new_bl,0)new_bl,COALESCE(y.new_duty,0)new_duty,COALESCE(y.coronafee,0)coronafee," +
			" round(CAST(float8(COALESCE(y.old_box,0)) as numeric), 0)as old_box, "+
			//"COALESCE(y.old_box,0)old_box," +
			"COALESCE(y.old_bl,0)old_bl,COALESCE(y.old_duty,0)old_duty " +
			" ,case when COALESCE(y.old_box,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_box,0)/y.old_box)*100)) as numeric), 2) end as per_box " +
			" ,case when COALESCE(y.old_bl,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_bl,0)/y.old_bl)*100)) as numeric), 2)  end as per_bl " +
			" ,case when COALESCE(y.old_duty,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_duty+y.coronafee,0)/y.old_duty)*100)) as numeric), 2) end as per_duty  " +
			" from retail.retail_shop x left join  " +
			" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.coronafee)coronafee,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
			" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,sum(cr.coronafee) as coronafee, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
			" select br.shop_id,sum(br.box)box,sum(br.bl) as bl,sum(br.duty) as duty,sum(br.coronafee) as coronafee " +
			" from  " +
			" ("
			+ "select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee ,a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
			" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
			" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 ar " +
			" where ar.vch_to='RT' and ar.vch_from  in ('FL2B','FL2' )  and EXTRACT(MONTH FROM ar.dt_date)="+act.getSelectedMonth()+" )a " +
			" ,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b , distillery.packaging_details_20_21 d ,distillery.brand_registration_20_21 c " +
			" where  a.vch_gatepass_no=b.vch_gatepass_no  and b.int_brand_id=c.brand_id  and c.license_category not in  ('BEER','LAB','IMPORTED BEER' )  and d.package_id=b.int_pckg_id group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty,d.cesh union all "
			+ "select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee ,a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
			" from (select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
			" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
			" where ar.vch_to='RT' and ar.vch_from='FL2' and ar.dt_date>='2020-05-01'  and EXTRACT(MONTH FROM ar.dt_date)="+act.getSelectedMonth()+" )a " +
			" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b , distillery.packaging_details_19_20 d " +
			" where  a.vch_gatepass_no=b.vch_gatepass_no  and d.package_id=b.int_pckg_id group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty,d.cesh "
			+ ")br " +
			" " +
			" group by br.shop_id  " +
			" )cr group by cr.shop_id " +
			" union all " +
			"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as coronafee,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
			" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,sum(br.duty) as duty from  " +
			" ("
			+ "select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
			" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
			" from fl2d.gatepass_to_districtwholesale_fl2d_20_21 ar where ar.vch_to='RT' and EXTRACT(MONTH FROM ar.dt_date)="+act.getSelectedMonth()+")a  " +
			" ,fl2d.fl2d_stock_trxn_20_21  b ,distillery.brand_registration_20_21 az  where a.vch_gatepass_no=b.vch_gatepass_no        and az.brand_id=b.int_brand_id and  az.strength > 8.00  group by a.shop_id, b.int_pckg_id "
			+ " union all select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
			" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
			" from fl2d.gatepass_to_districtwholesale_fl2d_19_20  ar where ar.vch_to='RT' and ar.dt_date>='2020-05-01'  and EXTRACT(MONTH FROM ar.dt_date)="+act.getSelectedMonth()+")a  " +
			" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_19_20 az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00  group by a.shop_id, b.int_pckg_id"
					+ ") br, " +
			" distillery.packaging_details_20_21 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity)cr " +
			 
			 
			" group by cr.shop_id " +
			" union all  " +
			" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, 0 as coronafee, " +
			" case when '1'='"+act.getSelectedMonth()+"' then sum(jan_19_box) when '2'='"+act.getSelectedMonth()+"' then sum(feb_19_box) when '3'='"+act.getSelectedMonth()+"' then sum(march_19_box) when '4'='"+act.getSelectedMonth()+"' then sum(april_18_box) when '5'='"+act.getSelectedMonth()+"' then sum(may_18_box) when '6'='"+act.getSelectedMonth()+"' then sum(june_18_box) when '7'='"+act.getSelectedMonth()+"' then sum(july_18_box) when '8'='"+act.getSelectedMonth()+"' then sum(august_18_box) when '9'='"+act.getSelectedMonth()+"' then sum(sept_18_box) when '10'='"+act.getSelectedMonth()+"' then sum(oct_18_box) when '11'='"+act.getSelectedMonth()+"' then sum(nov_18_box)  when '12'='"+act.getSelectedMonth()+"' then sum(dec_18_box) end as old_box " +
			" ,case when '1'='"+act.getSelectedMonth()+"' then sum(jan_19_bl) when '2'='"+act.getSelectedMonth()+"' then sum(feb_19_bl) when '3'='"+act.getSelectedMonth()+"' then sum(march_19_bl) when '4'='"+act.getSelectedMonth()+"' then sum(april_18_bl) when '5'='"+act.getSelectedMonth()+"' then sum(may_18_bl) when '6'='"+act.getSelectedMonth()+"' then sum(june_18_bl) when '7'='"+act.getSelectedMonth()+"' then sum(july_18_bl) when '8'='"+act.getSelectedMonth()+"' then sum(august_18_bl) when '9'='"+act.getSelectedMonth()+"' then sum(sept_18_bl) when '10'='"+act.getSelectedMonth()+"' then sum(oct_18_bl) when '11'='"+act.getSelectedMonth()+"' then sum(nov_18_bl)  when '12'='"+act.getSelectedMonth()+"' then sum(dec_18_bl) end as old_bl   " +
			" ,case when '1'='"+act.getSelectedMonth()+"' then sum(jan_19_duty) when '2'='"+act.getSelectedMonth()+"' then sum(feb_19_duty) when '3'='"+act.getSelectedMonth()+"' then sum(march_19_duty) when '4'='"+act.getSelectedMonth()+"' then sum(april_18_duty) when '5'='"+act.getSelectedMonth()+"' then sum(may_18_duty) when '6'='"+act.getSelectedMonth()+"' then sum(june_18_duty) when '7'='"+act.getSelectedMonth()+"' then sum(july_18_duty) when '8'='"+act.getSelectedMonth()+"' then sum(august_18_duty) when '9'='"+act.getSelectedMonth()+"' then sum(sept_18_duty) when '10'='"+act.getSelectedMonth()+"' then sum(oct_18_duty) when '11'='"+act.getSelectedMonth()+"' then sum(nov_18_duty)  when '12'='"+act.getSelectedMonth()+"' then sum(dec_18_duty) end as old_duty " +
			" from retail.retail_shop_lifting_duty_19_20 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
			" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
			" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  "+filterDistrict +filterSector +filterShop+"  order by  x.serial_no,description,x.vch_name_of_shop  ";
		
		  
		 }
		 
		 else{
	reportQuery="" +
			"  " +
			" select x.serial_no,(x.vch_name_of_shop||' - '||x.vch_shop_type) as vch_name_of_shop,(SELECT sector_name FROM public.mst_sector_master where sector_id= x.sector) as sector_name, " +
			" (select description from public.district d where d.districtid=x.district_id) as description, " +
			//" y.new_box,y.new_bl,y.new_duty,y.old_box,y.old_bl,y.old_duty " +
			" COALESCE(y.new_box,0)new_box,COALESCE(y.new_bl,0)new_bl,COALESCE(y.new_duty,0)new_duty,COALESCE(y.coronafee,0)coronafee," +
			" round(CAST(float8(COALESCE(y.old_box,0)) as numeric), 0)as old_box, "+
			//"COALESCE(y.old_box,0)old_box," +
			"COALESCE(y.old_bl,0)old_bl,COALESCE(y.old_duty,0)old_duty " +
			" ,case when COALESCE(y.old_box,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_box,0)/y.old_box)*100)) as numeric), 2) end as per_box " +
			" ,case when COALESCE(y.old_bl,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_bl,0)/y.old_bl)*100)) as numeric), 2)  end as per_bl " +
			" ,case when COALESCE(y.old_duty,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_duty+y.coronafee,0)/y.old_duty)*100)) as numeric), 2) end as per_duty  " +
			" from retail.retail_shop x left join  " +
			" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.coronafee)coronafee,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
			" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,sum(cr.coronafee) as coronafee, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
			" select br.shop_id,sum(br.box)box, sum(br.bl) as bl,sum(br.duty) as duty,sum(br.coronafee) as coronafee " +
			" from  " +
			" ("
			+ "select a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
			" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
			" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 ar " +
			" where ar.vch_to='RT' and ar.vch_from  in ('FL2B','FL2' )  and " +
			" case when 4="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
			" when 1="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
			" when 2="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
			" when 3="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end )a " +
			" ,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b, distillery.packaging_details_20_21 d" +
			" where  a.vch_gatepass_no=b.vch_gatepass_no and  d.package_id=b.int_pckg_id  group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty ,d.cesh "
			+ " union all select a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
			" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
			" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
			" where ar.vch_to='RT' and ar.vch_from='FL2' and ar.dt_date>='2020-05-01' and " +
			" case when 4="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
			" when 1="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
			" when 2="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
			" when 3="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end )a " +
			" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b , distillery.packaging_details_19_20 d" +
			" where a.dt_date>='2020-05-01' and  a.vch_gatepass_no=b.vch_gatepass_no and  d.package_id=b.int_pckg_id  group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty ,d.cesh "
			+ ")br " +
			"  " +
			" group by br.shop_id " +
			" )cr group by cr.shop_id " +
			" union all " +
			"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as coronafee,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
			" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,sum(br.duty) as duty from  " +
			" (select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
			" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
			" from fl2d.gatepass_to_districtwholesale_fl2d_20_21 ar where ar.vch_to='RT' and ar.dt_date>='2020-05-01' and  " +
			" case when 4="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
			" when 1="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
			" when 2="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
			" when 3="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
			" ,fl2d.fl2d_stock_trxn_20_21  b ,distillery.brand_registration_20_21 az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00 group by a.shop_id, b.int_pckg_id) br, " +
			" distillery.packaging_details_20_21 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity)cr " +
			" group by cr.shop_id " +
			" union all  " +
			" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, 0 as coronafee," +
			" case when '1'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(april_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(may_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(june_18_box,0)) as numeric), 0)) when '2'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(july_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(august_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(sept_18_box ,0)) as numeric), 0)) when '3'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(oct_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(nov_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(dec_18_box ,0)) as numeric), 0)) when '4'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(jan_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(feb_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(march_19_box ,0)) as numeric), 0)) end as old_box " +
			" ,case when '1'='"+act.getSelectedQuarter()+"' then sum(april_18_bl + may_18_bl + june_18_bl) when '2'='"+act.getSelectedQuarter()+"' then sum(july_18_bl+ august_18_bl + sept_18_bl) when '3'='"+act.getSelectedQuarter()+"' then sum(oct_18_bl + nov_18_bl + dec_18_bl) when '4'='"+act.getSelectedQuarter()+"' then sum(jan_19_bl + feb_19_bl+ march_19_bl) end as old_bl "+
			" ,case when '1'='"+act.getSelectedQuarter()+"' then sum(april_18_duty + may_18_duty + june_18_duty) when '2'='"+act.getSelectedQuarter()+"' then sum(july_18_duty+ august_18_duty + sept_18_duty) when '3'='"+act.getSelectedQuarter()+"' then sum(oct_18_duty + nov_18_duty + dec_18_duty) when '4'='"+act.getSelectedQuarter()+"' then sum(jan_19_duty + feb_19_duty+ march_19_duty) end as old_duty " +
			" from retail.retail_shop_lifting_duty_19_20 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
			" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
			" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  "+filterDistrict +filterSector +filterShop+"  order  x.serial_no,by description,x.vch_name_of_shop  ";
		 
		 
		 }
	 
		pst = con.prepareStatement(reportQuery);
		rs = pst.executeQuery();

		if (rs.next()) {

			rs = pst.executeQuery();
			Map parameters = new HashMap();
			parameters.put("REPORT_CONNECTION", con);
			parameters.put("SUBREPORT_DIR", relativePath + File.separator);
			parameters.put("image", relativePath + File.separator);
			parameters.put("radioType", act.getRadio());
			if(act.getSelectedMonth()>0) {
				parameters.put("monthName1", act.getMonthName());
				}
				if(act.getSelectedQuarter()>0) {
					parameters.put("monthName1","Quarter- " + act.getSelectedQuarter());
				}
			if(act.getSelectedMonth()>0) {
				if(act.getSelectedMonth()==1 ||act.getSelectedMonth()==2 ||act.getSelectedMonth()==3||act.getSelectedMonth()==4) {
					parameters.put("monthName", act.getMonthName()+"-2020");
				}else {
					parameters.put("monthName", act.getMonthName()+"-2020");
				}
			}
			else {
			if(act.getSelectedQuarter()==1 ||act.getSelectedQuarter()==2 ||act.getSelectedQuarter()==3 ) {
				parameters.put("monthName","Quarter- " +  act.getSelectedQuarter()+"-2020");
			}else {
				parameters.put("monthName", "Quarter- " + act.getSelectedQuarter()+"-2020");
			}

			 	 
			}

			JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

			jasperReport = (JasperReport) JRLoader.loadObject(relativePath
					+ File.separator + "fl_lifting_comparision_shopwise2020.jasper");

			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, jrRs);
			Random rand = new Random();
			int n = rand.nextInt(250) + 1;
			JasperExportManager.exportReportToPdfFile(print,
					relativePathpdf + File.separator
							+ "fl_lifting_comparision_shopwise_"+act.getBwfl_id()+"" + "-" + n + ".pdf");
			act.setPdfName("fl_lifting_comparision_shopwise_" +act.getBwfl_id()+""+ "-" + n + ".pdf");
			act.setPrintFlag(true);
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"No Data Found!!", "No Data Found!!"));
			act.setPrintFlag(false);
		}
	} catch (JRException e) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						e.getMessage()+reportQuery,e.getMessage()+reportQuery));
		
	} catch (Exception e) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						e.getMessage()+reportQuery,e.getMessage()+reportQuery));
		
	} finally {
		try {
			if (rs != null)
				rs.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							e.getMessage()+reportQuery,e.getMessage()+reportQuery));
			
		}
	}

}



public boolean excelShopWise(LifitingComparisionFLAction action) throws ParseException {

	Connection con = null;

	
    double cases_of_last_year_sale = 0.0;
    double bl_of_last_year_sale = 0;
    double revenue_of_last_year_sale = 0;
    double cases_of_current_year_sale = 0;
    double bl_of_current_year_sale = 0;
    double revenue_of_current_year_sale = 0;
    double cases_box=0.0;
    double bl_of =0;
    double revenue=0;
   
	
    DecimalFormat diciformatter = new DecimalFormat("#.####");
	
	String filterDistrict = "";
	 String filterSector="";
	 String filterShop="";
	
	// Date date11=new SimpleDateFormat("dd/MM/yyyy").parse(action.getDateSelected());
	 
	 //System.out.println("action.getDateSelected()==date11=="+date11);
	
	 if(!action.getDistid().equalsIgnoreCase("99") && !action.getDistid().equalsIgnoreCase("9999"))
		{
			filterDistrict=" and x.district_id="+ Integer.parseInt(action.getDistid())+ "";
			
			
			
			if(action.getSectorId().equalsIgnoreCase("0"))
			{
				filterSector ="";
				
				if(action.getShopId().equalsIgnoreCase("0"))
				{
					filterShop="";
				}
				else
				{
					filterShop=" and x.serial_no='"+action.getShopId()+"' ";
				}
			}
				
			else
			{
				filterSector = " and x.sector='"+action.getSectorId()+"' ";
				
				
				if(action.getShopId().equalsIgnoreCase("0"))
				{
					filterShop="";
				}
				else
				{
					filterShop=" and x.serial_no='"+action.getShopId()+"' ";
				}
				
				
			}
			
			
			
		}
	else
	{
		System.out.println("123");
		filterDistrict="";
		filterSector = "";
		filterShop="";
	}

	
	String reportQuery="";
	 if (action.getSelectedQuarter()==0){
	reportQuery="" +
			" select x.serial_no,(x.vch_name_of_shop||' - '||x.vch_shop_type) as vch_name_of_shop,(SELECT sector_name FROM public.mst_sector_master where sector_id= x.sector) as sector_name, " +
			" (select description from public.district d where d.districtid=x.district_id) as description, " +
			//" y.new_box,y.new_bl,y.new_duty,y.old_box,y.old_bl,y.old_duty " +
			" COALESCE(y.new_box,0)new_box,COALESCE(y.new_bl,0)new_bl,COALESCE(y.new_duty,0)new_duty," +
			" round(CAST(float8(COALESCE(y.old_box,0)) as numeric), 0)as old_box, "+
			//"COALESCE(y.old_box,0)old_box," +
			"COALESCE(y.old_bl,0)old_bl,COALESCE(y.old_duty,0)old_duty " +
			" ,case when COALESCE(y.old_box,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_box,0)/y.old_box)*100)) as numeric), 2) end as per_box " +
			" ,case when COALESCE(y.old_bl,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_bl,0)/y.old_bl)*100)) as numeric), 2)  end as per_bl " +
			" ,case when COALESCE(y.old_duty,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_duty,0)/y.old_duty)*100)) as numeric), 2) end as per_duty  " +
			" from retail.retail_shop x left join  " +
			" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
			" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
			" select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(br.bottle)) as duty " +
			" from  " +
			" (select a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
			" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
			" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
			" where ar.vch_to='RT' and ar.vch_from='FL2'  and ar.dt_date<'2020-05-01' and EXTRACT(MONTH FROM ar.dt_date)="+action.getSelectedMonth()+")a " +
			" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b " +
			" where  a.vch_gatepass_no=b.vch_gatepass_no group by a.shop_id,b.int_pckg_id)br, distillery.packaging_details_19_20 d " +
			" where d.package_id=br.int_pckg_id " +
			" group by br.shop_id,d.quantity,d.duty,d.adduty " +
			" )cr group by cr.shop_id " +
			" union all " +
			"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
			" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,sum(br.duty) as duty from  " +
			" (select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
			" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
			" from fl2d.gatepass_to_districtwholesale_fl2d_19_20 ar where ar.vch_to='RT' and ar.dt_date<'2020-05-01'  and EXTRACT(MONTH FROM ar.dt_date)="+action.getSelectedMonth()+")a  " +
			" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_19_20 az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00  group by a.shop_id, b.int_pckg_id) br, " +
			" distillery.packaging_details_19_20 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity)cr " +
			" group by cr.shop_id " +
			" union all  " +
			" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, " +
			" case when '1'='"+action.getSelectedMonth()+"' then sum(jan_19_box) when '2'='"+action.getSelectedMonth()+"' then sum(feb_19_box) when '3'='"+action.getSelectedMonth()+"' then sum(march_19_box) when '4'='"+action.getSelectedMonth()+"' then sum(april_18_box) when '5'='"+action.getSelectedMonth()+"' then sum(may_18_box) when '6'='"+action.getSelectedMonth()+"' then sum(june_18_box) when '7'='"+action.getSelectedMonth()+"' then sum(july_18_box) when '8'='"+action.getSelectedMonth()+"' then sum(august_18_box) when '9'='"+action.getSelectedMonth()+"' then sum(sept_18_box) when '10'='"+action.getSelectedMonth()+"' then sum(oct_18_box) when '11'='"+action.getSelectedMonth()+"' then sum(nov_18_box)  when '12'='"+action.getSelectedMonth()+"' then sum(dec_18_box) end as old_box " +
			" ,case when '1'='"+action.getSelectedMonth()+"' then sum(jan_19_bl) when '2'='"+action.getSelectedMonth()+"' then sum(feb_19_bl) when '3'='"+action.getSelectedMonth()+"' then sum(march_19_bl) when '4'='"+action.getSelectedMonth()+"' then sum(april_18_bl) when '5'='"+action.getSelectedMonth()+"' then sum(may_18_bl) when '6'='"+action.getSelectedMonth()+"' then sum(june_18_bl) when '7'='"+action.getSelectedMonth()+"' then sum(july_18_bl) when '8'='"+action.getSelectedMonth()+"' then sum(august_18_bl) when '9'='"+action.getSelectedMonth()+"' then sum(sept_18_bl) when '10'='"+action.getSelectedMonth()+"' then sum(oct_18_bl) when '11'='"+action.getSelectedMonth()+"' then sum(nov_18_bl)  when '12'='"+action.getSelectedMonth()+"' then sum(dec_18_bl) end as old_bl   " +
			" ,case when '1'='"+action.getSelectedMonth()+"' then sum(jan_19_duty) when '2'='"+action.getSelectedMonth()+"' then sum(feb_19_duty) when '3'='"+action.getSelectedMonth()+"' then sum(march_19_duty) when '4'='"+action.getSelectedMonth()+"' then sum(april_18_duty) when '5'='"+action.getSelectedMonth()+"' then sum(may_18_duty) when '6'='"+action.getSelectedMonth()+"' then sum(june_18_duty) when '7'='"+action.getSelectedMonth()+"' then sum(july_18_duty) when '8'='"+action.getSelectedMonth()+"' then sum(august_18_duty) when '9'='"+action.getSelectedMonth()+"' then sum(sept_18_duty) when '10'='"+action.getSelectedMonth()+"' then sum(oct_18_duty) when '11'='"+action.getSelectedMonth()+"' then sum(nov_18_duty)  when '12'='"+action.getSelectedMonth()+"' then sum(dec_18_duty) end as old_duty " +
			" from retail.retail_shop_lifting_duty_18_19 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
			" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
			" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  "+filterDistrict +filterSector +filterShop+"  order by description,x.vch_name_of_shop  ";
		
		  
		 }
		 
		 else{
	reportQuery="" +
			"  " +
			" select x.serial_no,(x.vch_name_of_shop||' - '||x.vch_shop_type) as vch_name_of_shop,(SELECT sector_name FROM public.mst_sector_master where sector_id= x.sector) as sector_name, " +
			" (select description from public.district d where d.districtid=x.district_id) as description, " +
			//" y.new_box,y.new_bl,y.new_duty,y.old_box,y.old_bl,y.old_duty " +
			" COALESCE(y.new_box,0)new_box,COALESCE(y.new_bl,0)new_bl,COALESCE(y.new_duty,0)new_duty," +
			" round(CAST(float8(COALESCE(y.old_box,0)) as numeric), 0)as old_box, "+
			//"COALESCE(y.old_box,0)old_box," +
			"COALESCE(y.old_bl,0)old_bl,COALESCE(y.old_duty,0)old_duty " +
			" ,case when COALESCE(y.old_box,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_box,0)/y.old_box)*100)) as numeric), 2) end as per_box " +
			" ,case when COALESCE(y.old_bl,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_bl,0)/y.old_bl)*100)) as numeric), 2)  end as per_bl " +
			" ,case when COALESCE(y.old_duty,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_duty,0)/y.old_duty)*100)) as numeric), 2) end as per_duty  " +
			" from retail.retail_shop x left join  " +
			" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
			" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
			" select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(br.bottle)) as duty " +
			" from  " +
			" (select a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
			" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
			" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
			" where ar.vch_to='RT'  and ar.dt_date<'2020-05-01' and ar.vch_from='FL2' and " +
			" case when 4="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
			" when 1="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
			" when 2="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
			" when 3="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end )a " +
			" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b " +
			" where  a.vch_gatepass_no=b.vch_gatepass_no group by a.shop_id,b.int_pckg_id)br, distillery.packaging_details_19_20 d " +
			" where d.package_id=br.int_pckg_id " +
			" group by br.shop_id,d.quantity,d.duty,d.adduty " +
			" )cr group by cr.shop_id " +
			" union all " +
			"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
			" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,sum(br.duty) as duty from  " +
			" (select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
			" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
			" from fl2d.gatepass_to_districtwholesale_fl2d_19_20 ar where ar.vch_to='RT'  and ar.dt_date<'2020-05-01' and  " +
			" case when 4="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
			" when 1="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
			" when 2="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
			" when 3="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
			" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_19_20 az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00 group by a.shop_id, b.int_pckg_id) br, " +
			" distillery.packaging_details_19_20 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity)cr " +
			" group by cr.shop_id " +
			" union all  " +
			" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, " +
			" case when '1'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(april_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(may_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(june_18_box,0)) as numeric), 0)) when '2'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(july_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(august_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(sept_18_box ,0)) as numeric), 0)) when '3'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(oct_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(nov_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(dec_18_box ,0)) as numeric), 0)) when '4'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(jan_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(feb_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(march_19_box ,0)) as numeric), 0)) end as old_box " +
			" ,case when '1'='"+action.getSelectedQuarter()+"' then sum(april_18_bl + may_18_bl + june_18_bl) when '2'='"+action.getSelectedQuarter()+"' then sum(july_18_bl+ august_18_bl + sept_18_bl) when '3'='"+action.getSelectedQuarter()+"' then sum(oct_18_bl + nov_18_bl + dec_18_bl) when '4'='"+action.getSelectedQuarter()+"' then sum(jan_19_bl + feb_19_bl+ march_19_bl) end as old_bl "+
			" ,case when '1'='"+action.getSelectedQuarter()+"' then sum(april_18_duty + may_18_duty + june_18_duty) when '2'='"+action.getSelectedQuarter()+"' then sum(july_18_duty+ august_18_duty + sept_18_duty) when '3'='"+action.getSelectedQuarter()+"' then sum(oct_18_duty + nov_18_duty + dec_18_duty) when '4'='"+action.getSelectedQuarter()+"' then sum(jan_19_duty + feb_19_duty+ march_19_duty) end as old_duty " +
			" from retail.retail_shop_lifting_duty_18_19 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
			" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
			" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  "+filterDistrict +filterSector +filterShop+"  order by description,x.vch_name_of_shop  ";
		 
		 
		 }
	
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
				.createSheet("FL Lifting Comparision Report");

		worksheet.setColumnWidth(0, 3000);
		worksheet.setColumnWidth(1, 5000);
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
		if(action.getSelectedMonth()>0){
		cellhead0.setCellValue("FL Lifting Comparision ShopWise" + "-" +action.getMonthName());
		}
		
		if(action.getSelectedQuarter()>0){
			
			cellhead0.setCellValue("FL Lifting Comparision ShopWise" + " " + "Quarter- " + action.getSelectedQuarter());
			
			}
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
		cellhead1.setCellValue("Shop id");
		cellhead1.setCellStyle(cellStyle);
		
		XSSFCell cellhead2 = rowhead.createCell((int) 1);
		cellhead2.setCellValue("District");
		cellhead2.setCellStyle(cellStyle);

		XSSFCell cellhead3 = rowhead.createCell((int) 2);
		cellhead3.setCellValue("Shop Name");
		cellhead3.setCellStyle(cellStyle);
		
		XSSFCell cellhead4 = rowhead.createCell((int) 3);
		cellhead4.setCellValue("Sector");
		cellhead4.setCellStyle(cellStyle);

		XSSFCell cellhead5 = rowhead.createCell((int) 4);
		cellhead5.setCellValue("Cases of Last year sale");
		cellhead5.setCellStyle(cellStyle);
		
		XSSFCell cellhead6 = rowhead.createCell((int) 5);
		cellhead6.setCellValue("BL of Last year sale");
		cellhead6.setCellStyle(cellStyle);
		

		XSSFCell cellhead7 = rowhead.createCell((int) 6);
		cellhead7.setCellValue("Revenue of Last year sale");
		cellhead7.setCellStyle(cellStyle);
		

		XSSFCell cellhead8 = rowhead.createCell((int) 7);
		cellhead8.setCellValue("Cases of current year sale");
		cellhead8.setCellStyle(cellStyle);
		
		XSSFCell cellhead9 = rowhead.createCell((int) 8);
		cellhead9.setCellValue("BL of current year sale");
		cellhead9.setCellStyle(cellStyle);
		

		XSSFCell cellhead10 = rowhead.createCell((int) 9);
		cellhead10.setCellValue("Revenue of current year sale");
		cellhead10.setCellStyle(cellStyle);
		
		XSSFCell cellhead11 = rowhead.createCell((int) 10);
		cellhead11.setCellValue("Cases of % increase");
		cellhead11.setCellStyle(cellStyle);
		
		XSSFCell cellhead12 = rowhead.createCell((int) 11);
		cellhead12.setCellValue("BL of % increase");
		cellhead12.setCellStyle(cellStyle);
		
		XSSFCell cellhead13 = rowhead.createCell((int) 12);
		cellhead13.setCellValue("Revenue of % increase");
		cellhead13.setCellStyle(cellStyle);
		
		
	/*	XSSFCell cellhead8 = rowhead.createCell((int) 7);
		cellhead8.setCellValue(action.getMonthName()+" "+"Month Actual");
		cellhead8.setCellStyle(cellStyle);
		
		XSSFCell cellhead9 = rowhead.createCell((int) 8);
		cellhead9.setCellValue(action.getMonthName()+" "+" Month %");
		cellhead9.setCellStyle(cellStyle);
		*/
		

		int i = 0;
		
		while (rs.next()) 
		{
			cases_of_last_year_sale = cases_of_last_year_sale + rs.getDouble("old_box");
			bl_of_last_year_sale = bl_of_last_year_sale + rs.getDouble("old_bl");
			
			revenue_of_last_year_sale = revenue_of_last_year_sale + rs.getDouble("old_duty");
			cases_of_current_year_sale = cases_of_current_year_sale + rs.getDouble("new_box");
		
			bl_of_current_year_sale = bl_of_current_year_sale + rs.getDouble("new_bl");
			revenue_of_current_year_sale = revenue_of_current_year_sale + rs.getDouble("new_duty");
			

			k++; //
			XSSFRow row1 = worksheet.createRow((int) k); //
			XSSFCell cellA1 = row1.createCell((int) 0); //
			cellA1.setCellValue(rs.getInt("serial_no")); //
			
			
			XSSFCell cellB1 = row1.createCell((int) 1);
			cellB1.setCellValue(rs.getString("description")); 
			
			XSSFCell cellC1 = row1.createCell((int) 2);
			cellC1.setCellValue(rs.getString("vch_name_of_shop"));
			
			XSSFCell cellD1 = row1.createCell((int) 3);
			cellD1.setCellValue(rs.getString("sector_name"));
			
			
			XSSFCell cellE1 = row1.createCell((int) 4);
			cellE1.setCellValue(rs.getDouble("old_box"));
			
			XSSFCell cellF1 = row1.createCell((int) 5);
			cellF1.setCellValue(rs.getDouble("old_bl"));
			
			XSSFCell cellG1 = row1.createCell((int) 6);
			cellG1.setCellValue(rs.getDouble("old_duty"));
			
			XSSFCell cellH1 = row1.createCell((int) 7);
			cellH1.setCellValue(rs.getDouble("new_box"));
			
			XSSFCell cellI1 = row1.createCell((int) 8);
			cellI1.setCellValue(rs.getDouble("new_bl"));
			
			XSSFCell cellJ1 = row1.createCell((int) 9);
			cellJ1.setCellValue(rs.getDouble("new_duty"));
			
			XSSFCell cellK1 = row1.createCell((int) 10);
			cellK1.setCellValue(rs.getDouble("per_box"));
			
			XSSFCell cellL1 = row1.createCell((int) 11);
			cellL1.setCellValue(rs.getDouble("per_bl"));
			
			XSSFCell cellM1 = row1.createCell((int) 12);
			cellM1.setCellValue(rs.getDouble("per_duty"));
			
	
		//System.out.println("------------ "+Math.round(cases_of_last_year_sale));

		}
		
		if(cases_of_last_year_sale>0){
		cases_box=((cases_of_current_year_sale/cases_of_last_year_sale)*100);
		}
		
		if(bl_of_last_year_sale>0){
			bl_of=((bl_of_current_year_sale/bl_of_last_year_sale)*100);
		}
		
		
		
		if(revenue_of_last_year_sale>0){
		revenue=((revenue_of_current_year_sale/revenue_of_last_year_sale)*100);
		}
		
		Random rand = new Random();
		int n = rand.nextInt(550) + 1;
		fileOut = new FileOutputStream(relativePath
				+ "//ExciseUp//MIS//Excel//"+action.getBwfl_id()+"" + n+ "_FL_Lifting_Comparision_shopwise.xls");

		action.setExlname(action.getBwfl_id()+""+n+"_FL_Lifting_Comparision_shopwise.xls");
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
		cellA4.setCellValue("Total:"); 
		cellA4.setCellStyle(cellStyle); 
		
		XSSFCell cellA5 = row1.createCell((int) 4);
		cellA5.setCellValue(cases_of_last_year_sale);
		cellA5.setCellStyle(cellStyle);
		
		XSSFCell cellA6 = row1.createCell((int) 5);
		cellA6.setCellValue(bl_of_last_year_sale);
		cellA6.setCellStyle(cellStyle);
		
		XSSFCell cellA7 = row1.createCell((int) 6);
		cellA7.setCellValue(revenue_of_last_year_sale); 
		cellA7.setCellStyle(cellStyle);
		
		XSSFCell cellA8 = row1.createCell((int) 7);
		cellA8.setCellValue(cases_of_current_year_sale);
		cellA8.setCellStyle(cellStyle);
		
		XSSFCell cellA9 = row1.createCell((int) 8);
		cellA9.setCellValue(bl_of_current_year_sale);
		cellA9.setCellStyle(cellStyle);
		
		
		XSSFCell cellA10 = row1.createCell((int) 9);
		cellA10.setCellValue(revenue_of_current_year_sale);
		cellA10.setCellStyle(cellStyle);
		
		XSSFCell cellA11 = row1.createCell((int) 10);	
		cellA11.setCellStyle(cellStyle);
		
		
		XSSFCell cellA12 = row1.createCell((int) 11);		
		cellA12.setCellStyle(cellStyle);
	
		XSSFCell cellA13 = row1.createCell((int) 12);		
		cellA13.setCellStyle(cellStyle);
	
	
	


		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		flag = true;
		action.setExcelFlag(true);
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

	return flag;

}

public boolean excelShopWise2020(LifitingComparisionFLAction action) throws ParseException {

	Connection con = null;

	
    double cases_of_last_year_sale = 0.0;
    double bl_of_last_year_sale = 0;
    double revenue_of_last_year_sale = 0;
    double cases_of_current_year_sale = 0;
    double bl_of_current_year_sale = 0;
    double revenue_of_current_year_sale = 0;
    double cases_box=0.0;
    double bl_of =0;
    double revenue=0;
    double new_revenue=0;
   
	 double corna_fee=0;
    double total_new_revenue_=0;
	
    DecimalFormat diciformatter = new DecimalFormat("#.####");
	
	String filterDistrict = "";
	 String filterSector="";
	 String filterShop="";
	
	// Date date11=new SimpleDateFormat("dd/MM/yyyy").parse(action.getDateSelected());
	 
	 //System.out.println("action.getDateSelected()==date11=="+date11);
	
	 if(!action.getDistid().equalsIgnoreCase("99") && !action.getDistid().equalsIgnoreCase("9999"))
		{
			filterDistrict=" and x.district_id="+ Integer.parseInt(action.getDistid())+ "";
			
			
			
			if(action.getSectorId().equalsIgnoreCase("0"))
			{
				filterSector ="";
				
				if(action.getShopId().equalsIgnoreCase("0"))
				{
					filterShop="";
				}
				else
				{
					filterShop=" and x.serial_no='"+action.getShopId()+"' ";
				}
			}
				
			else
			{
				filterSector = " and x.sector='"+action.getSectorId()+"' ";
				
				
				if(action.getShopId().equalsIgnoreCase("0"))
				{
					filterShop="";
				}
				else
				{
					filterShop=" and x.serial_no='"+action.getShopId()+"' ";
				}
				
				
			}
			
			
			
		}
	else
	{
	
		filterDistrict="";
		filterSector = "";
		filterShop="";
	}

	
	String reportQuery="";
	 if (action.getSelectedQuarter()==0){
			reportQuery="" +
					" select x.serial_no,(x.vch_name_of_shop||' - '||x.vch_shop_type) as vch_name_of_shop,(SELECT sector_name FROM public.mst_sector_master where sector_id= x.sector) as sector_name, " +
					" (select description from public.district d where d.districtid=x.district_id) as description, " +
					//" y.new_box,y.new_bl,y.new_duty,y.old_box,y.old_bl,y.old_duty " +
					" COALESCE(y.new_box,0)new_box,COALESCE(y.new_bl,0)new_bl,COALESCE(y.new_duty,0)new_duty,COALESCE(y.coronafee,0)coronafee," +
					" round(CAST(float8(COALESCE(y.old_box,0)) as numeric), 0)as old_box, "+
					//"COALESCE(y.old_box,0)old_box," +
					"COALESCE(y.old_bl,0)old_bl,COALESCE(y.old_duty,0)old_duty " +
					" ,case when COALESCE(y.old_box,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_box,0)/y.old_box)*100)) as numeric), 2) end as per_box " +
					" ,case when COALESCE(y.old_bl,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_bl,0)/y.old_bl)*100)) as numeric), 2)  end as per_bl " +
					" ,case when COALESCE(y.old_duty,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_duty+y.coronafee,0)/y.old_duty)*100)) as numeric), 2) end as per_duty  " +
					" from retail.retail_shop x left join  " +
					" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.coronafee)coronafee,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
					" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,sum(cr.coronafee) as coronafee, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
					 " select br.shop_id,sum(br.box)box,sum(br.bl) as bl,sum(br.duty) as duty,sum(br.coronafee) as coronafee " +
					" from  " +
					" ("
					+ "select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee , a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
					" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 ar " +
					" where ar.vch_to='RT' and ar.vch_from  in ('FL2B','FL2' )  and EXTRACT(MONTH FROM ar.dt_date)="+action.getSelectedMonth()+" )a " +
					" ,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b, distillery.packaging_details_20_21 d ,distillery.brand_registration_20_21 c  " +
					" where  a.vch_gatepass_no=b.vch_gatepass_no and d.package_id=b.int_pckg_id  and b.int_brand_id=c.brand_id  and c.license_category not in  ('BEER','LAB','IMPORTED BEER')  group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty,d.cesh union all "
					+ "select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee ,a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
					" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
					" where ar.vch_to='RT' and ar.vch_from='FL2' and ar.dt_date>='2020-05-01'  and EXTRACT(MONTH FROM ar.dt_date)="+action.getSelectedMonth()+" )a " +
					" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b, distillery.packaging_details_19_20  d " +
					" where  a.vch_gatepass_no=b.vch_gatepass_no and d.package_id=b.int_pckg_id  group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty,d.cesh  "
					+ ")br " +
					"   " +
					" group by br.shop_id   " +
					" )cr group by cr.shop_id " +
					" union all " +
					"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as coronafee,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
					" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.permit)*sum(br.bottle)) as duty from  " +
					" ("
					+ "select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
					" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
					" from fl2d.gatepass_to_districtwholesale_fl2d_20_21 ar where ar.vch_to='RT' and EXTRACT(MONTH FROM ar.dt_date)="+action.getSelectedMonth()+")a  " +
					" ,fl2d.fl2d_stock_trxn_20_21  b ,distillery.brand_registration_20_21 az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00  group by a.shop_id, b.int_pckg_id"
					+ " union all select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
					" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
					" from fl2d.gatepass_to_districtwholesale_fl2d_19_20  ar where ar.vch_to='RT' and ar.dt_date>='2020-05-01'  and EXTRACT(MONTH FROM ar.dt_date)="+action.getSelectedMonth()+")a  " +
					" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_19_20 az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00  group by a.shop_id, b.int_pckg_id"
							+ ") br, " +
					" distillery.packaging_details_20_21 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity,d.permit)cr " +
					" group by cr.shop_id " +
					" union all  " +
					" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, 0 as coronafee, " +
					" case when '1'='"+action.getSelectedMonth()+"' then sum(jan_19_box) when '2'='"+action.getSelectedMonth()+"' then sum(feb_19_box) when '3'='"+action.getSelectedMonth()+"' then sum(march_19_box) when '4'='"+action.getSelectedMonth()+"' then sum(april_18_box) when '5'='"+action.getSelectedMonth()+"' then sum(may_18_box) when '6'='"+action.getSelectedMonth()+"' then sum(june_18_box) when '7'='"+action.getSelectedMonth()+"' then sum(july_18_box) when '8'='"+action.getSelectedMonth()+"' then sum(august_18_box) when '9'='"+action.getSelectedMonth()+"' then sum(sept_18_box) when '10'='"+action.getSelectedMonth()+"' then sum(oct_18_box) when '11'='"+action.getSelectedMonth()+"' then sum(nov_18_box)  when '12'='"+action.getSelectedMonth()+"' then sum(dec_18_box) end as old_box " +
					" ,case when '1'='"+action.getSelectedMonth()+"' then sum(jan_19_bl) when '2'='"+action.getSelectedMonth()+"' then sum(feb_19_bl) when '3'='"+action.getSelectedMonth()+"' then sum(march_19_bl) when '4'='"+action.getSelectedMonth()+"' then sum(april_18_bl) when '5'='"+action.getSelectedMonth()+"' then sum(may_18_bl) when '6'='"+action.getSelectedMonth()+"' then sum(june_18_bl) when '7'='"+action.getSelectedMonth()+"' then sum(july_18_bl) when '8'='"+action.getSelectedMonth()+"' then sum(august_18_bl) when '9'='"+action.getSelectedMonth()+"' then sum(sept_18_bl) when '10'='"+action.getSelectedMonth()+"' then sum(oct_18_bl) when '11'='"+action.getSelectedMonth()+"' then sum(nov_18_bl)  when '12'='"+action.getSelectedMonth()+"' then sum(dec_18_bl) end as old_bl   " +
					" ,case when '1'='"+action.getSelectedMonth()+"' then sum(jan_19_duty) when '2'='"+action.getSelectedMonth()+"' then sum(feb_19_duty) when '3'='"+action.getSelectedMonth()+"' then sum(march_19_duty) when '4'='"+action.getSelectedMonth()+"' then sum(april_18_duty) when '5'='"+action.getSelectedMonth()+"' then sum(may_18_duty) when '6'='"+action.getSelectedMonth()+"' then sum(june_18_duty) when '7'='"+action.getSelectedMonth()+"' then sum(july_18_duty) when '8'='"+action.getSelectedMonth()+"' then sum(august_18_duty) when '9'='"+action.getSelectedMonth()+"' then sum(sept_18_duty) when '10'='"+action.getSelectedMonth()+"' then sum(oct_18_duty) when '11'='"+action.getSelectedMonth()+"' then sum(nov_18_duty)  when '12'='"+action.getSelectedMonth()+"' then sum(dec_18_duty) end as old_duty " +
					" from retail.retail_shop_lifting_duty_19_20 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
					" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
					" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  "+filterDistrict +filterSector +filterShop+"  order by  x.serial_no,description,x.vch_name_of_shop  ";
				

				 }
				 
				 else{
			reportQuery="" +
					"  " +
					" select x.serial_no,(x.vch_name_of_shop||' - '||x.vch_shop_type) as vch_name_of_shop,(SELECT sector_name FROM public.mst_sector_master where sector_id= x.sector) as sector_name, " +
					" (select description from public.district d where d.districtid=x.district_id) as description, " +
					//" y.new_box,y.new_bl,y.new_duty,y.old_box,y.old_bl,y.old_duty " +
					" COALESCE(y.new_box,0)new_box,COALESCE(y.new_bl,0)new_bl,COALESCE(y.new_duty,0)new_duty,COALESCE(y.coronafee,0)coronafee," +
					" round(CAST(float8(COALESCE(y.old_box,0)) as numeric), 0)as old_box, "+
					//"COALESCE(y.old_box,0)old_box," +
					"COALESCE(y.old_bl,0)old_bl,COALESCE(y.old_duty,0)old_duty " +
					" ,case when COALESCE(y.old_box,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_box,0)/y.old_box)*100)) as numeric), 2) end as per_box " +
					" ,case when COALESCE(y.old_bl,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_bl,0)/y.old_bl)*100)) as numeric), 2)  end as per_bl " +
					" ,case when COALESCE(y.old_duty,0)=0 then 0 else round(CAST(float8(((COALESCE(y.new_duty+y.coronafee,0)/y.old_duty)*100)) as numeric), 2) end as per_duty  " +
					" from retail.retail_shop x left join  " +
					" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.coronafee)coronafee,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
					" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,sum(cr.coronafee) as coronafee, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
					" select br.shop_id,sum(br.box)box,sum(br.bl) as bl,sum(br.duty) as duty,sum(br.coronafee) as coronafee " +
					" from  " +
					" ("
					+ "select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee ,a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
					" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 ar " +
					" where ar.vch_to='RT' and ar.vch_from  in ('FL2B','FL2' )  and " +
					" case when 4="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
					" when 1="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
					" when 2="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
					" when 3="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end )a " +
					" ,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b , distillery.packaging_details_20_21 d  ,distillery.brand_registration_20_21 c  " +
					" where  a.vch_gatepass_no=b.vch_gatepass_no and d.package_id=b.int_pckg_id  and b.int_brand_id=c.brand_id  and c.license_category not in  ('BEER','LAB','IMPORTED BEER')  group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty ,d.cesh"
					+ " union all select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee ,a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
					" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
					" where ar.vch_to='RT' and ar.vch_from='FL2' and ar.dt_date>='2020-05-01' and " +
					" case when 4="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
					" when 1="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
					" when 2="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
					" when 3="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end )a " +
					" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b , distillery.packaging_details_19_20 d " +
					" where a.dt_date>='2020-05-01' and d.package_id=b.int_pckg_id and    a.vch_gatepass_no=b.vch_gatepass_no group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty ,d.cesh"
					+ ")br " +
					"   " +
					" group by br.shop_id " +
					" )cr group by cr.shop_id " +
					" union all " +
					"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as coronafee,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
					" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,sum(br.duty) as duty from  " +
					" (select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
					" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
					" from fl2d.gatepass_to_districtwholesale_fl2d_20_21 ar where ar.vch_to='RT' and ar.dt_date>='2020-05-01' and  " +
					" case when 4="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
					" when 1="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
					" when 2="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
					" when 3="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
					" ,fl2d.fl2d_stock_trxn_20_21  b ,distillery.brand_registration_20_21 az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.license_category not in  ('BEER','LAB','IMPORTED BEER')  group by a.shop_id, b.int_pckg_id) br, " +
					" distillery.packaging_details_20_21 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity)cr " +
					" group by cr.shop_id " +
					" union all  " +
					" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, 0 as coronafee," +
					" case when '1'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(april_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(may_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(june_18_box,0)) as numeric), 0)) when '2'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(july_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(august_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(sept_18_box ,0)) as numeric), 0)) when '3'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(oct_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(nov_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(dec_18_box ,0)) as numeric), 0)) when '4'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(jan_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(feb_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(march_19_box ,0)) as numeric), 0)) end as old_box " +
					" ,case when '1'='"+action.getSelectedQuarter()+"' then sum(april_18_bl + may_18_bl + june_18_bl) when '2'='"+action.getSelectedQuarter()+"' then sum(july_18_bl+ august_18_bl + sept_18_bl) when '3'='"+action.getSelectedQuarter()+"' then sum(oct_18_bl + nov_18_bl + dec_18_bl) when '4'='"+action.getSelectedQuarter()+"' then sum(jan_19_bl + feb_19_bl+ march_19_bl) end as old_bl "+
					" ,case when '1'='"+action.getSelectedQuarter()+"' then sum(april_18_duty + may_18_duty + june_18_duty) when '2'='"+action.getSelectedQuarter()+"' then sum(july_18_duty+ august_18_duty + sept_18_duty) when '3'='"+action.getSelectedQuarter()+"' then sum(oct_18_duty + nov_18_duty + dec_18_duty) when '4'='"+action.getSelectedQuarter()+"' then sum(jan_19_duty + feb_19_duty+ march_19_duty) end as old_duty " +
					" from retail.retail_shop_lifting_duty_19_20 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
					" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
					" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  "+filterDistrict +filterSector +filterShop+"  order by  x.serial_no,description,x.vch_name_of_shop  ";
				 
				 
						 
				 }
	 
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
				.createSheet("FL Lifting Comparision Report");

		worksheet.setColumnWidth(0, 3000);
		worksheet.setColumnWidth(1, 5000);
		worksheet.setColumnWidth(2, 5000);
		worksheet.setColumnWidth(3, 5000);
		worksheet.setColumnWidth(4, 6000);
		worksheet.setColumnWidth(5, 6000);
		worksheet.setColumnWidth(6, 6000);
		worksheet.setColumnWidth(7, 6000);
		worksheet.setColumnWidth(8, 6000);
		worksheet.setColumnWidth(9, 6000);
		worksheet.setColumnWidth(10, 6000);
		worksheet.setColumnWidth(11, 9000);
		worksheet.setColumnWidth(12, 9000);
		worksheet.setColumnWidth(13, 5000);
		worksheet.setColumnWidth(14, 6000);
		worksheet.setColumnWidth(15, 6000);

     
		XSSFRow rowhead0 = worksheet.createRow((int) 0);
		XSSFCell cellhead0 = rowhead0.createCell((int) 0);
		if(action.getSelectedMonth()>0){
		cellhead0.setCellValue("FL Lifting Comparision ShopWise" + "-" +action.getMonthName());
		}
		
		if(action.getSelectedQuarter()>0){
			
			cellhead0.setCellValue("FL Lifting Comparision ShopWise" + " " + "Quarter- " + action.getSelectedQuarter());
			
			}
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
		cellhead1.setCellValue("S.No");
		cellhead1.setCellStyle(cellStyle);
		
		XSSFCell cellhead2 = rowhead.createCell((int) 1);
		cellhead2.setCellValue("Description");
		cellhead2.setCellStyle(cellStyle);

		
		XSSFCell cellhead3 = rowhead.createCell((int) 2);
		cellhead3.setCellValue("Shop Id");
		cellhead3.setCellStyle(cellStyle);

		XSSFCell cellhead4 = rowhead.createCell((int) 3);
		cellhead4.setCellValue("Shop Name");
		cellhead4.setCellStyle(cellStyle);
		
		XSSFCell cellhead5 = rowhead.createCell((int) 4);
		cellhead5.setCellValue("Sector");
		cellhead5.setCellStyle(cellStyle);

		XSSFCell cellhead6 = rowhead.createCell((int) 5);
		cellhead6.setCellValue("Cases of Last year sale");
		cellhead6.setCellStyle(cellStyle);
		
		XSSFCell cellhead7 = rowhead.createCell((int) 6);
		cellhead7.setCellValue("BL of Last year sale");
		cellhead7.setCellStyle(cellStyle);
		

		XSSFCell cellhead8 = rowhead.createCell((int) 7);
		cellhead8.setCellValue("Revenue of Last year sale");
		cellhead8.setCellStyle(cellStyle);
		

		XSSFCell cellhead9 = rowhead.createCell((int) 8);
		cellhead9.setCellValue("Cases of current year sale");
		cellhead9.setCellStyle(cellStyle);
		
		XSSFCell cellhead10 = rowhead.createCell((int) 9);
		cellhead10.setCellValue("BL of current year sale");
		cellhead10.setCellStyle(cellStyle);
		

		XSSFCell cellhead11 = rowhead.createCell((int) 10);
		cellhead11.setCellValue("Duty of current year sale");
		cellhead11.setCellStyle(cellStyle);
		
		XSSFCell cellhead12 = rowhead.createCell((int) 11);
		cellhead12.setCellValue("Add Consid.. Fee of current year sale");
		cellhead12.setCellStyle(cellStyle);
		
		XSSFCell cellhead13 = rowhead.createCell((int) 12);
		cellhead13.setCellValue("Revenue of current year sale");
		cellhead13.setCellStyle(cellStyle);
				
		XSSFCell cellhead14 = rowhead.createCell((int) 13);
		cellhead14.setCellValue("Cases of % increase");
		cellhead14.setCellStyle(cellStyle);
		
		XSSFCell cellhead15 = rowhead.createCell((int) 14);
		cellhead15.setCellValue("BL of % increase");
		cellhead15.setCellStyle(cellStyle);
		
		XSSFCell cellhead16 = rowhead.createCell((int) 15);
		cellhead16.setCellValue("Revenue of % increase");
		cellhead16.setCellStyle(cellStyle);
		
		
	/*	XSSFCell cellhead8 = rowhead.createCell((int) 7);
		cellhead8.setCellValue(action.getMonthName()+" "+"Month Actual");
		cellhead8.setCellStyle(cellStyle);
		
		XSSFCell cellhead9 = rowhead.createCell((int) 8);
		cellhead9.setCellValue(action.getMonthName()+" "+" Month %");
		cellhead9.setCellStyle(cellStyle);
		*/
		

		int i = 0;
		
		while (rs.next()) 
		{
			cases_of_last_year_sale = cases_of_last_year_sale + rs.getDouble("old_box");
			bl_of_last_year_sale = bl_of_last_year_sale + rs.getDouble("old_bl");
			
			revenue_of_last_year_sale = revenue_of_last_year_sale + rs.getDouble("old_duty");
			cases_of_current_year_sale = cases_of_current_year_sale + rs.getDouble("new_box");
		
			bl_of_current_year_sale = bl_of_current_year_sale + rs.getDouble("new_bl");
			revenue_of_current_year_sale = revenue_of_current_year_sale + rs.getDouble("new_duty");
			
			new_revenue = rs.getDouble("new_duty")+rs.getDouble("coronafee"); 
			
			 corna_fee= corna_fee +rs.getDouble("coronafee");
			 total_new_revenue_=  revenue_of_current_year_sale + corna_fee ;
			i++;
			k++; //
			XSSFRow row1 = worksheet.createRow((int) k); //
			XSSFCell cellA1 = row1.createCell((int) 0); //
			cellA1.setCellValue(k - 1);
			//cellA1.setCellValue(rs.getInt("serial_no")); //
			
			
			XSSFCell cellB1 = row1.createCell((int) 1);
			cellB1.setCellValue(rs.getString("description")); 
			
			XSSFCell cellC1 = row1.createCell((int) 2);
			cellC1.setCellValue(rs.getString("serial_no"));
			
			XSSFCell cellD1 = row1.createCell((int) 3);
			cellD1.setCellValue(rs.getString("vch_name_of_shop"));
			
			XSSFCell cellE1 = row1.createCell((int) 4);
			cellE1.setCellValue(rs.getString("sector_name"));
			
			
			XSSFCell cellF1 = row1.createCell((int) 5);
			cellF1.setCellValue(rs.getDouble("old_box"));
			
			XSSFCell cellG1 = row1.createCell((int) 6);
			cellG1.setCellValue(rs.getDouble("old_bl"));
			
			XSSFCell cellH1 = row1.createCell((int) 7);
			cellH1.setCellValue(rs.getDouble("old_duty"));
			
			XSSFCell cellI1 = row1.createCell((int) 8);
			cellI1.setCellValue(rs.getDouble("new_box"));
			
			XSSFCell cellJ1 = row1.createCell((int) 9);
			cellJ1.setCellValue(rs.getDouble("new_bl"));
			
			XSSFCell cellK1 = row1.createCell((int) 10);
			cellK1.setCellValue(rs.getDouble("new_duty"));
			
			XSSFCell cellL1 = row1.createCell((int) 11);
			cellL1.setCellValue(rs.getDouble("coronafee"));
			
			XSSFCell cellM1 = row1.createCell((int) 12);
			cellM1.setCellValue(new_revenue);
			
			XSSFCell cellN1 = row1.createCell((int) 13);
			cellN1.setCellValue(rs.getDouble("per_box"));
			
			XSSFCell cellO1 = row1.createCell((int) 14);
			cellO1.setCellValue(rs.getDouble("per_bl"));
			
			XSSFCell cellP1 = row1.createCell((int) 15);
			cellP1.setCellValue(rs.getDouble("per_duty"));
			
		//System.out.println("------------ "+Math.round(cases_of_last_year_sale));

		}
		
		if(cases_of_last_year_sale>0){
		cases_box=((cases_of_current_year_sale/cases_of_last_year_sale)*100);
		}
		
		if(bl_of_last_year_sale>0){
			bl_of=((bl_of_current_year_sale/bl_of_last_year_sale)*100);
		}
		
		
		
		if(revenue_of_last_year_sale>0){
		revenue=((revenue_of_current_year_sale/revenue_of_last_year_sale)*100);
		}
		
		Random rand = new Random();
		int n = rand.nextInt(550) + 1;
		fileOut = new FileOutputStream(relativePath
				+ "//ExciseUp//MIS//Excel//"+action.getBwfl_id()+"" + n+ "_FL_Lifting_Comparision_shopwise_2020.xls");

		action.setExlname(action.getBwfl_id()+""+n+"_FL_Lifting_Comparision_shopwise_2020.xls");
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
		cellA5.setCellValue("Total:"); 
		cellA5.setCellStyle(cellStyle); 
		
		XSSFCell cellA6 = row1.createCell((int) 5);
		cellA6.setCellValue(cases_of_last_year_sale);
		cellA6.setCellStyle(cellStyle);
		
		XSSFCell cellA7 = row1.createCell((int) 6);
		cellA7.setCellValue(bl_of_last_year_sale);
		cellA7.setCellStyle(cellStyle);
		
		XSSFCell cellA8 = row1.createCell((int) 7);
		cellA8.setCellValue(revenue_of_last_year_sale); 
		cellA8.setCellStyle(cellStyle);
		
		XSSFCell cellA9 = row1.createCell((int) 8);
		cellA9.setCellValue(cases_of_current_year_sale);
		cellA9.setCellStyle(cellStyle);
		
		XSSFCell cellA10 = row1.createCell((int) 9);
		cellA10.setCellValue(bl_of_current_year_sale);
		cellA10.setCellStyle(cellStyle);
		
		
		XSSFCell cellA11 = row1.createCell((int) 10);
		cellA11.setCellValue(revenue_of_current_year_sale);
		cellA11.setCellStyle(cellStyle);
		
		XSSFCell cellA12 = row1.createCell((int) 11);	
		cellA12.setCellValue(corna_fee);
		cellA12.setCellStyle(cellStyle);
		
		
		XSSFCell cellA13 = row1.createCell((int) 12);
		cellA13.setCellValue(total_new_revenue_);
		cellA13.setCellStyle(cellStyle);
	
		XSSFCell cellA14 = row1.createCell((int) 13);		
		cellA14.setCellStyle(cellStyle);
	
		XSSFCell cellA15 = row1.createCell((int) 14);		
		cellA15.setCellStyle(cellStyle);
	
		XSSFCell cellA16 = row1.createCell((int) 15);		
		cellA16.setCellStyle(cellStyle);
	
	


		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		flag = true;
		action.setExcelFlag(true);
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

	return flag;

}

public ArrayList getShop(LifitingComparisionFLAction act, String o) {

	ArrayList list = new ArrayList();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String filterSector="";

	SelectItem item = new SelectItem();
	item.setLabel("--ALL--");
	item.setValue("0");
	list.add(item);
	try {
		
		
		if(o.equalsIgnoreCase("0"))
		{
			filterSector="";
		}else
		{
			filterSector=" and sector="+Integer.parseInt(o)+" ";	
		}
		
		
		
		
		String query = " select serial_no ,vch_name_of_shop from retail.retail_shop " +
				" where       district_id="+Integer.parseInt(act.getDistid())+" " +
						//"and   vch_shop_type='Country Liquor'" +
				filterSector +" order by trim(vch_name_of_shop)";
		
		
		
		
		

		conn = ConnectionToDataBase.getConnection();
		pstmt = conn.prepareStatement(query);

		
		System.out.println("shop list with sector by sector===="+query);
		
		
		rs = pstmt.executeQuery();

		while (rs.next()) {
			item = new SelectItem();

			item.setLabel(rs.getString("vch_name_of_shop") + " - "
					+ rs.getString("serial_no"));
			item.setValue(rs.getString("serial_no"));
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


public ArrayList getShopL(LifitingComparisionFLAction act, String o) {

	ArrayList list = new ArrayList();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String filterSector="";

	SelectItem item = new SelectItem();
	item.setLabel("--ALL--");
	item.setValue("0");
	list.add(item);
	try {
		
		
		
		
		
		
		String query = " select serial_no ,vch_name_of_shop from retail.retail_shop " +
				" where       district_id="+Integer.parseInt(o)+" " +
						"and vch_shop_type in ('Foreign Liquor','Model Shop')  " +
						"order by trim(vch_name_of_shop)";
		
		
		System.out.println();
		
		

		conn = ConnectionToDataBase.getConnection();
		pstmt = conn.prepareStatement(query);

		
		//System.out.println("shop list without sector by sector===="+query);
		
		
		rs = pstmt.executeQuery();

		while (rs.next()) {
			item = new SelectItem();

			item.setLabel(rs.getString("vch_name_of_shop") + " - "
					+ rs.getString("serial_no"));
			item.setValue(rs.getString("serial_no"));
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

public ArrayList getSectorList(LifitingComparisionFLAction action, String districtid)
{
	ArrayList list = new ArrayList();
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	SelectItem item = new SelectItem();
	item.setLabel("-Select-");
	item.setValue(0);
	list.add(item);
	try {
		String query = "SELECT sector_id, sector_name  " +
				" FROM public.mst_sector_master   order by sector_name  ";

		
		con = ConnectionToDataBase.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		while (rs.next()) {
			item = new SelectItem();
			item.setValue((rs.getInt("sector_id")));
			item.setLabel(rs.getString("sector_name"));
			list.add(item);
		}
	//	action.setDistid(null);
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {

			if (con != null)
				con.close();
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

public ArrayList getDistList() {

	ArrayList list = new ArrayList();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	SelectItem item = new SelectItem();
	item.setLabel("--ALL--");
	item.setValue(9999);
	list.add(item);
	try {
		String query = " SELECT DistrictID, Description FROM district order by Description ";

		conn = ConnectionToDataBase.getConnection();
		pstmt = conn.prepareStatement(query);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			item = new SelectItem();

			item.setValue(rs.getString("DistrictID"));
			item.setLabel(rs.getString("Description"));

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
public ArrayList selectedMonth()
{

	ArrayList list = new ArrayList();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	SelectItem item = new SelectItem();
	item.setLabel("--select--");
	item.setValue(0);
	list.add(item);
	try {
		String query = " SELECT month_id, description FROM public.month_master order by month_id ";

		conn = ConnectionToDataBase.getConnection();
		pstmt = conn.prepareStatement(query);

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


public ArrayList selectedQuarter()
{

	ArrayList list = new ArrayList();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	SelectItem item = new SelectItem();
	item.setLabel("--select--");
	item.setValue(0);
	list.add(item);
	try {
		String query = " SELECT month_id, description FROM public.month_master order by month_id ";

		conn = ConnectionToDataBase.getConnection();
		pstmt = conn.prepareStatement(query);

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


public void printReportDistrictWise(LifitingComparisionFLAction act) {
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
	String filterDistrict = "";
	 String filterSector="";
	 String filterShop="";


	try {
		con = ConnectionToDataBase.getConnection();
 
		 
		if(act.getSelectedQuarter()==0)
		{
	reportQuery="" +
			"  " +
			" select  (select description from public.district d where d.districtid=x.district_id) as description, " +
			" sum(COALESCE(y.new_box,0))new_box,round(CAST(float8(sum(COALESCE(y.new_bl,0))) as numeric), 2) as new_bl, " +
			" round(CAST(float8(sum(COALESCE(y.new_duty,0))) as numeric), 2) as new_duty, " +
			" sum(COALESCE(y.old_box,0))old_box,sum(COALESCE(y.old_bl,0))old_bl,sum(COALESCE(y.old_duty,0))old_duty " +
			" ,case when sum(COALESCE(y.old_box,0))=0 then 0 else round(CAST(float8(((sum(COALESCE(y.new_box,0))/sum(COALESCE(y.old_box,0)))*100)) as numeric), 2) end as per_box " +
			" ,case when sum(COALESCE(y.old_bl,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE(y.new_bl,0))/sum(COALESCE(y.old_bl,0)))*100)) as numeric), 2)  end as per_bl " +
			" ,case when sum(COALESCE(y.old_duty,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE(y.new_duty,0))/sum(COALESCE(y.old_duty,0)))*100)) as numeric), 2) end as per_duty  " +
			" from retail.retail_shop x left join  " +
			" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
			" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
			" select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(br.bottle)) as duty " +
			" from  " +
			" (select a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
			" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
			" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
			" where ar.vch_to='RT' and ar.vch_from='FL2' and ar.dt_date<'2020-05-01' and EXTRACT(MONTH FROM ar.dt_date)="+act.getSelectedMonth()+")a " +
			" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b " +
			" where a.vch_gatepass_no=b.vch_gatepass_no group by a.shop_id,b.int_pckg_id)br, distillery.packaging_details_19_20 d " +
			" where d.package_id=br.int_pckg_id " +
			" group by br.shop_id,d.quantity,d.duty,d.adduty " +
			" )cr group by cr.shop_id " +
			" union all " +
			"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
			" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,sum(br.duty) as duty from  " +
			" (select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
			" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
			" from fl2d.gatepass_to_districtwholesale_fl2d_19_20 ar where ar.vch_to='RT' and ar.dt_date<'2020-05-01' and EXTRACT(MONTH FROM ar.dt_date)="+act.getSelectedMonth()+")a  " +
			" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_19_20  az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00 group by a.shop_id, b.int_pckg_id) br, " +
			" distillery.packaging_details_19_20 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity)cr " +
			" group by cr.shop_id " +
			" union all  " +
			" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, " +
			" case when '1'='"+act.getSelectedMonth()+"' then sum(jan_19_box) when '2'='"+act.getSelectedMonth()+"' then sum(feb_19_box) when '3'='"+act.getSelectedMonth()+"' then sum(march_19_box) when '4'='"+act.getSelectedMonth()+"' then sum(april_18_box) when '5'='"+act.getSelectedMonth()+"' then sum(may_18_box) when '6'='"+act.getSelectedMonth()+"' then sum(june_18_box) when '7'='"+act.getSelectedMonth()+"' then sum(july_18_box) when '8'='"+act.getSelectedMonth()+"' then sum(august_18_box) when '9'='"+act.getSelectedMonth()+"' then sum(sept_18_box) when '10'='"+act.getSelectedMonth()+"' then sum(oct_18_box) when '11'='"+act.getSelectedMonth()+"' then sum(nov_18_box)  when '12'='"+act.getSelectedMonth()+"' then sum(dec_18_box) end as old_box " +
			" ,case when '1'='"+act.getSelectedMonth()+"' then sum(jan_19_bl) when '2'='"+act.getSelectedMonth()+"' then sum(feb_19_bl) when '3'='"+act.getSelectedMonth()+"' then sum(march_19_bl) when '4'='"+act.getSelectedMonth()+"' then sum(april_18_bl) when '5'='"+act.getSelectedMonth()+"' then sum(may_18_bl) when '6'='"+act.getSelectedMonth()+"' then sum(june_18_bl) when '7'='"+act.getSelectedMonth()+"' then sum(july_18_bl) when '8'='"+act.getSelectedMonth()+"' then sum(august_18_bl) when '9'='"+act.getSelectedMonth()+"' then sum(sept_18_bl) when '10'='"+act.getSelectedMonth()+"' then sum(oct_18_bl) when '11'='"+act.getSelectedMonth()+"' then sum(nov_18_bl)  when '12'='"+act.getSelectedMonth()+"' then sum(dec_18_bl) end as old_bl   " +
			" ,case when '1'='"+act.getSelectedMonth()+"' then sum(jan_19_duty) when '2'='"+act.getSelectedMonth()+"' then sum(feb_19_duty) when '3'='"+act.getSelectedMonth()+"' then sum(march_19_duty) when '4'='"+act.getSelectedMonth()+"' then sum(april_18_duty) when '5'='"+act.getSelectedMonth()+"' then sum(may_18_duty) when '6'='"+act.getSelectedMonth()+"' then sum(june_18_duty) when '7'='"+act.getSelectedMonth()+"' then sum(july_18_duty) when '8'='"+act.getSelectedMonth()+"' then sum(august_18_duty) when '9'='"+act.getSelectedMonth()+"' then sum(sept_18_duty) when '10'='"+act.getSelectedMonth()+"' then sum(oct_18_duty) when '11'='"+act.getSelectedMonth()+"' then sum(nov_18_duty)  when '12'='"+act.getSelectedMonth()+"' then sum(dec_18_duty) end as old_duty " +
			" from retail.retail_shop_lifting_duty_18_19 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
			" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
			" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  group by x.district_id order by description ";
	//System.out.println("districtwise="+reportQuery);
		pst = con.prepareStatement(reportQuery);
		rs = pst.executeQuery();

		}
		
		else{
			//System.out.println(" Entered in eLSE ===============");
			reportQuery="" +
					"  " +
					" select  (select description from public.district d where d.districtid=x.district_id) as description, " +
					" sum(COALESCE(y.new_box,0))new_box,round(CAST(float8(sum(COALESCE(y.new_bl,0))) as numeric), 2) as new_bl, " +
					" round(CAST(float8(sum(COALESCE(y.new_duty,0))) as numeric), 2) as new_duty, " +
					" sum(COALESCE(y.old_box,0))old_box,sum(COALESCE(y.old_bl,0))old_bl,sum(COALESCE(y.old_duty,0))old_duty " +
					" ,case when sum(COALESCE(y.old_box,0))=0 then 0 else round(CAST(float8(((sum(COALESCE(y.new_box,0))/sum(COALESCE(y.old_box,0)))*100)) as numeric), 2) end as per_box " +
					" ,case when sum(COALESCE(y.old_bl,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE(y.new_bl,0))/sum(COALESCE(y.old_bl,0)))*100)) as numeric), 2)  end as per_bl " +
					" ,case when sum(COALESCE(y.old_duty,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE(y.new_duty,0))/sum(COALESCE(y.old_duty,0)))*100)) as numeric), 2) end as per_duty  " +
					" from retail.retail_shop x left join  " +
					" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
					" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
					" select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(br.bottle)) as duty " +
					" from  " +
					" (select a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
					" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
					" where ar.vch_to='RT'  and ar.dt_date<'2020-05-01'  and ar.vch_from='FL2' and " +
					" case when 4="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
					" when 1="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
					" when 2="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
					" when 3="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
					" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b " +
					" where a.vch_gatepass_no=b.vch_gatepass_no group by a.shop_id,b.int_pckg_id)br, distillery.packaging_details_19_20 d " +
					" where d.package_id=br.int_pckg_id " +
					" group by br.shop_id,d.quantity,d.duty,d.adduty " +
					" )cr group by cr.shop_id " +
					" union all " +
					"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
					" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,sum(br.duty) as duty from  " +
					" (select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
					" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
					" from fl2d.gatepass_to_districtwholesale_fl2d_19_20 ar where ar.vch_to='RT'  and ar.dt_date<'2020-05-01'  and " +
					" case when 4="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
					" when 1="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
					" when 2="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
					" when 3="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
					" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_19_20  az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00  group by a.shop_id, b.int_pckg_id) br, " +
					" distillery.packaging_details_19_20 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity)cr " +
					" group by cr.shop_id " +
					" union all  " +
					" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, " +
					" case when '1'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(april_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(may_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(june_18_box,0)) as numeric), 0)) when '2'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(july_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(august_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(sept_18_box ,0)) as numeric), 0)) when '3'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(oct_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(nov_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(dec_18_box ,0)) as numeric), 0)) when '4'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(jan_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(feb_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(march_19_box ,0)) as numeric), 0)) end as old_box " +
					" ,case when '1'='"+act.getSelectedQuarter()+"' then sum(april_18_bl + may_18_bl + june_18_bl) when '2'='"+act.getSelectedQuarter()+"' then sum(july_18_bl+ august_18_bl + sept_18_bl) when '3'='"+act.getSelectedQuarter()+"' then sum(oct_18_bl + nov_18_bl + dec_18_bl) when '4'='"+act.getSelectedQuarter()+"' then sum(jan_19_bl + feb_19_bl+ march_19_bl) end as old_bl "+
					" ,case when '1'='"+act.getSelectedQuarter()+"' then sum(april_18_duty + may_18_duty + june_18_duty) when '2'='"+act.getSelectedQuarter()+"' then sum(july_18_duty+ august_18_duty + sept_18_duty) when '3'='"+act.getSelectedQuarter()+"' then sum(oct_18_duty + nov_18_duty + dec_18_duty) when '4'='"+act.getSelectedQuarter()+"' then sum(jan_19_duty + feb_19_duty+ march_19_duty) end as old_duty " +
					" from retail.retail_shop_lifting_duty_18_19 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
					" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
					" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  group by x.district_id order by description ";
		 //System.out.println("District Wise==== " +reportQuery);
		
		}
		
		pst = con.prepareStatement(reportQuery);
		rs = pst.executeQuery();
		if (rs.next()) {

			rs = pst.executeQuery();
			Map parameters = new HashMap();
			parameters.put("REPORT_CONNECTION", con);
			parameters.put("SUBREPORT_DIR", relativePath + File.separator);
			parameters.put("image", relativePath + File.separator);
			parameters.put("radioType", act.getRadio());
			if(act.getSelectedMonth()>0) {
				parameters.put("monthName1", act.getMonthName());
				}
				if(act.getSelectedQuarter()>0) {
					parameters.put("monthName1","Quarter- " + act.getSelectedQuarter());
				}
			if(act.getSelectedMonth()>0) {
				if(act.getSelectedMonth()==1 ||act.getSelectedMonth()==2 ||act.getSelectedMonth()==3||act.getSelectedMonth()==4) {
					parameters.put("monthName", act.getMonthName()+"-2020");
				}else {
					parameters.put("monthName", act.getMonthName()+"-2019");
				}
			}
			else {
			if(act.getSelectedQuarter()==1 ||act.getSelectedQuarter()==2 ||act.getSelectedQuarter()==3 ) {
				parameters.put("monthName","Quarter- " +  act.getSelectedQuarter()+"-2019");
			}else {
				parameters.put("monthName", "Quarter- " + act.getSelectedQuarter()+"-2020");
			}

			 	 
			}
			JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

			jasperReport = (JasperReport) JRLoader.loadObject(relativePath
					+ File.separator + "fl_lifting_comparision_districtwise.jasper");

			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, jrRs);
			Random rand = new Random();
			int n = rand.nextInt(250) + 1;
			JasperExportManager.exportReportToPdfFile(print,
					relativePathpdf + File.separator
							+ "fl_lifting_comparision_districtwise_"+act.getBwfl_id()+"" + "-" + n + ".pdf");
			act.setPdfName("fl_lifting_comparision_districtwise_"+act.getBwfl_id()+"" + "-" + n + ".pdf");
			act.setPrintFlag(true);
		} else {
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

public void printReportDistrictWise2020(LifitingComparisionFLAction act) {
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
	String filterDistrict = "";
	 String filterSector="";
	 String filterShop="";


	try {
		con = ConnectionToDataBase.getConnection();
 
		 
		if(act.getSelectedQuarter()==0)
		{
	reportQuery="" +
			"  " +
			" select  (select description from public.district d where d.districtid=x.district_id) as description, " +
			" sum(COALESCE(y.new_box,0))new_box,round(CAST(float8(sum(COALESCE(y.new_bl,0))) as numeric), 2) as new_bl, " +
			" round(CAST(float8(sum(COALESCE(y.new_duty,0))) as numeric), 2) as new_duty, round(CAST(float8(sum(COALESCE(y.coronafee,0))) as numeric), 2) as coronafee, " +
			" sum(COALESCE(y.old_box,0))old_box,sum(COALESCE(y.old_bl,0))old_bl,sum(COALESCE(y.old_duty,0))old_duty " +
			" ,case when sum(COALESCE(y.old_box,0))=0 then 0 else round(CAST(float8(((sum(COALESCE(y.new_box,0))/sum(COALESCE(y.old_box,0)))*100)) as numeric), 2) end as per_box " +
			" ,case when sum(COALESCE(y.old_bl,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE(y.new_bl,0))/sum(COALESCE(y.old_bl,0)))*100)) as numeric), 2)  end as per_bl " +
			" ,case when sum(COALESCE(y.old_duty,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE((y.new_duty+y.coronafee),0))/sum(COALESCE(y.old_duty,0)))*100)) as numeric), 2) end as per_duty  " +
			" from retail.retail_shop x left join  " +
			" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.coronafee)coronafee,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
			" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,sum(cr.coronafee) as coronafee, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
			" select br.shop_id,sum(br.box)box,sum(br.bl) as bl,sum(br.duty) as duty,sum(br.coronafee) as coronafee" +
			" from  " +
			" ("
			+ "select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee ,a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
			" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
			" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 ar " +
			" where ar.vch_to='RT' and ar.vch_from='FL2' and EXTRACT(MONTH FROM ar.dt_date)="+act.getSelectedMonth()+")a " +
			" ,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b , distillery.packaging_details_20_21 d, distillery.brand_registration_19_20 c " +
			" where a.vch_gatepass_no=b.vch_gatepass_no and b.int_brand_id=c.brand_id and c.license_category not in  ('BEER','LAB','IMPORTED BEER') and d.package_id=b.int_pckg_id group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty ,d.cesh union all "
			+ "select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee ,a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
			" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
			" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
			" where ar.vch_to='RT' and ar.vch_from='FL2' and ar.dt_date>='2020-05-01' and EXTRACT(MONTH FROM ar.dt_date)="+act.getSelectedMonth()+")a " +
			" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b , distillery.packaging_details_19_20 d" +
			" where a.vch_gatepass_no=b.vch_gatepass_no and d.package_id=b.int_pckg_id group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty ,d.cesh"
			+ ")br " +
			"  " +
			" group by br.shop_id " +
			" )cr group by cr.shop_id " +
			" union all " +
			"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as coronafee,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
			" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.permit)*sum(br.bottle))  as duty from  " +
			" ("
			+ "select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
			" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
			" from fl2d.gatepass_to_districtwholesale_fl2d_20_21 ar where ar.vch_to='RT' and EXTRACT(MONTH FROM ar.dt_date)="+act.getSelectedMonth()+")a  " +
			" ,fl2d.fl2d_stock_trxn_20_21  b ,distillery.brand_registration_20_21  az  where   a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00 group by a.shop_id, b.int_pckg_id "
			+ " union all select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
			" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
			" from fl2d.gatepass_to_districtwholesale_fl2d_19_20 ar where ar.vch_to='RT' and ar.dt_date>'2020-05-01' and EXTRACT(MONTH FROM ar.dt_date)="+act.getSelectedMonth()+")a  " +
			" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_19_20  az   where    a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00 group by a.shop_id, b.int_pckg_id "
					+ ") br ,  distillery.packaging_details_20_21 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity,d.permit " +
			"  )cr " +
			" group by cr.shop_id " +
			" union all  " +
			" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty,0 as coronafee, " +
			" case when '1'='"+act.getSelectedMonth()+"' then sum(jan_19_box) when '2'='"+act.getSelectedMonth()+"' then sum(feb_19_box) when '3'='"+act.getSelectedMonth()+"' then sum(march_19_box) when '4'='"+act.getSelectedMonth()+"' then sum(april_18_box) when '5'='"+act.getSelectedMonth()+"' then sum(may_18_box) when '6'='"+act.getSelectedMonth()+"' then sum(june_18_box) when '7'='"+act.getSelectedMonth()+"' then sum(july_18_box) when '8'='"+act.getSelectedMonth()+"' then sum(august_18_box) when '9'='"+act.getSelectedMonth()+"' then sum(sept_18_box) when '10'='"+act.getSelectedMonth()+"' then sum(oct_18_box) when '11'='"+act.getSelectedMonth()+"' then sum(nov_18_box)  when '12'='"+act.getSelectedMonth()+"' then sum(dec_18_box) end as old_box " +
			" ,case when '1'='"+act.getSelectedMonth()+"' then sum(jan_19_bl) when '2'='"+act.getSelectedMonth()+"' then sum(feb_19_bl) when '3'='"+act.getSelectedMonth()+"' then sum(march_19_bl) when '4'='"+act.getSelectedMonth()+"' then sum(april_18_bl) when '5'='"+act.getSelectedMonth()+"' then sum(may_18_bl) when '6'='"+act.getSelectedMonth()+"' then sum(june_18_bl) when '7'='"+act.getSelectedMonth()+"' then sum(july_18_bl) when '8'='"+act.getSelectedMonth()+"' then sum(august_18_bl) when '9'='"+act.getSelectedMonth()+"' then sum(sept_18_bl) when '10'='"+act.getSelectedMonth()+"' then sum(oct_18_bl) when '11'='"+act.getSelectedMonth()+"' then sum(nov_18_bl)  when '12'='"+act.getSelectedMonth()+"' then sum(dec_18_bl) end as old_bl   " +
			" ,case when '1'='"+act.getSelectedMonth()+"' then sum(jan_19_duty) when '2'='"+act.getSelectedMonth()+"' then sum(feb_19_duty) when '3'='"+act.getSelectedMonth()+"' then sum(march_19_duty) when '4'='"+act.getSelectedMonth()+"' then sum(april_18_duty) when '5'='"+act.getSelectedMonth()+"' then sum(may_18_duty) when '6'='"+act.getSelectedMonth()+"' then sum(june_18_duty) when '7'='"+act.getSelectedMonth()+"' then sum(july_18_duty) when '8'='"+act.getSelectedMonth()+"' then sum(august_18_duty) when '9'='"+act.getSelectedMonth()+"' then sum(sept_18_duty) when '10'='"+act.getSelectedMonth()+"' then sum(oct_18_duty) when '11'='"+act.getSelectedMonth()+"' then sum(nov_18_duty)  when '12'='"+act.getSelectedMonth()+"' then sum(dec_18_duty) end as old_duty " +
			" from retail.retail_shop_lifting_duty_19_20 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
			" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
			" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  group by x.district_id order by description ";
	//System.out.println("districtwise="+reportQuery);
		pst = con.prepareStatement(reportQuery);
		rs = pst.executeQuery();

		}
		
		else{
			//System.out.println(" Entered in eLSE ===============");
			reportQuery="" +
					"  " +
					" select  (select description from public.district d where d.districtid=x.district_id) as description, " +
					" sum(COALESCE(y.new_box,0))new_box,round(CAST(float8(sum(COALESCE(y.new_bl,0))) as numeric), 2) as new_bl, " +
					" round(CAST(float8(sum(COALESCE(y.new_duty,0))) as numeric), 2) as new_duty, round(CAST(float8(sum(COALESCE(y.coronafee,0))) as numeric), 2) as  coronafee," +
					" sum(COALESCE(y.old_box,0))old_box,sum(COALESCE(y.old_bl,0))old_bl,sum(COALESCE(y.old_duty,0))old_duty " +
					" ,case when sum(COALESCE(y.old_box,0))=0 then 0 else round(CAST(float8(((sum(COALESCE(y.new_box,0))/sum(COALESCE(y.old_box,0)))*100)) as numeric), 2) end as per_box " +
					" ,case when sum(COALESCE(y.old_bl,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE(y.new_bl,0))/sum(COALESCE(y.old_bl,0)))*100)) as numeric), 2)  end as per_bl " +
					" ,case when sum(COALESCE(y.old_duty,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE((y.new_duty+y.coronafee),0))/sum(COALESCE(y.old_duty,0)))*100)) as numeric), 2) end as per_duty  " +
					" from retail.retail_shop x left join  " +
					" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.coronafee)coronafee,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
					" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,sum(cr.coronafee) as coronafee, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
					" select br.shop_id,sum(br.box)box,sum(br.bl) as bl,sum(br.duty) as duty,sum(br.coronafee) as coronafee " +
					" from  " +
					" ("
					+ "select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee ,a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
					" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 ar " +
					" where ar.vch_to='RT' and ar.vch_from='FL2' and " +
					" case when 4="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
					" when 1="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
					" when 2="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
					" when 3="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
					" ,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b , distillery.packaging_details_20_21 d " +
					" where a.vch_gatepass_no=b.vch_gatepass_no  and d.package_id=b.int_pckg_id group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty,d.cesh "
					+ " union all select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee ,a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
					" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
					" where ar.vch_to='RT' and ar.vch_from='FL2' and " +
					" case when 4="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
					" when 1="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
					" when 2="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
					" when 3="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
					" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b , distillery.packaging_details_19_20 d " +
					" where  a.dt_date>='2020-05-01'  and d.package_id=b.int_pckg_id and   a.vch_gatepass_no=b.vch_gatepass_no group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty,d.cesh "
					+ ")br" +
					"  " +
					" group by br.shop_id  " +
					" )cr group by cr.shop_id " +
					" union all " +
					"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as coronafee,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
					" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.permit)*sum(br.bottle)) as duty from  " +
					" ("
					+ "select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
					" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
					" from fl2d.gatepass_to_districtwholesale_fl2d_20_21 ar where ar.vch_to='RT' and " +
					" case when 4="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
					" when 1="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
					" when 2="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
					" when 3="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
					" ,fl2d.fl2d_stock_trxn_20_21  b ,distillery.brand_registration_20_21  az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00  group by a.shop_id, b.int_pckg_id"
					+ " union all select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
					" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
					" from fl2d.gatepass_to_districtwholesale_fl2d_19_20 ar where ar.vch_to='RT' and " +
					" case when 4="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
					" when 1="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
					" when 2="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
					" when 3="+act.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
					" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_20_21  az where a.dt_date>'2020-05-01' and  a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00  group by a.shop_id, b.int_pckg_id"
							+ ") br, " +
					" distillery.packaging_details_20_21 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity,d.permit)cr " +
					" group by cr.shop_id " +
					" union all  " +
					" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, 0 as coronafee," +
					" case when '1'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(april_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(may_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(june_18_box,0)) as numeric), 0)) when '2'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(july_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(august_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(sept_18_box ,0)) as numeric), 0)) when '3'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(oct_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(nov_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(dec_18_box ,0)) as numeric), 0)) when '4'='"+act.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(jan_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(feb_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(march_19_box ,0)) as numeric), 0)) end as old_box " +
					" ,case when '1'='"+act.getSelectedQuarter()+"' then sum(april_18_bl + may_18_bl + june_18_bl) when '2'='"+act.getSelectedQuarter()+"' then sum(july_18_bl+ august_18_bl + sept_18_bl) when '3'='"+act.getSelectedQuarter()+"' then sum(oct_18_bl + nov_18_bl + dec_18_bl) when '4'='"+act.getSelectedQuarter()+"' then sum(jan_19_bl + feb_19_bl+ march_19_bl) end as old_bl "+
					" ,case when '1'='"+act.getSelectedQuarter()+"' then sum(april_18_duty + may_18_duty + june_18_duty) when '2'='"+act.getSelectedQuarter()+"' then sum(july_18_duty+ august_18_duty + sept_18_duty) when '3'='"+act.getSelectedQuarter()+"' then sum(oct_18_duty + nov_18_duty + dec_18_duty) when '4'='"+act.getSelectedQuarter()+"' then sum(jan_19_duty + feb_19_duty+ march_19_duty) end as old_duty " +
					" from retail.retail_shop_lifting_duty_19_20 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
					" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
					" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  group by x.district_id order by description ";
		
		
		}
		// System.out.println("District Wise==== " +reportQuery);
		pst = con.prepareStatement(reportQuery);
		rs = pst.executeQuery();
		if (rs.next()) {

			rs = pst.executeQuery();
			Map parameters = new HashMap();
			parameters.put("REPORT_CONNECTION", con);
			parameters.put("SUBREPORT_DIR", relativePath + File.separator);
			parameters.put("image", relativePath + File.separator);
			parameters.put("radioType", act.getRadio());
			parameters.put("100", 100);
			if(act.getSelectedMonth()>0) {
				parameters.put("monthName1", act.getMonthName());
				}
				if(act.getSelectedQuarter()>0) {
					parameters.put("monthName1","Quarter- " + act.getSelectedQuarter());
				}
			if(act.getSelectedMonth()>0) {
				if(act.getSelectedMonth()==1 ||act.getSelectedMonth()==2 ||act.getSelectedMonth()==3||act.getSelectedMonth()==4) {
					parameters.put("monthName", act.getMonthName()+"-2021");
				}else {
					parameters.put("monthName", act.getMonthName()+"-2020");
				}
			}
			else {
			if(act.getSelectedQuarter()==1 ||act.getSelectedQuarter()==2 ||act.getSelectedQuarter()==3 ) {
				parameters.put("monthName","Quarter- " +  act.getSelectedQuarter()+"-2020");
			}else {
				parameters.put("monthName", "Quarter- " + act.getSelectedQuarter()+"-2021");
			}

			 	 
			}
			JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

			jasperReport = (JasperReport) JRLoader.loadObject(relativePath
					+ File.separator + "fl_lifting_comparision_districtwise2020.jasper");

			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, jrRs);
			Random rand = new Random();
			int n = rand.nextInt(250) + 1;
			JasperExportManager.exportReportToPdfFile(print,
					relativePathpdf + File.separator
							+ "fl_lifting_comparision_districtwise_"+act.getBwfl_id()+"" + "-" + n + ".pdf");
			act.setPdfName("fl_lifting_comparision_districtwise_"+act.getBwfl_id()+"" + "-" + n + ".pdf");
			act.setPrintFlag(true);
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"No Data Found!!", "No Data Found!!"));
			act.setPrintFlag(false);
		}
	} catch (JRException e) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						e.getMessage()+reportQuery,e.getMessage()+reportQuery));
		
	} catch (Exception e) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						e.getMessage()+reportQuery,e.getMessage()+reportQuery));
		
	} finally {
		try {
			if (rs != null)
				rs.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							e.getMessage()+reportQuery,e.getMessage()+reportQuery));
			
		}
	}

}




public boolean excelDitrictWise(LifitingComparisionFLAction action) throws ParseException {

	Connection con = null;
	/*double current_bl_per = 0;
	double current_bl = 0;
	
	double commulative_bl = 0;
	double commulative_bl_per = 0;
	

	double yearly_mgq = 0;
	double monthly_mgq = 0;
*/

	double cases_of_last_year_sale_=0;
	double bl_of_last_year_sale_=0;
	double revenue_of_last_year_sale_=0;
	double cases_of_current_year_sale_=0;
	double bl_of_current_year_sale_=0;
	double revenue_of_current_year_sale_=0;
	double cases_box_=0;
    double bl_of_ =0;
	double revenue_=0;
	

/*	String reportQuery =   " select bb.description,bb.yearly_mgq,bb.monthly_mgq,bb.current_actual_bl, bb.cummulative_act_bl,"+
			 
"  round(CAST(float8(bb.current_actual_bl_per) as numeric), 2)as  current_actual_bl_per,"+
"      round(CAST(float8( bb.cummulative_act_bl_per ) as numeric), 2)as  cummulative_act_bl_per  "+           
			                  
" 	from ( select x.description,x.yearly_mgq,x.monthly_mgq,x.current_actual_bl, x.cummulative_act_bl,"+
" 				case when x.monthly_mgq::int !=0 then x.current_actual_bl*100/x.monthly_mgq else 0 end  as current_actual_bl_per,"+
" 			 case when x.yearly_mgq::int !=0 then x.cummulative_act_bl*100/x.yearly_mgq else 0 end as cummulative_act_bl_per "+

 
"    from "+

			"( SELECT    b.serial_no, "+
			" b.vch_name_of_shop,a.description, "+
			" COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq,"+
			" COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq, "+
			" COALESCE(sum(cc.current_actual_bl),0) as current_actual_bl, "+
			"  case when sum(cc.current_actual_bl)!=0 and sum(cc.current_actual_bl) is not null "+
			"  and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null  "+
			" then round(CAST(float8((sum(cc.current_actual_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) "+
			" else 0 end as current_actual_bl_per,"+
			" COALESCE(sum(cc.cummulative_act_bl),0) as cummulative_act_bl,"+
			"  case when sum(cc.cummulative_act_bl)!=0 and sum(cc.cummulative_act_bl) is not null "+
			"  and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "+
			" then round(CAST(float8((sum(cc.cummulative_act_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2)) as numeric), 2) "+
			" else 0 end as cummulative_act_bl_per "+
		//	",(SELECT sector_name FROM public.mst_sector_master where sector_id= b.sector)  as sector "+
			" 	FROM public.district a left join retail.retail_shop b on a.districtid=b.district_id left join  "+
			"     ( "+
			"    select bb.shopId,sum(bb.current_actual_bl) as current_actual_bl,sum(bb.cummulative_act_bl) as cummulative_act_bl"+
			"     from "+
			"      (  "+
			"         select aa.shopId,aa.dispatch_date,  aa.bl_1,aa.bl_2,aa.bl_3, "+					         
			"         case when EXTRACT(MONTH FROM aa.dispatch_date)="+action.getSelectedMonth()+"	"+
			"         then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as current_actual_bl, "+	
			
  				"			case when "+
  				"			aa.dispatch_date  between '2020/04/01' and '"+action.getDateSelected()+"' "+
  				"			then "+
  				"			(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  "+
   			"			end "+
   			"			as cummulative_act_bl  "+
  
			
			" 	from( 	 "+
			" 	select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,  "+
			"        case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1, "+
			" 	case when  m.strength=36 then sum(bl) end as bl_2 ,"+
			"        case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3 "+
			" 	from	"+
			" 	(select x.dispatch_date,x.shopId,(((x.bottal)*f.qnt_ml_detail)/1000) as bl,d.strength from  	"+				
			" 	(select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal  	"+
			" 	from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+action.getBwfl_id()+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+action.getBwfl_id()+" b  			 "+
			" 	where  a.vch_from='CL2' and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null "+
			" 	group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id )x, 		"+				
			" 	distillery.box_size_details f,distillery.brand_registration_"+action.getBwfl_id()+" d,distillery.packaging_details_"+action.getBwfl_id()+" e  	"+
			" 	where x.int_brand_id=d.brand_id and e.box_id=f.box_id and x.package_id=e.package_id)m group by m.dispatch_date,m.shopId ,m.strength  	"+	
			" 	)aa group by aa.dispatch_date,aa.shopId, aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId  order by bb.shopId  	"+
			" 	)cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'  "
			+"  group by  a.description,b.vch_name_of_shop, b.serial_no order by a.description   )zz group by 	"+
"  zz.description 	"+
" order by zz.description )x order by x.description " +
" )bb  " +
" order by bb.description ";
*/
	String reportQuery="" ;

	if(action.getSelectedQuarter()==0)
	{
reportQuery="" +
		"  " +
		" select  (select description from public.district d where d.districtid=x.district_id) as description, " +
		" sum(COALESCE(y.new_box,0))new_box,round(CAST(float8(sum(COALESCE(y.new_bl,0))) as numeric), 2) as new_bl, " +
		" round(CAST(float8(sum(COALESCE(y.new_duty,0))) as numeric), 2) as new_duty, " +
		" sum(COALESCE(y.old_box,0))old_box,sum(COALESCE(y.old_bl,0))old_bl,sum(COALESCE(y.old_duty,0))old_duty " +
		" ,case when sum(COALESCE(y.old_box,0))=0 then 0 else round(CAST(float8(((sum(COALESCE(y.new_box,0))/sum(COALESCE(y.old_box,0)))*100)) as numeric), 2) end as per_box " +
		" ,case when sum(COALESCE(y.old_bl,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE(y.new_bl,0))/sum(COALESCE(y.old_bl,0)))*100)) as numeric), 2)  end as per_bl " +
		" ,case when sum(COALESCE(y.old_duty,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE(y.new_duty,0))/sum(COALESCE(y.old_duty,0)))*100)) as numeric), 2) end as per_duty  " +
		" from retail.retail_shop x left join  " +
		" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
		" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
		" select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(br.bottle)) as duty " +
		" from  " +
		" (select a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
		" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
		" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
		" where ar.vch_to='RT' and ar.vch_from='FL2' and ar.dt_date<'2020-05-01' and EXTRACT(MONTH FROM ar.dt_date)="+action.getSelectedMonth()+")a " +
		" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b " +
		" where a.vch_gatepass_no=b.vch_gatepass_no group by a.shop_id,b.int_pckg_id)br, distillery.packaging_details_19_20 d " +
		" where d.package_id=br.int_pckg_id " +
		" group by br.shop_id,d.quantity,d.duty,d.adduty " +
		" )cr group by cr.shop_id " +
		" union all " +
		"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
		" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,sum(br.duty) as duty from  " +
		" (select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
		" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
		" from fl2d.gatepass_to_districtwholesale_fl2d_19_20 ar where ar.vch_to='RT' and ar.dt_date<'2020-05-01' and EXTRACT(MONTH FROM ar.dt_date)="+action.getSelectedMonth()+")a  " +
		" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_19_20  az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00 group by a.shop_id, b.int_pckg_id) br, " +
		" distillery.packaging_details_19_20 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity)cr " +
		" group by cr.shop_id " +
		" union all  " +
		" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, " +
		" case when '1'='"+action.getSelectedMonth()+"' then sum(jan_19_box) when '2'='"+action.getSelectedMonth()+"' then sum(feb_19_box) when '3'='"+action.getSelectedMonth()+"' then sum(march_19_box) when '4'='"+action.getSelectedMonth()+"' then sum(april_18_box) when '5'='"+action.getSelectedMonth()+"' then sum(may_18_box) when '6'='"+action.getSelectedMonth()+"' then sum(june_18_box) when '7'='"+action.getSelectedMonth()+"' then sum(july_18_box) when '8'='"+action.getSelectedMonth()+"' then sum(august_18_box) when '9'='"+action.getSelectedMonth()+"' then sum(sept_18_box) when '10'='"+action.getSelectedMonth()+"' then sum(oct_18_box) when '11'='"+action.getSelectedMonth()+"' then sum(nov_18_box)  when '12'='"+action.getSelectedMonth()+"' then sum(dec_18_box) end as old_box " +
		" ,case when '1'='"+action.getSelectedMonth()+"' then sum(jan_19_bl) when '2'='"+action.getSelectedMonth()+"' then sum(feb_19_bl) when '3'='"+action.getSelectedMonth()+"' then sum(march_19_bl) when '4'='"+action.getSelectedMonth()+"' then sum(april_18_bl) when '5'='"+action.getSelectedMonth()+"' then sum(may_18_bl) when '6'='"+action.getSelectedMonth()+"' then sum(june_18_bl) when '7'='"+action.getSelectedMonth()+"' then sum(july_18_bl) when '8'='"+action.getSelectedMonth()+"' then sum(august_18_bl) when '9'='"+action.getSelectedMonth()+"' then sum(sept_18_bl) when '10'='"+action.getSelectedMonth()+"' then sum(oct_18_bl) when '11'='"+action.getSelectedMonth()+"' then sum(nov_18_bl)  when '12'='"+action.getSelectedMonth()+"' then sum(dec_18_bl) end as old_bl   " +
		" ,case when '1'='"+action.getSelectedMonth()+"' then sum(jan_19_duty) when '2'='"+action.getSelectedMonth()+"' then sum(feb_19_duty) when '3'='"+action.getSelectedMonth()+"' then sum(march_19_duty) when '4'='"+action.getSelectedMonth()+"' then sum(april_18_duty) when '5'='"+action.getSelectedMonth()+"' then sum(may_18_duty) when '6'='"+action.getSelectedMonth()+"' then sum(june_18_duty) when '7'='"+action.getSelectedMonth()+"' then sum(july_18_duty) when '8'='"+action.getSelectedMonth()+"' then sum(august_18_duty) when '9'='"+action.getSelectedMonth()+"' then sum(sept_18_duty) when '10'='"+action.getSelectedMonth()+"' then sum(oct_18_duty) when '11'='"+action.getSelectedMonth()+"' then sum(nov_18_duty)  when '12'='"+action.getSelectedMonth()+"' then sum(dec_18_duty) end as old_duty " +
		" from retail.retail_shop_lifting_duty_18_19 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
		" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
		" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  group by x.district_id order by description ";
 
	}
	
	else{
		 
		reportQuery="" +
				"  " +
				" select  (select description from public.district d where d.districtid=x.district_id) as description, " +
				" sum(COALESCE(y.new_box,0))new_box,round(CAST(float8(sum(COALESCE(y.new_bl,0))) as numeric), 2) as new_bl, " +
				" round(CAST(float8(sum(COALESCE(y.new_duty,0))) as numeric), 2) as new_duty, " +
				" sum(COALESCE(y.old_box,0))old_box,sum(COALESCE(y.old_bl,0))old_bl,sum(COALESCE(y.old_duty,0))old_duty " +
				" ,case when sum(COALESCE(y.old_box,0))=0 then 0 else round(CAST(float8(((sum(COALESCE(y.new_box,0))/sum(COALESCE(y.old_box,0)))*100)) as numeric), 2) end as per_box " +
				" ,case when sum(COALESCE(y.old_bl,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE(y.new_bl,0))/sum(COALESCE(y.old_bl,0)))*100)) as numeric), 2)  end as per_bl " +
				" ,case when sum(COALESCE(y.old_duty,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE(y.new_duty,0))/sum(COALESCE(y.old_duty,0)))*100)) as numeric), 2) end as per_duty  " +
				" from retail.retail_shop x left join  " +
				" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
				" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
				" select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(br.bottle)) as duty " +
				" from  " +
				" (select a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
				" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
				" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
				" where ar.vch_to='RT'  and ar.dt_date<'2020-05-01'  and ar.vch_from='FL2' and " +
				" case when 4="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
				" when 1="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
				" when 2="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
				" when 3="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
				" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b " +
				" where a.vch_gatepass_no=b.vch_gatepass_no group by a.shop_id,b.int_pckg_id)br, distillery.packaging_details_19_20 d " +
				" where d.package_id=br.int_pckg_id " +
				" group by br.shop_id,d.quantity,d.duty,d.adduty " +
				" )cr group by cr.shop_id " +
				" union all " +
				"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
				" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,sum(br.duty) as duty from  " +
				" (select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
				" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
				" from fl2d.gatepass_to_districtwholesale_fl2d_19_20 ar where ar.vch_to='RT'  and ar.dt_date<'2020-05-01'  and " +
				" case when 4="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
				" when 1="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
				" when 2="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
				" when 3="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
				" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_19_20  az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00  group by a.shop_id, b.int_pckg_id) br, " +
				" distillery.packaging_details_19_20 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity)cr " +
				" group by cr.shop_id " +
				" union all  " +
				" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, " +
				" case when '1'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(april_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(may_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(june_18_box,0)) as numeric), 0)) when '2'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(july_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(august_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(sept_18_box ,0)) as numeric), 0)) when '3'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(oct_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(nov_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(dec_18_box ,0)) as numeric), 0)) when '4'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(jan_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(feb_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(march_19_box ,0)) as numeric), 0)) end as old_box " +
				" ,case when '1'='"+action.getSelectedQuarter()+"' then sum(april_18_bl + may_18_bl + june_18_bl) when '2'='"+action.getSelectedQuarter()+"' then sum(july_18_bl+ august_18_bl + sept_18_bl) when '3'='"+action.getSelectedQuarter()+"' then sum(oct_18_bl + nov_18_bl + dec_18_bl) when '4'='"+action.getSelectedQuarter()+"' then sum(jan_19_bl + feb_19_bl+ march_19_bl) end as old_bl "+
				" ,case when '1'='"+action.getSelectedQuarter()+"' then sum(april_18_duty + may_18_duty + june_18_duty) when '2'='"+action.getSelectedQuarter()+"' then sum(july_18_duty+ august_18_duty + sept_18_duty) when '3'='"+action.getSelectedQuarter()+"' then sum(oct_18_duty + nov_18_duty + dec_18_duty) when '4'='"+action.getSelectedQuarter()+"' then sum(jan_19_duty + feb_19_duty+ march_19_duty) end as old_duty " +
				" from retail.retail_shop_lifting_duty_18_19 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
				" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
				" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  group by x.district_id order by description ";
	 System.out.println("District Wise==== " +reportQuery);
	
	}
	System.out.println("===eDistrictWise2020   ="+reportQuery);
	String relativePath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
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
				.createSheet("FL Lifting Comparision District Wise Report");

		worksheet.setColumnWidth(0, 3000);
		worksheet.setColumnWidth(1, 5000);
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
		cellhead0.setCellValue("FL Lifting Comparision District Wise");
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
		cellhead1.setCellValue("");
		cellhead1.setCellStyle(cellStyle);
		
		XSSFCell cellhead2 = rowhead.createCell((int) 1);
		cellhead2.setCellValue("District");
		cellhead2.setCellStyle(cellStyle);

		XSSFCell cellhead3 = rowhead.createCell((int) 2);
		cellhead3.setCellValue("Cases of Last year sale");
		cellhead3.setCellStyle(cellStyle);
		
		XSSFCell cellhead4 = rowhead.createCell((int) 3);
		cellhead4.setCellValue("BL of Last year sale");
		cellhead4.setCellStyle(cellStyle);

		XSSFCell cellhead5 = rowhead.createCell((int) 4);
		cellhead5.setCellValue("Revenue of Last year sale");
		cellhead5.setCellStyle(cellStyle);
		
		XSSFCell cellhead6 = rowhead.createCell((int) 5);
		cellhead6.setCellValue("Cases of current year sale");
		cellhead6.setCellStyle(cellStyle);
		

		XSSFCell cellhead7 = rowhead.createCell((int) 6);
		cellhead7.setCellValue("BL of current year sale");
		cellhead7.setCellStyle(cellStyle);
		

		XSSFCell cellhead8 = rowhead.createCell((int) 7);
		cellhead8.setCellValue("Revenue of current year sale");
		cellhead8.setCellStyle(cellStyle);
		
		XSSFCell cellhead9 = rowhead.createCell((int) 8);
		cellhead9.setCellValue("Cases of % increase");
		cellhead9.setCellStyle(cellStyle);
		

		XSSFCell cellhead10 = rowhead.createCell((int) 9);
		cellhead10.setCellValue("BL of % increase");
		cellhead10.setCellStyle(cellStyle);
		
		XSSFCell cellhead11 = rowhead.createCell((int) 10);
		cellhead11.setCellValue("Revenue of % increase");
		cellhead11.setCellStyle(cellStyle);
		
		XSSFCell cellhead12 = rowhead.createCell((int) 11);
		cellhead12.setCellValue("");
		cellhead12.setCellStyle(cellStyle);

		int i = 0;
		
		while (rs.next()) 
		{
			cases_of_last_year_sale_ = cases_of_last_year_sale_ + rs.getDouble("old_box");
			bl_of_last_year_sale_ = bl_of_last_year_sale_ + rs.getDouble("old_bl");
			
			revenue_of_last_year_sale_ = revenue_of_last_year_sale_ + rs.getDouble("old_duty");
			cases_of_current_year_sale_ = cases_of_current_year_sale_ + rs.getDouble("new_box");
		
			bl_of_current_year_sale_ = bl_of_current_year_sale_ + rs.getDouble("new_bl");
			revenue_of_current_year_sale_ = revenue_of_current_year_sale_ + rs.getDouble("new_duty");
			
			
			
			k++; //
			XSSFRow row1 = worksheet.createRow((int) k); //
			XSSFCell cellA1 = row1.createCell((int) 0); //
			cellA1.setCellValue(k); //
			
			
			XSSFCell cellB1 = row1.createCell((int) 1);
			cellB1.setCellValue(rs.getString("description"));
			
		
			XSSFCell cellC1 = row1.createCell((int) 2);
			cellC1.setCellValue(rs.getDouble("old_box"));
			
			XSSFCell cellD1 = row1.createCell((int) 3);
			cellD1.setCellValue(rs.getDouble("old_bl"));
			
			
			XSSFCell cellE1 = row1.createCell((int) 4);
			cellE1.setCellValue(rs.getDouble("old_duty"));
			
			XSSFCell cellF1 = row1.createCell((int) 5);
			cellF1.setCellValue(rs.getDouble("new_box"));
			
			XSSFCell cellG1 = row1.createCell((int) 6);
			cellG1.setCellValue(rs.getDouble("new_bl"));
			
			XSSFCell cellH1 = row1.createCell((int) 7);
			cellH1.setCellValue(rs.getDouble("new_duty"));
			
			XSSFCell cellI1 = row1.createCell((int) 8);
			cellI1.setCellValue(rs.getDouble("per_box"));
			
			XSSFCell cellJ1 = row1.createCell((int) 9);
			cellJ1.setCellValue(rs.getDouble("per_bl"));
			
			XSSFCell cellK1 = row1.createCell((int) 10);
			cellK1.setCellValue(rs.getDouble("per_duty"));
			
		/*XSSFCell cellL1 = row1.createCell((int) 11);
			cellL1.setCellValue(rs.getDouble(""));*/
			
		}
		
		
		if(cases_of_last_year_sale_>0){
		cases_box_=((cases_of_current_year_sale_/cases_of_last_year_sale_)*100);
		}
		
		if(bl_of_last_year_sale_>0){
		bl_of_=((bl_of_current_year_sale_/bl_of_last_year_sale_)*100);
		}
		if(revenue_of_last_year_sale_>0){
		revenue_=((revenue_of_current_year_sale_/revenue_of_last_year_sale_)*100);
		}
		
		
		
		Random rand = new Random();
		int n = rand.nextInt(550) + 1;
		fileOut = new FileOutputStream(relativePath
				+ "//ExciseUp//MIS//Excel//" + n+"_"+action.getBwfl_id()+ "_FL_Lifting_Comparision_districtwise.xls");

		action.setExlname(n+"_"+action.getBwfl_id()+"_FL_Lifting_Comparision_districtwise.xls");
		XSSFRow row1 = worksheet.createRow((int) k + 1);
		
	
		XSSFCell cellA1 = row1.createCell((int) 0);
		cellA1.setCellValue(" "); 
		cellA1.setCellStyle(cellStyle);
		
		XSSFCell cellA2 = row1.createCell((int) 1); 
		cellA2.setCellValue("Total: "); 
		cellA2.setCellStyle(cellStyle); 
		
		XSSFCell cellA3 = row1.createCell((int) 2); 
		cellA3.setCellValue(Math.round(cases_of_last_year_sale_)); 
		cellA3.setCellStyle(cellStyle); 
		

		XSSFCell cellA4 = row1.createCell((int) 3); 
		cellA4.setCellValue(bl_of_last_year_sale_); 
		cellA4.setCellStyle(cellStyle); 
		
		XSSFCell cellA5 = row1.createCell((int) 4);
		cellA5.setCellValue(revenue_of_last_year_sale_);
		cellA5.setCellStyle(cellStyle);
		
		XSSFCell cellA6 = row1.createCell((int) 5);
		cellA6.setCellValue(cases_of_current_year_sale_);
		cellA6.setCellStyle(cellStyle);
		
		XSSFCell cellA7 = row1.createCell((int) 6);
		cellA7.setCellValue(bl_of_current_year_sale_); 
		cellA7.setCellStyle(cellStyle);
		
		XSSFCell cellA8 = row1.createCell((int) 7);
		cellA8.setCellValue(revenue_of_current_year_sale_);
		cellA8.setCellStyle(cellStyle);
		
		XSSFCell cellA9 = row1.createCell((int) 8);
		cellA9.setCellStyle(cellStyle);
		
		
		XSSFCell cellA10 = row1.createCell((int) 9);
		cellA10.setCellStyle(cellStyle);
		
		XSSFCell cellA11 = row1.createCell((int) 10);
		cellA11.setCellStyle(cellStyle);
		
		
	
	

		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		flag = true;
		action.setExcelFlag(true);
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

	return flag;

}

public boolean excelDitrictWise2020(LifitingComparisionFLAction action) throws ParseException {

	Connection con = null;
 

	double cases_of_last_year_sale_=0;
	double bl_of_last_year_sale_=0;
	double revenue_of_last_year_sale_=0;
	double cases_of_current_year_sale_=0;
	double bl_of_current_year_sale_=0;
	double revenue_of_current_year_sale_=0;
	double cases_box_=0;
    double bl_of_ =0;
	double revenue_=0;
	
	double new_revenue_=0;
	double corna_fee=0;
	double total_new_revenue_=0;
	 
 
	String reportQuery="" ;

	if(action.getSelectedQuarter()==0)
	{
reportQuery="" +
		"  " +
		" select  (select description from public.district d where d.districtid=x.district_id) as description, " +
		" sum(COALESCE(y.new_box,0))new_box,round(CAST(float8(sum(COALESCE(y.new_bl,0))) as numeric), 2) as new_bl, " +
		" round(CAST(float8(sum(COALESCE(y.new_duty,0))) as numeric), 2) as new_duty, round(CAST(float8(sum(COALESCE(y.coronafee,0))) as numeric), 2) as coronafee, " +
		" sum(COALESCE(y.old_box,0))old_box,sum(COALESCE(y.old_bl,0))old_bl,sum(COALESCE(y.old_duty,0))old_duty " +
		" ,case when sum(COALESCE(y.old_box,0))=0 then 0 else round(CAST(float8(((sum(COALESCE(y.new_box,0))/sum(COALESCE(y.old_box,0)))*100)) as numeric), 2) end as per_box " +
		" ,case when sum(COALESCE(y.old_bl,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE(y.new_bl,0))/sum(COALESCE(y.old_bl,0)))*100)) as numeric), 2)  end as per_bl " +
		" ,case when sum(COALESCE(y.old_duty,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE((y.new_duty+y.coronafee),0))/sum(COALESCE(y.old_duty,0)))*100)) as numeric), 2) end as per_duty  " +
		" from retail.retail_shop x left join  " +
		" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.coronafee)coronafee,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
		" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,sum(cr.coronafee) as coronafee, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
		" select br.shop_id,sum(br.box)box,sum(br.bl) as bl,sum(br.duty) as duty,sum(br.coronafee) as coronafee " +
		" from  " +
		" ("
		+ "select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee , a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
		" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
		" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 ar " +
		" where ar.vch_to='RT'   and EXTRACT(MONTH FROM ar.dt_date)="+action.getSelectedMonth()+")a " +
		" ,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b , distillery.packaging_details_20_21 d,distillery.brand_registration_20_21 c   " +
		" where a.vch_gatepass_no=b.vch_gatepass_no  and d.package_id=b.int_pckg_id and b.int_brand_id=c.brand_id  and c.license_category not in  ('BEER','LAB','IMPORTED BEER' )  group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty ,d.cesh union all "
		+ "select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee , a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
		" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
		" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
		" where ar.vch_to='RT' and ar.vch_from='FL2' and ar.dt_date>='2020-05-01' and EXTRACT(MONTH FROM ar.dt_date)="+action.getSelectedMonth()+")a " +
		" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b, distillery.packaging_details_19_20 d  " +
		" where a.vch_gatepass_no=b.vch_gatepass_no  and d.package_id=b.int_pckg_id  group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty ,d.cesh"
		+ ")br " +
		"" +
		" group by br.shop_id " +
		" )cr group by cr.shop_id " +
		" union all " +
		"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as coronafee,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
		" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.permit)*sum(br.bottle)) as duty from  " +
		" ("
		+ "select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
		" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
		" from fl2d.gatepass_to_districtwholesale_fl2d_20_21 ar where ar.vch_to='RT' and EXTRACT(MONTH FROM ar.dt_date)="+action.getSelectedMonth()+")a  " +
		" ,fl2d.fl2d_stock_trxn_20_21  b ,distillery.brand_registration_20_21  az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and az.license_category not in  ('BEER','LAB','IMPORTED BEER' ) group by a.shop_id, b.int_pckg_id"
		+ " union all select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
		" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
		" from fl2d.gatepass_to_districtwholesale_fl2d_19_20 ar where ar.vch_to='RT' and ar.dt_date>'2020-05-01' and EXTRACT(MONTH FROM ar.dt_date)="+action.getSelectedMonth()+")a  " +
		" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_19_20  az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and   az.license_category not in  ('BEER','LAB','IMPORTED BEER' )  group by a.shop_id, b.int_pckg_id"
				+ ") br, " +
		" distillery.packaging_details_20_21 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity,d.permit)cr " +
		" group by cr.shop_id " +
		" union all  " +
		" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty,0 as coronafee, " +
		" case when '1'='"+action.getSelectedMonth()+"' then sum(jan_19_box) when '2'='"+action.getSelectedMonth()+"' then sum(feb_19_box) when '3'='"+action.getSelectedMonth()+"' then sum(march_19_box) when '4'='"+action.getSelectedMonth()+"' then sum(april_18_box) when '5'='"+action.getSelectedMonth()+"' then sum(may_18_box) when '6'='"+action.getSelectedMonth()+"' then sum(june_18_box) when '7'='"+action.getSelectedMonth()+"' then sum(july_18_box) when '8'='"+action.getSelectedMonth()+"' then sum(august_18_box) when '9'='"+action.getSelectedMonth()+"' then sum(sept_18_box) when '10'='"+action.getSelectedMonth()+"' then sum(oct_18_box) when '11'='"+action.getSelectedMonth()+"' then sum(nov_18_box)  when '12'='"+action.getSelectedMonth()+"' then sum(dec_18_box) end as old_box " +
		" ,case when '1'='"+action.getSelectedMonth()+"' then sum(jan_19_bl) when '2'='"+action.getSelectedMonth()+"' then sum(feb_19_bl) when '3'='"+action.getSelectedMonth()+"' then sum(march_19_bl) when '4'='"+action.getSelectedMonth()+"' then sum(april_18_bl) when '5'='"+action.getSelectedMonth()+"' then sum(may_18_bl) when '6'='"+action.getSelectedMonth()+"' then sum(june_18_bl) when '7'='"+action.getSelectedMonth()+"' then sum(july_18_bl) when '8'='"+action.getSelectedMonth()+"' then sum(august_18_bl) when '9'='"+action.getSelectedMonth()+"' then sum(sept_18_bl) when '10'='"+action.getSelectedMonth()+"' then sum(oct_18_bl) when '11'='"+action.getSelectedMonth()+"' then sum(nov_18_bl)  when '12'='"+action.getSelectedMonth()+"' then sum(dec_18_bl) end as old_bl   " +
		" ,case when '1'='"+action.getSelectedMonth()+"' then sum(jan_19_duty) when '2'='"+action.getSelectedMonth()+"' then sum(feb_19_duty) when '3'='"+action.getSelectedMonth()+"' then sum(march_19_duty) when '4'='"+action.getSelectedMonth()+"' then sum(april_18_duty) when '5'='"+action.getSelectedMonth()+"' then sum(may_18_duty) when '6'='"+action.getSelectedMonth()+"' then sum(june_18_duty) when '7'='"+action.getSelectedMonth()+"' then sum(july_18_duty) when '8'='"+action.getSelectedMonth()+"' then sum(august_18_duty) when '9'='"+action.getSelectedMonth()+"' then sum(sept_18_duty) when '10'='"+action.getSelectedMonth()+"' then sum(oct_18_duty) when '11'='"+action.getSelectedMonth()+"' then sum(nov_18_duty)  when '12'='"+action.getSelectedMonth()+"' then sum(dec_18_duty) end as old_duty " +
		" from retail.retail_shop_lifting_duty_19_20 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
		" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
		" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  group by x.district_id order by description ";

	}
	
	else{
		 
		reportQuery="" +
				"  " +
				" select  (select description from public.district d where d.districtid=x.district_id) as description, " +
				" sum(COALESCE(y.new_box,0))new_box,round(CAST(float8(sum(COALESCE(y.new_bl,0))) as numeric), 2) as new_bl, " +
				" round(CAST(float8(sum(COALESCE(y.new_duty,0))) as numeric), 2) as new_duty, round(CAST(float8(sum(COALESCE(y.coronafee,0))) as numeric), 2) as  coronafee," +
				" sum(COALESCE(y.old_box,0))old_box,sum(COALESCE(y.old_bl,0))old_bl,sum(COALESCE(y.old_duty,0))old_duty " +
				" ,case when sum(COALESCE(y.old_box,0))=0 then 0 else round(CAST(float8(((sum(COALESCE(y.new_box,0))/sum(COALESCE(y.old_box,0)))*100)) as numeric), 2) end as per_box " +
				" ,case when sum(COALESCE(y.old_bl,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE(y.new_bl,0))/sum(COALESCE(y.old_bl,0)))*100)) as numeric), 2)  end as per_bl " +
				" ,case when sum(COALESCE(y.old_duty,0))=0 then 0 else  round(CAST(float8(((sum(COALESCE((y.new_duty+y.coronafee),0))/sum(COALESCE(y.old_duty,0)))*100)) as numeric), 2) end as per_duty  " +
				" from retail.retail_shop x left join  " +
				" (select p.shop_id, sum(p.box)new_box,sum(p.bl)new_bl,sum(p.duty)new_duty,sum(p.coronafee)coronafee,sum(p.old_box)old_box,sum(p.old_bl)old_bl,sum(p.old_duty)old_duty from ( " +
				" select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,sum(cr.coronafee) as coronafee, 0 as old_box,0.0 as old_bl,0.0 as old_duty from ( " +
				" select br.shop_id,sum(br.box)box, sum(br.bl) as bl,sum(br.duty) as duty,sum(br.coronafee) as coronafee  " +
				" from  " +
				" ("
				+ "select  ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee ,a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
	 			" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
	 			" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 ar " +
	 			" where ar.vch_to='RT'   and " +
	 			" case when 4="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
	 			" when 1="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
	 			" when 2="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
	 			" when 3="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end  )a " +
	 			" ,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 b , distillery.packaging_details_20_21 d ,distillery.brand_registration_20_21 c " +
	 			" where  a.vch_gatepass_no=b.vch_gatepass_no  and b.int_brand_id=c.brand_id  and c.license_category not in  ('BEER','LAB','IMPORTED BEER'   )  and d.package_id=b.int_pckg_id  group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty,d.cesh "
	 			+ " union all select ((sum(b.dispatch_bottle)*d.quantity)/1000) as bl,((d.duty+d.adduty)*sum(b.dispatch_bottle)) as duty, (d.cesh*sum(b.dispatch_bottle)) as coronafee ,   a.shop_id,sum(b.dispatch_box) as box,sum(b.dispatch_bottle) as bottle,b.int_pckg_id  " +
				" from(select ar.int_fl2_fl2b_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to,ar.shop_id " +
				" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 ar " +
				" where ar.vch_to='RT' and ar.vch_from='FL2' and " +
				" case when 4="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
				" when 1="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
				" when 2="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
				" when 3="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
				" ,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b, distillery.packaging_details_19_20 d " +
				" where  a.dt_date>='2020-05-01' and   a.vch_gatepass_no=b.vch_gatepass_no and  d.package_id=b.int_pckg_id group by a.shop_id,b.int_pckg_id,d.quantity,d.duty,d.adduty,d.cesh"
				+ ")br " +
				"  " +
				" group by br.shop_id " +
				" )cr group by cr.shop_id " +
				" union all " +
				"  select cr.shop_id,sum(cr.box)box,sum(cr.bl)bl,sum(cr.duty)duty,0 as coronafee,0 as old_box,0.0 as old_bl,0.0 as old_duty from  " +
				" (select br.shop_id,sum(br.box)box,((sum(br.bottle)*d.quantity)/1000) as bl,((d.permit)*sum(br.bottle)) as duty from  " +
				" ("
				+ "select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
				" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
				" from fl2d.gatepass_to_districtwholesale_fl2d_20_21 ar where ar.vch_to='RT' and " +
				" case when 4="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
				" when 1="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
				" when 2="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
				" when 3="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
				" ,fl2d.fl2d_stock_trxn_20_21  b ,distillery.brand_registration_20_21  az where a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and    az.license_category not in  ('BEER','LAB','IMPORTED BEER'   )   group by a.shop_id, b.int_pckg_id"
				+ " union all select a.shop_id,sum(b.dispatch_box)box,sum(b.dispatch_bottle)bottle,sum(b.cal_duty) as duty,b.int_pckg_id from " +
				" (select ar.shop_id,ar.int_fl2d_id,ar.vch_gatepass_no,ar.dt_date,ar.vch_to  " +
				" from fl2d.gatepass_to_districtwholesale_fl2d_19_20 ar where ar.vch_to='RT' and " +
				" case when 4="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (1,2,3)" +
				" when 1="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (4,5,6)" +
				" when 2="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (7,8,9)" +
				" when 3="+action.getSelectedQuarter()+" then EXTRACT(MONTH FROM ar.dt_date) in (10,11,12) end   )a " +
				" ,fl2d.fl2d_stock_trxn_19_20  b ,distillery.brand_registration_20_21  az where a.dt_date>'2020-05-01' and  a.vch_gatepass_no=b.vch_gatepass_no and az.brand_id=b.int_brand_id and  az.strength > 8.00  group by a.shop_id, b.int_pckg_id"
						+ ") br, " +
				" distillery.packaging_details_20_21 d where br.int_pckg_id=d.package_id group by br.shop_id,d.quantity,d.permit)cr " +
				" group by cr.shop_id " +
				" union all  " +
				" select dr.shop_id:: text as shop_id, 0 as box, 0.0 as bl,0.0 as duty, 0 as coronafee," +
				" case when '1'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(april_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(may_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(june_18_box,0)) as numeric), 0)) when '2'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(july_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(august_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(sept_18_box ,0)) as numeric), 0)) when '3'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(oct_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(nov_18_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(dec_18_box ,0)) as numeric), 0)) when '4'='"+action.getSelectedQuarter()+"' then sum(round(CAST(float8(COALESCE(jan_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(feb_19_box ,0)) as numeric), 0) + round(CAST(float8(COALESCE(march_19_box ,0)) as numeric), 0)) end as old_box " +
				" ,case when '1'='"+action.getSelectedQuarter()+"' then sum(april_18_bl + may_18_bl + june_18_bl) when '2'='"+action.getSelectedQuarter()+"' then sum(july_18_bl+ august_18_bl + sept_18_bl) when '3'='"+action.getSelectedQuarter()+"' then sum(oct_18_bl + nov_18_bl + dec_18_bl) when '4'='"+action.getSelectedQuarter()+"' then sum(jan_19_bl + feb_19_bl+ march_19_bl) end as old_bl "+
				" ,case when '1'='"+action.getSelectedQuarter()+"' then sum(april_18_duty + may_18_duty + june_18_duty) when '2'='"+action.getSelectedQuarter()+"' then sum(july_18_duty+ august_18_duty + sept_18_duty) when '3'='"+action.getSelectedQuarter()+"' then sum(oct_18_duty + nov_18_duty + dec_18_duty) when '4'='"+action.getSelectedQuarter()+"' then sum(jan_19_duty + feb_19_duty+ march_19_duty) end as old_duty " +
				" from retail.retail_shop_lifting_duty_19_20 dr where dr.shop_type in ('Foreign Liquor','Model Shop') and dr.radio_type in  ('Foreign Liquor') group by shop_id " +
				" order by shop_id )p group by p.shop_id)y on x.serial_no::text=trim(y.shop_id)  " +
				" where x.vch_shop_type in ('Foreign Liquor','Model Shop')  group by x.district_id order by description ";
	
	 System.out.println("District Wise==== " +reportQuery);
	
	}
	String relativePath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
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
				.createSheet("FL Lifting Comparision District Wise Report");

		worksheet.setColumnWidth(0, 3000);
		worksheet.setColumnWidth(1, 5000);
		worksheet.setColumnWidth(2, 5000);
		worksheet.setColumnWidth(3, 5000);
		worksheet.setColumnWidth(4, 6000);
		worksheet.setColumnWidth(5, 6000);
		worksheet.setColumnWidth(6, 6000);
		worksheet.setColumnWidth(7, 9000);
		worksheet.setColumnWidth(8, 9000);
		worksheet.setColumnWidth(9, 6000);
		worksheet.setColumnWidth(10, 8000);
		worksheet.setColumnWidth(11, 6000);
		worksheet.setColumnWidth(12, 6000);
		worksheet.setColumnWidth(13, 6000);


		XSSFRow rowhead0 = worksheet.createRow((int) 0);
		XSSFCell cellhead0 = rowhead0.createCell((int) 0);
		cellhead0.setCellValue("FL Lifting Comparision District Wise");
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
		cellhead2.setCellValue("District");
		cellhead2.setCellStyle(cellStyle);

		XSSFCell cellhead3 = rowhead.createCell((int) 2);
		cellhead3.setCellValue("Cases of Last year sale");
		cellhead3.setCellStyle(cellStyle);
		
		XSSFCell cellhead4 = rowhead.createCell((int) 3);
		cellhead4.setCellValue("BL of Last year sale");
		cellhead4.setCellStyle(cellStyle);

		XSSFCell cellhead5 = rowhead.createCell((int) 4);
		cellhead5.setCellValue("Revenue of Last year sale");
		cellhead5.setCellStyle(cellStyle);
		
		XSSFCell cellhead6 = rowhead.createCell((int) 5);
		cellhead6.setCellValue("Cases of current year sale");
		cellhead6.setCellStyle(cellStyle);
		

		XSSFCell cellhead7 = rowhead.createCell((int) 6);
		cellhead7.setCellValue("BL of current year sale");
		cellhead7.setCellStyle(cellStyle);
		

		XSSFCell cellhead8 = rowhead.createCell((int) 7);
		cellhead8.setCellValue("Duty of current year sale");
		cellhead8.setCellStyle(cellStyle);
		
		XSSFCell cellhead9 = rowhead.createCell((int) 8);
		cellhead9.setCellValue("Add. Consd.Fee of current year sale");
		cellhead9.setCellStyle(cellStyle);
		

		XSSFCell cellhead10 = rowhead.createCell((int) 9);
		cellhead10.setCellValue("Revenue of current year sale");
		cellhead10.setCellStyle(cellStyle);
	
		XSSFCell cellhead11 = rowhead.createCell((int) 10);
		cellhead11.setCellValue("Cases of % increase");
		cellhead11.setCellStyle(cellStyle);
		

		XSSFCell cellhead12 = rowhead.createCell((int) 11);
		cellhead12.setCellValue("BL of % increase");
		cellhead12.setCellStyle(cellStyle);
		
		XSSFCell cellhead13 = rowhead.createCell((int) 12);
		cellhead13.setCellValue("Revenue of % increase");
		cellhead13.setCellStyle(cellStyle);
		
	

		int i = 0;
		
		while (rs.next()) 
		{
			cases_of_last_year_sale_ = cases_of_last_year_sale_ + rs.getDouble("old_box");
			bl_of_last_year_sale_ = bl_of_last_year_sale_ + rs.getDouble("old_bl");
			
			revenue_of_last_year_sale_ = revenue_of_last_year_sale_ + rs.getDouble("old_duty");
			cases_of_current_year_sale_ = cases_of_current_year_sale_ + rs.getDouble("new_box");
		
			bl_of_current_year_sale_ = bl_of_current_year_sale_ + rs.getDouble("new_bl");
			revenue_of_current_year_sale_ = revenue_of_current_year_sale_ + rs.getDouble("new_duty");
			
			
			new_revenue_ = rs.getDouble("new_duty")+rs.getDouble("coronafee");
			
			 corna_fee= corna_fee +rs.getDouble("coronafee");
			 total_new_revenue_=  revenue_of_current_year_sale_ + corna_fee ;
			 //double corna_fee=0;
		     //double total_new_revenue_=0;
			i++;
			
			k++; //
			XSSFRow row1 = worksheet.createRow((int) k); //
			XSSFCell cellA1 = row1.createCell((int) 0); //
			cellA1.setCellValue(k - 1); //
			
			
			XSSFCell cellB1 = row1.createCell((int) 1);
			cellB1.setCellValue(rs.getString("description"));
			
		
			XSSFCell cellC1 = row1.createCell((int) 2);
			cellC1.setCellValue(rs.getDouble("old_box"));
			
			XSSFCell cellD1 = row1.createCell((int) 3);
			cellD1.setCellValue(rs.getDouble("old_bl"));
			
			
			XSSFCell cellE1 = row1.createCell((int) 4);
			cellE1.setCellValue(rs.getDouble("old_duty"));
			
			XSSFCell cellF1 = row1.createCell((int) 5);
			cellF1.setCellValue(rs.getDouble("new_box"));
			
			XSSFCell cellG1 = row1.createCell((int) 6);
			cellG1.setCellValue(rs.getDouble("new_bl"));
			
			XSSFCell cellH1 = row1.createCell((int) 7);
			cellH1.setCellValue(rs.getDouble("new_duty"));
			
			XSSFCell cellI1 = row1.createCell((int) 8);
			cellI1.setCellValue(rs.getDouble("coronafee"));
			
			XSSFCell cellJ1 = row1.createCell((int) 9);
			cellJ1.setCellValue(new_revenue_);
			
			XSSFCell cellK1 = row1.createCell((int) 10);
			cellK1.setCellValue(rs.getDouble("per_box"));
			
			XSSFCell cellL1 = row1.createCell((int) 11);
			cellL1.setCellValue(rs.getDouble("per_bl"));
			
			XSSFCell cellM1 = row1.createCell((int) 12);
			cellM1.setCellValue(rs.getDouble("per_duty"));
			
		/*XSSFCell cellL1 = row1.createCell((int) 11);
			cellL1.setCellValue(rs.getDouble(""));*/
			
		}
		
		
		if(cases_of_last_year_sale_>0){
		cases_box_=((cases_of_current_year_sale_/cases_of_last_year_sale_)*100);
		}
		
		if(bl_of_last_year_sale_>0){
		bl_of_=((bl_of_current_year_sale_/bl_of_last_year_sale_)*100);
		}
		if(revenue_of_last_year_sale_>0){
		revenue_=((revenue_of_current_year_sale_/revenue_of_last_year_sale_)*100);
		}
		
		
		
		Random rand = new Random();
		int n = rand.nextInt(550) + 1;
		fileOut = new FileOutputStream(relativePath
				+ "//ExciseUp//MIS//Excel//" + n+"_"+action.getBwfl_id()+ "_FL_Lifting_Comparision_districtwise_2020.xls");

		action.setExlname(n+"_"+action.getBwfl_id()+"_FL_Lifting_Comparision_districtwise_2020.xls");
		XSSFRow row1 = worksheet.createRow((int) k + 1);
		
	
		XSSFCell cellA1 = row1.createCell((int) 0);
		cellA1.setCellValue(" "); 
		cellA1.setCellStyle(cellStyle);
		
		XSSFCell cellA2 = row1.createCell((int) 1); 
		cellA2.setCellValue("Total: "); 
		cellA2.setCellStyle(cellStyle); 
		
		XSSFCell cellA3 = row1.createCell((int) 2); 
		cellA3.setCellValue(Math.round(cases_of_last_year_sale_)); 
		cellA3.setCellStyle(cellStyle); 
		

		XSSFCell cellA4 = row1.createCell((int) 3); 
		cellA4.setCellValue(bl_of_last_year_sale_); 
		cellA4.setCellStyle(cellStyle); 
		
		XSSFCell cellA5 = row1.createCell((int) 4);
		cellA5.setCellValue(revenue_of_last_year_sale_);
		cellA5.setCellStyle(cellStyle);
		
		XSSFCell cellA6 = row1.createCell((int) 5);
		cellA6.setCellValue(cases_of_current_year_sale_);
		cellA6.setCellStyle(cellStyle);
		
		XSSFCell cellA7 = row1.createCell((int) 6);
		cellA7.setCellValue(bl_of_current_year_sale_); 
		cellA7.setCellStyle(cellStyle);
		
		XSSFCell cellA8 = row1.createCell((int) 7);
		cellA8.setCellValue(revenue_of_current_year_sale_);
		cellA8.setCellStyle(cellStyle);
		
		XSSFCell cellA9 = row1.createCell((int) 8);
		cellA9.setCellValue(corna_fee);
		cellA9.setCellStyle(cellStyle);
		
		
		XSSFCell cellA10 = row1.createCell((int) 9);
		cellA10.setCellValue(total_new_revenue_);
		cellA10.setCellStyle(cellStyle);
		
		XSSFCell cellA11 = row1.createCell((int) 10);
		cellA11.setCellStyle(cellStyle);
		
		XSSFCell cellA12 = row1.createCell((int) 11);
		cellA12.setCellStyle(cellStyle);
		
		
		XSSFCell cellA13 = row1.createCell((int) 12);
		cellA13.setCellStyle(cellStyle);
		
	
		
		
	
	

		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		flag = true;
		action.setExcelFlag(true);
		//con.close();

	} catch (Exception e) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						e.getMessage()+reportQuery,e.getMessage()+reportQuery));
		
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
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						e.getMessage()+reportQuery,e.getMessage()+reportQuery));
		
	}
	}

	return flag;

}
public ArrayList getBrandName() {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	CL_MCQ_Shop_Cummulative_FilteredAction act = (CL_MCQ_Shop_Cummulative_FilteredAction) facesContext.getApplication()
			.createValueBinding("#{CL_MCQ_Shop_Cummulative_FilteredAction}")
			.getValue(facesContext);
	ArrayList list = new ArrayList();
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	SelectItem item = new SelectItem();
	item.setLabel("--SELECT--");
	item.setValue(""); 
	list.add(item);
	String sql = "";

	try { 
		sql=" SELECT year, value, start_dt, end_dt FROM public.reporting_year";
		 
		con = ConnectionToDataBase.getConnection();
		ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

		while (rs.next()) {
			item = new SelectItem();
			item.setLabel(rs.getString("year"));
			item.setValue(rs.getString("value"));
			list.add(item);
		}	
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {

			if (con != null)
				con.close();
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
