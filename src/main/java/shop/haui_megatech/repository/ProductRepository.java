package shop.haui_megatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.haui_megatech.domain.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
