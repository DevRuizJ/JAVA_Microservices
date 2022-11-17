package com.example.OAuthservice.Client;

import com.example.commonslibrary.Entity.User;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("user/userRepo/search/get-username")
    public User findByUsername(@Param("nombreU") String nombreU);
}
