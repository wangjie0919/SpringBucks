package com.wj.mpDemo.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 常用实体类注解演示
 */
@Data
@TableName("mp_user")
public class User2 {
    @TableId
    private Long id;
    @TableField("name")
    private String realName;
    private Integer  age;
    private String email;
    private  Long  managerId;
    private LocalDateTime createTime;
    //备注信息
    @TableField(exist = false)
    private String remark;

}
