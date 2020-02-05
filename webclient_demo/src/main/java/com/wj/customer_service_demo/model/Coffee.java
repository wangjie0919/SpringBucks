package com.wj.customer_service_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coffee implements Serializable {
    private Long id;
    private String name;
    private Money price; //先用BigDecimal,后用Money
    private Date createTime;
    private Date updateTime;
}