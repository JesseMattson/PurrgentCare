# 🐈 Purrgent Care 🐕
___

### Description

This readme provides background information regarding the Purrgent Care application, how to run it locally,
and access the H2 in-memory database.

___

## 💻 Application Purpose

This application provides functionality to veterinarian's offices to maintain client's information regarding
their pets, their visits to the clinic, and diagnoses over time. Specific use cases will be built over time 
using an agile approach to continually expand functionality.

___

## 🏛 Architecture

This application is built on a [controller-service-repository architecture](https://tom-collings.medium.com/controller-service-repository-16e29a4684e5)
to provide a good separation of concerns. This architecture leverages a [repository pattern](https://java-design-patterns.com/patterns/repository/)
for data access and retrieval. A repository pattern leverages pre-built JPA repositories and is preferable than 
leveraging the entity manager by following the [DAO pattern](https://www.baeldung.com/java-dao-vs-repository).
This application uses [React](https://react.dev/) for the UI portion of the application. More details can be seen
in the [UI README][ui-readme]

___

## ⚙️ Pre-requisites

- IDE (IntelliJ IDEA preferred)
- Github
- Bash (Windows) or Zsh (Mac) shell
- Maven CLI
- Postman
- Gnu Make (Makefile)
- Lombok
- Node
- Node Package Manager (npm)

___

### 🏗 Build the Application

```shell
## Start Backend
mvn clean install -U
```

```shell
## Start UI
npm start --prefix ./purrgent-care-ui
```
- Also see commands in [makefile](./makefile)

___

### ▶️ Running the Application

```shell
## Start Backend
mvn spring-boot:run
```
- Backend alternatives: [Run config][run-config] or [Command line][run-command-line]

```shell
## Start UI
npm start --prefix ./purrgent-care-ui
```
- Also see commands in [makefile](./makefile)

___

## ⌨️ Development Workflow

### 🔀 [GitFlow][git-flow-doc]

This project will leverage [git flow][git-flow-doc] for development workflow.

### 💾 [Accessing the H2 Database Console](http://localhost:8080/h2-console/)

H2 is a light weight in-memory database that enables fast local development without the leg work of wiring up
a database, schema and connection settings. The database is only accessible while the application is running
and can be accessed through the built-in H2 console by URL only. [Additional Documentation](https://www.jetbrains.com/help/idea/h2.html)


### 🖥️ [Accessing the UI](http://localhost:3000/)

### 🧪 [Unit Testing][unit-testing-doc]

This project requires unit testing as per the Definition of Done and meet code coverage requirements.

- To run all tests run:
```shell
## Run Backend tests
mvn test jacoco:report
```

```shell
## Run UI tests
npm test --prefix ./purrgent-care-ui
```

- Also see commands in [makefile](./makefile)
___

# 🛠 Tools used in this project

- ### ＠ [Annotations](https://medium.com/@himani.prasad016/spring-boot-annotations-2894594e3c4b):
  - [Application annotations](https://www.geeksforgeeks.org/spring-boot-annotations/)
  - [Model annotations](https://wkrzywiec.medium.com/project-lombok-how-to-make-your-model-class-simple-ad71319c35d5)

- ### 📘 [Markdown][markdown-doc]

- ### 📫 [Postman][postman-doc]

- ### 📊 [Jacoco][jacoco-doc]

- ### 📝 [Makefile][makefile-doc]

- ### 🌶 [Lombok][lombok-doc]

[git-flow-doc]: documentation/git-flow.md
[markdown-doc]: documentation/markdown.md
[postman-doc]: documentation/postman.md
[jacoco-doc]: documentation/jacoco.md
[makefile-doc]: documentation/makefile.md
[lombok-doc]: documentation/lombok.md
[unit-testing-doc]: documentation/unit-testing.md
[ui-readme]: purrgent-care-ui/README.md
[run-config]: https://www.jetbrains.com/help/idea/run-debug-configuration-spring-boot.html
[run-command-line]: https://www.javaguides.net/2019/05/run-spring-boot-app-from-command-line.html