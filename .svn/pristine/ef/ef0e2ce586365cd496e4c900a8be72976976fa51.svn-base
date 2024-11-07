package com.titan.irgs.role.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.role.domain.Role;
import com.titan.irgs.role.repository.RoleRepository;
import com.titan.irgs.role.service.IRoleService;

@Service
public class RoleService implements IRoleService{
	
	
	private static final Logger logger= LoggerFactory.getLogger(RoleService.class);
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Role save(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	@Override
	public List<Role> getAll() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	@Override
	public Role getById(Long id) {
		// TODO Auto-generated method stub
		return roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The id is not found"));
	}

	@Override
	public Role findByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findByRoleName(roleName);
	}

	@Override
	public Role findByRoleNameIgnoreCase(String string) {
		// TODO Auto-generated method stub
		return roleRepository.findByRoleName(string);
	}
	
	
	

	
	

}
