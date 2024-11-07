/*package com.titan.irgs.inventory.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.titan.irgs.inventory.domain.InventoryDetail;
import com.titan.irgs.inventory.repository.InventoryDetailRepository;
import com.titan.irgs.inventory.service.IInventoryDetailService;


@Service
public class InventoryDetailService implements IInventoryDetailService {

private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private InventoryDetailRepository InventoryDetailRepository;
    
	*//**
	 * getAllInventoryDetail -> Method
	 * @param ->none
	 * @return list of saved InventoryDetail entity
	 *//*
	@Override
	public List<InventoryDetail> getAllInventoryDetail() {
		return InventoryDetailRepository.findAll();
	}
    
	*//**
	 * getInventoryDetailById -> Method
	 * @param InventoryDetailId
	 * @return saved InventoryDetail entity
	 *//*
	@Override
	public InventoryDetail getInventoryDetailById(Long inventoryDetailId) {
		InventoryDetail inventoryDetail = InventoryDetailRepository.findById(inventoryDetailId).orElseThrow(()->new EntityNotFoundException("InventoryDetail with InventoryDetailId " + inventoryDetailId + " not found"));
		
		return inventoryDetail;
	}
    
	*//**
	 * saveInventoryDetail ->Method
	 * @param InventoryDetail entity
	 * @return saved InventoryDetail entity
	 *//*
	@Override
	public InventoryDetail saveInventoryDetail(InventoryDetail inventoryDetail) {
		inventoryDetail.setCreatedOn(new Date());
		inventoryDetail.setUpdatedOn(null);
		return InventoryDetailRepository.save(inventoryDetail);
	}
    
	*//**
	 * updateInventoryDetail ->Method
	 * @param InventoryDetail entity
	 * @return updated InventoryDetail entity
	 *//*
	@Override
	public InventoryDetail updateInventoryDetail(InventoryDetail inventoryDetail) {
		InventoryDetail inventoryDetail1 = InventoryDetailRepository.findById(inventoryDetail.getInventoryDetailId()).orElseThrow(()->new EntityNotFoundException("InventoryDetail with InventoryDetailId " + inventoryDetail.getInventoryDetailId() + " not found"));
		
		return InventoryDetailRepository.save(inventoryDetail);
	}
    
	*//**
	 * deleteInventoryDetailById ->Method
	 * @param InventoryDetailId
	 * @return none
	 *//*
	@Override
	public void deleteInventoryDetailById(Long inventoryDetailId) {
		InventoryDetail inventoryDetail = InventoryDetailRepository.findById(inventoryDetailId).orElseThrow(()->new EntityNotFoundException("InventoryDetail with InventoryDetailId " + inventoryDetailId + " not found"));
	
		InventoryDetailRepository.deleteById(inventoryDetailId);
	}
    
}
*/