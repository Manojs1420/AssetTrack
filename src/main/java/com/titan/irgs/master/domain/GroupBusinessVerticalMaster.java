package com.titan.irgs.master.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "groupBusinessVerticalMaster")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class GroupBusinessVerticalMaster {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String groupBusinessVerticalName;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "groupBusinessVerticalMaster")
	private List<GroupBusinessVertical> groupBusinessVertical;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<GroupBusinessVertical> getGroupBusinessVertical() {
		return groupBusinessVertical;
	}

	public void setGroupBusinessVertical(List<GroupBusinessVertical> groupBusinessVertical) {
		this.groupBusinessVertical = groupBusinessVertical;
	}


}
