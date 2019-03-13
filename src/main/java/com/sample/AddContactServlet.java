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

@WebServlet(name = "addcontactservlet", urlPatterns = "/addContact")
public class AddContactServlet extends HttpServlet {
  ContactBookService contactBookService;
  @Inject
  public AddContactServlet(ContactBookService contactBookService)
  {
    this.contactBookService= contactBookService;
  }

  public AddContactServlet(){

  }
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String name = req.getParameter("Name");
    String emailAddress = req.getParameter("EmailAddress");
    String phoneNumber = req.getParameter("PhoneNumber");
    Cookie loginCookie = null;
    Cookie[] cookies = req.getCookies();
    if(cookies != null){
      for(Cookie cookie : cookies){
        if(cookie.getName().equals("userId")){
          loginCookie = cookie;
          break;
        }
      }
    }
    String userId=loginCookie.getValue();

    System.out.println(name+emailAddress+phoneNumber+userId);

    try {
      if(contactBookService.addContact(name,emailAddress,phoneNumber,userId))
      {
        RequestDispatcher view = req.getRequestDispatcher("home.html");
        view.forward(req,resp);
      }
      else
      {
        req.setAttribute("failure operation","addition failed");
        RequestDispatcher view = req.getRequestDispatcher("failed.jsp");
        view.forward(req,resp);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
