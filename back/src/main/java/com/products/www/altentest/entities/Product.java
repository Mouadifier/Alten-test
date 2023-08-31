package com.products.www.altentest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Product")
@Data
public class Product {
    
    @Id
    @SequenceGenerator(name = "PRODUCT_GENERATOR", sequenceName = "PRODUCT_GENERATOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_GENERATOR")
    private int id;

    @NotBlank(message = "message is mandatory!")
    private String code;
    
    @NotBlank(message = "name is mandatory!")
    private String name;

    @NotBlank(message = "description is mandatory!")
    private String description;
    
    private String image;
    
    @Positive
    private int price;

    @NotBlank(message = "category is mandatory!")
    private String category;
    
    @PositiveOrZero
    private int quantity;
    
    @NotBlank(message = "inventoryStatus is mandatory!")
    private String inventoryStatus;

    @PositiveOrZero
    @Max(5)
    private int rating;

    public boolean copy(Product product){

        this.setName(product.getName());
        this.setCategory(product.getCategory());
        this.setCode(product.getCode());
        this.setDescription(product.getDescription());
        this.setImage(product.getImage());
        this.setInventoryStatus(product.getInventoryStatus());
        this.setPrice(product.getPrice());
        this.setQuantity(product.getQuantity());
        this.setRating(product.getRating());

        return true;
    }
}
