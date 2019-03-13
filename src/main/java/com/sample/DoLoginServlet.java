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

@WebServlet(name = "RegisterUserServlet", urlPatterns = "/login")
public class DoLoginServlet extends HttpServlet {
  private ContactBookService contactBookService;
  @Inject
  public DoLoginServlet(ContactBookService contactBookService)
  {
    this.contactBookService=contactBookService;
  }

  public DoLoginServlet(){
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String userId = req.getParameter("UserId");
    String password = req.getParameter("Password");
    System.out.println(userId+password);
    try {
      if(contactBookService.validateUser(userId,password))
      {
        Cookie loginCookie = new Cookie("userId",userId);
        loginCookie.setMaxAge(30*60);
        resp.addCookie(loginCookie);
        RequestDispatcher view = req.getRequestDispatcher("home.html");
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
