package org.handrinp.diffyq;

import java.io.IOException;

/**
 * the main driver class for the Expression library
 * 
 * @author handrinp
 */
public class Main {
  /**
   * it all starts here :)
   * 
   * this is run when the jar is executed
   * 
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    Testing.runTestSuite(5);
  }
}
