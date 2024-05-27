package shop.haui_megatech.domain.entity.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "provinces")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Province {
    @Id
    private String code;

    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    private String codeName;

    @ManyToOne
    @JoinColumn(name = "administrative_unit_id")
    @JsonIgnore
    private AdministrativeUnit administrativeUnit;

    @ManyToOne
    @JoinColumn(name = "administrative_region_id")
    @JsonIgnore
    private AdministrativeRegion administrativeRegion;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "province")
    @JsonIgnore
    private List<District> districts;
}
