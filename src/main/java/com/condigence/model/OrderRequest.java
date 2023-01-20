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
public class OrderRequest implements Serializable {

    @Id
    private String id;
    private String customerName;
    private String email;
    private String phoneNumber;
    private String amount;

}
