package shop.haui_megatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.haui_megatech.domain.entity.location.Province;

public interface ProvinceRepository extends JpaRepository<Province, String> {
}
