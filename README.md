# Image Metadata Feature
Image Metadata Feature simple backend application that is capable of working with image metadata, that is typically generated on the platform.
The goal is to expose three endpoints for listing features, retrieving a specific feature by its ID and to return an actual image from a base64 encoded string.

[More details](UP42_backend_challenge.pdf)

## Steps

### 1. Download dependencies and build
```bash
./mvnw clean install
```
### 2. Run unit tests
```bash
./mvnw test
```
### 3. Run
```bash
./mvnw spring-boot:run
```

This will start the server at localhost:8080
* API Documentation [Swagger UI](http://localhost:8080/swagger-ui/index.html)

## Tech Stack

The service is built using only Java with SpringBoot. Apart from the starter libraries provided by [SpringBoot Initializer](https://start.spring.io/),folowing dependencies have been used:
* [Lombok](https://projectlombok.org/) - to reduce boilerplate code.
* [Jakarta Bean Validation API](https://beanvalidation.org/) - to use NotNull for uuid's in model and response.
* [Spring Doc Open API](https://springdoc.org/) - for api documentation.
Unit tests have been written with good code coverage.

## Tech Notes

1. The code is organized into following main packages:
    - **controller**: This package contains the SpringBoot controller and ControllerAdvice.
    - **data**: This package contains class to deserialize JSON to java object. This class contains business rules to get feature and feature by id.
    - **service**: This package contains classes used for serialisation to JSON.
    - **exception**: This package contains custom exceptions.
    - **mapper**: This package contains the mapper for mapping from Feature model to FeatureResponse dto.
    - **model**: This package represents the data model for required fields from json.
    - **dto**: This package contains response object for feature.
2. The data class has been built to read json source file and parse to java object.

## API Endpoints
1. **GET /features**
```text
Returns a list of features. The list is wrapped in an object to the api backward compatible.
```
**Sample cURL request**
```bash  
curl --location --request GET 'localhost:8080/v1/features'
```  
**Response**
```json  
{
    "features": [
    {
        "id": "39c2f29e-c0f8-4a39-a98b-deed547d6aea",
        "timestamp": 1554831167697,
        "beginViewingDate": 1554831167697,
        "endViewingDate": 1554831202043,
        "missionName": "Sentinel-1B"
    },
    {
        "id": "cf5dbe37-ab95-4af1-97ad-2637aec4ddf0",
        "timestamp": 1556904743783,
        "beginViewingDate": 1556904743783,
        "endViewingDate": 1556904768781,
        "missionName": "Sentinel-1B"
    }
   ]
}
```  
2. **GET /features/{id}**
```text
Returns a single representation of a feature.
```
**Sample cURL request**
```bash  
curl --location --request GET 'localhost:8080/v1/features/39c2f29e-c0f8-4a39-a98b-deed547d6aea'
```  
**Response**
```json  
{
        "id": "39c2f29e-c0f8-4a39-a98b-deed547d6aea",
        "timestamp": 1554831167697,
        "beginViewingDate": 1554831167697,
        "endViewingDate": 1554831202043,
        "missionName": "Sentinel-1B"
}
``` 
3. **GET /features/{id}/quicklook**
```text
Returns the image for the given id.
```
**Sample cURL request**
```bash  
curl --location --request GET 'localhost:8080/v1/features/39c2f29e-c0f8-4a39-a98b-deed547d6aea/quicklook' > response-quicklook.png
```  
**Response**
![](response.png)

##  Exception

**NotFoundException**
```text
The validation checks whether a feature exists for a given feature id and returns a error response if not found.
```
**Sample cURL request**
```bash  
curl --location --request GET 'localhost:8080/v1/features/39c2f29e-1111-4a39-1111-deed547d6aea'
```  
**Response**
```json  
{
    "timestamp": "2021-12-16T19:30:27.573+00:00",
    "error": "NOT_FOUND",
    "message": "Feature with id {39c2f29e-c0f8-4a39-a98b-0deed547d6ae} not found.",
    "details": "uri=/v1/features/39c2f29e-c0f8-4a39-a98b-deed547d6ae"
}
``` 

## Optimizations
### Wrapper object for top level list in source file
In case of source data file `source-data.json`, object wrapper can be used for the top level list. 

This will make the api, which produces the source-data, backward compatible.
The consumers of the api will not notice any change in case a new object is added to json.
```json
{
"data" :[
   [
      {
         "type": "FeatureCollection",
         "features": [
            {
               "type": "Feature"
            }
         ]
      }
   ]
  ]
}
```

Optimization approach has been used for api `GET /features` to make it backward compatible.
