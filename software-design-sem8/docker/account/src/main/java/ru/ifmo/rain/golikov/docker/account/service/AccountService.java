package ru.ifmo.rain.golikov.docker.account.service;

import org.springframework.stereotype.Service;
import ru.ifmo.rain.golikov.docker.account.model.Account;
import ru.ifmo.rain.golikov.docker.account.model.Stock;
import ru.ifmo.rain.golikov.docker.account.repository.AccountRepository;

import java.util.*;

@Service
public class AccountService {
  private final AccountRepository repository;
  private final ExchangeClient exchangeClient;

  public AccountService(final AccountRepository repository, final ExchangeClient exchangeClient) {
    this.repository = repository;
    this.exchangeClient = exchangeClient;
  }

  public Account addAccount(final Account account) {
    return repository.save(account);
  }

  public Account addMoney(final long id, final double amount) {
    var account = getAccount(id);
    account.addMoney(amount);
    return repository.save(account);
  }

  public Account buyStocks(final long id, final String company, final long amount) {
    var account = getAccount(id);
    var stock = exchangeClient.getStock(company);
    if (amount > stock.getAmount()) {
      throw new IllegalArgumentException();
    }
    account.addStocks(company, stock.getPrice(), amount);
    exchangeClient.buyStocks(company, amount);
    return repository.save(account);
  }

  public Account sellStocks(final long id, final String company, final long amount) {
    var account = getAccount(id);
    account.removeStocks(company, getPrice(company), amount);
    exchangeClient.sellStocks(company, amount);
    return repository.save(account);
  }

  private Account getAccount(final long id) {
    return repository.findById(id).orElseThrow(IllegalArgumentException::new);
  }

  public double getMoney(final long id) {
    var account = getAccount(id);
    return account.getMoney() + getStocks(account).stream().mapToDouble(s -> s.getAmount() * s.getPrice()).sum();
  }

  public List<Stock> getStocks(final long id) {
    return getStocks(getAccount(id));
  }

  private List<Stock> getStocks(final Account account) {
    List<Stock> result = new ArrayList<>();
    for (var entry : account.getStocks().entrySet()) {
      result.add(new Stock(entry.getKey(), entry.getValue(), getPrice(entry.getKey())));
    }
    return result;
  }

  private double getPrice(final String company) {
    return exchangeClient.getStock(company).getPrice();
  }
}
