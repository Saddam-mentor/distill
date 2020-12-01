package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.mentor.action.G6_DepositeChallanReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;

public class G6_DepositeChallanReportImpl {
	
	
	
	
	

	public ArrayList getDistrictList() {

		ArrayList list = new ArrayList();

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql=null;

		
		if(this.getUserRole().equals("199"))
		{
		
		 sql = "select * from public.district  where districtid!='0' and  deo='"+ResourceUtil.getUserNameAllReq()+"'  order by description ";
		}else
		{
			 sql = "select * from public.district where districtid!='0' order by description";
		}

		SelectItem item = new SelectItem();
		item.setLabel("--ALL--");
		item.setValue(9999);

		list.add(item);

		try {

			c = ConnectionToDataBase.getConnection();
			ps = c.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {

				item = new SelectItem();
				item.setLabel(rs.getString("description"));
				item.setValue(rs.getInt("districtid"));

				list.add(item);
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), e.getMessage()));
			e.printStackTrace();
		} finally {

			try {
				c.close();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e
								.getMessage(), e.getMessage()));
				e.printStackTrace();
			}
		}

		return list;
	}

	
	
	
	
	
	
	public String getUserId()
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String userId="";
		
		
		try
		{
		String query = "SELECT jbp_uid from jbp_users where jbp_uname='"+ResourceUtil.getUserNameAllReq()+"' ";
        
	//	System.out.println("get user id==="+query);

				   
		conn = ConnectionToDataBase.getConnection2();
		pstmt=conn.prepareStatement(query);
		rs=pstmt.executeQuery();
		if(rs.next())
		{
		userId=rs.getString("jbp_uid");	 
							
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
	return userId;	
	}
	
	public String getUserRole()
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String userRole="";
		String query="";
		
		try
		{
	//		System.out.println("================USER ID ==="+getUserId());
			
		if(ResourceUtil.getUserNameAllReq()!=null)
		{

		 query = "SELECT jbp_rid from jbp_role_membership where jbp_uid='"+getUserId()+"' ";
		}else
		{
			// query="";
		}

       
				   
		conn = ConnectionToDataBase.getConnection2();
		pstmt=conn.prepareStatement(query);
		rs=pstmt.executeQuery();
		while(rs.next())
		{
		userRole=rs.getString("jbp_rid");	
		
		 //System.out.println("User Roll ====== "+ query);
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
	return userRole;	
	}
	
	
	
//	======================= District wise Revenue -------
	
	public void printDistrictWise(G6_DepositeChallanReportAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
				
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
				
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String filter = "";
		String filter2 = "";
        String date="";
		try {
			
			if(act.getYear().equals("19"))
			{
				date="2019-04-01";
			}else{
				date="2020-04-01";
			}
			
			
			if(act.getDistrictID().equalsIgnoreCase("9999"))
			{				
				filter2="";
			}else{				
				filter2="and d.districtid='"+act.getDistrictID()+"'";
			}

			con = ConnectionToDataBase.getConnection();




			
			if(this.getUserRole().equals("199"))
			{
			

			reportQuery =   " SELECT d.description, "+
					" SUM(COALESCE(aa.jan_amt,0)) as amt_jan, SUM(COALESCE(aa.feb_amt,0)) as amt_feb, "+
					" SUM(COALESCE(aa.mar_amt,0)) as amt_mar, SUM(COALESCE(aa.apr_amt,0)) as amt_apr, "+
					" SUM(COALESCE(aa.may_amt,0)) as amt_may, SUM(COALESCE(aa.june_amt,0)) as amt_june, "+
					" SUM(COALESCE(aa.july_amt,0)) as amt_july, SUM(COALESCE(aa.aug_amt,0)) as amt_aug, "+
					" SUM(COALESCE(aa.sep_amt,0)) as amt_sep, SUM(COALESCE(aa.oct_amt,0)) as amt_oct, "+
					" SUM(COALESCE(aa.nov_amt,0)) as amt_nov, SUM(COALESCE(aa.dec_amt,0)) as amt_dec, "+
					
					" COALESCE(sum(aa.jan_amt),0.0) + COALESCE(sum(aa.feb_amt),0.0) +  COALESCE(sum(aa.mar_amt),0.0) +  "+
					" COALESCE(sum(aa.apr_amt),0.0) + COALESCE(sum(aa.may_amt),0.0) + COALESCE(sum(aa.june_amt),0.0) +  "+
					" COALESCE(sum(aa.july_amt),0.0) + COALESCE(sum(aa.aug_amt),0.0) + COALESCE(sum(aa.sep_amt),0.0) +  "+
					" COALESCE(sum(aa.oct_amt),0.0) +  COALESCE(sum(aa.nov_amt),0.0) + COALESCE(sum(aa.dec_amt),0.0) "+
					" as total "+
					" FROM "+
					" (SELECT x.vch_tresiory_name, x.month,"+
					" CASE WHEN x.month=1 THEN SUM(x.amount) end as jan_amt, "+
					" CASE WHEN x.month=2 THEN SUM(x.amount) end as feb_amt, "+
					" CASE WHEN x.month=3 THEN SUM(x.amount) end as mar_amt, "+
					" CASE WHEN x.month=4 THEN SUM(x.amount) end as apr_amt, "+
					" CASE WHEN x.month=5 THEN SUM(x.amount) end as may_amt, "+
					" CASE WHEN x.month=6 THEN SUM(x.amount) end as june_amt, "+
					" CASE WHEN x.month=7 THEN SUM(x.amount) end as july_amt, "+
					" CASE WHEN x.month=8 THEN SUM(x.amount) end as aug_amt, "+
					" CASE WHEN x.month=9 THEN SUM(x.amount) end as sep_amt, "+
					" CASE WHEN x.month=10 THEN SUM(x.amount) end as oct_amt, "+
					" CASE WHEN x.month=11 THEN SUM(x.amount) end as nov_amt, "+
					" CASE WHEN x.month=12 THEN SUM(x.amount) end as dec_amt "+
					" FROM "+
					" (SELECT EXTRACT(MONTH FROM a.date_challan_date) as month,a.vch_tresiory_name , SUM(a.double_amt) as amount "+
					" FROM public.challan_deposit a, public.challn_deposit_detail b "+
					" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type "+
					" AND a.date_challan_date >= '"+date+"' "+
					" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a') "+
					" GROUP BY month, a.vch_tresiory_name "+
					" UNION "+
					" SELECT EXTRACT(MONTH FROM a.date_challan_date) as month,a.vch_tresiory_name , SUM(a.double_amt) as amount "+
					" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b "+
					" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type " +
					" AND a.date_challan_date >= '"+date+"' "+
					" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a') "+
					" GROUP BY month, a.vch_tresiory_name "+
					" UNION "+
					" SELECT EXTRACT(MONTH FROM a.date_challan_date) as month,a.vch_tresiory_name,  SUM(a.double_amt) as amount "+
					" FROM bwfl.challan_deposit_brewery a, bwfl.challn_deposit_brewery_detail b "+
					" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type "+
					" AND a.date_challan_date >= '"+date+"' "+
					" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a')  "+
					" GROUP BY month, a.vch_tresiory_name "+
					" UNION "+
					" SELECT EXTRACT(MONTH FROM a.chalan_date) as month, a.tres_id as vch_tresiory_name,  SUM(a.amount) as amount "+
					" FROM bwfl_license.chalan_deposit_bwfl_fl2d a "+
					" Where a.chalan_date >= '"+date+"' "+
					" GROUP BY month, a.tres_id)x GROUP BY  x.month, x.vch_tresiory_name "+
					" ORDER by x.month,x.vch_tresiory_name)aa, licence.treasury t, public.district d "+
					" WHERE aa.vch_tresiory_name=t.tcode AND t.district_id=d.districtid "+
                 //   " and d.deo='"+ResourceUtil.getUserNameAllReq()+"'  "+
					
                    " "+filter2+" "+
					" GROUP BY d.description ORDER BY d.description ";

             // System.out.println("reportQuery-----IF---------" + reportQuery);
			}
			
		else{
			
			
reportQuery =		    " SELECT d.description, "+
						" SUM(COALESCE(aa.jan_amt,0)) as amt_jan, SUM(COALESCE(aa.feb_amt,0)) as amt_feb, "+
						" SUM(COALESCE(aa.mar_amt,0)) as amt_mar, SUM(COALESCE(aa.apr_amt,0)) as amt_apr, "+
						" SUM(COALESCE(aa.may_amt,0)) as amt_may, SUM(COALESCE(aa.june_amt,0)) as amt_june, "+
						" SUM(COALESCE(aa.july_amt,0)) as amt_july, SUM(COALESCE(aa.aug_amt,0)) as amt_aug, "+
						" SUM(COALESCE(aa.sep_amt,0)) as amt_sep, SUM(COALESCE(aa.oct_amt,0)) as amt_oct, "+
						" SUM(COALESCE(aa.nov_amt,0)) as amt_nov, SUM(COALESCE(aa.dec_amt,0)) as amt_dec, "+
						
						" COALESCE(sum(aa.jan_amt),0.0) + COALESCE(sum(aa.feb_amt),0.0) +  COALESCE(sum(aa.mar_amt),0.0) +  "+
						" COALESCE(sum(aa.apr_amt),0.0) + COALESCE(sum(aa.may_amt),0.0) + COALESCE(sum(aa.june_amt),0.0) +  "+
						" COALESCE(sum(aa.july_amt),0.0) + COALESCE(sum(aa.aug_amt),0.0) + COALESCE(sum(aa.sep_amt),0.0) +  "+
						" COALESCE(sum(aa.oct_amt),0.0) +  COALESCE(sum(aa.nov_amt),0.0) + COALESCE(sum(aa.dec_amt),0.0) "+
						" as total "+
						" FROM "+
						" (SELECT x.vch_tresiory_name, x.month,"+
						" CASE WHEN x.month=1 THEN SUM(x.amount) end as jan_amt, "+
						" CASE WHEN x.month=2 THEN SUM(x.amount) end as feb_amt, "+
						" CASE WHEN x.month=3 THEN SUM(x.amount) end as mar_amt, "+
						" CASE WHEN x.month=4 THEN SUM(x.amount) end as apr_amt, "+
						" CASE WHEN x.month=5 THEN SUM(x.amount) end as may_amt, "+
						" CASE WHEN x.month=6 THEN SUM(x.amount) end as june_amt, "+
						" CASE WHEN x.month=7 THEN SUM(x.amount) end as july_amt, "+
						" CASE WHEN x.month=8 THEN SUM(x.amount) end as aug_amt, "+
						" CASE WHEN x.month=9 THEN SUM(x.amount) end as sep_amt, "+
						" CASE WHEN x.month=10 THEN SUM(x.amount) end as oct_amt, "+
						" CASE WHEN x.month=11 THEN SUM(x.amount) end as nov_amt, "+
						" CASE WHEN x.month=12 THEN SUM(x.amount) end as dec_amt "+
						" FROM "+
						" (SELECT EXTRACT(MONTH FROM a.date_challan_date) as month,a.vch_tresiory_name , SUM(a.double_amt) as amount "+
						" FROM public.challan_deposit a, public.challn_deposit_detail b "+
						" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type "+
						" AND a.date_challan_date >= '"+date+"' "+
						" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a') "+
						" GROUP BY month, a.vch_tresiory_name "+
						" UNION "+
						" SELECT EXTRACT(MONTH FROM a.date_challan_date) as month,a.vch_tresiory_name , SUM(a.double_amt) as amount "+
						" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b "+
						" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type "+
						" AND a.date_challan_date >= '"+date+"' "+
						" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a') "+
						" GROUP BY month, a.vch_tresiory_name "+
						" UNION "+
						" SELECT EXTRACT(MONTH FROM a.date_challan_date) as month,a.vch_tresiory_name,  SUM(a.double_amt) as amount "+
						" FROM bwfl.challan_deposit_brewery a, bwfl.challn_deposit_brewery_detail b "+
						" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type "+
						" AND a.date_challan_date >= '"+date+"' "+
						" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a')  "+
						" GROUP BY month, a.vch_tresiory_name "+
						" UNION "+
						" SELECT EXTRACT(MONTH FROM a.chalan_date) as month, a.tres_id as vch_tresiory_name,  SUM(a.amount) as amount "+
						" FROM bwfl_license.chalan_deposit_bwfl_fl2d a "+
						" Where a.chalan_date >= '"+date+"' "+
						" GROUP BY month, a.tres_id)x GROUP BY  x.month, x.vch_tresiory_name "+
						" ORDER by x.month,x.vch_tresiory_name)aa, licence.treasury t, public.district d "+
						" WHERE aa.vch_tresiory_name=t.tcode AND t.district_id=d.districtid "+
						" "+filter2+" "+
						" GROUP BY d.description ORDER BY d.description ";

                   // System.out.println("reportQuery-----ELSE---------" + reportQuery); 
		}
			
	
			pst = con.prepareStatement(reportQuery);
			

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
			//	parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));

						
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "G6_Challan_Report_District_Wise_Revenue.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "G6_Challan_Report_District_Wise_Revenue" + "-" + n + ".pdf");
				act.setPdfName("G6_Challan_Report_District_Wise_Revenue" + "-" + n + ".pdf");
				act.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Data Found!!", "No Data Found!!"));
								
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
	
//	======================= Head wise Revenue -------
	
	public void printHeadWise(G6_DepositeChallanReportAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
				
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
				
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String filter = "";
        String date="";
        String filter2="";
		try {
			con = ConnectionToDataBase.getConnection();

		
			
			
			
			
			if(act.getYear().equals("19"))
			{
				date="2019-04-01";
			}else{
				date="2020-04-01";
			}
			
			
			
			
			

			
			if(this.getUserRole().equals("199"))
			{
			

reportQuery =		

					" SELECT aa.head_code, "+
					" SUM(COALESCE(aa.jan_amt,0)) as amt_jan, SUM(COALESCE(aa.feb_amt,0)) as amt_feb,  "+
					" SUM(COALESCE(aa.mar_amt,0)) as amt_mar, SUM(COALESCE(aa.apr_amt,0)) as amt_apr,  "+
					" SUM(COALESCE(aa.may_amt,0)) as amt_may, SUM(COALESCE(aa.june_amt,0)) as amt_june, "+ 
					" SUM(COALESCE(aa.july_amt,0)) as amt_july, SUM(COALESCE(aa.aug_amt,0)) as amt_aug,  "+
					" SUM(COALESCE(aa.sep_amt,0)) as amt_sep, SUM(COALESCE(aa.oct_amt,0)) as amt_oct,  "+
					" SUM(COALESCE(aa.nov_amt,0)) as amt_nov, SUM(COALESCE(aa.dec_amt,0)) as amt_dec, "+
										
					" COALESCE(sum(aa.jan_amt),0.0) + COALESCE(sum(aa.feb_amt),0.0) +  COALESCE(sum(aa.mar_amt),0.0) +  "+
					" COALESCE(sum(aa.apr_amt),0.0) + COALESCE(sum(aa.may_amt),0.0) + COALESCE(sum(aa.june_amt),0.0) +  "+ 
					" COALESCE(sum(aa.july_amt),0.0) + COALESCE(sum(aa.aug_amt),0.0) + COALESCE(sum(aa.sep_amt),0.0) +  "+
					" COALESCE(sum(aa.oct_amt),0.0) +  COALESCE(sum(aa.nov_amt),0.0) + COALESCE(sum(aa.dec_amt),0.0)  "+
					" as total "+
					" FROM "+
					" (SELECT x.head_code, x.month,x.vch_tresiory_name, "+
					" CASE WHEN x.month=1 THEN SUM(x.amount) end as jan_amt,  "+
					" CASE WHEN x.month=2 THEN SUM(x.amount) end as feb_amt,  "+
					" CASE WHEN x.month=3 THEN SUM(x.amount) end as mar_amt,  "+
					" CASE WHEN x.month=4 THEN SUM(x.amount) end as apr_amt,  "+
					" CASE WHEN x.month=5 THEN SUM(x.amount) end as may_amt,  "+
					" CASE WHEN x.month=6 THEN SUM(x.amount) end as june_amt, "+
					" CASE WHEN x.month=7 THEN SUM(x.amount) end as july_amt, "+
					" CASE WHEN x.month=8 THEN SUM(x.amount) end as aug_amt,  "+
					" CASE WHEN x.month=9 THEN SUM(x.amount) end as sep_amt,  "+
					" CASE WHEN x.month=10 THEN SUM(x.amount) end as oct_amt, "+
					" CASE WHEN x.month=11 THEN SUM(x.amount) end as nov_amt, "+
					" CASE WHEN x.month=12 THEN SUM(x.amount) end as dec_amt  "+
					" FROM "+ 
					" (SELECT EXTRACT(MONTH FROM a.date_challan_date) as month, a.vch_tresiory_name, SUM(a.double_amt) as amount,  "+
					" left(b.vch_head_code,15) as head_code "+
					" FROM public.challan_deposit a, public.challn_deposit_detail b  "+
					" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type "+
					" AND a.date_challan_date >= '"+date+"' "+
					" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a')  "+
					" GROUP BY month, head_code, a.vch_tresiory_name "+
					" UNION  "+
					" SELECT EXTRACT(MONTH FROM a.date_challan_date) as month, a.vch_tresiory_name, SUM(a.double_amt) as amount,  "+
					" left(b.vch_head_code,15) as head_code "+
					" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b  "+
					" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type "+
					" AND a.date_challan_date >= '"+date+"' "+
					" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a') "+
					" GROUP BY month, head_code,a.vch_tresiory_name "+
					" UNION  "+
					" SELECT EXTRACT(MONTH FROM a.date_challan_date) as month, a.vch_tresiory_name, SUM(a.double_amt) as amount,  "+
					" left(b.vch_head_code,15) as head_code "+
					" FROM bwfl.challan_deposit_brewery a, bwfl.challn_deposit_brewery_detail b  "+
					" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type "+
					" AND a.date_challan_date >= '"+date+"' "+
					" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a')  "+
					" GROUP BY month, head_code,a.vch_tresiory_name "+
					" UNION "+
					" SELECT EXTRACT(MONTH FROM a.chalan_date) as month,a.tres_id as vch_tresiory_name, SUM(a.amount) as amount,  "+
					" left(a.head_code,15) as head_code "+
					" FROM bwfl_license.chalan_deposit_bwfl_fl2d a  "+
					" WHERE a.chalan_date >= '"+date+"' "+
					" GROUP BY month, a.head_code,a.tres_id)x GROUP BY  x.month, x.head_code, x.vch_tresiory_name "+
					" ORDER by x.month,x.head_code)aa, "+
					" licence.treasury t, public.district d  "+
					" WHERE aa.vch_tresiory_name=t.tcode AND t.district_id=d.districtid "+
				    " AND d.deo='"+ResourceUtil.getUserNameAllReq()+"' " +
					

					" GROUP BY aa.head_code " ;
            
              //System.out.println("reportQuery-----head wise if---------" + reportQuery);
			
			}
			
		else{
			
reportQuery =		
				"SELECT aa.head_code, "+
						" SUM(COALESCE(aa.jan_amt,0)) as amt_jan, SUM(COALESCE(aa.feb_amt,0)) as amt_feb,    "+
						" SUM(COALESCE(aa.mar_amt,0)) as amt_mar, SUM(COALESCE(aa.apr_amt,0)) as amt_apr,    "+
						" SUM(COALESCE(aa.may_amt,0)) as amt_may, SUM(COALESCE(aa.june_amt,0)) as amt_june,  "+
						" SUM(COALESCE(aa.july_amt,0)) as amt_july, SUM(COALESCE(aa.aug_amt,0)) as amt_aug,  "+
						" SUM(COALESCE(aa.sep_amt,0)) as amt_sep, SUM(COALESCE(aa.oct_amt,0)) as amt_oct,    "+
						" SUM(COALESCE(aa.nov_amt,0)) as amt_nov, SUM(COALESCE(aa.dec_amt,0)) as amt_dec,    "+
						"                                                                                    "+
						"                                                                                                   "+
						" COALESCE(sum(aa.jan_amt),0.0) + COALESCE(sum(aa.feb_amt),0.0) +  COALESCE(sum(aa.mar_amt),0.0) +  "+
						" COALESCE(sum(aa.apr_amt),0.0) + COALESCE(sum(aa.may_amt),0.0)+  COALESCE(sum(aa.june_amt),0.0)+   "+
						" COALESCE(sum(aa.july_amt),0.0)+  COALESCE(sum(aa.aug_amt),0.0) + COALESCE(sum(aa.sep_amt),0.0) +  "+
						" COALESCE(sum(aa.oct_amt),0.0)  + COALESCE(sum(aa.nov_amt),0.0) + COALESCE(sum(aa.dec_amt),0.0)    "+
						" as total                                                                                          "+
						"                                                                                                   "+
						" FROM                                                                                              "+
						" (SELECT x.head_code, x.month, "+
						" CASE WHEN x.month=1 THEN SUM(x.amount) end as jan_amt,    "+
						" CASE WHEN x.month=2 THEN SUM(x.amount) end as feb_amt,    "+
						" CASE WHEN x.month=3 THEN SUM(x.amount) end as mar_amt,    "+
						" CASE WHEN x.month=4 THEN SUM(x.amount) end as apr_amt,    "+
						" CASE WHEN x.month=5 THEN SUM(x.amount) end as may_amt,    "+
						" CASE WHEN x.month=6 THEN SUM(x.amount) end as june_amt,   "+
						" CASE WHEN x.month=7 THEN SUM(x.amount) end as july_amt,   "+
						" CASE WHEN x.month=8 THEN SUM(x.amount) end as aug_amt,    "+
						" CASE WHEN x.month=9 THEN SUM(x.amount) end as sep_amt,    "+
						" CASE WHEN x.month=10 THEN SUM(x.amount) end as oct_amt,   "+
						" CASE WHEN x.month=11 THEN SUM(x.amount) end as nov_amt,   "+
						" CASE WHEN x.month=12 THEN SUM(x.amount) end as dec_amt    "+
						"                                                           "+
						" FROM                                                                                              "+
						" (SELECT EXTRACT(MONTH FROM a.date_challan_date) as month, SUM(a.double_amt) as amount,            "+
						" left(b.vch_head_code,15) as head_code                                                             "+
						" FROM public.challan_deposit a, public.challn_deposit_detail b                                     "+
						" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                "+
						" AND a.date_challan_date >= '"+date+"' "+
						"                                                                                                   "+
						" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a')   "+
						" GROUP BY month, head_code                                                                         "+
						" UNION                                                                                             "+
						" SELECT EXTRACT(MONTH FROM a.date_challan_date) as month, SUM(a.double_amt) as amount,             "+
						" left(b.vch_head_code,15) as head_code                                                             "+
						" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                             "+
						" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                "+
						" AND a.date_challan_date >= '"+date+"' "+
						"                                                                                                   "+
						" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a')   "+
						" GROUP BY month, head_code                                                                         "+
						" UNION                                                                                             "+
						" SELECT EXTRACT(MONTH FROM a.date_challan_date) as month, SUM(a.double_amt) as amount,             "+
						" left(b.vch_head_code,15) as head_code                                                             "+
						" FROM bwfl.challan_deposit_brewery a, bwfl.challn_deposit_brewery_detail b                         "+
						" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type                "+
						" AND a.date_challan_date >= '"+date+"' "+
						"                                                                                                   "+
						" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a')   "+
						" GROUP BY month, head_code                                                                         "+
						" UNION                                                                                             "+
						" SELECT EXTRACT(MONTH FROM a.chalan_date) as month, SUM(a.amount) as amount,                       "+
						" left(a.head_code,15) as head_code                                                                 "+
						" FROM bwfl_license.chalan_deposit_bwfl_fl2d a                                                      "+
						" WHERE a.chalan_date >= '"+date+"' "+
						 " "+filter2+" "+
						" GROUP BY month, a.head_code)x GROUP BY  x.month, x.head_code "+
						" ORDER by x.month,x.head_code)aa GROUP BY aa.head_code  " ;
                          
				   //System.out.println("reportQuery-----head wise else---------" + reportQuery);

			
		}
			
		
			
			
			
			
			
			
			
			
			

			pst = con.prepareStatement(reportQuery);
			

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
		//		parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
						

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "G6_Challan_Report_Head_Wise_Revenue.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "G6_Challan_Report_Head_Wise_Revenue" + "-" + n + ".pdf");
				act.setPdfName("G6_Challan_Report_Head_Wise_Revenue" + "-" + n + ".pdf");
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

	
	
//	======================= Month wise Revenue -------
	
	public void printMonthWise(G6_DepositeChallanReportAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
				
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
				
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String filter = "";
        String date="";
        String filter2="";
		try {
			con = ConnectionToDataBase.getConnection();

			if(act.getYear().equals("19"))
			{
				date="2019-04-01";
			}else{
				date="2020-04-01";
			}
			
			
			
			
			if(this.getUserRole().equals("199")){
				
				
				reportQuery ="" +
						" SELECT aa.head_code,  " +
						" SUM(COALESCE(aa.jan_amt,0)) as amt_jan, SUM(COALESCE(aa.feb_amt,0)) as amt_feb,     " +
						" SUM(COALESCE(aa.mar_amt,0)) as amt_mar, SUM(COALESCE(aa.apr_amt,0)) as amt_apr,     " +
						" SUM(COALESCE(aa.may_amt,0)) as amt_may, SUM(COALESCE(aa.june_amt,0)) as amt_june,   " +
						" SUM(COALESCE(aa.july_amt,0)) as amt_july, SUM(COALESCE(aa.aug_amt,0)) as amt_aug,   " +
						" SUM(COALESCE(aa.sep_amt,0)) as amt_sep, SUM(COALESCE(aa.oct_amt,0)) as amt_oct,     " +
						" SUM(COALESCE(aa.nov_amt,0)) as amt_nov, SUM(COALESCE(aa.dec_amt,0)) as amt_dec,     " +
						" SUM(COALESCE(aa.curr_amt,0)) as amt_current,                                        " +
						" COALESCE(sum(aa.jan_amt),0.0) + COALESCE(sum(aa.feb_amt),0.0) +  COALESCE(sum(aa.mar_amt),0.0) +  " +
						" COALESCE(sum(aa.apr_amt),0.0) + COALESCE(sum(aa.may_amt),0.0) + COALESCE(sum(aa.june_amt),0.0) +  " +
						" COALESCE(sum(aa.july_amt),0.0) + COALESCE(sum(aa.aug_amt),0.0) + COALESCE(sum(aa.sep_amt),0.0) +  " +
						" COALESCE(sum(aa.oct_amt),0.0) +  COALESCE(sum(aa.nov_amt),0.0) + COALESCE(sum(aa.dec_amt),0.0)    " +
						" as total " +
						" FROM  " +
						" (SELECT x.head_code, x.month,x.vch_tresiory_name, " +
						" CASE WHEN x.month=1 THEN SUM(x.amount) end as jan_amt,  " +
						" CASE WHEN x.month=2 THEN SUM(x.amount) end as feb_amt,  " +
						" CASE WHEN x.month=3 THEN SUM(x.amount) end as mar_amt,  " +
						" CASE WHEN x.month=4 THEN SUM(x.amount) end as apr_amt,  " +
						" CASE WHEN x.month=5 THEN SUM(x.amount) end as may_amt,  " +
						" CASE WHEN x.month=6 THEN SUM(x.amount) end as june_amt, " +
						" CASE WHEN x.month=7 THEN SUM(x.amount) end as july_amt, " +
						" CASE WHEN x.month=8 THEN SUM(x.amount) end as aug_amt,  " +
						" CASE WHEN x.month=9 THEN SUM(x.amount) end as sep_amt,  " +
						" CASE WHEN x.month=10 THEN SUM(x.amount) end as oct_amt, " +
						" CASE WHEN x.month=11 THEN SUM(x.amount) end as nov_amt, " +
						" CASE WHEN x.month=12 THEN SUM(x.amount) end as dec_amt, " +
						" CASE WHEN x.month=to_char(CURRENT_DATE ,'MM')::int THEN SUM(x.amount) end as curr_amt  " +
						" FROM                                                                                                        " +
						" (SELECT EXTRACT(MONTH FROM a.date_challan_date) as month, a.vch_tresiory_name, SUM(a.double_amt) as amount, " +
						" left(b.vch_head_code,15) as head_code  " +
						" FROM public.challan_deposit a, public.challn_deposit_detail b  " +
						" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type    " +
						" AND a.date_challan_date >='"+date+"' "+
						" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a')  " +
						" GROUP BY month, head_code, a.vch_tresiory_name   " +
						" UNION   " +
						" SELECT EXTRACT(MONTH FROM a.date_challan_date) as month, a.vch_tresiory_name, SUM(a.double_amt) as amount, " +
						" left(b.vch_head_code,15) as head_code    " +
						" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b  " +
						" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type   " +
						" AND a.date_challan_date >= '"+date+"' "+
						" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a')   " +
						" GROUP BY month, head_code,a.vch_tresiory_name  " +
						" UNION " +
						" SELECT EXTRACT(MONTH FROM a.date_challan_date) as month, a.vch_tresiory_name, SUM(a.double_amt) as amount, " +
						" left(b.vch_head_code,15) as head_code  " +
						" FROM bwfl.challan_deposit_brewery a, bwfl.challn_deposit_brewery_detail b   " +
						" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type " +
						" AND a.date_challan_date >= '"+date+"' "+
						" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a') " +
						" GROUP BY month, head_code,a.vch_tresiory_name       " +
						" UNION   " +
						" SELECT EXTRACT(MONTH FROM a.chalan_date) as month,a.tres_id as vch_tresiory_name, SUM(a.amount) as amount,  " +
						" left(a.head_code,15) as head_code   " +
						" FROM bwfl_license.chalan_deposit_bwfl_fl2d a    " +
						" WHERE a.chalan_date >= '"+date+"' "+
						" GROUP BY month, a.head_code,a.tres_id)x GROUP BY  x.month, x.head_code, x.vch_tresiory_name  " +
						" ORDER by x.month,x.head_code)aa," +
						" licence.treasury t, public.district d  " +
						" WHERE aa.vch_tresiory_name=t.tcode AND t.district_id=d.districtid " +
					   " AND d.deo='"+ResourceUtil.getUserNameAllReq().trim()+"' "+
						

						" GROUP BY aa.head_code ";                                                                                             
                        
				//System.out.println("Report Query------------IF---------------" + reportQuery);
			}
	
			else{
				
				reportQuery ="" +
						"" +                                                                                               
						" SELECT aa.head_code,                                                                              "+
						" SUM(COALESCE(aa.jan_amt,0)) as amt_jan, SUM(COALESCE(aa.feb_amt,0)) as amt_feb,                  "+
						" SUM(COALESCE(aa.mar_amt,0)) as amt_mar, SUM(COALESCE(aa.apr_amt,0)) as amt_apr,                  "+
						" SUM(COALESCE(aa.may_amt,0)) as amt_may, SUM(COALESCE(aa.june_amt,0)) as amt_june,                "+
						" SUM(COALESCE(aa.july_amt,0)) as amt_july, SUM(COALESCE(aa.aug_amt,0)) as amt_aug,                "+
						" SUM(COALESCE(aa.sep_amt,0)) as amt_sep, SUM(COALESCE(aa.oct_amt,0)) as amt_oct,                  "+
						" SUM(COALESCE(aa.nov_amt,0)) as amt_nov, SUM(COALESCE(aa.dec_amt,0)) as amt_dec,                  "+
						" SUM(COALESCE(aa.curr_amt,0)) as amt_current,                                                     "+
						"                                                                                                  "+
                        "                                                                                                  "+
                        "                                                                                                  "+
						" COALESCE(sum(aa.jan_amt),0.0) + COALESCE(sum(aa.feb_amt),0.0) +  COALESCE(sum(aa.mar_amt),0.0) + "+
						" COALESCE(sum(aa.apr_amt),0.0) + COALESCE(sum(aa.may_amt),0.0)+  COALESCE(sum(aa.june_amt),0.0)+  "+
						" COALESCE(sum(aa.july_amt),0.0)+  COALESCE(sum(aa.aug_amt),0.0) + COALESCE(sum(aa.sep_amt),0.0) + "+
						" COALESCE(sum(aa.oct_amt),0.0)  + COALESCE(sum(aa.nov_amt),0.0) + COALESCE(sum(aa.dec_amt),0.0)   "+
						" as total                                                                                         "+
                        "                                                                                                  "+
						" FROM                                                                                             "+
						" (SELECT x.head_code, x.month,                                                                    "+
						" CASE WHEN x.month=1 THEN SUM(x.amount) end as jan_amt,                                           "+
						" CASE WHEN x.month=2 THEN SUM(x.amount) end as feb_amt,                                           "+
						" CASE WHEN x.month=3 THEN SUM(x.amount) end as mar_amt,                                           "+
						" CASE WHEN x.month=4 THEN SUM(x.amount) end as apr_amt,                                           "+
						" CASE WHEN x.month=5 THEN SUM(x.amount) end as may_amt,                                           "+
						" CASE WHEN x.month=6 THEN SUM(x.amount) end as june_amt,                                          "+
						" CASE WHEN x.month=7 THEN SUM(x.amount) end as july_amt,                                          "+
						" CASE WHEN x.month=8 THEN SUM(x.amount) end as aug_amt,                                           "+
						" CASE WHEN x.month=9 THEN SUM(x.amount) end as sep_amt,                                           "+
						" CASE WHEN x.month=10 THEN SUM(x.amount) end as oct_amt,                                          "+
						" CASE WHEN x.month=11 THEN SUM(x.amount) end as nov_amt,                                          "+
						" CASE WHEN x.month=12 THEN SUM(x.amount) end as dec_amt,                                          "+
						" CASE WHEN x.month=to_char(CURRENT_DATE ,'MM')::int THEN SUM(x.amount) end as curr_amt           "+
                        "                                                                                                  "+
						" FROM                                                                                             "+
						" (SELECT EXTRACT(MONTH FROM a.date_challan_date) as month, SUM(a.double_amt) as amount,           "+
						" left(b.vch_head_code,15) as head_code                                                            "+
						" FROM public.challan_deposit a, public.challn_deposit_detail b                                    "+
						" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type               "+
						" AND a.date_challan_date >= '"+date+"' "+
                        "                                                                                                  "+
						" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a')  "+
						" GROUP BY month, head_code                                                                        "+
						" UNION                                                                                            "+
						" SELECT EXTRACT(MONTH FROM a.date_challan_date) as month, SUM(a.double_amt) as amount,            "+
						" left(b.vch_head_code,15) as head_code                                                            "+
						" FROM revenue.g6_challan_deposit a, revenue.g6_challn_deposit_detail b                            "+
						" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type               "+
						" AND a.date_challan_date >= '"+date+"' "+
                        "                                                                                                  "+
						" AND a.vch_assessment_year = (select (sesn_id)  from public.mst_financial_year where active='a')  "+
						" GROUP BY month, head_code                                                                        "+
						" UNION                                                                                            "+
						" SELECT EXTRACT(MONTH FROM a.date_challan_date) as month, SUM(a.double_amt) as amount,            "+
						" left(b.vch_head_code,15) as head_code                                                            "+
						" FROM bwfl.challan_deposit_brewery a, bwfl.challn_deposit_brewery_detail b                        "+
						" WHERE a.int_challan_id=b.int_challan_id AND  a.vch_challan_type=b.vch_challan_type               "+
						" AND a.date_challan_date >= '"+date+"' "+                                                   
						" GROUP BY month, head_code                                                                        "+
						" UNION                                                                                            "+
						" SELECT EXTRACT(MONTH FROM a.chalan_date) as month, SUM(a.amount) as amount,                      "+
						" left(a.head_code,15) as head_code                                                                "+
						" FROM bwfl_license.chalan_deposit_bwfl_fl2d a                                                     "+
						" WHERE a.chalan_date >= '"+date+"' "+
				
						" GROUP BY month, a.head_code)x GROUP BY  x.month, x.head_code                                     "+
						" ORDER by x.month,x.head_code)aa GROUP BY aa.head_code  ";
				
				
			}
			pst = con.prepareStatement(reportQuery);
			System.out.println("reportQuery-----CL2---------" + reportQuery);

			rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
			//	parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
						

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "G6_Challan_Report_Month_Wise_Revenue.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "G6_Challan_Report_Month_Wise_Revenue" + "-" + n + ".pdf");
				act.setPdfName("G6_Challan_Report_Month_Wise_Revenue" + "-" + n + ".pdf");
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

	
	
	
	
	
	
}
