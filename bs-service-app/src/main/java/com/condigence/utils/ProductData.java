package com.condigence.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductData {

    private String title;
    private int stockLeft;
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


}
