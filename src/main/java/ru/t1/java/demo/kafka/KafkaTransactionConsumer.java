package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.dto.TransactionDto;
import ru.t1.java.demo.service.TransactionService;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaTransactionConsumer {
    private final TransactionService transactionService;

    @KafkaListener(topics = "${t1.kafka.topic.transaction_created}",
            containerFactory = "transactionKafkaListenerContainerFactory")
    public void listen(@Payload List<TransactionDto> messages, Acknowledgment ack) {
        try {
            messages.forEach(transactionService::addTransaction);
        }
        catch (Exception e) {
            log.error("Account consumer:  ошибка записи: {}", e.getMessage());
        }
        finally {
            ack.acknowledge();
        }
        log.debug("Transaction consumer: записи обработаны");
    }
}