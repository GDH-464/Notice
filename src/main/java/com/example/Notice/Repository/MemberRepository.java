package com.example.Notice.Repository;

import com.example.Notice.Entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    Optional<MemberEntity> findByIdx(Long idx);
    Optional<MemberEntity> findByUserid(String userid);
    Optional<MemberEntity> findByNick(String nick);

    Page<MemberEntity> findByNickContaining(String search, Pageable pageable);

    Page<MemberEntity> findByUseridContaining(String search, Pageable pageable);

    Page<MemberEntity> findByEmailContaining(String search, Pageable pageable);

    void deleteByUserid(String userid);
}
