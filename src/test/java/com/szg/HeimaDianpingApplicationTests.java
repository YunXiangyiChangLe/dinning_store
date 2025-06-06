package com.szg;

import com.szg.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class HeimaDianpingApplicationTests {
    @Resource
    private ShopServiceImpl shopService;

    @Test
    void testSaveDate() throws InterruptedException {
        shopService.saveShop2Redis(1L,10L);
    }
    @Test
    void contextLoads() {
    }

}
