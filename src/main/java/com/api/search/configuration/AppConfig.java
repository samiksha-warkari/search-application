package com.api.search.configuration;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Description : AppConfig supposed to contain the configuration details like
 * swagger, etc.@EnableSwagger2 enables Swagger support for the api
 * documentation.
 * 
 * @author : Samiksha Warkari
 * @Date : 13-Sep-2020
 */
@Configuration
@EnableAsync
@EnableSwagger2
public class AppConfig {
	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
	public RestTemplate restTemplate;

	/**
	 * Description : Returns the RestTemplate object which is required to call
	 * the another rest api call.
	 * 
	 * @author : Samiksha Warkari
	 * @Date : 13-Sep-2020
	 */
	@Bean
	public RestTemplate restTemplate() {
		logger.info("Inside restTemplate method");
		restTemplate = new RestTemplate();
		/*
		 * To convert the any type of content-type (ex. text/javascript;
		 * charset=utf-8, etc) of the rest api response to the application/json.
		 */
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL, MediaType.APPLICATION_JSON));
		restTemplate.getMessageConverters().add(0, converter);
		return restTemplate;
	}

	/**
	 * Description : Configures the swagger for documentation.
	 * 
	 * @author : Samiksha Warkari
	 * @Date : 15-Sep-2020
	 */
	@Bean
	public Docket productApi() {
		logger.info("Inside productApi method");
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.api.search.controller")).build().apiInfo(metaData());
	}

	private ApiInfo metaData() {
		logger.info("Inside metaData method");
		@SuppressWarnings("deprecation")
		ApiInfo apiInfo = new ApiInfo("Search REST API",
				"Spring Boot REST API for searching the data from 3rd party APIs", "1.0", "Terms of service",
				"Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0", null);
		return apiInfo;
	}

	@Bean
	public HttpTraceRepository httpTraceRepository() {
		return new InMemoryHttpTraceRepository();
	}
}
