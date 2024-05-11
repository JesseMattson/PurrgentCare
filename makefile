package:
	mvn -B package --file pom.xml -DskipTests

build:
	mvn clean install -DskipTests

container:
	docker build . --file Dockerfile --tag purrgent-care:latest

test:
	mvn test

run:
	mvn spring-boot:run

pipeline: package build test container

docker-clean:
	docker system prune
