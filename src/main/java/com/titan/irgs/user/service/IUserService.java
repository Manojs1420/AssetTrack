package com.titan.irgs.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.titan.irgs.user.domain.User;

public interface IUserService extends UserDetailsService{

	User save(User user);

	Page<User> getAll(String username, String roleName, String businessVerticalTypeName, String firstName, String emailId, String mobileNo,String stringStoreNames,List<Long> webStoreIds, String departmentName, String department, Pageable pageable);

	User getById(Long id);

	User findByUserName(String name);

	User update(User user);

	List<Object[]> getAllForExcel();

	List<Object[]> getAllForExcel(Long id);

	Page<User> getAll(String username, String roleName, String businessVerticalTypeName, String firstName,
			String emailId, String mobileNo, List<User> storeIRST, Pageable page);

}
