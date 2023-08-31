package com.products.www.altentest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.products.www.altentest.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    
}
