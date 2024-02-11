package org.redcare.coding.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleErrors(Exception e) {
    return new ResponseEntity<>(String.valueOf(e), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<String> handleIOErrors(Exception e) {
    return new ResponseEntity<>(String.valueOf(e), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(Exception e) {
    return new ResponseEntity<>(
        String.valueOf(e), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResponseFailureException.class)
  public ResponseEntity<String> handleAllOtherErrors(Exception e) {
    return new ResponseEntity<>(
        String.valueOf(e), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

    @ExceptionHandler(JsonParsingException.class)
    public ResponseEntity<String> handleJsonError(Exception e) {
        return new ResponseEntity<>(
                String.valueOf(e), new HttpHeaders(), HttpStatus.PARTIAL_CONTENT);
    }
}
