package myCrudBoard.demo.repository;

import myCrudBoard.demo.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    //user가 안불러지는 거 같아 fetch join 쿼리
    @Query("select b from Board b JOIN fetch b.user where b.id=:id")
    Optional<Board> findByIdWithUser(@Param("id") Long id);
}
