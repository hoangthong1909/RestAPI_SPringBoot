# syntax=docker/dockerfile:experimental
# Java image
FROM openjdk:11
# Khi container bat len thi tu dong nhay vao thu muc nay
# working directory
WORKDIR /app

EXPOSE 8080
#Copy from Host(PC , Laptop) to container
COPY ./target/RestAPI-0.0.1-SNAPSHOT.jar .

#Run inside container
CMD ["java","-jar", "RestAPI-0.0.1-SNAPSHOT.jar"]