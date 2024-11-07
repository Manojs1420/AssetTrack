package com.titan.irgs.sremailescalation.Mapper;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.titan.irgs.sremailescalation.Domain.AmcNotification;
import com.titan.irgs.sremailescalation.Model.AmcNotificationEmailCcModel;
import com.titan.irgs.sremailescalation.Model.AmcNotificationEmailToModel;
import com.titan.irgs.sremailescalation.Model.AmcNotificationModel;



@Component
public class AmcNotificationMapper {

	public AmcNotification convertModeltoDomain(AmcNotificationModel srNotificationEmailModel) {
		AmcNotification srNotificationEmail = new AmcNotification();
		BeanUtils.copyProperties(srNotificationEmailModel, srNotificationEmail);
		return srNotificationEmail;
	
	}
	
	public AmcNotificationModel convertDomainToModel(AmcNotification srnotificationemail) {
		List<AmcNotificationEmailToModel>escalationModels=new ArrayList<AmcNotificationEmailToModel>();
		AmcNotificationModel srnotificationemailModel = new AmcNotificationModel();
		BeanUtils.copyProperties(srnotificationemail, srnotificationemailModel);
		srnotificationemail.getAmcNotificationEmailTo().forEach(srnotificationemail1->{	
			AmcNotificationEmailToModel escalationModel=new AmcNotificationEmailToModel();
			escalationModel.setVerticalName(srnotificationemail1.getVerticalName()==null?srnotificationemail1.getWebRole().getWebMaster().getWebMasterName():srnotificationemail1.getVerticalName());
			escalationModel.setWebRoleId(srnotificationemail1.getWebRole().getWebRoleId());
			escalationModel.setAmcnotificationId(srnotificationemail1.getAmcNotification().getAmcnotificationId());
			escalationModel.setVerticalId(srnotificationemail1.getVerticalId()==null?srnotificationemail1.getWebRole().getWebMaster().getWebMasterId():srnotificationemail1.getVerticalId());
			escalationModel.setAmcnotificationemailtoId(srnotificationemail1.getAmcnotificationemailtoId());
			escalationModel.setRoleName(srnotificationemail1.getRoleName()==null?srnotificationemail1.getWebRole().getRole().getRoleName():srnotificationemail1.getRoleName());
			escalationModels.add(escalationModel);
		});
		srnotificationemailModel.setAmcNotificationEmailToModel(escalationModels);
		List<AmcNotificationEmailCcModel>escalationModels1=new ArrayList<AmcNotificationEmailCcModel>();		
		srnotificationemail.getAmcNotificationEmailCc().forEach(srnotificationemail2->{
			
			AmcNotificationEmailCcModel escalationModel1=new AmcNotificationEmailCcModel();
			escalationModel1.setVerticalName(srnotificationemail2.getVerticalName()==null?srnotificationemail2.getWebRole().getWebMaster().getWebMasterName():srnotificationemail2.getVerticalName());
			escalationModel1.setWebRoleId(srnotificationemail2.getWebRole().getWebRoleId());
			escalationModel1.setAmcnotificationId(srnotificationemail2.getAmcNotification().getAmcnotificationId());
			escalationModel1.setVerticalId(srnotificationemail2.getVerticalId()==null?srnotificationemail2.getWebRole().getWebMaster().getWebMasterId():srnotificationemail2.getVerticalId());
			escalationModel1.setAmcnotificationemailccId(srnotificationemail2.getAmcnotificationemailccId());
			escalationModel1.setRoleName(srnotificationemail2.getRoleName()==null?srnotificationemail2.getWebRole().getRole().getRoleName():srnotificationemail2.getRoleName());
			escalationModels1.add(escalationModel1);
		});
		srnotificationemailModel.setAmcNotificationEmailCcModel(escalationModels1);
		return srnotificationemailModel;
	
	}

}
