package com.posod.kafkaadminserver.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;

@Getter
@NoArgsConstructor(force = true)
public class ConfigModifyRequest {

    @NonNull
    private Map<String, String> config;
}
