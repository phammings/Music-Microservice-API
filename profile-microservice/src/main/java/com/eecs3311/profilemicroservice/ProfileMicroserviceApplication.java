package com.eecs3311.profilemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;

/**
 * Main class for the Profile Microservice Application.
 * This class is responsible for bootstrapping and configuring the Spring Boot application
 * and initializing connections to the Neo4j database.
 *
 * <p>It sets up the necessary database drivers and establishes a connection to the Neo4j database.
 * It initializes the profile and playlist databases by calling the respective
 * initialization methods in {@link ProfileDriverImpl} and {@link PlaylistDriverImpl}.
 *
 * <p>You have to run this class as a Java application to start the Profile Microservice.
 */
@SpringBootApplication
public class ProfileMicroserviceApplication {
	public static String dbUri = "bolt://myneo4j:7687";
	public static Config config = Config.builder().withoutEncryption().build();
    public static Driver driver = GraphDatabase.driver(dbUri, AuthTokens.basic("neo4j","12345678"), config);

	/**
	 * The main method used to run the Profile Microservice Application.
	 *
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProfileMicroserviceApplication.class, args);
		
		ProfileDriverImpl.InitProfileDb();
		PlaylistDriverImpl.InitPlaylistDb();
		
		System.out.println("Profile service is running on port 8080");
	}
}

