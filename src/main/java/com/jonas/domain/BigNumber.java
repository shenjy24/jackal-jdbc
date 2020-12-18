package com.jonas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenjy
 * @date 2020/6/22
 * @description 大数据索引测试
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BigNumber {
    private Integer id;
    private String money;
    private Integer count;

    public BigNumber(String money, Integer count) {
        this.money = money;
        this.count = count;
    }
}
