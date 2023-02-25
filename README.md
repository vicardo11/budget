# Budget &nbsp;![Status in progress](https://img.shields.io/badge/Status%20-In%20progress-orange)

## Table of Contents

* [About](#about)
* [Technologies](#technologies)
* [Architecture](#architecture)
* [Logs](#logs)
* [Services](#services)
* [How to run](#how-to-run)
* [Ports](#ports)

## About

Application enables users to track their finances - expenses, income, account balance.
In the future it's planned to add also features like: investments tracking, currency exchange.

## Technologies

- Java 17
- Spring Boot
- Spring Cloud
- PostgreSQL
- Docker
- Elasticsearch
- Logstash
- Kibana
- Keycloak
- JUnit
- Mockito
- Maven

## Architecture

Application is developed in microservices approach. Every user's request goes
first to Gateway, where the authorization is done and request is passed further to the specific
microservices.

## Logs

All the logs are collected using ELK stack.
* Logstash
* Logback
* Elasticsearch
* Kibana

After the startup, logs are available at URI:
http://localhost:5601

## Services
### Account Balance Service
Expenses:
* Get list of expenses of logged-in user
* Create new expense

Income:
* Get list of income of logged-in user
* Create new income

Account balance:
* Get account balance

Currency exchange: (using external API - https://freecurrencyapi.com/)
* Calculate value between two currencies

## How to run
TODO

## Ports

| PORT | Service                   |
|------|:--------------------------|
| 8000 | API Gateway               |
| 5601 | Kibana                    |
| 9200 | Elasticsearch             |
| 5000 | Logstash                  |
| 8100 | Keycloak                  |
| 8200 | Account Balance Service   |
| 8300 | Currency Exchange Service |