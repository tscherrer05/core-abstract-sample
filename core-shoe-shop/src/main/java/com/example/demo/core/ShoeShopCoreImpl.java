package com.example.demo.core;

import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.in.StockUpdate;
import com.example.demo.dto.out.Category;
import com.example.demo.dto.out.Shoes;
import com.example.demo.dto.out.Stock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

@Implementation(version = 3)
public class ShoeShopCoreImpl extends AbstractShoeShopCore {

    private static final int fullStockLimit = 30;

    @Autowired
    private DatabaseAdapter databaseAdapter;

    @Override
    public Shoes search(ShoeFilter filter) {
        return null;
    }

    @Override
    public Stock getStock() {
        var currentAvailableShoes = databaseAdapter.getAllShoes();
        var stockCount = currentAvailableShoes.size();
        var categories = new ArrayList<Category>();

        currentAvailableShoes
                .stream()
                .collect(groupingBy(ShoeEntity::getColor, groupingBy(ShoeEntity::getSize)))
                .forEach((color, sizes) ->
                        sizes.forEach((size, shoes) ->
                                categories.add(
                                        Category.builder()
                                            .color(ShoeFilter.Color.valueOf(color))
                                            .size(size)
                                            .quantity(shoes.size())
                                            .build())));

        var stockState = Stock.State.EMPTY;
        if(stockCount == fullStockLimit)
            stockState = Stock.State.FULL;

        if(0 < stockCount && stockCount < fullStockLimit)
            stockState = Stock.State.SOME;

        return new Stock(stockState, categories);
    }

    @Override
    public void updateStock(StockUpdate stockUpdate) {
        if(databaseAdapter.countShoes() == fullStockLimit) return;
        var modelCount = databaseAdapter.countShoes(stockUpdate.color, stockUpdate.size);
        if(stockUpdate.quantity > 0)
            IntStream.range(0, stockUpdate.quantity).forEach(i -> databaseAdapter.saveShoe(stockUpdate.color, stockUpdate.size));
        else if(modelCount >= -stockUpdate.quantity)
            IntStream.range(stockUpdate.quantity, 0).forEach(i -> databaseAdapter.removeShoe(stockUpdate.color, stockUpdate.size));
    }
}
