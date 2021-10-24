package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.DBClient;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
      DBClient.executeQuery("SELECT * FROM PRODUCT",
        rs -> response.getWriter().println("<html><body>"),
        rs -> {
          String  name = rs.getString("name");
          int price  = rs.getInt("price");
          response.getWriter().println(name + "\t" + price + "</br>");

        },
        rs -> response.getWriter().println("</body></html>"));

      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
}
