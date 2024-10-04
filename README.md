# Currency Conversion Service

This project is a currency conversion service that includes currency conversion API, a React frontend, and monitoring through InfluxDB and Grafana. The service allows users to convert between different currencies using real-time exchange rates, provided by [swop.cx](https://swop.cx).

## Prerequisites

- **Java 21**: [Download here](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
- **Maven**: [Install Maven](https://maven.apache.org/install.html)
- **Docker Compose**: [Install Docker Compose](https://docs.docker.com/compose/install/)

## Setup and Build

### Step 1: Clone the Repository

```bash
git clone https://github.com/georgiy-rimsha/currency-conversion-service.git
cd currency-conversion-service
```

### Step 2: Build the Application

To build the backend and run integration tests, use Maven:
```bash
cd backend
mvn verify
```

To build and push the Docker images, we can use the build-docker-image Maven profile:
```bash
cd backend
mvn verify -Pbuild-docker-image
```

## Running the Application
The easiest way to run the application is by using Docker Compose:
```bash
docker compose up -d
```

### Access the Services

Once the services are up, you can access them via the following URLs:

- **Frontend**: http://localhost:3001
- **API**: http://localhost:8081
- **Grafana:**: http://localhost:3006 (```admin``` ```admin```)
- **InfluxDB**: http://localhost:8086 (```admin``` ```password123```)

### Interacting with the API

Once the service is running, you can perform currency conversions using the following endpoint:


#### Convert Currency:
```GET /api/v1/conversions/{sourceCurrency}/{targetCurrency}?amount={amount}```

Example:
```
http://localhost:8081/api/v1/conversions/USD/EUR?amount=100
```
Response:
```json
{
  "convertedAmount": 90.6207,
  "conversionRate": 0.906207
}
```
This will convert the specified amount from the sourceCurrency to the targetCurrency using the latest conversion rate.

#### Get Available Currencies:
To retrieve a list of available currency codes, use the following endpoint:

```GET /api/v1/currencies```

Example:
```
http://localhost:8081/api/v1/currencies
```
Response:
```
[
  "AED",
  "AFN",
  "ALL",
  "AMD",
  "ANG",
  ...
]
```
This returns a list of supported currency codes for conversion.

## Grafana configuration

Grafana's data sources and dashboards are automatically configured using the provisioning files located in the ```monitoring/grafana/provisioning``` directory.



