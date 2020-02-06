package com.wj.jpademo.service;

import com.wj.jpademo.model.Coffee;
import com.wj.jpademo.model.CoffeeCache;
import com.wj.jpademo.repository.CoffeeCacheRepository;
import com.wj.jpademo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Slf4j
@Service
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeService {

    private static final String CACHE = "spingubucks-menu";

    @Autowired
    private RedisTemplate<String,Coffee> redisTemplate;
    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeCacheRepository coffeeCacheRepository;

        public List<Coffee> getCoffeeByName(List<String> names) {
            return coffeeRepository.findByNameInOrderById(names);
        }
     public Coffee getById(long id){
            return coffeeRepository.getOne(id);
 }
    public Coffee getByName(String name){
            return coffeeRepository.getByName(name);
 }
    public Optional<Coffee> findOneCoffee2(String name){
        //注意example的使用方式
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name",exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder()
        .name(name).build(),matcher));
        log.info("Coffee found:{}",coffee);
        return  coffee;
    }
    public Optional<Coffee> findOneCoffee(String name){
        HashOperations<String,String,Coffee> hashOperations = redisTemplate.opsForHash();
        if (redisTemplate.hasKey(CACHE)&& hashOperations.hasKey(CACHE,name)){
            log.info("Get coffee {} from redis",hashOperations.get(CACHE,name));
            return Optional.of(hashOperations.get(CACHE,name));
        }
        //注意example的使用方式
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name",exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder()
                .name(name).build(),matcher));
        log.info("Coffee found:{}",coffee);
        if (coffee.isPresent()){
            log.info("Put coffee {} to redis",coffee);
            hashOperations.put(CACHE,name,coffee.get());
            redisTemplate.expire(CACHE,1, TimeUnit.MINUTES); //为缓存设置1分钟的过期时间。注意：一定要设置过期时间
        }
        return  coffee;
    }

    public Optional<Coffee> findSimpleCoffeFromCache(String name){
        Optional<CoffeeCache> cache = coffeeCacheRepository.findOneByName(name);
        if (cache.isPresent()){
            CoffeeCache coffeeCache = cache.get();
            Coffee coffee = Coffee.builder()
                     .name(coffeeCache.getName())
                    .price(coffeeCache.getPrice())
                    .build();
            log.info("Coffee {} found in Cache",coffee);
            return Optional.of(coffee);

        }else {
           Optional<Coffee> coffee = coffeeRepository.findByName(name);
            if (coffee.isPresent()){
                CoffeeCache coffeeCache = CoffeeCache.builder()
                        .name(coffee.get().getName())
                        .price(coffee.get().getPrice())
                        .build();
                log.info("save coffeecache {} to cache",coffeeCache);
                coffeeCacheRepository.save(coffeeCache);

            }

            return coffee;
        }

    }
    @Cacheable //开启缓存
    public List<Coffee> findAllCoffee(){
        return coffeeRepository.findAll();
    }
    @CacheEvict
    public void reload(){

    }
    public Coffee saveCoffee(String name, Money price) {
        return coffeeRepository.save(Coffee.builder().name(name).price(price).build());
    }
}
