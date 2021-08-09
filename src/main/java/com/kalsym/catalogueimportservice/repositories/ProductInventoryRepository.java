package com.kalsym.catalogueimportservice.repositories;

import com.kalsym.catalogueimportservice.models.product.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, String> {
}
