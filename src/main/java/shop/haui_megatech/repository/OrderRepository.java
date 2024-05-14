package shop.haui_megatech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.haui_megatech.domain.entity.Order;

import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o")
    Page<Order> findByAll(Pageable pageable);

    @Query(
            "SELECT o FROM Order o " +
            "WHERE o.user.id = :userId " +
            "AND (" +
            "o.address.district LIKE CONCAT('%', :keyword, '%')" +
            ")"
    )
    Page<Order> searchOrderForUser(String keyword, Integer userId, Pageable pageable);

    @Query(
            "SELECT o FROM Order o " +
            "WHERE o.address.district LIKE CONCAT('%', :keyword, '%')"
    )
    Page<Order> searchOrderForAdmin(String keyword, Pageable pageable);

    @Query(
            "SELECT o FROM Order o " +
            "WHERE o.user.id = :userId"
    )
    Page<Order> findOrderByUserId(Integer userId, Pageable pageable);

    @Query(
            "SELECT o FROM Order o " +
            "WHERE o.id = :orderId " +
            "AND o.user.id = :userId"
    )
    Optional<Order> findOrderDetailById_UserId(Integer orderId, Integer userId);
}
