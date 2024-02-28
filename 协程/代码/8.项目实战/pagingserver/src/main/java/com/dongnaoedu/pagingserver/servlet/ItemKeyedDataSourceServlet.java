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
public class ItemKeyedDataSourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ItemKeyedDataSourceServlet doGet");
        int since = Integer.parseInt(request.getParameter("since"));
        int pagesize = Integer.parseInt(request.getParameter("pagesize"));
        System.out.println("since:"+since+",pagesize:"+pagesize);

        //从MOVIES集合中取出一段数据出来
        List<Movie> searchList = new ArrayList<>();
        //第一次请求，since等于0，重新给since赋值为第一个元素的id
        if(since == 0){
            Movie movie = ServerStartupListener.MOVIES.get(0);
            searchList.add(movie);
            since = movie.getId();
        }

        Gson gson = new Gson();
        for (int i = 0; i < ServerStartupListener.MOVIES.size(); i++) {
            try{
                //通过请求参数since，找到id等于since的元素
                //往后找到pagesize个元素
                Movie movie = ServerStartupListener.MOVIES.get(i);
                if(movie.getId() == since){
                    for (int j = i+1; j < i + pagesize; j++) {
                        searchList.add(ServerStartupListener.MOVIES.get(j));
                    }
                }
            }catch (IndexOutOfBoundsException e){
                //索引越界，跳出循环
                System.out.println(e.getMessage());
                break;
            }
        }
        JsonArray jsonArray = (JsonArray) gson.toJsonTree(searchList, new TypeToken<List<Movie>>(){}.getType());
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonArray.toString());
        System.out.println(jsonArray.toString());
        out.close();
    }
}
