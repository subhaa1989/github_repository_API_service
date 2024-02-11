package org.redcare.coding.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExternalProviderServiceUnavailableException extends RuntimeException {
  public ExternalProviderServiceUnavailableException(String s, String body) {
    super(s);
    log.error("API unavailable exception {}", body);
  }
}
