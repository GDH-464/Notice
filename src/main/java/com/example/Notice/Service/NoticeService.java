package com.example.Notice.Service;

import com.example.Notice.Dto.CommentGetDTO;
import com.example.Notice.Dto.NoticeDTO;
import com.example.Notice.Entity.CommentEntity;
import com.example.Notice.Entity.FileEntity;
import com.example.Notice.Entity.NoticeEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface NoticeService {


    NoticeEntity write_proc(NoticeDTO notice);


    void upfile(NoticeEntity notice, MultipartFile[] files);

    Page announcement();

    Page list(int page, String search, String sear);

    ArrayList page(Page list, int page);

    Page announcement1();

    Page list1(int page, String search, String sear);

    Page weeklist(int page, String search, String sear);

    Page monthlist(int page, String search, String sear);

    NoticeEntity view(Long idx, String userid, HttpServletRequest request, HttpServletResponse response);

    List<FileEntity> viewfile(NoticeEntity notice);

    CommentEntity commentEntityadd(String userid, String comment, String nick, Long noticeidx);

    ResponseEntity<Resource> download(String idx) throws IOException;

    Map<String, String> commentadd(CommentGetDTO commentGetDTO);
}
