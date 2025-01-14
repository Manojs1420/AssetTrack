package com.titan.irgs.master.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.Equipment;
import com.titan.irgs.master.domain.EquipmentType;
import com.titan.irgs.master.domain.EquipmentWiseDepartments;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.domain.VendorEquipment;
import com.titan.irgs.master.repository.VendorEquipmentRepository;
import com.titan.irgs.master.service.IEquipmentTypeService;
import com.titan.irgs.master.service.IVendorService;
import com.titan.irgs.master.vo.EquipmentVO;
import com.titan.irgs.master.vo.VendorEquipmentVO;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;

/**
 * Model Mapper class(i.e, EquipmentMapper) which is responsible for converting VO(i.e EquipmentVO) -> DO(i.e Equipment) and vice versia
 * @author 
 *
 */
@Component
public class EquipmentMapper {
	
	@Autowired
	IEquipmentTypeService equipmentTypeService;
	
	@Autowired
	IVendorService vendorServic;
	
	@Autowired
	VendorEquipmentRepository vendorEquipmentRepository;
	@Autowired
	WebMasterService webMasterService;
	
	
	public EquipmentVO getVoFromEntity(Equipment equipment) {

		EquipmentVO equipmentVo = null;
		List<VendorEquipmentVO> vendorEquipmentVOs=new ArrayList<VendorEquipmentVO>();
		
		ModelMapper equipmentVoModelMapper = new ModelMapper();

		PropertyMap<Equipment, EquipmentVO> equipmentEntityToVOPropertyMap = new PropertyMap<Equipment, EquipmentVO>() {
			@Override
			protected void configure() {
				map().setEquipmentId(source.getEquipmentId());
				map().setEquipmentCode(source.getEquipmentCode());
				map().setEquipmentName(source.getEquipmentName());
				map().setEquipmentCost(source.getEquipmentCost());
				map().setCreatedOn(source.getCreatedOn());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setEquipmentTypeId(source.getEquipmentType().getEquipmentTypeId());
				map().setEquipmentTypeName(source.getEquipmentType().getEquipmentTypeName());
				map().setWebMasterId(source.getWebMaster().getWebMasterId());
				map().setWebMasterName(source.getWebMaster().getWebMasterName());
			

			}
		};
		
		equipmentVoModelMapper.getConfiguration().setAmbiguityIgnored(true);
		equipmentVoModelMapper.addMappings(equipmentEntityToVOPropertyMap);
		equipmentVo = equipmentVoModelMapper.map(equipment, EquipmentVO.class);

		List<VendorEquipment> vendorEquipments=vendorEquipmentRepository.findByEquipmentId(equipment.getEquipmentId());
		for(VendorEquipment vendorEquipment:vendorEquipments) {
			VendorEquipmentVO vendorEquipmentVO=new VendorEquipmentVO();
			vendorEquipmentVO.setVendorId(vendorEquipment.getVendor().getVendorId());
			vendorEquipmentVO.setVendorName(vendorEquipment.getVendor().getVendorName());
			vendorEquipmentVO.setVendorCode(vendorEquipment.getVendor().getVendorCode());
			vendorEquipmentVOs.add(vendorEquipmentVO);
		}
	
		
		equipmentVo.setVendorEquipmentInfo(vendorEquipmentVOs);
		WebMaster webMaster = webMasterService.getById(equipment.getWebMaster().getWebMasterId());
		equipmentVo.setWebMasterId(webMaster.getWebMasterId());
		equipmentVo.setWebMasterName(webMaster.getWebMasterName());
	
		
		if(equipment.getEquipmentWiseDepartments()!=null) {
			List<EquipmentWiseDepartments> equipmentWiseDepartments=new ArrayList<EquipmentWiseDepartments>();
			for(EquipmentWiseDepartments equipmentWiseDepartment:equipment.getEquipmentWiseDepartments()) {
				EquipmentWiseDepartments equipmentWiseDepartment1=new EquipmentWiseDepartments();
				equipmentWiseDepartment1.setEquipmentWiseDepartmentsId(equipmentWiseDepartment.getEquipmentWiseDepartmentsId());
				equipmentWiseDepartment1.setCreatedOn(equipmentWiseDepartment.getCreatedOn());
				equipmentWiseDepartment1.setDepartment(equipmentWiseDepartment.getDepartment());
				equipmentWiseDepartment1.setEquipment(equipment);
				equipmentWiseDepartments.add(equipmentWiseDepartment1);
			}
			equipmentVo.setEquipmentWiseDepartments(equipmentWiseDepartments);
		}
		
		return equipmentVo;

	}

	public Equipment getEntityFromVo(EquipmentVO equipmentVo) {

		Equipment equipment = null;
		Vendor vendor=null;
		ModelMapper equipmentModelMapper = new ModelMapper();
		
		PropertyMap<EquipmentVO, Equipment> equipmentVOToEntityPropertyMap = new PropertyMap<EquipmentVO, Equipment>() {
			@Override
			protected void configure() {
				map().setEquipmentId(source.getEquipmentId());
				map().setEquipmentName(source.getEquipmentName());
				map().setEquipmentCost(source.getEquipmentCost());
				map().setCreatedOn(source.getCreatedOn());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				
			}
		};
		
		equipmentModelMapper.getConfiguration().setAmbiguityIgnored(true);
		equipmentModelMapper.addMappings(equipmentVOToEntityPropertyMap);
		equipment = equipmentModelMapper.map(equipmentVo, Equipment.class);


	//	equipment = equipmentModelMapper.map(equipmentVo, Equipment.class);

		
		EquipmentType equipmentType = equipmentTypeService.getEquipmentTypeById(equipmentVo.getEquipmentTypeId());
		
		equipment.setEquipmentType(equipmentType);

	
		return equipment;
	}

}
