package org.redcare.coding.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redcare.coding.model.Repositories;
import org.redcare.coding.model.Response;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class JsonToPoJo {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public Repositories extractData(String jsonData) throws JsonProcessingException {

    return OBJECT_MAPPER.readValue(jsonData, Repositories.class);
  }

  public Response extractResponseData(String jsonData) throws JsonProcessingException {

    return OBJECT_MAPPER.readValue(jsonData, Response.class);
  }
}
