package com.hh.contractstestsadmin.exception;

public class MinioClientException extends Exception {
  private final int statusCode;

  public MinioClientException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public MinioClientException(String message, Throwable cause, int statusCode) {
    super(message, cause);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
