package com.titan.irgs.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.OwnerType;

@Repository
public interface OwnerTypeRepository extends JpaRepository<OwnerType, Long> {

	OwnerType findByOwnerTypeName(String ownerTypeName);
	
	OwnerType findByOwnerTypeId(Long ownerTypeId);

}
