package shop.haui_megatech.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import shop.haui_megatech.domain.entity.enums.PaymentMethod;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Integer id;
    private Float   shippingCost;
    private Float   preTotal;
    private Float   tax;
    private Float   total;
    private Date    whenCreated;
    private Date    paidTime;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address shippingAddress;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "invoice"
    )
    private List<InvoiceItem> invoiceItems;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
