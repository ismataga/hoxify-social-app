package com.hoxify.hoxify_new.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@SuperBuilder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
public class ApiError {
    private int status;
    private String message;
    private String path;
    private long timestamp = new Date().getTime();
    private Map<String, String> validationErrors = null;


}
