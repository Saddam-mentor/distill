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

import com.mentor.action.BreakageReportaction;

import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class BreakageReportimpl {

	/*
	 * public void printReport(BreakageReportaction action) {
	 * 
	 * String mypath=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH;
	 * 
	 * String
	 * relativePath=mypath+File.separator+"ExciseUp"+File.separator+"MIS"+
	 * File.separator+"jasper"; String
	 * relativePathpdf=mypath+File.separator+"ExciseUp"
	 * +File.separator+"MIS"+File.separator+"pdf"; JasperReport jasperReport =
	 * null; JasperPrint jasperPrint = null; PreparedStatement pst=null;
	 * Connection con=null; ResultSet rs=null; String reportQuery=null; String
	 * reportQuerybrewery=null; String month=null; String val=null; String
	 * filter=""; String type="";
	 * 
	 * 
	 * if(action.getRadio().equalsIgnoreCase("D")){
	 * filter=" a.int_app_id_f='"+action.getUnit_id()+"' and "; }else
	 * if(action.getRadio().equalsIgnoreCase("AD")) { filter="  "; }else
	 * if(action.getRadio().equalsIgnoreCase("B")){
	 * filter=" a.vch_app_id_f='"+action.getUnit_id()+"' and  "; }else
	 * if(action.getRadio().equalsIgnoreCase("AB")) { filter="  "; }
	 * 
	 * try { con =ConnectionToDataBase.getConnection();
	 * 
	 * 
	 * if(action.getRadio2().equalsIgnoreCase("S")){ type="Summary";
	 * 
	 * 
	 * 
	 * reportQuery =
	 * " SELECT a.vch_undertaking_name as unit_name,sum(b.int_boxes) as int_boxes, "
	 * +
	 * " sum(c.bottling_no_of_box) as bottling_no_of_box,(sum(b.int_boxes)-sum(c.bottling_no_of_box))"
	 * + " as breakage FROM public.dis_mst_pd1_pd2_lic a," +
	 * " distillery.mst_bottling_plan_20_21 b,distillery.daily_bottling_stock_20_21 c "
	 * + " where  "+filter+
	 * " a.int_app_id_f=b.int_distillery_id and b.int_distillery_id=c.distillery_id"
	 * + " group by 	a.vch_undertaking_name ";
	 * 
	 * reportQuerybrewery=
	 * "SELECT a.brewery_nm as unit_name,sum( coalesce (b.int_boxes, '0')) as int_boxes, "
	 * + " sum(coalesce(c.bottling_no_of_box,'0')) as bottling_no_of_box," +
	 * " (sum(coalesce (b.int_boxes, '0'))-sum(coalesce(c.bottling_no_of_box,'0'))) as breakage"
	 * + " FROM public.bre_mst_b1_lic a,bwfl.mst_bottling_plan_20_21 b," +
	 * " bwfl.daily_bottling_stock_20_21 c where "+filter+" " +
	 * " a.vch_app_id_f=b.int_brewery_id and b.int_brewery_id=c.distillery_id "
	 * + " group by 	a.brewery_nm ";
	 * 
	 * } else if (action.getRadio2().equalsIgnoreCase("D")) { type="Detail";
	 * 
	 * reportQuery=
	 * "SELECT sum(b.int_boxes) as seq, b.plan_dt , b.int_brand_id, b.int_pack_id , sum(b.int_boxes) "
	 * +
	 * " as bottling_no_of_box ,(sum(b.int_boxes)-sum(c.bottling_no_of_box)) as breakge"
	 * +
	 * " FROM public.dis_mst_pd1_pd2_lic a, distillery.mst_bottling_plan_20_21 b "
	 * +
	 * " ,distillery.daily_bottling_stock_20_21 c where  a.int_app_id_f=b.int_distillery_id "
	 * +
	 * "and b.int_distillery_id=c.distillery_id group by b.plan_dt , b.int_brand_id, b.int_pack_id "
	 * ;
	 * 
	 * 
	 * reportQuerybrewery=" SELECT b.int_pack_id,b.int_brand_id,b.plan_dt,b.seq, "
	 * + " sum(coalesce(c.bottling_no_of_box,'0')) as bottling_no_of_box, " +
	 * " (sum(coalesce (b.int_boxes, '0'))-sum(coalesce(c.bottling_no_of_box,'0')))"
	 * +
	 * " as breakage FROM public.bre_mst_b1_lic a,bwfl.mst_bottling_plan_20_21 b, bwfl.daily_bottling_stock_20_21 "
	 * +
	 * " c where  a.vch_app_id_f=b.int_brewery_id and b.int_brewery_id=c.distillery_id  group by "
	 * + " a.brewery_nm , b.int_pack_id,b.int_brand_id,b.plan_dt,b.seq "; }
	 * System.out.println("order by---------------------------"+reportQuery);
	 * System
	 * .out.println("order by---------------------------"+reportQuerybrewery);
	 * 
	 * if(action.getRadio().equalsIgnoreCase("D") ||
	 * action.getRadio().equalsIgnoreCase("AD")){
	 * if(action.getRadio2().equalsIgnoreCase("S")){
	 * pst=con.prepareStatement(reportQuery); } } else
	 * if(action.getRadio().equalsIgnoreCase("B") ||
	 * action.getRadio().equalsIgnoreCase("AB")){
	 * if(action.getRadio2().equalsIgnoreCase("S")){
	 * pst=con.prepareStatement(reportQuerybrewery); } }
	 * 
	 * rs=pst.executeQuery(); if(rs.next()){ rs=pst.executeQuery(); Map
	 * parameters = new HashMap(); parameters.put("REPORT_CONNECTION", con);
	 * parameters.put("SUBREPORT_DIR", relativePath+File.separator);
	 * parameters.put("image", relativePath+File.separator);
	 * 
	 * parameters.put("val", type);
	 * 
	 * JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
	 * if(action.getRadio2().equalsIgnoreCase("S")){ jasperReport =
	 * (JasperReport)
	 * JRLoader.loadObject(relativePath+File.separator+"Breakage_Report.jasper"
	 * );
	 * 
	 * }else{ jasperReport = (JasperReport) JRLoader.loadObject(relativePath +
	 * File.separator+ "Detail_breakage1.jasper"); }
	 * 
	 * 
	 * JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters,
	 * jrRs); Random rand = new Random(); int n = rand.nextInt(250) + 1;
	 * 
	 * JasperExportManager.exportReportToPdfFile(print,relativePathpdf+File.
	 * separator+"Breakage_Report"+n+".pdf");
	 * action.setPdfname("Breakage_Report"+n+".pdf"); action.setPrintFlag(true);
	 * }else{ FacesContext.getCurrentInstance().addMessage( null, new
	 * FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data Found!!",
	 * "No Data Found!!")); action.setPrintFlag(false); }
	 * 
	 * } catch (JRException e) { e.printStackTrace(); } catch (Exception e) {
	 * e.printStackTrace(); } finally { try { if(rs!=null)rs.close();
	 * if(con!=null)con.close(); } catch(SQLException e) { e.printStackTrace();
	 * } }
	 * 
	 * }
	 */

	// -----------------------------------print----

	public void printReport(BreakageReportaction action) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String reportQuerybrewery = null;
		String month = null;
		String val = null;
		String filter = "";
		String type = "";

		if (action.getRadio().equalsIgnoreCase("D")) {
			filter = " a.int_app_id_f='" + action.getUnit_id() + "' and ";
		} else if (action.getRadio().equalsIgnoreCase("AD")) {
			filter = "  ";
		} else if (action.getRadio().equalsIgnoreCase("B")) {
			filter = " a.vch_app_id_f='" + action.getUnit_id() + "' and  ";
		} else if (action.getRadio().equalsIgnoreCase("AB")) {
			filter = "  ";
		}

		try {
			con = ConnectionToDataBase.getConnection();

			if (action.getRadio2().equalsIgnoreCase("S")) {
				type = "Summary";

				if (action.getRadio().equalsIgnoreCase("D")
						|| action.getRadio().equalsIgnoreCase("AD")) {
					reportQuery = "SELECT a.vch_undertaking_name as unit_name,b.int_distillery_id ,sum(b.int_boxes)"
							+ " as int_boxes, (select sum(d.box) from (select  round(((d.int_no_bottle)/(e.box_size))+.5) "
							+ " as box from distillery.bottling_dtl_20_21 d,  distillery.box_size_details "
							+ " e where d.int_dissleri_id='30' and  e.qnt_ml_detail=d.double_quantity  "
							+ " and e.type='D' union all select  round(((d.int_no_bottle)/(e.box_size))+.5)  as box"
							+ " from distillery.bottling_dtl_cl_20_21 d, distillery.box_size_details e "
							+ "where d.int_dissleri_id='30' and  e.qnt_ml_detail=d.double_quantity  and e.type='D' )d)"
							+ " as int_no_bottle,(select sum(c.bottling_no_of_box) "
							+ "from distillery.daily_bottling_stock_20_21 c  where   b.int_distillery_id=c.distillery_id  )"
							+ "as bottling_no_of_box  FROM public.dis_mst_pd1_pd2_lic a, distillery.mst_bottling_plan_20_21 b "
							+ "where "
							+ filter
							+ " a.int_app_id_f=b.int_distillery_id group by 	a.vch_undertaking_name,b.int_distillery_id  ";

					 

				} else if (action.getRadio().equalsIgnoreCase("B")
						|| action.getRadio().equalsIgnoreCase("AB")) {
					reportQuerybrewery = "SELECT  a.brewery_nm as unit_name,sum( coalesce (b.int_boxes, '0')) as int_boxes, "
							+ "(select sum(d.box) from (select  round(((d.int_no_bottle)/(e.box_size))+.5) "
							+ " as box from bwfl.bottling_dtl_20_21 d,  distillery.box_size_details e"
							+ " where d.int_brewery_id='3'  and  e.qnt_ml_detail=d.double_quantity  and e.type='B'"
							+ "union all "
							+ "select  round(((d.int_no_bottle)/(e.box_size))+.5)  as box from bwfl.bottling_dtl_20_21 d,  "
							+ "distillery.box_size_details e where d.int_brewery_id='30' and  e.qnt_ml_detail=d.double_quantity  and e.type='B' )d) "
							+ "as int_no_bottle,(select sum(c.bottling_no_of_box) from bwfl.daily_bottling_stock_20_21 c  where "
							+ "b.int_brewery_id=c.distillery_id  ) as bottling_no_of_box  FROM public.bre_mst_b1_lic a, "
							+ "bwfl.mst_bottling_plan_20_21 b   where "
							+ filter
							+ " a.vch_app_id_f=b.int_brewery_id   "
							+ "group by 	a.brewery_nm,b.int_brewery_id ";

					 
				}
			} else if (action.getRadio2().equalsIgnoreCase("DL")) {
				type = "Detail";

				if (action.getRadio().equalsIgnoreCase("D")
						|| action.getRadio().equalsIgnoreCase("AD")) {

					reportQuery = "SELECT a.vch_undertaking_name as unit_name,b.int_distillery_id ,b.seq, " +
							"  b.int_brand_id,b.int_pack_id,pp.package_name  ,bb.brand_name, " +
							" 	sum(b.int_boxes) as int_boxes, (select sum(d.box) from   " +
							" 	(select  round(((d.int_no_bottle)/(e.box_size))+.5)  as box from  " + 
							" 	distillery.bottling_dtl_20_21 d,distillery.bottling_master_20_21 f,   " +  
							" 	distillery.box_size_details e where d.int_dissleri_id=b.int_distillery_id and " +  
							" 	e.qnt_ml_detail=d.double_quantity  and e.type='D'   and b.int_brand_id=d.int_brand_id and b.int_pack_id=d.vch_description::int  " + 
							" 	and f.int_id=d.bottoling_masterid_fk and f.int_dissleri_id=d.int_dissleri_id and f.planid=b.seq  " +
							" 	union all   " +
							" 	select  round(((d.int_no_bottle)/(e.box_size))+.5)  as box from distillery.bottling_dtl_cl_20_21 d,distillery.bottling_master_cl_20_21 f,  " + 
							" 	distillery.box_size_details e where d.int_dissleri_id=b.int_distillery_id and  e.qnt_ml_detail=d.double_quantity  and e.type='D'  " +
							" 	and b.int_brand_id=d.int_brand_id and b.int_pack_id=d.vch_description::int  and f.int_id=d.bottoling_masterid_fk and  " +
							" 	f.int_dissleri_id=d.int_dissleri_id and f.planid=b.seq )d)  as int_no_bottle,(select sum(c.bottling_no_of_box)  " +
							" 	from distillery.daily_bottling_stock_20_21 c  where  b.int_distillery_id=c.distillery_id and b.seq=c.pland_id  " +
							" 	and b.int_brand_id=c.brand_id and b.int_pack_id=c.packaging_id ) as bottling_no_of_box  FROM public.dis_mst_pd1_pd2_lic a,   " +
							" 	distillery.mst_bottling_plan_20_21 b ,distillery.packaging_details_20_21 pp ,distillery.brand_registration_20_21 bb   where  " + 
							" 	  "+filter+" pp.package_id=b.int_pack_id and bb.brand_id=b.int_brand_id and " +
							" 	b.plan_dt ='"+Utility.convertUtilDateToSQLDate(action.getFromDate())+"'    and   " +
							" 	a.int_app_id_f=b.int_distillery_id   group by 	a.vch_undertaking_name,b.int_distillery_id ,b.seq,b.int_brand_id,b.int_pack_id " + 
							" 	,pp.package_name,bb.brand_name order by a.vch_undertaking_name";
					
					
		
					 
				}

				else if (action.getRadio().equalsIgnoreCase("B")
						|| action.getRadio().equalsIgnoreCase("AB")) {
					reportQuerybrewery = "SELECT  a.brewery_nm as unit_name,b.int_brewery_id as int_distillery_id ,b.seq,b.int_brand_id,b.int_pack_id," +
										" pp.package_name , bb.brand_name," +
										" sum(b.int_boxes) as int_boxes, (select sum(d.box) from (select  round(((d.int_no_bottle)/(e.box_size))+.5)" + 
										" as box from bwfl.bottling_dtl_20_21 d,bwfl.bottling_master_20_21 f,  distillery.box_size_details e  where d.int_brewery_id=b.int_brewery_id" +  
										" and  e.qnt_ml_detail=d.double_quantity and e.type='B'   and b.int_brand_id=d.int_brand_id and b.int_pack_id=d.vch_description::int " +
										" and f.int_brewery_id=d.bottoling_masterid_fk and f.int_brewery_id=d.int_brewery_id " +
										" and f.planid=b.seq" +
										" union all " +
										" select  round(((d.int_no_bottle)/(e.box_size))+.5)  as box from bwfl.bottling_dtl_20_21 d, " +
										" bwfl.bottling_master_20_21 f, distillery.box_size_details e where d.int_brewery_id=b.int_brewery_id and " +
										" e.qnt_ml_detail=d.double_quantity  and e.type='D' and b.int_brand_id=d.int_brand_id and b.int_pack_id=d.vch_description::int" + 
										" and f.int_id=d.bottoling_masterid_fk and f.int_brewery_id=d.int_brewery_id and f.planid=b.seq" +
										" )d)  as int_no_bottle,(select sum(c.bottling_no_of_box) from bwfl.daily_bottling_stock_20_21 c " +
										" where b.int_brewery_id=c.distillery_id and b.seq=c.pland_id and b.int_brand_id=c.brand_id and b.int_pack_id=c.packaging_id )" +
										" as bottling_no_of_box  FROM public.bre_mst_b1_lic a, bwfl.mst_bottling_plan_20_21 b ," +
										" distillery.packaging_details_20_21 pp ,distillery.brand_registration_20_21 bb  " +
										" where  "+filter+" " +
										" b.plan_dt ='"+Utility.convertUtilDateToSQLDate(action.getFromDate())+"'and  a.vch_app_id_f=b.int_brand_id    and pp.package_id=b.int_pack_id and bb.brand_id=b.int_brand_id" +
										" group by 	 a.brewery_nm,b.int_brand_id ,b.seq,b.int_brand_id,b.int_pack_id,b.int_brewery_id,pp.package_name , bb.brand_name order by a.brewery_nm";
									
	
							 

				}
			}

			if (action.getRadio2().equalsIgnoreCase("S")) {
				if (action.getRadio().equalsIgnoreCase("D")
						|| action.getRadio().equalsIgnoreCase("AD")) {
					pst = con.prepareStatement(reportQuery);

				} else if (action.getRadio().equalsIgnoreCase("B")
						|| action.getRadio().equalsIgnoreCase("AB")) {

					pst = con.prepareStatement(reportQuerybrewery);
				}
			} else if (action.getRadio2().equalsIgnoreCase("DL")) {
				if (action.getRadio().equalsIgnoreCase("D")
						|| action.getRadio().equalsIgnoreCase("AD")) {

					 
					pst = con.prepareStatement(reportQuery);
				}

				else if (action.getRadio().equalsIgnoreCase("B")
						|| action.getRadio().equalsIgnoreCase("AB")) {
				 
					pst = con.prepareStatement(reportQuerybrewery);
				}
			}

			rs = pst.executeQuery();
			if (rs.next()) {
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);

				parameters.put("val", type);
				if (action.getRadio2().equalsIgnoreCase("DL")) {
					parameters.put("plan_date", Utility
							.convertUtilDateToSQLDate(action.getFromDate()));
				}
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				if (action.getRadio2().equalsIgnoreCase("DL")) {
					jasperReport = (JasperReport) JRLoader
							.loadObject(relativePath + File.separator
									+ "Detail_breakage1.jasper");

				} else {
					jasperReport = (JasperReport) JRLoader
							.loadObject(relativePath + File.separator
									+ "Breakage_Report.jasper");
				}

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator + "Breakage_Report"
								+ n + ".pdf");
				action.setPdfname("Breakage_Report" + n + ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found!!", "No Data Found!!"));
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

	public ArrayList getList(BreakageReportaction act) {
		{

			ArrayList list = new ArrayList();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query = null;
			SelectItem item = new SelectItem();
			item.setLabel("--select--");
			item.setValue(0);
			list.add(item);

			try {

				if (act.getRadio().equalsIgnoreCase("D")) {
					query = "  SELECT int_app_id_f as app_id,vch_undertaking_name as name,vch_wrk_add  from  dis_mst_pd1_pd2_lic order by vch_undertaking_name ";

				}

				else if (act.getRadio().equalsIgnoreCase("B")) {
					query = " 	select   vch_app_id_f as app_id , brewery_nm as name , vch_reg_address  FROM public.bre_mst_b1_lic  where vch_verify_flag='V'   order by name  ";

				} else if (ResourceUtil.getUserNameAllReq().trim()
						.substring(0, 9).equalsIgnoreCase("Excise-DL")) {

					if (act.getRadio().equalsIgnoreCase("D")) {
						query = "  SELECT int_app_id_f as app_id,vch_undertaking_name as name,vch_wrk_add  from  dis_mst_pd1_pd2_lic where int_app_id_f='"
								+ act.getUnit_id()
								+ "' order by vch_undertaking_name ";

					}
				}
				conn = ConnectionToDataBase.getConnection();
				pstmt = conn.prepareStatement(query);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					item = new SelectItem();

					item.setValue(rs.getString("app_id"));
					item.setLabel(rs.getString("name"));

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
	}

	// --------------------- validation------------------

	public String getDetails(BreakageReportaction action) {
		int id = 0;
		Connection con = null;
		PreparedStatement pstmt = null, ps2 = null;
		ResultSet rs = null, rs2 = null;

		try {
			String queryList = "";
			con = ConnectionToDataBase.getConnection();
			// try {
			if (ResourceUtil.getUserNameAllReq().trim().substring(0, 9)
					.equalsIgnoreCase("Excise-DL")) {
				// action.setDist_name(ResourceUtil.getUserNameAllReq().trim());
				action.setUnit_id(ResourceUtil.getUserNameAllReq().trim()
						.substring(10));
				// ac.setAddress("");

				System.out.println("1--"
						+ ResourceUtil.getUserNameAllReq().trim()
								.substring(0, 9) + "-");
			} else {
				queryList = " SELECT int_app_id_f as app_id,vch_undertaking_name as name ,vch_wrk_add as address,'D' as login_type"
						+ " FROM dis_mst_pd1_pd2_lic WHERE vch_wrk_phon='"
						+ ResourceUtil.getUserNameAllReq().trim() + "'";

				pstmt = con.prepareStatement(queryList);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					// action.setDist_name(rs.getString("name"));
					action.setUnit_id(rs.getString("app_id"));
					// ac.setAddress(rs.getString("address"));

				}

			}
		} catch (SQLException se) {
			se.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(se.getMessage(), se.getMessage()));
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
