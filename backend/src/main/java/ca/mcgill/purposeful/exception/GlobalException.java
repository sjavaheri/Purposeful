package ca.mcgill.purposeful.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Shidan Javaheri This class is the exception class that we will use for all exceptions
 */
@SuppressWarnings("serial")
public class GlobalException extends RuntimeException {

  // this exception has an HTTP status in addition to a method
  private HttpStatus status;

  /**
   * @param status  the http status of the failure
   * @param message the error message
   * @author Shidan Javaheri This constructor creates the a Global Exception All exceptions thrown
   * will be Global Exceptions
   */
  public GlobalException(HttpStatus status, String message) {
    super(message);
    this.status = status;

  }

  public HttpStatus getStatus() {
    return status;
  }


}

