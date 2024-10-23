package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import ru.t1.java.demo.model.TimeLimitExceedLog;

@Slf4j
@RequiredArgsConstructor
public class KafkaTleProducer {
    private final KafkaTemplate<String, TimeLimitExceedLog> template;

    public void sendMessage(TimeLimitExceedLog tleLog) {
        try {
            template.sendDefault(tleLog).get();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            template.flush();
        }
    }
}
