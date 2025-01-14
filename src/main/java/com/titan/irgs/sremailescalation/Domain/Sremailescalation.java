package com.titan.irgs.sremailescalation.Domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.webMaster.domain.WebMaster;

@Entity
@Table(name = "sremailescalation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "sremailescalationId", scope = Long.class)
public class Sremailescalation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sremailescalationId;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdOn;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sremailescalation")
	private List<Escalation> escalations;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sremailescalation")
	private List<Escalation1> escalations1;

	private String escalationLevel;

	private long days;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "verticalId", nullable = true)
	private WebMaster webMaster;

	private String verticalName;

	public String getVerticalName() {
		return verticalName;
	}

	public void setVerticalName(String verticalName) {
		this.verticalName = verticalName;
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

	public List<Escalation> getEscalations() {
		return escalations;
	}

	public void setEscalations(List<Escalation> escalations) {
		this.escalations = escalations;
	}

	public List<Escalation1> getEscalations1() {
		return escalations1;
	}

	public void setEscalations1(List<Escalation1> escalations1) {
		this.escalations1 = escalations1;
	}

	public WebMaster getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(WebMaster webMaster) {
		this.webMaster = webMaster;
	}

}
