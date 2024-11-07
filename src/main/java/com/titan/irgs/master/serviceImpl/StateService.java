package com.titan.irgs.master.serviceImpl;



import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.irgs.master.domain.State;
import com.titan.irgs.master.repository.StateRepository;
import com.titan.irgs.master.service.IStateService;




/**
 * This is StateRepository interface(implemented on Jpa framework) which are responsible to perform CRUD operation on state table

 *
 */

@Service
public class StateService implements IStateService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StateRepository stateRepository; 
	
	/**
	 * getAllState -> Method
	 * @param ->none
	 * @return list of saved state entity
	 */
	@Override
	public List<State> getAllState() {
		return stateRepository.findAll();
	}
    
	/**
	 * getStateById -> Method
	 * @param stateId
	 * @return saved state entity
	 */
	@Override
	public State getStateById(Long stateId) {
		State state = stateRepository.getOne(stateId);
		if(state == null)
		{
			logger.error("State with stateId {} not found", stateId);
            throw new EntityNotFoundException("State with stateId " + stateId + " not found");
		}
		return state;
	}
    
	/**
	 * saveState ->Method
	 * @param state entity
	 * @return saved state entity
	 */
	@Override
	public State saveState(State state) {
		state.setCreatedDate(new Date());
		return stateRepository.save(state);
	}
    
	/**
	 * updateState ->Method
	 * @param state entity
	 * @return updated state entity
	 */
	@Override
	public State updateState(State state) {
		State state1 = stateRepository.getOne(state.getStateId());
		if(state1 == null)
		{
			logger.error("State with stateId {} not found", state.getStateId());
            throw new EntityNotFoundException("State with stateId " + state.getStateId() + " not found");
		}
		
		return stateRepository.save(state);
	}
    
	/**
	 * deleteStateById ->Method
	 * @param stateId
	 * @return none
	 */
	@Override
	public void deleteStateById(Long stateId) {
		State state = stateRepository.getOne(stateId);
		if(state == null)
		{
			logger.error("State with stateId {} not found", stateId);
            throw new EntityNotFoundException("State with stateId " + stateId + " not found");
		}
		stateRepository.deleteById(stateId);
	}
	
	/**
	 * checkIfStateNameIsExit ->Method
	 * @param stateName
	 * @return boolean
	 */
	@Override
	public boolean checkIfStateNameIsExit(String stateName) {
		State state = stateRepository.findByStateName(stateName);
		if (state != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<State> getAllStateOnCountryId(Long countryId) {
		return stateRepository.findByCountryId(countryId);
	}
/*
	@Override
	public List<Region> getAllRegionOnStateId(Long stateId) {
		State state = stateRepository.findOne(stateId);
		if (state == null) {
			logger.error("state with stateId {} not found", stateId);
			throw new EntityNotFoundException("state with stateId " + stateId + " not found");
		}
		return state.getRegions();
	}
*/
	
	
	@Override
	public State findByStateName(String stateName) {
		State state = stateRepository.findByStateName(stateName);
		
		/*if (brand == null) {
			logger.error("brand with brandName {} not found", brandName);
			throw new EntityNotFoundException("brand with brandName " + brandName + " not found");
		}*/
		return state;
	}

	@Override
	public State getStateById(List<Long> stateIds) {
	
		return null;
	}

	@Override
	public State findByCountryCountryId(Long countryId) {
		
		return stateRepository.findByCountryCountryId(countryId);
	}

	@Override
	public List<State> findByStateIdIn(List<Long> stateIds) {
	
		return stateRepository.findByStateIdIn(stateIds);
	}

	@Override
	public List<State> findByCountryCountryIdIn(List<Long> countryId) {
		
		return stateRepository.findByCountryCountryIdIn(countryId);
	}

	@Override
	public List<Object[]> getAllForExcel() {
		// TODO Auto-generated method stub
		return stateRepository.getAllForExcel();
	}

}
