package com.encore.ordering.order.controller;

import com.encore.ordering.order.dto.MemberDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    private final String MEMBER_API = "http://member-service"; // LoadBalanced를 붙였기 때문에 localhost를 안붙여도 됨

    private final RestTemplate restTemplate;

    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/test/resttemplate")
    public void restTemplateTest() {
        String url = MEMBER_API + "/member/1";
        MemberDto members = restTemplate.getForObject(url, MemberDto.class);
        System.out.println(members);
    }

}
