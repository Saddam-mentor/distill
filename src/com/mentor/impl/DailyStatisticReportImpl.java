package com.mentor.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mentor.action.DailyStatisticReportAction;
import com.mentor.datatable.DailyStatisticDispatchFromDistrictWholeSaleDataTable;
import com.mentor.datatable.DailyStatisticDispatchToDistrictWholeSaleDataTable;
import com.mentor.datatable.DailyStatisticReportDatatable;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Utility;

public class DailyStatisticReportImpl {
	
	
	public ArrayList getDailyStatistic(DailyStatisticReportAction action)
	{
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		ArrayList list =new ArrayList();
			
		
		String sql=	
			"	select 'FL' as liquor_type,sum(x.bl) as bl,sum(x.duty) as duty,x.dt_date as date from      "
			+"	(select dispatchd_box,size::numeric,a.duty,d.qnt_ml_detail,b.dt_date ,                     "
			+"	ROUND(((dispatchd_box*size::numeric*qnt_ml_detail)/1000),2)as bl                           "
			+"	from distillery.fl1_stock_trxn a ,                                                         "
			+"	distillery.gatepass_to_manufacturewholesale b,                                             "
			+"	distillery.packaging_details c,distillery.box_size_details d                               "
			+"	where a.vch_gatepass_no=b.vch_gatepass_no and dt_date=?                         "
			+"	and a.int_brand_id=c.brand_id_fk  and a.int_pckg_id=c.package_id and c.box_id=d.box_id)x   "
			+"	group by x.dt_date                                                                         "
			+"	union all                                                                                  "
			+"	select 'CL' as liquor_type,sum(x.bl) as bl,sum(x.duty) as duty,x.dt_date as date from      "
			+"	(select dispatchd_box,size::numeric,a.duty,d.qnt_ml_detail,b.dt_date ,                     "
			+"	ROUND(((dispatchd_box*size::numeric*qnt_ml_detail)/1000),2)as bl                           "
			+"	from distillery.cl2_stock_trxn a ,                                                         "
			+"	distillery.gatepass_to_manufacturewholesale_cl b,                                          "
			+"	distillery.packaging_details c,distillery.box_size_details d                               "
			+"	where a.vch_gatepass_no=b.vch_gatepass_no and b.dt_date=?                       "
			+"	and a.int_brand_id=c.brand_id_fk  and a.int_pckg_id=c.package_id and c.box_id=d.box_id)x   "
			+"	group by x.dt_date                                                                         "
			+"	union all                                                                                  "
			+"	select 'BEER' as liquor_type,sum(x.bl) as bl,sum(x.duty) as duty,x.dt_date as date from    "
			+"	(select dispatchd_box,size::numeric,a.duty,d.qnt_ml_detail,b.dt_date ,                     "
			+"	ROUND(((dispatchd_box*size::numeric*qnt_ml_detail)/1000),2)as bl                           "
			+"	from bwfl.fl1_stock_trxn a ,                                                               "
			+"	bwfl.gatepass_to_manufacturewholesale b,                                                   "
			+"	distillery.packaging_details c,distillery.box_size_details d                               "
			+"	where a.vch_gatepass_no=b.vch_gatepass_no and b.dt_date=?                       "
			+"	and a.int_brand_id=c.brand_id_fk  and a.int_pckg_id=c.package_id and c.box_id=d.box_id)x   "
			+"	group by x.dt_date                                                                         ";
		
		try{
			conn=ConnectionToDataBase.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setDate(1, Utility.convertUtilDateToSQLDate(action.getDate()));
			pstmt.setDate(2, Utility.convertUtilDateToSQLDate(action.getDate()));
			pstmt.setDate(3, Utility.convertUtilDateToSQLDate(action.getDate()));
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				
				DailyStatisticReportDatatable dt=new DailyStatisticReportDatatable();
				
				
				dt.setBl(rs.getBigDecimal("bl"));
				dt.setLiqor_type(rs.getString("liquor_type"));
				dt.setDuty(rs.getBigDecimal("duty"));
				list.add(dt);
			
				
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
		
		return list;
	}
	
	
	
	public ArrayList getDailyStatisticDispatchToDistrict(DailyStatisticReportAction action)
	{
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		ArrayList list =new ArrayList();
		String sql=
				
				
				"select 'FL' as liquor_type,sum(x.bl) as bl,sum(dispatch_box)as cases,x.dt_date as date from     "
				+"(select dispatch_box,size::numeric,d.qnt_ml_detail,b.dt_date ,                                  "
				+"ROUND(((dispatch_box*size::numeric*qnt_ml_detail)/1000),2)as bl                                 "
				+"from distillery.fl2_stock_trxn a ,                                                              "
				+"distillery.gatepass_to_districtwholesale b,                                                     "
				+"distillery.packaging_details c,distillery.box_size_details d                                    "
				+"where a.vch_gatepass_no=b.vch_gatepass_no and dt_date=?                              "
				+"and a.int_brand_id=c.brand_id_fk  and a.int_pckg_id=c.package_id and c.box_id=d.box_id)x        "
				+"group by x.dt_date                                                                              "
				+"union all                                                                                       "
				+"select 'CL' as liquor_type,sum(x.bl) as bl,sum(dispatchd_box)as cases,x.dt_date as date from    "
				+"(select a.dispatchd_box,size::numeric,d.qnt_ml_detail,b.dt_date ,                               "
				+"ROUND(((dispatchd_box*size::numeric*qnt_ml_detail)/1000),2)as bl                                "
				+"from distillery.cl2_stock_trxn a ,                                                              "
				+"distillery.gatepass_to_manufacturewholesale_cl b,                                               "
				+"distillery.packaging_details c,distillery.box_size_details d                                    "
				+"where a.vch_gatepass_no=b.vch_gatepass_no and b.dt_date=?                            "
				+"and a.int_brand_id=c.brand_id_fk  and a.int_pckg_id=c.package_id and c.box_id=d.box_id)x        "
				+"group by x.dt_date                                                                              "
				+"union all                                                                                       "
				+"select 'BEER' as liquor_type,sum(x.bl) as bl,sum(dispatch_box)as cases,x.dt_date as date from   "
				+"(select dispatch_box,size::numeric,d.qnt_ml_detail,b.dt_date ,                                  "
				+"ROUND(((dispatch_box*size::numeric*qnt_ml_detail)/1000),2)as bl                                 "
				+"from bwfl.fl2_stock_trxn a ,                                                                    "
				+"bwfl.gatepass_to_districtwholesale b,                                                           "
				+"distillery.packaging_details c,distillery.box_size_details d                                    "
				+"where a.vch_gatepass_no=b.vch_gatepass_no and dt_date=?                              "
				+"and a.int_brand_id=c.brand_id_fk  and a.int_pckg_id=c.package_id and c.box_id=d.box_id)x        "
				+"group by x.dt_date                                                                              ";

				
				
		
		try{
			
			
			conn=ConnectionToDataBase.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setDate(1, Utility.convertUtilDateToSQLDate(action.getDate()));
			pstmt.setDate(2, Utility.convertUtilDateToSQLDate(action.getDate()));
			pstmt.setDate(3, Utility.convertUtilDateToSQLDate(action.getDate()));
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				
				DailyStatisticDispatchToDistrictWholeSaleDataTable dt=new DailyStatisticDispatchToDistrictWholeSaleDataTable();
				
				
				dt.setBl(rs.getBigDecimal("bl"));
				dt.setLiqor_type(rs.getString("liquor_type"));
				dt.setNo_of_cases(rs.getLong("cases"));
				list.add(dt);
			
				
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
		
		return list;
	}
	
	
	
	public ArrayList getDailyStatisticDispatchFromDistrict(DailyStatisticReportAction action)
	{
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		ArrayList list =new ArrayList();
			String sql=
					
					
					
					
					"select 'FL' as liquor_type,sum(x.bl) as bl,sum(dispatch_box)as cases,x.dt_date as date from     "+
					"(select dispatch_box,size::numeric,d.qnt_ml_detail,b.dt_date ,                                  "+
					"ROUND(((dispatch_box*size::numeric*qnt_ml_detail)/1000),2)as bl                                 "+
					"from fl2d.fl2_stock_trxn_fl2_fl2b a ,                                                           "+
					"fl2d.gatepass_to_districtwholesale_fl2_fl2b b,                                                  "+
					"distillery.packaging_details c,distillery.box_size_details d                                    "+
					"where a.vch_gatepass_no=b.vch_gatepass_no                                                       "+
					" and dt_date=?                                                                       "+
					" and b.vch_from='FL2'                                                                           "+
					"and a.int_brand_id=c.brand_id_fk  and a.int_pckg_id=c.package_id and c.box_id=d.box_id)x        "+
					"group by x.dt_date                                                                              "+
					"union all                                                                                       "+
					"select 'CL' as liquor_type,sum(x.bl) as bl,sum(dispatch_box)as cases,x.dt_date as date from     "+
					"(select a.dispatch_box,size::numeric,d.qnt_ml_detail,b.dt_date ,                                "+
					"ROUND(((dispatch_box*size::numeric*qnt_ml_detail)/1000),2)as bl                                 "+
					"from fl2d.fl2_stock_trxn_fl2_fl2b a ,                                                           "+
					"fl2d.gatepass_to_districtwholesale_fl2_fl2b b,                                                  "+
					"distillery.packaging_details c,distillery.box_size_details d                                    "+
					"where a.vch_gatepass_no=b.vch_gatepass_no                                                       "+
					" and b.dt_date=? and b.vch_from='CL2'                                                "+
					"and a.int_brand_id=c.brand_id_fk  and a.int_pckg_id=c.package_id and c.box_id=d.box_id)x        "+
					"group by x.dt_date                                                                              "+
					"union all                                                                                       "+
					"select 'BEER' as liquor_type,sum(x.bl) as bl,sum(dispatch_box)as cases,x.dt_date as date from   "+
					"(select dispatch_box,size::numeric,d.qnt_ml_detail,b.dt_date ,                                  "+
					"ROUND(((dispatch_box*size::numeric*qnt_ml_detail)/1000),2)as bl                                 "+
					"from fl2d.fl2_stock_trxn_fl2_fl2b a ,                                                           "+
					"fl2d.gatepass_to_districtwholesale_fl2_fl2b b,                                                  "+
					"distillery.packaging_details c,distillery.box_size_details d                                    "+
					"where a.vch_gatepass_no=b.vch_gatepass_no and dt_date=? and b.vch_from='FL2B'        "+
					"and a.int_brand_id=c.brand_id_fk  and a.int_pckg_id=c.package_id and c.box_id=d.box_id)x          "+
				"	group by x.dt_date                                                                                ";

					
					
					
					
		
		try{
			conn=ConnectionToDataBase.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setDate(1, Utility.convertUtilDateToSQLDate(action.getDate()));
			pstmt.setDate(2, Utility.convertUtilDateToSQLDate(action.getDate()));
			pstmt.setDate(3, Utility.convertUtilDateToSQLDate(action.getDate()));
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				
				DailyStatisticDispatchFromDistrictWholeSaleDataTable dt=new DailyStatisticDispatchFromDistrictWholeSaleDataTable();
				
				
				dt.setBl(rs.getBigDecimal("bl"));
				dt.setLiqor_type(rs.getString("liquor_type"));
				dt.setNo_of_cases(rs.getLong("cases"));
				list.add(dt);
			
				
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
		
		return list;
	}
	
	
	public BigDecimal getTotalDeposit()
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=
		
		"select sum(amt)as amt from                                                                  "+
		"(SELECT   sum( double_amt)as amt                                                            "+
		"FROM public.challan_deposit where vch_challan_type='D' group by vch_challan_type            "+
		"union                                                                                       "+
		"SELECT   sum( double_amt)as amt                                                             "+
		"FROM bwfl.challan_deposit_brewery where vch_challan_type='B' group by vch_challan_type)x    ";
		                                                                                             
		                                                                                             
		BigDecimal amt=null;
		
		try{
		conn=	ConnectionToDataBase.getConnection();
		 pstmt=conn.prepareStatement(sql);
		 rs=pstmt.executeQuery();
	    if(rs.next())
	    {
	    	amt=rs.getBigDecimal("amt");
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
		return amt;
		
	}
	
	
	
	

}
