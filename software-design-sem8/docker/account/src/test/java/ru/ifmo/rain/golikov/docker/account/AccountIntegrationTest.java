package ru.ifmo.rain.golikov.docker.account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import ru.ifmo.rain.golikov.docker.account.controller.AccountController;
import ru.ifmo.rain.golikov.docker.account.http.ResponseParser;
import ru.ifmo.rain.golikov.docker.account.model.Stock;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountIntegrationTest {
  private static final GenericContainer<?> simpleWebServer
          = new FixedHostPortGenericContainer("exchange:1.0-SNAPSHOT")
          .withFixedExposedPort(8080, 8080)
          .withExposedPorts(8080);
  public static final String LOGIN = "golikovnik";
  public static final String COMPANY = "YNDX";
  private final TestRestTemplate testRestTemplate = new TestRestTemplate();
  public final static String HOST = "http://localhost:8080/exchange";
  @Autowired
  private AccountController accountController;
  public static final ResponseParser parser = new ResponseParser();

  @BeforeEach
  public void setUp() {
    simpleWebServer.start();
  }

  @AfterEach
  public void shutdown() {
    simpleWebServer.stop();
  }

  @Test
  public void testSetStockPrice() {
    var account = accountController.createAccount(LOGIN);
    var id = account.getId();
    addCompany(COMPANY, 100, 5);
    accountController.addMoney(id, 300);
    accountController.buyStocks(id, COMPANY, 20);
    setStockPrice(COMPANY, 1000);
    assertEquals(accountController.getMoney(id), 200 + 20 * 1000);
  }

  @Test
  public void testNoMoney() {
    var account = accountController.createAccount(LOGIN);
    var id = account.getId();
    addCompany(COMPANY, 100, 5);
    accountController.addMoney(id, 30);
    try {
      accountController.buyStocks(id, COMPANY, 20);
      Assertions.fail("Expected exception");
    } catch (final Exception e) {
      //  pass
    }
    setStockPrice(COMPANY, 1000);
    assertEquals(accountController.getMoney(id), 30);
  }

  @Test
  public void buyTooMuchStock() {
    var account = accountController.createAccount(LOGIN);
    var id = account.getId();
    addCompany(COMPANY, 100, 1);
    accountController.addMoney(id, 200);
    try {
      accountController.buyStocks(id, COMPANY, 110);
      Assertions.fail("Expected exception");
    } catch (final Exception e) {
      //  pass
    }
    setStockPrice(COMPANY, 1000);
    var money = accountController.getMoney(id);
    assertEquals(money, 200);
    assertEquals(getStock(COMPANY).getAmount(), 100);
  }

  @Test
  public void testGetStocks() {
    var account = accountController.createAccount(LOGIN);
    var id = account.getId();
    addCompany(COMPANY, 100, 5);
    accountController.addMoney(id, 300);
    accountController.buyStocks(id, COMPANY, 20);
    var stocks = accountController.getStocks(id);
    assertEquals(stocks.size(), 1);
    var stock = stocks.get(0);
    assertEquals(stock.getName(), COMPANY);
    assertEquals(stock.getAmount(), 20);
    assertEquals(stock.getPrice(), 5);
  }

  private Stock getStock(final String company) {
    return parser.parseStock(testRestTemplate.getForEntity(HOST + "/get-company?name={company}", String.class, company).getBody());
  }

  private void setStockPrice(final String company, final double price) {
    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("name", company);
    map.add("newPrice", price);
    testRestTemplate.postForEntity(HOST + "/set-price", new HttpEntity<>(map), String.class);
  }

  private void addCompany(final String name, final long amount, final double price) {
    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("name", name);
    map.add("stockAmount", amount);
    map.add("stockPrice", price);
    testRestTemplate.postForEntity(HOST + "/add-company", new HttpEntity<>(map), String.class);
  }
}
