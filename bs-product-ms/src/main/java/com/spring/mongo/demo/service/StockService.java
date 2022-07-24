package com.spring.mongo.demo.service;

import com.spring.mongo.demo.bean.StockBean;
import com.spring.mongo.demo.dto.StockDTO;

public interface StockService {

    StockDTO findStockByProductId(String productId);

    StockDTO addStock(StockBean stockBean, String productId);

    StockDTO updateStock(int orderedQuantity, String productId);

    void deleteStock(String productId);
}
