package com.szg.service;

import com.szg.dto.Result;
import com.szg.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IShopService extends IService<Shop> {

    Result queryById(Long id);
    Result updateShop(Shop shop);
}
