package com.example.Notice.Repository;

import com.example.Notice.Entity.CommentEntity;
import com.example.Notice.Entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
    List<CommentEntity> findByNotice(NoticeEntity notice);
}
