package com.titan.irgs.master.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.titan.irgs.master.domain.Currency;
import com.titan.irgs.master.repository.CurrencyRepo;
import com.titan.irgs.master.service.CurrencyService;
import com.titan.irgs.master.vo.CurrencyVO;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService{

	@Autowired
	CurrencyRepo currencyRepo;
	

	@Override
	public Currency saveCurrency(Currency currency) {
		currency.setCreatedOn(new Date());
		currency.setUpdatedOn(null);
		return currencyRepo.save(currency);
	}


	@Override
	public Currency updateCurrency(Currency currency) {
		currency.setUpdatedOn(new Date());
		Currency currency1 = currencyRepo.findById(currency.getCurrencyId()).orElseThrow(()->new EntityNotFoundException("currency with currencyId " + currency.getCurrencyId() + " not found"));
	
		return currencyRepo.save(currency);
	}


	@Override
	public void deleteCurrencyById(Long currencyId) {
		Currency currency = currencyRepo.findById(currencyId).orElseThrow(()->new EntityNotFoundException("currency with currencyId " + currencyId + " not found"));
		
		currencyRepo.deleteById(currencyId);
	}


	@Override
	public Page<Currency> getAllCurrency(CurrencyVO currencyVO, Pageable page) {
		return currencyRepo.findAll(new Specification<Currency>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<Currency> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();



				if (currencyVO.getCurrencyName() != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("currencyName"),"%" +currencyVO.getCurrencyName() + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, page);
	}


	@Override
	public Currency getCurrencyById(Long currencyId) {
		Currency currency = currencyRepo.findById(currencyId).orElseThrow(()->new EntityNotFoundException("currency with currencyId " + currencyId + " not found"));

		return currency;
	}
}
