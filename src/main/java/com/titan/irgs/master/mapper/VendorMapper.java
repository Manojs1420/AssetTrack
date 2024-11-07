package com.titan.irgs.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.City;
import com.titan.irgs.master.domain.Currency;
import com.titan.irgs.master.domain.State;
import com.titan.irgs.master.domain.Vendor;
import com.titan.irgs.master.domain.VendorType;
import com.titan.irgs.master.service.CurrencyService;
import com.titan.irgs.master.service.ICityService;
import com.titan.irgs.master.service.IStateService;
import com.titan.irgs.master.service.IVendorTypeService;
import com.titan.irgs.master.vo.VendorVO;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.service.IWebMaster;

/**
 * Model Mapper class(i.e, VendorMapper) which is responsible for converting VO(i.e VendorVO) -> DO(i.e Vendor) and vice versia
 * @author 
 *
 */
@Component
public class VendorMapper {
	
	@Autowired
	private ICityService cityService;
	
	@Autowired
	private IStateService iStateService;
	
	@Autowired
	private IVendorTypeService vendorTypeService;
	
	@Autowired
	private IWebMaster webMasterService;
	
	@Autowired
	private CurrencyService currencyService;

	public VendorVO getVoFromEntity(Vendor vendor) {

		VendorVO vendorVo = null;
		ModelMapper vendorVoModelMapper = new ModelMapper();

		PropertyMap<Vendor, VendorVO> vendorEntityToVOPropertyMap = new PropertyMap<Vendor, VendorVO>() {
			@Override
			protected void configure() {
				map().setVendorId(source.getVendorId());
				map().setVendorCode(source.getVendorCode());
				map().setVendorName(source.getVendorName());
				map().setVendorTypeId(source.getVendorType().getVendorTypeId());
				map().setVendorTypeName(source.getVendorType().getVendorTypeName());
				map().setBillingAddress(source.getBillingAddress());
				map().setBillingEmailId(source.getBillingEmailId());
				map().setCityId(source.getCity().getCityId());
				map().setCityName(source.getCity().getCityName());
				map().setContactNumber(source.getContactNumber());
				map().setContactPerson(source.getContactPerson());
				map().setServiceAddress1(source.getServiceAddress1());
				map().setServiceAddress2(source.getServiceAddress2());
				map().setServiceEmailId1(source.getServiceEmailId1());
				map().setServiceEmailId2(source.getServiceEmailId2());
				map().setVendorStatus(source.getVendorStatus());
				map().setPinCode(source.getPinCode());
				map().setCreatedOn(source.getCreatedOn());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
				map().setCurrencyId(source.getCurrency().getCurrencyId());
				map().setCurrencyName(source.getCurrency().getCurrencyName());

			}
		};
		vendorVoModelMapper.addMappings(vendorEntityToVOPropertyMap);
		vendorVo = vendorVoModelMapper.map(vendor, VendorVO.class);
		vendorVo.setStateId(vendor.getState()==null?null:vendor.getState().getStateId());
		vendorVo.setStateName(vendor.getState()==null?null:vendor.getState().getStateName());
		return vendorVo;

	}

	public Vendor getEntityFromVo(VendorVO vendorVo) {

		Vendor vendor = null;
		ModelMapper vendorModelMapper = new ModelMapper();
		
		
		VendorType vendorType = vendorTypeService.getVendorTypeById(vendorVo.getVendorTypeId());
		
		PropertyMap<VendorVO, Vendor> vendorVOToEntityPropertyMap = new PropertyMap<VendorVO, Vendor>() {
			@Override
			protected void configure() {
				map().setVendorCode(source.getVendorCode());
				map().setVendorName(source.getVendorName());
				map().setBillingAddress(source.getBillingAddress());
				map().setBillingEmailId(source.getBillingEmailId());
				map().setContactNumber(source.getContactNumber());
				map().setContactPerson(source.getContactPerson());
				map().setServiceAddress1(source.getServiceAddress1());
				map().setServiceAddress2(source.getServiceAddress2());
				map().setServiceEmailId1(source.getServiceEmailId1());
				map().setServiceEmailId2(source.getServiceEmailId2());
				map().setVendorStatus(source.getVendorStatus());
				map().setPinCode(source.getPinCode());
				map().setCreatedOn(source.getCreatedOn());
				map().setUpdatedOn(source.getUpdatedOn());
				map().setCreatedBy(source.getCreatedBy());
				map().setUpdatedBy(source.getUpdatedBy());
			}
		};
		vendorModelMapper.addMappings(vendorVOToEntityPropertyMap);
		vendor = vendorModelMapper.map(vendorVo, Vendor.class);
		vendor.setVendorType(vendorType);
		City city = null;
		WebMaster webMaster = null;
		State state=null;
		if(vendorVo.getWebMasterId()!=0) {
			webMaster = webMasterService.getById(vendorVo.getWebMasterId());
			
			vendor.setWebMaster(webMaster);
			}else {
				
				vendor.setWebMaster(null);
			}
		if(vendorVo.getCityId() != 0) {
			
			 city = cityService.getCityById(vendorVo.getCityId());
			 vendor.setCity(city);
		}else {
			vendor.setCity(null);
		}
		
		if(vendorVo.getStateId() != 0) {
			
			 state = iStateService.getStateById(vendorVo.getStateId());
			 vendor.setState(state);
		}else {
			vendor.setState(null);
		}
		
		Currency currency=null;
		if(vendorVo.getCurrencyId()!=null) {
			currency=currencyService.getCurrencyById(vendorVo.getCurrencyId());
			vendor.setCurrency(currency);
		}
		
		
       
		return vendor;
	}
}
