package com.wj.jpademo.repository;

import com.wj.jpademo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder,Long> {
    List<CoffeeOrder> findByCustomerOrderById(String customer);
    List<CoffeeOrder> findByItems_Name(String customer);
    Optional<CoffeeOrder> findById(Long id);
}
