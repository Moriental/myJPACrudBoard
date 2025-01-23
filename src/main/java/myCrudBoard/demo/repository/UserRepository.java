package myCrudBoard.demo.repository;

import myCrudBoard.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findById(long id);
    Boolean existsByNickname(String nickname);
}
