package com.example.demo.controller;

import com.example.demo.core.ShoeCore;
import com.example.demo.core.ShoeShopCore;
import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.in.StockUpdate;
import com.example.demo.dto.out.Shoes;
import com.example.demo.dto.out.Stock;
import com.example.demo.facade.CommonShoeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;

@Controller
@RequestMapping(path = "/shoes")
@RequiredArgsConstructor
public class ShoeController {

  private final CommonShoeFacade commonShoeFacade;


  @GetMapping(path = "/search")
  public ResponseEntity<Shoes> all(ShoeFilter filter, @RequestHeader Integer version) {

      try {
          return ResponseEntity.ok(commonShoeFacade.<ShoeCore>get(version).search(filter));
      } catch (NotSupportedException e) {
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }

  }

  @GetMapping(path = "/stock")
  public ResponseEntity<Stock> get(@RequestHeader Integer version) {

      try {
          return ResponseEntity.ok(commonShoeFacade.<ShoeShopCore>get(version).getStock());
      } catch (NotSupportedException e) {
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }

  }

  @PatchMapping(path = "/stock")
  public ResponseEntity<HttpStatus> patch(@RequestBody StockUpdate stockUpdate, @RequestHeader Integer version) {

      try {
          commonShoeFacade.<ShoeShopCore>get(version).updateStock(stockUpdate);
          return ResponseEntity.ok(HttpStatus.OK);
      } catch (NotSupportedException e) {
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }

  }

}
