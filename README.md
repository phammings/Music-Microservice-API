# Music-Microservice-API
<a name="readme-top"></a>
<!-- PROJECT LOGO -->
<br />
<div align="center">

<h3 align="center">Music-Microservice-API</h3>

  <p align="center">
Spotify Music Player API clone interfacing through MongoDB and Neo4j, and implementing background logic for REST API endpoints, NoSQL and Graph databases
  </p>
</div>


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#instructions">Instructions</a></li>
    <li><a href="#demo">Video Demo</a></li>
    <li><a href="#api">API Documentation and Use</a></li>
  </ol>
</details>


<!-- ABOUT THE PROJECT -->
## About The Project


The project focuses on a backend Java API with two microservices, interfacing with MongoDB and Neo4j databases. Emphasising real-world scenarios, the project aims to explore NoSQL and Graph databases, implement background logic for REST API endpoints, and extend functionality through new features of a Spotify music webservice clone. Created features for the Song and Profile microservice application such as adding songs to a playlist, liking songs, creating profiles, etc. and using okhttp3 to communicate between the two microservices. Git usage, code style conventions, and testing with tools like Curl or Postman were also essential components implemented. 

The application will emphasize on database and node based relationships between different components, in an attempt to recreate industry practices and how business create large scale products.


<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [![Java][Java.java]][Java-url]
* [![SpringBoot][SpringBoot]][Spring-url]
* [![Maven][Maven]][Maven-url]
* [![MongoDB][MongoDB]][MongoDB-url]
* [![Neo4j][Neo4j]][Neo4j-url]
* [![Docker][Docker]][Docker-url]

### Tested With

* [![Postman][Postman]][Postman-url]
* [![Robot][Robot]][Robot-url]
* [![JUnit5][JUnit5]][JUnit5-url]


<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap

- ✔️ Explore NoSQL (MongoDB) / Graph Database (Neo4j)
- ✔️ To practice the implementation of background business logic to support REST API endpoints that provide access to read and modify data in a variety of databases
- ✔️ Practice Software Architecture, in particular Server/Client model
- ✔️ Learn how to extend functionality of a software project by adding new features.
- ✔️ Practice using a build automation tool such as Maven
- ✔️ Testing HTTP responses with Postman
- ✔️ Built automated test cases with the Robot Framework and JUnit5
- ✔️ Utilize Docker for containerized deployment and easy setup across different environments
- ✔️ Deployed microservices on an AWS EC2 instance, leveraging cloud infrastructure for enhanced scalability, reliability, and
  global accessibility (EC2 instance terminated now due to costs)


<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- INSTRUCTIONS -->
## Instructions

- Ensure Docker in installed on your system
  - See https://docs.docker.com/ for installation instructions
1) Build the Docker image and ensure no previous cache is used:
   - ```docker-compose build --no-cache```
2) Compose the Docker image:
   - ```docker-compose up```
3) Initialize the MongoDB database with: 
   - ```docker-compose exec mongodb mongoimport --db eecs3311-test --jsonArray --collection songs --legacy "/import/MOCK_DATA.json```
- Should you get a "ServiceUnavailibleException" for profile-app:
  - ```docker restart profile```
- Should you get any other errors:
  - ```docker-compose down```
  - Restart your Docker Desktop application
  - Repeat the above steps again
    
<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- DEMO -->
## Video Demo

[![](https://markdown-videos-api.jorgenkh.no/youtube/JfKhoIC_kjw)](https://youtu.be/JfKhoIC_kjw)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- API -->
## API Documentation and Use

<a href="https://documenter.getpostman.com/view/31547597/2s9YeK5WGW">Song Microservice API Documentation</a>

<a href="https://documenter.getpostman.com/view/31547597/2s9YeK5WGV">Profile Microservice API Documentation</a>

<p align="right">(<a href="#readme-top">back to top</a>)</p>


[Java.java]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.java.com/en/
[Maven]: https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white
[Maven-url]: https://maven.apache.org/
[SpringBoot]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/projects/spring-boot
[MongoDB]: https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white
[MongoDB-url]: https://www.mongodb.com/
[Neo4j]: https://img.shields.io/badge/Neo4j-008CC1?style=for-the-badge&logo=neo4j&logoColor=white
[Neo4j-url]: https://neo4j.com/
[Docker]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/
[Postman]: https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white
[Postman-url]: https://www.postman.com/
[Robot]: https://img.shields.io/badge/Robot%20Framework-000000?style=for-the-badge&logo=robot%20framework&logoColor=white
[Robot-url]: https://robotframework.org/
[JUnit5]: https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white
[JUnit5-url]: https://junit.org/junit5/