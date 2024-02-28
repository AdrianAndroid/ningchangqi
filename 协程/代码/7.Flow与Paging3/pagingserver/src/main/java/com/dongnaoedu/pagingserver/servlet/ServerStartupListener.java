package com.dongnaoedu.pagingserver.servlet;

import com.dongnaoedu.pagingserver.utils.JsonFileReader;
import com.google.gson.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ningchuanqi
 * @description
 */
public class ServerStartupListener implements ServletContextListener{

    public static final List<Movie> MOVIES = new ArrayList<>();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //构建一个内存数据集合
        String json = JsonFileReader.load().toString();
        JsonObject rootElement = (JsonObject) new JsonParser().parse(json);
        JsonArray subjects = rootElement.get("subjects").getAsJsonArray();
        for (int i = 0; i < subjects.size(); i++) {
            JsonObject element = (JsonObject) subjects.get(i);
            Movie movie = new Movie();
            movie.setId(element.get("id").getAsInt());
            movie.setTitle(element.get("title").getAsString());
            movie.setRate(element.get("rate").getAsString());
            movie.setCover(element.get("cover").getAsString());
            MOVIES.add(movie);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
