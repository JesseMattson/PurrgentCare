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

___

## ⚙️ Pre-requisites

- IDE (IntelliJ IDEA preferred)
- Github
- Bash (Windows) or Zsh (Mac) shell
- Maven CLI
- Postman

___

### ▶️ Running the Application

- IntelliJ Run Configuration - See [Documentation](https://www.jetbrains.com/help/idea/run-debug-configuration-spring-boot.html)
    
    **OR**

- Command Line (Jar or Maven) - See [Documentation](https://www.javaguides.net/2019/05/run-spring-boot-app-from-command-line.html)

___

## ⌨️ Development Workflow

###  [GitFlow][git-flow-doc]

This project will leverage [git flow][git-flow-doc] for development workflow.

## 💾 Accessing the H2 Database Console

H2 is a light weight in-memory database that enables fast local development without the leg work of wiring up
a database, schema and connection settings. The database is only accessible while the application is running
and can be accessed through the built-in H2 console by URL only. [Additional Documentation](https://www.jetbrains.com/help/idea/h2.html)

- H2 console [link](http://localhost:8080/h2-console/)

___

# 🛠 Tools used in this project

___

## 📘 [Markdown][markdown-doc]

## 📫 [Postman](https://www.postman.com/)

[Postman](https://www.postman.com/) is a software tool to test your API endpoints and
validate backend functionality. This project leverages Postman and includes the collection
that can be [imported](https://learning.postman.com/docs/getting-started/importing-and-exporting/importing-and-exporting-overview/)
from the postman folder in this repository. 

[git-flow-doc]: documentation/git-flow.md
[markdown-doc]: documentation/markdown.md
