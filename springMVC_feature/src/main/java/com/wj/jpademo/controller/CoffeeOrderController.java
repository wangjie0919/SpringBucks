package com.wj.jpademo.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wj.jpademo.controller.request.NewOrderRequest;
import com.wj.jpademo.model.Coffee;
import com.wj.jpademo.model.CoffeeOrder;
import com.wj.jpademo.service.CoffeeOrderService;
import com.wj.jpademo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

//@RestController
@Controller
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {
    @Autowired
    private CoffeeOrderService orderService;
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/{id}")
    @ResponseBody
    public CoffeeOrder getOrder(@PathVariable("id") Long id) {
        return orderService.get(id);
    }

    @PostMapping(path = "/",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrder){
        log.info("Receive new Order {}",newOrder);
        Coffee [] coffees = coffeeService.getCoffeeByName(newOrder.getItems()).toArray(new Coffee[]{});
        return orderService.createOrder(newOrder.getCustomer(),coffees);

    }

    @ModelAttribute
    public List<Coffee>  coffeeList(){ //缓存到ModelMap中
        return coffeeService.findAllCoffee();
    }

    @GetMapping(path = "/") //访问根路径，呈现视图
    public ModelAndView showCreateForm(){
        return new ModelAndView("create_order_form");
    }
    @PostMapping(path = "/",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String createOrder(@Valid NewOrderRequest newOrder, BindingResult result, ModelMap modelMap){
        if (result.hasErrors()){
            log.warn("Bind Result:{}",result);
            modelMap.addAttribute("message",result.toString());
            return "create_order_form";
        }
        log.info("Receive new Order {}",newOrder);
        Coffee [] coffees = coffeeService.getCoffeeByName(newOrder.getItems()).toArray(new Coffee[]{});
        CoffeeOrder order = orderService.createOrder(newOrder.getCustomer(), coffees);
        return "redirect:/order/"+order.getId();
    }
}