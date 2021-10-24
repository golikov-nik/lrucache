package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServletTest {
  public static final Product IPHONE_6 = new Product("iphone6", 300);
  public static final List<Product> PRODUCT_LIST = List.of(new Product("a", 1),
          new Product("b", 2),
          new Product("c", 3));
  public static final int PORT = 8081;
  public static final String LINE_END = "</br>";
  public static final String BASE_URI = "http://localhost:%d/".formatted(PORT);
  private final HttpClient client = HttpClient.newHttpClient();
  private Server server;

  @Before
  public void beforeTest() throws Exception {
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
      String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
              "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
              " NAME           TEXT    NOT NULL, " +
              " PRICE          INT     NOT NULL)";
      Statement stmt = c.createStatement();

      stmt.executeUpdate(sql);
      stmt.close();
    }

    server = new Server(PORT);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    context.addServlet(new ServletHolder(new AddProductServlet()), "/add-product");
    context.addServlet(new ServletHolder(new GetProductsServlet()), "/get-products");
    context.addServlet(new ServletHolder(new QueryServlet()), "/query");

    server.start();
  }

  @After
  public void afterTest() throws Exception {
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
      String sql = "DROP TABLE PRODUCT";
      Statement stmt = c.createStatement();

      stmt.executeUpdate(sql);
      stmt.close();
    }

    server.stop();
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
    return new Product(items[0], Integer.parseInt(items[1]));
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
