package com.example.demo.repository;

import com.example.demo.model.Coffee;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface CoffeeRepository extends R2dbcRepository<Coffee, Long> {
    @Query("select * from t_coffee where name=$1")
    Mono<Coffee> findByName(String name);
}