package com.thecampaignforge.campaignforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CampaignforgeApplication {

	public static void main(final String[] args) {
		SpringApplication.run(CampaignforgeApplication.class, args);
	}
}
