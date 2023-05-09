package com.posod.kafkaadminserver.controller;

import com.posod.kafkaadminserver.annotation.V1Version;
import com.posod.kafkaadminserver.dto.Entry;
import com.posod.kafkaadminserver.dto.request.ConfigModifyRequest;
import com.posod.kafkaadminserver.service.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@V1Version
@RestController
@RequiredArgsConstructor
public class BrokerController {

    private final BrokerService brokerService;

    @GetMapping("/brokers")
    Map<String, List<Entry<String, String>>> getBrokerConfigs() throws ExecutionException, InterruptedException {
        return this.brokerService.getAllConfigs();
    }

    @GetMapping("/brokers/{nodeId}")
    List<Entry<String, String>> getBrokerConfig(@PathVariable String nodeId) throws ExecutionException, InterruptedException {
        return this.brokerService.getConfig(nodeId);
    }

    @PutMapping("/brokers/{nodeId}")
    boolean updateBrokerConfig(@PathVariable String nodeId, @RequestBody ConfigModifyRequest configModifyRequest) throws ExecutionException, InterruptedException {
        return this.brokerService.updateConfig(nodeId, configModifyRequest);
    }
}
