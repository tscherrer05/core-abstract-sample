package com.example.demo.core;

import com.example.demo.dto.in.StockUpdate;
import com.example.demo.dto.out.Stock;

public interface ShoeShopCore extends ShoeCore {

    Stock getStock();

    void updateStock(StockUpdate stockUpdate);

}
