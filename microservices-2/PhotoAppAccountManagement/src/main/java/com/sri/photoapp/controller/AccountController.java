package com.sri.photoapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

	@GetMapping("/status/check")
	public String getAccount() {

		return "Hello From Account!";
	}
}
