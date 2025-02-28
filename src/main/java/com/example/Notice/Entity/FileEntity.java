package com.example.Notice.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Table(name="noticefile")
@NoArgsConstructor
@Entity
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "fileseq")
    @SequenceGenerator(name="fileseq",sequenceName = "noticefile_seq",allocationSize = 1)
    private Long idx;
    @ManyToOne
    @JoinColumn(name = "noticeidx")
    private NoticeEntity notice;

    private String userid;
    private String sfile;
    private String ofile;
    private String nick;

    public FileEntity(Long idx,NoticeEntity notice,String userid,String sfile,String ofile,String nick)
    {
        this.idx=idx;
        this.notice=notice;
        this.userid=userid;
        this.sfile=sfile;
        this.ofile=ofile;
        this.nick=nick;
    }
}
