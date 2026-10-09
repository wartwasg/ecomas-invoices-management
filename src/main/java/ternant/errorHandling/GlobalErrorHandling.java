package ternant.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandling {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception e){
       return new ResponseEntity<ApiResponse>(new ApiResponse(
               e.getMessage(),
               "Failed"
       ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JwtTokenIsNotAvailable.class)
    public ResponseEntity<?> JwtTokenIsNotAvailable(JwtTokenIsNotAvailable e){
        return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage(),"failed"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<?> invalidJwtException(InvalidJwtException e){
        return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage(),"failed"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvoiceAlreadyExists.class)
    public ResponseEntity<?> invoiceAlreadyExistsHandler(InvoiceAlreadyExists e){
        return new ResponseEntity<ApiResponse>(new ApiResponse(
                e.getMessage(),
                "Failed"
        ), HttpStatus.CONFLICT);
    }
}
