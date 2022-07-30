package com.condigence.service.impl;

import com.condigence.bean.StockBean;
import com.condigence.dto.StockDTO;
import com.condigence.model.Stock;
import com.condigence.repository.StockRepository;
import com.condigence.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository repository;


    /**
     * @param productId
     * @return
     */
    @Override
    public StockDTO findStockByProductId(String productId) {
        Stock stock = repository.findByProductId(productId);
        StockDTO stockDTO = null;
        if (stock != null) {
            stockDTO = new StockDTO();
            stockDTO.setId(stock.getId());
            stockDTO.setProductId(stock.getProductId());
            stockDTO.setQuantityInStock(stock.getQuantityInStock());
        }


        return stockDTO;
    }

    /**
     * @param stockBean
     * @param productId
     * @return
     */
    @Override
    public StockDTO addStock(StockBean stockBean, String productId) {
        Stock stock = repository.findByProductId(productId);
        int newStock = stock.getQuantityInStock() + stockBean.getQuantityInStock();
        stock.setQuantityInStock(newStock);
        repository.save(stock);
        Stock updatedStock = repository.findByProductId(productId);
        StockDTO dto = new StockDTO();
        dto.setQuantityInStock(updatedStock.getQuantityInStock());
        dto.setProductId(updatedStock.getProductId());
        return dto;
    }

    /**
     * @param orderedQuantity
     * @param productId
     * @return
     */
    @Override
    public StockDTO updateStock(int orderedQuantity, String productId) {
        Stock stock = repository.findByProductId(productId);
        int newStock = stock.getQuantityInStock() - orderedQuantity;
        stock.setQuantityInStock(newStock);
        repository.save(stock);
        Stock updatedStock = repository.findByProductId(productId);
        StockDTO dto = new StockDTO();
        dto.setQuantityInStock(updatedStock.getQuantityInStock());
        dto.setProductId(updatedStock.getProductId());
        return dto;
    }

    /**
     * @param productId
     */
    @Override
    public void deleteStock(String productId) {
        repository.delete(repository.findByProductId(productId));
    }
}
