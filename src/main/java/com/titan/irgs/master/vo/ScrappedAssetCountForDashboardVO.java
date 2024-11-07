package com.titan.irgs.master.vo;

import java.util.List;

public class ScrappedAssetCountForDashboardVO {

	private List<Long> scrapedAssets;
	private List<String> vertical;
	private List<String> departmentName;
	
	public List<Long> getScrapedAssets() {
		return scrapedAssets;
	}
	public void setScrapedAssets(List<Long> scrapedAssets) {
		this.scrapedAssets = scrapedAssets;
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
