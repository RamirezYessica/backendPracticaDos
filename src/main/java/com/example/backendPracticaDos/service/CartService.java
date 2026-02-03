package com.example.backendPracticaDos.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backendPracticaDos.dto.CartItemResponse;
import com.example.backendPracticaDos.model.CartItem;
import com.example.backendPracticaDos.model.Product;

@Service
public class CartService {

    // Carritos en memoria por sessionId
    private final Map<String, List<CartItem>> carts = new HashMap<>();

    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    /* =========================
       AGREGAR PRODUCTO
    ========================== */
    public void addItem(String sessionId, Long productId, int quantity) {

        Product product = productService.getProductById(productId);

        if (product == null) {
            throw new RuntimeException("Producto no encontrado");
        }

        List<CartItem> cart =
                carts.computeIfAbsent(sessionId, k -> new ArrayList<>());

        // Si el producto ya existe en el carrito, aumentar cantidad
        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                //item.setQuantity(quantity);
                return;
            }
        }

        // Si no existe, agregar nuevo
        cart.add(new CartItem(product, quantity));
    }

    /* =========================
       OBTENER CARRITO (DTO)
    ========================== */
    public List<CartItemResponse> getCartWithProductDetails(String sessionId) {
        List<CartItem> cartItems =
                carts.getOrDefault(sessionId, new ArrayList<>());

        return cartItems.stream()
                .map(item -> {
                    Product product = item.getProduct();

                    if (product == null) {
                        return null;
                    }

                    return new CartItemResponse(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            item.getQuantity()
                    );
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    /*Quita un solo producto del carrito*/
    public void removeItem(String sessionId, Long productId) {
        List<CartItem> cart = carts.get(sessionId);

        if (cart == null) {
            return;
        }

        for (int i = 0; i < cart.size(); i++) {
            CartItem item = cart.get(i);

            if (item.getProduct().getId().equals(productId)) {

                //  Si hay más de 1, solo restar 1
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    // Si era el último, eliminar el producto
                    cart.remove(i);
                }

                return; //salir después de modificar
            }
        }
    }

    
    /* =========================
    QUITAR TODOS LOS PRODUCTOS
 ========================== */
	 public void removeAllOfItem(String sessionId, Long productId) {
	     List<CartItem> cart = carts.get(sessionId);
	
	     if (cart == null) {
	         return;
	     }
	
	     cart.removeIf(item ->
	             item.getProduct().getId().equals(productId)
	     );
	 }
    
    /**TOTAL DEL CARRITO*/
    public double getTotal(String sessionId) {
        List<CartItem> cart =
                carts.getOrDefault(sessionId, new ArrayList<>());

        return cart.stream()
                .mapToDouble(item ->
                        item.getProduct().getPrice() * item.getQuantity()
                )
                .sum();
    }

}
