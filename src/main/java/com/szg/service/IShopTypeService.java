package com.szg.service;

import com.szg.dto.Result;
import com.szg.entity.ShopType;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IShopTypeService extends IService<ShopType> {

    Result queryAll();

}
