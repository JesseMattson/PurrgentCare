package-backend:
	mvn -B package --file pom.xml -DskipTests

build-backend:
	mvn clean install -DskipTests

test-backend:
	mvn test jacoco:report

open-jacoco-report:
	open target/site/jacoco/index.html

test-and-coverage: test-backend open-jacoco-report

compile-backend: package-backend build-backend test-backend

container-backend:
	docker build . --file Dockerfile --tag purrgent-care:latest

start-backend:
	make build-backend
	mvn spring-boot:run

test-backend-pipeline: compile-backend container-backend

docker-clean:
	docker system prune

build-ui:
	npm --prefix ./purrgent-care-ui install ./purrgent-care-ui

test-ui:
	npm test --prefix ./purrgent-care-ui -- --coverage

start-ui:
	make build-ui
	npm start --prefix ./purrgent-care-ui