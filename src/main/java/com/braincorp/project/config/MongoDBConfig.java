package com.braincorp.project.config;


import com.braincorp.project.model.Groups;
import com.braincorp.project.model.Users;
import com.braincorp.project.repository.GroupsRepository;
import com.braincorp.project.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableMongoRepositories(basePackageClasses = {UsersRepository.class, GroupsRepository.class})
@Configuration
public class MongoDBConfig {
    @Bean
    CommandLineRunner commandLineRunner(UsersRepository usersRepository, GroupsRepository groupsRepository) {
        return strings -> {
            usersRepository.save(new Users("root", 0, 0, "root", "/root", "/bin/bash"));
            usersRepository.save(new Users("dwoodlins", 1001, 1001, "", "/home/dwoodlins", "/bin/false"));
            groupsRepository.save(new Groups("docker", 1002, "dwoodlins"));
            groupsRepository.save(new Groups("_analyticsusers", 250, "_analyticsd"));
        };
    }

}

