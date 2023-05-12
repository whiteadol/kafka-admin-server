package com.posod.kafkaadminserver.service;

import com.posod.kafkaadminserver.dto.Entry;
import com.posod.kafkaadminserver.dto.Topic;
import com.posod.kafkaadminserver.dto.request.ConfigModifyRequest;
import com.posod.kafkaadminserver.dto.request.CreateTopicRequest;
import com.posod.kafkaadminserver.dto.request.PartitionModifyRequest;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TopicService extends AbstractKafkaAdminClientService {

    private final ConfigService configService;
    private static final ConfigResource.Type RESOURCE_TYPE = ConfigResource.Type.TOPIC;

    public TopicService(AdminClient kafkaAdminClient, ConfigService configService) {
        super(kafkaAdminClient);
        this.configService = configService;
    }

    public boolean create(CreateTopicRequest createTopicRequest) throws ExecutionException, InterruptedException {

        NewTopic newTopic = new NewTopic(createTopicRequest.getTopicName(), createTopicRequest.getNumPartitions(), createTopicRequest.getReplicationFactor().shortValue());

        this.kafkaAdminClient.createTopics(List.of(newTopic))
                .all()
                .get();

        return true;
    }

    public Set<String> getAll() throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.listTopics()
                .listings()
                .thenApply(it -> it.stream()
                        .map(TopicListing::name)
                        .collect(Collectors.toSet())
                ).get();
    }

    public Map<String, Topic> get(List<String> topicNames) throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.describeTopics(topicNames)
                .allTopicNames()
                .thenApply(it -> it.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                            TopicDescription topic = e.getValue();
                            return new Topic(topic.name(), topic.topicId().toString(), topic.partitions().size());
                        }))
                ).get();
    }

    public boolean delete(String topicName) throws ExecutionException, InterruptedException {
        this.kafkaAdminClient.deleteTopics(List.of(topicName))
                .all()
                .get();

        return true;
    }

    public List<Entry<String, String>> getConfig(String topicName) throws ExecutionException, InterruptedException {
        ConfigResource configResource = new ConfigResource(RESOURCE_TYPE, topicName);
        return this.configService.getConfig(configResource);
    }

    public boolean updateConfig(String topicName, ConfigModifyRequest configModifyRequest) throws ExecutionException, InterruptedException {
        ConfigResource configResource = new ConfigResource(RESOURCE_TYPE, topicName);
        return this.configService.update(configResource, configModifyRequest.getConfig());
    }

    public boolean updatePartition(PartitionModifyRequest partitionModifyRequest) throws ExecutionException, InterruptedException {

        Map<String, NewPartitions> counts = new HashMap<>();
        counts.put(partitionModifyRequest.getTopicName(), NewPartitions.increaseTo(partitionModifyRequest.getNumPartitions()));

        this.kafkaAdminClient.createPartitions(counts)
                .all()
                .get();

        return true;
    }
}
