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
	@Table(name = "sr_notification_email_to")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "srnotificationemailtoId", scope = Long.class)
	public class SrNotificationEmailTo {	
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long srnotificationemailtoId;
		@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
		@JoinColumn(name="srnotificationemailId")
		private SrNotificationEmail srNotificationEmail;

		@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
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

		public void setVerticalId(Long verticalId) {
			this.verticalId = verticalId;
		}

		public Long getSrnotificationemailtoId() {
			return srnotificationemailtoId;
		}

		public void setSrnotificationemailtoId(Long srnotificationemailtoId) {
			this.srnotificationemailtoId = srnotificationemailtoId;
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

	
		
}
