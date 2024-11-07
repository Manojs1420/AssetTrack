package com.titan.irgs.master.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.GroupBusinessVerticalMaster;
import com.titan.irgs.master.repository.StoreRepository;
import com.titan.irgs.master.service.IStoreService;
import com.titan.irgs.master.vo.GroupBusinessVerticalMasterVO;
import com.titan.irgs.master.vo.GroupBusinessVerticalVO;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;

@Component
public class GroupBusinessVerticalMapper {
	@Autowired
	WebMasterRepo webMasterRepo;
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	WebMasterService webMasterService;
	
	@Autowired
	IStoreService storeService;
	
	public GroupBusinessVerticalMaster getEntityFromVo(GroupBusinessVerticalMasterVO groupBusinessVerticalVO) {
		GroupBusinessVerticalMaster groupBusinessVertical = new GroupBusinessVerticalMaster();
		BeanUtils.copyProperties(groupBusinessVerticalVO, groupBusinessVertical);
		groupBusinessVertical.setGroupBusinessVerticalName(groupBusinessVerticalVO.getGroupBusinessVerticalName());
		
	//	groupBusinessVertical.setWebMaster(webMasterRepo.findById(groupBusinessVerticalVO.getWebMasterId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));
		
	//	inventory.setUser(iUserService.getById(inventoryVO.getUserId()));

		return groupBusinessVertical;
	}

	public GroupBusinessVerticalMasterVO getVoFromEntity(GroupBusinessVerticalMaster groupBusinessVerticals) {
		List<GroupBusinessVerticalVO> groupBusinessVerticalVOs=new ArrayList<GroupBusinessVerticalVO>();
		GroupBusinessVerticalMasterVO groupBusinessVerticalMasterVO= new GroupBusinessVerticalMasterVO();
		BeanUtils.copyProperties(groupBusinessVerticals, groupBusinessVerticalMasterVO);
		
		groupBusinessVerticals.getGroupBusinessVertical().forEach(groupBusinessVertical->{
			
			GroupBusinessVerticalVO GroupBusinessVertical=new GroupBusinessVerticalVO();
			GroupBusinessVertical.setWebMasterId(groupBusinessVertical.getWebMaster().getWebMasterId());
			GroupBusinessVertical.setWebMasterName(groupBusinessVertical.getWebMaster().getWebMasterName());
			GroupBusinessVertical.setCreatedOn(groupBusinessVertical.getCreatedOn());
		//	GroupBusinessVertical.setGroupBusinessVerticalName(groupBusinessVertical.getGroupBusinessVerticalName());
			GroupBusinessVertical.setUpdatedOn(groupBusinessVertical.getUpdatedOn());
			GroupBusinessVertical.setGroupBusinessVerticalId(groupBusinessVerticals.getId());
			GroupBusinessVertical.setId(groupBusinessVertical.getId());
			groupBusinessVerticalVOs.add(GroupBusinessVertical);
		});
		groupBusinessVerticalMasterVO.setGroupBusinessVerticalvo(groupBusinessVerticalVOs);
	
//		User user = iUserService.getById(inventory.getUser().getId());
	
		return groupBusinessVerticalMasterVO;
	}
}
