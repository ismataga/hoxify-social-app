package com.hoxify.hoxify_new.error;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class ApiError {
   private int status;
   private String message;
   private String path;
   private long timestamp = new Date().getTime();
   private Map<String, String> validationErrors = new HashMap<String, String>();


}
