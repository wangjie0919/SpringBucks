package com.example.demo;

import com.example.demo.context.TestBean;
import com.example.demo.parent.ParentConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@Slf4j
public class DemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ApplicationContext parentContext = new AnnotationConfigApplicationContext(ParentConfig.class);
		ClassPathXmlApplicationContext childContext = new ClassPathXmlApplicationContext(
				new String[]{"applicationContext.xml"},parentContext //该参数表示其父类（上一级的上下文）
		);
		TestBean bean = parentContext.getBean("testBeanX",TestBean.class);
		bean.hello();  //hello ,parentX

		log.info("===============");

		TestBean bean1 = childContext.getBean("testBeanX",TestBean.class);
		bean1.hello();//有增强，//hello ,Children

		TestBean bean2 = childContext.getBean("testBeanY",TestBean.class);
		bean2.hello(); //无增强，//hello,parentY

	}
}
