package ternant.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

    @ExceptionHandler(WrongUsernameOrPasswordException.class)
    public ResponseEntity<?> wrongUsernameOrPasswordException(WrongUsernameOrPasswordException e){
        return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage(),"failed"), HttpStatus.UNAUTHORIZED);
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
    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<?> userAlreadyExists(UserAlreadyExists e){
        return new ResponseEntity<ApiResponse>(new ApiResponse(
                e.getMessage(),
                "Failed"
        ), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException e) {
        return new ResponseEntity<ApiResponse>(new ApiResponse(
                e.getMessage(),
                "Failed"
        ), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(InvoiceDoesNotExist.class)
    public ResponseEntity<?> invoiceDoesNotExist(InvoiceDoesNotExist e){
        return new ResponseEntity<ApiResponse>(new ApiResponse(
                e.getMessage(),
                "Failed"
        ), HttpStatus.CONFLICT);
    }
}
