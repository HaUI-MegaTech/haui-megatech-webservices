package shop.haui_megatech.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Integer id;

	private String name;
	private Float oldPrice;
	private Float newPrice;
	private String display;
	private String processor;
	private String card;
	private String battery;
	private Float weight;
	private Integer discountPercent;
	private Integer ram;
	private String storage;
	private String bannerImg;
}
