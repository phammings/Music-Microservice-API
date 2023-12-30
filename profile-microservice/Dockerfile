FROM maven:3.8.6-openjdk-11

WORKDIR /workspace

RUN apt-get update && apt-get install dos2unix

COPY . /workspace

RUN mvn -N io.takari:maven:0.7.7:wrapper && \
    dos2unix /workspace/mvnw && \
    chmod +x /workspace/mvnw 


CMD /workspace/mvnw spring-boot:run