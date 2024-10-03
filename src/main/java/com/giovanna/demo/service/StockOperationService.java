package com.giovanna.demo.service;

import com.giovanna.demo.dto.stock.StockOperationRecordDto;
import com.giovanna.demo.enums.OperationType;
import com.giovanna.demo.infra.exception.product.ProductNotFoundException;
import com.giovanna.demo.infra.exception.stock.InviableStockOperationException;
import com.giovanna.demo.infra.exception.stock.NoStockOperationsFoundException;
import com.giovanna.demo.infra.exception.stock.StockOperationNotFoundException;
import com.giovanna.demo.infra.exception.store.StoreNotFoundException;
import com.giovanna.demo.model.ProductModel;
import com.giovanna.demo.model.StockOperationModel;
import com.giovanna.demo.model.StoreModel;
import com.giovanna.demo.repository.ProductRepository;
import com.giovanna.demo.repository.StockOperationRepository;
import com.giovanna.demo.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class StockOperationService {
    @Autowired
    private StockOperationRepository stockOperationRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;

    @Transactional
    public StockOperationModel saveOperation(StockOperationRecordDto stockOperationRecordDto) {
        StockOperationModel stockOperation = new StockOperationModel();
        ProductModel product = productRepository.findById(stockOperationRecordDto.productId())
                .orElseThrow(ProductNotFoundException::new);

        if (stockOperationRecordDto.operationType().equals(OperationType.ENTRY)) {
            product.setStock(product.getStock() + stockOperationRecordDto.productQuantity());
        }
        else {
            if (!isOperationPossible(stockOperationRecordDto.operationType(), stockOperationRecordDto.productId(), stockOperationRecordDto.productQuantity())) {
                throw new InviableStockOperationException();
            }
            product.setStock(product.getStock() - stockOperationRecordDto.productQuantity());
        }

        BeanUtils.copyProperties(stockOperationRecordDto, stockOperation);
        stockOperation.setProduct(product);
        stockOperation.setStore(product.getStore());
        stockOperation.setOperationDate(LocalDateTime.now());

        return stockOperationRepository.save(stockOperation);
    }

    public StockOperationModel getOperationById(UUID id) {
        return stockOperationRepository.findById(id)
                .orElseThrow(StockOperationNotFoundException::new);
    }

    public List<StockOperationModel> getOperationByDateRange(LocalDateTime from, LocalDateTime to) {
        List<StockOperationModel> operationsByDateRange = stockOperationRepository.findAllByOperationDateBetween(from, to);

        if (operationsByDateRange.isEmpty()) {
            throw new NoStockOperationsFoundException();
        }

        return operationsByDateRange;
    }

    public List<StockOperationModel> getOperationByStore(UUID storeId) {
        StoreModel store = storeRepository.findById(storeId)
                .orElseThrow(StoreNotFoundException::new);

        List<StockOperationModel> operationsByStore = stockOperationRepository.findAllByStore(store);

        if (operationsByStore.isEmpty()) {
            throw new NoStockOperationsFoundException("no matching stock operations found");
        }

        return operationsByStore;
    }

    public List<StockOperationModel> getAllOperations() {
        List<StockOperationModel> stockOperations = stockOperationRepository.findAll();

        if (stockOperations.isEmpty()) {
            throw new NoStockOperationsFoundException();
        }

        return stockOperations;
    }

    public boolean isOperationPossible(OperationType operationType, UUID productId, Integer productQuantity) {
        if (operationType.equals(OperationType.ENTRY)) {
            return true;
        }

        ProductModel product = productRepository.findById(productId).
                orElseThrow(ProductNotFoundException::new);

        return product.getStock() >= productQuantity;
    }
}
