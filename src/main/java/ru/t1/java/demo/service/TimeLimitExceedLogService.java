package ru.t1.java.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.model.TimeLimitExceedLog;
import ru.t1.java.demo.repository.TimeLimitExceedLogRepository;

@Service
@RequiredArgsConstructor
public class TimeLimitExceedLogService {
    private final TimeLimitExceedLogRepository repository;

    public void logTimeLimitExceed(TimeLimitExceedLog log) {
        repository.save(log);
    }
}
