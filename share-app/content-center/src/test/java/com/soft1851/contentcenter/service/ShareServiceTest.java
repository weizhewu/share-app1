package com.soft1851.contentcenter.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ShareServiceTest {
    private final ShareService shareService;

    @Test
    void findById() {
        System.out.println(shareService.findById(1));
    }
}