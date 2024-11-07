package com.titan.irgs.sremailescalation.Model;

import java.util.Date;
import java.util.List;

public class SremailescalationModel {
	private Long sremailescalationId;
	private String escalationLevel;
	private List<EscalationModel> escalation;
	private List<EscalationModel1> escalation1;
	private Long days;	
	private Long verticalId;
	private String verticalName;

	private Date createdOn;
	
	public Long getVerticalId() {
		return verticalId;
	}
	public void setVerticalId(Long verticalId) {
		this.verticalId = verticalId;
	}
	
	public String getEscalationLevel() {
		return escalationLevel;
	}
	public void setEscalationLevel(String escalationLevel) {
		this.escalationLevel = escalationLevel;
	}
	
	
	public Long getDays() {
		return days;
	}
	public void setDays(Long days) {
		this.days = days;
	}

	public Long getSremailescalationId() {
		return sremailescalationId;
	}
	public void setSremailescalationId(Long sremailescalationId) {
		this.sremailescalationId = sremailescalationId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public List<EscalationModel> getEscalation() {
		return escalation;
	}
	public void setEscalation(List<EscalationModel> escalation) {
		this.escalation = escalation;
	}
	public List<EscalationModel1> getEscalation1() {
		return escalation1;
	}
	public void setEscalation1(List<EscalationModel1> escalation1) {
		this.escalation1 = escalation1;
	}
	public String getVerticalName() {
		return verticalName;
	}
	public void setVerticalName(String verticalName) {
		this.verticalName = verticalName;
	}

}

