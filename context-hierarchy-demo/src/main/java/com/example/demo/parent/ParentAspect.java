package com.example.demo.parent;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
public class ParentAspect {
    @AfterReturning("bean(testBean*)")
    public void printAfter(){
        System.out.println("after hello");
    }
}
