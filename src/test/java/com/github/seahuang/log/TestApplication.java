package com.github.seahuang.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.seahuang.log.spring.EnableLoggable;

//@EnableLoggable(logDuration=true)
@SpringBootApplication
public class TestApplication {
	
	public static void main(String[] args){
		SpringApplication.run(TestApplication.class, args);
	}
}
