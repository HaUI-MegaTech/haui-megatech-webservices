package shop.haui_megatech.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer id;
    private String  link;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
