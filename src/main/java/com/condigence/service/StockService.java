package com.condigence.service;

import com.condigence.bean.StockBean;
import com.condigence.dto.StockDTO;

public interface StockService {

    StockDTO findStockByProductId(String productId);

    StockDTO addStock(StockBean stockBean, String productId);

    StockDTO updateStock(int orderedQuantity, String productId);

    void deleteStock(String productId);
}
