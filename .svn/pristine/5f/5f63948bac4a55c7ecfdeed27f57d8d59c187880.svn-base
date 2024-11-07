package com.titan.irgs.master.service;



import java.util.List;

import com.titan.irgs.master.domain.State;



/**
 * This is service layer(i.e, service provider) which will interact with DAO layer(i.e, State domain class).
 * This is StateService interface(i.e, custom interface) which has CRUD method specification for State domain class.
 * It is responsible to provide service(i.e state releated data) to state web page and vice-versa
 * 
 * 
 *
 */
public interface IStateService {

	List<State> getAllState();

	State getStateById(Long stateId);

	State saveState(State state);

	State updateState(State state);

	void deleteStateById(Long stateId);
	
	boolean checkIfStateNameIsExit(String stateName);
	
	List<State> getAllStateOnCountryId(Long countryId);

	State findByStateName(String stateName);

	State getStateById(List<Long> stateIds);
	
	State findByCountryCountryId(Long countryId);
	
	List<State>findByStateIdIn(List<Long> stateIds);
	
	List<State> findByCountryCountryIdIn(List<Long> countryId);

	List<Object[]> getAllForExcel();
	
    //List<Region> getAllRegionOnStateId(Long stateId);

}
