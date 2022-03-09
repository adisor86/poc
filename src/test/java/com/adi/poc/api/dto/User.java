package com.adi.poc.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User {
    //private int id;
    private String name;
    private String email;
    private String gender;
    private String status;
}
