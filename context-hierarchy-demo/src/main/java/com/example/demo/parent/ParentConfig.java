package com.example.demo.parent;

import com.example.demo.context.TestBean;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy //开启切面
public class ParentConfig {
    @Bean
    public TestBean testBeanX(){
        return new TestBean("parentX");
    }
    @Bean
    public TestBean testBeanY(){
        return new TestBean("parentY");
    }
    @Bean
    public ParentAspect parentAspect(){
        return new ParentAspect();
    }
}
