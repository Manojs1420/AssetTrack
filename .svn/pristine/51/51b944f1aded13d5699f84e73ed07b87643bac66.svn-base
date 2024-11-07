package com.titan.irgs.master.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.VendorType;
import com.titan.irgs.master.repository.VendorTypeRepository;
import com.titan.irgs.master.service.IVendorTypeService;

/**
 * Method Implementation for IVendorTypeService method
 * @author 
 *
 */
@Service
public class VendorTypeService implements IVendorTypeService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private VendorTypeRepository vendorTypeRepository;
    
	/**
	 * getAllVendorType ->Method
	 * @param ->none
	 * @return list of saved VendorType entity;
	 * 
	 */
	@Override
	public List<VendorType> getAllVendorType() {
		return vendorTypeRepository.findAll();
	}
    
	/**
	 * getVendorTypeById ->Method
	 * @param vendorTypeId
	 * @return saved VendorType entity
	 */
	@Override
	public VendorType getVendorTypeById(Long vendorTypeId) {
		
		VendorType vendorType = vendorTypeRepository.findById(vendorTypeId).orElseThrow(()->new EntityNotFoundException("vendorType with vendorTypeId " + vendorTypeId + " not found"));
		
		return vendorType;
		
	}

	/**
	 * saveVendorType ->Method
	 * @param vendorType entity
	 * @return saved vendorType entity
	 */
	@Override
	public VendorType saveVendorType(VendorType vendorType) {
		vendorType.setCreatedOn(new Date());
		vendorType.setUpdatedOn(null);
		return vendorTypeRepository.save(vendorType);
	}

	/**
	 * updateVendorType ->Method
	 * @param vendorType entity
	 * @return updated vendorType entity
	 */
	@Override
	public VendorType updateVendorType(VendorType vendorType) {
		VendorType vendorType1 = vendorTypeRepository.findById(vendorType.getVendorTypeId()).orElseThrow(()->new EntityNotFoundException("vendorType with vendorTypeId " + vendorType.getVendorTypeId() + " not found"));
		
		return vendorTypeRepository.save(vendorType);
	}
    
	/**
	 * deleteVendorTypeById ->Method
	 * @param vendortypeId
	 * @return none
	 */
	@Override
	public void deleteVendorTypeById(Long vendorTypeId) {
		VendorType vendorType = vendorTypeRepository.findById(vendorTypeId).orElseThrow(()->new EntityNotFoundException("vendorType with vendorTypeId " + vendorTypeId + " not found"));
		
		
		vendorTypeRepository.deleteById(vendorTypeId);	

	}

	/**
	 * checkIfVendorTypeNameIsExit ->Method
	 * @param vendorTypeName
	 * @return true -> Exit , false -> Not Exit
	 */
	@Override
	public boolean checkIfVendorTypeNameIsExit(String vendorTypeName) {
		VendorType vendorType = vendorTypeRepository.findByVendorTypeName(vendorTypeName);
		if (vendorType != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public VendorType findByVendorTypeName(String vendorTypeName) {
		
		return vendorTypeRepository.findByVendorTypeName(vendorTypeName);
	}

}
