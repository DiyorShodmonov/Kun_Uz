package com.example.kunuz.entity;

import com.example.kunuz.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "article_like", uniqueConstraints = @UniqueConstraint(columnNames = {"profile_id", "article_id"}))
@Getter
@Setter
public class ArticleLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;


    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column
    @Enumerated(EnumType.STRING)
    private LikeStatus status;
}
