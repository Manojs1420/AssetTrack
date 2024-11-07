package com.titan.irgs.inventory.domain;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class MaintainanceUpload {
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long maintainanceUpload;
	
	
	@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "maintainanceId",nullable = false)
	private Maintainance maintainance;
	
	private String srFileploadPath;
	
	private String endingPath;
	
	private String documentDescription;

	public Long getMaintainanceUpload() {
		return maintainanceUpload;
	}

	public void setMaintainanceUpload(Long maintainanceUpload) {
		this.maintainanceUpload = maintainanceUpload;
	}

	public Maintainance getMaintainance() {
		return maintainance;
	}

	public void setMaintainance(Maintainance maintainance) {
		this.maintainance = maintainance;
	}

	public String getSrFileploadPath() {
		return srFileploadPath;
	}

	public void setSrFileploadPath(String srFileploadPath) {
		this.srFileploadPath = srFileploadPath;
	}

	public String getEndingPath() {
		return endingPath;
	}

	public void setEndingPath(String endingPath) {
		this.endingPath = endingPath;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}
 

	

	}
