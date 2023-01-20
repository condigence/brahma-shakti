package com.condigence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDetailDTO implements Serializable {

    @Id
    private String id;
    private int itemQuantity;
    private int totalAmount;
    private ProductDTO productDTO;
    private UserDTO userDTO;
    private String fromDate;
    private String toDate;
    private Integer noOfDays;
    private String frequency;
    private String status; // PENDING or CONFIRMED or CANCELLED

}
