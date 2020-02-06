package com.wj.jpademo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "springbuck-menu",timeToLive =60)
public class CoffeeCache {
    @Id
    private Long id;
    @Indexed //作为索引
    private String name;
    private Money price;
}
