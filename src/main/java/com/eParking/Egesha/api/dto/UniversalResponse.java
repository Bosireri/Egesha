package com.eParking.Egesha.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UniversalResponse(
        int status,
        String message,
        Object data,
        List<String> errors,
        Integer totalItems) {
    public static UniversalResponseBuilder builder() {
        return new UniversalResponseBuilder();

    }
}
