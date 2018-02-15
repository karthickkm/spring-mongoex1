package com.springmongo.springmongoex1;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Collections;

@SpringBootApplication
public class SpringMongoex1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringMongoex1Application.class, args);
	}

	/*public @Bean
	MongoClient mongoClient() {
		return new MongoClient("localhost");
	}*/

	@Bean
	public MongoClient mongoClient() {
		return new MongoClient(Collections.singletonList(new ServerAddress("localhost", 27017)));
	}

	/*@Bean
	public MongoClient mongoClient() {
		return new MongoClient(Collections.singletonList(new ServerAddress("localhost", 27017)),
				Collections.singletonList(MongoCredential.createCredential("", "Hotel", "".toCharArray())));
	}*/

	public @Bean
	MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), "Hotel");
	}

}
