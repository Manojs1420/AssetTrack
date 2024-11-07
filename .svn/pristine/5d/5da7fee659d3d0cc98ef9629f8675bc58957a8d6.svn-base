package com.titan.irgs.master.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.master.domain.Engineer;

/**
 * This is service layer(i.e, service provider) which will interact with DAO layer(i.e, Engineer domain class).
 * This is EngineerService interface(i.e, custom interface) which has CRUD method specification for Engineer domain class.
 * It is responsible to provide service(i.e engineer related data) to engineer web page and vice-versa
 * 
 * @author 
 *
 */
public interface IEngineerService {
	
	Page<Engineer> getAllEngineer(String engineerName, String emailId, String mobileNo, String vendor, Pageable page);

	Engineer getEngineerById(Long engineerId);

	Engineer saveEngineer(Engineer engineer);

	Engineer updateEngineer(Engineer engineer);

	void deleteEngineerById(Long engineerId);

	List<Engineer>findByVendorId(long vendorId);

	Engineer findByEngineerName(String engineerName);

	List<Engineer> getAllEnginerByVendorId(Long id);

}
