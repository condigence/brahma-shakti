package com.condigence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "super_hero")
public class SuperHero implements Serializable {

    @Id
    private String id;

    private String name;
    private String superName;
    private String profession;
    private int age;
    private boolean canFly;

    // Constructor, Getter and Setter
}