package com.fabrick.api;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@PropertySource("classpath:api.properties")
public class MyConfiguration {

	@Value("${BaseUrl}")
	String BaseUrl;

	@Value("${Auth-Schema}")
	String authSchema;

	@Value("${Api-Key}")
	String apiKey;

	@Value("${Idchiave}")
	String Idchiave;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		RestTemplate rest = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(30))
				.setReadTimeout(Duration.ofSeconds(30)).build();
		rest.setUriTemplateHandler(new DefaultUriBuilderFactory(BaseUrl));
		return rest;
	}

	@Bean
	public HttpHeaders httpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Auth-Schema", authSchema);
		headers.set("BaseUrl", BaseUrl);
		headers.set("Api-Key", apiKey);
		headers.set("Idchiave", Idchiave);
		return headers;
	}
}