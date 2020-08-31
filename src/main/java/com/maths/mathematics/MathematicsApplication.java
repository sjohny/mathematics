package com.maths.mathematics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableAutoConfiguration
@ComponentScan(basePackages={"com.maths.mathematics"})
@EnableJpaRepositories(basePackages="com.maths.mathematics.repositories")
@EnableTransactionManagement
@EntityScan(basePackages="com.maths.mathematics.entities")
@SpringBootApplication
public class MathematicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MathematicsApplication.class, args);
	}

}
