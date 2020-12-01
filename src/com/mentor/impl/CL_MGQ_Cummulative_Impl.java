package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mentor.action.CL_MGQ_Cummulative_Action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class CL_MGQ_Cummulative_Impl 
{
	
	

public void printReportShopWise(CL_MGQ_Cummulative_Action act) {
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
			filterDistrict=" and b.district_id="+ Integer.parseInt(act.getDistid())+ "";
			
			
			
			if(act.getSectorId().equalsIgnoreCase("0"))
			{
				filterSector ="";
				
				if(act.getShopId().equalsIgnoreCase("0"))
				{
					filterShop="";
				}
				else
				{
					filterShop=" and b.serial_no='"+act.getShopId()+"' ";
				}
			}
				
			else
			{
				filterSector = " and b.sector='"+act.getSectorId()+"' ";
				
				
				if(act.getShopId().equalsIgnoreCase("0"))
				{
					filterShop="";
				}
				else
				{
					filterShop=" and b.serial_no='"+act.getShopId()+"' ";
				}
				
				
			}
			
			
			
		}
		else
		{
			//System.out.println("123");
			filterDistrict="";
			filterSector = "";
			filterShop="";
		}
		
		if(act.getBwfl_id().equals("19_20"))
		{
		reportQuery =
				" SELECT    b.serial_no, "+
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
				",(SELECT sector_name FROM public.mst_sector_master where sector_id= b.sector)  as sector "+
				" 	FROM public.district a left join retail.retail_shop_19_20_backup b on a.districtid=b.district_id left join  "+
				"     ( "+
				"    select bb.shopId,sum(bb.current_actual_bl) as current_actual_bl,sum(bb.cummulative_act_bl) as cummulative_act_bl"+
				"     from "+
				"      (  "+
				"         select aa.shopId,aa.dispatch_date,  aa.bl_1,aa.bl_2,aa.bl_3, "+					         
				"         case when EXTRACT(MONTH FROM aa.dispatch_date)="+act.getSelectedMonth()+"	"+
				"         then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as current_actual_bl, "+	
				
	   				"			case when "+
	   				"			aa.dispatch_date  between '2019/04/01' and '"+act.getDateSelected()+"' "+
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
				" 	from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b  			 "+
				" 	where  a.vch_from='CL2' and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null "+
				" 	group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id )x, 		"+				
				" 	distillery.box_size_details f,distillery.brand_registration_19_20 d,distillery.packaging_details_19_20 e  	"+
				" 	where x.int_brand_id=d.brand_id and e.box_id=f.box_id and x.package_id=e.package_id)m group by m.dispatch_date,m.shopId ,m.strength  	"+	
				" 	)aa group by aa.dispatch_date,aa.shopId, aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId  order by bb.shopId  	"+
				" 	)cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'  " + filterDistrict +filterSector +filterShop
				+"  group by b.sector, a.description,b.vch_name_of_shop, b.serial_no order by a.description ";


		}else {

			reportQuery =
					" SELECT    b.serial_no, "+
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
					",(SELECT sector_name FROM public.mst_sector_master where sector_id= b.sector)  as sector "+
					" 	FROM public.district a left join retail.retail_shop b on a.districtid=b.district_id left join  "+
					"     ( "+
					"    select bb.shopId,sum(bb.current_actual_bl) as current_actual_bl,sum(bb.cummulative_act_bl) as cummulative_act_bl"+
					"     from "+
					"      (  "+
					"         select aa.shopId,aa.dispatch_date,  aa.bl_1,aa.bl_2,aa.bl_3, "+					         
					"         case when EXTRACT(MONTH FROM aa.dispatch_date)="+act.getSelectedMonth()+"	"+
					"         then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as current_actual_bl, "+	
					
		   				"			case when "+
		   				"			aa.dispatch_date  between '2020/04/01' and '"+act.getDateSelected()+"' "+
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
					" 	(select x.dispatch_date,x.shopId,  bl,x.strength from  	"+				
					" 	(select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal ,(((sum(b.dispatch_bottle))*e.quantity)/1000) as bl ,d.strength	"+
					" 	from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+act.getBwfl_id()+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+act.getBwfl_id()+" b   ,distillery.brand_registration_20_21 d,distillery.packaging_details_20_21 e	,	distillery.box_size_details f   			 "+
					" 	where  a.vch_from='CL2' and  e.box_id=f.box_id and  b.int_brand_id=d.brand_id  and b.int_pckg_id=e.package_id  and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null "+
					" 	group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id,e.quantity ,d.strength union "+
				"	select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal,(((sum(b.dispatch_bottle))*e.quantity)/1000) as bl ,d.strength 	"+
					" 	from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b  ,distillery.brand_registration_19_20 d,distillery.packaging_details_19_20 e,	distillery.box_size_details f  		 "+
					" 	where  a.vch_from='CL2' and  e.box_id=f.box_id and  b.int_brand_id=d.brand_id  and b.int_pckg_id=e.package_id  and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and  dt_date>'2020-04-01' and a.shop_id is not null "+
					" 	group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id,e.quantity,d.strength "
					+ " )x  		"+				
					" 		"+
					" 	 )m group by m.dispatch_date,m.shopId ,m.strength  	"+	
					" 	)aa group by aa.dispatch_date,aa.shopId, aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId  order by bb.shopId  	"+
					" 	)cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'  " + filterDistrict +filterSector +filterShop
					+"  group by  a.description,b.vch_name_of_shop, b.serial_no order by a.description ";


			
		}
	 
		pst = con.prepareStatement(reportQuery);
		rs = pst.executeQuery();
		System.out.println(reportQuery);
		if (rs.next()) {

			rs = pst.executeQuery();
			Map parameters = new HashMap();
			parameters.put("REPORT_CONNECTION", con);
			parameters.put("SUBREPORT_DIR", relativePath + File.separator);
			parameters.put("image", relativePath + File.separator);
			parameters.put("radioType", act.getRadio());
			
			if(act.getBwfl_id().equals("19_20"))
			{
			if(act.getSelectedMonth()==1 ||act.getSelectedMonth()==2 ||act.getSelectedMonth()==3||act.getSelectedMonth()==4) {
				parameters.put("monthName", act.getMonthName()+"-2020");
			}else {
				parameters.put("monthName", act.getMonthName()+"-2019");
			}
			}else {

				if(act.getSelectedMonth()==1 ||act.getSelectedMonth()==2 ||act.getSelectedMonth()==3||act.getSelectedMonth()==4) {
					parameters.put("monthName", act.getMonthName()+"-2021");
				}else {
					parameters.put("monthName", act.getMonthName()+"-2020");
				}
				
			}

			JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

			jasperReport = (JasperReport) JRLoader.loadObject(relativePath
					+ File.separator + "CL_MGQ_AchievtShopWise_Cummulative.jasper");

			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, jrRs);
			Random rand = new Random();
			int n = rand.nextInt(250) + 1;
			JasperExportManager.exportReportToPdfFile(print,
					relativePathpdf + File.separator
							+ "CL_MGQ_AchievtShopWise_Cummulative" + "-" + n + ".pdf");
			act.setPdfName("CL_MGQ_AchievtShopWise_Cummulative" + "-" + n + ".pdf");
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



public boolean excelShopWise(CL_MGQ_Cummulative_Action action) throws ParseException {

	Connection con = null;
	
//System.out.println("action.getDateSelected()===="+action.getDateSelected());
	double current_bl_per = 0;
	double current_bl = 0;
	
	double commulative_bl = 0;
	double commulative_bl_per = 0;
	

	double yearly_mgq = 0;
	double monthly_mgq = 0;

	DecimalFormat diciformatter = new DecimalFormat("#.##");
	
	String filterDistrict = "";
	 String filterSector="";
	 String filterShop="";
	
	// Date date11=new SimpleDateFormat("dd/MM/yyyy").parse(action.getDateSelected());
	 
	 //System.out.println("action.getDateSelected()==date11=="+date11);
	
	if(!action.getDistid().equalsIgnoreCase("99") && !action.getDistid().equalsIgnoreCase("9999"))
	{
		filterDistrict=" and b.district_id="+ Integer.parseInt(action.getDistid())+ "";
		
		
		
		if(action.getSectorId().equalsIgnoreCase("0"))
		{
			filterSector ="";
			
			if(action.getShopId().equalsIgnoreCase("0"))
			{
				filterShop="";
			}
			else
			{
				filterShop=" and b.serial_no='"+action.getShopId()+"' ";
			}
		}
			
		else
		{
			filterSector = " and b.sector='"+action.getSectorId()+"' ";
			
			
			if(action.getShopId().equalsIgnoreCase("0"))
			{
				filterShop="";
			}
			else
			{
				filterShop=" and b.serial_no='"+action.getShopId()+"' ";
			}
			
			
		}
		
		
		
	}
	else
	{
		 
		filterDistrict="";
		filterSector = "";
		filterShop="";
	}

	
	

/*String	reportQuery =
" SELECT    b.serial_no, "+
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
",(SELECT sector_name FROM public.mst_sector_master where sector_id= b.sector)  as sector "+
" 	FROM public.district a left join retail.retail_shop b on a.districtid=b.district_id left join  "+
"     ( "+
"    select bb.shopId,sum(bb.current_actual_bl) as current_actual_bl,sum(bb.cummulative_act_bl) as cummulative_act_bl"+
"     from "+
"      (  "+
"         select aa.shopId,aa.dispatch_date,  aa.bl_1,aa.bl_2,aa.bl_3, "+					         
"         case when EXTRACT(MONTH FROM aa.dispatch_date)="+action.getSelectedMonth()+"	"+// enter month
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
" 	)cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'  " + filterDistrict +filterSector +filterShop
+"  group by  a.description,b.vch_name_of_shop, b.serial_no order by a.description ";*/
	String reportQuery ="";

	if(action.getBwfl_id().equals("19_20"))
	{
	reportQuery =
			" SELECT    b.serial_no, "+
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
					",(SELECT sector_name FROM public.mst_sector_master where sector_id= b.sector)  as sector "+
					" 	FROM public.district a left join retail.retail_shop_19_20_backup b on a.districtid=b.district_id left join  "+
					"     ( "+
					"    select bb.shopId,sum(bb.current_actual_bl) as current_actual_bl,sum(bb.cummulative_act_bl) as cummulative_act_bl"+
					"     from "+
					"      (  "+
					"         select aa.shopId,aa.dispatch_date,  aa.bl_1,aa.bl_2,aa.bl_3, "+					         
					"         case when EXTRACT(MONTH FROM aa.dispatch_date)="+action.getSelectedMonth()+"	"+
					"         then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as current_actual_bl, "+	
					
		   				"			case when "+
		   				"			aa.dispatch_date  between '2019/04/01' and '"+action.getDateSelected()+"' "+
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
					" 	from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b  			 "+
					" 	where  a.vch_from='CL2' and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null "+
					" 	group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id )x, 		"+				
					" 	distillery.box_size_details f,distillery.brand_registration_19_20 d,distillery.packaging_details_19_20 e  	"+
					" 	where x.int_brand_id=d.brand_id and e.box_id=f.box_id and x.package_id=e.package_id)m group by m.dispatch_date,m.shopId ,m.strength  	"+	
					" 	)aa group by aa.dispatch_date,aa.shopId, aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId  order by bb.shopId  	"+
					" 	)cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'  " + filterDistrict +filterSector +filterShop
					+"  group by b.sector, a.description,b.vch_name_of_shop, b.serial_no order by a.description ";


	}else {

		reportQuery =
				" SELECT    b.serial_no, "+
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
				",(SELECT sector_name FROM public.mst_sector_master where sector_id= b.sector)  as sector "+
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
				" 	(select x.dispatch_date,x.shopId,  bl,x.strength from  	"+				
				" 	(select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal ,(((sum(b.dispatch_bottle))*e.quantity)/1000) as bl ,d.strength	"+
				" 	from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+action.getBwfl_id()+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+action.getBwfl_id()+" b   ,distillery.brand_registration_20_21 d,distillery.packaging_details_20_21 e ,distillery.box_size_details f		 			 "+
				" 	where  a.vch_from='CL2' 	and  e.box_id=f.box_id and  b.int_brand_id=d.brand_id  and b.int_pckg_id=e.package_id  and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null "+
				" 	group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id,e.quantity,d.strength  union "+
			"	select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal,(((sum(b.dispatch_bottle))*e.quantity)/1000) as bl ,d.strength 	"+
				" 	from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b  ,distillery.brand_registration_19_20 d,distillery.packaging_details_19_20 e	,distillery.box_size_details f			 "+
				" 	where  a.vch_from='CL2' 	and  e.box_id=f.box_id and  b.int_brand_id=d.brand_id  and b.int_pckg_id=e.package_id  and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and  dt_date>'2020-04-01' and a.shop_id is not null "+
				" 	group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id,e.quantity,d.strength "
				+ " )x  )m group by m.dispatch_date,m.shopId ,m.strength  	"+	
				" 	)aa group by aa.dispatch_date,aa.shopId, aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId  order by bb.shopId  	"+
				" 	)cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'  " + filterDistrict +filterSector +filterShop
				+"  group by  a.description,b.vch_name_of_shop, b.serial_no order by a.description ";


		
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
				.createSheet("Cl2 Cummulative MGQ  Report");

		worksheet.setColumnWidth(0, 3000);
		worksheet.setColumnWidth(1, 5000);
		worksheet.setColumnWidth(2, 5000);
		worksheet.setColumnWidth(3, 15000);
		worksheet.setColumnWidth(4, 5000);
		worksheet.setColumnWidth(5, 5000);
		worksheet.setColumnWidth(6, 5000);
		worksheet.setColumnWidth(7, 5000);
		worksheet.setColumnWidth(8, 5000);
		worksheet.setColumnWidth(9, 5000);


		XSSFRow rowhead0 = worksheet.createRow((int) 0);
		XSSFCell cellhead0 = rowhead0.createCell((int) 0);
		cellhead0.setCellValue("CL -Cummulative MGQ Achievement ShopWise");
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
		cellhead6.setCellValue("Yearly MGQ");
		cellhead6.setCellStyle(cellStyle);
		

		XSSFCell cellhead7 = rowhead.createCell((int) 6);
		cellhead7.setCellValue("Monthly MGQ");
		cellhead7.setCellStyle(cellStyle);
		

		XSSFCell cellhead8 = rowhead.createCell((int) 7);
		cellhead8.setCellValue(action.getMonthName()+" "+"Month Actual");
		cellhead8.setCellStyle(cellStyle);
		
		XSSFCell cellhead9 = rowhead.createCell((int) 8);
		cellhead9.setCellValue(action.getMonthName()+" "+" Month %");
		cellhead9.setCellStyle(cellStyle);
		

		XSSFCell cellhead10 = rowhead.createCell((int) 9);
		cellhead10.setCellValue("Commulative Actual");
		cellhead10.setCellStyle(cellStyle);
		
		XSSFCell cellhead11 = rowhead.createCell((int) 10);
		cellhead11.setCellValue("Commulative %");
		cellhead11.setCellStyle(cellStyle);
		

		int i = 0;
		
		while (rs.next()) 
		{
			current_bl = current_bl + rs.getDouble("current_actual_bl");
			current_bl_per = current_bl_per + rs.getDouble("current_actual_bl_per");
			
			commulative_bl = commulative_bl + rs.getDouble("cummulative_act_bl");
			commulative_bl_per = commulative_bl_per + rs.getDouble("cummulative_act_bl_per");
		
			yearly_mgq = yearly_mgq + rs.getDouble("yearly_mgq");
			monthly_mgq = monthly_mgq + rs.getDouble("monthly_mgq");
			
			k++; //
			XSSFRow row1 = worksheet.createRow((int) k); //
			XSSFCell cellA1 = row1.createCell((int) 0); //
			cellA1.setCellValue(k - 1); //
			
			
			XSSFCell cellB1 = row1.createCell((int) 1);
			cellB1.setCellValue(rs.getString("description"));
			
			XSSFCell cellC1 = row1.createCell((int) 2);
			cellC1.setCellValue(rs.getString("serial_no"));
			
			XSSFCell cellD1 = row1.createCell((int) 3);
			cellD1.setCellValue(rs.getString("vch_name_of_shop"));
			
			
			XSSFCell cellE1 = row1.createCell((int) 4);
			cellE1.setCellValue(rs.getString("sector"));
			
			XSSFCell cellF1 = row1.createCell((int) 5);
			cellF1.setCellValue(rs.getString("yearly_mgq"));
			
			XSSFCell cellG1 = row1.createCell((int) 6);
			cellG1.setCellValue(rs.getString("monthly_mgq"));
			
			XSSFCell cellH1 = row1.createCell((int) 7);
			cellH1.setCellValue(rs.getDouble("current_actual_bl"));
			
			XSSFCell cellI1 = row1.createCell((int) 8);
			cellI1.setCellValue(rs.getString("current_actual_bl_per"));
			
			XSSFCell cellJ1 = row1.createCell((int) 9);
			cellJ1.setCellValue(rs.getDouble("cummulative_act_bl"));
			
			XSSFCell cellK1 = row1.createCell((int) 10);
			cellK1.setCellValue(rs.getDouble("cummulative_act_bl_per"));
		
	
		

		}
		Random rand = new Random();
		int n = rand.nextInt(550) + 1;
		fileOut = new FileOutputStream(relativePath
				+ "//ExciseUp//MIS//Excel//" + action.getRadio() + "-" + n
				+ "CL_MGQ_Achievement.xls");

		action.setExlname(action.getRadio() + "-" + n);
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
		cellA5.setCellValue("total:");
		cellA5.setCellStyle(cellStyle);
		
		XSSFCell cellA6 = row1.createCell((int) 5);
		cellA6.setCellValue(yearly_mgq);
		cellA6.setCellStyle(cellStyle);
		
		XSSFCell cellA7 = row1.createCell((int) 6);
		cellA7.setCellValue(monthly_mgq); 
		cellA7.setCellStyle(cellStyle);
		
		XSSFCell cellA8 = row1.createCell((int) 7);
		cellA8.setCellValue(current_bl);
		cellA8.setCellStyle(cellStyle);
		
		XSSFCell cellA9 = row1.createCell((int) 8);
		cellA9.setCellValue("");
		cellA9.setCellStyle(cellStyle);
		
		
		XSSFCell cellA10 = row1.createCell((int) 9);
		cellA10.setCellValue(commulative_bl);
		cellA10.setCellStyle(cellStyle);
	
	
	


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

public ArrayList getShop(CL_MGQ_Cummulative_Action act, String o) {

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
				" where       district_id="+Integer.parseInt(act.getDistid())+" and   "
				+ "  vch_shop_type='Country Liquor'" +filterSector +
				" order by trim(vch_name_of_shop)";
		
		
		
		
		

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


public ArrayList getShopL(CL_MGQ_Cummulative_Action act, String o) {

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
				" where       district_id="+Integer.parseInt(o)+" and   "
				+ "  vch_shop_type='Country Liquor'" +
				" order by trim(vch_name_of_shop)";
		
		
		
		
		

		conn = ConnectionToDataBase.getConnection();
		pstmt = conn.prepareStatement(query);

		
		System.out.println("shop list without sector by sector===="+query);
		
		
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

public ArrayList getSectorList(CL_MGQ_Cummulative_Action action, String districtid)
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
		String query = " SELECT month_id, description	FROM public.month_master order by month_id ";

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









public void printReportDistrictWise(CL_MGQ_Cummulative_Action act) {
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
 
		if(act.getBwfl_id().equals("19_20"))
		{
		reportQuery = 	"  select bb.description,bb.yearly_mgq,bb.monthly_mgq,bb.current_actual_bl, bb.cummulative_act_bl,	"+
				 
				 "  round(CAST(float8(bb.current_actual_bl_per) as numeric), 2)as  current_actual_bl_per,	"+
				 "      round(CAST(float8( bb.cummulative_act_bl_per ) as numeric), 2)as  cummulative_act_bl_per  	"+           
				                  
				     " from ( select x.description,x.yearly_mgq,x.monthly_mgq,x.current_actual_bl, x.cummulative_act_bl,	"+
				     "	case when x.monthly_mgq::int !=0 then x.current_actual_bl*100/x.monthly_mgq else 0 end  as current_actual_bl_per,	"+
								"  case when x.yearly_mgq::int !=0 then x.cummulative_act_bl*100/x.yearly_mgq else 0 end as cummulative_act_bl_per 	"+

				  
						 " from	"+

				 "  (select zz.description,sum(zz.yearly_mgq) as yearly_mgq,sum(zz.monthly_mgq) as monthly_mgq,    	"+ 
						 "  sum(zz.current_actual_bl) as current_actual_bl ,sum(zz.cummulative_act_bl) as cummulative_act_bl 	"+  
				  "  from ( SELECT    b.serial_no,  b.vch_name_of_shop,a.description,  COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq, 	"+
						 "  COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,  COALESCE(sum(cc.current_actual_bl),0) as current_actual_bl, 	"+
				 "  case when sum(cc.current_actual_bl)!=0 and sum(cc.current_actual_bl) is not null   and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null 	"+
						 "  then round(CAST(float8((sum(cc.current_actual_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2)  else 0 end as current_actual_bl_per, 	"+
				 "  COALESCE(sum(cc.cummulative_act_bl),0) as cummulative_act_bl,  case when sum(cc.cummulative_act_bl)!=0 and sum(cc.cummulative_act_bl) is not null 	"+
						 "  and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null  then round(CAST(float8((sum(cc.cummulative_act_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2)) 	"+
						 "  as numeric), 2)  else 0 end as cummulative_act_bl_per  	FROM public.district a left join retail.retail_shop_19_20_backup b on a.districtid=b.district_id left join    	"+
						 "  (     select bb.shopId,sum(bb.current_actual_bl) as current_actual_bl,sum(bb.cummulative_act_bl) as cummulative_act_bl    	"+
						 "  from     	"+
				 "  (           select aa.shopId,aa.dispatch_date,  aa.bl_1,aa.bl_2,aa.bl_3,       " +
				 "   case when EXTRACT(MONTH FROM aa.dispatch_date)="+act.getSelectedMonth()+"	   	"+
						 "  then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as current_actual_bl, 			case when 		"+
				 "  aa.dispatch_date  between '2019/04/01' and '"+act.getDateSelected()+"' "+	
				 " 		 then 			(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  	"+		
				 "  end 			as cummulative_act_bl   	from( 	  	select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,       	"+
						 "  case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1,  	case when  m.strength=36 then sum(bl) end as bl_2 ,  	"+
						 "  case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3  		"+
						 "  from	 	(select x.dispatch_date,x.shopId,(((x.bottal)*f.qnt_ml_detail)/1000) as bl,d.strength from  	"+
						 "  (select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal  		"+
						 "  from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+act.getBwfl_id()+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+act.getBwfl_id()+" b  		"+	
				  
				 "  where  a.vch_from='CL2' and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null  	"+
						  "  group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id )x, 		 	distillery.box_size_details f,distillery.brand_registration_"+act.getBwfl_id()+" d,distillery.packaging_details_"+act.getBwfl_id()+" e  	"+
				  "  where x.int_brand_id=d.brand_id and e.box_id=f.box_id and x.package_id=e.package_id)m group by m.dispatch_date,m.shopId ,m.strength  	 	)aa group by aa.dispatch_date,aa.shopId,	"+
				  "   aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId  order by bb.shopId  	"+
				  "   )cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'   	"+
						  "  group by  a.description,b.vch_name_of_shop, b.serial_no order by a.description   	"+
				       
				 
				 "  )zz group by 	  zz.description 	" +
				 "   order by zz.description )x order by x.description )bb 	"+
				  "   order by bb.description ";
		}else {

			reportQuery = 	"  select bb.description,bb.yearly_mgq,bb.monthly_mgq,bb.current_actual_bl, bb.cummulative_act_bl,	"+
					 
					 "  round(CAST(float8(bb.current_actual_bl_per) as numeric), 2)as  current_actual_bl_per,	"+
					 "      round(CAST(float8( bb.cummulative_act_bl_per ) as numeric), 2)as  cummulative_act_bl_per  	"+           
					                  
					     " from ( select x.description,x.yearly_mgq,x.monthly_mgq,x.current_actual_bl, x.cummulative_act_bl,	"+
					     "	case when x.monthly_mgq::int !=0 then x.current_actual_bl*100/x.monthly_mgq else 0 end  as current_actual_bl_per,	"+
									"  case when x.yearly_mgq::int !=0 then x.cummulative_act_bl*100/x.yearly_mgq else 0 end as cummulative_act_bl_per 	"+

					  
							 " from	"+

					 "  (select zz.description,sum(zz.yearly_mgq) as yearly_mgq,sum(zz.monthly_mgq) as monthly_mgq,    	"+ 
							 "  sum(zz.current_actual_bl) as current_actual_bl ,sum(zz.cummulative_act_bl) as cummulative_act_bl 	"+  
					  "  from ( SELECT    b.serial_no,  b.vch_name_of_shop,a.description,  COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq, 	"+
							 "  COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,  COALESCE(sum(cc.current_actual_bl),0) as current_actual_bl, 	"+
					 "  case when sum(cc.current_actual_bl)!=0 and sum(cc.current_actual_bl) is not null   and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null 	"+
							 "  then round(CAST(float8((sum(cc.current_actual_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2)  else 0 end as current_actual_bl_per, 	"+
					 "  COALESCE(sum(cc.cummulative_act_bl),0) as cummulative_act_bl,  case when sum(cc.cummulative_act_bl)!=0 and sum(cc.cummulative_act_bl) is not null 	"+
							 "  and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null  then round(CAST(float8((sum(cc.cummulative_act_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2)) 	"+
							 "  as numeric), 2)  else 0 end as cummulative_act_bl_per  	FROM public.district a left join retail.retail_shop b on a.districtid=b.district_id left join    	"+
							 "  (     select bb.shopId,sum(bb.current_actual_bl) as current_actual_bl,sum(bb.cummulative_act_bl) as cummulative_act_bl    	"+
							 "  from     	"+
					 "  (           select aa.shopId,aa.dispatch_date,  aa.bl_1,aa.bl_2,aa.bl_3,       " +
					 "   case when EXTRACT(MONTH FROM aa.dispatch_date)="+act.getSelectedMonth()+"	   	"+
							 "  then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as current_actual_bl, 			case when 		"+
					 "  aa.dispatch_date  between '2020/04/01' and '"+act.getDateSelected()+"' "+	
					 " 		 then 			(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  	"+		
					 "  end 			as cummulative_act_bl   	from( 	  	select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,       	"+
							 "  case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1,  	case when  m.strength=36 then sum(bl) end as bl_2 ,  	"+
							 "  case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3  		"+
							 "  from	 	(select x.dispatch_date,x.shopId,    bl,x.strength from  	"+
							 "  (select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal  	,(((sum(b.dispatch_bottle))*e.quantity)/1000) as bl ,d.strength	"+
							 "  from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+act.getBwfl_id()+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+act.getBwfl_id()+" b   ,distillery.brand_registration_20_21 d,distillery.packaging_details_20_21 e ,distillery.box_size_details f			"+	
					  
					 "  where  a.vch_from='CL2' and  e.box_id=f.box_id and  b.int_brand_id=d.brand_id  and b.int_pckg_id=e.package_id   and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null  	"+
							  "  group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id,e.quantity,d.strength union "+
							 " select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal  	,(((sum(b.dispatch_bottle))*e.quantity)/1000) as bl,d.strength 	"+
								 "  from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b ,distillery.brand_registration_19_20 d,distillery.packaging_details_19_20 e , distillery.box_size_details f	 		"+	
						  
						 "  where  a.vch_from='CL2'  and  e.box_id=f.box_id and  b.int_brand_id=d.brand_id  and b.int_pckg_id=e.package_id   and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and  dt_date>'2020-04-01' and a.shop_id is not null  	"+
								  "  group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id ,e.quantity,d.strength "
							  + ")x  	 "+
					  "   )m group by m.dispatch_date,m.shopId ,m.strength  	 	)aa group by aa.dispatch_date,aa.shopId,	"+
					  "   aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId  order by bb.shopId  	"+
					  "   )cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'   	"+
							  "  group by  a.description,b.vch_name_of_shop, b.serial_no order by a.description   	"+
					       
					 
					 "  )zz group by 	  zz.description 	" +
					 "   order by zz.description )x order by x.description )bb 	"+
					  "   order by bb.description ";
			
		}
				  
 	pst = con.prepareStatement(reportQuery);
		rs = pst.executeQuery();
System.out.println(reportQuery);
		if (rs.next()) {

			rs = pst.executeQuery();
			Map parameters = new HashMap();
			parameters.put("REPORT_CONNECTION", con);
			parameters.put("SUBREPORT_DIR", relativePath + File.separator);
			parameters.put("image", relativePath + File.separator);
			parameters.put("radioType", act.getRadio());
			if(act.getBwfl_id().equals("19_20")) {
				parameters.put("monthName", act.getMonthName()+" 2019-20");
			}else {
			parameters.put("monthName", act.getMonthName()+" 2020-21");
			}
			JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

			jasperReport = (JasperReport) JRLoader.loadObject(relativePath
					+ File.separator + "CL_MGQ_DsitrictWise_Cummulative.jasper");

			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, jrRs);
			Random rand = new Random();
			int n = rand.nextInt(250) + 1;
			JasperExportManager.exportReportToPdfFile(print,
					relativePathpdf + File.separator
							+ "CL_MGQ_DsitrictWise_Cummulative" + "-" + n + ".pdf");
			act.setPdfName("CL_MGQ_DsitrictWise_Cummulative" + "-" + n + ".pdf");
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




public boolean excelDitrictWise(CL_MGQ_Cummulative_Action action) throws ParseException {

	Connection con = null;
	double current_bl_per = 0;
	double current_bl = 0;
	
	double commulative_bl = 0;
	double commulative_bl_per = 0;
	

	double yearly_mgq = 0;
	double monthly_mgq = 0;


	

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
	String reportQuery =   	" ";

	if(action.getBwfl_id().equals("19_20"))
	{
	  reportQuery =   	"  select bb.description,bb.yearly_mgq,bb.monthly_mgq,bb.current_actual_bl, bb.cummulative_act_bl,	"+
	 
	 "  round(CAST(float8(bb.current_actual_bl_per) as numeric), 2)as  current_actual_bl_per,	"+
	 "      round(CAST(float8( bb.cummulative_act_bl_per ) as numeric), 2)as  cummulative_act_bl_per  	"+           
	                  
	     " from ( select x.description,x.yearly_mgq,x.monthly_mgq,x.current_actual_bl, x.cummulative_act_bl,	"+
	     "	case when x.monthly_mgq::int !=0 then x.current_actual_bl*100/x.monthly_mgq else 0 end  as current_actual_bl_per,	"+
					"  case when x.yearly_mgq::int !=0 then x.cummulative_act_bl*100/x.yearly_mgq else 0 end as cummulative_act_bl_per 	"+

	  
			 " from	"+

	 "  (select zz.description,sum(zz.yearly_mgq) as yearly_mgq,sum(zz.monthly_mgq) as monthly_mgq,    	"+ 
			 "  sum(zz.current_actual_bl) as current_actual_bl ,sum(zz.cummulative_act_bl) as cummulative_act_bl 	"+  
	  "  from ( SELECT    b.serial_no,  b.vch_name_of_shop,a.description,  COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq, 	"+
			 "  COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,  COALESCE(sum(cc.current_actual_bl),0) as current_actual_bl, 	"+
	 "  case when sum(cc.current_actual_bl)!=0 and sum(cc.current_actual_bl) is not null   and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null 	"+
			 "  then round(CAST(float8((sum(cc.current_actual_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2)  else 0 end as current_actual_bl_per, 	"+
	 "  COALESCE(sum(cc.cummulative_act_bl),0) as cummulative_act_bl,  case when sum(cc.cummulative_act_bl)!=0 and sum(cc.cummulative_act_bl) is not null 	"+
			 "  and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null  then round(CAST(float8((sum(cc.cummulative_act_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2)) 	"+
			 "  as numeric), 2)  else 0 end as cummulative_act_bl_per  	FROM public.district a left join retail.retail_shop_19_20_backup b on a.districtid=b.district_id left join    	"+
			 "  (     select bb.shopId,sum(bb.current_actual_bl) as current_actual_bl,sum(bb.cummulative_act_bl) as cummulative_act_bl    	"+
			 "  from     	"+
	 "  (           select aa.shopId,aa.dispatch_date,  aa.bl_1,aa.bl_2,aa.bl_3,       " +
	 "   case when EXTRACT(MONTH FROM aa.dispatch_date)="+action.getSelectedMonth()+"	   	"+
			 "  then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as current_actual_bl, 			case when 		"+
	 "  aa.dispatch_date  between '2019/04/01' and '"+action.getDateSelected()+"' "+	
	 " 		 then 			(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  	"+		
	 "  end 			as cummulative_act_bl   	from( 	  	select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,       	"+
			 "  case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1,  	case when  m.strength=36 then sum(bl) end as bl_2 ,  	"+
			 "  case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3  		"+
			 "  from	 	(select x.dispatch_date,x.shopId,(((x.bottal)*f.qnt_ml_detail)/1000) as bl,d.strength from  	"+
			 "  (select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal  		"+
			 "  from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+action.getBwfl_id()+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+action.getBwfl_id()+" b  		"+	
	  
	 "  where  a.vch_from='CL2' and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null  	"+
			  "  group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id )x, 		 	distillery.box_size_details f,distillery.brand_registration_"+action.getBwfl_id()+" d,distillery.packaging_details_"+action.getBwfl_id()+" e  	"+
	  "  where x.int_brand_id=d.brand_id and e.box_id=f.box_id and x.package_id=e.package_id)m group by m.dispatch_date,m.shopId ,m.strength  	 	)aa group by aa.dispatch_date,aa.shopId,	"+
	  "   aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId  order by bb.shopId  	"+
	  "   )cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'   	"+
			  "  group by  a.description,b.vch_name_of_shop, b.serial_no order by a.description   	"+
	       
	 
	 "  )zz group by 	  zz.description 	" +
	 "   order by zz.description )x order by x.description )bb 	"+
	  "   order by bb.description ";
	  
	  
	}else {

		reportQuery = 	"  select bb.description,bb.yearly_mgq,bb.monthly_mgq,bb.current_actual_bl, bb.cummulative_act_bl,	"+
				 
				 "  round(CAST(float8(bb.current_actual_bl_per) as numeric), 2)as  current_actual_bl_per,	"+
				 "      round(CAST(float8( bb.cummulative_act_bl_per ) as numeric), 2)as  cummulative_act_bl_per  	"+           
				                  
				     " from ( select x.description,x.yearly_mgq,x.monthly_mgq,x.current_actual_bl, x.cummulative_act_bl,	"+
				     "	case when x.monthly_mgq::int !=0 then x.current_actual_bl*100/x.monthly_mgq else 0 end  as current_actual_bl_per,	"+
								"  case when x.yearly_mgq::int !=0 then x.cummulative_act_bl*100/x.yearly_mgq else 0 end as cummulative_act_bl_per 	"+

				  
						 " from	"+

				 "  (select zz.description,sum(zz.yearly_mgq) as yearly_mgq,sum(zz.monthly_mgq) as monthly_mgq,    	"+ 
						 "  sum(zz.current_actual_bl) as current_actual_bl ,sum(zz.cummulative_act_bl) as cummulative_act_bl 	"+  
				  "  from ( SELECT    b.serial_no,  b.vch_name_of_shop,a.description,  COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq, 	"+
						 "  COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,  COALESCE(sum(cc.current_actual_bl),0) as current_actual_bl, 	"+
				 "  case when sum(cc.current_actual_bl)!=0 and sum(cc.current_actual_bl) is not null   and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null 	"+
						 "  then round(CAST(float8((sum(cc.current_actual_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2)  else 0 end as current_actual_bl_per, 	"+
				 "  COALESCE(sum(cc.cummulative_act_bl),0) as cummulative_act_bl,  case when sum(cc.cummulative_act_bl)!=0 and sum(cc.cummulative_act_bl) is not null 	"+
						 "  and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null  then round(CAST(float8((sum(cc.cummulative_act_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2)) 	"+
						 "  as numeric), 2)  else 0 end as cummulative_act_bl_per  	FROM public.district a left join retail.retail_shop b on a.districtid=b.district_id left join    	"+
						 "  (     select bb.shopId,sum(bb.current_actual_bl) as current_actual_bl,sum(bb.cummulative_act_bl) as cummulative_act_bl    	"+
						 "  from     	"+
				 "  (           select aa.shopId,aa.dispatch_date,  aa.bl_1,aa.bl_2,aa.bl_3,       " +
				 "   case when EXTRACT(MONTH FROM aa.dispatch_date)="+action.getSelectedMonth()+"	   	"+
						 "  then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as current_actual_bl, 			case when 		"+
				 "  aa.dispatch_date  between '2020/04/01' and '"+action.getDateSelected()+"' "+	
				 " 		 then 			(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  	"+		
				 "  end 			as cummulative_act_bl   	from( 	  	select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,       	"+
						 "  case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1,  	case when  m.strength=36 then sum(bl) end as bl_2 ,  	"+
						 "  case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3  		"+
						 "  from	 	(select x.dispatch_date,x.shopId,    bl,x.strength from  	"+
						 "  (select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal  	,(((sum(b.dispatch_bottle))*e.quantity)/1000) as bl ,d.strength	"+
						 "  from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+action.getBwfl_id()+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+action.getBwfl_id()+" b   ,distillery.brand_registration_20_21 d,distillery.packaging_details_20_21 e , 		 	distillery.box_size_details f		"+	
				  
				 "  where  a.vch_from='CL2'  and   e.box_id=f.box_id  and  b.int_brand_id=d.brand_id  and b.int_pckg_id=e.package_id   and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null  	"+
						  "  group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id,e.quantity,d.strength union "+
						 " select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal  	,(((sum(b.dispatch_bottle))*e.quantity)/1000) as bl ,d.strength	"+
							 "  from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b ,distillery.brand_registration_19_20 d,distillery.packaging_details_19_20 e   , 		 	distillery.box_size_details f		"+	
					  
					 "  where  a.vch_from='CL2'  and   e.box_id=f.box_id and  b.int_brand_id=d.brand_id  and b.int_pckg_id=e.package_id   and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and  dt_date>'2020-04-01' and a.shop_id is not null  	"+
							  "  group by a.shop_id, a.dt_date,b.int_pckg_id,b.int_brand_id,e.quantity,d.strength "
						  + ")x	"+
				  "   )m group by m.dispatch_date,m.shopId ,m.strength  	 	)aa group by aa.dispatch_date,aa.shopId,	"+
				  "   aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId  order by bb.shopId  	"+
				  "   )cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'   	"+
						  "  group by  a.description,b.vch_name_of_shop, b.serial_no order by a.description   	"+
				       
				 
				 "  )zz group by 	  zz.description 	" +
				 "   order by zz.description )x order by x.description )bb 	"+
				  "   order by bb.description ";
		
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
				.createSheet("Cl2 Cummulative MGQ Consolidated Report");

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


		XSSFRow rowhead0 = worksheet.createRow((int) 0);
		XSSFCell cellhead0 = rowhead0.createCell((int) 0);
		cellhead0.setCellValue("CL -Cummulative MGQ Achievement District Wise");
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

		XSSFCell cellhead3 = rowhead.createCell((int) 2);
		cellhead3.setCellValue("Yearly MGQ");
		cellhead3.setCellStyle(cellStyle);
		

		XSSFCell cellhead4 = rowhead.createCell((int) 3);
		cellhead4.setCellValue("Monthly MGQ");
		cellhead4.setCellStyle(cellStyle);
		

		XSSFCell cellhead5 = rowhead.createCell((int) 4);
		cellhead5.setCellValue(action.getMonthName()+" "+"Month Actual");
		cellhead5.setCellStyle(cellStyle);
		
		XSSFCell cellhead6 = rowhead.createCell((int) 5);
		cellhead6.setCellValue(action.getMonthName()+" "+" Month %");
		cellhead6.setCellStyle(cellStyle);
		

		XSSFCell cellhead7 = rowhead.createCell((int) 6);
		cellhead7.setCellValue("Commulative Actual");
		cellhead7.setCellStyle(cellStyle);
		
		XSSFCell cellhead8 = rowhead.createCell((int) 7);
		cellhead8.setCellValue("Commulative %");
		cellhead8.setCellStyle(cellStyle);
		

		int i = 0;
		
		while (rs.next()) 
		{
			current_bl = current_bl + rs.getDouble("current_actual_bl");
			current_bl_per = current_bl_per + rs.getDouble("current_actual_bl_per");
			
			commulative_bl = commulative_bl + rs.getDouble("cummulative_act_bl");
			commulative_bl_per = commulative_bl_per + rs.getDouble("cummulative_act_bl_per");
		
			yearly_mgq = yearly_mgq + rs.getDouble("yearly_mgq");
			monthly_mgq = monthly_mgq + rs.getDouble("monthly_mgq");
			
			k++; //
			XSSFRow row1 = worksheet.createRow((int) k); //
			XSSFCell cellA1 = row1.createCell((int) 0); //
			cellA1.setCellValue(k - 1); //
			
			
			XSSFCell cellB1 = row1.createCell((int) 1);
			cellB1.setCellValue(rs.getString("description"));
			
		
			
			XSSFCell cellC1 = row1.createCell((int) 2);
			cellC1.setCellValue(rs.getString("yearly_mgq"));
			
			XSSFCell cellD1 = row1.createCell((int) 3);
			cellD1.setCellValue(rs.getString("monthly_mgq"));
			
			XSSFCell cellE1 = row1.createCell((int) 4);
			cellE1.setCellValue(rs.getDouble("current_actual_bl"));
			
			XSSFCell cellF1 = row1.createCell((int) 5);
			cellF1.setCellValue(rs.getString("current_actual_bl_per"));
			
			XSSFCell cellG1 = row1.createCell((int) 6);
			cellG1.setCellValue(rs.getDouble("cummulative_act_bl"));
			
			XSSFCell cellH1 = row1.createCell((int) 7);
			cellH1.setCellValue(rs.getDouble("cummulative_act_bl_per"));
		
	
		

		}
		Random rand = new Random();
		int n = rand.nextInt(550) + 1;
		fileOut = new FileOutputStream(relativePath
				+ "//ExciseUp//MIS//Excel//" + action.getRadio() + "-" + n
				+ "CL_MGQ_Achievement.xls");

		action.setExlname(action.getRadio() + "-" + n);
		XSSFRow row1 = worksheet.createRow((int) k + 1);
		

		XSSFCell cellA1 = row1.createCell((int) 0);
		cellA1.setCellValue(" "); 
		cellA1.setCellStyle(cellStyle);
		
		XSSFCell cellA2 = row1.createCell((int) 1); 
		cellA2.setCellValue("total:"); 
		cellA2.setCellStyle(cellStyle); 
		
		XSSFCell cellA3 = row1.createCell((int) 2); 
		cellA3.setCellValue(yearly_mgq); 
		cellA3.setCellStyle(cellStyle); 
		

		XSSFCell cellA4 = row1.createCell((int) 3); 
		cellA4.setCellValue(monthly_mgq); 
		cellA4.setCellStyle(cellStyle); 
		
		XSSFCell cellA5 = row1.createCell((int) 4);
		cellA5.setCellValue(current_bl);
		cellA5.setCellStyle(cellStyle);
		
		XSSFCell cellA6 = row1.createCell((int) 5);
		cellA6.setCellValue("");
		cellA6.setCellStyle(cellStyle);
		
		XSSFCell cellA7 = row1.createCell((int) 6);
		cellA7.setCellValue(commulative_bl); 
		cellA7.setCellStyle(cellStyle);
		
		XSSFCell cellA8 = row1.createCell((int) 7);
		cellA8.setCellValue("");
		cellA8.setCellStyle(cellStyle);
		
	
	
	


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






}
