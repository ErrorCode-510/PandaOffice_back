package com.errorCode.pandaOffice.notice.domain.repository;

import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    // 전체 공지사항을 최신순으로 조회
    Page<Notice> findAll(Pageable pageable);

    // 분류와 소분류별 공지사항 조회 (페이징 및 정렬) (최신순으로 조회)
    Page<Notice> findByCategoryAndSubCategory(String category, String subCategory, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Notice n SET n.viewCount = n.viewCount + 1 WHERE n.noticeId = :noticeId")
    void incrementViewCount(@Param("noticeId") int noticeId);
}
