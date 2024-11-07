package com.titan.irgs.sremailescalation.Mapper;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.titan.irgs.customException.ResourceNotFoundException;
import com.titan.irgs.sremailescalation.Domain.Sremailescalation;
import com.titan.irgs.sremailescalation.Model.EscalationModel;
import com.titan.irgs.sremailescalation.Model.EscalationModel1;
import com.titan.irgs.sremailescalation.Model.SremailescalationModel;
import com.titan.irgs.webMaster.domain.WebMaster;
import com.titan.irgs.webMaster.repository.WebMasterRepo;
import com.titan.irgs.webMaster.service.IWebMaster;
import com.titan.irgs.webMaster.serviceImpl.WebMasterService;


@Component
public class SremailescalationMapper {
	@Autowired
	IWebMaster iWebMaster;
	@Autowired
	WebMasterRepo webMasterRepo;

	@Autowired
	WebMasterService webMasterService;
	public Sremailescalation convertModeltoDomain(SremailescalationModel sremailescalationModel) {
		Sremailescalation sremailescalation = new Sremailescalation();
		BeanUtils.copyProperties(sremailescalationModel, sremailescalation);
		sremailescalation.setWebMaster(webMasterRepo.findById(sremailescalationModel.getVerticalId()).orElseThrow(()->new ResourceNotFoundException("The Web Master Id  not found")));

		return sremailescalation;
	
	}
	
	public SremailescalationModel convertDomainToModel(Sremailescalation sremailescalation) {
		List<EscalationModel>escalationModels=new ArrayList<EscalationModel>();
		SremailescalationModel sremailescalationModel = new SremailescalationModel();

		BeanUtils.copyProperties(sremailescalation, sremailescalationModel);
		WebMaster webMaster = webMasterService.getById(sremailescalation.getWebMaster().getWebMasterId());
		sremailescalationModel.setVerticalId(webMaster.getWebMasterId());
		sremailescalationModel.setVerticalName(webMaster.getWebMasterName());
		
		sremailescalation.getEscalations().forEach(sremailescalation1->{	
			EscalationModel escalationModel=new EscalationModel();
			escalationModel.setVerticalName(sremailescalation1.getSremailescalation().getWebMaster().getWebMasterName());
			escalationModel.setVerticalId(sremailescalation1.getSremailescalation().getWebMaster().getWebMasterId());
	//		escalationModel.setVerticalName(sremailescalation1.getVerticalName()==null?sremailescalation1.getWebRole().getWebMaster().getWebMasterName():sremailescalation1.getVerticalName());
			escalationModel.setWebRoleId(sremailescalation1.getWebRole().getWebRoleId());
			escalationModel.setSremailescalationId(sremailescalation1.getSremailescalation().getSremailescalationId());
	//		escalationModel.setVerticalId(sremailescalation1.getVerticalId()==null?sremailescalation1.getWebRole().getWebMaster().getWebMasterId():sremailescalation1.getVerticalId());
			escalationModel.setEscalationId(sremailescalation1.getEscalationId());
			escalationModel.setRoleName(sremailescalation1.getRoleName()==null?sremailescalation1.getWebRole().getRole().getRoleName():sremailescalation1.getRoleName());
			escalationModels.add(escalationModel);
		});
		sremailescalationModel.setEscalation(escalationModels);
		List<EscalationModel1>escalationModels1=new ArrayList<EscalationModel1>();		
		sremailescalation.getEscalations1().forEach(sremailescalation2->{
			
			EscalationModel1 escalationModel1=new EscalationModel1();
			escalationModel1.setVerticalName(sremailescalation2.getSremailescalation().getWebMaster().getWebMasterName());
			escalationModel1.setVerticalId(sremailescalation2.getSremailescalation().getWebMaster().getWebMasterId());
	//		escalationModel1.setVerticalName(sremailescalation2.getVerticalName()==null?sremailescalation2.getWebRole().getWebMaster().getWebMasterName():sremailescalation2.getVerticalName());

			escalationModel1.setWebRoleId(sremailescalation2.getWebRole().getWebRoleId());
			escalationModel1.setSremailescalationId(sremailescalation2.getSremailescalation().getSremailescalationId());
			escalationModel1.setEscalationId1(sremailescalation2.getEscalationId1());
			escalationModel1.setRoleName(sremailescalation2.getRoleName()==null?sremailescalation2.getWebRole().getRole().getRoleName():sremailescalation2.getRoleName());
			escalationModels1.add(escalationModel1);
		});
		sremailescalationModel.setEscalation1(escalationModels1);
		return sremailescalationModel;
	
	}

}
