package com.example.kunuz.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArticleShortInfoDTO {

    private String title;
    private String description;
    private String content;
    private Integer region_id;
    private Integer moderator_id;
    private Integer publisher_id;


}
