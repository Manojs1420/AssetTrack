package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.StoreAllotedDetails;

@Repository
public interface StoreAllotedDetailsRepository extends JpaRepository<StoreAllotedDetails, Long> {

	@Query("from StoreAllotedDetails s where s.store.storeId=:storeId")
	List<StoreAllotedDetails> findByStoreStoreId(@Param("storeId")Long storeId);

	StoreAllotedDetails findByStoreAllotedDetailsId(Long id);

	StoreAllotedDetails findByStoreAllotedDetailsIdAndStoreAllotedStoreAllotedId(Long storeAllotedDetailId, Long storeAllotedId);

	List<StoreAllotedDetails> findByStoreAllotedStoreAllotedId(Long storeAllotedId);
	
	StoreAllotedDetails findByStoreAllotedStoreAllotedIdAndStoreStoreId(Long storeAllocatedId,Long storeId);
	
	

}
