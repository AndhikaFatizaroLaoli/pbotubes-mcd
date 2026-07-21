package pbotubesmcd.models;

import java.math.BigDecimal;

public class CartItem {
    private Menu menu;
    private int jumlah;

    public CartItem(Menu menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public BigDecimal getSubtotal() {
        return menu.getHarga().multiply(BigDecimal.valueOf(jumlah));
    }
}
