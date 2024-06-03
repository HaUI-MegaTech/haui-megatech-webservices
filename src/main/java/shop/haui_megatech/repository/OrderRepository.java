package shop.haui_megatech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.haui_megatech.domain.dto.order.StatisticByAdminRegionResponse;
import shop.haui_megatech.domain.dto.order.StatisticByMonthResponseDTO;
import shop.haui_megatech.domain.entity.Order;

import java.util.List;
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
            "WHERE CONCAT(o.paymentMethod, '') LIKE CONCAT('%', :keyword, '%')"
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

    @Query(
            value = "SELECT new shop.haui_megatech.domain.dto.order.StatisticByMonthResponseDTO(DATE_FORMAT(o.orderTime, '%d %b %Y'), SUM(o.subTotal)) " +
                    "FROM Order o " +
                    "WHERE MONTH(o.orderTime) = :month AND YEAR(o.orderTime) = :year " +
                    "GROUP BY DATE_FORMAT(o.orderTime, '%d %b %Y') " +
                    "ORDER BY DATE_FORMAT(o.orderTime, '%d %b %Y')"
    )
    List<StatisticByMonthResponseDTO> statisticByMonth(@Param("month") Integer month, @Param("year") Integer year);

    @Query(
            value = "SELECT new shop.haui_megatech.domain.dto.order.StatisticByAdminRegionResponse(adre.name, SUM(o.subTotal)) " +
                    "FROM Order o " +
                    "JOIN Address ad ON o.address.id = ad.id " +
                    "JOIN Province p ON ad.provinceCode = p.code " +
                    "JOIN AdministrativeRegion adre ON p.administrativeRegion.id = adre.id " +
                    "WHERE YEAR(o.orderTime) = :year " +
                    "GROUP BY adre.id, adre.name " +
                    "ORDER BY SUM(o.subTotal) ASC"
    )
    List<StatisticByAdminRegionResponse> statisticByAdminRegion(@Param("year") int year);

}
