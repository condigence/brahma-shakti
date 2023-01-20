package com.condigence.dto;

import com.condigence.model.Product;
import com.condigence.model.Subscription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO implements Serializable {

    private String id;

    private float subtotalAmount;
    private float grandTotal;

    private int totalItemCount;
    private float discountAmount;
    private float taxAmount;
    private String lastUpdated;

    @JsonIgnore
    private Map<String, Product> productsPicked = new HashMap<>();
    @JsonIgnore
    private Map<String, Subscription> subscriptions = new HashMap<>();

    private List<CartDetailDTO> itemDetails;
    private List<SubscriptionDetailDTO> subscriptionDetails;

    private String userId;
    private String convId;
    private UserDTO userDTO;

    String outOfStockMessage;

}
