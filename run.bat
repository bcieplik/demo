@echo off

echo "Building..."
call mvn clean install

echo "Running..."
java -jar target\demo-0.0.1-SNAPSHOT.jar
