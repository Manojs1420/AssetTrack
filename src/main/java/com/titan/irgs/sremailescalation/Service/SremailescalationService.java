package com.titan.irgs.sremailescalation.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.titan.irgs.sremailescalation.Domain.Sremailescalation;


public interface SremailescalationService {


	Sremailescalation save(Sremailescalation sremailescalation);

	Sremailescalation getSremailescalationById(Long id);

	Sremailescalation update(Sremailescalation sremailescalation);

	Page<Sremailescalation> getAllSremailescalation(Pageable page);

	Sremailescalation getVerticalId(Long verticalId);

	Sremailescalation getVerticalName(String verticalName);

	Page<Sremailescalation> getAllSremailescalation(String days, String escalationLevel, String verticalId,
			String verticalName, Pageable pageable);
	

	
	

}

