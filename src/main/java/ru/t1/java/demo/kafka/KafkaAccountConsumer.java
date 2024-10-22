package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.dto.AccountDto;
import ru.t1.java.demo.service.AccountService;

@RequiredArgsConstructor
@Component
@Slf4j
public class KafkaAccountConsumer {
    private final AccountService accountService;

    @KafkaListener(topics = "${t1.kafka.topic.account_created}",
            containerFactory = "accountKafkaListenerContainerFactory")
    public void listen(AccountDto message, Acknowledgment ack) {
        try {
            accountService.addAccount(message);
        }
        catch (Exception e) {
            log.error("Account consumer:  ошибка записи: {}", e.getMessage());
        }
        finally {
            ack.acknowledge();
        }
        log.debug("Account consumer: записи обработаны");
    }
}
