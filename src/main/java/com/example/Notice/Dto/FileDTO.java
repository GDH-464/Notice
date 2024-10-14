package com.example.Notice.Dto;

import com.example.Notice.Entity.FileEntity;
import com.example.Notice.Entity.NoticeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private Long idx;
    private NoticeEntity noticeidx;
    private String userid;
    private String sfile;
    private String ofile;
    private String nick;

    public FileEntity tofileentity()
    {
        String ext = ofile.substring(ofile.lastIndexOf("."));
        sfile = Long.toString(System.currentTimeMillis()) + ext;
        return new FileEntity(idx,noticeidx,userid,sfile,ofile,nick);
    }
}
