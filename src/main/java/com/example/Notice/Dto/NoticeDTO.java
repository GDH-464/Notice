package com.example.Notice.Dto;

import com.example.Notice.Entity.MemberEntity;
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
public class NoticeDTO {
    private Long idx;
    private String title;
    private String userid;
    private MemberEntity member;
    private String content;
    private Date regdate = new Date(System.currentTimeMillis());
    private Long grade;
    private String nick;
    private Long hit;

    public NoticeDTO(Long idx, String title, MemberEntity member, String content, Date regdate, Long grade, String nick, Long hit) {
        this.idx = idx;
        this.title = title;
        this.member = member;
        this.content = content;
        this.regdate = regdate;
        this.grade = grade;
        this.nick = nick;
        this.hit = hit;
    }

    public NoticeEntity tonoticeentity()
    {
        hit = hit==null ? 0L : hit;
        return new NoticeEntity(idx,title,member,content,regdate,grade,nick,hit);
    }
}
