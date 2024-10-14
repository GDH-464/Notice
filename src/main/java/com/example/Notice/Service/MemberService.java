package com.example.Notice.Service;

import com.example.Notice.Dto.MemberDTO;
import com.example.Notice.Entity.MemberEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public interface MemberService {

    Map<String, String> userid_check(Map<String, String> requestBody);

    Map<String, String> pwd_check(Map<String, String> requestBody);

    Map<String, String> nick_check(Map<String, String> requestBody);

    void join_proc(MemberDTO member);

    Map<String, String> login_check(Map<String, String> requestBody, HttpServletRequest request);


    void logout(HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> setsesstion(HttpServletRequest request);

    Page list(int page, String search, String sear);

    ArrayList page(Page list, int page);

    Optional<MemberEntity> useridview(String userid);

    void modify_proc(MemberDTO member, String existingIcon, Date moregdate, Boolean deleteIcon);

    void delete(String userid);

    ResponseEntity<Resource> icon(Long idx) throws IOException;
}
