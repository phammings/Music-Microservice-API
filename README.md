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
  </ol>
</details>


<!-- ABOUT THE PROJECT -->
## About The Project


The project focuses on a backend Java API with two microservices, interfacing with MongoDB and Neo4j databases. Emphasising real-world scenarios, the project aims to explore NoSQL and Graph databases, implement background logic for REST API endpoints, and extend functionality through new features of a Spotify music webservice clone. Adhering to specific requirements for the Song microservice application such as adding songs to a playlist, liking songs, creating profiles, etc. Also git usage, code style conventions, and testing with tools like Curl or Postman are essential components will be implemented. 

The application will have two important components which includes profile microservice and song microservice that will be implemented using all the different tools, frameworks, and design principles we discussed. The application will emphasize on database and node based relationships between different components, in an attempt to recreate industry practices and how business create large scale products.


<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [![Java][Java.java]][Java-url]
* [![SpringBoot][SpringBoot]][Spring-url]
* [![Maven][Maven]][Maven-url]
* [![MongoDB][MongoDB]][MongoDB-url]
* [![Neo4j][Neo4j]][Neo4j-url]

### Tested With

* [![Postman][Postman]][Postman-url]


<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap

- ✔️ Explore NoSQL (MongoDB) / Graph Database (Neo4j)
- ✔️ To practice the implementation of background business logic to support REST API endpoints that provide access to read and modify data in a variety of databases
- ✔️ Practice Software Architecture, in particular Server/Client model
- ✔️ Learn how to extend functionality of a software project by adding new features.
- ✔️ Practice using a build automation tool such as Maven


<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- INSTRUCTIONS -->
## Instructions

- Run both ProfileMicroserviceApplication.java (runs on port 3002) and SongMicroserviceApplication (runs on port 3001):
  - _**Profile Microservice:**_
    - Database username must be "neo4j"
    - Database password must be "12345678"
    - Profile Data must be connected to port 7687
  - _**Song Microservice:**_
    - Database must be called "eecs3311-test"
    - Collection must be called "songs"
      - Running ```import-songs-db.sh``` will create the database and import ```MOCK_DATA.json```
    - Song Database must be connected to port 27017
    
    
<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- DEMO -->
## Video Demo

[![](https://markdown-videos-api.jorgenkh.no/youtube/JfKhoIC_kjw)](https://youtu.be/JfKhoIC_kjw)


<p align="right">(<a href="#readme-top">back to top</a>)</p>


[Java.java]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://developer.mozilla.org/en-US/docs/Web/JavaScript
[Maven]: https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white
[Maven-url]: https://tailwindcss.com/
[SpringBoot]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://developer.mozilla.org/en-US/docs/Web/HTML
[MongoDB]: https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white
[MongoDB-url]: https://developer.mozilla.org/en-US/docs/Web/HTML
[Neo4j]: https://img.shields.io/badge/Neo4j-008CC1?style=for-the-badge&logo=neo4j&logoColor=white
[Neo4j-url]: https://developer.mozilla.org/en-US/docs/Web/HTML
[Postman]: https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white
[Postman-url]: https://developer.mozilla.org/en-US/docs/Web/HTML