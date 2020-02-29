# User Service
## Table of Contents

- [Description](#description)
- [Documentation](#documentation)
- [Features](#features)
- [Requirements](#requirements)
- [Quick Start / Setup](#quick-start--setup)
- [Configuration](configuration)
- [API](#api)

## Description
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/asys1920/userservice)](https://github.com/asys1920/userservice/releases/tag/v1.0.0)

This microservice is part of the car-rental project which was built
by the Asys course 19/20 at the TH Bingen.

It manages the users of the car-rental, keeps track of all users that are registered with the Car-Rental and keeps
track of the status of the users.

The Microservice can be monitored by Prometheus.

Logs can be sent to Elasticsearch/Logstash using Filebeat.

## Documentation
See [Management project](https://github.com/asys1920/management) for a documentation of the whole Car-Rental project.
## Features
This microservice can create, update, delete and read users from a local H2 Database. Furthermore, it exposes health,
info and shutdown endpoints using Spring Boot Actuator. By exposing a special /actuator/prometheus endpoint it can
be monitored using Prometheus. By using Filebeat the logs the microservice generates are sent to Elasticsearch/Logstash.

## Requirements
A JDK with at least Java Version 11.

### Local
### Docker
## Quick Start / Setup
### Run Local
### Run Docker

## Configuration
You can set the Port of the microservice using the `USER_PORT` environment variable.
The default Port used by the application is `8084`. To set the Address the Microservice
listens on you can use the `USER_ADDRESS` environment variable, its default value is
`localhost`.

## API
To see a full documentation view the swagger documentation while running the microservice. You can
find the Swagger Documentation at `http://<host>:<port>/swagger-ui.html` 

Method | Endpoint | Parameters | Request Body | Description
--- | --- | ---  | --- | ---
GET | /users | /{id} | N/A | Get an existing user
POST | /users | N/A | User in JSON Format | Create a new user
DELETE | /users | /{id} | N/A | Delete an existing user
PATCH | /users | N/A | User in JSON Format | Updates a specific User