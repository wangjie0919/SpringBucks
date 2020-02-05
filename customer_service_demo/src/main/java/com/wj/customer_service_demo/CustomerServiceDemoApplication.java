package com.wj.customer_service_demo;

import com.sun.org.apache.regexp.internal.RE;
import com.wj.customer_service_demo.model.Coffee;
import com.wj.customer_service_demo.support.CustomConnectionKeepAliveStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class CustomerServiceDemoApplication {
	@Autowired
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(CustomerServiceDemoApplication.class) //以当前类作为配置元
				.bannerMode(Banner.Mode.OFF) //不需要Banner
				.web(WebApplicationType.NONE) //不会再启动tomcat
				.run(args);
	}

	/*简单声明RestTemplate类1
	@Bean //构造bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		*//*return builder.build();*//*
		return new RestTemplate();
	}*/

	/**
	 * 声明RestTemplate类2，并设置连接超时时间以及连接工厂为requestFactory
	 * @param builder
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofMillis(100))
				.setReadTimeout(Duration.ofMillis(500))
				.requestFactory(this::requestFactory)
				.build();
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory requestFactory(){
		//定义连接管理类
		PoolingHttpClientConnectionManager connectionManager =
				new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
		connectionManager.setMaxTotal(200);
		connectionManager.setDefaultMaxPerRoute(20);

		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connectionManager)
				.evictIdleConnections(30, TimeUnit.SECONDS) //设置连接空闲时间
				.disableAutomaticRetries() //禁用重试机制
				// 有 Keep-Alive 认里面的值，没有的话永久有效
				//.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
				// 换成自定义的
				.setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
				.build();

		HttpComponentsClientHttpRequestFactory requestFactory =
				new HttpComponentsClientHttpRequestFactory(httpClient);

		return requestFactory;
	}


	private void testRestTemplate() {
		URI uri = UriComponentsBuilder
				.fromUriString("http://localhost:8080/coffee/{id}")
				.build(1);
		ResponseEntity<Coffee> c =  restTemplate.getForEntity(uri,Coffee.class);
		log.info("Response status:{},Response headers:{}",c.getStatusCode(),c.getHeaders());
		log.info("Coffee:{}",c.getBody());

		String coffeeUri = "http://localhost:8080/coffee/";
		Coffee request = Coffee.builder()
				.name("Americano")
				.price(Money.of(CurrencyUnit.of("CNY"),26.00))
				.build();
		Coffee response = restTemplate.postForObject(coffeeUri,request,Coffee.class);
		log.info("New Coffee:{}",response);

		String s = restTemplate.getForObject(coffeeUri,String.class);
		List<Coffee> coffeeList = restTemplate.getForObject(coffeeUri,List.class);
		//log.info("String:{}",s);
		log.info("List:{}",coffeeList);
	}

}
