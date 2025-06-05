package com.szg.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.szg.dto.Result;
import com.szg.entity.Shop;
import com.szg.mapper.ShopMapper;
import com.szg.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szg.utils.RedisConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryById(Long id) {
        String key = RedisConstants.CACHE_SHOP_KEY;
        //redis查询缓存
        String shopJson = stringRedisTemplate.opsForValue().get(key + id);
        if (StrUtil.isNotEmpty(shopJson)) {
            Shop shop = JSONUtil.toBean(shopJson, Shop.class);
            return Result.ok(shop);
        }

        //不存在缓存查数据库
        Shop shop = getById(id);
        if (shop == null) {
            return Result.fail("店铺不存在！");
        }
        stringRedisTemplate.opsForValue().set(key + id, JSONUtil.toJsonStr(shop),
                RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);

        return Result.ok(shop);
    }

    @Override
    @Transactional
    public Result updateShop(Shop shop) {
        Long shopId = shop.getId();
        if(shopId==null){
            return Result.fail("店铺id不能为空呢！");
        }
        //更新数据库
        updateById(shop);
        //删除缓存
        stringRedisTemplate.delete(RedisConstants.CACHE_SHOP_KEY+shop.getId());
        return Result.ok();
    }
}
