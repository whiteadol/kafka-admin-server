package com.posod.kafkaadminserver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Converter {
    BASIC {
        @Override
        public ObjectMapper getMapper() {
            return om;
        }
    };

    public abstract ObjectMapper getMapper();

    private static final ObjectMapper om = new ObjectMapper();
}
