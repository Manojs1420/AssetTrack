package com.titan.irgs.user.domain;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.titan.irgs.master.domain.GroupBusinessVerticalMaster;
import com.titan.irgs.master.domain.OwnerType;
import com.titan.irgs.master.domain.StoreServiceType;
import com.titan.irgs.role.domain.RoleWiseDepartments;
import com.titan.irgs.webRole.domain.WebRole;

/**
 * 
 * @author hari.k
 *
 */
@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class User extends Metadata implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	 private String firstName;
	    
	 private String lastName;

	private Boolean accountNonExpired = false;

	private Boolean accountNonLocked = true;

	private Boolean credentialsNonExpired = true;

	private Boolean isEnabled = true;
	
	private String email;
	
	private String address;
	
	private String discription;
	
	private String phoneNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	
	private Long loginCount;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="webRoleId",nullable=true)
	private WebRole role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="roleWiseDepartmentsId",nullable=true)
	private RoleWiseDepartments roleWiseDepartments;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="groupBusinessMasterId",nullable=true)
	private GroupBusinessVerticalMaster groupBusinessVerticalMaster;
	
	private String isgroupBusiness;
	
	private String inventoryUser;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ownerTypeId", nullable = true)
	private OwnerType ownerType;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "storeServiceTypeId", nullable = true)
	private StoreServiceType storeServiceType;
	
	public OwnerType getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(OwnerType ownerType) {
		this.ownerType = ownerType;
	}

	public StoreServiceType getStoreServiceType() {
		return storeServiceType;
	}

	public void setStoreServiceType(StoreServiceType storeServiceType) {
		this.storeServiceType = storeServiceType;
	}

	public String getInventoryUser() {
		return inventoryUser;
	}

	public void setInventoryUser(String inventoryUser) {
		this.inventoryUser = inventoryUser;
	}

	public RoleWiseDepartments getRoleWiseDepartments() {
		return roleWiseDepartments;
	}

	public void setRoleWiseDepartments(RoleWiseDepartments roleWiseDepartments) {
		this.roleWiseDepartments = roleWiseDepartments;
	}

	public String getIsgroupBusiness() {
		return isgroupBusiness;
	}

	public void setIsgroupBusiness(String isgroupBusiness) {
		this.isgroupBusiness = isgroupBusiness;
	}

	private Long repotingUserId;

	
	


	public GroupBusinessVerticalMaster getGroupBusinessVerticalMaster() {
		return groupBusinessVerticalMaster;
	}

	public void setGroupBusinessVerticalMaster(GroupBusinessVerticalMaster groupBusinessVerticalMaster) {
		this.groupBusinessVerticalMaster = groupBusinessVerticalMaster;
	}

	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);
	

	
	
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	

	
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Long getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}

	public WebRole getRole() {
		return role;
	}

	public void setRole(WebRole role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getRepotingUserId() {
		return repotingUserId;
	}

	public void setRepotingUserId(Long repotingUserId) {
		this.repotingUserId = repotingUserId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
    
    
}

