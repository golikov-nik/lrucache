package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.html.HTMLWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static ru.akirakozov.sd.refactoring.db.DBUtils.productFromRS;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      try (var writer = new HTMLWriter(response.getWriter())) {
        DBManager.queryAll(rs -> writer.writeProduct(productFromRS(rs)));
      }

      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
}
