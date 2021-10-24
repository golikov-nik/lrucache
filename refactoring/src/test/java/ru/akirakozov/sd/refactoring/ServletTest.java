package ru.akirakozov.sd.refactoring;

import org.junit.After;
import org.junit.Before;
import ru.akirakozov.sd.refactoring.data.Product;
import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.server.ServerManager;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServletTest {
  public static final Product IPHONE_6 = new Product("iphone6", 300);
  public static final List<Product> PRODUCT_LIST = List.of(new Product("a", 1),
          new Product("b", 2),
          new Product("c", 3));
  public static final String LINE_END = "</br>";
  public static final String BASE_URI = "http://localhost:%d/".formatted(ServerManager.PORT);
  private final HttpClient client = HttpClient.newHttpClient();

  @Before
  public void beforeTest() throws Exception {
    DBManager.initDB();
    ServerManager.start();
  }

  @After
  public void afterTest() throws Exception {
    DBManager.dropDB();
    ServerManager.stop();
  }

  protected void addProduct(final Product product) {
    String uri = "add-product?name=%s&price=%d".formatted(product.name(), product.price());
    assertEquals(makeRequest(uri), "OK\n");
  }

  protected void addProducts(final Collection<Product> products) {
    products.forEach(this::addProduct);
  }

  protected List<String> getLines(final String body) {
    var result = List.of(body.split("\n"));
    assertTrue(result.size() >= 2);
    assertEquals(result.get(0), "<html><body>");
    assertEquals(result.get(result.size() - 1), "</body></html>");
    return result.subList(1, result.size() - 1);
  }

  protected String makeRequest(final String uri) {
    try {
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(BASE_URI + uri))
              .build();
      return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    } catch (final IOException | InterruptedException e) {
      throw new RuntimeException("Exception occured while making request", e);
    }
  }

  protected Product readProduct(final String line) {
    assertTrue(line.endsWith(LINE_END));
    var items = line.substring(0, line.length() - LINE_END.length()).split("\t");
    assertEquals(items.length, 2);
    return new Product(items[0], Long.parseLong(items[1]));
  }

  protected Set<Product> getProducts() {
    return getLines(makeRequest("get-products"))
            .stream()
            .map(this::readProduct)
            .collect(Collectors.toSet());
  }

  protected String makeQuery(final String command) {
    return makeRequest("query?command=" + command);
  }
}
