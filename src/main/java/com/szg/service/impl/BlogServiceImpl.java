package com.szg.service.impl;

import com.szg.entity.Blog;
import com.szg.mapper.BlogMapper;
import com.szg.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

}
