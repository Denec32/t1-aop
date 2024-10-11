package ru.t1.java.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;

@Service
@RequiredArgsConstructor
public class DataSourceErrorLogService {
    private final DataSourceErrorLogRepository repository;

    public void logError(DataSourceErrorLog log) {
        repository.save(log);
    }
}
