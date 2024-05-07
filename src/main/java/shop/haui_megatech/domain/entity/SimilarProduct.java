package shop.haui_megatech.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "similar_products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimilarProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "similar_product_id")
    private Integer id;
    private String  websiteUrl;
    private String  productUrl;
    private String  productName;
    private String  productImageUrl;
    private Float   price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
}
