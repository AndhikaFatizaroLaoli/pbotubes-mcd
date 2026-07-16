package pbotubesmcd.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
    private Integer id_menu;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Categories category;

    @Column(nullable = false)
    private String nama_menu;

    @Column(nullable = false)
    private BigDecimal harga;

    @Column(nullable = false)
    private Integer stok;

    @Column(nullable = false)
    private String gambar;

    public Menu() {
    }

    public Menu(Integer id_menu, Categories category, String nama_menu, BigDecimal harga, Integer stok, String gambar) {
        this.id_menu = id_menu;
        this.category = category;
        this.nama_menu = nama_menu;
        this.harga = harga;
        this.stok = stok;
        this.gambar = gambar;
    }

    public Integer getIdMenu() {
        return id_menu;
    }

    public Categories getCategory() {
        return category;
    }

    public String getNamaMenu() {
        return nama_menu;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public Integer getStok() {
        return stok;
    }

    public String getGambar() {
        return gambar;
    }

}
