package com.example.userservice.Repository;

import com.example.userservice.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "userRepo")
public interface IUserRepository extends PagingAndSortingRepository<User, Long> {

    @RestResource(path = "get-username")
    public User findByUsername(@Param("nombreU") String username);

    @Query("select u from User u where u.username=?1")
    public User getUsername(String username);

}
