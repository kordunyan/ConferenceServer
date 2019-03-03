package com.aconference;

import com.aconference.config.AppProperties;
import com.aconference.config.security.oauth2.GoogleProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class, GoogleProperties.class})
public class ConferenceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConferenceServerApplication.class, args);
	}

}

