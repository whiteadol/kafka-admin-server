package com.posod.kafkaadminserver.controller;

import com.posod.kafkaadminserver.annotation.V1Version;
import com.posod.kafkaadminserver.dto.Node;
import com.posod.kafkaadminserver.service.ClusterService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.acl.AclOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@V1Version
@RestController
@RequiredArgsConstructor
public class ClusterController {

    private final ClusterService clusterService;

    @GetMapping("cluster-ids")
    String getClusterId() throws ExecutionException, InterruptedException {
        return this.clusterService.getClusterId();
    }

    @GetMapping("cluster/controller")
    Node getController() throws ExecutionException, InterruptedException {
        return this.clusterService.getController();
    }

    @GetMapping("cluster-nodes")
    List<Node> getNodes() throws ExecutionException, InterruptedException {
        return this.clusterService.getNodes();
    }

    @GetMapping("acls")
    Set<AclOperation> getAclOperations() throws ExecutionException, InterruptedException {
        return this.clusterService.getAclOperations();
    }
}
