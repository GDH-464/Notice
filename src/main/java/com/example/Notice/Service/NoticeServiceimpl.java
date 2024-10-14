package com.example.Notice.Service;

import com.example.Notice.Dto.FileDTO;
import com.example.Notice.Dto.NoticeDTO;
import com.example.Notice.Entity.*;
import com.example.Notice.Repository.FileRepository;
import com.example.Notice.Repository.MemberRepository;
import com.example.Notice.Repository.NoticeRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Transactional
@Service
@AllArgsConstructor
public class NoticeServiceimpl implements NoticeService {
    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final FileRepository fileRepository;
    @Override
    public NoticeEntity write_proc(NoticeDTO notice)
    {
        Optional<MemberEntity> memberOptional = memberRepository.findByUserid(notice.getUserid());
        MemberEntity member = memberOptional.get();
        notice.setMember(member);
        NoticeEntity noticeEntity = notice.tonoticeentity();
        noticeRepository.save(noticeEntity);

        List<NoticeEntity> noticeEntities = noticeRepository.findByMember(notice.getMember());
        if (noticeEntities.isEmpty()) {
            throw new RuntimeException("No notice found for the member");
        }
        return noticeEntity;

    }

    @Override
    public void upfile(NoticeEntity notice, MultipartFile[] files)
    {
        if (files == null || files.length == 0) {
            return;
        }
        List<NoticeEntity> noticeEntities = noticeRepository.findByMember(notice.getMember());
        if (noticeEntities.isEmpty()) {
            throw new RuntimeException("No notice found for the member");
        }
        String dir = "D:\\data\\file";
        Path upPath = Paths.get(dir);

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
                continue;
            }

            FileDTO fileDto = new FileDTO();
            fileDto.setNoticeidx(notice); // NoticeEntity 설정
            fileDto.setUserid(notice.getMember().getUserid()); // 현재 로그인된 사용자 ID 설정 (예시)
            fileDto.setSfile(file.getOriginalFilename()); // 서버에 저장될 파일명 설정
            fileDto.setOfile(file.getOriginalFilename()); // 원래 파일명 설정
            fileDto.setNick(notice.getMember().getNick()); // 사용자 닉네임 설정 (예시)

            FileEntity fileEntity = fileDto.tofileentity();

            fileRepository.save(fileEntity);

            try {
                // 실제 파일 저장 로직 (서버에 파일 저장)
                Path targetLocation = upPath.resolve(fileEntity.getSfile());  // 저장할 파일 경로
                Files.copy(file.getInputStream(), targetLocation);

                // 파일 정보 저장 (필요 시 DB에 fileEntity 저장 로직 추가)
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
            }
        }
    }
    @Override
    public Page announcement()
    {
        Sort sort = Sort.by(Sort.Direction.DESC, "idx");
        Pageable pageable = PageRequest.of(0, 2, sort);
        Page<NoticeEntity> announcement = noticeRepository.findByGrade(1L,pageable);

        return announcement;
    }
    @Override
    public Page list(int page, String search, String sear)
    {
        Sort sort = Sort.by(Sort.Direction.DESC, "idx");
        Pageable pageable = PageRequest.of(page, 8, sort);
        Page<NoticeEntity> list = null;
        switch(sear) {
            case "title" :       //제목으로 검색했을때
                list = noticeRepository.findByTitleAndGradeContaining(search,2L, pageable);
                break;
            case "userid" :     //아이디로 검색했을때
                Optional<MemberEntity> member = memberRepository.findByUserid(search);
                list = noticeRepository.findByMemberAndGradeContaining(member.get(),2L, pageable);
                break;
            default:        //해당사항이 없을때
                list = this.noticeRepository.findByGrade(2L,pageable);
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
    public Page announcement1()
    {
        Sort sort = Sort.by(Sort.Direction.DESC, "idx");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<NoticeEntity> announcement = noticeRepository.findByGrade(1L,pageable);

        return announcement;
    }
    @Override
    public Page list1(int page, String search, String sear)
    {
        Sort sort = Sort.by(Sort.Direction.DESC, "idx");
        Pageable pageable = PageRequest.of(page, 10, sort);
        Page<NoticeEntity> list = null;
        switch(sear) {
            case "title" :       //제목으로 검색했을때
                list = noticeRepository.findByTitleAndGradeContaining(search,1L, pageable);
                break;
            case "userid" :     //아이디로 검색했을때
                Optional<MemberEntity> member = memberRepository.findByUserid(search);
                list = noticeRepository.findByMemberAndGradeContaining(member.get(),1L, pageable);
                break;
            default:        //해당사항이 없을때
                list = this.noticeRepository.findByGrade(1L,pageable);
                break;
        }
        return list;
    }
    @Override
    public Page weeklist(int page, String search, String sear)
    {
        Sort sort = Sort.by(Sort.Direction.DESC, "idx");
        Pageable pageable = PageRequest.of(page, 8, sort);
        Page<NoticeEntity> list = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        java.util.Date oneWeekAgo = cal.getTime(); // java.util.Date
        java.sql.Date sqlDate = new java.sql.Date(oneWeekAgo.getTime()); // java.sql.Date

        switch (sear) {
            case "title":
                list = noticeRepository.findByTitleContainingAndGradeAndRegdateAfter(search, 2L, sqlDate, pageable);
                break;
            case "userid":
                Optional<MemberEntity> member = memberRepository.findByUserid(search);
                list = noticeRepository.findByMemberContainingAndGradeAndRegdateAfter(member.get(), 2L, sqlDate, pageable);
                break;
            default:
                list = this.noticeRepository.findByRegdateAfterAndGrade(sqlDate, 2L, pageable);
                break;
        }
        return list;
    }
    @Override
    public Page monthlist(int page, String search, String sear)
    {
        Sort sort = Sort.by(Sort.Direction.DESC, "idx");
        Pageable pageable = PageRequest.of(page, 8, sort);
        Page<NoticeEntity> list = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        java.util.Date oneWeekAgo = cal.getTime(); // java.util.Date
        java.sql.Date sqlDate = new java.sql.Date(oneWeekAgo.getTime()); // java.sql.Date

        switch (sear) {
            case "title":
                list = noticeRepository.findByTitleContainingAndGradeAndRegdateAfter(search, 2L, sqlDate, pageable);
                break;
            case "userid":
                Optional<MemberEntity> member = memberRepository.findByUserid(search);
                list = noticeRepository.findByMemberContainingAndGradeAndRegdateAfter(member.get(), 2L, sqlDate, pageable);
                break;
            default:
                list = this.noticeRepository.findByRegdateAfterAndGrade(sqlDate, 2L, pageable);
                break;
        }
        return list;
    }
    @Override
    public NoticeEntity view(Long idx, String userid, HttpServletRequest request, HttpServletResponse response)
    {
        NoticeEntity notice = noticeRepository.findByIdx(idx).get();
        Cookie[] cookies = request.getCookies();

        if(cookies !=null) {
            for(Cookie cookie : cookies) {
                if (notice.getIdx().toString().equals(cookie.getValue()) ) {
                    return notice;
                }
            }
        }
        NoticeDTO noticeDTO = new NoticeDTO(notice.getIdx(), notice.getTitle(), notice.getMember(), notice.getContent(), notice.getRegdate(), notice.getGrade(), notice.getNick(), notice.getHit());
        Long hit = notice.getHit() + 1;
        noticeDTO.setHit(hit);
        noticeRepository.save(noticeDTO.tonoticeentity());

        Cookie sessionCookie = new Cookie("sessionidx", String.valueOf(idx));
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(60 * 60 * 24);
        response.addCookie(sessionCookie);
        return notice;
    }
    @Override
    public List<FileEntity> viewfile(NoticeEntity notice)
    {
        List<FileEntity> fileEntityList = fileRepository.findByNotice(notice);
        return fileEntityList;
    }
    @Override
    public CommentEntity commentEntityadd(String userid, String comment, String nick, Long noticeidx)
    {

        return new CommentEntity();
    }
    @Override
    public ResponseEntity<Resource> download(String idx) throws IOException {
        try {
            log.error(idx);
            FileEntity file = fileRepository.findByIdx(Long.valueOf(idx)).get();
            Path filePath = Paths.get("D:\\data\\file\\" + file.getSfile());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOfile() + "\"")
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
