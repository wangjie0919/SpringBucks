/*
package com.wj.jpademo;

import com.wj.jpademo.converter.BytesToMoneyConverter;
import com.wj.jpademo.converter.MoneyToBytesConverter;
import com.wj.jpademo.model.Coffee;
import com.wj.jpademo.model.CoffeeOrder;
import com.wj.jpademo.model.OrderState;
import com.wj.jpademo.repository.CoffeeRepository;
import com.wj.jpademo.service.CoffeeOrderService;
import com.wj.jpademo.service.CoffeeService;
import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
@EnableRedisRepositories
@EnableAspectJAutoProxy //开启切面
public class SpringBucksApplication implements ApplicationRunner {
    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeOrderService orderService;



    public static void main(String[] args) {
        SpringApplication.run(SpringBucksApplication.class, args);
    }

    @Bean
    public RedisTemplate<String, Coffee> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Coffee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public LettuceClientConfigurationBuilderCustomizer customizer() {
        return builder -> builder.readFrom(ReadFrom.MASTER_PREFERRED);//配置，优先读主节点
    }

    @Bean
    public RedisCustomConversions redisCustomConversions(){
        return new RedisCustomConversions(Arrays.asList(new MoneyToBytesConverter(),new BytesToMoneyConverter()));
    }

    private void findSimpleFormatFromCache() {
        Optional<Coffee> c = coffeeService.findSimpleCoffeFromCache("mocha");
        log.info("Coffee {}", c);
        for (int i = 0; i < 5; i++) {
            c = coffeeService.findSimpleCoffeFromCache("mocha");
        }

        log.info("Value from Redis: {}", c);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
       // log.info("All Coffee: {}", coffeeRepository.findAll());

        List<Coffee> allCoffee = coffeeService.findAllCoffee();
        Optional<Coffee> latte = coffeeRepository.findByName("latte");
        log.info("coffee:{}",latte);
        */
/*if (latte.isPresent()) {
            CoffeeOrder order = orderService.createOder("Li Lei", latte.get());
            log.info("Update INIT to PAID: {}", orderService.updateOrder(order, OrderState.PAID));
            log.info("Update PAID to INIT: {}", orderService.updateOrder(order, OrderState.INIT));
        }*//*

    }
}*/
