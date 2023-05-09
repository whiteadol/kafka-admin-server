package com.posod.kafkaadminserver.service;

import com.posod.kafkaadminserver.dto.Entry;
import com.posod.kafkaadminserver.dto.Node;
import com.posod.kafkaadminserver.dto.request.ConfigModifyRequest;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class BrokerService extends AbstractKafkaAdminClientService {

    private final ClusterService clusterService;
    private final ConfigService configService;
    private static final ConfigResource.Type RESOURCE_TYPE = ConfigResource.Type.BROKER;

    private BrokerService(AdminClient kafkaAdminClient, ClusterService clusterService, ConfigService configService) {
        super(kafkaAdminClient);
        this.clusterService = clusterService;
        this.configService = configService;
    }

    public Map<String, List<Entry<String, String>>> getAllConfigs() throws ExecutionException, InterruptedException {

        List<ConfigResource> configResources = clusterService.getNodes().stream()
                .map(Node::getId)
                .map(it -> new ConfigResource(RESOURCE_TYPE, it))
                .toList();

        return this.configService.getConfigs(configResources);
    }

    public List<Entry<String, String>> getConfig(String nodeId) throws ExecutionException, InterruptedException {

        ConfigResource configResource = new ConfigResource(RESOURCE_TYPE, nodeId);
        return this.configService.getConfig(configResource);
    }

    public boolean updateConfig(String nodeId, ConfigModifyRequest configModifyRequest) throws ExecutionException, InterruptedException {

        ConfigResource configResource = new ConfigResource(RESOURCE_TYPE, nodeId);
        return this.configService.update(configResource, configModifyRequest.getConfig());
    }
}
