package com.example.Notice.Controller;

import com.example.Notice.Dto.MemberDTO;
import com.example.Notice.Entity.MemberEntity;
import com.example.Notice.Repository.MemberRepository;
import com.example.Notice.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;


@Log4j2
@AllArgsConstructor
@Controller
@Transactional
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    @GetMapping
    public String home(Model model, HttpServletRequest request)
    {
        if(memberService.setsesstion(request) != null)
        {
            model.addAttribute("session",memberService.setsesstion(request));
        }

        return "member/home";
    }

    @PostMapping("/userid_check") // 사용자 ID 검사
    public ResponseEntity<Map<String, String>> checkUserId(@RequestBody Map<String, String> requestBody) {
        return ResponseEntity.ok(memberService.userid_check(requestBody)); // JSON 형태로 반환
    }

    @PostMapping("/pwd_check") //사용자 비밀번호 검사
    public ResponseEntity<Map<String, String>> checkPwd(@RequestBody Map<String,String> requestBody)
    {
        return  ResponseEntity.ok(memberService.pwd_check(requestBody));
    }

    @PostMapping("/nick_check") // 사용자 닉네임검사
    public ResponseEntity<Map<String, String>> nick_check(@RequestBody Map<String, String> requestBody) {
        return ResponseEntity.ok(memberService.nick_check(requestBody)); // JSON 형태로 반환
    }

    @GetMapping("/join")
    public String join()
    {
        return "member/join";
    }

    @PostMapping("/join_proc") // 가입될때 process
    public String join_proc(MemberDTO member)
    {
            memberService.join_proc(member);

        return "redirect:/";
    }
    @GetMapping("/login")
    public String login()
    {
        return "member/login";
    }
    @PostMapping("/login_check")
    public ResponseEntity<Map<String, String>> login_check(@RequestBody Map<String, String> requestBody,HttpServletRequest request)
    {
        return ResponseEntity.ok(memberService.login_check(requestBody,request));
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        memberService.logout(request,response);
        return "redirect:/";
    }

    @GetMapping("memberlist")
    public String memberlist(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "sear", defaultValue = "") String sear,
                       @RequestParam(value = "search", defaultValue = "") String search,
                       HttpServletRequest request) {
        if (memberService.setsesstion(request) != null) {
            model.addAttribute("session",memberService.setsesstion(request));
        }

        Page member = memberService.list(page, search, sear);
        ArrayList pagegroup = memberService.page(member, page);
        model.addAttribute("member", member);
        model.addAttribute("sear", sear);          //검색유형
        model.addAttribute("search", search);     //검색어
        model.addAttribute("currentPage", page);     //현재 페이지
        model.addAttribute("totalPages", member.getTotalPages());       //총 페이지
        model.addAttribute("pageSize", pagegroup.get(0));     //페이지 그룹 크기
        model.addAttribute("currentGroup", pagegroup.get(1));     //현재 페이지 그룹
        model.addAttribute("totalGroups", pagegroup.get(2));       //총 페이지그룹
        model.addAttribute("startPage", pagegroup.get(3));   //현재 페이지 그룹의 시작페이지
        model.addAttribute("endPage", pagegroup.get(4));       //현재 페이지 그룹의 끝페이지
        return "member/memberlist";
    }

    @GetMapping("/memberview")
    public String memberview(@RequestParam(value = "userid") String userid,HttpServletRequest request,Model model){
        if (memberService.setsesstion(request) != null) {
            model.addAttribute("session",memberService.setsesstion(request));
        }
        MemberEntity member =  memberService.useridview(userid).get();
        model.addAttribute("member",member);
        model.addAttribute("imagepath","D:\\Notice\\src\\main\\resources\\static\\data\\icon");
        return "member/memberview";
    }
    @GetMapping("/modify")
    public String modify(@RequestParam(value = "userid")String userid,HttpServletRequest request,Model model)
    {
        if (memberService.setsesstion(request) != null) {
            model.addAttribute("session",memberService.setsesstion(request));
        }
        MemberEntity member =  memberService.useridview(userid).get();
        model.addAttribute("member",member);
        return "member/modify";
    }

    @PostMapping("/modify_proc")
    public String modify_proc(MemberDTO member, @RequestParam(value="existingIcon",defaultValue = "")String existingIcon,
                              @RequestParam(value="moregdate",defaultValue = "") Date moregdate, @RequestParam(value = "deleteIcon", required = false) Boolean deleteIcon)
    {
        memberService.modify_proc(member,existingIcon,moregdate,deleteIcon);
        return "redirect:/";
    }
    @GetMapping("/delete")
    public String del(@RequestParam(value="userid") String userid,HttpServletRequest request)
    {
        memberService.delete(userid);
        Long grade = (Long) request.getSession().getAttribute("grade");
        if (grade != null && grade.equals(1L)) {
            return "redirect:memberlist";
        }
        return "redirect:/";
    }
    @GetMapping("/iconimg")
    public ResponseEntity<Resource> icon(@RequestParam(value = "idx") Long idx) {
        try {
            ResponseEntity<Resource> response = memberService.icon(idx);
            return response;
        } catch (IOException e) {
            log.error("Error retrieving icon for user ID: {}. Exception: {}", idx, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
