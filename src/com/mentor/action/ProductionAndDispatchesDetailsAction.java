package com.mentor.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.ProductionAndDispatchesDetailsImpl;

public class ProductionAndDispatchesDetailsAction {

	private double bl_cl;
	private int cases_cl;
	private int bottles_cl;
	private double bl_fl;
	private int cases_fl;
	private int bottles_fl;
	private double bl_beer;
	private int cases_beer;
	private int bottles_beer;
	private String due;
	private String deposit;
	private Double tempDeposit;

	private double bl_cl2;
	private int cases_cl2;
	private int bottles_cl2;
	private double bl_fl2;
	private int cases_fl2;
	private int bottles_fl2;
	private double bl_beer2;
	private int cases_beer2;
	private int bottles_beer2;

	// ---------------new variables----------------

	private String hidden;
	private double bl_bwfl2a;
	private double bl_bwfl2b;
	private double bl_bwfl2c;
	private double bl_bwfl2d;

	private int cases_bwfl2a;
	private int cases_bwfl2b;
	private int cases_bwfl2c;
	private int cases_bwfl2d;

	private int bottles_bwfl2a;
	private int bottles_bwfl2b;
	private int bottles_bwfl2c;
	private int bottles_bwfl2d;

	private double bl_fl2d;
	private int cases_fl2d;
	private int bottles_fl2d;

	public double getBl_bwfl2a() {
		return bl_bwfl2a;
	}

	public void setBl_bwfl2a(double bl_bwfl2a) {
		this.bl_bwfl2a = bl_bwfl2a;
	}

	public double getBl_bwfl2b() {
		return bl_bwfl2b;
	}

	public void setBl_bwfl2b(double bl_bwfl2b) {
		this.bl_bwfl2b = bl_bwfl2b;
	}

	public double getBl_bwfl2c() {
		return bl_bwfl2c;
	}

	public void setBl_bwfl2c(double bl_bwfl2c) {
		this.bl_bwfl2c = bl_bwfl2c;
	}

	public double getBl_bwfl2d() {
		return bl_bwfl2d;
	}

	public void setBl_bwfl2d(double bl_bwfl2d) {
		this.bl_bwfl2d = bl_bwfl2d;
	}

	public int getCases_bwfl2a() {
		return cases_bwfl2a;
	}

	public void setCases_bwfl2a(int cases_bwfl2a) {
		this.cases_bwfl2a = cases_bwfl2a;
	}

	public int getCases_bwfl2b() {
		return cases_bwfl2b;
	}

	public void setCases_bwfl2b(int cases_bwfl2b) {
		this.cases_bwfl2b = cases_bwfl2b;
	}

	public int getCases_bwfl2c() {
		return cases_bwfl2c;
	}

	public void setCases_bwfl2c(int cases_bwfl2c) {
		this.cases_bwfl2c = cases_bwfl2c;
	}

	public int getCases_bwfl2d() {
		return cases_bwfl2d;
	}

	public void setCases_bwfl2d(int cases_bwfl2d) {
		this.cases_bwfl2d = cases_bwfl2d;
	}

	public int getBottles_bwfl2a() {
		return bottles_bwfl2a;
	}

	public void setBottles_bwfl2a(int bottles_bwfl2a) {
		this.bottles_bwfl2a = bottles_bwfl2a;
	}

	public int getBottles_bwfl2b() {
		return bottles_bwfl2b;
	}

	public void setBottles_bwfl2b(int bottles_bwfl2b) {
		this.bottles_bwfl2b = bottles_bwfl2b;
	}

	public int getBottles_bwfl2c() {
		return bottles_bwfl2c;
	}

	public void setBottles_bwfl2c(int bottles_bwfl2c) {
		this.bottles_bwfl2c = bottles_bwfl2c;
	}

	public int getBottles_bwfl2d() {
		return bottles_bwfl2d;
	}

	public void setBottles_bwfl2d(int bottles_bwfl2d) {
		this.bottles_bwfl2d = bottles_bwfl2d;
	}

	public double getBl_fl2d() {
		return bl_fl2d;
	}

	public void setBl_fl2d(double bl_fl2d) {
		this.bl_fl2d = bl_fl2d;
	}

	public int getCases_fl2d() {
		return cases_fl2d;
	}

	public void setCases_fl2d(int cases_fl2d) {
		this.cases_fl2d = cases_fl2d;
	}

	public int getBottles_fl2d() {
		return bottles_fl2d;
	}

	public void setBottles_fl2d(int bottles_fl2d) {
		this.bottles_fl2d = bottles_fl2d;
	}

	public String getHidden() {
		try {
			new ProductionAndDispatchesDetailsImpl().getbasicdtlSecond(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public double getBl_cl2() {
		return bl_cl2;
	}

	public void setBl_cl2(double bl_cl2) {
		this.bl_cl2 = bl_cl2;
	}

	public double getBl_fl2() {
		return bl_fl2;
	}

	public void setBl_fl2(double bl_fl2) {
		this.bl_fl2 = bl_fl2;
	}

	public double getBl_beer2() {
		return bl_beer2;
	}

	public void setBl_beer2(double bl_beer2) {
		this.bl_beer2 = bl_beer2;
	}

	public void setDue(String due) {
		this.due = due;
	}

	public Double getTempDeposit() {
		return tempDeposit;
	}

	public void setTempDeposit(Double tempDeposit) {
		this.tempDeposit = tempDeposit;
	}

	private double tempdue1;
	private double tempdue2;

	public double getBl_cl() {
		return bl_cl;
	}

	public void setBl_cl(double bl_cl) {
		this.bl_cl = bl_cl;
	}

	public double getBl_fl() {
		return bl_fl;
	}

	public void setBl_fl(double bl_fl) {
		this.bl_fl = bl_fl;
	}

	public double getBl_beer() {
		return bl_beer;
	}

	public void setBl_beer(double bl_beer) {
		this.bl_beer = bl_beer;
	}

	public double getTempdue1() {
		return tempdue1;
	}

	public void setTempdue1(double tempdue1) {
		this.tempdue1 = tempdue1;
	}

	public double getTempdue2() {
		return tempdue2;
	}

	public void setTempdue2(double tempdue2) {
		this.tempdue2 = tempdue2;
	}

	public String getDue() {
		return due;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public int getCases_cl() {
		return cases_cl;
	}

	public void setCases_cl(int cases_cl) {
		this.cases_cl = cases_cl;
	}

	public int getBottles_cl() {
		return bottles_cl;
	}

	public void setBottles_cl(int bottles_cl) {
		this.bottles_cl = bottles_cl;
	}

	public int getCases_fl() {
		return cases_fl;
	}

	public void setCases_fl(int cases_fl) {
		this.cases_fl = cases_fl;
	}

	public int getBottles_fl() {
		return bottles_fl;
	}

	public void setBottles_fl(int bottles_fl) {
		this.bottles_fl = bottles_fl;
	}

	public int getCases_beer() {
		return cases_beer;
	}

	public void setCases_beer(int cases_beer) {
		this.cases_beer = cases_beer;
	}

	public int getBottles_beer() {
		return bottles_beer;
	}

	public void setBottles_beer(int bottles_beer) {
		this.bottles_beer = bottles_beer;
	}

	public int getCases_cl2() {
		return cases_cl2;
	}

	public void setCases_cl2(int cases_cl2) {
		this.cases_cl2 = cases_cl2;
	}

	public int getBottles_cl2() {
		return bottles_cl2;
	}

	public void setBottles_cl2(int bottles_cl2) {
		this.bottles_cl2 = bottles_cl2;
	}

	public int getCases_fl2() {
		return cases_fl2;
	}

	public void setCases_fl2(int cases_fl2) {
		this.cases_fl2 = cases_fl2;
	}

	public int getBottles_fl2() {
		return bottles_fl2;
	}

	public void setBottles_fl2(int bottles_fl2) {
		this.bottles_fl2 = bottles_fl2;
	}

	public int getCases_beer2() {
		return cases_beer2;
	}

	public void setCases_beer2(int cases_beer2) {
		this.cases_beer2 = cases_beer2;
	}

	public int getBottles_beer2() {
		return bottles_beer2;
	}

	public void setBottles_beer2(int bottles_beer2) {
		this.bottles_beer2 = bottles_beer2;
	}

	private Date date_dt = new Date();

	public Date getDate_dt() {
		return date_dt;
	}

	public void setDate_dt(Date date_dt) {
		this.date_dt = date_dt;
	}

	public void tankChange(ValueChangeEvent e) {

		try {
			Date d = (Date) e.getNewValue();
			this.setDate_dt(d);

			new ProductionAndDispatchesDetailsImpl().getbasicdtlSecond(this);

		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}

}
