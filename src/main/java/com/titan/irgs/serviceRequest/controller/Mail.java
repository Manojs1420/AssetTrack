package com.titan.irgs.serviceRequest.controller;

import java.util.List;
import java.util.Map;

public class Mail {


	private long id;
	
	private String mailFrom;
	
	private List<String> mailTo;
	
	private List<String> mailCC;
	
	private List<String> mailBCC;
	
	private String mailContent;
	
	private String mailSubject;
	
	private Map<String,Object> model;
	
	private List<String> fileUriLocation;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public List<String> getMailTo() {
		return mailTo;
	}
	public void setMailTo(List<String> mailTo) {
		this.mailTo = mailTo;
	}
	public List<String> getMailCC() {
		return mailCC;
	}
	public void setMailCC(List<String> mailCC) {
		this.mailCC = mailCC;
	}
	public String getMailContent() {
		return mailContent;
	}
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	
	public String getMailSubject() {
		return mailSubject;
	}
	
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	
	
	public List<String> getMailBCC() {
		return mailBCC;
	}
	public void setMailBCC(List<String> mailBCC) {
		this.mailBCC = mailBCC;
	}
	
	
	
	public List<String> getFileUriLocation() {
		return fileUriLocation;
	}
	public void setFileUriLocation(List<String> fileUriLocation) {
		this.fileUriLocation = fileUriLocation;
	}
	public Mail() {
	
	}
	public Map<String, Object> getModel() {
		return model;
	}
	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
	
	@Override
	public String toString() {
		return "Mail [id=" + id + ", mailFrom=" + mailFrom + ", mailTo=" + mailTo + ", mailCC=" + mailCC + ", mailBCC="
				+ mailBCC + ", mailContent=" + mailContent + ", mailSubject=" + mailSubject + ", model=" + model
				+ ", fileUriLocation=" + fileUriLocation + "]";
	}



}
