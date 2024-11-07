package com.titan.irgs.master.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.titan.irgs.master.domain.Currency;
import com.titan.irgs.master.vo.CurrencyVO;

@Component
public class CurrencyMapper {

	public CurrencyVO getVoFromEntity(Currency currency) {

		CurrencyVO currencyVO = new CurrencyVO();
		BeanUtils.copyProperties(currency, currencyVO);
		
		return currencyVO;
	}

	public Currency getEntityFromVo(CurrencyVO currencyVO) {

		Currency currency = new Currency();
		BeanUtils.copyProperties(currencyVO, currency);
		
		return currency;
	}
}
