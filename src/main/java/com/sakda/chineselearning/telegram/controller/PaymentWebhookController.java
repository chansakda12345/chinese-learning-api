package com.sakda.chineselearning.telegram.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.telegram.enums.SubscriptionPlan;
import com.sakda.chineselearning.telegram.service.SubscriptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Payment Webhook API", description = "Automated endpoints for KHQR/Bank callbacks")
@RestController
@RequestMapping("/api/v1/payments/webhook")
@RequiredArgsConstructor
public class PaymentWebhookController {
	
	private final SubscriptionService subscriptionService;
	
	@Operation(summary = "Bank calls this when KHQR payment succeeds")
    @PostMapping("/khqr-success")
	public ResponseEntity<ApiResponse<String>> handleKhqrPaymentSuccess(
			@RequestParam String email,
			@RequestParam SubscriptionPlan plan
	) {
		log.info("Received automated KHQR successful payment for email: {}, plan: {}", email, plan);
		
		subscriptionService.grantPremiumAccess(email, plan);
		
		return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment processed automatically.",
                        null
                )
        );
	}
	

}
