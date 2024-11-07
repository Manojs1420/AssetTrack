package com.titan.irgs.master.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.Currency;
import com.titan.irgs.master.vo.CurrencyVO;

public interface CurrencyService {

	Currency saveCurrency(Currency currency);

	Currency updateCurrency(Currency currency);

	void deleteCurrencyById(Long currencyId);

	Page<Currency> getAllCurrency(CurrencyVO currencyVO, Pageable page);

	Currency getCurrencyById(Long currencyId);

}
