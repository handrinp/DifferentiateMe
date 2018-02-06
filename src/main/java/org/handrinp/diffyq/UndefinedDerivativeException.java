package org.handrinp.diffyq;

/**
 * this gets thrown when an unimplemented/undefined derivative is calculated
 * 
 * @author handrinp
 */
public class UndefinedDerivativeException extends RuntimeException {
  private static final long serialVersionUID = 2406632924882085480L;

  /**
   * construct an undefined derivative exception with the given message
   * 
   * @param msg
   */
  public UndefinedDerivativeException(String msg) {
    super(msg);
  }
}
