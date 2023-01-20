package com.condigence.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDTO implements Serializable {

    private String id;
    private String title;
    private String image;
    private String unit;
    private String description;
    private int price;
    private int discount;
    private String promoCodes;
    private String category;
    private String productType;
    private double rating;
    private boolean isSubscribable;

    private int quantity;
    private int stockLeft;

}
