package com.dongnaoedu.pagingserver.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ningchuanqi
 * @description
 */
public class PageKeyedDataSourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PageKeyedDataSourceServlet doGet");
        int page = Integer.parseInt(request.getParameter("page"));
        int pagesize = Integer.parseInt(request.getParameter("pagesize"));
        System.out.println("page:"+page+",pagesize:"+pagesize);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("has_more",true);
        Gson gson = new Gson();
        //从MOVIES集合中取出一段数据出来
        List<Movie> searchList = new ArrayList<>();
        int end = page * pagesize;
        int begin = end - pagesize;
        System.out.println("begin:"+begin+",end:"+end);
        for (int i = begin; i < end; i++) {
            try{
                searchList.add(ServerStartupListener.MOVIES.get(i));
            }catch (IndexOutOfBoundsException e){
                //索引越界，跳出循环
                System.out.println(e.getMessage());
                if (i > ServerStartupListener.MOVIES.size()) {
                    jsonObject.addProperty("has_more",false);
                }
                break;
            }
        }
        JsonArray jsonArray = (JsonArray) gson.toJsonTree(searchList, new TypeToken<List<Movie>>(){}.getType());
        jsonObject.add("subjects",jsonArray);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonObject.toString());
        System.out.println(jsonObject.toString());
        out.close();
    }
}
