package com.titan.irgs.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import com.titan.irgs.inventory.domain.VendorInvoiceFileUpload;
import com.titan.irgs.master.domain.Asset;

public interface VendorInvoiceFileUploadRepo extends JpaRepository<VendorInvoiceFileUpload, Long>,JpaSpecificationExecutor<VendorInvoiceFileUpload> {

	@Query(value="SELECT m.* FROM vendor_invoice_file_upload m where m.asset_id=:id",nativeQuery = true)
	List<VendorInvoiceFileUpload> getVendorInvoiceFileUploadByAssetId(@PathVariable("id")Long id);

	
	@Query(value="SELECT m.* FROM vendor_invoice_file_upload m where m.asset_id=:id",nativeQuery = true)
	VendorInvoiceFileUpload getByAssetId(@PathVariable("id")Long id);
	
	List<VendorInvoiceFileUpload> findByAsset(Asset asset);
}
