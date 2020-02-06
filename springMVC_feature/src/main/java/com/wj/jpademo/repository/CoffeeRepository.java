package com.wj.jpademo.repository;

import com.wj.jpademo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoffeeRepository extends JpaRepository<Coffee,Long> {

    public Optional<Coffee> findByName(String name);
    List<Coffee> findByNameInOrderById(List<String> list);


    Coffee getByName(String name);
}
