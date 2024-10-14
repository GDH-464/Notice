package com.example.Notice.Repository;

import com.example.Notice.Entity.FileEntity;
import com.example.Notice.Entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity,String> {
    Optional<FileEntity> findByIdx(Long idx);
    List<FileEntity> findByNotice(NoticeEntity notice);
}
