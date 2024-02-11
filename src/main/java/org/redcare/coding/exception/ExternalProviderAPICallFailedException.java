package org.redcare.coding.exception;

public class ExternalProviderAPICallFailedException extends RuntimeException {
  public ExternalProviderAPICallFailedException(String message) {
    super(message);
  }
}
