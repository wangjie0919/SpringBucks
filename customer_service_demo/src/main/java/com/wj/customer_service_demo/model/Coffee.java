package com.wj.customer_service_demo.model;

import lombok.*;
import org.joda.money.Money;

import java.io.Serializable;
import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coffee  implements Serializable {
    private Long id;
    private String name;
    private Money price; //先用BigDecimal,后用Money
    private Date createTime;
    private Date updateTime;
}