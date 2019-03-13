package com.sample;

import com.google.inject.Inject;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ContactBookService {
  public Database database;

  @Inject
  ContactBookService(Database database) {
    this.database = database;
  }


  public boolean validateUser(String userId, String password) throws SQLException {
    Connection connection = database.connect();
    Statement st = connection.createStatement();
    String queryToValidate =
        "select * from  users where userid =" + userId + " and  password = " + password;
    ResultSet rs = st.executeQuery(queryToValidate);
    if (rs.next()) {
      System.out.println("User-Id : " + rs.getString(1));
    } else {
      System.out.println("No such user id is already registered");
      database.closeConnection();
      return false;
    }
    database.closeConnection();
    return true;
  }

  public boolean registerUser(String userId, String password,String username) throws SQLException {
    Connection connection = database.connect();
    PreparedStatement st =
        connection.prepareStatement(
            "Insert into users (username , userid, password ) Values (?,?,?)");
    st.setString(1, username);
    st.setString(2, userId);
    st.setString(3, password);
    int rows = st.executeUpdate();

    if (rows > 0) System.out.println("Successfully Registered");
    else System.out.println("Registration Failed");

    database.closeConnection();
    return true;
  }



  public boolean addContact(String name, String emailAddress, String phoneNumber, String userId)
      throws SQLException {
    Connection connection = database.connect();
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

    database.closeConnection();
    return true;
  }

  public boolean deleteContact(String name,String emailAddress ,String phoneNumber,String userId)
      throws SQLException {
    Connection connection = database.connect();
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

    database.closeConnection();
    return true;
  }

  public boolean updateContactPhoneNumber(String name,String emailAddress ,String phoneNumber,String userId)
      throws SQLException {
    Connection connection = database.connect();
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

    database.closeConnection();
    return true;
  }
}
