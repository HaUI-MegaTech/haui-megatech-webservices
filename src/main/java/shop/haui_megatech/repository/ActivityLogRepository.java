package shop.haui_megatech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.haui_megatech.domain.entity.ActivityLog;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Integer> {

    @Query(
            """
            SELECT a FROM ActivityLog a WHERE a.subject.username like %?1%                      
            """
    )
    Page<ActivityLog> search(String keyword, Pageable pageable);


}
