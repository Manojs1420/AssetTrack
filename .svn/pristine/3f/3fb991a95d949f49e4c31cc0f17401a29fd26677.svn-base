package com.titan.irgs.inventory.controller;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.titan.irgs.inventory.domain.Inventory;

@Entity
public class InventoryQRCodeUpload {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long inventoryQRCodeUpload;
	
	
	@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "inventoryId",nullable = false)
	private Inventory inventory;
	
	private String QrFilePath;
	
	private String endingPath;

	public Long getInventoryQRCodeUpload() {
		return inventoryQRCodeUpload;
	}

	public void setInventoryQRCodeUpload(Long inventoryQRCodeUpload) {
		this.inventoryQRCodeUpload = inventoryQRCodeUpload;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public String getQrFilePath() {
		return QrFilePath;
	}

	public void setQrFilePath(String qrFilePath) {
		QrFilePath = qrFilePath;
	}

	public String getEndingPath() {
		return endingPath;
	}

	public void setEndingPath(String endingPath) {
		this.endingPath = endingPath;
	}
	
	
}
