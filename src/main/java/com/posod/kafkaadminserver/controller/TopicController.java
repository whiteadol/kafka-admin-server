package com.posod.kafkaadminserver.controller;

import com.posod.kafkaadminserver.annotation.V1Version;
import com.posod.kafkaadminserver.dto.Entry;
import com.posod.kafkaadminserver.dto.Topic;
import com.posod.kafkaadminserver.dto.request.ConfigModifyRequest;
import com.posod.kafkaadminserver.dto.request.CreateTopicRequest;
import com.posod.kafkaadminserver.dto.request.PartitionModifyRequest;
import com.posod.kafkaadminserver.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@V1Version
@RestController
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping("topics")
    boolean createTopic(CreateTopicRequest createTopicRequest) throws ExecutionException, InterruptedException {
        return this.topicService.create(createTopicRequest);
    }

    @GetMapping("topic-names")
    Set<String> getAllTopicNames() throws ExecutionException, InterruptedException {
        return this.topicService.getAll();
    }

    @GetMapping("topics/{topicName}")
    Topic getTopic(@PathVariable String topicName) throws ExecutionException, InterruptedException {
        return this.topicService.get(List.of(topicName))
                .get(topicName);
    }

    @GetMapping("topics")
    Map<String, Topic> getTopics(@RequestParam List<String> topicNames) throws ExecutionException, InterruptedException {
        return this.topicService.get(topicNames);
    }

    @DeleteMapping("topics/{topicName}")
    boolean deleteTopic(@PathVariable String topicName) throws ExecutionException, InterruptedException {
        return this.topicService.delete(topicName);
    }

    @PutMapping("/topics/{topicName}")
    boolean updateTopicConfig(@PathVariable String topicName, @RequestBody ConfigModifyRequest configModifyRequest) throws ExecutionException, InterruptedException {
        return this.topicService.updateConfig(topicName, configModifyRequest);
    }

    @GetMapping("topic-configs/{topicName}")
    List<Entry<String, String>> getTopicConfig(@PathVariable String topicName) throws ExecutionException, InterruptedException {
        return this.topicService.getConfig(topicName);
    }

    @PutMapping("/topics-partition")
    boolean updatePartition(@RequestBody PartitionModifyRequest partitionModifyRequest) throws ExecutionException, InterruptedException {
        return this.topicService.updatePartition(partitionModifyRequest);
    }
}
