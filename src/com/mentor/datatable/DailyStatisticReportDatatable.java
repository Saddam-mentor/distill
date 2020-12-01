package com.mentor.datatable;

import java.math.BigDecimal;
import java.util.Date;

public class DailyStatisticReportDatatable {
	public String liqor_type;
	public  BigDecimal bl;
	public BigDecimal duty;
	public String getLiqor_type() {
		return liqor_type;
	}
	public void setLiqor_type(String liqor_type) {
		this.liqor_type = liqor_type;
	}
	public BigDecimal getBl() {
		return bl;
	}
	public void setBl(BigDecimal bl) {
		this.bl = bl;
	}
	
	public BigDecimal getDuty() {
		return duty;
	}
	public void setDuty(BigDecimal duty) {
		this.duty = duty;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date date;
}
