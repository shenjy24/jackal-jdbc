package com.jonas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameNumber {
    @Column(name = "player_uuid")
    private String playerUUID;
    @Column(name = "player_name")
    private String playerName;
    private Integer number;
    private Timestamp ctime;
    private Timestamp utime;
}
