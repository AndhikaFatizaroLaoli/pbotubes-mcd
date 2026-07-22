package pbotubesmcd.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pbotubesmcd.enums.OrderStatus;
import pbotubesmcd.enums.OrderType;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_order;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(insertable = false, updatable = false)
    private LocalDateTime tanggal_order;

    @Column(nullable = false)
    private Integer nomor_antrean;

    @Column(nullable = false)
    private BigDecimal total_harga;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "order_status", nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "order_type", nullable = false)
    private OrderType tipe_pesanan;

    public Order() {
    }

    public Order(Integer id_order, User user, LocalDateTime tanggal_order, Integer nomor_antrean,
            BigDecimal total_harga, OrderStatus status, OrderType tipe_pesanan) {
        this.id_order = id_order;
        this.user = user;
        this.tanggal_order = tanggal_order;
        this.nomor_antrean = nomor_antrean;
        this.total_harga = total_harga;
        this.status = status;
        this.tipe_pesanan = tipe_pesanan;
    }

    public Integer getIdOrder() {
        return id_order;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getTanggalOrder() {
        return tanggal_order;
    }

    public Integer getNomorAntrean() {
        return nomor_antrean;
    }

    public BigDecimal getTotalHarga() {
        return total_harga;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderType getOrderType() {
        return tipe_pesanan;
    }

}
