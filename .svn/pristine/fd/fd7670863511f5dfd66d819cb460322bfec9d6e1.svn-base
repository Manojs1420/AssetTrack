package com.titan.irgs.user.model;

import java.util.List;

public class JwtResponse {
	

	
	    private String token;
	    private String type = "Bearer";
	  
	    private Long webRoleId;
	    private String webRolName;
	    private Long userId;
	    private String userName;
	    private  List<Long> assetWbMasterIds;
		   
		private List<String> assetwebMasterName;
		
		private Long departmentId;
		private String departmentName;
		 
	 
	public JwtResponse(String accessToken, Long userId,  Long webRoleId,String userName,String webRolName, List<Long> assetWbMasterIds,List<String> assetwebMasterName) {
        this.token = accessToken;
        this.userId=userId;
        this.webRoleId=webRoleId;
        this.userName=userName;
        this.webRolName=webRolName;
        this.assetWbMasterIds=assetWbMasterIds;
        this.assetwebMasterName=assetwebMasterName;
    }


	public JwtResponse(String accessToken, Long userId,  Long webRoleId,String userName,String webRolName) {
		this.token = accessToken;
        this.userId=userId;
        this.webRoleId=webRoleId;
        this.userName=userName;
        this.webRolName=webRolName;
	}

		public JwtResponse(String jwt, Long id, Long webRoleId2, String username2, String roleName, String departmentName,
			Long departmentId) {
			this.token = jwt;
	        this.userId=id;
	        this.webRoleId=webRoleId2;
	        this.userName=username2;
	        this.webRolName=roleName;
	        this.departmentName=departmentName;
	        this.departmentId=departmentId;
	}


		public Long getDepartmentId() {
			return departmentId;
		}


		public void setDepartmentId(Long departmentId) {
			this.departmentId = departmentId;
		}


		public String getDepartmentName() {
			return departmentName;
		}


		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}


		public List<Long> getAssetWbMasterIds() {
		return assetWbMasterIds;
	}


	public void setAssetWbMasterIds(List<Long> assetWbMasterIds) {
		this.assetWbMasterIds = assetWbMasterIds;
	}


	public List<String> getAssetwebMasterName() {
		return assetwebMasterName;
	}


	public void setAssetwebMasterName(List<String> assetwebMasterName) {
		this.assetwebMasterName = assetwebMasterName;
	}


		public String getToken() {
			return token;
		}


		public void setToken(String token) {
			this.token = token;
		}


		public String getType() {
			return type;
		}


		public void setType(String type) {
			this.type = type;
		}


		public Long getWebRoleId() {
			return webRoleId;
		}


		public void setWebRoleId(Long webRoleId) {
			this.webRoleId = webRoleId;
		}


		public String getWebRolName() {
			return webRolName;
		}


		public void setWebRolName(String webRolName) {
			this.webRolName = webRolName;
		}


		public Long getUserId() {
			return userId;
		}


		public void setUserId(Long userId) {
			this.userId = userId;
		}


		public String getUserName() {
			return userName;
		}


		public void setUserName(String userName) {
			this.userName = userName;
		}
	 
	   


	    
}
