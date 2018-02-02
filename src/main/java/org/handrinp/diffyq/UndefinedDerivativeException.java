package org.handrinp.diffyq;

public class UndefinedDerivativeException extends RuntimeException {
  private static final long serialVersionUID = 2406632924882085480L;

  public UndefinedDerivativeException(String msg) {
    super(msg);
  }
}
