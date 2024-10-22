package ru.t1.java.demo.service;

import ru.t1.java.demo.model.dto.TransactionDto;

public interface TransactionService {

    TransactionDto addTransaction(TransactionDto message);
}
