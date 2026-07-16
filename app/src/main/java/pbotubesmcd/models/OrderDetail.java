package pbotubesmcd.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    private Integer id_detail;

    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id_menu", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private Integer jumlah;

    @Column(nullable = false)
    private BigDecimal subtotal;

    public OrderDetail() {
    }

    public OrderDetail(Integer id_detail, Order order, Menu menu, Integer jumlah, BigDecimal subtotal) {
        this.id_detail = id_detail;
        this.order = order;
        this.menu = menu;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    public Integer getId_detail() {
        return id_detail;
    }

    public Order getOrder() {
        return order;
    }

    public Menu getMenu() {
        return menu;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
