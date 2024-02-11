package org.redcare.coding.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redcare.coding.httprequest.HttpRequestHandler;
import org.redcare.coding.model.Request;
import org.redcare.coding.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PopularRepositoryServiceTest {

  @Autowired PopularRepositoryService popularRepositoryService;

  @Mock QueryParametersResolver queryParametersResolver;

  @Mock HttpRequestHandler httpRequestHandler;

  @Test
  void test_getPopularGithubRepository() throws IOException, InterruptedException {
    when(queryParametersResolver.getRequestURI(
            anyString(), anyString(), anyInt(), any(LocalDate.class)))
        .thenReturn(
            URI.create(
                "https://api.github.com/search/repositories?q=created:2023-01-01+language:&SORT=stars&per_page=2&ORDER=desc"));

    var httpResponse = mock(HttpResponse.class);
    when(httpResponse.statusCode()).thenReturn(200);
    when(httpRequestHandler.sendRequest(any(URI.class))).thenReturn(httpResponse);

    var response =
        popularRepositoryService.getPopularGithubRepository(
            Request.builder()
                .count(3)
                .language("Go")
                .fromDate(DateUtils.toDate("2020-01-02"))
                .build());
    assertEquals(200, response.getStatus());
    assertEquals(3, response.getTotalCount());
    assertFalse(response.getRepositories().getItems().isEmpty());
  }
}
