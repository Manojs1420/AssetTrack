package com.titan.irgs.master.enums;

public enum OptoAlloted {
	
	ACTIVE {
		@Override
		public String toString() {
			return "ACTIVE";
		}
	}, 
	
	INACTIVE {
		@Override
		public String toString() {
			return "INACTIVE";
		}
		
	},
	
	NA {
		@Override
		public String toString() {
			return "NA";
		}
		
	};


}
