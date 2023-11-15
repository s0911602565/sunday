package com.example.abc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String pwd;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate birthdayA;
    public User(){
        this.setUsername("測試 ModelAttribute username");
        this.setPwd("測試 ModelAttribute pwd");
    }

}
