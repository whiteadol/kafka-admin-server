package com.posod.kafkaadminserver.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Value
@ConfigurationProperties("kafka.admin")
public class KafkaAdminClientProperties {
    List<String> servers;
}
