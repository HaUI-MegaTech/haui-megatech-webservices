package shop.haui_megatech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.haui_megatech.domain.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query(
            "SELECT f " +
            "FROM Feedback f " +
            "WHERE f.user.id = :userId "
    )
    Page<Feedback> findAllByUserId(Integer userId, Pageable pageable);

    @Query(
            "SELECT f " +
            "FROM Feedback f " +
            "WHERE f.product.id = :productId "
    )
    Page<Feedback> findALlByProductId(Integer productId, Pageable pageable);
}
