package com.example.Notice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Table(name="notice")
@NoArgsConstructor
@Entity
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "noticeseq")
    @SequenceGenerator(name="noticeseq",sequenceName = "notice_seq",allocationSize = 1)
    private Long idx;
    @OneToMany(mappedBy = "notice",cascade = CascadeType.ALL)
    @JsonIgnore
    List<FileEntity> fileEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "notice",cascade = CascadeType.ALL)
    @JsonIgnore
    List<CommentEntity> commentEntityList = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userid",referencedColumnName = "userid")
    private MemberEntity member;

    private String title;
    private String content;
    private Date regdate;
    private Long grade;
    private String nick;
    private Long hit;

    public NoticeEntity(Long idx,String title, MemberEntity member, String content, Date regdate, Long grade, String nick,Long hit)
    {
        this.idx = idx;
        this.title = title;
        this.member = member;
        this.content = content;
        this.regdate = regdate;
        this.grade = grade;
        this.nick = nick;
        this.hit = hit;
    }
}
