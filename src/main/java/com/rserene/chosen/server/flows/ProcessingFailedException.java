package com.rserene.chosen.server.flows;

public class ProcessingFailedException extends RuntimeException {
   public ProcessingFailedException(Throwable cause) {
      super(cause);
   }
}
