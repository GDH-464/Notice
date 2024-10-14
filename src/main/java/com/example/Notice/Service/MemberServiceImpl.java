package com.example.Notice.Service;

import com.example.Notice.Dto.MemberDTO;
import com.example.Notice.Entity.MemberEntity;
import com.example.Notice.Repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Log4j2
@Transactional
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    @Override
    public Map<String, String> userid_check(Map<String, String> requestBody)
    {
        String userid = requestBody.get("userid");

        Map<String, String> response = new HashMap<>();
        if(userid.equals(""))
        {
            response.put("result", "P");
            return response;
        }
        // 정규 표현식을 사용하여 ID 형식 검사 (6~20자 알파벳, 숫자만 가능)
        if (!Pattern.matches("^[a-zA-Z0-9]{6,20}$", userid)) {
            response.put("result", "C");// 형식에 맞지 않음
            return response;
        }
        if (memberRepository.findByUserid(userid).isPresent()) {
            response.put("result", "N");// 중복된 아이디
            return response;
        }
        response.put("result", "Y"); // 사용 가능한 아이디
        return response;
    }
    @Override
    public Map<String, String> pwd_check(Map<String, String> requestBody)
    {
        String pwd = requestBody.get("pwd") !=null ? requestBody.get("pwd") : "";
        String pwdch = requestBody.get("pwdch") != null ? requestBody.get("pwdch") : "";
        Map<String, String> response = new HashMap<>();
        if(pwd.equals(""))
        {
            response.put("result", "P");
            return response;
        }
        // 정규 표현식을 사용하여 ID 형식 검사 (6~20자 알파벳, 숫자만 가능)
        if (!Pattern.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{6,20}$", pwd)) {
            response.put("result", "C");// 형식에 맞지 않음
            return response;
        }
        if (pwd.equals(pwdch)) {
            response.put("result", "Y");//사용가능
            return response;
        }
        response.put("result", "N"); //비밀번호가 맞지않음
        return response;
    }
    @Override
    public Map<String, String> nick_check(Map<String, String> requestBody)
    {
        String nick = requestBody.get("nick");
        String userid = requestBody.get("userid") == null ? "" : requestBody.get("userid");
        Map<String, String> response = new HashMap<>();
        if(nick.equals(""))
        {
            response.put("result", "P");
            return response;
        }
        // 정규 표현식을 사용하여 ID 형식 검사 (6~20자 알파벳, 숫자만 가능)
        if (!Pattern.matches("^.{2,20}$", nick)) {
            response.put("result", "C");// 형식에 맞지 않음
            return response;
        }
        Optional<MemberEntity> memberOptional = memberRepository.findByNick(nick);
        if (memberOptional.isPresent()) {
            MemberEntity member = memberOptional.get();
            System.out.println("Request userid: " + userid);
            System.out.println("DB userid: " + member.getUserid());

            if (userid.equals(member.getUserid())) {
                response.put("result", "Y"); // 사용 가능한 아이디
                return response;
            }
            response.put("result", "N"); // 중복된 아이디
            return response;
        }
        response.put("result", "Y"); // 사용 가능한 아이디
        return response;
    }
    @Override
    public void join_proc(MemberDTO member)
    {
        String dir = "D:\\Notice\\src\\main\\resources\\static\\data\\icon";
        Path upPath = Paths.get(dir);

        MemberEntity memberEntity = member.toMemberEntity();
        MultipartFile file =  member.getIcon()==null ? null : member.getIcon();
        Path targetLocation = upPath.resolve(memberEntity.getIcon());
        if(file ==null) {
            try {
                Files.copy(file.getInputStream(), targetLocation);
            } catch (Exception e) {
                System.out.println(e.toString());
                return;
            }
        }
        memberRepository.save(memberEntity);
    }
    @Override
    public Map<String, String> login_check(Map<String, String> requestBody, HttpServletRequest request)
    {
        String userid = requestBody.get("userid");
        String pwd = requestBody.get("pwd");
        Optional<MemberEntity> memberOptional = memberRepository.findByUserid(userid);
        Map<String, String> response = new HashMap<>();
        if(!memberOptional.isPresent())
        {
            response.put("result","N");
            return response;
        }
        else if(!pwd.equals(memberOptional.get().getPwd()))
        {
            response.put("result","PN");
            return response;
        }
        HttpSession session = request.getSession();
        session.setAttribute("userid",userid);
        session.setAttribute("nick",memberOptional.get().getNick());
        session.setAttribute("grade",memberOptional.get().getGrade());
        response.put("result","Y");
        return response;
    }
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession(false);
        if(session != null)
        {
            session.invalidate();
        }
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    @Override
    public Map<String, Object> setsesstion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Map<String, Object> sessionInfo = new HashMap<>();

        if (session != null) {
            sessionInfo.put("userid", session.getAttribute("userid"));
            sessionInfo.put("nick", session.getAttribute("nick"));
            sessionInfo.put("grade", session.getAttribute("grade"));
        } else {
            sessionInfo.put("userid", null);
            sessionInfo.put("nick", null);
            sessionInfo.put("grade", null);
        }
        return sessionInfo;
    }
    @Override
    public Page list(int page, String search, String sear)
    {
        Sort sort = Sort.by(Sort.Direction.DESC, "idx");
        Pageable pageable = PageRequest.of(page, 10, sort);
        Page<MemberEntity> list = null;

        switch(sear) {
            case "nick" :       //닉네임으로 검색했을때
                list = memberRepository.findByNickContaining(search, pageable);
                break;
            case "userid" :     //아이디로 검색했을때
                list = memberRepository.findByUseridContaining(search, pageable);
                break;
            case "email" :        //이메일로 검색했을때
                list = memberRepository.findByEmailContaining(search, pageable);
                break;
            default:        //해당사항이 없을때
                list = this.memberRepository.findAll(pageable);
                break;
        }
        return list;
    }
    @Override
    public ArrayList page(Page list, int page)
    {
        ArrayList page1 = new ArrayList();
        int pageSize = 5; // 페이지 그룹 크기
        page1.add(pageSize);
        int currentGroup = page / pageSize; // 현재 페이지 그룹
        page1.add(currentGroup);
        int totalGroups = (int) Math.ceil((double) list.getTotalPages() / pageSize); // 전체 페이지 그룹 수
        page1.add(totalGroups);
        int startPage = currentGroup * pageSize; // 현재 페이지 그룹의 시작 페이지
        page1.add(startPage);
        int endPage = Math.min(startPage + pageSize - 1, list.getTotalPages() - 1); // 현재 페이지 그룹의 끝 페이지
        page1.add(endPage);
        return page1;
    }
    @Override
    public Optional<MemberEntity> useridview(String userid)
    {
        Optional<MemberEntity> memberview = memberRepository.findByUserid(userid);
        return memberview;
    }
    @Override
    public void modify_proc(MemberDTO member, String existingIcon, Date moregdate, Boolean deleteIcon) {
        String dir = "D:\\data\\icon";
        Path upPath = Paths.get(dir);
        deleteIcon = deleteIcon != null;
        MemberEntity memberEntity = member.modifyMemberEntity(existingIcon, moregdate);
        MultipartFile file = member.getIcon(); // icon이 null인지 체크
        if (deleteIcon) {
            String filePath = dir + "\\" + existingIcon; // 파일 경로 설정
            File files = new File(filePath);
            files.delete(); // 파일 삭제
            memberEntity.setIcon("");
        }
        else {
            memberEntity.setIcon(existingIcon);
        }
        if (file != null && !file.isEmpty()) { // 파일이 존재하는 경우
            String fileName = Long.toString(System.currentTimeMillis()) +
                    file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            Path targetLocation = upPath.resolve(fileName);
            try {
                Files.copy(file.getInputStream(), targetLocation);
                memberEntity.setIcon(fileName); // 새로운 아이콘 경로 설정

            } catch (IOException e) {
                e.printStackTrace(); // 예외 출력
                return;
            }
        }
        memberRepository.save(memberEntity);
    }
    @Override
    public void delete(String userid)
    {
        memberRepository.deleteByUserid(userid);
    }
    @Override
    public ResponseEntity<Resource> icon(Long idx) throws IOException {
        try {
            MemberEntity member = memberRepository.findByIdx(idx).get();
            String iconimg = member.getIcon()==null ? "아이콘X.jpg":member.getIcon();
            Path filePath = Paths.get("D:\\data\\icon\\"+iconimg);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        //.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().build();
        } catch (IOException ex) {
            return ResponseEntity.status(500).build();
        }

    }

}
