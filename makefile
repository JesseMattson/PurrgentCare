## Backend section
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
## End Backend section

## Docker section
docker-clean:
	docker system prune
## End Docker section

## UI section
UI_DIRECTORY=./purrgent-care-ui
build-ui:
	npm --prefix ${UI_DIRECTORY} install ${UI_DIRECTORY}

test-ui:
	npm test --prefix ${UI_DIRECTORY} -- --coverage

start-ui:
	make build-ui
	npm start --prefix ${UI_DIRECTORY}
## End UI Section