package com.example.OAuthservice.Service;

import com.example.commonslibrary.Entity.User;

public interface IUserService {

    public User findByUsername(String nombreU);

    public User updateUser(User user, Long id);
}
