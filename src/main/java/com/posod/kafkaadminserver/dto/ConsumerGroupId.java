package com.posod.kafkaadminserver.dto;

import lombok.Builder;
import lombok.Value;
import org.apache.kafka.clients.admin.ConsumerGroupListing;

@Value
@Builder
public class ConsumerGroupId {

    String groupId;
    boolean simpleConsumerGroup;

    public static ConsumerGroupId from(ConsumerGroupListing consumerGroupListing) {
        return ConsumerGroupId.builder()
                .groupId(consumerGroupListing.groupId())
                .simpleConsumerGroup(consumerGroupListing.isSimpleConsumerGroup())
                .build();
    }
}
