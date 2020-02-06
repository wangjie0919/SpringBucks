package com.wj.jpademo.rxjava;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 消息处理
 */
public class TestDealMsg {
    public static void main(String[] args) {
     //1、subscribe方法处理 可处理正常消息和错误消息
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .subscribe(System.out::println, System.err::println);
        //2、出现错误时返回默认值
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .onErrorReturn(0)
                .subscribe(System.out::println);
    }
}
