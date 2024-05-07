package shop.haui_megatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.haui_megatech.domain.entity.location.District;

public interface DistrictRepository extends JpaRepository<District, String> {
}
