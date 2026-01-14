package com.example.backendPracticaDos.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddToCartRequest {
	  @NotBlank
	    private String sessionId;

	    @NotNull
	    private Long productId;

	    @Min(1)
	    private int quantity;

	    public String getSessionId() {
	        return sessionId;
	    }

	    public Long getProductId() {
	        return productId;
	    }

	    public int getQuantity() {
	        return quantity;
	    }
}
