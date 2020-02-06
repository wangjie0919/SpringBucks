package com.wj.mpDemo.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import org.apache.ibatis.annotations.Result;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User>{
    @TableId(type = IdType.ID_WORKER_STR)
   // @TableId(type = IdType.UUID)
    //@TableId(type = IdType.ID_WORKER)
   // @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @TableField(condition = SqlCondition.LIKE_RIGHT)
    private String name;
    //@TableField(condition = "%s&lt;#{%s}")
    private Integer  age;
    private String email;
    private  Long  managerId;
    private LocalDateTime createTime;
}
