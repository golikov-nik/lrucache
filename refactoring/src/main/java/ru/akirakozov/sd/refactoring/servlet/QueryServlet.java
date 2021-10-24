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
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
          try (var writer = new HTMLWriter(response.getWriter())) {
            writer.printHeader("Product with max price: ");
            DBClient.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1",
                    rs -> writer.writeProduct(new Product(rs.getString("name"), rs.getInt("price")))
            );
          }
        } else if ("min".equals(command)) {
          try (var writer = new HTMLWriter(response.getWriter())) {
            writer.printHeader("Product with min price: ");
            DBClient.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1",
                    rs -> writer.writeProduct(new Product(rs.getString("name"), rs.getInt("price")))
            );
          }
        } else if ("sum".equals(command)) {
          try (var writer = new HTMLWriter(response.getWriter())) {
            writer.println("Summary price: ");
            DBClient.executeQuery("SELECT SUM(price) FROM PRODUCT",
                    rs -> writer.println(rs.getInt(1))
            );
          }
        } else if ("count".equals(command)) {
          try (var writer = new HTMLWriter(response.getWriter())) {
            writer.println("Number of products: ");
            DBClient.executeQuery("SELECT COUNT(*) FROM PRODUCT",
                    rs -> writer.println(rs.getInt(1)));
          }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
