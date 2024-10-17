package com.example.Notice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Getter
@Table(name="member")
@NoArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq")
    @SequenceGenerator(name="seq",sequenceName = "mem_seq",allocationSize = 1)
    private Long idx;
    private String userid;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<NoticeEntity> noticeEntityList = new ArrayList<>();

    private String pwd;
    private String name;
    private String nick;
    private String email;
    private Long postnumber;
    private String street;
    private String address;
    private Date regdate;
    private Long grade;
    private String icon;

    public MemberEntity(Long idx, String userid, String pwd,String name,String nick,String email,Long postnumber,String Street, String address, Date regdate,Long grade,String icon)
    {
        this.idx = idx;
        this.userid = userid;
        this.name = name;
        this.nick = nick;
        this.pwd = pwd;
        this.email = email;
        this.postnumber = postnumber;
        this.street = Street;
        this.address = address;
        this.regdate = regdate != null ? regdate : new Date(System.currentTimeMillis());
        this.grade = grade;
        this.icon = icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
