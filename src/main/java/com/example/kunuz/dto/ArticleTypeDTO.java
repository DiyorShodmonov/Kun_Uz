package com.example.kunuz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleTypeDTO {


    private Integer id;

    private String key;
    private String name_uz;
    private String name_ru;
    private String name_en;

    private LocalDateTime createdDate;

}
