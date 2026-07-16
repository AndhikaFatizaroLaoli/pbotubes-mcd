package pbotubesmcd.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Categories {
    @Id
    private Integer id_category;

    @Column(nullable = false)
    private String nama_kategori;

    public Categories() {
    }

    public Categories(Integer id_category, String nama_kategori) {
        this.id_category = id_category;
        this.nama_kategori = nama_kategori;
    }

    public Integer getIdCategory() {
        return id_category;
    }

    public String getNamaKategori() {
        return nama_kategori;
    }
}
