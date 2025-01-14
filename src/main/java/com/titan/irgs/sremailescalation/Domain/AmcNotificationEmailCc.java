package com.titan.irgs.sremailescalation.Domain;



	import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.webRole.domain.WebRole;

	@Entity
	@Table(name = "amc_notification_email_cc")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "srnotificationemailccId", scope = Long.class)
	public class AmcNotificationEmailCc implements Serializable {
		
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long amcnotificationemailccId;
	
		@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
		@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
		@JoinColumn(name="amcnotificationId")
		private AmcNotification amcNotification;

		@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
		@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
		@JoinColumn(name="webRoleId",nullable=true)
		private WebRole webRole;
		
		private String RoleName;
		private String verticalName;
		private Long verticalId;
		
		
		

		
		public String getVerticalName() {
			return verticalName;
		}

		public void setVerticalName(String verticalName) {
			this.verticalName = verticalName;
		}

		public Long getVerticalId() {
			return verticalId;
		}



		
		
		public Long getAmcnotificationemailccId() {
			return amcnotificationemailccId;
		}

		public void setAmcnotificationemailccId(Long amcnotificationemailccId) {
			this.amcnotificationemailccId = amcnotificationemailccId;
		}

		public AmcNotification getAmcNotification() {
			return amcNotification;
		}

		public void setAmcNotification(AmcNotification amcNotification) {
			this.amcNotification = amcNotification;
		}

		public WebRole getWebRole() {
			return webRole;
		}

		public void setWebRole(WebRole webRole) {
			this.webRole = webRole;
		}

		public String getRoleName() {
			return RoleName;
		}

		public void setRoleName(String roleName) {
			RoleName = roleName;
		}

		public void setVerticalId(Long webMasterId) {
			// TODO Auto-generated method stub
			
		}

		}
