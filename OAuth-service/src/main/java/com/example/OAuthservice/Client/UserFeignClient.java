package com.example.OAuthservice.Client;

import com.example.commonslibrary.Entity.User;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("userRepo/search/get-username")
    public User findByUsername(@RequestParam String nombreU);

    @PutMapping("/userRepo/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id);
}
