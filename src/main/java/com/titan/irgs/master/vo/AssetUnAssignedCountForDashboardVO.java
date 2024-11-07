package com.titan.irgs.master.vo;

import java.util.List;

public class AssetUnAssignedCountForDashboardVO {

	private List<Long> unAssignedAssets;
	private List<String> vertical;
	private List<String> departmentName;
	
	public List<Long> getUnAssignedAssets() {
		return unAssignedAssets;
	}
	public void setUnAssignedAssets(List<Long> unAssignedAssets) {
		this.unAssignedAssets = unAssignedAssets;
	}
	public List<String> getVertical() {
		return vertical;
	}
	public void setVertical(List<String> vertical) {
		this.vertical = vertical;
	}
	public List<String> getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(List<String> departmentName) {
		this.departmentName = departmentName;
	}
	
	
}
