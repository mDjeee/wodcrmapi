#!/bin/bash

# Stop any existing running application
pkill -f "java -jar wodcrmapi.*.jar" || true

# Remove old files (if they exist)
rm -f /home/ubuntu/wodcrmapi.jar
rm -f /home/ubuntu/application.properties

# Move new files to proper location
mv /home/ubuntu/wodcrmapi-*.jar /home/ubuntu/wodcrmapi.jar
cp /home/ubuntu/application.properties /home/ubuntu/  # If needed

# Start the application
nohup java -jar /home/ubuntu/wodcrmapi.jar > /home/ubuntu/app.log 2>&1 &