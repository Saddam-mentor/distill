package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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

import com.mentor.action.Sheera_StatementAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class Sheera_StatementImpl {

	public void printReportCountryLiquor(Sheera_StatementAction action) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		// String
		// relativePath=mypath+File.separator+"ExciseUp"+File.separator+"reports";

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "pdf";

		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		try {
			con = ConnectionToDataBase.getConnection();

			reportQuery = "select Sum(x.mollases_opening_bal_19) as mollases_opening_bal_for_19 , "
					+ "	  Sum(x.curr_yr_productn_quantiyi_19) as curr_yr_productn_quantiyi_for_19, "
					+ "	  SUM(x.currnt_consmptn_captive_19) as  currnt_consmptn_captive_for_19, "
					+ "	  SUM(x.currnt_consmptn_unrsrvd_19) as currnt_consmptn_unrsrvd_for_19, "
					+ "	  SUM(x.export_19) as export_for_19, "
					+ "	  SUM(x.db_wastage_19) as db_wastage_19, "
					+ "	  SUM(x.below_grade_19) as below_grade_19, "
					+ "	 SUM(x.mollases_opening_bal_18)as mollases_opening_bal_18, "
					+ "					SUM(x.curr_yr_productn_quantiyi_18)as curr_yr_productn_quantiyi_18, "
					+ "					SUM(x.currnt_consmptn_captive_18)as currnt_consmptn_captive_18, "
					+ "					SUM(x.currnt_consmptn_unrsrvd_18) as currnt_consmptn_unrsrvd_18, "
					+ "					SUM(x.export_18) as export_18,  "
					+ "					SUM(x.db_wastage_18)as db_wastage_18, "
					+ "					sum(x.below_grade_18) as below_grade_18, "
					+ "					SUM(x.mollases_opening_bal_17)as mollases_opening_bal_17, "
					+ "					SUM(x.curr_yr_productn_quantiyi_17)as curr_yr_productn_quantiyi_17 , "
					+ "					SUM(x.currnt_consmptn_captive_17) as currnt_consmptn_captive_17, "
					+ "					SUM(x.currnt_consmptn_unrsrvd_17) as currnt_consmptn_unrsrvd_17, "
					+ "					SUM(x.export_17) as export_17, "
					+ "					SUM(x.db_wastage_17) as db_wastage_17, "
					+ "					SUM(x.below_grade_17) as below_grade_17 "
					+ "					from ("
					+ "					     select mollases_opening_bal as mollases_opening_bal_17, "
					+ "					    curr_yr_productn_quantiyi as curr_yr_productn_quantiyi_17 , "
					+ "					    currnt_consmptn_captive as currnt_consmptn_captive_17,  "
					+ "					    currnt_consmptn_unrsrvd as currnt_consmptn_unrsrvd_17, "
					+ "					    export as export_17,a.gain_loss as db_wastage_17, "
					+ "					    b.below_grade as below_grade_17,"
					+ "					   					0 as mollases_opening_bal_18,0 as curr_yr_productn_quantiyi_18 , "
					+ "					    0 as currnt_consmptn_captive_18,0 as currnt_consmptn_unrsrvd_18, "
					+ "					    0 as export_18, 0 as  db_wastage_18,0 as below_grade_18, "
					+ "					     0 as  currnt_consmptn_captive_19,"
					+ "					 0 as   currnt_consmptn_unrsrvd_19,0 as export_19, "
					+ "					 0 as mollases_opening_bal_19, 0 as curr_yr_productn_quantiyi_19, "
					+ "					 0 as  db_wastage_19,0 as below_grade_19"
					+ "					from  public.sugarmill_molasses_burnt_grade_res_unres b, "
					+ "					    public.sessionwise_mollases_stock c"
					+ "					    left outer join public.sugarmill_dtl a on c.int_mil_id=a.int_sugarmill_id  "
					+ "					    where int_session='11' "
					+ "					  					      union all "
					+ "					    					    					      select  "
					+ "					0 as mollases_opening_bal_17,0 as curr_yr_productn_quantiyi_17 , "
					+ "					    0 as currnt_consmptn_captive_17,0 as currnt_consmptn_unrsrvd_17, "
					+ "					    0 as db_wastage_17,"
					+ "					    0 as export_17,0 as below_grade_17,"
					+ "					    mollases_opening_bal as mollases_opening_bal_18, "
					+ "					    curr_yr_productn_quantiyi as curr_yr_productn_quantiyi_18 , "
					+ "					    currnt_consmptn_captive as currnt_consmptn_captive_18,  "
					+ "					   currnt_consmptn_unrsrvd as currnt_consmptn_unrsrvd_18, "
					+ "					    export as export_18,a.gain_loss as db_wastage_18, "
					+ "					    b.below_grade as below_grade_18, "
					+ "					     0 as  currnt_consmptn_captive_19, "
					+ "					 0 as   currnt_consmptn_unrsrvd_19,0 as export_19, "
					+ "					 0 as  db_wastage_19 , 0 as mollases_opening_bal_19, 0 as curr_yr_productn_quantiyi_19, "
					+ "					    0 as below_grade_19 "
					+ "					 from public.sugarmill_molasses_burnt_grade_res_unres b, "
					+ "					    public.sessionwise_mollases_stock c "
					+ "					    left outer join public.sugarmill_dtl a on c.int_mil_id=a.int_sugarmill_id  "
					+ "					    where int_session='12' "
					+ "					    					    union all "
					+ "					    					     select 0 as  currnt_consmptn_captive_17, "
					+ "					 0 as   currnt_consmptn_unrsrvd_17,0 as export_17, "
					+ "					 0 as  db_wastage_17 , 0 as mollases_opening_bal_17, 0 as curr_yr_productn_quantiyi_17, "
					+ "					    0 as below_grade_17, 0 as mollases_opening_bal_18,0 as curr_yr_productn_quantiyi_18 ,"
					+ "					    0 as currnt_consmptn_captive_18,0 as currnt_consmptn_unrsrvd_18, "
					+ "					    0 as export_18,0 as db_wastage_18 ,0 as below_grade_18,"
					+ "					    					   mollases_opening_bal as mollases_opening_bal_19,"
					+ "					   curr_yr_productn_quantiyi as curr_yr_productn_quantiyi_19 ,"
					+ "					    currnt_consmptn_captive as currnt_consmptn_captive_19, "
					+ "					    currnt_consmptn_unrsrvd as currnt_consmptn_unrsrvd_19,"
					+ "					    export as export_19,a.gain_loss as db_wastage_19,"
					+ "					    b.below_grade as below_grade_19 "
					+ "					    					   					   					        from  "
					+ "					   public.sugarmill_molasses_burnt_grade_res_unres b,"
					+ "					    public.sessionwise_mollases_stock c "
					+ "					   left outer join public.sugarmill_dtl a on c.int_mil_id=a.int_sugarmill_id  "
					+ "					    where int_session='13' "
					+ "					    					    					       )x";

			pst = con.prepareStatement(reportQuery);
			System.out.println("sandeep=" + reportQuery);
			rs = pst.executeQuery();

			if (rs.next()) {
				rs = pst.executeQuery();

				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "sheeraStatement.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator + "sheeraStatement"
								+ n + ".pdf");
				action.setPdfname("sheeraStatement" + n + ".pdf");
				action.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
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

}
