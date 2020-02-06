package com.wj.jpademo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_COFFEE")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true) //需要指明包含父类中的属性
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Coffee extends BaseEntity implements Serializable{

    private String name;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
    parameters = {@org.hibernate.annotations.Parameter(name="currencyCode",value = "CNY")})
    private Money price;

}
