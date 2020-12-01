package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.mentor.action.SpecialFeeReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.*;

public class SpecialFeeReportImpl {

	public void printReportPDF(SpecialFeeReportAction action)
	{
		Connection conn=null;
	    PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    String sql="";
	    JasperPrint jasperPrint=null;
		String bwflFl2d=
				                                                                                                                                   
			"	select x.vch_firm_name||'-'||x.type as name,x.cr_date,x.description,x.permit,x.bl,x.specialfee from                                "+
			"	(select b.vch_firm_name,a.cr_date,c.description,a.permitno as permit,'BWFL' as type,                                               "+
			"	((a.int_planned_bottles*int_quantity)/1000)as bl,(((a.int_planned_bottles*int_quantity)/1000)*2) as specialfee                     "+
			"from bwfl_license.mst_bottling_plan_19_20 a,                                                                                          "+
			"	bwfl.registration_of_bwfl_lic_holder_19_20 b,                                                                                      "+
			"	public.district c                                                                                                                  "+
			"	where a.licence_no=b.vch_license_number                                                                                            "+
			"	 and c.districtid=b.vch_firm_district_id::numeric                                                                                  "+
			"	union all                                                                                                                          "+
			"	 select b.vch_firm_name,a.cr_date,c.description,a.permit_no as permit,'FL2D' as type,                                              "+
			"	 ((a.int_planned_bottles*int_quantity)/1000)as bl,(((a.int_planned_bottles*int_quantity)/1000)*10) as specialfee                   "+
			"	 from fl2d.mst_stock_receive_19_20 a,licence.fl2_2b_2d_19_20 b ,public.district c                                                  "+
			"	 where a.licence_no=b.vch_licence_no and c.districtid=b.core_district_id::numeric and int_liquor_type=2                            "+
			"	 union all                                                                                                                         "+
			"	 select b.vch_firm_name,a.cr_date,c.description,a.permit_no as permit,'FL2D' as type,                                              "+
			"	 ((a.int_planned_bottles*int_quantity)/1000)as bl,(((a.int_planned_bottles*int_quantity)/1000)*20) as specialfee                   "+
			"	 from fl2d.mst_stock_receive_19_20 a,licence.fl2_2b_2d_19_20 b ,public.district c                                                  "+
			"	 where a.licence_no=b.vch_licence_no and c.districtid=b.core_district_id::numeric and int_liquor_type!=2)x where x.cr_date between "+
			"        ? and ?                                                                                                                                 "+
			"	 order by x.description,x.vch_firm_name,x.type                                                                                     ";
		
		
		
		
		
		                                                                                                                                         
	String dis=
		"	select  x.vch_license_type,x.qnt_ml_detail, x.type, x.name,sum(x.totalbottle) as bottle,                                   "+
		"	x.date,x.couses,(sum(x.totalbottle)*x.couses)as specialfee from                                                            "+
		"	(                                                                                                                          "+
		"	( select b.vch_license_type,d.qnt_ml_detail,1 as type, e.vch_undertaking_name as name,                                     "+
		"	sum(a.int_no_bottle)as totalbottle,b.date_currunt_date as date,d.couses_fl3 as couses ,                              "+
		"	(sum(a.int_no_bottle)*d.couses_fl3)as specialfee                                                                     "+
		"	from distillery.bottling_dtl_19_20 a ,                                                                                     "+
		"	distillery.bottling_master_19_20 b,                                                                                        "+
		"	distillery.packaging_details_19_20 c,                                                                                      "+
		"	distillery.box_size_details d,public.dis_mst_pd1_pd2_lic e ,distillery.brand_registration_19_20 f                          "+
		"	where b.int_id=a.bottoling_masterid_fk and a.int_brand_id=c.brand_id_fk  and c.brand_id_fk=f.brand_id                      "+
		"	and a.int_brand_id=f.brand_id  and liquor_category!=1    and a.double_quantity=d.qnt_ml_detail  and d.type='D'                                                                     "+
		"	and a.vch_description::numeric=c.package_id and a.int_dissleri_id=e.int_app_id_f and b.vch_license_type='FL3'              "+
		"	and c.box_id=d.box_id group by e.vch_undertaking_name,b.date_currunt_date,d.couses_fl3,                                    "+
		"	b.vch_license_type,d.qnt_ml_detail order by e.vch_undertaking_name)                                                        "+
		"	UNION all                                                                                                                  "+
		"	(select b.vch_license_type,d.qnt_ml_detail,1 as type, e.vch_undertaking_name as name,                                      "+
		"	sum(a.int_no_bottle)as totalbottle,b.date_currunt_date as date,d.couses_fl3a as couses ,                             "+
		"	(sum(a.int_no_bottle)*d.couses_fl3a)as specialfee                                                                    "+
		"	from distillery.bottling_dtl_19_20 a ,                                                                                     "+
		"	distillery.bottling_master_19_20 b,                                                                                        "+
		"	distillery.packaging_details_19_20 c,                                                                                      "+
		"	distillery.box_size_details d,public.dis_mst_pd1_pd2_lic e ,distillery.brand_registration_19_20 f                          "+
		"	where b.int_id=a.bottoling_masterid_fk and a.int_brand_id=c.brand_id_fk  and c.brand_id_fk=f.brand_id                      "+
		"	and a.int_brand_id=f.brand_id  and liquor_category!=1        and a.double_quantity=d.qnt_ml_detail  and d.type='D'                                                                    "+
		"	and a.vch_description::numeric=c.package_id and a.int_dissleri_id=e.int_app_id_f and b.vch_license_type='FL3A'             "+
		"	and c.box_id=d.box_id group by e.vch_undertaking_name,b.date_currunt_date,d.couses_fl3a,b.vch_license_type,                "+
		"	 d.qnt_ml_detail order by e.vch_undertaking_name)                                                                          "+
        "                                                                                                                              "+
		"	UNION all                                                                                                                  "+
        "                                                                                                                              "+
		"	(select b.vch_license_type,d.qnt_ml_detail, 2 as type,e.brewery_nm as name,sum(a.int_no_bottle)as bottle ,           "+
		"	b.date_currunt_date as date,d.couses_fl3 as couses  ,(sum(a.int_no_bottle)*d.couses_fl3)as specialfee                "+
		"	from bwfl.bottling_dtl_19_20 a ,                                                                                           "+
		"	bwfl.bottling_master_19_20 b,                                                                                              "+
		"	distillery.packaging_details_19_20 c,                                                                                      "+
		"	distillery.box_size_details d,public.bre_mst_b1_lic e ,distillery.brand_registration_19_20 f                               "+
		"	where b.int_id=a.bottoling_masterid_fk and a.int_brand_id=c.brand_id_fk  and c.brand_id_fk=f.brand_id                      "+
		"	and a.int_brand_id=f.brand_id  and liquor_category!=1     and a.double_quantity=d.qnt_ml_detail  and d.type='B'                                                                     "+
		"	and a.vch_description::numeric=c.package_id and a.int_brewery_id=e.vch_app_id_f and b.vch_license_type='FL3'               "+
		"	and c.box_id=d.box_id group by type,e.brewery_nm,b.date_currunt_date,d.couses_fl3,b.vch_license_type,d.qnt_ml_detail       "+
		"	order by e.brewery_nm)                                                                                                     "+
		"	UNION all                                                                                                                  "+
		"	(select b.vch_license_type,d.qnt_ml_detail, 2 as type,e.brewery_nm as name,sum(a.int_no_bottle)as bottle ,           "+
		"	b.date_currunt_date as date,d.couses_fl3a as couses  ,(sum(a.int_no_bottle)*d.couses_fl3a)as specialfee              "+
		"	from bwfl.bottling_dtl_19_20 a ,                                                                                           "+
		"	bwfl.bottling_master_19_20 b,                                                                                              "+
		"	distillery.packaging_details_19_20 c,                                                                                      "+
		"	distillery.box_size_details d,public.bre_mst_b1_lic e ,distillery.brand_registration_19_20 f                               "+
		"	where b.int_id=a.bottoling_masterid_fk and a.int_brand_id=c.brand_id_fk  and c.brand_id_fk=f.brand_id                      "+
		"	 and a.int_brand_id=f.brand_id  and liquor_category!=1     and a.double_quantity=d.qnt_ml_detail  and d.type='B'                                                                    "+
		"	and a.vch_description::numeric=c.package_id and a.int_brewery_id=e.vch_app_id_f and b.vch_license_type='FL3A'              "+
		"   group by type,e.brewery_nm,b.date_currunt_date,d.couses_fl3a,b.vch_license_type,d.qnt_ml_detail      "+
		"	 order by e.brewery_nm)                                                                                                    "+
		"	)x where x.date between ? and ? group by x.vch_license_type,x.qnt_ml_detail, x.name,x.date,x.couses,x.type order by x.type,x.name                       ";
                                                                                                                                       
                                                                                               
		
	String pathjasper=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH+File.separator+"ExciseUp"+File.separator+"specialfee"+File.separator+"jasper"+File.separator;
		
	String pathpdf=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH+File.separator+"ExciseUp"+File.separator+"specialfee"+File.separator+"pdf"+File.separator;	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		try{
			if(action.getSelectRadio().equals("B"))
			{
				sql=dis;
			}else if(action.getSelectRadio().equals("P"))
			{
				sql=bwflFl2d;
			}
			
			
			conn=ConnectionToDataBase.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setDate(1, new java.sql.Date(action.getFromDate().getTime()) );
			pstmt.setDate(2, new java.sql.Date(action.getToDate().getTime()) );
			rs=pstmt.executeQuery();
			if(rs.next())
			{
				rs=pstmt.executeQuery();
				JRResultSetDataSource jrd=new JRResultSetDataSource(rs);
				HashMap map=new HashMap();
				map.put("fromDate", action.getFromDate());
				map.put("toDate", action.getToDate());
				map.put("image", pathjasper);
				if(action.getSelectRadio().equals("B"))
				{
				jasperPrint=JasperFillManager.fillReport(pathjasper+"SpecialFeeDisBre.jasper", map, jrd);
				}else if(action.getSelectRadio().equals("P"))
				{
					jasperPrint=JasperFillManager.fillReport(pathjasper+"SpecialFeeBwflFl2d.jasper", map, jrd);	
				}
				
				Random rm=new Random();
				int i=rm.nextInt(500);
				action.setPdfName("specialfee"+i+".pdf");
				JasperExportManager.exportReportToPdfFile(jasperPrint, pathpdf+"specialfee"+i+".pdf");
				
				action.setPrintFlag(true);
				
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No RECORD Found", "No RECORD Found"));
				action.setPrintFlag(false);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			
			try{
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
				
			}
			
		}
	}
	public void printReportPDF2020(SpecialFeeReportAction action)
	{
		Connection conn=null;
	    PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    String sql="";
	    JasperPrint jasperPrint=null;
		String bwflFl2d=
				                                                                                                                                   
			"	select x.vch_firm_name||'-'||x.type as name,x.cr_date,x.description,x.permit,x.bl,x.specialfee from                                "+
			"	(select b.vch_firm_name,a.cr_date,c.description,a.permitno as permit,'BWFL' as type,                                               "+
			"	((a.int_planned_bottles*int_quantity)/1000)as bl,(((a.int_planned_bottles*int_quantity)/1000)*2) as specialfee                     "+
			"from bwfl_license.mst_bottling_plan_20_21 a,                                                                                          "+
			"	bwfl.registration_of_bwfl_lic_holder_20_21 b,                                                                                      "+
			"	public.district c                                                                                                                  "+
			"	where a.licence_no=b.vch_license_number                                                                                            "+
			"	 and c.districtid=b.vch_firm_district_id::numeric                                                                                  "+
			"	union all                                                                                                                          "+
			"	 select b.vch_firm_name,a.cr_date,c.description,a.permit_no as permit,'FL2D' as type,                                              "+
			"	 ((a.int_planned_bottles*int_quantity)/1000)as bl,(((a.int_planned_bottles*int_quantity)/1000)*10) as specialfee                   "+
			"	 from fl2d.mst_stock_receive_20_21 a,licence.fl2_2b_2d_20_21 b ,public.district c                                                  "+
			"	 where a.licence_no=b.vch_licence_no and c.districtid=b.core_district_id::numeric and int_liquor_type=2                            "+
			"	 union all                                                                                                                         "+
			"	 select b.vch_firm_name,a.cr_date,c.description,a.permit_no as permit,'FL2D' as type,                                              "+
			"	 ((a.int_planned_bottles*int_quantity)/1000)as bl,(((a.int_planned_bottles*int_quantity)/1000)*20) as specialfee                   "+
			"	 from fl2d.mst_stock_receive_20_21 a,licence.fl2_2b_2d_20_21 b ,public.district c                                                  "+
			"	 where a.licence_no=b.vch_licence_no and c.districtid=b.core_district_id::numeric and int_liquor_type!=2)x where x.cr_date between "+
			"        ? and ?                                                                                                                                 "+
			"	 order by x.description,x.vch_firm_name,x.type                                                                                     ";
		
		
		
		
		
		                                                                                                                                         
	String dis=
		"	select  x.vch_license_type,x.qnt_ml_detail, x.type, x.name,sum(x.totalbottle) as bottle,                                   "+
		"	x.date,x.couses,(sum(x.totalbottle)*x.couses)as specialfee from                                                            "+
		"	(                                                                                                                          "+
		"	( select b.vch_license_type,d.qnt_ml_detail,1 as type, e.vch_undertaking_name as name,                                     "+
		"	sum(a.int_no_bottle)as totalbottle,b.date_currunt_date as date,d.couses_fl3 as couses ,                              "+
		"	(sum(a.int_no_bottle)*d.couses_fl3)as specialfee                                                                     "+
		"	from distillery.bottling_dtl_20_21 a ,                                                                                     "+
		"	distillery.bottling_master_20_21 b,                                                                                        "+
		"	distillery.packaging_details_20_21 c,                                                                                      "+
		"	distillery.box_size_details d,public.dis_mst_pd1_pd2_lic e ,distillery.brand_registration_20_21 f                          "+
		"	where b.int_id=a.bottoling_masterid_fk and a.int_brand_id=c.brand_id_fk  and c.brand_id_fk=f.brand_id                      "+
		"	and a.int_brand_id=f.brand_id  and liquor_category!=1   and b.date_currunt_date between ? and ?  and a.vch_description::numeric=c.package_id   and a.double_quantity=d.qnt_ml_detail  and d.type='D'                                                                     "+
		"	and a.vch_description::numeric=c.package_id and a.int_dissleri_id=e.int_app_id_f and b.vch_license_type='FL3'              "+
		"	and c.box_id=d.box_id group by e.vch_undertaking_name,b.date_currunt_date,d.couses_fl3,                                    "+
		"	b.vch_license_type,d.qnt_ml_detail order by e.vch_undertaking_name)                                                        "+
		"	UNION all                                                                                                                  "+
		"	(select b.vch_license_type,d.qnt_ml_detail,1 as type, e.vch_undertaking_name as name,                                      "+
		"	sum(a.int_no_bottle)as totalbottle,b.date_currunt_date as date,d.couses_fl3a as couses ,                             "+
		"	(sum(a.int_no_bottle)*d.couses_fl3a)as specialfee                                                                    "+
		"	from distillery.bottling_dtl_20_21 a ,                                                                                     "+
		"	distillery.bottling_master_20_21 b,                                                                                        "+
		"	distillery.packaging_details_20_21 c,                                                                                      "+
		"	distillery.box_size_details d,public.dis_mst_pd1_pd2_lic e ,distillery.brand_registration_20_21 f                          "+
		"	where b.int_id=a.bottoling_masterid_fk and a.int_brand_id=c.brand_id_fk  and c.brand_id_fk=f.brand_id                      "+
		"	and a.int_brand_id=f.brand_id  and liquor_category!=1  and b.date_currunt_date between ? and ?    and a.vch_description::numeric=c.package_id      and a.double_quantity=d.qnt_ml_detail  and d.type='D'                                                                    "+
		"	and a.vch_description::numeric=c.package_id and a.int_dissleri_id=e.int_app_id_f and b.vch_license_type='FL3A'             "+
		"	and c.box_id=d.box_id group by e.vch_undertaking_name,b.date_currunt_date,d.couses_fl3a,b.vch_license_type,                "+
		"	 d.qnt_ml_detail order by e.vch_undertaking_name)                                                                          "+
        "                                                                                                                              "+
		"	UNION all                                                                                                                  "+
        "                                                                                                                              "+
		"	(select b.vch_license_type,d.qnt_ml_detail, 2 as type,e.brewery_nm as name,sum(a.int_no_bottle)as bottle ,           "+
		"	b.date_currunt_date as date,d.couses_fl3 as couses  ,(sum(a.int_no_bottle)*d.couses_fl3)as specialfee                "+
		"	from bwfl.bottling_dtl_20_21 a ,                                                                                           "+
		"	bwfl.bottling_master_20_21 b,                                                                                              "+
		"	distillery.packaging_details_20_21 c,                                                                                      "+
		"	distillery.box_size_details d,public.bre_mst_b1_lic e ,distillery.brand_registration_20_21 f                               "+
		"	where b.int_id=a.bottoling_masterid_fk and a.int_brand_id=c.brand_id_fk  and c.brand_id_fk=f.brand_id                      "+
		"	and a.int_brand_id=f.brand_id  and liquor_category!=1   and b.date_currunt_date between ? and ?   and a.vch_description::numeric=c.package_id   and a.double_quantity=d.qnt_ml_detail  and d.type='B'                                                                     "+
		"	and a.vch_description::numeric=c.package_id and a.int_brewery_id=e.vch_app_id_f and b.vch_license_type='FL3'               "+
		"	and c.box_id=d.box_id group by type,e.brewery_nm,b.date_currunt_date,d.couses_fl3,b.vch_license_type,d.qnt_ml_detail       "+
		"	order by e.brewery_nm)                                                                                                     "+
		"	UNION all                                                                                                                  "+
		"	(select b.vch_license_type,d.qnt_ml_detail, 2 as type,e.brewery_nm as name,sum(a.int_no_bottle)as bottle ,           "+
		"	b.date_currunt_date as date,d.couses_fl3a as couses  ,(sum(a.int_no_bottle)*d.couses_fl3a)as specialfee              "+
		"	from bwfl.bottling_dtl_20_21 a ,                                                                                           "+
		"	bwfl.bottling_master_20_21 b,                                                                                              "+
		"	distillery.packaging_details_20_21 c,                                                                                      "+
		"	distillery.box_size_details d,public.bre_mst_b1_lic e ,distillery.brand_registration_20_21 f                               "+
		"	where b.int_id=a.bottoling_masterid_fk and a.int_brand_id=c.brand_id_fk  and c.brand_id_fk=f.brand_id                      "+
		"	 and a.int_brand_id=f.brand_id  and liquor_category!=1  and b.date_currunt_date between ? and ?   and a.vch_description::numeric=c.package_id   and a.double_quantity=d.qnt_ml_detail  and d.type='B'                                                                    "+
		"	and a.vch_description::numeric=c.package_id and a.int_brewery_id=e.vch_app_id_f and b.vch_license_type='FL3A'              "+
		"   group by type,e.brewery_nm,b.date_currunt_date,d.couses_fl3a,b.vch_license_type,d.qnt_ml_detail      "+
		"	 order by e.brewery_nm)                                                                                                    "+
		"	)x where x.date between ? and ? group by x.vch_license_type,x.qnt_ml_detail, x.name,x.date,x.couses,x.type order by x.type,x.name                       ";
                                                                                                                                       
                                                                                               
		
	String pathjasper=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH+File.separator+"ExciseUp"+File.separator+"specialfee"+File.separator+"jasper"+File.separator;
		
	String pathpdf=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH+File.separator+"ExciseUp"+File.separator+"specialfee"+File.separator+"pdf"+File.separator;	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		try{
			
			conn=ConnectionToDataBase.getConnection();
			
			if(action.getSelectRadio().equals("B"))
			{
				sql=dis;
				pstmt=conn.prepareStatement(sql);
				pstmt.setDate(1, new java.sql.Date(action.getFromDate().getTime()) );
				pstmt.setDate(2, new java.sql.Date(action.getToDate().getTime()) );
				pstmt.setDate(3, new java.sql.Date(action.getFromDate().getTime()) );
				pstmt.setDate(4, new java.sql.Date(action.getToDate().getTime()) );
				pstmt.setDate(5, new java.sql.Date(action.getFromDate().getTime()) );
				pstmt.setDate(6, new java.sql.Date(action.getToDate().getTime()) );
				pstmt.setDate(7, new java.sql.Date(action.getFromDate().getTime()) );
				pstmt.setDate(8, new java.sql.Date(action.getToDate().getTime()) );
				pstmt.setDate(9, new java.sql.Date(action.getFromDate().getTime()) );
				pstmt.setDate(10, new java.sql.Date(action.getToDate().getTime()) );
			}else if(action.getSelectRadio().equals("P"))
			{
				sql=bwflFl2d;
				pstmt=conn.prepareStatement(sql);
				pstmt.setDate(1, new java.sql.Date(action.getFromDate().getTime()) );
				pstmt.setDate(2, new java.sql.Date(action.getToDate().getTime()) ); 
			}
			
			
			
			
			rs=pstmt.executeQuery();
			if(rs.next())
			{
				rs=pstmt.executeQuery();
				JRResultSetDataSource jrd=new JRResultSetDataSource(rs);
				HashMap map=new HashMap();
				map.put("fromDate", action.getFromDate());
				map.put("toDate", action.getToDate());
				map.put("image", pathjasper);
				if(action.getSelectRadio().equals("B"))
				{
				jasperPrint=JasperFillManager.fillReport(pathjasper+"SpecialFeeDisBre.jasper", map, jrd);
				}else if(action.getSelectRadio().equals("P"))
				{
					jasperPrint=JasperFillManager.fillReport(pathjasper+"SpecialFeeBwflFl2d.jasper", map, jrd);	
				}
				
				Random rm=new Random();
				int i=rm.nextInt(500);
				action.setPdfName("specialfee"+i+".pdf");
				JasperExportManager.exportReportToPdfFile(jasperPrint, pathpdf+"specialfee"+i+".pdf");
				
				action.setPrintFlag(true);
				
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No RECORD Found", "No RECORD Found"));
				action.setPrintFlag(false);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			
			try{
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
			}catch(Exception e)
			{
				e.printStackTrace();
				
			}
			
		}
	}
}
