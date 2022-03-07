package com.adi.poc.api.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Controller;

@Data
@Builder
public class UserDto {
    private String name;
    private String email;
    private String gender;
}
