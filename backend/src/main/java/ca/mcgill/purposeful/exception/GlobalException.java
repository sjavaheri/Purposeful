package ca.mcgill.purposeful.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class GlobalException extends RuntimeException {
        // this exception has an HTTP status in addition to a method
    private HttpStatus status; 

    /**
     * @author Shidan Javaheri
     * This class will be used to handle exceptions globally in our project
     * All exceptions thrown will be Global Exceptions
     * @param status the http status of the failure
     * @param message the error message
     */
    public GlobalException (HttpStatus status, String message) { 
        super(message); 
        this.status = status; 
        
    }
    public HttpStatus getStatus() {
        return status;
    }
    
    
}

