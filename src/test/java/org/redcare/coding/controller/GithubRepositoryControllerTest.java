package org.redcare.coding.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redcare.coding.model.Request;
import org.redcare.coding.model.Response;
import org.redcare.coding.service.PopularRepositoryService;
import org.redcare.coding.utils.JsonToPoJo;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
class GithubRepositoryControllerTest {

  @InjectMocks GithubRepositoryController githubRepositoryController;

  @Mock PopularRepositoryService popularRepositoryService;

  @BeforeEach
  public void setUp() {}

  @Test
  void getRepositoryDetails() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    when(popularRepositoryService.getPopularGithubRepository(any(Request.class)))
        .thenReturn(Response.builder().totalCount(1).status(200).build());
    var repos =
        githubRepositoryController.getRepositoryDetails("java", 2, LocalDate.of(2013, 02, 02));
    assertEquals(HttpStatus.SC_OK, repos.getStatusCode().value());
    var jsonRes = new JsonToPoJo().extractResponseData(repos.getBody());
    assertEquals(1, jsonRes.getTotalCount());
    assertEquals(200, jsonRes.getStatus());
    assertNull(jsonRes.getRepositories());
  }

  @Test
  void getTop10RepositoryDetails() throws IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    when(popularRepositoryService.getPopularGithubRepository(any(Request.class)))
        .thenReturn(Response.builder().totalCount(10).status(200).build());
    var repos =
        githubRepositoryController.getRepositoryDetails("java", 2, LocalDate.of(2013, 02, 02));
    assertEquals(HttpStatus.SC_OK, repos.getStatusCode().value());
    var jsonRes = new JsonToPoJo().extractResponseData(repos.getBody());
    assertEquals(10, jsonRes.getTotalCount());
    assertEquals(200, jsonRes.getStatus());
    assertNull(jsonRes.getRepositories());
  }
}
