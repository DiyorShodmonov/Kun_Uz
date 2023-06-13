package com.example.kunuz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "article_type")

@Getter
@Setter
public class ArticleTypeEntity {
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

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
