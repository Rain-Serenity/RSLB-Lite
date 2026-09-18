package com.rserene.chosen.server.http;

import java.io.IOException;

public class InterruptedRetryException extends IOException {
   public InterruptedRetryException(Throwable cause) {
      super(cause);
   }
}
