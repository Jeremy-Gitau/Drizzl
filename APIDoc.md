# Drizzl Weather API Documentation

Welcome to the Drizzl Weather API documentation. This API allows developers to retrieve current weather information for specified locations, enabling integration with weather data in their applications. The Drizzl Weather API is built using Ktor principles and provides JSON responses for easy consumption.

## Base URL

The base URL for accessing the Drizzl Weather API is: http://api.drizzlapp.com/v1

## Authentication

API access to the Drizzl Weather API is protected by an API key. Developers must include their API key as a request parameter with each API call.

### API Key Parameter

Parameter | Description
--- | ---
`key` | Required. API Key provided to developers.

## Endpoints

The Drizzl Weather API provides the following endpoint for accessing current weather information:

- `/current`: Retrieve current weather information for a specified location.

## Request Parameters

### Common Parameters

The following parameter is common to the `/current` endpoint:

Parameter | Description
--- | ---
`q` | Required. Query parameter based on which data is sent back. It could be latitude and longitude, city name, postal code, airport code, or IP address.

## Response Fields

### `/current` Endpoint Fields

Field | Description
--- | ---
`location` | Object containing information about the location.
`current` | Object containing current weather information.

## Location Object

The `location` object returned in the API response contains information about the specified location.

Field | Description
--- | ---
`lat` | Latitude in decimal degrees.
`lon` | Longitude in decimal degrees.
`name` | Location name.
`region` | Region or state of the location.
`country` | Location country.

## Example Request

### Get Current Weather

```GET /current?q=New York&key=YOUR_API_KEY ```


## Example Response

```json
{
  "location": {
    "lat": 40.7128,
    "lon": -74.006,
    "name": "New York",
    "region": "New York",
    "country": "United States"
  },
  "current": {
    "temp_c": 23.5,
    "humidity": 60,
    "wind_kph": 10.5,
    "condition": {
      "text": "Partly cloudy"
    }
  }
}
```

## Error Handling
If there is an error processing the request, the API will return an appropriate HTTP status code along with an error message in the response body.

## Rate Limiting
The Drizzl Weather API imposes rate limits to prevent abuse. Developers should adhere to the specified rate limits to ensure continued access to the API.


