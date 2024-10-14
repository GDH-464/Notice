package com.example.Notice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private String userid;
    private String nick;
    private Long grade;
    private Long noticeidx;
}
