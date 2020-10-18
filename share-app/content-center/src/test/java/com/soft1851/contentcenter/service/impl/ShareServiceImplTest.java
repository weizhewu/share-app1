package com.soft1851.contentcenter.service.impl;

import com.soft1851.contentcenter.domain.dto.ExchangeDTO;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ShareServiceImplTest {
    private final ShareServiceImpl shareService;

    @Test
    void exchange() {
        ExchangeDTO exchangeDTO = ExchangeDTO.builder()
                .shareId(4)
                .userId(16).build();
        System.out.println(shareService.exchange(exchangeDTO));
    }
}