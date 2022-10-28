package net.example.finance.mybank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class BasicRunningController {
	
	@GetMapping("/")
	public String runningTest() {
		return "Running Testing...xD!!!";
	}
	
}

