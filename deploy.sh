#!/bin/bash

# Stop any existing Spring Boot app
pkill -f "java -jar wodcrmapi.jar"

# Remove old files
rm -f /home/ubuntu/wodcrmapi.jar
rm -f /home/ubuntu/application.properties

# Copy new files
cp target/wodcrmapi.jar /home/ubuntu/
cp src/main/resources/application.properties /home/ubuntu/

# Start the application
nohup java -jar /home/ubuntu/wodcrmapi.jar --spring.config.location=/home/ubuntu/application.properties > /home/ubuntu/app.log 2>&1 &