package shop.haui_megatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.haui_megatech.domain.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}
