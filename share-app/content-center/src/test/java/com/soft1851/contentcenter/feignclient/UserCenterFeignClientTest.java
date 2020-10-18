package com.soft1851.contentcenter.feignclient;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserCenterFeignClientTest {
    private final UserCenterFeignClient userCenterFeignClient;

    @Test
    void findUserById() {
        System.out.println(userCenterFeignClient.findUserById(16));
    }
}