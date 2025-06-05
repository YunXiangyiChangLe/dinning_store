package com.szg.service.impl;

import com.szg.entity.UserInfo;
import com.szg.mapper.UserInfoMapper;
import com.szg.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
