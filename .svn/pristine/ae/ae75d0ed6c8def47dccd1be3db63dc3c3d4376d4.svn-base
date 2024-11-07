package com.titan.irgs.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.StoreBusinessServiceType;


@Repository
public interface StoreBusinessServiceTypeRepository extends JpaRepository<StoreBusinessServiceType, Long>,JpaSpecificationExecutor<StoreBusinessServiceType> {

	StoreBusinessServiceType findByStoreBusinessServiceTypeName(String storeBusinessServiceTypeName);

	StoreBusinessServiceType findByStoreBusinessServiceTypeId(Long storeBusinessServiceTypeId);

}
