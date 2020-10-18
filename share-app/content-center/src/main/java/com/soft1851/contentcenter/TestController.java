package com.soft1851.contentcenter;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.soft1851.contentcenter.domain.dto.UserDTO;
import com.soft1851.contentcenter.feignclient.TestUserCenterFeignClient;
import com.soft1851.contentcenter.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;
import java.util.Random;


@Slf4j
@RestController
@RequestMapping(value = "/test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    private final DiscoveryClient discoveryClient;

    private final RestTemplate restTemplate;

    private final TestUserCenterFeignClient testUserCenterFeignClient;

    private final TestService testService;

    @GetMapping("/test-a")
    public String testA() {
        this.testService.commonMethod();
        return "test-a";
    }

    @GetMapping("/test-b")
    public String testB() {
        this.testService.commonMethod();
        return "test-b";
    }

    @GetMapping("byResource")
    @SentinelResource(value = "hello", blockHandler = "handlerException")
    public String byResource() {
        return "按名称限流";
    }

    public String handlerException(BlockException blockException) {
        RestTemplate restTemplate = new RestTemplate();
        String object = restTemplate.getForObject("http://localhost:8888/test/byResource", String.class);
        return "服务不可用";
    }

    @GetMapping("/discovery")
    public List<ServiceInstance> getInstances() {
        return this.discoveryClient.getInstances("user-center");
    }

    @GetMapping("/call/hello")
    public String callUserCenter() {
        // 用户中心所有的实例信息
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        ServiceInstance serviceInstance = instances.get(new Random().nextInt(instances.size()));
        String targetUrl = serviceInstance.getUri() + "/user/hello";
        //stream编程，Lambda表达式，函数式编程
//        String targetUrl = instances.stream()
//                .map(instance -> instance.getUri().toString() + "/user/hello")
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("当前没有实例！"));
        log.info("请求的目的地址：{}", targetUrl);
        return restTemplate.getForObject(targetUrl, String.class);
    }

    @GetMapping(value = "/call/ribbon")
    public String callByRibbon() {
        return restTemplate.getForObject("http://user-center/user/hello", String.class);
    }

    @GetMapping(value = "/test-q")
    public UserDTO query(UserDTO userDTO) {
        return testUserCenterFeignClient.query(userDTO);
    }

}
