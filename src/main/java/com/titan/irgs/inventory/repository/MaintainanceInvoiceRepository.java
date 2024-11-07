package com.titan.irgs.inventory.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.titan.irgs.inventory.domain.MaintainanceInvoice;

@Repository 
 public interface MaintainanceInvoiceRepository extends JpaRepository<MaintainanceInvoice, Long> {

	
	
	@Query(value="SELECT m.* FROM maintainance_invoice m where m.maintainance_id=:id",nativeQuery = true)
	List<MaintainanceInvoice> getMaintainanceInvoiceByMaintainanceId(@PathVariable("id")Long id);
	
	
	
}
		 
