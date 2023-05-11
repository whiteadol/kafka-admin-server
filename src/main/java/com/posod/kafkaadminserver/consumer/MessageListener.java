package com.posod.kafkaadminserver.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posod.kafkaadminserver.dto.infiRay.dsd.TempData;
import com.posod.kafkaadminserver.repository.TempDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {

    private final KafkaTemplate<String, Object> kafkaProducerTemplate;
    private final TempDataRepository tempDataRepository;

    @KafkaListener(topics = "cam-thermal-data", containerFactory = "commonKafkaListenerContainerFactory")
    public void listenNifiTopic(ConsumerRecord<String, Object> record) {
        log.info("Receive Message from Nifi");
        log.info("Receive Message from cam-thermal-data, values {}", record.value());

        CompletableFuture<SendResult<String, Object>> future = kafkaProducerTemplate.send("CAM1_REDIRECT", record.value());

        future.thenAcceptAsync(
                result ->
                        log.info("Send message with offset: {}, partition: {}", result.getRecordMetadata().offset(), result.getRecordMetadata().partition())
        ).exceptionally(throwable -> {
            log.error("Fail to send message to broker: {}", throwable.getMessage());
            return null;
        });
    }

    @KafkaListener(topics = "CAM1", containerFactory = "commonKafkaListenerContainerFactory")
    public void listenAiResultTopic(ConsumerRecord<String, Object> record) {
        log.info("Receive Message from AI");

        ObjectMapper mapper = new ObjectMapper();
        TempData tempData = mapper.convertValue(record.value(), TempData.class);
        tempDataRepository.save(tempData);
    }
}
