package com.titan.irgs.master.vo;

import java.util.List;

public class ServiceRequestCountForDashboardVO {

	private List<Long> totalServices;
	private List<Long> openCount;
	private List<Long> closeCount;
	private List<String> vertical;
	private List<String> departmentName;
	
	public List<Long> getTotalServices() {
		return totalServices;
	}
	public void setTotalServices(List<Long> totalServices) {
		this.totalServices = totalServices;
	}
	public List<Long> getOpenCount() {
		return openCount;
	}
	public void setOpenCount(List<Long> openCount) {
		this.openCount = openCount;
	}
	public List<Long> getCloseCount() {
		return closeCount;
	}
	public void setCloseCount(List<Long> closeCount) {
		this.closeCount = closeCount;
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
