package ru.t1.java.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "account")
public class Account extends AbstractPersistable<Long> {
    @Column
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AccountEnum accountType;

    @Column(name = "client_id")
    private Long clientId;
}