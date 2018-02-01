package com.teamtreehouse.sparkangulartodo.exception;

public class DaoException extends Exception {

  private final Exception originalException;

  public DaoException(Exception originalException, String message) {
    super(message);
    this.originalException = originalException;
  }
}
