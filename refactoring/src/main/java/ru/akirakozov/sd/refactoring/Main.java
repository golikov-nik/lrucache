package ru.akirakozov.sd.refactoring;

import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.server.ServerManager;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
      DBManager.initDB();
      ServerManager.start();
      ServerManager.join();
    }
}
