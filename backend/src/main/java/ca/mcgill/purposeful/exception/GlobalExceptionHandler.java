package ca.mcgill.purposeful.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Shidan Javaheri This class handles all the exceptions thrown in the application
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * @param ex the thrown exception
   * @return a response entity that contains the error message and http status
   * @author Shidan Javaheri This method handles all the times we throw an Global Exception
   */
  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<String> handleMmssException(GlobalException ex) {
    return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
  }

}
