package com.giovanna.demo.service;

import com.giovanna.demo.dto.store.StoreRecordDto;
import com.giovanna.demo.model.StoreModel;
import com.giovanna.demo.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Transactional
    public StoreModel saveStore(StoreRecordDto storeRecordDto) {
        StoreModel store = new StoreModel();
        BeanUtils.copyProperties(storeRecordDto, store);

        return storeRepository.save(store);
    }
}
