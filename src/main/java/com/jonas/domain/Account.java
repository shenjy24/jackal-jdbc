package com.jonas.domain;

import javafx.beans.NamedArg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-10-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Column(name = "account_id")
    private Integer accountId;
    private String account;
    private Timestamp ctime;
    private Timestamp utime;
}
