package com.example.Notice.Repository;

import com.example.Notice.Entity.MemberEntity;
import com.example.Notice.Entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.sql.Date;

public interface NoticeRepository extends JpaRepository<NoticeEntity,Long> {
    List<NoticeEntity> findByMember(MemberEntity member);
    Optional<NoticeEntity> findByIdx(Long idx);
    Page<NoticeEntity> findByGrade(Long grade, Pageable pageable);

    Page<NoticeEntity> findByTitleAndGradeContaining(String search,Long grade, Pageable pageable);

    Page<NoticeEntity> findByMemberAndGradeContaining(MemberEntity member,Long grade, Pageable pageable);

    Page<NoticeEntity> findByRegdateAfterAndGrade(Date regdate, Long grade, Pageable pageable);

    Page<NoticeEntity> findByTitleContainingAndGradeAndRegdateAfter(String title, Long grade, Date regdate, Pageable pageable);

    Page<NoticeEntity> findByMemberContainingAndGradeAndRegdateAfter(MemberEntity member, Long grade, Date regdate, Pageable pageable);

}
