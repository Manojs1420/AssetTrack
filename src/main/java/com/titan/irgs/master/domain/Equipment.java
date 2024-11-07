package com.titan.irgs.master.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.webMaster.domain.WebMaster;

/**
 * This is Equipment domain class which map the 'equipment' table
 * @author 
 *
 */
@Entity
@Table(name = "equipment")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "equipmentId", scope = Long.class)
public class Equipment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long equipmentId;
	
	@Column(unique = true, nullable = true)
	private String equipmentCode;
	
	@Column(unique = false, nullable = true)
	private String equipmentName;
	
	private String equipmentCost;
	
	private long createdBy;
	
	private long updatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "equipmentTypeId", nullable = true)
	private EquipmentType equipmentType;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "WebMasterId", nullable = true)
	private WebMaster webMaster;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "equipment")
	private List<EquipmentWiseDepartments> equipmentWiseDepartments;
	
	/*@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "vendorId", nullable = true)
	private Vendor vendor;*/
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "equipment")
    private List<EquipmentDetail> equipmentDetailList = new ArrayList<EquipmentDetail>(0);
	*/
	//@Column(nullable = false)
	
	
	public Long getEquipmentId() {
		return equipmentId;
	}

	public List<EquipmentWiseDepartments> getEquipmentWiseDepartments() {
		return equipmentWiseDepartments;
	}

	public void setEquipmentWiseDepartments(List<EquipmentWiseDepartments> equipmentWiseDepartments) {
		this.equipmentWiseDepartments = equipmentWiseDepartments;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}


	public String getEquipmentCost() {
		return equipmentCost;
	}

	public void setEquipmentCost(String equipmentCost) {
		this.equipmentCost = equipmentCost;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
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

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}

	public WebMaster getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(WebMaster webMaster) {
		this.webMaster = webMaster;
	}

	

	

	
	
	
    
}
