package org.redcare.coding.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.redcare.coding.exception.ResponseFailureException;
import org.redcare.coding.httprequest.HttpRequestHandler;
import org.redcare.coding.model.Request;
import org.redcare.coding.model.Response;
import org.redcare.coding.utils.JsonToPoJo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class PopularRepositoryService {
  @Autowired private HttpRequestHandler httpRequestHandler;
  @Autowired private JsonToPoJo modelConverter;
  @Autowired private QueryParametersResolver queryParametersResolver;

  @Value("${base_url}")
  @Getter
  @Setter
  private String githubUrl;

  public Response getPopularGithubRepository(final Request request) {
    log.info("Github url received {}", getGithubUrl());
    int status = 0;
    try {
      var httpRequestUrl =
          queryParametersResolver.getRequestURI(
              getGithubUrl(), request.getLanguage(), request.getCount(), request.getFromDate());
      var response = httpRequestHandler.sendRequest(httpRequestUrl);
      status = response.statusCode();
      var repositories = modelConverter.extractData(response.body());
      log.info("Received repo info of size {}", repositories.getItems().size());
      return Response.builder()
          .repositories(repositories)
          .totalCount(repositories.getItems().size())
              .status(response.statusCode())
          .build();
    } catch (InterruptedException | IOException e) {
      log.error("Error occured during service {}", e.getMessage());
      throw new ResponseFailureException(
          "Something went wrong with the request with status" + status);
    }
  }
}
