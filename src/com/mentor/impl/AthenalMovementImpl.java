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

import com.mentor.action.AthenalMovementAction;
import com.mentor.action.DateWiseDispatchAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;



public class AthenalMovementImpl {
	
	
	public ArrayList getDistrictList1(AthenalMovementAction ac) {

		ArrayList list = new ArrayList();

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "select * from public.district order by description ";

		SelectItem item = new SelectItem();
		item.setLabel("---ALL---");
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

	

	public boolean printReportImpl(AthenalMovementAction act) {
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
		boolean printFlag = false;
		String filter="";
		
		
		try {
			
			
			
			if(act.getDist_id()==9999 )
			{
				filter="";
			}else{
				filter="and c.depo_dist_id=" + act.getDist_id();
			}
			if(act.getRadio().equalsIgnoreCase("A")){
				
				reportQuery = "select c.depo_name, d.description,b.indent_no, b.approval_date," +
						"		 b.approved_qty,a.gatepass,a.recv_bl,a.dt_created,e.received_date" +
						"		from distillery.export_denatured_spirit a ," +
						"		fl41.fl41_indent_detail_approved b," +
						"		fl41.fl41_registration_approval c," +
						"		public.district d," +
						"		fl41.fl41_pass_receipt e" +
						"		where a.fl41_indent_no=b.indent_no and" +
						"		c.int_id=b.fl41_id and " +
						"		c.depo_dist_id=d.districtid and " +
						"		a.gatepass=e.pass_no" +
						"		 AND b.approval_date BETWEEN '"+
						 Utility.convertUtilDateToSQLDate(act.getFromdate())
						+ "' AND "
						+ " '"+
						Utility.convertUtilDateToSQLDate(act.getTodate())
						+ "'	"+	filter;
				}else{
					reportQuery = "select c.depo_name, d.description,b.indent_no, b.approval_date," +
							"		 b.approved_qty,a.gatepass,a.recv_bl,a.dt_created,e.received_date" +
							"		from distillery.export_denatured_spirit a ," +
							"		fl41.fl41_indent_detail_approved b," +
							"		fl41.fl41_registration_approval c," +
							"		public.district d," +
							"		fl41.fl41_pass_receipt e" +
							"		where a.fl41_indent_no=b.indent_no and" +
							"		c.int_id=b.fl41_id and " +
							"		c.depo_dist_id=d.districtid and " +
							"		a.gatepass=e.pass_no" +
							"		 AND b.approval_date BETWEEN '"+
							 Utility.convertUtilDateToSQLDate(act.getFromdate())
							+ "' AND "
							+ " '"+
							Utility.convertUtilDateToSQLDate(act.getTodate())
							+ "'	and indent_id='0'	"+filter
							 ;
				}
			
			
			
			con = ConnectionToDataBase.getConnection();


			pst = con.prepareStatement(reportQuery);
			System.out.println("---->>>>   "+reportQuery);

			rs = pst.executeQuery();
			if (rs.next()) {

				rs = pst.executeQuery();

				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",
						Utility.convertUtilDateToSQLDate(act.getFromdate()));
				parameters.put("toDate",
						Utility.convertUtilDateToSQLDate(act.getTodate()));

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator
						+ "AthenalMovementReport.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "AthenalMovementReport" + n + ".pdf");

			
				act.setPdfname("AthenalMovementReport" + n + ".pdf");
				act.setPrintFlag(true);
				printFlag = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
				act.setPrintFlag(false);
				printFlag = false;
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

		return printFlag;
	}

	
	
}
