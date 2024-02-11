
# Github repository API service

This coding challenge focus on retrieving popular github repositories with optional parameters.


### Github API
Github provides a vast public API to search through the repositories. In this project, we focus only on
searching public repositories with optional creation date, maxlimit and language to filter.
Please refer [this link](https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-repositories) to see all the parameters available.
And [this](https://docs.github.com/en/search-github/searching-on-github/searching-for-repositories) lists all possible filters to apply on query string.

### Sample Response 

Lets assume this is thr request passed to API, 
```
https://api.github.com/search/repositories?q=created:2024-01-01+language:java&sort=stars&order=desc
```
The output looks like contents in `response.json` [here](./src/main/resources/response.json)

### Assumption

- The service is available 99% of the time
- No API Key required to access 
- Pagination is ignored in implementation when larger than 100 results are requested, but can be easily extended 
with parameter `page`
- Limit on number of entries is achieved by providing number of entries per page (<100)

### Libraries used

- Spring boot to ease the development
- Spring web to handle REST endpoints
- Jackson to convert to and fro from the request and response.
- Lombok to avoid boiler plates
- used the online [service](https://json2csharp.com/code-converters/json-to-pojo) to convert json to POJO

### Service

I have used RestController with multiple endpoints to receive the inputs and serve back the response including error
and success messages.

## Rest Endpoint

### Get popular repositories

```
http://localhost:8080/getrepo
```
Optionally you can also pass parameters
```
http://localhost:8080/getrepo?count=20&language=go&fromDate=2020-01-01
```
When RestController receives the request, it will validate input and throw error.

Few example errors
`org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' 
to required type 'java.time.LocalDate'; Failed to convert from type [java.lang.String] to type 
[@org.springframework.web.bind.annotation.RequestParam @org.springframework.format.annotation.DateTimeFormat java.time.LocalDate] for value [2020-01-015]`

`jakarta.validation.ConstraintViolationException: getRepositoryDetails.count: only 100 results can be retrieved at a time, use pagination to receive next page`

### Top 10 repos

```
http://localhost:8080/get/top10
```
Returns top 10 popular repositories with default values. Refer [Controller](./src/main/java/org/redcare/coding/controller/GithubRepositoryController.java)

### Other endpoints
Same applies for other top 20 and top 50 popular repos.
```
http://localhost:8080/get/top20
```
```
http://localhost:8080/get/top50
```

### Improvements

Due to time constraint, I have discarded few improvements. But we can implement
- Java future/virtual threads to enable parallelism
- We could extend Response to only certain fields by using @JsonIgnore. As it is not specified, i have not skipped any field from the respons
- error status codes can be improved/added
- more logs and metrics could be added
- Better library could have been used to form queryString , as with Uribuilder, I could not skip encoding the queryParam, 
I have used the simple string builder to build on my own.
- Only 1 API endpoint `getRepo` might be sufficient for usecase, for convenience I have introduced `top*` endpoints
- More tests can be added
- Default values can be added in application.properties for easy modifications
- Swagger API can be used to document API endpoints and shared to other stakeholders.
- caching can be introduced to increase response time.

