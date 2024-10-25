package com.example.Notice;

import com.example.Notice.Entity.FileEntity;
import com.example.Notice.Entity.MemberEntity;
import com.example.Notice.Entity.NoticeEntity;
import com.example.Notice.Repository.FileRepository;
import com.example.Notice.Repository.MemberRepository;
import com.example.Notice.Repository.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
class NoticeApplicationTests {
	@Autowired
	private final MemberRepository memberRepository;
	@Autowired
	private final NoticeRepository noticeRepository;
	@Autowired
	private final FileRepository fileRepository;

    NoticeApplicationTests(MemberRepository memberRepository, NoticeRepository noticeRepository, FileRepository fileRepository) {
        this.memberRepository = memberRepository;
        this.noticeRepository = noticeRepository;
        this.fileRepository = fileRepository;
    }

    @Test
	void contextLoads() {
		NoticeEntity notice = noticeRepository.findByIdx(28L).get();
		List<FileEntity> fileEntities = fileRepository.findByNotice(notice);
		System.out.printf(fileEntities.toString());
	}

}
