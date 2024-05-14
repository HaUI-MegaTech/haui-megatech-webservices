package shop.haui_megatech.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shop.haui_megatech.domain.entity.enums.Gender;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String avatarImageUrl;
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Address> addresses;

    private Date lastUpdated;

    @CreatedDate
    private Date whenCreated;

    private Date    lastLogined;
    private Integer logined;

    private Date    whenDeleted;
    private Boolean deleted;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
