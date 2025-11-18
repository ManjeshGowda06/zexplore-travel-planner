package com.zexplore.travelplanner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ZexploreTravelPlannerApplication {

	public static void main(String[] args) {

		SpringApplication.run(ZexploreTravelPlannerApplication.class, args);
		log.info("Application Zexplore TravelPlanner started...!");

	}

}
