package com.example.Notice;

import com.example.Notice.Entity.MemberEntity;
import com.example.Notice.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
class NoticeApplicationTests {
	private final MemberRepository memberRepository;

    NoticeApplicationTests(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Test
	void contextLoads() {
		Optional<MemberEntity> memberEntityOptional = memberRepository.findByUserid("Grille15");
		MemberEntity member = memberEntityOptional.get();
		System.out.printf(member.toString());
	}

}
