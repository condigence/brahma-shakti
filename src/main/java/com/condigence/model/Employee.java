package com.condigence.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Employee implements Serializable, Comparable {

    @Id
    private String id;

    private int empId;
    private String firstName;
    private String lastName;
    private float salary;

    private String city;

    private String number;

    private int age;

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
