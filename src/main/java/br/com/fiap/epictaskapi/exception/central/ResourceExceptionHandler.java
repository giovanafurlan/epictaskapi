package br.com.fiap.epictaskapi.exception.central;

import br.com.fiap.epictaskapi.exception.custom.ResourceNotFoundException;
import br.com.fiap.epictaskapi.exception.custom.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException resourceNotFoundException, HttpServletRequest httpServletRequest) {
        String error = "OBJECT NOT FOUND EXCEPTION";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), httpStatus.value(), error, resourceNotFoundException.getMessage(), httpServletRequest.getRequestURI());
        return ResponseEntity.status(httpStatus).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> enumInvalid(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest httpServletRequest) {
        String error = "CONSTRAINT NOT BLANK";
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), httpStatus.value(), error, methodArgumentNotValidException.getMessage(), httpServletRequest.getRequestURI());
        return ResponseEntity.status(httpStatus).body(standardError);
    }

}
