package com.jonas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_age")
    private Integer userAge;
    @Column(name = "user_status")
    private Integer userStatus;  //是否启用 0:否 1:是
    @Column(name = "user_score")
    private Integer userScore;
    private Long ctime;
    private Long utime;
}
