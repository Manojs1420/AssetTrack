package com.titan.irgs.configuration;

public enum AutoTrigger {
	
	SR_Escalation_Email_Scheduler {
		@Override
		public String toString() {
			return "SR_Escalation_Email_Scheduler";
		}
	},

	AUTO_AMCSR_Escalation_Scheduler {
		@Override
		public String toString() {
			return "AUTO_AMCSR_Escalation_Scheduler";
		}
	}
}
