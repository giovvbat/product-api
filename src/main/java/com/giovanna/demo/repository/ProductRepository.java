package com.giovanna.demo.repository;

import com.giovanna.demo.model.ProductModel;
import com.giovanna.demo.model.StoreModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
    List<ProductModel> findAllByName(String name);
    List<ProductModel> findAllByStockBetween(Integer minStock, Integer maxStock);
    List<ProductModel> findAllByStockLessThan(Integer maxStock);
    List<ProductModel> findAllByStore(StoreModel store);
}
