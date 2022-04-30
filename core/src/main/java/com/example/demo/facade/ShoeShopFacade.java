package com.example.demo.facade;

import com.example.demo.core.ShoeShopCore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.transaction.NotSupportedException;

@Component
public class ShoeShopFacade {

    public <T> ShoeShopCore get(Integer version) throws NotSupportedException {
        throw new NotSupportedException();
    }
}
