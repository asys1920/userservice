package com.asys1920.service.advice;

import com.asys1920.service.exceptions.UserAlreadyExsitsException;
import lombok.Data;
import net.minidev.json.JSONObject;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RepositoryConstraintViolationException.class})
    public ResponseEntity<ErrorMessage> handleRepositoryConstraintViolationException(
            RepositoryConstraintViolationException ex) {
        List<String> errors = ex.getErrors().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new ErrorMessage(errors));
    }

    @ExceptionHandler(value = {UserAlreadyExsitsException.class})
    @ResponseBody
    public ResponseEntity<String> handleValidationException(Exception ex) {
        return new ResponseEntity<>(jsonFromException(ex), HttpStatus.CONFLICT);
    }

    @Data
    private class ErrorMessage {
        private final String cause = "VALIDATION FAILED";
        private List<String> description;

        public ErrorMessage(List<String> description) {
            this.description = description;
        }
    }

    private String jsonFromException(Exception ex) {
        JSONObject response = new JSONObject();
        response.put("message", ex.getMessage());
        return response.toJSONString();
    }
}