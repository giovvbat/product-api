package com.giovanna.demo.service;

import com.giovanna.demo.dto.product.ProductRecordDto;
import com.giovanna.demo.infra.exception.product.NoProductsFoundException;
import com.giovanna.demo.infra.exception.product.ProductNotFoundException;
import com.giovanna.demo.infra.exception.store.StoreNotFoundException;
import com.giovanna.demo.model.ProductModel;
import com.giovanna.demo.model.StoreModel;
import com.giovanna.demo.repository.ProductRepository;
import com.giovanna.demo.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;

    @Transactional
    public ProductModel saveProduct(ProductRecordDto productRecordDto) {
        ProductModel product = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, product);
        Optional<StoreModel> store = storeRepository.findById(productRecordDto.storeId());

        if (store.isEmpty()) {
            throw new StoreNotFoundException();
        }

        product.setStore(store.get());
        return productRepository.save(product);
    }

    public ProductModel getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductModel> getAllProductsByName(String name) {
        List<ProductModel> productsByName = productRepository.findAllByName(name);

        if (productsByName.isEmpty()) {
            throw new NoProductsFoundException("no matching products found");
        }

        return productsByName;
    }

    public List<ProductModel> getAllProductsByStockRange(Integer minStock, Integer maxStock) {
        List<ProductModel> productsByStockRange = productRepository.findAllByStockBetween(minStock, maxStock);

        if (productsByStockRange.isEmpty()) {
            throw new NoProductsFoundException("no matching products found");
        }

        return productsByStockRange;
    }

    public List<ProductModel> getAllProductsByStockLessThan(Integer maxStock) {
        List<ProductModel> productsByStockLessThan = productRepository.findAllByStockLessThan(maxStock);

        if (productsByStockLessThan.isEmpty()) {
            throw new NoProductsFoundException("no matching products found");
        }

        return productsByStockLessThan;
    }

    public List<ProductModel> getAllProductsByStore(UUID storeId) {
        StoreModel store = storeRepository.findById(storeId)
                .orElseThrow(StoreNotFoundException::new);

        List<ProductModel> productsByStore = productRepository.findAllByStore(store);

        if (productsByStore.isEmpty()) {
            throw new NoProductsFoundException("no matching products found");
        }

        return productsByStore;
    }

    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new NoProductsFoundException();
        }

        return products;
    }
}
