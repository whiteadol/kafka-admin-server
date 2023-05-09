package com.posod.kafkaadminserver.dto.request;

import com.posod.kafkaadminserver.dto.OffSetAndMetadata;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;

@Getter
@NoArgsConstructor(force = true)
public class OffsetChangeRequest {

    @NonNull
    private Map<String, OffSetAndMetadata> offsets;
}
