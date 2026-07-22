package pbotubesmcd.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import pbotubesmcd.models.CartItem;
import pbotubesmcd.models.Menu;

public class CartController {
    private static CartController instance;
    private final List<CartItem> cartItems;

    private CartController() {
        this.cartItems = new ArrayList<>();
    }

    public static CartController getInstance() {
        if (instance == null) {
            instance = new CartController();
        }
        return instance;
    }

    public void addMenu(Menu menu) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getMenu().getIdMenu().equals(menu.getIdMenu())) {
                cartItem.setJumlah(cartItem.getJumlah() + 1);
                return;
            }
        }
        cartItems.add(new CartItem(menu, 1));
    }

    public void decreaseMenu(Menu menu) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getMenu().getIdMenu().equals(menu.getIdMenu())) {
                if (cartItem.getJumlah() > 1) {
                    cartItem.setJumlah(cartItem.getJumlah() - 1);
                } else {
                    cartItems.remove(cartItem);
                }
                return;
            }
        }
    }

    public void removeMenu(Menu menu) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getMenu().getIdMenu().equals(menu.getIdMenu())) {
                cartItems.remove(cartItem);
                return;
            }
        }
    }

    public BigDecimal getTotalCartPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            total = total.add(cartItem.getSubtotal());
        }
        return total;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
