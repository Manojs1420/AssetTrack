package com.titan.irgs.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Currency;

@Repository
public interface CurrencyRepo extends JpaRepository<Currency, Long>,JpaSpecificationExecutor<Currency>{

	@Query(value="select * from currency where currency_name=:currencyName",nativeQuery = true)
	Currency findByCurrencyName(String currencyName);

}
