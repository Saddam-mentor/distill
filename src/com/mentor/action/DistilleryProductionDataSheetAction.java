package com.mentor.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.faces.event.ValueChangeEvent;
 
import com.mentor.impl.DistilleryProductionDataSheetImpl;

public class DistilleryProductionDataSheetAction {

	private BigDecimal molasses_consumed_reserved;
	private BigDecimal molasses_consumed_unreserved; 
	private BigDecimal spirit_produced_by_molasses;
	private BigDecimal spirit_produced_by_grains;
	private BigDecimal spirit_purchased_up;
	private BigDecimal spirit_purchased_export;
	private BigDecimal spirit_sold_up;
	private BigDecimal spirit_sold_export;
	private BigDecimal spirit_used_cl_production;
	private BigDecimal spirit_used_fl_production;
	private BigDecimal produced_bl_for_cl;
	private BigDecimal produced_bl_for_fl;
	private String distilleryId;
	private ArrayList distilleryList = new ArrayList();
	DistilleryProductionDataSheetImpl impl = new DistilleryProductionDataSheetImpl();

	public void distChangeLisnr(ValueChangeEvent vce) {
		try {
			Object obj = vce.getNewValue();
			String s = (String) obj;
			//System.out.println("id=" + s);
			impl.getBL(this, s);
			impl.getMolassesAndSpiritConsumed(this,s);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public BigDecimal getMolasses_consumed_reserved() {
		return molasses_consumed_reserved;
	}

	public void setMolasses_consumed_reserved(
			BigDecimal molasses_consumed_reserved) {
		this.molasses_consumed_reserved = molasses_consumed_reserved;
	}

	public BigDecimal getMolasses_consumed_unreserved() {
		return molasses_consumed_unreserved;
	}

	public void setMolasses_consumed_unreserved(
			BigDecimal molasses_consumed_unreserved) {
		this.molasses_consumed_unreserved = molasses_consumed_unreserved;
	}

	public BigDecimal getSpirit_produced_by_molasses() {
		return spirit_produced_by_molasses;
	}

	public void setSpirit_produced_by_molasses(
			BigDecimal spirit_produced_by_molasses) {
		this.spirit_produced_by_molasses = spirit_produced_by_molasses;
	}

	public BigDecimal getSpirit_produced_by_grains() {
		return spirit_produced_by_grains;
	}

	public void setSpirit_produced_by_grains(
			BigDecimal spirit_produced_by_grains) {
		this.spirit_produced_by_grains = spirit_produced_by_grains;
	}

	public BigDecimal getSpirit_purchased_up() {
		return spirit_purchased_up;
	}

	public void setSpirit_purchased_up(BigDecimal spirit_purchased_up) {
		this.spirit_purchased_up = spirit_purchased_up;
	}

	public BigDecimal getSpirit_purchased_export() {
		return spirit_purchased_export;
	}

	public void setSpirit_purchased_export(BigDecimal spirit_purchased_export) {
		this.spirit_purchased_export = spirit_purchased_export;
	}

	public BigDecimal getSpirit_sold_up() {
		return spirit_sold_up;
	}

	public void setSpirit_sold_up(BigDecimal spirit_sold_up) {
		this.spirit_sold_up = spirit_sold_up;
	}

	public BigDecimal getSpirit_sold_export() {
		return spirit_sold_export;
	}

	public void setSpirit_sold_export(BigDecimal spirit_sold_export) {
		this.spirit_sold_export = spirit_sold_export;
	}

	public BigDecimal getSpirit_used_cl_production() {
		return spirit_used_cl_production;
	}

	public void setSpirit_used_cl_production(
			BigDecimal spirit_used_cl_production) {
		this.spirit_used_cl_production = spirit_used_cl_production;
	}

	public BigDecimal getSpirit_used_fl_production() {
		return spirit_used_fl_production;
	}

	public void setSpirit_used_fl_production(
			BigDecimal spirit_used_fl_production) {
		this.spirit_used_fl_production = spirit_used_fl_production;
	}

	public BigDecimal getProduced_bl_for_cl() {
		return produced_bl_for_cl;
	}

	public void setProduced_bl_for_cl(BigDecimal produced_bl_for_cl) {
		this.produced_bl_for_cl = produced_bl_for_cl;
	}

	public BigDecimal getProduced_bl_for_fl() {
		return produced_bl_for_fl;
	}

	public void setProduced_bl_for_fl(BigDecimal produced_bl_for_fl) {
		this.produced_bl_for_fl = produced_bl_for_fl;
	}

	public String getDistilleryId() {
		return distilleryId;
	}

	public void setDistilleryId(String distilleryId) {
		this.distilleryId = distilleryId;
	}

	public ArrayList getDistilleryList() {
		distilleryList = impl.getDistillery();
		return distilleryList;
	}

	public void setDistilleryList(ArrayList distilleryList) {
		this.distilleryList = distilleryList;
	}

}
