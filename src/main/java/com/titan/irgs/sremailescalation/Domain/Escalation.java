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
	@Table(name = "escalation") 
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "escalationId", scope = Long.class)
	public class Escalation implements Serializable {
		
		private static final long serialVersionUID = 1L;

		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long escalationId;
	
		@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
		@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.MERGE)
		@JoinColumn(name="sremailescalationId")
		private Sremailescalation sremailescalation;

		@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
		@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.MERGE)
		@JoinColumn(name="webRoleId",nullable=true)
		private WebRole webRole;
		
		private String RoleName;
		
		private String verticalName;
		private Long verticalId;

		


		public Long getVerticalId() {
			return verticalId;
		}

		public void setVerticalId(Long verticalId) {
			this.verticalId = verticalId;
		}

		public String getVerticalName() {
			return verticalName;
		}

		public void setVerticalName(String verticalName) {
			this.verticalName = verticalName;
		}

		public Long getEscalationId() {
			return escalationId;
		}

		public void setEscalationId(Long escalationId) {
			this.escalationId = escalationId;
		}
		public Sremailescalation getSremailescalation() {
			return sremailescalation;
		}

		public void setSremailescalation(Sremailescalation sremailescalation) {
			this.sremailescalation = sremailescalation;
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
