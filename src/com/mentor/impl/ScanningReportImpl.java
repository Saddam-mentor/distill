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

import com.mentor.action.ScanningReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class ScanningReportImpl {
	
	
	public boolean printReport(ScanningReportAction action)
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		JasperFillManager fillmanager=null;
		JasperExportManager export=null;
		JasperPrint print=null;
		boolean flag=false;
				
	String path=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH+File.separator+File.separator+"ExciseUp"+File.separator+"Scanning"+File.separator;
	
	
	String sql=
			
		"	select a.vch_challan_id,vch_rajaswa_head,g6_head,head_type,amount,vch_trn_status,dat_created_date,vch_depositor_name           "+
		"	from licence.mst_challan_master a,licence.challan_head_details b                                                               "+
		"	where a.vch_challan_id=b.vch_challan_id and vch_rajaswa_head='003900800060000' and dat_created_date between ? and ?  and vch_trn_status='SUCCESS'          "+
		"	group by a.vch_challan_id,vch_rajaswa_head,g6_head,head_type,amount,vch_trn_status,dat_created_date,vch_depositor_name         "+
		"	order by vch_rajaswa_head,g6_head,head_type,dat_created_date                                                                   ";
			                                                                                                                               
			
		try{
			
			conn=ConnectionToDataBase.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setDate(1, Utility.convertUtilDateToSQLDate(action.getFromDate()));
			pstmt.setDate(2, Utility.convertUtilDateToSQLDate(action.getToDate()));
			rs=pstmt.executeQuery();
			if(rs.next())
			{
				rs=pstmt.executeQuery();
				
				HashMap map=new HashMap();
				map.put("image",path+"jasper"+File.separator);
				JRResultSetDataSource jr=new JRResultSetDataSource(rs);
				
				print=	JasperFillManager.fillReport(path+"jasper"+File.separator+"scanning_fee_report.jasper", map, jr);
				
				Random r=new Random();
				int no=r.nextInt()+50;
				String exportPath=path+"pdf"+File.separator+"ScanningReport"+no+".pdf";
				JasperExportManager.exportReportToPdfFile(print,exportPath);
				
				action.setUrl(exportPath);
				action.setPrintFlag(true);
				
				
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Report Print Successful", "Report Print Successful"));	
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No Data Found", "No Data Found"));
				action.setPrintFlag(false);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return flag;
	}

}
