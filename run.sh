#!/bin/bash

echo -e "Building...\n"
mvn clean install

echo -e "Running...\n"
java -jar target/demo-0.0.1-SNAPSHOT.jar
