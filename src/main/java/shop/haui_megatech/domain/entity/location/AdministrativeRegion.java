package shop.haui_megatech.domain.entity.location;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "administrative_regions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdministrativeRegion {
    @Id
    private Integer id;

    private String name;
    private String nameEn;
    private String codeName;
    private String codeNameEn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "administrativeRegion")
    private List<Province> provinces;

}
