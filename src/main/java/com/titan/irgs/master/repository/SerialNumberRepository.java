package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.SerialNumber;

@Repository
public interface SerialNumberRepository
		extends JpaRepository<SerialNumber, Long>, JpaSpecificationExecutor<SerialNumber> {

	@Query(value = "SELECT * FROM serial_number where asset_id=:assetId", nativeQuery = true)
	List<SerialNumber> findByAssetId(Long assetId);

	@Query(value = "select * from serial_number where serial_number=:serialNumber and asset_id=:assetId", nativeQuery = true)
	SerialNumber findBySerialNumberAndAssetId(String serialNumber, Long assetId);

	void deleteByAsset(Asset asset);

	List<SerialNumber> findByAsset(Asset asset);

	@Query(value = "SELECT *\r\n"
			+ "FROM serial_number s\r\n"
			+ "WHERE s.serial_id NOT IN (SELECT serial_id FROM inventory WHERE serial_id IS NOT NULL )and asset_id=:assetId", nativeQuery = true)
	List<SerialNumber> findByAssetIdWhereSerialIdNotThereInInventory(Long assetId);

}
