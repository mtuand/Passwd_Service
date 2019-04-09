package com.braincorp.project.repository;

import com.braincorp.project.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;


public interface UsersRepository extends MongoRepository<Users, String> {
    Users findByuid(int uid);
    List<Users> findAllByname(String name);
    List<Users> findAllByuid(int uid);
    List<Users> findAllBygid(int gid);
    List<Users> findAllBycomment(String comment);
    List<Users> findAllByhome(String home);
    List<Users> findAllByshell(String shell);
}
