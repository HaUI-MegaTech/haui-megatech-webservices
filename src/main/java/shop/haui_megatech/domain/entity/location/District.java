package shop.haui_megatech.domain.entity.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "districts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class District {
    @Id
    private String code;

    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    private String codeName;

    @ManyToOne
    @JoinColumn(name = "province_code")
    @JsonIgnore
    private Province province;

    @ManyToOne
    @JoinColumn(name = "administrative_unit_id")
    @JsonIgnore
    private AdministrativeUnit administrativeUnit;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "district")
    @JsonIgnore
    private List<Ward> wards;
}
