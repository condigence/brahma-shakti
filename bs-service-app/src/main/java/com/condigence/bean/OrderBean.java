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
public class OrderBean implements Serializable {

    private String id;
    private String userId;
    private String addressId;
    private String paymentMethod;
    private String cartId;
}
