package com.titan.irgs.master.vo;

import java.util.List;

public class AssetCountForDashboardVO {

	private List<Long> totalAssets;
	
	private List<String> vertical;
	private List<String> departmentName;

	public List<String> getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(List<String> departmentName) {
		this.departmentName = departmentName;
	}
	public List<Long> getTotalAssets() {
		return totalAssets;
	}
	public void setTotalAssets(List<Long> totalAssets) {
		this.totalAssets = totalAssets;
	}
	
	
	public List<String> getVertical() {
		return vertical;
	}
	public void setVertical(List<String> vertical) {
		this.vertical = vertical;
	}
	
}
