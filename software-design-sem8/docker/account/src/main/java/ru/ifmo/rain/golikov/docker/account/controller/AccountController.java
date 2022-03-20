package ru.ifmo.rain.golikov.docker.account.controller;

import org.springframework.web.bind.annotation.*;
import ru.ifmo.rain.golikov.docker.account.model.Account;
import ru.ifmo.rain.golikov.docker.account.model.Stock;
import ru.ifmo.rain.golikov.docker.account.service.AccountService;

import java.util.*;

@RestController
@RequestMapping("/account")
public class AccountController {
  private final AccountService accountService;

  public AccountController(final AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping("/create-account")
  public Account createAccount(@RequestParam String login) {
    return accountService.addAccount(new Account(login));
  }

  @PostMapping("/add-money")
  public Account addMoney(@RequestParam long id, @RequestParam double amount) {
    return accountService.addMoney(id, amount);
  }

  @PostMapping("/buy-stocks")
  public Account buyStocks(@RequestParam long id, @RequestParam String company, @RequestParam long amount) {
    return accountService.buyStocks(id, company, amount);
  }

  @PostMapping("/sell-stocks")
  public Account sellStocks(@RequestParam long id, @RequestParam String company, @RequestParam long amount) {
    return accountService.sellStocks(id, company, amount);
  }

  @GetMapping("/get-money")
  public double getMoney(@RequestParam long id) {
    return accountService.getMoney(id);
  }

  @GetMapping("/get-stocks")
  public List<Stock> getStocks(@RequestParam long id) {
    return accountService.getStocks(id);
  }
}
