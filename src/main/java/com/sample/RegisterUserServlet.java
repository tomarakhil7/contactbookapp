package com.sample;

import com.google.inject.Inject;

import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "registeruserservlet", urlPatterns = "/register")
public class RegisterUserServlet extends HttpServlet {
  private  ContactBookService contactBookService;
  @Inject
  public RegisterUserServlet(ContactBookService contactBookService)
  {
    this.contactBookService= new ContactBookService();
  }

  public RegisterUserServlet(){
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String userId = req.getParameter("UserId");
    String password = req.getParameter("Password");
    String username = req.getParameter("UserName");


    System.out.println(userId+password+username);
    try {
      if(contactBookService.registerUser(userId,password,username))
      {
        Cookie loginCookie = new Cookie("userId",userId);
        loginCookie.setMaxAge(30*60);
        resp.addCookie(loginCookie);
        RequestDispatcher view = req.getRequestDispatcher("login.html");
        view.forward(req,resp);
      }
      else
      {
        RequestDispatcher view = req.getRequestDispatcher("register.html");
        view.forward(req,resp);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
