package com.posod.kafkaadminserver.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaAdminClientProperties.class)
public class KafkaAdminClientConfig {

    private final KafkaAdminClientProperties kafkaAdminClientProperties;

    @Bean
    AdminClient kafkaAdminClient() {
        Properties props = new Properties();
        props.put(AdminClientConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 10_000); // 10s
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 10_000);     // 10S
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaAdminClientProperties.getServers());

        return AdminClient.create(props);
    }
}
