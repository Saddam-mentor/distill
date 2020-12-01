package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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
import com.mentor.action.CL2_Mgq_ReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class CL2_Mgq_ReportImpl {

	public void printReportDistrict(CL2_Mgq_ReportAction act) {
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
		String year = act.getYear();

		try {
			con = ConnectionToDataBase.getConnection();

			if(year.equals("19_20"))
			{
			reportQuery="" +
					" SELECT  a.description,COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq,                                                             " +
					" COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,                                                                           " +
					" COALESCE(sum(cc.apr_bl),0)apr_bl,COALESCE(sum(cc.may_bl),0)may_bl,COALESCE(sum(cc.jun_bl),0)jun_bl,COALESCE(sum(cc.jul_bl),0)jul_bl,                            " +
					" COALESCE(sum(cc.aug_bl),0)aug_bl,COALESCE(sum(cc.sep_bl),0)sep_bl,COALESCE(sum(cc.oct_bl),0)oct_bl,COALESCE(sum(cc.nov_bl),0)nov_bl,                            " +
					" COALESCE(sum(cc.dec_bl),0)dec_bl,COALESCE(sum(cc.jan_bl),0)jan_bl,COALESCE(sum(cc.feb_bl),0)feb_bl,COALESCE(sum(cc.mar_bl),0)mar_bl,                            " +
					" case when sum(cc.apr_bl)!=0 and sum(cc.apr_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.apr_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as apr_bl_per,                " +
					" case when sum(cc.may_bl)!=0 and sum(cc.may_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.may_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as may_bl_per,                " +
					" case when sum(cc.jun_bl)!=0 and sum(cc.jun_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.jun_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jun_bl_per,                " +
					" case when sum(cc.jul_bl)!=0 and sum(cc.jul_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.jul_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jul_bl_per,                " +
					" case when sum(cc.aug_bl)!=0 and sum(cc.aug_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.aug_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as aug_bl_per,                " +
					" case when sum(cc.sep_bl)!=0 and sum(cc.sep_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.sep_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as sep_bl_per,                " +
					" case when sum(cc.oct_bl)!=0 and sum(cc.oct_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.oct_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as oct_bl_per,                " +
					" case when sum(cc.nov_bl)!=0 and sum(cc.nov_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.nov_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as nov_bl_per,                " +
					" case when sum(cc.dec_bl)!=0 and sum(cc.dec_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                   " +
					" then round(CAST(float8((sum(cc.dec_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as dec_bl_per,        " +
					" case when sum(cc.jan_bl)!=0 and sum(cc.jan_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
					" then round(CAST(float8((sum(cc.jan_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jan_bl_per,    " +
					" case when sum(cc.feb_bl)!=0 and sum(cc.feb_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
					" then round(CAST(float8((sum(cc.feb_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as feb_bl_per,        " +
					" case when sum(cc.mar_bl)!=0 and sum(cc.mar_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
					" then round(CAST(float8((sum(cc.mar_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as mar_bl_per         " +
					" FROM public.district a left join retail.retail_shop_19_20_backup b on a.districtid=b.district_id left join                                                               " +
					" (select bb.shopId,sum(bb.apr_bl) as apr_bl,sum(bb.may_bl)may_bl,sum(bb.jun_bl)jun_bl,sum(bb.jul_bl)jul_bl,sum(bb.aug_bl)aug_bl,                         " +
					" sum(bb.sep_bl)sep_bl,sum(bb.oct_bl)oct_bl,sum(bb.nov_bl)nov_bl,sum(bb.dec_bl)dec_bl,sum(bb.jan_bl)jan_bl,sum(bb.feb_bl)feb_bl,sum(bb.mar_bl)mar_bl      " +
					" from(                                                                                                                                           " +
					" select aa.shopId,aa.dispatch_date,                                                                                                                              " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=4 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as apr_bl,                                          " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=5 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as may_bl,                                          " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=6 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jun_bl,                                          " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=7 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jul_bl,                                          " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=8 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as aug_bl,                                          " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=9 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as sep_bl,                                          " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=10 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as oct_bl,                                     " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=11 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as nov_bl,                                     " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=12 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as dec_bl,                                     " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=1 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jan_bl,                                      " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=2 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as feb_bl,                                      " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=3 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as mar_bl                                       " +
					" from(  " +
					" select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,                                                                                                            " +
					" case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1,                                                                                 " +
					" case when  m.strength=36 then sum(bl) end as bl_2 ,                                                                                                                        " +
					" case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3                                                                              " +
					" from                                                                                                  " +
					" (select x.dispatch_date,x.shopId,(((x.bottal)*f.qnt_ml_detail)/1000) as bl,d.strength from                                                                     " +
					" (select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal         " +
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+year+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b                                       " +
					" where a.dt_date<'2020-04-01' and  a.vch_from='CL2' and  b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null  group by a.shop_id,      " +
					" a.dt_date,b.int_pckg_id,b.int_brand_id )x,                                                                                                                      " +
					" distillery.box_size_details f,distillery.brand_registration_"+year+" d,distillery.packaging_details_"+year+" e                                " +
					" where x.int_brand_id=d.brand_id and e.box_id=f.box_id and x.package_id=e.package_id)m group by m.dispatch_date,m.shopId,m.strength                 " +
					" )aa group by aa.dispatch_date,aa.shopId)bb group by  bb.shopId order by bb.shopId                                                                       " +
					" )cc on b.serial_no::text=cc.shopId where  b.vch_shop_type='Country Liquor' group by  a.description order by a.description;                          " +
					"  ";
			
			}else {
				reportQuery="" +
						" SELECT  a.description,COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq,                                                             " +
						" COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,                                                                           " +
						" COALESCE(sum(cc.apr_bl),0)apr_bl,COALESCE(sum(cc.may_bl),0)may_bl,COALESCE(sum(cc.jun_bl),0)jun_bl,COALESCE(sum(cc.jul_bl),0)jul_bl,                            " +
						" COALESCE(sum(cc.aug_bl),0)aug_bl,COALESCE(sum(cc.sep_bl),0)sep_bl,COALESCE(sum(cc.oct_bl),0)oct_bl,COALESCE(sum(cc.nov_bl),0)nov_bl,                            " +
						" COALESCE(sum(cc.dec_bl),0)dec_bl,COALESCE(sum(cc.jan_bl),0)jan_bl,COALESCE(sum(cc.feb_bl),0)feb_bl,COALESCE(sum(cc.mar_bl),0)mar_bl,                            " +
						" case when sum(cc.apr_bl)!=0 and sum(cc.apr_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.apr_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as apr_bl_per,                " +
						" case when sum(cc.may_bl)!=0 and sum(cc.may_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.may_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as may_bl_per,                " +
						" case when sum(cc.jun_bl)!=0 and sum(cc.jun_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.jun_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jun_bl_per,                " +
						" case when sum(cc.jul_bl)!=0 and sum(cc.jul_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.jul_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jul_bl_per,                " +
						" case when sum(cc.aug_bl)!=0 and sum(cc.aug_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.aug_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as aug_bl_per,                " +
						" case when sum(cc.sep_bl)!=0 and sum(cc.sep_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.sep_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as sep_bl_per,                " +
						" case when sum(cc.oct_bl)!=0 and sum(cc.oct_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.oct_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as oct_bl_per,                " +
						" case when sum(cc.nov_bl)!=0 and sum(cc.nov_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.nov_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as nov_bl_per,                " +
						" case when sum(cc.dec_bl)!=0 and sum(cc.dec_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                   " +
						" then round(CAST(float8((sum(cc.dec_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as dec_bl_per,        " +
						" case when sum(cc.jan_bl)!=0 and sum(cc.jan_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
						" then round(CAST(float8((sum(cc.jan_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jan_bl_per,    " +
						" case when sum(cc.feb_bl)!=0 and sum(cc.feb_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
						" then round(CAST(float8((sum(cc.feb_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as feb_bl_per,        " +
						" case when sum(cc.mar_bl)!=0 and sum(cc.mar_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
						" then round(CAST(float8((sum(cc.mar_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as mar_bl_per         " +
						" FROM public.district a left join retail.retail_shop b on a.districtid=b.district_id left join                                                               " +
						" (select bb.shopId,sum(bb.apr_bl) as apr_bl,sum(bb.may_bl)may_bl,sum(bb.jun_bl)jun_bl,sum(bb.jul_bl)jul_bl,sum(bb.aug_bl)aug_bl,                         " +
						" sum(bb.sep_bl)sep_bl,sum(bb.oct_bl)oct_bl,sum(bb.nov_bl)nov_bl,sum(bb.dec_bl)dec_bl,sum(bb.jan_bl)jan_bl,sum(bb.feb_bl)feb_bl,sum(bb.mar_bl)mar_bl      " +
						" from(                                                                                                                                           " +
						" select aa.shopId,aa.dispatch_date,                                                                                                                              " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=4 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as apr_bl,                                          " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=5 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as may_bl,                                          " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=6 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jun_bl,                                          " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=7 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jul_bl,                                          " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=8 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as aug_bl,                                          " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=9 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as sep_bl,                                          " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=10 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as oct_bl,                                     " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=11 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as nov_bl,                                     " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=12 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as dec_bl,                                     " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=1 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jan_bl,                                      " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=2 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as feb_bl,                                      " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=3 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as mar_bl                                       " +
						" from(  " +
						" select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,                                                                                                            " +
						" case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1,                                                                                 " +
						" case when  m.strength=36 then sum(bl) end as bl_2 ,                                                                                                                        " +
						" case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3                                                                              " +
						" from                                                                                                  " +
						" (select x.dispatch_date,x.shopId,  bl,x.strength from                                                                     " +
						" (select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal ,(((sum(b.dispatch_bottle))*f.qnt_ml_detail)/1000) as bl  ,d.strength      " +
						" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+year+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b,distillery.brand_registration_"+year+" d,distillery.packaging_details_"+year+" e  ,distillery.box_size_details f                                    " +
						" where  a.vch_from='CL2'  and   e.box_id=f.box_id  and b.int_pckg_id=e.package_id and b.int_brand_id=d.brand_id   and  b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null  group by a.shop_id,      " +
						" a.dt_date,b.int_pckg_id,b.int_brand_id ,f.qnt_ml_detail,d.strength  union "+
						" select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal   ,(((sum(b.dispatch_bottle))*f.qnt_ml_detail)/1000) as bl ,d.strength      " +
						" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b ,distillery.brand_registration_19_20 d,distillery.packaging_details_20_21 e  ,distillery.box_size_details f                                    " +
						" where  a.vch_from='CL2'  and   e.box_id=f.box_id  and b.int_pckg_id=e.package_id and b.int_brand_id=d.brand_id   and  b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and  dt_date>'2020-04-01' and a.shop_id is not null  group by a.shop_id,      " +
						" a.dt_date,b.int_pckg_id,b.int_brand_id,f.qnt_ml_detail ,d.strength "+
						  ")x                                                                                                                     " +
						"                                 " +
						")m group by m.dispatch_date,m.shopId,m.strength                 " +
						" )aa group by aa.dispatch_date,aa.shopId)bb group by  bb.shopId order by bb.shopId                                                                       " +
						" )cc on b.serial_no::text=cc.shopId where  b.vch_shop_type='Country Liquor' group by  a.description order by a.description;                          " +
						"  ";
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

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "CL_MGQ_AchievementDistrict.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "CL_MGQ_AchievementDistrict"+ "-" + n + year
								+ ".pdf");
				act.setPdfName("CL_MGQ_AchievementDistrict" + "-" +n+ year + ".pdf");
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

	public void printReportShopWise(CL2_Mgq_ReportAction act) {
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
		String filter = "";
		 String filter2="";
		 String year = act.getYear();
		
		/*if(act.getDistid()!="0" && act.getDistid()!=null){
			filter2=" and a.districtid='"+act.getDistid()+"' ";
		}*/

		try {
			con = ConnectionToDataBase.getConnection();
		 if(!act.getDistid().equalsIgnoreCase("99"))
			{
				filter2=" and b.district_id="+ Integer.parseInt(act.getDistid())+ "";
				//System.out.println("filter2="+filter2);
				if(act.getShopId().equalsIgnoreCase("0")){
					 
					//System.out.println("120");
				 
				}else{
					filter = " and b.serial_no='" + act.getShopId() + "'";		
				}
			}else{
				//System.out.println("123");
				filter="";
				filter2 = "";
			}
			
		  
			 
			if(year.equals("19_20"))
			{

			reportQuery = " SELECT  b.vch_name_of_shop,a.description,COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq,"
					+ " COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,"
					+ " COALESCE(sum(cc.apr_bl),0)apr_bl,COALESCE(sum(cc.may_bl),0)may_bl,COALESCE(sum(cc.jun_bl),0)jun_bl,COALESCE(sum(cc.jul_bl),0)jul_bl,"
					+ " COALESCE(sum(cc.aug_bl),0)aug_bl,COALESCE(sum(cc.sep_bl),0)sep_bl,COALESCE(sum(cc.oct_bl),0)oct_bl,COALESCE(sum(cc.nov_bl),0)nov_bl,"
					+ " COALESCE(sum(cc.dec_bl),0)dec_bl,COALESCE(sum(cc.jan_bl),0)jan_bl,COALESCE(sum(cc.feb_bl),0)feb_bl,COALESCE(sum(cc.mar_bl),0)mar_bl,"
					+ " case when sum(cc.apr_bl)!=0 and sum(cc.apr_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.apr_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as apr_bl_per,"
					+ " case when sum(cc.may_bl)!=0 and sum(cc.may_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.may_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as may_bl_per,"
					+ " case when sum(cc.jun_bl)!=0 and sum(cc.jun_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.jun_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jun_bl_per,"
					+ " case when sum(cc.jul_bl)!=0 and sum(cc.jul_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.jul_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jul_bl_per,"
					+ " case when sum(cc.aug_bl)!=0 and sum(cc.aug_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.aug_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as aug_bl_per,"
					+ " case when sum(cc.sep_bl)!=0 and sum(cc.sep_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.sep_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as sep_bl_per,"
					+ " case when sum(cc.oct_bl)!=0 and sum(cc.oct_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.oct_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as oct_bl_per,"
					+ " case when sum(cc.nov_bl)!=0 and sum(cc.nov_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.nov_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as nov_bl_per,"
					+ " 	case when sum(cc.dec_bl)!=0 and sum(cc.dec_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " 		then round(CAST(float8((sum(cc.dec_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as dec_bl_per,"
					+ " 		case when sum(cc.jan_bl)!=0 and sum(cc.jan_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " 			then round(CAST(float8((sum(cc.jan_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jan_bl_per,"
					+ " 		case when sum(cc.feb_bl)!=0 and sum(cc.feb_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " 		then round(CAST(float8((sum(cc.feb_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as feb_bl_per,"
					+ " 		case when sum(cc.mar_bl)!=0 and sum(cc.mar_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " 		then round(CAST(float8((sum(cc.mar_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as mar_bl_per "
					+ " 	FROM public.district a left join retail.retail_shop_19_20_backup b on a.districtid=b.district_id left join "
					+ " 		(select bb.shopId,sum(bb.apr_bl) as apr_bl,sum(bb.may_bl)may_bl,sum(bb.jun_bl)jun_bl,sum(bb.jul_bl)jul_bl,sum(bb.aug_bl)aug_bl,"
					+ " 				sum(bb.sep_bl)sep_bl,sum(bb.oct_bl)oct_bl,sum(bb.nov_bl)nov_bl,sum(bb.dec_bl)dec_bl,sum(bb.jan_bl)jan_bl,sum(bb.feb_bl)feb_bl,sum(bb.mar_bl)mar_bl "
					+ " 				from( " +
					
  " select aa.shopId,aa.dispatch_date,"+
 "  aa.bl_1,aa.bl_2,aa.bl_3,"+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=4"+
 " 			then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as apr_bl,"+
 "    "+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=5 "+
 "  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))   end as may_bl, "+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=6 "+
 "  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as jun_bl, "+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=7 "+
 "  		then(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as jul_bl,"+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=8 "+
 "  	then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as aug_bl,"+
  
"   case when EXTRACT(MONTH FROM aa.dispatch_date)=9"+
  " 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as sep_bl, "+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=10 "+
 "  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as oct_bl, 	"+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=11"+
  " 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as nov_bl, 	"+
  
  " case when EXTRACT(MONTH FROM aa.dispatch_date)=12 "+
  " 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as dec_bl,"+
  
  " case when EXTRACT(MONTH FROM aa.dispatch_date)=1 "+
 "	  then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as jan_bl, 	"+
  
  " case when EXTRACT(MONTH FROM aa.dispatch_date)=2 "+
 "  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as feb_bl, 	"+
  
  " case when EXTRACT(MONTH FROM aa.dispatch_date)=3 "+
  " 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as mar_bl 	"+
   		
 "  	   from( 	"+
   		
  " 	  select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,"+
      
  "  case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1, "+
  
 "  case when  m.strength=36 then sum(bl) end as bl_2 , "+
   
  " case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3 "
					
					
					+ " from			(select x.dispatch_date,x.shopId,(((x.bottal)*f.qnt_ml_detail)/1000) as bl,d.strength from "
					+ " 					(select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal "
					+ " 							from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+year+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b "
					+ " 						where  a.vch_from='CL2' and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null "
					+
					// "and a.shop_id='"+filter+"' " +
					" group by a.shop_id,"
					+ " a.dt_date,b.int_pckg_id,b.int_brand_id )x,"
					+ " 						distillery.box_size_details f,distillery.brand_registration_"+year+" d,distillery.packaging_details_"+year+" e "
					+ " 						where x.int_brand_id=d.brand_id and e.box_id=f.box_id and x.package_id=e.package_id)m group by m.dispatch_date,m.shopId ,m.strength "
					+ " 		)aa group by aa.dispatch_date,aa.shopId, aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId order by bb.shopId "
					+ " 			)cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'   " + filter +filter2
					+ " group by  a.description,b.vch_name_of_shop order by a.description";
			
			
			
			}else {/*
				reportQuery="" +
						" SELECT  b.vch_name_of_shop,a.description,COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq,                                                             " +
						" COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,                                                                           " +
						" COALESCE(sum(cc.apr_bl),0)apr_bl,COALESCE(sum(cc.may_bl),0)may_bl,COALESCE(sum(cc.jun_bl),0)jun_bl,COALESCE(sum(cc.jul_bl),0)jul_bl,                            " +
						" COALESCE(sum(cc.aug_bl),0)aug_bl,COALESCE(sum(cc.sep_bl),0)sep_bl,COALESCE(sum(cc.oct_bl),0)oct_bl,COALESCE(sum(cc.nov_bl),0)nov_bl,                            " +
						" COALESCE(sum(cc.dec_bl),0)dec_bl,COALESCE(sum(cc.jan_bl),0)jan_bl,COALESCE(sum(cc.feb_bl),0)feb_bl,COALESCE(sum(cc.mar_bl),0)mar_bl,                            " +
						" case when sum(cc.apr_bl)!=0 and sum(cc.apr_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.apr_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as apr_bl_per,                " +
						" case when sum(cc.may_bl)!=0 and sum(cc.may_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.may_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as may_bl_per,                " +
						" case when sum(cc.jun_bl)!=0 and sum(cc.jun_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.jun_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jun_bl_per,                " +
						" case when sum(cc.jul_bl)!=0 and sum(cc.jul_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.jul_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jul_bl_per,                " +
						" case when sum(cc.aug_bl)!=0 and sum(cc.aug_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.aug_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as aug_bl_per,                " +
						" case when sum(cc.sep_bl)!=0 and sum(cc.sep_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.sep_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as sep_bl_per,                " +
						" case when sum(cc.oct_bl)!=0 and sum(cc.oct_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.oct_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as oct_bl_per,                " +
						" case when sum(cc.nov_bl)!=0 and sum(cc.nov_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
						" then round(CAST(float8((sum(cc.nov_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as nov_bl_per,                " +
						" case when sum(cc.dec_bl)!=0 and sum(cc.dec_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                   " +
						" then round(CAST(float8((sum(cc.dec_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as dec_bl_per,        " +
						" case when sum(cc.jan_bl)!=0 and sum(cc.jan_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
						" then round(CAST(float8((sum(cc.jan_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jan_bl_per,    " +
						" case when sum(cc.feb_bl)!=0 and sum(cc.feb_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
						" then round(CAST(float8((sum(cc.feb_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as feb_bl_per,        " +
						" case when sum(cc.mar_bl)!=0 and sum(cc.mar_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
						" then round(CAST(float8((sum(cc.mar_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as mar_bl_per         " +
						" FROM public.district a left join retail.retail_shop b on a.districtid=b.district_id left join                                                               " +
						" (select bb.shopId,sum(bb.apr_bl) as apr_bl,sum(bb.may_bl)may_bl,sum(bb.jun_bl)jun_bl,sum(bb.jul_bl)jul_bl,sum(bb.aug_bl)aug_bl,                         " +
						" sum(bb.sep_bl)sep_bl,sum(bb.oct_bl)oct_bl,sum(bb.nov_bl)nov_bl,sum(bb.dec_bl)dec_bl,sum(bb.jan_bl)jan_bl,sum(bb.feb_bl)feb_bl,sum(bb.mar_bl)mar_bl      " +
						" from(                                                                                                                                           " +
						" select aa.shopId,aa.dispatch_date,                                                                                                                              " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=4 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as apr_bl,                                          " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=5 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as may_bl,                                          " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=6 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jun_bl,                                          " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=7 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jul_bl,                                          " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=8 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as aug_bl,                                          " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=9 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as sep_bl,                                          " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=10 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as oct_bl,                                     " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=11 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as nov_bl,                                     " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=12 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as dec_bl,                                     " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=1 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jan_bl,                                      " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=2 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as feb_bl,                                      " +
						" case when EXTRACT(MONTH FROM aa.dispatch_date)=3 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as mar_bl                                       " +
						" from(  " +
						" select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,                                                                                                            " +
						" case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1,                                                                                 " +
						" case when  m.strength=36 then sum(bl) end as bl_2 ,                                                                                                                        " +
						" case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3                                                                              " +
						" from                                                                                                  " +
						" (select x.dispatch_date,x.shopId,  bl,x.strength from                                                                     " +
						" (select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal ,(((sum(b.dispatch_bottle))*f.qnt_ml_detail)/1000) as bl ,d.strength       " +
						" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+year+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b,distillery.brand_registration_"+year+" d,distillery.packaging_details_"+year+" e  ,distillery.box_size_details f                                     " +
						" where  a.vch_from='CL2'  and  e.box_id=f.box_id and b.int_pckg_id=e.package_id and b.int_brand_id=d.brand_id   and  b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null  group by a.shop_id,      " +
						" a.dt_date,b.int_pckg_id,b.int_brand_id,f.qnt_ml_detail ,d.strength union "+
						" select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal   ,(((sum(b.dispatch_bottle))*f.qnt_ml_detail)/1000) as bl  ,d.strength     " +
						" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b ,distillery.brand_registration_19_20 d,distillery.packaging_details_20_21 e    ,distillery.box_size_details f                                   " +
						" where  a.vch_from='CL2'  and  e.box_id=f.box_id and b.int_pckg_id=e.package_id and b.int_brand_id=d.brand_id   and  b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and  dt_date>'2020-04-01' and a.shop_id is not null  group by a.shop_id,      " +
						" a.dt_date,b.int_pckg_id,b.int_brand_id ,f.qnt_ml_detail,d.strength"+
						  ")x                                                                                                                      " +
						"                                " +
						" )m group by m.dispatch_date,m.shopId,m.strength                 " +
						" )aa group by aa.dispatch_date,aa.shopId)bb group by  bb.shopId order by bb.shopId                                                                       " +
						" )cc on b.serial_no::text=cc.shopId where  b.vch_shop_type='Country Liquor' group by   b.vch_name_of_shop,a.description order by a.description;                          " +
						"  ";
			*/reportQuery = " SELECT  b.vch_name_of_shop,a.description,COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq,"
					+ " COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,"
					+ " COALESCE(sum(cc.apr_bl),0)apr_bl,COALESCE(sum(cc.may_bl),0)may_bl,COALESCE(sum(cc.jun_bl),0)jun_bl,COALESCE(sum(cc.jul_bl),0)jul_bl,"
					+ " COALESCE(sum(cc.aug_bl),0)aug_bl,COALESCE(sum(cc.sep_bl),0)sep_bl,COALESCE(sum(cc.oct_bl),0)oct_bl,COALESCE(sum(cc.nov_bl),0)nov_bl,"
					+ " COALESCE(sum(cc.dec_bl),0)dec_bl,COALESCE(sum(cc.jan_bl),0)jan_bl,COALESCE(sum(cc.feb_bl),0)feb_bl,COALESCE(sum(cc.mar_bl),0)mar_bl,"
					+ " case when sum(cc.apr_bl)!=0 and sum(cc.apr_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.apr_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as apr_bl_per,"
					+ " case when sum(cc.may_bl)!=0 and sum(cc.may_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.may_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as may_bl_per,"
					+ " case when sum(cc.jun_bl)!=0 and sum(cc.jun_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.jun_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jun_bl_per,"
					+ " case when sum(cc.jul_bl)!=0 and sum(cc.jul_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.jul_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jul_bl_per,"
					+ " case when sum(cc.aug_bl)!=0 and sum(cc.aug_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.aug_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as aug_bl_per,"
					+ " case when sum(cc.sep_bl)!=0 and sum(cc.sep_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.sep_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as sep_bl_per,"
					+ " case when sum(cc.oct_bl)!=0 and sum(cc.oct_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.oct_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as oct_bl_per,"
					+ " case when sum(cc.nov_bl)!=0 and sum(cc.nov_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.nov_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as nov_bl_per,"
					+ " 	case when sum(cc.dec_bl)!=0 and sum(cc.dec_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " 		then round(CAST(float8((sum(cc.dec_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as dec_bl_per,"
					+ " 		case when sum(cc.jan_bl)!=0 and sum(cc.jan_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " 			then round(CAST(float8((sum(cc.jan_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jan_bl_per,"
					+ " 		case when sum(cc.feb_bl)!=0 and sum(cc.feb_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " 		then round(CAST(float8((sum(cc.feb_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as feb_bl_per,"
					+ " 		case when sum(cc.mar_bl)!=0 and sum(cc.mar_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " 		then round(CAST(float8((sum(cc.mar_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as mar_bl_per "
					+ " 	FROM public.district a left join retail.retail_shop b on a.districtid=b.district_id left join "
					+ " 		(select bb.shopId,sum(bb.apr_bl) as apr_bl,sum(bb.may_bl)may_bl,sum(bb.jun_bl)jun_bl,sum(bb.jul_bl)jul_bl,sum(bb.aug_bl)aug_bl,"
					+ " 				sum(bb.sep_bl)sep_bl,sum(bb.oct_bl)oct_bl,sum(bb.nov_bl)nov_bl,sum(bb.dec_bl)dec_bl,sum(bb.jan_bl)jan_bl,sum(bb.feb_bl)feb_bl,sum(bb.mar_bl)mar_bl "
					+ " 				from( " +
					
  " select aa.shopId,aa.dispatch_date,"+
 "  aa.bl_1,aa.bl_2,aa.bl_3,"+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=4"+
 " 			then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as apr_bl,"+
 "    "+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=5 "+
 "  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))   end as may_bl, "+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=6 "+
 "  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as jun_bl, "+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=7 "+
 "  		then(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as jul_bl,"+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=8 "+
 "  	then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as aug_bl,"+
  
"   case when EXTRACT(MONTH FROM aa.dispatch_date)=9"+
  " 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as sep_bl, "+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=10 "+
 "  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as oct_bl, 	"+
  
 "  case when EXTRACT(MONTH FROM aa.dispatch_date)=11"+
  " 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as nov_bl, 	"+
  
  " case when EXTRACT(MONTH FROM aa.dispatch_date)=12 "+
  " 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as dec_bl,"+
  
  " case when EXTRACT(MONTH FROM aa.dispatch_date)=1 "+
 "	  then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as jan_bl, 	"+
  
  " case when EXTRACT(MONTH FROM aa.dispatch_date)=2 "+
 "  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as feb_bl, 	"+
  
  " case when EXTRACT(MONTH FROM aa.dispatch_date)=3 "+
  " 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as mar_bl 	"+
   		
 "  	   from( 	"+
   		
  " 	  select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,"+
      
  "  case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1, "+
  
 "  case when  m.strength=36 then sum(bl) end as bl_2 , "+
   
  " case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3 "
					
					
					+ " from		  (select x.dispatch_date,x.shopId,  bl,x.strength from                                                                     " +
					" (select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal ,(((sum(b.dispatch_bottle))*f.qnt_ml_detail)/1000) as bl ,d.strength       " +
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+year+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b,distillery.brand_registration_"+year+" d,distillery.packaging_details_"+year+" e  ,distillery.box_size_details f                                     " +
					" where  a.vch_from='CL2'  and  e.box_id=f.box_id and b.int_pckg_id=e.package_id and b.int_brand_id=d.brand_id   and  b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null  group by a.shop_id,      " +
					" a.dt_date,b.int_pckg_id,b.int_brand_id,f.qnt_ml_detail ,d.strength union "+
					" select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal   ,(((sum(b.dispatch_bottle))*f.qnt_ml_detail)/1000) as bl  ,d.strength     " +
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b ,distillery.brand_registration_19_20 d,distillery.packaging_details_20_21 e    ,distillery.box_size_details f                                   " +
					" where  a.vch_from='CL2'  and  e.box_id=f.box_id and b.int_pckg_id=e.package_id and b.int_brand_id=d.brand_id   and  b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and  dt_date>'2020-04-01' and a.shop_id is not null  group by a.shop_id,      " +
					" a.dt_date,b.int_pckg_id,b.int_brand_id ,f.qnt_ml_detail,d.strength"+
					  ")x  )m group by m.dispatch_date,m.shopId ,m.strength "
					+ " 		)aa group by aa.dispatch_date,aa.shopId, aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId order by bb.shopId "
					+ " 			)cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'   " + filter +filter2
					+ " group by  a.description,b.vch_name_of_shop order by a.description";
			
			}
			
			
			
			
			
			

			
			//where b.vch_shop_type='Country Liquor' 
			
			pst = con.prepareStatement(reportQuery);
		 	System.out.println("reportQuery---kkkaammiilll-------" + reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("radioType", act.getRadio());

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "CL_MGQ_AchievtShopWise.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "CL_MGQ_AchievtShopWise" + "-" + n+year + ".pdf");
				act.setPdfName("CL_MGQ_AchievtShopWise" + "-" +n+ year + ".pdf");
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
				e.printStackTrace();
			}
		}

	}

	/*public ArrayList getShop(CL2_Mgq_ReportAction act, String o) {
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SelectItem item = new SelectItem();
		try {
			item.setLabel("--All--");
			item.setValue(0);
			list.add(item);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String SQl = null;

		String filter = "";

		try {

			SQl = " select serial_no ,vch_name_of_shop from retail.retail_shop where district_id="
					+ Integer.parseInt(o)
					+ ""
					+ "  order by trim(vch_name_of_shop)";

			con = ConnectionToDataBase.getConnection();
			System.out.println("SQl====="+SQl);
			ps = con.prepareStatement(SQl);

			rs = ps.executeQuery();

			rs = ps.executeQuery();
			while (rs.next()) {
				item = new SelectItem();
				item.setLabel(rs.getString("vch_name_of_shop") + " - "
						+ rs.getString("serial_no"));
				try {
					item.setValue(rs.getString("serial_no"));
				} catch (Exception e) {
					item.setValue(0);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
*/
	
	/*public ArrayList getShop(CL2_Mgq_ReportAction act, String o) {
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SelectItem item = new SelectItem();
		item.setLabel("--Select one--");
		item.setValue("0");
		list.add(item);
		String SQl = null;

		String filter = "";


		try {

			
			SQl = " select serial_no ,vch_name_of_shop from retail.retail_shop where district_id="
					+ Integer.parseInt(o)
					+ ""
					+ "  order by trim(vch_name_of_shop)";

			System.out.println("report -- " + SQl);

			con = ConnectionToDataBase.getConnection();

			ps = con.prepareStatement(SQl);

			rs = ps.executeQuery();

			rs = ps.executeQuery();
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
	}*/

	/*
	 * public ArrayList getShop2(CL2_Mgq_ReportAction act,String shpId) {
	 * ArrayList list = new ArrayList(); Connection con = null;
	 * PreparedStatement ps = null; ResultSet rs = null; SelectItem item = new
	 * SelectItem(); item.setLabel("--All--"); item.setValue("0");
	 * list.add(item); String SQl = null;
	 * 
	 * String filter = "";
	 * 
	 * 
	 * try {
	 * 
	 * 
	 * 
	 * SQl =
	 * " select serial_no::text as serial_no ,vch_name_of_shop from retail.retail_shop "
	 * + " where serial_no='"+act.getShopId()+"' " +
	 * "  order by vch_name_of_shop";
	 * 
	 * 
	 * 
	 * 
	 * System.out.println("report -- " + SQl);
	 * 
	 * con = ConnectionToDataBase.getConnection();
	 * 
	 * ps = con.prepareStatement(SQl);
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * rs = ps.executeQuery(); while (rs.next()) { item = new SelectItem();
	 * item.setLabel(rs.getString("vch_name_of_shop") + " - " +
	 * rs.getString("serial_no")); item.setValue(rs.getString("serial_no"));
	 * list.add(item); } } catch (Exception e) { e.printStackTrace(); } finally
	 * { try {
	 * 
	 * if (con != null) con.close(); if (ps != null) ps.close(); if (rs != null)
	 * rs.close();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } } return list; }
	 */
	
	
	public boolean excelDistrictWise(CL2_Mgq_ReportAction action) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double apr_bl_per = 0;
		double may_bl_per = 0;
		double jun_bl_per = 0;
		double jul_bl_per = 0;
		double aug_bl_per = 0;
		double sep_bl_per = 0;
		double oct_bl_per = 0;
		double nov_bl_per = 0;
		double dec_bl_per = 0;
		double jan_bl_per = 0;
		double feb_bl_per = 0;
		double mar_bl_per = 0;
		double apr_bl = 0;
		double may_bl = 0;
		double jun_bl = 0;
		double jul_bl = 0;
		double aug_bl = 0;
		double sep_bl = 0;
		double oct_bl = 0;
		double nov_bl = 0;
		double dec_bl = 0;
		double jan_bl = 0;
		double feb_bl = 0;
		double mar_bl = 0;
		double yearly_mgq = 0;
		double monthly_mgq = 0;
		String year = action.getYear();
		DecimalFormat diciformatter = new DecimalFormat("#.##");

		String reportQuery = null;
		if(year.equals("19_20"))
		{
		reportQuery="" +
				" SELECT  a.description,COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq,                                                             " +
				" COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,                                                                           " +
				" COALESCE(sum(cc.apr_bl),0)apr_bl,COALESCE(sum(cc.may_bl),0)may_bl,COALESCE(sum(cc.jun_bl),0)jun_bl,COALESCE(sum(cc.jul_bl),0)jul_bl,                            " +
				" COALESCE(sum(cc.aug_bl),0)aug_bl,COALESCE(sum(cc.sep_bl),0)sep_bl,COALESCE(sum(cc.oct_bl),0)oct_bl,COALESCE(sum(cc.nov_bl),0)nov_bl,                            " +
				" COALESCE(sum(cc.dec_bl),0)dec_bl,COALESCE(sum(cc.jan_bl),0)jan_bl,COALESCE(sum(cc.feb_bl),0)feb_bl,COALESCE(sum(cc.mar_bl),0)mar_bl,                            " +
				" case when sum(cc.apr_bl)!=0 and sum(cc.apr_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
				" then round(CAST(float8((sum(cc.apr_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as apr_bl_per,                " +
				" case when sum(cc.may_bl)!=0 and sum(cc.may_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
				" then round(CAST(float8((sum(cc.may_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as may_bl_per,                " +
				" case when sum(cc.jun_bl)!=0 and sum(cc.jun_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
				" then round(CAST(float8((sum(cc.jun_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jun_bl_per,                " +
				" case when sum(cc.jul_bl)!=0 and sum(cc.jul_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
				" then round(CAST(float8((sum(cc.jul_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jul_bl_per,                " +
				" case when sum(cc.aug_bl)!=0 and sum(cc.aug_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
				" then round(CAST(float8((sum(cc.aug_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as aug_bl_per,                " +
				" case when sum(cc.sep_bl)!=0 and sum(cc.sep_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
				" then round(CAST(float8((sum(cc.sep_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as sep_bl_per,                " +
				" case when sum(cc.oct_bl)!=0 and sum(cc.oct_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
				" then round(CAST(float8((sum(cc.oct_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as oct_bl_per,                " +
				" case when sum(cc.nov_bl)!=0 and sum(cc.nov_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
				" then round(CAST(float8((sum(cc.nov_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as nov_bl_per,                " +
				" case when sum(cc.dec_bl)!=0 and sum(cc.dec_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                   " +
				" then round(CAST(float8((sum(cc.dec_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as dec_bl_per,        " +
				" case when sum(cc.jan_bl)!=0 and sum(cc.jan_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
				" then round(CAST(float8((sum(cc.jan_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jan_bl_per,    " +
				" case when sum(cc.feb_bl)!=0 and sum(cc.feb_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
				" then round(CAST(float8((sum(cc.feb_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as feb_bl_per,        " +
				" case when sum(cc.mar_bl)!=0 and sum(cc.mar_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
				" then round(CAST(float8((sum(cc.mar_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as mar_bl_per         " +
				" FROM public.district a left join retail.retail_shop_19_20_backup b on a.districtid=b.district_id left join                                                               " +
				" (select bb.shopId,sum(bb.apr_bl) as apr_bl,sum(bb.may_bl)may_bl,sum(bb.jun_bl)jun_bl,sum(bb.jul_bl)jul_bl,sum(bb.aug_bl)aug_bl,                         " +
				" sum(bb.sep_bl)sep_bl,sum(bb.oct_bl)oct_bl,sum(bb.nov_bl)nov_bl,sum(bb.dec_bl)dec_bl,sum(bb.jan_bl)jan_bl,sum(bb.feb_bl)feb_bl,sum(bb.mar_bl)mar_bl      " +
				" from(                                                                                                                                           " +
				" select aa.shopId,aa.dispatch_date,                                                                                                                              " +
				" case when EXTRACT(MONTH FROM aa.dispatch_date)=4 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as apr_bl,                                          " +
				" case when EXTRACT(MONTH FROM aa.dispatch_date)=5 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as may_bl,                                          " +
				" case when EXTRACT(MONTH FROM aa.dispatch_date)=6 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jun_bl,                                          " +
				" case when EXTRACT(MONTH FROM aa.dispatch_date)=7 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jul_bl,                                          " +
				" case when EXTRACT(MONTH FROM aa.dispatch_date)=8 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as aug_bl,                                          " +
				" case when EXTRACT(MONTH FROM aa.dispatch_date)=9 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as sep_bl,                                          " +
				" case when EXTRACT(MONTH FROM aa.dispatch_date)=10 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as oct_bl,                                     " +
				" case when EXTRACT(MONTH FROM aa.dispatch_date)=11 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as nov_bl,                                     " +
				" case when EXTRACT(MONTH FROM aa.dispatch_date)=12 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as dec_bl,                                     " +
				" case when EXTRACT(MONTH FROM aa.dispatch_date)=1 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jan_bl,                                      " +
				" case when EXTRACT(MONTH FROM aa.dispatch_date)=2 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as feb_bl,                                      " +
				" case when EXTRACT(MONTH FROM aa.dispatch_date)=3 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as mar_bl                                       " +
				" from(  " +
				" select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,                                                                                                            " +
				" case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1,                                                                                 " +
				" case when  m.strength=36 then sum(bl) end as bl_2 ,                                                                                                                        " +
				" case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3                                                                              " +
				" from                                                                                                  " +
				" (select x.dispatch_date,x.shopId,(((x.bottal)*f.qnt_ml_detail)/1000) as bl,d.strength from                                                                     " +
				" (select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal         " +
				" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+year+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b                                       " +
				" where a.dt_date<'2020-04-01' and  a.vch_from='CL2' and  b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null  group by a.shop_id,      " +
				" a.dt_date,b.int_pckg_id,b.int_brand_id )x,                                                                                                                      " +
				" distillery.box_size_details f,distillery.brand_registration_"+year+" d,distillery.packaging_details_"+year+" e                                " +
				" where x.int_brand_id=d.brand_id and e.box_id=f.box_id and x.package_id=e.package_id)m group by m.dispatch_date,m.shopId,m.strength                 " +
				" )aa group by aa.dispatch_date,aa.shopId)bb group by  bb.shopId order by bb.shopId                                                                       " +
				" )cc on b.serial_no::text=cc.shopId where  b.vch_shop_type='Country Liquor' group by  a.description order by a.description;                          " +
				"  ";
		}else {

			reportQuery="" +
					" SELECT  a.description,COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq,                                                             " +
					" COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,                                                                           " +
					" COALESCE(sum(cc.apr_bl),0)apr_bl,COALESCE(sum(cc.may_bl),0)may_bl,COALESCE(sum(cc.jun_bl),0)jun_bl,COALESCE(sum(cc.jul_bl),0)jul_bl,                            " +
					" COALESCE(sum(cc.aug_bl),0)aug_bl,COALESCE(sum(cc.sep_bl),0)sep_bl,COALESCE(sum(cc.oct_bl),0)oct_bl,COALESCE(sum(cc.nov_bl),0)nov_bl,                            " +
					" COALESCE(sum(cc.dec_bl),0)dec_bl,COALESCE(sum(cc.jan_bl),0)jan_bl,COALESCE(sum(cc.feb_bl),0)feb_bl,COALESCE(sum(cc.mar_bl),0)mar_bl,                            " +
					" case when sum(cc.apr_bl)!=0 and sum(cc.apr_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.apr_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as apr_bl_per,                " +
					" case when sum(cc.may_bl)!=0 and sum(cc.may_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.may_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as may_bl_per,                " +
					" case when sum(cc.jun_bl)!=0 and sum(cc.jun_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.jun_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jun_bl_per,                " +
					" case when sum(cc.jul_bl)!=0 and sum(cc.jul_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.jul_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jul_bl_per,                " +
					" case when sum(cc.aug_bl)!=0 and sum(cc.aug_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.aug_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as aug_bl_per,                " +
					" case when sum(cc.sep_bl)!=0 and sum(cc.sep_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.sep_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as sep_bl_per,                " +
					" case when sum(cc.oct_bl)!=0 and sum(cc.oct_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.oct_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as oct_bl_per,                " +
					" case when sum(cc.nov_bl)!=0 and sum(cc.nov_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                       " +
					" then round(CAST(float8((sum(cc.nov_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as nov_bl_per,                " +
					" case when sum(cc.dec_bl)!=0 and sum(cc.dec_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                                   " +
					" then round(CAST(float8((sum(cc.dec_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as dec_bl_per,        " +
					" case when sum(cc.jan_bl)!=0 and sum(cc.jan_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
					" then round(CAST(float8((sum(cc.jan_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jan_bl_per,    " +
					" case when sum(cc.feb_bl)!=0 and sum(cc.feb_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
					" then round(CAST(float8((sum(cc.feb_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as feb_bl_per,        " +
					" case when sum(cc.mar_bl)!=0 and sum(cc.mar_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null                               " +
					" then round(CAST(float8((sum(cc.mar_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as mar_bl_per         " +
					" FROM public.district a left join retail.retail_shop b on a.districtid=b.district_id left join                                                               " +
					" (select bb.shopId,sum(bb.apr_bl) as apr_bl,sum(bb.may_bl)may_bl,sum(bb.jun_bl)jun_bl,sum(bb.jul_bl)jul_bl,sum(bb.aug_bl)aug_bl,                         " +
					" sum(bb.sep_bl)sep_bl,sum(bb.oct_bl)oct_bl,sum(bb.nov_bl)nov_bl,sum(bb.dec_bl)dec_bl,sum(bb.jan_bl)jan_bl,sum(bb.feb_bl)feb_bl,sum(bb.mar_bl)mar_bl      " +
					" from(                                                                                                                                           " +
					" select aa.shopId,aa.dispatch_date,                                                                                                                              " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=4 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as apr_bl,                                          " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=5 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as may_bl,                                          " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=6 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jun_bl,                                          " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=7 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jul_bl,                                          " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=8 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as aug_bl,                                          " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=9 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as sep_bl,                                          " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=10 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as oct_bl,                                     " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=11 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as nov_bl,                                     " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=12 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as dec_bl,                                     " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=1 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as jan_bl,                                      " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=2 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as feb_bl,                                      " +
					" case when EXTRACT(MONTH FROM aa.dispatch_date)=3 then round(CAST(float8(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0)) as numeric), 2) else 0 end as mar_bl                                       " +
					" from(  " +
					" select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,                                                                                                            " +
					" case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1,                                                                                 " +
					" case when  m.strength=36 then sum(bl) end as bl_2 ,                                                                                                                        " +
					" case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3                                                                              " +
					" from                                                                                                  " +
					" (select x.dispatch_date,x.shopId,  bl,x.strength from                                                                     " +
					" (select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal ,((sum(b.dispatch_bottle)*f.qnt_ml_detail)/1000) as bl  ,d.strength      " +
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+year+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b ,distillery.box_size_details f,distillery.brand_registration_20_21 d,distillery.packaging_details_20_21 e                                        " +
					" where  a.vch_from='CL2'  and b.int_brand_id=d.brand_id and e.box_id=f.box_id and b.int_pckg_id=e.package_id and  b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null  group by a.shop_id,      " +
					" a.dt_date,b.int_pckg_id,b.int_brand_id ,f.qnt_ml_detail,d.strength union    "+
					" select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal  ,((sum(b.dispatch_bottle)*f.qnt_ml_detail)/1000) as bl ,d.strength      " +
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b  ,  distillery.box_size_details f,distillery.brand_registration_19_20 d,distillery.packaging_details_19_20 e                                     " +
					" where  a.vch_from='CL2'  and b.int_brand_id=d.brand_id and e.box_id=f.box_id and b.int_pckg_id=e.package_id and  b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and  dt_date>'2020-04-01' and a.shop_id is not null  group by a.shop_id,      " +
					" a.dt_date,b.int_pckg_id,b.int_brand_id ,f.qnt_ml_detail,d.strength   )x                                                                                                                        " +
					"                               " +
					")m group by m.dispatch_date,m.shopId,m.strength                 " +
					" )aa group by aa.dispatch_date,aa.shopId)bb group by  bb.shopId order by bb.shopId                                                                       " +
					" )cc on b.serial_no::text=cc.shopId where  b.vch_shop_type='Country Liquor' group by  a.description order by a.description;                          " +
					"  ";
			
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

			pstmt = con.prepareStatement(reportQuery);

			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook
					.createSheet("Cl2 MGQ DistrictWise Report"+year);

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
			worksheet.setColumnWidth(12, 5000);
			worksheet.setColumnWidth(13, 5000);
			worksheet.setColumnWidth(14, 5000);
			worksheet.setColumnWidth(15, 5000);
			worksheet.setColumnWidth(16, 5000);
			worksheet.setColumnWidth(17, 5000);
			worksheet.setColumnWidth(18, 5000);
			worksheet.setColumnWidth(19, 5000);
			worksheet.setColumnWidth(20, 5000);
			worksheet.setColumnWidth(21, 5000);
			worksheet.setColumnWidth(22, 5000);
			worksheet.setColumnWidth(23, 5000);
			worksheet.setColumnWidth(24, 5000);
			worksheet.setColumnWidth(25, 5000);
			worksheet.setColumnWidth(26, 5000);
			worksheet.setColumnWidth(26, 5000); 
			 
			
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("CL - MGQ Achievement DistrictWise"+year);
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
			cellhead5.setCellValue("April BL(Actual)");
			cellhead5.setCellStyle(cellStyle);
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("April BL(in %)");
			cellhead6.setCellStyle(cellStyle);

			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("May BL(Actual)");
			cellhead7.setCellStyle(cellStyle);
			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("May BL(in %)");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("June BL(Actual)");
			cellhead9.setCellStyle(cellStyle);
			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("June BL(in %)");
			cellhead10.setCellStyle(cellStyle);

			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("July BL(Actual)");
			cellhead11.setCellStyle(cellStyle);
			XSSFCell cellhead12 = rowhead.createCell((int) 11);
			cellhead12.setCellValue("July BL(in %)");
			cellhead12.setCellStyle(cellStyle);

			XSSFCell cellhead13 = rowhead.createCell((int) 12);
			cellhead13.setCellValue("August BL(Actual)");
			cellhead13.setCellStyle(cellStyle);
			XSSFCell cellhead14 = rowhead.createCell((int) 13);
			cellhead14.setCellValue("August BL(in %)");
			cellhead14.setCellStyle(cellStyle);

			XSSFCell cellhead15 = rowhead.createCell((int) 14);
			cellhead15.setCellValue("September BL(Actual)");
			cellhead15.setCellStyle(cellStyle);
			XSSFCell cellhead16 = rowhead.createCell((int) 15);
			cellhead16.setCellValue("September BL(in %)");
			cellhead16.setCellStyle(cellStyle);

			XSSFCell cellhead17 = rowhead.createCell((int) 16);
			cellhead17.setCellValue("October BL(Actual)");
			cellhead17.setCellStyle(cellStyle);
			XSSFCell cellhead18 = rowhead.createCell((int) 17);
			cellhead18.setCellValue("October BL(in %)");
			cellhead18.setCellStyle(cellStyle);

			XSSFCell cellhead19 = rowhead.createCell((int) 18);
			cellhead19.setCellValue("November BL(Actual)");
			cellhead19.setCellStyle(cellStyle);
			XSSFCell cellhead20 = rowhead.createCell((int) 19);
			cellhead20.setCellValue("November BL(in %)");
			cellhead20.setCellStyle(cellStyle);

			XSSFCell cellhead21 = rowhead.createCell((int) 20);
			cellhead21.setCellValue("December BL(Actual)");
			cellhead21.setCellStyle(cellStyle);
			XSSFCell cellhead22 = rowhead.createCell((int) 21);
			cellhead22.setCellValue("December BL(in %)");
			cellhead22.setCellStyle(cellStyle);

			XSSFCell cellhead23 = rowhead.createCell((int) 22);
			cellhead23.setCellValue("January BL(Actual)");
			cellhead23.setCellStyle(cellStyle);
			XSSFCell cellhead24 = rowhead.createCell((int) 23);
			cellhead24.setCellValue("January BL(in %)");
			cellhead24.setCellStyle(cellStyle);

			XSSFCell cellhead25 = rowhead.createCell((int) 24);
			cellhead25.setCellValue("February BL(Actual)");
			cellhead25.setCellStyle(cellStyle);
			XSSFCell cellhead26 = rowhead.createCell((int) 25);
			cellhead26.setCellValue("February BL(in %)");
			cellhead26.setCellStyle(cellStyle);

			XSSFCell cellhead27 = rowhead.createCell((int) 26);
			cellhead27.setCellValue("March BL(Actual)");
			cellhead27.setCellStyle(cellStyle);
			XSSFCell cellhead28 = rowhead.createCell((int) 27);
			cellhead28.setCellValue("March BL(in %)");
			cellhead28.setCellStyle(cellStyle);

			int i = 0;
			while (rs.next()) {// System.out.println(rs.getDouble("apr_bl_per"));
				apr_bl_per = apr_bl_per + rs.getDouble("apr_bl_per");
				may_bl_per = may_bl_per + rs.getDouble("may_bl_per");
				jun_bl_per = jun_bl_per + rs.getDouble("jun_bl_per");
				jul_bl_per = jul_bl_per + rs.getDouble("jul_bl_per");
				aug_bl_per = aug_bl_per + rs.getDouble("aug_bl_per");
				sep_bl_per = sep_bl_per + rs.getDouble("sep_bl_per");
				oct_bl_per = oct_bl_per + rs.getDouble("oct_bl_per");
				nov_bl_per = nov_bl_per + rs.getDouble("nov_bl_per");
				dec_bl_per = dec_bl_per + rs.getDouble("dec_bl_per");
				jan_bl_per = jan_bl_per + rs.getDouble("jan_bl_per");
				feb_bl_per = feb_bl_per + rs.getDouble("feb_bl_per");
				mar_bl_per = mar_bl_per + rs.getDouble("mar_bl_per");
				apr_bl = apr_bl + rs.getDouble("apr_bl");
				may_bl = may_bl + rs.getDouble("may_bl");
				jun_bl = jun_bl + rs.getDouble("jun_bl");
				jul_bl = jul_bl + rs.getDouble("jul_bl");
				aug_bl = aug_bl + rs.getDouble("aug_bl");
				sep_bl = sep_bl + rs.getDouble("sep_bl");
				oct_bl = oct_bl + rs.getDouble("oct_bl");
				nov_bl = nov_bl + rs.getDouble("nov_bl");
				dec_bl = dec_bl + rs.getDouble("dec_bl");
				jan_bl = jan_bl + rs.getDouble("jan_bl");
				feb_bl = feb_bl + rs.getDouble("feb_bl");
				mar_bl = mar_bl + rs.getDouble("mar_bl");
				yearly_mgq = yearly_mgq + rs.getDouble("yearly_mgq");
				monthly_mgq = monthly_mgq + rs.getDouble("monthly_mgq");
				// System.out.println("apr_bl_per="+apr_bl_per);//
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
				cellE1.setCellValue(rs.getDouble("apr_bl"));
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("apr_bl_per"));
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getDouble("may_bl"));
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("may_bl_per"));
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getDouble("jun_bl"));
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getDouble("jun_bl_per"));
				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getDouble("jul_bl"));
				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(rs.getDouble("jul_bl_per"));
				XSSFCell cellM1 = row1.createCell((int) 12);
				cellM1.setCellValue(rs.getDouble("aug_bl"));
				XSSFCell cellN1 = row1.createCell((int) 13);
				cellN1.setCellValue(rs.getDouble("aug_bl_per"));
				XSSFCell cellO1 = row1.createCell((int) 14);
				cellO1.setCellValue(rs.getDouble("sep_bl"));
				XSSFCell cellP1 = row1.createCell((int) 15);
				cellP1.setCellValue(rs.getDouble("sep_bl_per"));
				XSSFCell cellQ1 = row1.createCell((int) 16);
				cellQ1.setCellValue(rs.getDouble("oct_bl"));
				XSSFCell cellR1 = row1.createCell((int) 17);
				cellR1.setCellValue(rs.getDouble("oct_bl_per"));
				XSSFCell cellS1 = row1.createCell((int) 18);
				cellS1.setCellValue(rs.getDouble("nov_bl"));
				XSSFCell cellT1 = row1.createCell((int) 19);
				cellT1.setCellValue(rs.getDouble("nov_bl_per"));
				XSSFCell cellU1 = row1.createCell((int) 20);
				cellU1.setCellValue(rs.getDouble("dec_bl"));
				XSSFCell cellV1 = row1.createCell((int) 21);
				cellV1.setCellValue(rs.getDouble("dec_bl_per"));
				XSSFCell cellW1 = row1.createCell((int) 22);
				cellW1.setCellValue(rs.getDouble("jan_bl"));
				XSSFCell cellX1 = row1.createCell((int) 23);
				cellX1.setCellValue(rs.getDouble("jan_bl_per"));
				XSSFCell cellY1 = row1.createCell((int) 24);
				cellY1.setCellValue(rs.getDouble("feb_bl"));
				XSSFCell cellZ1 = row1.createCell((int) 25);
				cellZ1.setCellValue(rs.getDouble("feb_bl_per"));
				XSSFCell cellAA1 = row1.createCell((int) 26);
				cellAA1.setCellValue(rs.getDouble("mar_bl"));
				XSSFCell cellAB1 = row1.createCell((int) 27);
				cellAB1.setCellValue(rs.getDouble("mar_bl_per"));

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + action.getRadio() + "-" + year
					+ "CL_MGQ_Achievement.xlsx");

			action.setExlname(action.getRadio() + "-" + year);
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
			cellA3.setCellValue(yearly_mgq);
			cellA3.setCellStyle(cellStyle);
			XSSFCell cellA4 = row1.createCell((int) 3);
			cellA4.setCellValue(monthly_mgq);
			cellA4.setCellStyle(cellStyle);
			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(apr_bl); 
			cellA5.setCellStyle(cellStyle); 			
			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(Math.round((apr_bl/monthly_mgq)*100));
			cellA6.setCellStyle(cellStyle);
			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(may_bl);
			cellA7.setCellStyle(cellStyle);
			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(Math.round((may_bl/monthly_mgq)*100));
			cellA8.setCellStyle(cellStyle);
			XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(jun_bl);
			cellA9.setCellStyle(cellStyle);
			XSSFCell cellA10 = row1.createCell((int) 9);
			cellA10.setCellValue(Math.round((jun_bl/monthly_mgq)*100));
			cellA10.setCellStyle(cellStyle);
			XSSFCell cellA11 = row1.createCell((int) 10);
			cellA11.setCellValue(jul_bl);
			cellA11.setCellStyle(cellStyle);
			XSSFCell cellA12 = row1.createCell((int) 11);
			cellA12.setCellValue(Math.round((jul_bl/monthly_mgq)*100));
			cellA12.setCellStyle(cellStyle);
			XSSFCell cellA13 = row1.createCell((int) 12);
			cellA13.setCellValue(aug_bl);
			cellA13.setCellStyle(cellStyle);
			XSSFCell cellA14 = row1.createCell((int) 13);
			cellA14.setCellValue(Math.round((aug_bl/monthly_mgq)*100));
			cellA14.setCellStyle(cellStyle);
			XSSFCell cellA15 = row1.createCell((int) 14);
			cellA15.setCellValue(sep_bl); 
			cellA15.setCellStyle(cellStyle); 
			XSSFCell cellA16 = row1.createCell((int) 15); 
			cellA16.setCellValue(Math.round((sep_bl/monthly_mgq)*100)); 
			cellA16.setCellStyle(cellStyle); 
			XSSFCell cellA17 = row1.createCell((int) 16); 
			cellA17.setCellValue(oct_bl); 
			cellA17.setCellStyle(cellStyle); 
			XSSFCell cellA18 = row1.createCell((int) 17); 
			cellA18.setCellValue(Math.round((oct_bl/monthly_mgq)*100)); 
			cellA18.setCellStyle(cellStyle);
			XSSFCell cellA19 = row1.createCell((int) 18); 
			cellA19.setCellValue(nov_bl); 
			cellA19.setCellStyle(cellStyle); 
			XSSFCell cellA20 = row1.createCell((int) 19); 
			cellA20.setCellValue(Math.round((nov_bl/monthly_mgq)*100)); 
			cellA20.setCellStyle(cellStyle); 
			XSSFCell cellA21 = row1.createCell((int) 20); 
			cellA21.setCellValue(dec_bl);
			cellA21.setCellStyle(cellStyle);
			XSSFCell cellA22 = row1.createCell((int) 21);
			cellA22.setCellValue(Math.round((dec_bl/monthly_mgq)*100));
			cellA22.setCellStyle(cellStyle);
			XSSFCell cellA23 = row1.createCell((int) 22);
			cellA23.setCellValue(jan_bl);
			cellA23.setCellStyle(cellStyle);
			XSSFCell cellA24 = row1.createCell((int) 23);
			cellA24.setCellValue(Math.round((jan_bl/monthly_mgq)*100));
			cellA24.setCellStyle(cellStyle);
			XSSFCell cellA25 = row1.createCell((int) 24);
			cellA25.setCellValue(feb_bl);
			cellA25.setCellStyle(cellStyle);
			XSSFCell cellA26 = row1.createCell((int) 25);
			cellA26.setCellValue(Math.round((feb_bl/monthly_mgq)*100));
			cellA26.setCellStyle(cellStyle);
			XSSFCell cellA27 = row1.createCell((int) 26);
			cellA27.setCellValue(mar_bl);
			cellA27.setCellStyle(cellStyle);
			XSSFCell cellA28 = row1.createCell((int) 27);
			cellA28.setCellValue(Math.round((mar_bl/monthly_mgq)*100));
			cellA28.setCellStyle(cellStyle);

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			action.setExcelFlag(true);
			con.close();

		} catch (Exception e) {

			// System.out.println("xlsx2" + e.getMessage());
			e.printStackTrace();

	/*		FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), e.getMessage()));*/

		} finally {

			try {
				con.close();
			} catch (Exception e) {
			/*	FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e
								.getMessage(), e.getMessage()));
*/
				e.printStackTrace();
			}
		}

		return flag;

	}
	
//===========================excelshop wise	====================================
	
	public boolean excelShopWise(CL2_Mgq_ReportAction action) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();
		String year = action.getYear();

		double apr_bl_per = 0;
		double may_bl_per = 0;
		double jun_bl_per = 0;
		double jul_bl_per = 0;
		double aug_bl_per = 0;
		double sep_bl_per = 0;
		double oct_bl_per = 0;
		double nov_bl_per = 0;
		double dec_bl_per = 0;
		double jan_bl_per = 0;
		double feb_bl_per = 0;
		double mar_bl_per = 0;
		double apr_bl = 0;
		double may_bl = 0;
		double jun_bl = 0;
		double jul_bl = 0;
		double aug_bl = 0;
		double sep_bl = 0;
		double oct_bl = 0;
		double nov_bl = 0;
		double dec_bl = 0;
		double jan_bl = 0;
		double feb_bl = 0;
		double mar_bl = 0;
		double yearly_mgq = 0;
		double monthly_mgq = 0;
		String filter="";
		DecimalFormat diciformatter = new DecimalFormat("#.##");
		String filter2="";
		String reportQuery = null;
		if(!action.getDistid().equalsIgnoreCase("99")){
			filter2=" and b.district_id="+ Integer.parseInt(action.getDistid())+ "";
			System.out.println("filter2="+filter2);
			if(action.getShopId().equalsIgnoreCase("0")){
				 
				//System.out.println("120");
			 
			}else{
				filter = " and b.serial_no='" + action.getShopId() + "'";		
			}
		}else{
			//System.out.println("123");
			filter="";
			filter2 = "";
		}

		if(year.equals("19_20"))
		{
		reportQuery = " SELECT  b.vch_name_of_shop,a.description,COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq,"
				+ " COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,"
				+ " COALESCE(sum(cc.apr_bl),0)apr_bl,COALESCE(sum(cc.may_bl),0)may_bl,COALESCE(sum(cc.jun_bl),0)jun_bl,COALESCE(sum(cc.jul_bl),0)jul_bl,"
				+ " COALESCE(sum(cc.aug_bl),0)aug_bl,COALESCE(sum(cc.sep_bl),0)sep_bl,COALESCE(sum(cc.oct_bl),0)oct_bl,COALESCE(sum(cc.nov_bl),0)nov_bl,"
				+ " COALESCE(sum(cc.dec_bl),0)dec_bl,COALESCE(sum(cc.jan_bl),0)jan_bl,COALESCE(sum(cc.feb_bl),0)feb_bl,COALESCE(sum(cc.mar_bl),0)mar_bl,"
				+ " case when sum(cc.apr_bl)!=0 and sum(cc.apr_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
				+ " then round(CAST(float8((sum(cc.apr_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as apr_bl_per,"
				+ " case when sum(cc.may_bl)!=0 and sum(cc.may_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
				+ " then round(CAST(float8((sum(cc.may_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as may_bl_per,"
				+ " case when sum(cc.jun_bl)!=0 and sum(cc.jun_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
				+ " then round(CAST(float8((sum(cc.jun_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jun_bl_per,"
				+ " case when sum(cc.jul_bl)!=0 and sum(cc.jul_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
				+ " then round(CAST(float8((sum(cc.jul_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jul_bl_per,"
				+ " case when sum(cc.aug_bl)!=0 and sum(cc.aug_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
				+ " then round(CAST(float8((sum(cc.aug_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as aug_bl_per,"
				+ " case when sum(cc.sep_bl)!=0 and sum(cc.sep_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
				+ " then round(CAST(float8((sum(cc.sep_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as sep_bl_per,"
				+ " case when sum(cc.oct_bl)!=0 and sum(cc.oct_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
				+ " then round(CAST(float8((sum(cc.oct_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as oct_bl_per,"
				+ " case when sum(cc.nov_bl)!=0 and sum(cc.nov_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
				+ " then round(CAST(float8((sum(cc.nov_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as nov_bl_per,"
				+ " 	case when sum(cc.dec_bl)!=0 and sum(cc.dec_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
				+ " 		then round(CAST(float8((sum(cc.dec_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as dec_bl_per,"
				+ " 		case when sum(cc.jan_bl)!=0 and sum(cc.jan_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
				+ " 			then round(CAST(float8((sum(cc.jan_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jan_bl_per,"
				+ " 		case when sum(cc.feb_bl)!=0 and sum(cc.feb_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
				+ " 		then round(CAST(float8((sum(cc.feb_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as feb_bl_per,"
				+ " 		case when sum(cc.mar_bl)!=0 and sum(cc.mar_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
				+ " 		then round(CAST(float8((sum(cc.mar_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as mar_bl_per "
				+ " 	FROM public.district a left join retail.retail_shop_19_20_backup b on a.districtid=b.district_id left join "
				+ " 		(select bb.shopId,sum(bb.apr_bl) as apr_bl,sum(bb.may_bl)may_bl,sum(bb.jun_bl)jun_bl,sum(bb.jul_bl)jul_bl,sum(bb.aug_bl)aug_bl,"
				+ " 				sum(bb.sep_bl)sep_bl,sum(bb.oct_bl)oct_bl,sum(bb.nov_bl)nov_bl,sum(bb.dec_bl)dec_bl,sum(bb.jan_bl)jan_bl,sum(bb.feb_bl)feb_bl,sum(bb.mar_bl)mar_bl "
				+ " 				from( " +
				
" select aa.shopId,aa.dispatch_date,"+
"  aa.bl_1,aa.bl_2,aa.bl_3,"+

"  case when EXTRACT(MONTH FROM aa.dispatch_date)=4"+
" 			then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as apr_bl,"+
"    "+

"  case when EXTRACT(MONTH FROM aa.dispatch_date)=5 "+
"  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))   end as may_bl, "+

"  case when EXTRACT(MONTH FROM aa.dispatch_date)=6 "+
"  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as jun_bl, "+

"  case when EXTRACT(MONTH FROM aa.dispatch_date)=7 "+
"  		then(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as jul_bl,"+

"  case when EXTRACT(MONTH FROM aa.dispatch_date)=8 "+
"  	then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as aug_bl,"+

"   case when EXTRACT(MONTH FROM aa.dispatch_date)=9"+
" 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as sep_bl, "+

"  case when EXTRACT(MONTH FROM aa.dispatch_date)=10 "+
"  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as oct_bl, 	"+

"  case when EXTRACT(MONTH FROM aa.dispatch_date)=11"+
" 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as nov_bl, 	"+

" case when EXTRACT(MONTH FROM aa.dispatch_date)=12 "+
" 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as dec_bl,"+

" case when EXTRACT(MONTH FROM aa.dispatch_date)=1 "+
"	  then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as jan_bl, 	"+

" case when EXTRACT(MONTH FROM aa.dispatch_date)=2 "+
"  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as feb_bl, 	"+

" case when EXTRACT(MONTH FROM aa.dispatch_date)=3 "+
" 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as mar_bl 	"+
		
"  	   from( 	"+
		
" 	  select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,"+
  
"  case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1, "+

"  case when  m.strength=36 then sum(bl) end as bl_2 , "+

" case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3 "
				
				
				+ " from			(select x.dispatch_date,x.shopId,(((x.bottal)*f.qnt_ml_detail)/1000) as bl,d.strength from "
				+ " 					(select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal "
				+ " 							from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+year+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b "
				+ " 						where a.dt_date<'2020-04-01' and  a.vch_from='CL2' and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null "
				+
				// "and a.shop_id='"+filter+"' " +
				" group by a.shop_id,"
				+ " a.dt_date,b.int_pckg_id,b.int_brand_id )x,"
				+ " 						distillery.box_size_details f,distillery.brand_registration_"+year+" d,distillery.packaging_details_"+year+" e "
				+ " 						where x.int_brand_id=d.brand_id and e.box_id=f.box_id and x.package_id=e.package_id)m group by m.dispatch_date,m.shopId ,m.strength "
				+ " 		)aa group by aa.dispatch_date,aa.shopId, aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId order by bb.shopId "
				+ " 			)cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'   " + filter +filter2
				+ " group by  a.description,b.vch_name_of_shop order by a.description";

		}else {

			reportQuery = " SELECT  b.vch_name_of_shop,a.description,COALESCE(round(CAST(float8(sum(vch_yearly_mgq)) as numeric), 2),0)yearly_mgq,"
					+ " COALESCE(round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2),0) as monthly_mgq,"
					+ " COALESCE(sum(cc.apr_bl),0)apr_bl,COALESCE(sum(cc.may_bl),0)may_bl,COALESCE(sum(cc.jun_bl),0)jun_bl,COALESCE(sum(cc.jul_bl),0)jul_bl,"
					+ " COALESCE(sum(cc.aug_bl),0)aug_bl,COALESCE(sum(cc.sep_bl),0)sep_bl,COALESCE(sum(cc.oct_bl),0)oct_bl,COALESCE(sum(cc.nov_bl),0)nov_bl,"
					+ " COALESCE(sum(cc.dec_bl),0)dec_bl,COALESCE(sum(cc.jan_bl),0)jan_bl,COALESCE(sum(cc.feb_bl),0)feb_bl,COALESCE(sum(cc.mar_bl),0)mar_bl,"
					+ " case when sum(cc.apr_bl)!=0 and sum(cc.apr_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.apr_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as apr_bl_per,"
					+ " case when sum(cc.may_bl)!=0 and sum(cc.may_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.may_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as may_bl_per,"
					+ " case when sum(cc.jun_bl)!=0 and sum(cc.jun_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.jun_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jun_bl_per,"
					+ " case when sum(cc.jul_bl)!=0 and sum(cc.jul_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.jul_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jul_bl_per,"
					+ " case when sum(cc.aug_bl)!=0 and sum(cc.aug_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.aug_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as aug_bl_per,"
					+ " case when sum(cc.sep_bl)!=0 and sum(cc.sep_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.sep_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as sep_bl_per,"
					+ " case when sum(cc.oct_bl)!=0 and sum(cc.oct_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.oct_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as oct_bl_per,"
					+ " case when sum(cc.nov_bl)!=0 and sum(cc.nov_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " then round(CAST(float8((sum(cc.nov_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as nov_bl_per,"
					+ " 	case when sum(cc.dec_bl)!=0 and sum(cc.dec_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " 		then round(CAST(float8((sum(cc.dec_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as dec_bl_per,"
					+ " 		case when sum(cc.jan_bl)!=0 and sum(cc.jan_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " 			then round(CAST(float8((sum(cc.jan_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as jan_bl_per,"
					+ " 		case when sum(cc.feb_bl)!=0 and sum(cc.feb_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " 		then round(CAST(float8((sum(cc.feb_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as feb_bl_per,"
					+ " 		case when sum(cc.mar_bl)!=0 and sum(cc.mar_bl) is not null and sum(vch_yearly_mgq)!=0 and sum(vch_yearly_mgq) is not null "
					+ " 		then round(CAST(float8((sum(cc.mar_bl)*100)/round(CAST(float8(sum(vch_yearly_mgq)/12) as numeric), 2)) as numeric), 2) else 0 end as mar_bl_per "
					+ " 	FROM public.district a left join retail.retail_shop b on a.districtid=b.district_id left join "
					+ " 		(select bb.shopId,sum(bb.apr_bl) as apr_bl,sum(bb.may_bl)may_bl,sum(bb.jun_bl)jun_bl,sum(bb.jul_bl)jul_bl,sum(bb.aug_bl)aug_bl,"
					+ " 				sum(bb.sep_bl)sep_bl,sum(bb.oct_bl)oct_bl,sum(bb.nov_bl)nov_bl,sum(bb.dec_bl)dec_bl,sum(bb.jan_bl)jan_bl,sum(bb.feb_bl)feb_bl,sum(bb.mar_bl)mar_bl "
					+ " 				from( " +
					
	" select aa.shopId,aa.dispatch_date,"+
	"  aa.bl_1,aa.bl_2,aa.bl_3,"+

	"  case when EXTRACT(MONTH FROM aa.dispatch_date)=4"+
	" 			then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as apr_bl,"+
	"    "+

	"  case when EXTRACT(MONTH FROM aa.dispatch_date)=5 "+
	"  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))   end as may_bl, "+

	"  case when EXTRACT(MONTH FROM aa.dispatch_date)=6 "+
	"  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as jun_bl, "+

	"  case when EXTRACT(MONTH FROM aa.dispatch_date)=7 "+
	"  		then(coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as jul_bl,"+

	"  case when EXTRACT(MONTH FROM aa.dispatch_date)=8 "+
	"  	then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as aug_bl,"+

	"   case when EXTRACT(MONTH FROM aa.dispatch_date)=9"+
	" 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as sep_bl, "+

	"  case when EXTRACT(MONTH FROM aa.dispatch_date)=10 "+
	"  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as oct_bl, 	"+

	"  case when EXTRACT(MONTH FROM aa.dispatch_date)=11"+
	" 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as nov_bl, 	"+

	" case when EXTRACT(MONTH FROM aa.dispatch_date)=12 "+
	" 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as dec_bl,"+

	" case when EXTRACT(MONTH FROM aa.dispatch_date)=1 "+
	"	  then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as jan_bl, 	"+

	" case when EXTRACT(MONTH FROM aa.dispatch_date)=2 "+
	"  		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as feb_bl, 	"+

	" case when EXTRACT(MONTH FROM aa.dispatch_date)=3 "+
	" 		then (coalesce(sum(aa.bl_1),0)+coalesce(sum(aa.bl_2),0)+coalesce(sum(aa.bl_3),0))  end as mar_bl 	"+
			
	"  	   from( 	"+
			
	" 	  select m.dispatch_date,m.shopId,sum(bl) as bl,m.strength,"+
	  
	"  case when  m.strength=25 then round(CAST(float8(sum(bl))as numeric),2)*25/36 end as bl_1, "+

	"  case when  m.strength=36 then sum(bl) end as bl_2 , "+

	" case when  m.strength=42.80 then round(CAST(float8(sum(bl))as numeric),2)*42.80/36 end as bl_3 "
					
					
					+ " from			(select x.dispatch_date,x.shopId,  bl,x.strength from "
					+ " 					(select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal,((sum(b.dispatch_bottle)*f.qnt_ml_detail)/1000) as bl ,d.strength "
					+ " 							from fl2d.gatepass_to_districtwholesale_fl2_fl2b_"+year+" a,fl2d.fl2_stock_trxn_fl2_fl2b_"+year+" b ,distillery.box_size_details f,distillery.brand_registration_20_21 d,distillery.packaging_details_20_21 e "
					+ " 						where  a.vch_from='CL2'  and  b.int_brand_id=d.brand_id  and e.box_id=f.box_id and b.int_pckg_id=e.package_id and b.vch_gatepass_no=a.vch_gatepass_no and a.shop_id!='0' and a.shop_id is not null "
					+
					// "and a.shop_id='"+filter+"' " +
					" group by a.shop_id,"
					+ " a.dt_date,b.int_pckg_id,b.int_brand_id ,f.qnt_ml_detail,d.strength union " +
					 " select b.int_pckg_id as package_id,b.int_brand_id, a.dt_date as dispatch_date,a.shop_id as shopId,sum(b.dispatch_bottle) as bottal,((sum(b.dispatch_bottle)*f.qnt_ml_detail)/1000) as bl,d.strength "
							+ " 							from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,fl2d.fl2_stock_trxn_fl2_fl2b_19_20 b ,distillery.box_size_details f,distillery.brand_registration_19_20 d,distillery.packaging_details_19_20 e "
							+ " 						where  a.vch_from='CL2'  and  b.int_brand_id=d.brand_id  and e.box_id=f.box_id and b.int_pckg_id=e.package_id and b.vch_gatepass_no=a.vch_gatepass_no and  dt_date>'2020-04-01' and a.shop_id!='0' and a.shop_id is not null "+
							 " group by a.shop_id,"  
							+ " a.dt_date,b.int_pckg_id,b.int_brand_id,f.qnt_ml_detail,d.strength )x "
					+ " 						"
					+ " 						 )m group by m.dispatch_date,m.shopId ,m.strength "
					+ " 		)aa group by aa.dispatch_date,aa.shopId, aa.bl_1,aa.bl_2,aa.bl_3)bb group by  bb.shopId order by bb.shopId "
					+ " 			)cc on b.serial_no::text=cc.shopId where b.vch_shop_type='Country Liquor'   " + filter +filter2
					+ " group by  a.description,b.vch_name_of_shop order by a.description";

			
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

			pstmt = con.prepareStatement(reportQuery);

			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook
					.createSheet("Cl2 MGQ DistrictWise Report");

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
			worksheet.setColumnWidth(12, 5000);
			worksheet.setColumnWidth(13, 5000);
			worksheet.setColumnWidth(14, 5000);
			worksheet.setColumnWidth(15, 5000);
			worksheet.setColumnWidth(16, 5000);
			worksheet.setColumnWidth(17, 5000);
			worksheet.setColumnWidth(18, 5000);
			worksheet.setColumnWidth(19, 5000);
			worksheet.setColumnWidth(20, 5000);
			worksheet.setColumnWidth(21, 5000);
			worksheet.setColumnWidth(22, 5000);
			worksheet.setColumnWidth(23, 5000);
			worksheet.setColumnWidth(24, 5000);
			worksheet.setColumnWidth(25, 5000);
			worksheet.setColumnWidth(26, 5000);
			worksheet.setColumnWidth(26, 5000);
			worksheet.setColumnWidth(27, 5000);
 
			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("CL - MGQ Achievement ShopWise");
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

			XSSFCell cellhead2 = rowhead.createCell((int) 2);
			cellhead2.setCellValue("District Name");
			cellhead2.setCellStyle(cellStyle);
			XSSFCell cellhead3 = rowhead.createCell((int) 1);
			cellhead3.setCellValue("Shop Name");
			cellhead3.setCellStyle(cellStyle);

			
			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Yearly MGQ");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Monthly MGQ");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("April BL(Actual)");
			cellhead6.setCellStyle(cellStyle);
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("April BL(in %)");
			cellhead7.setCellStyle(cellStyle);

			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("May BL(Actual)");
			cellhead8.setCellStyle(cellStyle);
			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("May BL(in %)");
			cellhead9.setCellStyle(cellStyle);

			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("June BL(Actual)");
			cellhead10.setCellStyle(cellStyle);
			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("June BL(in %)");
			cellhead11.setCellStyle(cellStyle);

			XSSFCell cellhead12 = rowhead.createCell((int) 11);
			cellhead12.setCellValue("July BL(Actual)");
			cellhead12.setCellStyle(cellStyle);
			XSSFCell cellhead13 = rowhead.createCell((int) 12);
			cellhead13.setCellValue("July BL(in %)");
			cellhead13.setCellStyle(cellStyle);

			XSSFCell cellhead14 = rowhead.createCell((int) 13);
			cellhead14.setCellValue("August BL(Actual)");
			cellhead14.setCellStyle(cellStyle);
			XSSFCell cellhead15 = rowhead.createCell((int) 14);
			cellhead15.setCellValue("August BL(in %)");
			cellhead15.setCellStyle(cellStyle);

			XSSFCell cellhead16 = rowhead.createCell((int) 15);
			cellhead16.setCellValue("September BL(Actual)");
			cellhead16.setCellStyle(cellStyle);
			XSSFCell cellhead17 = rowhead.createCell((int) 16);
			cellhead17.setCellValue("September BL(in %)");
			cellhead17.setCellStyle(cellStyle);

			XSSFCell cellhead18 = rowhead.createCell((int) 17);
			cellhead18.setCellValue("October BL(Actual)");
			cellhead18.setCellStyle(cellStyle);
			XSSFCell cellhead19 = rowhead.createCell((int) 18);
			cellhead19.setCellValue("October BL(in %)");
			cellhead19.setCellStyle(cellStyle);

			XSSFCell cellhead20 = rowhead.createCell((int) 19);
			cellhead20.setCellValue("November BL(Actual)");
			cellhead20.setCellStyle(cellStyle);
			XSSFCell cellhead21 = rowhead.createCell((int) 20);
			cellhead21.setCellValue("November BL(in %)");
			cellhead21.setCellStyle(cellStyle);

			XSSFCell cellhead22 = rowhead.createCell((int) 21);
			cellhead22.setCellValue("December BL(Actual)");
			cellhead22.setCellStyle(cellStyle);
			XSSFCell cellhead23 = rowhead.createCell((int) 22);
			cellhead23.setCellValue("December BL(in %)");
			cellhead23.setCellStyle(cellStyle);

			XSSFCell cellhead24 = rowhead.createCell((int) 23);
			cellhead24.setCellValue("January BL(Actual)");
			cellhead24.setCellStyle(cellStyle);
			XSSFCell cellhead25 = rowhead.createCell((int) 24);
			cellhead25.setCellValue("January BL(in %)");
			cellhead25.setCellStyle(cellStyle);

			XSSFCell cellhead26 = rowhead.createCell((int) 25);
			cellhead26.setCellValue("February BL(Actual)");
			cellhead26.setCellStyle(cellStyle);
			XSSFCell cellhead27 = rowhead.createCell((int) 26);
			cellhead27.setCellValue("February BL(in %)");
			cellhead27.setCellStyle(cellStyle);

			XSSFCell cellhead28 = rowhead.createCell((int) 27);
			cellhead28.setCellValue("March BL(Actual)");
			cellhead28.setCellStyle(cellStyle);
			XSSFCell cellhead29 = rowhead.createCell((int) 28);
			cellhead29.setCellValue("March BL(in %)");
			cellhead29.setCellStyle(cellStyle);
			

			int i = 0;
			while (rs.next()) {// System.out.println(rs.getDouble("apr_bl_per"));
				apr_bl_per = apr_bl_per + rs.getDouble("apr_bl_per");
				may_bl_per = may_bl_per + rs.getDouble("may_bl_per");
				jun_bl_per = jun_bl_per + rs.getDouble("jun_bl_per");
				jul_bl_per = jul_bl_per + rs.getDouble("jul_bl_per");
				aug_bl_per = aug_bl_per + rs.getDouble("aug_bl_per");
				sep_bl_per = sep_bl_per + rs.getDouble("sep_bl_per");
				oct_bl_per = oct_bl_per + rs.getDouble("oct_bl_per");
				nov_bl_per = nov_bl_per + rs.getDouble("nov_bl_per");
				dec_bl_per = dec_bl_per + rs.getDouble("dec_bl_per");
				jan_bl_per = jan_bl_per + rs.getDouble("jan_bl_per");
				feb_bl_per = feb_bl_per + rs.getDouble("feb_bl_per");
				mar_bl_per = mar_bl_per + rs.getDouble("mar_bl_per");
				apr_bl = apr_bl + rs.getDouble("apr_bl");
				may_bl = may_bl + rs.getDouble("may_bl");
				jun_bl = jun_bl + rs.getDouble("jun_bl");
				jul_bl = jul_bl + rs.getDouble("jul_bl");
				aug_bl = aug_bl + rs.getDouble("aug_bl");
				sep_bl = sep_bl + rs.getDouble("sep_bl");
				oct_bl = oct_bl + rs.getDouble("oct_bl");
				nov_bl = nov_bl + rs.getDouble("nov_bl");
				dec_bl = dec_bl + rs.getDouble("dec_bl");
				jan_bl = jan_bl + rs.getDouble("jan_bl");
				feb_bl = feb_bl + rs.getDouble("feb_bl");
				mar_bl = mar_bl + rs.getDouble("mar_bl");
				yearly_mgq = yearly_mgq + rs.getDouble("yearly_mgq");
				monthly_mgq = monthly_mgq + rs.getDouble("monthly_mgq");
				// System.out.println("apr_bl_per="+apr_bl_per);//
				k++; //
				XSSFRow row1 = worksheet.createRow((int) k); //
				XSSFCell cellA1 = row1.createCell((int) 0); //
				cellA1.setCellValue(k - 1); //
				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));
				
				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("vch_name_of_shop"));
				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("yearly_mgq"));
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("monthly_mgq"));
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("apr_bl"));
				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("apr_bl_per"));
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("may_bl"));
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getDouble("may_bl_per"));
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getDouble("jun_bl"));
				XSSFCell cellK1 = row1.createCell((int) 10);
				cellK1.setCellValue(rs.getDouble("jun_bl_per"));
				XSSFCell cellL1 = row1.createCell((int) 11);
				cellL1.setCellValue(rs.getDouble("jul_bl"));
				XSSFCell cellM1 = row1.createCell((int) 12);
				cellM1.setCellValue(rs.getDouble("jul_bl_per"));
				XSSFCell cellN1 = row1.createCell((int) 13);
				cellN1.setCellValue(rs.getDouble("aug_bl"));
				XSSFCell cellO1 = row1.createCell((int) 14);
				cellO1.setCellValue(rs.getDouble("aug_bl_per"));
				XSSFCell cellP1 = row1.createCell((int) 15);
				cellP1.setCellValue(rs.getDouble("sep_bl"));
				XSSFCell cellQ1 = row1.createCell((int) 16);
				cellQ1.setCellValue(rs.getDouble("sep_bl_per"));
				XSSFCell cellR1 = row1.createCell((int) 17);
				cellR1.setCellValue(rs.getDouble("oct_bl"));
				XSSFCell cellS1 = row1.createCell((int) 18);
				cellS1.setCellValue(rs.getDouble("oct_bl_per"));
				XSSFCell cellT1 = row1.createCell((int) 19);
				cellT1.setCellValue(rs.getDouble("nov_bl"));
				XSSFCell cellU1 = row1.createCell((int) 20);
				cellU1.setCellValue(rs.getDouble("nov_bl_per"));
				XSSFCell cellV1 = row1.createCell((int) 21);
				cellV1.setCellValue(rs.getDouble("dec_bl"));
				XSSFCell cellW1 = row1.createCell((int) 22);
				cellW1.setCellValue(rs.getDouble("dec_bl_per"));
				XSSFCell cellX1 = row1.createCell((int) 23);
				cellX1.setCellValue(rs.getDouble("jan_bl"));
				XSSFCell cellY1 = row1.createCell((int) 24);
				cellY1.setCellValue(rs.getDouble("jan_bl_per"));
				XSSFCell cellZ1 = row1.createCell((int) 25);
				cellZ1.setCellValue(rs.getDouble("feb_bl"));
				XSSFCell cellAA1 = row1.createCell((int) 26);
				cellAA1.setCellValue(rs.getDouble("feb_bl_per"));
				XSSFCell cellAB1 = row1.createCell((int) 27);
				cellAB1.setCellValue(rs.getDouble("mar_bl"));
				XSSFCell cellAC1 = row1.createCell((int) 28);
				cellAC1.setCellValue(rs.getDouble("mar_bl_per"));
			

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + action.getRadio() + "-" + year
					+ "CL_MGQ_Achievement.xlsx");

			action.setExlname(action.getRadio() + "-" + year);
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
			cellA4.setCellValue(yearly_mgq);
			cellA4.setCellStyle(cellStyle);
			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(monthly_mgq);
			cellA5.setCellStyle(cellStyle);
			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(apr_bl); 
			cellA6.setCellStyle(cellStyle);
			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(Math.round((apr_bl/monthly_mgq)*100));
			cellA7.setCellStyle(cellStyle);
			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(may_bl);
			cellA8.setCellStyle(cellStyle);
			XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(Math.round((may_bl/monthly_mgq)*100));
			cellA9.setCellStyle(cellStyle);
			XSSFCell cellA10 = row1.createCell((int) 9);
			cellA10.setCellValue(jun_bl);
			cellA10.setCellStyle(cellStyle);
			XSSFCell cellA11 = row1.createCell((int) 10);
			cellA11.setCellValue(Math.round((jun_bl/monthly_mgq)*100));
			cellA11.setCellStyle(cellStyle);
			XSSFCell cellA12 = row1.createCell((int) 11);
			cellA12.setCellValue(jul_bl);
			cellA12.setCellStyle(cellStyle);
			XSSFCell cellA13 = row1.createCell((int) 12);
			cellA13.setCellValue(Math.round((jul_bl/monthly_mgq)*100));
			cellA13.setCellStyle(cellStyle);
			XSSFCell cellA14 = row1.createCell((int) 13);
			cellA14.setCellValue(aug_bl);
			cellA14.setCellStyle(cellStyle);
			XSSFCell cellA15 = row1.createCell((int) 14);
			cellA15.setCellValue(Math.round((aug_bl/monthly_mgq)*100));
			cellA15.setCellStyle(cellStyle);
			XSSFCell cellA16 = row1.createCell((int) 15);
			cellA16.setCellValue(sep_bl); 
			cellA16.setCellStyle(cellStyle); 
			XSSFCell cellA17 = row1.createCell((int) 16); 
			cellA17.setCellValue(Math.round((sep_bl/monthly_mgq)*100)); 
			cellA17.setCellStyle(cellStyle); 
			XSSFCell cellA18 = row1.createCell((int) 17); 
			cellA18.setCellValue(oct_bl); 
			cellA18.setCellStyle(cellStyle); 
			XSSFCell cellA19 = row1.createCell((int) 18); 
			cellA19.setCellValue(Math.round((oct_bl/monthly_mgq)*100)); 
			cellA19.setCellStyle(cellStyle);
			XSSFCell cellA20 = row1.createCell((int) 19); 
			cellA20.setCellValue(nov_bl); 
			cellA20.setCellStyle(cellStyle); 
			XSSFCell cellA21 = row1.createCell((int) 20); 
			cellA21.setCellValue(Math.round((nov_bl/monthly_mgq)*100)); 
			cellA21.setCellStyle(cellStyle); 
			XSSFCell cellA22 = row1.createCell((int) 21); 
			cellA22.setCellValue(dec_bl);
			cellA22.setCellStyle(cellStyle);
			XSSFCell cellA23 = row1.createCell((int) 22);
			cellA23.setCellValue(Math.round((dec_bl/monthly_mgq)*100));
			cellA23.setCellStyle(cellStyle);
			XSSFCell cellA24 = row1.createCell((int) 23);
			cellA24.setCellValue(jan_bl);
			cellA24.setCellStyle(cellStyle);
			XSSFCell cellA25 = row1.createCell((int) 24);
			cellA25.setCellValue(Math.round((jan_bl/monthly_mgq)*100));
			cellA24.setCellStyle(cellStyle);
			XSSFCell cellA26 = row1.createCell((int) 25);
			cellA26.setCellValue(feb_bl);
			cellA26.setCellStyle(cellStyle);
			XSSFCell cellA27 = row1.createCell((int) 26);
			cellA27.setCellValue(Math.round((feb_bl/monthly_mgq)*100));
			cellA27.setCellStyle(cellStyle);
			XSSFCell cellA28 = row1.createCell((int) 27);
			cellA28.setCellValue(mar_bl);
			cellA28.setCellStyle(cellStyle);
			XSSFCell cellA29 = row1.createCell((int) 28);
			cellA29.setCellValue(Math.round((mar_bl/monthly_mgq)*100));
			cellA29.setCellStyle(cellStyle);



			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			action.setExcelFlag(true);
			con.close();

		} catch (Exception e) {

			// System.out.println("xlsx2" + e.getMessage());
			e.printStackTrace();

		/*	FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), e.getMessage()));
*/
		} finally {

			try {
				con.close();
			} catch (Exception e) {
		/*		FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e
								.getMessage(), e.getMessage()));

	*/			e.printStackTrace();
			}
		}

		return flag;

	}
	
	public ArrayList getShop(CL2_Mgq_ReportAction act, String o) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--ALL--");
		item.setValue("0");
		list.add(item);
		try {
			String query = " select serial_no ,vch_name_of_shop from retail.retail_shop where district_id="
					+ Integer.parseInt(o)
					+ ""
					+ " and vch_shop_type='Country Liquor' order by trim(vch_name_of_shop)";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);

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
	
	
	
	//===========================year==============================================	
	
	public ArrayList yearListImpl(CL2_Mgq_ReportAction act) {
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
			//	System.out.println("== year== "+query);

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
