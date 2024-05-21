package shop.haui_megatech.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "login_statistics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginStatistic {
    @Id
    @Temporal(TemporalType.DATE)
    private Date    date;
    private Integer loggedIn;
    private Date    lastUpdated;
}
