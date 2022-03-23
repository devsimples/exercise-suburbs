package com.city.management.exercise.api.dto;

import lombok.Builder;

@Builder
public class ErrorMessageResponse {

    private String message;
    private String exceptionMessage;
}
