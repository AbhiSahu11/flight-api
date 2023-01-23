package nl.abnamro.api.flightsearch.error;

import lombok.extern.slf4j.Slf4j;
import nl.abnamro.api.flightsearch.payload.response.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity handleInvalidInputException(final InvalidInputException ex, final WebRequest request) {
        return new ResponseEntity(
                ApiError.builder()
                        .message("Validation failed")
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(ex.getMessage())
                        .build(),
                new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        return new ResponseEntity(
                ApiError.builder()
                        .message("Validation failed")
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(ex.getMessage())
                        .build(),
                new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity unhandledExceptions(Exception ex){
        return new ResponseEntity(
                ApiError.builder()
                        .message("Request Not valid")
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(ex.getMessage())
                        .build(),
                new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

}
