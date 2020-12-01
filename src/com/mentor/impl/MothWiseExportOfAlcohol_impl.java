	package com.mentor.impl;
	import java.io.File;
import java.io.FileOutputStream;
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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mentor.action.MothWiseExportOfAlcohol_action;
	import com.mentor.action.MothWiseExportOfAlcohol_action;
import com.mentor.action.report_power_alco_action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

	public class MothWiseExportOfAlcohol_impl {
		
		public ArrayList getMonthList(MothWiseExportOfAlcohol_action act)
		{

			ArrayList list = new ArrayList();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			SelectItem item = new SelectItem();
			item.setLabel("--select--");
			item.setValue("");
			list.add(item);
			try {
				String query = " SELECT month_id, description FROM public.month_master ORDER BY month_id ";

				conn = ConnectionToDataBase.getConnection();
				pstmt = conn.prepareStatement(query);
				
				System.out.println("------------------get Month List-------------"+query);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					item = new SelectItem();

					item.setValue(rs.getString("month_id"));
					item.setLabel(rs.getString("description"));
					
					//act.setMontth(rs.getInt("month_id"));

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
		
	
		
		public void month_name(MothWiseExportOfAlcohol_action act)
		{
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				String query = " SELECT m.description,year FROM public.month_master m,public.reporting_year where month_id = '"+act.getMontth()+"' and value = '"+act.getYear_value()+"' ";

				conn = ConnectionToDataBase.getConnection();
				pstmt = conn.prepareStatement(query);
				
				System.out.println("---------------name month----------"+query);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					
				act.setMonth_name(rs.getString("description"));
				act.setYear_name(rs.getString("year"));
				
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
			
	}
		
		//===============================year list
		
	    public ArrayList yearListImpl(MothWiseExportOfAlcohol_action act) {
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
			
			   rs = ps.executeQuery();
	      
			while (rs.next()) {

				item = new SelectItem();

				item.setValue(rs.getString("value"));
				item.setLabel(rs.getString("year"));
				
				
				//act.setYearr(rs.getString("value"));
				
				list.add(item);
				
				//System.out.println("== get year List== "+query);

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
		
		public void print_pdf(MothWiseExportOfAlcohol_action action)
		{

			String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

			String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
			String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
			JasperReport jasperReport = null;
			PreparedStatement pst = null;
			Connection con = null;
			ResultSet rs = null;
			String sql = null;
			String type=null;
			

			try {
				con = ConnectionToDataBase.getConnection();
				
 sql =    
		 
		 
" select a.* ,' AL ' as al_type , a.rs_ena_drink_sum+a.industry_sum as total_sum from ( select x.*, x.rs_al_drink+x.ena_al_drink as rs_ena_drink_sum,                            "+
" x.ena_al_other+x.rs_al_other+x.ds_and_sds_al_drink+x.absolute_al_drink+x.power_al_drink as industry_sum   from                                                                 "+
" (select (select coalesce(sum(saleoutcountrydrink_al+saleoutstatedrink_al),0) from                                                                                              "+
" distillery.productionandconsumptionofalcohol where spirit_type='ENA' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"') as ena_al_drink,                                                                                         "+
" (select coalesce(sum(saleoutcountrydrink_al+saleoutstatedrink_al),0) from                                                                                                      "+
" distillery.productionandconsumptionofalcohol where spirit_type='RS' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"')  as rs_al_drink ,                                                                                         "+
" (select coalesce(sum(saleoutcountryother_al+saleoutstateother_al),0) from                                                                                                      "+
" distillery.productionandconsumptionofalcohol where spirit_type='ENA' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"') as ena_al_other,                                                                                         "+
" (select coalesce(sum(saleoutcountryother_al+saleoutstateother_al),0) from                                                                                                      "+
" distillery.productionandconsumptionofalcohol where spirit_type='RS' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"')   as rs_al_other,                                                                                         "+
" (select coalesce(sum(saleoutcountrydrink_al+saleoutstatedrink_al+saleoutcountryother_al+saleoutstateother_al),0) from                                                                                                      "+
" distillery.productionandconsumptionofalcohol where spirit_type='DS And SDS' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"')   as ds_and_sds_al_drink,                                                                         "+
" (select coalesce(sum(saleoutcountrydrink_al+saleoutstatedrink_al+saleoutcountryother_al+saleoutstateother_al),0) from                                                                                                      "+
" distillery.productionandconsumptionofalcohol where spirit_type='Absolute Alcohol' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"')   as absolute_al_drink,                                                                     "+
" (select coalesce(sum(saleoutcountrydrink_al+saleoutstatedrink_al+saleoutcountryother_al+saleoutstateother_al),0) from                                                                                                      "+
" distillery.productionandconsumptionofalcohol where spirit_type='Power Alcohol' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"')   as power_al_drink) x)a                                                                       "+
" union                                                                                                                                                                          "+
" select c.* , ' BL ' as al_type , c.rs_ena_drink_sum+c.industry_sum as total_sum from (select y.* ,y.rs_al_drink+y.ena_al_drink as rs_ena_drink_sum,                            "+
" y.ena_al_other+y.rs_al_other+y.ds_and_sds_al_drink+y.absolute_al_drink+y.power_al_drink as industry_sum                                                                        "+
" from (select (select coalesce(sum(saleoutcountrydrink_bl+saleoutstatedrink_bl),0) from                                                                                         "+
" distillery.productionandconsumptionofalcohol where spirit_type='ENA' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"') as ena_al_drink,                                                                                         "+
" (select coalesce(sum(saleoutcountrydrink_bl+saleoutstatedrink_bl),0) from                                                                                                      "+
" distillery.productionandconsumptionofalcohol where spirit_type='RS' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"') as rs_al_drink,                                                                                         "+
" (select coalesce(sum(saleoutcountryother_bl+saleoutstateother_bl),0) from                                                                                                      "+
" distillery.productionandconsumptionofalcohol where spirit_type='ENA' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"')   as ena_al_other,                                                                                         "+
" (select coalesce(sum(saleoutcountryother_bl+saleoutstateother_bl),0) from                                                                                                      "+
" distillery.productionandconsumptionofalcohol where spirit_type='RS' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"')   as rs_al_other,                                                                                         "+
" (select coalesce(sum(saleoutcountrydrink_bl+saleoutstatedrink_bl+saleoutcountryother_bl+saleoutstateother_bl),0) from                                                                                                      "+
" distillery.productionandconsumptionofalcohol where spirit_type='DS And SDS' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"')   as ds_and_sds_al_drink,                                                                         "+
" (select coalesce(sum(saleoutcountrydrink_bl+saleoutstatedrink_bl+saleoutcountryother_bl+saleoutstateother_bl),0) from                                                                                                      "+
" distillery.productionandconsumptionofalcohol where spirit_type='Absolute Alcohol' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"')   as absolute_al_drink,                                                                     "+
" (select coalesce(sum(saleoutcountrydrink_bl+saleoutstatedrink_bl+saleoutcountryother_bl+saleoutstateother_bl),0) from                                                                                                      "+
" distillery.productionandconsumptionofalcohol where spirit_type='Power Alcohol' and year_id = '"+action.getYear_value()+"' and month_id='"+action.getMontth()+"')   as power_al_drink ) y) c order by al_type 			                                     " ;
                                                                                                                                                                                 

							                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
						
				pst = con.prepareStatement(sql);
				
				System.out.println("reportQuery jasper: ================-"+sql);
				
					
					rs = pst.executeQuery();

					System.out.println("--monthname --- "+action.getYear_name()+action.getMonth_name());
				if (rs.next()) {

					
				
					rs = pst.executeQuery();
					Map parameters = new HashMap();
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("SUBREPORT_DIR", relativePath + File.separator);
					parameters.put("image", relativePath + File.separator);		
					parameters.put("monthName",action.getMonth_name());
					parameters.put("year",action.getYear_name());
					//parameters.put("todate",Utility.convertUtilDateToSQLDate(act.getTodate()));
					JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

					
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "MonthWiseExportOfalcohol.jasper");
					

					JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
					Random rand = new Random();
					int n = rand.nextInt(250) + 1;
					JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "MonthWiseExportOfalcohol" + "-" + n + ".pdf");
					action.setPdfName("MonthWiseExportOfalcohol" + "-" + n + ".pdf");
					action.setPrintFlag(true);
				} else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
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
		
	}

	
	

