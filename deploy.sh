#!/bin/bash

# Deployment configuration
APP_NAME="wodcrmapi"
APP_JAR="/home/ubuntu/${APP_NAME}.jar"
APP_PROPERTIES="/home/ubuntu/application.properties"
LOG_FILE="/home/ubuntu/app.log"
DEPLOY_LOG="/home/ubuntu/deploy.log"

# Create deploy log
echo "Starting deployment at $(date)" > $DEPLOY_LOG

# Stop existing application
echo "Stopping existing application..." >> $DEPLOY_LOG
pkill -f "${APP_NAME}.jar" || echo "No existing application running" >> $DEPLOY_LOG
sleep 3

# Verify files exist
echo "Verifying files..." >> $DEPLOY_LOG
[ -f "$APP_JAR" ] || { echo "JAR file missing!" >> $DEPLOY_LOG; exit 1; }
[ -f "$APP_PROPERTIES" ] || { echo "Properties file missing!" >> $DEPLOY_LOG; exit 1; }

# Start new application
echo "Starting application..." >> $DEPLOY_LOG
nohup java -jar $APP_JAR --spring.config.location=$APP_PROPERTIES > $LOG_FILE 2>&1 &

# Verify startup
sleep 5
APP_PID=$(pgrep -f "${APP_NAME}.jar")
if [ -z "$APP_PID" ]; then
  echo "Application failed to start!" >> $DEPLOY_LOG
  echo "Last 20 lines of log:" >> $DEPLOY_LOG
  tail -n 20 $LOG_FILE >> $DEPLOY_LOG
  exit 1
else
  echo "Application started successfully with PID: $APP_PID" >> $DEPLOY_LOG
fi

echo "Deployment completed at $(date)" >> $DEPLOY_LOG