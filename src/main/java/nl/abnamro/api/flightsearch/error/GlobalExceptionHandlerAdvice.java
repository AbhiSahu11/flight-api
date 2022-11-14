package nl.abnamro.api.flightsearch.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.api.flightsearch.payload.response.ApiError;
import nl.abnamro.api.flightsearch.service.InvalidInputException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
