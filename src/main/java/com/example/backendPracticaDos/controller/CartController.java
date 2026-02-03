package com.example.backendPracticaDos.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.backendPracticaDos.dto.AddToCartRequest;
import com.example.backendPracticaDos.dto.CartItemResponse;

import com.example.backendPracticaDos.service.CartService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
@Validated
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // POST /api/cart/items
    @PostMapping("/items")
    public ResponseEntity<Void> addItem(
            @Valid @RequestBody AddToCartRequest request) {

        cartService.addItem(
                request.getSessionId(),
                request.getProductId(),   // ahora solo el ID
                //request.getProductId() + 1,
                request.getQuantity()
        );

        return ResponseEntity.ok().build();
    }

    // GET /api/cart/{sessionId}
    @GetMapping("/{sessionId}")
    public ResponseEntity<List<CartItemResponse>> getCart(
            @PathVariable String sessionId) {

        return ResponseEntity.ok(
                cartService.getCartWithProductDetails(sessionId)
        );
    }

    // DELETE /api/cart/{sessionId}/items/{productId}
    @DeleteMapping("/{sessionId}/items/{productId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable String sessionId,
            @PathVariable Long productId) {

        cartService.removeItem(sessionId, productId);
        return ResponseEntity.noContent().build();
    }
    
 // DELETE /api/cart/{sessionId}/items/{productId}/all
    @DeleteMapping("/{sessionId}/items/{productId}/all")
    public ResponseEntity<Void> removeAll(
            @PathVariable String sessionId,
            @PathVariable Long productId) {

        cartService.removeAllOfItem(sessionId, productId);
        return ResponseEntity.noContent().build();
    }

    
    
    
    /** TOTAL DEL CARRITO */
 // GET /api/cart/{sessionId}/total
    @GetMapping("/{sessionId}/total")
    public ResponseEntity<Double> getTotal(
            @PathVariable String sessionId) {

        return ResponseEntity.ok(cartService.getTotal(sessionId));
    }

}
