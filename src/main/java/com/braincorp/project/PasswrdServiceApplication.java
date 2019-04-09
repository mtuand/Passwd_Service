package com.braincorp.project;

import com.braincorp.project.model.Users;
import com.braincorp.project.repository.GroupsRepository;
import com.braincorp.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PasswrdServiceApplication{

	public static void main(String[] args) {

		SpringApplication.run(PasswrdServiceApplication.class, args);
	}

}
