package com.titan.irgs.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.titan.irgs.master.domain.StoreServiceType;

public interface StoreServiceTypeRepository extends JpaRepository<StoreServiceType, Long>,JpaSpecificationExecutor<StoreServiceType> {

	StoreServiceType findByStoreServiceTypeName(String storeServiceTypeName);

	StoreServiceType findByStoreServiceTypeId(Long storeServiceTypeId);

}
