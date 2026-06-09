package com.example.model;

import java.util.List;

public record JobErrorResponse(
        int status,
        String message,
        List<String> details,
        String timestamp
) { }
