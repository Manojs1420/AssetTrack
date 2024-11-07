package com.titan.irgs.sremailescalation.Domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "sr_notification_email")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "srnotificationemailId", scope = Long.class)
public class SrNotificationEmail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long srnotificationemailId;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdOn;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "srNotificationEmail")
	private List<SrNotificationEmailTo> srNotificationEmailTo;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "srNotificationEmail")
	private List<SrNotificationEmailCc> srNotificationEmailCc;
	
private Long verticalId;
	private String verticalName;
	private String activityName;

	public SrNotificationEmail() {
		// TODO Auto-generated constructor stub
	}

	public SrNotificationEmail(String activityName2, Long verticalId2) {
		// TODO Auto-generated constructor stub
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Long getSrnotificationemailId() {
		return srnotificationemailId;
	}

	public void setSrnotificationemailId(Long srnotificationemailId) {
		this.srnotificationemailId = srnotificationemailId;
	}

	public List<SrNotificationEmailTo> getSrNotificationEmailTo() {
		return srNotificationEmailTo;
	}

	public void setSrNotificationEmailTo(List<SrNotificationEmailTo> srNotificationEmailTo) {
		this.srNotificationEmailTo = srNotificationEmailTo;
	}

	public List<SrNotificationEmailCc> getSrNotificationEmailCc() {
		return srNotificationEmailCc;
	}

	public void setSrNotificationEmailCc(List<SrNotificationEmailCc> srNotificationEmailCc) {
		this.srNotificationEmailCc = srNotificationEmailCc;
	}




	public Long getVerticalId() {
		return verticalId;
	}

	public void setVerticalId(Long verticalId) {
		this.verticalId = verticalId;
	}

	public String getVerticalName() {
		return verticalName;
	}
	public void setVerticalName(String verticalName) {
		this.verticalName = verticalName;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
