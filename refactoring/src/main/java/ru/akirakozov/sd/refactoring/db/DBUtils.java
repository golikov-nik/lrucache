package ru.akirakozov.sd.refactoring.db;

import ru.akirakozov.sd.refactoring.data.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
  public static Product productFromRS(final ResultSet rs) throws SQLException {
    return new Product(rs.getString("name"), rs.getInt("price"));
  }
}
