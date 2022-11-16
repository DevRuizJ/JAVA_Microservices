package com.example.OAuthservice.Client;

import com.example.commonslibrary.Entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("user/userRepo/search/get-username")
    public User findByUsername(@RequestParam String nombreU);
}
