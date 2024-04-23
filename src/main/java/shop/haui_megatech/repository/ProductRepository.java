package shop.haui_megatech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.haui_megatech.domain.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p " +
            "WHERE (p.deleted = false OR p.deleted IS NULL) " +
            "AND (p.hidden = false OR p.hidden IS NULL) "
    )
    Page<Product> getActiveProductsPage(Pageable pageable);


    @Query("SELECT p FROM Product p " +
            "WHERE (p.deleted = false OR p.deleted IS NULL) " +
            "AND p.hidden = true "
    )
    Page<Product> getHiddenProductsPage(Pageable pageable);


    @Query("SELECT p FROM Product p " +
            "WHERE p.deleted = true "
    )
    Page<Product> getDeletedProductsPage(Pageable pageable);


    @Query("SELECT p FROM Product p " +
            "WHERE (p.name LIKE %?1% OR p.processor LIKE %?1%) " +
            "AND (p.deleted = false OR p.deleted IS NULL) " +
            "AND (p.hidden = false OR p.hidden IS NULL) "
    )
    Page<Product> searchActiveProductsPage(String keyword, Pageable pageable);


    @Query("SELECT p FROM Product p " +
            "WHERE (p.name LIKE %?1% OR p.processor LIKE %?1%) " +
            "AND p.hidden = true "
    )
    Page<Product> searchHiddenProductsPage(String keyword, Pageable pageable);


    @Query("SELECT p FROM Product p " +
            "WHERE (p.name LIKE %?1% OR p.processor LIKE %?1%) " +
            "AND p.deleted = true "
    )
    Page<Product> searchDeletedProductsPage(String keyword, Pageable pageable);
}
