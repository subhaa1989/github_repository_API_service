package org.redcare.coding.exception;

public class ResponseFailureException extends RuntimeException {
  public ResponseFailureException(String message) {
    super(message);
  }
}
