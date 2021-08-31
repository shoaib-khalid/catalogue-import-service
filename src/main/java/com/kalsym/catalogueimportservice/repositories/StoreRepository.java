package com.kalsym.catalogueimportservice.repositories;

import com.kalsym.catalogueimportservice.models.product.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends PagingAndSortingRepository<Store, String>, JpaRepository<Store, String> {

}
