package com.titan.irgs.user.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.titan.irgs.application.util.Status;
import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.master.domain.ClusterUser;
import com.titan.irgs.master.repository.ClusterUserRepository;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.model.UserPrinciple;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.user.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ClusterUserRepository clusterUserRepository;
	
	
	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		user.setCredentialsNonExpired(true);
		return userRepo.save(user);
	}

	@SuppressWarnings("serial")
	@Override
	public Page<User> getAll(String username, String roleName, String businessVerticalTypeName, String firstName, String emailId, String mobileNo,String stringStoreNames,List<Long> webStoreIds,String departmentName, 
			String department,Pageable page) {
		// TODO Auto-generated method stub
		return userRepo.findAll(new Specification<User>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();


				
				if (username != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("username"),"%" + username + "%")));

				}
				
				if (roleName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("role").join("role")
							.get("roleName"),"%" + roleName + "%")));

				}

				if (businessVerticalTypeName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("role").join("webMaster")
							.get("webMasterName"), "%" + businessVerticalTypeName + "%")));

				}
				
				if (firstName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.get("firstName"), "%" + firstName + "%")));

				}

				if (emailId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.get("email"), "%" + emailId + "%")));

				}
				if (mobileNo != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.get("phoneNo"), "%" + mobileNo + "%")));

				}
				
				if(webStoreIds!=null) {
				//	predicates.add(criteriaBuilder.and(root.join("amcInventory").join("store").get("storeId").in(stringStoreIds)));
				//	predicates.add(criteriaBuilder.and(root.get("username").in(stringStoreNames)));
					predicates.add(criteriaBuilder.and(root.join("role").get("webRoleId").in(webStoreIds)));
				}

				

				if (departmentName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("roleWiseDepartments").join("department").get("departmentName"), "%" + departmentName + "%")));

				}
				
				if (department != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("roleWiseDepartments").join("department").get("departmentName"), "%" + department + "%")));

				}
				
				/*
				 * if (distributorName != null) {
				 * predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.join("item").
				 * join("").get(""), frameShapeName)));
				 * 
				 * }
				 */

				

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, page);
	}

	@Override
	public User getById(Long id) {
		// TODO Auto-generated method stub
		return userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("the request id not found"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user= userRepo.findByUsername(username);
				
		return UserPrinciple.build(user);

	}

	@Override
	public User findByUserName(String name) {
		// TODO Auto-generated method stub
		return userRepo.findByUsername(name);
	}

	@Override
	public User update(User user) {
		
		User existUser= userRepo.getOne(user.getId());
		if(existUser==null) 
		{
			throw new ResourceNotFoundException("the requested user not found");
		}
		else {
			if(existUser.isAccountNonLocked()==false && user.getAccountNonLocked()==true) {
				
				List<ClusterUser> activeclusterUserexists=clusterUserRepository.checkUserIsactiveInClusterUser(user.getId());
				if(activeclusterUserexists.isEmpty()) {
					List<ClusterUser> clusterUsers=clusterUserRepository.getClusterByUserById(user.getId());
					for(ClusterUser clusterUser:clusterUsers) {
						clusterUser.setUserActive(Status.ACTIVE.toString());
						clusterUserRepository.save(clusterUser);
						
						}
					
				}
				else {
					
					throw new ResourceAlreadyExitException("Already reagion as been assigned to the role.. you cant make active.");
				}
				
				
				
			}
			
		}

		user.setCredentialsNonExpired(true);
		
		return userRepo.save(user);
	}

	@Override
	public List<Object[]> getAllForExcel() {
		// TODO Auto-generated method stub
		return userRepo.getAllForExcel();
	}

	@Override
	public List<Object[]> getAllForExcel(Long id) {
		// TODO Auto-generated method stub
		return userRepo.getAllForExcel(id);
	}

	@Override
	public Page<User> getAll(String username, String roleName, String businessVerticalTypeName, String firstName,
			String emailId, String mobileNo, List<User> storeIRST, Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
