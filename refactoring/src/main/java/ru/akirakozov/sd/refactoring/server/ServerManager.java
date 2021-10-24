package ru.akirakozov.sd.refactoring.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

public class ServerManager {
  public static final int PORT = 8081;
  private static final Server server = new Server(PORT);

  public static void start() throws Exception {
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    context.addServlet(new ServletHolder(new AddProductServlet()), "/add-product");
    context.addServlet(new ServletHolder(new GetProductsServlet()),"/get-products");
    context.addServlet(new ServletHolder(new QueryServlet()),"/query");

    server.start();
  }

  public static void join() throws InterruptedException {
    server.join();
  }

  public static void stop() throws Exception {
    server.stop();
  }
}
