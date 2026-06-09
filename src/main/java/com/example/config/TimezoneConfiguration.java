package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.time.ZoneId;

@ConfigurationProperties("timezone")
public record TimezoneConfiguration(
        ZoneId location
) { }
