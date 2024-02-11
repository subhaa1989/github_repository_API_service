package org.redcare.coding.exception;

public class TooManyRequestsPerSecondException extends RuntimeException {
    public TooManyRequestsPerSecondException(String s) {
        super(s);
    }
}
