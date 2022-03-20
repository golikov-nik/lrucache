package ru.ifmo.rain.golikov.docker.exchange.controller;

import org.springframework.web.bind.annotation.*;
import ru.ifmo.rain.golikov.docker.exchange.model.Company;
import ru.ifmo.rain.golikov.docker.exchange.dao.ExchangeDao;

import java.util.*;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {
  private final ExchangeDao exchangeDao;

  public ExchangeController(final ExchangeDao exchangeDao) {
    this.exchangeDao = exchangeDao;
  }

  @PostMapping("/add-company")
  public Company addCompany(@RequestParam String name, @RequestParam long stockAmount, @RequestParam double stockPrice) {
    return exchangeDao.addCompany(new Company(name, stockAmount, stockPrice));
  }

  @PostMapping("/add-stocks")
  public Company addStocks(@RequestParam String name, @RequestParam long amount) {
    return exchangeDao.addStocks(name, amount);
  }

  @PostMapping("/buy-stocks")
  public Company buyStocks(@RequestParam String name, @RequestParam long amount) {
    return exchangeDao.buyStocks(name, amount);
  }

  @PostMapping("/set-price")
  public Company setStockPrice(@RequestParam String name, @RequestParam double newPrice) {
    return exchangeDao.setStockPrice(name, newPrice);
  }

  @GetMapping("/get-companies")
  public List<Company> getCompanies() {
    return exchangeDao.getCompanies();
  }

  @GetMapping("/get-company")
  public Company getCompany(@RequestParam String name) {
    return exchangeDao.getCompany(name);
  }
}
