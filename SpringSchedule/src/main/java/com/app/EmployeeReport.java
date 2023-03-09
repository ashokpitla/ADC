package com.app;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmployeeReport {
	@Scheduled(fixedDelay=4000)
	public void getReport() {
		System.out.println("Hi,Ashok");
	}

}
