package com.asys1920.userservice.repository;


import java.util.List;

import com.asys1920.userservice.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
//@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    List<User> findByLastName(@Param("lastName") String name);
    List<User> findAll();
}
