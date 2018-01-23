package com.scut.originsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(value = "com.scut.originsystem.mapper")
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class OriginsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OriginsystemApplication.class, args);
	}
}
