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
public class MaintainanceInvoice {

		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long maintainanceInvoiceId;
		
		
		@ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
		@JoinColumn(name = "maintainanceId",nullable = true)
		private Maintainance maintainance;
		
		private String invoicePath;
		
		private String endingPath;
		
		private String invoiceNumber;
		
		private String invoiceType;
		
		private String charges;
		
		private String documentDescription;
		
		

		
		



		public Long getMaintainanceInvoiceId() {
			return maintainanceInvoiceId;
		}

		public void setMaintainanceInvoiceId(Long maintainanceInvoiceId) {
			this.maintainanceInvoiceId = maintainanceInvoiceId;
		}

		public Maintainance getMaintainance() {
			return maintainance;
		}

		public void setMaintainance(Maintainance maintainance) {
			this.maintainance = maintainance;
		}

		public String getInvoicePath() {
			return invoicePath;
		}

		public void setInvoicePath(String invoicePath) {
			this.invoicePath = invoicePath;
		}


		

		

		public String getInvoiceNumber() {
			return invoiceNumber;
		}

		public void setInvoiceNumber(String invoiceNumber) {
			this.invoiceNumber = invoiceNumber;
		}

		public String getInvoiceType() {
			return invoiceType;
		}

		public void setInvoiceType(String invoiceType) {
			this.invoiceType = invoiceType;
		}

		public String getCharges() {
			return charges;
		}

		public void setCharges(String charges) {
			this.charges = charges;
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
		
		
		
		
		



	}

