package com.example.demo.core;

import com.example.demo.facade.ShoeFacade;
import com.example.demo.facade.ShoeShopFacade;
import lombok.val;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Optional;

public abstract class AbstractShoeShopCore implements ShoeShopCore {

  @Autowired
  private ShoeShopFacade shoeShopFacade;

  @PostConstruct
  void init(){

    val version = Optional.ofNullable(this.getClass().getAnnotation(Implementation.class))
                          .map(Implementation::version)
                          .orElseThrow(() -> new FatalBeanException("AbstractShoeCore implementation should be annotated with @Implementation"));

    shoeShopFacade.register(version, this);

  }

}
