package com.example.Notice.Dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentGetDTO {
    private String userid;
    private String noticeidx;
    private String nick;
    private String comment;
}
