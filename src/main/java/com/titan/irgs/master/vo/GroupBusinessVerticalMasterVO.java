package com.titan.irgs.master.vo;

import java.util.Date;
import java.util.List;

public class GroupBusinessVerticalMasterVO {

	private Long id;
	
	private String groupBusinessVerticalName;
	
	private Long webMasterId;
	
	private List<Long> webMasterIds;
	
	private List<String> webMasterNames;
	
	private String webMasterName;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	private List<GroupBusinessVerticalVO> groupBusinessVerticalvo;
	
	
	public List<Long> getWebMasterIds() {
		return webMasterIds;
	}

	public void setWebMasterIds(List<Long> webMasterIds) {
		this.webMasterIds = webMasterIds;
	}

	public List<String> getWebMasterNames() {
		return webMasterNames;
	}

	public void setWebMasterNames(List<String> webMasterNames) {
		this.webMasterNames = webMasterNames;
	}

	
	public List<GroupBusinessVerticalVO> getGroupBusinessVerticalvo() {
		return groupBusinessVerticalvo;
	}

	public void setGroupBusinessVerticalvo(List<GroupBusinessVerticalVO> groupBusinessVerticalvo) {
		this.groupBusinessVerticalvo = groupBusinessVerticalvo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getGroupBusinessVerticalName() {
		return groupBusinessVerticalName;
	}

	public void setGroupBusinessVerticalName(String groupBusinessVerticalName) {
		this.groupBusinessVerticalName = groupBusinessVerticalName;
	}

	public Long getWebMasterId() {
		return webMasterId;
	}

	public void setWebMasterId(Long webMasterId) {
		this.webMasterId = webMasterId;
	}

	public String getWebMasterName() {
		return webMasterName;
	}

	public void setWebMasterName(String webMasterName) {
		this.webMasterName = webMasterName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
}
