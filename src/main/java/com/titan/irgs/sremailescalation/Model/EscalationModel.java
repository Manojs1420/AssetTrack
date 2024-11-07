package com.titan.irgs.sremailescalation.Model;

public class EscalationModel {

		private Long escalationId;

		private Long sremailescalationId;

		private Long webRoleId;
		
		private String RoleName;
		
private Long verticalId;


		

		public Long getVerticalId() {
			return verticalId;
		}

		public void setVerticalId(Long verticalId) {
			this.verticalId = verticalId;
		}



		private String verticalName;
		
		public String getVerticalName() {
			return verticalName;
		}

		public void setVerticalName(String verticalName) {
			this.verticalName = verticalName;
		}
		public Long getWebRoleId() {
			return webRoleId;
		}

		public void setWebRoleId(Long webRoleId) {
			this.webRoleId = webRoleId;
		}

		public Long getSremailescalationId() {
			return sremailescalationId;
		}

		public void setSremailescalationId(Long sremailescalationId) {
			this.sremailescalationId = sremailescalationId;
		}
		
		public String getRoleName() {
			return RoleName;
		}

		public void setRoleName(String roleName) {
			RoleName = roleName;
		}

		public Long getEscalationId() {
			return escalationId;
		}

		public void setEscalationId(Long escalationId) {
			this.escalationId = escalationId;
		}

		
		
	}
