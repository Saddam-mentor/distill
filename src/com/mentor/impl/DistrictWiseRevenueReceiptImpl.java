package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.mentor.action.DistrictWiseRevenueReceiptAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;
public class DistrictWiseRevenueReceiptImpl {
	public String getUserDetails(DistrictWiseRevenueReceiptAction act) {


		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;

		
		try {
			con = ConnectionToDataBase.getConnection();
            
			 String selQr = " SELECT description, districtid FROM public.district " +
			 		        " WHERE deo='"+ ResourceUtil.getUserNameAllReq().trim() + "' ";

			 pstmt = con.prepareStatement(selQr);

			 System.out.println("login details---------------" + selQr);
            
			rs = pstmt.executeQuery();

			if (rs.next()) {

				act.setDistrictName(rs.getString("description"));
				
				act.setDistrict_id(rs.getString("districtid"));	
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
	public void printReport(DistrictWiseRevenueReceiptAction action)
	{

		String mypath=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH;
		
		String relativePath=mypath+File.separator+"ExciseUp"+File.separator+"MIS"+File.separator+"jasper";
		String relativePathpdf=mypath+File.separator+"ExciseUp"+File.separator+"MIS"+File.separator+"pdf";
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst=null;
		Connection con=null;
		ResultSet rs=null;
		String reportQuery=null;
		String month=null;
		String val=null;
		if(action.getRadio().equalsIgnoreCase("D")){
			val="district";
		}
		else if(action.getRadio().equalsIgnoreCase("M")){
			val="month_target";
		}
        else if(action.getRadio().equalsIgnoreCase("U")){
			val="upto_month_target";
		}
        else if(action.getRadio().equalsIgnoreCase("P")){
	        val="month_target_percent";
        }
		if(action.getFinanMonth().trim().equalsIgnoreCase("1")){
			month="January";
		}else if(action.getFinanMonth().trim().equalsIgnoreCase("2")){
			month="February";
		}else if(action.getFinanMonth().trim().equalsIgnoreCase("3")){
			month="March";
		}else if(action.getFinanMonth().trim().equalsIgnoreCase("4")){
			month="April";
		}else if(action.getFinanMonth().trim().equalsIgnoreCase("5")){
			month="May";
		}else if(action.getFinanMonth().trim().equalsIgnoreCase("6")){
			month="June";
		}else if(action.getFinanMonth().trim().equalsIgnoreCase("7")){
			month="July";
		}else if(action.getFinanMonth().trim().equalsIgnoreCase("8")){
			month="August";
		}else if(action.getFinanMonth().trim().equalsIgnoreCase("9")){
			month="September";
		}else if(action.getFinanMonth().trim().equalsIgnoreCase("10")){
			month="October";
		}else if(action.getFinanMonth().trim().equalsIgnoreCase("11")){
			month="November";
		}else if(action.getFinanMonth().trim().equalsIgnoreCase("12")){
			month="December";
		}
		
		try {
			con =ConnectionToDataBase.getConnection();
			
			reportQuery="select x.district,x.zone_name,x.chrg_name,x.district_name,x.deo,x.annual_target," +
					" x.month_target::numeric,x.upto_month_target::numeric,x.month_achievement," +
					" coalesce(upto_month_achievement ,0)as upto_month_achievement," +
					" ((x.upto_month_achievement/x.upto_month_target)*100 )as upto_m_trg_prs," +
					" ((x.upto_month_achievement/x.annual_target)*100 )as anu_trg_prs," +
					" ((x.month_achievement/x.month_target)*100 )as month_target_percent from " +
					" ( SELECT DISTINCT b.district,d.jc_user_name AS zone_name,CONCAT('Excise-CH-',c.description)as chrg_name,a.description as district_name,a.deo," +
					" (SELECT DISTINCT SUM(e.jan + e.feb + e.mar + e.apr + e.may + e.june + e.july + e.aug + e.sep + e.oct + e.nov + e.dec)AS annual_target" +
					"       FROM licence.month_wise_revenue_target e WHERE district_id=a.districtid)," +
					"       CASE WHEN b.monthid=1 THEN (e.jan) WHEN b.monthid=2 THEN (e.feb) WHEN b.monthid=3 THEN (e.mar) WHEN b.monthid=4 THEN (e.apr)" +
					"       WHEN b.monthid=5 THEN (e.may) WHEN b.monthid=6 THEN (e.june) WHEN b.monthid=7 THEN (e.july) WHEN b.monthid=8 THEN (e.aug)" +
					"       WHEN b.monthid=9 THEN (e.sep) WHEN b.monthid=10 THEN (e.oct) WHEN b.monthid=11 THEN (e.nov) WHEN b.monthid=12 THEN (e.dec) " +
					"       END AS month_target ," +
					"       CASE WHEN b.monthid=1 THEN (e.apr + e.may + e.june + e.july + e.aug + e.sep + e.oct + e.nov + e.dec)" +
					"       WHEN b.monthid=2 THEN (e.jan + e.apr + e.may + e.june + e.july + e.aug + e.sep + e.oct + e.nov + e.dec)" +
					"       WHEN b.monthid=3 THEN (e.feb + e.jan + e.apr + e.may + e.june + e.july + e.aug + e.sep + e.oct + e.nov + e.dec)" +
					"       WHEN b.monthid=4 THEN 0" +
					"       WHEN b.monthid=5 THEN (e.apr) WHEN b.monthid=6 THEN (e.apr + e.may) WHEN b.monthid=7 THEN (e.apr + e.may + e.june) " +
					"       WHEN b.monthid=8 THEN (e.apr + e.may + e.june + e.july)  WHEN b.monthid=9 THEN (e.apr + e.may + e.june + e.july+ e.aug)" +
					"       WHEN b.monthid=10 THEN (e.apr + e.may + e.june + e.july + e.aug + e.sep) " +
					"       WHEN b.monthid=11 THEN (e.apr + e.may + e.june + e.july + e.aug + e.sep + e.oct) " +
					"       WHEN b.monthid=12 THEN (e.apr + e.may + e.june + e.july + e.aug + e.sep + e.oct + e.nov)  " +
					"       END AS upto_month_target ," +
					"       SUM(b.actualrevenuereceipt) AS month_achievement," +
					"       CASE WHEN b.monthid=1 THEN (select SUM(coalesce(b.actualrevenuereceipt,0)) from retail.sixpointinfoentry b  where b.district=e.district_id AND  monthid in (4,5,6,7,8,9,10,11,12) )" +
					"       WHEN b.monthid=2 THEN (select SUM(coalesce(b.actualrevenuereceipt,0)) from retail.sixpointinfoentry b where b.district=e.district_id AND  monthid in (1,4,5,6,7,8,9,10,11,12) )" +
					"       WHEN b.monthid=3 THEN (select SUM(coalesce(b.actualrevenuereceipt,0)) from retail.sixpointinfoentry b  where b.district=e.district_id AND  monthid in (1,2,4,5,6,7,8,9,10,11,12) )" +
					"       WHEN b.monthid=4 THEN 0" +
					"       WHEN b.monthid=5 THEN (select SUM(coalesce(b.actualrevenuereceipt,0)) from retail.sixpointinfoentry b  where b.district=e.district_id AND  monthid in (4) )" +
					"       WHEN b.monthid=6 THEN (select SUM(coalesce(b.actualrevenuereceipt,0)) from retail.sixpointinfoentry b   where b.district=e.district_id AND  monthid in (4,5) )" +
					"       WHEN b.monthid=7 THEN (select SUM(coalesce(b.actualrevenuereceipt,0)) from retail.sixpointinfoentry b  where b.district=e.district_id AND  monthid in (4,5,6) ) " +
					"       WHEN b.monthid=8 THEN (select SUM(coalesce(b.actualrevenuereceipt,0)) from retail.sixpointinfoentry b   where b.district=e.district_id AND  monthid in (4,5,6,7) )" +
					"       WHEN b.monthid=9 THEN (select SUM(coalesce(b.actualrevenuereceipt,0)) from retail.sixpointinfoentry b   where b.district=e.district_id AND  monthid in (4,5,6,7,8) )" +
					"       WHEN b.monthid=10 THEN (select SUM(coalesce(b.actualrevenuereceipt,0)) from retail.sixpointinfoentry b  where b.district=e.district_id AND  monthid in (4,5,6,7,8,9) ) " +
					"       WHEN b.monthid=11 THEN (select SUM(coalesce(b.actualrevenuereceipt,0)) from retail.sixpointinfoentry b  where b.district=e.district_id AND  monthid in (4,5,6,7,8,9,10) )" +
					"       WHEN b.monthid=12 THEN (select SUM(coalesce(b.actualrevenuereceipt,0)) from retail.sixpointinfoentry b  where b.district=e.district_id AND  monthid in (4,5,6,7,8,9,10,11) )  " +
					"       END AS upto_month_achievement" +
					"       FROM public.district a,retail.sixpointinfoentry b,public.charge c,public.joint_commissioners_zone_master d,licence.month_wise_revenue_target e" +
					"       WHERE  e.district_id=a.districtid AND b.district=e.district_id AND b.monthid='"+action.getFinanMonth()+"' AND a.chargeid=c.chargeid AND a.zoneid=c.zoneid AND c.zoneid=d.pk_id    " +
					"	    AND b.yearid::text=(SELECT DISTINCT sesn_id FROM public.mst_season where active='a')" +
					"       GROUP BY e.district_id,a.description,a.deo,a.districtid,month_target,upto_month_target,b.district,d.jc_user_name,chrg_name, a.description,b.monthid,b.actualrevenuereceipt" +
					"       )x ORDER BY "+val+",zone_name, chrg_name, district_name";

			System.out.println("order by---------------------------"+reportQuery);
			
			
			pst=con.prepareStatement(reportQuery);
			
			rs=pst.executeQuery();
			Map parameters = new HashMap();
			parameters.put("REPORT_CONNECTION", con);
			parameters.put("SUBREPORT_DIR", relativePath+File.separator);
			parameters.put("image", relativePath+File.separator);
			parameters.put("month", month);
			parameters.put("val",val);
			
			JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

			jasperReport = (JasperReport) JRLoader.loadObject(relativePath+File.separator+"DistrictWiseRevenueReceipt.jasper");

			JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);	
			Random rand = new Random();
        	int  n = rand.nextInt(250) + 1;

			JasperExportManager.exportReportToPdfFile(print,relativePathpdf+File.separator+"DistrictWiseRevenueReceipt"+n+".pdf");
			action.setPdfname("DistrictWiseRevenueReceipt"+n+".pdf");
			action.setPrintFlag(true);
			 
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}

	}
	public String getUserData(DistrictWiseRevenueReceiptAction act){
	
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		
		try {
			con = ConnectionToDataBase.getConnection();
            
			 String selQr = " SELECT b.month_id, a.districtid FROM public.district a, month_master b" +
			 		        " WHERE deo='"+ ResourceUtil.getUserNameAllReq().trim() + "' ";

			 pstmt = con.prepareStatement(selQr);

			 System.out.println("login details---------------" + selQr);
            
			rs = pstmt.executeQuery();

			if (rs.next()) {

				act.setFinanMonth(rs.getString("month_id"));
				
				act.setDistrict_id(rs.getString("districtid"));	
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



