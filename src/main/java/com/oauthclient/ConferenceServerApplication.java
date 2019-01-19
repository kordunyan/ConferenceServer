package com.oauthclient;

import com.oauthclient.config.AppProperties;
import com.oauthclient.config.security.oauth2.GoogleProperties;
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

