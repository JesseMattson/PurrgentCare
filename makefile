package:
	mvn -B package --file pom.xml -DskipTests

build:
	mvn clean install -DskipTests

run-tests:
	mvn test

compile: package build run-tests

container:
	docker build . --file Dockerfile --tag purrgent-care:latest

start-app:
	mvn spring-boot:run

test-pipeline: compile container

docker-clean:
	docker system prune
