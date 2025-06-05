package com.szg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szg.entity.User;
import com.szg.mapper.UserMapper;
import com.szg.service.IUserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
