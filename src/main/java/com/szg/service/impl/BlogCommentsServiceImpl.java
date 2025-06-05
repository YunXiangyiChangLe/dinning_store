package com.szg.service.impl;

import com.szg.entity.BlogComments;
import com.szg.mapper.BlogCommentsMapper;
import com.szg.service.IBlogCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments> implements IBlogCommentsService {

}
