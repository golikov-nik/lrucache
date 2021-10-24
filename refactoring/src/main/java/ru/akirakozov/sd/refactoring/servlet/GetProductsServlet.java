package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.db.DBClient;
import ru.akirakozov.sd.refactoring.html.HTMLWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      try (var writer = new HTMLWriter(response.getWriter())) {
        DBClient.executeQuery("SELECT * FROM PRODUCT",
                rs -> writer.writeProduct(new Product(rs.getString("name"), rs.getInt("price")))
        );
      }

      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
}
