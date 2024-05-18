package shop.haui_megatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.haui_megatech.domain.entity.ActivityLog;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Integer> {
}
