package com.example.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record JobRequest(
        @NotBlank @Size(min = 1, max = 100) String jobName,
        @NotBlank @Size(min = 1, max = 1000) String jobDescription,
        @NotNull(message = "Capability is required") Capability capability,
        @NotNull(message = "Band is required") Band band
) { }
