package shop.haui_megatech.domain.entity.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ward {
    @Id
    private String code;

    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    private String codeName;

    @ManyToOne
    @JoinColumn(name = "district_code")
    @JsonIgnore
    private District district;

    @ManyToOne
    @JoinColumn(name = "administrative_unit_id")
    @JsonIgnore
    private AdministrativeUnit administrativeUnit;
}
