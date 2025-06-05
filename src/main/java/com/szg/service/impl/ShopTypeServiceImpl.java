package com.szg.service.impl;

import cn.hutool.json.JSONUtil;
import com.szg.dto.Result;
import com.szg.entity.ShopType;
import com.szg.mapper.ShopTypeMapper;
import com.szg.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szg.utils.RedisConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result queryAll() {
        String key = RedisConstants.CACHE_SHOP_TYPE;
        //查询缓存
        String shopTypeJson=stringRedisTemplate.opsForValue().get(key);
        if(shopTypeJson!=null){
            List<ShopType>typeList=JSONUtil.toList(shopTypeJson,ShopType.class);
            return Result.ok(typeList);
        }

        //缓存不存在，取数据库
        List<ShopType> typeList = query().orderByAsc("sort").list();
        if(typeList==null||typeList.size()==0){
            return Result.fail("暂无店铺！");
        }
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(typeList));
        return Result.ok(typeList);
    }
}
