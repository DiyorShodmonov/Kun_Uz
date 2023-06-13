package com.example.kunuz.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer shared_count;
    private Integer image_id;
    private Integer region_id;
    private Integer article_type_id;

    private Integer moderator_id;

    private RegionDTO regionDTO;
    private ArticleTypeDTO articleTypeDTO;
    private ProfileDTO moderatorDTO;


    private LocalDateTime createdDate;

}
