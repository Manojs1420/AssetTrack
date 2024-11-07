package com.titan.irgs.master.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.repository.VendorRepository;
import com.titan.irgs.master.service.IVendorService;
import com.titan.irgs.role.domain.Role;
import com.titan.irgs.role.domain.RoleWiseDepartments;
import com.titan.irgs.role.repository.RoleRepository;
import com.titan.irgs.role.repository.RoleWiseDepartmentsRepo;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;


/**
 * Method Implementation for IVendorService method
 * @author 
 *
 */
@Service
@Transactional
public class VendorService implements IVendorService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private VendorRepository vendorRepository;
	
	@Autowired
	private UserRepo userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private WebRoleRepo webRoleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
    
	@Autowired
	RoleWiseDepartmentsRepo roleWiseDepartmentsRepo;
	
	/**
	 * getAllVendor -> Method
	 * @param ->none
	 * @return list of saved vendor entity
	 */
	@SuppressWarnings("serial")
	@Override
	public Page<Vendor> getAllVendor(String vendorName, String vendorCode, String vendorType,String stateName, String cityName, String contactNumber, String vendorStatus,String webMasterName, Pageable page) {
		return vendorRepository.findAll(new Specification<Vendor>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<Vendor> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();


				
				if (vendorName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("vendorName"), vendorName + "%")));

				}
				
				if (vendorStatus != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("vendorStatus").as(String.class), vendorStatus)));

				}
				
				if(stateName!=null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("state")
							.get("stateName"),"%" +stateName+"%")));
				}

				if (cityName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("city")
							.get("cityName"), "%" + cityName + "%")));

				}
				
				if (vendorCode != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.get("vendorCode"), "%" + vendorCode + "%")));

				}

				if (contactNumber != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.get("contactNumber"), "%" + contactNumber + "%")));

				}
				if (vendorType != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("vendorType").get("vendorTypeName"), "%" + vendorType + "%")));

				}
				
				
				if (webMasterName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.join("webMaster").get("webMasterName"),"%" + webMasterName + "%")));

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
    
	/**
	 * getVendorById -> Method
	 * @param vendorId
	 * @return saved vendor entity
	 */
	@Override
	public Vendor getVendorById(Long vendorId) {
		Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(()->new EntityNotFoundException("vendor with vendorId " + vendorId + " not found"));
		
		return vendor;
	}
    
	/**
	 * saveVendor ->Method
	 * @param vendor entity
	 * @return saved vendor entity
	 */
	@Override
	public Vendor saveVendor(Vendor vendor,Long roleId) {
		vendor.setCreatedOn(new Date());
		vendor.setUpdatedOn(null);
		Vendor savedVendor = vendorRepository.save(vendor);
		 
		WebRole webRole = webRoleRepository.findByRoleRoleIdAndWebMasterWebMasterId(roleId, vendor.getWebMaster().getWebMasterId());
		  
		RoleWiseDepartments roleWiseDepartments=roleWiseDepartmentsRepo.findRoleWiseDepartmentsIdsByWebRoleIdandDepartmentId(webRole.getWebRoleId(), roleId);

		if (savedVendor != null && roleId != null && webRole != null) {
			
			User user = new User();
			
			user.setUsername(savedVendor.getVendorCode());
			user.setPassword(passwordEncoder.encode(savedVendor.getVendorCode()));
			user.setFirstName(savedVendor.getVendorName());
			user.setEmail(vendor.getServiceEmailId1());
			user.setPhoneNo(vendor.getContactNumber());
			user.setRole(webRole);
	        user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
			user.setIsgroupBusiness("false");
			user.setInventoryUser("false");
			userRepository.save(user);

		}

	
		return savedVendor;
		
	}
    
	/**
	 * updateVendor ->Method
	 * @param vendor entity
	 * @return updated vendor entity
	 */
	@Override
	public Vendor updateVendor(Vendor vendor) 
	{
		vendor.setUpdatedOn(new Date());
		Vendor vendor1 = vendorRepository.findById(vendor.getVendorId()).orElseThrow(()->new EntityNotFoundException("vendor with vendorId " + vendor.getVendorId() + " not found"));
		
		String roleName = "Vendor";
		Role	role  = roleRepository.findByRoleNameIgnoreCase(roleName);
	 	WebRole webRole = webRoleRepository.findByRoleRoleIdAndWebMasterWebMasterId(role.getRoleId(), vendor.getWebMaster().getWebMasterId());
		if(vendor1 != null && webRole != null) {
		
		User user = userRepository.findByUsername(vendor1.getVendorCode());
		if(user==null) {
		user=new User();
		user.setUsername(vendor.getVendorCode());
		user.setPassword(passwordEncoder.encode(vendor.getVendorCode()));
		user.setFirstName(vendor.getVendorName());
		user.setRole(webRole);
		user.setEmail(vendor.getServiceEmailId1());
		user.setPhoneNo(vendor.getContactNumber());
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setIsgroupBusiness("false");
		user.setInventoryUser("false");
		}
		else {
			user.setUsername(vendor.getVendorCode());
			user.setPassword(passwordEncoder.encode(vendor.getVendorCode()));
			user.setFirstName(vendor.getVendorName());
			user.setRole(webRole);
			user.setEmail(vendor.getServiceEmailId1());
			user.setPhoneNo(vendor.getContactNumber());
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			
			user.setIsgroupBusiness("false");
			user.setInventoryUser("false");
			
		}
		userRepository.save(user);
		
		
		}
		return vendorRepository.save(vendor);
	}
    
	/**
	 * deleteVendorById ->Method
	 * @param vendorId
	 * @return none
	 */
	@Override
	public void deleteVendorById(Long vendorId) {
		Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(()->new EntityNotFoundException("vendor with vendorId " + vendorId + " not found"));
		
		vendorRepository.deleteById(vendorId);
	}

	@Override
	public boolean checkIfVendorCodeIsExit(String vendorCode) 
	{
		Vendor vendor = vendorRepository.findByVendorCode(vendorCode);
		if (vendor != null) {
			return true;
		} else {
			return false;
		}
	}

	
	
	@Override
	public Vendor findByVendorName(String vendorName) {

		Vendor vendor = vendorRepository.findByVendorName(vendorName);
		/*if (vendor == null) {
			logger.error("vendor with vendorName {} not found", vendorName);
			throw new EntityNotFoundException("vendor with vendorName" + vendorName+ " not found");
		}*/
		return vendor;
	}

	@Override
	public Vendor findByVendorCode(String vendorCode) 
	{
		Vendor vendor = vendorRepository.findByVendorCode(vendorCode);
		/*if (vendor == null) {
			logger.error("vendor with vendorCode {} not found", vendorCode);
			throw new EntityNotFoundException("vendor with vendorCode" + vendorCode+ " not found");
		}*/
		return vendor;	}

	@Override
	public Vendor findByVendorBillingEmailId(String billingEmailId) {
	
		return vendorRepository.findByBillingEmailId(billingEmailId);
	}

	@Override
	public List<Vendor> findByVendorIdNotIn(List<Long> ids) {
		
		return vendorRepository.findByVendorIdNotIn(ids);
	}

	@Override
	public List<Vendor> getAllVendorsByUsingAssetId(Long id) {
		// TODO Auto-generated method stub
		return vendorRepository.getAllVendorsByUsingAssetId(id);
	}

	@Override
	public List<Vendor> getAllVendors() {
		// TODO Auto-generated method stub
		return vendorRepository.findAll();
	}

	@Override
	public List<Object[]> getAllForExcel() {
		// TODO Auto-generated method stub
		return vendorRepository.getAllForExcel();
	}

	@Override
	public List<Object[]> getAllForExcel(Long id) {
		// TODO Auto-generated method stub
		return vendorRepository.getAllForExcel(id);
	}

	@Override
	public List<Vendor> getVendorByTypeVerticalId(Long id) {
		// TODO Auto-generated method stub
		return vendorRepository.getVendorByTypeVerticalId(id);
	}
	

}
