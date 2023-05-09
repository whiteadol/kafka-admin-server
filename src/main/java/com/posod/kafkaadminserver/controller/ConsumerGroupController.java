package com.posod.kafkaadminserver.controller;

import com.posod.kafkaadminserver.annotation.V1Version;
import com.posod.kafkaadminserver.dto.ConsumerGroup;
import com.posod.kafkaadminserver.dto.ConsumerGroupId;
import com.posod.kafkaadminserver.dto.OffSetAndMetadata;
import com.posod.kafkaadminserver.dto.request.OffsetChangeRequest;
import com.posod.kafkaadminserver.service.ConsumerGroupService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.TopicPartition;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@V1Version
@RestController
@RequiredArgsConstructor
public class ConsumerGroupController {

    private final ConsumerGroupService consumerGroupService;

    @GetMapping("consumer-group-ids")
    List<ConsumerGroupId> getConsumerGroupIds() throws ExecutionException, InterruptedException {
        return this.consumerGroupService.getAll();
    }

    @GetMapping("consumer-groups/{groupId}")
    ConsumerGroup getConsumerGroupInfo(@PathVariable String groupId) throws ExecutionException, InterruptedException, TimeoutException {
        return this.consumerGroupService.get(List.of(groupId))
                .get(groupId);
    }

    @GetMapping("consumer-groups")
    Map<String, ConsumerGroup> getConsumerGroupInfos(@RequestParam List<String> groupIds) throws ExecutionException, InterruptedException, TimeoutException {
        return this.consumerGroupService.get(groupIds);
    }

    @GetMapping("consumer-groups/{groupId}/offsets")
    List<Map<TopicPartition, OffSetAndMetadata>> getConsumerGroupOffsets(@PathVariable String groupId) throws ExecutionException, InterruptedException {
        return this.consumerGroupService.getOffset(groupId);
    }

    @DeleteMapping("consumer-groups/{groupId}")
    boolean deleteConsumerGroup(@PathVariable String groupId) throws ExecutionException, InterruptedException {
        return this.consumerGroupService.delete(groupId);
    }

    @PutMapping("consumer-groups/{groupId}")
    boolean updateConsumerGroupOffsetAndMetadata(@PathVariable String groupId, @RequestBody OffsetChangeRequest offsetChangeRequest) throws ExecutionException, InterruptedException {
        return this.consumerGroupService.updateOffsetAndMetadata(groupId, offsetChangeRequest);
    }
}
