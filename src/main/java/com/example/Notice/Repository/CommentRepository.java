package com.example.Notice.Repository;

import com.example.Notice.Dto.CommentDTO;
import com.example.Notice.Entity.CommentEntity;
import com.example.Notice.Entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
    Optional<CommentEntity> findByIdx(Long idx);
    List<CommentEntity> findByNotice(NoticeEntity notice);
}
