package com.condigence.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartBean {

    private String userId;
    private String convId;
    private Map<String, ProductBean> productsPicked = new HashMap<>();
    private Map<String, SubscriptionBean> subscriptions = new HashMap<>();
}
