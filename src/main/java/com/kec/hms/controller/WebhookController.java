package com.kec.hms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/webhook")
public class WebhookController {
	
	@PostMapping("/payment")
	public ResponseEntity<String> handlePaymentWebHook(@RequestBody Map<String, Object> payload){
		System.out.println("Webhook received"+ payload);
		
		
		return ResponseEntity.ok("Webhook Received and processed");
	}
	

}
