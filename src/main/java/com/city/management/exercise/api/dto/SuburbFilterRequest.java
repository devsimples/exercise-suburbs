package com.city.management.exercise.api.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SuburbFilterRequest {

    private Long postCodeStart;
    private Long postCodeEnd;

}
