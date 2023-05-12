package com.posod.kafkaadminserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class PartitionModifyRequest {

    @NonNull
    String topicName;

    @NonNull
    Integer numPartitions;
}
