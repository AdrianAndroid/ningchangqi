package com.dongnaoedu.kotlinstudyserver.servlet;

import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ningchuanqi
 * @description
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        if(user != null){
            System.out.println(user);
        }
        System.out.println("doGet");
        PrintWriter out = response.getWriter();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name","jason");
        jsonObject.addProperty("address","California");
        out.write(jsonObject.toString());
        System.out.println(jsonObject.toString());
        out.close();
    }


}
