package com.example.Notice.Dto;

import com.example.Notice.Entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

import static org.thymeleaf.util.StringUtils.substring;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private Long idx;
    private String userid;
    private String pwd;
    private String name;
    private String nick;
    private String email;
    private String email2;
    private String email3;
    private Long postnumber;
    private String street;
    private String address;
    private Date regdate = new Date(System.currentTimeMillis());
    private Long grade;
    MultipartFile icon;

    public MemberEntity toMemberEntity()
    {
        switch (email2)
        {
            case "other" : email = email + "@" +email3; break;
            case "" : break;
            default : email = email + "@" + email2; break;
        }
        String icon1 ="";
        if(icon !=null && icon.getOriginalFilename()!=null&&!icon.getOriginalFilename().isEmpty()) {
            String ext = icon.getOriginalFilename().substring(icon.getOriginalFilename().lastIndexOf("."));
            icon1 = Long.toString(System.currentTimeMillis()) + ext;
        }
        return new MemberEntity(idx,userid,pwd,name,nick,email,postnumber,street,address,regdate,grade,icon1);
    }
    public MemberEntity modifyMemberEntity(String existingIcon,Date moregdate)
    {

        String icon1 ="";
        if (icon != null && icon.getOriginalFilename() != null && !icon.getOriginalFilename().isEmpty()) {
            String ext = icon.getOriginalFilename().substring(icon.getOriginalFilename().lastIndexOf("."));
            icon1 = Long.toString(System.currentTimeMillis()) + ext;
        } else {
            // 기존 아이콘 사용
            icon1 = existingIcon; // 기존 아이콘 경로 사용
        }
        return new MemberEntity(idx,userid,pwd,name,nick,email,postnumber,street,address,moregdate,grade,icon1);
    }
}
