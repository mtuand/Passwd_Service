package com.braincorp.project.repository;

import com.braincorp.project.model.Groups;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupsRepository extends MongoRepository<Groups, String> {
    Groups findBygid(int gid);
    List<Groups> findAllBymembers(String members);
    List<Groups> findAllByname(String name);
    List<Groups> findAllBygid(int gid);
}