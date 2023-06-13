package com.example.kunuz.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "types")

@Getter
@Setter
public class TypesEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String key;

    @Column
    private String name_uz;

    @Column
    private String name_ru;

    @Column
    private String name_en;

    @Column
    private Boolean visible;

    @Column(name = "created_date")
    private LocalDateTime createdDate;


}
