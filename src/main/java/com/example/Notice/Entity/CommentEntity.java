package com.example.Notice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@ToString
@Getter
@Table(name="noticecomment")
@NoArgsConstructor
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "comment")
    @SequenceGenerator(name="comment",sequenceName = "comment_sequence",allocationSize = 1)
    private Long idx;
    @ManyToOne
    @JoinColumn(name = "noticeidx",referencedColumnName="idx")
    private NoticeEntity notice;
    private String userid;
    private String content;
    private Date regdate;
    private String isdelete;
    private String nick;

    public CommentEntity(Long idx,NoticeEntity notice,String userid,String content,Date regdate,String isdelete,String nick)
    {
        this.idx = idx;
        this.notice = notice;
        this.userid = userid;
        this.content = content;
        this.regdate = regdate;
        this.isdelete = isdelete;
        this.nick = nick;
    }
}
