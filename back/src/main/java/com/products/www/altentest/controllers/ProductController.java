package com.products.www.altentest.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.www.altentest.dto.APIResponse;
import com.products.www.altentest.dto.PatchDTO;
import com.products.www.altentest.entities.Product;
import com.products.www.altentest.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    
    public static final String SUCCESS = "Success";
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllProducts(){

        var listProducts = this.productService.findAllProduct();

        APIResponse<List<Product>> response = APIResponse.<List<Product>>builder()
        .status(SUCCESS)
        .results(listProducts)
        .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIResponse> createProduct(@RequestBody @Valid Product product){
        Product createdProduct = this.productService.saveProduct(product);

        APIResponse<Product> response = APIResponse.<Product>builder()
        .status(SUCCESS)
        .results(createdProduct)
        .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<APIResponse> getProjectWithId(@PathVariable int productId){
        Product foundProduct = this.productService.findProductById(productId);

        APIResponse<Product> response = APIResponse.<Product>builder()
        .status(SUCCESS)
        .results(foundProduct)
        .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<APIResponse> patchProjecttWithId(@PathVariable int productId, @RequestBody PatchDTO patch){

        boolean success = this.productService.patchProduct(productId, patch);

        APIResponse<Boolean> response = APIResponse.<Boolean>builder()
        .status(SUCCESS)
        .results(success)
        .build();

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<APIResponse> putProjecttWithId(@PathVariable int productId, @RequestBody Product product){

        boolean success = this.productService.putProduct(productId, product);

        APIResponse<Boolean> response = APIResponse.<Boolean>builder()
        .status(SUCCESS)
        .results(success)
        .build();

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<APIResponse> deleteProdcut(@PathVariable int productId){
        boolean success = this.productService.deleteProduct(productId);

        APIResponse response =  APIResponse.<Boolean>builder().status(SUCCESS).results(success).build();

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
