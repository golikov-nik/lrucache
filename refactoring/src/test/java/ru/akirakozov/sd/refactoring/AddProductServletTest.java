package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

import static org.junit.Assert.assertEquals;

public class AddProductServletTest {
  public static final int PORT = 8081;
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

  @Test
  public void testAdd() throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    String format = "http://localhost:%d/add-product?name=%s&price=%d";
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format(format, PORT, "iphone6", 300)))
            .build();
    var response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(response.body(), "OK\n");
  }
}
