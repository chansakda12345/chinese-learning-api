package com.sakda.chineselearning.telegram.service;

import com.sakda.chineselearning.telegram.enums.SubscriptionPlan;

public interface SubscriptionService {
	
	void grantPremiumAccess(String email, SubscriptionPlan plan);

}
