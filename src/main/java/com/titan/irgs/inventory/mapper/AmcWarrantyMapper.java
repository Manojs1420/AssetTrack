
package com.titan.irgs.inventory.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.inventory.domain.AmcWarranty;
import com.titan.irgs.inventory.repository.AmcInventoryRepository;
import com.titan.irgs.inventory.service.AmcInventoryService;
import com.titan.irgs.inventory.vo.AmcWarrantyVO;

@Component
public class AmcWarrantyMapper {

	@Autowired
	AmcInventoryService amcInventoryService;

	@Autowired
	AmcInventoryRepository amcInventoryRepository;

	public AmcWarranty getEntityFromVo(AmcWarrantyVO amcWarrantyVO) {
		AmcWarranty amcWarranty = new AmcWarranty();
		BeanUtils.copyProperties(amcWarrantyVO, amcWarranty);

		return amcWarranty;
	}

	public AmcWarrantyVO getVoFromEntity(AmcWarranty amcWarranty) {
		AmcWarrantyVO amcWarrantyVO = new AmcWarrantyVO();
		BeanUtils.copyProperties(amcWarranty, amcWarrantyVO);

		return amcWarrantyVO;
	}

}
