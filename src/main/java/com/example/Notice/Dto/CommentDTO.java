package com.example.Notice.Dto;

import com.example.Notice.Entity.CommentEntity;
import com.example.Notice.Entity.MemberEntity;
import com.example.Notice.Entity.NoticeEntity;
import com.example.Notice.Repository.MemberRepository;
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
    private Date regdate;
    private String isdelete;
    private String nick;


    public CommentDTO(CommentEntity comment) {
        this.idx = comment.getIdx();
        this.notice = comment.getNotice();
        this.userid = comment.getUserid();
        this.content = comment.getContent();
        this.regdate = comment.getRegdate();
        this.isdelete = comment.getIsdelete();
        this.nick = comment.getNick();
    }

    public CommentEntity tocommententity()
    {
        regdate = new Date(System.currentTimeMillis());
        isdelete = "false";
        return new CommentEntity(null,notice,userid,content,regdate,isdelete,nick);
    }
    public CommentEntity modifycommententity()
    {
        return new CommentEntity(idx,notice,userid,content,regdate,isdelete,nick);
    }
}
