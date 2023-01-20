package com.condigence.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionBean {
    private ProductBean productBean = new ProductBean();
    private String fromDate;
    private String toDate;
    private Integer noOfDays;
    private String frequency;
}
