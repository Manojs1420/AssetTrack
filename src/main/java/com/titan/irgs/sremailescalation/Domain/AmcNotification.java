package com.titan.irgs.sremailescalation.Domain;


import java.io.Serializable;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "amc_notification")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "srnotificationemailId", scope = Long.class)
public class AmcNotification implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long amcnotificationId;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdOn;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "amcNotification")
	private List<AmcNotificationEmailTo> amcNotificationEmailTo;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "amcNotification")
	private List<AmcNotificationEmailCc> amcNotificationEmailCc;
	
private Long verticalId;
	
	private String verticalName;
	private String activityName;

	public AmcNotification() {
		// TODO Auto-generated constructor stub
	}

	public AmcNotification(String activityName2, Long verticalId2) {
		// TODO Auto-generated constructor stub
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	
		public Long getAmcnotificationId() {
		return amcnotificationId;
	}

	public void setAmcnotificationId(Long amcnotificationId) {
		this.amcnotificationId = amcnotificationId;
	}

	public List<AmcNotificationEmailTo> getAmcNotificationEmailTo() {
		return amcNotificationEmailTo;
	}

	public void setAmcNotificationEmailTo(List<AmcNotificationEmailTo> amcNotificationEmailTo) {
		this.amcNotificationEmailTo = amcNotificationEmailTo;
	}

	public List<AmcNotificationEmailCc> getAmcNotificationEmailCc() {
		return amcNotificationEmailCc;
	}

	public void setAmcNotificationEmailCc(List<AmcNotificationEmailCc> amcNotificationEmailCc) {
		this.amcNotificationEmailCc = amcNotificationEmailCc;
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
