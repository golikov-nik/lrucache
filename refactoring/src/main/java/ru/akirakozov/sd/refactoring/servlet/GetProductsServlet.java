package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

import static ru.akirakozov.sd.refactoring.db.DBManager.withDatabase;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
      withDatabase(stmt -> {
        ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");
        response.getWriter().println("<html><body>");

        while (rs.next()) {
          String  name = rs.getString("name");
          int price  = rs.getInt("price");
          response.getWriter().println(name + "\t" + price + "</br>");
        }
        response.getWriter().println("</body></html>");

        rs.close();
      });

      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
}
