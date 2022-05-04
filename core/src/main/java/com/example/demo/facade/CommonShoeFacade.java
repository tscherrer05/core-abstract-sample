package com.example.demo.facade;

import com.example.demo.core.BaseShoeCore;
import org.springframework.stereotype.Component;

import javax.transaction.NotSupportedException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommonShoeFacade {

    private final Map<Integer, BaseShoeCore> implementations = new HashMap<>();

    public <T extends BaseShoeCore> T get(Integer version) throws NotSupportedException {
        var implementation = implementations.get(version);
        if(implementation == null)
            throw new NotSupportedException();
        return (T) implementation;
    }

    public void register(Integer version, BaseShoeCore implementation){
        this.implementations.put(version, implementation);
    }
}
