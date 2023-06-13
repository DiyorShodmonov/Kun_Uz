package com.example.kunuz.entity;

import com.example.kunuz.enums.ArticleStatus;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")

@Getter
@Setter
public class ArticleEntity {

    @Id
    @GeneratedValue(generator = "generator_uuid")
    @GenericGenerator(name = "generator_uuid",
            strategy = "org.hibernate.id.UUIDGenerator")
    private String id;


    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity region;

    @ManyToOne
    @JoinColumn(name = "article_type_id")
    private ArticleTypeEntity articleType;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private ProfileEntity moderator;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private ProfileEntity publisher;

    @Enumerated(EnumType.STRING)
    @Column
    private ArticleStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column
    private Boolean visible;

    @Column(name = "view_count")
    private Integer viewCount;
}


