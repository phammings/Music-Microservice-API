package com.eecs3311.songmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Song Microservice application.
 */
@SpringBootApplication
public class SongMicroserviceApplication {

	/**
	 * The entry point for starting the Song Microservice application.
	 *
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SongMicroserviceApplication.class, args);
		System.out.println("Song Microservice is running on port 3001");
	}
}
