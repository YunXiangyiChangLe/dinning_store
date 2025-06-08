package com.szg;

import com.szg.entity.User;
import com.szg.mapper.UserMapper;
import com.szg.service.IUserService;
import com.szg.service.impl.ShopServiceImpl;
import com.szg.utils.RedisConstants;
import com.szg.utils.RedisIdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootTest
class HeimaDianpingApplicationTests {
    @Resource
    private ShopServiceImpl shopService;

    @Resource
    private RedisIdWorker redisIdWorker;
    @Resource
    private UserMapper userMapper; // 注入 UserMapper 用于查询所有用户手机号

    @Resource
    private IUserService userService; // 注入 UserService 用于调用登录逻辑

    @Resource
    private StringRedisTemplate stringRedisTemplate; // 注入 StringRedisTemplate 用于手动设置验证码


    private ExecutorService executorService = Executors.newFixedThreadPool(500);

    @Test
    void testSaveDate() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(300);
        Runnable task = () -> {
            for (int i = 0; i < 1; i++) {
                long order = redisIdWorker.nextId("order");
                System.out.println(order);
            }
            countDownLatch.countDown();
        };
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 300; i++) {
            executorService.execute(task);
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();

        System.out.println("花费时间" + (end - begin));
    }

    @Test
    void contextLoads() {
    }

    @Test
    void saveUserTokens() throws IOException {
// 1. 从数据库中查询所有用户的手机号
        // select("phone") 表示只查询 phone 这一列，提高效率
        List<User> users = userMapper.selectList(null);
        List<String> phones = users.stream()
                .map(User::getPhone)
                .collect(Collectors.toList());

        // 指定要写入的文件路径，这里会保存在项目根目录下
        String filePath = "tokens.txt";
        System.out.println("将开始获取 " + phones.size() + " 个用户的 token，并写入到文件: " + filePath);

        // 2. 遍历手机号，模拟登录并获取 token，然后写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String phone : phones) {
                // a. 模拟发送验证码：在 Redis 中手动存入一个验证码
                String code = "123456"; // 使用一个固定的验证码进行测试
                String redisKey = RedisConstants.LOGIN_CODE_KEY + phone;
                stringRedisTemplate.opsForValue().set(redisKey, code);

                // b. 构造登录表单
                com.szg.dto.LoginFormDTO loginForm = new com.szg.dto.LoginFormDTO();
                loginForm.setPhone(phone);
                loginForm.setCode(code);

                // c. 调用登录服务
                // 注意：login 方法的第二个参数是 HttpSession，在单元测试环境中通常为 null
                // 需要确保您的 login 方法能够处理 session 为 null 的情况。
                // 从您的 UserServiceImpl 实现来看，session 仅用于保存验证码，而我们现在直接操作 Redis，
                // 所以传入 null 应该是可行的。
                com.szg.dto.Result result = userService.login(loginForm, null);

                // d. 检查登录是否成功并获取 token
                if (result.getSuccess() && result.getData() != null) {
                    String token = result.getData().toString();
                    // e. 将 token 写入文件，每个 token 占一行
                    writer.write(token);
                    writer.newLine(); // 写入换行符
                    System.out.println("成功获取并写入用户 " + phone + " 的 token。");
                } else {
                    System.out.println("用户 " + phone + " 登录失败: " + result.getErrorMsg());
                }
            }
            System.out.println("所有用户 token 获取并写入完成！");
        } catch (IOException e) {
            System.err.println("写入 token 文件时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
