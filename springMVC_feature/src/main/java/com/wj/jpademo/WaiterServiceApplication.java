package com.wj.jpademo;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.wj.jpademo.controller.request.PerformanceInteceptor;
import com.wj.jpademo.model.Coffee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class WaiterServiceApplication implements WebMvcConfigurer{
    public static void main(String[] args) {
        SpringApplication.run(WaiterServiceApplication.class, args);
    }

    /**
     * 实现WebMvcConfigurer.addInterceptors(),添加自定义的拦截器。
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PerformanceInteceptor()).addPathPatterns("/order/**")
        .addPathPatterns("/coffee/**");
    }

    @Bean
    public Hibernate5Module hibernate5Module(){
        return new Hibernate5Module();
    }
    @Bean //起格式化json结果的作用
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
        return builder ->{
            builder.indentOutput(true);
            builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai")); //定制化时区
        };
    }

    @Bean
    public RedisTemplate<String,Coffee> redisTemplate() {
        return redisTemplate();
    }

    @Bean
    public RedisTemplate<String,Coffee> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Coffee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
