package shop.haui_megatech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.haui_megatech.domain.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(
            "SELECT p FROM Product p " +
            "WHERE (p.deleted = false OR p.deleted IS NULL) " +
            "AND (p.hidden = false OR p.hidden IS NULL) "
    )
    Page<Product> getActiveProductsPage(Pageable pageable);


    @Query(
            "SELECT p FROM Product p " +
            "WHERE (p.deleted = false OR p.deleted IS NULL) " +
            "AND p.hidden = true "
    )
    Page<Product> getHiddenProductsPage(Pageable pageable);


    @Query(
            "SELECT p FROM Product p " +
            "WHERE p.deleted = true "
    )
    Page<Product> getDeletedProductsPage(Pageable pageable);


    @Query(
            "SELECT p FROM Product p " +
            "WHERE LOWER(CONCAT(p.name, p.processor, p.storage, p.memoryCapacity)) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (p.deleted = false OR p.deleted IS NULL) " +
            "AND (p.hidden = false OR p.hidden IS NULL) "
    )
    Page<Product> searchActiveProductsPage(String keyword, Pageable pageable);


    @Query(
            "SELECT p FROM Product p " +
            "WHERE (p.name LIKE %?1% OR p.processor LIKE %?1%) " +
            "AND p.hidden = true "
    )
    Page<Product> searchHiddenProductsPage(String keyword, Pageable pageable);


    @Query(
            "SELECT p FROM Product p " +
            "WHERE (p.name LIKE %?1% OR p.processor LIKE %?1%) " +
            "AND p.deleted = true "
    )
    Page<Product> searchDeletedProductsPage(String keyword, Pageable pageable);


    @Query(
            "SELECT p FROM Product p " +
            "WHERE p.currentPrice BETWEEN :minPrice AND :maxPrice " +
            "AND (p.deleted = false OR p.deleted IS NULL) " +
            "AND (p.hidden = false OR p.hidden IS NULL) "
    )
    Page<Product> filterActiveListByPrice(Float minPrice, Float maxPrice, Pageable pageable);

    @Query(
            "SELECT p FROM Product p " +
            "WHERE (p.brand.id IN :brandIds) " +
            "AND (p.deleted = false OR p.deleted IS NULL) " +
            "AND (p.hidden = false OR p.hidden IS NULL) "
    )
    Page<Product> filterActiveListByBrandIds(List<Integer> brandIds, Pageable pageable);

    @Query(
            "SELECT p FROM Product p " +
            "WHERE (p.brand.id IN :brandIds) " +
            "AND (p.currentPrice BETWEEN :minPrice AND :maxPrice) " +
            "AND (p.deleted = false OR p.deleted IS NULL) " +
            "AND (p.hidden = false OR p.hidden IS NULL) "
    )
    Page<Product> filterActiveListByPriceAndBrandIds(
            List<Integer> brandIds,
            Float minPrice,
            Float maxPrice,
            Pageable pageable
    );

    @Query(
            "SELECT p FROM Product p " +
            "WHERE LOWER(CONCAT(p.name, p.processor, p.storage, p.memoryCapacity)) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (p.brand.id IN :brandIds) " +
            "AND (p.deleted = false OR p.deleted IS NULL) " +
            "AND (p.hidden = false OR p.hidden IS NULL) "
    )
    Page<Product> filterActiveListByKeywordAndBrandIds(
            String keyword,
            List<Integer> brandIds,
            Pageable pageable
    );

    @Query(
            "SELECT p FROM Product p " +
            "WHERE LOWER(CONCAT(p.name, p.processor, p.storage, p.memoryCapacity)) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (p.currentPrice BETWEEN :minPrice AND :maxPrice) " +
            "AND (p.deleted = false OR p.deleted IS NULL) " +
            "AND (p.hidden = false OR p.hidden IS NULL) "
    )
    Page<Product> filterActiveListByKeywordAndPrice(
            String keyword,
            Float minPrice,
            Float maxPrice,
            Pageable pageable
    );

    @Query(
            "SELECT p FROM Product p " +
            "WHERE LOWER(CONCAT(p.name, p.processor, p.storage, p.memoryCapacity)) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (p.brand.id IN :brandIds) " +
            "AND (p.currentPrice BETWEEN :minPrice AND :maxPrice) " +
            "AND (p.deleted = false OR p.deleted IS NULL) " +
            "AND (p.hidden = false OR p.hidden IS NULL) "
    )
    Page<Product> filterActiveListByKeywordAndBrandIdsAndPrice(
            String keyword,
            List<Integer> brandIds,
            Float minPrice,
            Float maxPrice,
            Pageable pageable
    );
}
