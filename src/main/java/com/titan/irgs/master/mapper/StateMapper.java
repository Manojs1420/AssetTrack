package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.Country;
import com.titan.irgs.master.domain.State;
import com.titan.irgs.master.service.ICountryService;
import com.titan.irgs.master.vo.StateVO;

@Component
public class StateMapper {
	
	@Autowired
	private ICountryService countryService;

	public StateVO getVoFromEntity(State state) {

		StateVO stateVo = null;
		ModelMapper stateVoModelMapper = new ModelMapper();

		PropertyMap<State, StateVO> stateEntityToVOPropertyMap = new PropertyMap<State, StateVO>() {
			@Override
			protected void configure() {
				map().setStateId(source.getStateId());
				map().setStateName(source.getStateName());
				map().setCountryId(source.getCountry().getCountryId());
				map().setCountryName(source.getCountry().getCountryName());
				map().setCreatedDate(source.getCreatedDate());
				map().setUpdatedDate(source.getUpdateDate());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
			}
		};

		stateVoModelMapper.addMappings(stateEntityToVOPropertyMap);
		stateVo = stateVoModelMapper.map(state, StateVO.class);
		return stateVo;
	}

	public State getEntityFromVo(StateVO stateVo) {
		ModelMapper stateModelMapper = new ModelMapper();
		State state = null;
        
		Country country = countryService.getCountryById(stateVo.getCountryId());
		
		PropertyMap<StateVO, State> stateVOToEntityPropertyMap = new PropertyMap<StateVO, State>() {
			@Override
			protected void configure() {
				map().setStateName(source.getStateName());
				map().setCreatedDate(source.getCreatedDate());
				map().setUpdateDate(source.getUpdatedDate());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());

			}
		};

		stateModelMapper.addMappings(stateVOToEntityPropertyMap);
		state = stateModelMapper.map(stateVo, State.class);
		state.setCountry(country);
		return state;
	}
}