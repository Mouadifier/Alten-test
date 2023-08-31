package com.products.www.altentest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.products.www.altentest.dto.PatchDTO;
import com.products.www.altentest.entities.Product;
import com.products.www.altentest.repositories.ProductRepository;
import com.products.www.altentest.exceptions.ProductAccessDeniedException;
import com.products.www.altentest.exceptions.ProductNotFoundException;
import com.products.www.altentest.exceptions.ProductServiceBusinessException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
    
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> findAllProduct(){
        log.info("Find all products execution started");
        log.debug("Getting products from database");
        try{
            return this.productRepository.findAll();
         } catch (Exception ex) {
            log.error("Exception occurred while persisting productDetails to database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while retreiving productDetails list");
        }
    }

    public Product findProductById(int id){
        Product productDetails;
        
        log.info("find productDetails with id {}", id);
         productDetails = this.productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));

        return productDetails;
    }

    public Product saveProduct(Product productDetails){
        Product createdProduct;
        try{
            log.info("Saving productDetails to database.");
            createdProduct = this.productRepository.save(productDetails);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting productDetails to database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while create a new productDetails");
        }

        return createdProduct;
    }

    public boolean patchProduct(int id, PatchDTO patch){
        
        log.info("Find Product to patch in database!");
         var prodcutToPatch = this.productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));
       
        log.info("Patch productDetails");
        switch(patch.getKey()){
            case "id": log.error("ACCESS DENIED: Prodcut ID cannot be modified!");
                        throw new ProductAccessDeniedException("Product cannot be modified!"); 
            case "code": prodcutToPatch.setCode(patch.getValue()); break;
            case "name": prodcutToPatch.setName(patch.getValue()); break;
            case "description": prodcutToPatch.setDescription(patch.getValue()); break;
            case "price": prodcutToPatch.setPrice(Integer.parseInt(patch.getValue())); break;
            case "quantity": prodcutToPatch.setQuantity(Integer.parseInt(patch.getValue())); break;
            case "inventoryStatus": prodcutToPatch.setInventoryStatus(patch.getValue()); break;
            case "category": prodcutToPatch.setCategory(patch.getValue()); break;
            case "image": prodcutToPatch.setImage(patch.getValue()); break;
            case "rating": prodcutToPatch.setRating(Integer.parseInt(patch.getValue()));  break;
            default: return false;
        }
        try{
            log.info("Saving update to database.");
            this.productRepository.save(prodcutToPatch);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting productDetails to database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while updating productDetails data");
        }

        return true;
    }

    public boolean deleteProduct(int id){
        try{
        log.info("Find Product to delete in database!");
        var prodcutToDelete = this.productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));

        log.info("Deleting productDetails from database");
        this.productRepository.delete(prodcutToDelete);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting productDetails to database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while deleting the productDetails");
        }

        return true;
    }

    public boolean putProduct(int id, Product productDetails){
        try{
        log.info("Find Product to update in database!");
        Product prodcutToUpdate = this.productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));

        log.info("Updating productDetails data");
        prodcutToUpdate.setName(productDetails.getName());
        prodcutToUpdate.setCategory(productDetails.getCategory());
        prodcutToUpdate.setCode(productDetails.getCode());
        prodcutToUpdate.setDescription(productDetails.getDescription());
        prodcutToUpdate.setImage(productDetails.getImage());
        prodcutToUpdate.setInventoryStatus(productDetails.getInventoryStatus());
        prodcutToUpdate.setPrice(productDetails.getPrice());
        prodcutToUpdate.setQuantity(productDetails.getQuantity());
        prodcutToUpdate.setRating(productDetails.getRating());
        
        log.info("Saving update to database.");
        this.productRepository.save(prodcutToUpdate);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting productDetails to database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while updating productDetails data");
        }

        return true;
    }
}
