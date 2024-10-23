package com.example.Notice.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @JsonBackReference
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
