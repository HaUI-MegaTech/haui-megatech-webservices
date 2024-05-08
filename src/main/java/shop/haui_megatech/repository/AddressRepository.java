package shop.haui_megatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import shop.haui_megatech.domain.entity.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(
            "DELETE FROM Address a " +
            "WHERE a.id IN :addressIds "
    )
    @Transactional
    @Modifying
    void deleteAddressByIds(List<Integer> addressIds);
}
