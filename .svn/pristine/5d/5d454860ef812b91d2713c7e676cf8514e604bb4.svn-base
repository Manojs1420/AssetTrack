package com.titan.irgs.serviceRequest.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.titan.irgs.enumUtils.EnumUtils;

public enum BreakDownTracking {
	
	AMC,NONAMC;

	@JsonCreator
	public static BreakDownTracking FromValue(String value) {
		return EnumUtils.getEnumFromString(BreakDownTracking.class, value);
	}

	@JsonValue
	public String toJson() {
		return name().toUpperCase();
	}

}
