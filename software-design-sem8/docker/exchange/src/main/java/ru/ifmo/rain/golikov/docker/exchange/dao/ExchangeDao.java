package ru.ifmo.rain.golikov.docker.exchange.dao;

import org.springframework.stereotype.Service;
import ru.ifmo.rain.golikov.docker.exchange.model.Company;
import ru.ifmo.rain.golikov.docker.exchange.repository.ExchangeRepository;

import java.util.*;

@Service
public class ExchangeDao {
  private final ExchangeRepository repository;

  public ExchangeDao(final ExchangeRepository repository) {
    this.repository = repository;
  }

  public List<Company> getCompanies() {
    return repository.findAll();
  }

  public Company addCompany(final Company company) {
    return repository.save(company);
  }

  public Company addStocks(final String name, final long amount) {
    var company = getCompany(name);
    company.addStocks(amount);
    return repository.save(company);
  }

  public Company buyStocks(final String name, final long amount) {
    var company = getCompany(name);
    company.buyStocks(amount);
    return repository.save(company);
  }

  public Company getCompany(final String name) {
    return repository.findByName(name).orElseThrow(IllegalArgumentException::new);
  }

  public Company setStockPrice(final String name, final double newPrice) {
    var company = getCompany(name);
    company.setStockPrice(newPrice);
    return repository.save(company);
  }
}
