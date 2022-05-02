package com.example.demo.facade;

import com.example.demo.core.ShoeCore;
import com.example.demo.core.ShoeShopCore;
import org.springframework.stereotype.Component;

import javax.transaction.NotSupportedException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ShoeShopFacade {

    private final Map<Integer, ShoeShopCore> implementations = new HashMap<>();

    public ShoeShopCore get(Integer version) throws NotSupportedException {
        if(version != 3)
            throw new NotSupportedException();

        return implementations.get(version);
    }

    public void register(Integer version, ShoeShopCore implementation){
        this.implementations.put(version, implementation);
    }
}
