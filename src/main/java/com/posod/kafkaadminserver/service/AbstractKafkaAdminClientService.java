package com.posod.kafkaadminserver.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;

@RequiredArgsConstructor
public abstract class AbstractKafkaAdminClientService {
    protected final AdminClient kafkaAdminClient;
}
