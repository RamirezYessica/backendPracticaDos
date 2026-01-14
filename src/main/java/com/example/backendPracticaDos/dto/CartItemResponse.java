package com.example.backendPracticaDos.dto;

public class CartItemResponse {
	 private Long productId;
	    private String name;
	    private double price;
	    private int quantity;

	    public CartItemResponse(Long productId, String name, double price, int quantity) {
	        this.productId = productId;
	        this.name = name;
	        this.price = price;
	        this.quantity = quantity;
	    }

	    public Long getProductId() {
	        return productId;
	    }

	    public String getName() {
	        return name;
	    }

	    public double getPrice() {
	        return price;
	    }

	    public int getQuantity() {
	        return quantity;
	    }
}
