package com.wj.jpademo.repository;

import com.wj.jpademo.model.Coffee;
import com.wj.jpademo.model.CoffeeCache;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CoffeeCacheRepository extends CrudRepository<CoffeeCache,Long> {
    Optional<CoffeeCache> findOneByName(String name);
}
