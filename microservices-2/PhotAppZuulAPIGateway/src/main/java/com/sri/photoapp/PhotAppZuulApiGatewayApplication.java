package com.sri.photoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class PhotAppZuulApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotAppZuulApiGatewayApplication.class, args);
	}

	@Bean
	public SimpleFilter simpleFilter() {
		return new SimpleFilter();
	}
	
	@Bean
	public Sampler defaultSampler(){
		return Sampler.ALWAYS_SAMPLE;
	}

}
