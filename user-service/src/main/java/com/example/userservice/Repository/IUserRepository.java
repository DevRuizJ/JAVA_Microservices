package com.example.userservice.Repository;

import com.example.userservice.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "userRepo")
public interface IUserRepository extends PagingAndSortingRepository<User, Long> {

    public User findByUsername(String userName);

    @Query("select u from User u where u.username=?1")
    public User getUserName(String userName);

}
