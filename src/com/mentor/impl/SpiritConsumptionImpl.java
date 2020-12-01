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


import com.mentor.action.SpiritConsumptionAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class SpiritConsumptionImpl {
	
	// ===================get distillery list======================

		public ArrayList getDistilleryList(SpiritConsumptionAction act) {

			ArrayList list = new ArrayList();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String query = 	"";
			SelectItem item = new SelectItem();
			item.setLabel("--SELECT--");
			item.setValue("0");
			list.add(item);

			
			try {

				query = " SELECT vch_undertaking_name, int_app_id_f FROM public.dis_mst_pd1_pd2_lic " +
						" WHERE vch_verify_flag='V'  " +
						" ORDER BY vch_undertaking_name ";

				conn = ConnectionToDataBase.getConnection();
				pstmt = conn.prepareStatement(query);

				System.out.println("distillery query---------------------" + query);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					item = new SelectItem();
					item.setValue(rs.getString("int_app_id_f"));
					item.setLabel(rs.getString("vch_undertaking_name"));

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
		
		// ===================print report======================

		public void printReport(SpiritConsumptionAction act){

			String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

			String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
			String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
			JasperReport jasperReport = null;
			PreparedStatement pst = null;
			Connection con = null;
			ResultSet rs = null;
			String reportQuery = null;

			try {
				con = ConnectionToDataBase.getConnection();
					
				                                                                                                                            
			reportQuery = 	" SELECT x.dist_id, x.dt, x.description, SUM(x.sale_bl)sale_bl, SUM(x.sale_al)sale_al,                       "+
							" mst.vch_undertaking_name as dist_name                                                                     "+
							" FROM                                                                                                      "+
							" (SELECT a.distillery_id as dist_id, a.dt_created as dt, SUM(a.recv_bl)sale_bl, SUM(a.recv_al)sale_al,     "+
							" CASE WHEN a.vch_saletype='SUP' THEN 'Spirit Sale In UP'                                                   "+
							" WHEN a.vch_saletype='EI' THEN 'Spirit Export In India'                                                    "+
							" WHEN a.vch_saletype='EO' THEN 'Spirit Export In Other Countries' end as description                       "+
							" FROM distillery.export_spirit_in_state a                                                                  "+
							" WHERE a.dt_created BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                      "+
							" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                               "+
							" AND a.distillery_id='"+act.getDistilleryID()+"'                                                           "+
							" GROUP BY a.distillery_id, a.dt_created, a.vch_saletype                                                    "+
							" UNION                                                                                                     "+
							" SELECT a.distillery_id as dist_id, a.dt_created as dt, SUM(a.recv_bl)sale_bl, SUM(a.recv_al)sale_al,      "+
							" CASE WHEN a.vch_saletype='SUP' THEN 'Denature Spirit Sale In UP'                                          "+
							" WHEN a.vch_saletype='EI' THEN 'Denature Spirit Export In India'                                           "+
							" WHEN a.vch_saletype='EO' THEN 'Denature Spirit Export In Other Countries' end as description              "+
							" FROM distillery.export_denatured_spirit a                                                                 "+
							" WHERE a.dt_created BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                      "+
							" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                               "+
							" AND a.distillery_id='"+act.getDistilleryID()+"'                                                           "+
							" GROUP BY a.distillery_id, a.dt_created, a.vch_saletype                                                    "+
							" UNION                                                                                                     "+
							" SELECT a.distillery_id as dist_id, a.txn_date as dt, SUM(a.recieve_bl)sale_bl,                            "+
							" SUM(a.recieve_al)sale_al, 'Transfer To FL Blending VAT' as description                                    "+
							" FROM distillery.master_bottoling_of_fl a                                                                  "+
							" WHERE a.txn_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                        "+
							" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                               "+
							" AND a.distillery_id='"+act.getDistilleryID()+"'                                                           "+
							" GROUP BY a.distillery_id, a.txn_date                                                                      "+
							" UNION                                                                                                     "+
							" SELECT a.distillery_id as dist_id, a.date_created as dt, SUM(a.sprit_taken)sale_bl,                       "+
							" SUM(a.sprit_taken_al)sale_al, 'Transfer To CL Blending VAT' as description                                "+
							" FROM distillery.country_liquor_blending a                                                                 "+
							" WHERE a.date_created BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"'                    "+
							" AND '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"'                                               "+
							" AND a.distillery_id='"+act.getDistilleryID()+"'                                                           "+
							" GROUP BY a.distillery_id, a.date_created)x, public.dis_mst_pd1_pd2_lic mst                                "+
							" WHERE x.dist_id=mst.int_app_id_f GROUP BY x.dist_id, x.dt, x.description, dist_name                       "+
							" ORDER BY dist_name, x.dt ";                                                                              
				

				

				pst = con.prepareStatement(reportQuery);
				System.out.println("reportQuery-----FL2---------" + reportQuery);

				rs = pst.executeQuery();

				if(rs.next()) {

					
					rs = pst.executeQuery();
					Map parameters = new HashMap();
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("SUBREPORT_DIR", relativePath + File.separator);
					parameters.put("image", relativePath + File.separator);
					parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
					parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
					
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

					jasperReport = (JasperReport) JRLoader.loadObject(relativePath+ File.separator + "SpiritConsumptionReport.jasper");

					JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager.exportReportToPdfFile(print, relativePathpdf+ File.separator + "SpiritConsumptionReport_" + n + ".pdf");
					act.setPdfName("SpiritConsumptionReport_" + n + ".pdf");
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

}
