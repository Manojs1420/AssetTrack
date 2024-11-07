package com.titan.irgs.sremailescalation.Model;

public class AmcNotificationEmailToModel {

		private Long amcnotificationemailtoId;

		private Long amcnotificationId;

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

		

		public Long getAmcnotificationemailtoId() {
			return amcnotificationemailtoId;
		}

		public void setAmcnotificationemailtoId(Long amcnotificationemailtoId) {
			this.amcnotificationemailtoId = amcnotificationemailtoId;
		}

		public Long getAmcnotificationId() {
			return amcnotificationId;
		}

		public void setAmcnotificationId(Long amcnotificationId) {
			this.amcnotificationId = amcnotificationId;
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
