BASE_URL=http://localhost:8080
PERSONS_BASE_URL=$(BASE_URL)/persons
PET_BASE_URL=$(BASE_URL)/pets
ACCOUNT_BASE_URL=$(BASE_URL)/accounts

## Backend section
package-backend:
	mvn -B package --file pom.xml -DskipTests

build-backend:
	mvn clean install -DskipTests

test-backend:
	mvn test jacoco:report

test-postman:
	newman run PurrgentCare.postman_collection.json --env-var "personsBaseURL=$(PERSONS_BASE_URL)" \
	--env-var "petBaseURL=$(PET_BASE_URL)" --env-var "accountBaseURL=$(ACCOUNT_BASE_URL)"

open-jacoco-report:
	open target/site/jacoco/index.html

test-and-coverage: test-backend open-jacoco-report

compile-backend: package-backend build-backend

container-backend:
	docker build . --file Dockerfile --tag purrgent-care:latest

start-backend:
	make build-backend
	mvn spring-boot:run

#TODO use docker to run application so we can run postman without needing two terminals
test-backend-pipeline: compile-backend test-backend container-backend
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