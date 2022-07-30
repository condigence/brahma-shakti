package com.condigence.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartBean {

    private String userId;
    private int totalItemCount;
    private int totalItems;
    private float discountAmount;
    private float taxAmount;
    private float subtotalAmount;
    private float grandTotal;
    private String lastUpdated;
    private List<CartDetailBean> items;
}
