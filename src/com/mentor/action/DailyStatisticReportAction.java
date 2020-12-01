package com.mentor.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.mentor.impl.DailyStatisticReportImpl;

public class DailyStatisticReportAction {
	
	
	public ArrayList dailyStaistic=new ArrayList();
	public ArrayList dispatchToDistrict=new ArrayList();
	public ArrayList dispatchFromDistrict=new ArrayList();
	public Date date=new Date();
	public BigDecimal totalDeposit;
	
	
	public BigDecimal getTotalDeposit() {
		try{
			this.totalDeposit=new DailyStatisticReportImpl().getTotalDeposit();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return totalDeposit;
	}
	public void setTotalDeposit(BigDecimal totalDeposit) {
		this.totalDeposit = totalDeposit;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public ArrayList getDailyStaistic() {
		
try{
		this.dailyStaistic=new DailyStatisticReportImpl().getDailyStatistic(this);	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return dailyStaistic;
	}
	public void setDailyStaistic(ArrayList dailyStaistic) {
		
		
		this.dailyStaistic = dailyStaistic;
	}
	public ArrayList getDispatchToDistrict() {
		
		try{
			this.dispatchToDistrict=new DailyStatisticReportImpl().getDailyStatisticDispatchToDistrict(this);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return dispatchToDistrict;
	}
	public void setDispatchToDistrict(ArrayList dispatchToDistrict) {
		this.dispatchToDistrict = dispatchToDistrict;
	}
	public ArrayList getDispatchFromDistrict() {
		
try{
	this.dispatchFromDistrict=	new DailyStatisticReportImpl().getDailyStatisticDispatchFromDistrict(this);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return dispatchFromDistrict;
	}
	public void setDispatchFromDistrict(ArrayList dispatchFromDistrict) {
		this.dispatchFromDistrict = dispatchFromDistrict;
	}
	
	
	
	
	

}
