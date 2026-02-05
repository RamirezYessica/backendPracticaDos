package com.example.backendPracticaDos.service;

import com.example.backendPracticaDos.dto.CartItemResponse;
import com.example.backendPracticaDos.model.CartItem;
import com.example.backendPracticaDos.model.Product;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public void addProductToCart(String sessionId, Long productId, int quantity) {

        Product product = getValidatedProduct(productId);
        List<CartItem> cart = getOrCreateCart(sessionId);
        addOrUpdateCartItem(productId, quantity, cart, product);
    }

    /*AGREGAMOS O ACTUALIZAMOS EL CARRITO*/
    private void addOrUpdateCartItem(Long productId, int quantity, @NonNull List<CartItem> cart, Product product) {
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

    /*OBTENEMOS O CREAMOS EL CARRITO*/
    @NonNull
    private List<CartItem> getOrCreateCart(String sessionId) {
        return carts.computeIfAbsent(sessionId, k -> new ArrayList<>());
    }

    /*VALIDAMOS SI EXISTE EL PRODUCTO*/
    @NonNull
    private Product getValidatedProduct(Long productId) {
        Product product = productService.getProductById(productId);

        if (product == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        return product;
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
                .toList();
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
