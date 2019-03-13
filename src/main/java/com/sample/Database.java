package com.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  private final String url;
  private final String user;
  private final String password;
  private Connection conn;


  Database()
  {
    this.url="localhost:5432/contactbookapp";
    this.user="akhil.t";
    this.password="wewillcode";

  }
  public Connection connect() {
    try {
      conn = DriverManager.getConnection(url, user, password);
      System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return conn;
  }

  public void closeConnection (){
    conn=null;
  }

}
