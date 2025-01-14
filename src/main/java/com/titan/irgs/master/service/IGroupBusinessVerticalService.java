package com.titan.irgs.master.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.GroupBusinessVertical;
import com.titan.irgs.master.domain.GroupBusinessVerticalMaster;

public interface IGroupBusinessVerticalService {

	GroupBusinessVerticalMaster getGroupBusinessVerticalById(Long id);

	void deleteGroupBusinessVerticalById(Long id);

	Page<GroupBusinessVerticalMaster> getAllGroupBusinessVertical1(String businessVerticalTypeName,
			String businessVerticalTypeName1,
			Long groupBusinessVerticalId, String groupBusinessVerticalName, String createdOn, Pageable page);

	GroupBusinessVerticalMaster saveGroupBusinessVertical(GroupBusinessVerticalMaster groupBusinessVerticalMaster);

	GroupBusinessVerticalMaster updateGroupBusinessVertical(GroupBusinessVerticalMaster groupBusinessVerticalMaster);

	Page<GroupBusinessVertical> getAllGroupBusinessVertical(String businessVerticalTypeName,
			String businessVerticalTypeName1, Long groupBusinessVerticalId, String groupBusinessVerticalName,
			String createdOn, Pageable page);
	
	

	
}
