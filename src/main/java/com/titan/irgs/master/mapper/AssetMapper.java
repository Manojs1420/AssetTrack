package com.titan.irgs.master.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.inventory.domain.Inventory;
import com.titan.irgs.inventory.repository.InventoryRepository;
import com.titan.irgs.master.domain.Asset;
import com.titan.irgs.master.domain.AssetSpecification;
import com.titan.irgs.master.domain.Department;
import com.titan.irgs.master.domain.Equipment;
import com.titan.irgs.master.domain.Model;
import com.titan.irgs.master.domain.SerialNumber;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.repository.AssetSpecificationRepository;
import com.titan.irgs.master.repository.DepartmentRepo;
import com.titan.irgs.master.repository.SerialNumberRepository;
import com.titan.irgs.master.service.IEquipmentService;
import com.titan.irgs.master.service.IModelService;
import com.titan.irgs.master.serviceImpl.VendorService;
import com.titan.irgs.master.vo.AssetVO;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.service.IWebMaster;

/**
 * Model Mapper class(i.e, ModelMapper) which is responsible for converting
 * VO(i.e AssetVO) -> DO(i.e Asset) and vice versia
 * 
 * @author 
 *
 */
@Component
public class AssetMapper {
	
	@Autowired
	private IModelService modelService;
	
		@Autowired
		private IEquipmentService equipmentService;
		
		@Autowired
		private IWebMaster webMasterService;
		
		@Autowired
		AssetSpecificationRepository assetSpecificationRepository;
		
		@Autowired
		VendorService vendorService;
		@Autowired
		InventoryRepository inventoryRepository;
		
		@Autowired
		DepartmentRepo departmentRepo;
		
		@Autowired
		SerialNumberRepository serialNumberRepository;
	
	public AssetVO getVoFromEntity(Asset asset) {
		AssetVO assetVo = null;
		ModelMapper assetVoModelMapper = new ModelMapper();

		PropertyMap<Asset, AssetVO> assetEntityToVOPropertyMap = new PropertyMap<Asset, AssetVO>() {

			@Override
			protected void configure() {
				map().setAssetId(source.getAssetId());
				map().setAssetCode(source.getAssetCode());
				map().setAssetName(source.getAssetName());
				map().setModelId(source.getModel().getModelId());
				map().setModelName(source.getModel().getModelName());
				map().setModelNo(source.getModel().getModelNo());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setCreatedOn(source.getCreatedOn());
				map().setUpdatedOn(source.getUpdatedOn());
                map().setItemNo (source.getOriginalQty());
                map().setEquipmentId(source.getEquipment().getEquipmentId());
                map().setEquipmentName(source.getEquipment().getEquipmentName());
                map().setEquipmentCode(source.getEquipment().getEquipmentCode());
                map().setWebMasterId(source.getWebMaster().getWebMasterId());
                map().setWebMasterName(source.getWebMaster().getWebMasterName());
                map().setBrandId(source.getModel().getBrand().getBrandId());
                map().setBrandName(source.getModel().getBrand().getBrandName());
                map().setFarNo(source.getFarNo());
                map().setBrandCode(source.getModel().getBrand().getBrandCode());
                map().setRemarks(source.getModel().getRemarks());
                map().setVendorCode(source.getVendor().getVendorCode());
                map().setVendorName(source.getVendor().getVendorName());
                map().setVendorId(source.getVendor().getVendorId());
                map().setPoNo(source.getPoNo());		
                map().setVendorInvoiceRef(source.getVendorInvoiceRef());
                map().setDepartmentId(source.getDepartment().getDepartmentId());
                map().setDepartmentName(source.getDepartment().getDepartmentName());
                map().setValue(source.getValue());
                map().setDescription(source.getDescription());
                map().setIsScrap(source.getIsScrap());
			}
		};

		assetVoModelMapper.addMappings(assetEntityToVOPropertyMap);
		assetVo = assetVoModelMapper.map(asset, AssetVO.class);
		assetVo.setAssetSpecificationName(asset.getAssetSpecification()==null?null:asset.getAssetSpecification().getAssetSpecificationName());
		assetVo.setAssetSpecificationId(asset.getAssetSpecification()==null?null:asset.getAssetSpecification().getAssetSpecificationId());
		
		if(asset.getVendor()!=null) {
			Vendor vendor = vendorService.getVendorById(asset.getVendor().getVendorId());
			assetVo.setVendorId(vendor.getVendorId());
			assetVo.setVendorCode(vendor.getVendorCode());
			assetVo.setVendorName(vendor.getVendorName());
		}
		List<Inventory> inventorys=inventoryRepository.findInventoryByAssetCodes(asset.getAssetCode());
		assetVo.setConsumedQty((long) inventorys.size());
		
		assetVo.setPendingQty(asset.getOriginalQty()-(long) inventorys.size());
		
		
		List<SerialNumber> serialNumbers1=serialNumberRepository.findByAssetId(asset.getAssetId());
		
		List<SerialNumber> serialNumbers=serialNumberRepository.findByAssetIdWhereSerialIdNotThereInInventory(asset.getAssetId());
		List<String> serial=new ArrayList<>();

		List<Long> serialIds=new ArrayList<>();
		for (SerialNumber serialNumber : serialNumbers) {	
			serial.add(serialNumber.getSerialNumber());
			serialIds.add(serialNumber.getSerialId());
		}
		List<String> serialforAsset=new ArrayList<>();
		List<Long> serialIdsforAsset=new ArrayList<>();
		for (SerialNumber serialNumber : serialNumbers1) {	
			serialforAsset.add(serialNumber.getSerialNumber());
			serialIdsforAsset.add(serialNumber.getSerialId());
		}
		assetVo.setSerialNumbers(null);
		assetVo.setSerialBasedAssets(serial);
		assetVo.setSerialIdsBasedAssets(serialIds);
		assetVo.setSerialBasedAssetsForAsset(serialforAsset);
		assetVo.setSerialIdsBasedAssetsForAsset(serialIdsforAsset);
		return assetVo;

	}

	public Asset getEntityFromVo(AssetVO assetVo) {

		Asset asset = null;
		

		ModelMapper assetModelMapper = new ModelMapper();

		PropertyMap<AssetVO, Asset> assetVOToEntityPropertyMap = new PropertyMap<AssetVO, Asset>() {

			@Override
			protected void configure() {
				map().setAssetId(source.getAssetId());
				map().setAssetCode(source.getAssetCode());
				map().setAssetName(source.getAssetName());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setCreatedOn(source.getCreatedOn());
				map().setUpdatedOn(source.getUpdatedOn());
                map().setOriginalQty(source.getItemNo());
                map().setFarNo(source.getFarNo());
                map().setPoNo(source.getPoNo());		
                map().setVendorInvoiceRef(source.getVendorInvoiceRef());
                map().setValue(source.getValue());
                map().setDescription(source.getDescription());
                map().setIsScrap(source.getIsScrap());
			}
		};
		assetModelMapper.addMappings(assetVOToEntityPropertyMap);
		asset = assetModelMapper.map(assetVo, Asset.class);
		
		Model model = null;
		Equipment equipment = null;
		WebMaster webMaster = null;
		Vendor vendor=null;
		Department department=null;
		
		if(assetVo.getWebMasterId()!=0) {
			webMaster = webMasterService.getById(assetVo.getWebMasterId());
			
			asset.setWebMaster(webMaster);
			}else {
				
				asset.setWebMaster(null);
			}
		
		if(assetVo.getAssetSpecificationId()!=0) {
			AssetSpecification assetSpecification= assetSpecificationRepository.getOne(assetVo.getAssetSpecificationId());
			
			asset.setAssetSpecification(assetSpecification);
			}else {
				
				asset.setAssetSpecification(null);
			}
		
		if(assetVo.getDepartmentId()!=0) {
			department=departmentRepo.findByDepartmentId(assetVo.getDepartmentId());
			asset.setDepartment(department);
		}else {
			asset.setDepartment(null);
		}
		
		if(assetVo.getEquipmentId()!=0) {
			equipment = equipmentService.getEquipmentById(assetVo.getEquipmentId());
			
			asset.setEquipment(equipment);
			}else {
				
				asset.setEquipment(null);
			}
		/*if(assetVo.getModelId() == null) {
			model = modelService.findByModelName(assetVo.getModelName());
		} else if(assetVo.getModelName() == null) {
			model = modelService.findByModelNo(assetVo.getModelNo());
		} else {
			model = modelService.getModelById(assetVo.getModelId());
		}*/
		
		if(assetVo.getModelId() != 0) {
	       model = modelService.getModelById(assetVo.getModelId());
	       asset.setModel(model);
		}
		
      	if(assetVo.getVendorId()!=null) {
			vendor=vendorService.getVendorById(assetVo.getVendorId());
			
			asset.setVendor(vendor);
		}else {
			asset.setVendor(null);
		}
		
		return asset;

	}
	
	

}
