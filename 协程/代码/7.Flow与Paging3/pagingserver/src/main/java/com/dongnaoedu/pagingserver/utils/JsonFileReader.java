package com.dongnaoedu.pagingserver.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author ningchuanqi
 * @description
 */
public class JsonFileReader {

    public static StringBuilder load(){
        StringBuilder sb = new StringBuilder();
        URL url = JsonFileReader.class.getClassLoader().getResource("data.json");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
            String line = null;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }

}
