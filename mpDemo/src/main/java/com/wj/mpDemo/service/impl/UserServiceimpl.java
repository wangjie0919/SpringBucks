package com.wj.mpDemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.mpDemo.dao.UserMapper;
import com.wj.mpDemo.entity.User;
import com.wj.mpDemo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl extends ServiceImpl<UserMapper,User> implements UserService {

}
