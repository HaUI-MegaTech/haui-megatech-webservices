package shop.haui_megatech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.haui_megatech.domain.entity.LoginStatistic;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoginStatisticRepository extends JpaRepository<LoginStatistic, Date> {

}
