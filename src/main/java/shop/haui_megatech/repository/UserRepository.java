package shop.haui_megatech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.haui_megatech.domain.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.deleted = false OR u.deleted IS NULL")
    Page<User> getAllActiveUsers(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.deleted = true")
    Page<User> getAllDeletedUsers(Pageable pageable);

    @Query(
            "SELECT u FROM User u WHERE (u.username LIKE %?1% " +
                    "OR u.email LIKE %?1%) AND (u.deleted = false OR u.deleted IS NULL)"
    )
    Page<User> searchActiveUsers(String keyword, Pageable pageable);

    @Query(
            "SELECT u FROM User u WHERE (u.username LIKE %?1% " +
                    "OR u.email LIKE %?1%) AND u.deleted = true"
    )
    Page<User> searchDeletedUsers(String keyword, Pageable pageable);
}
