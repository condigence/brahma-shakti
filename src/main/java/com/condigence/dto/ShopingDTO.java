package com.condigence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopingDTO implements Serializable {

    Map<String, Integer> items;
    int quantity;

    private String fromDate;
    private String toDate;
    private Integer noOfDays;
    private String frequency;
    String outOfStockMessage;

}
