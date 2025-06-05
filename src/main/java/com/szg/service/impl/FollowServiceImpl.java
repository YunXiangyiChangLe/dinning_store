package com.szg.service.impl;

import com.szg.entity.Follow;
import com.szg.mapper.FollowMapper;
import com.szg.service.IFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {

}
