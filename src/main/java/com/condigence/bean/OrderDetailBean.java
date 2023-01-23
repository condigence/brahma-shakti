package com.condigence.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailBean implements Serializable {

    private String id;
    private String productId;
    private Integer quantity;
    private Integer orderDiscount;


}
