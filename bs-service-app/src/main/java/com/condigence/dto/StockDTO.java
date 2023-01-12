package com.condigence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDTO implements Serializable {

    private String id;
    private int productId;
    private int quantityInStock;

}
