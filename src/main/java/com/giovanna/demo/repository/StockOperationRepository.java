package com.giovanna.demo.repository;

import com.giovanna.demo.model.StockOperationModel;
import com.giovanna.demo.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface StockOperationRepository extends JpaRepository<StockOperationModel, UUID> {
    List<StockOperationModel> findAllByOperationDateBetween(LocalDateTime from, LocalDateTime to);
    List<StockOperationModel> findAllByStore(StoreModel store);
}
