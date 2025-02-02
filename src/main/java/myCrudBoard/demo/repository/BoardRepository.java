package myCrudBoard.demo.repository;

import myCrudBoard.demo.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    //user가 안불러지는 거 같아 fetch join 쿼리
    //fetch join 오류 아니였음 그래도 공부 위해서 지우지는 않음
    @Query("select b from Board b JOIN fetch b.user where b.id=:id")
    Optional<Board> findByIdWithUser(@Param("id") Long id);
    Board findById(long id);
    //페이징 메소드
    Page<Board> findAll(Pageable pageable);

    //
    Page<Board> findByTitleContaining(String title,Pageable pageable);

    //제목으로 검색하는 키워드
    List<Board> findByTitleContaining(String keyword);
}
