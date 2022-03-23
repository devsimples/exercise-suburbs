package com.city.management.exercise.api;

import com.city.management.exercise.api.dto.ErrorMessageResponse;
import com.city.management.exercise.model.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ErrorMessageResponse> manageBusinessException(BusinessException e, WebRequest request) {
        log.error("Logical Exception", e);

        var message = ErrorMessageResponse.builder()
                .message("Logical Exception")
                .exceptionMessage(e.getMessage())
                .build();

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
