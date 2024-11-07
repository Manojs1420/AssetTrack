package com.titan.irgs.master.vo;

public class VendorEquipmentVO {

	private Long vendorEquipmentId;

	private Long vendorId;

	private String VendorName;
	
	private String vendorCode;

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return VendorName;
	}

	public void setVendorName(String vendorName) {
		VendorName = vendorName;
	}

	public Long getVendorEquipmentId() {
		return vendorEquipmentId;
	}

	public void setVendorEquipmentId(Long vendorEquipmentId) {
		this.vendorEquipmentId = vendorEquipmentId;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	
	

}
