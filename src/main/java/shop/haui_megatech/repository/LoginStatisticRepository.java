package shop.haui_megatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.haui_megatech.domain.entity.LoginStatistic;

import java.util.Date;

@Repository
public interface LoginStatisticRepository extends JpaRepository<LoginStatistic, Date> {

}
