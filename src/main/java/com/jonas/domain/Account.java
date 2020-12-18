package com.jonas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Column(name = "account_id")
    private String accountId;
    private Integer balance;
    private Timestamp ctime;
    private Timestamp utime;
}
