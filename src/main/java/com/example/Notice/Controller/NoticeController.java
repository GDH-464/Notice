package com.example.Notice.Controller;

import com.example.Notice.Dto.*;
import com.example.Notice.Entity.CommentEntity;
import com.example.Notice.Entity.FileEntity;
import com.example.Notice.Entity.NoticeEntity;
import com.example.Notice.Repository.FileRepository;
import com.example.Notice.Repository.NoticeRepository;
import com.example.Notice.Service.MemberService;
import com.example.Notice.Service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Log4j2
@AllArgsConstructor
@Controller
@Transactional
public class NoticeController {
    private static final List<Map<String, Object>> actions = Arrays.asList(
            Map.of("type", 1, "action", "/announcement"),
            Map.of("type", 2, "action", "/notice"),
            Map.of("type", 3, "action", "/weeknotice"),
            Map.of("type", 4, "action", "/monthnotice")
    );
    private final FileRepository fileRepository;
    private final MemberService memberService;
    private final NoticeService noticeService;
    @GetMapping("/notice")
    public String notice(HttpServletRequest request, Model model,
                         @RequestParam(value = "page",defaultValue = "0")int page,
                         @RequestParam(value = "sear",defaultValue = "")String sear,
                         @RequestParam(value = "search",defaultValue = "")String search)
    {
        Page announcement = noticeService.announcement();
        Page notice = noticeService.list(page,sear,search);
        ArrayList pagegroup = noticeService.page(notice,page);;
        model.addAttribute("actions", actions);
        model.addAttribute("type",2);
        model.addAttribute("announcement",announcement);
        model.addAttribute("notice", notice);
        model.addAttribute("sear", sear);          //검색유형
        model.addAttribute("search", search);     //검색어
        model.addAttribute("currentPage", page);     //현재 페이지
        model.addAttribute("totalPages", notice.getTotalPages());       //총 페이지
        model.addAttribute("pageSize", pagegroup.get(0));     //페이지 그룹 크기
        model.addAttribute("currentGroup", pagegroup.get(1));     //현재 페이지 그룹
        model.addAttribute("totalGroups", pagegroup.get(2));       //총 페이지그룹
        model.addAttribute("startPage", pagegroup.get(3));   //현재 페이지 그룹의 시작페이지
        model.addAttribute("endPage", pagegroup.get(4));       //현재 페이지 그룹의 끝페이지
        if(memberService.setsesstion(request) != null)
        {
            model.addAttribute("session",memberService.setsesstion(request));
        }
        return "notice/notice";
    }
    @GetMapping("/announcement")
    public String announcement(HttpServletRequest request, Model model,
                               @RequestParam(value = "page",defaultValue = "0")int page,
                               @RequestParam(value = "sear",defaultValue = "")String sear,
                               @RequestParam(value = "search",defaultValue = "")String search)
    {
        Page announcement = noticeService.announcement1();
        Page notice = noticeService.list1(page,sear,search);
        ArrayList pagegroup = noticeService.page(notice,page);
        model.addAttribute("actions", actions);
        model.addAttribute("type",1);
        model.addAttribute("announcement",announcement);
        model.addAttribute("notice", notice);
        model.addAttribute("sear", sear);          //검색유형
        model.addAttribute("search", search);     //검색어
        model.addAttribute("currentPage", page);     //현재 페이지
        model.addAttribute("totalPages", notice.getTotalPages());       //총 페이지
        model.addAttribute("pageSize", pagegroup.get(0));     //페이지 그룹 크기
        model.addAttribute("currentGroup", pagegroup.get(1));     //현재 페이지 그룹
        model.addAttribute("totalGroups", pagegroup.get(2));       //총 페이지그룹
        model.addAttribute("startPage", pagegroup.get(3));   //현재 페이지 그룹의 시작페이지
        model.addAttribute("endPage", pagegroup.get(4));       //현재 페이지 그룹의 끝페이지
        if(memberService.setsesstion(request) != null)
        {
            model.addAttribute("session",memberService.setsesstion(request));
        }
        return "notice/announcement";
    }
    @GetMapping("/weeknotice")
    public String weeknotice(HttpServletRequest request, Model model,
                         @RequestParam(value = "page",defaultValue = "0")int page,
                         @RequestParam(value = "sear",defaultValue = "")String sear,
                         @RequestParam(value = "search",defaultValue = "")String search)
    {
        Page announcement = noticeService.announcement();
        Page notice = noticeService.weeklist(page,sear,search);
        ArrayList pagegroup = noticeService.page(notice,page);
        model.addAttribute("actions", actions);
        model.addAttribute("type",3);
        model.addAttribute("announcement",announcement);
        model.addAttribute("notice", notice);
        model.addAttribute("sear", sear);          //검색유형
        model.addAttribute("search", search);     //검색어
        model.addAttribute("currentPage", page);     //현재 페이지
        model.addAttribute("totalPages", notice.getTotalPages());       //총 페이지
        model.addAttribute("pageSize", pagegroup.get(0));     //페이지 그룹 크기
        model.addAttribute("currentGroup", pagegroup.get(1));     //현재 페이지 그룹
        model.addAttribute("totalGroups", pagegroup.get(2));       //총 페이지그룹
        model.addAttribute("startPage", pagegroup.get(3));   //현재 페이지 그룹의 시작페이지
        model.addAttribute("endPage", pagegroup.get(4));       //현재 페이지 그룹의 끝페이지
        if(memberService.setsesstion(request) != null)
        {
            model.addAttribute("session",memberService.setsesstion(request));
        }
        return "notice/weeknotice";
    }
    @GetMapping("/monthnotice")
    public String monthnotice(HttpServletRequest request, Model model,
                             @RequestParam(value = "page",defaultValue = "0")int page,
                             @RequestParam(value = "sear",defaultValue = "")String sear,
                             @RequestParam(value = "search",defaultValue = "")String search)
    {
        Page announcement = noticeService.announcement();
        Page notice = noticeService.monthlist(page,sear,search);
        ArrayList pagegroup = noticeService.page(notice,page);
        model.addAttribute("actions", actions);
        model.addAttribute("type",4);
        model.addAttribute("announcement",announcement);
        model.addAttribute("notice", notice);
        model.addAttribute("sear", sear);          //검색유형
        model.addAttribute("search", search);     //검색어
        model.addAttribute("currentPage", page);     //현재 페이지
        model.addAttribute("totalPages", notice.getTotalPages());       //총 페이지
        model.addAttribute("pageSize", pagegroup.get(0));     //페이지 그룹 크기
        model.addAttribute("currentGroup", pagegroup.get(1));     //현재 페이지 그룹
        model.addAttribute("totalGroups", pagegroup.get(2));       //총 페이지그룹
        model.addAttribute("startPage", pagegroup.get(3));   //현재 페이지 그룹의 시작페이지
        model.addAttribute("endPage", pagegroup.get(4));       //현재 페이지 그룹의 끝페이지
        if(memberService.setsesstion(request) != null)
        {
            model.addAttribute("session",memberService.setsesstion(request));
        }
        return "notice/monthnotice";
    }
    @GetMapping("/noticewrite")
    public String write(HttpServletRequest request,Model model)
    {
        if(memberService.setsesstion(request) != null)
        {
            model.addAttribute("session",memberService.setsesstion(request));
        }
        return "notice/write";
    }
    @PostMapping("/noticewrite_proc")
    public String noticewrite_proc(NoticeDTO notice, @RequestParam(value = "ofile",required = false) MultipartFile[] files) {
        // 데이터 출력
        NoticeEntity notice1 =noticeService.write_proc(notice);
        if(files !=null && files.length > 0) {
            noticeService.upfile(notice1, files);
        }
        return "redirect:/notice";
    }
    @GetMapping("/noticeview")
    public String view(@RequestParam(value = "idx")Long idx, @RequestParam(value="userid",defaultValue = "")String userid, Model model, HttpServletRequest request, HttpServletResponse response)
    {
        if(memberService.setsesstion(request) != null)
        {
            model.addAttribute("session",memberService.setsesstion(request));
        }
        NoticeEntity notice =noticeService.view(idx,userid,request,response);
        List<FileEntity> file = noticeService.viewfile(notice);
        List<CommentEntity> comment = noticeService.commentview(notice);

        model.addAttribute("comment",comment);
        model.addAttribute("notice",notice);
        model.addAttribute("file",file);
        model.addAttribute("session",request);
        return "notice/view";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam(value = "idx") String idx)
    {
        try {
            return new ResponseEntity<Resource>(noticeService.download(idx).getStatusCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/commentadd")
    public ResponseEntity<Map<String, String>> commentadd(@RequestBody CommentGetDTO commentGetDTO)
    {
        return ResponseEntity.ok(noticeService.commentadd(commentGetDTO));
    }
    @PostMapping("/commentreturn")
    public ResponseEntity<Map<String, String>> commentreturn(@RequestBody CommentreturnDTO commentreturnDTO)
    {
        return ResponseEntity.ok(noticeService.commentreturn(commentreturnDTO));
    }
    @PostMapping("/commentmodify")
    public ResponseEntity<Map<String,String>> commentmodifytrue(@RequestBody CommentmodifyDTO commentmodifyDTO)
    {
        log.error(commentmodifyDTO);
        return ResponseEntity.ok(noticeService.commentmodify(commentmodifyDTO));
    }
    @PostMapping("/commentdelete")
    public ResponseEntity<String> commentdelete(@RequestBody CommentreturnDTO commentreturnDTO)
    {
        noticeService.commentdelete(commentreturnDTO);
        return ResponseEntity.ok("처리 완료");
    }

    @GetMapping("/noticemodify")
    public String modify()
    {
        return "notice/modify";
    }

}
