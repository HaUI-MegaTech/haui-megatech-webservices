package shop.haui_megatech.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "order_details")
@Builder
public class OrderDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;
    private Float price;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
