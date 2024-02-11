package org.redcare.coding.exception;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.redcare.coding.controller.GithubRepositoryController;
import org.redcare.coding.model.Request;
import org.redcare.coding.model.Response;
import org.redcare.coding.service.PopularRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(GithubRepositoryController.class)
class RestExceptionHandlerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean PopularRepositoryService popularRepositoryService;

  @Test
  void whenCountGreaterThan100given_thenExceptionIsThrown() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/getrepo?count=202"))
        .andExpect(MockMvcResultMatchers.status().is(HttpStatus.SC_BAD_REQUEST));
  }

  @Test
  void whenDateFormatIsWronglyGiven_thenExceptionIsThrown() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/getrepo?fromDate=202"))
        .andExpect(MockMvcResultMatchers.status().is(HttpStatus.SC_INTERNAL_SERVER_ERROR));
  }

  @Test
  void whenDateFormatIsCorrect_thenNoExceptionIsThrown() throws Exception {
    when(popularRepositoryService.getPopularGithubRepository(any(Request.class)))
        .thenReturn(Response.builder().totalCount(1).status(200).build());
    mockMvc
        .perform(MockMvcRequestBuilders.get("/getrepo?fromDate=2020-01-01"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
