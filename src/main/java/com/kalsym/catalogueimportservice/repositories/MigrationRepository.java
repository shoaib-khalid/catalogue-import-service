package com.kalsym.catalogueimportservice.repositories;

import com.kalsym.catalogueimportservice.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MigrationRepository extends JpaRepository<Product, String> {
}