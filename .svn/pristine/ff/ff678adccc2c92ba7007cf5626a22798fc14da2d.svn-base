package com.titan.irgs.master.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.OwnerType;
import com.titan.irgs.master.repository.OwnerTypeRepository;
import com.titan.irgs.master.service.IOwnerTypeService;

@Service
public class OwnerTypeService implements IOwnerTypeService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OwnerTypeRepository OwnerTypeRepository;

	/**
	 * getAllOwnerType -> Method
	 * @param ->none
	 * @return list of saved OwnerType entity
	 */
	@Override
	public List<OwnerType> getAllOwnerType() {

		return OwnerTypeRepository.findAll();
	}

	/**
	 * getOwnerTypeById -> Method
	 * @param OwnerTypeId
	 * @return saved OwnerType entity
	 */
	@Override
	public OwnerType getOwnerTypeById(Long OwnerTypeId) {
		OwnerType OwnerType = OwnerTypeRepository.findById(OwnerTypeId).orElseThrow(()->new EntityNotFoundException("OwnerType with Id " + OwnerTypeId + " not found"));
		
		return OwnerType;
	}

	/**
	 * saveOwnerType ->Method
	 * @param OwnerType entity
	 * @return saved OwnerType entity
	 */
	@Override
	public OwnerType saveOwnerType(OwnerType OwnerType) {
		OwnerType.setCreatedOn(new Date());
		OwnerType.setUpdatedOn(null);
		return OwnerTypeRepository.save(OwnerType);
	}

	/**
	 * updateOwnerType ->Method
	 * @param OwnerType entity
	 * @return updated OwnerType entity
	 */
	@Override
	public OwnerType updateOwnerType(OwnerType OwnerType) {
		OwnerType.setUpdatedOn(new Date());
		OwnerType OwnerType1 = OwnerTypeRepository.findById(OwnerType.getOwnerTypeId()).orElseThrow(()->new EntityNotFoundException("StoreType with Id " + OwnerType.getOwnerTypeId() + " not found"));
		if (OwnerType1 == null) {
			logger.error("OwnerType with OwnerTypeId {} not found", OwnerType.getOwnerTypeId());
			throw new EntityNotFoundException("OwnerType with OwnerTypeId " + OwnerType.getOwnerTypeId() + " not found");
		}
		return OwnerTypeRepository.save(OwnerType);
	}

	/**
	 * deleteOwnerTypeById ->Method
	 * @param OwnerTypeId
	 * @return none
	 */
	@Override
	public void deleteOwnerTypeById(Long OwnerTypeId) {
		OwnerType OwnerType = OwnerTypeRepository.findById(OwnerTypeId).orElseThrow(()->new EntityNotFoundException("StoreType with Id " + OwnerTypeId + " not found"));
		
		OwnerTypeRepository.deleteById(OwnerTypeId);

	}

	@Override
	public OwnerType findByOwnerTypeName(String OwnerTypeName) {
		OwnerType OwnerType = OwnerTypeRepository.findByOwnerTypeName(OwnerTypeName);
		if (OwnerType == null) {
			logger.error("OwnerType with OwnerTypeName {} not found", OwnerTypeName);
			//throw new EntityNotFoundException("OwnerType with OwnerTypeName " + OwnerTypeName + " not found");
		}
		return OwnerType;
	}

	@Override
	public boolean checkIfOwnerTypeNameIsExit(String OwnerTypeName) {
		OwnerType OwnerType = OwnerTypeRepository.findByOwnerTypeName(OwnerTypeName);
		if (OwnerType != null) {
			return true;
		} else {
			return false;
		}
	}
	

}
