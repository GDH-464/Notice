package com.example.Notice.Dto;

import com.example.Notice.Entity.CommentEntity;
import com.example.Notice.Entity.NoticeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long idx;
    private NoticeEntity notice;
    private String userid;
    private String content;
    private Date regdate = new Date(System.currentTimeMillis());
    private String isdelete = "false";
    private String nick;

    public CommentEntity tocommententity()
    {
        return new CommentEntity(idx,notice,userid,content,regdate,isdelete,nick);
    }
}
