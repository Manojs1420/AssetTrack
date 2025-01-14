package com.titan.irgs.sremailescalation.Domain;


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
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.webRole.domain.WebRole;

	@Entity
	@Table(name = "sr_notification_email_cc")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "srnotificationemailccId", scope = Long.class)
	public class SrNotificationEmailCc  {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long srnotificationemailccId;
	
		@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
		@JoinColumn(name="srnotificationemailId")
		private SrNotificationEmail srNotificationEmail;

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

		public Long getSrnotificationemailccId() {
			return srnotificationemailccId;
		}

		public void setSrnotificationemailccId(Long srnotificationemailccId) {
			this.srnotificationemailccId = srnotificationemailccId;
		}

		public SrNotificationEmail getSrNotificationEmail() {
			return srNotificationEmail;
		}

		public void setSrNotificationEmail(SrNotificationEmail srNotificationEmail) {
			this.srNotificationEmail = srNotificationEmail;
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
