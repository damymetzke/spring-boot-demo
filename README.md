# Spring Boot Demo

This is a basic demo for a spring boot application.
It covers the following topics:

- Spring Data JPA
- Rest API using CRUD operations
- Using a MySQL database

## Quickstart

Before running the sever make sure you have a MySQL server running.
The MySQL server should be listening on the default port (3306).
For your convenience a `docker-compose.yml` file has been created.
See the docker section for more information.

If you do decide to host the server yourself,
make sure to create the `demo` database.

When you have a MySQL server running you can start the server by running:

```bash
./mvnw spring-boot:run
```

Note that the server will keep the terminal occupied.
The server is now available on port 8080.

## Docker

If you have docker and docker-compose installed
you can run one of the following:

```bash
# The current terminal will display all logging information
docker-compose up

# Or use the following command:
# The containers will detach, so you can keep using the terminal
docker-compose up -d
```

This will start a MySQL service on port 3306.
It will also start Adminer on port 8081.
Adminer will give you full insight in the MySQL database.
It does the same thing as phpMyAdmin, which you may know.

To stop the docker containers run one of the following commands:

```bash
docker-compose down

# This will also remove the volumes.
# Use this if you want to completely reset the database.
# For example when the database is in a broken state.
docker-compose down -v
```
