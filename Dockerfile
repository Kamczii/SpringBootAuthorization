FROM openjdk:17
LABEL maintainer="pawcie"
ADD target/authorization-0.0.1-SNAPSHOT.jar authorization.jar
ENTRYPOINT ["java","-jar", "authorization.jar"]