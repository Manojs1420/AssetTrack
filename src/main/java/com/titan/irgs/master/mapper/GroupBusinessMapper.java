package com.titan.irgs.master.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.GroupBusinessVertical;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.service.IStoreService;
import com.titan.irgs.master.vo.GroupBusinessVerticalVO;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;

@Component
public class GroupBusinessMapper {

	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	WebMasterService webMasterService;
	
	@Autowired
	IStoreService storeService;
	
	public GroupBusinessVertical getEntityFromVo(GroupBusinessVerticalVO groupBusinessVerticalVO) {
		GroupBusinessVertical groupBusinessVertical = new GroupBusinessVertical();
		BeanUtils.copyProperties(groupBusinessVerticalVO, groupBusinessVertical);
		//groupBusinessVertical.setGroupBusinessVerticalName(groupBusinessVerticalVO.getGroupBusinessVerticalName());
		
	//	groupBusinessVertical.setWebMaster(webMasterRepo.findById(groupBusinessVerticalVO.getWebMasterId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));
		
	//	inventory.setUser(iUserService.getById(inventoryVO.getUserId()));

		return groupBusinessVertical;
	}

	public GroupBusinessVerticalVO getVoFromEntity(GroupBusinessVertical groupBusinessVerticals) {
		
		GroupBusinessVerticalVO groupBusinessVerticalVO= new GroupBusinessVerticalVO();
		BeanUtils.copyProperties(groupBusinessVerticals, groupBusinessVerticalVO);
		
		groupBusinessVerticalVO.setWebMasterId(groupBusinessVerticals.getWebMaster().getWebMasterId());
		groupBusinessVerticalVO.setWebMasterName(groupBusinessVerticals.getWebMaster().getWebMasterName());
		groupBusinessVerticalVO.setGroupBusinessVerticalId(groupBusinessVerticals.getGroupBusinessVerticalMaster().getId());
	
		return groupBusinessVerticalVO;
	}
	
}
