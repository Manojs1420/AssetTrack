package com.titan.irgs.master.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.Store;
import com.titan.irgs.master.enums.StoreStatus;
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.repository.RegionRepository;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.service.IStoreService;
import com.titan.irgs.user.domain.User;
import com.titan.irgs.user.repository.UserRepo;
import com.titan.irgs.user.service.IUserService;
import com.titan.irgs.webRole.domain.WebRole;
import com.titan.irgs.webRole.repository.WebRoleRepo;

@Service
public class StoreService implements IStoreService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	@Autowired
	private StoreRepository StoreRepository;
	
	@Autowired
	private WebRoleRepo webRoleRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	ClusterService clusterService;
	
	@Autowired
	ClusterUserService clusterUserService;
	
	@Autowired
	DepartmentRepo departmentRepo;
	
    
	/**
	 * getAllStore -> Method
	 * @param ->none
	 * @return list of saved Store entity
	 */
	@SuppressWarnings("serial")
	@Override
	public Page<Store> getAllStore(String storeName, String storeCode, String webMasterName, 
			String ownerType, String emailId, String cityName,String storeStatus,String regionName, Pageable pageable) {
		
		
		return StoreRepository.findAll(new Specification<Store>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();


				
				if (storeName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("storeName"),"%" + storeName + "%")));

				}

				if (storeStatus != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("storeStatus"), StoreStatus.valueOf(storeStatus))));

				}

				if (storeCode != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("storeCode"), "%" + storeCode + "%")));

				}
				
				if (webMasterName != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("webMaster").get("webMasterName"), "%" + webMasterName + "%")));

				}

				if (ownerType != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder
							.like(root.join("ownerType").get("ownerTypeName"), "%" + ownerType + "%")));

				}
				
				if (emailId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("emailId"), "%" + emailId + "%")));

				}

				if (cityName != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(root.join("city").get("cityName"), "%" + cityName + "%")));

				}
				if (regionName != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.like(root.join("region").get("regionName"), "%" + regionName + "%")));

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
		}, pageable);
	}
	
    
	/**
	 * getStoreById -> Method
	 * @param StoreId
	 * @return saved Store entity
	 */
	@Override
	public Store getStoreById(Long StoreId) {
		Store Store = StoreRepository.findById(StoreId).orElseThrow(()->new EntityNotFoundException("InventoryUser with inventoryUserId " + StoreId + " not found"));
		/*if(Store == null)
		{
			logger.error("Store with StoreId {} not found", StoreId);
            throw new EntityNotFoundException("Store with StoreId " + StoreId + " not found");
		}*/
		return Store;
	}
    
	/**
	 * saveStore ->Method
	 * @param Store entity
	 * @return saved Store entity
	 */
	@Override
	@Transactional
	public Store saveStore(Store Store,Long reportingToId) {
		Store.setCreatedOn(new Date());
		//Store.setOptoAlloted(Store.getOptoAlloted());
		//Store.setReportingTo(reportingTo)
		Store saveStore=StoreRepository.save(Store);
		
		return saveStore;
	}
	/**
	 * updateStore ->Method
	 * @param Store entity
	 * @return updated Store entity
	 */
	@Override
	@Transactional
	public Store updateStore(Store store) {
		Store Store1 = StoreRepository.findById(store.getStoreId()).orElseThrow(()->new EntityNotFoundException("Inventory user with userId " + store.getStoreId() + " not found"));
		store.setUpdatedOn(new Date());
		Store saveStore=StoreRepository.save(store);
		return saveStore;

	}
    
	/**
	 * deleteStoreById ->Method
	 * @param StoreId
	 * @return none
	 */
	@Override
	public void deleteStoreById(Long StoreId) {
		Store Store = StoreRepository.findById(StoreId).orElseThrow(()->new EntityNotFoundException("Store with storeId " + StoreId + " not found"));
		
		
		StoreRepository.deleteById(StoreId);
	}


	@Override
	public boolean checkIfStoreCodeIsExit(String StoreCode) {
		Store Store = StoreRepository.findByStoreCodeIgnoreCase(StoreCode);
		if(Store!=null) {
			return true;
		}else{
			return false;
		}
	}


	@Override
	public Store findByStoreCode(String StoreCode) {
		Store Store=StoreRepository.findByStoreCode(StoreCode);
		
		
		return Store;
	}


	@Override
	public Store saveStoreUpload(Store store, Long webRoleId, Long userId) {
		
	
		//Store.setOptoAlloted(Store.getOptoAlloted());
		//Store.setReportingTo(reportingTo)
		Store saveStore=StoreRepository.save(store);
		
		WebRole webRole = webRoleRepo.findById(webRoleId).orElseThrow(()->new EntityNotFoundException("WebRole with Id " + webRoleId + " not found"));
;

		if (saveStore != null && webRoleId != null) {
			
			User user2=userService.getById(userId);
			
			
			User user = new User();
			user.setUsername(saveStore.getStoreCode());
			//user.setFirstName(saveStore.getStoreCode());
			//user.setLastName(saveStore.getEpStoreCode());
			user.setPassword(passwordEncoder.encode("INIT@123"));
			//user.setReportingTo(idd); 
			
			user.setRole(webRole);
			//user.setAccountStatus(true);
			//user.setVendor(true);
			user.setAccountNonExpired(true);
			user.setCredentialsNonExpired(true);
			
			if(store.getStoreStatus()!=null && store.getStoreStatus().equals(StoreStatus.INACTIVE)) {
				 user.setAccountNonLocked(false);
				}
				else {
					 user.setAccountNonLocked(true);

				}
		//	user.setStoreId(saveStore.getStoreId());
			//user.setUserStatus(UserStatus.ACTIVE);
          
			userRepo.save(user);
		
			//StoreRepository.save(saveStore);
			
		/*	String uus="admin";
			
		User user1=userRepository.findByUserName(uus);
		Long a=user1.getId();
		user.setReportingTo(a);
		
		userRepository.save(user);
		*/
		}

		else {

			System.out.println("It is a Normal User");

		}
		
	

		
		
		return store;
	}


	@Override
	public Store findByCostcentre(String costcentre) {
		// TODO Auto-generated method stub
		return StoreRepository.findByCostcentre(costcentre);
	}


	@Override
	public List<Store> getAllStoreNotPresentInUser(Long webroleid, Long webmasterid) {
		// TODO Auto-generated method stub
		return StoreRepository.getAllStoreNotPresentInUser( webroleid,  webmasterid);
	}


	@Override
	public List<Store> getStoreByBussinessId(Long id) {
		// TODO Auto-generated method stub
		return StoreRepository.getStoreByBussinessId(id);
	}


	@Override
	public List<Object[]> getAllForExcel() {
		// TODO Auto-generated method stub
		return StoreRepository.getAllForExcel();
	}


	@Override
	public List<Object[]> getAllForExcel(Long id) {
		// TODO Auto-generated method stub
		return StoreRepository.getAllForExcel(id);
	}


	


	

}
