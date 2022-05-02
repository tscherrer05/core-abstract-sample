package com.example.demo.core;

import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.out.Category;
import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Shoes;
import com.example.demo.dto.out.Stock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static java.util.stream.Collectors.groupingBy;

@Implementation(version = 3)
public class ShoeShopCoreImpl extends AbstractShoeShopCore {

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
        var fullStockLimit = 30;
        var categories = new ArrayList<Category>();

        currentAvailableShoes
                .stream()
                .collect(groupingBy(ShoeEntity::getColor, groupingBy(ShoeEntity::getSize)))
                .forEach((color, sizes) ->
                        sizes.forEach((size, shoes) ->
                                categories.add(Category.builder()
                                    .shoe(Shoe.builder()
                                            .color(ShoeFilter.Color.valueOf(color))
                                            .size(size)
                                            .build())
                                    .quantity(shoes.size())
                                    .build())));

        var stockState = Stock.State.EMPTY;
        if(stockCount == fullStockLimit)
            stockState = Stock.State.FULL;

        if(0 < stockCount && stockCount < fullStockLimit)
            stockState = Stock.State.SOME;

        return new Stock(stockState, categories);
    }
}
