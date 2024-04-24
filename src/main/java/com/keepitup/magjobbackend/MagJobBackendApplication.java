package com.keepitup.magjobbackend;

import com.keepitup.magjobbackend.configuration.KeycloakController;
import com.keepitup.magjobbackend.configuration.KeycloakSecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MagJobBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagJobBackendApplication.class, args);
		new KeycloakController(new KeycloakSecurityUtil());
	}

	@Bean
	public RestTemplate restTemplate(@Value("${keepitup.magjob.backend.url}") String baseUrl) {
		RestTemplate template = new RestTemplateBuilder().rootUri(baseUrl).build();
		template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		return template;
	}

}
