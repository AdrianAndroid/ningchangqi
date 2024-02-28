package com.dongnaoedu.pagingserver.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CarBrandServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CarBrandServlet doGet");
        int since = Integer.parseInt(request.getParameter("since"));
        int pagesize = Integer.parseInt(request.getParameter("pagesize"));
        System.out.println("since:"+since+",pagesize:"+pagesize);

        //从CAR_BRANDS集合中取出一段数据出来
        List<CarBrand> searchList = new ArrayList<>();
        //第一次请求，since等于0，重新给since赋值为第一个元素的id
        if(since == 0){
            CarBrand carBrand = ServerStartupListener.CAR_BRANDS.get(0);
            searchList.add(carBrand);
            since = carBrand.getId();
        }

        Gson gson = new Gson();
        for (int i = 0; i < ServerStartupListener.CAR_BRANDS.size(); i++) {
            try{
                //通过请求参数since，找到id等于since的元素
                //往后找到pagesize个元素
                CarBrand carBrand = ServerStartupListener.CAR_BRANDS.get(i);
                if(carBrand.getId() == since){
                    for (int j = i+1; j < i + pagesize; j++) {
                        searchList.add(ServerStartupListener.CAR_BRANDS.get(j));
                    }
                }
            }catch (IndexOutOfBoundsException e){
                //索引越界，跳出循环
                System.out.println(e.getMessage());
                break;
            }
        }
        JsonArray jsonArray = (JsonArray) gson.toJsonTree(searchList, new TypeToken<List<CarBrand>>(){}.getType());
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonArray.toString());
        System.out.println(jsonArray.toString());
        out.close();
    }
}
