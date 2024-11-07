package com.titan.irgs.sremailescalation.Mapper;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.titan.irgs.sremailescalation.Domain.SrNotificationEmail;
import com.titan.irgs.sremailescalation.Model.SrNotificationEmailCcModel;
import com.titan.irgs.sremailescalation.Model.SrNotificationEmailModel;
import com.titan.irgs.sremailescalation.Model.SrNotificationEmailToModel;



@Component
public class SrNotificationEmailMapper {

	public SrNotificationEmail convertModeltoDomain(SrNotificationEmailModel srNotificationEmailModel) {
		SrNotificationEmail srNotificationEmail = new SrNotificationEmail();
		BeanUtils.copyProperties(srNotificationEmailModel, srNotificationEmail);
		return srNotificationEmail;
	
	}
	
	public SrNotificationEmailModel convertDomainToModel(SrNotificationEmail srnotificationemail) {
		List<SrNotificationEmailToModel>escalationModels=new ArrayList<SrNotificationEmailToModel>();
		SrNotificationEmailModel srnotificationemailModel = new SrNotificationEmailModel();
		BeanUtils.copyProperties(srnotificationemail, srnotificationemailModel);
		srnotificationemail.getSrNotificationEmailTo().forEach(srnotificationemail1->{	
			SrNotificationEmailToModel escalationModel=new SrNotificationEmailToModel();
			escalationModel.setVerticalName(srnotificationemail1.getVerticalName()==null?srnotificationemail1.getWebRole().getWebMaster().getWebMasterName():srnotificationemail1.getVerticalName());
			escalationModel.setWebRoleId(srnotificationemail1.getWebRole().getWebRoleId());
			escalationModel.setSrnotificationemailId(srnotificationemail1.getSrNotificationEmail().getSrnotificationemailId());
			escalationModel.setVerticalId(srnotificationemail1.getVerticalId()==null?srnotificationemail1.getWebRole().getWebMaster().getWebMasterId():srnotificationemail1.getVerticalId());
			escalationModel.setSrnotificationemailtoId(srnotificationemail1.getSrnotificationemailtoId());
			escalationModel.setRoleName(srnotificationemail1.getRoleName()==null?srnotificationemail1.getWebRole().getRole().getRoleName():srnotificationemail1.getRoleName());
			escalationModels.add(escalationModel);
		});
		srnotificationemailModel.setSrNotificationEmailToModel(escalationModels);
		List<SrNotificationEmailCcModel>escalationModels1=new ArrayList<SrNotificationEmailCcModel>();		
		srnotificationemail.getSrNotificationEmailCc().forEach(srnotificationemail2->{
			
			SrNotificationEmailCcModel escalationModel1=new SrNotificationEmailCcModel();
			escalationModel1.setVerticalName(srnotificationemail2.getVerticalName()==null?srnotificationemail2.getWebRole().getWebMaster().getWebMasterName():srnotificationemail2.getVerticalName());
			escalationModel1.setWebRoleId(srnotificationemail2.getWebRole().getWebRoleId());
			escalationModel1.setSrnotificationemailId(srnotificationemail2.getSrNotificationEmail().getSrnotificationemailId());
			escalationModel1.setVerticalId(srnotificationemail2.getVerticalId()==null?srnotificationemail2.getWebRole().getWebMaster().getWebMasterId():srnotificationemail2.getVerticalId());
			escalationModel1.setSrnotificationemailccId(srnotificationemail2.getSrnotificationemailccId());
			escalationModel1.setRoleName(srnotificationemail2.getRoleName()==null?srnotificationemail2.getWebRole().getRole().getRoleName():srnotificationemail2.getRoleName());
			escalationModels1.add(escalationModel1);
		});
		srnotificationemailModel.setSrNotificationEmailCcModel(escalationModels1);
		return srnotificationemailModel;
	
	}

}
