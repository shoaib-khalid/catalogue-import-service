package com.kalsym.catalogueimportservice.repositories;

import com.kalsym.catalogueimportservice.models.product.Product;
import com.kalsym.catalogueimportservice.models.product.ProductWithDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductRepository extends JpaRepository<ProductWithDetails, String> {
}