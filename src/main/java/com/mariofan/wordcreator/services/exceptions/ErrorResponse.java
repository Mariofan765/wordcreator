package com.mariofan.wordcreator.services.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class ErrorResponse {

    private int status;
    private String message;
    private String details;
    private LocalDateTime timestamp;
    private String path;
    private List<String> errors;

}
