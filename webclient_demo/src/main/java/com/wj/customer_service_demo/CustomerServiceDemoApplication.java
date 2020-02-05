package com.wj.customer_service_demo;

import com.wj.customer_service_demo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@Slf4j
public class CustomerServiceDemoApplication implements ApplicationRunner{

	@Autowired
	public WebClient webClient;

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(CustomerServiceDemoApplication.class) //以当前类作为配置元
				.bannerMode(Banner.Mode.OFF) //不需要Banner
				.web(WebApplicationType.NONE) //不会再启动tomcat
				.run(args);
	}
@Bean
public WebClient webClient(WebClient.Builder builder){
		return builder.baseUrl("http://localhost:8080").build();
}
	@Override
	public void run(ApplicationArguments args) throws Exception {
		CountDownLatch latch = new CountDownLatch(2);

		webClient.get()
				.uri("/coffee/{id}",1)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.retrieve()
				.bodyToMono(Coffee.class)
				.doOnError(t->log.error("error:{}",t.toString()))
				.doFinally(s->latch.countDown())
				.subscribeOn(Schedulers.single())
				.subscribe(c->log.info("Coffee:{}",c));

		Mono<Coffee> americano = Mono.just(
				Coffee.builder()
				.name("americano")
				.price(Money.of(CurrencyUnit.of("CNY"),22.00))
				.build()
		);
      webClient.post()
			  .uri("/coffee/")
			  .body(americano,Coffee.class)
			  .retrieve()
			  .bodyToMono(Coffee.class)
			  .doFinally(s->latch.countDown())
			  .subscribeOn(Schedulers.single())
			  .subscribe(c->log.info("Coffee Created:{}",c));

      latch.await();
      webClient.get()
			  .uri("/coffee/")
			  .retrieve()
			  .bodyToFlux(Coffee.class)
			  .toStream()
			  .forEach(c->log.info("get Coffee:{}",c));
	}
}
