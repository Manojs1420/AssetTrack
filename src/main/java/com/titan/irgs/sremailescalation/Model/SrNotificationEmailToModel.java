package com.titan.irgs.sremailescalation.Model;

public class SrNotificationEmailToModel {

		private Long srnotificationemailtoId;

		private Long srnotificationemailId;

		private Long webRoleId;
		
		private String RoleName;
		private Long verticalId;
		private String verticalName;
		
		
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

		public Long getSrnotificationemailtoId() {
			return srnotificationemailtoId;
		}

		public void setSrnotificationemailtoId(Long srnotificationemailtoId) {
			this.srnotificationemailtoId = srnotificationemailtoId;
		}

		public Long getSrnotificationemailId() {
			return srnotificationemailId;
		}

		public void setSrnotificationemailId(Long srnotificationemailId) {
			this.srnotificationemailId = srnotificationemailId;
		}

		public Long getWebRoleId() {
			return webRoleId;
		}

		public void setWebRoleId(Long webRoleId) {
			this.webRoleId = webRoleId;
		}

		
		public String getRoleName() {
			return RoleName;
		}

		public void setRoleName(String roleName) {
			RoleName = roleName;
		}

		
	}
