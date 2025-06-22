# Beer Travel Itinerary

This project is a Spring Boot application that searches for the best itinerary to visit as many breweries as possible within 2000 km of a variable starting point. It uses a PostgreSQL database to store the breweries and beer data.

## Requirements

Docker and Docker Compose for easy setup and deployment. Dependencies are handled automatically.

## Build / Start

To build and start the application and its dependencies, simply run:

```bash
docker-compose up --build
```

## Accessing the application
Once started, the application will be accessible at ``http://localhost:8081``.

From there, input a latitude and longitude (default values are provided as an example), and select which search algorithm to use:
- v1 is a basic search algorithm that always picks the next closest node from the starting point
- v2 is more advanced and, on each step, gets the closest node from the current one

## Stopping the application
To stop the application and remove the containers:

```bash
docker-compose down
```