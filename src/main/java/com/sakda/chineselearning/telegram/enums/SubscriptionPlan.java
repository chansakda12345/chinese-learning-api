package com.sakda.chineselearning.telegram.enums;

public enum SubscriptionPlan {
	
	ONE_MONTH(30),
	SIX_MONTHS(180),
	ONE_YEAR(365);
	
	private final int days;
	
	SubscriptionPlan(int days) {
		this.days = days;
	}
	
	public int getDays() {
		return days;
	}

}
