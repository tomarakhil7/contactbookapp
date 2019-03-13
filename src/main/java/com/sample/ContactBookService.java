package com.sample;

import com.google.inject.Inject;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ContactBookService {
  private final String url;
  private final String user;
  private final String password;
  private Connection conn;

  @Inject
  ContactBookService() {
    this.url="jdbc:postgresql://localhost:5432/contactbookapp";
    this.user="akhil.t";
    this.password="wewillcode";
  }
  public Connection connect() {
    try {
      Class.forName("org.postgresql:postgresql:9.4.1207.jre7");
      conn = DriverManager.getConnection(url, user, password);
      System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return conn;
  }

  public void closeConnection (){
    conn=null;
  }



  public boolean validateUser(String userId, String password) throws SQLException {
    Connection connection = DriverManager.getConnection(url, user, password);
    System.out.println(connection);
    PreparedStatement st = connection.prepareStatement("select * from users where userid =? and password = ?");
    st.setString(1,userId);
    st.setString(2,password);
    ResultSet rs = st.executeQuery();
    if (rs.next()) {
      System.out.println("User-Id : " + rs.getString(1));
    } else {
      System.out.println("No such user id is already registered");
      closeConnection();
      return false;
    }
    closeConnection();
    return true;
  }

  public boolean registerUser(String userId, String password,String username) throws SQLException {
    Connection connection = connect();
    PreparedStatement st =
        connection.prepareStatement(
            "Insert into users (username , userid, password ) Values (?,?,?)");
    st.setString(1, username);
    st.setString(2, userId);
    st.setString(3, password);
    int rows = st.executeUpdate();

    if (rows > 0) System.out.println("Successfully Registered");
    else System.out.println("Registration Failed");

    closeConnection();
    return true;
  }



  public boolean addContact(String name, String emailAddress, String phoneNumber, String userId)
      throws SQLException {
    Connection connection = connect();
    PreparedStatement st =
        connection.prepareStatement(
            "Insert into contactbook (name , emailaddress, phonenumber , userid ) Values (?,?,?,?)");
    st.setString(1, name);
    st.setString(2, emailAddress);
    st.setString(3, phoneNumber);
    st.setString(4, userId);

    int rows = st.executeUpdate();

    if (rows > 0) System.out.println("Successfully Inserted");
    else System.out.println("Insert Failed");

   closeConnection();
    return true;
  }

  public boolean deleteContact(String name,String emailAddress ,String phoneNumber,String userId)
      throws SQLException {
    Connection connection = connect();
    PreparedStatement st =
        connection.prepareStatement(
            "delete  from  contactbook where  name =?  and  emailaddress = ? and phonenumber = ? and  userid= ? ");
    st.setString(1, name);
    st.setString(2, emailAddress);
    st.setString(3, phoneNumber);
    st.setString(4, userId);

    int rows = st.executeUpdate();

    if (rows > 0) System.out.println("Successfully Deleted");
    else System.out.println("Deletion  Failed");

    closeConnection();
    return true;
  }

  public boolean updateContactPhoneNumber(String name,String emailAddress ,String phoneNumber,String userId)
      throws SQLException {
    Connection connection = connect();
    PreparedStatement st =
        connection.prepareStatement(
            "update    contactbook set  phonenumber =?  where   emailaddress = ? and name = ? and  userid= ? ");
    st.setString(3, name);
    st.setString(2, emailAddress);
    st.setString(1, phoneNumber);
    st.setString(4, userId);

    int rows = st.executeUpdate();

    if (rows > 0) System.out.println("Successfully updated phonenumber");
    else System.out.println("updation  Failed");

    closeConnection();
    return true;
  }
}
