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
	public class SRFileUploadMaintainance {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long SRFileUploadId;
		
		
		@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
		@JoinColumn(name = "maintainanceId",nullable = false)
		private Maintainance maintainance;
		
		private String srFileploadPath;
		
		private String endingPath;
		
		private Long spareQuantity;
		
		private String spareCharges;
		
		private String documentDescription;
		
		private String status;
		
		private Boolean approved;
	 

		public Long getSRFileUploadId() {
			return SRFileUploadId;
		}

		public void setSRFileUploadId(Long sRFileUploadId) {
			SRFileUploadId = sRFileUploadId;
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

		public Long getSpareQuantity() {
			return spareQuantity;
		}

		public void setSpareQuantity(Long spareQuantity) {
			this.spareQuantity = spareQuantity;
		}

		public String getSpareCharges() {
			return spareCharges;
		}

		public void setSpareCharges(String spareCharges) {
			this.spareCharges = spareCharges;
		}

		public String getDocumentDescription() {
			return documentDescription;
		}

		public void setDocumentDescription(String documentDescription) {
			this.documentDescription = documentDescription;
		}

		public String getEndingPath() {
			return endingPath;
		}

		public void setEndingPath(String endingPath) {
			this.endingPath = endingPath;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Boolean getApproved() {
			return approved;
		}

		public void setApproved(Boolean approved) {
			this.approved = approved;
		}
		
		
		
		
		

	}
