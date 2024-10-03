package com.giovanna.demo.model;

import com.giovanna.demo.enums.OperationType;
import com.giovanna.demo.infra.exception.stock.InviableStockOperationException;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_stock_operation")
public class StockOperationModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "operation_id")
    private UUID operationId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel product;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreModel store;

    @Column(name = "operation_date", nullable = false)
    private LocalDateTime operationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    private OperationType operationType;

    @Column(name = "operation_quantity", nullable = false)
    private Integer productQuantity;

    public StockOperationModel() {
    }

    public StockOperationModel(UUID operationId, ProductModel product, StoreModel store, LocalDateTime operationDate, OperationType operationType, Integer productQuantity) {
        this.operationId = operationId;
        this.product = product;
        this.store = store;
        this.operationDate = operationDate;
        this.operationType = operationType;
        this.productQuantity = productQuantity;
    }

    public UUID getOperationId() {
        return operationId;
    }

    public void setOperationId(UUID operationId) {
        this.operationId = operationId;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public StoreModel getStore() {
        return this.store;
    }

    public void setStore(StoreModel store) {
        this.store = store;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType type) {
        this.operationType = type;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
