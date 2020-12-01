package com.mentor.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.mentor.action.OccasionalBarStatusAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class OccasionalBarStatusImpl {
	
	// =======================print report=================================

	public void printReport(OccasionalBarStatusAction act)
	{


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

			reportQuery=  	" SELECT z.description, z.nmbr_of_applications,  " +
							" z.approved_same_period, z.approved_earlier, (z.approved_same_period+z.approved_earlier) as tot_approved, " +
							" z.reject_same_period, z.reject_earlier, (z.reject_same_period+z.reject_earlier) as tot_rejected, " +
							" z.timebar_same_period, z.timebar_earler, (z.timebar_same_period+z.timebar_earler) as tot_timebar, " +
							" SUM(COALESCE(z.revenue,0)) as revenue " +
							" FROM " +
							" (SELECT a.districtid, a.description, COALESCE(x.nmbr_of_applications,0) as nmbr_of_applications, x.revenue, " +
							" (SELECT COUNT(c.id) as approved_same_period FROM hotel_bar_rest.request_for_occasional_bar_license c " +
							" WHERE c.vch_challan_no IS NOT NULL AND c.vch_approve IS NOT NULL AND c.vch_approve='Approved' " +
							" AND c.application_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+  
							" AND a.districtid=c.applicant_district_id), " +
							" (SELECT COUNT(d.id) as approved_earlier FROM hotel_bar_rest.request_for_occasional_bar_license d " +
							" WHERE d.vch_challan_no IS NOT NULL AND d.vch_approve IS NOT NULL AND d.vch_approve='Approved' " +
							" AND d.application_date<'"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND a.districtid=d.applicant_district_id), " +
							" (SELECT COUNT(e.id) as reject_same_period FROM hotel_bar_rest.request_for_occasional_bar_license e " +
							" WHERE e.vch_challan_no IS NOT NULL AND e.vch_approve IS NOT NULL AND e.vch_approve='Rejected' " +
							" AND e.application_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+ 
							" AND a.districtid=e.applicant_district_id), " +
							" (SELECT COUNT(f.id) as reject_earlier FROM hotel_bar_rest.request_for_occasional_bar_license f " +
							" WHERE f.vch_challan_no IS NOT NULL AND f.vch_approve IS NOT NULL AND f.vch_approve='Rejected' " +
							" AND f.application_date<'"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND a.districtid=f.applicant_district_id), " +
							" (SELECT COUNT(g.id) as timebar_same_period FROM hotel_bar_rest.request_for_occasional_bar_license g " +
							" WHERE g.vch_challan_no IS NOT NULL AND g.vch_approve IS NOT NULL  " +
							" AND g.vch_approve='Rejected' AND g.remarks='Auto' " +
							" AND g.application_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+ 
							" AND a.districtid=g.applicant_district_id ), " +
							" (SELECT COUNT(h.id) as timebar_earler FROM hotel_bar_rest.request_for_occasional_bar_license h " +
							" WHERE h.vch_challan_no IS NOT NULL AND h.vch_approve IS NOT NULL  " +
							" AND h.vch_approve='Rejected' AND h.remarks='Auto' " +
							" AND h.application_date<'"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND a.districtid=h.applicant_district_id) " +
							" FROM public.district a LEFT OUTER JOIN " +
							" (SELECT COUNT(b.id) as nmbr_of_applications, SUM(b.vch_total_charge::int) as revenue, b.applicant_district_id " +
							" FROM hotel_bar_rest.request_for_occasional_bar_license b  " +
							" WHERE b.application_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' "+ 
							" GROUP BY b.applicant_district_id)x ON a.districtid=x.applicant_district_id " +
							" WHERE a.districtid!=0 " +
							" GROUP BY a.districtid, a.description, x.nmbr_of_applications, x.revenue)z  " +
							" GROUP BY z.description, z.nmbr_of_applications, z.approved_same_period, z.approved_earlier,  " +
							" z.reject_same_period, z.reject_earlier, z.timebar_same_period, z.timebar_earler " +
							" ORDER BY z.description";
			

				pst = con.prepareStatement(reportQuery);
				//System.out.println("reportQuery----------"+reportQuery);
				
				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);				
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "OccasionalBarStatusReport.jasper");
				
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "OccasionalBarStatusReport" + "-" + n + ".pdf");
				act.setPdfName("OccasionalBarStatusReport" + "-" + n + ".pdf");
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
