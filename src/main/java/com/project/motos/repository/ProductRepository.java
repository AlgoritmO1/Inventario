package com.project.motos.repository;

import com.project.motos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("product_repository")
public interface ProductRepository extends JpaRepository<Product,Serializable> {
}
