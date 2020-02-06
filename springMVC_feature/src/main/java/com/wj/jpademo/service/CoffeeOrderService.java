package com.wj.jpademo.service;

import com.wj.jpademo.model.Coffee;
import com.wj.jpademo.model.CoffeeOrder;
import com.wj.jpademo.model.OrderState;
import com.wj.jpademo.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Service
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository orderRepository;

    public CoffeeOrder createOrder(String customer, Coffee...coffees){
        CoffeeOrder coffeeOrder = CoffeeOrder.builder()
                .customer(customer).state(OrderState.INIT)
                .items(new ArrayList<>(Arrays.asList(coffees))).build();
        CoffeeOrder saved = orderRepository.save(coffeeOrder);
        log.info("new Coffeeorder ,{} saved",saved);
        return saved;
    }
    public boolean updateOrder(CoffeeOrder order,OrderState state){
        //判断验证，订单状态不能回退，如不能从paid状态回退为init状态。
        if (state.compareTo(order.getState())<=0){
            log.warn("wrong state orders,{}{}",state,order.getState());
        }
        order.setState(state);
        orderRepository.save(order);
        log.info("update order :{}",order);
        return true;
    }
    public CoffeeOrder get(Long id) {
        return orderRepository.findById(id).get();
    }
}
