package com.titan.irgs.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titan.irgs.role.domain.ApplicationAccess;



public interface ApplicationAccessRepo extends JpaRepository<ApplicationAccess, Long>{

}