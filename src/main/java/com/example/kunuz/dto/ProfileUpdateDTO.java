package com.example.kunuz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProfileUpdateDTO {
    private Integer id;
    private String name;
    private String surname;
    private String password;
}
