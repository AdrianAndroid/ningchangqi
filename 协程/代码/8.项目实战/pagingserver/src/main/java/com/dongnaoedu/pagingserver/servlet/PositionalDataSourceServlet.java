package com.dongnaoedu.pagingserver.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ningchuanqi
 * @description
 */
public class PositionalDataSourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PositionalDataSourceServlet doGet");
        int start = Integer.parseInt(request.getParameter("start"));
        int count = Integer.parseInt(request.getParameter("count"));
        System.out.println("start:"+start+",count:"+count);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("count",count);
        jsonObject.addProperty("start",start);
        jsonObject.addProperty("total",ServerStartupListener.MOVIES.size());

        Gson gson = new Gson();
        //从MOVIES集合中取出一段数据出来
        List<Movie> searchList = new ArrayList<>();
        for (int i = start; i < start + count; i++) {
            try{
                searchList.add(ServerStartupListener.MOVIES.get(i));
            }catch (IndexOutOfBoundsException e){
                //索引越界，跳出循环
                System.out.println(e.getMessage());
                break;
            }
        }

        JsonArray jsonArray = (JsonArray) gson.toJsonTree(searchList, new TypeToken<List<Movie>>(){}.getType());
        jsonObject.add("subjects",jsonArray);

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.print(jsonObject.toString());
        out.close();
    }
}
