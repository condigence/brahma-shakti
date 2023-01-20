package com.condigence.model;

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
public class Order implements Serializable {

    /**
     *
     */

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String number;
    private String userId;
    private String type;
    private String dateTime;
    private String status; // PENDING or CONFIRMED or CANCELLED

    private String addressId;
    private String paymentMethod;
    private String cartId;

    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;

}
