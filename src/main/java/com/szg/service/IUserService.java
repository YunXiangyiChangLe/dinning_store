package com.szg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.szg.dto.LoginFormDTO;
import com.szg.dto.Result;
import com.szg.entity.User;

import javax.servlet.http.HttpSession;


public interface IUserService extends IService<User> {

    Result sendCode(String phone, HttpSession session);

    Result login(LoginFormDTO loginForm, HttpSession session);

}
