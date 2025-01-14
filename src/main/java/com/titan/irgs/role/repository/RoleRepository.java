package com.titan.irgs.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.irgs.role.domain.Role;



public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByRoleName(String roleName);
	
	Role findByRoleNameIgnoreCase(String roleName);

	Role findByRoleId(Long roleId);

}
