package com.titan.irgs.role.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.titan.irgs.enumUtils.EnumUtils;


public enum OperationTypeEnum {
	
	Country,Region;
	
	
	@JsonCreator
	public static OperationTypeEnum FromValue(String value) {
		return EnumUtils.getEnumFromString(OperationTypeEnum.class, value);
	}

	@JsonValue
	public String toJson() {
		return name().toUpperCase();
	}

}
