package shop.haui_megatech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.haui_megatech.domain.entity.CartItem;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @Query(
            "SELECT c FROM CartItem c " +
            "INNER JOIN User u ON u.id = c.user.id " +
            "INNER JOIN Product p ON p.id = c.product.id " +
            "WHERE c.user.id = :userId "
    )
    Page<CartItem> getCartItemsByUserId(Integer userId, Pageable pageable);


    @Query(
            "SELECT c FROM CartItem c " +
            "INNER JOIN User u ON u.id = c.user.id " +
            "INNER JOIN Product p ON p.id = c.product.id " +
            "WHERE c.user.id = :userId " +
            "AND LOWER(CONCAT(c.product.name, c.product.processor, c.product.storage)) LIKE LOWER(CONCAT('%', :keyword, '%')) "
    )
    Page<CartItem> searchCartItemsByUserIdAndKeyword(String keyword, Integer userId, Pageable pageable);


    @Query(
            "DELETE FROM CartItem c " +
            "WHERE c.id IN :ids "
    )
    @Modifying
    @Transactional
    void deleteAllByIds(List<Integer> ids);


}
