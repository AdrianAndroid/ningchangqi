package com.dongnaoedu.kotlinstudyserver.servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
public class ArticleServlet extends HttpServlet {

    List<String> data = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        data.add("Refactored versions of the Android APIs that are not bundled with the operating system.");
        data.add("Jetpack Compose is a modern toolkit for building native Android UI. Jetpack Compose simplifies and accelerates UI development on Android with less code, powerful tools, and intuitive Kotlin APIs.");
        data.add("Includes APIs for testing your Android app, including Espresso, JUnit Runner, JUnit4 rules, and UI Automator.");
        data.add("Includes ConstraintLayout and related APIs for building constraint-based layouts.");
        data.add("Includes APIs to help you write declarative layouts and minimize the glue code necessary to bind your application logic and layouts.");
        data.add("Provides APIs for building Android Automotive apps.");
        data.add("A library for building Android Auto apps. This library is currently in beta. You can design, develop, and test navigation, parking, and charging apps for Android Auto, but you can't distribute these apps through the Google Play Store yet. We will make announcements in the future when you can distribute these apps through the Google Play Store.");
        data.add("Provides APIs to build apps for wearable devices running Wear OS by Google.");
        data.add("Material Components for Android (MDC-Android) help developers execute Material Design to build beautiful and functional Android apps.");
        data.add("The Android NDK is a toolset that lets you implement parts of your app in native code, using languages such as C and C++.");
        data.add("The Android Gradle Plugin (AGP) is the supported build system for Android applications and includes support for compiling many different types of sources and linking them together into an application that you can run on a physical Android device or an emulator.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("key");
        if (key != null) {
            System.out.println(key);
        }else{
            key = "Includes";
        }
        System.out.println("doGet");
        PrintWriter out = response.getWriter();
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < data.size(); i++) {
            String text = data.get(i);
            if (text.contains(key)) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", i);
                jsonObject.addProperty("text", text);
                jsonArray.add(jsonObject);
            }
        }
        out.write(jsonArray.toString());
        System.out.println(jsonArray.toString());
        out.close();
    }


}
