package com.titan.irgs.master.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.StoreAlloted;
import com.titan.irgs.master.repository.StoreAllotedRepository;
import com.titan.irgs.master.service.IStoreAllotedService;

@Service
public class StoreAllotedService implements IStoreAllotedService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StoreAllotedRepository StoreAllotedRepository;

	/**
	 * getAllStoreAlloted -> Method
	 * @param ->none
	 * @return list of saved StoreAlloted entity
	 */
	@Override
	public List<StoreAlloted> getAllStoreAlloted() {

		return StoreAllotedRepository.findAll();
	}

	/**
	 * getStoreAllotedById -> Method
	 * @param StoreAllotedId
	 * @return saved StoreAlloted entity
	 */
	@Override
	public StoreAlloted getStoreAllotedById(Long StoreAllotedId) {
		StoreAlloted StoreAlloted = StoreAllotedRepository.findById(StoreAllotedId).orElseThrow(()->new EntityNotFoundException("StoreType with Id " + StoreAllotedId + " not found"));
		
		return StoreAlloted;
	}

	/**
	 * saveStoreAlloted ->Method
	 * @param StoreAlloted entity
	 * @return saved StoreAlloted entity
	 */
	@Override
	public StoreAlloted saveStoreAlloted(StoreAlloted StoreAlloted) {
		StoreAlloted.setCreatedOn(new Date());
		StoreAlloted.setUpdatedOn(null);
		return StoreAllotedRepository.save(StoreAlloted);
	}

	/**
	 * updateStoreAlloted ->Method
	 * @param StoreAlloted entity
	 * @return updated StoreAlloted entity
	 */
	@Override
	public StoreAlloted updateStoreAlloted(StoreAlloted StoreAlloted) {
		StoreAlloted.setUpdatedOn(new Date());
		StoreAlloted StoreAlloted1 = StoreAllotedRepository.findById(StoreAlloted.getStoreAllotedId()).orElseThrow(()->new EntityNotFoundException("StoreType with Id " + StoreAlloted.getStoreAllotedId() + " not found"));
		if (StoreAlloted1 == null) {
			logger.error("StoreAlloted with StoreAllotedId {} not found", StoreAlloted.getStoreAllotedId());
			throw new EntityNotFoundException("StoreAlloted with StoreAllotedId " + StoreAlloted.getStoreAllotedId() + " not found");
		}
		return StoreAllotedRepository.save(StoreAlloted);
	}

	/**
	 * deleteStoreAllotedById ->Method
	 * @param StoreAllotedId
	 * @return none
	 */
	@Override
	public void deleteStoreAllotedById(Long StoreAllotedId) {
		StoreAlloted StoreAlloted = StoreAllotedRepository.findById(StoreAllotedId).orElseThrow(()->new EntityNotFoundException("StoreType with Id " + StoreAllotedId + " not found"));
		
		StoreAllotedRepository.deleteById(StoreAllotedId);

	}

	@Override
	public StoreAlloted findByStoreAllotedName(String StoreAllotedName) {
		StoreAlloted StoreAlloted = StoreAllotedRepository.findByStoreAllotedType(StoreAllotedName);
		if (StoreAlloted == null) {
			logger.error("StoreAlloted with StoreAlloted {} not found", StoreAllotedName);
			throw new EntityNotFoundException("StoreAlloted with StoreAlloted " + StoreAllotedName + " not found");
		}
		return StoreAlloted;
	}

	@Override
	public boolean checkIfStoreAllotedNameIsExit(String StoreAllotedName) {
		StoreAlloted StoreAlloted = StoreAllotedRepository.findByStoreAllotedType(StoreAllotedName);
		if (StoreAlloted != null) {
			return true;
		} else {
			return false;
		}
	}
	

}
